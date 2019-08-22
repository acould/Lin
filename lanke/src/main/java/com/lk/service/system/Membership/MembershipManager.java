package com.lk.service.system.Membership;

import java.util.List;

import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.util.PageData;

/**
 * 
 * @Title 会员开通规则
 * @author 陈明阳
 * @date 2018年12月17日上午10:40:25
 */
public interface MembershipManager{

	/**
	 * 新增
	 * @param pd 
	 * @throws Exception
	 */
	public String save(PageData pd)throws Exception;
	
	/**
	 * 显示设置
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData view(PageData pd) throws Exception;
	
	/**
	 * 查询已选门店名称
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> yxstore(PageData pd) throws Exception;
	
	/**
	 * 修改状态
	 * @param type
	 * @throws Exception
	 */
	public void editType(PageData pd)throws Exception;

	
	/**
	 * 查询id  by 卡劵信息
	 * @return
	 * @throws Exception
	 */
	public PageData selectCard(String card_id) throws Exception;
	
	/**
	 * 查询id  by 卡劵信息
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selectCards(PageData pd) throws Exception;
	
	/**
	 * 添加会员
	 * @param store_id
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public JSONObject level(String store_id, JSONObject param,PageData pd) throws Exception;
	
	/**
	 * 查询门店开通会员的状态
	 * @param store_id
	 * @return
	 * @throws Exception
	 */
	public JSONObject typeStore(String store_id) throws Exception;
	
	/**
	 * 查询门店会员等级
	 * @param store_id
	 * @return
	 * @throws Exception
	 */
	public PageData StoreObject(String STORE_ID) throws Exception;
	
	
	/**
	 * 查询门店
	 * @param store_id
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCityList(String internet_id) throws Exception;
	
	
	/**
	 * 通过卡劵id 和 userid 查询 卡劵是否领取
	 * @param pdS 
	 * @throws Exception
	 */
	public String CardIdandUserIdbycard(PageData pdS) throws Exception;
	
	/**
	 * 查询申请会员的记录
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData selectfind(PageData pd) throws Exception;
	
	/**
	 * 添加申请会员记录
	 * @param pd
	 * @throws Exception
	 */
	public void addmemberfind(PageData pd) throws Exception;
	
	/**
	 * 线程修改微信用户信息
	 * @param pd
	 * @throws Exception
	 */
	public void information(PageData pd) throws Exception;
}

