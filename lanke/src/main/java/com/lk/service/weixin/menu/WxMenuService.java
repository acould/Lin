package com.lk.service.weixin.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lk.util.PageData;

import net.sf.json.JSONObject;


public interface WxMenuService {
	
	
	/**
	 * 保存单条菜单到数据库
	 * @param pd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveMenu(PageData pd,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据行列序号删除对应的菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void deleteByAppId(PageData pd) throws Exception;
	
	/**
	 * 根据行列序号删除对应的菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteMenuByOrder(PageData pd) throws Exception;
	
	/**
	 * 通过序号（行或者列）查找菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByOrder(PageData pd) throws Exception;
	
	/**
	 * 通过列序号查询子菜单列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findSonByLie(PageData pd) throws Exception;
	
	/**
	 * 调用微信菜单接口，生成默认菜单，并将数据保存
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject defaultMenu(PageData pd) throws Exception;

	/**
	 * 上移下移，改变菜单的序号
	 * @param pd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject move(PageData pd) throws Exception;

	/**
	 * 发布，调用微信接口生成菜单
	 * @param pd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject issue(PageData pd) throws Exception;
	
	/**
	 * 通过公众号appId查询所有的一级菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByAppId(PageData pd) throws Exception;
	

	/**
	 * 根据value值搜索该菜单的文字或者卡券id（发送客服消息）
	 * @param pdMenu
	 * @return
	 * @throws Exception
	 */
	public PageData findByKey(PageData pdMenu) throws Exception;
	
}
