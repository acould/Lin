package star.warehouse.enums;


/**
 * 仓库类型
 * warehouse_type` varchar(20) NOT NULL COMMENT '仓库类别:product_ok货品成品仓库;product_bug货品残次品仓；material材料仓；other其他',
 * @Author:xxh
 * @Since:2018年11月21日下午3:59:26
 */
public enum WarehouseTypeEnum {
	PRODUCT_OK("product_ok"),//货品成品仓库
	PRODUCT_BUG("product_bug"),//货品残次品仓
	MATERIAL("material"),//材料仓
	OTHER("other") //其他
	;
	
	private String value;

	WarehouseTypeEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
	
}
