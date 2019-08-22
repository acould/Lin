package com.lk.wechat.request;

import java.util.Date;

import com.lk.wechat.util.MessageUtil;

import com.lk.wechat.response.RespBaseMessage;
import com.lk.wechat.response.TextMessage;

/**
 * 图片消息
 * 
 */
public class ImageMessage extends BaseMessage {
	public static ImageMessage getInstance(String picUrl) {
		return new ImageMessage(picUrl);
	}

	@Override
	public RespBaseMessage processMessage() {
		// 回复文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent("您发送的是图片消息！");
		return textMessage;
	}

	public ImageMessage(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	// 图片链接
	private String picUrl;

}
