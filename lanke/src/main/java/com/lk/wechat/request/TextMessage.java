package com.lk.wechat.request;

import com.lk.wechat.decoder.TextMsgDecoder;

import com.lk.wechat.util.BeanUtils;

/**
 * 文本消息
 * 
 */
public abstract class TextMessage extends BaseMessage {
	public static TextMessage getInstance(String content) {
		TextMsgDecoder decoder = BeanUtils.getBean("textMsgDecoder",
				TextMsgDecoder.class);
		return decoder.decode(content);
	}

	// 消息内容
	protected String content;

	public TextMessage(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}