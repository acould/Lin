package com.lk.service.billiCenter.userFlow;

import java.util.List;

import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQuery;
import com.lk.entity.billecenter.SWQueryFlow;
import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.util.PageData;


public interface UserFlowService{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 获取user_flow
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject userFlow(String store_id, JSONObject json) throws Exception;
	/**
	 * 查询流水充值是否成功
	 * @param store_id
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject request(String store_id, JSONObject param) throws Exception;
	/**
	 * 从数据库中查流水
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> userFlowLocal(PageData pd) throws Exception;
	
	public List<PageData> dataUserFlow(PageData pd) throws Exception;
	
	public JSONObject userFlowLocalToLanker(String store_id, JSONObject json) throws Exception ;
	
	
	public List<PageData> userStoreFlow(PageData pd) throws Exception;

	/**
	 * 获取消费次数/消费总金额
	 */
	public PageData findByCardId(PageData pageData)throws Exception;

	
	public void pushMoneyChange(JSONObject json, String string) throws Exception;

	public JSONObject sendMoneyChangeTemp(String internet_id, String open_id, String k1, String k2, String k3, String k4) throws Exception;


	/*********************************** 新的查询方式******************************************/



    /**
     * 发送 顺网--"查询会员信息" 接口
     * @return
     * @throws Exception
     */
    Message2 sendSelUserFlow(String store_id, SWQueryFlow queryFlow) throws Exception;

    /**
     * 通过msg的flag获取缓存数据
     * @param msgFlag
     * @return
     * @throws Exception
     */
    Message2 getSelSWFlowByFlag(String msgFlag) throws Exception;


    /**
     * 请求用户流水(新的)
     * @return
     * @throws Exception
     */
    Message2 getUserFlow(String store_id, String card_id, int filter_type, int begin_timestamp, int end_timestamp) throws Exception;



}

