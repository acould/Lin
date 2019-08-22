package com.lk.service.billiCenter.userSale;

import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQuery;
import net.sf.json.JSONObject;


public interface UserSaleService{


	/**
	 * 发送 顺网--"查询会员消费信息" 接口('使用中' 的用户)
	 * @return
	 * @throws Exception
	 */
	Message2 sendSelUserSale(String store_id, SWQuery query) throws Exception;

	/**
	 * 通过msg的flag获取缓存数据
	 * @param msgFlag
	 * @return
	 * @throws Exception
	 */
	Message2 getSelSWUserSaleByFlag(String msgFlag) throws Exception;


	
}

