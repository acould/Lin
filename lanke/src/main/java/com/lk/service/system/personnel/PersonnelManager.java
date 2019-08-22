package com.lk.service.system.personnel;

import java.util.List;

import com.lk.entity.system.Role;
import com.lk.util.PageData;

/**
 * 角色管理接口
 * @author Administrator
 *
 */
public interface PersonnelManager {
	
	/**
	 * 列出此组下的角色
	 * @param (必填:网吧id--INTENET_ID;选填:角色id--ROLE_ID)
	 * @return 此组下的角色
	 * @throws Exception
	 */
	public List<Role> listAllRolesByPId(PageData pd) throws Exception;
	
	/**
	 * 保存新增角色
	 * @param (必填:RIGHTS--菜单权限,ROLE_ID--角色id,ROLE_NAME--角色id,ADD_QX--新增权限,DEL_QX--删除权限,EDIT_QX--修改权限,CHA_QX--查看权限,INTENET_ID--网吧id)
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception;
	
	/**
	 * 通过id查找角色信息
	 * @param (必填:角色id--ROLE_ID)
	 * @return 该角色信息
	 * @throws Exception
	 */
	public Role getRoleById(String ROLE_ID) throws Exception;
	
	/**给当前角色附加菜单权限
	 * @param role
	 * @throws Exception
	 */
	public void updateRoleRights(Role role) throws Exception;
	
	/**给全部子角色加菜单权限
	 * @param pd
	 * @throws Exception
	 */
	public void setAllRights(PageData pd) throws Exception;
	
	/**权限(增删改查)
	 * @param msg 区分增删改查
	 * @param pd
	 * @throws Exception
	 */
	public void saveB4Button(String msg,PageData pd) throws Exception;
	
	/**
	 * 通过id查找角色信息
	 * @param pd(必填:角色id--ROLE_ID)
	 * @return 满足条件的角色信息
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception;
	
	/**
	 * 保存角色修改信息(通过角色id修改修改权限)
	 * @param pd(必填:角色id--ROLE_ID,修改权限--EDIT_QX)
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;
	
	/**
	 * 删除角色
	 * 通过角色id删除该角色信息
	 * @param (必填:角色id--ROLE_ID)
	 * @throws Exception
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception;
	
	/**
	 * 通过INTENET_ID和ROLE_NAME判断角色名是否重复
	 * @param pd(必填:网吧Id--INTENET_ID,角色名称--ROLE_NAME)
	 * @return 满足条件的角色
	 * @throws Exception
	 */
	public PageData getRoleByName (PageData pd) throws Exception;
	
	/**
	 * Save Service
	 * 保存角色的权限
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData personnelSave(PageData pageData)throws Exception;

}
