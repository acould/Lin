package com.lk.communicate.server.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lk.communicate.server.model.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * lanker通讯格式编码器
 * @author haven
 *
 */
public class LankerEncoder extends MessageToByteEncoder<Message> {

    public static final Logger log = LoggerFactory.getLogger(LankerEncoder.class);

    /**
     * 编码实现类，用于拦截链中自动进行编码，把message转换成byte，创建4个字节的头信息
     * 
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        String data = JSON.toJSONString(msg);
        out.writeInt(data.length());
        out.writeBytes(data.getBytes(TcpConfig.charsetName));

    }

}
