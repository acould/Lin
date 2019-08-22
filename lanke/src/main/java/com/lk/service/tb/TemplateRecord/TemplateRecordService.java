package com.lk.service.tb.TemplateRecord;

import com.lk.util.PageData;

/**
 * @author myq
 * @description
 * @create 2019-04-12 15:33
 */
public interface TemplateRecordService {

    /**
     * 保存
     * @param pd
     * @return
     * @throws Exception
     */
    int save(PageData pd) throws Exception;

    /**
     * 保存
     * @param pd
     * @return
     * @throws Exception
     */
    int update(PageData pd) throws Exception;



    PageData findById(String id) throws Exception;

}
