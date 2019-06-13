package star.facade.warehouse.vo;

import java.util.Date;

import star.util.validate.ValidationParam;
import star.util.validate.ValidationType;
import star.vo.BaseVo;

/**
 * 
 * 验货确认单表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class StoreInCheckVo extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:store_in_check.id
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：商家ID
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="商家ID不能为空")
	private Long merchantId;

	/**
	 * 字段：状态 ON在用，OFF停用,DEL删除
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="状态不能为空")
	private String status;

	/**
	 * 字段：状态init创建单子；toCheck提交审核,checkPass审核通过
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="状态不能为空")
	private String checkStatus;

	/**
	 * 字段：仓库ID
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="仓库ID不能为空")
	private Long warehouseId;

	/**
	 * 字段：入库单ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long storeInId;

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
	@ValidationParam(type=ValidationType.NotBlank,msg="商品ID不能为空")
	private Long productId;

	/**
	 * 字段：商品skuID
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="商品skuID不能为空")
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
	 * 字段：实际数量
	 *
	 * @haoxz11MyBatis
	 */
	private Long numReal;

	/**
	 * 字段：应到数量
	 *
	 * @haoxz11MyBatis
	 */
	private Long numNeed;

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
	 * 读取：store_in_check.id
	 *
	 * @return store_in_check.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：store_in_check.id
	 *
	 * @param id store_in_check.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return store_in_check.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId store_in_check.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：状态 ON在用，OFF停用,DEL删除
	 *
	 * @return store_in_check.status
	 *
	 * @haoxz11MyBatis
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置：状态 ON在用，OFF停用,DEL删除
	 *
	 * @param status store_in_check.status
	 *
	 * @haoxz11MyBatis
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取：状态init创建单子；toCheck提交审核,checkPass审核通过
	 *
	 * @return store_in_check.check_status
	 *
	 * @haoxz11MyBatis
	 */
	public String getCheckStatus() {
		return checkStatus;
	}

	/**
	 * 设置：状态init创建单子；toCheck提交审核,checkPass审核通过
	 *
	 * @param checkStatus store_in_check.check_status
	 *
	 * @haoxz11MyBatis
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	/**
	 * 读取：仓库ID
	 *
	 * @return store_in_check.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId store_in_check.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：入库单ID
	 *
	 * @return store_in_check.store_in_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getStoreInId() {
		return storeInId;
	}

	/**
	 * 设置：入库单ID
	 *
	 * @param storeInId store_in_check.store_in_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setStoreInId(Long storeInId) {
		this.storeInId = storeInId;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return store_in_check.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime store_in_check.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return store_in_check.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime store_in_check.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：商品ID
	 *
	 * @return store_in_check.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * 设置：商品ID
	 *
	 * @param productId store_in_check.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * 读取：商品skuID
	 *
	 * @return store_in_check.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductSkuId() {
		return productSkuId;
	}

	/**
	 * 设置：商品skuID
	 *
	 * @param productSkuId store_in_check.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	/**
	 * 读取：商品名称
	 *
	 * @return store_in_check.product_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 设置：商品名称
	 *
	 * @param productName store_in_check.product_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 读取：规格名称
	 *
	 * @return store_in_check.product_sku_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getProductSkuName() {
		return productSkuName;
	}

	/**
	 * 设置：规格名称
	 *
	 * @param productSkuName store_in_check.product_sku_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuName(String productSkuName) {
		this.productSkuName = productSkuName;
	}

	/**
	 * 读取：实际数量
	 *
	 * @return store_in_check.num_real
	 *
	 * @haoxz11MyBatis
	 */
	public Long getNumReal() {
		return numReal;
	}

	/**
	 * 设置：实际数量
	 *
	 * @param numReal store_in_check.num_real
	 *
	 * @haoxz11MyBatis
	 */
	public void setNumReal(Long numReal) {
		this.numReal = numReal;
	}

	/**
	 * 读取：应到数量
	 *
	 * @return store_in_check.num_need
	 *
	 * @haoxz11MyBatis
	 */
	public Long getNumNeed() {
		return numNeed;
	}

	/**
	 * 设置：应到数量
	 *
	 * @param numNeed store_in_check.num_need
	 *
	 * @haoxz11MyBatis
	 */
	public void setNumNeed(Long numNeed) {
		this.numNeed = numNeed;
	}

	/**
	 * 读取：计量单位
	 *
	 * @return store_in_check.unit_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * 设置：计量单位
	 *
	 * @param unitName store_in_check.unit_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 读取：备注
	 *
	 * @return store_in_check.remark
	 *
	 * @haoxz11MyBatis
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：备注
	 *
	 * @param remark store_in_check.remark
	 *
	 * @haoxz11MyBatis
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}