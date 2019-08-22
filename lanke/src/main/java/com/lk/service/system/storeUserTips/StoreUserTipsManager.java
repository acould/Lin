package com.lk.service.system.storeUserTips;

import javax.servlet.http.HttpServletRequest;

import com.lk.util.PageData;

import net.sf.json.JSONObject;

public interface StoreUserTipsManager {

    /**
     * 新增
     *
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd) throws Exception;

    /**
     * 修改
     * 通过门店id修改会员标签
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception;


    /**
     * 根据id查询标签信息
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception;
    
    /**
     * 根据id查询标签信息
     * @param request
     * @return
     * @throws Exception
     */
    public JSONObject findById1(HttpServletRequest request) throws Exception;

}
