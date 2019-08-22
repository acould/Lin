package com.lk.entity.system;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 用戶关联关系pojo类 Description: DataApplyInfo.java Create on 2013-01-25
 * 
 * @version 1.0 Company : HIWAN<br>
 *          Copyright (c) 2012 Company,Inc. All Rights Reserved.
 * @author hlm
 */
public class UserRelation implements Serializable {
private static final long serialVersionUID = 8477784876084205226L;
	private static final char SPLIT_CHAR = ',';

	/**
	 * 用戶关联关系编号(主键)
	 */
	private String relationId;
	/**
	 * 用户编号
	 */
	private String userId;
	/**
	 * 所属机构编号
	 */
	private String orgId;
	/**
	 * 所属部门编号
	 */
	private String deptId;
	/**
	 * 角色编号
	 */
	private String roleIdstr;
	/**
	 * 该关系的序号，0表示首选，1表示非首选，首选只有一个
	 */
	private String reOrder;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 创建人
	 */
	private String updateUser;
	/**
	 * 创建时间
	 */
	private String updateTime;
	/**
	 * 任职状态
	 */
	private String status;
	/**
	 * 注销时间
	 */
	private String cancelTime;
	/**
	 * 用户角色列表
	 */
	private List<String> roleIds;

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getReOrder() {
		return reOrder;
	}

	public void setReOrder(String reOrder) {
		this.reOrder = reOrder;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRoleIdstr() {
		return roleIdstr;
	}

	public void setRoleIdstr(String roleIdstr) {
		if (roleIdstr != null) {
			roleIds = Arrays.asList(StringUtils.split(roleIdstr, SPLIT_CHAR));
		}
		this.roleIdstr = roleIdstr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	@Override
	public String toString() {
		return "UserRelation [relationId=" + relationId + ", userId=" + userId + ", orgId=" + orgId + ", deptId="
				+ deptId + ", roleIdstr=" + roleIdstr + ", reOrder=" + reOrder + ", createUser=" + createUser
				+ ", createTime=" + createTime + ", updateUser=" + updateUser + ", updateTime=" + updateTime
				+ ", status=" + status + ", cancelTime=" + cancelTime + ", roleIds=" + roleIds + "]";
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		if (roleIds != null) {
			StringBuffer buffer = new StringBuffer();
			for (String roleId : roleIds) {
				buffer.append(roleId);
				buffer.append(SPLIT_CHAR);
			}

			this.roleIdstr = buffer.substring(0, buffer.length() - 1);
		}
		this.roleIds = roleIds;
	}
}
