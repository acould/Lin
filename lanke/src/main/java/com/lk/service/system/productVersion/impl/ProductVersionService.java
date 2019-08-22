package com.lk.service.system.productVersion.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.productVersion.ProductVersionManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * 产品版本
 * 
 * @author lzh
 */
@Service("productVersionService")
public class ProductVersionService implements ProductVersionManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 查询版本列表
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject lists(Page page) throws Exception {
		JSONObject json = new JSONObject();
		List<PageData> list = (List<PageData>) dao.findForList("ProductVersionMapper.list", null);
		json.put("data", list);
		json.put("code", "0");
		return json;
	}

	/**
	 * 查询指定版本信息
	 * 
	 * @throws Exception
	 */
	@Override
	public PageData showVersion(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ProductVersionMapper.showVersion", pd);
	}

	/**
	 * 查询指定版本详情
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ProductVersionMapper.showDetail", pd);
	}

	/**
	 * 新增版本日志
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public JSONObject editVersion(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		PageData pd1 = new PageData();
		PageData pd2 = new PageData();
		String state = pd.getString("type");
		Integer sum = Integer.parseInt(pd.get("sum").toString());
		if (state.equals("add")) {// 新增
			pd1.put("id", StringUtil.get32UUID());// 放入id
			pd2.put("version_id", pd1.get("id").toString());
			pd1.put("version", pd.get("version").toString());// 版本号
			pd1.put("updatetime", pd.get("updatetime").toString());// 上线时间
			try {
				if(sum == 0) {
					String type = "state" + 1;
					String content = "content" + 1;
					pd2.put("id", StringUtil.get32UUID());// 放入id
					pd2.put("type", pd.get(type)); // 放入类型
					pd2.put("content", pd.get(content)); // 放入内容
					dao.save("ProductVersionMapper.saveDetail", pd2);// 保存到产品版本表
				}else {
					for (int i = 1; i <= sum; i++) {
						String type = "state" + i;
						String content = "content" + i;
						pd2.put("id", StringUtil.get32UUID());// 放入id
						pd2.put("type", pd.get(type)); // 放入类型
						pd2.put("content", pd.get(content)); // 放入内容
						dao.save("ProductVersionMapper.saveDetail", pd2);// 保存到产品版本表
					}
				}
				dao.save("ProductVersionMapper.save", pd1);// 保存到产品版本表
				json.put("message", "新增成功");
				json.put("result", PublicManagerUtil.TRUE);
			} catch (Exception e) {
				e.printStackTrace();
				json.put("message", "系统繁忙,请稍后再试");
				json.put("result", PublicManagerUtil.FAIL);
			}
		} else {// 编辑(较复杂,所以先删除再添加最简单)
			pd.put("id", pd.get("version_id").toString());
			pd2.put("version_id", pd.get("version_id").toString());
			try {// 先删除
				dao.delete("ProductVersionMapper.deleteDetail", pd);
				// 再新增
				for (int i = 1; i <= sum; i++) {
					String type = "state" + i;
					String content = "content" + i;
					pd2.put("id", StringUtil.get32UUID());// 放入id
					pd2.put("type", pd.get(type)); // 放入类型
					pd2.put("content", pd.get(content)); // 放入内容
					dao.save("ProductVersionMapper.saveDetail", pd2);// 保存到产品版本表
				}
				//修改产品版本表信息
				dao.update("ProductVersionMapper.edit", pd);
				json.put("message", "编辑成功");
				json.put("result", PublicManagerUtil.TRUE);
			} catch (Exception e) {
				e.printStackTrace();
				json.put("message", "系统繁忙,请稍后再试");
				json.put("result", PublicManagerUtil.FAIL);
			}
		}
		return json;
	}

	/**
	 * 删除版本日志
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public JSONObject deleteVersion(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.delete("ProductVersionMapper.deleteVersion", pd);
			dao.delete("ProductVersionMapper.deleteDetail", pd);
			json.put("message", "操作成功");
			json.put("result", PublicManagerUtil.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "系统繁忙,请稍后再试");
			json.put("result", PublicManagerUtil.FAIL);
		}
		return json;
	}

	/**
	 * 查询最新版本的id
	 * 
	 * @throws Exception
	 */
	@Override
	public PageData selectId() throws Exception {
		return (PageData) dao.findForObject("ProductVersionMapper.selectId", null);
	}

	/**
	 * 查询所有版本日志
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> selectAllId() throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		PageData pd1 = new PageData();
		List<PageData> idList = (List<PageData>) dao.findForList("ProductVersionMapper.selectAllId", null);// 先按时间查询所有版本id
		if (StringUtil.isEmpty(idList)) {
			return null;
		} else {
			for (int i = 0; i < idList.size(); i++) {
				pd1.put("version_id", idList.get(i).get("id").toString());
				PageData pd = this.showVersion(pd1);// 查询指定版本信息
				List<PageData> list1 = this.showDetail(pd1);// 查询指定版本详情
				Map<String, Object> map = new HashMap<>();
				map.put("pd", pd);
				map.put("list1", list1);
				list.add(map);
			}
		}
		return list;
	}
}
