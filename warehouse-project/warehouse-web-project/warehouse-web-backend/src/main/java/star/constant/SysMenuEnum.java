package star.constant;

/**
 * 系统菜单
 * @author huangm
 *
 */
public enum SysMenuEnum {

	//顶级菜单，左侧菜单，子菜单
	TOP("TOP"), LEFT("LEFT"), LEAF("LEAF") ;
	
	private String value;

	SysMenuEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
	
}
