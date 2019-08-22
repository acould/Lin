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
public class Message2 {

    private Integer errcode;
    private String errmsg;
    private Map<String,Object> data = new HashMap<>();

    /**
     * 直接返回成功
     * @return
     */
    public static Message2 ok(){
        Message2 m = new Message2();
        m.setErrcode(0);
        m.setErrmsg("ok");
        return m;
    }

    /**
     * 成功，并返回消息提示
     * @return
     */
    public static Message2 success(String errmsg) {
        Message2 m = new Message2();
        m.setErrcode(0);
        m.setErrmsg(errmsg);
        return m;
    }

    /**
     * 成功，并返回消息提示和数据
     * @param data
     * @return
     */
    public static Message2 success(String errmsg, Map<String,Object> data) {
        Message2 m = new Message2();
        m.setErrcode(0);
        m.setErrmsg(errmsg);
        m.setData(data);
        return m;
    }


    /**
     * 失败，返回错误提示
     * @param errmsg
     * @return
     */
    public static Message2 error(String errmsg) {
        Message2 m = new Message2();
        m.setErrcode(-1);
        m.setErrmsg(errmsg);
        return m;
    }

    /**
     * 失败，返回错误码和错误提示
     * @param errcode
     * @param errmsg
     * @return
     */
    public static Message2 error(Integer errcode, String errmsg) {
        Message2 m = new Message2();
        m.setErrcode(errcode);
        m.setErrmsg(errmsg);
        return m;
    }


    /**
     * 给返回结果添加数据
     * @param key
     * @param object
     * @return
     */
    public Message2 addData(String key, Object object){
        this.data.put(key, object);
        return this;
    }

    /**
     * 给返回结果设置返回数据
     * @param data
     * @return
     */
    public Message2 setMata(Map<String, Object> data) {
        this.data = data;
        return this;
    }


}

