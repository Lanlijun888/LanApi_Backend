package com.api.client;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.api.utils.HttpResponseDataUtils;
import com.api.utils.Params2JsonUtils;
import com.api.utils.SignUtil;
import com.google.gson.JsonElement;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Editor
 */
@Data
public class ApiClient {

    String accessKey;
    String secretKey;

    public static final String GATEWAY_LOCALHOST = "http://localhost:8090";

    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getName(String name){
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("name","Editor");
        String result = HttpUtil.get(GATEWAY_LOCALHOST+"/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String postNameByParams(String name){
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("name","user");
        String result = HttpUtil.post(GATEWAY_LOCALHOST+"/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public Map<String, String> getMap(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        String nacos = RandomUtil.randomNumbers(4);
        hashMap.put("nacos",nacos);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtil.getSign(nacos,secretKey));
        return hashMap;
    }

    public ImmutablePair<Integer, String> postNameByBody( String userParams){
        //发送请求时携带上accessKey和secretKey
        HttpResponse response = HttpRequest.post(GATEWAY_LOCALHOST+"/api/name/user")
                .body(userParams).addHeaders(getMap()).execute();
        return HttpResponseDataUtils.resData(response);
    }


    /**
     * 随机输出一句冷笑话
     * charset 返回编码类型[gbk|utf-8]默认utf-8
     * encode 返回格式类型[text|js|json]默认text
     * @return 状态码和响应值
     */
    public ImmutablePair<Integer, String> randomMessage(String params) {
        String charset, encode;
        HttpRequest request = HttpRequest.get(GATEWAY_LOCALHOST + "/api/invoke/randomMessage");
        if (StringUtils.isNotBlank(params)) {
            JsonElement jsonParams = Params2JsonUtils.getJsonParams(params);
            charset = jsonParams.getAsJsonObject().get("charset").getAsString();
            encode = jsonParams.getAsJsonObject().get("encode").getAsString();
            // 不传参时使用默认参数
            request = StringUtils.isNotBlank(charset) ? request.form("charset", charset) : request.form("charset", "utf-8");
            request = StringUtils.isNotBlank(encode) ? request.form("encode", encode) : request.form("encode", "text");
        }

        HttpResponse response = request.addHeaders(getMap()).execute();
        return HttpResponseDataUtils.resData(response);
    }

    /**
     * 随机返回二次元图片
     * @return 状态码和响应值
     */
    public ImmutablePair<Integer, String> randomACGPictures() {
        HttpRequest request = HttpRequest.get(GATEWAY_LOCALHOST + "/api/invoke/randomACGPictures");
        HttpResponse response = request.addHeaders(getMap()).execute();
        return HttpResponseDataUtils.resData(response);
    }

    /**
     * 一句一言
     * charset 返回编码类型[gbk|utf-8]默认utf-8
     * encode 返回格式类型[text|js|json]默认text
     * @return 状态码和响应值
     */
    public ImmutablePair<Integer, String> randomIan() {
        HttpRequest request = HttpRequest.get(GATEWAY_LOCALHOST + "/api/invoke/randomIan");
        HttpResponse response = request.addHeaders(getMap()).execute();
        return HttpResponseDataUtils.resData(response);
    }

    /**
     * 随机笑话
     * charset 返回编码类型[gbk|utf-8]默认utf-8
     * encode 返回格式类型[text|js|json]默认text
     * @return 状态码和响应值
     */
    public ImmutablePair<Integer, String> randomJoke() {
        HttpRequest request = HttpRequest.get(GATEWAY_LOCALHOST + "/api/invoke/randomJoke");
        HttpResponse response = request.addHeaders(getMap()).execute();
        return HttpResponseDataUtils.resData(response);
    }

    /**
     * 随机图片生成
     * url:网站地址
     * @return 状态码和响应值
     */
    public ImmutablePair<Integer, String> randomImage() {
        HttpRequest request = HttpRequest.get(GATEWAY_LOCALHOST + "/api/invoke/randomImage");
        HttpResponse response = request.addHeaders(getMap()).execute();
        return HttpResponseDataUtils.resData(response);
    }

    /**
     * 根据QQ号获取头像
     * url:网站地址
     * @return 状态码和响应值
     */
    public ImmutablePair<Integer, String> getQQImage(String QQId) {
        HttpRequest request = HttpRequest.get(GATEWAY_LOCALHOST + "/api/invoke/getQQImage?QQId=" + QQId);
        HttpResponse response = request.addHeaders(getMap()).execute();
        return HttpResponseDataUtils.resDataImage(response);
    }

    /**
     * 获取网站标题
     * @param url 网址
     * @return
     */
    public ImmutablePair<Integer, String> getWebTitle(String url) {
        HttpRequest request = HttpRequest.get(GATEWAY_LOCALHOST + "/api/invoke/getWebTitle?url=" + url);
        HttpResponse response = request.addHeaders(getMap()).execute();
        return HttpResponseDataUtils.resDataTranslate(response,1);
    }

    /**
     * 万能翻译
     * @return 状态码和响应值
     */
    public ImmutablePair<Integer, String> translate(String text) {
        HttpRequest request = HttpRequest.get(GATEWAY_LOCALHOST + "/api/invoke/translate?text=" + text);
        HttpResponse response = request.addHeaders(getMap()).execute();
        return HttpResponseDataUtils.resDataTranslate(response,3);
    }


}
