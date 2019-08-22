package com.lk.entity.unionPay;



/**
 * Created by faliny on 2017/8/25.
 */

public class Goods {

    public Goods(String goodsId, String goodsName, String quantity, String price, String goodsCategory, String body,String subMerchantId,Long subOrderAmount) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.quantity = quantity;
        this.price = price;
        this.goodsCategory = goodsCategory;
        this.body = body;
        this.subMerchantId = subMerchantId;
        this.subOrderAmount = subOrderAmount;
    }

    private String goodsId;     	//商品ID
    private String goodsName;   	//商品名称
    private String quantity;     		//商品数量
    private String price;        		//商品单价（分）
    private String goodsCategory;   //商品分类
    private String body;			//商品说明
    private String subMerchantId;	//子商户号
    private Long subOrderAmount;  //子商户商品总额
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getGoodsCategory() {
		return goodsCategory;
	}
	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSubMerchantId() {
		return subMerchantId;
	}
	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}
	public Long getSubOrderAmount() {
		return subOrderAmount;
	}
	public void setSubOrderAmount(Long subOrderAmount) {
		this.subOrderAmount = subOrderAmount;
	}
    
}
