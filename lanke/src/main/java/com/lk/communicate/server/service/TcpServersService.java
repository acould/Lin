package com.lk.communicate.server.service;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

import javax.annotation.PostConstruct;

import com.lk.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.tcp.TcpServer;
import com.lk.communicate.server.util.Tools2;
import com.lk.entity.clientCache.ClientCache;

import io.netty.channel.ChannelHandlerContext;

@Service
public class TcpServersService {
    public static final Logger log = LoggerFactory.getLogger(TcpServersService.class);

    private Integer port = 8027;//服务器tcp端口

    @PostConstruct
    public void init() {
        new Thread(new TcpServersThread()).start();
        new Thread(new ThreadTest()).start();

    }

    class TcpServersThread implements Runnable {

        @Override
        public void run() {

            try {
                Thread.currentThread().sleep(10000);//毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new TcpServer(port).start();
        }

    }

    class ThreadTest implements Runnable {

        @Override
        public void run() {
        	while (true) {
            	Iterator<Entry<String, ChannelHandlerContext>> iterator = ChannelCache.channelMap.entrySet().iterator();
            	String storeList = "";
            	while (iterator.hasNext()) {
            		Entry<String, ChannelHandlerContext> entry = iterator.next();
                    //判断连接是否存在
                    if(!entry.getValue().channel().isActive()){
                        ChannelCache.channelMap.values().remove(entry.getValue());
                    }else{
            		    storeList += entry.getKey()+",";
                    }
            	}
            	log.info("ChannelCache.channelMap.size : " + ChannelCache.channelMap.size() + ",storeList : "+ storeList);

                log.info("check ===ChannelCache.channelMap.size : " + ChannelCache.channelMap.size() + "");
                /*Iterator<Entry<String, ChannelHandlerContext>> iterator = ChannelCache.channelMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, ChannelHandlerContext> entry = iterator.next();
                    log.info("key:" + entry.getKey() + ", value:" + entry.getValue());

                    //ChannelCache.channelMap.get("3e876cfd70dc496982e8de7c5339139c") 通过barId获取具体需要通讯的客户端连接
                    //测试发送消息
                    Message message = new Message();
                    message.setType("0x00008005");
                    message.setData(
                            Tools2.desEncoder("898730ED", "{\"field_value\":130722199409240031,\"field_type\":0}"));
                    message.setFlag(Math.abs(new Random().nextInt()));

                    entry.getValue().writeAndFlush(message);
                }*/
//                Iterator<Entry<String, PageData>> iterator2 = ChannelCache.userUpMap.entrySet().iterator();
//                PageData pdBoard = null;
//                String key = "";
//                log.info("ChannelCache.userUpMap.size : " + ChannelCache.userUpMap.size());
//                while (iterator2.hasNext()) {
//                    log.info("iterator2======");
//                    Entry<String, PageData> entry = iterator2.next();
//                    key += entry.getKey()+",";
//                    pdBoard = ChannelCache.userUpMap.get(entry.getKey());
//                    log.info("pdBoard : "+ pdBoard.toString());
//                }
//                log.info("key : "+ key);

                try {
                    Thread.sleep(1000 * 20);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

}
