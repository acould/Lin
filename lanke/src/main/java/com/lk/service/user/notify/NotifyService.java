package com.lk.service.user.notify;

import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-06-08 16:01
 */
public interface NotifyService {


    int save(PageData pd)throws Exception;

    int delete(String id)throws Exception;

    int edit(PageData pd)throws Exception;

    PageData findById(String id)throws Exception;


    List<PageData> findByOpenId(String open_id) throws Exception;

    List<PageData> findUnreadByOpenId(PageData pd) throws Exception;
}
