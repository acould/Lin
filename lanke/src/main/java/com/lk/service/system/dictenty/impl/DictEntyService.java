package com.lk.service.system.dictenty.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.dictenty.DictEntyManager;

/** 
 * 说明： 字典内容表
 * 创建人：洪智鹏
 * 创建时间：2016-10-12
 * @version
 */
@Service("dictentyService")
public class DictEntyService implements DictEntyManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd 包含字典新增信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DictEntyMapper.save", pd);
	}
	
	/**删除
	 * @param pd 包含主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DictEntyMapper.delete", pd);
	}
	
	/**修改
	 * @param pd 包含修改字段信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DictEntyMapper.edit", pd);
	}
	
	/**列表
	 * @param page 展示字典列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DictEntyMapper.datalistPage", page);
	}
	
	/**
	 * 列表(全部)
	 * 通过优惠劵类型查询信息
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DictEntyMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd 包含主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DictEntyMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS 表主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DictEntyMapper.deleteAll", ArrayDATA_IDS);
	}
	/** 根据类型查询字典包含的信息
	 * @param PageData   字典码和类型
	 * @throw
	 */
	public PageData findBySc(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DictEntyMapper.findBySc", pd);
	}
}

