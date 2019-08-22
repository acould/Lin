package com.lk.service.system.store.impl;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.store.StoreReviewService;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-05-14 16:49
 */
@Service
public class StoreReviewServiceImpl implements StoreReviewService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "storeService")
    private StoreManager storeService;


    /******************************* 计费审核 ******************************/
    /**
     * 查询门店加V申请/改IP申请的门店及其信息
     * @param page (必填:网吧id--internetId,门店状态--STATE)
     * @return (Company_Name--公司名称,STORE_NAME--门店名称,INTENET_NAME--网吧名称,STATE--门店状态,STORE_ID--门店id)
     * @throws Exception
     */
    @Override
    public LayMessage getStoreVList(PageData pd, Page page) throws Exception{

        page.setPd(pd);

        String value = pd.getString("DATED");
        List<PageData> list = null;
        if("1".equals(value)){//过期
            list = (List<PageData>) dao.findForList("StoreMapper.listPageaddv2", page);
        }else if("2".equals(value)){//没有过期
            list = (List<PageData>) dao.findForList("StoreMapper.listPageaddv3", page);
        }else{//没有条件的
            list = (List<PageData>) dao.findForList("StoreMapper.listPageaddv", page);
        }


        return LayMessage.ok(page.getTotalResult(), list, page.getShowCount());
    }

    @Override
    public Message toOfficial(PageData pd) throws Exception {

        //默认延长一年
        pd.put("STORE_ID", pd.getString("store_id"));
        PageData pdV = storeService.findStoreVById(pd);
        Date date = Tools.str2Date(pdV.getString("EXPIRATION_TIME"), "yyyy-MM-dd");

        if(date.getTime() < new Date().getTime()){
            date = new Date();
        }

        String time = Tools.date2Str(Tools.sAddDays(date, 365), "yyyy-MM-dd");

        pd.put("time", time);
        pd.put("CHOOSE_PACKAGE", "1");
        int num = (int) dao.update("StoreMapper.editTimeByStoreId", pd);//修改套餐，并延长
        if(num > 0){
            //修改缓存
            Cache storeVCache = CacheManager.getStoreVCache();
            storeVCache.insertObject(PublicManagerUtil.client_prefix + pd.getString("store_id"), "1");
            return Message.ok("转正成功");
        }

        return Message.error("找不到数据");
    }

    @Override
    public Message toDelay(PageData pd) throws Exception {

        if(StringUtil.isEmpty(pd.get("month"))){
            return Message.error("请输入月份");
        }

        int month = Math.round(Float.parseFloat(pd.getString("month")));

        pd.put("STORE_ID", pd.getString("store_id"));
        PageData pdV = storeService.findStoreVById(pd);

        Date date = Tools.str2Date(pdV.getString("EXPIRATION_TIME"), "yyyy-MM-dd");
        String time = Tools.date2Str(Tools.sAddMonth(date, month), "yyyy-MM-dd");

        pd.put("time", time);
        pd.remove("CHOOSE_PACKAGE");
        int num = (int) dao.update("StoreMapper.editTimeByStoreId", pd);//只延长时长
        if(num > 0){
            //修改缓存
            Cache storeVCache = CacheManager.getStoreVCache();
            storeVCache.insertObject(PublicManagerUtil.client_prefix + pd.getString("store_id"), "1");
            return Message.ok("延长成功");
        }

        return Message.error("找不到数据");
    }


    /******************************* 银联审核 ******************************/




    /******************************* 嘉联审核 ******************************/



}
