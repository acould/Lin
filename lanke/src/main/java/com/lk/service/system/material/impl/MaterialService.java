package com.lk.service.system.material.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.material.MaterialManager;

/** 
 * 说明： 素材管理
 * 创建人：洪智鹏
 * 创建时间：2017-06-05
 * @version
 */
@Service("materialService")
public class MaterialService implements MaterialManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd包含素材管理相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MaterialMapper.save", pd);
	}
	
	/**删除
	 * @param  pd删除素材管理包含主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MaterialMapper.delete", pd);
	}
	
	/**修改
	 * @param pd包含素材管理相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("MaterialMapper.edit", pd);
	}
	
	/**列表
	 * @param page包含页面和检索字段信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MaterialMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MaterialMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd包含主键字段信息，根据主键查询数据
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MaterialMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS批量删除信息包含主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MaterialMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

