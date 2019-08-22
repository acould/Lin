package com.lk.service.user.notify.impl;

import com.lk.dao.DaoSupport;
import com.lk.service.internet.sendManager.impl.SendManagerServiceImpl;
import com.lk.service.user.notify.NotifyService;
import com.lk.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author myq
 * @description 用户通知——业务层
 * @create 2019-06-08 16:02
 */
@Service
public class NotifyServiceImpl implements NotifyService {

    private static final Logger log = LoggerFactory.getLogger(NotifyServiceImpl.class);
    private static String mapperName = "NotifyMapper.";

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
     *
     * @param id
     * @throws Exception
     */
    @Override
    public int delete(String id) throws Exception {
        return (int) dao.delete(mapperName + "delete", id);
    }

    /**
     * 修改
     *
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
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PageData findById(String id) throws Exception {
        PageData pd = (PageData) dao.findForObject(mapperName + "findById", id);
        return pd;
    }

    /**
     * 根据微信用户open_id查询
     * @param open_id
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> findByOpenId(String open_id) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "findByOpenId", open_id);
    }


    /**
     * 获取已读或未读的列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> findUnreadByOpenId(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "findUnreadByOpenId", pd);
    }
}
