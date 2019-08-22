package com.lk.controller.system.introduc;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.DateUtil;
import com.lk.util.FileUpload;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.Jurisdiction;
import com.lk.util.PathUtil;
import com.lk.util.PublicManagerUtil;
import com.lk.service.system.introduc.IntroducManager;

/** 
 * 说明：网吧介绍(网吧介绍管理)
 * 创建人：洪智鹏
 * 创建时间：2016-12-19
 */
@Controller
@RequestMapping(value="/introduc")
public class IntroducController extends BaseController {
	
	String menuUrl = "introduc/list.do"; //菜单地址(权限用)
	@Resource(name="introducService")
	private IntroducManager introducService;
	
	/**保存
	 * @param 根据下列参数心中网吧介绍表
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(@RequestParam(value="PIC_ONE",required=false) MultipartFile fileOne,
			 @RequestParam(value="PIC_TWO",required=false) MultipartFile fileTwo,
			 @RequestParam(value="PIC_THREE",required=false) MultipartFile fileThree,
			 @RequestParam(value="PIC_FOUR",required=false) MultipartFile fileFour,
			 @RequestParam(value="ADDRESS",required=false) String address,
			 @RequestParam(value="PHONE",required=false)  String PHONE,
			 @RequestParam(value="CONTENT",required=false) String CONTENT) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Introduc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

		PageData pd = new PageData();
		String  ffile = DateUtil.getDays();
		pd = this.getPageData();
		if (null != fileOne && !fileOne.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;		//文件上传路径
		    String fileNameOne = FileUpload.fileUp(fileOne, filePath, this.get32UUID());				//执行上传
		    pd.put("PIC_ONE", fileNameOne);
		}else{
			System.out.println("上传失败");
		}
		if (null != fileTwo && !fileTwo.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;		//文件上传路径
			String fileNameTwo = FileUpload.fileUp(fileTwo, filePath, this.get32UUID());				//执行上传
			pd.put("PIC_TWO", fileNameTwo);
		}else{
			System.out.println("上传失败");
		}
		if (null != fileThree && !fileThree.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;		//文件上传路径
			String fileNameThree = FileUpload.fileUp(fileThree, filePath, this.get32UUID());				//执行上传
			pd.put("PIC_THREE", fileNameThree);
			
		}else{
			System.out.println("上传失败");
		}
		if (null != fileFour && !fileFour.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;		//文件上传路径
			String fileNameFour = FileUpload.fileUp(fileFour, filePath, this.get32UUID());				//执行上传
			pd.put("PIC_FOUR", fileNameFour);
		}else{
			System.out.println("上传失败");
		}
		pd.put("INTRODUC_ID", this.get32UUID());	//主键
		pd.put("INTENET_ID", user.getINTENET_ID());	//网吧id
		pd.put("ADDRESS", address);	
		pd.put("PHONE",PHONE);	
		pd.put("CONTENT",CONTENT);	
		introducService.save(pd);
		mv.addObject("msg",PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out 删除网吧介绍
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Introduc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		introducService.delete(pd);
		out.write(PublicManagerUtil.SUCCESS);
		out.close();
	}
	
	/**修改
	 * @param  PageData 包含网吧介绍修改
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Introduc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		introducService.edit(pd);
		mv.addObject("msg",PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page 展示网吧介绍
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Introduc");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = introducService.list(page);	//列出Introduc列表
		mv.setViewName("system/introduc/introduc_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/introduc/introduc_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = introducService.findById(pd);	//根据ID读取
		mv.setViewName("system/introduc/introduc_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param 批量删除网吧介绍
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Introduc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			introducService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", PublicManagerUtil.OK);
		}else{
			pd.put("msg", PublicManagerUtil.NO);
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Introduc到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("网吧id");	//1
		titles.add("地址");	    //2
		titles.add("联系电话");	//3
		titles.add("网吧图片1");	//4
		titles.add("图片2");	//5
		titles.add("图片3");	//6
		titles.add("图片4");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = introducService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("INTENET_ID"));	//1
			vpd.put("var2", varOList.get(i).getString("ADDRESS"));	//2
			vpd.put("var3", varOList.get(i).getString("PHONE"));	//3
			vpd.put("var4", varOList.get(i).getString("PIC_ONE"));	//4
			vpd.put("var5", varOList.get(i).getString("PIC_TWO"));	//5
			vpd.put("var6", varOList.get(i).getString("PIC_THREE"));	//6
			vpd.put("var7", varOList.get(i).getString("PIC_FOUR"));	//7
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
