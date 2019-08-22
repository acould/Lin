package com.lk.entity.weixin;
/**
 * 微信--个性化菜单--匹配规则
 * @author myq
 * @time 2017-9-29
 */
public class ButtonRule {
	//至少要有一个匹配规则
	private String tag_id;
	private String sex;						//性别：1男；2女，不填不做匹配
	private String country;
	private String province;
	private String city;
	private String client_platform_type;	//客户端版本：1.IOS;2.Android；3.Others，不填不做匹配
	private String language;				//语言（用户在微信中设置的语言）：中文zh_CN(zh_TW，zh_HK),英文en
	
	
	public String getTag_id() {
		return tag_id;
	}
	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getClient_platform_type() {
		return client_platform_type;
	}
	public void setClient_platform_type(String client_platform_type) {
		this.client_platform_type = client_platform_type;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String toString() {
		return "ButtonRule [tag_id=" + tag_id + ", sex=" + sex + ", country="
				+ country + ", province=" + province + ", city=" + city
				+ ", client_platform_type=" + client_platform_type
				+ ", language=" + language + "]";
	}
	
	
}
