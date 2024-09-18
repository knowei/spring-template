package com.liwen.project.common.password;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.rmi.ServerException;

/**
 * @description: AES加密工具类
 * @author: 孤独的小饼干
 * @create:
 **/
public class AesUtil {


    /**
     * 加密
     *
     * @param sSrc 需要加密的字符串
     * @param sKey 此处使用AES-128-ECB加密模式，key需要为16位。
     * @return
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {

        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * 解密
     *
     * @param sSrc 需要解密的字符串
     * @param sKey 此处使用AES-128-ECB加密模式，key需要为16位。
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc, String sKey) throws ServerException {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;

        } catch (Exception ex) {
            throw new ServerException("Aes解密失败！");
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "2Ev7HV17x78xMwCW";
        // 需要加密的字串
        String cSrc = "给我用aes进行加密";
        System.out.println(cSrc);
        // 加密
        String enString = AesUtil.encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AesUtil.decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
    }
}

