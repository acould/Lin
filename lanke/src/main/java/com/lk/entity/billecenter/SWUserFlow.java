package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 顺网——用户流水信息
 * @create 2019-04-16 16:45
 */
@Data
public class SWUserFlow {

    private Integer flow_type;//流水类型
    private String flow_desc;//流水描述
    private String flow_time;//流水时间
    private String flow_timestamp;//流水时间戳
    private Integer pay_type;//支付类型
    private Double amount;//加钱或减钱的金额
    private Double reward;//加钱或减钱的金额中奖励部分
    private String order_id;//支付订单号
    private String order_from;//支付来源
    private String memo;//备注

}
