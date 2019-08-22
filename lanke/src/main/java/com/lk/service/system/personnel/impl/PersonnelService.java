package com.lk.service.system.personnel.impl;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.system.Role;
import com.lk.service.system.personnel.PersonnelManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.RightsHelper;
import com.lk.util.Tools;

/**
 * 角色管理
 * @author Administrator
 *
 */
@Service("personnelService")
public class PersonnelService implements PersonnelManager {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="personnelService")
	private PersonnelService personnelService;
	
	/**
	 * 列出此组下的角色
	 * @param (必填:网吧id--INTENET_ID;选填:角色id--ROLE_ID)
	 * @return 此组下的角色
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("PersonnelMapper.listAllRolesByPId", pd);
	}
	
	/**
	 * 保存新增角色
	 * @param (必填:RIGHTS--菜单权限,ROLE_ID--角色id,ROLE_NAME--角色id,ADD_QX--新增权限,DEL_QX--删除权限,EDIT_QX--修改权限,CHA_QX--查看权限,INTENET_ID--网吧id)
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		dao.save("PersonnelMapper.insert", pd);
	}
	
	/**
	 * 通过id查找角色信息
	 * @param (必填:角色id--ROLE_ID)
	 * @return 该角色信息
	 * @throws Exception
	 */
	public Role getRoleById(String ROLE_ID) throws Exception {
		return (Role) dao.findForObject("PersonnelMapper.getRoleById", ROLE_ID);
	}

	/**给当前角色附加菜单权限
	 * @param role
	 * @throws Exception
	 */
	public void updateRoleRights(Role role) throws Exception {
		dao.update("PersonnelMapper.updateRoleRights", role);
	}
	
	/**给全部子角色加菜单权限
	 * @param pd
	 * @throws Exception
	 */
	public void setAllRights(PageData pd) throws Exception {
		dao.update("PersonnelMapper.setAllRights", pd);
	}
	
	/**权限(增删改查)
	 * @param msg 区分增删改查
	 * @param pd
	 * @throws Exception
	 */
	public void saveB4Button(String msg,PageData pd) throws Exception {
		dao.update("PersonnelMapper."+msg, pd);
	}
	
	/**
	 * 通过id查找角色信息
	 * @param pd(必填:角色id--ROLE_ID)
	 * @return 满足条件的角色信息
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PersonnelMapper.findObjectById", pd);
	}
	
	/**
	 * 保存角色修改信息(通过角色id修改修改权限)
	 * @param pd(必填:角色id--ROLE_ID,修改权限--EDIT_QX)
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("PersonnelMapper.edit", pd);
	}
	
	/**
	 * 删除角色
	 * 通过角色id删除该角色信息
	 * @param (必填:角色id--ROLE_ID)
	 * @throws Exception
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("PersonnelMapper.deleteRoleById", ROLE_ID);
	}
	
	/**
	 * 通过INTENET_ID和ROLE_NAME判断角色名是否重复
	 * @param pd(必填:网吧Id--INTENET_ID,角色名称--ROLE_NAME)
	 * @return 满足条件的角色
	 * @throws Exception
	 */
	public PageData getRoleByName (PageData pd) throws Exception{
		return (PageData)dao.findForObject("PersonnelMapper.getRoleByName", pd);
	}
	
	/**
	 * Save Service
	 * 保存角色的权限
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData personnelSave(PageData pageData)throws Exception{
		PageData pdpd = new PageData();		//返回PageData
		String ROLE_ID = pageData.getString("ROLE_ID");
		String menuIds = pageData.getString("menuIds");
		String menuIdsAdd = pageData.getString("menuIdsAdd");
		String menuIdsDel = pageData.getString("menuIdsDel");
		String menuIdsEdit = pageData.getString("menuIdsEdit");
		String menuIdsCha = pageData.getString("menuIdsCha");
		PageData getPageData = (PageData)pageData.get("getPageData");
		try{
			PageData pd = new PageData();
			/*菜单权限*/
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds)); //用菜单ID做权处理
				Role role = personnelService.getRoleById(ROLE_ID);						 //通过id获取角色对象
				role.setRIGHTS(rights.toString());
				personnelService.updateRoleRights(role);								 //更新当前角色菜单权限
				pd.put("rights",rights.toString());
			}else{
				/*获取当前点击用户的菜单权限*/
				Role role = new Role();
				role.setRIGHTS("");
				role.setROLE_ID(ROLE_ID);
				personnelService.updateRoleRights(role);				//更新当前角色菜单权限(没有任何勾选)
				pd.put("rights","");
			}
				pd.put("ROLE_ID", ROLE_ID);
				personnelService.setAllRights(pd);					//更新此角色所有子角色的菜单权限
				
				/*增*/
				PageData pdAdd = getPageData;
				if(null != menuIdsAdd && !"".equals(menuIdsAdd.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIdsAdd));
					pdAdd.put("value",rights.toString());
				}else{
					pdAdd.put("value","");
				}
				pdAdd.put("ROLE_ID", ROLE_ID);
				String msgAdd = "add_qx";
				personnelService.saveB4Button(msgAdd,pdAdd);
				
				/*删*/
				PageData pdDel = getPageData;
				if(null != menuIdsDel && !"".equals(menuIdsDel.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIdsDel));
					pdDel.put("value",rights.toString());
				}else{
					pdDel.put("value","");
				}
				pdDel.put("ROLE_ID", ROLE_ID);
				String msgDel = "del_qx";
				personnelService.saveB4Button(msgDel,pdDel);
				
				/*改*/
				PageData pdEdit = getPageData;
				if(null != menuIdsEdit && !"".equals(menuIdsEdit.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIdsEdit));
					pdEdit.put("value",rights.toString());
				}else{
					pdEdit.put("value","");
				}
				pdEdit.put("ROLE_ID", ROLE_ID);
				String msgEdit = "edit_qx";
				personnelService.saveB4Button(msgEdit,pdEdit);
				
				/*查*/
				PageData pdCha = getPageData;
				if(null != menuIdsCha && !"".equals(menuIdsCha.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIdsCha));
					pdCha.put("value",rights.toString());
				}else{
					pdCha.put("value","");
				}
				pdCha.put("ROLE_ID", ROLE_ID);
				String msgCha = "cha_qx";
				personnelService.saveB4Button(msgCha,pdCha);
				
				pdpd.put("msg", PublicManagerUtil.SUCCESS);
		} catch(Exception e){
			pdpd.put("msg", PublicManagerUtil.FAILED);
		}
		return pdpd;
	}
	
}
