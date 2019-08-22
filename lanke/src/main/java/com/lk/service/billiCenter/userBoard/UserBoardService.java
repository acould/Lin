package com.lk.service.billiCenter.userBoard;


import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQrParam;
import com.lk.entity.billecenter.SWQuery;
import net.sf.json.JSONObject;

import com.lk.util.PageData;


public interface UserBoardService{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;


	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;


	/**
	 * 查询上机信息
	 * @return
	 * @throws Exception
	 */
	public JSONObject userBoard(String store_id, JSONObject param)throws Exception;

	/**
	 * 根据上机标识查guid
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByGuid(PageData pd)throws Exception;


	/**
	 * 推送上机模板
	 * @param json
	 * @throws Exception
	 */
	public void pushUserBoard(JSONObject json, String store_id)throws Exception;



	/**
	 * 发送网络唤醒指令,开启电脑
	 * @param store_id
	 * @param param （传入computer_name, ip, mac_address）
	 * @return 调用接口后，不确定是否开启电脑
	 * @throws Exception
	 */
	public Message2 wakeToPower(String store_id, JSONObject param)throws Exception;


	/**
	 * 顺网开机命令	OL6.3.22.0新增接口
	 * @param store_id
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Message2 swWakeToPower(String store_id, JSONObject param)throws Exception;



	/**
	 * 发送 顺网--"查询会员上机信息" 接口
	 * @return
	 * @throws Exception
	 */
	Message2 sendSelUserUP(String store_id, SWQuery query) throws Exception;

	/**
	 * 通过msg的flag获取缓存数据
	 * @param msgFlag
	 * @return
	 * @throws Exception
	 */
	Message2 getSelUserUPByFlag(String msgFlag) throws Exception;


	/**
	 * 请求获取上机信息
	 * @param store_id
	 * @param card_id
	 * @return SWUserBoard
	 * @throws Exception
	 */
	Message2 getUserBoard(String store_id, String card_id, String computer_name) throws Exception;


	/**
	 * 请求上机
	 * @return
	 * @throws Exception
	 */
	Message2 userUp(String store_id, String card_id, String computer_name, String psd) throws Exception;

	/**
	 * 请求换机
	 * @param store_id
	 * @param card_id
	 * @param computer_name
	 * @param psd
	 * @return
	 * @throws Exception
	 */
	Message2 userUpChange(String store_id, String card_id, String computer_name, String psd) throws Exception;


	/**
	 * 请求设置扫码上机参数
	 * @param store_id
	 * @param swQrParam
	 * @return
	 * @throws Exception
	 */
	Message2 setQrParam(String store_id, SWQrParam swQrParam) throws Exception;


}

