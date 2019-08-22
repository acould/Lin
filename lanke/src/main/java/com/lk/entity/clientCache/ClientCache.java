package com.lk.entity.clientCache;

import java.util.Date;
import io.netty.channel.ChannelHandlerContext;

public class ClientCache {

	private Date connectTime; //连接时间
	private ChannelHandlerContext channelHandlerContext;
	public Date getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}
	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}
	public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
		this.channelHandlerContext = channelHandlerContext;
	}
	
	
}
