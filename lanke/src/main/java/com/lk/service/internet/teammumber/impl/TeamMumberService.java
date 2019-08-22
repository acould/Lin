package com.lk.service.internet.teammumber.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.internet.teammumber.TeamMumberManager;

/** 
 * 说明： TeamMumber
 * 创建人：洪智鹏
 * 创建时间：2017-10-19
 * @version
 */
@Service("teammumberService")
public class TeamMumberService implements TeamMumberManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param  pd包含新增组队成员的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TeamMumberMapper.save", pd);
	}
	
	/**删除
	 * @param pd当中包含组队成员表的主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TeamMumberMapper.delete", pd);
	}
	
	/**修改
	 * @param pd包含新增组队成员的相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TeamMumberMapper.edit", pd);
	}
	
	/**列表
	 * @param page包含页面检索字段
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TeamMumberMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TeamMumberMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd当中包含组队成员表的主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TeamMumberMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS组局功能团队报名成员信息表主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TeamMumberMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

