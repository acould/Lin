package com.lk.entity.system;


public class RoleMenuInfo {
	/**
	 * 角色编号(主键)
	 */
	private String roleId;
	private String roleName;
	/**
	 * 角色菜单编号
	 */
	private String menuId;
	private String menuUrl;
	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
