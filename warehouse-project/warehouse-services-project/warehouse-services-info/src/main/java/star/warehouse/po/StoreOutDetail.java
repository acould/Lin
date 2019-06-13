package star.warehouse.po;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 出库单明细表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class StoreOutDetail extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:store_out_detail.id
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：商家ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long merchantId;

	/**
	 * 字段：状态 ON在用，OFF停用,DEL删除
	 *
	 * @haoxz11MyBatis
	 */
	private String status;

	/**
	 * 字段：仓库ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long warehouseId;

	/**
	 * 字段：出库单ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long storeOutId;

	/**
	 * 字段：创建时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date createTime;

	/**
	 * 字段：修改时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date modifyTime;

	/**
	 * 字段：商品ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long productId;

	/**
	 * 字段：商品skuID
	 *
	 * @haoxz11MyBatis
	 */
	private Long productSkuId;

	/**
	 * 字段：商品名称
	 *
	 * @haoxz11MyBatis
	 */
	private String productName;

	/**
	 * 字段：规格名称
	 *
	 * @haoxz11MyBatis
	 */
	private String productSkuName;

	/**
	 * 字段：单价(以厘为单位)
	 *
	 * @haoxz11MyBatis
	 */
	private Long price;

	/**
	 * 字段：其他分摊成本(以厘为单位)
	 *
	 * @haoxz11MyBatis
	 */
	private Long otherCost;

	/**
	 * 字段：数量
	 *
	 * @haoxz11MyBatis
	 */
	private Long num;

	/**
	 * 字段：计量单位
	 *
	 * @haoxz11MyBatis
	 */
	private String unitName;

	/**
	 * 字段：备注
	 *
	 * @haoxz11MyBatis
	 */
	private String remark;

	/**
	 * 读取：store_out_detail.id
	 *
	 * @return store_out_detail.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：store_out_detail.id
	 *
	 * @param id store_out_detail.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return store_out_detail.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId store_out_detail.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：状态 ON在用，OFF停用,DEL删除
	 *
	 * @return store_out_detail.status
	 *
	 * @haoxz11MyBatis
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置：状态 ON在用，OFF停用,DEL删除
	 *
	 * @param status store_out_detail.status
	 *
	 * @haoxz11MyBatis
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取：仓库ID
	 *
	 * @return store_out_detail.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId store_out_detail.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：出库单ID
	 *
	 * @return store_out_detail.store_out_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getStoreOutId() {
		return storeOutId;
	}

	/**
	 * 设置：出库单ID
	 *
	 * @param storeOutId store_out_detail.store_out_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setStoreOutId(Long storeOutId) {
		this.storeOutId = storeOutId;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return store_out_detail.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime store_out_detail.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return store_out_detail.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime store_out_detail.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：商品ID
	 *
	 * @return store_out_detail.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * 设置：商品ID
	 *
	 * @param productId store_out_detail.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * 读取：商品skuID
	 *
	 * @return store_out_detail.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductSkuId() {
		return productSkuId;
	}

	/**
	 * 设置：商品skuID
	 *
	 * @param productSkuId store_out_detail.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	/**
	 * 读取：商品名称
	 *
	 * @return store_out_detail.product_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 设置：商品名称
	 *
	 * @param productName store_out_detail.product_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 读取：规格名称
	 *
	 * @return store_out_detail.product_sku_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getProductSkuName() {
		return productSkuName;
	}

	/**
	 * 设置：规格名称
	 *
	 * @param productSkuName store_out_detail.product_sku_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuName(String productSkuName) {
		this.productSkuName = productSkuName;
	}

	/**
	 * 读取：单价(以厘为单位)
	 *
	 * @return store_out_detail.price
	 *
	 * @haoxz11MyBatis
	 */
	public Long getPrice() {
		return price;
	}

	/**
	 * 设置：单价(以厘为单位)
	 *
	 * @param price store_out_detail.price
	 *
	 * @haoxz11MyBatis
	 */
	public void setPrice(Long price) {
		this.price = price;
	}

	/**
	 * 读取：其他分摊成本(以厘为单位)
	 *
	 * @return store_out_detail.other_cost
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOtherCost() {
		return otherCost;
	}

	/**
	 * 设置：其他分摊成本(以厘为单位)
	 *
	 * @param otherCost store_out_detail.other_cost
	 *
	 * @haoxz11MyBatis
	 */
	public void setOtherCost(Long otherCost) {
		this.otherCost = otherCost;
	}

	/**
	 * 读取：数量
	 *
	 * @return store_out_detail.num
	 *
	 * @haoxz11MyBatis
	 */
	public Long getNum() {
		return num;
	}

	/**
	 * 设置：数量
	 *
	 * @param num store_out_detail.num
	 *
	 * @haoxz11MyBatis
	 */
	public void setNum(Long num) {
		this.num = num;
	}

	/**
	 * 读取：计量单位
	 *
	 * @return store_out_detail.unit_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * 设置：计量单位
	 *
	 * @param unitName store_out_detail.unit_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 读取：备注
	 *
	 * @return store_out_detail.remark
	 *
	 * @haoxz11MyBatis
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：备注
	 *
	 * @param remark store_out_detail.remark
	 *
	 * @haoxz11MyBatis
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}