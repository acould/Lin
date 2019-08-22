package com.lk.controller.system.storeReview;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.agent.AgentManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.store.StoreReviewService;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.tb.Message.MessageManager;
import com.lk.util.*;
import com.lk.wechat.util.SmsLogUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明：门店加V审核 创建人：李泽华 创建时间：2018-04-08
 */

@Controller
@RequestMapping(value = "/storeReview")
public class StoreReviewController extends BaseController {
	String menuUrl = "storeReview/list.do"; // 菜单地址(权限用)
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "agentService")
	private AgentManager agentService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	@Resource(name = "messageService")
	private MessageManager messageService;

    @Autowired
    private StoreReviewService storeReviewService;
	
	
	/**
	 * 门店加V审核列表 展示所有进行加V申请/改IP申请的门店及其信息
	 * 
	 * @return mv(指定视图,varList--加V/改IP门店信息)
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Store");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/storeReview/storeVReview_list");
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

    @ResponseBody
    @RequestMapping(value = "/getList")
    public LayMessage getList(Page page) throws Exception{

	    //传入参数
        PageData pd = this.getPageData();
        return storeReviewService.getStoreVList(pd, page);
    }


	/**
	 * 去加V信息页面(审核(待审核),查看(已通过/未通过)) 点击审核/查看按钮进入相对页面
	 * 
	 * @param page
	 * @return mv(指定视图,pd INTENET_NAME--网吧名称,STORE_NAME--门店名称,EXPIRATION_TIME--到期时间)
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit", produces = { "application/text;charset=UTF-8" })
	public ModelAndView toAdd(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "门店加V审核");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("STORE_ID", this.getRequest().getParameter("STORE_ID"));
		pd.put("ROLE_ID", PublicManagerUtil.INTERNETROLEID);
		page.setPd(pd);
		// 查询门店加V日志--查询指定门店加V日志(查看)
		List<PageData> addvLogList = storeService.addvLog(page); 
		if (addvLogList.size() != 0) {
			mv.addObject("List", addvLogList);
		} else {
			pd.put("List", "0");
		}

		// 查询门店加V信息--查询指定门店加V信息(审核)
		List<PageData> varList = storeService.listshowv(page); 
		String STORE_PHONE=varList.get(0).getString("STORE_PHONE");
		if(StringUtil.isEmpty(STORE_PHONE)) {//去获取揽客注册手机号
				List<PageData> a = storeService.listPhone(page);//获取揽客绑定手机号
				STORE_PHONE = a.get(0).get("PHONE").toString();
				varList.get(0).put("STORE_PHONE", STORE_PHONE);
		}
		mv.addObject("map", varList.get(0));
		// 查询网吧名称(通过门店id查询网吧名称)
		List<PageData> a = storeService.queryName(page); 
		pd.put("INTENET_NAME", a.get(0).get("INTENET_NAME"));
		pd.put("STORE_NAME", a.get(0).get("STORE_NAME"));

		// 0表示去加V审核,1表示去加V查看(已通过),2表示加V查看(未通过),3表示改IP审核,4表示改IP查看
		pd.put("STATE", this.getRequest().getParameter("STATE"));
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间
		if (varList.get(0).get("CHOOSE_PACKAGE").equals("0")) { // 试用一个月
			ca.add(Calendar.MONTH, 1); // 加一个月
			Date resultDate = ca.getTime(); // 一个月后日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			pd.put("EXPIRATION_TIME", sdf.format(resultDate));
			pd.put("ODDS", "1");
		} else if (varList.get(0).get("CHOOSE_PACKAGE").equals("1")) {// 缴费一年
			ca.add(Calendar.YEAR, +1); // 年份默认加1年
			Date resultDate = ca.getTime(); // 一年后日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			pd.put("EXPIRATION_TIME", sdf.format(resultDate));
			pd.put("ODDS", varList.get(0).get("ODDS"));
		} else if (varList.get(0).get("CHOOSE_PACKAGE").equals("2")) {// 缴费一年
			pd.put("EXPIRATION_TIME", Tools.dateStr(Tools.sAddDays(new Date(), 7)));
			pd.put("ODDS", varList.get(0).get("ODDS"));
		}
		
		//记录已读
		User user = this.getUser();//得到用户
		if(StringUtil.isNotEmpty(pd.get("STATE"))){
			PageData pdMessage = new PageData();
			pdMessage.put("message_id", pd.get("STORE_ID"));
			pdMessage.put("state", "1");
			pdMessage.put("user_id", user.getUSER_ID());
			pdMessage.put("read_time", Tools.date2Str(new Date()));
			messageService.edit(pdMessage);
		}
		
		mv.setViewName("system/storeReview/storeReview_edit");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 保存审核指定门店结果 通过返回不同的状态,进行不同的操作
	 * 
	 * @param --门店id,DSTATE--状态(0--未通过,1--通过审核)
	 * @return result
	 * @throws Exception
	 */
	@RequestMapping(value = "/review")
	@ResponseBody
	public JSONObject review(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STORE_ID", this.getRequest().getParameter("STORE_ID"));
		pd.put("ROLE_ID", PublicManagerUtil.INTERNETROLEID);
		page.setPd(pd);
		List<PageData> vList = storeService.listshowv(page); // 查询门店加V信息(申请)
		JSONObject result = new JSONObject();
		String DSTATE = this.getRequest().getParameter("DSTATE");
		PageData pdUser = new PageData();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeNow = dateFm.format(new Date());
		pdUser.put("TIME", timeNow);
		pdUser.put("STATE", this.getRequest().getParameter("DSTATE"));
		pdUser.put("STORE_ID", this.getRequest().getParameter("STORE_ID"));
		pdUser.put("ADD_USERNAME", vList.get(0).get("UPDATE_USERNAME"));
		pdUser.put("ADDTIME", vList.get(0).get("UPDATETIME"));
		pdUser.put("Note", this.getRequest().getParameter("Note"));
		// 下列为门店名称,门店联系方式,两个参数(短信用)
		String STORE_NAME = vList.get(0).get("STORE_NAME").toString();
		String phone = vList.get(0).get("STORE_PHONE").toString();
		// 判断是否有预留联系方式
		if ("".equals(phone)) {
			// 如果为空,去查询揽客绑定手机号(通过门店id查询手机号)
			List<PageData> a = storeService.listPhone(page); 
			phone = a.get(0).get("PHONE").toString();
		}

		// 0表示去加V审核,1表示去加V查看(已通过),2表示加V查看(未通过),3表示改IP审核,4表示改IP查看
		String STATE = this.getRequest().getParameter("STATE");

		if (DSTATE.equals("0")) { // 0为未通过
			if (STATE.equals("0")) { // 未通过加V,去改变加V表状态(2-3)
				pd.put("Through_Time", "");
				pd.put("EXPIRATION_TIME", "");
				pd.put("UPDATETIME", timeNow); // 审核时间
				pd.put("STATE", '3'); // 加V未通过
				SmsLogUtil.sendMessageFail(STORE_NAME, phone); // 发送短信(加V失败)
				storeService.addVlog(pdUser); // 先记录到加V日志表
				storeService.updateVstate(pd); // 改变加V表状态
				result.put("result", PublicManagerUtil.TRUE);
				result.put("message", "操作成功");
				
				//保存未读消息提醒
				PageData pdMessage = new PageData();
				pdMessage.put("id", StringUtil.get32UUID());
				pdMessage.put("message_id", pd.getString("STORE_ID"));
				pdMessage.put("internet_id", vList.get(0).get("internet_id"));
				pdMessage.put("title", "计费系统信息审核失败");
				pdMessage.put("type", "pubwin");
				pdMessage.put("content", "您的门店："+STORE_NAME+"，申请的计费系统审核失败，请重新提交。原因如下："+pdUser.getString("Note"));
				pdMessage.put("announce_time", Tools.date2Str(new Date()));
				pdMessage.put("state", "0");//未读
				messageService.save(pdMessage);
			}
		}

		if (DSTATE.equals("1")) { // 加V通过审核
			if (STATE.equals("0")) { // 加V通过,优先调用pubin接口
				pd.put("ODDS", this.getRequest().getParameter("ODDS"));
				pd.put("Through_Time", timeNow); // 通过时间
				pd.put("UPDATETIME", timeNow); // 审核时间
				pd.put("EXPIRATION_TIME", this.getRequest().getParameter("EXPIRATION_TIME"));
				pd.put("STATE", '1');
				String EXPIRATION_TIME = this.getRequest().getParameter("EXPIRATION_TIME");
				storeService.addVlog(pdUser); // 先记录到加V日志表
				storeService.updateVstate(pd); // 再改变加V表状态
				SmsLogUtil.sendMessagePass(STORE_NAME, phone, EXPIRATION_TIME);// 然后发送短信(加V成功)
				// 代理商编号为空时,删除代理商和门店关系表信息
				if (StringUtil.isEmpty(vList.get(0).getString("SERVICE_ID"))) {
					agentService.deleteAgentStore(pd);
				} else {// 不为空时
					pd.put("agent_number", vList.get(0).getString("SERVICE_ID"));// 代理商编号
					List<PageData> list = agentService.exist(pd);// 查询该代理商信息
					if (list.size() == 0) {// 代理商不存在
						result.put("result", PublicManagerUtil.FAILED);
						result.put("message", "该代理商编号不存在");
						return result;
					} else {// 该代理商存在时,更新关系表
						List<PageData> list1 = agentService.selAgentStore(pd);// 先去查看关系表是否存在该门店
						pd.put("agent_id", list.get(0).get("id"));// 代理商id
						if (list1.size() == 0) {
							agentService.addAgentStore(pd);// 新增
						} else {
							agentService.editAgentStore(pd);// 修改
						}
					}
				}
				result.put("result", PublicManagerUtil.TRUE);
				result.put("message", "操作成功");
				
				//保存未读消息提醒
				PageData pdMessage = new PageData();
				pdMessage.put("id", StringUtil.get32UUID());
				pdMessage.put("message_id", pd.getString("STORE_ID"));
				pdMessage.put("internet_id", vList.get(0).get("internet_id"));
				pdMessage.put("title", "计费系统开通成功");
				pdMessage.put("type", "pubwin");
				pdMessage.put("content", "您的门店："+STORE_NAME+"，申请的计费系统已正式开通，请查看。");
				pdMessage.put("announce_time", Tools.date2Str(new Date()));
				pdMessage.put("state", "0");//未读
				messageService.save(pdMessage);
				
				//加v门店放入缓存
				Cache storeVCache = CacheManager.getStoreVCache();
                storeVCache.insertObject(PublicManagerUtil.client_prefix, "1");

			}
		}
		return result;
	}

	/**
	 * 导出到excel 将选中信息导出为excel
	 * 
	 * @param
	 * @return mv--返回指定信息和视图
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出MemberLottery到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		this.getRequest().setCharacterEncoding("UTF-8");
		pd = this.getPageData();

		// 管理员可以看到多家网吧加V申请
		Page page = new Page();
		page.setPd(pd);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("企业名称"); // 1
		titles.add("门店名称"); // 2
		titles.add("用户ID"); // 3
		titles.add("门店IP地址"); // 4
		titles.add("手机号码"); // 5
		titles.add("申请人"); // 6
		titles.add("申请时间"); // 7
		titles.add("状态"); // 8
		dataMap.put("titles", titles);

		String keywords = new String(pd.getString("keywords").getBytes("iso-8859-1"), "utf-8");// 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String STATE = this.getRequest().getParameter("STATE");
		pd.put("STATE", STATE);
		String str = this.getRequest().getParameter("str");
		List<String> list = Arrays.asList(str.split(","));
		List<PageData> varList = new ArrayList<PageData>();
		for (int j = 0; j < list.size(); j++) {
			pd.put("STORE_ID", list.get(j));
			List<PageData> varOList = storeService.listExcel(page); // 加V信息导出为excel
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("Company_Name")); // 1
				vpd.put("var2", varOList.get(i).getString("STORE_NAME")); // 1
				vpd.put("var3", varOList.get(i).getString("pubwin_store_id")); // 1
				vpd.put("var4", varOList.get(i).getString("pubwin_ip")); // 1
				vpd.put("var5", varOList.get(i).getString("STORE_PHONE")); // 1
				vpd.put("var6", varOList.get(i).getString("UPDATE_USERNAME")); // 1
				vpd.put("var7", varOList.get(i).getString("UPDATETIME")); // 1
				if (varOList.get(i).get("STATE").equals(0)) {
					vpd.put("var8", "未绑定");
				}
				if (varOList.get(i).get("STATE").equals(1)) {
					vpd.put("var8", "已绑定");
				}
				if (varOList.get(i).get("STATE").equals(2)) {
					vpd.put("var8", "加V待审核");
				}
				if (varOList.get(i).get("STATE").equals(3)) {
					vpd.put("var8", "加V未通过");
				}
				if (varOList.get(i).get("STATE").equals(4)) {
					vpd.put("var8", "改IP待审核");
				}
				if (varOList.get(i).get("STATE").equals(5)) {
					vpd.put("var8", "改IP未通过");
				}
				varList.add(vpd);
			}
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}


	@ResponseBody
    @RequestMapping(value = "/ajaxOfficial")
	public Message ajaxOfficial() throws Exception{

        PageData pd = this.getPageData();

        return storeReviewService.toOfficial(pd);
    }

    @ResponseBody
    @RequestMapping(value = "/ajaxDelay")
    public Message ajaxDelay() throws Exception{

        PageData pd = this.getPageData();

        return storeReviewService.toDelay(pd);
    }

}
