package com.lk.service.billiCenter.consume;


import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWConsume;
import net.sf.json.JSONObject;

import com.lk.util.PageData;


public interface ConsumeService{

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
	 * 发送 顺网--"会员减余额" 接口
	 * @return
	 * @throws Exception
	 */
	Message2 sendConsume(String store_id, SWConsume consume) throws Exception;

	/**
	 * 通过msg的flag获取缓存数据
	 * @param msgFlag
	 * @return
	 * @throws Exception
	 */
	Message2 getConsumeByFlag(String msgFlag) throws Exception;


	Message2 newConsume(String store_id, SWConsume consume)throws Exception;
}

