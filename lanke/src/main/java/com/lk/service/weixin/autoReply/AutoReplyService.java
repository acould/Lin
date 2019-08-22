package com.lk.service.weixin.autoReply;

import java.util.List;

import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.util.PageData;


public interface AutoReplyService {

	/**
	 * 首页列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;

	/**
	 * 新增保存
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject save(PageData pd) throws Exception;
	
	/**
	 * 修改
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject edit(PageData pd) throws Exception;
	
	/**
	 * 删除
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject delete(PageData pd) throws Exception;

	/**+
	 * 批量删除
	 * @param arrayDATA_IDS
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteAll(String[] arrayDATA_IDS) throws Exception;
	
	/**
	 * 根据主键找对象
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 根据事件类型查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByEvent(PageData pd) throws Exception;

	/**
	 * 上传图片
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject uploadImg(PageData pd) throws Exception;

	/**
	 * 微信自动回复
	 * @param string
	 * @param openid
	 * @param string2
	 * @return
	 * @throws Exception
	 */
	public JSONObject wxReply(PageData pdReply) throws Exception;
	
	/**
	 * 获取微信凭证token
	 * @param internetId为网吧id
	 * @throws Exception
	 */
	public String getAuthToken(String internetId) throws Exception;

}
