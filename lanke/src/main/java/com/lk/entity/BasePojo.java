package com.lk.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**、
 * 
 * Datetime   ： 2016年1月4日 下午1:44:18<br>
 * Title      :  DataEntity.java<br>
 * Description:   公用的数据实体<br>
 * Company    :  hiwan<br>
 * @author cbj
 *
 */
public abstract class BasePojo{
	
	protected String remarks;	// 备注
	protected String createBy;	// 创建者
	protected String createDate;// 创建日期
	protected String updateBy;	// 更新者
	protected String updateDate;// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除）
	protected String flag; 	// 标记
	

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@JsonIgnore
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
