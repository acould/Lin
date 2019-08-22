package com.lk.service.billiCenter.recharge;


import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQuery;
import com.lk.entity.billecenter.SWRecharge;
import net.sf.json.JSONObject;

import com.lk.util.PageData;


public interface RechargeService{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;


	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;


	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**
	 * 充值操作
	 * @return
	 * @throws Exception
	 */
	public JSONObject recharge(String store_id, JSONObject param) throws Exception;


	/**
	 * 新版充值操作
	 * @param store_id
	 * @param recharge
	 * @return
	 * @throws Exception
	 */
	Message2 newRecharge(String store_id, SWRecharge recharge) throws Exception;

	/**
	 * 发送 顺网--"会员充值" 接口
	 * @return
	 * @throws Exception
	 */
	Message2 sendRecharge(String store_id, SWRecharge recharge) throws Exception;

	/**
	 * 通过msg的flag获取缓存数据
	 * @param msgFlag
	 * @return
	 * @throws Exception
	 */
	Message2 getRechargeByFlag(String msgFlag) throws Exception;






}

