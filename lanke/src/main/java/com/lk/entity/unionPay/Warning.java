package com.lk.entity.unionPay;

import java.util.Date;

public class Warning {

	private String id;
	private Date createtime;
	private String falg;
	private String store_id;
	private Integer percentage;
	private Integer count;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getFalg() {
		return falg;
	}
	public void setFalg(String falg) {
		this.falg = falg;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Warning [id=" + id + ", createtime=" + createtime + ", falg="
				+ falg + ", store_id=" + store_id + ", percentage="
				+ percentage + ", count=" + count + "]";
	}
	
}
