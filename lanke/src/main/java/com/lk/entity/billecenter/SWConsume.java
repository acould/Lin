package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 顺网——会员减余额
 * @create 2019-04-16 18:08
 */
@Data
public class SWConsume {

    private String card_id;//卡号
    private Double principal;//扣本金
    private Double reward;//扣奖励
    private String order_id;//支付订单号（选填）
    private String pay_from;//支付来源
    private String memo;//备注（选填）

}
