package com.lk.service.internet.permission.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.internet.permission.PermissionManager;

/** 
 * 说明： 公众号权限集合
 * 创建人：洪智鹏
 * 创建时间：2017-08-04
 * @version
 */
@Service("permissionService")
public class PermissionService implements PermissionManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增公众号权限集合
	 * @param pd  公众号权限集合相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PermissionMapper.save", pd);
	}
	

	/**根据APP_ID删除
	 * @param pd包含appid信息 微信公众号的appid
	 * @throws Exception
	 */
	public void deleteApp(PageData pd)throws Exception{
		dao.delete("PermissionMapper.deleteApp", pd);
	}
	
	/**根据PERMISSION_ID删除
	 * @param pd包含主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PermissionMapper.delete", pd);
	}
	
	
	
	/**修改
	 * @param pd公众号权限集合相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PermissionMapper.edit", pd);
	}
	
	/**列表
	 * @param page包含页面和检索信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PermissionMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd无
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PermissionMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd包含主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PermissionMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PermissionMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 通过appid获取数据
	 * @param pd 微信id--appid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByAppId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PermissionMapper.findByAppId", pd);
	}
	
}

