package com.lk.service.lankeManager.autosellmanager;

import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.util.PageData;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

public interface AutoSellService {

    public LayMessage list(PageData pd, Page page) throws Exception;

    void delete(PageData pd) throws Exception;

    List getNoByStore_id(PageData pd) throws Exception;

    void deleteAll(PageData pd) throws Exception;

    void save(List<Map<String,Object>> list) throws Exception;

    Map getAppid(Map map) throws  Exception;

    List<PageData> getMsgByopen_id(String open_id) throws Exception;

    List<PageData> getMember(String user_id) throws Exception;

    void getPay(int price) throws Exception;

    String getUsername(String store_id) throws  Exception;

    List<PageData> getStoreAndId(String out_order_no) throws Exception;

    void insertRefundMsg(PageData pd) throws Exception;

    String getInternet(String store_id) throws Exception;

    String template_Id(String internet_id, String temple_name) throws Exception;

    void addOrder(Map<String, Object> map) throws Exception;

    List<PageData> getOrderById(String id) throws Exception;

    void addUserMsg(Map<String, Object> map)throws Exception;

    void addState(Map<String, Object> map2) throws Exception;

    List<PageData> findOrder(String order_no) throws Exception;

    void addUserMsg2(Map<String, Object> map)throws Exception;

    PageData selectRefundMsg(String out_order_no) throws Exception;

    void addState2(String lanke_order_no) throws  Exception;

    PageData getOrderResult(String out_order_no) throws Exception;

    PageData getStoreName(String store_id) throws Exception;

    PageData check(String sm_no) throws Exception;

    PageData checkOrder(String order_no) throws Exception;

    LayMessage listInternetsell(PageData pd, Page page) throws Exception;
}
