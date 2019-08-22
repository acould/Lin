package com.lk.communicate.server.model;

/**
 * tcp通讯模型
 * @author haven
 *
 */
public class Message {

    private String barId;//网吧ID

    private String type;//消息类型

    private Integer flag;//消息唯一标识符

    private String data;//消息内容，json字符串

    public String getBarId() {
        return barId;
    }

    public void setBarId(String barId) {
        this.barId = barId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
