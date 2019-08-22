package com.lk.communicate.server.model;

/**
 * @author myq
 * @description tcp 通讯 消息体
 * @create 2019-04-16 14:48
 */
public class MessageExample extends Message {

    private String result;//返回内容，顺网返回json字符串

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MessageExample{" +
                "result='" + result + '\'' +
                '}';
    }
}
