package com.lk.service.system.store;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.PageData;

import net.sf.json.JSONObject;

/**
 * 说明： 门店管理接口 
 * 创建人：洪智鹏 
 * 创建时间：2017-02-27
 */
public interface StoreManager {

	/**
	 * 筛选列表(全部)
	 * 通过page查询该角色关联门店
	 * @param page 关键词--keywords门店id--STORE_ID,网吧id--internetId
	 * @throws Exception
	 */
	public List<PageData> listUU(Page page) throws Exception;

	/**
	 * 新增门店
	 * 保存门店信息
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;
	
	/**
	 * 新增门店
	 * 保存门店信息
	 * @param pd
	 * @param request 
	 * @throws Exception
	 */
	public void save1(PageData pd, HttpServletRequest request) throws Exception;

	/**
	 * 新增门店
	 *
	 * @param pd网吧门店相关信息
	 * @throws Exception
	 */
	public void saveStore(PageData pd) throws Exception;

	/**
	 * 删除
	 * 通过门店id删除门店信息
	 * @param pd 网吧门店的主键信息
	 * @return 
	 * @throws Exception
	 */
	public JSONObject delete(PageData pd) throws Exception;

	/**
	 * 门店管理(修改)
	 * 保存修改后的门店信息
	 * @param pd修改后的门店信息
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;

	/**
	 * 修改门店名称
	 */
	public void editStore(PageData pd) throws Exception;

	/**
	 * 列表(门店管理用--老板)
	 * 
	 * @param page 检索字段和页面部分信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;
	
	/**
	 * 门店管理列表
	 * 通过网吧id/门店id,关键字keywords去查询用户相关门店信息
	 * @param pd 
	 * @param page--网吧id/门店id,关键字keywords
	 * @throws Exception
	 */
	public List<PageData> storeList(Page page, PageData pd) throws Exception;

	/**
	 * 门店列表(门店管理用--员工)
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> sList(Page page) throws Exception;

	/**
	 * 列表(全部)
	 * 查询全部门店信息
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception;

	/**
	 * 门店管理(修改)
	 * 通过门店id获取门店信息
	 * @param pd--门店id
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 去查看页面
	 * 获取绑定的门店信息
	 * @param pd--门店id
	 * @throws Exception
	 */
	public PageData findByVId(PageData pd) throws Exception;

	/**
	 *根据store_id查询加v信息
	 * @param pd（store_id）
	 * @return
	 * @throws Exception
	 */
	public PageData findStoreVById(PageData pd) throws Exception;

	/**
	 * 通过id删除数据
	 * 通过门店id批量删除门店信息
	 * @param ArrayDATA_IDS
	 * @return 
	 * @throws Exception
	 */
	public JSONObject deleteAll(String[] ArrayDATA_IDS) throws Exception;
	
	/**
	 * 通过网吧id查询现有门店(没有被禁用的)
	 * @param pd 网吧id--internetId
	 * @throws Exception
	 */
	public List<PageData> listByIntenet(PageData pd) throws Exception;

	public PageData findByUserId(PageData pdStore) throws Exception;

	/**
	 * 通过name去判断是否存在
	 * @param pdStore--门店名称
	 * @throws Exception
	 */
	public PageData findByStoreName(PageData pdStore) throws Exception;

	/**
	 * 通过卡劵id获取信息
	 * @param pd 卡劵id--cardId
	 * @throws Exception
	 */
	public List<PageData> listByCardId(PageData pdStore) throws Exception;

	public List<PageData> findCityByInternetId(PageData pd) throws Exception;

	public List<PageData> findAreaByCity(PageData pd) throws Exception;

	public List<PageData> findStoreByArea(PageData pd) throws Exception;

	/**
	 * 通过USER_ID和INTENET_ID获取数据
	 * @param pdStore
	 * @return
	 * @throws Exception
	 */
	public PageData findByStore(PageData pdStore) throws Exception;

	/**
	 * 判断门店是否存在(修改)
	 * 通过STORE_NAME和INTENET_ID判断门店是否存在
	 * @param pdStore
	 * @throws Exception
	 */
	public PageData findByStoreName_Intenet(PageData pdStore) throws Exception;
	
	/**
	 * 判断门店是否存在(修改)
	 * 通过STORE_NAME和INTENET_ID判断门店是否存在
	 * @param pd
	 * @param STORE_ID 
	 * @param STATE 
	 * @throws Exception
	 */
	public JSONObject findByStoreName_Intenet1(PageData pd, String STATE, String STORE_ID) throws Exception;

	/**
	 * 根据STORE_ID获取门店数据(查询自己所在的分店)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listByStore(PageData pd) throws Exception;

	/**
	 * 修改门店状态(禁用/启用)
	 * 通过不同操作修改门店状态
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public JSONObject statebt(PageData pd) throws Exception;

	/**
	 * Save Service
	 *
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData StoreSave(PageData pageData) throws Exception;

	/**
	 * 门店详情展示
	 * 去查看该门店的详情
	 * @param pd(必填:门店id--STORE_ID)
	 * @return 门店详情信息(STORE_NAME--门店名称,PROVINCE--省,CITY--市,COUNTY--区,ADDRESS--详细地址,TELEPHONE--门店电话,STATE--加V状态,EXPIRATION_TIME--到期时间)
	 * @throws Exception
	 */
	public List<PageData> storeDetails(PageData pd) throws Exception;

	/**
	 * 停用指定门店
	 * 改变指定门店的状态
	 * @param pd(必填:门店id--STORE_ID)
	 * @return 
	 * @throws Exception
	 */
	public JSONObject disableUsers(PageData pd) throws Exception;

	/**
	 * 启用指定门店
	 * 改变指定门店的状态
	 * @param pd(必填:门店id--STORE_ID)
	 * @return 
	 * @throws Exception
	 */
	public JSONObject enableUser(PageData pd) throws Exception;
	
	/**
	 * 门店特权列表
	 * 通过用户状态,网吧id,门店id,关键词等去查询相关门店信息
	 * @param page(必填:网吧id--internetId,门店id--STORE_ID;选填:关键词--keywords,用户状态--STATE)
	 * @return 该用户旗下门店信息(STORE_NAME--门店名称,STATE--加V状态,STORE_ID--门店状态)
	 * @throws Exception
	 */
	public List<PageData> listv(Page page) throws Exception;
	
	/**
	 * 门店特权列表
	 * 通过条件等去查询相关门店信息
	 * @param (pd--封装信息,user--缓存用户信息,page)
	 * @return 该用户旗下门店信息(STORE_NAME--门店名称,STATE--加V状态,STORE_ID--门店状态)
	 * @throws Exception
	 */
	public List<PageData> listv(PageData pd) throws Exception;
	
	/**
	 * 门店特权列表(员工)
	 * @param page
	 * @throws Exception
	 */
	/*public List<PageData> sListv(Page page) throws Exception;*/



	public List<PageData> listshow(Page page) throws Exception;
	
	/**
	 * 保存加V信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveV(PageData pd)throws Exception;
	
	/**
	 * 查询指定门店加V信息(审核)
	 * 通过门店id去查询加V信息表
     * @param page (必填:门店id--STORE_ID)
     * @return (Company_Name--企业名称,STORE_NAME--门店名称,pubwin_store_id,pubwin_ip,STORE_PHONE--门店手机号,STORE_ID--门店id,UPDATE_USERNAME--修改人,UPDATETIME--修改时间)
	 * @throws Exception
	 */
	public List<PageData> listshowv(Page page) throws Exception;
	
	/**
	 * 记录加V日志
	 * 将审核状态和结果存进加V日志表
	 * @param pd (必填:门店id--STORE_ID,TIME--审核时间,STATE--审核状态,ADD_USERNAME--添加人,ADDTIME--添加时间,Note--备注)
	 * @throws Exception
	 */
	public List<PageData> addvLog(Page page) throws Exception;
	
	/**
	 * 记录加V日志
	 * 将审核状态和结果存进加V日志表
	 * @param pd (必填:门店id--STORE_ID,Through_Time--通过实践,STATE--审核状态,EXPIRATION_TIME--到期时间)
	 * @throws Exception
	 */
	public void addVlog(PageData pd) throws Exception;
	
	/**
	 * 记录加V日志
	 * 将审核状态和结果存进加V日志表
	 * @param pd (必填:门店id--STORE_ID,Through_Time--通过实践,STATE--审核状态,EXPIRATION_TIME--到期时间)
	 * @throws Exception
	 */
	public void updateVstate(PageData pd) throws Exception;
	
	/**
	 * 修改加V信息(修改/重新提交)
     * @param pdU
	 * @throws Exception
	 */
	public void updateV(PageData pdU) throws Exception;
	
	/**
	 * 加V信息导出为excel
	 * 通过门店id去查询相关门店信息并导出为excel
	 * @param page (选填:STORE_ID--门店id,pd.STATE--状态)
	 * @throws Exception
	 */
	public List<PageData> listExcel(Page page) throws Exception;
	
	/**
	 * 修改加V信息(修改)
	 * 通过门店ip去修改门店加V信息IP
	 * @param page(必填:STORE_ID--门店id,pubwin_ip,UPDATE_USERNAME--修改人,UPDATETIME--修改时间,STATE--状态)
	 * @throws Exception
	 */
	public void updateIP(PageData pd) throws Exception;
	
	/**
	 * 管理门店(后台)查全部门店
	 * 去查询所有门店信息
	 * @param pd 
	 * @throws Exception
	 */
	public List<PageData> listALL(Page page, PageData pd) throws Exception;
	
	/**
	 * 通过门店id查询手机号
	 * @param page (必填:门店id--STORE_ID,角色id--ROLE_ID)
	 * @return PHONE--手机号
	 * @throws Exception
	 */
	public List<PageData> listPhone(Page page) throws Exception;
	
	/**
	 * 通过门店id查询网吧名称
	 * @param page (必填:门店id--STORE_ID)
	 * @return (INTENET_NAME--网吧名称,STORE_NAME--门店名称)
	 * @throws Exception
	 */
	public List<PageData> queryName(Page page) throws Exception;
	
	/**
	 * 通过网吧id查询网吧状态
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> usercheck(PageData pd) throws Exception;
	
	/**
	 * 加V特权展示页
	 * 查询指定门店的信息
	 * @param page(选填:门店id--STORE_ID,网吧id--internetId)
	 * @return 指定门店加V信息
	 * @throws Exception
	 */
	public List<PageData> listShowV(Page page) throws Exception;
	
	/**
	 * 查询该用户全部门店信息
	 * @param page(必填:网吧id--internetId;选填:门店id--pd.STORE_ID)
	 * @return (store_id--门店id,store_name--门店名称,intenet_name--网吧名称,INSERT_TIME--授权时间)
	 * @throws Exception
	 */
	public List<PageData> storeInfo(Page page) throws Exception;
	
	/**
	 * 通过name查询加V数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> hasName(PageData pd) throws Exception;
	
	/**
	 * 通过id查询加V数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> hasID(PageData pd) throws Exception;
	
	/**
	 * 通过ip查询加V数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> hasIP(PageData pd) throws Exception;
	
	/**
	 * 通过网吧id查询现有门店(没有被禁用的)
	 * @param pd 网吧id--internetId
	 * @throws Exception
	 */
	public List<PageData> getStore(PageData pdUser)  throws Exception;
	
	/**
	 * 新增门店
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public PageData addStore(PageData pd) throws Exception;
	
	
	/**
	 * 通过user_id去找关联门店id和name的集合
	 * @param userId
	 * @return 
	 * @throws Exception
	 */
	public List<PageData> getStoreList(String userId) throws Exception;
	
	/**
	 * 通过user_id去找关联门店id和name的集合
	 * @param userId
	 * @return 
	 * @throws Exception
	 */
	public List<PageData> getStoreList1(String userId) throws Exception;
	
	/**
	 * 查询加V缺失门店
	 * 查询在store表中有store_v表中没有的门店(完善数据库)
	 * @param page
	 * @return 加V信息缺失门店id--STORE_ID
	 * @throws Exception
	 */
	public void getStoreAll(Page page) throws Exception;
	
	/**
	 * 判断后去添加store_v缺失信息
	 * @param page
	 * @throws Exception
	 */
	public void addVlist(PageData pd) throws Exception;
	
	/**
	 * 通过门店id去查原来的门店名称
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findByStoreId(PageData pd) throws Exception;

	/**
	 * 通过网吧id获取门店信息
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listByCard(PageData pdStore) throws Exception;

	/**
	 * 通过门店id和状态和网吧id获取数据
	 * @param pdBind
	 * @throws Exception
	 */
	public List<PageData> findNotBan(PageData pdBind)throws Exception;

	/**
	 * 通过pubwin_ip获取相关门店加V信息
	 * @param pdBind
	 * @throws Exception
	 */
	public PageData findByPubwinIP(PageData pd)throws Exception;

	/**
	 * 获取网吧内加v的门店列表
	 * @param page 网吧id--internet_id,门店状态--state
	 * @throws Exception
	 */
	public List<PageData> findStoreV(PageData pd)throws Exception;

	
	/**
	 * 根据门店id及其审核状态获取
	 * @param pd（store_id和state）
	 * @return
	 * @throws Exception
	 */
	public PageData findByStoreIdAndState(PageData pd)throws Exception;
	
	
	/**
	 * 检查门店是否加v，检查channelMap中是否存在该网吧连接
	 * @param json（传入store_id）
	 * @return
	 * @throws Exception
	 */
	public Boolean checkStoreV(String store_id) throws Exception;

	/**
	 * 查询已加v的门店列表
	 * @param pd（state加v状态）
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listStoreV(PageData pd)throws Exception;

	/**
	 * 查询已加v未查看的门店信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selAddV(Page page) throws Exception;

	/**
	 * 查看信息时去修改加V表标识
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public void editV(PageData pd) throws Exception;

	/**
	 * 查询所有加V门店的信息
	 * @param page
	 * @throws Exception
	 */
	public  List<PageData> selV() throws Exception;

	/**
	 * 去查询服务商号是否存在
	 * @param page
	 * @throws Exception
	 */
	public JSONObject hasSERVICE_ID(PageData pd) throws Exception;

	/**
	 * 去查询指定门店加V状态
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selState(PageData pd) throws Exception;

	/**
	 * 查询门店的主体信息（store_id,store_name,internet_id,internet_name）
	 * @param pd(store_id)
	 * @return
	 * @throws Exception
	 */
	public PageData findStoreInfo(PageData pd) throws Exception;

	/**
	 * 查看门店的计费系统和在线支付开通情况
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findStoreOpenInfo(PageData pd) throws Exception;

	/**
	 * 查看已加V门店的信息
	 * @throws Exception
	 */
	public List<PageData> selectV() throws Exception;

	/**
	 * 通过门店id修改标识状态
	 * @throws Exception
	 */
	public void editStoreV(PageData pd) throws Exception;

	/**
	 * 通过门店id查询门店加V状态
	 * @throws Exception
	 */
	public PageData selectState(Page page) throws Exception;

	/**
	 * 通过卡券Id获取家V门店信息
	 * @throws Exception
	 */
	public List<PageData> listFindCardId(PageData pd1) throws Exception;

	/**
	 * 查询加V门店的id集合
	 * @throws Exception
	 */
	public List<PageData> selectAddV() throws Exception;

	/**
	 * 根据情况查询门店
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findStoreByOthers(PageData pd) throws Exception;

	/**
	 * 根据pubwinid获取网吧信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByPubwinStoreId(PageData pd) throws Exception;


	/**
	 * 根据store_id获取网吧信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findInternetInfo(String store_id) throws Exception;


    List<PageData> listByInternetId(String intenet_id) throws Exception;
}
