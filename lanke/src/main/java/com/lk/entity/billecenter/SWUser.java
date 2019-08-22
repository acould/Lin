package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 顺网——用户信息
 * @create 2019-04-16 16:22
 */
@Data
public class SWUser {

    private String card_id;//卡号
    private String id_card;//证件号
    private Integer id_type;//证件类型
    private String user_name;//用户名
    private String phone_number;//手机号
    private String member_level;//会员级别
    private Integer usable_integral;//可用的积分
    private Double overage;//卡余额，包含奖励金额
    private Double reward;//奖励金额

    private String user_password;//用户密码
    private Integer is_create_bar;
}
