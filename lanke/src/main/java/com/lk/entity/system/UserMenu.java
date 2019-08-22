package com.lk.entity.system;


/**
 * 用户自定义菜单维护表
 * Datetime   ： 2013-2-7 上午9:57:59<br>
 * Title      :  UserMenu.java<br>
 * Company    :  杭州展鸿科技<br>
 * @author 朱缙伟
 *
 */
public class UserMenu {
	
	/**
	 * 用户关系Id
	 */
	private String relationId;
	/**
	 * 菜单编号
	 */
	private String menuId;
	/**
	 * 状态
	 */
	private String status;
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "UserMenu [relationId=" + relationId + ", menuId=" + menuId + ", status=" + status + "]";
	}
	
}
