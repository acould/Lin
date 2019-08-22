package com.lk.service.system.sysscene.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.cancel.CancelManager;
import com.lk.service.system.sysscene.SysSceneManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Tools;

/** 
 * 说明： 优惠券使用场景
 * 创建人：洪智鹏
 * 创建时间：2017-06-08
 * @version
 */
@Service("syssceneService")
@SuppressWarnings("restriction")
public class SysSceneService implements SysSceneManager{
	
	@Resource(name="cancelService")
	private CancelManager cancelService;
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd优惠券使用场景相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysSceneMapper.save", pd);
	}
	
	/**删除
	 * @param pd优惠券使用场景的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysSceneMapper.delete", pd);
	}
	
	/**修改
	 * @param pd优惠券使用场景相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysSceneMapper.edit", pd);
	}
	
	/**列表
	 * @param page检索字段信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysSceneMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysSceneMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd优惠券使用场景的主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysSceneMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS优惠券使用场景相关信息的主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysSceneMapper.deleteAll", ArrayDATA_IDS);
	}
	/**通过name获取数据
	 * @param pd优惠券使用场景信息主要有名称
	 * @throws Exception
	 */
	public PageData findByName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SysSceneMapper.findByName", pd);
	}
	/**通过cardid获取数据
	 * @param pd cardid
	 * @throws Exception
	 */
	public PageData findByCard(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SysSceneMapper.findByCard", pd);
	}
	/**通过cardid获取数据
	 * @param pdSce 场景信息
	 * @param pdCancle 审核信息
	 * @param pdUser 用户信息
	 * @throws Exception
	 */
	public JSONObject SceneCancel( PageData pdSce,PageData pdCancle,PageData pdUser)throws Exception {
		JSONObject jsonObject=new JSONObject();
		 if(pdSce.getString("FAV_TYPE").endsWith("MAN")){
			 String cardEd=pdUser.getString("CARDED");
				String cardNew=cardEd.substring(cardEd.length()-2, cardEd.length()-1);
				int number=Integer.parseInt(cardNew);
				if(number%2==0){
					jsonObject.put("msg",PublicManagerUtil.FALSE);
					jsonObject.put("result","不是男性身份证号条件不符要求。");
					 return jsonObject;
				}
				jsonObject=judgeDateNumber(pdSce,pdCancle);
				 
		 }else if(pdSce.getString("FAV_TYPE").endsWith("WEM")){
			 String cardEd=pdUser.getString("CARDED");
			 String cardNew=cardEd.substring(cardEd.length()-2, cardEd.length()-1);
			 int number=Integer.parseInt(cardNew);
			 if(number%2!=0){
				jsonObject.put("msg",PublicManagerUtil.FALSE);
				jsonObject.put("result","不是女性身份证号条件不符要求。");
				 return jsonObject;
			}
			jsonObject=judgeDateNumber(pdSce,pdCancle);
		 }else if(pdSce.getString("FAV_TYPE").endsWith("NEW")){
				jsonObject=judgeDateNumber(pdSce,pdCancle);
		 }else if(pdSce.getString("FAV_TYPE").endsWith("OLD")){
				jsonObject=judgeDateNumber(pdSce,pdCancle);
		 }else if(pdSce.getString("FAV_TYPE").endsWith("CURREN")){
				jsonObject=judgeDateNumber(pdSce,pdCancle);
		 }else if(pdSce.getString("FAV_TYPE").endsWith("CHANG")){
				jsonObject=judgeDateNumber(pdSce,pdCancle);
		 }else if(pdSce.getString("FAV_TYPE").endsWith("BIRTH")){
				jsonObject=judgeDateNumber(pdSce,pdCancle);
		 }
		return jsonObject;
	}
	
	private JSONObject judgeDateNumber( PageData pdSce,PageData pdCancle)throws Exception {
		JSONObject jsonObject=new JSONObject();
		 PageData pdNew = new PageData();
		 pdNew.put("CARD_ID", pdCancle.getString("CARD_ID"));
		 pdNew.put("OPEN_ID", pdCancle.getString("OPEN_ID"));
		 Date dateTimeUpdate=new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     String dateNowStr = sdf.format(dateTimeUpdate);  
	     Date startTime=null;
		 if(StringUtils.isNoneEmpty(pdSce.getString("RECEIVE_DETIL"))&&StringUtils.isNoneEmpty(pdSce.getString("BLANK_NUMBER"))){
			 int a=Integer.parseInt(pdSce.getString("BLANK_NUMBER"));
			 if(pdSce.getString("RECEIVE_DETIL").endsWith("WEEK")){
				 startTime=Tools.sAddDays(dateTimeUpdate, -a*7);
			 }else if(pdSce.getString("RECEIVE_DETIL").endsWith("YEAR")){
				 startTime=Tools.sAddYear(dateTimeUpdate, -a);
			 }else if(pdSce.getString("RECEIVE_DETIL").endsWith("DAY")){
				 startTime=Tools.sAddDays(dateTimeUpdate, -a);
			 }else if(pdSce.getString("RECEIVE_DETIL").endsWith("MON")){
				 startTime=Tools.sAddMonth(dateTimeUpdate, -a);
			 }
			 pdNew.put("END_TIME", dateNowStr);
			 pdNew.put("START_TIME", startTime);
		 }
		 pdNew.put("STATE", 2);
		 List<PageData> pdCancleList = cancelService.listByUser(pdNew);
		 if(StringUtils.isNoneEmpty(pdSce.getString("BLANK_NUMBER"))){
			  String aa=pdSce.getString("BLANK_NUMBER");
			 int bb=Integer.parseInt(aa);
			 if(pdCancleList.size()>bb){
				 jsonObject.put("msg",PublicManagerUtil.FALSE);
				 jsonObject.put("result","超过规定时间内核销数量");
				return jsonObject;
			 }else{
				 jsonObject.put("msg",PublicManagerUtil.SUCCESS);
				return jsonObject;
			 }
		 }else{
			 jsonObject.put("msg",PublicManagerUtil.SUCCESS);
				return jsonObject;
		 }
	}
}

