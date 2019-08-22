package com.lk.entity.unionPay;

import java.util.Date;

/**
 * @Title 充值订单表
 * @author 陈明阳
 * @date 2018年7月16日下午5:20:46
 */
public class Recharge {
	private String id;     			//主键id
	private String order_number;	//订单编号
	private String user_id;			//用户id
	private String user_name;		//用户姓名
	private String store_id;		//门店id
	private String internet_id;		//网吧 主键id
	private float rincipal_balance;	//充值金额
	private float reward_balance;	//奖励金额
	private String internetrule_id;//规则id
	private Date createtime;		//创建时间
	private String return_code;		//返回状态码
	private String return_msg;		//返回信息
	private char pay_state;			//支付状态(0-创建订单,1-用户取消支付,2-支付失败,4-支付失败,5-发起支付,6-支付中(a-顺网支付失败,b-顺网支付无消息,c-顺网支付成功))
	private float pay_actualbalance;//实际支付金额
	private char pay_type;			//支付类型(1.微信支付)
	private Date pay_starttime;		//支付时间
	private Date pat_endtime;		//支付结束时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getInternet_id() {
		return internet_id;
	}
	public void setInternet_id(String internet_id) {
		this.internet_id = internet_id;
	}
	public float getRincipal_balance() {
		return rincipal_balance;
	}
	public void setRincipal_balance(float rincipal_balance) {
		this.rincipal_balance = rincipal_balance;
	}
	public float getReward_balance() {
		return reward_balance;
	}
	public void setReward_balance(float reward_balance) {
		this.reward_balance = reward_balance;
	}
	public String getInternetrule_id() {
		return internetrule_id;
	}
	public void setInternetrule_id(String internetrule_id) {
		this.internetrule_id = internetrule_id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public char getPay_state() {
		return pay_state;
	}
	public void setPay_state(char pay_state) {
		this.pay_state = pay_state;
	}
	public float getPay_actualbalance() {
		return pay_actualbalance;
	}
	public void setPay_actualbalance(float pay_actualbalance) {
		this.pay_actualbalance = pay_actualbalance;
	}
	public char getPay_type() {
		return pay_type;
	}
	public void setPay_type(char pay_type) {
		this.pay_type = pay_type;
	}
	public Date getPay_starttime() {
		return pay_starttime;
	}
	public void setPay_starttime(Date pay_starttime) {
		this.pay_starttime = pay_starttime;
	}
	public Date getPat_endtime() {
		return pat_endtime;
	}
	public void setPat_endtime(Date pat_endtime) {
		this.pat_endtime = pat_endtime;
	}
	
}
