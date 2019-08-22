package com.lk.controller.system.intintegral;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import javax.jws.WebParam;

import com.lk.entity.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Jurisdiction;
import com.lk.util.StringUtil;
import com.lk.service.system.intintegral.IntIntegralManager;
import com.lk.service.system.sysintegral.SysIntegralManager;

/** 
 * 说明：j积分规则表(积分奖励设置)
 * 创建人：洪智鹏
 * 创建时间：2016-11-01
 */
@Controller
@RequestMapping(value="/intintegral")
public class IntIntegralController extends BaseController {
	
	String menuUrl = "intintegral/list.do"; //菜单地址(权限用)
	@Resource(name="intintegralService")
	private IntIntegralManager intintegralService;
	@Resource(name="sysintegralService")
	private SysIntegralManager sysIntegralService;
	
	
	/**列表
	 * @param page 展示网吧新增消耗积分规则列表
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public ModelAndView list(Page page) throws Exception{

		ModelAndView mv = this.getModelAndView();
		JSONObject json = new JSONObject();
		User user = this.getUser();
		String intenet_id = user.getINTENET_ID();
		List<PageData>  allStores = intintegralService.getStores(intenet_id);
		List list = new ArrayList();
		for(int i=0;i<allStores.size();i++){
			Map map = new HashMap();
			String store_id = allStores.get(i).getString("store_id");
			String store_name = allStores.get(i).getString("store_name");
			map.put("store_id",store_id);
			map.put("store_name",store_name);
			String awardType = "";
			PageData signList = intintegralService.getSign(map);//查询门店是否签到
			PageData scanUpList = intintegralService.getScanUp(map);//查询门店是否扫码上机
			if(signList!=null&&scanUpList!=null){
				awardType = "签到、上机积分 ";
			}else if(signList==null&&scanUpList!=null){
				awardType ="上机积分";
			}else if(signList!=null&&scanUpList==null){
				awardType +="签到";
			}else{
				awardType="";
			}
			map.put("awardType",awardType);
			awardType="";//归空
			list.add(map);
		}
			json.put("data",list);
		mv.setViewName("system/intintegral/intintegral_list");
		mv.addObject("data",json);
		return mv;
	}

	/**
	 * 得到所有门店
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getStores")
	@ResponseBody
	public JSONObject getStore_ids() throws Exception{
		JSONObject json = new JSONObject();
		User user = this.getUser();
		String intenet_id = user.getINTENET_ID();
		List<PageData> list = intintegralService.getStores(intenet_id);//得到所有门店
		json.put("data",list);
		return json;
	}

	/**
	 * 获取不加v门店
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getVStores")
	@ResponseBody
	public JSONObject getVStore_ids() throws Exception{
		JSONObject json = new JSONObject();
		User user = this.getUser();
		String intenet_id = user.getINTENET_ID();
		List<PageData> list = intintegralService.getVStores(intenet_id);//得到所有门店
		json.put("data",list);
		return json;
	}

	/**
	 * 新增网吧积分功能
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSign",method = RequestMethod.POST)
	@ResponseBody
	public Message addSign(@RequestBody String body) throws Exception {
		Message msg = new Message();
		JSONObject json = JSONObject.fromObject(body);
		String CATEGRORY = json.getString("CATEGRORY");
		String data = json.getString("data");
		JSONArray strs = JSONArray.fromObject(data);
		String type = json.getString("type")==null?"":json.getString("type");
		List list = new ArrayList();
		if ("2".equals(CATEGRORY)) {
			for (int i = 0; i < strs.size(); i++) {
				String id = StringUtil.get32UUID();
				Map map = new HashMap();
				JSONObject str = strs.getJSONObject(i);
				String store_id = str.getString("store_id");
				String INTEGRAL_SEND = str.getString("INTEGRAL_SEND");
				String WEEKEND_SEND = str.getString("WEEKEND_SEND");
				map.put("CATEGRORY", CATEGRORY);
				map.put("id", id);
				map.put("store_id", store_id);
				map.put("INTEGRAL_SEND", INTEGRAL_SEND);
				map.put("WEEKEND_SEND", WEEKEND_SEND);
				list.add(map);
				PageData pd = intintegralService.checkSign(map);//查找是否有这个签到
				if("add".equals(type)){
					if (pd != null) {
						return Message.error("有门店设置已经过签到功能");
					}
				}
			}
			if("save".equals(type)){
				intintegralService.saveSigns(list);
				msg = Message.ok("保存成功");
			}else{
				intintegralService.addSign(list);
				msg = Message.ok("新增成功");
			}
		} else if ("5".equals(CATEGRORY)) {
			for (int i = 0; i < strs.size(); i++) {
				String id = StringUtil.get32UUID();
				Map map = new HashMap();
				JSONObject str = strs.getJSONObject(i);
				String store_id = str.getString("store_id");
				String sign_time_set = str.getString("sign_time_set");
				map.put("id", id);
				map.put("store_id", store_id);
				map.put("sign_time_set", sign_time_set);
				map.put("CATEGRORY", CATEGRORY);
				list.add(map);
				PageData pd = intintegralService.checkSign2(map);//查找是否有这个扫码上机功能
				if("add".equals(type)){
					if (pd != null) {
						return Message.error("有门店已经设置过扫码上机到功能");
					}
				}
			}
			if("save".equals(type)){
				intintegralService.saveSigns2(list);
				msg = Message.ok("保存成功");
			}else{
				intintegralService.addSign2(list);
				msg = Message.ok("新增成功");
			}
		}
		return msg;
	}

	/**
	 * 签到删除功能
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSign")
	@ResponseBody
	public Message deleteSign() throws Exception{
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String store_id = pd.getString("store_id");
		Map map = new HashMap();
		map.put("store_id",store_id);
		try{
			if(type.indexOf(",")!=-1){//含有
				String[] strs = type.split(",");
				String type1 = strs[0];
				String type2 = strs[1];
				map.put("type1",type1);
				map.put("type2",type2);
				intintegralService.deleteSign(map);
			}else{
				map.put("type",type);
				intintegralService.deleteSign2(map);
			}
		}catch (Exception e){
			return Message.error("系统发生异常");
		}
		return Message.ok("删除成功");
	}

	/**
	 * 签到编辑获取数据
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Message edit() throws Exception{
		PageData pd = this.getPageData();
		String store_id = pd.getString("store_id");
		List<PageData> list = intintegralService.getAllSign(pd);
		Map map = new HashMap();
		map.put("data",list);
		return Message.success(0,"查询结果",map);
	}



	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/intintegral/intintegral_edit");
//		User user = this.getUser();
//		String intenenet_id = user.getINTENET_ID();
//		Map map = new HashMap();
//		map.put("INTENET_ID",intenenet_id);
//		List<PageData> typeLists = intintegralService.getTypes(map);
//		mv.addObject("typeLists",typeLists);
		mv.addObject("msg", PublicManagerUtil.SAVE);
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
		pd = intintegralService.findById(pd);	//根据ID读取(通过积分表id查询数据)
		mv.setViewName("system/intintegral/intintegral_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		String sign_time_set = pd.getString("sign_time_set");
		if(sign_time_set!=null){
			String [] strs = sign_time_set.split(",");
			JSONObject days = new JSONObject();
			days.put("day1",strs[0]);
			days.put("day2",strs[1]);
			days.put("day3",strs[2]);
			days.put("day4",strs[3]);
			days.put("day5",strs[4]);
			days.put("day6",strs[5]);
			days.put("day7",strs[6]);
			mv.addObject("days",days);
		}
		return mv;
	}	
	
	
	/**
	 * 新增或修改保存 网吧新增消耗积分规则列表相关信息
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveIntegral")
	@ResponseBody
	public JSONObject saveIntegral() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增或修改保存IntIntegral");
		JSONObject json = new JSONObject();

		User user = this.getUser();//得到用户
		PageData pd = this.getPageData();
		pd.put("INTENET_ID", user.getINTENET_ID());
		//判断新增还是修改
		if(StringUtil.isNotEmpty(pd.getString("INTINTEGRAL_ID"))){
			//修改，校验权限
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有修改权限，请联系管理员！");
				return json;
			}
			intintegralService.edit(pd);  //通过INTINTEGRAL_ID保存修改后的信息
		}else{
			//新增
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有新增权限，请联系管理员！");
				return json;
			}
			pd.put("INTINTEGRAL_ID", this.get32UUID());	//主键
			pd.put("STATE", "1");
			pd.put("INTENET_ID",user.getINTENET_ID());
			PageData pdExist = intintegralService.findByCATEGRORY(pd); //通过类型和网吧id获取数据
			if(StringUtil.isNotEmpty(pdExist)){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "当前类型的积分已存在，请确认后再提交！");
				return json;
			}else{
				if(StringUtil.isEmpty(pd.getString("WEEKEND_SEND"))){
					pd.put("WEEKEND_SEND", 0);//默认周末积分为0
				}
				intintegralService.save(pd); //新增积分规则
			}
		}
		json.put("result", PublicManagerUtil.TRUE);
		json.put("message", "保存成功！");
		return json;
	}
	
	
	/**删除
	 * @return  删除网吧积分规则表
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public JSONObject delete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除IntIntegral");
		JSONObject json = new JSONObject();
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有删除权限，请联系管理员！");
			return json;
		} 
		PageData pd = this.getPageData();
		intintegralService.delete(pd); //通过INTINTEGRAL_ID删除积分规则
		
		json.put("result", PublicManagerUtil.TRUE);
		json.put("message", "删除成功！");
		return json;
	}
	
	
	 /**批量删除 PageData 包含网吧积分规则表
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public JSONObject deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除IntIntegral");
		JSONObject json = new JSONObject();
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有删除权限，请联系管理员！");
			return json;
		} 
		
		PageData pd = new PageData();		
		pd = this.getPageData();
		if(StringUtil.isNotEmpty(pd.getString("DATA_IDS"))){
			String DATA_IDS = pd.getString("DATA_IDS");
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			intintegralService.deleteAll(ArrayDATA_IDS);  //通过INTINTEGRAL_ID批量删除积分规则
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "删除成功！");
		}else{
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "请选择需要删除的选项！");
			return json;
		}
		return json;
	}
	
	
	/**一键新增积分规则 网吧一键建立积分规则
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/addAll")
	@ResponseBody
	public JSONObject addAll() throws Exception{
		JSONObject gjson =new JSONObject();
		logBefore(logger, Jurisdiction.getUsername()+"一键新增IntIntegral");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			gjson.put("message", "没有权限");
			return gjson;} //校验权限
		PageData pd = new PageData();
		User user = this.getUser();//得到用户

		pd.put("intenetId", user.getINTENET_ID());
		List<PageData>	varList = intintegralService.listAll(pd);	//列出IntIntegral列表(通过网吧id获取信息)
		if(varList.size()>0){
			gjson.put("message","已经设置过了，请不要重复一键设置");
			return gjson;
		}else{
			List<PageData>	intenetList = sysIntegralService.listAll(pd);	//列出IntIntegral列表(通过网吧id获取信息)
			for(PageData intenet: intenetList){
				intenet.put("INTINTEGRAL_ID", this.get32UUID());
				if(intenet.get("INTEGRAL_TYPE").equals("1")){
					intenet.put("STATE", 1);
				}else{
					intenet.put("STATE", 2);
				}
				intenet.put("INTENET_ID",  user.getINTENET_ID());
				intintegralService.save(intenet); //新增积分规则
			}
			gjson.put("message","设置成功");
			return gjson;
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}

	/**
	 * 新增扫码开机和编辑
	 * @return
	 */
	@RequestMapping(value = "/signset.do",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject signSet(@RequestBody String body)throws Exception{
		JSONObject json = new JSONObject();
		try{
			Map map = new HashMap();
			String  sign_time_set =JSONObject.fromObject(body).getString("sign_time_set");
			JSONObject json3 = JSONObject.fromObject(sign_time_set);
			int day1 = Integer.parseInt( json3.getString("day1"));
			int day2 = Integer.parseInt( json3.getString("day2"));
			int day3 = Integer.parseInt( json3.getString("day3"));
			int day4 = Integer.parseInt( json3.getString("day4"));
			int day5 = Integer.parseInt( json3.getString("day5"));
			int day6 = Integer.parseInt( json3.getString("day6"));
			int day7 = Integer.parseInt( json3.getString("day7"));
			List list = new ArrayList();
			list.add(day1);
			list.add(day2);
			list.add(day3);
			list.add(day4);
			list.add(day5);
			list.add(day6);
			list.add(day7);
			Collections.sort(list);
			int mix = (int) list.get(0);
			int max = (int) list.get(6);
			String integralnums = mix+"-"+max;
			map.put("integralnums",integralnums);//奖励积分
			String CATEGRORY = JSONObject.fromObject(body).getString("CATEGRORY");//积分类型
			String type = JSONObject.fromObject(body).getString("type");//积分类型
			String days = day1+","+day2+","+day3+","+day4+","+day5+","+day6+","+day7;//签到积分设置
			User user = this.getUser();
			String intenet_id = user.getINTENET_ID();//网吧id
			map.put("CATEGRORY",CATEGRORY);//设置类型
			map.put("INTENET_ID",intenet_id);//设置网吧id
			map.put("sign_time_set",days);//设置签到时间
			map.put("WEEKEND_SEND","0");//周末奖励
			List<PageData> pdExist = intintegralService.findnums(map); //通过类型和网吧id获取数据
			if("edit".equals(type)){
				intintegralService.updateSign(map);
				json.put("code",0);
				json.put("msg","修改成功");
				return json;
			}
			if(StringUtil.isNotEmpty(pdExist)){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "当前类型的积分已存在，请确认后再提交！");
				return json;
			}else{
				String id = StringUtil.get32UUID();
				map.put("id",id);
				intintegralService.saveSign(map);//新增
				json.put("code",0);
				json.put("msg","保存成功");
			}
		}catch (Exception e){
			json.put("code",1);
			json.put("msg","系统出现异常");
			return json;
		}
		return null;
	}


}
