package com.lk.service.internet.team.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.internet.team.TeamManager;

/** 
 * 说明： Team
 * 创建人：洪智鹏
 * 创建时间：2017-10-19
 * @version
 */
@Service("teamService")
public class TeamService implements TeamManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd组局功能的组队参加相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TeamMapper.save", pd);
	}
	
	/**删除
	 * @param pd组局功能的组队表主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TeamMapper.delete", pd);
	}
	
	/**GROUP_ID 删除
	 * @param pd 组局功能主键
	 * @throws Exception
	 */
	public void deleteByGroupId(PageData pd)throws Exception{
		dao.delete("TeamMapper.deleteByGroupId", pd);
	}
	
	/**修改
	 * @param pd组局功能的组队参加相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TeamMapper.edit", pd);
	}
	

	/**列表
	 * @param page页面检索信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TeamMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TeamMapper.listAll", pd);
	}
	
	/**列表(GroupidTeam)
	 * @param pd组局功能主键
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listGroupidTeam(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TeamMapper.listGroupidTeam", pd);
	}
	

	/**通过id获取数据
	 * @param pd组局功能的组队表主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TeamMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS组局功能的组队表主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TeamMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

