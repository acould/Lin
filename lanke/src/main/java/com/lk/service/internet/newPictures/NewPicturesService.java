package com.lk.service.internet.newPictures;

import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description 图片操作——接口
 * @create 2019-04-29 16:46
 */
public interface NewPicturesService {


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


    /**
     * 根据store_id和sort类型查找图片
     * @param pdImg
     * @return
     */
    PageData findByStoreIdAndSort(PageData pdImg) throws Exception;

    /**
     * 根据store_id和type查找图片
     * @param pdImg
     * @return
     */
    List<PageData> findByStoreIdAndType(PageData pdImg) throws Exception;

    /**
     * 根据外键id和sort删除图片
     * @param pd
     * @return
     * @throws Exception
     */
    int delByForeignIdAndSort(PageData pd) throws Exception;

    PageData findByForeignId(String id) throws Exception;

    List<PageData> findByForeignIdAndType(PageData pdImg) throws Exception;
}
