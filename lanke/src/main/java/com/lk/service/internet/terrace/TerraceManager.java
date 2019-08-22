package com.lk.service.internet.terrace;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 授权的微信公众号接口
 * 创建人：洪智鹏
 * 创建时间：2017-08-04
 * @version
 */
public interface TerraceManager{

	/**新增
	 * @param pd授权的微信公众号的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd授权的微信公众号的主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd授权的微信公众号的相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	/**修改
	 * @param pd授权的微信公众号的相关信息
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception;
	
	/**修改
	 * @param pd授权的微信公众号的相关信息
	 * @throws Exception
	 */
	public void update1(PageData pd)throws Exception;
	
	/**列表
	 * @param page页面检索字段信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd无
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd 授权的微信公众号的主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过网吧id获取数据
	 * @param pd 包含网吧id主键
	 * @throws Exception
	 */
	public List<PageData> findByIntenet_id(PageData pd) throws Exception;
	/**批量删除
	 * @param ArrayDATA_IDS授权的微信公众号组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 通过appid和新增时间去获取AUTHORIZER_ACCESS_TOKEN(通过缓存读取数据AUTHORIZER_ACCESS_TOKEN)
	 * @param pd 新增时间--INSERT_TIME
	 * @throws Exception
	 */
	public PageData findByAppId(PageData pdmyop)throws Exception;
	
	/**
	 * 通过网吧id去获取授权信息
	 * @param pd 网吧id--INTENET_ID
	 * @throws Exception
	 */
	public PageData findByInternetId(PageData pd) throws Exception;

	/**
	 * 通过网吧id去获取授权信息
	 * @param pd 微信appid--AppId
	 * @throws Exception
	 */
	public List<PageData> findByappId(PageData pd) throws Exception;

	/**
	 * 实时获取js-sdk中的api_ticket(包含缓存)
	 * @param pd(传入APP_ID,TOKEN)
	 * @return
	 * @throws Exception
	 */
	public String getApiTicket(PageData pd) throws Exception;
	public String getApiTicketByInternetId(PageData pd) throws Exception;

	/**
	 * 实时获取js-sdk中的jsapi_ticket(包含缓存)
	 * @param pd(传入APP_ID,TOKEN)
	 * @return
	 * @throws Exception
	 */
	public String getJsApiTicket(PageData pd) throws Exception;
	public String getJsApiTicketByInternetId(PageData pd) throws Exception;

	
	
}

