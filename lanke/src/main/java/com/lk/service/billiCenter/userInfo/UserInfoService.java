package com.lk.service.billiCenter.userInfo;

import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQuery;
import net.sf.json.JSONObject;


public interface UserInfoService{


	/**
	 * 获取会员信息
	 * @return
	 * @throws Exception
	 */
//	public JSONObject userInfo(String store_id, JSONObject json)throws Exception;


    /**
     * 检查参数
     * @param json
     * @return
     */
    public JSONObject check(JSONObject json);


	/**
	 * 发送 顺网--"查询会员信息" 接口
	 * @return
	 * @throws Exception
	 */
	Message2 sendSelUserInfo(String store_id, SWQuery query) throws Exception;

	/**
	 * 通过msg的flag获取缓存数据
	 * @param msgFlag
	 * @return
	 * @throws Exception
	 */
	Message2 getSelSWUserByFlag(String msgFlag) throws Exception;


	/**
	 * 调用接口获取用户信息
	 * @param store_id
	 * @param card_id
	 * @return
	 * @throws Exception
	 */
	Message2 getSWUser(String store_id, String card_id)throws Exception;
}

