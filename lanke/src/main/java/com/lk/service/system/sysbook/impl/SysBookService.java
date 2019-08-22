package com.lk.service.system.sysbook.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.sysbook.SysBookManager;

/** 
 * 说明： 订座管理
 * 创建人：洪智鹏
 * 创建时间：2016-10-08
 * @version
 */
@Service("sysbookService")
public class SysBookService implements SysBookManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd订座管理相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysBookMapper.save", pd);
	}
	
	/**删除
	 * @param pd订座管理相关信息的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysBookMapper.delete", pd);
	}
	
	/**修改
	 * @param pd订座管理相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysBookMapper.edit", pd);
	}
	
	/**列表
	 * @param page订座查询列表页面和检索字段
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysBookMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd无，查询全部
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysBookMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd通过主键获取订座信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysBookMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS订座管理相关信息主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysBookMapper.deleteAll", ArrayDATA_IDS);
	}
	/**修改状态字段来确定是否订座
	 * @param pd 包含主键和状态字段等信息
	 * @throws Exception
	 */
	public void updateByState(PageData pd) throws Exception {
		dao.update("SysBookMapper.updateByState", pd);
		
	}
	
}

