package com.lk.service.system.store;

import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.util.PageData;

/**
 * @author myq
 * @description 审核业务接口
 * @create 2019-05-14 16:49
 */
public interface StoreReviewService {

    /******************************* 计费审核 ******************************/
    LayMessage getStoreVList(PageData pd, Page page) throws Exception;

    Message toOfficial(PageData pd) throws Exception;

    Message toDelay(PageData pd) throws Exception;


    /******************************* 银联审核 ******************************/




    /******************************* 嘉联审核 ******************************/



}
