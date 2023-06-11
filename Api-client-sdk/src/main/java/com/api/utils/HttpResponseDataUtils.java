package com.api.utils;

import cn.hutool.http.HttpResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * response 接收第三方接口封装的返回值，格式为{"状态码":"返回信息"}
 * @author Editor
 */
public class HttpResponseDataUtils {
    /**
     * response 接收第三方接口封装的返回值，格式为{"状态码":"返回信息"}
     *
     * @param response
     * @return
     */
    public static ImmutablePair<Integer, String> resData(HttpResponse response) {
        String str = response.body();
        // 状态码为 200 时，切割出返回值
        if (response.getStatus() == 200) {
            str = str.substring(1, str.length() - 1);
            String value = "SDK 字符串匹配出错";
            // 只截取文字部分
            String regex = ".*:(.*)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                value = matcher.group(1).replaceAll("\"", "");
            }
            return ImmutablePair.of(200, value);
        } else {
            // 非正常状态时，返回状态码和数值
            return ImmutablePair.of(response.getStatus(), str);
        }
    }

    /**
     * response 接收第三方接口封装的返回值中的image地址，格式为{"状态码":"ImageUrl"}
     * @param response
     * @return
     */
    public static ImmutablePair<Integer, String> resDataImage(HttpResponse response) {
        String str = response.body();
        // 状态码为 200 时，切割出返回值
        if (response.getStatus() == 200) {
            String imageTemp = str.split(",")[1];
            int index = imageTemp.indexOf(":");
            String imageUrl = imageTemp.substring(index+1);
            String imageUrlTemp = imageUrl.replaceAll("\\\\", "");
            int length = imageUrlTemp.length();
            String value = imageUrlTemp.substring(1, length - 1).split(":")[1];
            return ImmutablePair.of(200, value);
        } else {
            // 非正常状态时，返回状态码和数值
            return ImmutablePair.of(response.getStatus(), str);
        }
    }

    /**
     * response 接收第三方接口封装的返回值，格式为{"状态码":"返回信息"}
     *
     * @param response
     * @return
     */
    public static ImmutablePair<Integer, String> resDataTranslate(HttpResponse response,int index) {
        String str = response.body();
        // 状态码为 200 时，切割出返回值
        if (response.getStatus() == 200) {
            String strTemp = str.split(",")[index];
            String data = strTemp.split(":")[1];
            String value = data.replaceAll("\\\\","").replaceAll("}", "").replaceAll("\"","");
            return ImmutablePair.of(200, value);
        } else {
            // 非正常状态时，返回状态码和数值
            return ImmutablePair.of(response.getStatus(), str);
        }
    }
}