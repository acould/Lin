package com.lk.communicate.server.tcp;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.util.Md5Encoder;
import com.lk.communicate.server.util.Tools2;
import com.lk.service.billiCenter.clientConnectLog.ClientConnectLogService;
import com.lk.service.billiCenter.userBoard.UserBoardService;
import com.lk.service.billiCenter.userDown.UserDownService;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.hd.impl.Business;
import com.lk.service.system.client.impl.ClientServiceImpl;
import com.lk.service.system.store.StoreManager;
import com.lk.tcp.DESUtil;
import com.lk.util.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author myq
 * @description tcp 服务端 通讯收发处理
 * @create 2019-04-16 14:47
 */
public class ServerHandler2 extends SimpleChannelInboundHandler<Message> {

    public static final Logger log = LoggerFactory.getLogger(ServerHandler2.class);


    /**
     * 接收到客户端传来的数据时调用该方法，所以的通讯接收业务逻辑都在该方法内
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        //心跳机制
        if (TcpMessageType.TYPE_HEARTBEAT.equals(msg.getType())){
            return;
        }

        String store_id = msg.getBarId();//网吧id
        String type = msg.getType();//消息类型
        String data = msg.getData();//消息内容
        PageData pd = new PageData();

        //握手登录，成功则保存连接缓存
        if(TcpMessageType.TYPE_LOGIN.equals(msg.getType())){
            //加密验证
            if (Md5Encoder.encode(store_id + TcpConfig.handshakeKey).equals(msg.getData())) {
                //获取缓存中的连接（如果已存在该网吧的缓存，则剔除后来的连接）
                ChannelHandlerContext cacheCHC = ChannelCache.channelMap.get(store_id);
                if (null != cacheCHC){
                    return;
                }

                Cache storeVCache = CacheManager.getStoreVCache();
                StoreManager storeService = (StoreManager) ContextLoader.getCurrentWebApplicationContext().getBean("storeService");
                if (storeVCache.getObject(PublicManagerUtil.client_prefix + store_id) == null) {
                    //获取注入的service
                    //判断连接的门店是否开通计费系统（加v）
                    PageData pdStore = new PageData();
                    pd.put("store_id", store_id);
                    pd.put("state", "1");//已加v
                    pdStore = storeService.findByStoreIdAndState(pd);
                    if(pdStore == null){
                        storeVCache.insertObject(PublicManagerUtil.client_prefix + store_id, "0");
                        log.info(store_id + "尚未开通计费系统（加v），拒绝客户端连接");
                        ctx.close();
                        return;
                    }
                    // 过期：到期日期 < 本日日期
                    String EXPIRATION_TIME = pdStore.getString("EXPIRATION_TIME");
                    Date expire = Tools.str2Date(EXPIRATION_TIME, "yyyy-MM-dd");
                    if (expire.getTime() < new Date().getTime()) {
                        storeVCache.insertObject(PublicManagerUtil.client_prefix + store_id, "0");
                        log.info(pdStore.getString("Company_Name")+"已过期，拒绝客户端连接");
                        ctx.close();
                        return;
                    }
                    storeVCache.insertObject(PublicManagerUtil.client_prefix + store_id, "1");
                }else{
                    String status = (String) storeVCache.getObject(PublicManagerUtil.client_prefix + store_id);
                    if(status.equals("0")){
                        ctx.close();
                        return;
                    }
                }



                //缓存中不存在连接，且门店已加v ==》 可保存客户端连接
                ChannelCache.channelMap.put(store_id, ctx);
                //保存客户端连接情况
                PageData pdClient = new PageData();
                pdClient.put("id", StringUtil.get32UUID());
                pdClient.put("store_id", store_id);
                pdClient.put("create_time", Tools.date2Str(new Date()));
                pdClient.put("status", "1");//已连接
                ClientConnectLogService ccls = (ClientConnectLogService) ContextLoader.getCurrentWebApplicationContext().getBean("clientConnectLogServiceImpl");
                ccls.save(pdClient);
                log.info(new Date() + ", ip:" + ctx.channel().remoteAddress().toString() + ", 网吧ID:" + store_id + "的客户端连接成功");


                //设置扫码上机参数
                new Thread(new ClientServiceImpl.setQrParam(store_id)).start();
                return;

            }else{
                //加密验证不通过
                ctx.close();
                log.info("message消息加密验证不通过");
            }
        }

        //其余接口返回消息
        String enStr = DESUtil.decode(MsgUtil.ENCODE_KEY, Base64.decodeBase64(msg.getData()));
        JSONObject json = JSONObject.fromObject(enStr);
        log.info("顺网返回store_id==" + store_id + "，返回类型type==" + msg.getType() + "，返回结果==" + json);

        //将返回消息存储到msgCache中，2分钟后失效
        Message m2 = msg;
        m2.setData(json.toString());
        CacheManager.getMsgCache().insertObject(msg.getFlag().toString(), m2);

        //将接收到的信息放入缓存(之前的写法，没整合完，先留着)
        if(Business.businesses.containsKey(msg.getFlag())){
            Business.businesses.put(msg.getFlag(), enStr);
        }


        if(type.equals("0x8081")){
            //将激活信息放入缓存（激活即已上机）
            log.info("激活新建缓存==="+store_id + "_" + json.getString("card_id"));
            ChannelCache.userUpMap.put(store_id + "_" + json.getString("card_id"), json);
            //顺网返回结果：：{"active_from":"收银端激活","card_id":"09240031","console_ip":"192.168.2.253","deposit_fee":0,"guid":"{76ab4422-2890-496e-b815-32fcfb3baa83}"}
        }

        //处理推送消息（上下机、换机、余额推送、消费通知等）
        if(type.equals("0x8082") || type.equals("0x8083")){
            //推送会员上机或下机
            JSONObject param = new JSONObject();
            param.put("field_value", json.getString("guid"));
            param.put("field_type", "3");

            //查询会员上机或下机
            JSONObject msgCache = new JSONObject();
            Message msgBoard = new Message();
            msgBoard.setBarId(msg.getBarId());
            if(type.equals("0x8082")){
                msgBoard.setType(MsgUtil.MSG_USERBOARD);//查询上机接口
                msgCache.put("param", "saveUserBoard");

                //将上机信息放入缓存
                log.info("上机新建缓存==="+store_id + "_" + json.getString("card_id"));
                ChannelCache.userUpMap.put(store_id + "_" + json.getString("card_id"), json);
            }else if(type.equals("0x8083")){
                msgBoard.setType(MsgUtil.MSG_USERDOWN);//查询下机接口
                msgCache.put("param", "saveUserDown");

                //将上机信息从缓存中移除
                log.info("下机去除缓存==="+store_id + "_" + json.getString("card_id"));
                ChannelCache.userUpMap.remove(store_id + "_" + json.getString("card_id"));
                //顺网返回结果：：{"card_id":"09240031","down_time":"2019-07-29 14:25:28","flag":0,"guid":"{76ab4422-2890-496e-b815-32fcfb3baa83}"}
            }
            msgBoard.setFlag(Tools2.getCenterMsgId2());
            msgBoard.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));
            ctx.writeAndFlush(msgBoard);

            msgCache.put("card_id", json.getString("card_id"));
            MessageCache.msgMap.put(msgBoard.getFlag(), msgCache);

        }else if(type.equals("0x808a")){
            //加减余额变动通知，收银端减钱无推送
            //第三方充值操作不进行推送
            if(!json.getString("opter_type").equals("16")){

                JSONObject param = new JSONObject();
                param.put("card_id", json.getString("card_id"));
                param.put("filter_type", json.getString("opter_type"));

                int begin_time = Integer.parseInt((new Date().getTime() - (int)(0.5*60*1000))/1000 + "");
                int end_time = Integer.parseInt((new Date().getTime() + (int)(1*60*1000))/1000 +"");
                param.put("begin_time", begin_time);
                param.put("end_time", end_time);

                //查询流水
                Message msgMoney = new Message();
                msgMoney.setBarId(msg.getBarId());
                msgMoney.setType(MsgUtil.MSG_USERFLOW);//查询流水接口
                msgMoney.setFlag(Tools2.getCenterMsgId2());
                msgMoney.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));
                ctx.writeAndFlush(msgMoney);

                JSONObject msgCache = new JSONObject();
                msgCache.put("param", "saveRecharge");
                msgCache.put("card_id", json.getString("card_id"));
                MessageCache.msgMap.put(msgMoney.getFlag(), msgCache);
            }

        }else if(type.equals("0x8087")){
            //消费信息通知--通过卡余额支付（购买商品）
            JSONObject param = new JSONObject();
            param.put("card_id", json.getString("card_id"));
            param.put("filter_type", "4");
            int begin_time = Integer.parseInt((new Date().getTime() - (int)(0.5*60*1000))/1000 + "");
            int end_time = Integer.parseInt((new Date().getTime() + (int)(1*60*1000))/1000 +"");
            param.put("begin_time", begin_time);
            param.put("end_time", end_time);

            //查询流水
            Message msgSale = new Message();
            msgSale.setBarId(msg.getBarId());
            msgSale.setType(MsgUtil.MSG_USERFLOW);//查询流水接口
            msgSale.setFlag(Tools2.getCenterMsgId2());
            msgSale.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));
            ctx.writeAndFlush(msgSale);

            JSONObject msgCache = new JSONObject();
            msgCache.put("param", "saveSale");
            msgCache.put("card_id", json.getString("card_id"));
            MessageCache.msgMap.put(msgSale.getFlag(), msgCache);
        }

        if(MessageCache.msgMap.containsKey(msg.getFlag())){
            JSONObject msgJson = MessageCache.msgMap.get(msg.getFlag());

            if(msgJson.getString("param").equals("saveUserBoard")){
                //推送上机模板
                UserBoardService userBoardService = (UserBoardService) ContextLoader.getCurrentWebApplicationContext().getBean("userBoardService");
                try{
                    json.put("card_id", msgJson.getString("card_id"));
                    userBoardService.pushUserBoard(json, msg.getBarId());
                }catch(Exception e){
                    e.printStackTrace();
                }

            }else if(msgJson.getString("param").equals("saveUserDown")){
                //推送下机模板
                UserDownService userDownService = (UserDownService) ContextLoader.getCurrentWebApplicationContext().getBean("userDownService");
                try{
                    json.put("card_id", msgJson.getString("card_id"));
                    userDownService.pushUserDown(json, msg.getBarId());
                }catch(Exception e){
                    e.printStackTrace();
                }

            }else{
                //推送余额变动模板
                UserFlowService userFlowService = (UserFlowService) ContextLoader.getCurrentWebApplicationContext().getBean("userFlowService");
                try{
                    json.put("card_id", msgJson.getString("card_id"));
                    userFlowService.pushMoneyChange(json, msg.getBarId());
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
            MessageCache.msgMap.remove(msg.getFlag());
        }

//        ctx.flush();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        log.error("tcp连接触发一个异常，判断channel是否正常：：" + channel.isActive());

        super.exceptionCaught(ctx, cause);

        Iterator<Map.Entry<String, ChannelHandlerContext>> iterator = ChannelCache.channelMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> entry = iterator.next();
            //判断连接是否存在
            if(entry.getValue().equals(ctx)){
                String store_id = entry.getKey();

                //保存客户端连接情况
                PageData pdClient = new PageData();
                pdClient.put("id", StringUtil.get32UUID());
                pdClient.put("store_id", store_id);
                pdClient.put("create_time", Tools.date2Str(new Date()));
                pdClient.put("status", "3");//异常断开连接
                ClientConnectLogService ccls = (ClientConnectLogService) ContextLoader.getCurrentWebApplicationContext().getBean("clientConnectLogServiceImpl");
                ccls.save(pdClient);
                break;
            }
        }

        log.error("channel通道发生异常，将其关掉");
        ctx.close();
        ChannelCache.channelMap.remove(ctx);

    }

    /**
     * 连接断开时触发的方法
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Collection<ChannelHandlerContext> values = ChannelCache.channelMap.values();
        ctx.close();

        Iterator<Map.Entry<String, ChannelHandlerContext>> iterator = ChannelCache.channelMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> entry = iterator.next();
            //判断连接是否存在
            if(entry.getValue().equals(ctx)){
                log.info("触发了连接断开。。。。。。");
                String store_id = entry.getKey();

                //保存客户端连接情况
                PageData pdClient = new PageData();
                pdClient.put("id", StringUtil.get32UUID());
                pdClient.put("store_id", store_id);
                pdClient.put("create_time", Tools.date2Str(new Date()));
                pdClient.put("status", "2");//断开连接
                ClientConnectLogService ccls = (ClientConnectLogService) ContextLoader.getCurrentWebApplicationContext().getBean("clientConnectLogServiceImpl");
                ccls.save(pdClient);
                break;
            }
        }

        values.remove(ctx);
        //log.info(new Date() + "位于" + ctx.channel().remoteAddress() + "的客户端断开连接");
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                log.info("一段时间内没有数据接收。。。");
                ctx.close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                log.info(" 一段时间内没有数据发送。。。");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                log.info("一段时间内没有数据接收或者发送");
            }
        }
        super.userEventTriggered(ctx, evt);
    }


}
