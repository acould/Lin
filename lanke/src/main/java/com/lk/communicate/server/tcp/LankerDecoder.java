package com.lk.communicate.server.tcp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lk.communicate.server.model.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class LankerDecoder extends ByteToMessageDecoder {
    public static final Logger log = LoggerFactory.getLogger(LankerDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < TcpConfig.headLength) {
            return;
        }

        in.markReaderIndex(); //我们标记一下当前的readIndex的位置

        int dataLength = in.readInt();

        if (in.readableBytes() < dataLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            in.resetReaderIndex();
            return;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Message message = JSON.parseObject(new String(data, TcpConfig.charsetName), Message.class);
        out.add(message);
    }

}
