package com.lk.service.intuser.wxML;

import com.lk.entity.Message;
import com.lk.util.PageData;

/**
 * @author myq
 * @description
 * @create 2019-06-14 11:31
 */
public interface WxMLService {

    Message loadML(PageData pd) throws Exception;

    Message loadRushOrder(PageData pd)  throws Exception;

    Message rushPay(PageData pd) throws Exception;

    Message handleJLCallback(PageData pd);

    Message readAct(PageData pd) throws Exception;
}
