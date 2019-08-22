package com.lk.service.lankeManager.cartcanclemanager;

import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.util.PageData;

public interface CartCancleService {

    LayMessage list(PageData pd, Page page) throws Exception;
}
