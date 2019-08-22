package com.lk.wechat.response;

/**
 * 语音消息
 * 
 */
public class VoiceMessage extends RespBaseMessage {
	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
