package star.facade.warehouse.vo.request;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 入库单查询条件表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class StoreInReqVo extends BaseVo {
	/**
	 *
	 */
	transient private static final long serialVersionUID = -1L;


	/**
	 * 字段：状态 ON在用，OFF停用,DEL删除
	 *
	 */
	private String status;

	/**
	 * 字段：状态init创建单子；toCheck提交审核,checkPass审核通过
	 */
	private String checkStatus;

	/**
	 * 字段：仓库ID
	 */
	private Long warehouseId;

	/**
	 * 字段：记录人为编号-入库单号
	 */
	private String code;

	/**
	 * 字段：外部单号类型（采购单，退款单，退款单，生产单等）
	 */
	private String outType;


	/**
	 * 字段：外部单号编码
	 */
	private String outCode;

	/**
	 * 字段：经办人姓名
	 *
	 * @haoxz11MyBatis
	 */
	private String operatorName;

	/**
	 * 字段：制单时间
	 */
	private Date operateTime;

	/**
	 * 字段：审核人姓名
	 *
	 * @haoxz11MyBatis
	 */
	private String checkerName;

	/**
	 * 字段：审核时间
	 */
	private Date checkTime;

	/**
	 * 字段：供应商名称
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
	 */
	private String cause;

	//user
	/**
	 * 查询条件选择项
	 */
	private String searchKey;
	/**
	 * 查询条件选择项　的值
	 */
	private String keyValue;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public String getOutCode() {
		return outCode;
	}
	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getCheckerName() {
		return checkerName;
	}
	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	
	
	
	
	

}