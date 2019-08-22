package com.lk.service.system.smslog.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.smslog.SmslogManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.util.UuidUtil;
import com.lk.wechat.util.SmsLogUtil;

/** 
 * 说明： 手机验证码
 * 创建人：洪智鹏
 * 创建时间：2017-05-10
 * @version
 */
@Service("smslogService")
public class SmslogService implements SmslogManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * 短信发送成功后保存信息到日志表
	 * @param pd--手机验证码相关信息(TB_SMSLOG表信息)
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SmslogMapper.save", pd);
	}
	
	/**
	 * 新增
	 * 短信发送成功后保存信息到日志表
	 * @param pd--手机验证码相关信息(TB_SMSLOG表信息)
	 * @throws Exception
	 */
	public JSONObject save1(PageData pd)throws Exception{
		User user = Jurisdiction.getSessionUser().getUser();//得到用户

		JSONObject  json        = new JSONObject();
		pd.put("SMSLOG_ID", UuidUtil.get32UUID());        // 主键
		pd.put("USER_ID", user.getUSER_ID());             // 用户id(-------)
		pd.put("TYPE", "reg");                            // 验证码类型
		pd.put("ADD_IP", "");                             // 新增ip
		pd.put("INTENET_ID", user.getINTENET_ID());       // 网吧id(----------)
		pd.put("CODE_TIME", Tools.date2Str(new Date()));  // 发送时间
		pd.put("ADD_TIME", Tools.date2Str(new Date()));   // 新增时间
		try {
			dao.save("SmslogMapper.save", pd);
			json.put("result", PublicManagerUtil.SUCCESS); // 短信发送成功
			json.put("message", "短信发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统繁忙,请稍后再试!");
		}
		return json;
	}
	
	/**删除
	 * @param pd 手机验证码主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SmslogMapper.delete", pd);
	}
	
	/**修改
	 * @param pd手机验证码相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SmslogMapper.edit", pd);
	}
	
	/**列表
	 * @param page手机验证码检索和页面部分信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SmslogMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd无查询全部
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SmslogMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd 手机验证码主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SmslogMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS手机验证码主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SmslogMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 根据电话和时间查询数据
	 * @param (必填:type--类型,phone--手机号,startTimeL--开始时间,endTimeL--到期时间)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getByPhone(String type, String phone,Date startTimeL, Date endTimeL) throws Exception {
		PageData pd= new PageData();
		pd.put("TYPE", type);
		pd.put("PHONE", phone);	
		pd.put("startTimeL", startTimeL);	
		pd.put("endTimeL", endTimeL);	
		return (List<PageData>)dao.findForList("SmslogMapper.getByPhone", pd);
	}
	
	/**
	 * 根据电话和时间查询数据
	 * @param (必填:type--类型,phone--手机号)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getByPhone(String type, String phone) throws Exception {
		PageData pd= new PageData();
		Date endTimeL = new Date();
		Date startTimeL = Tools.sAddMinute(endTimeL, -1); // 前60秒
		pd.put("TYPE", type);
		pd.put("PHONE", phone);	
		pd.put("startTimeL", startTimeL);	
		pd.put("endTimeL", endTimeL);	
		return (List<PageData>)dao.findForList("SmslogMapper.getByPhone", pd);
	}
	
	/**
	 * 根据电话和时间查询数据
	 * @param (phone--手机号)
	 * @return json
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  JSONObject getByPhone(String phone) throws Exception {
		JSONObject json  = new JSONObject();
		PageData   pd    = new PageData();
		Date    endTimeL = new Date();
		Date  startTimeL = Tools.sAddMinute(endTimeL, -1); // 前60秒
		pd.put("TYPE", "reg");
		pd.put("PHONE", phone);	
		pd.put("startTimeL", startTimeL);	
		pd.put("endTimeL", endTimeL);	
		List<PageData> list = (List<PageData>)dao.findForList("SmslogMapper.getByPhone", pd);    //根据电话和时间查询数据
		if (list != null && !list.isEmpty()) {
			json.put("result", "frequent");
			json.put("message", "短信发送太频繁");
		}
		else {
			json.put("result", PublicManagerUtil.TRUE);
		}
		return json;
	}

	/**
	 * 根据电话获取数据
	 * @param phone(必填:手机号--PHONE)
	 * @return 指定短信日志信息
	 * @throws Exception
	 */
	public PageData findByPhone(String phone) throws Exception {
		PageData pd= new PageData();
		pd.put("PHONE", phone);	
		return (PageData)dao.findForObject("SmslogMapper.findByPhone", pd);
	}
	
	/**
	 * 根据电话获取数据
	 * @param phone(必填:手机号--PHONE)
	 * @return 指定短信日志信息
	 * @throws Exception
	 */
	public JSONObject findByPhone(String PHONE,String CODE) throws Exception {
		PageData   pd   = new PageData();
		JSONObject json = new JSONObject();
		pd.put("PHONE", PHONE);	
		try {
			PageData SmslogPd = new PageData();
			/* //万能验证码,测试启用
			    if(!"000000".equals(CODE)) {    
				SmslogPd=(PageData)dao.findForObject("SmslogMapper.findByPhone", pd); //通过电话获取数据
				if (null == SmslogPd || SmslogPd.getString("CODE") == null || !SmslogPd.getString("CODE").equals(CODE)) { //验证码
					json.put("result", PublicManagerUtil.ERROR);
					return json;
				}
			}*/
			
			//正常流程
			SmslogPd=(PageData)dao.findForObject("SmslogMapper.findByPhone", pd); //通过电话获取数据
			if (null == SmslogPd || SmslogPd.getString("CODE") == null || !SmslogPd.getString("CODE").equals(CODE)) { //验证码
				json.put("result", PublicManagerUtil.ERROR);
				return json;
			}
			
			//验证码5分钟有效，现在时间    > 发送时间 + 5分钟 
			if(StringUtil.isNotEmpty(SmslogPd)){
				long sendTime = Tools.str2Date(SmslogPd.getString("CODE_TIME")).getTime()+ Const.AVAILABLE_TIME;
				long nowTime = new Date().getTime();
				if(nowTime > sendTime){
					json.put("result", "code_invalid");
					return json;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.ERROR1);
		}
		return json;
	}
	
	/**
	 * 发送验证码
	 * @param pd（phone,ip）
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendCode(PageData pd)throws Exception{
		JSONObject result = new JSONObject();
		
		String phone = pd.getString("phone");
		String user_id = pd.getString("user_id");
		String internet_id = pd.getString("internet_id");
		String request_ip = pd.getString("request_ip");
		result = checkPhone(pd);
		if(result.getString("result").equals("false")){
			return result;
		}
		
		//防止发送频繁：60s发送一次，每天同一手机号最多发送10次
		PageData pdMsg = this.findLastMsg(pd);
		if(StringUtil.isNotEmpty(pdMsg)){
			String send_time = pdMsg.getString("ADD_TIME");
			if(new Date().getTime() < (Tools.str2Date(send_time).getTime()+60*1000)){
				result.put("result", "false");
				result.put("message", "操作太频繁，请稍后再试");
				return result;
			}
		}
		String date = Tools.date2Str(new Date(), "yyyy-MM-dd");
		pd.put("start_time", date+" 00:00:00");
		pd.put("end_time", date+" 23:59:59");
		List<PageData> msgList = this.findByDate(pd);
		if(msgList.size() > 10){
			result.put("result", "false");
			result.put("message", "发送次数已达到今日最大次数");
			return result;
		}
		
		int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
		PageData pdSms = new PageData();
		pdSms.put("SMSLOG_ID", StringUtil.get32UUID());
		pdSms.put("USER_ID", user_id);
		pdSms.put("TYPE", "reg");
		pdSms.put("PHONE", phone);
		pdSms.put("CONTENTS", "【网晶揽客】验证码"+mobileCode+"，您正在进行揽客的绑定会员卡验证，打死不要告诉别人哦！");
		pdSms.put("CODE", mobileCode); // 验证码
		pdSms.put("CODE_TIME", Tools.date2Str(new Date()));
		pdSms.put("ADD_TIME", Tools.date2Str(new Date()));
		pdSms.put("ADD_IP", request_ip);
		pdSms.put("INTENET_ID", internet_id);
		Boolean state = SmsLogUtil.sendMessage(String.valueOf(mobileCode), phone, "");
		if (state) {
			this.save(pdSms);
			result.put("result", "true");
			result.put("message", "发送成功");
		} else {
			result.put("result", "false");
			result.put("message", "短信验证码发送失败，请重试");
			return result;
		}
		
		return result;
	}
	
	/**
	 * 判断手机号格式，用户情况
	 * @param pd（传入phone，user_id）
	 * @return
	 */
	private JSONObject checkPhone(PageData pd){
		JSONObject result = new JSONObject();
		
		String phone = pd.getString("phone");
		String user_id = pd.getString("user_id");
		
		if(StringUtil.isEmpty(phone)){
			result.put("result", "false");
			result.put("message", "请输入手机号");
			return result;
		}
		if(StringUtil.isEmpty(user_id)){
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}
		Pattern pattern = Pattern.compile(PublicManagerUtil.PHONE_REG);
		Matcher matcher = pattern.matcher(phone);
		if (!matcher.find()) {
			result.put("result", "false");
			result.put("message", "手机号格式错误");
			return result;
		}
		result.put("result", "true");
		return result;
	}

	/**
	 * 查找手机号最后一条
	 * @param pd（传入phone）
	 * @return
	 * @throws Exception
	 */
	public PageData findLastMsg(PageData pd)throws Exception{
		return (PageData) dao.findForObject("SmslogMapper.findLastMsg", pd);
	}
	
	/**
	 * 查找手机号在同一天的发送量
	 * @param pd（传入phone，start_time，end_time）
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByDate(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("SmslogMapper.findByDate", pd);
	}
}

