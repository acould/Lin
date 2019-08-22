package com.lk.controller.system.productNews;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.productNews.ProductNewsManager;
import com.lk.service.system.productVersion.ProductVersionManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * 说明：产品动态
 */
@Controller
@RequestMapping(value = "/productNews")
public class ProductNewsController extends BaseController {
	String menuUrl = "productNews/list.do"; // 菜单地址(权限用)

	@Resource(name = "productNewsService")
	private ProductNewsManager productNewsService;
	@Resource(name = "productVersionService")
	private ProductVersionManager productVersionService;

	/**
	 * 前往产品日志页面
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() throws Exception {
		ModelAndView mv = this.getModelAndView();  
		PageData pd1 = productNewsService.list();// 查询版本预告信息
		mv.setViewName("system/productNews/productNews_list");
		mv.addObject("pd1", pd1); 
		return mv;
	}

	/**
	 * 展示所有版本更新日志
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/lists")
	public JSONObject lists(Page page) throws Exception {
		JSONObject json = new JSONObject();
		json = productVersionService.lists(page);// 查询版本列表
		return json;
	}

	/**
	 * 前往新增/修改产品版本页面
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEditVersion")
	public ModelAndView toEditVersion() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/productNews/productNews_edit");
		if (pd.get("version_id").equals("add")) {// 新增
			pd.put("type", "add");
			mv.addObject("pd", pd);
			return mv;
		} else {// version_id不为空,则为编辑
			PageData pd1 = productVersionService.showVersion(pd);// 查询指定版本信息
			List<PageData> list = productVersionService.showDetail(pd);// 查询指定版本详情
			pd1.put("sum", list.size());
			pd1.put("type", "edit");
			mv.addObject("pd", pd1);
			mv.addObject("list", list);
		}
		return mv;
	}

	/**
	 * 新增版本日志
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/editVersion")
	public JSONObject editVersion() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		json = productVersionService.editVersion(pd);
		return json;
	}

	/**
	 * 删除版本日志
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteVersion")
	public JSONObject deleteVersion() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		json = productVersionService.deleteVersion(pd);
		return json;
	}

	/**
	 * 查看版本详情
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/selete")
	public ModelAndView selete() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pd1 = productVersionService.showVersion(pd);// 查询指定版本信息
		List<PageData> list = productVersionService.showDetail(pd);// 查询指定版本详情
		mv.addObject("pd", pd1);
		mv.addObject("list", list);
		mv.setViewName("system/productNews/productNews_selete");
		return mv;
	}

	/**
	 * 编辑版本预告
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEditNews")
	public ModelAndView toEditNews() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pd1 = productNewsService.list();// 查询版本预告信息
		if (StringUtil.isEmpty(pd1)) {
			mv.addObject("pd", pd);
		} else {
			pd1.put("state", pd.getString("state"));
			mv.addObject("pd", pd1);
		}
		mv.setViewName("system/productNews/productNews_editNews");
		return mv;
	}

	/**
	 * 保存版本预告
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/editNews")
	public JSONObject editNews() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		json = productNewsService.edit(pd);// 查询版本预告信息
		return json;
	}

	/**
	 * 删除版本预告
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteNews")
	public JSONObject deleteNews() throws Exception {
		JSONObject json = new JSONObject();
		json = productNewsService.deleteNews();
		return json;
	}

	/**
	 * 查看所有版本日志
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectAll")
	public ModelAndView selectAll() throws Exception {
		User user = this.getUser();//得到用户

		ModelAndView mv = this.getModelAndView();
		PageData pd = productNewsService.list();// 查询版本预告信息
		List<Map<String, Object>> list = productVersionService.selectAllId();// 查询所有版本日志
		mv.setViewName("system/productNews/productNews_seleteAll");
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.addObject("INTENET_NAME", user.getNAME());
		return mv;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			String a="pd"+i;
			System.out.println(a);
		}
	}
}
