package com.lk.service.lankeManager.cusmanager;

import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.util.PageData;
import net.sf.json.JSONObject;
import java.util.List;

public interface CusManagerService {
    public LayMessage list(PageData pd,Page page) throws Exception;

    List<PageData> listExcel(Page page)throws Exception;

    JSONObject listSum(Page page) throws Exception;
}
