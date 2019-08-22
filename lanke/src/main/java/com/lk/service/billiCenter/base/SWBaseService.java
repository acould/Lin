package com.lk.service.billiCenter.base;

import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQuery;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author myq
 * @description
 * @create 2019-04-16 17:18
 */
public interface SWBaseService {

    Message2 sendToSW(String store_id, Object obj, String msg_type) throws Exception;


    Message2 getMsgFromCache(String msg_flag) throws Exception;
}
