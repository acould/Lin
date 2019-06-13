package star.constant;

import star.modules.cache.enumerate.BaseCacheEnum;
import star.modules.cache.enumerate.CacheResourceEnum;

public enum ManagerCacheEnum implements BaseCacheEnum{
	
	SYS_MENU("sys_menu"),
	SYS_ROLE("sys_role"),
	SYS_MENU_LOCATE("sys_menu_locate")
	;

	 private String type;

	 ManagerCacheEnum(String type) {
	        this.type = type;
	}
	
	@Override
	public String getAnchor() {
	    return this.type;
	}
	
	@Override
	public BaseCacheEnum get(String key) {
	    if (key != null && key.length() > 0) {
	        for (CacheResourceEnum type : CacheResourceEnum.values()) {
	            if (key.equals(type.getAnchor())) {
	                return type;
	            }
	        }
	    }
	    return null;
	}

}
