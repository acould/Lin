package com.lk.entity.unionPay;



/**
 * Created by faliny on 2017/8/25.
 */

public class SubOrders {

    public SubOrders(String mid,Long totalAmount) {
        this.mid = mid;
        this.totalAmount = totalAmount;
    }

    private String mid;     	//子商户号
    private Long totalAmount;   	//子商户分账金额
    
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
    
}
