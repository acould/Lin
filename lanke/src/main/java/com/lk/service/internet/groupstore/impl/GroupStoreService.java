package com.lk.service.internet.groupstore.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.internet.groupstore.GroupStoreManager;

/** 
 * 说明： 组局和分店关系表
 * 创建人：洪智鹏
 * 创建时间：2017-10-19
 * @version
 */
@Service("groupstoreService")
public class GroupStoreService implements GroupStoreManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增 组局和分店关系表
	 * @param pd组局和分店关系表相关数据
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("GroupStoreMapper.save", pd);
	}
	
	/**删除 组局和分店关系表
	 * @param pd 包含主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("GroupStoreMapper.delete", pd);
	}
	
	/**修改
	 * @param pd包含组局和分店关系表相关数据
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("GroupStoreMapper.edit", pd);
	}
	
	/**列表
	 * @param page包含检索字段
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GroupStoreMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GroupStoreMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd包含主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GroupStoreMapper.findById", pd);
	}
	
	/**通过GROUP_ID获取数据
	 * @param pd 组局主键来
	 * @throws Exception
	 */
	public List<PageData> listStore(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GroupStoreMapper.listStore", pd);
	}
	/**批量删除
	 * @param ArrayDATA_IDS组局和分店关系表主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("GroupStoreMapper.deleteAll", ArrayDATA_IDS);
	}
	/**通过GROUP_ID删除关系表
	 * @param pd 组局主键来
	 * @throws Exception
	 */
	public void deleteByGroupId(PageData pd)throws Exception{
		dao.delete("GroupStoreMapper.deleteByGroupId", pd);
	}
	
}

