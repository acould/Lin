package com.lk.util;

/**
 * 顺网接口，通用返回码
 * @author myq
 *
 */
public interface ErrUtil {
	
	//系统繁忙
	public static final String ERR_FU1 = "{'errcode':-1,'errmsg':'The system is busy. Please try again later.','errinfo':'-1，系统繁忙，请稍后再试！'}";
	//正确返回
	public static final String ERR_0 = "{'errcode':0,'errmsg':'ok'}";
	
	//查询到的数据为空
	public static final String ERR_1 = "{'errcode':1,'errmsg':'not find msg'}";
		
	//通道连接超时
	public static final String ERR_2 = "{'errcode':2,'errmsg':'connect time out'}";
	//tcp请求失败
	public static final String ERR_3 = "{'errcode':3,'errmsg':'request failed'}";
	//调用除连接之外的其他接口时，判断该tcp是否存在
	public static final String ERR_5 = "{'errcode':5,'errmsg':'tcp connect is not exist'}";
	
	/**
	 * 无效的store_id，没有加v，或者tcp连接不存在
	 */
	public static final String ERR_10001 = "{'errcode':10001,'errmsg':'store_id is invalid'}";
	//门店id参数为空
	public static final String ERR_10002 = "{'errcode':10002,'errmsg':'store_id is null'}";
	//请求来源信息为空
	public static final String ERR_10003 = "{'errcode':10003,'errmsg':'msg_from is null'}";
	//pubwin_ip参数无效
	public static final String ERR_10004 = "{'errcode':10004,'errmsg':'pubwin_ip is invalid'}";
	//pubwin_store_id为空
	public static final String ERR_10005 = "{'errcode':10005,'errmsg':'pubwin_store_id is null'}";
	//修改ip时，检测到数据库中未有该门店审核记录
	public static final String ERR_10006 = "{'errcode':10006,'errmsg':'store is not exist'}";
	
	
	//请求ip无效
	public static final String ERR_10010 = "{'errcode':10010,'errmsg':'ip is invalid'}";
	//请求ip被拒绝
	public static final String ERR_10011 = "{'errcode':10011,'errmsg':'ip is refused'}";
	
	
	//传入的参数无效
	public static final String ERR_10030 = "{'errcode':10030,'errmsg':'amount is invalid'}";
	public static final String ERR_10031 = "{'errcode':10031,'errmsg':'allow_reward is invalid'}";
	public static final String ERR_10032 = "{'errcode':10032,'errmsg':'pay_from is null'}";
	public static final String ERR_10033 = "{'errcode':10033,'errmsg':'reward is invalid'}";
	//接口接收数据为空
	public static final String ERR_10037 = "{'errcode':10037,'errmsg':'result is null'}";
	
	//传入的参数无效
	public static final String ERR_10040 = "{'errcode':10040,'errmsg':'principal is invalid'}";
	
	//传入的参数为空
	public static final String ERR_10050 = "{'errcode':10050,'errmsg':'card_id is null'}";
	//传入的参数无效
	public static final String ERR_10051 = "{'errcode':10051,'errmsg':'filter_type is invalid'}";
	public static final String ERR_10052 = "{'errcode':10052,'errmsg':'begin_time is invalid'}";
	public static final String ERR_10053 = "{'errcode':10053,'errmsg':'end_time is invalid'}";
	
	
	//传入的参数无效
	public static final String ERR_10060 = "{'errcode':10060,'errmsg':'field_value is invalid'}";
	public static final String ERR_10061 = "{'errcode':10061,'errmsg':'field_type is invalid'}";
	
	//传入的参数无效
	public static final String ERR_10070 = "{'errcode':10070,'errmsg':'user_name is invalid'}";
	public static final String ERR_10071 = "{'errcode':10071,'errmsg':'user_password is invalid'}";
	public static final String ERR_10072 = "{'errcode':10072,'errmsg':'id_card is null'}";
	public static final String ERR_10073 = "{'errcode':10073,'errmsg':'id_type is invalid'}";
	public static final String ERR_10074 = "{'errcode':10074,'errmsg':'phone_number is invalid'}";
	public static final String ERR_10075 = "{'errcode':10075,'errmsg':'member_level is invalid'}";
	public static final String ERR_10076 = "{'errcode':10076,'errmsg':'confirm_type is invalid'}";
	
}
