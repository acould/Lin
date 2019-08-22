package com.lk.wechat.request;

import java.util.Date;

import com.lk.wechat.util.MessageUtil;

import com.lk.wechat.response.RespBaseMessage;
import com.lk.wechat.response.TextMessage;


/**
 * 音频消息
 * 
 */
public class VoiceMessage extends BaseMessage {
	public static VoiceMessage getInstance(String mediaId, String format,String recognition) {
		return new VoiceMessage(mediaId, format,recognition);
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
		textMessage.setContent("您发送的是音频消息！"+recognition);
		return textMessage;
	}

	public VoiceMessage(String mediaId, String format,String recognition) {
		this.mediaId = mediaId;
		this.format = format;
		this.recognition=recognition;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	// 媒体ID
	private String mediaId;
	// 语音格式
	private String format;
	// 识别结果
	private String recognition;
}
