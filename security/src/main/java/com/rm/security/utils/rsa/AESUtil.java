package com.rm.security.utils.rsa;

import com.rm.security.web.response.ResponseEnum;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AESUtil {


    /**
     * 根据参数生成KEY
     */
    public static Key getKey(String strKey) {
        try {
            byte[] raw = strKey.getBytes("utf-8");
            return new SecretKeySpec(raw, "AES");
        } catch (Exception e) {
            throw ResponseEnum.SYSTEM_ERROR.throwEx("AES key error: ", e);
        }
    }


    public static byte[] encrypt(String keyData, byte[] raw) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(keyData));
            return cipher.doFinal(raw);
        } catch (Exception e) {
            throw ResponseEnum.SYSTEM_ERROR.throwEx("AES加密失败!", e);
        }
    }

    public static byte[] decrypt(String keyData, byte[] pwData) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getKey(keyData));
            return cipher.doFinal(pwData);
        } catch (Exception e) {
            throw ResponseEnum.SYSTEM_ERROR.throwEx("AES文件解密失败!", e);
        }
    }

    public static void main(String[] args) {
        String key = "1234567812345678";
        String data = "ndasndioa";
        byte[] pwdata = encrypt(key, data.getBytes());
        System.out.println(new String(pwdata));
        byte[] raw = decrypt(key, pwdata);
        System.out.println(new String(raw));
    }


}
