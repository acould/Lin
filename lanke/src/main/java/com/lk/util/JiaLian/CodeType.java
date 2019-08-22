package com.lk.util.JiaLian;


/**
 * @author myq
 * @description
 * @create 2019-04-26 16:47
 */
public enum CodeType {

    ER01("ER01", "订单不存在"),
    ER02("ER02", "订单已已被撤销"),
    ER03("ER03", "订单已超过90天，无法完成退款"),
    ER04("ER04", "原始支付通道异常（因风控或其他原因，通道被暂停使用）"),
    ER05("ER05", "无法创建退款订单"),
    ER06("ER06", "请求退款失败（申请发生异常）"),
    ER07("ER07", "退款失败（退款结果标注退款失败）")
    ;

    private String code;
    private String result;

    CodeType(String code, String result){
        this.code = code;
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
