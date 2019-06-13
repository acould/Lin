package star.webtool.struct;

/**
 * 
 * 
 * 
 * Title: cookie的枚举类
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午9:52:01
 * @version $Id: CookieEnum.java 88226 2015-06-01 02:06:41Z zhjy $
 */
public enum CookieEnum {
	// 验证码
	CODE("code"),
	// 用户账号
	LOGIN("l"),
	// 菜单
	MENU("m"),

	;

	private String value;

	CookieEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
