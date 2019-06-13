package star.warehouse.po;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 入库单表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class StoreIn extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:store_in.id
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
	 * 字段：状态init创建单子；toCheck提交审核,checkPass审核通过
	 *
	 * @haoxz11MyBatis
	 */
	private String checkStatus;

	/**
	 * 字段：仓库ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long warehouseId;

	/**
	 * 字段：记录人为编号-入库单号
	 *
	 * @haoxz11MyBatis
	 */
	private String code;

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
	 * 字段：创建者
	 *
	 * @haoxz11MyBatis
	 */
	private Long createrMid;

	/**
	 * 字段：修改者
	 *
	 * @haoxz11MyBatis
	 */
	private Long updaterMid;

	/**
	 * 字段：外部单号类型（采购单，退款单，退款单，生产单等）
	 *
	 * @haoxz11MyBatis
	 */
	private String outType;

	/**
	 * 字段：外部单号id
	 *
	 * @haoxz11MyBatis
	 */
	private Long outId;

	/**
	 * 字段：外部单号编码
	 *
	 * @haoxz11MyBatis
	 */
	private String outCode;

	/**
	 * 字段：经办人id
	 *
	 * @haoxz11MyBatis
	 */
	private Long operatorId;

	/**
	 * 字段：经办人姓名
	 *
	 * @haoxz11MyBatis
	 */
	private String operatorName;

	/**
	 * 字段：制单时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date operateTime;

	/**
	 * 字段：审核人id
	 *
	 * @haoxz11MyBatis
	 */
	private Long checkerId;

	/**
	 * 字段：审核人姓名
	 *
	 * @haoxz11MyBatis
	 */
	private String checkerName;

	/**
	 * 字段：审核时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date checkTime;

	/**
	 * 字段：供应商id
	 *
	 * @haoxz11MyBatis
	 */
	private Long supplierId;

	/**
	 * 字段：供应商名称
	 *
	 * @haoxz11MyBatis
	 */
	private String supplierName;

	/**
	 * 字段：仓库名称
	 *
	 * @haoxz11MyBatis
	 */
	private String warehouseName;

	/**
	 * 字段：入库原因
	 *
	 * @haoxz11MyBatis
	 */
	private String cause;

	/**
	 * 字段：标记
	 *
	 * @haoxz11MyBatis
	 */
	private String flagNo;

	/**
	 * 字段：货款合计(以厘为单位)
	 *
	 * @haoxz11MyBatis
	 */
	private Long totalAmount;

	/**
	 * 字段：其他费用(以厘为单位)
	 *
	 * @haoxz11MyBatis
	 */
	private Long otherFee;

	/**
	 * 字段：冲销状态
	 *
	 * @haoxz11MyBatis
	 */
	private String writeOffStatus;

	/**
	 * 字段：冲销单号
	 *
	 * @haoxz11MyBatis
	 */
	private String writeOffNo;

	/**
	 * 字段：备注
	 *
	 * @haoxz11MyBatis
	 */
	private String remark;

	/**
	 * 字段：物流公司编号
	 *
	 * @haoxz11MyBatis
	 */
	private Long logisticCode;

	/**
	 * 字段：物流单编号
	 *
	 * @haoxz11MyBatis
	 */
	private String logisticNumber;

	/**
	 * 字段：物流单费用(以厘为单位)
	 *
	 * @haoxz11MyBatis
	 */
	private Long logisticFee;

	/**
	 * 字段：付款账户号
	 *
	 * @haoxz11MyBatis
	 */
	private Long oidAcctno;

	/**
	 * 字段：货币编号
	 *
	 * @haoxz11MyBatis
	 */
	private String currencyCode;

	/**
	 * 字段：货币汇率
	 *
	 * @haoxz11MyBatis
	 */
	private Double currencyRate;

	/**
	 * 字段：现付金额(以厘为单位)
	 *
	 * @haoxz11MyBatis
	 */
	private Long paidAmont;

	/**
	 * 字段：执行价格编号
	 *
	 * @haoxz11MyBatis
	 */
	private String priceSpecCode;

	/**
	 * 字段：折扣
	 *
	 * @haoxz11MyBatis
	 */
	private Double priceDis;

	/**
	 * 读取：store_in.id
	 *
	 * @return store_in.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：store_in.id
	 *
	 * @param id store_in.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return store_in.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId store_in.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：状态 ON在用，OFF停用,DEL删除
	 *
	 * @return store_in.status
	 *
	 * @haoxz11MyBatis
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置：状态 ON在用，OFF停用,DEL删除
	 *
	 * @param status store_in.status
	 *
	 * @haoxz11MyBatis
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取：状态init创建单子；toCheck提交审核,checkPass审核通过
	 *
	 * @return store_in.check_status
	 *
	 * @haoxz11MyBatis
	 */
	public String getCheckStatus() {
		return checkStatus;
	}

	/**
	 * 设置：状态init创建单子；toCheck提交审核,checkPass审核通过
	 *
	 * @param checkStatus store_in.check_status
	 *
	 * @haoxz11MyBatis
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	/**
	 * 读取：仓库ID
	 *
	 * @return store_in.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId store_in.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：记录人为编号-入库单号
	 *
	 * @return store_in.code
	 *
	 * @haoxz11MyBatis
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置：记录人为编号-入库单号
	 *
	 * @param code store_in.code
	 *
	 * @haoxz11MyBatis
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return store_in.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime store_in.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return store_in.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime store_in.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：创建者
	 *
	 * @return store_in.creater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public Long getCreaterMid() {
		return createrMid;
	}

	/**
	 * 设置：创建者
	 *
	 * @param createrMid store_in.creater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreaterMid(Long createrMid) {
		this.createrMid = createrMid;
	}

	/**
	 * 读取：修改者
	 *
	 * @return store_in.updater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public Long getUpdaterMid() {
		return updaterMid;
	}

	/**
	 * 设置：修改者
	 *
	 * @param updaterMid store_in.updater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public void setUpdaterMid(Long updaterMid) {
		this.updaterMid = updaterMid;
	}

	/**
	 * 读取：外部单号类型（采购单，退款单，退款单，生产单等）
	 *
	 * @return store_in.out_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getOutType() {
		return outType;
	}

	/**
	 * 设置：外部单号类型（采购单，退款单，退款单，生产单等）
	 *
	 * @param outType store_in.out_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setOutType(String outType) {
		this.outType = outType;
	}

	/**
	 * 读取：外部单号id
	 *
	 * @return store_in.out_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOutId() {
		return outId;
	}

	/**
	 * 设置：外部单号id
	 *
	 * @param outId store_in.out_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setOutId(Long outId) {
		this.outId = outId;
	}

	/**
	 * 读取：外部单号编码
	 *
	 * @return store_in.out_code
	 *
	 * @haoxz11MyBatis
	 */
	public String getOutCode() {
		return outCode;
	}

	/**
	 * 设置：外部单号编码
	 *
	 * @param outCode store_in.out_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	/**
	 * 读取：经办人id
	 *
	 * @return store_in.operator_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * 设置：经办人id
	 *
	 * @param operatorId store_in.operator_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * 读取：经办人姓名
	 *
	 * @return store_in.operator_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 设置：经办人姓名
	 *
	 * @param operatorName store_in.operator_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 读取：制单时间
	 *
	 * @return store_in.operate_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * 设置：制单时间
	 *
	 * @param operateTime store_in.operate_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * 读取：审核人id
	 *
	 * @return store_in.checker_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getCheckerId() {
		return checkerId;
	}

	/**
	 * 设置：审核人id
	 *
	 * @param checkerId store_in.checker_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setCheckerId(Long checkerId) {
		this.checkerId = checkerId;
	}

	/**
	 * 读取：审核人姓名
	 *
	 * @return store_in.checker_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getCheckerName() {
		return checkerName;
	}

	/**
	 * 设置：审核人姓名
	 *
	 * @param checkerName store_in.checker_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	/**
	 * 读取：审核时间
	 *
	 * @return store_in.check_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCheckTime() {
		return checkTime;
	}

	/**
	 * 设置：审核时间
	 *
	 * @param checkTime store_in.check_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	/**
	 * 读取：供应商id
	 *
	 * @return store_in.supplier_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getSupplierId() {
		return supplierId;
	}

	/**
	 * 设置：供应商id
	 *
	 * @param supplierId store_in.supplier_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * 读取：供应商名称
	 *
	 * @return store_in.supplier_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * 设置：供应商名称
	 *
	 * @param supplierName store_in.supplier_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * 读取：仓库名称
	 *
	 * @return store_in.warehouse_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getWarehouseName() {
		return warehouseName;
	}

	/**
	 * 设置：仓库名称
	 *
	 * @param warehouseName store_in.warehouse_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	/**
	 * 读取：入库原因
	 *
	 * @return store_in.cause
	 *
	 * @haoxz11MyBatis
	 */
	public String getCause() {
		return cause;
	}

	/**
	 * 设置：入库原因
	 *
	 * @param cause store_in.cause
	 *
	 * @haoxz11MyBatis
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/**
	 * 读取：标记
	 *
	 * @return store_in.flag_no
	 *
	 * @haoxz11MyBatis
	 */
	public String getFlagNo() {
		return flagNo;
	}

	/**
	 * 设置：标记
	 *
	 * @param flagNo store_in.flag_no
	 *
	 * @haoxz11MyBatis
	 */
	public void setFlagNo(String flagNo) {
		this.flagNo = flagNo;
	}

	/**
	 * 读取：货款合计(以厘为单位)
	 *
	 * @return store_in.total_amount
	 *
	 * @haoxz11MyBatis
	 */
	public Long getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 设置：货款合计(以厘为单位)
	 *
	 * @param totalAmount store_in.total_amount
	 *
	 * @haoxz11MyBatis
	 */
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 读取：其他费用(以厘为单位)
	 *
	 * @return store_in.other_fee
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOtherFee() {
		return otherFee;
	}

	/**
	 * 设置：其他费用(以厘为单位)
	 *
	 * @param otherFee store_in.other_fee
	 *
	 * @haoxz11MyBatis
	 */
	public void setOtherFee(Long otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * 读取：冲销状态
	 *
	 * @return store_in.write_off_status
	 *
	 * @haoxz11MyBatis
	 */
	public String getWriteOffStatus() {
		return writeOffStatus;
	}

	/**
	 * 设置：冲销状态
	 *
	 * @param writeOffStatus store_in.write_off_status
	 *
	 * @haoxz11MyBatis
	 */
	public void setWriteOffStatus(String writeOffStatus) {
		this.writeOffStatus = writeOffStatus;
	}

	/**
	 * 读取：冲销单号
	 *
	 * @return store_in.write_off_no
	 *
	 * @haoxz11MyBatis
	 */
	public String getWriteOffNo() {
		return writeOffNo;
	}

	/**
	 * 设置：冲销单号
	 *
	 * @param writeOffNo store_in.write_off_no
	 *
	 * @haoxz11MyBatis
	 */
	public void setWriteOffNo(String writeOffNo) {
		this.writeOffNo = writeOffNo;
	}

	/**
	 * 读取：备注
	 *
	 * @return store_in.remark
	 *
	 * @haoxz11MyBatis
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：备注
	 *
	 * @param remark store_in.remark
	 *
	 * @haoxz11MyBatis
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 读取：物流公司编号
	 *
	 * @return store_in.logistic_code
	 *
	 * @haoxz11MyBatis
	 */
	public Long getLogisticCode() {
		return logisticCode;
	}

	/**
	 * 设置：物流公司编号
	 *
	 * @param logisticCode store_in.logistic_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogisticCode(Long logisticCode) {
		this.logisticCode = logisticCode;
	}

	/**
	 * 读取：物流单编号
	 *
	 * @return store_in.logistic_number
	 *
	 * @haoxz11MyBatis
	 */
	public String getLogisticNumber() {
		return logisticNumber;
	}

	/**
	 * 设置：物流单编号
	 *
	 * @param logisticNumber store_in.logistic_number
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogisticNumber(String logisticNumber) {
		this.logisticNumber = logisticNumber;
	}

	/**
	 * 读取：物流单费用(以厘为单位)
	 *
	 * @return store_in.logistic_fee
	 *
	 * @haoxz11MyBatis
	 */
	public Long getLogisticFee() {
		return logisticFee;
	}

	/**
	 * 设置：物流单费用(以厘为单位)
	 *
	 * @param logisticFee store_in.logistic_fee
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogisticFee(Long logisticFee) {
		this.logisticFee = logisticFee;
	}

	/**
	 * 读取：付款账户号
	 *
	 * @return store_in.oid_acctno
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOidAcctno() {
		return oidAcctno;
	}

	/**
	 * 设置：付款账户号
	 *
	 * @param oidAcctno store_in.oid_acctno
	 *
	 * @haoxz11MyBatis
	 */
	public void setOidAcctno(Long oidAcctno) {
		this.oidAcctno = oidAcctno;
	}

	/**
	 * 读取：货币编号
	 *
	 * @return store_in.currency_code
	 *
	 * @haoxz11MyBatis
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置：货币编号
	 *
	 * @param currencyCode store_in.currency_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 读取：货币汇率
	 *
	 * @return store_in.currency_rate
	 *
	 * @haoxz11MyBatis
	 */
	public Double getCurrencyRate() {
		return currencyRate;
	}

	/**
	 * 设置：货币汇率
	 *
	 * @param currencyRate store_in.currency_rate
	 *
	 * @haoxz11MyBatis
	 */
	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}

	/**
	 * 读取：现付金额(以厘为单位)
	 *
	 * @return store_in.paid_amont
	 *
	 * @haoxz11MyBatis
	 */
	public Long getPaidAmont() {
		return paidAmont;
	}

	/**
	 * 设置：现付金额(以厘为单位)
	 *
	 * @param paidAmont store_in.paid_amont
	 *
	 * @haoxz11MyBatis
	 */
	public void setPaidAmont(Long paidAmont) {
		this.paidAmont = paidAmont;
	}

	/**
	 * 读取：执行价格编号
	 *
	 * @return store_in.price_spec_code
	 *
	 * @haoxz11MyBatis
	 */
	public String getPriceSpecCode() {
		return priceSpecCode;
	}

	/**
	 * 设置：执行价格编号
	 *
	 * @param priceSpecCode store_in.price_spec_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setPriceSpecCode(String priceSpecCode) {
		this.priceSpecCode = priceSpecCode;
	}

	/**
	 * 读取：折扣
	 *
	 * @return store_in.price_dis
	 *
	 * @haoxz11MyBatis
	 */
	public Double getPriceDis() {
		return priceDis;
	}

	/**
	 * 设置：折扣
	 *
	 * @param priceDis store_in.price_dis
	 *
	 * @haoxz11MyBatis
	 */
	public void setPriceDis(Double priceDis) {
		this.priceDis = priceDis;
	}
}