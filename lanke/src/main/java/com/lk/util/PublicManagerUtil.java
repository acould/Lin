package com.lk.util;

/**
 * 说明：公共常量类 
 * 创建人：李泽华 
 * 创建时间：2018-05-14
 */
public interface PublicManagerUtil {
	// 几种常用返回值状态标识
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String FALSE1 = "false2";
	public static final String FAIL = "fail";
	public static final String ERR = "err";
	public static final String ERROR = "error";
	public static final String ERROR1 = "error1";
	public static final String ERROR2 = "error2";
	public static final String ADD = "add";
	public static final String SAVE = "save";
	public static final String EDIT = "edit";
	public static final String OK = "ok";
	public static final String NO = "no";
	public static final String START = "start";
	public static final String END = "end";
	public static final String NULL = "null";
	public static final String EXIST = "exist";

	// 密码加密(初始密码123456)--PASSWORD
	public static final String PASSWORD = "123456";
	// 密码加密(初始密码123)--PASSWORD1
	public static final String PASSWORD1 = "123";
	// 最高管理员角色ID
	public static final String ADMINROLEID = "1";
	// 揽客运营者角色ID
	public static final String LKROLEID = "f0e1814564fe40ca8d6e4dcf30e5c524";
	// 网吧管理员角色ID
	public static final String INTERNETROLEID = "94b10e8f8b1c4ae39e13d1316813b1d4";
	// 网吧分店员角色ID
	public static final String BRANCHESROLEID = "deef6fb22460429da89a4739bd66da46";
	// 推广员角色ID
	public static final String PROMOTERROLEID = "2f753afa151d4d41b18a659854c263d4";
	// 代理商角色ID
	public static final String AGENTROLEID = "2dc52c42a0fc43deaee1b8c71bb15cee";
	// 买家网吧id
	public static final String INTENET_ID1 = "321dfm54lod846579985e1ss2135cvbn";

	/****************正式服务器的数据******************/
//	// 微信(我方)appid
//	public static final String APPID = "wx7d71780213f70683";
//    //APPSECRET(开发者密码)
//    public static final String APPSECRET = "575cb861312fccef78abc6c3423ace25";
//    //域名
//    public static final String URL1 = "http://lanke8.cc/";
//    public static final String URL2 = "https://lanke8.cc/";
//    public static final String domain_ip = "47.101.42.12";
//	  public static final String website = "lanke8.cc";

	/****************结束线******************/
	
	/****************测试服务器的数据******************/
	// 微信(我方)appid
	public static final String APPID = "wxc408e8c863c2a8d5";
	//APPSECRET(开发者密码)
	public static final String APPSECRET = "5bdd42b214280af248f152e6ccf7aa1b";
	//域名
//    public static final String URL1 = "http://gh.easy.echosite.cn/lanker/";
	public static final String URL1 = "http://wkbar.cc/";
	public static final String URL2 = "https://wkbar.cc/";
	public static final String domain_ip = "47.100.219.94";
	public static final String myq_local = "http://myq.cross.echosite.cn/lanker/";
	public static final String website = "wkbar.cc";
//	public static final String domain_ip = "192.168.2.153";
	/****************结束线******************/


	// 预览图文
	public static String wxmenuUrl = URL1 + "sendRecord/phonePreview.do?mm=";

	public static final String TOKEN = "jswechat";
	public static final String key = "4dWjZHzvfebEYtxHiJtHKKc3sPlSQ82G2nyEc05XsfE";

	// 微信返回错误码errcode
	public static final String ERRCODE1 = "40163";
	public static final String ERRCODE2 = "45009"; // 每日新增达到上线！
	//常用的几个url拼接

	// sys-intenet表中状态码(状态 0注册 2正式 3禁用 4流失 5订阅号)
	public static final Integer SYS_INTENET_STATE1 = 0;
	public static final Integer SYS_INTENET_STATE2 = 2;
	public static final Integer SYS_INTENET_STATE3 = 3;
	public static final Integer SYS_INTENET_STATE4 = 4;
	public static final Integer SYS_INTENET_STATE5 = 5;

	// internet_store表中状态码(1-已启用,2-已禁用(用),3-已禁用(管))
	public static final String INTERNET_STORE_STATE1 = "1";
	public static final String INTERNET_STORE_STATE2 = "2";
	public static final String INTERNET_STORE_STATE3 = "3";

	// internet_store_v表中状态码(门店状态,0-未绑定,1-已绑定,2-加V审核,3-加V未通过,4-改IP审核,5-改ip未通过)
	public static final String INTERNET_STORE_STATE_V0 = "0";
	public static final String INTERNET_STORE_STATE_V1 = "1";
	public static final String INTERNET_STORE_STATE_V2 = "2";
	public static final String INTERNET_STORE_STATE_V3 = "3";
	public static final String INTERNET_STORE_STATE_V4 = "4";
	public static final String INTERNET_STORE_STATE_V5 = "5";
	
	//手机号的正则（注意最新版本）
	public static final String PHONE_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
	//身份证正则
	public static final String CARD_REG = "^([1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
	
	// FILE(文件类型)
	public static final String BASE64_JPEG = "data:image/jpeg;base64,";
	public static final String BASE64_PNG = "data:image/png;base64,";
	// SUFFIXNAME(图片尾缀)
	public static final String SUFFIXNAME_JPG = ".jpg";
	public static final String SUFFIXNAME_PNG = ".png";
	//URL(上传下载)
	public static final String URL = URL1+"uploadFiles/uploadImgs/";
	
	//在线支付开通的五种类型
	public static final String[] typeList = new String[]{"business","legal_front","legal_side","open_bank","internet_culture"};
	
	//代理商的三种图片类型
	public static final String[] typeAgentList = new String[]{"agent_business","legal_front","legal_side"};
	
	public static final String[] member_label = new String[]{"consume","balance","live"};//消费能力，余额系数，活跃
	public static final String[] label = new String[]{"high","mid","low"};
	public static final String[] label_info = new String[]{"高","中","低"};
	
	public static final String domain = "https://";


	public static final String client_prefix = "lk_client_";
}
