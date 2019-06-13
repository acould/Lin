package star.vo;

import java.util.List;

/**
 * 
 * @author SRC
 *
 * @Time 2016年5月11日下午6:00:40
 */
public class RuleType {
	private String key ;//P_T1BN,P_T2B3,P_L1T2B2,P_L2C2R2,P_T2C2B2,P_L1RT1B2,P_T2B2
	private List<ImgLimit> imgLimit; //
	public List<ImgLimit> getImgLimit() {
		return imgLimit;
	}

	public void setImgLimit(List<ImgLimit> imgLimit) {
		this.imgLimit = imgLimit;
	}
	
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}



	
	

}
