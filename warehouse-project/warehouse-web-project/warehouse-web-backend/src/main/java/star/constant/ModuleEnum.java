package star.constant;

public enum ModuleEnum{

	INDEX("index", "首页")
	;

	 private String type;
	 private String name;

	 ModuleEnum(String type, String name) {
	        this.type = type;
	        this.name = name;
	}

	public String getType() {
	 	return this.type;
	}

	public String getName() {
		return this.name;
	}

}
