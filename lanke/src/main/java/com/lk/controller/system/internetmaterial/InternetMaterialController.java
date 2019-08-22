package com.lk.controller.system.internetmaterial;

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
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.service.information.pictures.PicturesManager;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.internetmaterial.InternetMaterialManager;
import com.lk.service.system.wechat.WechatManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.SrcUtils;
import com.lk.util.Tools;
import com.lk.wechat.util.WechatMaterialUtil;
import com.lk.wechat.util.WechatMessageUtil;

/** 
 * 说明：网吧素材
 * 创建人：洪智鹏
 * 创建时间：2017-06-05
 */
@Controller
@RequestMapping(value="/internetmaterial")
public class InternetMaterialController extends BaseController {
	
	String menuUrl = "internetmaterial/list.do"; //菜单地址(权限用)
	
	@Resource(name="internetmaterialService")
	private InternetMaterialManager internetmaterialService;
	@Resource(name="wechatService")
	private WechatManager wechatService;
	@Resource(name="intenetService")
	private IntenetManager intenetService;
	@Resource(name="picturesService")
	private PicturesManager picturesService;
	@Resource(name="terraceService")
	private TerraceManager terraceService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;
	 
	 
	/**保存
	 * @param PageData 包含网吧素材相关信息
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增InternetMaterial");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		pd.put("INTERNETMATERIAL_ID", this.get32UUID());	//主键
		pd.put("INTERNET_ID", user.getINTENET_ID());	//网吧id
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("THUMB_MEDIA_ID",pd.getString("LOGO_URL") );		 
		PageData pdWechat = new PageData();
		Intenet org=intenetService.getIntenetById(user.getINTENET_ID());
	    pdWechat.put("INTERNET_ID",  user.getINTENET_ID());
		pdWechat=this.wechatService.findByInternetId(pdWechat);
		
         //获取公众号凭证
     	String AUTHORIZER_ACCESS_TOKEN = autoReplyService.getAuthToken(org.getINTENET_ID());

		 List<String> imgSrt=new ArrayList<String>();
		 imgSrt=SrcUtils.ImgSrc(pd.getString("CONTENT"));
		 if(imgSrt.size()>0){
			for(int i=0;i<imgSrt.size();i++){
				 String filePath = imgSrt.get(i);	
				   if(filePath.indexOf("image")>0){
				    	 String[] file1 = imgSrt.get(i).split("/");
						 JSONObject resultJSON=WechatMaterialUtil.addMaterialImg(AUTHORIZER_ACCESS_TOKEN,  filePath);
						 PageData pdPic = new PageData();
						 pdPic.put("PICTURES_ID", this.get32UUID());			//主键
						 pdPic.put("TITLE", pd.getString("TITLE")+i);			//标题
						 pdPic.put("NAME", file1[file1.length-1]);				//文件名
						 pdPic.put("PATH",filePath);							//路径
						 pdPic.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
						 pdPic.put("MASTER_ID", "1");							//附属与
						 pdPic.put("INTERNET_ID", user.getINTENET_ID());
					     pdPic.put("URL", resultJSON.get("url"));	
						 picturesService.save(pdPic);
						 String count=pd.getString("CONTENT").replace(filePath,resultJSON.get("url").toString());
						 pd.put("CONTENT",count);		
				    }
			}
			
		}
		List<String> vedioSrt=new ArrayList<String>();
		vedioSrt=SrcUtils.ImgVedio(pd.getString("CONTENT"));
		if(vedioSrt.size()>0){
			for(int i=0;i<vedioSrt.size();i++){
				 String filePath = vedioSrt.get(i);	
				    if(filePath.indexOf("video")>0){
				    	 String[] file1 = vedioSrt.get(i).split("/");
				    	 JSONObject resultJSON=WechatMaterialUtil.postVideo(filePath,pd.getString("TITLE"),pd.getString("DIGEST"),AUTHORIZER_ACCESS_TOKEN  );
				    	 JSONObject resultJSONVdedio=WechatMaterialUtil.getVedioMaterial(resultJSON.getString("media_id"),AUTHORIZER_ACCESS_TOKEN  );
						 PageData pdPic = new PageData();
						 pdPic.put("PICTURES_ID", this.get32UUID());			//主键
						 pdPic.put("TITLE",pd.getString("TITLE")+i);			//标题
						 pdPic.put("NAME", file1[file1.length-1]);				//文件名
						 pdPic.put("PATH",filePath);							//路径
						 pdPic.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
						 pdPic.put("MASTER_ID", "1");							//附属与
						 pdPic.put("INTERNET_ID", user.getINTENET_ID());
						 pdPic.put("MATERIAL_ID", resultJSON.get("media_id"));
						 pdPic.put("URL", resultJSONVdedio.get("down_url"));	
						 picturesService.save(pdPic);
						 String count= pd.getString("CONTENT").replace(filePath,resultJSONVdedio.get("down_url").toString());
						 pd.put("CONTENT",count);	
				    }
			}
			
		}
		//pd.put("CONTENT",  pd.getString("CONTENT"));	//创建时间
		
		 JSONObject jsonObject=WechatMaterialUtil.postNew(pd, AUTHORIZER_ACCESS_TOKEN);
		 if(jsonObject.containsKey("media_id")){
			 pd.put("INTERNETMATERIAL_ID", jsonObject.get("media_id"));
				internetmaterialService.save(pd);
				mv.addObject("msg",PublicManagerUtil.SUCCESS);
				mv.setViewName("save_result");
		 }else{
				mv.addObject("msg",PublicManagerUtil.FAIL);
				mv.addObject("result","上传图文到微信公众号失败");
				mv.setViewName("save_result");
		 }
		
		return mv;
	}
	
	/**删除
	 * @param out 删除网吧素材信息
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除InternetMaterial");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		User user = this.getUser();//得到用户
		PageData pd = this.getPageData();
		PageData pdWechat = new PageData();
		Intenet org=intenetService.getIntenetById(user.getINTENET_ID());
	    pdWechat.put("INTERNET_ID",  user.getINTENET_ID());
		pdWechat=this.wechatService.findByInternetId(pdWechat);
		
        //获取公众号凭证
  		String AUTHORIZER_ACCESS_TOKEN = autoReplyService.getAuthToken(org.getINTENET_ID());

		 JSONObject jsonObject=WechatMaterialUtil.deleteMaterial(pd.getString("INTERNETMATERIAL_ID"), AUTHORIZER_ACCESS_TOKEN);
		 if(jsonObject.containsKey("errcode")){
			 if(jsonObject.get("errcode").equals(0)){
				 internetmaterialService.delete(pd);
				 out.write(PublicManagerUtil.SUCCESS);
			 }else{
				 out.write(PublicManagerUtil.FAIL);
			 }
		 }else{
			 out.write(PublicManagerUtil.FAIL);
		 }
		
		out.close();
	}

	/**发送图文  发送图文给微信公众号粉丝
	 * @throws Exception
	 */
	@RequestMapping(value="/send")
	@ResponseBody
	public JSONObject send() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除InternetMaterial");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		JSONObject gjson = new JSONObject();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		PageData pdWechat = new PageData();
		Intenet org=intenetService.getIntenetById(user.getINTENET_ID());
	    pdWechat.put("INTERNET_ID",  user.getINTENET_ID());
		pdWechat=this.wechatService.findByInternetId(pdWechat);

		
		
       //获取公众号凭证
   		String AUTHORIZER_ACCESS_TOKEN = autoReplyService.getAuthToken(org.getINTENET_ID());
   		
		 JSONObject jsonObject=WechatMessageUtil.sendTwByOpenIds(AUTHORIZER_ACCESS_TOKEN, pd.getString("INTERNETMATERIAL_ID"));
		 if(jsonObject.containsKey("errcode")){
			 if(jsonObject.get("errcode").equals(0)){
				 gjson.put("msg", PublicManagerUtil.OK);
				 gjson.put("message", "发送成功");
			 }else{
				 gjson.put("msg", PublicManagerUtil.FAIL);
				 gjson.put("message", "请确认是否还有群发次数，服务号一月只能发四次");
			 }
		 }else{
			 gjson.put("msg", PublicManagerUtil.FAIL);
			 gjson.put("message", "发送失败，请联系管理员");
		 }
		
				return gjson;
	}
	
	/**修改
	 * @param PageData 包含网吧素材相关信息
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改InternetMaterial");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		pd.put("THUMB_MEDIA_ID",pd.getString("LOGO_URL") );		 
		PageData pdWechat = new PageData();
		Intenet org=intenetService.getIntenetById(user.getINTENET_ID());
	    pdWechat.put("INTERNET_ID",  user.getINTENET_ID());
		pdWechat=this.wechatService.findByInternetId(pdWechat);
		
        //获取公众号凭证
  		String AUTHORIZER_ACCESS_TOKEN = autoReplyService.getAuthToken(org.getINTENET_ID());
  		
		 List<String> imgSrt=new ArrayList<String>();
		 imgSrt=SrcUtils.ImgSrc(pd.getString("CONTENT"));
		 if(imgSrt.size()>0){
			for(int i=0;i<imgSrt.size();i++){
				 String filePath = imgSrt.get(i);	
				   if(filePath.indexOf("ueditor")>0&&filePath.indexOf("image")>0){
				    	 String[] file1 = imgSrt.get(i).split("/");
						 JSONObject resultJSON=WechatMaterialUtil.addMaterialImg(AUTHORIZER_ACCESS_TOKEN,  filePath);
						 PageData pdPic = new PageData();
						 pdPic.put("PICTURES_ID", this.get32UUID());			//主键
						 pdPic.put("TITLE", pd.getString("TITLE")+i);								//标题
						 pdPic.put("NAME", file1[file1.length-1]);							//文件名
						 pdPic.put("PATH",filePath);				//路径
						 pdPic.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
						 pdPic.put("MASTER_ID", "1");							//附属与
						 pdPic.put("INTERNET_ID", user.getINTENET_ID());
					     pdPic.put("URL", resultJSON.get("url"));	
						 picturesService.save(pdPic);
						 String count=pd.getString("CONTENT").replace(filePath,resultJSON.get("url").toString());
						 pd.put("CONTENT",count);	
				    }
			}
			
		}
		List<String> vedioSrt=new ArrayList<String>();
		vedioSrt=SrcUtils.ImgVedio(pd.getString("CONTENT"));
		if(vedioSrt.size()>0){
			for(int i=0;i<vedioSrt.size();i++){
				 String filePath = vedioSrt.get(i);	
				    if(filePath.indexOf("ueditor")>0 && filePath.indexOf("video")>0){
				    	 String[] file1 = vedioSrt.get(i).split("/");
						 JSONObject resultJSON=WechatMaterialUtil.postVideo(filePath,pd.getString("TITLE"),pd.getString("DIGEST"),AUTHORIZER_ACCESS_TOKEN  );
						 JSONObject resultJSONVdedio=WechatMaterialUtil.getVedioMaterial(resultJSON.getString("media_id"),AUTHORIZER_ACCESS_TOKEN  );
						 PageData pdPic = new PageData();
						 pdPic.put("PICTURES_ID", this.get32UUID());			//主键
						 pdPic.put("TITLE",pd.getString("TITLE")+i);								//标题
						 pdPic.put("NAME", file1[file1.length-1]);							//文件名
						 pdPic.put("PATH",filePath);				//路径
						 pdPic.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
						 pdPic.put("MASTER_ID", "1");							//附属与
						 pdPic.put("INTERNET_ID", user.getINTENET_ID());
						 pdPic.put("MATERIAL_ID", resultJSON.get("media_id"));
						 pdPic.put("URL", resultJSONVdedio.get("down_url"));	
						 picturesService.save(pdPic);
						 String count= pd.getString("CONTENT").replace(filePath,resultJSONVdedio.get("down_url").toString());
						 pd.put("CONTENT",count);	
				    }
			}
			
		}
		

		 JSONObject jsonObject=WechatMaterialUtil.updateNew(pd, AUTHORIZER_ACCESS_TOKEN,"0");


		 if(jsonObject.containsKey("errcode")){
			 if(jsonObject.get("errcode").equals(0)){
				 internetmaterialService.edit(pd);
					mv.addObject("msg",PublicManagerUtil.SUCCESS);
					mv.setViewName("save_result");
			 }else{
				    mv.addObject("msg",PublicManagerUtil.FAIL);
					mv.addObject("result","修改图文到微信公众号失败");
					mv.setViewName("save_result");
			 }
		 }else{
				mv.addObject("msg",PublicManagerUtil.FAIL);
				mv.addObject("result","修改图文到微信公众号失败");
				mv.setViewName("save_result");
		 }
		return mv;
	}
	
	/**列表
	 * @param page 网吧素材展示列表
	 * @throws Exception
	 */ 
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表InternetMaterial");
		User user = this.getUser();//得到用户

		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = internetmaterialService.list(page);	//列出InternetMaterial列表
		for (PageData pageData : varList) {
			PageData pdm = new PageData();
			pdm.put("MATERIAL_ID", pageData.get("THUMB_MEDIA_ID"));
			pdm = picturesService.findByMetrial(pdm);
			if(pdm != null){
				pageData.put("PATH", pdm.get("PATH"));
			}
		}
		mv.setViewName("system/internetmaterial/internetmaterial_list");
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
		PageData pd = this.getPageData();
		User user = this.getUser();//得到用户

	    pd.put("INTERNETID", user.getINTENET_ID());
		List<PageData>	varList = picturesService.findByIntenetMetrial(pd);	//列出Pictures列表
		mv.setViewName("system/internetmaterial/internetmaterial_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		mv.addObject("varList", varList);
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
		pd = internetmaterialService.findById(pd);	//根据ID读取
		User user = this.getUser();//得到用户

	    pd.put("INTERNETID", user.getINTENET_ID());
		List<PageData>	varList = picturesService.findByIntenetMetrial(pd);	//列出Pictures列表
		mv.setViewName("system/internetmaterial/internetmaterial_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param 批量删除网吧素材信息
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除InternetMaterial");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			internetmaterialService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出InternetMaterial到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("标题");	//1
		titles.add("网吧id");	//2
		titles.add("创建用户");	//3
		titles.add("原文链接地址");	//4
		titles.add("创建时间");	//5
		titles.add("图文内容");	//6
		titles.add("摘要");	//7
		titles.add("图文信息封面图片");	//8
		dataMap.put("titles", titles);
		List<PageData> varOList = internetmaterialService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("TITLE"));	//1
			vpd.put("var2", varOList.get(i).getString("INTERNET_ID"));	//2
			vpd.put("var3", varOList.get(i).getString("CREATE_USER"));	//3
			vpd.put("var4", varOList.get(i).getString("CONTENT_SOURCE_URL"));	//4
			vpd.put("var5", varOList.get(i).getString("CREATE_TIME"));	//5
			vpd.put("var6", varOList.get(i).getString("CONTENT"));	//6
			vpd.put("var7", varOList.get(i).getString("DIGEST"));	//7
			vpd.put("var8", varOList.get(i).getString("THUMB_MEDIA_ID"));	//8
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
