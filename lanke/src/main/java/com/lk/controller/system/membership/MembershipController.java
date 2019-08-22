package com.lk.controller.system.membership;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lk.entity.Message;
import com.lk.service.internet.matches.MatchesService;
import com.lk.service.system.menu.MenuManager;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.Membership.MembershipManager;
import com.lk.service.system.memberMarke.MemberMarkeManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

/**
 * 
 * @Title 申请会员
 * @author 陈明阳
 * @date 2018年12月13日上午10:40:41
 */
@Controller
@RequestMapping(value = "/membership")
public class MembershipController extends BaseController {

	@Resource(name = "membershipService")
	private MembershipManager membershipService;
	@Resource(name = "memberMarkeService")
	private MemberMarkeManager memberMarkeService;
    @Resource(name = "menuService")
    private MenuManager menuService;


    @Autowired
    private MatchesService matchesService;
	String menuUrl = "membership/show.do"; // 菜单地址(权限用)
	public static final String TYPE7 = "DATE_TYPE_FIX_TIME_RANGE";
	public static final String TYPE8 = "DATE_TYPE_FIX_TERM";

	// 显示开通会员
	@RequestMapping(value = "/show")
	public ModelAndView show(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		User user = this.getUser();//得到用户

		pd.put("internet_id", user.getINTENET_ID());
		List<PageData> yxstore = membershipService.yxstore(pd);
		String idList="";
		for (PageData p:yxstore) {
			idList+=p.getString("storeid")+",";
		}
		mv.addObject("idList", idList);
		mv.addObject("addedStoreList", yxstore);
		pd = membershipService.view(pd);
		if(StringUtil.isNotEmpty(pd)){
		PageData pdInternet = membershipService.selectCard(pd.getString("couponid"));
		if (StringUtil.isNotEmpty(pdInternet)) {
			String cardId = pdInternet.getString("CARD_ID");
			PageData pdStore = new PageData();
			pdStore.put("CARD_ID", cardId);
			pdStore.put("state", "1");// 1--加V状态
			List<PageData> sList = memberMarkeService.listByCardId(pdStore);// 通过卡券id获取门店信息
			String storeName = "";
			if (sList.size() > 0) {
				for (PageData pds : sList) {
					storeName += pds.getString("STORE_NAME") + "，";
				}
				pdInternet.put("STORE_NAME",
						storeName.substring(0, storeName.length() - 1));
			}
			if (pdInternet.getString("TYPE").equals(TYPE7)) {
				pdInternet.put("AVAILABLE_TIME",
						pdInternet.getString("BEGIN_TIMESTAMP") + "至"
								+ pdInternet.getString("END_TIMESTAMP"));
			} else if (pdInternet.getString("TYPE").equals(TYPE8)) {
				String name = "";
				if (pdInternet.getString("FIXED_BEGIN_TERM").equals("0")) {
					name = "当天";
				} else {
					name = pdInternet.getString("FIXED_BEGIN_TERM") + "天";
				}
				pdInternet.put("AVAILABLE_TIME", "自领取后" + name + "生效，"
						+ pdInternet.getString("FIXED_TERM") + "天内有效");
			}
		}
		mv.addObject("pdInternet", pdInternet);
		}
		mv.addObject("pd", pd);
		mv.setViewName("system/Membership/Membership");
		return mv;
	}

	// 保存开通会员规则
	@ResponseBody
	@RequestMapping(value = "/save")
	public JSONObject save() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		User user = this.getUser();//得到用户

		pd.put("userid", user.getUSER_ID());
		pd.put("createtime", Tools.date2Str(new Date()));
		if (StringUtil.isEmpty(pd.get("memberId"))) {
			pd.put("memberId", this.get32UUID());
		}
		pd.put("internet_id", user.getINTENET_ID());
		json.put("result", membershipService.save(pd));
		return json;
	}

	// 是否禁用
	@ResponseBody
	@RequestMapping(value = "/editType")
	public JSONObject editType() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		membershipService.editType(pd);
		json.put("result", PublicManagerUtil.SUCCESS);
		return json;
	}


	/**
	 * 获取门店列表
	 * @param
	 * @throws Exception
	 */
    @ResponseBody
    @RequestMapping(value = "/getStoreList")
	public Message getStoreList() throws Exception {
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
        pd.put("internet_id", user.getINTENET_ID());

        //已选择的门店
        List<PageData> addedStoreList = membershipService.yxstore(pd);

        //菜单
        PageData pdMenu = new PageData();
        pdMenu.put("menu_url", "accountSettings/list.do");
        pdMenu.put("menu_name", "用户中心");
        pdMenu = menuService.findByUrlAndName(pdMenu);

        pd.put("type", "pub");
        return matchesService.chooseStore(pd).addData("pdMenu", pdMenu).addData("addedStoreList", addedStoreList);
	}

	/**
	 * 查看卡劵列表
	 * 
	 * @param request
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cardList")
	@ResponseBody
	public ModelAndView cardList(HttpServletRequest request, Page page)
			throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "获取符合要求的卡券");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = this.getUser();//得到用户

		pd.put("NEW1", "APPLY");
		pd.put("state", "1");
		if (StringUtil.isNotEmpty(pd.getString("store_id"))) {
			String store_ids[] = pd.getString("store_id").split(",");
			List<String> list = new ArrayList<>();
			for (int i = 0; i < store_ids.length; i++) {
				list.add(i, store_ids[i]);
			}
			pd.put("list", Joiner.on("','").join(list)); // 门店id集合
			pd.put("storelist", list);
		}
		pd.put("internet_id", user.getINTENET_ID());
		List<PageData> cardList = membershipService.selectCards(pd); // 列出Card列表(条件:1.非老带新券,2.非新手券,3.门店没有禁用,4.门店加v(选择对象为会员时))
		if (StringUtil.isNotEmpty(cardList)) {
			// 处理卡券展示数据
            for (int i = 0; i < cardList.size(); i++) {
                PageData pdInternet = cardList.get(i);
				if(pdInternet.get("D_STATE").toString() .equals("1")){
                    //已删除的不显示
                    cardList.remove(i);
                    continue;
				}

				String cardId = pdInternet.getString("CARD_ID");
				PageData pdStore = new PageData();
				pdStore.put("CARD_ID", cardId);
				pdStore.put("state", "1");// 1--加V状态
				List<PageData> sList = memberMarkeService.listByCardId(pdStore);// 通过卡券id获取门店信息
				String storeName = "";
				if (sList.size() > 0) {
					for (PageData pds : sList) {
						storeName += pds.getString("STORE_NAME") + "，";
					}
					pdInternet.put("STORE_NAME",
							storeName.substring(0, storeName.length() - 1));
				}
				if (pdInternet.getString("TYPE").equals(TYPE7)) {
					pdInternet.put("AVAILABLE_TIME",
							pdInternet.getString("BEGIN_TIMESTAMP") + "至"
									+ pdInternet.getString("END_TIMESTAMP"));
				} else if (pdInternet.getString("TYPE").equals(TYPE8)) {
					String name = "";
					if (pdInternet.getString("FIXED_BEGIN_TERM").equals("0")) {
						name = "当天";
					} else {
						name = pdInternet.getString("FIXED_BEGIN_TERM") + "天";
					}
					pdInternet.put("AVAILABLE_TIME", "自领取后" + name + "生效，"
							+ pdInternet.getString("FIXED_TERM") + "天内有效");
				}
			}
		}
		mv.setViewName("weixin/wxMenu/card");
		mv.addObject("cardList", cardList);
		pd.put("falges", "1");
		mv.addObject("pd", pd);
		return mv;
	}

}