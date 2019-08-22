package com.lk.util.unionPay;

import java.util.ResourceBundle;

import com.lk.util.PublicManagerUtil;
/**
 * 支付常量值
 * @Title PayConfig.java
 * @author 陈明阳
 * @date 2018年7月24日下午3:31:13
 */
public class PayConfig {
    private static ResourceBundle emayConfig = ResourceBundle.getBundle("publicpayParams");
    public static String APIurl = emayConfig.getString("url"); // 银商平台接口地址
    public static String mid = emayConfig.getString("mid");// 商户号
    public static String tid = emayConfig.getString("tid"); // 终端号
    public static String instMid = emayConfig.getString("instMid"); // 业务类型
    public static String msgSrc = emayConfig.getString("msgSrc");// 来源系统
    public static String md5Key = emayConfig.getString("key"); // 通讯秘钥
    public static String msgType_refund = emayConfig.getString("msgType_refund"); // 消息类型:订单退款
    public static String msgType_secureCancel = emayConfig.getString("msgType_secureCancel");// 消息类型:订单担保撤销
    public static String msgType_secureComplete = emayConfig.getString("msgType_secureComplete"); // 消息类型:订单担保撤销
    public static String msgType_close = emayConfig.getString("msgType_close"); // 消息类型:订单担保撤销
    public static String msgType_query = emayConfig.getString("msgType_query");// 消息类型:订单查询
	final public static String msgType = "WXPay.jsPay"; //消息类型
	final public static String msgSrcId = "4432";  //来源编号
	//final public static String apiUrl_makeOrder = "https://qr-test2.chinaums.com/netpay-portal/webpay/pay.do"; //测试环境接口地址
	final public static String apiUrl_makeOrder = "https://qr.chinaums.com/netpay-portal/webpay/pay.do";//	生产环境接口地址
	final public static String notifyUrl = PublicManagerUtil.URL2 +"indexMember/notifyUrl.do"; //测试服务地址 （异步通知）
	final public static String returnUrl = PublicManagerUtil.URL2 + "indexMember/returnUrl.do"; //测试服务地址 （同步回调）
	   
	 
//  @Value("${url}")
//  private String APIurl;
//  @Value("${mid}")
//  private String mid;
//  @Value("${tid}")
//  private String tid;
//  @Value("${instMid}")
//  private String instMid;
//  @Value("${msgSrc}")
//  private String msgSrc;
//  @Value("${key}")
//  private String md5Key;
//
//  @Value("${msgType_refund}")
//  private String msgType_refund;
//
//  @Value("${msgType_secureCancel}")
//  private String msgType_secureCancel;
//
//  @Value("${msgType_secureComplete}")
//  private String msgType_secureComplete;
//
//  @Value("${msgType_close}")
//  private String msgType_close;
//
//  @Value("${msgType_query}")
//  private String msgType_query;
//
//  final private static String msgType = "WXPay.jsPay";
//  final private static String msgSrcId = "3194";
//  final private static String apiUrl_makeOrder = "https://qr-test2.chinaums.com/netpay-portal/webpay/pay.do";
//  final private static String notifyUrl = "https://wkbar.cc/indexMember/notifyUrl.do";
//  final private static String returnUrl = "https://wkbar.cc/indexMember/returnUrl.do";
  //本地
  //final private static String notifyUrl = "http://192.168.2.194/lanker/indexMember/notifyUrl.do";
  //final private static String returnUrl = "http://192.168.2.194/lanker/indexMember/returnUrl.do";
}
