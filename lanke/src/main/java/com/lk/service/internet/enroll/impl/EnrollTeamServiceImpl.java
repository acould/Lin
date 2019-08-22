package com.lk.service.internet.enroll.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.internet.enroll.EnrollService;
import com.lk.service.internet.enroll.EnrollTeamService;
import com.lk.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author myq
 * @description 报名队伍-业务处理
 * @create 2019-05-21 11:41
 */
@Service
public class EnrollTeamServiceImpl implements EnrollTeamService {

    private static final Logger log = LoggerFactory.getLogger(EnrollTeamServiceImpl.class);
    private static String mapperName = "EnrollTeamMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    /**
     * 新增
     * @param pd
     * @throws Exception
     */
    @Override
    public int save(PageData pd) throws Exception {
        return (int) dao.save(mapperName + "save", pd);
    }

    /**
     * 删除
     * @param id
     * @throws Exception
     */
    @Override
    public int delete(String id) throws Exception {
        return (int) dao.delete(mapperName + "delete", id);
    }

    /**
     * 修改
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public int edit(PageData pd) throws Exception {
        return (int) dao.update(mapperName + "edit", pd);
    }

    /**
     * 根据主键查找id
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PageData findById(String id) throws Exception {
        return (PageData) dao.findForObject(mapperName + "findById", id);
    }

    /**
     * 分页查找
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> findTeamByMatchesId(Page page, PageData pd) throws Exception{
        page.setPd(pd);
        return (List<PageData>) dao.findForList(mapperName + "findTeamByMatchesIdlistPage", page);
    }

    @Override
    public PageData finByTeamName(PageData pd) throws Exception{
        return (PageData) dao.findForObject(mapperName + "finByTeamName", pd);
    }

    /***************************** 业务代码 **********************/
}
