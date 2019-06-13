package star.facade.warehouse.vo.dto;

import star.vo.BaseVo;

/**
 * 
 * 商品与仓库空间关联表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class ProductXyzcodeStorageDTO extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:product_xyzcode_storage.id
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
	 * 字段：sku货品空间编码
	 *
	 * @haoxz11MyBatis
	 */
	private String productSkuXyzcode;

	/**
	 * 字段：仓库ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long warehouseId;

	/**
	 * 字段：仓库空间编码
	 *
	 * @haoxz11MyBatis
	 */
	private String warehouseXyzcode;

	/**
	 * 字段：存储区ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long storageAreaId;

	/**
	 * 字段：存储区空间编码
	 *
	 * @haoxz11MyBatis
	 */
	private String storageAreaXyzcode;

	

	/**
	 * 读取：product_xyzcode_storage.id
	 *
	 * @return product_xyzcode_storage.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：product_xyzcode_storage.id
	 *
	 * @param id product_xyzcode_storage.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return product_xyzcode_storage.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId product_xyzcode_storage.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：商品ID
	 *
	 * @return product_xyzcode_storage.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * 设置：商品ID
	 *
	 * @param productId product_xyzcode_storage.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * 读取：商品skuID
	 *
	 * @return product_xyzcode_storage.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductSkuId() {
		return productSkuId;
	}

	/**
	 * 设置：商品skuID
	 *
	 * @param productSkuId product_xyzcode_storage.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	/**
	 * 读取：sku货品空间编码
	 *
	 * @return product_xyzcode_storage.product_sku_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public String getProductSkuXyzcode() {
		return productSkuXyzcode;
	}

	/**
	 * 设置：sku货品空间编码
	 *
	 * @param productSkuXyzcode product_xyzcode_storage.product_sku_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuXyzcode(String productSkuXyzcode) {
		this.productSkuXyzcode = productSkuXyzcode;
	}

	/**
	 * 读取：仓库ID
	 *
	 * @return product_xyzcode_storage.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId product_xyzcode_storage.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：仓库空间编码
	 *
	 * @return product_xyzcode_storage.warehouse_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public String getWarehouseXyzcode() {
		return warehouseXyzcode;
	}

	/**
	 * 设置：仓库空间编码
	 *
	 * @param warehouseXyzcode product_xyzcode_storage.warehouse_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseXyzcode(String warehouseXyzcode) {
		this.warehouseXyzcode = warehouseXyzcode;
	}

	/**
	 * 读取：存储区ID
	 *
	 * @return product_xyzcode_storage.storage_area_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getStorageAreaId() {
		return storageAreaId;
	}

	/**
	 * 设置：存储区ID
	 *
	 * @param storageAreaId product_xyzcode_storage.storage_area_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setStorageAreaId(Long storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	/**
	 * 读取：存储区空间编码
	 *
	 * @return product_xyzcode_storage.storage_area_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public String getStorageAreaXyzcode() {
		return storageAreaXyzcode;
	}

	/**
	 * 设置：存储区空间编码
	 *
	 * @param storageAreaXyzcode product_xyzcode_storage.storage_area_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public void setStorageAreaXyzcode(String storageAreaXyzcode) {
		this.storageAreaXyzcode = storageAreaXyzcode;
	}

	
}