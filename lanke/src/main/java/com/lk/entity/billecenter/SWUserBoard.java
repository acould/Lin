package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 顺网——用户上机信息
 * @create 2019-04-16 16:35
 */
@Data
public class SWUserBoard {


    private String computer_name;//电脑名称
    private String computer_ip;//ip
    private String computer_mac;//mac
    private String card_id;//卡号
    private String id_card;//证件号
    private Integer id_type;//证件类型
    private String user_name;//用户名
    private String user_level;//用户级别
    private Double remain_fee;//剩余金额
    private Double remain_reward;//剩余奖励
    private Double consume_fee;//消费金额
    private Double rate_fee;//每小时费率
    private Integer remain_min;//剩余分钟
    private String guid;//上机唯一标识
    private String up_time;//上机时间
}
