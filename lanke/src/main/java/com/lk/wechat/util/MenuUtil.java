package com.lk.wechat.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.entity.weixin.Button;
import com.lk.entity.weixin.Button.SUB_BUTTON;
import com.lk.entity.weixin.ButtonRule;
import com.lk.util.StringUtil;


/**
 * 微信--菜单接口
 * @author myq
 * @time 2017-9-28
 *
 */
public class MenuUtil {

	/**
	 * 创建菜单
	 * @param token
	 * @param list
	 * @return
	 */
	public static JSONObject createMenu(String token,List<Button> list,ButtonRule rule){
		String URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		URL = URL.replace("ACCESS_TOKEN", token);
		
		JSONObject json = new JSONObject();
			JSONArray menuList = new JSONArray();
			//一级菜单
			for(int i=0;i<list.size();i++){
				JSONObject firstButton = new JSONObject();
					//二级菜单
					List<SUB_BUTTON> subList = new ArrayList<SUB_BUTTON>();
					
					if(StringUtil.isNotEmpty(list.get(i).getSub_button())){
						subList = list.get(i).getSub_button();
						JSONArray firstButtonList = new JSONArray();
						for(int j=0;j<subList.size();j++){
							JSONObject sub_button = new JSONObject();
							sub_button.put("type", subList.get(j).getType());
							sub_button.put("name", subList.get(j).getName());
							if(StringUtil.isNotEmpty(subList.get(j).getUrl())){
								sub_button.put("url", subList.get(j).getUrl());
								//小程序
								if(subList.get(j).getType().equals("miniprogram")){
									sub_button.put("appid", subList.get(j).getAppId());
									sub_button.put("pagepath", subList.get(j).getPagePath());
								}
							}
							if(StringUtil.isNotEmpty(subList.get(j).getKey())){
								sub_button.put("key", subList.get(j).getKey());
							}
							if(StringUtil.isNotEmpty(subList.get(j).getMedia_id())){
								sub_button.put("media_id", subList.get(j).getMedia_id());
							}
							firstButtonList.add(sub_button);
						}
						firstButton.put("name", list.get(i).getName());
						firstButton.put("sub_button", firstButtonList);
					}else {
						firstButton.put("type", list.get(i).getType());
						firstButton.put("name", list.get(i).getName());
						if(StringUtil.isNotEmpty(list.get(i).getUrl())){
							firstButton.put("url", list.get(i).getUrl());
							//小程序
							if(list.get(i).getType().equals("miniprogram")){
								firstButton.put("appid", list.get(i).getAppId());
								firstButton.put("pagepath", list.get(i).getPagePath());
							}
						}
						if(StringUtil.isNotEmpty(list.get(i).getKey())){
							firstButton.put("key", list.get(i).getKey());
						}
						if(StringUtil.isNotEmpty(list.get(i).getMedia_id())){
							firstButton.put("media_id", list.get(i).getMedia_id());
						}
					}
				menuList.add(firstButton);
			}
			//个性化菜单配置
			JSONObject ruleJSON = new JSONObject();
			if(StringUtil.isNotEmpty(rule)){
				ruleJSON.put("tag_id", rule.getTag_id());
				ruleJSON.put("sex", rule.getSex());
				ruleJSON.put("country", rule.getCountry());
				ruleJSON.put("province", rule.getProvince());
				ruleJSON.put("city", rule.getCity());
				ruleJSON.put("client_platform_type", rule.getClient_platform_type());
				ruleJSON.put("language", rule.getLanguage());
				json.put("matchrule", ruleJSON);
			}
			
		json.put("button", menuList);
		System.out.println("++++++"+json.toString());
		JSONObject result = HttpUtil.wechatRequest(URL, "POST", json, "创建菜单");
		return result;
	}
	
	/**
	 * 删除菜单
	 * @param token
	 */
	public static JSONObject deleteMenu(String token){
		String URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
		URL = URL.replace("ACCESS_TOKEN", token);
		
		JSONObject result = HttpUtil.wechatRequest(URL, "GET", null, "删除菜单");
		return result;
	}

	/**
	 * 查询自定义菜单
	 * @param token
	 * @return
	 */
	public static JSONObject getMenu(String token){
		String URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
		URL = URL.replace("ACCESS_TOKEN", token);
		
		JSONObject result = HttpUtil.wechatRequest(URL, "GET", null, "查询菜单");
		return result;
	}
	
	
	
	
}
