package com.lk.service.internet.scene.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.internet.scene.SceneManager;
import com.lk.service.system.cancel.CancelManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

import net.sf.json.JSONObject;

/** 
 * 说明： 优惠券使用场景
 * 创建人：洪智鹏
 * 创建时间：2017-06-08
 * @version
 */
@Service("sceneService")
@SuppressWarnings("restriction")
public class SceneService implements SceneManager{
	
	@Resource(name="cancelService")
	private CancelManager cancelService;
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd包含优惠券使用场景相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SceneMapper.save", pd);
	}
	
	/**
	 * 删除
	 * 通过卡劵id删除卡劵使用场景
	 * @param pd 包含主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SceneMapper.delete", pd);
	}
	
	/**
	 * 修改场景数据
	 * @param pd包含优惠券使用场景相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SceneMapper.edit", pd);
	}
	/**列表
	 * @param page页面检索字段信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SceneMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd无，查询全部
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SceneMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd场景主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SceneMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS场景主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SceneMapper.deleteAll", ArrayDATA_IDS);
	}
	/**根据名称判断是否重复
	 * @param pd包含 场景名称，场景id和网吧id
	 * @throws Exception
	 */
	public PageData findByName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SceneMapper.findByName", pd);
	}

	/**
	 * 根据名称判断是否重复
	 * @param pd 传入的是cardid，优惠券主键
	 * @throws Exception
	 */
	public PageData findByCard(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SceneMapper.findByCard", pd);
	}
	/**
	 * 根据场景核销优惠券的判断
	 * @param pdSce 场景信息
	 * @param pdCancle 卡卷信息
	 * @param pdUser 优惠券对应的用户信息(粉丝信息)
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
		     //去除核销的时候，生日判断

//			 String cardEd=pdUser.getString("CARDED");
//			 String cardNew=cardEd.substring(cardEd.length()-8, cardEd.length()-4);
//			 Date dateTimeUpdate=new Date();
//			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		     String dateNow = sdf.format(dateTimeUpdate);
//		     String dateNowStr = dateNow.substring(dateNow.length()-5, dateNow.length()).replace("-","");
//			 if(!cardNew.endsWith(dateNowStr)){
//				jsonObject.put("msg",PublicManagerUtil.FALSE);
//				jsonObject.put("result","生日和身份证绑定信息不符。");
//				 return jsonObject;
//			}
				jsonObject=judgeDateNumber(pdSce,pdCancle);
		 }
		return jsonObject;
	}
	/**
	 * 判断时间问题
	 * @param pdSce 场景信息
	 * @param pd 卡卷信息
	 * @throws Exception
	 */
	private JSONObject judgeDateNumber( PageData pdSce,PageData pdCancle)throws Exception {
		JSONObject jsonObject=new JSONObject();
		 PageData pdNew = new PageData();
		 pdNew.put("CARD_ID", pdCancle.getString("CARD_ID"));
		 pdNew.put("OPEN_ID", pdCancle.getString("OPEN_ID"));
		 Date dateTimeUpdate=new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     String dateNowStr = sdf.format(dateTimeUpdate);  
	     Date startTime=null;
		 if(StringUtil.isNotEmpty(pdSce.getString("RECEIVE_DETIL")) && StringUtil.isNotEmpty(pdSce.getString("BLANK_NUMBER"))){
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
		 if(StringUtil.isNotEmpty(pdSce.getString("BLANK_NUMBER"))){
			  String aa=pdSce.getString("BLANK_NUMBER");
			 int bb=Integer.parseInt(aa);

			 //取消核销数量限制
			 jsonObject.put("msg",PublicManagerUtil.SUCCESS);
			 return jsonObject;

//			 if(pdCancleList.size()>=bb){
//				 jsonObject.put("msg",PublicManagerUtil.FALSE);
//				 jsonObject.put("result","超过该卡券的核销数量");
//				return jsonObject;
//			 }else{
//				 jsonObject.put("msg",PublicManagerUtil.SUCCESS);
//				return jsonObject;
//			 }
		 }else{
			 jsonObject.put("msg",PublicManagerUtil.SUCCESS);
			return jsonObject;
		 }
	}
	/**
	 * 查看系统设置的场景是否已经添加
	 * @param pd 包含网吧id和状态字段
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByState(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SceneMapper.listByState", pd);
	}

}

