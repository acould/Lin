package star.webtool.external;

import star.vo.BaseVo;

public class StatusMsgVo  extends BaseVo  {
	/**
	 * 
	 * @since：2015年7月22日下午7:02:10
	 * @user： xuxinghua
	 */
	private static final long serialVersionUID = 1L;
	private int status;
	private String msg;
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
