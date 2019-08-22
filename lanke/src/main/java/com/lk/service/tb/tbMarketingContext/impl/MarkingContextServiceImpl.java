package com.lk.service.tb.tbMarketingContext.impl;

import com.lk.dao.DaoSupport;
import com.lk.service.tb.tbMarketingContext.MarkingContextService;
import com.lk.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-04-11 18:04
 */
@Service
public class MarkingContextServiceImpl implements MarkingContextService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public int save(PageData pd) throws Exception {
       return (int) dao.save("TbMarketingContextMapper.save", pd);
    }

    @Override
    public PageData findById(String id) throws Exception {
        return (PageData) dao.findForObject("TbMarketingContextMapper.findById", id);
    }

    @Override
    public List<PageData> findByTypeAndTime(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TbMarketingContextMapper.findByTypeAndTime", pd);
    }

    @Override
    public int updateSendType(PageData pdType) throws Exception{
        return (int) dao.update("TbMarketingContextMapper.updateSendType", pdType);
    }

}
