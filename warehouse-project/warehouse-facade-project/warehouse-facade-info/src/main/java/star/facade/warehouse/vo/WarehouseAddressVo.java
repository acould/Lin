package star.facade.warehouse.vo;

import java.util.Date;

import star.util.validate.ValidationParam;
import star.util.validate.ValidationType;
import star.vo.BaseVo;

/**
 * 
 * 仓库位置表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class WarehouseAddressVo extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：仓库ID
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="仓库ID不能为空")
	private Long warehouseId;

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
	 * 字段：商家ID
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type=ValidationType.NotBlank,msg="商家ID不能为空")
	private Long merchantId;

	/**
	 * 字段：国家
	 *
	 * @haoxz11MyBatis
	 */
	private String country;

	/**
	 * 字段：州省
	 *
	 * @haoxz11MyBatis
	 */
	private String province;

	/**
	 * 字段：区市
	 *
	 * @haoxz11MyBatis
	 */
	private String city;

	/**
	 * 字段：区县
	 *
	 * @haoxz11MyBatis
	 */
	private String county;

	/**
	 * 字段：街道
	 *
	 * @haoxz11MyBatis
	 */
	private String street;

	/**
	 * 字段：详细地址
	 *
	 * @haoxz11MyBatis
	 */
	private String address;

	/**
	 * 字段：管理员名称
	 *
	 * @haoxz11MyBatis
	 */
	private String manager;

	/**
	 * 字段：管理员id
	 *
	 * @haoxz11MyBatis
	 */
	private Long managerId;

	/**
	 * 字段：邮编
	 *
	 * @haoxz11MyBatis
	 */
	private String postCode;

	/**
	 * 字段：联系电话
	 *
	 * @haoxz11MyBatis
	 */
	private String telphone;

	/**
	 * 读取：仓库ID
	 *
	 * @return warehouse_address.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId warehouse_address.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return warehouse_address.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime warehouse_address.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return warehouse_address.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime warehouse_address.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return warehouse_address.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId warehouse_address.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：国家
	 *
	 * @return warehouse_address.country
	 *
	 * @haoxz11MyBatis
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 设置：国家
	 *
	 * @param country warehouse_address.country
	 *
	 * @haoxz11MyBatis
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 读取：州省
	 *
	 * @return warehouse_address.province
	 *
	 * @haoxz11MyBatis
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 设置：州省
	 *
	 * @param province warehouse_address.province
	 *
	 * @haoxz11MyBatis
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 读取：区市
	 *
	 * @return warehouse_address.city
	 *
	 * @haoxz11MyBatis
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 设置：区市
	 *
	 * @param city warehouse_address.city
	 *
	 * @haoxz11MyBatis
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 读取：区县
	 *
	 * @return warehouse_address.county
	 *
	 * @haoxz11MyBatis
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * 设置：区县
	 *
	 * @param county warehouse_address.county
	 *
	 * @haoxz11MyBatis
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * 读取：街道
	 *
	 * @return warehouse_address.street
	 *
	 * @haoxz11MyBatis
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * 设置：街道
	 *
	 * @param street warehouse_address.street
	 *
	 * @haoxz11MyBatis
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * 读取：详细地址
	 *
	 * @return warehouse_address.address
	 *
	 * @haoxz11MyBatis
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置：详细地址
	 *
	 * @param address warehouse_address.address
	 *
	 * @haoxz11MyBatis
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 读取：管理员名称
	 *
	 * @return warehouse_address.manager
	 *
	 * @haoxz11MyBatis
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * 设置：管理员名称
	 *
	 * @param manager warehouse_address.manager
	 *
	 * @haoxz11MyBatis
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * 读取：管理员id
	 *
	 * @return warehouse_address.manager_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getManagerId() {
		return managerId;
	}

	/**
	 * 设置：管理员id
	 *
	 * @param managerId warehouse_address.manager_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	/**
	 * 读取：邮编
	 *
	 * @return warehouse_address.post_code
	 *
	 * @haoxz11MyBatis
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * 设置：邮编
	 *
	 * @param postCode warehouse_address.post_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * 读取：联系电话
	 *
	 * @return warehouse_address.telphone
	 *
	 * @haoxz11MyBatis
	 */
	public String getTelphone() {
		return telphone;
	}

	/**
	 * 设置：联系电话
	 *
	 * @param telphone warehouse_address.telphone
	 *
	 * @haoxz11MyBatis
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
}