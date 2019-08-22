package com.lk.service.system.Membership.impl;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.lk.entity.system.SessionUser;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.util.*;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.billiCenter.request.RequestService;
import com.lk.service.billiCenter.response.ResponseService;
import com.lk.service.hd.CallBack;
import com.lk.service.hd.impl.Business;
import com.lk.service.system.Membership.MembershipManager;
import com.lk.service.system.smslog.SmslogManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.wechat.response.WechatUser;
import com.lk.wechat.util.WeixinUtil;

/** 
 * 说明： 赛事报名表
 * 创建人：洪智鹏
 * 创建时间：2017-02-05
 * @version
 */
@Service("membershipService")
public class MembershipService implements MembershipManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="callBackService")
	private CallBack callBackService;
	@Resource(name = "requestService")
	private RequestService requestService;
	@Resource(name = "responseService")
	private ResponseService responseService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "autoReplyService")
    private AutoReplyService autoReplyService;
	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;
	@Resource(name = "smslogService")
	private SmslogManager smslogService;


    @Resource(name = "sysuserService")
    private SysUserManager sysuserService;
	/**新增
	 * @param 
	 * @throws Exception
	 */
	public String save(PageData pd)throws Exception{
		String result=PublicManagerUtil.TRUE;
		try {
			dao.delete("MembershipMapper.delete", pd);
			dao.save("MembershipMapper.save", pd);
			String str[] = pd.getString("storeids").split(",");
			pd.put("array", str);
			dao.save("MembershipMapper.savenexus", pd);
		} catch (Exception e) {
			result=PublicManagerUtil.FALSE;
		}
		return result;
	}
	
	/**修改Type
	 * @param 
	 * @throws Exception
	 */
	public void editType(PageData pd)throws Exception{
		dao.update("MembershipMapper.editType", pd);
	}

	@Override
	public PageData view(PageData pd) throws Exception {
		PageData pd1=(PageData)dao.findForObject("MembershipMapper.view", pd);
		return pd1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> yxstore(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MembershipMapper.findStoreId", pd);
	}

	@Override
	public PageData selectCard(String card_id) throws Exception {
		return (PageData) dao.findForObject("MembershipMapper.selectCard", card_id);
	}

	@Override
	public JSONObject level(String store_id, JSONObject param,PageData pd) throws Exception {
			JSONObject jsonResult = new JSONObject();
			PageData pdSmslog = new PageData();
			pdSmslog = smslogService.findByPhone(pd.getString("phone"));
			if (StringUtil.isEmpty(pdSmslog) || !pdSmslog.getString("CODE").equals(pd.getString("verificationCode"))) {
				jsonResult.put("errcode", "");
				jsonResult.put("errmsg", "短信验证码不正确");
				return jsonResult;
			}
			// 验证码5分钟有效，现在时间 > 发送时间 + 5分钟
			if (StringUtil.isNotEmpty(pdSmslog)) {
				long sendTime = Tools.str2Date(pdSmslog.getString("CODE_TIME")).getTime() + Const.AVAILABLE_TIME;
				long nowTime = new Date().getTime();
				if (nowTime > sendTime) {
					jsonResult.put("errcode", "");
					jsonResult.put("errmsg", "短信验证码已失效");
					return jsonResult;
				}
			}
			
			//判断store_id是否为空
			if(StringUtil.isEmpty(store_id)){
				return JSONObject.fromObject(ErrUtil.ERR_10002);
			}
			//判断store_id是否有效
			if(!storeService.checkStoreV(store_id)){
				JSONObject result = new JSONObject();
				result.put("errcode", "");
				result.put("errmsg", "客户端未连接！");
				return result;
			}
			Message msg = new Message();
			msg.setBarId(store_id);
			msg.setType(MsgUtil.ADD_MEMBER);
			msg.setFlag(Tools2.getCenterMsgId2());
			msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));
			
			// 通过store_id获取具体需要通讯的客户端连接
			ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
			Business.exc(msg.getFlag(), MsgUtil.BASE_TIME);
			String result = callBackService.result(msg.getFlag());
			System.out.println("======result=====:"+result);
	        if(StringUtil.isNotEmpty(result)){
	    		JSONObject json2 = JSONObject.fromObject(result);
	    		System.out.println("============json2============="+json2);
	    			if("0".equals(json2.getJSONObject("head").getString("errcode"))){
	    				jsonResult.put("errcode", "0");
	    				jsonResult.put("errmsg", "success");
	    			}else{
	    				jsonResult.put("errcode", json2.getJSONObject("head").getString("errcode"));
	    				jsonResult.put("errmsg", json2.getJSONObject("head").getString("errmsg"));
	    			}
	        }else{
				jsonResult = JSONObject.fromObject(ErrUtil.ERR_10037);
	        }
	        Business.businesses.remove(msg.getFlag());
			return jsonResult;
		
	}
	

	@Override
	public JSONObject typeStore(String store_id) throws Exception {
		JSONObject json=new JSONObject();
		PageData pd= (PageData)dao.findForObject("MembershipMapper.typeStore", store_id);
		String typeo=pd.getString("typeo");
		String typet=pd.getString("typet");
		if("1".equals(typeo) && "2".equals(typet)){
			json.put("falg", "T");
		}else  if("2".equals(typeo) && "1".equals(typet)){
			json.put("falg", "F");
		}else{
			json.put("falg", "O");
		}
		return json;
	}

	@Override
	public PageData StoreObject(String STORE_ID) throws Exception {
		return (PageData) dao.findForObject("StoreMapper.findById", STORE_ID);
	}
	
	
	/**
	 * 获取该网吧下所有城市，以及城市里的所有的区域，以及该区域内所有的门店
	 * @return
	 * @throws Exception
	 */
	@Override
    public JSONObject getCityList(String internet_id) throws Exception {
		List<String> storeidList  = (List<String>) dao.findForList("MembershipMapper.findinterbystoreid", internet_id);
        String STORE_IDS=Joiner.on("','").join(storeidList);
		PageData pdCity = new PageData();
        pdCity.put("STORE_IDS", STORE_IDS);
        List<PageData> cityList = (List<PageData>) dao.findForList("MembershipMapper.findCityByInternetId", pdCity);

        for (int i = 0; i < cityList.size(); i++) {
            String city = cityList.get(i).getString("CITY");
            PageData pdArea = new PageData();
            pdArea.put("CITY", city);
            pdArea.put("STORE_IDS", STORE_IDS);
            List<PageData> areaList = (List<PageData>) dao.findForList("MembershipMapper.findAreaByCity", pdArea);
            cityList.get(i).put("areaList", areaList);

            for (int j = 0; j < areaList.size(); j++) {
                String area = areaList.get(j).getString("COUNTY");
                PageData pd = new PageData();
                pd.put("CITY", city);
                pd.put("COUNTY", area);
                pd.put("STORE_IDS", STORE_IDS);
                List<PageData> storeList = (List<PageData>) dao.findForList("MembershipMapper.findStoreByArea", pd);
                areaList.get(j).put("storeList", storeList);
            }
        }
        JSONObject result = new JSONObject();
        result.put("cityList", cityList);
        
        String city = "";
		String area = "";
		if (cityList.size() > 0) {
			city = cityList.get(0).getString("CITY");
			List<PageData> areaList = (List<PageData>) cityList.get(0).get("areaList");
			area = areaList.get(0).getString("COUNTY");
		}
		result.put("city", city);
		result.put("area", area);
        return result;
    }

	@Override
	public String CardIdandUserIdbycard(PageData pdS) throws Exception {
		PageData pdData=(PageData)dao.findForObject("MembershipMapper.CardIdandUserIdbycard", pdS);
		if(StringUtil.isEmpty(pdData)){
			return "F";
		}
		return "T";
	}

	@Override
	public List<PageData> selectCards(PageData pd) throws Exception {
		List<PageData> sy=(List<PageData>)dao.findForList("MembershipMapper.selectCards", pd);
		for(int i=0; i <sy.size();i++){
			List<String> storList=(List<String>)dao.findForList("MembershipMapper.selectCardstoreid", sy.get(i).getString("CARD_ID"));
			List<String> storelist=(List<String>)pd.get("storelist");
			boolean c = storList.containsAll(storelist);
			if(!c){
				sy.remove(i);
			}
		}
		return sy;
	}

	@Override
	public PageData selectfind(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MembershipMapper.selectfind", pd);
	}

	@Override
	public void addmemberfind(PageData pd) throws Exception {
		dao.save("MembershipMapper.savememberfind", pd);
	}

	@Override
	public void information(PageData pd) throws Exception {
		new Thread(new thread(pd.getString("internet_id"),pd.getString("userid"))).start();
	}
	class thread implements Runnable {
		private String internet_id;
		private String userid;
		public thread(String internet_id,String userid) {
			this.internet_id = internet_id;
			this.userid = userid;
		}
        @Override
        public void run() {
        	PageData pd = new PageData();
            try {
            	pd.put("USER_ID", userid);
            	String AUTHORIZER_ACCESS_TOKEN = autoReplyService.getAuthToken(internet_id);
            	WechatUser userInfo = WeixinUtil.getUserInfo(AUTHORIZER_ACCESS_TOKEN, wechatuserService.findByUserId(pd).getString("OPEN_ID"));
            	userInfo.setINTENET_ID(internet_id);
            	userInfo.setNECK_NAME(URLEncoder.encode(userInfo.getNECK_NAME(), "utf-8"));
            	dao.update("MembershipMapper.edit", userInfo);

            	//清理缓存
                sysuserService.clearSessionUser(false, true, true);//清理session
                sysuserService.getBundUserByUserId(userid);
                sysuserService.getByOpenId(userInfo.getOPEN_ID());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

