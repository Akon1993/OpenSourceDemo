package com.lihg.test;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

public class Main {

    public static void main(String[] args) {
        String content = "test中文";
        // 随机生成密钥
        String value = SymmetricAlgorithm.AES.getValue();
        System.out.println(value);
        byte[] key = SecureUtil.generateKey(value).getEncoded();
        for (int i = 0; i < key.length; i++) {
            System.out.println(key[i]);
        }
        System.out.println(key.length);
        // 构建
        AES aes = SecureUtil.aes(key);
        // 加密
        byte[] encrypt = aes.encrypt(content);
        // 解密
        byte[] decrypt = aes.decrypt(encrypt);
        // 加密为16进制表示
        String encryptHex = aes.encryptHex(content);
        // 解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(encryptHex);
        System.out.println(decryptStr);
    }

}
