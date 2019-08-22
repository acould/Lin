package com.lk.service.lankeManager.vouchermanager.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.service.lankeManager.vouchermanager.VoucherManagerService;
import com.lk.service.system.task.Timers;
import com.lk.util.PageData;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class VoucherManagerServiceImpl implements VoucherManagerService {

    private static String mapperName = "VoucherManagerMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource
    private Timers timers;

    @Override
    public LayMessage list(PageData pd, Page page) throws Exception {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");//转格式
        String dateStr = sdf.format(date);
        dateStr +="23:59:59";
        pd.put("datetime",dateStr);
        page.setPd(pd);
        List<PageData> list = this.dataListpage(page);
        return LayMessage.ok(page.getTotalResult(), list);
    }

    @Override
    public JSONObject reSend(PageData pd) throws Exception {
        JSONObject sendResult = timers.sendRushCard(pd);
        return sendResult;
    }


    private List<PageData> dataListpage(Page page) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "datalistPage", page);
    }
}
