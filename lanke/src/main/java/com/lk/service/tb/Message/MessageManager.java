package com.lk.service.tb.Message;

import java.util.List;

import com.lk.entity.Page;
import net.sf.json.JSONObject;

import com.lk.util.PageData;

public interface MessageManager {

	
	/**
	 * 保存数据
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;

	/**
	 * 修改数据
	 * @param pdMessage
	 * @throws Exception
	 */
	public void edit(PageData pdMessage) throws Exception;

	/**
	 * 查询未读的消息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByStateOrType(PageData pd) throws Exception;

	/**
	 * 加载首页数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadMessage(PageData pd, Page page) throws Exception;
	
	/**
	 * 查询消息数量
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Integer findNumber(PageData pd) throws Exception;

	/**
	 * 获取消息的主表信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject showDetail(PageData pd) throws Exception;
	
	
	
}
