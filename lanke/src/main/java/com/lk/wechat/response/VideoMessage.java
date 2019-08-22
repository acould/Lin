package com.lk.wechat.response;

/**
 * 视频消息
 * 
 */
public class VideoMessage extends RespBaseMessage {
	// 视频
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
