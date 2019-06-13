package star.warehouse.enums;

import star.modules.cache.enumerate.BaseCacheEnum;


public enum CacheWarehouseEnum implements BaseCacheEnum {
	WAREHOUSEDTO_LIST("warehousedto_list"),//仓库list
	PRODUCTXYZCODESTORAGEDTO_LIST("productxyzcodestoragedto_list"),//
	STORAGEAREADTO_LIST("storageareadto_list"),
	STORAGEAREAXYZCODEDTO_LIST("storageareaxyzcodedto_list"),
	WAREHOUSEPRODUCTINFODTO_LIST("warehouseproductinfodto_list"),
	WAREHOUSEPRODUCTWARNINFODTO_LIST("warehouseproductwarninfodto"),
	WAREHOUSECLASSDTOLIST("WarehouseClassDTOList"),//仓库class
	
	;

	private String type;

	CacheWarehouseEnum(String type) {
		this.type = type;
	}

	@Override
	public String getAnchor() {
		return this.type;
	}

	@Override
	public BaseCacheEnum get(String key) {
		if (key != null && key.length() > 0) {
			for (CacheWarehouseEnum ctype : CacheWarehouseEnum.values()) {
				if (key.equals(ctype.getAnchor())) {
					return ctype;
				}
			}
		}
		return null;
	}

}
