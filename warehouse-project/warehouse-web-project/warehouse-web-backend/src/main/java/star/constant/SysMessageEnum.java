package star.constant;

public enum SysMessageEnum {
	DEL_UNDELETED("n"), DEL_DELETED("y"), READ("y"), UNREAD("n"), USERTYPE_ALL("0");
	private String value;

	SysMessageEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
