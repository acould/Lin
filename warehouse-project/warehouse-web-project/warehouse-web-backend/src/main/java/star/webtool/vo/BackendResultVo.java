package star.webtool.vo;

import star.vo.BaseVo;

/**
 * 
 * @description 商城请求处理结果对象
 * @author lufang
 * @date 2018年3月21日 下午12:46:18
 * @param <T>
 */
public class BackendResultVo<T> extends BaseVo {
	private static final long serialVersionUID = -1222614520893986846L;
	
	private T result;
	
	/**
	 * 请求处理结果
	 */
	private boolean success = false;
	
	/**
	 * 请求回传code
	 */
	private String code;
	
	/**
	 * 请求处理结果描述
	 */
	private String resultDes;
	
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultDes() {
		return resultDes;
	}

	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}
}
