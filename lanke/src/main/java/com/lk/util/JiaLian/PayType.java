package com.lk.util.JiaLian;

/**
 * @author myq
 * @description
 * @create 2019-04-26 16:53
 */
public enum PayType {

    P_1001("1001", "BTC微信支付"),
    P_1002("1002", "BTC支付宝支付"),
    P_1003("1003", "CTB微信支付"),
    P_1004("1004", "CTB支付宝支付"),
    P_1008("1008", "微信公众号支付"),
    P_1014("1014", "支付宝服务窗"),
    P_1016("1016", "云闪付"),
    ;


    private String type;
    private String info;

    PayType(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
