package com.lk.service.internet.group.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lk.controller.base.BaseController;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Tools;
import com.lk.service.internet.group.GroupManager;
import com.lk.service.internet.groupstore.GroupStoreManager;
import com.lk.service.internet.team.TeamManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;

/** 
 * 说明： 组局功能
 * 创建人：洪智鹏
 * 创建时间：2017-10-19
 * @version
 */
@Service("groupService")
public class GroupService extends BaseController implements GroupManager{
	//STATE状态信息
	public static final String STATE1 = "fabu";
	public static final String STATE2 = "baocun";

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="groupService")
	private GroupManager groupService;
	@Resource(name="groupstoreService")
	private GroupStoreManager groupstoreService;
	@Resource(name="teamService")
	private TeamManager teamService;
	

	/**新增 组局功能接
	 * @param pd里面包含组局相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("GroupMapper.save", pd);
	}
	
	/**删除 组局功能接
	 * @param pd组局表主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("GroupMapper.delete", pd);
	}
	
	/**修改
	 * @param pd里面包含比赛相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("GroupMapper.edit", pd);
	}
	
	/**列表
	 * @param page检索字段等信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GroupMapper.datalistPage", page);
	}
	
	
	/**列表(全部)
	 * @param pd无信息查询全部
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GroupMapper.listAll", pd);
	}
	
	/**
	 * group or store列表
	 * @param pd 网吧id
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listGroupAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GroupMapper.listGroupAll", pd);
	}
	/**通过id获取数据
	 * @param pd组局表主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GroupMapper.findById", pd);
	}
	
	/**
	 * 通过GROUP-id获取数据
	 * @param pd组局表主键
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByGroupId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GroupMapper.listByGroupId", pd);
	}
	
	/**
	 * 通过获取组局功能选择的分店(改组局功能能在哪些分店开组局)
	 * @param storeList store网吧分店id主键数组
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> storeList(String[] storeList)throws Exception{
		return (List<PageData>)dao.findForList("GroupMapper.storeList", storeList);
	}
	/**批量删除
	 * @param ArrayDATA_IDS主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("GroupMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 保存service
	 * @param pd里面包含组局相关信息
	 * @return
	 * @throws Exception
	 */
	public PageData groupSave(PageData pagedata)throws Exception{
		PageData pdpd = new PageData(); //返回PageData
		User user = (User)pagedata.get("user");
		PageData pd = (PageData)pagedata.get("pd");
		
		pd.put("GROUP_ID", this.get32UUID());	//主键
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("CREATE_USER", user.getUSER_ID());	//创建用户
		pd.put("INTERNET_ID", user.getINTENET_ID());	//所属网吧
		String[] storeList = this.getRequest().getParameterValues("STORE_ID");
		int f=Integer.parseInt(pd.getString("TEAM_NUMBER"));
		if(pd.getString("STATE").equals(STATE1)){
			List<PageData> list = groupService.storeList(storeList);
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			long from;
			long to;
			int hours;
			for (PageData pageData : list) {
				from = simpleFormat.parse(pageData.getString("BEGIN_TIME")).getTime();
				to = simpleFormat.parse(pd.getString("BEGIN_TIME")).getTime();
				hours = (int) ((to - from)/(1000 * 60 * 60));
				if(hours <=1 && hours>=-1){
					pdpd.put("msg", PublicManagerUtil.FAILED);
					pdpd.put("result", "当前赛事跟选用门店赛事有时间冲突！");
					return pdpd;
				}
			}
		}
		PageData pdStore = new PageData();
		for (int i = 0; i < storeList.length; i++) {
			pdStore.put("GROUPSTORE_ID", this.get32UUID());	
			pdStore.put("GROUP_ID", pd.getString("GROUP_ID"));
			pdStore.put("STORE_ID", storeList[i]);
			groupstoreService.save(pdStore);
			for(int j=1;j<=f;j++){
				PageData pdgroupTeam = new PageData();
				pdgroupTeam.put("TEAM_ID", this.get32UUID());	//主键
				pdgroupTeam.put("GROUP_ID",  pd.getString("GROUP_ID"));	//组局id
				pdgroupTeam.put("STORE_ID", storeList[i]);	//门店id
				pdgroupTeam.put("TEAM_NAME", "战队"+j);	//战队名称
				teamService.save(pdgroupTeam);
			}
		}
		if(pd.getString("STATE").equals(STATE1)){
			pd.put("STATE", "2");//保存为1，发布为2
		}else if(pd.getString("STATE").equals(STATE2)){
			pd.put("STATE", "1");//保存为1，发布为2
		}
		groupService.save(pd);
		
		pdpd.put("msg", PublicManagerUtil.SUCCESS);
		return pdpd;
	}
	
	/**
	 * 修改service
	 * @param pagedata里面包含组局相关信息
	 * @return
	 * @throws Exception
	 */
	public PageData groupEdit(PageData pagedata)throws Exception{
		PageData pdpd = new PageData();		//返回PageData
		PageData pd = (PageData)pagedata.get("pd");
		HttpServletRequest request = (HttpServletRequest)pagedata.get("request");
		if(pd.getString("STATE").equals(STATE2)){
			pd.remove("STATE");
		}
		String[] storeList = request.getParameterValues("STORE_ID");
		int f=Integer.parseInt(pd.getString("TEAM_NUMBER"));
		//先删除组局和门店关系
		groupstoreService.deleteByGroupId(pd);
		//再删除门店跟战队关系
		teamService.deleteByGroupId(pd);
		//更换适用的门店
		PageData pdStore = new PageData();
		for (int i = 0; i < storeList.length; i++) {
			pdStore.put("GROUPSTORE_ID", this.get32UUID());	
			pdStore.put("GROUP_ID", pd.getString("GROUP_ID"));
			pdStore.put("STORE_ID", storeList[i]);
			groupstoreService.save(pdStore);
			for(int j=1;j<=f;j++){
				PageData pdgroupTeam = new PageData();
				pdgroupTeam.put("TEAM_ID", this.get32UUID());	//主键
				pdgroupTeam.put("GROUP_ID",  pd.getString("GROUP_ID"));	//组局id
				pdgroupTeam.put("STORE_ID", storeList[i]);	//门店id
				pdgroupTeam.put("TEAM_NAME", "战队"+j);	//战队名称
				teamService.save(pdgroupTeam);
			}
		}
		groupService.edit(pd);
		pdpd.put("msg", PublicManagerUtil.SUCCESS);
		return pdpd;
	}
	
}

