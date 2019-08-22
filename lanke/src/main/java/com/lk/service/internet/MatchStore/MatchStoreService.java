package com.lk.service.internet.MatchStore;

import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description 门店与各业务主键id的关联表——接口
 * @create 2019-04-29 16:39
 */
public interface MatchStoreService {


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


    List<PageData> listByMatchesId(String id)throws Exception;
}
