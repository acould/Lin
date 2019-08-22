package com.lk.service.billiCenter.base.impl;

import com.lk.cache.CacheManager;
import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.entity.Message2;
import com.lk.service.billiCenter.base.SWBaseService;
import com.lk.util.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author myq
 * @description
 * @create 2019-04-16 17:18
 */
@Service
public class SWBaseServiceImpl implements SWBaseService{

    private static Logger logger = LoggerFactory.getLogger(SWBaseService.class);

    @Override
    public Message2 sendToSW(String store_id, Object obj, String msg_type) throws Exception {
        //判断客户端连接是否存在
        if (!ChannelCache.channelMap.containsKey(store_id)) {
            return Message2.error("揽客客户端已断开连接，请先连接").addData("err_type", "lanke_client_stop_err");
        }

        //封装Message
        Message msg = new Message();
        msg.setBarId(store_id);
        msg.setType(msg_type);
        msg.setFlag(Tools2.getCenterMsgId2());
        msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY, JSONObject.fromObject(obj).toString()));

        logger.info("顺网接口——发送消息store_id==" + store_id +"，发送消息param==" + JSONObject.fromObject(obj).toString());
        //发送tcp通讯
        ChannelCache.channelMap.get(store_id).writeAndFlush(msg);

        //将flag放入缓存，提供给返回消息存储
        CacheManager.getMsgCache().insertObject(msg.getFlag().toString(), "");

        return Message2.ok().addData("msg_flag", msg.getFlag().toString());
    }

    @Override
    public Message2 getMsgFromCache(String msg_flag) throws Exception{

        Object obj = CacheManager.getMsgCache().getObject(msg_flag);
        while (obj.toString().equals("")){
            Thread.sleep(MsgUtil.MIN_TIME);
            obj = CacheManager.getMsgCache().getObject(msg_flag);
            if(obj == null){
                //缓存不存在，需要重新请求
                return Message2.error("查找不到数据").addData("err_type", "cache_is_null");
            }
        }
        Message msg = (Message) obj;

        JSONObject data = JSONObject.fromObject(msg.getData());
        if(!data.getJSONObject("head").getString("errcode").equals("0")){
            return Message2.error(Integer.parseInt(data.getJSONObject("head").getString("errcode")), data.getJSONObject("head").getString("errmsg"));
        }

        JSONArray arr = new JSONArray();
        if(data.containsKey("body")) {
            if (data.get("body") instanceof JSONObject) {
                arr.add(data.getJSONObject("body"));
            }else{
                arr = data.getJSONArray("body");
            }
        }
        return Message2.ok().addData("arr",arr);
    }


}
