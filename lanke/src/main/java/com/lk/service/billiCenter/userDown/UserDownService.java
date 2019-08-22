package com.lk.service.billiCenter.userDown;

import java.util.List;

import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQuery;
import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.util.PageData;


public interface UserDownService{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;



    PageData calUserUpTime(String store_id, String card_id
            , String begin_time, String end_time)throws Exception;

	List<PageData> list(Page page) throws Exception;

	/**
	 * 根据上机标识查
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByGuid(PageData pd)throws Exception;
	

	/**
	 * 发送下机模板消息
	 * @param json：接口返回数据
	 *
	 * 下机的json 中的body为JSONObject
	 * @throws Exception
	 */
	public void pushUserDown(JSONObject json, String store_id)throws Exception;





	/**
	 * 发送 顺网--"查询会员下机信息" 接口
	 * @return
	 * @throws Exception
	 */
	Message2 sendSelUserDown(String store_id, SWQuery query) throws Exception;

	/**
	 * 通过msg的flag获取缓存数据
	 * @param msgFlag
	 * @return
	 * @throws Exception
	 */
	Message2 getSelUserDownByFlag(String msgFlag) throws Exception;


	/**
	 * 请求下机
	 * @return
	 * @throws Exception
	 */
	Message2 userDown(String store_id, String card_id) throws Exception;



}

