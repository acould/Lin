package com.lk.controller.weixin.imgmsg;

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
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.SrcUtils;
import com.lk.util.Tools;
import com.lk.util.Jurisdiction;
import com.lk.wechat.util.WechatMaterialUtil;
import com.lk.wechat.util.WeixinUtil;
import com.lk.service.information.pictures.PicturesManager;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.wechat.WechatManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.imgmsg.ImgmsgService;
import com.lk.service.weixin.textmsg.TextmsgService;

/** 
 * 类名称：ImgmsgController
 * 创建人：FH 
 * 创建时间：2015-05-10
 */
@Controller
@RequestMapping(value="/imgmsg")
public class ImgmsgController extends BaseController {
	
	String menuUrl = "imgmsg/list.do"; //菜单地址(权限用)
	@Resource(name="imgmsgService")
	private ImgmsgService imgmsgService;
	@Resource(name="picturesService")
	private PicturesManager picturesService;
	@Resource(name="textmsgService")
	private TextmsgService textmsgService;
	@Resource(name="intenetService")
	private IntenetManager intenetService;
	@Resource(name="wechatService")
	private WechatManager wechatService;
	@Resource(name="terraceService")
	private TerraceManager terraceService;
    @Resource(name = "autoReplyService")
    private AutoReplyService autoReplyService;
    
	
	/**新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Imgmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		
		
		pd.put("URL", "url");
		JSONObject json = getMediaId(user,pd,"save",null);
		if(json.containsKey("errcode")){
			if(json.getString("errcode").equals("0")){//新增永久素材成功
				pd.put("IMGMSG_ID", this.get32UUID());	//主键
				pd.put("INTERNET_ID", user.getINTENET_ID());
				pd.put("CREATETIME", Tools.date2Str(new Date()));
				pd.put("MEDIA_ID", json.getString("media_id"));
				pd.put("URL", json.getString("url"));
				
				pd.put("MATERIAL_ID", pd.getString("IMG_URL"));
				PageData pdImg = picturesService.findByMetrial(pd);
				pd.put("PIC_URL", pdImg.getString("URL"));
				
				imgmsgService.save(pd);
				mv.addObject("msg",PublicManagerUtil.SUCCESS);
				mv.setViewName("save_result");
				return mv;
			}else if(json.getString("errcode").equals(PublicManagerUtil.ERRCODE2)){
				mv.addObject("msg",PublicManagerUtil.FAIL);
				mv.addObject("result","每日新增达到上线！");
				mv.setViewName("save_result");
				return mv;
			}else{
				mv.addObject("msg",PublicManagerUtil.FAIL);
				mv.addObject("result","新增永久图文素材失败！");
				mv.setViewName("save_result");
				return mv;
			}
		}else{
			pd.put("IMGMSG_ID", this.get32UUID());	//主键
			pd.put("INTERNET_ID", user.getINTENET_ID());
			pd.put("CREATETIME", Tools.date2Str(new Date()));
			pd.put("MEDIA_ID", json.getString("media_id"));
			pd.put("URL", json.getString("url"));
			pd.put("MATERIAL_ID", pd.getString("IMG_URL"));
			PageData pdImg = picturesService.findByMetrial(pd);
			pd.put("PIC_URL", pdImg.getString("URL"));
			
			imgmsgService.save(pd);
			mv.addObject("msg",PublicManagerUtil.SUCCESS);
			mv.setViewName("save_result");
			return mv;
		}
		
	}
	
	/**删除
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, "删除Imgmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = this.getPageData();
		User user = this.getUser();//得到用户

		PageData pdImgMsg = imgmsgService.findById(pd);
		JSONObject json = getMediaId(user, pd, "delete", pdImgMsg);

		if(json.containsKey("errcode")){
			if(json.getString("errcode").equals("0")){
				imgmsgService.delete(pd);
				out.write(PublicManagerUtil.SUCCESS);
				out.close();
			}else{
				out.write("删除永久图文素材失败");
				out.close();
			}
		}else{
			imgmsgService.delete(pd);
			out.write(PublicManagerUtil.SUCCESS);
			out.close();
		}
		
	}
	
	/**修改
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Imgmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
		PageData pd = this.getPageData();
		
		PageData pdImgMsg = imgmsgService.findById(pd);
		JSONObject json = getMediaId(user,pd,"edit",pdImgMsg);
		
		if(json.containsKey("errcode")){
			if(json.getString("errcode").equals("0")){
				pd.put("MATERIAL_ID", pd.getString("IMG_URL"));
				PageData pdImg = picturesService.findByMetrial(pd);
				pd.put("PIC_URL", pdImg.getString("URL"));
				imgmsgService.edit(pd);
				mv.addObject("msg",PublicManagerUtil.SUCCESS);
				mv.setViewName("save_result");
				return mv;
			}else{
				mv.addObject("msg",PublicManagerUtil.FAIL);
				mv.addObject("result","修改永久图文素材失败");
				mv.setViewName("save_result");
				return mv;
			}
		}else{
			pd.put("MATERIAL_ID", pd.getString("IMG_URL"));
			PageData pdImg = picturesService.findByMetrial(pd);
			pd.put("PIC_URL", pdImg.getString("URL"));
			imgmsgService.edit(pd);
			mv.addObject("msg",PublicManagerUtil.SUCCESS);
			mv.setViewName("save_result");
			return mv;
		}
	}
	
	/**列表
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Imgmsg");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
		PageData pd = this.getPageData();
		
		String KEYWORD = this.getRequest().getParameter("KEYWORD");
		if(null != KEYWORD && !"".equals(KEYWORD)){
			pd.put("KEYWORD", KEYWORD.trim());
		}
		pd.put("INTERNET_ID", user.getINTENET_ID());
		page.setPd(pd);
		List<PageData>	varList = imgmsgService.list(page);	//列出Imgmsg列表
		
		mv.setViewName("weixin/imgmsg/imgmsg_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd() throws Exception{
		logBefore(logger, "去新增Imgmsg页面");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
		PageData pd = this.getPageData();
		
		pd.put("INTERNETID", user.getINTENET_ID());
		List<PageData> imgList = picturesService.findByIntenetMetrial(pd);	//列出Pictures列表
		
		mv.setViewName("weixin/imgmsg/imgmsg_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		mv.addObject("imgList", imgList);
		return mv;
	}	
	
	/**去修改页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit() throws Exception{
		logBefore(logger, "去修改Imgmsg页面");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
		PageData pd = this.getPageData();
		
		pd.put("INTERNETID", user.getINTENET_ID());
		List<PageData> imgList = picturesService.findByIntenetMetrial(pd);	//列出Pictures列表
		
		pd.put("IMGMSG_ID", this.getRequest().getParameter("IMGMSG_ID"));
		pd = imgmsgService.findById(pd);	//根据ID读取
		mv.setViewName("weixin/imgmsg/imgmsg_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("imgList", imgList);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**批量删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, "批量删除Imgmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();

		String ArrayDATA_IDS[] = pd.getString("DATA_IDS").split(",");
		
		for(int i=0;i<ArrayDATA_IDS.length;i++){//批量删除永久素材
			pd.put("IMGMSG_ID", ArrayDATA_IDS[i]);
			PageData pdImgMsg = imgmsgService.findById(pd);
			JSONObject json = getMediaId(user, pd, "delete", pdImgMsg);
			if(json.containsKey("errcode")){
				if(!json.getString("errcode").equals("0")){
					map.put("result", "批量删除永久图文素材失败");
					return AppUtil.returnObject(pd, map);
				}
			}
		}
		
		if(ArrayDATA_IDS.length > 0){
			imgmsgService.deleteAll(ArrayDATA_IDS);
			map.put("result", PublicManagerUtil.SUCCESS);
			map.put("msg", PublicManagerUtil.OK);
		}else{
			map.put("msg", PublicManagerUtil.NO);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Imgmsg到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("关键词");	//1
			titles.add("创建时间");	//2
			titles.add("状态");	//3
			titles.add("备注");	//4
			titles.add("标题1");	//5
			titles.add("描述1");	//6
			titles.add("图片地址1");	//7
			titles.add("超链接1");	//8
			titles.add("标题2");	//9
			titles.add("描述2");	//10
			titles.add("图片地址2");	//11
			titles.add("超链接2");	//12
			titles.add("标题3");	//13
			titles.add("描述3");	//14
			titles.add("图片地址3");	//15
			titles.add("超链接3");	//16
			titles.add("标题4");	//17
			titles.add("描述4");	//18
			titles.add("图片地址4");	//19
			titles.add("超链接4");	//20
			titles.add("标题5");	//21
			titles.add("描述5");	//22
			titles.add("图片地址5");	//23
			titles.add("超链接5");	//24
			titles.add("标题6");	//25
			titles.add("描述6");	//26
			titles.add("图片地址6");	//27
			titles.add("超链接6");	//28
			titles.add("标题7");	//29
			titles.add("描述7");	//30
			titles.add("图片地址7");	//31
			titles.add("超链接7");	//32
			titles.add("标题8");	//33
			titles.add("描述8");	//34
			titles.add("图片地址8");	//35
			titles.add("超链接8");	//36
			dataMap.put("titles", titles);
			List<PageData> varOList = imgmsgService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("KEYWORD"));	//1
				vpd.put("var2", varOList.get(i).getString("CREATETIME"));	//2
				vpd.put("var3", varOList.get(i).get("STATUS").toString());	//3
				vpd.put("var4", varOList.get(i).getString("BZ"));	//4
				vpd.put("var5", varOList.get(i).getString("TITLE1"));	//5
				vpd.put("var6", varOList.get(i).getString("DESCRIPTION1"));	//6
				vpd.put("var7", varOList.get(i).getString("IMGURL1"));	//7
				vpd.put("var8", varOList.get(i).getString("TOURL1"));	//8
				vpd.put("var9", varOList.get(i).getString("TITLE2"));	//9
				vpd.put("var10", varOList.get(i).getString("DESCRIPTION2"));	//10
				vpd.put("var11", varOList.get(i).getString("IMGURL2"));	//11
				vpd.put("var12", varOList.get(i).getString("TOURL2"));	//12
				vpd.put("var13", varOList.get(i).getString("TITLE3"));	//13
				vpd.put("var14", varOList.get(i).getString("DESCRIPTION3"));	//14
				vpd.put("var15", varOList.get(i).getString("IMGURL3"));	//15
				vpd.put("var16", varOList.get(i).getString("TOURL3"));	//16
				vpd.put("var17", varOList.get(i).getString("TITLE4"));	//17
				vpd.put("var18", varOList.get(i).getString("DESCRIPTION4"));	//18
				vpd.put("var19", varOList.get(i).getString("IMGURL4"));	//19
				vpd.put("var20", varOList.get(i).getString("TOURL4"));	//20
				vpd.put("var21", varOList.get(i).getString("TITLE5"));	//21
				vpd.put("var22", varOList.get(i).getString("DESCRIPTION5"));	//22
				vpd.put("var23", varOList.get(i).getString("IMGURL5"));	//23
				vpd.put("var24", varOList.get(i).getString("TOURL5"));	//24
				vpd.put("var25", varOList.get(i).getString("TITLE6"));	//25
				vpd.put("var26", varOList.get(i).getString("DESCRIPTION6"));	//26
				vpd.put("var27", varOList.get(i).getString("IMGURL6"));	//27
				vpd.put("var28", varOList.get(i).getString("TOURL6"));	//28
				vpd.put("var29", varOList.get(i).getString("TITLE7"));	//29
				vpd.put("var30", varOList.get(i).getString("DESCRIPTION7"));	//30
				vpd.put("var31", varOList.get(i).getString("IMGURL7"));	//31
				vpd.put("var32", varOList.get(i).getString("TOURL7"));	//32
				vpd.put("var33", varOList.get(i).getString("TITLE8"));	//33
				vpd.put("var34", varOList.get(i).getString("DESCRIPTION8"));	//34
				vpd.put("var35", varOList.get(i).getString("IMGURL8"));	//35
				vpd.put("var36", varOList.get(i).getString("TOURL8"));	//36
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	/**判断关键词是否存在
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/hasK")
	@ResponseBody
	public Object hasK() throws Exception{
		User user = this.getUser();//得到用户
	    
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = PublicManagerUtil.SUCCESS;
		PageData pd = this.getPageData();
		pd.put("INTERNET_ID", user.getINTENET_ID());
		pd.put("KEYWORD", this.getRequest().getParameter("KEYWORD"));
		
		if(textmsgService.findByKw(pd) != null){//先检查文本回复有没有该关键字
			errInfo = PublicManagerUtil.EXIST;
		}else{
			String imgmsgId = this.getRequest().getParameter("IMGMSG_ID");
			if(imgmsgId == null || imgmsgId.equals("")){//为空时，表示是新增功能
				List<PageData> imgList = imgmsgService.listByKw(pd);
				if(imgList.size() > 0){//不为空，表示已有该关键字
					if(imgList.size() >=  8){//最多回复图文最多8条
						errInfo = PublicManagerUtil.FAIL;
					}else{
						errInfo = imgList.size()+"";
					}
				}
			}
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	public JSONObject getMediaId(User user,PageData pd,String flag,PageData pdUpdate) throws Exception{
		PageData pdWechat = new PageData();
		Intenet internet = intenetService.getIntenetById(user.getINTENET_ID());
	    pdWechat.put("INTERNET_ID",  user.getINTENET_ID());
		pdWechat=this.wechatService.findByInternetId(pdWechat);
		
        //获取公众号凭证
        String AUTHORIZER_ACCESS_TOKEN = autoReplyService.getAuthToken(user.getINTENET_ID());
		
		JSONObject jsonObject = new JSONObject();
		if(flag.equals("delete")){//删除永久图文素材
			jsonObject = WechatMaterialUtil.deleteMaterial(pdUpdate.getString("MEDIA_ID"), AUTHORIZER_ACCESS_TOKEN);
			return jsonObject;
		}else{
			 List<String> imgSrt=new ArrayList<String>();
			 imgSrt = SrcUtils.ImgSrc(pd.getString("CONTENT"));
			 if(imgSrt.size()>0){
				for(int i=0;i<imgSrt.size();i++){
					 String filePath = imgSrt.get(i);	
					   if(filePath.indexOf("image")>0){
					    	 String[] file1 = imgSrt.get(i).split("/");
							 JSONObject resultJSON=WechatMaterialUtil.addMaterialImg(AUTHORIZER_ACCESS_TOKEN,  filePath);
							 PageData pdPic = new PageData();
							 pdPic.put("PICTURES_ID", this.get32UUID());			
							 pdPic.put("TITLE", pd.getString("TITLE")+i);							
							 pdPic.put("NAME", file1[file1.length-1]);		
							 pdPic.put("PATH",filePath);		
							 pdPic.put("CREATETIME", Tools.date2Str(new Date()));	
							 pdPic.put("MASTER_ID", "1");					
							 pdPic.put("INTERNET_ID", user.getINTENET_ID());
						     pdPic.put("URL", resultJSON.get("url"));	
							 picturesService.save(pdPic);
							 String count = pd.getString("CONTENT").replace(filePath,resultJSON.get("url").toString());
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
					    	 JSONObject resultJSON=WechatMaterialUtil.postVideo(filePath,pd.getString("TITLE"),pd.getString("DESCRIPTION"),AUTHORIZER_ACCESS_TOKEN  );
					    	 JSONObject resultJSONVdedio=WechatMaterialUtil.getVedioMaterial(resultJSON.getString("media_id"),AUTHORIZER_ACCESS_TOKEN  );
							 PageData pdPic = new PageData();
							 pdPic.put("PICTURES_ID", this.get32UUID());	
							 pdPic.put("TITLE",pd.getString("TITLE")+i);			
							 pdPic.put("NAME", file1[file1.length-1]);	
							 pdPic.put("PATH",filePath);
							 pdPic.put("CREATETIME", Tools.date2Str(new Date()));
							 pdPic.put("MASTER_ID", "1");	
							 pdPic.put("INTERNET_ID", user.getINTENET_ID());
							 pdPic.put("MATERIAL_ID", resultJSON.get("media_id"));
							 pdPic.put("URL", resultJSONVdedio.get("down_url"));	
							 picturesService.save(pdPic);
							 String count= pd.getString("CONTENT").replace(filePath,resultJSONVdedio.get("down_url").toString());
							 pd.put("CONTENT",count);	
					    }
				}
				
			}
		
		
			pd.put("DIGEST", pd.getString("DESCRIPTION"));
			pd.put("CREATE_USER", pd.getString("AUTHOR"));
			pd.put("THUMB_MEDIA_ID", pd.getString("IMG_URL"));
			pd.put("CONTENT_SOURCE_URL", pd.getString("SOURCE_URL"));
			JSONObject json = new JSONObject();
			if(flag.equals("save")){
				jsonObject = WechatMaterialUtil.postNew(pd, AUTHORIZER_ACCESS_TOKEN);
				if(jsonObject.containsKey("errcode")){
					if(!jsonObject.getString("errcode").equals("0")){
						return jsonObject;
					}
				}
				json = WechatMaterialUtil.getVedioMaterial(jsonObject.getString("media_id"), AUTHORIZER_ACCESS_TOKEN);
				List<JSONObject> rtnList = new ArrayList<JSONObject>(); 
				rtnList = (List<JSONObject>) json.get("news_item");
				
				jsonObject.put("url", rtnList.get(0).getString("url"));
			}else{
				pd.put("INTERNETMATERIAL_ID", pdUpdate.getString("MEDIA_ID"));

				jsonObject = WechatMaterialUtil.updateNew(pd, AUTHORIZER_ACCESS_TOKEN,"0");

			}
			return jsonObject;
		}
		
		
	}
}
