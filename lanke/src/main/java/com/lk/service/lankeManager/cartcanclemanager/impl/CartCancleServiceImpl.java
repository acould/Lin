package com.lk.service.lankeManager.cartcanclemanager.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.service.internet.enroll.impl.EnrollServiceImpl;
import com.lk.service.lankeManager.cartcanclemanager.CartCancleService;
import com.lk.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CartCancleServiceImpl implements CartCancleService {


    private static final Logger log = LoggerFactory.getLogger(EnrollServiceImpl.class);
    private static String mapperName = "CardCancelManagerMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * STATE=='2' && d.MONEY_STATE
     * CARD_TICKET == 1
     * @param pd
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public LayMessage list(PageData pd, Page page) throws Exception {
        page.setPd(pd);
        /*String keywords = pd.getString("keywords").trim();
        pd.put("keywords",keywords);*/
        List<PageData> list = this.dataListpage(page);
       /* for (int i = 0; i < list.size(); i++) {
            list.get(i).put("create_time", list.get(i).get("create_time").toString());
        }*/

        return LayMessage.ok(page.getTotalResult(), list);
    }

    private List<PageData> dataListpage(Page page) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "datalistPage", page);
    }
}
