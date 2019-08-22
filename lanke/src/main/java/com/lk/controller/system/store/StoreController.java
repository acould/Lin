package com.lk.controller.system.store;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.branch.BranchManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;

/**
 * 说明：门店管理 创建人：洪智鹏 创建时间：2017-02-27
 */
@Controller
@RequestMapping(value = "/store")
public class StoreController extends BaseController {

	String menuUrl = "store/list.do"; // 菜单地址(权限用)
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "branchService")
	private BranchManager branchService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;

	/**
	 * 保存
	 * 
	 * @param 门店管理新增相关信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(HttpServletRequest request, String STORE_ID) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Store");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		PageData pageData = new PageData();
		pageData.put("user", user);
		pageData.put("request", request);
		pageData.put("pd", pd);
		pageData.put("STORE_ID", STORE_ID);
		PageData pdpd = storeService.StoreSave(pageData);// 新增门店(新增门店的同时加入加V表)
		if (pdpd.containsKey("msg") && pdpd.get("msg").toString() != null) {
			mv.addObject("msg", pdpd.get("msg").toString());
			mv.setViewName("save_result");
		}
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param STORE_ID 门店管理的主键
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam String STORE_ID) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "删除门店");
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		String errInfo = "";
		try {
			System.out.println("---------" + STORE_ID);
			pd.put("STORE_ID", STORE_ID);
			String ArrayDATA_IDS[] = pd.getString("STORE_ID").split(",");
			List userList_z = storeUserService.listAllSU(ArrayDATA_IDS); // 列出此门店的所有下级
			if (userList_z.size() > 0) {
				errInfo = PublicManagerUtil.FALSE; // 下级有数据时，删除失败
			} else {
				storeService.delete(pd); // 执行删除
				errInfo = PublicManagerUtil.SUCCESS;
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 修改
	 * @param request 门店管理修改相关信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Store");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("STORE_ID", request.getParameter("STORE_ID"));
		pd.put("STORE_NAME", request.getParameter("STORE_NAME"));
		/*
		 * pd.put("PROVINCE", request.getParameter("s_province")); pd.put("CITY",
		 * request.getParameter("s_city")); pd.put("COUNTY",
		 * request.getParameter("s_county")); pd.put("ADDRESS",
		 * request.getParameter("ADDRESS")); pd.put("TELEPHONE",
		 * request.getParameter("TELEPHONE")); pd.put("POSSWARD",
		 * request.getParameter("POSSWARD")); pd.put("UPDATE_TIME", Tools.date2Str(new
		 * Date())); //修改图片 PageData pdimg = new PageData(); logBefore(logger,
		 * Jurisdiction.getUsername()+"修改Branch"); String ffile = DateUtil.getDays(),
		 * fileName = ""; String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG
		 * + ffile; for (MultipartFile mf : file) { if(!mf.isEmpty()){ //文件上传路径 fileName
		 * = FileUpload.fileUp(mf, filePath, this.get32UUID());//执行上传 }else{ continue; }
		 * pdimg.put("BRANCH_ID", this.get32UUID()); //主键 pdimg.put("STORE_ID",
		 * pd.get("STORE_ID")); //分店id pdimg.put("URL", ffile + "/" + fileName); //路径
		 * pdimg.put("CREATETIME", Tools.date2Str(new Date())); //创建时间
		 * branchService.save(pdimg); }
		 */
		storeService.editStore(pd);
		mv.addObject("msg", PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}

	/**列表
	 * @param page检索字段和部分页面信息
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Store");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)){
			PageData pdStoreUser = new PageData();
			pdStoreUser.put("USER_ID", user.getUSER_ID());
			pdStoreUser = storeUserService.findByUserId(pdStoreUser);//找到操作用户所在的门店
			pd.put("STORE_ID", pdStoreUser.get("STORE_ID"));
		}
		pd.put("internetId", user.getINTENET_ID());
		page.setPd(pd);
		List<PageData>	varList = storeService.list(page);	//列出Store列表
		mv.setViewName("system/store/store_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

	/**
	 * 判断门店是否存在
	 *  @param pd 网吧id和分店名称
	 * @return
	 */
	@RequestMapping(value = "/hasS")
	@ResponseBody
	public Object hasS() {
		User user = this.getUser();//得到用户

		Map<String, String> map = new HashMap<String, String>();
		String errInfo = PublicManagerUtil.SUCCESS;
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("STORE_NAME", this.getRequest().getParameter("STORE_NAME"));
			pd.put("INTERNET_ID", user.getINTENET_ID());
			if (storeService.findByStoreName_Intenet(pd) != null) {
				errInfo = PublicManagerUtil.ERROR;
			} else {
				errInfo = PublicManagerUtil.SUCCESS;
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
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
		mv.setViewName("system/store/store_add");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STORE_ID", request.getParameter("STORE_ID"));
		pd.put("pre", getPre());
		List<PageData> picList = branchService.listAll(pd);
		pd = storeService.findById(pd); // 根据ID读取
		mv.setViewName("system/store/store_update");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("picList", picList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param DATA_IDS 主键组成的数组
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Store");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		String errInfo = null;
		String DATA_IDS = pd.getString("DATA_IDS");
		List<PageData> pdList = new ArrayList<PageData>();
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			List userList_z = storeUserService.listAllSU(ArrayDATA_IDS); // 列出此门店的所有下级
			if (userList_z.size() > 0) {
				errInfo = PublicManagerUtil.FALSE;
				map.put("msg", "删除失败，请先删除下面人员");
			} // 下级有数据时，删除失败
			else {
				storeService.deleteAll(ArrayDATA_IDS);
				errInfo = PublicManagerUtil.SUCCESS;
				map.put("msg", "删除成功");
			}
			pdList.add(pd);
			map.put("list", pdList);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(pd, map);
	}

	/* *//**
			 * 导出到excel
			 * 
			 * @param
			 * @throws Exception
			 *//*
				 * @RequestMapping(value="/excel") public ModelAndView exportExcel() throws
				 * Exception{ logBefore(logger, Jurisdiction.getUsername()+"导出Store到excel");
				 * if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
				 * ModelAndView mv = new ModelAndView(); PageData pd = new PageData(); pd =
				 * this.getPageData(); Map<String,Object> dataMap = new
				 * HashMap<String,Object>(); List<String> titles = new ArrayList<String>();
				 * titles.add("门店名称"); //1 titles.add("门店所在的省份"); //2 titles.add("门店所在的城市"); //3
				 * titles.add("门店所在的详细街道地址"); //4 titles.add("门店的电话"); //5 titles.add("网吧id");
				 * //6 titles.add("用户id"); //7 titles.add("登录帐号"); //8 titles.add("用户密码"); //9
				 * titles.add("新增时间"); //10 titles.add("修改时间"); //11 dataMap.put("titles",
				 * titles); List<PageData> varOList = storeService.listAll(pd); List<PageData>
				 * varList = new ArrayList<PageData>(); for(int i=0;i<varOList.size();i++){
				 * PageData vpd = new PageData(); vpd.put("var1",
				 * varOList.get(i).getString("STORE_NAME")); //1 vpd.put("var2",
				 * varOList.get(i).getString("PROVINCE")); //2 vpd.put("var3",
				 * varOList.get(i).getString("CITY")); //3 vpd.put("var4",
				 * varOList.get(i).getString("ADDRESS")); //4 vpd.put("var5",
				 * varOList.get(i).getString("TELEPHONE")); //5 vpd.put("var6",
				 * varOList.get(i).getString("INTERNET_ID")); //6 vpd.put("var7",
				 * varOList.get(i).getString("USER_ID")); //7 vpd.put("var8",
				 * varOList.get(i).getString("USER_NAME")); //8 vpd.put("var9",
				 * varOList.get(i).getString("POSSWARD")); //9 vpd.put("var10",
				 * varOList.get(i).getString("INSERT_TIME")); //10 vpd.put("var11",
				 * varOList.get(i).getString("UPDATE_TIME")); //11 varList.add(vpd); }
				 * dataMap.put("varList", varList); ObjectExcelView erv = new ObjectExcelView();
				 * mv = new ModelAndView(erv,dataMap); return mv; }
				 */

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
