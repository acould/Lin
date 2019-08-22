package com.lk.entity.unionPay;

import lombok.Data;

import java.util.Date;

/**
 * 订单表
 * @Title TBRecharge.java
 * @author 陈明阳
 * @date 2018年7月23日上午11:49:18
 */
@Data
public class TBRecharge {
	private String id;
	private String merOrderId;
	private String user_id;
	private String carded;
	private String user_name;
	private String storename;
	private String openid;
	private String store_id;
	private String store_name;
	private String internet_id;
	private String rincipal_balance;
	private String reward_balance;
	private String internetrule_id;
	private Date createtime;
	private String return_code;
	private String return_msg;
	private String pay_state;
	private String pay_actualbalance;
	private char pay_type;
	private Date pay_starttime;
	private Date pat_endtime;
	private Date request_time;
	private String business_number;
	private String memo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerOrderId() {
		return merOrderId;
	}

	public void setMerOrderId(String merOrderId) {
		this.merOrderId = merOrderId;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCarded() {
		return carded;
	}

	public void setCarded(String carded) {
		this.carded = carded;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getInternet_id() {
		return internet_id;
	}

	public void setInternet_id(String internet_id) {
		this.internet_id = internet_id;
	}

	public String getRincipal_balance() {
		return rincipal_balance;
	}

	public void setRincipal_balance(String rincipal_balance) {
		this.rincipal_balance = rincipal_balance;
	}

	public String getReward_balance() {
		return reward_balance;
	}

	public void setReward_balance(String reward_balance) {
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

	public String getPay_state() {
		return pay_state;
	}

	public String getBusiness_number() {
		return business_number;
	}

	public void setBusiness_number(String business_number) {
		this.business_number = business_number;
	}

	public void setPay_state(String pay_state) {
		this.pay_state = pay_state;
	}

	public String getPay_actualbalance() {
		return pay_actualbalance;
	}

	public void setPay_actualbalance(String pay_actualbalance) {
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

	public Date getRequest_time() {
		return request_time;
	}

	public void setRequest_time(Date request_time) {
		this.request_time = request_time;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

}
