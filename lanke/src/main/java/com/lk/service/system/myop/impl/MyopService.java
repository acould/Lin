package com.lk.service.system.myop.impl;

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
import com.lk.service.system.myop.MyopManager;

/**
 * 说明： 开放平台参数管理
 * 创建人：洪智鹏
 * 创建时间：2017-08-01
 */
@Service("myopService")
public class MyopService implements MyopManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 新增
     *
     * @param pd含开放平台参数管理相关信息
     * @throws Exception
     */
    public void save(PageData pd) throws Exception {
        dao.save("MyopMapper.save", pd);
    }

    /**
     * 删除
     *
     * @param pd包含主键信息
     * @throws Exception
     */
    public void delete(PageData pd) throws Exception {
        dao.delete("MyopMapper.delete", pd);
    }

    /**
     * 修改
     *
     * @param pd包含开放平台参数管理相关信息
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception {
        dao.update("MyopMapper.edit", pd);
    }

    /**
     * 列表
     *
     * @param page包含页面相关信息和检索信息
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("MyopMapper.datalistPage", page);
    }

    /**
     * 列表(全部)
     *
     * @param pd
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("MyopMapper.listAll", pd);
    }

    /**
     * 通过id获取数据
     *
     * @param pd含开放平台参数管理主键
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("MyopMapper.findById", pd);
    }

    /**
     * 批量删除
     *
     * @param ArrayDATA_IDS包含多个开放平台参数管理主键组成的数组
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("MyopMapper.deleteAll", ArrayDATA_IDS);
    }


    /**
	 * 通过缓存或者数据库读取数据COMPONENT_ACCESS_TOKEN
	 * @param pdmyop包含APPID和时间TOKEN_TIME（必须为date格式yyyy-MM-dd HH:mm:ss）,COMPONENT_VERIFY_TICKET(选填)
	 * @throws Exception
	 */
    public PageData findByAppId(PageData pd) throws Exception {
    	 Cache cache = CacheManager.myGzhCahce();                            //得到缓存对象(含有效时间110分钟)
    	//判断ticket是否微信推送的
    	 PageData my = new PageData();
    	 my.put("APPID", pd.getString("APPID"));
    	 my = (PageData)dao.findForObject("MyopMapper.findByAppId", my);    //获取COMPONENT_VERIFY_TICKET
		if(pd.containsKey("COMPONENT_VERIFY_TICKET")){
            if(StringUtil.isEmpty(my)){
            	my = new PageData();
            	my.put("MYOP_ID", pd.getString("APPID"));    //主键
            	my.put("APPID", pd.getString("APPID"));    //appid
            	my.put("COMPONENT_VERIFY_TICKET", pd.getString("COMPONENT_VERIFY_TICKET"));    //读取口令
            	my.put("UPDATE_TIME", Tools.date2Str(new Date()));    //ticket更新时间
            	
            	JSONObject component_access_token = WeChatOpenUtil.getComponent(pd.getString("APPID"), PublicManagerUtil.APPSECRET, pd.getString("COMPONENT_VERIFY_TICKET"));//获取第三方平台component_access_token
        		String aaa = component_access_token.getString("component_access_token");
            	my.put("TOKEN_TIME", Tools.date2Str(new Date()));    //token更新时间
            	my.put("COMPONENT_ACCESS_TOKEN", aaa);    //更新时间
            	this.save(my);
            }else{
            	my.put("COMPONENT_VERIFY_TICKET", pd.getString("COMPONENT_VERIFY_TICKET"));    //读取口令
            	my.put("UPDATE_TIME", Tools.date2Str(new Date()));    //ticket更新时间
    			
            	//查看token时间有没有过期 ，传入的 > 数据库中的，说明时间过期
            	if(((Date) pd.get("TOKEN_TIME")).getTime() > Tools.str2Date(my.get("TOKEN_TIME")+"").getTime()){
            		JSONObject component_access_token = WeChatOpenUtil.getComponent(pd.getString("APPID"), PublicManagerUtil.APPSECRET, my.getString("COMPONENT_VERIFY_TICKET"));//获取第三方平台component_access_token
            		String aaa = component_access_token.getString("component_access_token");
            		my.put("COMPONENT_ACCESS_TOKEN", aaa);
            		my.put("TOKEN_TIME", Tools.date2Str(new Date()));
            	}
            	
            	my.put("COMPONENT_VERIFY_TICKET", pd.getString("COMPONENT_VERIFY_TICKET"));
            	my.put("UPDATE_TIME", Tools.date2Str(new Date()));
            	this.edit(my);
            }
		}else{
			//不是微信推送的，正常调用接口
			Object object = cache.getObject(pd.getString("APPID"));                //按key查
			if (StringUtil.isNotEmpty(object)) {
	            PageData pdd = (PageData) object;
	            return pdd;
	        }
			
			//查看token时间有没有过期 ，传入的 > 数据库中的，说明时间过期
        	if(((Date) pd.get("TOKEN_TIME")).getTime() > Tools.str2Date(my.get("TOKEN_TIME")+"").getTime()){
        		JSONObject component_access_token = WeChatOpenUtil.getComponent(pd.getString("APPID"), PublicManagerUtil.APPSECRET, my.getString("COMPONENT_VERIFY_TICKET"));//获取第三方平台component_access_token
        		String aaa = component_access_token.getString("component_access_token");
        		my.put("COMPONENT_ACCESS_TOKEN", aaa);
        		my.put("TOKEN_TIME", Tools.date2Str(new Date()));
        		this.edit(my);
        	}
		}
		
        //把COMPONENT_ACCESS_TOKEN加入缓存
        cache.insertObject(pd.getString("APPID"), my);
        return my;
    }

}

