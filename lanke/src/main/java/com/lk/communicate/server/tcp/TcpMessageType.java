package com.lk.communicate.server.tcp;

/**
 * 通讯消息type的定义接口
 * @author haven
 *
 */
public interface TcpMessageType {

    String TYPE_LOGIN = "0x00008001";//登录
    String TYPE_HEARTBEAT = "0x00008002";//心跳

}
