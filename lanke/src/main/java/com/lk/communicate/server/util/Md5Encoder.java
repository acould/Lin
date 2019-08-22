package com.lk.communicate.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encoder {

    private static final String DEFAULT_ENCODE = "UTF-8";

    private static final String DIGEST_TYPE = "MD5";

    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    public static String encode(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance(DIGEST_TYPE);
            md.update(value.getBytes(DEFAULT_ENCODE));
            // MD5 的计算结果是一个 128 位的长整数
            return toHexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("resource")
    public static byte[] encodeByte(File file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            MessageDigest md = MessageDigest.getInstance(DIGEST_TYPE);
            int numRead = 0;
            while ((numRead = in.read(buffer)) != -1)
                md.update(buffer, 0, numRead);
            in.close();
            // MD5 的计算结果是一个 128 位的长整数
            return md.digest();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(File file) {
        return toHexString(encodeByte(file));
    }

    public static String encodeBase64(File file) {
        return new String(Base64.encodeBase64(encodeByte(file)));
    }

    public static String encode(byte[] b) {
        return toHexString(encodeByte(b));
    }

    public static byte[] encodeByte(byte[] b) {
        try {
            MessageDigest md = MessageDigest.getInstance(DIGEST_TYPE);
            md.update(b, 0, b.length);
            // MD5 的计算结果是一个 128 位的长整数
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
    }

    /**
     * 换成 16 进制表示的字符
     * 
     * @param b
     * @return
     */
    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_CHAR[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_CHAR[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}
