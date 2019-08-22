package com.lk.service.internet.StoreLog.impl;

import com.lk.dao.DaoSupport;
import com.lk.service.internet.StoreLog.StoreLogService;
import com.lk.service.internet.jiaLian.impl.JiaLianServiceImpl;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author myq
 * @description 门店审核业务 —— 实现
 * @create 2019-04-29 16:42
 */
@Service
public class StoreLogServiceImpl implements StoreLogService {

    private static final Logger log = LoggerFactory.getLogger(StoreLogServiceImpl.class);
    private static String mapperName = "StoreLogMapper.";

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
    public List<PageData> findByForeignId(PageData pd) throws Exception{
        List<PageData> list = (List<PageData>) dao.findForList(mapperName + "findByForeignId", pd);
        for (PageData pdd : list) {
            if(StringUtil.isNotEmpty(pdd.get("apply_time"))){
                pdd.put("apply_time", pdd.get("apply_time").toString().substring(0,18));
            }
            if(StringUtil.isNotEmpty(pdd.get("check_time"))){
                pdd.put("check_time", pdd.get("check_time").toString().substring(0,18));
            }
        }
        return list;
    }
}
