package com.lk.service.tb.tbMarketingContext;

import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-04-11 18:03
 */
public interface MarkingContextService {

    /**
     * 保存
     * @param pd
     * @return
     * @throws Exception
     */
    int save(PageData pd) throws Exception;

    /**
     * 根据主键id查询
     * @return
     * @throws Exception
     */
    PageData findById(String id) throws Exception;


    /**
     * 查询列表
     * @param pd （send_type, current_time）
     * @return
     * @throws Exception
     */
    List<PageData> findByTypeAndTime(PageData pd) throws Exception;

    /**
     * 修改
     * @param pdType
     * @return
     */
    int updateSendType(PageData pdType) throws Exception;
}
