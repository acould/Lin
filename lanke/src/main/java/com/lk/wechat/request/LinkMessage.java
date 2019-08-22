package com.lk.wechat.request;

import java.util.Date;

import com.lk.wechat.util.MessageUtil;

import com.lk.wechat.response.RespBaseMessage;
import com.lk.wechat.response.TextMessage;

/**
 * 链接消息
 * 
 */
public class LinkMessage extends BaseMessage {
	public static LinkMessage getInstance(String title, String description,
			String url) {
		return new LinkMessage(title, description, url);
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
		textMessage.setContent("您发送的是链接消息！");
		return textMessage;
	}

	public LinkMessage(String title, String description, String url) {
		this.title = title;
		this.description = description;
		this.url = url;
	}

	// 消息标题
	private String title;
	// 消息描述
	private String description;
	// 消息链接
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
