package com.lk.service.lankeManager.signmanager;

import com.lk.util.PageData;

import java.util.List;

public interface SignService {
    List<PageData> getSign(String user_id, String date,String store_id) throws Exception;

    List<PageData> getSigns(PageData pd) throws Exception;

    void addSign(String id, String signCount, String date, String store_id, String user_id,String integral) throws Exception;

    void addSign2(String id, String signCount, String date, String store_id, String user_id, String integral) throws Exception;
}
