package star.constant;

public enum OnOrOff {
	ON("on"),
	OFF("off");
	
	String val;
	
	public final static String[] values = new String[]{ON.getVal(),OFF.getVal()};
	
	public String getVal() {
		return val;
	}


	public void setVal(String val) {
		this.val = val;
	}


	private OnOrOff(String val){
		this.val = val;
	}
}
