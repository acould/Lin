package com.lk.communicate.server.tcp;

import com.lk.util.PageData;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.stereotype.Service;

import com.lk.entity.clientCache.ClientCache;


/**
 * 客户端连接缓存类，用于存放连接上来的连接实例
 * @author haven
 *
 */
@Service
public class ChannelCache {

    //tcp通道map
    public static ConcurrentHashMap<String, ChannelHandlerContext> channelMap = new ConcurrentHashMap<String, ChannelHandlerContext>();

    //推送用户上机的缓存信息
    public static HashMap<String, JSONObject> userUpMap = new ManagedMap<String, JSONObject>();


}
