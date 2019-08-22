package com.lk.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

public class CommonJudge {
	
	public static JSONObject judgePhone(String phone){
		JSONObject result = new JSONObject();
		if(phone == "18222956710"){
			result.put("result", "true");
			return result; 
		}
		
		if(StringUtil.isEmpty(phone)){
			result.put("result", "false");
			result.put("message", "请输入手机号");
			return result;
		}else{
			Pattern pattern = Pattern.compile(PublicManagerUtil.PHONE_REG);
			Matcher matcher = pattern.matcher(phone);
			if (!matcher.find()) {
				result.put("result", "false");
				result.put("message", "手机号格式错误");
				return result;
			}
		}
		
		result.put("result", "true");
		return result;
	}
	
	public static JSONObject judgeCard(String card){
		JSONObject result = new JSONObject();
		if(StringUtil.isEmpty(card)){
			result.put("result", "false");
			result.put("message", "请输入身份证");
			return result;
		}else{
			Pattern pattern = Pattern.compile(PublicManagerUtil.CARD_REG);
			Matcher matcher = pattern.matcher(card);
			if (!matcher.find()) {
				result.put("result", "false");
				result.put("message", "身份证格式错误");
				return result;
			}
		}
		result.put("result", "true");
		return result;
	}

}
