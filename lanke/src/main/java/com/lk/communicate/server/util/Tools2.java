package com.lk.communicate.server.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.lk.util.StringUtil;

/**
 * 工具类
 * @author haven
 *
 */
public class Tools2 {

    private static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    /**
     * int转byte数组
     * @param a
     * @return
     */
    public static byte[] intToByteArray(int a) {
        return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF) };
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws InvalidAlgorithmParameterException 
     * @throws Exception 
     */
    public static String desEncoder(String key, String data) {
        if (data == null)
            return null;
        try {
            SecretKeySpec keys = new SecretKeySpec(key.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, keys, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 取后9位
     * @return
     */
    public static int getCenterMsgId2(){
 	   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
 	   String time = formatter.format(new Date());
 	   return Integer.parseInt(time.substring(time.length()-9));
    }
    
    
    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，获取日期字符串
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，获取日期字符串
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date str2Date(String date) {
        return str2Date(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    
    /**
     * 按照参数format的格式，日期转字符串
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }
    /**
     *  按照参数format的格式，字符串转日期
     * @param date
     * @return
     */
    public static Date str2Date(String date, String format) {
        if (StringUtil.isNotEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }
}
