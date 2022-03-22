package com.rm.security.utils.rsa;


import com.rm.security.web.response.ResponseEnum;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;

public class EncFileUtils {


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

    /**
     * 文件file进行加密并保存目标文件destFile中
     *
     * @param keyData 加密密钥
     * @param is      需要加密的流   要加密的文件 如c:/test/srcFile.txt
     * @param out     加密之后的流 如c:/加密后文件.txt
     */
    public static void encrypt(String keyData, InputStream is, OutputStream out) {
        CipherInputStream cis = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(keyData));
            cis = new CipherInputStream(is, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = cis.read(buffer)) > 0) {
                out.write(buffer, 0, r);
            }
            cis.close();
            is.close();
            out.close();
        } catch (Exception e) {
            throw ResponseEnum.SYSTEM_ERROR.throwEx("文件加密失败!", e);
        } finally {
            closeIO(cis, is, out);
        }
    }

    /**
     * 文件采用AES算法解密文件
     *
     * @param keyData 解密密钥
     * @param is      需要解密的流   要加密的文件 如c:/test/srcFile.txt
     * @param out     加解密密之后的流 如c:/加密后文件.txt
     */
    public static void decrypt(String keyData, InputStream is, OutputStream out) {
        CipherInputStream cis = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getKey(keyData));
            CipherOutputStream cos = new CipherOutputStream(out, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = is.read(buffer)) >= 0) {
                System.out.println();
                cos.write(buffer, 0, r);
            }
            cos.close();
            out.close();
            is.close();
        } catch (Exception e) {
            throw ResponseEnum.SYSTEM_ERROR.throwEx("文件加密失败!", e);
        } finally {
            closeIO(cis, is, out);
        }
    }

    /**
     * 关闭流
     *
     * @param cis
     * @param is
     * @param out
     */
    private static void closeIO(CipherInputStream cis, InputStream is, OutputStream out) {
        if (cis != null) {
            try {
                cis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件file进行加密并保存目标文件destFile中
     *
     * @param file     要加密的文件 如c:/test/srcFile.txt
     * @param destFile 加密后存放的文件名 如c:/加密后文件.txt
     */
    public static void encrypt(String key, String file, String destFile) throws Exception {
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        encrypt(key, is, out);
    }

    /**
     * 文件采用AES算法解密文件
     *
     * @param file 已加密的文件 如c:/加密后文件.txt
     *             * @param destFile
     *             解密后存放的文件名 如c:/ test/解密后文件.txt
     */
    public static void decrypt(String key, String file, String dest) throws Exception {
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        decrypt(key, is, out);
    }

    public static void main(String[] args) throws Exception {


        String key = "1234567812345678";
        EncFileUtils.encrypt(key, "/Users/Apple/Desktop/code/resources/shaunggang/1.txt", "/Users/Apple/Desktop/code/resources/shaunggang/1enc.txt"); //加密
        EncFileUtils.decrypt(key, "/Users/Apple/Desktop/code/resources/shaunggang/1enc.txt", "/Users/Apple/Desktop/code/resources/shaunggang/1dec.txt"); //解密

        EncFileUtils.encrypt(key, "/Users/Apple/Desktop/code/resources/shaunggang/1.jpg", "/Users/Apple/Desktop/code/resources/shaunggang/1enc.jpg"); //加密
        EncFileUtils.decrypt(key, "/Users/Apple/Desktop/code/resources/shaunggang/1enc.jpg", "/Users/Apple/Desktop/code/resources/shaunggang/1dec.jpg"); //解密


//        File file = new File("/Users/Apple/Desktop/code/resources/shaunggang/1.txt");
//        File encfile = new File("/Users/Apple/Desktop/code/resources/shaunggang/1enc.txt");
//        InputStream is = new FileInputStream(file);
//        OutputStream os = new FileOutputStream(encfile);
//        EncFileUtils.encrypt("qqqq", is, os); //加密
//
//        File encfileNew = new File("/Users/Apple/Desktop/code/resources/shaunggang/1enc.txt");
//        File decfile = new File("/Users/Apple/Desktop/code/resources/shaunggang/1dec.txt");
//        InputStream is1 = new FileInputStream(encfileNew);
//        OutputStream os1 = new FileOutputStream(decfile);
//        EncFileUtils.decrypt("qqqq", is1, os1); //解密
    }


}
