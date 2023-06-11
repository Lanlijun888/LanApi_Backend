package com.yupi;

import com.api.apiCommon.model.entity.InterfaceInfo;
import com.api.apiCommon.model.entity.User;
import com.api.apiCommon.model.entity.UserInterfaceInfo;
import com.api.apiCommon.service.InnerInterfaceInfoService;
import com.api.apiCommon.service.InnerUserInterfaceInfoService;
import com.api.apiCommon.service.InnerUserService;
import com.api.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局过滤器
 * @author Editor
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;
    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    @DubboReference
    private InnerUserService innerUserService;
    public static final String INTERFACE_HOST = "http://localhost:8123";

    /**
     * 过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //请求地址
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        //3. 用户鉴权（判断ak，sk是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nacos = headers.getFirst("nacos");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");

        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error",e);
        }

        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInterfaceInfo error",e);
        }
        if(invokeUser == null || interfaceInfo == null){
            //根据accessKey查询数据库是否存在
            return handleNoAuth(response);
        }

        Long userId = invokeUser.getId();
        Long interfaceInfoId = interfaceInfo.getId();
        //根据userId和interfaceInfoId查询数据库，对于status是否正常
        UserInterfaceInfo userInterfaceIfo = innerUserInterfaceInfoService.getUserInterfaceIfo(interfaceInfoId, userId);
        if(userInterfaceIfo.getStatus() != 0){
            return handleInvokeError(response);
        }


        if(nacos.length() != 4){
            return handleNoAuth(response);
        }

        //判断nacos是否在有效期内，防止重放，打算采用Redis
        long currentTime = System.currentTimeMillis() / 1000;
        if((currentTime - Long.parseLong(timestamp)) > 60*5L){
            return handleNoAuth(response);
        }

        //取出secretKey重新生成密钥
        String checkSign = SignUtil.getSign(nacos,invokeUser.getSecretKey());
        if(!checkSign.equals(sign)){
            return handleNoAuth(response);
        }

        //6. 请求转发，调用模拟接口
        Mono<Void> filter = chain.filter(exchange);
        return responseLog(exchange,chain,interfaceInfoId,userId);
    }

    /**
     * 处理响应
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> responseLog(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceInfoId,long userId) {
        try {
            //拿到response对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            //缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if(statusCode == HttpStatus.OK){
                //拿到被装饰的response
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                // TODO 调用成功，接口调用次数+1
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error",e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                sb2.append("<--- {} {} \n");
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                log.info("响应结果"+data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            //降级处理返回数据
            return chain.filter(exchange);
        }catch (Exception e){
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        //禁止访问
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        //禁止访问
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}