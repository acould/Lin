package com.lk.service.system.smslog;

import java.util.Date;
import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

import net.sf.json.JSONObject;

/** 
 * 说明： 手机验证码接口
 * 创建人：洪智鹏
 * 创建时间：2017-05-10
 * @version
 */
public interface SmslogManager{

	/**
	 * 新增
	 * 短信发送成功后保存信息到日志表
	 * @param pd--手机验证码相关信息(TB_SMSLOG表信息)
	 * @return 
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 新增
	 * 短信发送成功后保存信息到日志表
	 * @param pd--手机验证码相关信息(TB_SMSLOG表信息)
	 * @return 
	 * @throws Exception
	 */
	public JSONObject save1(PageData pd)throws Exception;
	
	/**删除
	 * @param pd 手机验证码主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd手机验证码相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page手机验证码检索和页面部分信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd 手机验证码表的主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 根据电话和时间查询数据
	 * @param string--类型,phone--手机号,startTimeL--开始时间,endTimeL--到期时间
	 * @throws Exception
	 */
	public List<PageData> getByPhone(String string, String phone,Date startTimeL, Date endTimeL)throws Exception;
	
	/**
	 * 根据电话和时间查询数据
	 * @param string--类型,phone--手机号
	 * @throws Exception
	 */
	public List<PageData> getByPhone(String string, String phone)throws Exception;
	
	/**
	 * 根据电话和时间查询数据
	 * @param phone--手机号
	 * @throws Exception
	 */
	public JSONObject getByPhone(String phone)throws Exception;
	
	/**
	 * 根据电话获取数据
	 * @param phone(必填:手机号--PHONE)
	 * @return 指定短信日志信息
	 * @throws Exception
	 */
	public PageData findByPhone(String phone)throws Exception;
	
	/**
	 * 根据电话获取数据
	 * @param phone(必填:手机号--PHONE)
	 * @return 指定短信日志信息
	 * @throws Exception
	 */
	public JSONObject findByPhone(String PHONE,String CODE)throws Exception;

	/**
	 * 发送验证码
	 * @param pd（phone,request_ip,user_id,internet_id）
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendCode(PageData pd)throws Exception;
	
	/**
	 * 查找手机号最后一条
	 * @param pd（传入phone）
	 * @return
	 * @throws Exception
	 */
	public PageData findLastMsg(PageData pd)throws Exception;
	
}

