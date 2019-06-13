package star.constant;

/**
 * 系统角色
 * @author lorien
 *
 */
public enum SysRoleEnum {

	//管理员，运营，编辑
	admin(1), operator(2), editor(3) ;
	
	private int value;

	SysRoleEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
	
}
