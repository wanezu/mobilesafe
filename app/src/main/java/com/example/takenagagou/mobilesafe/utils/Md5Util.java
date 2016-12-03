package com.example.takenagagou.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by takenagagou on 2016/12/3.
 */

public class Md5Util {

    /**
     * 加密
     * @param args
     */
    public static String main(String args){
        encoder(args);
        return args;
    }

    /**
     * 需要加密的密码
     * @param psd
     */
    private static void encoder(String psd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bs = digest.digest(psd.getBytes());
            System.out.println(bs.length);
            StringBuffer stringBuffer = new StringBuffer();
            //循环遍历
            for (byte b : bs){
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2){
                    hexString = "0"+hexString;
                }
                stringBuffer.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
