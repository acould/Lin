package com.lk.service.system.task;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lk.service.billiCenter.userFlow.UserFlowHistoryService;
import com.lk.util.StringUtil;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.controller.base.BaseController;
import com.lk.service.billiCenter.memberUser.MemberUserService;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.store.impl.StoreService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.PageData;
import com.lk.util.Tools;
import com.lk.wechat.util.WechatCardUtil;

@Service("TaskService")
public class TaskService extends BaseController implements taskserviceimpl {
	@Resource(name = "cardService")
	private CardManager cardService;
	@Resource(name = "terraceService")
	private TerraceManager terraceService;
	@Resource(name = "bunduserService")
	private BundUserManager bunduserService;
	@Resource(name = "userFlowService")
	private UserFlowService userFlowService;
    @Resource(name = "autoReplyService")
    private AutoReplyService autoReplyService;
	
	@Resource(name = "memberUserService")
	private MemberUserService memberUserService;
	@Resource(name = "storeService")
	private StoreManager storeService;

    @Autowired
    private UserFlowHistoryService userFlowHistoryService;
	
	public boolean weat() {
		return false;
	}

	public void mission() throws Exception {
		PageData pd = new PageData();
		pd.put("AUDIT_STATE", "2");
		List<PageData> varList = cardService.findByState(pd);
		if (varList.size() > 0) {
			for (PageData pageData : varList) {
				if (pageData.getString("AUDIT_STATE").equals("2")) {
					//获取公众号的凭证
			        String AUTHORIZER_ACCESS_TOKEN = autoReplyService.getAuthToken(pageData.getString("INTENET_ID"));

					PageData pdpd = new PageData();
					pdpd.put("CARD_ID", pageData.getString("CARD_ID"));
					// 调用查询卡券详情接口
					JSONObject resultJSON = WechatCardUtil.getCardParticulars(
							AUTHORIZER_ACCESS_TOKEN, pageData.getString("CARD_ID"));
					String status = resultJSON.getJSONObject("card").getJSONObject("gift").getJSONObject("base_info")
							.getString("status");
					if (status.equals("CARD_STATUS_NOT_VERIFY")) {
						logBefore(logger, pageData.getString("BRAND_NAME") + ":" + pageData.getString("TITLE") + "待审核");
					} else if (status.equals("CARD_STATUS_VERIFY_FAIL")) {
						pdpd.put("AUDIT_STATE", "3");// 状态3为审核失败
						cardService.edit(pdpd);
						logBefore(logger,
								pageData.getString("BRAND_NAME") + ":" + pageData.getString("TITLE") + "审核失败");
					} else if (status.equals("CARD_STATUS_VERIFY_OK")) {
						pdpd.put("AUDIT_STATE", "1");// 状态1为正常使用
						cardService.edit(pdpd);
						logBefore(logger,
								pageData.getString("BRAND_NAME") + ":" + pageData.getString("TITLE") + "通过审核");
					} else if (status.equals("CARD_STATUS_DELETE")) {
						logBefore(logger,
								pageData.getString("BRAND_NAME") + ":" + pageData.getString("TITLE") + "卡券被商户删除");
					} else if (status.equals("CARD_STATUS_DISPATCH")) {
						logBefore(logger,
								pageData.getString("BRAND_NAME") + ":" + pageData.getString("TITLE") + "在公众平台投放过的卡券");
					}
				}
			}
		}
		logBefore(logger, "无需要审核的卡券");
	}


	
	/**
	 * 定时更新用户流水
	 * @throws Exception 
	 */
	public void addFlow() throws Exception {

        PageData pd = new PageData();

        pd.put("state", "1");//已加v状态
        List<PageData> storeList = storeService.listStoreV(pd); //查询所有加v门店
        String store_id="";
        int begin_time = Integer.parseInt((Tools.sAddDays(new Date(), -365).getTime() / 1000) + "");//一年前的时间
        int end_time = Integer.parseInt((new Date().getTime() / 1000) + "");//现在时间
        for(PageData pdStore : storeList){
            store_id = pdStore.getString("store_id");

            List<PageData> bindUserList = bunduserService.listByStoreId(store_id);
            String cardid = "";
            for(PageData  pdUser: bindUserList){
                //优先通过卡号查，再根据身份证 0为卡号；1位证件号
                cardid = StringUtil.isNotEmpty(pdUser.getString("CARDID")) ? pdUser.getString("CARDID") : pdUser.getString("CARDED");
                String carded = pdUser.getString("CARDED");//身份证


                //检查流水表中是否有该会员的流水数据，没有的话查一年的，有的话不查了
                pd.put("store_id", store_id);
                pd.put("card_id", carded);
                List<PageData> flowList = userFlowHistoryService.findByStoreIdAndCardId(pd); //通过门店id和会员卡号查询门店信息
                if(flowList.size() > 0){
                    String flow_time = flowList.get(0).getString("flow_time");

                    begin_time = Integer.parseInt((Tools.str2Date(flow_time).getTime() / 1000) + "") + 1; //比上一条流水多一秒时间开始查
                }

                userFlowHistoryService.saveUserFlowHistory(store_id, carded, cardid, 0, begin_time, end_time);//查询所有流水
            }
        }


		
	}
}
