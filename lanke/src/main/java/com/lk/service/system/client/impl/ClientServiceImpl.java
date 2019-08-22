package com.lk.service.system.client.impl;

import com.google.common.base.Joiner;
import com.lk.entity.billecenter.SWQrParam;
import com.lk.service.billiCenter.userBoard.UserBoardService;
import com.lk.service.system.store.StoreManager;
import com.lk.util.PublicManagerUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.client.ClientService;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import org.springframework.web.context.ContextLoader;

/**
 * 客户端管理
 * @Title ClientService.java
 * @author 陈明阳
 * @date 2018年10月18日上午11:41:55
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    public static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);


	@Resource(name = "daoSupport")
	private DaoSupport dao;

    @Resource(name = "storeService")
    private StoreManager storeService;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject list(PageData pd , Page page) throws Exception {
        String keywords = "";
        if (StringUtil.isNotEmpty(pd.get("keywords"))) {
            keywords = pd.getString("keywords").trim();
        }
        JSONObject result = new JSONObject();

        String storeIds = "";
        List<String> storeList = new ArrayList<>(); //缓存中的门店id
        Iterator<Map.Entry<String, ChannelHandlerContext>> it = ChannelCache.channelMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> entry = it.next();
            storeList.add(entry.getKey());
            storeIds += ",";
        }
        if (!storeIds.equals("")) {
            storeIds.substring(0, storeIds.length() - 1);
        }

        if ("在线".equals(pd.getString("online_state"))) {
            pd.put("storeIds", Joiner.on("','").join(storeList));
        } else if ("离线".equals(pd.getString("online_state"))) {
            pd.put("storeIds2", Joiner.on("','").join(storeList));
        }

        Page pa = page;
        pa.setPd(pd);
        //查询数据库信息
        List<PageData> list = (List<PageData>) dao.findForList("ClientMapper.datalistPage", pa);

        for (PageData pdd : list) {
            for (String storeId : storeList) {
                if (pdd.getString("store_id").equals(storeId)) {
                    pdd.put("online_state", "在线");
                    break;
                }
            }
            if (StringUtil.isEmpty(pdd.getString("online_state"))) {
                pdd.put("online_state", "离线");
            }
        }
        result.put("data", list);
        result.put("count", page.getTotalResult());
        result.put("msg", "查询成功");
        result.put("showCount", page.getShowCount());
        result.put("code", "0");
        return result;
    }
	
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("ClientMapper.edit", pd);
	}


    @Override
    public JSONObject loadClientDetail(PageData pd) throws Exception{
        JSONObject result = new JSONObject();
        Page page = (Page) pd.get("page");

        page.setPd(pd);
        List<PageData> list = (List<PageData>) dao.findForList("ClientConnectLogMapper.dataList", page);
        for (PageData pdd : list) {
            pdd.put("create_time", pdd.get("create_time").toString());
        }

        result.put("data", list);
        result.put("count", list.size());
        result.put("msg", "查询成功");
        result.put("showCount", page.getShowCount());
        result.put("code", "0");
        return result;
    }



    /**
     * 发送设置二维码参数线程
     * @param
     * @return
     */
    public static class setQrParam implements Runnable {
		private String store_id;

		public setQrParam(String store_id) {
			this.store_id = store_id;
		}
		@Override
		public void run() {
			try {
				StoreManager storeService = (StoreManager) ContextLoader.getCurrentWebApplicationContext().getBean("storeService");

				JSONObject parameters = new JSONObject();
				parameters.put("appid", storeService.findInternetInfo(store_id).getString("WECHAT_ID"));
				parameters.put("lanke_url", PublicManagerUtil.URL1.substring(0, PublicManagerUtil.URL1.length() - 1));
				parameters.put("component_appid", PublicManagerUtil.APPID);

				UserBoardService userBoardService = (UserBoardService) ContextLoader.getCurrentWebApplicationContext().getBean("userBoardService");
				SWQrParam swQrParam = new SWQrParam();
				swQrParam.setCompany_type(2);
				swQrParam.setParameters(parameters.toString());
				userBoardService.setQrParam(store_id, swQrParam);
			}catch (Exception e){
				log.info("设置扫码上机参数异常。。。。");
			}
		}
	}


}

