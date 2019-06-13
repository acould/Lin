package star.facade.warehouse.vo;

import java.util.Date;

import star.util.validate.ValidationParam;
import star.util.validate.ValidationType;
import star.vo.BaseVo;

/**
 * 
 * 仓库信息表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class WarehouseVo extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:warehouse.id
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
	 * 字段：仓库类别:product_ok货品成品仓库;product_bug货品残次品仓；material材料仓；other其他
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="仓库类别不能为空")
	private String warehouseType;

	/**
	 * 字段：仓库名称
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="仓库名称不能为空")
	private String name;

	/**
	 * 字段：仓库编号
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="仓库编号不能为空")
	private String code;

	/**
	 * 字段：排序
	 *
	 * @haoxz11MyBatis
	 */
	private Integer orderby;

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
	 * 字段：是否委外
	 *
	 * @haoxz11MyBatis
	 */
	private Integer isOutsource;

	/**
	 * 字段：委外名称
	 *
	 * @haoxz11MyBatis
	 */
	private String outsourceName;

	/**
	 * 字段：委外接口
	 *
	 * @haoxz11MyBatis
	 */
	private String outsourceUrl;

	/**
	 * 字段：备注说明
	 *
	 * @haoxz11MyBatis
	 */
	private String remark;
	/**
	 * 字段：仓库分类id
	 *
	 * @haoxz11MyBatis
	 */
	private Long warehouseClassId;

	/**
	 * 读取：warehouse.id
	 *
	 * @return warehouse.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：warehouse.id
	 *
	 * @param id warehouse.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return warehouse.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId warehouse.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：状态 ON在用，OFF停用,DEL删除
	 *
	 * @return warehouse.status
	 *
	 * @haoxz11MyBatis
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置：状态 ON在用，OFF停用,DEL删除
	 *
	 * @param status warehouse.status
	 *
	 * @haoxz11MyBatis
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return warehouse.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime warehouse.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return warehouse.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime warehouse.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：仓库类别:product_ok货品成品仓库;product_bug货品残次品仓；material材料仓；other其他
	 *
	 * @return warehouse.warehouse_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getWarehouseType() {
		return warehouseType;
	}

	/**
	 * 设置：仓库类别:product_ok货品成品仓库;product_bug货品残次品仓；material材料仓；other其他
	 *
	 * @param warehouseType warehouse.warehouse_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}

	/**
	 * 读取：仓库名称
	 *
	 * @return warehouse.name
	 *
	 * @haoxz11MyBatis
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：仓库名称
	 *
	 * @param name warehouse.name
	 *
	 * @haoxz11MyBatis
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取：仓库编号
	 *
	 * @return warehouse.code
	 *
	 * @haoxz11MyBatis
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置：仓库编号
	 *
	 * @param code warehouse.code
	 *
	 * @haoxz11MyBatis
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 读取：排序
	 *
	 * @return warehouse.orderby
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getOrderby() {
		return orderby;
	}

	/**
	 * 设置：排序
	 *
	 * @param orderby warehouse.orderby
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	/**
	 * 读取：创建者
	 *
	 * @return warehouse.creater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public Long getCreaterMid() {
		return createrMid;
	}

	/**
	 * 设置：创建者
	 *
	 * @param createrMid warehouse.creater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreaterMid(Long createrMid) {
		this.createrMid = createrMid;
	}

	/**
	 * 读取：修改者
	 *
	 * @return warehouse.updater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public Long getUpdaterMid() {
		return updaterMid;
	}

	/**
	 * 设置：修改者
	 *
	 * @param updaterMid warehouse.updater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public void setUpdaterMid(Long updaterMid) {
		this.updaterMid = updaterMid;
	}

	/**
	 * 读取：是否委外
	 *
	 * @return warehouse.is_outsource
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getIsOutsource() {
		return isOutsource;
	}

	/**
	 * 设置：是否委外
	 *
	 * @param isOutsource warehouse.is_outsource
	 *
	 * @haoxz11MyBatis
	 */
	public void setIsOutsource(Integer isOutsource) {
		this.isOutsource = isOutsource;
	}

	/**
	 * 读取：委外名称
	 *
	 * @return warehouse.outsource_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getOutsourceName() {
		return outsourceName;
	}

	/**
	 * 设置：委外名称
	 *
	 * @param outsourceName warehouse.outsource_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setOutsourceName(String outsourceName) {
		this.outsourceName = outsourceName;
	}

	/**
	 * 读取：委外接口
	 *
	 * @return warehouse.outsource_url
	 *
	 * @haoxz11MyBatis
	 */
	public String getOutsourceUrl() {
		return outsourceUrl;
	}

	/**
	 * 设置：委外接口
	 *
	 * @param outsourceUrl warehouse.outsource_url
	 *
	 * @haoxz11MyBatis
	 */
	public void setOutsourceUrl(String outsourceUrl) {
		this.outsourceUrl = outsourceUrl;
	}

	/**
	 * 读取：备注说明
	 *
	 * @return warehouse.remark
	 *
	 * @haoxz11MyBatis
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：备注说明
	 *
	 * @param remark warehouse.remark
	 *
	 * @haoxz11MyBatis
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWarehouseClassId() {
		return warehouseClassId;
	}

	public void setWarehouseClassId(Long warehouseClassId) {
		this.warehouseClassId = warehouseClassId;
	}

	
	/* 
	 * @chenhang
	 * 方便测试
	 */
	public String toString() {
		return "WarehouseVo [id=" + id + ", merchantId=" + merchantId + ", status=" + status + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + ", warehouseType=" + warehouseType + ", name=" + name
				+ ", code=" + code+"] ";
	}
	
	
}