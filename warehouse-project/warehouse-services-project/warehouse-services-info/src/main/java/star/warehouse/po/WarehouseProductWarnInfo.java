package star.warehouse.po;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 仓库里商品存放多少警戒信息表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class WarehouseProductWarnInfo extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:warehouse_product_warn_info.id
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
	 * 字段：状态 ON在用，OFF停用,DEL删除
	 *
	 * @haoxz11MyBatis
	 */
	private String status;

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
	 * 字段：商品警戒数量1
	 *
	 * @haoxz11MyBatis
	 */
	private Integer productNumWarn1;

	/**
	 * 字段：商品警戒数量2
	 *
	 * @haoxz11MyBatis
	 */
	private Integer productNumWarn2;

	/**
	 * 字段：商品sku警戒数量1
	 *
	 * @haoxz11MyBatis
	 */
	private Integer productSkuNumWarn1;

	/**
	 * 字段：商品sku警戒数量2
	 *
	 * @haoxz11MyBatis
	 */
	private Integer productSkuNumWarn2;

	/**
	 * 字段：计量单位
	 *
	 * @haoxz11MyBatis
	 */
	private String unitName;

	/**
	 * 读取：warehouse_product_warn_info.id
	 *
	 * @return warehouse_product_warn_info.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：warehouse_product_warn_info.id
	 *
	 * @param id warehouse_product_warn_info.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return warehouse_product_warn_info.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId warehouse_product_warn_info.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：仓库ID
	 *
	 * @return warehouse_product_warn_info.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId warehouse_product_warn_info.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：状态 ON在用，OFF停用,DEL删除
	 *
	 * @return warehouse_product_warn_info.status
	 *
	 * @haoxz11MyBatis
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置：状态 ON在用，OFF停用,DEL删除
	 *
	 * @param status warehouse_product_warn_info.status
	 *
	 * @haoxz11MyBatis
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return warehouse_product_warn_info.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime warehouse_product_warn_info.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return warehouse_product_warn_info.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime warehouse_product_warn_info.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：商品ID
	 *
	 * @return warehouse_product_warn_info.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * 设置：商品ID
	 *
	 * @param productId warehouse_product_warn_info.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * 读取：商品skuID
	 *
	 * @return warehouse_product_warn_info.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductSkuId() {
		return productSkuId;
	}

	/**
	 * 设置：商品skuID
	 *
	 * @param productSkuId warehouse_product_warn_info.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	/**
	 * 读取：商品警戒数量1
	 *
	 * @return warehouse_product_warn_info.product_num_warn1
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getProductNumWarn1() {
		return productNumWarn1;
	}

	/**
	 * 设置：商品警戒数量1
	 *
	 * @param productNumWarn1 warehouse_product_warn_info.product_num_warn1
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductNumWarn1(Integer productNumWarn1) {
		this.productNumWarn1 = productNumWarn1;
	}

	/**
	 * 读取：商品警戒数量2
	 *
	 * @return warehouse_product_warn_info.product_num_warn2
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getProductNumWarn2() {
		return productNumWarn2;
	}

	/**
	 * 设置：商品警戒数量2
	 *
	 * @param productNumWarn2 warehouse_product_warn_info.product_num_warn2
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductNumWarn2(Integer productNumWarn2) {
		this.productNumWarn2 = productNumWarn2;
	}

	/**
	 * 读取：商品sku警戒数量1
	 *
	 * @return warehouse_product_warn_info.product_sku_num_warn1
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getProductSkuNumWarn1() {
		return productSkuNumWarn1;
	}

	/**
	 * 设置：商品sku警戒数量1
	 *
	 * @param productSkuNumWarn1 warehouse_product_warn_info.product_sku_num_warn1
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuNumWarn1(Integer productSkuNumWarn1) {
		this.productSkuNumWarn1 = productSkuNumWarn1;
	}

	/**
	 * 读取：商品sku警戒数量2
	 *
	 * @return warehouse_product_warn_info.product_sku_num_warn2
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getProductSkuNumWarn2() {
		return productSkuNumWarn2;
	}

	/**
	 * 设置：商品sku警戒数量2
	 *
	 * @param productSkuNumWarn2 warehouse_product_warn_info.product_sku_num_warn2
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuNumWarn2(Integer productSkuNumWarn2) {
		this.productSkuNumWarn2 = productSkuNumWarn2;
	}

	/**
	 * 读取：计量单位
	 *
	 * @return warehouse_product_warn_info.unit_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * 设置：计量单位
	 *
	 * @param unitName warehouse_product_warn_info.unit_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}