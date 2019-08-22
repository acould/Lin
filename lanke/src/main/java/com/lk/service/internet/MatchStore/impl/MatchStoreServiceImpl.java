package com.lk.service.internet.MatchStore.impl;

import com.lk.dao.DaoSupport;
import com.lk.service.internet.MatchStore.MatchStoreService;
import com.lk.service.internet.jiaLian.impl.JiaLianServiceImpl;
import com.lk.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author myq
 * @description 门店与各业务主键id的关联表——接口
 * @create 2019-04-29 16:40
 */
@Service
public class MatchStoreServiceImpl implements MatchStoreService {


    private static final Logger log = LoggerFactory.getLogger(MatchStoreServiceImpl.class);
    private static String mapperName = "InternetMatchStoreMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;










    @Override
    public int save(PageData pd) throws Exception {
        return (int) dao.save(mapperName + "save", pd);
    }

    @Override
    public int delete(String id) throws Exception {
        return (int) dao.delete(mapperName + "delete", id);
    }

    @Override
    public int edit(PageData pd) throws Exception {
        return (int) dao.update(mapperName + "edit", pd);
    }

    @Override
    public PageData findById(String id) throws Exception {
        return (PageData) dao.findForObject(mapperName + "findById", id);
    }

    @Override
    public List<PageData> listByMatchesId(String matches_id) throws Exception{
        return (List<PageData>) dao.findForList(mapperName + "listByMatchesId", matches_id);
    }

}
