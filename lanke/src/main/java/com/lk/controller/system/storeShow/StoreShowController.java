package com.lk.controller.system.storeShow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Joiner;
import com.lk.service.system.storeUserTips.StoreUserTipsManager;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.Const;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.Jurisdiction;
import com.lk.util.PathUtil;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.service.system.branch.BranchManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;

/**
 * 说明：门店管理 创建人：洪智鹏 创建时间：2017-02-27
 */
@Controller
@RequestMapping(value = "/storeShow")
public class StoreShowController extends BaseController {

	/**
	 * 说明：门店管理 修改人：李泽华 修改时间：2018-04-15
	 */
	// FILE(文件类型)
	public static final String FILE1 = "data:image/jpeg;base64,";
	public static final String FILE2 = "data:image/png;base64,";
	// SUFFIXNAME(图片尾缀)
	public static final String SUFFIXNAME1 = ".jpg";
	public static final String SUFFIXNAME2 = ".png";

	String menuUrl = "storeShow/list.do"; // 菜单地址(权限用)
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "branchService")
	private BranchManager branchService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	@Resource(name = "storeUserTipsService")
	private StoreUserTipsManager storeUserTipsService;


	/**
	 * 门店管理列表 通过网吧id/门店id去查询门店详细信息
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Store");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;}//权限校验
		ModelAndView mv = this.getModelAndView();

		User user = this.getUser();//获取用户


		PageData pd = this.getPageData();
		String ROLE_ID = user.getROLE_ID();
		if (!ROLE_ID.equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)) {// 不是网吧老板
			List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());
			pd.put("STORE_ID", Joiner.on("','").join(storeid));
		}
		pd.put("internetId", user.getINTENET_ID());
		page.setPd(pd);
		
		List<PageData> varList = storeService.storeList(page,pd); // 列出Store列表
		mv.setViewName("system/storeShow/storeShow_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去修改页面 通过门店id去查询门店详细信息
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit(HttpServletRequest request) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("STORE_ID", request.getParameter("STORE_ID"));
		pd.put("pre", getPre());
		System.out.println(getPre());
		List<PageData> picList = branchService.listAll(pd); // 查询门店图片
		pd = storeService.findById(pd); // 根据ID读取
		pd.put("STATE", "2");
		mv.setViewName("system/storeShow/storeShow_add");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("picList", picList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 修改门店状态
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/statebt")
	@ResponseBody
	public JSONObject statebt() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "启用或禁用门店展示StoreShow");
		PageData pd = this.getPageData();
		JSONObject json=storeService.statebt(pd);// 修改门店状态
		return json;
	}

	/**
	 * 删除门店展示
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JSONObject delete() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + " 删除门店展示delete");
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) { // 校验权限
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有新增权限，请联系管理员！");
			return json;
		}
		json=storeService.delete(pd); // 通过门店id删除门店信息
		return json;
	}

	/**
	 * 批量删除
	 *
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public JSONObject deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除storeShow");
		JSONObject json = new JSONObject();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {// 校验权限
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有删除权限，请联系管理员！");
			return json;
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		if (StringUtil.isNotEmpty(pd.getString("DATA_IDS"))) {
			String DATA_IDS = pd.getString("DATA_IDS");
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			json=storeService.deleteAll(ArrayDATA_IDS);  // 通过门店id批量删除门店信息
		} else {
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "请选择需要删除的选项！");
		}
		return json;
	}
	
	/**
	 * 去编辑标签页面(会员标签)
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/goTipsEdit")
	public ModelAndView goConsumeEdit(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("store_id", request.getParameter("STORE_ID"));
		pd = storeUserTipsService.findById(pd);       // 根据ID查询标签信息
		if (pd == null) {
			pd = new PageData();
			pd.put("store_id", request.getParameter("STORE_ID"));
		}
		if(StringUtil.isNotEmpty(request.getParameter("memberId"))) {
			pd.put("memberId", request.getParameter("memberId"));
			pd.put("state", "marke");//修改来源--会员营销
		}
		mv.setViewName("system/storeShow/store_tips");
		mv.addObject("msg", "tips");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 保存标签参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/tips")
	@ResponseBody
	public JSONObject consume(HttpServletRequest request) throws Exception {
		JSONObject json =storeUserTipsService.findById1(request); // 根据id查询标签信息
		return json;
	}

	/**
	 * 去新增页面 门店新增操作
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("STATE", "1");
		mv.setViewName("system/storeShow/storeShow_add");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 判断门店是否存在(修改) 修改门店时通过门店名称去判断是否已存在
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/hasS")
	@ResponseBody
	public JSONObject hasS(String STORE_NAME, String STORE_ID, String STATE) throws Exception {
		User user = this.getUser();//获取用户

		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		pd.put("STORE_NAME", STORE_NAME);
		pd.put("INTERNET_ID", user.getINTENET_ID());
		json=storeService.findByStoreName_Intenet1(pd,STATE,STORE_ID);
		return json;
	}
	
	

	/**
	 * 保存门店(新增/修改) 保存门店新增/修改信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStore")
	@ResponseBody
	public JSONObject addStore() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "保存门店展示addStore");
		JSONObject json = new JSONObject();
		User user = this.getUser();//获取用户

		PageData pd = this.getPageData();
		if (pd.getString("STATE").equals("1")) { 
			if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {// 校验权限(新增)
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有新增权限，请联系管理员！");
				return json;
			}
		}
		if (pd.getString("STATE").equals("2")) { 
			if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {// 校验权限(编辑)
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有修改权限，请联系管理员！");
				return json;
			}
		}

		pd.put("STORE_NAME", pd.getString("STORE_NAME"));           //门店名称
		pd.put("INTERNET_ID", user.getINTENET_ID());                //网吧id
		if (storeService.findByStoreName_Intenet(pd) != null) {     //通过网吧id和门店名称查到重复的(门店名称已存在)
			if (pd.getString("STATE").equals("1")) {                //新增门店
				json.put("result", PublicManagerUtil.ERROR);
				json.put("message", "该门店已存在！");
			} 
			else if (pd.getString("STATE").equals("2")) {                                //编辑门店
				pd.put("STORE_ID", pd.getString("STORE_ID"));                            //门店id
				List<PageData> list = storeService.findByStoreId(pd);                    //通过门店id去查原来的门店名称
				if (pd.getString("STORE_NAME").equals(list.get(0).get("STORE_NAME"))) {  //如果为原来的名字
					branchService.deleteStore(pd);                                       //编辑门店先将门店图片删除(后续再次保存)
					pd.put("PROVINCE", pd.getString("s_province"));
					if (StringUtil.isEmpty(pd.getString("s_county"))) {
						pd.put("CITY", pd.getString("s_province"));
						pd.put("COUNTY", pd.getString("s_city"));
					} else {
						pd.put("CITY", pd.getString("s_city"));
						pd.put("COUNTY", pd.getString("s_county"));
					}
					pd.put("ADDRESS", pd.getString("ADDRESS")); 
					pd.put("TELEPHONE", pd.getString("TELEPHONE"));
					pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
					storeService.edit(pd);                                               //保存修改后的门店信息
					
					// 修改图片(已将门店图片删除,再次保存新照片)
					if (StringUtil.isNotEmpty(pd.getString("imgList"))) {
						String[] arr = pd.getString("imgList").split(",");
						for (int i = 0; i < arr.length; i++) {
							PageData pdimg = new PageData();
							pdimg.put("BRANCH_ID", i + this.get32UUID());
							pdimg.put("STORE_ID", pd.get("STORE_ID"));
							pdimg.put("URL", arr[i]);
							pdimg.put("CREATETIME", Tools.date2Str(new Date()));
							branchService.save(pdimg);     // 保存门店id,图片url,创建时间到分点图片表
						}
					}
					json.put("result", PublicManagerUtil.SUCCESS);
					json.put("message", "修改成功！");
				} else {// 已存在
					json.put("result", PublicManagerUtil.ERROR);
					json.put("message", "该门店已存在！");
				}
			}
		} else {                                              // 不存在
			if (pd.getString("STATE").equals("1")) {          //新增
				pd.put("STORE_ID", this.get32UUID());
				pd.put("STORE_NAME", pd.getString("STORE_NAME"));
				if (StringUtil.isEmpty(pd.getString("s_county"))) {
					pd.put("PROVINCE", pd.getString("s_province"));
					pd.put("CITY", pd.getString("s_province"));
					pd.put("COUNTY", pd.getString("s_city"));
				} else {
					pd.put("PROVINCE", pd.getString("s_province"));
					pd.put("CITY", pd.getString("s_city"));
					pd.put("COUNTY", pd.getString("s_county"));
				}
				pd.put("ADDRESS", pd.getString("ADDRESS"));
				pd.put("TELEPHONE", pd.getString("TELEPHONE"));
				pd.put("INTERNET_ID", user.getINTENET_ID());
				pd.put("USER_ID", user.getUSER_ID());
				storeService.addStore(pd);                   // 保存门店信息

				if (StringUtil.isNotEmpty(pd.getString("imgList"))) {
					String[] arr = pd.getString("imgList").split(",");
					for (int i = 0; i < arr.length; i++) {
						PageData pdimg = new PageData();
						pdimg.put("BRANCH_ID", i + this.get32UUID());
						pdimg.put("STORE_ID", pd.get("STORE_ID"));
						pdimg.put("URL", arr[i]);
						pdimg.put("CREATETIME", Tools.date2Str(new Date()));
						branchService.save(pdimg);             // 保存门店id,图片url,创建时间到分点图片表
					}
				}
				json.put("result", PublicManagerUtil.TRUE);
				json.put("message", "提交成功！");
			}
			if (pd.getString("STATE").equals("2")) {
				pd.put("STORE_NAME", pd.getString("STORE_NAME"));
				pd.put("PROVINCE", pd.getString("s_province"));
				if (StringUtil.isEmpty(pd.getString("s_county"))) {
					pd.put("CITY", pd.getString("s_province"));
					pd.put("COUNTY", pd.getString("s_city"));
				} else {
					pd.put("CITY", pd.getString("s_city"));
					pd.put("COUNTY", pd.getString("s_county"));
				}
				pd.put("ADDRESS", pd.getString("ADDRESS"));
				pd.put("TELEPHONE", pd.getString("TELEPHONE"));
				pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
				storeService.edit(pd);                        //保存修改后的门店信息
				
				// 修改图片
				if (StringUtil.isNotEmpty(pd.getString("imgList"))) {
					String[] arr = pd.getString("imgList").split(",");
					for (int i = 0; i < arr.length; i++) {
						PageData pdimg = new PageData();
						pdimg.put("BRANCH_ID", i + this.get32UUID());
						pdimg.put("STORE_ID", pd.get("STORE_ID"));
						pdimg.put("URL", arr[i]);
						pdimg.put("CREATETIME", Tools.date2Str(new Date()));
						branchService.save(pdimg);     // 保存门店id,图片url,创建时间到分点图片表
					}
				}
				json.put("result", PublicManagerUtil.SUCCESS);
				json.put("message", "修改成功！");
			} 
		}
		return json;
	}
	

	/**
	 * 上传图片
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadImg")
	@ResponseBody
	public JSONObject uploadImg() throws Exception {
		User user = this.getUser();//获取用户

		JSONObject json = new JSONObject();
		json.put("result", "false");
		json.put("message", "上传失败！");
		PageData pd = this.getPageData();
		String baseImg = pd.getString("baseImg");
		System.out.println("baseImg ======" + baseImg);
		if (StringUtil.isNotEmpty(baseImg)) {
			String suffixName = "";
			if (baseImg.contains(FILE1)) {
				suffixName = SUFFIXNAME1;
			} else if (baseImg.contains(FILE2)) {
				suffixName = SUFFIXNAME2;
			} else {
				suffixName = SUFFIXNAME1;
			}
			String fileName = new Date().getTime() + suffixName;
			String path = user.getINTENET_ID() + "/" + fileName;
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;
			// 上传到本地
			Tools.pic(baseImg, filePath);
			json.put("result", "true");
			json.put("message", "上传成功！");
			json.put("path", path);
			String url = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" + this.getRequest().getServerPort() + this.getRequest().getContextPath() + "/";
			url += Const.FILEPATHIMG + path;
			json.put("url", url);
		}
		return json;
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Store到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("门店名称"); // 1
		titles.add("门店所在的省份"); // 2
		titles.add("门店所在的城市"); // 3
		titles.add("门店所在的详细街道地址"); // 4
		titles.add("门店的电话"); // 5
		titles.add("网吧id"); // 6
		titles.add("用户id"); // 7
		titles.add("登录帐号"); // 8
		titles.add("用户密码"); // 9
		titles.add("新增时间"); // 10
		titles.add("修改时间"); // 11
		dataMap.put("titles", titles);
		List<PageData> varOList = storeService.listAll(pd); // 查询全部门店信息
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STORE_NAME")); // 1
			vpd.put("var2", varOList.get(i).getString("PROVINCE")); // 2
			vpd.put("var3", varOList.get(i).getString("CITY")); // 3
			vpd.put("var4", varOList.get(i).getString("ADDRESS")); // 4
			vpd.put("var5", varOList.get(i).getString("TELEPHONE")); // 5
			vpd.put("var6", varOList.get(i).getString("INTERNET_ID")); // 6
			vpd.put("var7", varOList.get(i).getString("USER_ID")); // 7
			vpd.put("var8", varOList.get(i).getString("USER_NAME")); // 8
			vpd.put("var9", varOList.get(i).getString("POSSWARD")); // 9
			vpd.put("var10", varOList.get(i).getString("INSERT_TIME")); // 10
			vpd.put("var11", varOList.get(i).getString("UPDATE_TIME")); // 11
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
