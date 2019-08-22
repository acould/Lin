package com.lk.wechat.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lk.wechat.util.MessageUtil;

import com.lk.wechat.response.Article;
import com.lk.wechat.response.NewsMessage;
import com.lk.wechat.response.RespBaseMessage;


/**
 * 地理位置消息
 * 
 */
public class LocationMessage extends BaseMessage {
	public static LocationMessage getInstance(String locationX,
			String locationY, String scale, String label) {
		return new LocationMessage(locationX, locationY, scale, label);
	}

	@Override
	public RespBaseMessage processMessage() {
		// 回复文本消息
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);

		List<Article> articles = new ArrayList<Article>();
		Article article = new Article();
		article.setTitle("欢迎访问展鸿科技微信平台");
		article.setDescription("description1");
		article.setPicUrl("http://" + MessageUtil.WEB_HOST
				+ MessageUtil.WEB_ROOT + "/wxImages/PubMarket.jpg");
		article.setUrl("http://" + MessageUtil.WEB_HOST + MessageUtil.WEB_ROOT
				+ "/produce.htm");
		articles.add(article);
		
		article = new Article();
		article.setTitle("周边网点查询");
		article.setDescription("description2");
		article.setPicUrl("http://" + MessageUtil.WEB_HOST
				+ MessageUtil.WEB_ROOT + "/wxImages/yzcx.jpg");
		article.setUrl("http://" + MessageUtil.WEB_HOST + MessageUtil.WEB_ROOT
				+ "/branchDetail.htm");
		articles.add(article);
		
		article = new Article();
		article.setTitle("周边ATM");
		article.setDescription("description3");
		article.setPicUrl("http://" + MessageUtil.WEB_HOST
				+ MessageUtil.WEB_ROOT + "/wxImages/yzcx_atm.jpg");
		article.setUrl("url3");
		articles.add(article);

		newsMessage.setArticles(articles);
		newsMessage.setArticleCount(articles.size());
		
		System.out.println(locationX+"-"+locationY+"-"+scale+"-"+label);
		
		return newsMessage;
	}

	public LocationMessage(String locationX, String locationY, String scale,
			String label) {
		this.locationX = locationX;
		this.locationY = locationY;
		this.scale = scale;
		this.label = label;
	}

	// 地理位置维度
	private String locationX;
	// 地理位置经度
	private String locationY;
	// 地图缩放大小
	private String scale;
	// 地理位置信息
	private String label;

	public String getLocationX() {
		return locationX;
	}

	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}

	public String getLocationY() {
		return locationY;
	}

	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
