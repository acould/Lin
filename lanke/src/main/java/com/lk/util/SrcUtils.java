package com.lk.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @Description: 截取img的src
 * @Project：test
 * @Author : hongzp
 * @Date ： 2014年4月20日 下午4:17:15
 * @version 1.0
 */
public class SrcUtils {
	public static  List<String>  ImgSrc(String url){
	    Pattern p_image;   
        Matcher m_image;  
        String img="";   
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>"; 
	    List<String> pics = new ArrayList<String>();
	    p_image = Pattern.compile (regEx_img,Pattern.CASE_INSENSITIVE);   
	    m_image = p_image.matcher(url); 
	    while(m_image.find()){   
	        img = img + "," + m_image.group();   
	        Matcher m  = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
	        while(m.find()){ 
	            pics.add(m.group(1));
	        }
	    }   
	    if(pics.size()>0){
	    	return pics;
	    }else{
	    	return pics;
	    }
			
		}
	
	
	
	public static  List<String>  ImgVedio(String url){
	    Pattern p_image;   
        Matcher m_image;  
        String img="";   
		String regEx_img = "<video.*src\\s*=\\s*(.*?)[^>]*?>"; 
	    List<String> pics = new ArrayList<String>();
	    p_image = Pattern.compile (regEx_img,Pattern.CASE_INSENSITIVE);   
	    m_image = p_image.matcher(url); 
	    while(m_image.find()){   
	        img = img + "," + m_image.group();   
	        Matcher m  = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
	        while(m.find()){
	            pics.add(m.group(1));
	        }
	    }   
	    if(pics.size()>0){
	    	return pics;
	    }else{
	    	return pics;
	    }
			
		}
}
