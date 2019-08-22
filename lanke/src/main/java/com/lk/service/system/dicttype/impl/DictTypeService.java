package com.lk.service.system.dicttype.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.dicttype.DictTypeManager;

/** 
 * 说明： 字典大表
 * 创建人：洪智鹏
 * 创建时间：2016-10-12
 * @version
 */
@Service("dicttypeService")
public class DictTypeService implements DictTypeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd包含大表需要的信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DictTypeMapper.save", pd);
	}
	
	/**删除
	 * @param pda删除字典大表包含表主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DictTypeMapper.delete", pd);
	}
	
	/**修改
	 * @param  pd包含大表需要的信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DictTypeMapper.edit", pd);
	}
	
	/**列表
	 * @param page 包含页面检索字段
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DictTypeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DictTypeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @parampd主键id
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DictTypeMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS批量删除字典大表信息表主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DictTypeMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

