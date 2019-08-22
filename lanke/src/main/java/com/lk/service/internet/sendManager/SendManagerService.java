package com.lk.service.internet.sendManager;

import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-06-04 17:47
 */
public interface SendManagerService {

    int save(PageData pd)throws Exception;

    int delete(String id)throws Exception;

    int edit(PageData pd)throws Exception;

    PageData findById(String id)throws Exception;

    List<PageData> dataListpage(Page page)throws Exception;

    LayMessage getList(PageData pd, Page page)throws Exception;

    Message saveNotify(PageData pd)throws Exception;

    Message saveNews(PageData pd)throws Exception;

    Message issueNews(PageData pd)throws Exception;

    Message saveActivity(PageData pd)throws Exception;

    Message delRecord(PageData pd) throws Exception;

    LayMessage getUserList(Page page, PageData pd) throws Exception;

    Message loadTempMsg(PageData pd) throws Exception;

    PageData findTempById(String send_id)throws Exception;

    Message delMoreRecord(PageData pd) throws Exception;

    Message loadActMsg(PageData pd)  throws Exception;

    PageData findActById(String send_id) throws Exception;

    Message loadInitTimes(PageData pd) throws Exception;

    List<PageData> findByTypeAndOtherType(PageData pd) throws Exception;
}
