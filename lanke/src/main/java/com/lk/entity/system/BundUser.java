package com.lk.entity.system;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author myq
 * @description 绑定的微信用户
 * @create 2019-05-28 11:08
 */
@Data
public class BundUser {

    //绑定信息
    private String bunduser_id;//主键
    private String user_id;//揽客用户id
    private String carded;//身份证
    private String name;//姓名
    private String phone;//手机号
    private String create_time;//绑定时间


    //顺网数据
    private String cardid;//卡号
    private String member_level;//用户等级
    private String usable_integral;//积分
    private String overage;//总金额
    private String reward;//奖励金额
    private String psd;//密码
    private String is_sw;//是否验证顺网信息 ：1是0否


    //门店信息
    private String store_id;
    private String store_name;

    //微信用户信息
    private String open_id;
    private String imgurl;
    private String neck_name;


    public String getNeck_name() {
        String nickname = "";
        try {
            nickname = URLDecoder.decode(neck_name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            nickname = neck_name;
        }
        return nickname;
    }
}
