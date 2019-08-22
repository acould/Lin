package com.lk.controller.system.card;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.information.pictures.PicturesManager;
import com.lk.service.internet.matches.MatchesService;
import com.lk.service.internet.scene.SceneManager;
import com.lk.service.system.cancel.CancelManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.cardStore.CardStoreManager;
import com.lk.service.system.dictenty.DictEntyManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.memberMarke.MemberMarkeManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.util.*;
import com.lk.wechat.util.WeChatOpenUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明：卡卷管理 (卡劵设置) 创建人：洪智鹏 创建时间：2016-10-31
 */
@Controller
@RequestMapping(value = "/card")
public class CardController extends BaseController {
	// DICTTYPE(字典编码)
	public static final String DICTTYPE1 = "LK001";
	public static final String DICTTYPE2 = "LK005";

	String menuUrl = "card/list.do"; // 菜单地址(权限用)
	@Resource(name = "cardService")
	private CardManager cardService;
	@Resource(name = "sceneService")
	private SceneManager sceneService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "cancelService")
	private CancelManager cancelService;
	@Resource(name = "dictentyService")
	private DictEntyManager dictentyService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;

    @Autowired
    private MatchesService matchesService;


	/**
	 * 列表 展示网吧优惠券信息
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Card");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/card/card_list");
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 分页加载卡券列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadCard")
	@ResponseBody
	public JSONObject loadCard() throws Exception {
		JSONObject json = new JSONObject();
		User user = this.getUser();//获取用户

		List<String> list = new ArrayList<>();
		List<PageData> sList = null;
		Integer sum = 0;
		// 传入参数（搜索，分类，状态，分页）
		Page page = new Page();
		PageData pd = this.getPageData();
		
		//获取当前时间time1和time2
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time1 = sdf1.format(new Date());
		String time2 = sdf2.format(new Date());
		pd.put("time1", time1);
		pd.put("time2", time2);
		pd.put("intenetId", user.getINTENET_ID());
		
		// 先判断角色
		if (user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)) {// 角色为网吧老板
			// 去查看所有门店(没被禁用的)
			pd.put("storeIds", "");
			pd.put("role", "boss");
			page.setPd(pd);
		} else {// 店长
			List<PageData> list1 = storeService.getStoreList1(user.getUSER_ID()); // 通过user_id去找关联门店id和name的集合
			for (int i = 0; i < list1.size(); i++) {
				list.add(i, list1.get(i).get("store_id").toString());
				if ("1".equals(list1.get(i).get("state").toString())) {// 1--门店加V
					list.add(list1.get(i).get("store_id").toString());
					sum = sum + 1;
				}
			}
			pd.put("list", Joiner.on("','").join(list)); // 门店id集合
			pd.put("role", "staff");
			page.setPd(pd);
		}
		
		//这里通过cardState,cardType去获取筛选后的卡券总和
		Integer count = cardService.lists(page); //count:卡券数量和
		if(StringUtil.isEmpty(pd.get("showCount"))) {//如果showCount为空,为第一次进来,默认赋值为10(一页10条数据)
			pd.put("showCount", 10);
		}
		if(StringUtil.isEmpty(pd.get("currentPage"))) {//如果currentPage为空,为第一次进来,默认赋值为1(第一页)
			pd.put("currentPage", 1);
		}
		//接下来计算limit x,y的值
		Integer x = (Integer.parseInt(pd.get("currentPage").toString())-1) * Integer.parseInt(pd.get("showCount").toString());
		Integer y = Integer.parseInt(pd.get("currentPage").toString()) * Integer.parseInt(pd.get("showCount").toString());
		pd.put("x", x);
		pd.put("y", y);
		page.setPd(pd);
		
		// 列出Card列表(通过网吧id/用户id/门店id去查询卡劵信息)
		List<PageData> varList = cardService.list(page); 
		
		// 通过卡劵id获取卡券信息
		for (PageData pdInternet : varList) {
			String cardId = pdInternet.getString("CARD_ID");
			PageData pdStore = new PageData();
			pdStore.put("CARD_ID", cardId);
			sList = storeService.listByCardId(pdStore);
			pdInternet.put("sList", sList);
			String store = "";
			for (int i = 0; i < sList.size(); i++) {
				if(i == sList.size()-1) {
					store = store + sList.get(i).getString("STORE_NAME");
				}else {
					store = store + sList.get(i).getString("STORE_NAME")+",";
				}
			}
			pdInternet.put("store", store);
		}

		// 遍历卡券集合,判断每张卡券适用门店中的加V门店
		List<PageData> vList = null;
		for (int i = 0; i < varList.size(); i++) {
			PageData pd1 = new PageData();
			pd1.put("CARD_ID", varList.get(i).get("CARD_ID"));
			vList = storeService.listFindCardId(pd1); // 通过卡劵id获取加V门店id
			if (StringUtil.isEmpty(vList)) {// 没有传0--未加V
				varList.get(i).put("state", "0");
			} else if (StringUtil.isNotEmpty(vList) && "boss".equals(pd.get("role").toString())) {// 网吧老板
				varList.get(i).put("state", "1");
			} else if (StringUtil.isNotEmpty(vList) && "staff".equals(pd.get("role").toString())) {// 店员
				if (sum == 0) {// 店员旗下门店均未加V
					varList.get(i).put("state", "0");
				} else {// 传合适的门店id
					List<String> list1 = new ArrayList<>(list);
					List<String> list2 = new ArrayList<>();
					for (int j = 0; j < vList.size(); j++) {
						list2.add(vList.get(j).get("store_id").toString());
					}
					list1.retainAll(list2);// 取list交集
					if (StringUtil.isEmpty(list1)) {
						varList.get(i).put("state", "0");
					} else {
						String storeIds = Joiner.on(",").join(list1);
						varList.get(i).put("state", "1");
						pd.put("storeIds", storeIds); // 店长相关门店id集合
					}
				}
			}
		}
		// cardState  0：全部，1：已失效，2：有效，3：未开始，4：已结束，5：正在抢
		// cardType  CURREN:通用券,NEW:新手券,OLD:老带新,MAN:男神券,WE:女神券,BIRTH:生日券,GRAB:限时秒抢券,APPLY:申请会员福利券,TERM:上网满时长福利券,RUSH:抵用券
		json.put("pd", pd);
		json.put("varList", varList);
		json.put("result", PublicManagerUtil.TRUE);
		json.put("count", count);    
		json.put("currentPage", pd.get("currentPage").toString()); //当前页数
		json.put("showCount", page.getShowCount());  //分页(10,20,30......)
		return json;
	}

	/**
	 * 保存优惠券
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveCard")
	@ResponseBody
	public Message saveCard() throws Exception {
		User user = this.getUser();//获取用户


        //提交的数据
		PageData pd = this.getPageData();
		pd.put("INTENET_ID", user.getINTENET_ID());
        removeCardParam(pd);
		//门店列表
		String[] storeList = pd.getString("STORE_LIST").split(",");

        System.out.println("param ==== "+pd.toString());
		//每个门店，每种时长的券只能存在一张
		if(pd.getString("FAV_TYPE").equals("TERM")) {
			pd.put("internet_id", user.getINTENET_ID());
			pd.put("cardSum_type", pd.getString("cardSum_type"));

			JSONObject result = cardService.judgeTerm(pd, storeList);
			if(result.getString("result").equals("false")) {
				return Message.error(result.getString("message"));
			}
		}

		//判断条件
        Message m2 = checkSaveCardParam(pd);
		if(m2.getErrcode() != 0){
		    return m2;
        }

        if(pd.getString("FAV_TYPE").equals("RUSH")){
            return cardService.saveRushCard(pd);
        }else{
            return cardService.saveCardss(pd);
        }
	}


	/**
	 * 删除
	 * 
	 * @return删除优惠券包含优惠券主键
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JSONObject delete() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Card");
		JSONObject result = new JSONObject();
		PageData pdCard = this.getPageData();
		if(pdCard.get("cardType").toString().equals("RUSH")) {//抵用券(特殊处理)
			//抵用券，只做逻辑删除
            pdCard.put("D_STATE", "1");
            cardService.logicDel(pdCard);
            result.put("result", PublicManagerUtil.TRUE);
            result.put("message", "删除成功");
		}else {
			List<PageData> cancelList = cancelService.findByCardId(pdCard); // 通过卡劵id查询数据
			if (cancelList.size() > 0) {//已被领取过
//				result.put("result", PublicManagerUtil.FALSE);
//				result.put("message", "该卡券已经有领取记录无法删除！");
//				return result;

				//现在改为可逻辑删除
				pdCard.put("D_STATE", "1");
				cardService.logicDel(pdCard);

			} else {//未被领取过
				pdCard = cardService.findById(pdCard); // 通过卡劵id查询数据
				cardService.delete(pdCard); // 通过卡劵id删除卡劵信息
				// 删除场景
				sceneService.delete(pdCard); // 通过卡劵id删除卡劵使用场景
			}
			result.put("result", PublicManagerUtil.TRUE);
			result.put("message", "删除成功");
		}
		return result;
	}

	/**
	 * 创建卡券--先去选择场景
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAddScene")
	public ModelAndView goAddScene() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "----创建卡券（选择场景）goAddScene");
		ModelAndView mv = this.getModelAndView();

		// 场景数据
		PageData page = new PageData();
		page.put("dictType", DICTTYPE1);// 优惠券类型（新手，女神，男神，老带新，通用，生日券）
		List<PageData> sceneList = dictentyService.listAll(page); // 通过优惠劵类型查询信息

		mv.setViewName("system/card/card_type");
		mv.addObject("sceneList", sceneList);
		return mv;
	}

    /**
     * 加载卡券编辑页面的数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadCardEditInfo")
    public Message loadCardEditInfo() throws Exception{

        User user = this.getUser();//获取用户

        //传入参数：CARD_ID, FAV_TYPE
        PageData pd = this.getPageData();
        String FAV_TYPE = pd.getString("FAV_TYPE");


        PageData pdInternet = new PageData();
        PageData pdCard = new PageData();
        if(StringUtil.isEmpty(pd.getString("CARD_ID"))){//新增时
            //获取公众号信息（名称，头像）
            pdInternet = intenetService.findByInternetId(user.getINTENET_ID(), true);

        }else{//编辑时
            //获取卡券信息及其场景信息
            pdCard = cardService.findCardAndSceneById(pd.getString("CARD_ID"));

            pdInternet.put("HEAD_IMG", pdCard.getString("LOGO_URL"));
            pdInternet.put("INTENET_NAME", pdCard.getString("BRAND_NAME"));
            // 已选好的门店id列表
            List<PageData> selStoreList = storeService.listByCardId(pdCard); // 通过卡劵id获取信息
            String store_ids = "";
            for (PageData pdd : selStoreList) {
                store_ids += pdd.getString("STORE_ID") + ",";
            }
            store_ids = store_ids.substring(0, store_ids.length() - 1);
            pdCard.put("store_ids", store_ids);

            FAV_TYPE = pdCard.getString("FAV_TYPE");
            pd.put("FAV_TYPE", FAV_TYPE);
            pd.put("SCENE_ID", pdCard.getString("SCENE_ID"));
            if(FAV_TYPE.equals("RUSH")) {//抵用券时
                //通过卡券id去获取每次赠送金额
                List<PageData> moneyList = cardService.getHandSel(pdCard);
                pdCard.put("moneyList", moneyList);
            }
        }
        //获取操作用户名称
        pd.put("NAME", user.getNAME());


        //获取门店列表
        pd.put("type", "");
        if(FAV_TYPE.equals("RUSH") || FAV_TYPE.equals("APPLY") || FAV_TYPE.equals("TERM")) {
            pd.put("type", "pub");//抵用券，上网满时长福利券，申请会员福利券需要门店加V
        }
        Message m2 = matchesService.chooseStore(pd);//type=pub_yl_jl  noType=noPub_noYl_noJl
        List<PageData> storeList = (List<PageData>) m2.getData().get("list");

        // 获取场景数据
        PageData page = new PageData();
        page.put("dictType", DICTTYPE2);// 时间间隔
        List<PageData> listJg = dictentyService.listAll(page); // 通过优惠劵类型查询信息


        return Message.ok().addData("pd", pd)
                .addData("pdInternet", pdInternet)
                .addData("pdCard", pdCard)
                .addData("listJg", listJg)
                .addData("storeList", storeList);
    }


	/**
	 * 去新增/编辑卡券页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAddCard")
	public ModelAndView goAddCard() throws Exception {
		ModelAndView mv = this.getModelAndView();
		this.getRequest().setCharacterEncoding("UTF-8");
		PageData pd = this.getPageData();
        pd.put("NAME", this.getUser().getNAME());

        if(StringUtil.isEmpty(pd.getString("CARD_ID"))){
            String FAV_TYPE = pd.getString("FAV_TYPE");
            if (FAV_TYPE.equals("TERM")){
                pd.put("cardSum_type", 1);
            }
        }


		mv.addObject("pd", pd);
        mv.setViewName("system/card/card_eedit");
		return mv;
	}


	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public JSONObject deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Card");
		JSONObject result = new JSONObject();

		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			result.put("result", "false");
			result.put("message", "您还没有删除的权限");
			return result;
		}
		PageData pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");

			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				PageData pdCard = new PageData();
				pdCard.put("CARD_ID", ArrayDATA_IDS[i]);
				List<PageData> cancelList = cancelService.findByCardId(pdCard); // 通过卡劵id查询数据
				if (cancelList.size() > 0) {
					result.put("result", "false");
					result.put("message", "卡券名：" + cancelList.get(0).getString("SUB_TITLE") + "，该卡券已经有领取记录，无法删除！");
					return result;
				}
			}

			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				PageData pdCard = new PageData();
				pdCard.put("CARD_ID", ArrayDATA_IDS[i]);
				pdCard = cardService.findById(pdCard); // 通过卡劵id查询数据
				cardService.delete(pdCard); // 通过卡劵id删除卡劵信息
				// 删除场景
				sceneService.delete(pdCard); // 通过id删除卡劵使用场景
			}
			result.put("result", "true");
			result.put("message", "批量删除成功");

		} else {
			result.put("result", "false");
			result.put("message", "参数为空");
			return result;
		}
		return result;
	}

	/**
	 * 修改卡券信息时间大小验证（在修改时 启用时间不能比原来大， 结束时间不能比原来的小，总体：修改时间 时不可以缩小原来有效区间）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/timeold_new")
	@ResponseBody
	public Object timeold_new() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改卡券信息时间验证！");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		PageData pdd = new PageData();
		pdd = cardService.findByCardId(pd); // 通过卡劵id获取卡劵数据
		if (pd.getString("END_TIMESTAMP") != null) {
			Date oldTime = Tools.str2Date2(pdd.getString("END_TIMESTAMP"));
			Date newTime = Tools.str2Date2(pd.getString("END_TIMESTAMP"));
			if (oldTime.getTime() > newTime.getTime()) {
				map.put("result", PublicManagerUtil.ERROR);
			} else {
				map.put("result", PublicManagerUtil.SUCCESS);
			}
		} else if (pd.getString("BEGIN_TIMESTAMP") != null) {
			Date oldTime = Tools.str2Date2(pdd.getString("BEGIN_TIMESTAMP"));
			Date newTime = Tools.str2Date2(pd.getString("BEGIN_TIMESTAMP"));
			if (oldTime.getTime() < newTime.getTime()) {
				map.put("result", PublicManagerUtil.ERROR);
			} else {
				map.put("result", PublicManagerUtil.SUCCESS);
			}
		}
		return AppUtil.returnObject(pd, map);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}




    /******************************************* 可通用方法 ********************************/

    /**
     * 不同类型的卡券，移除其余无效参数
     */
    public void removeCardParam(PageData pd){
        if(!pd.getString("FAV_TYPE").equals("RUSH")){//抵用券
            pd.remove("recharge");
            pd.remove("handsel");
            pd.remove("handsel_sum");
            pd.remove("handsel_type");
            pd.remove("handsel_time");
            pd.remove("handsel_times");
        }
        if(!pd.getString("FAV_TYPE").equals("TERM")){//满时长券
            pd.remove("cardSum_type");
            pd.remove("cardSum_time");
        }
    }

    /**
     * 检查保存卡券的 提交的参数
     * @param pd
     * @return
     */
    public Message checkSaveCardParam(PageData pd) throws Exception{

        //通用判断
        if (StringUtil.isEmpty(pd.getString("COLOR"))) {
            return Message.error("请输入卡券颜色");
        }
        if (StringUtil.isEmpty(pd.getString("QUANTITY"))) {
            return Message.error("请输入卡券库存数量");
        }
        if (StringUtil.isEmpty(pd.getString("RECEIVE_NUMBER"))) {
            return Message.error("请输入单人领取数量");
        }

        if(!pd.getString("FAV_TYPE").equals("APPLY") && !pd.getString("FAV_TYPE").equals("OLD") && !pd.getString("FAV_TYPE").equals("NEW") && !pd.getString("FAV_TYPE").equals("RUSH")) {
            if (StringUtil.isEmpty(pd.getString("BLANK_NUMBER"))) {
                return Message.error("请输入领取间隔");
            }
        }

        if (StringUtil.isEmpty(pd.getString("RECEIVE_DETIL"))) {
            return Message.error("请输入领取间隔单位");
        }
        if (StringUtil.isEmpty(pd.getString("DESCRIPTION"))) {
            return Message.error("请输入优惠说明");
        }
        if (StringUtil.isEmpty(pd.getString("NOTICE"))) {
            return Message.error("请输入使用提醒");
        }

        if (StringUtil.isEmpty(pd.getString("CARD_ID"))) {//新增

            //如果卡券类型为现金券,判断门店是否是加V用户
            if ("cash_ticket".equals(pd.getString("card"))) {
                String[] storeIdList = pd.getString("STORE_LIST").split(",");// 获取传入门店的STORE_ID

                PageData pdParam = new PageData();
                pdParam.put("type", "pub");
                Message m = matchesService.chooseStore(pd);

                //获取已加v的门店列表
                List<PageData> list = (List<PageData>) m.getData().get("list");
                String storev_ids = "";
                String storev_names = "";
                for (PageData pdd : list) {
                    storev_ids += pdd.getString("STORE_ID") + ",";
                    storev_names += pdd.getString("STORE_NAME") + ",";
                }

                //判断已选门店中，是否已加v
                for (int i = 0; i < storeIdList.length; i++) {
                    String STORE_ID = storeIdList[i];
                    if(!storev_ids.contains(STORE_ID)){
                        String[] strarr = storev_ids.split(",");
                        String store_name = "";
                        for (int j = 0; j < strarr.length; j++) {
                            if(strarr[j].equals(STORE_ID)){
                                store_name = storev_names.split(",")[j];
                                break;
                            }
                        }
                        return Message.error( store_name + "：该网吧尚未加v，现金券功能不能使用");
                    }

                }
            }

            // 新增特殊判断
            if (StringUtil.isEmpty(pd.getString("SUB_TITLE"))) {
                return Message.error("请输入卡券名");
            }
            if (StringUtil.isEmpty(pd.getString("TYPE"))) {
                return Message.error("请选择有效期类型");
            } else if (pd.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE")) {
                if (StringUtil.isEmpty(pd.getString("BEGIN_TIMESTAMP"))) {
                    return Message.error("请选择启用时间");
                }
                if (StringUtil.isEmpty(pd.getString("END_TIMESTAMP"))) {
                    return Message.error("请选择结束时间");
                }
                if (Tools.str2Date2(pd.getString("BEGIN_TIMESTAMP")).getTime() >= Tools
                        .str2Date2(pd.getString("END_TIMESTAMP")).getTime()) {
                    return Message.error("结束时间必须大于启用时间");
                }
                if (new Date().getTime() >= Tools.str2Date2(pd.getString("END_TIMESTAMP")).getTime()) {
                    return Message.error("结束时间必须大于当前时间");
                }
            } else if (pd.getString("TYPE").equals("DATE_TYPE_FIX_TERM")) {
                if (StringUtil.isEmpty(pd.getString("FIXED_TERM"))) {
                    return Message.error("请输入几天内有效");
                }
                pd.put("FIXED_BEGIN_TERM", "0");// 固定时长时，设置默认当天生效
            }

        }else{//修改
            PageData pdOld = new PageData();
            pdOld.put("CARD_ID", pd.getString("CARD_ID"));
            pdOld = cardService.findByCardId(pdOld);

            if (pd.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE")) {
                if (Tools.str2Date2(pd.getString("BEGIN_TIMESTAMP")).getTime() >= Tools
                        .str2Date2(pd.getString("END_TIMESTAMP")).getTime()) {
                    return Message.error("结束时间必须大于启用时间");
                }
                if (Tools.str2Date2(pd.getString("BEGIN_TIMESTAMP")).getTime() > Tools
                        .str2Date2(pdOld.getString("BEGIN_TIMESTAMP")).getTime()) {
                    return Message.error("修改后的启用时间必须小于之前的启用时间");
                }
                if (Tools.str2Date2(pd.getString("END_TIMESTAMP")).getTime() < Tools
                        .str2Date2(pdOld.getString("END_TIMESTAMP")).getTime()) {
                    return Message.error("修改后的结束时间必须大于之前的结束时间");
                }
            }
        }

        return Message.ok();
    }

    /**
     * 通过fav_type 获取其含义
     * @param fav_type
     * @return
     */
    public static String getByFavType(String fav_type){
        String typeInfo = "";
        switch (fav_type) {
            case "NEW":
                typeInfo = "新手券";
                break;
            case "OLD":
                typeInfo = "老带新";
                break;
            case "MAN":
                typeInfo = "男神券";
                break;
            case "WEM":
                typeInfo = "女神券";
                break;
            case "BIRTH":
                typeInfo = "生日券";
                break;
            case "CURREN":
                typeInfo = "通用券";
                break;
            case "GRAB":
                typeInfo = "限时秒抢券";
                break;
            case "APPLY":
                typeInfo = "申请会员福利券";
                break;
            case "TERM":
                typeInfo = "上网满时长福利券";
                break;
            case "RUSH":
                typeInfo = "抵用券";
                break;
        }
        return typeInfo;
    }

    /**
     * 去可选择卡券列表页面
     * @return
     */
    @RequestMapping(value = "/goSelCard")
    public ModelAndView goSelCard(){
        ModelAndView mv = this.getModelAndView();

        mv.setViewName("internet/msgManager/card_list");
        return mv;
    }

    /**
     * 查找卡券列表
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/selCardList")
    public Message selCardList() throws Exception{
        PageData pd = this.getPageData();

        User user = this.getUser();
        pd.put("internet_id", user.getINTENET_ID());

        return cardService.selCardList(pd);
    }



}
