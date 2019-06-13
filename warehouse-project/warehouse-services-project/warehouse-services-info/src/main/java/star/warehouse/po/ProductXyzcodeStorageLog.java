package star.warehouse.po;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 商品出入库、变更等记录表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class ProductXyzcodeStorageLog extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:product_xyzcode_storage_log.id
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
	 * 字段：状态log
	 *
	 * @haoxz11MyBatis
	 */
	private String status;

	/**
	 * 字段：类型
	 *
	 * @haoxz11MyBatis
	 */
	private String moduleType;

	/**
	 * 字段：外键ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long outId;

	/**
	 * 字段：备注
	 *
	 * @haoxz11MyBatis
	 */
	private String remark;

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
	 * 读取：product_xyzcode_storage_log.id
	 *
	 * @return product_xyzcode_storage_log.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：product_xyzcode_storage_log.id
	 *
	 * @param id product_xyzcode_storage_log.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return product_xyzcode_storage_log.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId product_xyzcode_storage_log.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：商品ID
	 *
	 * @return product_xyzcode_storage_log.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * 设置：商品ID
	 *
	 * @param productId product_xyzcode_storage_log.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * 读取：商品skuID
	 *
	 * @return product_xyzcode_storage_log.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductSkuId() {
		return productSkuId;
	}

	/**
	 * 设置：商品skuID
	 *
	 * @param productSkuId product_xyzcode_storage_log.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	/**
	 * 读取：sku货品空间编码
	 *
	 * @return product_xyzcode_storage_log.product_sku_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public String getProductSkuXyzcode() {
		return productSkuXyzcode;
	}

	/**
	 * 设置：sku货品空间编码
	 *
	 * @param productSkuXyzcode product_xyzcode_storage_log.product_sku_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuXyzcode(String productSkuXyzcode) {
		this.productSkuXyzcode = productSkuXyzcode;
	}

	/**
	 * 读取：仓库ID
	 *
	 * @return product_xyzcode_storage_log.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId product_xyzcode_storage_log.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：仓库空间编码
	 *
	 * @return product_xyzcode_storage_log.warehouse_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public String getWarehouseXyzcode() {
		return warehouseXyzcode;
	}

	/**
	 * 设置：仓库空间编码
	 *
	 * @param warehouseXyzcode product_xyzcode_storage_log.warehouse_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseXyzcode(String warehouseXyzcode) {
		this.warehouseXyzcode = warehouseXyzcode;
	}

	/**
	 * 读取：存储区ID
	 *
	 * @return product_xyzcode_storage_log.storage_area_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getStorageAreaId() {
		return storageAreaId;
	}

	/**
	 * 设置：存储区ID
	 *
	 * @param storageAreaId product_xyzcode_storage_log.storage_area_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setStorageAreaId(Long storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	/**
	 * 读取：存储区空间编码
	 *
	 * @return product_xyzcode_storage_log.storage_area_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public String getStorageAreaXyzcode() {
		return storageAreaXyzcode;
	}

	/**
	 * 设置：存储区空间编码
	 *
	 * @param storageAreaXyzcode product_xyzcode_storage_log.storage_area_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public void setStorageAreaXyzcode(String storageAreaXyzcode) {
		this.storageAreaXyzcode = storageAreaXyzcode;
	}

	/**
	 * 读取：状态log
	 *
	 * @return product_xyzcode_storage_log.status
	 *
	 * @haoxz11MyBatis
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置：状态log
	 *
	 * @param status product_xyzcode_storage_log.status
	 *
	 * @haoxz11MyBatis
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取：类型
	 *
	 * @return product_xyzcode_storage_log.module_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getModuleType() {
		return moduleType;
	}

	/**
	 * 设置：类型
	 *
	 * @param moduleType product_xyzcode_storage_log.module_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	/**
	 * 读取：外键ID
	 *
	 * @return product_xyzcode_storage_log.out_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOutId() {
		return outId;
	}

	/**
	 * 设置：外键ID
	 *
	 * @param outId product_xyzcode_storage_log.out_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setOutId(Long outId) {
		this.outId = outId;
	}

	/**
	 * 读取：备注
	 *
	 * @return product_xyzcode_storage_log.remark
	 *
	 * @haoxz11MyBatis
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：备注
	 *
	 * @param remark product_xyzcode_storage_log.remark
	 *
	 * @haoxz11MyBatis
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return product_xyzcode_storage_log.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime product_xyzcode_storage_log.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return product_xyzcode_storage_log.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime product_xyzcode_storage_log.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}