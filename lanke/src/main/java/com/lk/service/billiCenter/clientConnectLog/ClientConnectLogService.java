package com.lk.service.billiCenter.clientConnectLog;

import com.lk.util.PageData;

/**
 * @author myq
 * @description 客户端连接日志 业务
 * @create 2019-04-16 15:26
 */
public interface ClientConnectLogService {

    /**
     * 新增
     * @param pd
     * @throws Exception
     */
    int save(PageData pd)throws Exception;

    /**
     * 删除
     * @param id
     * @throws Exception
     */
    int delete(String id)throws Exception;


    /**
     * 修改
     * @param pd
     * @return
     * @throws Exception
     */
    int edit(PageData pd)throws Exception;



    /**
     * 根据主键查找id
     * @param id
     * @return
     * @throws Exception
     */
    PageData findById(String id)throws Exception;



}
