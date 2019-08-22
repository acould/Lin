package com.lk.service.internet.internetStaff;

import com.lk.entity.Page;
import com.lk.util.PageData;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-04-10 18:03
 */
public interface InternetStaffService {

    /**
     * 新增网吧服务人员
     * @param pd (name,open_id,store_id,internet_id)
     * @param perms (数组：接收的服务项)
     * @return
     * @throws Exception
     */
    JSONObject saveStaff(PageData pd, String[] perms) throws Exception;


    /**
     * 删除网吧服务人员
     * @param id 主键id
     * @return
     */
    JSONObject delStaff(String id) throws Exception;


    /**
     * 根据主键查
     * @param id
     * @return
     * @throws Exception
     */
    PageData findById(String id) throws Exception;


    /**
     * 获取二维码连接
     * @return
     * @throws Exception
     */
    String getQrcodeUrl(String internet_id) throws Exception;


    /**
     * 分页加载列表
     * @param pd (internet_id,)
     * @param page (showCount展示数量,currentPage当前页)
     * @return
     */
    JSONObject loadStaffList(PageData pd, Page page) throws Exception;

    /**
     * 获取网吧所有的服务人员
     * @param internet_id
     * @return
     * @throws Exception
     */
    List<PageData> getInternetStaff(String internet_id) throws Exception;

    /**
     * 获取门店（可通用）
     * @return
     * @throws Exception
     */
    List<PageData> getStoreList(String internet_id, String roleId, String userId) throws Exception;

    JSONObject editState(PageData pd) throws Exception;
}
