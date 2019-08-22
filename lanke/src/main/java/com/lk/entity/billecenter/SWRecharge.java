package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 顺网——会员充值
 * @create 2019-04-16 18:04
 */
@Data
public class SWRecharge {

    private String card_id;//卡号
    private String order_id;//订单号
    private String pay_from;//支付来源
    private String memo;//备注（选填）

    private Double amount;//充值金额
    private Double reward;//充值第三方奖励金额，默认为0（选填）
    private Integer allow_reward;//是否按万象奖励规则生成奖励;0：不生成奖励，amount增加本金金额，reward增加奖励金额；1：奖励；默认为1

}
