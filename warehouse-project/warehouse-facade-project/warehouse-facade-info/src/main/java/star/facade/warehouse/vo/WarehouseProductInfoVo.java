package star.facade.warehouse.vo;

import java.util.Date;

import star.util.validate.ValidationParam;
import star.util.validate.ValidationType;
import star.vo.BaseVo;

/**
 * 
 * 仓库产品数据信息表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class WarehouseProductInfoVo extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:warehouse_product_info.id
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
	 * 字段：仓库ID
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="仓库ID不能为空")
	private Long warehouseId;

	/**
	 * 字段：状态 ON在用，OFF停用,DEL删除
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="状态不能为空")
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
	@ValidationParam(type=ValidationType.NotBlank,msg="商品ID不能为空")
	private Long productId;

	/**
	 * 字段：商品skuID
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="商品sku不能为空")
	private Long productSkuId;

	/**
	 * 字段：规格名称
	 *
	 * @haoxz11MyBatis
	 */
	private String productSkuName;

	/**
	 * 字段：进货价(以厘为单位)--采购
	 *
	 * @haoxz11MyBatis
	 */
	private Long priceBuy;

	/**
	 * 字段：成本价(以厘为单位)--生产
	 *
	 * @haoxz11MyBatis
	 */
	private Long priceCost;

	/**
	 * 字段：单价（执行价格）(以厘为单位)
	 *
	 * @haoxz11MyBatis
	 */
	private Long priceUnit;

	/**
	 * 字段：实际库存总数量=已售占用+未售实际；库存数量可以负数（预售）
	 *
	 * @haoxz11MyBatis
	 */
	private Long numTotal;

	/**
	 * 字段：已售占用库存数量
	 *
	 * @haoxz11MyBatis
	 */
	private Long numSaleOccupy;

	/**
	 * 字段：实际库存未售数量
	 *
	 * @haoxz11MyBatis
	 */
	private Long numReal;

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
	 * 读取：warehouse_product_info.id
	 *
	 * @return warehouse_product_info.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：warehouse_product_info.id
	 *
	 * @param id warehouse_product_info.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return warehouse_product_info.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId warehouse_product_info.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：仓库ID
	 *
	 * @return warehouse_product_info.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId warehouse_product_info.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：状态 ON在用，OFF停用,DEL删除
	 *
	 * @return warehouse_product_info.status
	 *
	 * @haoxz11MyBatis
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置：状态 ON在用，OFF停用,DEL删除
	 *
	 * @param status warehouse_product_info.status
	 *
	 * @haoxz11MyBatis
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return warehouse_product_info.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime warehouse_product_info.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return warehouse_product_info.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime warehouse_product_info.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：商品ID
	 *
	 * @return warehouse_product_info.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * 设置：商品ID
	 *
	 * @param productId warehouse_product_info.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * 读取：商品skuID
	 *
	 * @return warehouse_product_info.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductSkuId() {
		return productSkuId;
	}

	/**
	 * 设置：商品skuID
	 *
	 * @param productSkuId warehouse_product_info.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	/**
	 * 读取：规格名称
	 *
	 * @return warehouse_product_info.product_sku_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getProductSkuName() {
		return productSkuName;
	}

	/**
	 * 设置：规格名称
	 *
	 * @param productSkuName warehouse_product_info.product_sku_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuName(String productSkuName) {
		this.productSkuName = productSkuName;
	}

	/**
	 * 读取：进货价(以厘为单位)--采购
	 *
	 * @return warehouse_product_info.price_buy
	 *
	 * @haoxz11MyBatis
	 */
	public Long getPriceBuy() {
		return priceBuy;
	}

	/**
	 * 设置：进货价(以厘为单位)--采购
	 *
	 * @param priceBuy warehouse_product_info.price_buy
	 *
	 * @haoxz11MyBatis
	 */
	public void setPriceBuy(Long priceBuy) {
		this.priceBuy = priceBuy;
	}

	/**
	 * 读取：成本价(以厘为单位)--生产
	 *
	 * @return warehouse_product_info.price_cost
	 *
	 * @haoxz11MyBatis
	 */
	public Long getPriceCost() {
		return priceCost;
	}

	/**
	 * 设置：成本价(以厘为单位)--生产
	 *
	 * @param priceCost warehouse_product_info.price_cost
	 *
	 * @haoxz11MyBatis
	 */
	public void setPriceCost(Long priceCost) {
		this.priceCost = priceCost;
	}

	/**
	 * 读取：单价（执行价格）(以厘为单位)
	 *
	 * @return warehouse_product_info.price_unit
	 *
	 * @haoxz11MyBatis
	 */
	public Long getPriceUnit() {
		return priceUnit;
	}

	/**
	 * 设置：单价（执行价格）(以厘为单位)
	 *
	 * @param priceUnit warehouse_product_info.price_unit
	 *
	 * @haoxz11MyBatis
	 */
	public void setPriceUnit(Long priceUnit) {
		this.priceUnit = priceUnit;
	}

	/**
	 * 读取：实际库存总数量=已售占用+未售实际；库存数量可以负数（预售）
	 *
	 * @return warehouse_product_info.num_total
	 *
	 * @haoxz11MyBatis
	 */
	public Long getNumTotal() {
		return numTotal;
	}

	/**
	 * 设置：实际库存总数量=已售占用+未售实际；库存数量可以负数（预售）
	 *
	 * @param numTotal warehouse_product_info.num_total
	 *
	 * @haoxz11MyBatis
	 */
	public void setNumTotal(Long numTotal) {
		this.numTotal = numTotal;
	}

	/**
	 * 读取：已售占用库存数量
	 *
	 * @return warehouse_product_info.num_sale_occupy
	 *
	 * @haoxz11MyBatis
	 */
	public Long getNumSaleOccupy() {
		return numSaleOccupy;
	}

	/**
	 * 设置：已售占用库存数量
	 *
	 * @param numSaleOccupy warehouse_product_info.num_sale_occupy
	 *
	 * @haoxz11MyBatis
	 */
	public void setNumSaleOccupy(Long numSaleOccupy) {
		this.numSaleOccupy = numSaleOccupy;
	}

	/**
	 * 读取：实际库存未售数量
	 *
	 * @return warehouse_product_info.num_real
	 *
	 * @haoxz11MyBatis
	 */
	public Long getNumReal() {
		return numReal;
	}

	/**
	 * 设置：实际库存未售数量
	 *
	 * @param numReal warehouse_product_info.num_real
	 *
	 * @haoxz11MyBatis
	 */
	public void setNumReal(Long numReal) {
		this.numReal = numReal;
	}

	/**
	 * 读取：计量单位
	 *
	 * @return warehouse_product_info.unit_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * 设置：计量单位
	 *
	 * @param unitName warehouse_product_info.unit_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 读取：备注
	 *
	 * @return warehouse_product_info.remark
	 *
	 * @haoxz11MyBatis
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：备注
	 *
	 * @param remark warehouse_product_info.remark
	 *
	 * @haoxz11MyBatis
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}