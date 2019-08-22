package com.lk.wechat.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lk.wechat.util.MessageUtil;

import com.lk.wechat.response.Article;
import com.lk.wechat.response.NewsMessage;
import com.lk.wechat.response.RespBaseMessage;


public class DefaultTextMessage extends TextMessage {
    public DefaultTextMessage(String content) {
        super(content);
    }

    @Override
    public RespBaseMessage processMessage() {
        com.lk.wechat.response.TextMessage textMessage = new com.lk.wechat.response.TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);
        if ("1".equals(content)) {
            textMessage.setContent("人民币存款年利率（%）\n 发布日期：2012-07-06\n 一、城乡居民及单位存款\n （一）活期：0.35\n （二）定期：\n 1.整存整取：\n   三个月：2.85\n   半年：3.05\n   一年：3.25\n   二年：3.75\n   三年：4.25\n   五年：4.75\n 2.零存整取、整存零取、存本取息\n   一年：2.85\n   三年：2.90\n   五年：3.00\n 3.定活两便：按一年以内定期整存整取同档次利率打6折执行\n 二、协定存款：1.15\n 三、通知存款\n   一天：0.80\n   七天：1.35\n ");
            return textMessage;
        } else if ("2".equals(content)) {
            textMessage.setContent("人民币贷款年利率（%）\n 发布日期：2012-07-06\n 一、短期贷款\n   六个月（含）：5.60\n   六个月至一年（含）：6.00\n 二、中长期贷款\n   一至三年（含）：6.15\n   三至五年（含）：6.40\n   五年以上：6.55");
            return textMessage;
        } else if ("3".equals(content)) {
            textMessage.setContent("外汇买卖参考牌价\n 发布日期：2014-05-10\n 更新时间：15:09:17\n 欧元美元\n 买入价：1.3699\n 卖出价：1.3819\n 美元日元\n 买入价：101.5000\n 卖出价：102.2400\n 英镑美元\n 买入价：1.6780\n 卖出价：1.6924\n 澳大利亚元美元\n 买入价：0.9315\n 卖出价：0.9409\n 美元瑞士法郎\n 买入价：0.8823\n 卖出价：0.8905\n 美元加拿大元\n 买入价：1.0856\n 卖出价：1.0946\n 美元港币\n 买入价：7.7168\n 卖出价：7.7866\n 美元新加坡元\n 买入价：1.2432\n 卖出价：1.2544\n");
            return textMessage;
        } else {
            List<Article> articleList = new ArrayList<Article>();
            Article article = new Article();
            article.setTitle("欢迎访问展鸿科技微信平台");
            article.setDescription("您发送的是文本信息");
            article.setPicUrl("http://" + MessageUtil.WEB_HOST + MessageUtil.WEB_ROOT + "/wxImages/xinfo.png");
            article.setUrl("http://www.pshiwan.com/");
            articleList.add(article);

            // 创建图文消息
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            newsMessage.setFuncFlag(0);

            // 设置图文消息个数
            newsMessage.setArticleCount(articleList.size());
            // 设置图文消息包含的图文集合
            newsMessage.setArticles(articleList);
            return newsMessage;
        }


    }
}
