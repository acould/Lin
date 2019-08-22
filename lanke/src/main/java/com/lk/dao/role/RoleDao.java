package com.lk.dao.role;


import java.util.List;

import javax.management.relation.RoleInfo;

import com.lk.dao.GenericDao;
import com.lk.dao.MyBatisDao;
import com.lk.entity.system.Role;
import com.lk.entity.system.RoleMenuInfo;



/**
 * 
 * Datetime   ： 2016年1月13日 下午4:19:10<br>
 * Title      :  RoleDao.java<br>
 * Description:   角色DAO层<br>
 * Company    :  hiwan<br>
 * @author cbj
 *
 */
@MyBatisDao
public interface RoleDao extends GenericDao<Role, String> {

	/**
	 * 获取角色对应菜单
	 */
	public List<RoleMenuInfo> selectRoleMenuInfo(String roleId);

	/**
	 * 删除角色菜单表中对应角色的菜单
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteRoleMenu(String roleId);

	/**
	 * 插入角色对应菜单
	 * 
	 * @param roleMenuInfo
	 * @return
	 */
	public int insertRoleMenu(RoleMenuInfo roleMenuInfo);

	public Role selectNewRole(String createUser);

	/**
	 * 获取同名角色的条数
	 * 
	 * @param roleInfo
	 * @return
	 */
	public Role selectRoleCount(RoleInfo roleInfo);
}
