package com.lk.service.internet.jiaLian;

import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.jiaLian.AuditResult;
import com.lk.util.PageData;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author myq
 * @description 嘉联支付业务——接口
 * @create 2019-04-29 15:36
 */
public interface JiaLianService {


    /**
     * 新增
     * @param pd
     * @throws Exception
     */
    int save(PageData pd)throws Exception;


    int saveMatchStore(PageData pd)throws Exception;

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
     * 根据store_id
     * @param id
     * @return
     * @throws Exception
     */
    PageData findByStoreId(String id)throws Exception;

    /**
     * 分页列表
     * @param page
     * @return
     * @throws Exception
     */
    List<PageData> dataListpage(Page page) throws Exception;


    Message saveShop(PageData pd) throws Exception;

    void saveImg(PageData pd, int type, String[] sortList) throws Exception;

    PageData putImg(PageData pd, PageData pdImg) throws Exception;

    Message delShop(PageData pd) throws Exception;


    LayMessage getList(PageData pd, Page page) throws Exception;

    boolean handleShopAudit(JSONObject obj) throws Exception;

    int editByShopNo(PageData pd) throws Exception;

    PageData findByShopNo(String merchNo) throws Exception;

    List<PageData> getAuditInfo(String id) throws Exception;

    Message updateAuditStatus(PageData pd) throws Exception;

    PageData findPaymentByStoreId(String store_id)throws Exception;

    Message delImg(PageData pd)throws Exception;

    PageData findByPhone(PageData pd)throws Exception;

    List<PageData> getOpenList(String internet_id)throws Exception;

    Message shareShopNo(PageData pd)throws Exception;

    int deleteMatchStoreByStoreId(PageData pd)throws Exception;

    int deleteMatchStoreByForeignId(PageData pd)throws Exception;
}
