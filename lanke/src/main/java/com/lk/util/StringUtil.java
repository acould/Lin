package com.lk.util;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	
	/**获取字符串编码
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   } 
	
	/**
	 * 获取唯一识别码（32位）
	 * @return
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}
	
	/**
	 * 判断不为null或""或长度不为0
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj){
		return null == obj ? false : 
			(obj instanceof CharSequence && ((CharSequence) obj).length() == 0) ? false :			//字符系列
				(obj instanceof Collection && ((Collection) obj).isEmpty()) ? false : 				//集合
					(obj instanceof Map && ((Map) obj).size() == 0) ? false	:						//map
						(obj instanceof Object[] && ((Object[]) obj).length == 0) ? false : true;	//数组
	}
	public static boolean isEmpty(Object obj){
		return !isNotEmpty(obj);
	}
	/**
	 * 判断是否是中文字符或者中文符号
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
			|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
			|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
			|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
			|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
			|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
			|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
			return false;
	 }
	
	
	
}
