package com.lk.service.system.role;

import java.util.List;

import javax.management.relation.RoleInfo;

import com.lk.entity.Page;
import com.lk.entity.system.Role;
import com.lk.util.PageData;

/**	角色接口类
 * @author
 * 修改日期：2015.11.6
 */
public interface RoleManager {
	
	/**
	 * 查询角色
	 * 通过page查询该人员的角色
	 * @param page 关键词--keywords门店id--STORE_ID,网吧id--internetId
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 列出此组下级角色
	 * @param pd 包含角色表主键(ROLE_ID--角色id)
	 * @return 返回满足条件的角色
	 * @throws Exception
	 */
	public List<Role> listAllRolesByPId(PageData pd) throws Exception;
	
	/**
	 * 通过网吧id查询改网吧拥有的角色
	 * @param pd 网吧id--internetId
	 * @throws Exception
	 */
	public List<PageData> listByIntenet(PageData pd)throws Exception;
	
	/**
	 * 通过id查找
	 * @param pd包含主键信息(必填:角色id--ROLE_ID)
	 * @return 满足条件的角色
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception;
	/**新增
	 * @param pd包含角色相关信息
	 * @throws Exception
	 */
	public void insert(PageData pd)throws Exception;
	/**添加
	 * @param pd包含角色相关信息
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception;
	
	/**保存修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;
	
	/**删除角色
	 * @param ROLE_ID
	 * @throws Exception
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception;
	
	/**给当前角色附加菜单权限
	 * @param role
	 * @throws Exception
	 */
	public void updateRoleRights(Role role) throws Exception;
	
	/**通过id查找
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Role getRoleById(String ROLE_ID) throws Exception;
	
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
	/**通过角色id获取角色名称
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRoleName(PageData pd) throws Exception;
}
