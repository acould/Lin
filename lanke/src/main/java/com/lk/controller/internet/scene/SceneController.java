package com.lk.controller.internet.scene;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.util.PublicManagerUtil;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.Jurisdiction;
import com.lk.service.internet.scene.SceneManager;
import com.lk.service.system.dictenty.DictEntyManager;
import com.lk.service.system.sysscene.SysSceneManager;

/**
 * 说明：优惠券使用场景 创建人：洪智鹏 创建时间：2017-06-08
 */
@Controller
@RequestMapping(value = "/scene")
@SuppressWarnings("restriction")
public class SceneController extends BaseController {
	// BENEFIT_TYPE
	public static final String BENEFIT_TYPE1 = "FLQ";
	public static final String BENEFIT_TYPE2 = "YHQ";
	// DICT_TYPE
	public static final String DICT_TYPE1 = "LK001";
	public static final String DICT_TYPE2 = "LK002";
	public static final String DICT_TYPE3 = "LK003";
	public static final String DICT_TYPE4 = "LK004";
	public static final String DICT_TYPE5 = "LK005";

	String menuUrl = "scene/list.do"; // 菜单地址(权限用)

	@Resource(name = "sceneService")
	private SceneManager sceneService;
	@Resource(name = "dictentyService")
	private DictEntyManager dictentyService;
	@Resource(name = "syssceneService")
	private SysSceneManager syssceneService;

	/**
	 * 保存
	 * 
	 * @param PageData
	 *            包含场景信息数据
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Scene");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdNew = new PageData();
		pdNew = sceneService.findByName(pd);
		if (pdNew == null) {
			pd.put("SCENE_ID", this.get32UUID()); // 主键
			User user = this.getUser();//获取用户

			pd.put("INTENET_ID", user.getINTENET_ID());
			pd.put("STATE", "2");
			if (pd.getString("FAV_TYPE").endsWith("MAN") || pd.getString("FAV_TYPE").endsWith("WEM") || pd.getString("FAV_TYPE").endsWith("BIRTH") 
			|| pd.getString("FAV_TYPE").endsWith("GRAB") || pd.getString("FAV_TYPE").endsWith("TERM")) {
				pd.put("BENEFIT_TYPE", BENEFIT_TYPE1);
			} else {
				pd.put("BENEFIT_TYPE", BENEFIT_TYPE2);
			}
			sceneService.save(pd);
			mv.addObject("msg", PublicManagerUtil.SUCCESS);
			mv.setViewName("save_result");
		} else {
			mv.addObject("msg", PublicManagerUtil.FAIL);
			mv.addObject("result", "存在相同名称 ");
			mv.setViewName("save_result");
		}

		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 *            删除某个场景根据场景id删除
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Scene");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		sceneService.delete(pd);
		out.write(PublicManagerUtil.SUCCESS);
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param PageData
	 *            修改场景信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Scene");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

		PageData pd = this.getPageData();
		PageData pdNew = new PageData();
		pd.put("INTENET_ID", user.getINTENET_ID());
		pdNew = sceneService.findByName(pd);
		if (pdNew == null) {
			pd.put("INTENET_ID", user.getINTENET_ID());
			pd.put("STATE", "2");
			if (pd.getString("FAV_TYPE").endsWith("MAN") || pd.getString("FAV_TYPE").endsWith("WEM") || pd.getString("FAV_TYPE").endsWith("BIRTH")
			 || pd.getString("FAV_TYPE").endsWith("GRAB") || pd.getString("FAV_TYPE").endsWith("TERM")) {
				pd.put("BENEFIT_TYPE", BENEFIT_TYPE1);
			} else {
				pd.put("BENEFIT_TYPE", BENEFIT_TYPE2);
			}
			sceneService.edit(pd);
			mv.addObject("msg", PublicManagerUtil.SUCCESS);
			mv.setViewName("save_result");
		} else {
			mv.addObject("msg", PublicManagerUtil.FAIL);
			mv.addObject("result", "存在相同名称 ");
			mv.setViewName("save_result");
		}
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 *            查看场景列表，包含检索字段等
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Scene");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		User user = this.getUser();//获取用户

		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		pd.put("INTENET_ID", user.getINTENET_ID());
		page.setPd(pd);
		List<PageData> varList = sceneService.list(page); // 列出Scene列表
		for (PageData pdNew : varList) {
			if (pdNew.containsKey("FAV_TYPE") && StringUtils.isNoneEmpty(pdNew.getString("FAV_TYPE"))) {
				PageData pdSce = new PageData();
				pdSce.put("DICT_TYPE", DICT_TYPE1);
				pdSce.put("DICT_CODE", pdNew.getString("FAV_TYPE"));
				pdSce = dictentyService.findBySc(pdSce);
				pdNew.put("FAV_TYPE", pdSce.getString("DICT_VALUE"));
			}
			if (pdNew.containsKey("BENEFIT_TYPE") && StringUtils.isNoneEmpty(pdNew.getString("BENEFIT_TYPE"))) {
				PageData pdFl = new PageData();
				pdFl.put("DICT_TYPE", DICT_TYPE2);
				pdFl.put("DICT_CODE", pdNew.getString("BENEFIT_TYPE"));
				pdFl = dictentyService.findBySc(pdFl);
				pdNew.put("BENEFIT_TYPE", pdFl.getString("DICT_VALUE"));
			}
            //下边有两个STATE4????(之前谁写的)
			if (pdNew.containsKey("RECEIVE_STATE") && StringUtils.isNoneEmpty(pdNew.getString("RECEIVE_STATE"))) {
				PageData pdHx = new PageData();
				pdHx.put("DICT_TYPE", DICT_TYPE4);
				pdHx.put("DICT_CODE", pdNew.getString("RECEIVE_STATE"));
				pdHx = dictentyService.findBySc(pdHx);
				pdNew.put("RECEIVE_STATE", pdHx.getString("DICT_VALUE"));
			}

			if (pdNew.containsKey("CANCEL_STATE") && StringUtils.isNoneEmpty(pdNew.getString("CANCEL_STATE"))) {
				PageData pdHx1 = new PageData();
				pdHx1.put("DICT_TYPE", DICT_TYPE4);
				pdHx1.put("DICT_CODE", pdNew.getString("CANCEL_STATE"));
				pdHx1 = dictentyService.findBySc(pdHx1);
				pdNew.put("CANCEL_STATE", pdHx1.getString("DICT_VALUE"));
			}

			if (pdNew.containsKey("RECEIVE_DETIL") && StringUtils.isNoneEmpty(pdNew.getString("RECEIVE_DETIL"))) {
				PageData pdJg = new PageData();
				pdJg.put("DICT_TYPE", DICT_TYPE5);
				pdJg.put("DICT_CODE", pdNew.getString("RECEIVE_DETIL"));
				pdJg = dictentyService.findBySc(pdJg);
				pdNew.put("RECEIVE_DETIL", pdJg.getString("DICT_VALUE"));
			}

		}
		mv.setViewName("internet/scene/scene_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData page = new PageData();
		page.put("dictType", DICT_TYPE1);
		List<PageData> listSce = this.dictentyService.listAll(page);
		page.put("dictType", DICT_TYPE2);
		List<PageData> listFl = this.dictentyService.listAll(page);
		page.put("dictType", DICT_TYPE4);
		List<PageData> listHx = this.dictentyService.listAll(page);
		page.put("dictType", DICT_TYPE5);
		List<PageData> listJg = this.dictentyService.listAll(page);
		mv.setViewName("internet/scene/scene_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		mv.addObject("pd", pd);
		mv.addObject("listJg", listJg);
		mv.addObject("listSce", listSce);
		mv.addObject("listFl", listFl);
		mv.addObject("listHx", listHx);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = sceneService.findById(pd); // 根据ID读取
		PageData page = new PageData();
		page.put("dictType", DICT_TYPE1);
		List<PageData> listSce = this.dictentyService.listAll(page);
		page.put("dictType", DICT_TYPE2);
		List<PageData> listFl = this.dictentyService.listAll(page);
		page.put("dictType", DICT_TYPE4);
		List<PageData> listHx = this.dictentyService.listAll(page);
		page.put("dictType", DICT_TYPE5);
		List<PageData> listJg = this.dictentyService.listAll(page);
		mv.setViewName("internet/scene/scene_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		mv.addObject("listJg", listJg);
		mv.addObject("listSce", listSce);
		mv.addObject("listFl", listFl);
		mv.addObject("listHx", listHx);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param DATA_IDS
	 *            根据场景id集合进行删除
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Scene");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			sceneService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", PublicManagerUtil.OK);
		} else {
			pd.put("msg", PublicManagerUtil.NO);
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAll")
	@ResponseBody
	public JSONObject addAll() throws Exception {
		JSONObject gjson = new JSONObject();
		logBefore(logger, Jurisdiction.getUsername() + "一键新增IntIntegral");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			gjson.put("message", "没有权限");
			return gjson;
		} // 校验权限
		PageData pd = new PageData();
		User user = this.getUser();//获取用户

		pd.put("INTENET_ID", user.getINTENET_ID());
		pd.put("STATE", "1");
		List<PageData> varList = sceneService.listByState(pd); // 列出IntIntegral列表
		if (varList.size() > 0) {
			gjson.put("message", "已经设置过了，如果需要设置，请删除全部系统场景");
			return gjson;
		} else {
			List<PageData> intenetList = syssceneService.listAll(pd); // 列出IntIntegral列表
			for (PageData intenet : intenetList) {
				intenet.put("SCENE_ID", this.get32UUID()); // 主键
				intenet.put("INTENET_ID", user.getINTENET_ID());
				intenet.put("STATE", 1);
				sceneService.save(intenet);
			}
			gjson.put("message", "设置成功");
			return gjson;
		}

	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Scene到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("优惠类型"); // 1
		titles.add("领取类型"); // 2
		titles.add("福利类型"); // 3
		titles.add("领取类型对应的数量"); // 4
		titles.add("领取详细说明"); // 5
		titles.add("是否领取限制"); // 6
		titles.add("是否核销限制"); // 7
		dataMap.put("titles", titles);
		List<PageData> varOList = sceneService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("FAV_TYPE")); // 1
			vpd.put("var2", varOList.get(i).getString("RECEIVE_TYPE")); // 2
			vpd.put("var3", varOList.get(i).getString("BENEFIT_TYPE")); // 3
			vpd.put("var4", varOList.get(i).getString("RECEIVE_NUMBER")); // 4
			vpd.put("var5", varOList.get(i).getString("RECEIVE_DETIL")); // 5
			vpd.put("var6", varOList.get(i).getString("RECEIVE_STATE")); // 6
			vpd.put("var7", varOList.get(i).getString("CANCEL_STATE")); // 7
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
