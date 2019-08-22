package com.lk.service.lankeManager.vouchermanager;

import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.util.PageData;
import net.sf.json.JSONObject;

public interface VoucherManagerService {
    LayMessage list(PageData pd, Page page) throws Exception;

    JSONObject reSend(PageData pd) throws  Exception;
}
