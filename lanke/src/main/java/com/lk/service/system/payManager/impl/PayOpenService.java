package com.lk.service.system.payManager.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.payManager.PayOpenManager;
import com.lk.service.system.smslog.SmslogManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.tb.Message.MessageManager;
import com.lk.util.CommonJudge;
import com.lk.util.Const;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PathUtil;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.SmsLogUtil;

@Service("payOpenService")
public class PayOpenService implements PayOpenManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "smslogService")
	private SmslogManager smslogService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	@Resource(name = "messageService")
	private MessageManager messageService;
	
	
	/**
	 * 保存开通资料
	 * @param pd（保存数据）
	 * @return
	 * @throws Exception
	 */
	public JSONObject savePayOpen(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		User user = (User) pd.get("user");
		pd.put("internet_id", user.getINTENET_ID());
		
		if(StringUtil.isEmpty(user)){
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}
		if(StringUtil.isEmpty(pd.getString("store_id"))){
			result.put("result", "false");
			result.put("message", "无效的门店");
			return result;
		}
		//判断资料审核中，或者审核已通过状态，无法再修改资料
		PageData pdData = new PageData();
		pdData.put("store_id", pd.getString("store_id"));
		pdData = (PageData) dao.findForObject("InternetDatumMapper.findByStoreId", pdData);
		if(StringUtil.isNotEmpty(pdData)){
			if(StringUtil.isNotEmpty(pdData.get("state")) && pdData.get("state").toString().equals("2")){
				result.put("result", "false");
				result.put("message", "正在审核中，请稍后");
				return result;
			}else if(StringUtil.isNotEmpty(pdData.get("state")) && pdData.get("state").toString().equals("1")){
				result.put("result", "false");
				result.put("message", "审核已通过");
				return result;
			}
		}
		
		if(StringUtil.isEmpty(pd.getString("phone"))){
			//获取操作用户注册的手机号
			pd.put("phone", user.getPHONE());
		}
		//提交时
		if(pd.getString("data_type").equals("0")){
			//待审核
			pd.put("state", "2");//2表示
			//需要判断手机号和身份证
			result = CommonJudge.judgePhone(pd.getString("phone"));
			if(result.getString("result").equals("false")){
				return result;
			}
			result = CommonJudge.judgeCard(pd.getString("corporate_id"));
			if(result.getString("result").equals("false")){
				return result;
			}
			
			//需要验证码
			PageData pdSms = new PageData();
			pdSms.put("phone", pd.getString("phone"));
			pdSms = smslogService.findLastMsg(pdSms);
			if(StringUtil.isEmpty(pdSms) || !pdSms.getString("CODE").equals(pd.getString("code"))){
				result.put("result", "false");
				result.put("phone", pd.getString("phone"));
				result.put("err_type", "err_code");
				result.put("message", "请输入正确的验证码");
				return result;
			}
		}
		
		
		if(StringUtil.isEmpty(pdData)){
			//新增
			pd.put("id", StringUtil.get32UUID());
			
			pd.put("createtime", Tools.date2Str(new Date()));
			pd.put("updatetime", pd.get("createtime"));
			
			//新增资料图片
			pd.put("img_flag", "add");
			addImg(pd);
			
			//新增网吧资料
			dao.save("InternetDatumMapper.save", pd);
			
		}else{
			//修改
			pd.put("updatetime", Tools.date2Str(new Date()));
			
			//修改网吧资料图片
			pd.put("img_flag", "edit");
			addImg(pd);
			
			//修改网吧资料
			dao.update("InternetDatumMapper.edit", pd);
		}
		
		result.put("result", "true");
		if(pd.getString("data_type").equals("0")){
			result.put("message", "提交成功");
		}else if(pd.getString("data_type").equals("1")){
			result.put("message", "保存成功");
		}
		result.put("id", pd.getString("id"));
		result.put("phone", pd.getString("phone"));
		return result;
	}
	
	//新增或修改5中类型图片
	public void addImg(PageData pd) throws Exception{
		PageData pdImg = new PageData();
		pdImg.put("intenetdatum_id", pd.getString("id"));
		
		String[] typeList = PublicManagerUtil.typeList;
		
		for(int i=0;i<typeList.length;i++){
			String type = typeList[i];
			if(StringUtil.isNotEmpty(pd.getString(type)) && pd.getString(type).startsWith("data:image")){
				pdImg.put("type", type);
				pdImg.put("flag", "add");
				
				if(pd.getString("img_flag").equals("add")){
					pdImg.put("sort", "1");
					pdImg.put("createtime", pd.get("updatetime"));
					pdImg = putImg(pd, pdImg);
					dao.save("DatumMapper.save", pdImg);
				}else{
					pdImg = (PageData) dao.findForObject("DatumMapper.findByInternetAdnType", pdImg);
					if(StringUtil.isEmpty(pdImg)){
						pdImg = new PageData();
						pdImg.put("intenetdatum_id", pd.getString("id"));
						pdImg.put("createtime", pd.get("updatetime"));
						pdImg.put("sort", "1");
						pdImg.put("type", type);
						pdImg.put("flag", "add");
						pdImg = putImg(pd, pdImg);
						dao.save("DatumMapper.save", pdImg);
					}else{
						pdImg.put("flag", "edit");
						pdImg = putImg(pd, pdImg);
						dao.update("DatumMapper.editByInternetAdnType", pdImg);
					}
				}
				
			}
		}
	}
	
	//保存图片到本地
	public PageData putImg(PageData pd, PageData pdImg) throws Exception{
		if(pdImg.getString("flag").equals("add")){
			pdImg.put("id", StringUtil.get32UUID());
		}
		
		//上传到本地
		String file = pd.getString(pdImg.getString("type"));
		String suffixName = "";
		if(file.contains(PublicManagerUtil.BASE64_JPEG)){
			suffixName = PublicManagerUtil.SUFFIXNAME_JPG;
		}else if(file.contains(PublicManagerUtil.BASE64_PNG)){
			suffixName = PublicManagerUtil.SUFFIXNAME_PNG;
		}else{
			suffixName = PublicManagerUtil.SUFFIXNAME_JPG;
		}
		
		String fileName = new Date().getTime() + suffixName;
		String path = pd.getString("internet_id") + "/" + pd.getString("store_id") + "/" + fileName ;
		String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;
		Tools.pic(file,filePath);
		pdImg.put("url", path);
		
		return pdImg;
	}
	
	
	
	/**
	 * 根据主键id查找
	 * @param pd（）
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("InternetDatumMapper.findById", pd);
	}
	
	
	/**
	 * 后台，开通的列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> payOpenList(PageData pd) throws Exception{
		//关键词检索
		User user = (User) pd.get("user");
		Page page = (Page) pd.get("page");
		pd.put("internet_id", user.getINTENET_ID());
		
		page.setPd(pd);
		return (List<PageData>) dao.findForList("InternetDatumMapper.payOpenlistPage", page);
	}
	
	
	/**
	 * 保存审核情况
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveReview(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		User user = (User) pd.get("user");
		pd.put("state", pd.getString("review_state"));
		if(pd.getString("state").equals("0") && StringUtil.isEmpty(pd.getString("opinion"))){
			result.put("result", "false");
			result.put("message", "请填写不通过原因");
			return result;
		}
		
		//判断是否已通过
		PageData pdData = new PageData();
		pdData.put("id", pd.getString("intenetdatum_id"));
		pdData = (PageData) dao.findForObject("InternetDatumMapper.findById", pdData);
		if(StringUtil.isNotEmpty(pdData)){
			if(StringUtil.isNotEmpty(pdData.get("state")) && !pdData.get("state").toString().equals("2")){
				result.put("result", "false");
				result.put("message", "只有在待审核状态时，才能审核");
				return result;
			}else if(StringUtil.isNotEmpty(pdData.get("state")) && pdData.get("state").toString().equals("1")){
				result.put("result", "true");
				result.put("message", "资料已通过");
				return result;
			}
		}else{
			result.put("result", "false");
			result.put("message", "参数错误");
			return result;
		}
		
		PageData pdExamine = new PageData();
		pdExamine.put("intenetdatum_id", pd.getString("intenetdatum_id"));
		pdExamine.put("state", "1");
		pdExamine = (PageData) dao.findForObject("InternetExamineMapper.findByInternetDatumId", pdExamine);
		if(StringUtil.isNotEmpty(pdExamine)){
			result.put("result", "true");
			result.put("message", "审核已通过");
			return result;
		}
		
		//保存审核情况
		pdExamine = new PageData();
		pdExamine.put("intenetdatum_id", pd.getString("intenetdatum_id"));
		pdExamine.put("state", pd.getString("state"));
		pdExamine.put("id", StringUtil.get32UUID());
		pdExamine.put("opinion", pd.getString("opinion"));
		pdExamine.put("examine_id", user.getUSER_ID());
		pdExamine.put("examine_time", Tools.date2Str(new Date()));
		pdExamine.put("createtime", Tools.date2Str(new Date()));
		dao.save("InternetExamineMapper.save", pdExamine);
		
		PageData pdInternetDatum = new PageData();
		pdInternetDatum.put("id", pd.getString("intenetdatum_id"));
		pdInternetDatum.put("state", pd.getString("review_state"));
		pdInternetDatum.put("data_type", 1);//保存状态
		if(pd.getString("review_state").equals("1")){
			//审核成功时
			pdInternetDatum.put("status", 2);//等待快递
			pdInternetDatum.put("data_type", 0);//提交状态
			
			//保存资料审核通过消息提醒
			PageData pdMessage = new PageData();
			pdMessage.put("id", StringUtil.get32UUID());
			pdMessage.put("message_id", pd.getString("intenetdatum_id"));
			pdMessage.put("internet_id", pdData.getString("internet_id"));//不是操作用户的网吧id
			pdMessage.put("title", "在线支付开通—资料审核通过");
			pdMessage.put("type", "payonline");
			pdMessage.put("content", "您的企业"+pdData.get("enterprise_name")+"，商户资料已经审核通过，请您尽快前往下载入网清单，完成在线支付的后续申请步骤");
			pdMessage.put("announce_time", Tools.date2Str(new Date()));
			pdMessage.put("state", "0");//未读
			messageService.save(pdMessage);

			//资料审核通过，给用户发送短信通知
			SmsLogUtil.sendOpenCondition(pdData.getString("phone"), pdData.getString("enterprise_name"), "material_success");
		}else if(pd.getString("review_state").equals("0")){
			//保存资料审核不通过消息提醒
			PageData pdMessage = new PageData();
			pdMessage.put("id", StringUtil.get32UUID());
			pdMessage.put("message_id", pd.getString("intenetdatum_id"));
			pdMessage.put("internet_id", pdData.getString("internet_id"));//不是操作用户的网吧id
			pdMessage.put("title", "在线支付开通—资料审核不通过");
			pdMessage.put("type", "payonline");
			pdMessage.put("content", "您的企业"+pdData.get("enterprise_name")+"，商户资料审核未通过。原因如下："+pd.getString("opinion")+"。<br>请尽快修改资料，重新提交！");
			pdMessage.put("announce_time", Tools.date2Str(new Date()));
			pdMessage.put("state", "0");//未读
			messageService.save(pdMessage);
			
			//资料审核不通过，给用户发送短信通知
			SmsLogUtil.sendOpenCondition(pdData.getString("phone"), pdData.getString("enterprise_name"), "material_false");
		}
		dao.update("InternetDatumMapper.edit", pdInternetDatum);
		
		
		result.put("result", "true");
		result.put("message", "提交成功");
		return result;
	}
	
	/**
	 * 获取日志
	 * @param pd（intenetdatum_id，state）
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByInternetDatumId(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("InternetExamineMapper.findByInternetDatumId", pd);
	}
	
	/**
	 * 保存快递单号
	 * @param pd（user，快递信息）
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveExpress(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		if(StringUtil.isEmpty(pd.getString("intenetdatum_id2"))
				|| StringUtil.isEmpty(pd.getString("express_company"))
				||StringUtil.isEmpty(pd.getString("express_number"))){
			result.put("result", "false");
			result.put("message", "参数为空");
			return result;
		}
		
		PageData pdData = new PageData();
		pdData.put("id", pd.getString("intenetdatum_id2"));
		pdData = (PageData) dao.findForObject("InternetDatumMapper.findById", pdData);
		if(StringUtil.isNotEmpty(pdData)){
			if(StringUtil.isNotEmpty(pdData.get("status")) && pdData.get("status").toString().equals("1")){
				result.put("result", "true");
				result.put("message", "已开通，无须再提交");
				return result;
			}else if(StringUtil.isNotEmpty(pdData.get("status")) && pdData.get("status").toString().equals("0")){
				result.put("result", "true");
				result.put("message", "快递信息已保存，无须再提交");
				return result;
			}
		}else{
			result.put("result", "false");
			result.put("message", "参数错误");
			return result;
		}
		
		PageData pdExpress = new PageData();
		pdExpress.put("id", pd.getString("intenetdatum_id2"));
		pdExpress.put("express_company", pd.getString("express_company"));
		pdExpress.put("express_number", pd.getString("express_number"));
		pdExpress.put("status", "0");//待开通
		dao.update("InternetDatumMapper.edit", pdExpress);
		
		
		result.put("result", "true");
		result.put("message", "提交成功");
		return result;
	}
	
	
	/**
	 * 开通在线支付
	 * @param pd（主键id，商户号）
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveOpen(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		User user = (User) pd.get("user");
		
		if(StringUtil.isEmpty(pd.getString("intenetdatum_id"))
				|| StringUtil.isEmpty(pd.getString("business_number").trim())){
			result.put("result", "false");
			result.put("message", "参数为空");
			return result;
		}
		
		PageData pdData = new PageData();
		pdData.put("id", pd.getString("intenetdatum_id"));
		pdData = (PageData) dao.findForObject("InternetDatumMapper.findById", pdData);
		if(StringUtil.isNotEmpty(pdData)){
			if(StringUtil.isNotEmpty(pdData.get("status")) && !pdData.get("status").toString().equals("0")){
				result.put("result", "false");
				result.put("message", "只有在待开通状态时，才能开通");
				return result;
			}else if(StringUtil.isNotEmpty(pdData.get("status")) && pdData.get("status").toString().equals("1")){
				result.put("result", "true");
				result.put("message", "已开通");
				return result;
			}
		}else{
			result.put("result", "false");
			result.put("message", "参数错误");
			return result;
		}
		
		PageData pdOpen = new PageData();
		pdOpen.put("id", pd.getString("intenetdatum_id"));
		pdOpen.put("business_number", pd.getString("business_number"));
		pdOpen.put("status", "1");//已开通
		dao.update("InternetDatumMapper.edit", pdOpen);
		
		//保存未读消息提醒
		PageData pdMessage = new PageData();
		pdMessage.put("id", StringUtil.get32UUID());
		pdMessage.put("message_id", pd.getString("intenetdatum_id"));
		pdMessage.put("internet_id", pdData.get("internet_id"));
		pdMessage.put("title", "在线支付开通成功");
		pdMessage.put("type", "payonline");
		pdMessage.put("content", "您的企业"+pdData.get("enterprise_name")+"，在网晶揽客申请的在线支付已正式开通，详情请查看");
		pdMessage.put("announce_time", Tools.date2Str(new Date()));
		pdMessage.put("state", "0");//未读
		messageService.save(pdMessage);
		
		//开通成功，发送短信提醒
		SmsLogUtil.sendOpenCondition(pdData.getString("phone"), pdData.getString("enterprise_name"), "open_success");
		
		result.put("result", "true");
		result.put("message", "开通成功");
		return result;
	}
	
	
	
	/**
	 * 导出Excel
	 * @param pd（搜索条件）
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportExcel(PageData pd) throws Exception{
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("序号");	
		titles.add("公众号");
		titles.add("门店名称");
		titles.add("企业名称");
		titles.add("法人姓名");
		
		titles.add("申请时间");
		titles.add("快递单号");
		titles.add("资料审核");
		titles.add("开通状态");
		titles.add("商户号");
		dataMap.put("titles", titles);
		List<PageData> varList = (List<PageData>) dao.findForList("InternetDatumMapper.excelPayOpenlist", pd);
		
		List<PageData> excelList = new ArrayList<PageData>();
		for(int i=0;i<varList.size();i++){
			PageData pdExcel = varList.get(i);
			PageData vpd = new PageData();
			vpd.put("var1", String.valueOf(i+1));
			vpd.put("var2", pdExcel.get("intenet_name"));
			vpd.put("var3", pdExcel.get("store_name"));
			vpd.put("var4", pdExcel.get("enterprise_name"));
			vpd.put("var5", pdExcel.get("corporate_name"));
			
			vpd.put("var6", pdExcel.get("updatetime").toString());
			vpd.put("var7", pdExcel.get("express_number"));
			if(StringUtil.isNotEmpty(pdExcel.get("state"))){
				if(pdExcel.get("state").toString().equals("0")){
					vpd.put("var8", "未通过");
				}else if(pdExcel.get("state").toString().equals("1")){
					vpd.put("var8", "已通过");
				}else if(pdExcel.get("state").toString().equals("2")){
					vpd.put("var8", "待审核");
				}
			}
			if(StringUtil.isNotEmpty(pdExcel.get("status"))){
				if(pdExcel.get("status").toString().equals("0")){
					vpd.put("var9", "待开通");
				}else if(pdExcel.get("status").toString().equals("1")){
					vpd.put("var9", "已开通");
				}else if(pdExcel.get("status").toString().equals("2")){
					vpd.put("var9", "等待快递");
				}
			}
			vpd.put("var10", pdExcel.get("business_number"));
			excelList.add(vpd);
		}
		dataMap.put("varList", excelList);
		ObjectExcelView erv = new ObjectExcelView();
		return new ModelAndView(erv,dataMap);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
