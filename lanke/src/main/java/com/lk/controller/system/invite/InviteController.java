package com.lk.controller.system.invite;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.invite.InviteManager;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.DateUtil;
import com.lk.util.FileUpload;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PathUtil;
import com.lk.util.PublicManagerUtil;

/** 
 * 说明： 网吧邀请好友设置(拉新设置)
 * 创建人：白弋冉
 * 创建时间：2017-07-04
 * @version
 */
@Controller
@RequestMapping(value="/invite")
public class InviteController extends BaseController {

	String menuUrl = "invite/list.do"; //菜单地址(权限用)
	@Resource(name="inviteService")
	private InviteManager inviteService;
	@Resource(name="intenetService")
	private IntenetManager intenetService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public JSONObject save(@RequestParam(value="PATH",required=false) MultipartFile PATH,
			 @RequestParam(value="BARCODE",required=false) MultipartFile BARCODE,
			 @RequestParam(value="TITLE",required=false)  String TITLE,
			 @RequestParam(value="DESCRIPTION",required=false)  String DESCRIPTION,
			 @RequestParam(value="WELCOME",required=false)  String WELCOME) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增invite");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		User user = this.getUser();//得到用户

		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String barcode = null;
		String ffile = DateUtil.getDays();
		String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;
		if (null != BARCODE && !BARCODE.isEmpty()) {
			barcode = FileUpload.fileUp(BARCODE, filePath, this.get32UUID()); // 执行上传
		}else{
			logBefore(logger, Jurisdiction.getUsername()+"新增invite上传失败");
			System.out.println("上传失败");
			json.put("msg", PublicManagerUtil.ERROR);
			return json;
		}
		PageData pdInternet = new PageData();
		pdInternet.put("INTENET_ID", user.getINTENET_ID());
		pdInternet = intenetService.findById(pdInternet);
		String logo_url = pdInternet.getString("HEAD_IMG");
		pd.put("INTIVE_ID", this.get32UUID());
		pd.put("BARCODE",ffile + "/" + barcode);
		pd.put("PATH", logo_url);
		pd.put("TITLE", TITLE);
		pd.put("DESCRIPTION", DESCRIPTION);
		pd.put("WELCOME", WELCOME);
		pd.put("INTENET_ID", user.getINTENET_ID());
		inviteService.save(pd);   //新增拉新设置标信息
		json.put("msg", PublicManagerUtil.SUCCESS);
		json.put("mv", "save_result");
		return json;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除invite");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inviteService.delete(pd);  //通过INTIVE_ID删除拉新设置信息
		out.write(PublicManagerUtil.SUCCESS);
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public JSONObject edit(
			@RequestParam(value="path",required=false)  String p,
			@RequestParam(value="barcode",required=false)  String b,
			 @RequestParam(value="INTIVE_ID",required=false)  String INTIVE_ID,
			@RequestParam(value="PATH",required=false) MultipartFile PATH,
			 @RequestParam(value="BARCODE",required=false) MultipartFile BARCODE,
			 @RequestParam(value="TITLE",required=false)  String TITLE,
			 @RequestParam(value="DESCRIPTION",required=false)  String DESCRIPTION,
			 @RequestParam(value="WELCOME",required=false)  String WELCOME) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改invite");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		JSONObject json = new JSONObject();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		String barcode = null;
		String ffile = DateUtil.getDays();
		String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;
		if (null != BARCODE && !BARCODE.isEmpty()) {
			barcode = FileUpload.fileUp(BARCODE, filePath, this.get32UUID()); // 执行上传
		}
		if(BARCODE.isEmpty()){
			pd.put("BARCODE",b);
		}else{
			pd.put("BARCODE",ffile + "/" +barcode);
		}
		pd.put("INTIVE_ID", INTIVE_ID);
		pd.put("TITLE", TITLE);
		pd.put("DESCRIPTION", DESCRIPTION);
		pd.put("WELCOME", WELCOME);
		pd.put("INTENET_ID", user.getINTENET_ID());
		inviteService.edit(pd); //通过INTIVE_ID保存修改后的信息
		json.put("msg", PublicManagerUtil.SUCCESS);
		return json;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表invite");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("INTENET_ID", user.getINTENET_ID());
		page.setPd(pd);
		List<PageData>	varList = inviteService.list(page);	//列出invite列表(通过网吧id获取拉新设置信息)
		mv.setViewName("system/invite/invite_list");
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
		mv.setViewName("system/invite/invite_edit");
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
		pd = inviteService.findById(pd);	//根据ID读取(通过INTIVE_ID获取拉新设置信息)
		mv.setViewName("system/invite/invite_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除invite");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inviteService.deleteAll(ArrayDATA_IDS);  //通过INTIVE_ID批量删除拉新设置信息
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
		logBefore(logger, Jurisdiction.getUsername()+"导出invite到excel");
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
		List<PageData> varOList = inviteService.listAll(pd);  //获取全部拉新设置信息
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
}
