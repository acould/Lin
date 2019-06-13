package star.facade.warehouse.vo;

import java.util.Date;

import star.util.validate.ValidationParam;
import star.util.validate.ValidationType;
import star.vo.BaseVo;

/**
 *	接收仓库基本信息和仓库地址的合并对象
 * Copyright: Copyright guang.com(c) 2019
 * @since：2019年6月8日下午3:48:53
 * @user： chenhang
 */
public class WarehouseAndAddressVo extends BaseVo {
	
	
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
	
	//仓库信息表
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
	@ValidationParam(type=ValidationType.NotBlank,msg="仓库分类不能为空")
	private Long warehouseClassId;
	
	
	//仓库地址信息表
	

	/**
	 * 字段：国家
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="国家不能为空")
	private String country;

	/**
	 * 字段：州省
	 */
	private String province;

	/**
	 * 字段：区市
	 */
	private String city;

	/**
	 * 字段：区县
	 */
	private String county;

	/**
	 * 字段：街道
	 */
	private String street;

	/**
	 * 字段：详细地址
	 */
	
	private String address;
	/**
	 * 字段：邮编
	 */
	private String postCode;

	/**
	 * 字段：联系电话
	 */
	private String telphone;
	
	/**
	 * 字段：管理员名称
	 */
	private String manager;

	/**
	 * 字段：管理员id
	 */
	private Long managerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public Long getCreaterMid() {
		return createrMid;
	}

	public void setCreaterMid(Long createrMid) {
		this.createrMid = createrMid;
	}

	public Long getUpdaterMid() {
		return updaterMid;
	}

	public void setUpdaterMid(Long updaterMid) {
		this.updaterMid = updaterMid;
	}

	public Integer getIsOutsource() {
		return isOutsource;
	}

	public void setIsOutsource(Integer isOutsource) {
		this.isOutsource = isOutsource;
	}

	public String getOutsourceName() {
		return outsourceName;
	}

	public void setOutsourceName(String outsourceName) {
		this.outsourceName = outsourceName;
	}

	public String getOutsourceUrl() {
		return outsourceUrl;
	}

	public void setOutsourceUrl(String outsourceUrl) {
		this.outsourceUrl = outsourceUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWarehouseClassId() {
		return warehouseClassId;
	}

	public void setWarehouseClassId(Long warehouseClassId) {
		this.warehouseClassId = warehouseClassId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	


	
}
	