package star.warehouse.po;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 验货单明细表或扫码明细log表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class StoreInCheckXyzcode extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:store_in_check_xyzcode.id
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
	 * 字段：仓库ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long warehouseId;

	/**
	 * 字段：入库验货单ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long storeInCheckId;

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
	 * 字段：商品skuID
	 *
	 * @haoxz11MyBatis
	 */
	private Long productSkuId;

	/**
	 * 字段：sku空间编码组合（一个商品sku，对应一组商品sku空间编码。）
	 *
	 * @haoxz11MyBatis
	 */
	private String pskuXyzcodes;

	/**
	 * 读取：store_in_check_xyzcode.id
	 *
	 * @return store_in_check_xyzcode.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：store_in_check_xyzcode.id
	 *
	 * @param id store_in_check_xyzcode.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return store_in_check_xyzcode.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId store_in_check_xyzcode.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：仓库ID
	 *
	 * @return store_in_check_xyzcode.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId store_in_check_xyzcode.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：入库验货单ID
	 *
	 * @return store_in_check_xyzcode.store_in_check_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getStoreInCheckId() {
		return storeInCheckId;
	}

	/**
	 * 设置：入库验货单ID
	 *
	 * @param storeInCheckId store_in_check_xyzcode.store_in_check_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setStoreInCheckId(Long storeInCheckId) {
		this.storeInCheckId = storeInCheckId;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return store_in_check_xyzcode.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime store_in_check_xyzcode.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return store_in_check_xyzcode.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime store_in_check_xyzcode.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：商品skuID
	 *
	 * @return store_in_check_xyzcode.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductSkuId() {
		return productSkuId;
	}

	/**
	 * 设置：商品skuID
	 *
	 * @param productSkuId store_in_check_xyzcode.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	/**
	 * 读取：sku空间编码组合（一个商品sku，对应一组商品sku空间编码。）
	 *
	 * @return store_in_check_xyzcode.psku_xyzcodes
	 *
	 * @haoxz11MyBatis
	 */
	public String getPskuXyzcodes() {
		return pskuXyzcodes;
	}

	/**
	 * 设置：sku空间编码组合（一个商品sku，对应一组商品sku空间编码。）
	 *
	 * @param pskuXyzcodes store_in_check_xyzcode.psku_xyzcodes
	 *
	 * @haoxz11MyBatis
	 */
	public void setPskuXyzcodes(String pskuXyzcodes) {
		this.pskuXyzcodes = pskuXyzcodes;
	}
}