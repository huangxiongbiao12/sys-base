package com.rm.security.utils.rsa;


import com.rm.security.utils.UUIDUitl;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: smart-meeting-server
 * @description: 非对称加密
 * @author: Mr.Zhang
 * @create: 2022-02-22 11:01
 **/
public class RSAUtil {
    private final static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNDy7J7kow1LmIcg84ZKWOJTayOcV+htTx5Kd8M7PiQLd6k5/A9BiFEGg9wb7T3wkkVXoV767qb5x0O73CJsnTruKklmrkavEhrdz2mcWj4BMoTblw6blm6iy3J1gv554jNvfWaQ2luNtQij7fl8o0XGcClkmCf2pNbNVvFv27zwIDAQAB";
    private final static String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI0PLsnuSjDUuYhyDzhkpY4lNrI5xX6G1PHkp3wzs+JAt3qTn8D0GIUQaD3BvtPfCSRVehXvrupvnHQ7vcImydOu4qSWauRq8SGt3PaZxaPgEyhNuXDpuWbqLLcnWC/nniM299ZpDaW421CKPt+XyjRcZwKWSYJ/ak1s1W8W/bvPAgMBAAECgYBVD7LHUkqq55x6C03iU88ByK8TCoNtqSEAPUJCQ2tWJHCAGkVbtdaTZzF1DEBmwzhgEKtuTvrLpEJUVTI4wesyNSL/gfHrf99XYxFmvym1ZW9l5gLQ9GoJiL8zO84grhvNpQuQWVlPjLt7JUU8EgkBf5mq/VXeKlIWmElgNt7rAQJBANFFgWq0N8cDS6USlAVd3zPQZotmGM3EYK6d2T8rhNjiblNCHmZhPisw8ISWoY724olpNUfGzoQZLGQzzE9y3uUCQQCsjn5YX/DuEVa+hlTKm6oKwLbXaZUbyEN30nf0MDcAPvJyyDxvb9GlOY9HXWciSfgDUhlwgAmXF39IRNHVBZCjAkBqMuWMKsyrd+apbJYlaw/cEYQYdcVclq2hr4UR45nVHoNySPl4ZxzPelR0VKTEElG//d4GAMPIkaXPa+Hg56BpAkAWLBUV+/X0USwJPS+F/SyDRzH3SezL7halPdkF+nAGyw/SKeKCGycgCN5dOCY5ZLHGk3ULXJhe3rkPKzCfI6DrAkBLr0bQsSlRh17wBxWOS4Uc3cS5OF1ZlkK1Q15sV8Vn32tBmy1ohKmFexWpw10x79RDcxCK650Y0KMNTVv1i1dZ";
    //用于封装随机产生的公钥与私钥
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();

    public static void main(String[] args) throws Exception {
        String key = UUIDUitl.generateMixString(16);
        String encryKey = RSAUtil.encrypt(key, PUBLIC_KEY);
        System.out.println("encryKey:" + encryKey);

        //生成公钥和私钥
//            genKeyPair();
        //加密字符串
        String message = "testMsg";
        System.out.println("随机生成的公钥为:" + PUBLIC_KEY);
        System.out.println("随机生成的私钥为:" + PRIVATE_KEY);
        String messageEn = encrypt(message, PUBLIC_KEY);
        System.out.println("明文：" + message);
        System.out.println("加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn, PRIVATE_KEY);
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
