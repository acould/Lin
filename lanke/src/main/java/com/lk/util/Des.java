package com.lk.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Des {
    public static String SMS_DES_KEY = "_vDXOOE#";
    private static String FINANCE_DES_KEY = "%DRO*2Fm";

    public static String encryptFinance(int id){
        return encryptNoException(String.valueOf(id), FINANCE_DES_KEY, true);
    }
    public static int decryptFinance(String id){
        if(isNumeric(id)) {
            return Integer.parseInt(id);
        }
        try {
            return Integer.parseInt(decryptNoException(id, FINANCE_DES_KEY, false));//项目ID解密时，不需要decode
        } catch (Exception e) {
            return -1;
        }
    }

    //加密
    public static String encryptNoException(String data,String key) {
        return encryptNoException(data, key, true);
    }
    public static String encryptNoException(String data,String key, boolean needEncode) {
        try {
            byte[] bt = encrypt(data.getBytes(),key.getBytes());
            String strs = new BASE64Encoder().encode(bt);
            if(needEncode) {
                return URLEncoder.encode(strs, "utf8");
            } else {
                return strs;
            }
        } catch (Exception e){
            return data;
        }
    }
    //解密
    public static String decryptNoException(String data,String key) {
        return decryptNoException(data, key, true);
    }
    public static String decryptNoException(String data,String key, boolean needDecode) {
        try {
            if(data==null)
                return null;
            if(needDecode) {
                data = URLDecoder.decode(data, "utf8");
            }
            BASE64Decoder decorder = new BASE64Decoder();
            byte[] buf = decorder.decodeBuffer(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt);
        } catch (Exception e){
            return data;
        }
    }
    //加密
    public static String encrypt (String data,String key)
            throws Exception
    {
        byte[] bt=encrypt(data.getBytes(), key.getBytes());
        String strs=new BASE64Encoder().encode(bt);
        return strs;
    }
    //解密
    public static String decrypt(String data,String key)
            throws Exception
    {
        if(data==null)
            return null;
        BASE64Decoder decorder=new BASE64Decoder();
        byte[] buf=decorder.decodeBuffer(data);
        byte[] bt=decrypt(buf, key.getBytes());
        return new String(bt);
    }

    //加密方法
    public static byte[] encrypt(byte[] data,byte[] key)
            throws Exception
    {
        DESKeySpec dks=new DESKeySpec(key);
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
        SecretKey secretkey=keyFactory.generateSecret(dks);
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.ENCRYPT_MODE, secretkey,iv);
        return cipher.doFinal(data);
    }


    //解密方法
    public static byte[] decrypt(byte[] data,byte[] key)
            throws Exception
    {
        DESKeySpec dks=new DESKeySpec(key);
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
        SecretKey secretkey=keyFactory.generateSecret(dks);
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.DECRYPT_MODE, secretkey,iv);
        return cipher.doFinal(data);
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.printf(String.valueOf(Des.decryptFinance("q%2BNvwKQw9P8%3D")));
    }
}
