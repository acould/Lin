package star.webtool.vo;

import star.vo.BaseVo;

/**
 * 
 * Title:文件上传DTO
 * 
 * Description:
 * 
 * Copyright: Copyright guang.com(c) 2015
 * 
 * @author yes
 * @created 2015年2月14日 上午10:11:06
 * @version $Id: FileUploadPathVo.java 86542 2015-03-24 02:45:58Z yes $
 */
public class FileUploadPathVo extends BaseVo{
	private static final long serialVersionUID = 1534191596981991817L;
	
	private String path;
	
	private String fileName;
	
	private String suffix;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
