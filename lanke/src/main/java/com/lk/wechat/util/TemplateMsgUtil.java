package com.lk.wechat.util;

import net.sf.json.JSONObject;

import com.lk.util.PageData;
import com.lk.util.StringUtil;

import java.util.List;

/**
 * 微信--模板消息
 * 
 * @author myq
 *
 */
public class TemplateMsgUtil {


	// 模板库编号--TEMPLATE_ID_SHORT

	public static final String user_up_short = "OPENTM405543981";// 网吧会员上机通知
	public static final String user_down_short = "OPENTM208008168";// 网吧会员下机通知
	public static final String member_mark_short = "OPENTM413117388";// 会员营销（系统事件通知）
	public static final String money_change_short = "OPENTM412195515";// 账户资金变动提醒
	public static final String server_guard_short = "OPENTM411122462";// 服务通知
	public static final String card_buy_success = "OPENTM416620745";// 购买成功通知
	public static final String server_plan_notify = "OPENTM408237350";// 服务进度提醒



    //网吧服务人员接受模板
	public static final String user_complain_short = "OPENTM208002162";// 投诉通知
	public static final String user_call_short = "OPENTM204588376";// 呼叫服务提醒
	public static final String user_order_short = "OPENTM415699551";// 新订单通知

	public static final String server_maintenance_short = "OPENTM411122462";// 服务器维护通知
	public static final String equipment_failure_short = "OPENTM417066125";// 设备故障通知
	public static final String failure_recovery_short = "OPENTM411245913";// 故障恢复通知



	/**
	 * 模板消息--设置所属行业，两个参数都是行业编号（具体可见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277）
	 * 
	 * @param industry_id1
	 *            39 文体娱乐-娱乐休闲
	 * @param industry_id2
	 *            2 IT科技-IT软件与服务
	 * @param token
	 * @return
	 */
	public static JSONObject setIndustry(String token, String industry_id1, String industry_id2) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + token;

		JSONObject gjson = new JSONObject();
		gjson.put("industry_id1", industry_id1);
		gjson.put("industry_id2", industry_id2);

		return HttpUtil.wechatRequest(urlStr, "POST", gjson, "模板消息--设置所属行业");
	}

	/**
	 * 模板消息--获取设置的行业信息
	 * 
	 * @param token
	 * @return
	 */
	public static JSONObject getIndustry(String token) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" + token;

		return HttpUtil.wechatRequest(urlStr, "POST", null, "模板消息--获取设置的行业信息");
	}

	/**
	 * 新增模板（template_id_short为微信模板库中的编号，有“TM**”和“OPENTMTM**”等形式）
	 * 
	 * @param token
	 * @param template_id_short
	 *            模板库编号
	 * @return 返回模板id----template_id
	 */
	public static JSONObject addTemplate(String token, String template_id_short) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=" + token;

		JSONObject gjson = new JSONObject();
		gjson.put("template_id_short", template_id_short);

		return HttpUtil.wechatRequest(urlStr, "POST", gjson, "模板消息--新增模板");
	}

	/**
	 * 模板消息--删除模板
	 * 
	 * @param token
	 * @param template_id
	 * @return
	 */
	public static JSONObject delTemplate(String token, String template_id) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=" + token;

		JSONObject gjson = new JSONObject();
		gjson.put("template_id", template_id);

		return HttpUtil.wechatRequest(urlStr, "POST", gjson, "模板消息--删除模板");
	}

	/**
	 * 发送模板消息
	 * 
	 * @param token
	 *            口令凭证
	 * @param openId
	 *            微信用户标识
	 * @param template_id
	 *            模板id
	 * @param url
	 *            点击模板后跳转页面，可为空
	 * @param miniprogram
	 *            点击模板后跳转小程序，可为空，如果两者同时有，优先小程序
	 * @param content
	 *            模板内容
	 * @return
	 */
	public static JSONObject sendTemplate(String token, String openId, String template_id, String url,
			JSONObject miniprogram, JSONObject content) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;

		JSONObject jsonPram = new JSONObject();
		jsonPram.put("touser", openId);
		jsonPram.put("template_id", template_id);
		if (StringUtil.isNotEmpty(url)) {
			jsonPram.put("url", url);
		}
		if (StringUtil.isNotEmpty(miniprogram)) {
			jsonPram.put("miniprogram", miniprogram);
		}
		jsonPram.put("data", content);

		return HttpUtil.wechatRequest(urlStr, "POST", jsonPram, "模板消息--发送模板消息");
	}

	/****************************************
	 * 以下为模板消息
	 *********************************************/


	/**
	 * 模板消息--会员营销消息提醒（模板编号OPENTM413117388）
	 * 
	 * @param pd
	 * @return
	 */
	public static JSONObject memberMark(PageData pd) {
		JSONObject jsonData = new JSONObject();
		// 活动标题
		JSONObject jsonFirst = new JSONObject();
		jsonFirst.put("value", pd.get("trade_title"));
		// 活动类型
		JSONObject jsonKeyword1 = new JSONObject();
		jsonKeyword1.put("value", pd.get("trade_type"));
		// 活动内容
		JSONObject jsonKeyword2 = new JSONObject();
		jsonKeyword2.put("value", pd.get("trade_context"));
		// 活动时间
		JSONObject jsonKeyword3 = new JSONObject();
		jsonKeyword3.put("value", pd.get("trade_time"));
		// 备注(选填)
		JSONObject jsonRemark = new JSONObject();
		jsonRemark.put("value", pd.get("trade_remark"));

		jsonData.put("first", jsonFirst);
		jsonData.put("keyword1", jsonKeyword1);
		jsonData.put("keyword2", jsonKeyword2);
		jsonData.put("keyword3", jsonKeyword3);
		jsonData.put("remark", jsonRemark);
		return jsonData;

	}


	/**
	 *
	 * 服务器维护通知--会员营销游戏资讯通知（模板编号OPENTM411122462）
	 * @param pd
	 * @return
	 */
	public static JSONObject serverGuard(PageData pd) {
		JSONObject jsonData = new JSONObject();
		// 活动标题
		JSONObject jsonFirst = new JSONObject();
		jsonFirst.put("value", pd.get("first_data"));
		// 维护时间
		JSONObject jsonKeyword1 = new JSONObject();
		jsonKeyword1.put("value", pd.get("keyword1"));
		// 恢复时间
		JSONObject jsonKeyword2 = new JSONObject();
		jsonKeyword2.put("value", pd.get("keyword2"));
		// 维护内容
		JSONObject jsonKeyword3 = new JSONObject();
		jsonKeyword3.put("value", pd.get("keyword3"));
		// 备注(选填)
		JSONObject jsonRemark = new JSONObject();
		jsonRemark.put("value", pd.get("remark_data"));

		jsonData.put("first", jsonFirst);
		jsonData.put("keyword1", jsonKeyword1);
		jsonData.put("keyword2", jsonKeyword2);
		jsonData.put("keyword3", jsonKeyword3);
		jsonData.put("remark", jsonRemark);
		return jsonData;

	}


	/**
	 * 通用--封装模板消息内容
	 * @param pd （first_data:标题内容；remark_data:备注内容）
	 * @param keywordList （主体内容，根据具体微信模板填写几个keyword）
	 * @return
	 */
	public static JSONObject commonTemplate(PageData pd, List<String> keywordList){

		JSONObject jsonData = new JSONObject();
		// 模板标题
		JSONObject jsonFirst = new JSONObject();
		jsonFirst.put("value", pd.get("first_data"));

		int num = 1;
		for (String str : keywordList){
			JSONObject keyword = new JSONObject();
			keyword.put("value", str);
			jsonData.put("keyword" + num, keyword);
			num++;
		}

		// 模板备注(选填)
		JSONObject jsonRemark = new JSONObject();
		jsonRemark.put("value", pd.get("remark_data"));

		jsonData.put("first", jsonFirst);
		jsonData.put("remark", jsonRemark);
		return jsonData;

	}

	/**
	 * 根据type类型，获取模板短号
	 * @param type
	 * @return
	 */
	public static String getTypeShort(String type){
		String shot = "";
		switch (type){
			case "user_up" :
				shot = TemplateMsgUtil.user_up_short;
				break;
			case "user_down" :
				shot = TemplateMsgUtil.user_down_short;
				break;
			case "member_mark" :
				shot = TemplateMsgUtil.member_mark_short;
				break;
			case "money_change" :
				shot = TemplateMsgUtil.money_change_short;
				break;
			case "server_guard" :
				shot = TemplateMsgUtil.server_guard_short;
				break;
            case "card_buy_success" :
				shot = TemplateMsgUtil.card_buy_success;
				break;
            case "server_plan_notify" :
				shot = TemplateMsgUtil.server_plan_notify;
				break;


			case "user_complain" :
				shot = TemplateMsgUtil.user_complain_short;
				break;
			case "user_call" :
				shot = TemplateMsgUtil.user_call_short;
				break;
			case "user_order" :
				shot = TemplateMsgUtil.user_order_short;
				break;

            case "server_maintenance" :
                shot = TemplateMsgUtil.server_maintenance_short;
                break;
            case "equipment_failure" :
                shot = TemplateMsgUtil.equipment_failure_short;
                break;
            case "failure_recovery" :
                shot = TemplateMsgUtil.failure_recovery_short;
                break;
		}
		return shot;
	}


}
