package com.lk.service.billiCenter.userFlow.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWUserFlow;
import com.lk.service.billiCenter.userFlow.UserFlowHistoryService;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myq
 * @description 流水历史表
 * @create 2019-06-06 16:13
 */
@Service
public class UserFlowHistoryServiceImpl implements UserFlowHistoryService {


    private static final Logger log = LoggerFactory.getLogger(UserFlowHistoryServiceImpl.class);
    private static String mapperName = "UserFlowHistoryMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Autowired
    private UserFlowService userFlowService;



    /**
     * 新增
     * @param pd
     * @throws Exception
     */
    @Override

    public int save(PageData pd) throws Exception {
        return (int) dao.save(mapperName + "save", pd);
    }

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Override
    public int delete(String id) throws Exception {
        return (int) dao.delete(mapperName + "delete", id);
    }

    /**
     * 修改
     *
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public int edit(PageData pd) throws Exception {
        return (int) dao.update(mapperName + "edit", pd);
    }

    /**
     * 根据主键查找id
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PageData findById(String id) throws Exception {
        PageData pd = (PageData) dao.findForObject(mapperName + "findById", id);
        return pd;
    }


    @Override
    public PageData findByStoreIdAndCardIdAndFlowTime(PageData pd) throws Exception{
        return (PageData) dao.findForObject(mapperName + "findByStoreIdAndCardIdAndFlowTime", pd);
    }

    @Override
    public List<PageData> findByStoreIdAndCardId(PageData pd) throws Exception{
        return (List<PageData>) dao.findForList(mapperName + "findByStoreIdAndCardId", pd);
    }

    /**
     * 统计用户金额
     * @param store_id 必填
     * @param card_id 必填 证件号
     * @param flow_types 选填 流水类型 数组
     * @param begin_time 选填 开始时间
     * @param end_time 选填 结束时间
     * @return
     * @throws Exception
     */
    @Override
    public PageData calMoney(String store_id, String card_id, String[] flow_types, int money_type
            , String begin_time, String end_time) throws Exception{
        PageData pd = new PageData();
        pd.put("store_id", store_id);
        pd.put("card_id", card_id);
        pd.put("flow_type", flow_types);
        pd.put("money_type", money_type);
        pd.put("begin_time", begin_time);
        pd.put("end_time", end_time);

        return (PageData) dao.findForObject(mapperName + "calMoney", pd);

    }

    /**
     * 保存到查询到的会员流水数据，保存到user_flow_history
     * @return
     * @throws Exception
     */
    @Override
    public Message2 saveUserFlowHistory(String store_id, String carded, String cardid, int filter_type, int begin_timestamp, int end_timestamp) throws Exception{

        List<SWUserFlow> list = new ArrayList();
        Message2 m2 = userFlowService.getUserFlow(store_id, cardid, filter_type, begin_timestamp, end_timestamp);
        if(m2.getErrcode() == 0){
            list = (List<SWUserFlow>) m2.getData().get("list");
        }


        for (SWUserFlow swuf : list) {
            PageData pdData = new PageData();
            pdData.put("flow_id", StringUtil.get32UUID());
            pdData.put("store_id", store_id);
            pdData.put("card_id", carded);

            pdData.put("flow_type", swuf.getFlow_type());
            pdData.put("flow_desc", swuf.getFlow_desc());
            pdData.put("amount", swuf.getAmount());
            pdData.put("reward", swuf.getReward());

            //消费，上机费， 第三方消费置负
            if(swuf.getFlow_type() == 4 || swuf.getFlow_type() == 2 || swuf.getFlow_type() == 32){
                if(swuf.getAmount() > 0 || swuf.getReward() > 0) {
                    pdData.put("amount", 0 - swuf.getAmount());
                    pdData.put("reward", 0 - swuf.getReward());
                }
            }

            //筛选需要保存的操作类型数据
            if(swuf.getFlow_desc().equals("购买商品")){

            }else if(swuf.getFlow_desc().equals("激活") || swuf.getFlow_desc().equals("取消激活")){
                continue;
            }else if(swuf.getFlow_desc().equals("减积分") || swuf.getFlow_desc().equals("加积分")){
                continue;
            }else if(swuf.getFlow_desc().equals("减奖励")){
                pdData.put("reward", swuf.getAmount());
                pdData.put("amount", 0);
            }else if(swuf.getFlow_desc().equals("退奖励")){
                pdData.put("reward", swuf.getAmount());
                pdData.put("amount", 0);
            }
            if("揽客优惠券".equals(swuf.getMemo())){
                pdData.put("reward", swuf.getReward());
                pdData.put("amount", 0);
            }else if("揽客充值".equals(swuf.getMemo())){
                pdData.put("amount", swuf.getAmount());
                pdData.put("reward", swuf.getReward());
            }



            String flow_time = Tools.date2Str(Tools.str2Date(swuf.getFlow_time().replaceAll("/", "-")));
            pdData.put("flow_time", flow_time);
            pdData.put("flow_timestamp", swuf.getFlow_timestamp());
            pdData.put("pay_type", swuf.getPay_type());
            pdData.put("order_id", swuf.getOrder_id());
            pdData.put("order_from", swuf.getOrder_from());
            pdData.put("memo", swuf.getMemo());
            if(swuf.getFlow_desc().equals("加本金") || swuf.getFlow_desc().equals("加奖励")  || swuf.getFlow_desc().equals("揽客充值")){
                PageData pdsc = new PageData();
                pdsc.put("flow_time", flow_time);
                pdsc.put("card_id", carded);
                pdsc.put("store_id", store_id);
                String flow_id = this.findByStoreIdAndCardIdAndFlowTime(pdsc).getString("flow_id");
                if(StringUtil.isNotEmpty(flow_id)){
                    if(swuf.getFlow_desc().equals("加本金")){
                        pdsc.put("amount", swuf.getAmount());
                    }else if(swuf.getFlow_desc().equals("加奖励")){
                        pdsc.put("reward", swuf.getAmount());
                    }
                    pdsc.put("flow_id", flow_id);
                    this.edit(pdsc);//将两笔订单整合为一笔
                }else{
                    //没有记录
                    if(swuf.getFlow_desc().equals("加奖励")){
                        pdData.put("reward", swuf.getAmount());
                    }
                    this.save(pdData);
                }
            }else{
                this.save(pdData);
            }
        }

        return Message2.ok();
    }


}
