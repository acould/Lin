package com.lk.service.system.memberMarke.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.entity.weixin.JSAPI;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.memberMarke.MemberMarkeManager;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.service.tb.tbMarketingContext.MarkingContextService;
import com.lk.service.wechat.WeixinService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.textmsg.TextmsgService;
import com.lk.util.*;
import com.lk.wechat.util.TemplateMsgUtil;
import com.lk.wechat.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明：会员营销--业务层
 */
@Service("memberMarkeService")
public class MemberMarkeService implements MemberMarkeManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "textmsgService")
	private TextmsgService textmsgService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;
	@Resource(name = "userFlowService")
	private UserFlowService userFlowService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	@Resource(name = "terraceService")
	private TerraceManager terraceService;

	@Resource(name = "weixinService")
	private WeixinService weixinService;

	@Autowired
	private MarkingContextService markingContextService;


    @Resource(name = "indexMemberService")
    private IndexMemberManager indexMemberService;



	public static final String TYPE1 = "material"; // 图文消息
	public static final String TYPE2 = "image"; // 图片
	public static final String TYPE3 = "text"; // 文字
	public static final String TYPE4 = "card"; // 卡劵
	public static final String TYPE5 = "link"; // 链接
	public static final String TYPE6 = "notice"; // 消息通知

	// URL(开发者密码)
	public static final String URL1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
	public static final String URL2 = "&response_type=code&scope=snsapi_base&state=1&component_appid="
			+ PublicManagerUtil.APPID + "#wechat_redirect";

	/**
	 * 去查询会员营销列表
	 * 
	 * @param page
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page, PageData pd) throws Exception {
		String keywords = pd.getString("keywords"); // 关键词检索条件
		List<PageData> list = null;
		PageData pd1 = page.getPd();
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		if (StringUtil.isEmpty(page.getPd().get("storeIds").toString())) {// 网吧老板
			list = (List<PageData>) dao.findForList("MemberMarkeMapper.datalistPages", page);
		} else {// 店长
			String storeIds = page.getPd().get("storeIds").toString();
			String storeId[] = storeIds.split(",");
			Set<String> set = new HashSet<>();
			for (int i = 0; i < storeId.length; i++) {
				String store_id = storeId[i];
				// 通过门店id获取相关联的营销id
				List<String> selectIds = (List<String>) dao.findForList("MemberMarkeMapper.selectId", store_id);
				if (StringUtil.isNotEmpty(selectIds)) {// 不为空则添加进set集合(防止重复)
					set.addAll(selectIds);
				}
			}
			List<String> list1 = new ArrayList<String>(set);// set转list
			if (StringUtil.isNotEmpty(list1)) {
				pd.put("list1", Joiner.on("','").join(list1)); // 门店相关营销id集合
			}
			page.setPd(pd);
			list = (List<PageData>) dao.findForList("MemberMarkeMapper.datalistPages1", page);
		}
		if (StringUtil.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {// 对消息进行转对象
				String mass_context = list.get(i).getString("mass_context");
				PageData pd2 = JSON.parseObject(mass_context, PageData.class);
				list.get(i).put("sex", pd2.getString("sex"));// 性别
				list.get(i).put("quiz2", pd2.getString("quiz2"));
				list.get(i).put("member", pd2.getString("member"));
			}
		}
		return list;
	}

	/**
	 * 查询网吧(或个人)门店
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> storeList(Page page) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("MemberMarkeMapper.storeList", page);
		return list;
	}

	/**
	 * 去查看该用户所有粉丝
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> fanList(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("MemberMarkeMapper.fanList", pd);
		return list;
	}

	// 保存文本内容
	public PageData saveText(PageData pd) {
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

		PageData pdText = new PageData();
		pdText.put("TEXTMSG_ID", StringUtil.get32UUID()); // 文本id
		pdText.put("INTERNET_ID", user.getINTENET_ID()); // 网吧id
		pdText.put("CONTENT", pd.getString("object_id")); // 文本内容
		pdText.put("CREATE_TIME", Tools.date2Str(new Date())); // 新增时间
		try {
			textmsgService.save(pdText); // 保存文本内容
			pd.put("object_id", pdText.getString("TEXTMSG_ID"));// 对象id
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	// 保存链接内容
	public PageData saveLink(PageData pd) {
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

		PageData pdText = new PageData();
		pdText.put("id", StringUtil.get32UUID()); // 链接id
		pdText.put("interent_id", user.getINTENET_ID()); // 网吧id
		pdText.put("content", pd.getString("object_id")); // 链接内容
		pdText.put("creattime", Tools.date2Str(new Date())); // 新增时间
		pdText.put("mass_id", pd.getString("mass_id")); // 群发消息id
		try {
			dao.save("MemberMarkeMapper.saveLink", pdText); // 保存模板链接信息
			pd.put("object_id", pdText.getString("id"));// 对象id
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	/**
	 * 保存群发消息内容
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void toMarket(PageData pd) throws Exception {
		PageData pd1 = new PageData();
		pd1.put("id", pd.getString("mass_id")); // 群发消息id
		if (pd.getString("type").equals(TYPE1) && StringUtil.isEmpty(pd.getString("object_id"))) {// 选择图文,没内容
			pd.put("type", TYPE6);// 将类型替换为消息通知类型
		}
		try {
			// 先保存数据到群发消息内容表
			String context = JSON.toJSONString(pd);// 消息内容
			pd1.put("mass_context", context); // 内容
			if(StringUtil.isNotEmpty(pd.getString("send_type"))){
				pd1.put("send_type", pd.getString("send_type"));//是否有下机推送
				pd1.put("start_time", pd.getString("start_time"));//开始时间
				pd1.put("end_time", pd.getString("end_time"));//结束时间
			}
			markingContextService.save(pd1);// 保存群发消息内容

			this.saveMark(pd);// 保存会员营销信息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 筛选满足条件的用户
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject sendMessage(PageData pd) throws Exception {
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

		List<PageData> list = new ArrayList<PageData>();// 新建用户open_id集合
		JSONObject json = new JSONObject();
		Integer stock = 0;
		pd.put("internet_id", user.getINTENET_ID()); // 网吧id
		pd.put("mass_id", StringUtil.get32UUID()); // 群发消息id

		PageData pd1 = pd;
		pd1.put("internet_id", user.getINTENET_ID());// 网吧id
		// 新增操作,先保存文本内容或链接内容
		if (pd.getString("type").equals("text")) {// 获取最新的object_id
			pd = this.saveText(pd);
		} else if (pd.getString("type").equals("link")) {// 获取最新的object_id
			pd = this.saveLink(pd);
		} else if (pd.getString("type").equals("card")) {// 获取卡券库存
			stock = stock + this.stock(pd);// 目前卡券库存
			// 由于卡券领取后才扣除库存(已经发送的,还没领取的)
			System.out.println("卡券库存为:" + stock);
		}

		// 先判断性别
		if (pd.getString("sex").equals("1")) {
			pd1.put("sex", "1");// 1--男
		} else if (pd.getString("sex").equals("2")) {
			pd1.put("sex", "2");// 2--女
		} else if (pd.getString("sex").equals("0")) {// 全部
			pd.put("sex", "");
			pd1.put("sex", "");
		}

		// 为粉丝时
		if (pd.getString("mass_object").equals("fans")) {
			list = this.fanList(pd1);// 查询满足条件的粉丝(网吧id+性别)
			if (list.size() == 0) {// 没有满足要求的粉丝
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "没有满足要求的粉丝,请重新选择条件");
				return json;
			}
			System.out.println("满足性别要求的粉丝的数量" + list.size());
//			if (pd.getString("type").equals("card")) {// 卡券库存小于满足条件的粉丝数数量
//				if (stock < list.size()) {
//					json.put("result", PublicManagerUtil.FALSE);
//					json.put("message", "卡券库存不足,请增加库存");
//					return json;
//				}
//			}

			// 遍历发送事件通知
			memberMarkeThread marke = new memberMarkeThread(pd, list);
			new Thread(marke).start();
			
		} else {// 为会员时,条件为:门店+会员标签+性别,先通过门店id和性别去查询满足条件的会员
			String store_id = pd.getString("store_id");
			String storeId[] = store_id.split(",");
			pd.put("storeId", storeId);
			List<PageData> memberList = (List<PageData>) dao.findForList("MemberMarkeMapper.memberList", pd);// 查询满足性别和门店要求的会员标签和会员卡号

			if (memberList.size() == 0) {// 没有满足要求的会员
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "没有满足要求的会员,请重新选择条件");
				return json;
			} else {
				System.out.println("会员类型:" + pd.getString("member"));
				if (pd.getString("member").equals("全部会员")) {// 全部会员
					System.out.println("满足性别要求的会员数量" + memberList.size());
//					if (pd.getString("type").equals("card")) {// 卡券库存小于满足条件的粉丝数数量
//						if (stock < memberList.size()) {// 卡券库存小于满足条件的会员数量
//							json.put("result", PublicManagerUtil.FALSE);
//							json.put("message", "卡券库存不足,请增加库存");
//							return json;
//						}
//					}

					// 遍历发送事件通知
					memberMarkeThread marke = new memberMarkeThread(pd, memberList);
					new Thread(marke).start();
				} else {// 创建符合要求的会员集合
					List<String> userList = new ArrayList<>();
					for (int i = 0; i < memberList.size(); i++) {// 遍历每一个会员对象,去计算三项能力,将符合要求的会员open_id存进集合中
						PageData pageData = memberList.get(i);// 获取对象的卡号和标签
						PageData cardIdResult = userFlowService.findByCardId(pageData);// 通过卡号获取活跃度/消费能力
						// 按活跃度
						if (pd.getString("member").equals("按活跃度")) {
							double count = Double.parseDouble(cardIdResult.get("count") + "");// 活跃度
							System.out.println("活跃度为" + count);
							if (pd.getString("quiz2").equals("高")) {
								if (pageData.containsKey("live_high")) {
									if (count > 0 && count <= (double) pageData.get("live_high")) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								} else {
									if (count > 0 && count <= 7) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								}
							} else if (pd.getString("quiz2").equals("中")) {
								if (pageData.containsKey("live_high")) {
									if (count > (double) pageData.get("live_high")
											&& count <= (double) pageData.get("live_low")) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								} else {
									if (count > 7 && count <= 30) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								}
							} else if (pd.getString("quiz2").equals("低")) {
								if (pageData.containsKey("live_high")) {
									if (count > (double) pageData.get("live_low")) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								} else {
									if (count > 30) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								}
							}
						} else if (pd.getString("member").equals("按消费能力")) {// 按消费能力
							double sum = (double) cardIdResult.get("sum");// 消费能力
							System.out.println("半年消费总金额" + sum);
							if (pd.getString("quiz2").equals("高")) {
								if (pageData.containsKey("consume_high")) {
									if (sum > (double) pageData.get("consume_high")) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								} else {
									if (sum >= 50) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								}
							} else if (pd.getString("quiz2").equals("中")) {
								if (pageData.containsKey("consume_high")) {
									if (sum <= (double) pageData.get("consume_high")
											&& sum >= (double) pageData.get("consume_low")) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								} else {
									if (sum < 50 && sum > 20) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								}
							} else if (pd.getString("quiz2").equals("低")) {
								if (pageData.containsKey("consume_high")) {
									if (sum < (double) pageData.get("consume_low")) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								} else {
									if (sum <= 20) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								}
							}
						} // 按卡余额
						else if (pd.getString("member").equals("按卡余额")) {
							double balance = Double.parseDouble(pageData.getString("OVERAGE"));// 获取会员卡余额
							System.out.println("会员卡余额" + balance);
							if (pd.getString("quiz2").equals("高")) {
								if (pageData.containsKey("balance_high")) {
									if (balance > (double) pageData.get("balance_high")) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								} else {
									if (balance >= 100) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								}
							} else if (pd.getString("quiz2").equals("中")) {
								if (pageData.containsKey("balance_high")) {
									if (balance <= (double) pageData.get("balance_high")
											&& balance >= (double) pageData.get("balance_low")) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								} else {
									if (balance < 100 && balance >= 20) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								}
							} else if (pd.getString("quiz2").equals("低")) {
								if (pageData.containsKey("balance_high")) {
									if (balance < (double) pageData.get("balance_low")) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								} else {
									if (balance < 20) {
										userList.add(pageData.getString("OPEN_ID"));
									}
								}
							}
						}
					}
					if (userList.size() == 0) {
						json.put("result", PublicManagerUtil.FALSE);
						json.put("message", "没有满足要求的会员,请重新选择条件");
						return json;
					} else {
						System.out.println("满足要求的会员数量为:" + userList.size());
//						if (pd.getString("type").equals("card")) {// 卡券库存小于满足条件的粉丝数数量
//							if (stock < userList.size()) {// 卡券库存小于满足条件的会员数量
//								json.put("result", PublicManagerUtil.FALSE);
//								json.put("message", "卡券库存不足,请增加库存");
//								return json;
//							}
//						}

						// 遍历发送事件通知
						memberMarkeThread1 marke = new memberMarkeThread1(pd1, userList);
						new Thread(marke).start();
					}
				}
			}
		}
		json.put("result", PublicManagerUtil.SUCCESS);
		json.put("message", "发送中,请稍后查看列表");
		return json;
	}

	/**
	 * 群发消息,同时保存营销内容
	 * 
	 * @param pd
	 * @param list
	 * @throws Exception
	 */
	public void memberMarkeList(PageData pd, List<PageData> list) throws Exception {
		JSONObject json = new JSONObject();
		Integer sum1 = 0;
		for (int i = 0; i < list.size(); i++) {
			json = this.sendMark(pd, list.get(i).getString("OPEN_ID"));
			if (StringUtil.isNotEmpty(json.getString("event"))) {// 先判断返回码不为空时
				if (json.getString("event").equals("true")) {// 如果有一条模板消息发送成功
					sum1 = sum1 + 1;
				} else {
					sum1 = sum1 + 0;
				}
			}
		}
		this.toMarket(pd);// 去保存会员营销相关内容
		System.out.println("发送成功条数sum1" + sum1);
	}

	/**
	 * 群发消息,同时保存营销内容
	 * 
	 * @param pd
	 * @param list
	 * @throws Exception
	 */
	public void memberMarkeList1(PageData pd, List<String> list) throws Exception {
		JSONObject json = new JSONObject();
		Integer sum1 = 0;
		for (int j = 0; j < list.size(); j++) {
			json = this.sendMark(pd, list.get(j));
			if (StringUtil.isNotEmpty(json.getString("event"))) {// 先判断返回码不为空时
				if (json.getString("event").equals("true")) {// 如果有一条模板消息发送成功
					sum1 = sum1 + 1;
				} else {
					sum1 = sum1 + 0;
				}
			}
		}
		this.toMarket(pd);// 去保存会员营销相关内容
		System.out.println("发送成功条数sum1" + sum1);
	}

	/**
	 * 通过对象id获取卡券库存
	 * 
	 * @param pd
	 * @throws Exception
	 */
	private Integer stock(PageData pd) throws Exception {
		PageData pd1 = (PageData) dao.findForObject("MemberMarkeMapper.stock", pd);
		return (Integer) pd1.get("QUANTITY");
	}

	/**
	 * 给指定用户发送模板消息
	 * 
	 * @param pd
	 * @param open_id
	 * @throws Exception
	 */
	public JSONObject sendMark(PageData pd, String open_id) throws Exception {
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

		// 判断该网吧是否有该模板id
		JSONObject json = new JSONObject();
		JSONObject jsonData = new JSONObject();
		JSONObject jsonState = new JSONObject();
		pd.put("id", StringUtil.get32UUID());// 记录表id
		// 获取token
		String token = autoReplyService.getAuthToken(user.getINTENET_ID());


		//发送模板消息前，处理模板设置(会员营销通知提醒)
		JSONObject tempJson = weixinService.preHandleTemplate(user.getINTENET_ID(), "member_marke", TemplateMsgUtil.member_mark_short);



		// 组装内容
		PageData pdTrade = new PageData();
		pdTrade.put("trade_title", "活动标题:" + pd.getString("title"));// 活动标题
		pdTrade.put("trade_type", pd.getString("activtyType"));// 活动类型
		pdTrade.put("trade_context", pd.getString("activtyContent"));// 活动内容
		pdTrade.put("trade_time", pd.getString("createtime"));// 活动时间
		pdTrade.put("trade_remark", "备注:" + pd.getString("remark"));// 备注(选填) JSONObject
		jsonData = TemplateMsgUtil.memberMark(pdTrade);// 模板消息--会员营销通知提醒（模板编号OPENTM413117388）
		// 发送模板消息
		System.out.println("发送模板消息");
		// 此处通过类型type和object_id去判断,是否选择类型,并且内容不为空
		if (StringUtil.isEmpty(pd.getString("object_id"))) {// object_id为空时
			jsonState = TemplateMsgUtil.sendTemplate(token, open_id, tempJson.getString("template_id"), null, null,
					jsonData);// 发送模板消息
		} // 图文
		else if (pd.getString("type").equals(TYPE1)) {
			String url = PublicManagerUtil.URL2 + "memberMarke/toDetails?open_id=" + open_id + "&type="
					+ pd.getString("type") + "&object_id=" + pd.getString("object_id") + "&id=" + pd.getString("id");
			jsonState = TemplateMsgUtil.sendTemplate(token, open_id, tempJson.getString("template_id"), url, null,
					jsonData);// 发送图文消息
		} // 文字或图片
		else if (pd.getString("type").equals(TYPE2) || pd.getString("type").equals(TYPE3)) {
			String url = PublicManagerUtil.URL2 + "memberMarke/toDetails?open_id=" + open_id + "&type="
					+ pd.getString("type") + "&object_id=" + pd.getString("object_id") + "&mass_id="
					+ pd.getString("mass_id") + "&id=" + pd.getString("id");
			jsonState = TemplateMsgUtil.sendTemplate(token, open_id, tempJson.getString("template_id"), url, null,
					jsonData);// 发送文字或图片
		} // 卡券
		else if (pd.getString("type").equals(TYPE4)) {
			String url = PublicManagerUtil.URL2 + "memberMarke/receiveCard?open_id=" + open_id + "&object_id="
					+ pd.getString("object_id") + "&id=" + pd.getString("id");
			jsonState = TemplateMsgUtil.sendTemplate(token, open_id, tempJson.getString("template_id"), url, null,
					jsonData);// 发送卡券
		} // 链接
		else if (pd.getString("type").equals(TYPE5)) {
			jsonState = TemplateMsgUtil.sendTemplate(token, open_id, tempJson.getString("template_id"),
					pd.getString("linkContent"), null, jsonData);// 发送链接
		}
		if (StringUtil.isNotEmpty(jsonState.getString("errcode"))) {
			if (jsonState.getString("errcode").equals("0")) {// 发送成功
				pd.put("sendtime", Tools.date2Str(new Date()));// 发送时间
				pd.put("state", "0");// 发送状态(0--成功)
				pd.put("open_id", open_id);// 用户微信id
				if (StringUtil.isEmpty(pd.getString("object_id")) || pd.getString("type").equals(TYPE5)) {// object_id为空时或者类型为链接时,
					pd.put("view_state", "1");// 查看详情状态(1--已查看)
				} else {
					pd.put("view_state", "0");// 查看详情状态(0--未查看)
				}
				dao.save("MemberMarkeMapper.saveRecord", pd); // 保存群发记录
				json.put("event", PublicManagerUtil.TRUE);// event--标识符,只要成功就返回
			} else {
				json.put("event", PublicManagerUtil.FALSE);// event--标识符,只要失败就返回
			}
		}
		return json;
	}

	/**
	 * 保存会员营销信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveMark(PageData pd) throws Exception {
        User user = Jurisdiction.getSessionUser().getUser();//得到用户
		// 再保存数据到会员营销表
		pd.put("internet_id", user.getINTENET_ID());// 网吧id
		pd.put("id", StringUtil.get32UUID()); // id
		pd.put("marketime", Tools.date2Str(new Date())); // 群发时间
		dao.save("MemberMarkeMapper.saveMarket", pd); // 保存会员营销模板
	}

	/**
	 * 去查询指定营销信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData view(PageData pd) throws Exception {
		PageData pd1 = (PageData) dao.findForObject("MemberMarkeMapper.view", pd);
		String mass_context = pd1.getString("mass_context");
		PageData pd2 = JSON.parseObject(mass_context, PageData.class);
		pd2.put("markingcontext_id", pd1.getString("id"));
		if(StringUtil.isNotEmpty(pd1.get("send_type")) ){
			pd2.put("send_type", pd1.get("send_type"));
			pd2.put("start_time", pd1.get("start_time").toString().substring(0,10));
			pd2.put("end_time", pd1.get("end_time").toString().substring(0,10));
		}
		return pd2;
	}

	/**
	 * 删除指定营销信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public JSONObject delete(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.delete("MemberMarkeMapper.delete", pd);// 删除指定营销信息
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", PublicManagerUtil.FAIL);
			json.put("message", "系统繁忙,请稍后再试");
			return json;
		}
		json.put("result", PublicManagerUtil.SUCCESS);
		json.put("message", "操作成功");
		return json;
	}

	/**
	 * 批量删除指定营销信息
	 * 
	 * @param arrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public JSONObject deleteAll(String[] arrayDATA_IDS) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.delete("MemberMarkeMapper.deleteAll", arrayDATA_IDS);// 批量删除指定营销信息
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", PublicManagerUtil.FAIL);
			json.put("message", "系统繁忙,请稍后再试");
			return json;
		}
		json.put("result", PublicManagerUtil.SUCCESS);
		json.put("message", "操作成功");
		return json;
	}

	/**
	 * 通过门店id获取门店名称
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByStoreId(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("MemberMarkeMapper.findByStoreId", pd);
		return list;
	}

	/**
	 * 通过storeid去查询门店标签
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public JSONObject selectTips(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		PageData pd1 = (PageData) dao.findForObject("MemberMarkeMapper.selectTips", pd);
		json.put("result", PublicManagerUtil.SUCCESS);
		json.put("pd1", pd1);
		return json;
	}

	/**
	 * 通过id去查询链接信息
	 * 
	 * @param pdLink
	 * @throws Exception
	 */
	@Override
	public PageData findLink(PageData pdLink) throws Exception {
		PageData pd = (PageData) dao.findForObject("MemberMarkeMapper.findLink", pdLink);
		return pd;
	}

	/**
	 * 通过mass_id去查询信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findContent(PageData pd) throws Exception {
		PageData pd1 = (PageData) dao.findForObject("MemberMarkeMapper.findContent", pd);
		return pd1;
	}

	/**
	 * 获取符合条件的卡券
	 * 条件:1.FLQ--福利券  2,卡券未失效  3.门店没有禁用  4.门店加v(选择对象为会员时)
	 * @param page
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selectCard(Page page) throws Exception {
		return(List<PageData>) dao.findForList("MemberMarkeMapper.selectCard", page);
	}

	/**
	 * 通过卡券id获取卡券信息
	 * 
	 * @param pd
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> cardView(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MemberMarkeMapper.cardView", pd);
	}

	/**
	 * 通过门店id数组获取门店名称
	 * 
	 * @param store_ids
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> storeName(String[] store_ids) throws Exception {
		return (List<PageData>) dao.findForList("MemberMarkeMapper.storeName", store_ids);
	}

	/**
	 * 通过门店id获取门店名称
	 * 
	 * @param pd2
	 */
	@Override
	public PageData selectName(PageData pd2) throws Exception {
		PageData pd = (PageData) dao.findForObject("MemberMarkeMapper.selectName", pd2);
		return pd;
	}

	/**
	 * 通过记录表id去查询查看详情状态(view_state)
	 * 
	 * @param pd
	 */
	@Override
	public PageData findRecord(PageData pd) throws Exception {
		PageData pd1 = (PageData) dao.findForObject("MemberMarkeMapper.findRecord", pd);
		return pd1;
	}

	/**
	 * 通过记录表id去修改查看详情状态(view_state)
	 * 
	 * @param pd
	 */
	@Override
	public void editRecord(PageData pd) throws Exception {
		dao.update("MemberMarkeMapper.editRecord", pd);
	}

	/**
	 * 获取js-sdk 卡券接口的相关信息
	 * 
	 * @param pdd
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCard_Ext(PageData pdd) throws Exception {
		JSONObject wx = new JSONObject();
		String internet_id = pdd.getString("internet_id");
		Intenet org = intenetService.getIntenetById(internet_id);// 通过网吧id获取网吧信息
		/****** 开放平台为公众号提供的token *****/
		String token = autoReplyService.getAuthToken(internet_id);// 获取微信凭证token
		pdd.put("APP_ID", org.getWECHAT_ID());
		pdd.put("TOKEN", token);
		String api_ticket = terraceService.getApiTicket(pdd);// 实时获取缓存中的api_ticket
		System.out.println("api_ticket ========== " + api_ticket);

		// signature中的timestamp，nonce字段和card_ext中的timestamp，nonce_str字段必须保持一致。
		JSONObject card_ext = WeixinUtil.cardExtSign(api_ticket, pdd.getString("object_id"), "",pdd.getString("open_id"));// 获取card_ext
		wx.put("config", card_ext.toString());// 放入card_ext
		wx.put("card_id", pdd.getString("object_id"));// 放入card_id
		System.out.println("wx ==== " + wx);

        //获取jsapi
        JSAPI jsapi = indexMemberService.getWXJSdata(pdd.getString("url"), internet_id);

		wx.put("nonceStr", jsapi.getNonceStr());
		wx.put("timestamp", jsapi.getTimestamp());
		wx.put("signature", jsapi.getSignature());
		wx.put("appid", jsapi.getAppid());
		return wx;
	}
	
	/**
	 * 获取js-sdk 卡券接口的相关信息
	 * 
	 * @param pdd
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCard_Ext1(PageData pdd) throws Exception {
		JSONObject wx = new JSONObject();
		String internet_id = pdd.getString("internet_id");
		Intenet org = intenetService.getIntenetById(internet_id);// 通过网吧id获取网吧信息
		/****** 开放平台为公众号提供的token *****/
		String token = autoReplyService.getAuthToken(internet_id);// 获取微信凭证token
		//String token ="17_5EcG-16DE3aRSlKfMziFVxOxzxN-j5sj10E_TcmFGYdsWq_8mWWH1urvjUlqD0OAOAmH8ylz-jb7BUnDENZkTqTHv8cms_taDyQLKbrQNXDothm2WroVdEMWSmxk_D556Yn8XTFfIwTAFarGDNRdAJDZJN";
		pdd.put("APP_ID", org.getWECHAT_ID());
		pdd.put("TOKEN", token);
		String api_ticket = terraceService.getApiTicket(pdd);// 实时获取缓存中的api_ticket
		System.out.println("api_ticket ========== " + api_ticket);

		// signature中的timestamp，nonce字段和card_ext中的timestamp，nonce_str字段必须保持一致。
		JSONObject card_ext = WeixinUtil.cardExtSign1(api_ticket,pdd.getString("APP_ID"),pdd.getString("object_id"));// 获取card_ext
		wx.put("config", card_ext.toString());// 放入card_ext
		wx.put("card_id", pdd.getString("object_id"));// 放入card_id
		System.out.println("wx ==== " + wx);

		//获取jsapi
        JSAPI jsapi = indexMemberService.getWXJSdata(pdd.getString("url"), internet_id);

		wx.put("nonceStr", jsapi.getNonceStr());
		wx.put("timestamp", jsapi.getTimestamp());
		wx.put("signature", jsapi.getSignature());
		wx.put("appid", jsapi.getAppid());
		return wx;
	}

	/**
	 * 这里通过open_id和卡券id去判断用户是否已领取该卡券(未核销)
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findCard(PageData pd) throws Exception {
		PageData pd1 = (PageData) dao.findForObject("MemberMarkeMapper.findCard", pd);
		return pd1;
	}

	/**
	 * 这里通过user_id和卡券id去判断用户是否可以领取该卡券
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject judgeUser(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		// 先通过卡券id去获取卡券领取规则
		PageData pd1 = (PageData) dao.findForObject("MemberMarkeMapper.findRule", pd);
		Integer receive_number = Integer.parseInt(pd1.getString("RECEIVE_NUMBER"));// 一定時間內卡券领取限制数量(列如:一周最多2張)
		System.out.println("一定時間內卡券领取限制数量为:" + receive_number);
		if (!pd1.get("RECEIVE_STATE").equals("YES")) {// 该卡券没有领取限制
			json.put("result", PublicManagerUtil.TRUE);// true--可以领取
			return json;
		} else {
			Date dateTimeUpdate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateNowStr = sdf.format(dateTimeUpdate);
			Date startTime = null;
			int a = Integer.parseInt(pd1.getString("BLANK_NUMBER"));// 时间间隔数量
			if (pd1.getString("RECEIVE_DETIL").endsWith("WEEK")) {// 周
				startTime = Tools.sAddDays(dateTimeUpdate, -a * 7);
			} else if (pd1.getString("RECEIVE_DETIL").endsWith("YEAR")) {// 年
				startTime = Tools.sAddYear(dateTimeUpdate, -a);
			} else if (pd1.getString("RECEIVE_DETIL").endsWith("DAY")) {// 天
				startTime = Tools.sAddDays(dateTimeUpdate, -a);
			} else if (pd1.getString("RECEIVE_DETIL").endsWith("MON")) {// 月
				startTime = Tools.sAddMonth(dateTimeUpdate, -a);
			}
			pd.put("END_TIME", sdf.parse(dateNowStr).getTime() / 1000);// 结束时间
			String startTimeStr = sdf.format(startTime);
			pd.put("START_TIME", sdf.parse(startTimeStr).getTime() / 1000);// 开始时间
			// 获取用户信息
			List<PageData> list = (List<PageData>) dao.findForList("MemberMarkeMapper.findCard", pd);
			Integer number = 0;// 用户规定时间内领取的卡券数量
			if (StringUtil.isNotEmpty(list)) {
				number = list.size();
			}
			if (receive_number > number) {// 限制数量>领取数量
				json.put("result", PublicManagerUtil.TRUE);// true--可以领取
				return json;
			} else {
				Long nowdate = Long.parseLong(list.get(0).getString("CREAT_TIME")) * 1000;
				long l = nowdate - startTime.getTime();
				long day = l / (24 * 60 * 60 * 1000);
				long hour = (l / (60 * 60 * 1000) - day * 24);
				long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
				long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
				StringBuffer time = new StringBuffer();
				if (day > 0) {
					time.append(day + "天");
				}
				if (hour > 0) {
					time.append(+hour + "小时");
				}
				if (min > 0) {
					time.append(+min + "分");
				}
				if (s > 0) {
					time.append(+s + "秒");
				}
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "超过规定时间内领取数量," + time + "后再来吧！");
			}
		}
		return json;
	}

	/**
	 * 通过卡劵id获取信息
	 * 
	 * @param pdStore
	 * 卡劵id--cardId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByCardId(PageData pdStore) throws Exception {
		return (List<PageData>) dao.findForList("MemberMarkeMapper.listByCardId", pdStore);
	}


	@Override
	public JSONObject checkTypeSend(PageData pd) throws Exception{
		JSONObject result = new JSONObject();

		if(pd.getString("send_type") == null){
			//不进行下机推送
			result.put("result", "true");
			return result;
		}


		Date current_start_time = Tools.str2Date(pd.getString("start_time"), "yyyy-MM-dd");
		Date current_end_time = Tools.str2Date(pd.getString("end_time"),"yyyy-MM-dd");
		if(current_start_time.getTime() >  current_end_time.getTime()){
			result.put("result", "false");
			result.put("message", "结束时间必须大于开始时间");
			return result;
		}

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
		//检查填入的时间段是否存在
		PageData pdCheck = new PageData();
		pdCheck.put("send_type", pd.getString("send_type"));
		pdCheck.put("internet_id", user.getINTENET_ID());
		List<PageData> pdList = markingContextService.findByTypeAndTime(pdCheck);
		for (int i = 0; i < pdList.size(); i++) {
			if(StringUtil.isNotEmpty(pd.getString("markingcontext_id"))){
				if(pd.getString("markingcontext_id").equals(pdList.get(i).getString("id"))){
					continue;
				}
			}
			Date start_time = Tools.str2Date(pdList.get(i).get("start_time").toString());
			Date end_time = Tools.str2Date(pdList.get(i).get("end_time").toString());

			if (start_time.getTime() < current_start_time.getTime() && current_start_time.getTime() < end_time.getTime()) {
				result.put("result", "false");
				result.put("message", pdList.get(i).get("start_time").toString().substring(0,10) + "~" +pdList.get(i).get("end_time").toString().substring(0,10));
				result.put("err_type", "start_time_err");
				return result;
			}
			if (start_time.getTime() < current_end_time.getTime() && current_end_time.getTime() < end_time.getTime()) {
				result.put("result", "false");
				result.put("message", pdList.get(i).get("start_time").toString().substring(0,10) + "~" +pdList.get(i).get("end_time").toString().substring(0,10));
				result.put("err_type", "end_time_err");
				return result;
			}
		}

		result.put("result", "true");
		return result;
	}


	@Override
	public JSONObject updateSendType(PageData pd) throws Exception{
		JSONObject result = new JSONObject();

		PageData pdType = new PageData();
		pdType.put("id", pd.getString("markingcontext_id"));
		String send_type = pd.getString("send_type");
		if(StringUtil.isEmpty(send_type)){
			pdType.put("send_type", null);
			pdType.put("start_time", null);
			pdType.put("end_time", null);
		}else{
			pdType.put("send_type", send_type);
			pdType.put("start_time", pd.getString("start_time"));
			pdType.put("end_time", pd.getString("end_time"));
		}

		markingContextService.updateSendType(pdType);

		result.put("result", "true");
		result.put("message", "修改成功");
		return result;
	}

	// 会员群发线程1
	class memberMarkeThread implements Runnable {
		private PageData pd;
		private List<PageData> list = new ArrayList<>();
		public memberMarkeThread(PageData pd, List<PageData> list) {
			this.pd = pd;
			this.list = list;
		}
		public void run() {
			try {
				memberMarkeList(pd, list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 会员群发线程2
	class memberMarkeThread1 implements Runnable {
		private PageData pd;
		private List<String> list = new ArrayList<>();
		public memberMarkeThread1(PageData pd, List<String> list) {
			this.pd = pd;
			this.list = list;
		}
		public void run() {
			try {
				memberMarkeList1(pd, list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
