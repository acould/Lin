package com.lk.service.system.intenet.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import com.lk.service.system.myop.MyopManager;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.WeChatOpenUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.Intenet;
import com.lk.util.PageData;
import com.lk.service.system.intenet.IntenetManager;

/** 
 * 说明： 网吧
 * 创建人：洪智鹏
 * 创建时间：2016-10-20
 * @version
 */
@Service("intenetService")
public class IntenetService implements IntenetManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;


    @Resource(name = "myopService")
    private MyopManager myopService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("IntenetMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("IntenetMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("IntenetMapper.edit", pd);
	}
	
	/**通过公众号帐号基本信息修改
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception{
		dao.update("IntenetMapper.update", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("IntenetMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("IntenetMapper.listAll", pd);
	}
	
	/**默认显示网吧信息列表admin(全部)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> IntenetlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("IntenetMapper.IntenetlistPage", page);
	}
	
	/**
	 * 通过id获取数据
	 * 通过网吧id获取网吧信息
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("IntenetMapper.findById", pd);
	}
	
	/**
	 * 通过id获取数据
	 * 通过网吧id获取网吧信息
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findById1(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("IntenetMapper.findById1", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("IntenetMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 通过网吧id获取网吧信息
	 * @param orgId (必填:网吧id--INTENET_ID)
	 * @return 指定网吧信息
	 * @throws Exception
	 */
	public Intenet getIntenetById(String orgId) throws Exception {
		return (Intenet)dao.findForObject("IntenetMapper.getIntenetById", orgId);
	}
	
	
	public Intenet getIntenetByWeiXinId(String angetId)throws Exception {
		return (Intenet)dao.findForObject("IntenetMapper.getIntenetByWeiXinId", angetId);
	}

	/**
	 * 解除授权以后修改sys_intenet状态
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	@Override
	public void updateState(PageData pd) throws Exception {
		dao.update("IntenetMapper.updateState", pd);
	}

	/**
	 * 通过网吧id获取揽客绑定手机号
	 * @throws Exception
	 */
	@Override
	public PageData getPhone(String INTENET_ID) throws Exception {
		PageData pd = new PageData();
		pd.put("INTENET_ID", INTENET_ID);
		return (PageData)dao.findForObject("IntenetMapper.getPhone", pd);
	}

	/**
	 * 通过微信id获取揽客绑定手机号
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByappid(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("IntenetMapper.findByappid", pd);
	}

	/**
	 * 通过USERNAME获取揽客绑定手机号
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByUserName(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("IntenetMapper.findByUserName", pd);
	}


    @Override
	public List<PageData> findByState(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("IntenetMapper.findByState", pd);
	}


    @Override
    public PageData findByUserIdAndInternetId(PageData pd) throws Exception{
        return (PageData) dao.findForObject("IntenetMapper.findByUserIdAndInternetId", pd);
    }

    /**
     * 获取更新公众号头像后的公众号信息
     * @param internet_id
     * @param isGetHeadImg
     * @return
     * @throws Exception
     */
    @Override
    public PageData findByInternetId(String internet_id, boolean isGetHeadImg) throws Exception{
        PageData pdInternet = new PageData();
        pdInternet.put("INTENET_ID", internet_id);
        pdInternet = this.findById(pdInternet);

        // 没头像时，自动去获取头像信息.
        if(isGetHeadImg && StringUtil.isNotEmpty(pdInternet) && StringUtil.isEmpty(pdInternet.getString("HEAD_IMG"))){
            PageData pdMyop = new PageData();
            pdMyop.put("APPID", PublicManagerUtil.APPID);
            pdMyop.put("TOKEN_TIME", Tools.sAddHours(new Date(), -1));
            pdMyop = myopService.findByAppId(pdMyop);

            String componentAccessToken = pdMyop.getString("COMPONENT_ACCESS_TOKEN");
            JSONObject json = WeChatOpenUtil.getAuthorizerInfo(PublicManagerUtil.APPID, pdInternet.getString("WECHAT_ID"), componentAccessToken);
            pdInternet.put("HEAD_IMG", json.getJSONObject("authorizer_info").get("head_img"));
            this.edit(pdInternet);
        }
        return pdInternet;
    }

}

