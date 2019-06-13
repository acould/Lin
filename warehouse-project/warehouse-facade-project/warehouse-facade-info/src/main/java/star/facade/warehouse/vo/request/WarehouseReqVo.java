package star.facade.warehouse.vo.request;

/**
 *	仓库信息列表查询
 *	接收浏览器传来的搜索条件
 * Copyright: Copyright guang.com(c) 2019
 * @since：2019年6月9日下午4:49:59
 * @user： chenhang
 */
public class WarehouseReqVo {

	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：仓库类别:product_ok货品成品仓库;product_bug货品残次品仓；material材料仓；other其他
	 *
	 */
	private String warehouseType;

	/**
	 * 字段：仓库名称
	 *	
	 */
	private String name;

	/**
	 * 字段：仓库编号
	 *	
	 */
	private String code;

	/**
	 * 字段：仓库分类id
	 *	
	 */
	private Long warehouseClassId;
	
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

	public Long getWarehouseClassId() {
		return warehouseClassId;
	}

	public void setWarehouseClassId(Long warehouseClassId) {
		this.warehouseClassId = warehouseClassId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 查询条件的选择框
	 */
	private String searchKey;
	
	/**
	 * 输入的搜索框
	 */
	private String keyValue;
}
