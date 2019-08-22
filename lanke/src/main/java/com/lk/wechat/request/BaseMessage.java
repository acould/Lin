package com.lk.wechat.request;

import javax.servlet.http.HttpServletRequest;

import com.lk.wechat.response.RespBaseMessage;


/**
 * 消息基类（普通用户 -> 公众帐号）
 * 
 */
public abstract class BaseMessage {
	public abstract RespBaseMessage processMessage();

	// 开发者微信号
	protected String toUserName;
	// 发送方帐号（一个OpenID）
	protected String fromUserName;
	// 消息创建时间 （整型）
	protected long createTime;
	// 消息类型（text/image/location/link）
	protected String msgType;
	// 消息id，64位整型
	protected long msgId;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

}
