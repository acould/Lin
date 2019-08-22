package com.lk.service.lankeManager.officialmanager.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.service.internet.enroll.impl.EnrollServiceImpl;
import com.lk.service.lankeManager.officialmanager.OfficialService;
import com.lk.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OfficialServiceImpl implements OfficialService {

    private static final Logger log = LoggerFactory.getLogger(EnrollServiceImpl.class);
    private static String mapperName = "OfficialManagerMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 查询所有的公众号列表
     * @param pd
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public LayMessage list(PageData pd, Page page) throws Exception {
        page.setPd(pd);
        List<PageData> list = this.dataListpage(page);//查询列表
        return LayMessage.ok(page.getTotalResult(), list);
    }

    /**
     * 更新公众号的信息
     * @param pd
     * @throws Exception
     */
    @Override
    public void update(PageData pd) throws Exception {
        dao.update(mapperName + "edit", pd);
        dao.update(mapperName + "edit2", pd);
    }

    @Override
    public void updateToken(String intenet_id, String authToken) throws Exception {
        Map map = new HashMap<String,Object>();
        map.put("intenet_id",intenet_id);
        map.put("authToken",authToken);
        dao.update(mapperName + "updateToken", map);
    }

    /**
     * 调用dao层
     * @param page
     * @return
     * @throws Exception
     */
    private List<PageData> dataListpage(Page page) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "datalistPage", page);
    }
}
