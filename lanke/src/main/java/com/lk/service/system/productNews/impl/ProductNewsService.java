package com.lk.service.system.productNews.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.service.system.productNews.ProductNewsManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import net.sf.json.JSONObject;

/**
 * 产品动态
 * 
 * @author lzh
 */
@Service("productNewsService")
public class ProductNewsService implements ProductNewsManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 查询版本预告信息
	 * 
	 * @throws Exception
	 */
	@Override
	public PageData list() throws Exception {
		return (PageData) dao.findForObject("ProductNewsMapper.list", null);
	}

	/**
	 * 编辑版本预告信息
	 * 
	 * @throws Exception
	 */
	@Override
	public JSONObject edit(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		String state = pd.get("state").toString();
		try {
			if (state.equals("add")) {// 新增
				dao.save("ProductNewsMapper.save", pd);
			} else {// 编辑
				dao.save("ProductNewsMapper.edit", pd);
			}
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "编辑成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统繁忙,请稍后再试!");
		}
		return json;
	}

	/**
	 * 删除版本预告信息
	 * 
	 * @throws Exception
	 */
	@Override
	public JSONObject deleteNews() throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.delete("ProductNewsMapper.deleteNews", null);
			json.put("message", "操作成功");
			json.put("result", PublicManagerUtil.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "系统繁忙,请稍后再试");
			json.put("result", PublicManagerUtil.FAIL);
		}
		return json;
	}

}
