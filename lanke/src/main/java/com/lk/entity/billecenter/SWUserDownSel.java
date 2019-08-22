package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 顺网——用户下机信息
 * @create 2019-04-16 16:40
 */
@Data
public class SWUserDownSel {

    private String computer_name;//电脑名称
    private String card_id;//卡号
    private String member_level;//会员级别
    private String id_card;//证件号
    private Integer id_type;//证件类型
    private String user_name;//用户名
    private Integer pay_type;//支付方式
    private Double card_remain;//卡余额
    private Double consume_fee;//消费总计
    private Integer up_duration;//上机总分钟数
    private String up_time;//上机时间
    private String down_time;//下机时间
    private String guid;//上机唯一标识

}
