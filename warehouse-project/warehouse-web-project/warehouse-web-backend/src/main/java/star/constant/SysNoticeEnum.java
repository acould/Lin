package star.constant;

public enum SysNoticeEnum {

	DEL_UNDELETED("n"), DEL_DELETED("y"), STATUS_SHOW_FRONTEND("y"), STATUS_NOT_SHOW_FRONTEND("n"), ;
	private String value;

	SysNoticeEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
