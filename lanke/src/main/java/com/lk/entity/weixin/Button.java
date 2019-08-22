package com.lk.entity.weixin;

import java.util.List;

/**
 * 微信--菜单实体类
 * @author myq
 * @time 2017-9-29
 */
public class Button {
	
	private String name;				//最多16个字节 = 8个汉字
	private String type;
	private String url;					//type = view/miniprogram
	private String key;					//type = click等点击类型必须
	private String media_id;			//type = media_id/view_limited
	private String appId;				//小程序的appid，type = miniprogram
	private String pagePath;			//小程序的页面路径
	private List<SUB_BUTTON> sub_button;//二级菜单1-5个
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SUB_BUTTON> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<SUB_BUTTON> sub_button) {
		this.sub_button = sub_button;
	}
	
	public String toString() {
		return "Button [name=" + name + ", type=" + type + ", url=" + url
				+ ", key=" + key + ", media_id=" + media_id + ", sub_button="
				+ sub_button + "]";
	}
	
	//二级菜单
	public class SUB_BUTTON{
			
			private String name;			//最多60个字节 = 30个汉字
			private String type;
			private String url;
			private String key;
			private String media_id;
			private String appId;			
			private String pagePath;	
			
			public String getAppId() {
				return appId;
			}
			public void setAppId(String appId) {
				this.appId = appId;
			}
			public String getPagePath() {
				return pagePath;
			}
			public void setPagePath(String pagePath) {
				this.pagePath = pagePath;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
			public String getKey() {
				return key;
			}
			public void setKey(String key) {
				this.key = key;
			}
			public String getMedia_id() {
				return media_id;
			}
			public void setMedia_id(String media_id) {
				this.media_id = media_id;
			}
			
			public String toString() {
				return "SUB_BUTTON [name=" + name + ", type=" + type + ", url="
						+ url + ", key=" + key + ", media_id=" + media_id + "]";
			}
			
		}


}
