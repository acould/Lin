package com.lk.service.system.oldwith.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.oldwith.OldwithManager;

/** 
 * 说明： 老带新管理
 * 创建人：洪智鹏
 * 创建时间：2017-03-20
 * @version
 */
@Service("oldwithService")
public class OldwithService implements OldwithManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd包含老带新相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OldwithMapper.save", pd);
	}
	
	/**删除
	 * @param pd包含老带新的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OldwithMapper.delete", pd);
	}
	
	/**修改
	 * @param pd包含老带新相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OldwithMapper.edit", pd);
	}
	
	/**列表
	 * @param page包含页面部分信息和检索字段
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OldwithMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd无，查询全部信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OldwithMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd包含老带新相关信息的主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OldwithMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS包含老带新相关信息的主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OldwithMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

