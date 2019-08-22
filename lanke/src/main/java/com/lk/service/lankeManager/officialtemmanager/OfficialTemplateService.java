package com.lk.service.lankeManager.officialtemmanager;

import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.util.PageData;

import java.util.List;
import java.util.Map;

public interface OfficialTemplateService {

    LayMessage list(PageData pd, Page page) throws  Exception;

     List<PageData> getInternetList() throws Exception;


    void getinternetById(String internet_id, List<Map<String, Object>> list) throws  Exception;

    void getExist(String internet_id, List<Map<String, Object>> list) throws  Exception;

    void getExist2(String[] strs) throws  Exception;

    void deleteTemplate(PageData pd) throws  Exception;

    void deleteTemplate2() throws  Exception;
}
