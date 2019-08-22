package com.lk.service.internet.terrace.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.WeChatOpenUtil;
import com.lk.wechat.util.WeixinUtil;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.myop.MyopManager;

/** 
 * 说明： 授权的微信公众号
 * 创建人：洪智鹏
 * 创建时间：2017-08-04
 * @version
 */
@Service("terraceService")
public class TerraceService implements TerraceManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="myopService")
	private MyopManager myopService;
	
	/**新增
	 * @param pd授权的微信公众号的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TerraceMapper.save", pd);
	}
	
	/**删除
	 * @param pd授权的微信公众号的主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TerraceMapper.delete", pd);
	}
	
	/**修改
	 * @param pd授权的微信公众号的相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TerraceMapper.edit", pd);
	}
	/**修改
	 * @param pd授权的微信公众号的相关信息
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception{
		dao.update("TerraceMapper.update", pd);
	}
	
	/**修改
	 * @param pd授权的微信公众号的相关信息
	 * @throws Exception
	 */
	public void update1(PageData pd)throws Exception{
		dao.update("TerraceMapper.update1", pd);
	}
	
	/**列表
	 * @param page页面检索字段信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TerraceMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd无
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TerraceMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd 授权的微信公众号的主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TerraceMapper.findById", pd);
	}
	
	/**通过网吧id获取数据
	 * @param pd 包含网吧id主键
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByIntenet_id(PageData pd) throws Exception {
		return (List<PageData>)dao.findForObject("TerraceMapper.findByIntenet_id", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS授权的微信公众号组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TerraceMapper.deleteAll", ArrayDATA_IDS);
	}

	/*public PageData findByAppId(PageData pd)throws Exception {
		PageData myop=new PageData();
	    myop=(PageData)dao.findForObject("TerraceMapper.findByAppId", pd);
		 if(myop!=null){
			 return myop;
		 }else{
			 PageData my=new PageData();
			 my.put("APPID",PublicManagerUtil.APPID);
			 my.put("TOKEN_TIME", pd.getString("INSERT_TIME"));
			 my= this.myopService.findByAppId(my);
			 PageData myGzh=new PageData();
			 myGzh.put("APP_ID", pd.get("APP_ID"));
			 myGzh= this.findByAppId(myGzh);
			 if(myGzh!=null){
				 JSONObject component_access_token=WeChatOpenUtil.getAuthorizerRefresh(PublicManagerUtil.APPID, pd.getString("APP_ID"), my.getString("COMPONENT_ACCESS_TOKEN"),myGzh.getString("AUTHORIZER_REFRESH_TOKEN"));
				 String aaa=component_access_token.getString("authorizer_access_token");
				 String bbb=component_access_token.getString("authorizer_refresh_token");
				 myGzh.put("AUTHORIZER_ACCESS_TOKEN", aaa);
				 myGzh.put("AUTHORIZER_REFRESH_TOKEN", bbb);
				 myGzh.put("INSERT_TIME",  Tools.date2Str(new Date()));
				 this.edit(myGzh);
			 }
			 return myGzh;
		 }
	}*/
	
	/**
	 * 通过appid和新增时间去获取AUTHORIZER_ACCESS_TOKEN(通过缓存读取数据AUTHORIZER_ACCESS_TOKEN)
	 * @param pd 新增时间--INSERT_TIME
	 * @throws Exception
	 */
	public PageData findByAppId(PageData pd)throws Exception {
		Cache cache = CacheManager.myGzhCahce();  						//得到缓存对象(含有效时间110分钟)
		Object object = cache.getObject(pd.getString("APP_ID"));
		if(object != null){
			 PageData pdd = (PageData) object;
			 return pdd;
		 }else{
			 //获取第三平台的COMPONENT_ACCESS_TOKEN
			 PageData pdMyOp = new PageData();
			 pdMyOp.put("APPID",PublicManagerUtil.APPID);
			 pdMyOp.put("TOKEN_TIME", Tools.str2Date(pd.getString("INSERT_TIME")));
			 pdMyOp = myopService.findByAppId(pdMyOp);
			 
			 //获取公众号的AUTHORIZER_REFRESH_TOKEN
			 PageData pdTerrace=new PageData();
			 pdTerrace.put("APP_ID", pd.get("APP_ID"));
			 pdTerrace = (PageData)dao.findForObject("TerraceMapper.findByAppId", pdTerrace);
			 if(pdTerrace != null){
				 //判断时间是否过期 ，传入的 > 数据库中的，说明时间过期
				 if(Tools.str2Date(pd.getString("INSERT_TIME")).getTime() > Tools.str2Date(pdTerrace.get("INSERT_TIME")+"").getTime()){
					 //过期了通过刷新令牌，来刷新token
					 JSONObject terrace = WeChatOpenUtil.getAuthorizeRefresh(pdMyOp.getString("APPID"), pdTerrace.getString("APP_ID"), 
							 pdMyOp.getString("COMPONENT_ACCESS_TOKEN"),pdTerrace.getString("AUTHORIZER_REFRESH_TOKEN"));
					 pdTerrace.put("AUTHORIZER_ACCESS_TOKEN", terrace.getString("authorizer_access_token"));
					 pdTerrace.put("AUTHORIZER_REFRESH_TOKEN", terrace.getString("authorizer_refresh_token"));
					 pdTerrace.put("INSERT_TIME",  Tools.date2Str(new Date()));
					 this.edit(pdTerrace);
            	 }
			 }
			 //把AUTHORIZER_ACCESS_TOKEN加入缓存
			 cache.insertObject(pd.getString("APP_ID"), pdTerrace);
			 return pdTerrace;
		 }
}

	/**
	 * 通过网吧id去获取授权信息
	 * @param pd 网吧id--INTENET_ID
	 * @throws Exception
	 */
	public PageData findByInternetId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TerraceMapper.findByInternetId", pd);
	}

	/**
	 * 通过网吧id去获取授权信息
	 * @param pd 微信appid--AppId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByappId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("TerraceMapper.findByappId", pd);
	}
	
	
	/**
	 * 实时获取js-sdk中的api_ticket(包含缓存)
	 * @param pd(传入APP_ID,TOKEN)
	 * @return
	 * @throws Exception
	 */
	public String getApiTicket(PageData pd) throws Exception{//由于获取api_ticket 的api调用次数非常有限，频繁刷新api_ticket 会导致api调用受限，影响自身业务，开发者需在自己的服务存储与更新api_ticket。
		//得到缓存对象(含有效时间110分钟)
		Cache cache = CacheManager.apiCache();  						
		Object object = cache.getObject("api_ticket"+pd.getString("APP_ID"));
		if(StringUtil.isNotEmpty(object)) {
			return (String) object;
		}else {
			String api_ticket = WeixinUtil.getApiTicket(pd.getString("TOKEN"));
			cache.insertObject("api_ticket"+pd.getString("APP_ID"), api_ticket);
			return api_ticket;
		}
	}
    public String getApiTicketByInternetId(PageData pd) throws Exception{//由于获取api_ticket 的api调用次数非常有限，频繁刷新api_ticket 会导致api调用受限，影响自身业务，开发者需在自己的服务存储与更新api_ticket。
        //得到缓存对象(含有效时间110分钟)
        Cache cache = CacheManager.apiCache();
        Object object = cache.getObject("api_ticket"+pd.getString("internet_id"));
        if(StringUtil.isNotEmpty(object)) {
            return (String) object;
        }else {
            String api_ticket = WeixinUtil.getApiTicket(pd.getString("token"));
            cache.insertObject("api_ticket"+pd.getString("internet_id"), api_ticket);
            return api_ticket;
        }
    }
	
	/**
	 * 实时获取js-sdk中的jsapi_ticket(包含缓存)
	 * @param pd(传入APP_ID,TOKEN)
	 * @return
	 * @throws Exception
	 */
	public String getJsApiTicket(PageData pd) throws Exception{
		//得到缓存对象(含有效时间110分钟)
		Cache cache = CacheManager.jsapiCache();  						
		Object object = cache.getObject("jsapi_ticket"+pd.getString("APP_ID"));
		if(StringUtil.isNotEmpty(object)) {
			return (String) object;
		}else {
			String jsapi_ticket = WeixinUtil.getJsTicket(pd.getString("TOKEN"));
			cache.insertObject("jsapi_ticket"+pd.getString("APP_ID"), jsapi_ticket);
			return jsapi_ticket;
		}
	}

    public String getJsApiTicketByInternetId(PageData pd) throws Exception{
        //得到缓存对象(含有效时间110分钟)
        Cache cache = CacheManager.jsapiCache();
        Object object = cache.getObject("jsapi_ticket"+pd.getString("internet_id"));
        if(StringUtil.isNotEmpty(object)) {
            return (String) object;
        }else {
            String jsapi_ticket = WeixinUtil.getJsTicket(pd.getString("token"));
            cache.insertObject("jsapi_ticket"+pd.getString("internet_id"), jsapi_ticket);
            return jsapi_ticket;
        }
    }
}

