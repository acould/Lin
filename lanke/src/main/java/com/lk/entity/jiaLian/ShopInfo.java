package com.lk.entity.jiaLian;

import lombok.Data;

import java.util.List;

/**
 * @author myq
 * @description 嘉联商户向支付平台进件商户信息
 * @create 2019-04-26 14:34
 */
@Data
public class ShopInfo {

    //新增时shop_no不填
    //修改时user_name不填

    private String shop_no;//商户号
    private String shop_merchant_no;//代理商编号
    private String call_back_url;//消息通知地址（支付渠道开通后通过该地址告知，接入方提供，通知数据格式祥见接口：4.消息通知）
    private String net_type;//入网类型  1：营业执照入网2：租赁商户入网3：小微商户入网

    private String user_name;//登录名，用于登录系统
    private String merch_name;//商户名称
    private String shop_contact_name;//商户联系人真实姓名

    private String cert_no;//身份证号码
    private String cert_start_date;//身份证有效起始日期，格式：年.月.日
    private String cert_expire_date;//身份证有效截止日期，格式：年.月.日

    private String customer_service_phone;//客服电话，营业执照入网时必输
    private String mobile;//手机号
    private String email;//电子邮箱(选填)
    private String license_no;//营业执照号码，营业执照入网时必输(选填)
    private String det_address;//详细地址
    private String prov_code;//省行政编码
    private String city_code;//市行政编码
    private String area_code;//区行政编码
    private String account_type;//结算账户类型，0：对私，1：对公
    private String account_no;//结算账号
    private String account_name;//结算账户户名
    private String union_bank_no;//联行号，对公必输(选填)
    private String union_bank_name;//分支行名称
    private String rate;//费率(必须不小于代理商费率)
    private List<ShopPic> picList;//照片列表


}
