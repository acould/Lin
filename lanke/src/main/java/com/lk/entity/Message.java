package com.lk.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author myq
 * @description 返回码类
 * @create 2019-01-07 11:44
 */
@Data
public class Message {

    private Integer errcode;
    private String errmsg;
    private Map<String,Object> data = new HashMap<>();


    /**
     * 直接返回成功
     * @return
     */
    public static Message ok(){
        Message m = new Message();
        m.setErrcode(0);
        m.setErrmsg("ok");
        return m;
    }

    /**
     * 直接返回成功
     * @return
     */
    public static Message ok(String errmsg){
        Message m = new Message();
        m.setErrcode(0);
        m.setErrmsg(errmsg);
        return m;
    }

    /**
     * 成功提示
     * @return
     */
    public static Message success(){
        Message resultBean = new Message();
        resultBean.setErrcode(0);
        resultBean.setErrmsg("success");
        return resultBean;
    }

    /**
     * 成功，并返回数据
     * @return
     */
    public static Message success(Integer errcode, String errmsg) {
        Message resultBean = new Message();
        resultBean.setErrcode(errcode);
        resultBean.setErrmsg(errmsg);
        return resultBean;
    }
    public Message addData(String key, Object object){
        this.data.put(key, object);
        return this;
    }

    public Message setMata(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    /**
     * 成功，并返回数据
     *
     * @param data
     * @return
     */
    public static Message success(Integer errcode, String errmsg, Map<String,Object> data) {
        Message resultBean = new Message();
        resultBean.setErrcode(errcode);
        resultBean.setErrmsg(errmsg);
        resultBean.setData(data);
        return resultBean;
    }

    /**
     * 失败，返回错误提示
     * @param errmsg
     * @return
     */
    public static Message error(String errmsg) {
        Message m = new Message();
        m.setErrcode(-1);
        m.setErrmsg(errmsg);
        return m;
    }

    /**
     * 错误提示
     * @param errcode
     * @param errmsg
     * @return
     */
    public static Message error(Integer errcode, String errmsg) {
        Message resultBean = new Message();
        resultBean.setErrcode(errcode);
        resultBean.setErrmsg(errmsg);
        return resultBean;
    }

}
