package com.lk.service.lankeManager.officialmanager;

import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.util.PageData;

public interface OfficialService {

   public LayMessage list(PageData pd, Page page) throws Exception ;

   void update(PageData pd) throws Exception;

   void updateToken(String intenet_id, String authToken) throws Exception;
}
