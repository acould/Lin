package com.lk.service.system.store.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lk.communicate.server.tcp.ChannelCache;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.cardStore.CardStoreManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

import net.sf.json.JSONObject;

/**
 * 说明： 门店管理 (业务层)
 * 创建人：洪智鹏 
 * 创建时间：2017-02-27
 * 
 * @version
 */
@Service("storeService")
public class StoreService extends BaseController implements StoreManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "cardService")
	private CardManager cardService;
	@Resource(name = "cardStoreService")
	private CardStoreManager cardStoreService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;

	/**
	 * 筛选列表(全部)
	 * 通过page查询该角色关联门店
	 * @param page 关键词--keywords门店id--STORE_ID,网吧id--internetId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUU(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listUU", page);
	}

	/**
	 * 新增门店
	 * 保存门店同时, 同时保存到门店,门店加V,门店标签表中
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("StoreMapper.save", pd);        //保存门店信息到store表
		dao.save("StoreMapper.saveaddv", pd);    //保存门店基础信息到加V表
		dao.save("StoreMapper.saveaddTips", pd); //保存门店会员标签信息至tips表
	}
	
	/**
	 * 新增门店
	 * 保存门店同时, 同时保存到门店,门店加V,门店标签表中
	 * @param pd
	 * @throws Exception
	 */
	public void save1(PageData pd, HttpServletRequest request) throws Exception {
		pd.put("PROVINCE", request.getParameter("s_province"));  // 省
		pd.put("CITY", request.getParameter("s_city"));          // 市
		pd.put("COUNTY", request.getParameter("s_county"));      // 区
		pd.put("ADDRESS", request.getParameter("ADDRESS"));      // 详细地址
		pd.put("TELEPHONE", request.getParameter("TELEPHONE"));  // 门店电话
		pd.put("STORE_ID", this.get32UUID());                    // 主键(门店id)
		pd.put("INSERT_TIME", Tools.date2Str(new Date()));       // 新增时间
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));       // 修改时间
		
		dao.save("StoreMapper.save", pd);        //保存门店信息到store表
		dao.save("StoreMapper.saveaddv", pd);    //保存门店基础信息到加V表
		dao.save("StoreMapper.saveaddTips", pd); //保存门店会员标签信息至tips表
	}

	/**
	 * 删除
	 * 通过门店id删除门店信息
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public JSONObject delete(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.delete("StoreMapper.delete", pd);
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "删除失败!");
		}
		return json;
	}

	/**
	 * 门店管理(修改)
	 * 保存修改后的门店信息
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("StoreMapper.edit", pd);
	}

	/**
	 * 列表(门店展示)
	 * 查询并展示门店信息
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listshow(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listshow", page);
	}

	/**
	 * 列表(门店管理用--老板)
	 * 查询该用户关联所有门店并展示
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.datalistPage", page);
	}

	/**
	 * 列表(门店管理用--员工)
	 * 查询该用户关联所有门店并展示
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> sList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.sList", page);
	}

	/**
	 * 门店管理列表
	 * 通过网吧id/门店id,关键字keywords去查询用户相关门店信息
	 * @param page--网吧id/门店id,关键字keywords
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> storeList(Page page, PageData pd) throws Exception {
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String PROVINCE = pd.getString("province");
		if (StringUtil.isNotEmpty(PROVINCE)) {
			if (PROVINCE.equals("北京") || PROVINCE.equals("天津") || PROVINCE.equals("上海") || PROVINCE.equals("台湾")
					|| PROVINCE.equals("重庆") || PROVINCE.equals("香港") || PROVINCE.equals("澳门")) {
				pd.put("province", pd.getString("province"));
				pd.put("county", pd.getString("city"));
				pd.put("city", pd.getString("province"));
			}
		}
		List<PageData> varList = (List<PageData>) dao.findForList("StoreMapper.datalistPages", page);
		return varList;
	}

	/**
	 * 列表(全部)
	 * 查询全部门店信息
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listAll", pd);
	}

	/**
	 * 门店管理(修改)
	 * 通过门店id获取门店信息
	 * @param pd--门店id
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findById", pd);
	}

	/**
	 * 去查看页面
	 * 获取绑定的门店信息
	 * @param pd--门店id
	 * @throws Exception
	 */
	public PageData findByVId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findByVId", pd);
	}
	public PageData findStoreVById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findStoreVById", pd);
	}



	/**
	 * 批量删除
	 * 通过门店id批量删除相关门店信息
	 * @param ArrayDATA_IDS
	 * @return 
	 * @throws Exception
	 */
	public JSONObject deleteAll(String[] ArrayDATA_IDS) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.delete("StoreMapper.deleteAll", ArrayDATA_IDS);
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "删除失败！");
		}
		return json;
	}

	/**
	 * 通过网吧id查询现有门店(没有被禁用的)
	 * @param pd 网吧id--internetId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByIntenet(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listByIntenet", pd);
	}

	/**
	 * 根据STORE_ID获取门店数据(查询自己所在的分店)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByStore(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listByStore", pd);
	}

	/**
	 * 通过用户id去查询相关门店信息
	 * @param pd 用户id--user_id
	 * @throws Exception
	 */
	public PageData findByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findByUserId", pd);
	}

	/**
	 * 通过USER_ID和INTENET_ID获取数据
	 * @param pdStore
	 * @return
	 * @throws Exception
	 */
	public PageData findByStore(PageData pdStore) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findByStore", pdStore);
	}

	/**
	 * 通过name去判断是否存在
	 * @param pdStore--门店名称
	 * @throws Exception
	 */
	public PageData findByStoreName(PageData pdStore) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findByStoreName", pdStore);
	}

	/**
	 * 判断门店是否存在(修改)
	 * 通过STORE_NAME和INTENET_ID判断门店是否存在
	 * @param pdStore
	 * @throws Exception
	 */
	public PageData findByStoreName_Intenet(PageData pdStore) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findByStoreName_Intenet", pdStore);
	}
	
	/**
	 * 判断门店是否存在(修改)
	 * 通过STORE_NAME和INTENET_ID判断门店是否存在
	 * @param pd
	 * @throws Exception
	 */
	public JSONObject findByStoreName_Intenet1(PageData pd, String STATE, String STORE_ID) throws Exception {
		JSONObject json = new JSONObject();
		if ((PageData) dao.findForObject("StoreMapper.findByStoreName_Intenet", pd) != null) {// 通过网吧id和门店名称查到重复的
			if (STATE.equals("1")) {// 新增
				json.put("result", PublicManagerUtil.ERROR);
				json.put("message", "该门店已存在");
			} else if (STATE.equals("2")) {// 修改
				pd.put("STORE_ID", STORE_ID);
				List<PageData> list = this.findByStoreId(pd); // 通过门店id去查原来的门店名称
				if (pd.get("STORE_NAME").equals(list.get(0).get("STORE_NAME"))) {// 如果为原来的名字
					json.put("result", PublicManagerUtil.SUCCESS);
				} else {// 已存在
					json.put("result", PublicManagerUtil.ERROR);
					json.put("message", "该门店已存在");
				}
			}
		} else {// 不存在
			json.put("result", PublicManagerUtil.SUCCESS);
		}
		return json;
	}

	/**
	 * 通过门店id去修改门店名称
	 * @param pd--门店id,store_name--门店名称
	 * @throws Exception
	 */
	public void editStore(PageData pd) throws Exception {
		dao.update("StoreMapper.editStore", pd);
	}

	/**
	 * 修改门店状态(禁用/启用)
	 * 通过不同操作修改门店状态
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public JSONObject statebt(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.update("StoreMapper.statebt", pd);
			json.put("result", PublicManagerUtil.TRUE);
			if (pd.getString("STATE").equals("2")) {
				json.put("message", "禁用成功！");
			} else if (pd.getString("STATE").equals("1")) {
				json.put("message", "启用成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FAIL);
			json.put("message", "系统繁忙,请稍后再试!");
		}
		return json;
	}

	/**
	 * 新增门店
	 * @param
	 * @throws Exception
	 */
	public void saveStore(PageData pd) throws Exception {
		dao.update("StoreMapper.saveStore", pd);
	}

	/**
	 * 通过卡劵id获取信息
	 * @param pdStore 卡劵id--cardId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByCardId(PageData pdStore) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listByCardId", pdStore);
	}

	/**
	 * 通过网吧id和状态查询相关信息
	 * @param pd 网吧id--INTERNET_ID
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findCityByInternetId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.findCityByInternetId", pd);
	}

	/**
	 * 通过网吧ID和城市获取数据
	 * @param pd 网吧id--INTERNET_ID,城市--CITY
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findAreaByCity(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.findAreaByCity", pd);
	}
	
	/**
	 * 通过网吧ID和城市和区域获取数据
	 * @param pd 网吧id--INTERNET_ID,城市--CITY,区域--COUNTY
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findStoreByArea(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.findStoreByArea", pd);
	}

	/**
	 * StoreSave Service
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData StoreSave(PageData pageData) throws Exception {
		PageData pdpd = new PageData(); // 返回PageData
		User user = (User) pageData.get("user");
		//HttpServletRequest request = (HttpServletRequest) pageData.get("request");
		PageData pd = (PageData) pageData.get("pd");
		String STORE_ID = pageData.getString("STORE_ID");
		pd.put("STORE_ID", STORE_ID); // 主键
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeNow = dateFm.format(new Date());
		pd.put("UPDATE_TIME", timeNow);
		if (storeService.findByStoreName(pd) == null) {
			pd.put("INTERNET_ID", user.getINTENET_ID()); // 网吧id
			pd.put("USER_ID", this.get32UUID());
			// 新增门店的时候把通用赛事插入（同步）
			PageData pdInt = new PageData();
			pdInt.put("internetId", user.getINTENET_ID());
			List<PageData> slist = storeService.listByIntenet(pdInt);

			if (slist.size() != 0) {


				PageData pdCard = new PageData();
				pdCard.put("INTERNET_ID", user.getINTENET_ID());
				List<PageData> cardList = cardService.listByInternet(pdCard);
				for (PageData card : cardList) {
					if (card.getString("IS_ALL").equals("1")) {// 通用的时候，同步卡券
						PageData pdStore = new PageData();
						pdStore.put("CARD_STORE_ID", this.get32UUID());
						pdStore.put("CARD_ID", card.get("CARD_ID"));
						pdStore.put("STORE_ID", pd.getString("STORE_ID"));
						cardStoreService.save(pdStore);
					}
				}
			}
			storeService.save(pd);// 新增门店(新增门店的同时加入加V表)
			pdpd.put("msg", PublicManagerUtil.SUCCESS);
		} else {
			pdpd.put("msg", PublicManagerUtil.FAILED);
		}
		return pdpd;
	}

	/**
	 * 门店详情展示
	 * 去查看该门店的详情
	 * @param pd(必填:门店id--STORE_ID)
	 * @return 门店详情信息(STORE_NAME--门店名称,PROVINCE--省,CITY--市,COUNTY--区,ADDRESS--详细地址,TELEPHONE--门店电话,STATE--加V状态,EXPIRATION_TIME--到期时间)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> storeDetails(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.storeDetails", pd);
	}

	/**
	 * 停用指定门店
	 * 改变指定门店的状态
	 * @param pd(必填:门店id--STORE_ID)
	 * @return 
	 * @throws Exception
	 */
	public JSONObject disableUsers(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.update("StoreMapper.disableUsers", pd);
			json.put("result", PublicManagerUtil.SUCCESS);
			json.put("message", "停用成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.ERR);
			json.put("message", "系统繁忙中,请稍后再试");
		}
		return json;
	}

	/**
	 * 启用指定门店
	 * 改变指定门店的状态
	 * @param pd(必填:门店id--STORE_ID)
	 * @throws Exception
	 */
	public JSONObject enableUser(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.update("StoreMapper.enableUser", pd);
			json.put("result", PublicManagerUtil.SUCCESS);
			json.put("message", "停用成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.ERR);
			json.put("message", "系统繁忙中,请稍后再试");
		}
		return json;
	}

	/**
	 * 门店特权列表
	 * 通过用户状态,网吧id,门店id,关键词等去查询相关门店信息
	 * @param page(必填:网吧id--internetId,门店id--STORE_ID;选填:关键词--keywords,用户状态--STATE)
	 * @return 该用户旗下门店信息(STORE_NAME--门店名称,STATE--加V状态,STORE_ID--门店状态)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listv(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listPagev", page);
	}
	
	/**
	 * 门店特权列表
	 * 通过条件等去查询相关门店信息
	 * @param (pd--封装信息,user--缓存用户信息,page)
	 * @return 该用户旗下门店信息(STORE_NAME--门店名称,STATE--加V状态,STORE_ID--门店状态)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listv(PageData pd) throws Exception {
		//关键词检索
		User user = (User) pd.get("user");
		Page page = (Page) pd.get("page");
		pd.put("internet_id", user.getINTENET_ID());
		
		//判断网吧管理员还是门店管理员的角色（根据其用户角色）
		if (!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)) {  
			List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());
			pd.put("STORE_ID", Joiner.on("','").join(storeid));
		}
		page.setPd(pd);

//		List<PageData> list = (List<PageData>) dao.findForList("StoreMapper.listPagev", page);//连接银联支付的查询
		List<PageData> list = (List<PageData>) dao.findForList("StoreMapper.listPagev2", page);//连接嘉联支付的查询

		return list;
	}

	/**
	 * 门店特权列表(员工)
	 * @param page
	 * @throws Exception
	 */
	/*
	 * @SuppressWarnings("unchecked") public List<PageData> sListv(Page page) throws
	 * Exception { return (List<PageData>)dao.findForList("StoreMapper.sListv",
	 * page); }
	 */

	/**
	 * 保存加V信息
	 * 通过门店id去保存相关加V信息
	 * @param pd 门店相关加V信息
	 * @throws Exception
	 */
	public void saveV(PageData pd) throws Exception {
		dao.save("StoreMapper.saveV", pd);
	}

	/**
	 * 查询指定门店加V信息(审核)
	 * 通过门店id去查询加V信息表
     * @param page (必填:门店id--STORE_ID)
     * @return (Company_Name--企业名称,STORE_NAME--门店名称,pubwin_store_id,pubwin_ip,STORE_PHONE--门店手机号,STORE_ID--门店id,UPDATE_USERNAME--修改人,UPDATETIME--修改时间)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listshowv(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listshowv", page);
	}

	/**
	 * 查询指定门店加V日志(查看)
	 * 通过门店id去查询日志表
     * @param page (必须:门店id--STORE_ID)
     * @return (ASTATE--加V通过状态,ADDTIME--新增时间,TIME--审核时间,EXPIRATION_TIME--过期时间,Note--备注,STORE_ID--门店Id)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> addvLog(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.addvLog", page);
	}

	/**
	 * 记录加V日志
	 * 将审核状态和结果存进加V日志表
	 * @param pd (必填:门店id--STORE_ID,TIME--审核时间,STATE--审核状态,ADD_USERNAME--添加人,ADDTIME--添加时间,Note--备注)
	 * @throws Exception
	 */
	public void addVlog(PageData pd) throws Exception {
		dao.save("StoreMapper.addVlog", pd);
	}

	/**
	 * 记录加V日志
	 * 将审核状态和结果存进加V日志表
	 * @param pd (必填:门店id--STORE_ID,Through_Time--通过实践,STATE--审核状态,EXPIRATION_TIME--到期时间)
	 * @throws Exception
	 */
	public void updateVstate(PageData pd) throws Exception {
		dao.save("StoreMapper.updateVstate", pd);
	}

	/**
	 * 修改加V信息(重新提交)
	 * 通过门店ip去修改门店加V信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateV(PageData pd) throws Exception {
		dao.save("StoreMapper.updateV", pd);
	}

	/**
	 * 修改加V信息(修改)
	 * 通过门店ip去修改门店加V信息IP
	 * @param pd(必填:STORE_ID--门店id,pubwin_ip,UPDATE_USERNAME--修改人,UPDATETIME--修改时间,STATE--状态)
	 * @throws Exception
	 */
	public void updateIP(PageData pd) throws Exception {
		dao.save("StoreMapper.updateIP", pd);

	}

	/**
	 * 加V信息导出为excel
	 * 通过门店id去查询相关门店信息并导出为excel
	 * @param page (选填:STORE_ID--门店id,pd.STATE--状态)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listExcel(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listExcel", page);
	}

	/**
	 * 管理门店(后台)查全部门店
	 * 去查询所有门店信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listALL(Page page, PageData pd) throws Exception {
		// 关键词检索条件
		String keywords = pd.getString("keywords");
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
			}
		page.setPd(pd);
		return (List<PageData>) dao.findForList("StoreMapper.listPageALL", page);
	}

	/**
	 * 通过门店id查询手机号
	 * @param page (必填:门店id--STORE_ID,角色id--ROLE_ID)
	 * @return PHONE--手机号
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPhone(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listPhone", page);
	}

	/**
	 * 通过门店id查询网吧名称
	 * @param page (必填:门店id--STORE_ID)
	 * @return (INTENET_NAME--网吧名称,STORE_NAME--门店名称)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryName(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.queryName", page);
	}

	/**
	 * 通过网吧id查询网吧用户状态
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> usercheck(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.usercheck", pd);
	}

	/**
	 * 加V特权展示页
	 * 查询指定门店的信息
	 * @param page(选填:门店id--STORE_ID,网吧id--internetId)
	 * @return 指定门店加V信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listShowV(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listShowV", page);
	}
	
	/**
	 * 查询该用户全部门店信息
	 * @param page(必填:网吧id--internetId;选填:门店id--pd.STORE_ID)
	 * @return (store_id--门店id,store_name--门店名称,intenet_name--网吧名称,INSERT_TIME--授权时间)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> storeInfo(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listStore", page);
	}
	/**
	 * 通过name查询加V数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> hasName(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.hasName", pd);
	}
	
	/**
	 * 通过id查询加V数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hasID(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.hasID", pd);
	}
	/**
	 * 通过ip查询加V数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hasIP(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.hasIP", pd);
	}
	/**
	 * 通过网吧id查询现有门店(没有被禁用的)
	 * @param pd 网吧id--internetId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getStore(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.getStore", pd);
	}
	
	/**
	 * 新增门店
	 * @param pageData
	 * @throws Exception
	 */
	public PageData addStore(PageData pageData) throws Exception {
		PageData pd = new PageData(); // 返回PageData
		pd.put("STORE_ID", pageData.get("STORE_ID")); // 主键
		pd.put("STORE_NAME", pageData.get("STORE_NAME"));
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeNow = dateFm.format(new Date());
		pd.put("INSERT_TIME", timeNow);
		pd.put("UPDATE_TIME", timeNow);
		pd.put("PROVINCE", pageData.get("PROVINCE"));
		pd.put("CITY", pageData.get("CITY"));
		pd.put("COUNTY", pageData.get("COUNTY"));
		pd.put("ADDRESS", pageData.get("ADDRESS"));
		pd.put("TELEPHONE", pageData.get("TELEPHONE"));
		pd.put("INTERNET_ID", pageData.get("INTERNET_ID")); // 网吧id
		pd.put("member_level", pageData.get("member_level"));
		pd.put("ODDS","0");
		if (storeService.findByStoreName(pd) == null) {//这里判断网吧名称(新增门店时已进行门店名称判断,同一网吧不能重名,不同网吧可以重名)
			pd.put("INTERNET_ID", pageData.get("INTERNET_ID")); // 网吧id
			pd.put("USER_ID", this.get32UUID());
			// 新增门店的时候把通用赛事插入（同步）
			PageData pdInt = new PageData();
			pdInt.put("internetId", pageData.get("INTERNET_ID"));
			List<PageData> slist = storeService.listByIntenet(pdInt); //通过网吧id查询现有门店(没有被禁用的)

			if (slist.size() != 0) {

				PageData pdCard = new PageData();
				pdCard.put("INTERNET_ID", pageData.get("INTERNET_ID"));
				List<PageData> cardList = cardService.listByInternet(pdCard);
				for (PageData card : cardList) {
					if (card.getString("IS_ALL").equals("1")) {// 通用的时候，同步卡券
						PageData pdStore = new PageData();
						pdStore.put("CARD_STORE_ID", this.get32UUID());
						pdStore.put("CARD_ID", card.get("CARD_ID"));
						pdStore.put("STORE_ID", pd.getString("STORE_ID"));
						cardStoreService.save(pdStore);
					}
				}
			}
			storeService.save(pd);// 新增门店(新增门店的同时加入加V表)
			pd.put("msg", PublicManagerUtil.SUCCESS);
		} else {
			pd.put("msg", PublicManagerUtil.FAILED);
		}
		return pd;
	}
	
	/**
	 * 通过user_id去找关联门店id和name的集合
	 * @param userId
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getStoreList(String userId) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.getStoreList", userId);
	}
	
	/**
	 * 通过user_id去找关联门店id和name的集合
	 * @param userId
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getStoreList1(String userId) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.getStoreList1", userId);
	}
	
	/**
	 * 查询加V缺失门店
	 * 查询在store表中有store_v表中没有的门店(完善数据库)
	 * @param page
	 * @return 加V信息缺失门店id--STORE_ID
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void getStoreAll(Page page) throws Exception {
		List<PageData> listAll = (List<PageData>) dao.findForList("StoreMapper.getStoreAll", page); //查询加V缺失门店(完善数据库)
		if (listAll.size() != 0) {// 如果有缺失,则去添加
			for (int i = 0; i < listAll.size(); i++) {
				PageData pd1 = new PageData();
				pd1.put("STORE_ID", listAll.get(i).get("STORE_ID"));
				pd1.put("ODDS", "0");                     //免费试用机会(默认为0)  
				this.addVlist(pd1);                       //判断后去添加store_v缺失信息
			}
		}
		List<PageData> list = (List<PageData>) dao.findForList("StoreMapper.getStoreTips", page); //查询tips表中是否有该门店信息
		for (int j = 0; j < list.size(); j++) {
			PageData pd = new PageData();
			pd.put("STORE_ID", list.get(j).get("STORE_ID"));
			pd.put("create_time", list.get(j).get("create_time"));
			pd.put("update_time", list.get(j).get("create_time"));
			dao.save("StoreMapper.addTips", pd);     //查到有门店没惠新标签的,保存门店会员标签信息至tips表	
		}
	}
	
	/**
	 * 判断后去添加store_v缺失信息
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void addVlist(PageData pd) throws Exception {
		dao.save("StoreMapper.saveaddv", pd);
	}
	
	/**
	 * 通过门店id去查原来的门店名称
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByStoreId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.findByStoreId", pd);
	}

	/**
	 * 通过网吧id获取门店信息
	 * @param pdStore
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listByCard(PageData pdStore) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listByCard", pdStore);
	}
	
	/**
	 * 通过门店id和状态和网吧id获取数据
	 * @param pdBind
	 * @throws Exception
	 */
	@Override
	public List<PageData> findNotBan(PageData pdBind) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.findNotBan", pdBind);
	}

	/**
	 * 通过pubwin_ip获取相关门店加V信息
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByPubwinIP(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findByPubwinIP", pd);
	}

	/**
	 * 获取网吧内加v的门店列表
	 * @param pd 网吧id--internet_id,门店状态--state
	 * @throws Exception
	 */
	@Override
	public List<PageData> findStoreV(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.findStoreV", pd);
	}

	/**
	 * 根据门店id及其审核状态获取
	 * @param pd（store_id和state）
	 * @return
	 * @throws Exception
	 */
	public PageData findByStoreIdAndState(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findByStoreIdAndState", pd);
	}
	
	/**
	 * 检查门店是否加v，检查channelMap中是否存在该网吧连接
	 * @param store_id（传入store_id）
	 * @return
	 * @throws Exception
	 */
	public Boolean checkStoreV(String store_id) throws Exception{
		
		//检查传入参数是否为空
		if(StringUtil.isEmpty(store_id)){
			return false;
		}
		
		//检查门店是否加v
		PageData pd = new PageData();
		pd.put("store_id", store_id);
		pd.put("state", "1");//加v状态
		pd = findByStoreIdAndState(pd);
		System.out.println("是否家v"+StringUtil.isEmpty(pd));
		if(StringUtil.isEmpty(pd)){
			return false;
		}
		
		//检查是否存在tcp连接
		if(StringUtil.isEmpty(ChannelCache.channelMap.get(store_id))){
			return false;
		}
		
		return true;
	}

	/**
	 * 查询是否加v（已加v）状态的门店列表
	 * @param pd（state加v状态）
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listStoreV(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listStoreV", pd);
	}

	/**
	 * 查询已加v未查看的门店信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selAddV(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.selAddV", page);
	}

	/**
	 * 查看信息时去修改加V表标识
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void editV(PageData pd) throws Exception {
		dao.save("StoreMapper.editV", pd);
	}

	/**
	 * 查询所有加V门店的信息
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selV() throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.selV",null);
	}

	/**
	 * 去查询服务商号是否存在
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public JSONObject hasSERVICE_ID(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		PageData pd1=(PageData) dao.findForObject("StoreMapper.hasSERVICE_ID",pd);
		if(StringUtil.isNotEmpty(pd1)) {//代理商编号存在
			json.put("result", PublicManagerUtil.SUCCESS);
		}else {
			json.put("result", PublicManagerUtil.FALSE);
		}
		return json ;
	}

	/**
	 * 去查询指定门店加V状态
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selState(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.selState",pd);
	}
	
	/**
	 * 查询门店的主体信息（store_id,store_name,internet_id,internet_name）
	 * @param pd(store_id)
	 * @return
	 * @throws Exception
	 */
	public PageData findStoreInfo(PageData pd) throws Exception{
		return (PageData) dao.findForObject("StoreMapper.findStoreInfo", pd);
	}
	
	/**
	 * 查看门店的计费系统和在线支付开通情况
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findStoreOpenInfo(PageData pd) throws Exception{
		
		return (PageData) dao.findForObject("StoreMapper.findStoreOpenInfo", pd);
	}

	/**
	 * 查看已加V门店的信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selectV() throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.selectV",null);
	}

	/**
	 * 通过门店id修改标识状态
	 * @throws Exception
	 */
	@Override
	public void editStoreV(PageData pd) throws Exception {
		dao.update("StoreMapper.editStoreV", pd);
	}

	/**
	 * 通过门店id查询门店加V状态
	 * @throws Exception
	 */
	@Override
	public PageData selectState(Page page) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.selectState", page);
	}

	/**
	 * 通过卡券Id获取家V门店信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listFindCardId(PageData pd1) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listFindCardId",pd1);
	}

	/**
	 * 查询加V门店的id集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selectAddV() throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.selectAddV",null);
	}

	@Override
	public List<PageData> findStoreByOthers(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.findStoreByOthers", pd);
	}

	@Override
	public PageData findByPubwinStoreId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findByPubwinStoreId", pd);
	}
	@Override
	public PageData findInternetInfo(String store_id) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findInternetInfo", store_id);
	}


	@Override
	public List<PageData> listByInternetId(String internet_id) throws Exception {
		return (List<PageData>) dao.findForList("StoreMapper.listByInternetId", internet_id);
	}
}
