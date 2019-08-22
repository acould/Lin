package com.lk.service.system.card.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lk.entity.Message;
import com.lk.service.wechat.WeixinService;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.lk.communicate.server.util.Tools2;
import com.lk.controller.base.BaseController;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.service.internet.scene.SceneManager;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.cardStore.CardStoreManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.store.impl.StoreService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.template.TemplateManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.TemplateMsgUtil;
import com.lk.wechat.util.WechatCardUtil;
import com.lk.wechat.util.WeixinUtil;

/**
 * 说明： 卡卷管理 创建人：洪智鹏 创建时间：2016-10-31
 */
@Service("cardService")
public class CardService extends BaseController implements CardManager {
	// IS_ALL(是否通用，1为通用，0为不通用)

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "cardService")
	private CardManager cardService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	@Resource(name = "sceneService")
	private SceneManager sceneService;
	@Resource(name = "cardStoreService")
	private CardStoreManager cardStoreService;
	@Resource(name = "storeService")
	private StoreService storeService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;
	@Resource(name = "indexMemberService")
	private IndexMemberManager indexMemberService;


	@Resource(name = "weixinService")
	private WeixinService weixinService;


	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("CardMapper.save", pd);
	}

	/**
	 * 删除 通过卡劵id删除卡劵信息
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("CardMapper.delete", pd);
	}

	/**
	 * 修改 修改卡券信息
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("CardMapper.edit", pd);
	}


    /**
     * 通过卡劵id获取数据
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("CardMapper.findById", pd);
    }

	/**
	 * 列表 通过网吧id/用户id/门店id去查询卡劵信息
	 * @param page
	 * 设置pd（必填：intenetId，选填：STORE_ID操作人员所在的门店id，keywords关键词）
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception {
		List<PageData> list = null;
		if(page.getPd().get("cardType").toString().equals("RUSH")) {
			list = (List<PageData>) dao.findForList("CardMapper.list1", page);
		}else {
			list = (List<PageData>) dao.findForList("CardMapper.list", page);
		}
		return list;
	}


	/**
	 * 列表(全部) 查询全部卡劵信息
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CardMapper.listAll", pd);
	}


	/**
	 * 通过卡劵id获取卡劵数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCardId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CardMapper.findByCardId", pd);
	}

    /**
     * 通过卡券id获取其卡券信息及场景信息
     * @param card_id
     * @return
     * @throws Exception
     */
	@Override
	public PageData findCardAndSceneById(String card_id) throws Exception {
		return (PageData) dao.findForObject("CardMapper.findCardAndSceneById", card_id);
	}


	/**
	 * 批量删除 通过卡劵id进行批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("CardMapper.deleteAll", ArrayDATA_IDS);
	}


	/**
	 * 修改卡券状态、
	 * 
	 * @param card（必填：CARD_ID，STATE）
	 */
	public void updateState(PageData card) throws Exception {
		dao.update("CardMapper.updateState", card);

	}


    /**
     * 通过卡券id查询数据
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> selRushs(String card_id) throws Exception {
        return (List<PageData>)dao.findForList("CardMapper.selRushs", card_id);
    }

    @Override
    public int logicDel(PageData pdCard) throws Exception {
        return (int) dao.update("CardMapper.logicDel", pdCard);
    }


    /**
     * 通过场景查看优惠券(查看某场景对应的优惠券)
     *
     * @throws Exception
     */
    public PageData findByCenece(PageData pd) throws Exception {
        return (PageData) dao.findForObject("CardMapper.findByCenece", pd);
    }

    /**
     * 根据优惠类型，查看网吧的优惠券（按新增时间降序，限只取一条）
     *
     * @param pdCard（INTERNET_ID，FAV_TYPE卡券场景（FAV_TYPE优惠类型））
     */
    public PageData findByInternetId(PageData pdCard) throws Exception {
        return (PageData) dao.findForObject("CardMapper.findByInternetId", pdCard);
    }


    /**
     * 查看网吧所有的优惠券
     *
     * @param pd（必填：INTERNET_ID）
     */
    public List<PageData> listByInternet(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CardMapper.listByInternet", pd);
    }

    /**
     * 自定义菜单中使用，查看网吧的通用优惠券（FAV_TYPE = 'CURREN'和IS_ALL = '1'的通用券）
     *
     * @param pd（INTERNET_ID）
     */
    @Override
    public List<PageData> listByMenu(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CardMapper.listByMenu", pd);
    }

    /**
     * 通过AUDIT_STATE获取数据
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public List<PageData> findByState(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CardMapper.findByState", pd);
    }


    /**
     * 保存冲送券卡券关系
     * @param pd
     * @throws Exception
     */
    @Override
    public void saveRush(PageData pd) throws Exception {
        dao.save("CardMapper.saveRush", pd);
    }

    /**
     * 根据卡券id去获取赠送金额
     * @param pdCard
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> getHandSel(PageData pdCard) throws Exception {
        return (List<PageData>)dao.findForList("CardMapper.getHandSel", pdCard);
    }


    /**
     * 保存冲送券推送信息
     * @param pd1
     * @throws Exception
     */
    public void saveCardOpen(PageData pd1) throws Exception {
        dao.save("CardMapper.saveCardOpen", pd1);
    }

    /**
     * 修改冲送券推送信息
     * @param pd1
     * @throws Exception
     */
    public void editCardOpen(PageData pd1) throws Exception {
        dao.update("CardMapper.editCardOpen", pd1);
    }

    /**
     * 根据订单id，卡券id用户id，修改
     * @param pdCardOpen
     * @return
     * @throws Exception
     */
    @Override
    public int editByOrderIdAndCardIdAndOpenId(PageData pdCardOpen) throws Exception {
        return (int) dao.update("CardMapper.editByOrderIdAndCardIdAndOpenId", pdCardOpen);
    }

    /**
     * 通过门店id去获取适用该门店的冲送券信息
     * @param pd
     * @return
     */
    public List<PageData> getRush(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("CardMapper.getRush", pd);
    }

    /**
     * 通过冲送券卡券id去获取卡券详情
     * @return
     * @throws Exception
     */
    public List<PageData> getRushCard(String card_id) throws Exception {
        return (List<PageData>)dao.findForList("CardMapper.getRushCard", card_id);
    }

    /**
     * 查询今日发送的冲送券
     * @param time
     * @return
     * @throws Exception
     */
    public List<PageData> selRush(String time) throws Exception {
        return (List<PageData>)dao.findForList("CardMapper.selRush", time);
    }

    /**
     * 通过id查看领取情况
     * @return
     * @throws Exception
     */
    @Override
    public PageData findId(String id) throws Exception {
        return (PageData) dao.findForObject("CardMapper.findId", id);
    }

    @Override
    public List<PageData> findValidRushByCardId(String card_id) throws Exception{
        return (List<PageData>)dao.findForList("CardMapper.findValidRushByCardId", card_id);
    }

    @Override
    public List<PageData> findByStoreIdAndFavType(String store_id, String fav_type) throws Exception{
        PageData pd = new PageData();
        pd.put("store_id", store_id);
        pd.put("fav_type", fav_type);
        return (List<PageData>)dao.findForList("CardMapper.findByStoreIdAndFavType", pd);
    }

    /**
     * 查询订单的抵用券
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public PageData findByOrderIdAndCardId(PageData pd) throws Exception{
        return (PageData) dao.findForObject("CardMapper.findByOrderIdAndCardId", pd);
    }


    @Override
    public PageData findCardOpenById(String id) throws Exception{
        return (PageData) dao.findForObject("CardMapper.findCardOpenById", id);
    }




    /******************************************* 业务代码——后台 ********************************/


    @Override
    public Message saveCardss(PageData pd) throws Exception{
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        String card_id = "";//卡券id

        // 设置卡券默认数据
        pd.put("CODE_TYPE", "CODE_TYPE_QRCODE");// 设置默认文本
        pd.put("DEFAULT_DETAIL", pd.getString("DESCRIPTION"));// 设置默认优惠详情 = 优惠说明
        pd.put("TITLE", pd.getString("SUB_TITLE"));
        pd.put("CARD_TYPE", "GIFT");//设置优惠券

        Intenet org = intenetService.getIntenetById(user.getINTENET_ID()); // 通过网吧id获取网吧信息
        String token = autoReplyService.getAuthToken(user.getINTENET_ID());

        PageData pdOld = new PageData();
        if (StringUtil.isEmpty(pd.getString("CARD_ID"))) {//新增

            // 新增保存
            JSONObject resultJSON = WechatCardUtil.createCard(org, token, pd);
            // 判断微信公众号是否开通卡券
            if (resultJSON.containsKey("errcode") && resultJSON.getString("errcode").equals("48001")) {
                return Message.error("您的微信公众号尚未开通卡券功能！");
            }

            //判断是否创建成功
            if (StringUtil.isEmpty(resultJSON.getString("card_id"))) {
                return Message.error("微信创建卡券失败！");
            }

            pd.put("CARD_ID", resultJSON.getString("card_id"));
            card_id = pd.getString("CARD_ID");
            onlySaveCard(pd);

        } else {//修改
            pdOld.put("CARD_ID", pd.getString("CARD_ID"));
            pdOld = findByCardId(pdOld);

            //剔除某些字段
            pd.remove("DEFAULT_DETAIL");// 优惠详情不支持更改
            pd.remove("SUB_TITLE");// 卡券名称不支持更改
            pd.remove("TITLE");// 卡券名称不支持更改
            pd.remove("cardSum");//已领取数量不支持修改
            pd.put("TYPE", pdOld.getString("TYPE"));
            pd.put("FIXED_TERM", pdOld.getString("FIXED_TERM"));
            pd.put("FIXED_BEGIN_TERM", pdOld.getString("FIXED_BEGIN_TERM"));

            // 修改卡券信息接口
            JSONObject resultJSON = WechatCardUtil.updateCard(org, token, pd, pdOld); // 更改卡券信息

            String audit_state = judgeAuditStatus(resultJSON, token, pd.getString("CARD_ID"));
            pd.put("AUDIT_STATE", audit_state);

            //修改库存
            int newQUANTITY = Integer.parseInt(pd.getString("QUANTITY"));  //新填写的卡券总库存
            int oldQUANTITY = Integer.parseInt(String.valueOf(pdOld.get("QUANTITY"))); //已存的总库存
            if (newQUANTITY != oldQUANTITY) {
                WechatCardUtil.modifystock(token, pd.getString("CARD_ID"), oldQUANTITY, newQUANTITY); // 修改库存需重新调用接口
            }

            onlyUpdateCard(pd);
            card_id = pd.getString("CARD_ID");

        }
        return Message.ok("保存成功").addData("CARD_ID", card_id);
    }

    @Override
    public Message saveRushCard(PageData pd) throws Exception{
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        // 设置卡券默认数据
        pd.put("CODE_TYPE", "CODE_TYPE_QRCODE");// 设置默认文本
        pd.put("DEFAULT_DETAIL", pd.getString("DESCRIPTION"));// 设置默认优惠详情 = 优惠说明
        pd.put("TITLE", pd.getString("SUB_TITLE"));
        pd.put("CARD_TYPE", "GIFT");//设置优惠券
        pd.put("CARD_TICKET", "1");// 冲送券必为现金券
        pd.put("FIXED_BEGIN_TERM", "0");//当天生效
        pd.put("CASH_NUMBER", pd.get("handsel").toString());//现金券金额 = 赠送金额

        PageData pdOld = new PageData();
        if (StringUtil.isEmpty(pd.getString("CARD_ID"))) {//新增

            String card_id = StringUtil.get32UUID();//卡券id
            pd.put("CARD_ID", card_id);
            onlySaveCard(pd);

            //再去保存其关联的现金券
            Integer handsel_sum = Integer.parseInt(pd.get("handsel_sum").toString());
            for (int i = 0; i < handsel_sum; i++) {//便利保存卡券信息
                String a = "CASH_NUMBER"+i;

                //新增隐藏的卡券
                pd.put("CASH_NUMBER", pd.get(a));//获取现金券金额
                pd.remove("CARD_ID");
                Message m2 = saveCardss(pd);

                //保存关联表
                PageData pdCardRelation = new PageData();
                pdCardRelation.put("card_ids", card_id);//显示卡券
                pdCardRelation.put("CARD_ID", m2.getData().get("CARD_ID"));//微信创建的卡券（隐藏的）
                pdCardRelation.put("card_order", i+1);//第几次
                cardService.saveRush(pdCardRelation);//保存冲送券卡券关系
            }
        } else {//修改
            //冲送券只能修改
            // 颜色， 库存， 优惠说明，使用提醒
            pdOld.put("CARD_ID", pd.getString("CARD_ID"));
            pdOld = findByCardId(pdOld);

            Intenet org = intenetService.getIntenetById(user.getINTENET_ID()); // 通过网吧id获取网吧信息
            String token = autoReplyService.getAuthToken(user.getINTENET_ID());
            //剔除某些字段
            pd.remove("DEFAULT_DETAIL");// 优惠详情不支持更改
            pd.remove("SUB_TITLE");// 卡券名称不支持更改
            pd.remove("TITLE");// 卡券名称不支持更改
            pd.remove("cardSum");//已领取数量不支持修改
            pd.put("TYPE", pdOld.getString("TYPE"));
            pd.put("FIXED_TERM", pdOld.getString("FIXED_TERM"));
            pd.put("FIXED_BEGIN_TERM", pdOld.getString("FIXED_BEGIN_TERM"));

            // 修改卡券信息
            cardService.edit(pd);// 修改卡券信息

            //修改隐藏的卡券信息
            List<PageData> validRush = findValidRushByCardId(pd.getString("CARD_ID"));
            for (PageData pdValidRush : validRush) {

                pdValidRush.put("NOTICE", pd.getString("NOTICE"));
                pdValidRush.put("DESCRIPTION", pd.getString("DESCRIPTION"));
                pdValidRush.put("COLOR", pd.getString("COLOR"));

                // 修改卡券信息接口
                JSONObject resultJSON = WechatCardUtil.updateCard(org, token, pdValidRush, pdValidRush); // 更改卡券信息

                String audit_state = judgeAuditStatus(resultJSON, token, pdValidRush.getString("CARD_ID"));
                pdValidRush.put("AUDIT_STATE", audit_state);

                //修改库存
                int newQUANTITY = Integer.parseInt(pd.getString("QUANTITY"));  //新填写的卡券总库存
                int oldQUANTITY = Integer.parseInt(String.valueOf(pdValidRush.get("QUANTITY"))); //已存的总库存
                if (newQUANTITY != oldQUANTITY) {
                    WechatCardUtil.modifystock(token, pdValidRush.getString("CARD_ID"), oldQUANTITY, newQUANTITY); // 修改库存需重新调用接口
                    pdValidRush.put("QUANTITY", pd.getString("QUANTITY"));
                }

                cardService.edit(pdValidRush);// 修改卡券信息
            }

        }
        return Message.ok("保存成功");
    }

    /**
     * 仅保存
     * @param pd
     * @throws Exception
     */
    public void onlySaveCard(PageData pd) throws Exception{
        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        pd.put("STATE", "CSZT");

        pd.put("cardSum", "0");//已领取的卡券
        String sceneId = this.get32UUID();
        pd.put("SCENE_ID", sceneId);
        pd.put("CREATE_TIME", Tools.date2Str(new Date()));// 创建时间
        pd.put("AUDIT_STATE", 1);//1为正常
        // 是否通用，1为通用，0为不通用
        String all = pd.getString("allChecked");
        if (all == null) {
            pd.put("IS_ALL", 0);
        } else {
            if (user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)) {// 只有网吧管理员才能创建通用
                pd.put("IS_ALL", 1);
            } else {
                pd.put("IS_ALL", 0);
            }
        }

        pd.put("CARD_TICKET", pd.getString("CASH_NUMBER").isEmpty() ? 2 : 1);
        pd.put("D_STATE", "0");//默认卡券创建未删除0
        cardService.save(pd);//保存卡券信息


        // 新增场景数据
        PageData pdScene = new PageData();
        pdScene.put("SCENE_ID", sceneId);
        pdScene.put("SCENE_NAME", pd.getString("SCENE_NAME"));
        pdScene.put("FAV_TYPE", pd.getString("FAV_TYPE"));
        pdScene.put("BENEFIT_TYPE", judgeSceneType(pdScene.getString("FAV_TYPE")));
        pdScene.put("RECEIVE_NUMBER", pd.getString("RECEIVE_NUMBER"));
        pdScene.put("BLANK_NUMBER", pd.getString("BLANK_NUMBER"));
        pdScene.put("RECEIVE_DETIL", pd.getString("RECEIVE_DETIL"));
        pdScene.put("unusertime", pd.getString("time"));//添加无效时间
        if(pd.getString("FAV_TYPE").equals("RUSH")){
            pdScene.put("RUSH_BUY_NUMBER", pd.getString("RUSH_BUY_NUMBER"));
            pdScene.put("RUSH_BUY_TIME", pd.getString("RUSH_BUY_TIME"));
            pdScene.put("RUSH_BUY_TIME_UNITS", pd.getString("RUSH_BUY_TIME_UNITS"));
        }
        pdScene.put("INTENET_ID", user.getINTENET_ID());
        pdScene.put("STATE", "1");
        pdScene.put("RECEIVE_STATE", "YES");
        pdScene.put("CANCEL_STATE", "YES");
        sceneService.save(pdScene);

        // 新增卡券和门店关系表
        String[] storeList = pd.getString("STORE_LIST").split(",");
        PageData pdStore = new PageData();
        for (int i = 0; i < storeList.length; i++) {
            pdStore.put("CARD_STORE_ID", this.get32UUID());
            pdStore.put("CARD_ID", pd.get("CARD_ID"));
            pdStore.put("STORE_ID", storeList[i]);
            cardStoreService.save(pdStore);
        }
    }

    /**
     * 仅修改
     * @param pd
     * @throws Exception
     */
    public void onlyUpdateCard(PageData pd) throws Exception{
        // 是否通用，1为通用，0为不通用
        String all = pd.getString("allChecked");
        if (all == null) {
            pd.put("IS_ALL", 0);
        } else {
            pd.put("IS_ALL", 1);
        }

        // 修改卡券信息
        cardService.edit(pd);// 修改卡券信息

        // 修改场景数据
        PageData pdScene = new PageData();
        pdScene.put("SCENE_ID", pd.getString("SCENE_ID"));
        pdScene.put("RECEIVE_NUMBER", pd.getString("RECEIVE_NUMBER"));
        pdScene.put("BLANK_NUMBER", pd.getString("BLANK_NUMBER"));
        pdScene.put("RECEIVE_DETIL", pd.getString("RECEIVE_DETIL"));
        sceneService.edit(pdScene);// 修改场景数据

        // 更换适用的门店:先删除,再创建
        PageData pdCard = new PageData();
        pdCard.put("CARD_ID", pd.getString("CARD_ID"));
        cardStoreService.deleteByCardId(pdCard); // 通过卡劵id删除卡劵(更换门店)
        String[] storeList = pd.getString("STORE_LIST").split(",");// ajax提交
        for (int i = 0; i < storeList.length; i++) {
            String storeId = storeList[i];
            PageData pdCardStore = new PageData();
            pdCardStore.put("CARD_STORE_ID", this.get32UUID());
            pdCardStore.put("CARD_ID", pd.getString("CARD_ID"));
            pdCardStore.put("STORE_ID", storeId);
            cardStoreService.save(pdCardStore); // 新建卡劵(更换门店)
        }
    }



    /**
     * 判断审核状态
     * @return
     */
    public static String judgeAuditStatus(JSONObject resultJSON, String token, String card_id){
        //send_check 此次更新是否需要提审，true为需要，false为不需要。

        String audit_state = "";
        if (resultJSON.getString("errcode").equals("0") && resultJSON.getString("send_check").equals("false")) {
            audit_state = "1"; // 1为正常 审核通过
        } else if (resultJSON.getString("errcode").equals("0") && resultJSON.getString("send_check").equals("true")) {
            // 调用查询卡券详情接口
            JSONObject json = WechatCardUtil.getCardParticulars(token, card_id); // 查看卡券详情
            // 查看提审状态
            String status = json.getJSONObject("card").getJSONObject("gift")
                    .getJSONObject("base_info").getString("status");

            if (status.equals("CARD_STATUS_NOT_VERIFY")) {
                audit_state = "2"; // 2为审核中/待审核
            } else if (status.equals("CARD_STATUS_VERIFY_FAIL")) {
                audit_state = "3";// 3为正常 审核失败
            } else if (status.equals("CARD_STATUS_VERIFY_OK")) {
                audit_state = "1"; // 1为正常 审核通过
            } else if (status.equals("CARD_STATUS_DELETE")) {//卡券被商户删除
            } else if (status.equals("CARD_STATUS_DISPATCH")) {//在公众平台投放过的卡券
            }
        }


        return audit_state;
    }

    /**
     * 判断优惠券和福利券
     * @param FAV_TYPE
     * @return
     */
    public static String judgeSceneType(String FAV_TYPE){
        String BENEFIT_TYPE = "YHQ";
        if (FAV_TYPE.endsWith("MAN")
                || FAV_TYPE.endsWith("WEM")
                || FAV_TYPE.endsWith("BIRTH")
                || FAV_TYPE.endsWith("CURREN")
                || FAV_TYPE.endsWith("GRAB")
                || FAV_TYPE.endsWith("TERM")) {
            BENEFIT_TYPE = "FLQ";
        } else if(FAV_TYPE.endsWith("RUSH")){
            BENEFIT_TYPE = "YHQ";
        }
        return BENEFIT_TYPE;
    }




	 /**
     * 通过卡券id和user_id去判断是否可领取
     * @param pd
     * @return
     * @throws Exception
     */
	@Override
	public JSONObject getCard(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		PageData pd1 = (PageData) dao.findForObject("CardMapper.getCard", pd);
		if(pd1.getString("FAV_TYPE").equals("GRAB")) {//限时秒抢券
			//去判断秒抢券现在的状态 3：未开始，4：已结束，5：正在抢
			if(pd1.getString("cardState").equals("3")) {//GRAB_3：未开始
				json.put("result", PublicManagerUtil.FALSE);
				json.put("state", "GRAB_3");
				return json;
			}else if(pd1.getString("cardState").equals("4")) {//GRAB_4：已结束
				json.put("result", PublicManagerUtil.FALSE);
				json.put("state", "GRAB_4");
				return json;
			}else if(pd1.getString("cardState").equals("5")) {//GRAB_5：正在抢
				json.put("result", PublicManagerUtil.TRUE);
				json.put("state", "GRAB_5");
				return json;
			}
		}else if(pd1.getString("FAV_TYPE").equals("TERM")) {//连续上网满时常券(只有会员可以领取)
			Calendar c = Calendar.getInstance();// 日历
			Calendar c1 = Calendar.getInstance();// 日历
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");// 格式工具
			//通过user_id去获取上网时长
			if(pd1.get("cardSum_type").toString().equals("2")) {//连续一周
				c.add(Calendar.WEEK_OF_MONTH, -1);
				pd.put("time1", sdf.format(c.getTime()));
				pd.put("time2", sdf.format(c1.getTime()));
				pd.put("times", "10080");//times,分钟数:10080分(一周)
				
			}else if(pd1.get("cardSum_type").toString().equals("3")) {//连续一月
				c.add(Calendar.MONTH, -1);
				pd.put("time1", sdf.format(c.getTime()));
				pd.put("time2", sdf.format(c1.getTime()));
				pd.put("times", "43200");//times,分钟数:43200分(一月)
			}
			//通过user_id和time去获取时间区域内的上网总时长
			PageData pd2 = (PageData) dao.findForObject("CardMapper.getCardState", pd);
			Integer times = Integer.parseInt(pd.getString("times"));//要求时长
			Integer timeSum = Integer.parseInt(pd2.get("timeSum").toString());//实际时常
			if(timeSum>=times) {//TERM_1:满足条件
				json.put("result", PublicManagerUtil.TRUE);
				json.put("state", "TERM_1");
				return json;
			}else {//TERM_2:未满足领取条件
				json.put("result", PublicManagerUtil.FALSE);
				json.put("state", "TERM_2");
				json.put("cardSum_type", pd1.get("cardSum_type").toString());
				json.put("cardSum_time", pd1.get("cardSum_type").toString());
				return json;
			}
		}else {
			json.put("result", PublicManagerUtil.TRUE);
		}
		return json;
	}

	/**
	 * 通过网吧id去获取最近的秒抢券信息
	 * @param pd1
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData cardGrab(PageData pd1) throws Exception {
		//获取当前时间time1和time2
		pd1.put("time", Tools.date2Str(new Date()));
		PageData pd = (PageData) dao.findForObject("CardMapper.cardGrab", pd1);
		if(StringUtil.isEmpty(pd)) {
			return null;
		}
		return pd;
	}

	/**
	 * 通过网吧id去获取最近的连续上网满时长券信息
	 * @param pd1
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData cardTerm(PageData pd1) throws Exception {
		PageData pd = (PageData) dao.findForObject("CardMapper.cardTerm", pd1);
		if(StringUtil.isEmpty(pd)) {
			return null;
		}else {//卡券所适用的门店列表
			List<PageData> sList = storeService.listByCardId(pd);
			String store = "";
			for (int i = 0; i < sList.size(); i++) {
				if(i == sList.size()-1) {
					store = store + sList.get(i).getString("STORE_NAME");
				}else {
					store = store + sList.get(i).getString("STORE_NAME")+",";
				}
			}
			pd.put("store", store);
		}
		return pd;
	}

	/**
	 * 每个门店，每种时长的券只能存在一张
	 * @return
     * @param pd : store_id cardSum_type
	 * @throws Exception
	 */
	@Override
	public JSONObject judgeTerm(PageData pd, String[] storeList) throws Exception {
		JSONObject json = new JSONObject();
        json.put("result", "true");

        //每个门店，每种时长的券只能存在一张
        List<PageData> list = (List<PageData>) dao.findForList("CardMapper.judgeTerm", pd);

        for (int i = 0; i < storeList.length; i++) {
            String storeId = storeList[i];
            for(PageData pdd : list){
                //相同表示存在
                if(storeId.equals(pdd.getString("store_id"))
                        && !pdd.getString("CARD_ID").equals(pd.getString("CARD_ID"))){
                    json.put("result", "false");
                    json.put("store_id", storeId);
                    json.put("message", "每个门店，每种时长的券只能存在一张");
                    return json;
                }
            }
        }
		return json;
	}


   //计算时长1， 根据上下机时间，以及不可计算时长段，统计出除去的时长值
	private long calUnTimes(String unusertime, String up_time, String down_time, String up_date, long jie_minutes){
        long up_time_long = Tools.str2Date(up_time).getTime();
        long down_time_long = Tools.str2Date(down_time).getTime();
        String[] times = unusertime.split("、");
        for (int j = 0; j < times.length; j++) {
            String start = times[j].split(" - ")[0];
            String end = times[j].split(" - ")[1];
            Date date = Tools.str2Date(start, "mm:ss");
            Date date2 = Tools.str2Date(end, "mm:ss");
            long d = ((date2.getTime() - date.getTime()) / 1000) * 60 * 1000;//不可计算时间段

            //上下机在同一天
            Date jie_start = Tools.str2Date(up_date + " " + start, "yyyy-MM-dd HH:mm");
            Date jie_end = Tools.str2Date(up_date + " " + end, "yyyy-MM-dd HH:mm");

            //1. a < up < b ,不可计算时间 = up - b
            if (up_time_long > jie_start.getTime() && up_time_long < jie_end.getTime()) {
                jie_minutes += (jie_end.getTime() - up_time_long);
            }
            //2. up <= a , b <= down 不可计算时间 = a-b
            if (up_time_long <= jie_start.getTime() && jie_end.getTime() < down_time_long) {
                jie_minutes += d;
            }

            //3. a < down < b 不可计算时间 = down - a
            if (jie_start.getTime() < down_time_long && down_time_long < jie_end.getTime()) {
                jie_minutes += (down_time_long - jie_start.getTime());
            }
        }
        return jie_minutes;
    }

    //计算时长2
    private long calTimes(String unusertime, String up_time, String down_time, long jie_minutes) {
        //上下机日期
        String up_date = up_time.substring(0, 10);
        String down_date = down_time.substring(0, 10);
        if (up_date.equals(down_date)) {
            //上下机在同一天
            jie_minutes = calUnTimes(unusertime, up_time, down_time, up_date, jie_minutes);
        } else {
            //上下机不在同一天
            Date d1 = Tools.str2Date(up_date, "yyyy-MM-dd");
            Date d2 = Tools.str2Date(down_date, "yyyy-MM-dd");
            int tian = (int) ((d2.getTime() - d1.getTime()) / 1000 / 60 / 60 / 24) + 1;//上下机间隔天数
            for (int j = 0; j < tian; j++) {
                up_date = Tools.dateStr(Tools.sAddDays(d1, j)).substring(0, 10);
                String up_time2 = up_time;
                if (j > 0) {
                    up_time2 = up_date + " 00:00:00";//第n天的
                }
                String down_time2 = down_time;
                if (j != tian - 1) {
                    down_time2 = up_date + " 23:59:59";//第n天的
                }
                jie_minutes = calUnTimes(unusertime, up_time2, down_time2, up_date, jie_minutes);
                System.out.println(jie_minutes);
            }

        }
        return jie_minutes;
    }


    public static void main(String[] args) {
        String unusertime = "03:00 - 05:00、20:00 - 22:00";
        String up_time = "2019-08-02 03:10:20";
        String down_time = "2019-08-04 21:30:30";

        long minutes = new CardService().calTimes(unusertime, up_time, down_time, 0);
        System.out.println(minutes /1000 /60);//需要减去的分钟数
    }

	/**
	 * 会员下机，判断满足连续上网满时长券，满足则推送卡券
	 * @param pd1
	 * @throws Exception
	 */
	@Override
	public void getCards(PageData pd1) throws Exception {
        String id_card = pd1.getString("id_card");
        String user_id = pd1.getString("user_id");
        String open_id = pd1.getString("open_id");
        String internet_id = pd1.getString("internet_id");
        String up_duration = pd1.get("up_duration").toString();
        String up_time = pd1.get("up_time").toString();
        String down_time = pd1.get("down_time").toString();
        //本次上网分钟数
        float up_times = Float.parseFloat(up_duration);
	    logger.debug("卡号==" + id_card + "，上网时长（分钟）==" +up_times);


        //查询已设置的连续上网满时长券
        List<PageData> list = (List<PageData>) dao.findForList("CardMapper.getCards", pd1);

        for (int i = 0; i < list.size(); i++) {
            PageData pdCard = list.get(i);
            JSONObject result = indexMemberService.judgeDateNumber(pdCard,
                    pdCard.getString("CARD_ID"), user_id);
            //判断领取限制等条件
            if(!result.getString("msg").equals("success")){
                list.remove(i);
                i--;
                continue;
            }

            //判断是否过期
            if(pdCard.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE")){
                Date end_timestamp = Tools.str2Date2(pdCard.getString("END_TIMESTAMP"));
                //过期时间 《 当前时间 , 已过期
                if(end_timestamp.getTime() < new Date().getTime()){
                    list.remove(i);
                    i--;
                    continue;
                }
            }

            //时长是否满足 上机时长 < 设置时长 ， 不满足
            float term_minutes = Float.parseFloat(pdCard.getString("cardSum_time")) * 60;


            //判断实际上机时长（除去设置不可计算时段的时长）
            //如设置00:00 - 06:00 ，上机时间为2019-8-2 23:00:00 下机时间为2019-8-3 08:00:00 ， 则上机时长应除去6个小时
            String unusertime = pdCard.getString("unusertime");
            long jie_minutes = 0;
            if(StringUtil.isNotEmpty(unusertime)){
                jie_minutes = calTimes(unusertime, up_time, down_time, jie_minutes);

                jie_minutes = (jie_minutes /1000 /60);
                up_times -= jie_minutes;//除去不可计算的时长分钟
                if(up_times < term_minutes){
                    list.remove(i);
                    i--;
                    continue;
                }
            }
        }


        if(list.size() > 0){
            logger.info("本次上网满足连续满时长券的要求，给与发放...");
            //保存冲送券推送信息
            PageData pdCardOpen = new PageData();
            pdCardOpen.put("id", StringUtil.get32UUID());//主键Id
            pdCardOpen.put("card_id", list.get(0).get("CARD_ID").toString());//发送卡券id
            pdCardOpen.put("open_id", open_id);//关联用户id
            pdCardOpen.put("need_time", Tools.dateStr(new Date()));//需要发送日期
            pdCardOpen.put("state", "0");//发送状态(0-未发送,1-已发送,2-发送失败)
            pdCardOpen.put("internet_id", internet_id);//网吧id
            pdCardOpen.put("card_state", "0");//领取状态(0--未领取,1--已领取)
            pdCardOpen.put("type", "TERM");//卡券类型
            this.saveCardOpen(pdCardOpen);

            //发送充送券
            JSONObject obj = new JSONObject();
            obj.put("keyword1", "连续上网活动");//服务类型
            String string = "连续上网" + list.get(0).get("cardSum_time").toString() + "小时,送券";
            obj.put("keyword2", string);//服务状态
            obj.put("keyword3", Tools.date2Str(new Date()));//服务时间

            PageData pdData = new PageData();
            pdData.put("first_data", "亲爱的用户，您本次上网已获得满时长福利哦！");//标题
            pdData.put("keyword_data", obj.toString());//内容格式：{keyword1:1,keyword2:2,keyword3:3}
            pdData.put("remark_data", "点击详情领取上网满时长券");//备注
            pdData.put("url", PublicManagerUtil.URL2 + "wxML/goLqTermCard.do?CARD_ID="
                    + list.get(0).get("CARD_ID").toString()
                    + "&id=" + pdCardOpen.getString("id")
                    + "&openid=" + open_id);//跳转

            JSONObject sendResult = weixinService.sendTamplate(internet_id, open_id, "server_plan_notify", pdData);
            if (StringUtil.isNotEmpty(sendResult.getString("errcode"))) {
                if (sendResult.getString("errcode").equals("0")) {//发送成功
                    //去改变满时长券发送状态
                    PageData pdSendCard = new PageData();
                    pdSendCard.put("id", pdCardOpen.getString("id"));//主键Id
                    pdSendCard.put("real_time", Tools.date2Str(new Date()));//实际发送时间
                    pdSendCard.put("state", "1");//发送成功
                    cardService.editCardOpen(pdSendCard);
                }else{
                    //去改变满时长券发送状态
                    PageData pdSendCard = new PageData();
                    pdSendCard.put("id", pdCardOpen.getString("id"));//主键Id
                    pdSendCard.put("state", "2");//发送失败
                    cardService.editCardOpen(pdSendCard);
                }
            }
        }





	}

	/**
	 * 通过条件去获取卡券总和
	 * @param page
	 * @return
	 */
	@Override
	public Integer lists(Page page) throws Exception {
		PageData pd = (PageData) dao.findForObject("CardMapper.lists", page);
		return  Integer.parseInt(pd.get("count1").toString());
	}






    /******************************************* 业务代码——微信端 ********************************/


    /**
     * 微信端用，获取卡券列表
     *
     * @param pd（选填：intenetId，STORE_ID，cardType卡券场景（BENEFIT_TYPE））
     */
    public List<PageData> listFl(PageData pd) throws Exception {
        //获取当前时间time
        String time = Tools.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss");
        pd.put("time", time);
        List<PageData> list = (List<PageData>) dao.findForList("CardMapper.listFl", pd);

        Date now = new Date();

        for (int i = 0; i < list.size(); i++) {
            PageData pdCard = list.get(i);
            if(pdCard.get("FAV_TYPE").toString().equals("GRAB")){
                //秒抢券，延期展示1天
                String end_time = list.get(i).getString("garbEnd_time").split(" ")[0]; //卡券秒抢结束时间
                Date endDate = Tools.str2Date2(end_time);
                endDate = Tools.sAddDays(endDate, 1);//卡券领取时间+1天
                if(now.getTime() > endDate.getTime()){//当前时间>卡券秒抢结束时间+1天
                    list.remove(i);
                }
            }else{
                if(pdCard.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE")){
                    //TYPE类型为：时间区间的
                    if(!pdCard.toString().equals("0")) {//卡券库存不为0
                        String time1= list.get(i).getString("END_TIMESTAMP");
                        Date date1 = Tools.str2Date2(time1);
                        if (now.getTime() > date1.getTime()) {//现在时间>过期时间  （先过期,库存不为0）
                            date1 = Tools.sAddDays(date1, 1);//卡券过期时间+1天
                            if (now.getTime() > date1.getTime()) {//当前时间>过期日期+1天
                                list.remove(i);
                            }
                        }
                    }else {//库存为0
                        //通过卡券id去获取该类型卡券最后一张被领取的时间
                        PageData pd1 = (PageData) dao.findForObject("CardMapper.getTime", list.get(i).getString("CARD_ID"));
                        String time1 = pd1.get("UPDATE_TIME").toString().split(" ")[0]; //卡券领取时间
                        String time2= list.get(i).getString("END_TIMESTAMP");
                        Date date1 = Tools.str2Date2(time1);
                        Date date2 = Tools.str2Date2(time2);
                        if (date2.getTime() > date1.getTime()) {//过期时间>领取时间 (库存先为0,还未过期)
                            date1 = Tools.sAddDays(date1, 1);//卡券领取时间+1天
                            if (now.getTime() > date1.getTime()) {//当前时间>领取最后一张时间+1天
                                list.remove(i);
                            }
                        } else {//过期时间<领取时间 (先过期,库存后为0)   //过期后还可以领取??????
                            date2 = Tools.sAddDays(date2, 1);//卡券领取时间+1天
                            if (now.getTime() > date1.getTime()) {//当前时间>领取最后一张时间+1天
                                list.remove(i);
                            }
                            if (now.getTime() > date2.getTime()) {//当前时间>过期日期+1天
                                list.remove(i);
                            }
                        }
                    }
                }else{
                    //TYPE类型为：领取后几天生效的
                    if(pdCard.get("QUANTITY").toString().equals("0")) {//库存为0
                        PageData pd1 = (PageData) dao.findForObject("CardMapper.getTime", pdCard.getString("CARD_ID"));
                        String time1 = pd1.get("UPDATE_TIME").toString().split(" ")[0]; //卡券领取时间
                        Date date1 = Tools.str2Date2(time1);
                        date1 = Tools.sAddDays(date1, 1);//加一天
                        if(now.getTime() > date1.getTime()){//当前时间>领取最后一张时间+1天
                            list.remove(i);
                        }
                    }
                }

            }

        }
        return list;
    }



    /******************************************* 可通用方法 ********************************/

    /**
     * 查询可选的卡券列表 （通用券,男神券，女神券，生日券，秒抢券， 未删除的，未到期的）
     * @param pd 必填internet_id 选填store_id
     * @return
     */
    @Override
    public Message selCardList(PageData pd) throws Exception{

        //判断是网吧下属人员时，只显示当前门店的
        User user = Jurisdiction.getSessionUser().getUser();
        pd.put("store_ids", user.getStore_ids());


        String[] scenes = {"CURREN", "MAN", "WEM", "BIRTH", "GRAB"};
        pd.put("scenes", scenes);
        List<PageData> list = (List<PageData>) dao.findForList("CardMapper.selCardList", pd);
		for (int i = 0; i < list.size(); i++) {
			PageData pdd = list.get(i);
            if(pdd.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE")){
				pdd.put("avalid_time", pdd.getString("BEGIN_TIMESTAMP") + "至" + pdd.getString("END_TIMESTAMP"));
                Date begin = Tools.str2Date2(pdd.getString("BEGIN_TIMESTAMP"));
                Date end = Tools.str2Date2(pdd.getString("END_TIMESTAMP"));
                //去除 已过期的卡券
                if (begin.getTime() > new Date().getTime() || new Date().getTime() > end.getTime()) {
                    list.remove(i);
                    i--;
                    continue;
                }
            }else if(pdd.getString("TYPE").equals("DATE_TYPE_FIX_TERM")){
            	String str = "当天";
				String FIXED_BEGIN_TERM = pdd.getString("FIXED_BEGIN_TERM");
				if(!FIXED_BEGIN_TERM.equals("0")){
					str = FIXED_BEGIN_TERM + "天后";
				}
				str += "生效";
				String FIXED_TERM = pdd.getString("FIXED_TERM");
				str += "，" + FIXED_TERM + "天内有效";
				pdd.put("avalid_time", str);
			}

			//卡券所适用的们门店列表
			PageData pdStore = new PageData();
			pdStore.put("CARD_ID", pdd.getString("CARD_ID"));
			List<PageData> sList = storeService.listByCardId(pdStore);
			pdd = getStoresv(sList, pdd);
            list.set(i, pdd);
        }

        return Message.ok().addData("list", list);
    }


    public static PageData getStoresv(List<PageData> sList, PageData pd){
        String stores = "";
        String store_ids = "";
        String stores_v = "";
        String store_ids_v = "";
        String stores_nov = "";
        String store_ids_nov = "";

		for (PageData pdd : sList) {
			stores += pdd.getString("STORE_NAME") + ",";
			store_ids += pdd.getString("STORE_ID") + ",";

			if(StringUtil.isNotEmpty(pdd.get("STATE_V")) && pdd.get("STATE_V").toString().equals("1")){
				store_ids_v += pdd.getString("STORE_ID") + ",";
				stores_v += pdd.getString("STORE_NAME") + ",";
			}else{
				store_ids_nov += pdd.getString("STORE_ID") + ",";
				stores_nov += pdd.getString("STORE_NAME") + ",";
			}
		}
		if(stores.endsWith(",")){
			stores = stores.substring(0, stores.length() - 1);
			store_ids = store_ids.substring(0, store_ids.length() - 1);
		}
        if(stores_v.endsWith(",")){
			stores_v = stores_v.substring(0, stores_v.length() - 1);
			store_ids_v = store_ids_v.substring(0, store_ids_v.length() - 1);
        }
        if(stores_nov.endsWith(",")){
			stores_nov = stores_nov.substring(0, stores_nov.length() - 1);
			store_ids_nov = store_ids_nov.substring(0, store_ids_nov.length() - 1);
        }
        pd.put("stores", stores);//所有门店名称
        pd.put("store_ids", store_ids);//所有门店id
        pd.put("stores_v", stores_v);//加v的门店名称
        pd.put("store_ids_v", store_ids_v);//加v的门店id
        pd.put("stores_nov", stores_nov);//不加v的门店名称
        pd.put("store_ids_nov", store_ids_nov);//不加v的门店id
        return pd;
    }


    @Override
    public Message rushReceived(String cardId, String open_id, String order_id, String cancel_id) throws Exception{

        PageData pdCard = this.findCardAndSceneById(cardId);
        if(pdCard.getString("FAV_TYPE").equals("RUSH")){//抵用券
            PageData pdCardOpen = new PageData();
            pdCardOpen.put("card_id", cardId);//卡券id
            pdCardOpen.put("open_id", open_id);//微信用户id
            pdCardOpen.put("order_id", order_id);//订单id
            pdCardOpen.put("cancel_id", cancel_id);//核销表id
            pdCardOpen.put("card_state", 1);//已领取
            pdCardOpen.put("rec_time", Tools.dateStr(new Date()));//领取时间
            this.editByOrderIdAndCardIdAndOpenId(pdCardOpen);

        }else if(pdCard.getString("FAV_TYPE").equals("TERM")){//满时长券
            PageData pdCardOpen = new PageData();
            pdCardOpen.put("card_state", 1);//已领取
            pdCardOpen.put("cancel_id", cancel_id);//核销表id
            pdCardOpen.put("rec_time", Tools.dateStr(new Date()));//领取时间

            if(StringUtil.isNotEmpty(order_id)){//新的
                pdCardOpen.put("id", order_id);//卡券id
                dao.update("CardMapper.editCard", pdCardOpen);
            }else{//旧的方式
                //先去修改card_open表中的领取状态(通过card_id和open_id)
                pdCardOpen.put("CARD_ID", cardId);//卡券id
                pdCardOpen.put("OPEN_ID", open_id);//微信用户id
                PageData pd2 = (PageData) dao.findForObject("CardMapper.selectCard", pdCardOpen);
                if(pd2 != null){
                    pdCardOpen.put("id", pd2.getString("id"));
                    pdCardOpen.put("card_id", cardId);//卡券id
                    pdCardOpen.put("open_id", open_id);//微信用户id
                    dao.update("CardMapper.editCard", pdCardOpen);
                }
            }

        }

        return Message.ok();
    }
}
