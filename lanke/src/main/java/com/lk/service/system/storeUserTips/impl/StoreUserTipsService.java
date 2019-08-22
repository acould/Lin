package com.lk.service.system.storeUserTips.impl;

import com.lk.controller.base.BaseController;
import com.lk.dao.DaoSupport;
import com.lk.service.system.storeUserTips.StoreUserTipsManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Tools;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.tools.Tool;

@Service("storeUserTipsService")
public class StoreUserTipsService extends BaseController implements StoreUserTipsManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 新增标签
     *
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd) throws Exception {
        dao.save("StoreUserTipsMapper.save", pd);
    }

    /**
     * 修改
     * 通过门店id修改会员标签
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception {
        dao.update("StoreUserTipsMapper.edit", pd);
    }

    /**
     * 根据id查询标签信息
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("StoreUserTipsMapper.findById", pd);
    }
    
    /**
     * 根据id查询标签信息
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject findById1(HttpServletRequest request) throws Exception {
    	JSONObject json = new JSONObject();
    	PageData pd = new PageData();
    	
    	// 获取传入参数
    	double consumeHigh = Double.parseDouble(request.getParameter("consume_high"));
    	double consumeLow = Double.parseDouble(request.getParameter("consume_low"));
    	double liveHigh = Double.parseDouble(request.getParameter("live_high"));
    	double liveLow = Double.parseDouble(request.getParameter("live_low"));
    	double balanceHigh = Double.parseDouble(request.getParameter("balance_high"));
    	double balanceLow = Double.parseDouble(request.getParameter("balance_low"));
    	pd.put("store_id", request.getParameter("STORE_ID"));
    	PageData result = (PageData) dao.findForObject("StoreUserTipsMapper.findById", pd);
    	
    	// 保存
    	pd.put("consume_high", consumeHigh);
    	pd.put("consume_low", consumeLow);
    	pd.put("balance_high", balanceHigh);
    	pd.put("balance_low", balanceLow);
    	pd.put("live_high", liveHigh);
    	pd.put("live_low", liveLow);
    	
    	if (result == null) {   // 更新数据
			try {               //新增
				pd.put("create_time", Tools.date2Str(new Date()));
				pd.put("update_time", Tools.date2Str(new Date()));
				this.save(pd);  // 新增会员标签
				json.put("result", PublicManagerUtil.TRUE);
				json.put("message", "保存成功！");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "系统繁忙,请稍后再试!");
			}
		} else {                // 修改
			try {
				pd.put("update_time", Tools.date2Str(new Date()));
				this.edit(pd);  // 通过门店id修改会员标签
				json.put("result", PublicManagerUtil.TRUE);
				json.put("message", "保存成功！");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "系统繁忙,请稍后再试!");
			}
		}
        return json;
    }
}
