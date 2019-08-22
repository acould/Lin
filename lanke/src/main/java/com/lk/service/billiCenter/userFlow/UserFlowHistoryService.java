package com.lk.service.billiCenter.userFlow;

import com.lk.entity.Message2;
import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description 用户流水历史表
 * @create 2019-06-06 16:13
 */
public interface UserFlowHistoryService {


    int save(PageData pd)throws Exception;

    int delete(String id)throws Exception;

    int edit(PageData pd)throws Exception;

    PageData findById(String id)throws Exception;

    PageData findByStoreIdAndCardIdAndFlowTime(PageData pd) throws Exception;

    List<PageData> findByStoreIdAndCardId(PageData pd) throws Exception;

    PageData calMoney(String store_id, String card_id, String[] flow_types, int money_type,
                      String begin_time, String end_time) throws Exception;

    Message2 saveUserFlowHistory(String store_id, String carded, String cardid, int filter_type, int begin_timestamp, int end_timestamp)throws Exception;



}
