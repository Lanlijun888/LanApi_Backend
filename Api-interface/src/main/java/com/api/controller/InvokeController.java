package com.api.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.api.utils.ImgBase64Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.*;

/**
 * 第三方接口
 * @author Editor
 */
@RestController
@RequestMapping("/invoke")
public class InvokeController {
    /**
     * 随机输出一句冷笑话
     * @param charset 返回编码类型[gbk|utf-8]默认utf-8
     * @param encode  返回格式类型[text|js|json]默认text
     * @return 接口封装的返回值，格式为{"状态码":"返回信息"}，并将结果返回给 SDK，交由 SDK 进行解析
     */
    @GetMapping("/randomMessage")
    public ImmutablePair<Integer, String> randomMessage(String charset, String encode) {
        HttpRequest request = HttpRequest.get("https://api.btstu.cn/yan/api.php");
        if (StringUtils.isNotBlank(charset)) {
            request.form(charset, charset);
        }

        if (StringUtils.isNotBlank(encode)) {
            request.form(encode, encode);
        }
        HttpResponse response = request.execute();
        return ImmutablePair.of(response.getStatus(), response.body());
    }

    /**
     * 随机返回二次元图片
     *
     * @return 接口封装的返回值，格式为{"状态码":"返回信息"}，并将结果返回给 SDK，交由 SDK 进行解析
     */
    @GetMapping("/randomACGPictures")
    public ImmutablePair<Integer, String> randomACGPictures() {
        HttpRequest request = HttpRequest.post("https://tenapi.cn/v2/acg");
        HttpResponse response = request.execute();
        // 重定向之后的地址
        String LOCATION = response.header(Header.LOCATION);
        int status = response.getStatus();
        return ImmutablePair.of(status == 302 ? 200 : status, status == 302 ? LOCATION : response.body());
    }

    /**
     * 一句一言
     * @return 接口封装的返回值，格式为{"状态码":"返回信息"}，并将结果返回给 SDK，交由 SDK 进行解析
     */
    @GetMapping("/randomIan")
    public ImmutablePair<Integer, String> randomIan() {
        HttpRequest request = HttpRequest.get("https://api.vvhan.com/api/ian");
        HttpResponse response = request.execute();
        return ImmutablePair.of(response.getStatus(), response.body());
    }

    /**
     * 随机笑话
     * @return 接口封装的返回值，格式为{"状态码":"返回信息"}，并将结果返回给 SDK，交由 SDK 进行解析
     */
    @GetMapping("/randomJoke")
    public ImmutablePair<Integer, String> randomJoke() {
        HttpRequest request = HttpRequest.get("https://api.vvhan.com/api/joke");
        HttpResponse response = request.execute();
        return ImmutablePair.of(response.getStatus(), response.body());
    }

    /**
     * 随机一张图片
     * @return 接口封装的返回值，格式为{"状态码":"返回信息"}，并将结果返回给 SDK，交由 SDK 进行解析
     */
    @GetMapping("/randomImage")
    public ImmutablePair<Integer, String> randomImage() {
        HttpRequest request = HttpRequest.get("https://api.uomg.com/api/rand.img2");
        HttpResponse response = request.execute();
        // 重定向之后的地址
        String LOCATION = response.header(Header.LOCATION);
        int status = response.getStatus();
        return ImmutablePair.of(status == 302 ? 200 : status, status == 302 ? LOCATION : response.body());
    }

    /**
     * 根据QQ获取QQ头像
     * @return 接口封装的返回值，格式为{"状态码":"返回信息"}，并将结果返回给 SDK，交由 SDK 进行解析
     */
    @GetMapping("/getQQImage")
    public ImmutablePair<Integer, String> getQQImage(String QQId) {
        HttpRequest request = HttpRequest.get("https://api.vvhan.com/api/qq?qq=" + QQId);
        HttpResponse response = request.execute();
        return ImmutablePair.of(response.getStatus(), response.body());
    }

    /**
     * 获取网站标题
     * @return 接口封装的返回值，格式为{"状态码":"返回信息"}，并将结果返回给 SDK，交由 SDK 进行解析
     */
    @GetMapping("/getWebTitle")
    public ImmutablePair<Integer, String> getWebName(String url) {
        HttpRequest request = HttpRequest.get("https://api.vvhan.com/api/title?url=" + url);
        HttpResponse response = request.execute();
        return ImmutablePair.of(response.getStatus(), response.body());
    }

    /**
     * 获取网站标题
     * @return 接口封装的返回值，格式为{"状态码":"返回信息"}，并将结果返回给 SDK，交由 SDK 进行解析
     */
    @GetMapping("/translate")
    public ImmutablePair<Integer, String> translate(String text) {
        HttpRequest request = HttpRequest.get("https://api.vvhan.com/api/fy?text=" + text);
        HttpResponse response = request.execute();
        return ImmutablePair.of(response.getStatus(), response.body());
    }

}