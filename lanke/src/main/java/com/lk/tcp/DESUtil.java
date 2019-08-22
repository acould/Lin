package com.lk.tcp;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.lk.util.MsgUtil;

import sun.misc.BASE64Decoder;

/**
 * 加密解密工具包
 */
public class DESUtil {

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	   private static String sKey="12345678";
    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws InvalidAlgorithmParameterException 
     * @throws Exception 
     */
    public static String encode(String key,String data) {
    	if(data == null)
    		return null;
    	try{
	        SecretKeySpec keys = new SecretKeySpec(key.getBytes(), "DES");
	        Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
	        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
	        AlgorithmParameterSpec paramSpec = iv;
	        cipher.init(Cipher.ENCRYPT_MODE, keys,paramSpec);  
	        byte[] bytes = cipher.doFinal(data.getBytes());            
	        return  new String(Base64.encodeBase64(bytes), "UTF-8");
    	}catch(Exception e){
    		e.printStackTrace();
    		return data;
    	}
    }
    /** 
     * 使用默认密钥进行DES加密 
     */  
    public static byte[] encrypt(String plainText) {  
        try {  
            return new DES().encrypt(plainText);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String key,byte[]  data) {
    	if(data == null)
    		return null;
        try {
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(data));
        } catch (Exception e){
    		e.printStackTrace();
    		return data.toString();
        }
    }

	/**
	 * 二行制转字符串
	 * @param b
	 * @return
	 */
    private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b!=null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}
    
    public static byte[] hex2byte(byte[] b) {
        if((b.length%2)!=0)
            throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length/2];
		for (int n = 0; n < b.length; n+=2) {
		    String item = new String(b,n,2);
		    b2[n/2] = (byte)Integer.parseInt(item,16);
		}
        return b2;
    }
    public static byte[] des3EncodeCBC(byte[] key,  byte[] data)
    throws Exception {

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(key);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
    }

    public static void main(String[] args) throws Exception {
     // 需要加密的字串
    /*    String cSrc = "hello world";
        
        byte[]  ff= cSrc.getBytes();
        System.out.println("加密前的字串是："+ff.toString());
        // 加密
       String enString =DESUtil.encode(sKey, cSrc);
        System.out.println("加密后的字串是："+ enString);*/
        // 解密
//        String aa="AMoekheP6u72TpjpTmHAnI/C/0KhMLfGeYpCWyHrTurLjx6k6koqfP5AdASNVqDKf+kv95SGjxYPaOVy8liqNasbx+EJ0SQGtPP1I72SA2HCD8PrTDNTA6EiHAdzBcPraZpTpHnR7ZG794tkT7+bJk6riF04gImZZdGcmn0lKOuerF6VSqL0DHb8rEZJtwxnvH58Dg4bEiI=";
        String bb = "AMoekheP6u5+YHScgDTGVMnDn/9sps0aTfQjYGj6wFMBuPvbQcw1tFQTYvA8SV1ucO6G7JB/5oSydqqShqsCn69GVvcjtHnn9N6bQ2+4YLeWF2qejssz2/wU2PCHI8FhqiO1xDF5EUc1IGqP2Dmfa4VZv5Nd3wphTlOhNWDDxVaxT5GRdmOuwamuYIHlil/2lhFAvP6KQZSAYX9dsEXWboET2KvwptxR7dN06p7myw3lYvK+547q+HmFHGfzl/I4Ieu1cA1xuKzjhzioA8i044jE+eXuT4S43Ybxl+qRiCbSnpotjSbOpKKKE/ni4sHxj+aMphGFLF9Ax7ewMCYQxYUmtUpD70rNaQos8G7QronxPovQWV5GbrTRNBVZuA0lu+Rp5MX3nY7g2qhBmG2Co6PQoeYS9C0c6nI0dHthPJ5evk3tJsuA5kaDxkp1qFFNHPzbfKMmS9OFw4TovpLJPysqIzH4yivFbvD5lLq4pVnnUQAtu0sVRZKUhwUZJIh01X/8EIh5XPeh8nb4S7T0jhlMWZmVds1ko4U7epOitfbRSMctF2a6KVFwu2k272ejD98hG4GezhiyEe/TfwLM3qL8nJxV4RFdIesDvP2bgz2fM9lTqNWHN+Zw7niUtkYj1IRuCYlHwtXCC/WENq8n6//1LKhtDMZNgYSRg1ES0xHdc8uZAteJyMqpyOLexToVnHDpN8h1E7Xr8rXtlmGjykQ+bsUCjivkbX78CwQIXiLRO0TB7CCiS3kuyLv81plNb4Ysrws+KD7rZaDmbWUZxuUeFHYZFUPLKyvVAGcU6WYHCT+9VPqkYqADlXtQRpsWNlQo81uapDzOEkaZ+7eZhDU5B/h4S/OQ5mOTQossL//xmVvggsrIKRLl4BZfa1Mcck7+OcFoJWpPQq52cNJ31RzP57P9nu7Ugt+V3VR5oW7Xgwm5Euwld8NcyzFE9x4iookstYRqMbe7jgN7GkxKFRgVkNB+jdW";
       /* byte[]  ccc= DESUtil.encrypt(cSrc);
       System.out.println("加密后encrypt的字串是："+ new String(ccc, "UTF-8"));*/
        byte[] encrypted1 = new BASE64Decoder().decodeBuffer(bb);//先用base64解密
        String DeString = DESUtil.decode(MsgUtil.ENCODE_KEY, encrypted1);
        System.out.println("解密后的字串是：" + DeString);
    }
}