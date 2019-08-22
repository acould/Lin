package com.lk.service.article;

import com.lk.entity.Page;
import com.lk.util.PageData;
import net.sf.json.JSONObject;

/**
 * @author myq
 * @description
 * @create 2019-01-11 15:19
 */
public interface GroupSendService {

    /****************************************图文消息--分割线****************************************/

    JSONObject loadIndex(PageData pd, Page page) throws Exception;



    JSONObject loadNews(PageData pd) throws Exception;


    JSONObject delNews(PageData pd) throws Exception;


    JSONObject saveNews(PageData pd) throws Exception;


    JSONObject saveImgs(String base64Img) throws Exception;


    JSONObject preview(PageData pd) throws Exception;


    JSONObject getInternetList(PageData pd) throws Exception;


    JSONObject groupSend(PageData pd) throws Exception;


    /****************************************模板消息--分割线****************************************/


    JSONObject loadMessageIndex(PageData pd, Page page) throws Exception;


    JSONObject findByMessageId(PageData pd) throws Exception;


    JSONObject saveMessage(PageData pd) throws Exception;


    JSONObject delMessage(PageData pd) throws Exception;


    JSONObject sendMessage(PageData pd) throws Exception;



    /****************************************群发记录--分割线****************************************/


    JSONObject loadRecordIndex(PageData pd, Page page) throws Exception;





}
