package com.api.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.springframework.util.DigestUtils;

/**
 * @author Editor
 */
public class SignUtil {
    /**
     * 生成加密签名
     * @param randomNum
     * @param secretKey
     * @return
     */
    public static String getSign(String randomNum,String secretKey){
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content =  randomNum + "." + secretKey;
        return md5.digestHex(content);
    }
}
