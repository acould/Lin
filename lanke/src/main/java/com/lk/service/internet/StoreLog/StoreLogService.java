package com.lk.service.internet.StoreLog;

import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description 门店审核业务 —— 接口
 * @create 2019-04-29 16:42
 */
public interface StoreLogService {


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


    List<PageData> findByForeignId(PageData pd)throws Exception;
}
