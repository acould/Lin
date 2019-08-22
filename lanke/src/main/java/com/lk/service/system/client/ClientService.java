package com.lk.service.system.client;

import com.lk.entity.Page;
import net.sf.json.JSONObject;

import com.lk.util.PageData;

/**
 * 客户端管理
 * @Title ClientManager.java
 * @author 陈明阳
 * @date 2018年10月18日上午11:41:47
 */
public interface ClientService {
	/**
	 * 客户端管理列表查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject list(PageData pd, Page page) throws Exception;
	
	/**
     * 修改
     * 修改在线时间
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception;

    /**
     * 加载时间列表
     * @param pd
     * @return
     */
    JSONObject loadClientDetail(PageData pd) throws Exception;


}

