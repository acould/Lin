package com.lk.service.system.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.Role;
import com.lk.service.system.role.RoleManager;
import com.lk.util.PageData;

/**
 * 角色
 * 
 * @author  修改日期：2015.11.6
 */
@Service("roleService")
public class RoleService implements RoleManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 查询角色
	 * 通过page查询该人员的角色
	 * @param page 关键词--keywords门店id--STORE_ID,网吧id--internetId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.list", page);
	}

	/**
	 * 列出此组下级角色
	 * @param pd 包含角色表主键(ROLE_ID--角色id)
	 * @return 返回满足条件的角色
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesByPId", pd);
	}

	/**
	 * 通过id查找
	 * @param pd包含主键信息(必填:角色id--ROLE_ID)
	 * @return 满足条件的角色
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findObjectById", pd);
	}

	/**
	 * 添加
	 * 
	 * @param pd包含角色相关信息
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		dao.save("RoleMapper.insert", pd);
	}

	/**
	 * 保存修改
	 * 
	 * @param pd包含角色相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("RoleMapper.edit", pd);
	}

	/**
	 * 删除角色
	 * 
	 * @param ROLE_ID
	 * @throws Exception
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteRoleById", ROLE_ID);
	}

	/**
	 * 给当前角色附加菜单权限
	 * 
	 * @param role角色字段信息
	 * @throws Exception
	 */
	public void updateRoleRights(Role role) throws Exception {
		dao.update("RoleMapper.updateRoleRights", role);
	}

	/**
	 * 通过id查找
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Role getRoleById(String ROLE_ID) throws Exception {
		return (Role) dao.findForObject("RoleMapper.getRoleById", ROLE_ID);
	}

	/**
	 * 给全部子角色加菜单权限
	 * 
	 * @param pd包含权限和roleid
	 * @throws Exception
	 */
	public void setAllRights(PageData pd) throws Exception {
		dao.update("RoleMapper.setAllRights", pd);
	}

	/**
	 * 权限(增删改查)
	 * 
	 * @param msg
	 *            区分增删改查
	 * @param pd
	 * @throws Exception
	 */
	public void saveB4Button(String msg, PageData pd) throws Exception {
		dao.update("RoleMapper." + msg, pd);
	}

	/**
	 * 通过网吧id查询改网吧拥有的角色
	 * @param pd 网吧id--internetId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByIntenet(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listByIntenet", pd);
	}

	/**新增
	 * @param pd包含角色相关信息
	 * @throws Exception
	 */
	public void insert(PageData pd) throws Exception {
		dao.save("RoleMapper.insert", pd);

	}

	/**通过角色id获取角色名称
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getRoleName(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.getRoleName", pd);
	}

}
