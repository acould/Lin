package com.lk.service.tb.TemplateRecord.impl;

import com.lk.dao.DaoSupport;
import com.lk.service.tb.TemplateRecord.TemplateRecordService;
import com.lk.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author myq
 * @description
 * @create 2019-04-12 15:34
 */
@Service
public class TeamplateRecordServiceImpl implements TemplateRecordService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int save(PageData pd) throws Exception {
        return (int) dao.save("TemplateRecordMapper.save", pd);
    }

    @Override
    public int update(PageData pd) throws Exception {
        return (int) dao.save("TemplateRecordMapper.edit", pd);
    }

    @Override
    public PageData findById(String id) throws Exception {
        return (PageData) dao.findForObject("TemplateRecordMapper.findById", id);
    }


}
