package com.lk.util;

/**
 * 顺网接口，通用参数
 * @author myq
 *
 */
public interface MsgUtil {
	
	//产商标记
	public static final String COMPANY_TOKEN = "HZwjkj";
	//产商ID
	public static final int COMPANY_ID = 200010;
	//加密key
	public static final String ENCODE_KEY = "898730ED";
	//数据标记
	public static final int DATA_SIGN = 0xAB65AB65;
	
	
	//推送的地址
	public static final String URL = "http://118.31.166.98/intenetmumber/";
	
	//线程睡眠时长
	public static final long MIN_TIME = 500;
	public static final long BASE_TIME = 1100;
	public static final long MAX_TIME = 1700;
	
	//业务类型：登录
	public static final String MSG_CONNECT = "0x00008001";

	//业务类型：查询会员信息
	public static final String MSG_USERINFO = "0x00008003";
	public static final String BACK_USERINFO = "0x80008003";
	
	//业务类型：查询用户流水
	public static final String MSG_USERFLOW = "0x0000800D";
	public static final String BACK_USERFLOW = "0x8000800D";
	
	//业务类型：充值
	public static final String MSG_RECHARGE = "0x0000804A";
	public static final String BACK_RECHARGE = "0x8000804A";
	
	//业务类型：消费
	public static final String MSG_CONSUME = "0x0000804C";
	public static final String BACK_CONSUME = "0x8000804C";
	
	//业务类型：查询用户消费
	public static final String MSG_SEL_CONSUME = "0x00008008";
	public static final String BACK_SEL_CONSUME = "0x80008008";
	
	//业务类型：查询上机信息
	public static final String MSG_USERBOARD = "0x00008005";
	public static final String BACK_USERBOARD = "0x80008005";
	
	//业务类型：查询已下机信息
	public static final String MSG_USERDOWN = "0x00008009";
	public static final String BACK_USERDOWN = "0x80008009";
	
	//业务类型：添加会员
	public static final String ADD_MEMBER = "0x00008041";
	public static final String BACK_ADD_MEMBER = "0x80008041";


	//业务类型：请求上机
	public static final String USER_UP = "0x00008046";
	public static final String BACK_USER_UP = "0x80008046";
	//业务类型：请求下机
	public static final String USER_DOWN = "0x00008047";
	public static final String BACK_USER_DOWN = "0x80008047";
	//业务类型：请求换机
	public static final String USER_UP_CHANGE = "0x00008049";
	public static final String BACK_USER_UP_CHANGE = "0x80008049";


	//业务类型：设置扫码上机参数
	public static final String SET_QR_PARAM = "0x00008053";
	public static final String BACK_SET_QR_PARAM = "0x80008053";


	//网络唤醒（自己定义的接口）
	public static final String WAKE_POWER = "0x00009900";
	public static final String BACK_WAKE_POWER = "0x80009900";


	//顺网开机命令 OL6.3.22.0新增接口
	public static final String SW_WAKE_POWER = "0x00008071";
	public static final String BACK_SW_WAKE_POWER = "0x80008071";

	
}
