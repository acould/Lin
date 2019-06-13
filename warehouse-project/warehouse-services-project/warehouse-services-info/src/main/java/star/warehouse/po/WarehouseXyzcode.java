package star.warehouse.po;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 仓库空间编码表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class WarehouseXyzcode extends BaseVo {
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
	private Long merchantId;

	/**
	 * 字段：经度
	 *
	 * @haoxz11MyBatis
	 */
	private Double longitude;

	/**
	 * 字段：纬度
	 *
	 * @haoxz11MyBatis
	 */
	private Double latitude;

	/**
	 * 字段：高度
	 *
	 * @haoxz11MyBatis
	 */
	private Double altitude;

	/**
	 * 字段：空间编码
	 *
	 * @haoxz11MyBatis
	 */
	private String xyzCode;

	/**
	 * 读取：仓库ID
	 *
	 * @return warehouse_xyzcode.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId warehouse_xyzcode.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return warehouse_xyzcode.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime warehouse_xyzcode.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return warehouse_xyzcode.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime warehouse_xyzcode.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return warehouse_xyzcode.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId warehouse_xyzcode.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：经度
	 *
	 * @return warehouse_xyzcode.longitude
	 *
	 * @haoxz11MyBatis
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * 设置：经度
	 *
	 * @param longitude warehouse_xyzcode.longitude
	 *
	 * @haoxz11MyBatis
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 读取：纬度
	 *
	 * @return warehouse_xyzcode.latitude
	 *
	 * @haoxz11MyBatis
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * 设置：纬度
	 *
	 * @param latitude warehouse_xyzcode.latitude
	 *
	 * @haoxz11MyBatis
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * 读取：高度
	 *
	 * @return warehouse_xyzcode.altitude
	 *
	 * @haoxz11MyBatis
	 */
	public Double getAltitude() {
		return altitude;
	}

	/**
	 * 设置：高度
	 *
	 * @param altitude warehouse_xyzcode.altitude
	 *
	 * @haoxz11MyBatis
	 */
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	/**
	 * 读取：空间编码
	 *
	 * @return warehouse_xyzcode.xyz_code
	 *
	 * @haoxz11MyBatis
	 */
	public String getXyzCode() {
		return xyzCode;
	}

	/**
	 * 设置：空间编码
	 *
	 * @param xyzCode warehouse_xyzcode.xyz_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setXyzCode(String xyzCode) {
		this.xyzCode = xyzCode;
	}
}