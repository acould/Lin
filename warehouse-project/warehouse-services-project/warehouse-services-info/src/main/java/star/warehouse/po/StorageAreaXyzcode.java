package star.warehouse.po;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 存储区编码表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class StorageAreaXyzcode extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：存储区ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long storageAreaId;

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
	 * 字段：仓库ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long warehouseId;

	/**
	 * 字段：行(长cm)
	 *
	 * @haoxz11MyBatis
	 */
	private Integer x;

	/**
	 * 字段：列(宽cm)
	 *
	 * @haoxz11MyBatis
	 */
	private Integer y;

	/**
	 * 字段：层（高cm）
	 *
	 * @haoxz11MyBatis
	 */
	private Integer z;

	/**
	 * 字段：空间编码
	 *
	 * @haoxz11MyBatis
	 */
	private String xyzCode;

	/**
	 * 读取：存储区ID
	 *
	 * @return storage_area_xyzcode.storage_area_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getStorageAreaId() {
		return storageAreaId;
	}

	/**
	 * 设置：存储区ID
	 *
	 * @param storageAreaId storage_area_xyzcode.storage_area_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setStorageAreaId(Long storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return storage_area_xyzcode.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime storage_area_xyzcode.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return storage_area_xyzcode.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime storage_area_xyzcode.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return storage_area_xyzcode.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId storage_area_xyzcode.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：仓库ID
	 *
	 * @return storage_area_xyzcode.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId storage_area_xyzcode.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * 读取：行(长cm)
	 *
	 * @return storage_area_xyzcode.x
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * 设置：行(长cm)
	 *
	 * @param x storage_area_xyzcode.x
	 *
	 * @haoxz11MyBatis
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * 读取：列(宽cm)
	 *
	 * @return storage_area_xyzcode.y
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * 设置：列(宽cm)
	 *
	 * @param y storage_area_xyzcode.y
	 *
	 * @haoxz11MyBatis
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * 读取：层（高cm）
	 *
	 * @return storage_area_xyzcode.z
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getZ() {
		return z;
	}

	/**
	 * 设置：层（高cm）
	 *
	 * @param z storage_area_xyzcode.z
	 *
	 * @haoxz11MyBatis
	 */
	public void setZ(Integer z) {
		this.z = z;
	}

	/**
	 * 读取：空间编码
	 *
	 * @return storage_area_xyzcode.xyz_code
	 *
	 * @haoxz11MyBatis
	 */
	public String getXyzCode() {
		return xyzCode;
	}

	/**
	 * 设置：空间编码
	 *
	 * @param xyzCode storage_area_xyzcode.xyz_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setXyzCode(String xyzCode) {
		this.xyzCode = xyzCode;
	}
}