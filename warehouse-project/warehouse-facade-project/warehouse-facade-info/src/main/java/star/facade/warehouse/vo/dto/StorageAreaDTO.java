package star.facade.warehouse.vo.dto;

import star.vo.BaseVo;

/**
 * 
 * 存储区表（可以多级：一货架多货位等）
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class StorageAreaDTO extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:storage_area.id
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
	 * 字段：库区类型:pick_area拣货区;temp_area暂存区，transfer_area中转区，pack_area预包区；other其他
	 *
	 * @haoxz11MyBatis
	 */
	private String storageAreaType;

	/**
	 * 字段：名称
	 *
	 * @haoxz11MyBatis
	 */
	private String name;

	/**
	 * 字段：编号
	 *
	 * @haoxz11MyBatis
	 */
	private String code;

	/**
	 * 字段：排序
	 *
	 * @haoxz11MyBatis
	 */
	private Integer orderby;


	/**
	 * 字段：是否一货多位(y,n)
	 *
	 * @haoxz11MyBatis
	 */
	private String isMore;

	/**
	 * 字段：父级ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long parentId;

	/**
	 * 字段：备注说明
	 *
	 * @haoxz11MyBatis
	 */
	private String remark;

	/**
	 * 读取：storage_area.id
	 *
	 * @return storage_area.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：storage_area.id
	 *
	 * @param id storage_area.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return storage_area.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId storage_area.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}


	/**
	 * 读取：仓库ID
	 *
	 * @return storage_area.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * 设置：仓库ID
	 *
	 * @param warehouseId storage_area.warehouse_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}


	/**
	 * 读取：库区类型:pick_area拣货区;temp_area暂存区，transfer_area中转区，pack_area预包区；other其他
	 *
	 * @return storage_area.storage_area_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getStorageAreaType() {
		return storageAreaType;
	}

	/**
	 * 设置：库区类型:pick_area拣货区;temp_area暂存区，transfer_area中转区，pack_area预包区；other其他
	 *
	 * @param storageAreaType storage_area.storage_area_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setStorageAreaType(String storageAreaType) {
		this.storageAreaType = storageAreaType;
	}

	/**
	 * 读取：名称
	 *
	 * @return storage_area.name
	 *
	 * @haoxz11MyBatis
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：名称
	 *
	 * @param name storage_area.name
	 *
	 * @haoxz11MyBatis
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取：编号
	 *
	 * @return storage_area.code
	 *
	 * @haoxz11MyBatis
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置：编号
	 *
	 * @param code storage_area.code
	 *
	 * @haoxz11MyBatis
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 读取：排序
	 *
	 * @return storage_area.orderby
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getOrderby() {
		return orderby;
	}

	/**
	 * 设置：排序
	 *
	 * @param orderby storage_area.orderby
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}


	/**
	 * 读取：是否一货多位(y,n)
	 *
	 * @return storage_area.is_more
	 *
	 * @haoxz11MyBatis
	 */
	public String getIsMore() {
		return isMore;
	}

	/**
	 * 设置：是否一货多位(y,n)
	 *
	 * @param isMore storage_area.is_more
	 *
	 * @haoxz11MyBatis
	 */
	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}

	/**
	 * 读取：父级ID
	 *
	 * @return storage_area.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：父级ID
	 *
	 * @param parentId storage_area.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 读取：备注说明
	 *
	 * @return storage_area.remark
	 *
	 * @haoxz11MyBatis
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：备注说明
	 *
	 * @param remark storage_area.remark
	 *
	 * @haoxz11MyBatis
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}