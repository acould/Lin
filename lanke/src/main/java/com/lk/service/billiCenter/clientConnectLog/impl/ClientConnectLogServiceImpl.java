package com.lk.service.billiCenter.clientConnectLog.impl;

import com.lk.dao.DaoSupport;
import com.lk.service.billiCenter.clientConnectLog.ClientConnectLogService;
import com.lk.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author myq
 * @description 客户端连接日志 业务 -- 实现
 * @create 2019-04-16 15:26
 */
@Service
public class ClientConnectLogServiceImpl implements ClientConnectLogService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;





















    @Override
    public int save(PageData pd) throws Exception {
        return (int) dao.save("ClientConnectLogMapper.save", pd);
    }

    @Override
    public int delete(String id) throws Exception {
        return (int) dao.delete("ClientConnectLogMapper.delete", id);
    }

    @Override
    public int edit(PageData pd) throws Exception {
        return (int) dao.update("ClientConnectLogMapper,edit", pd);
    }

    @Override
    public PageData findById(String id) throws Exception {
        return (PageData) dao.findForObject("ClientConnectLogMapper.findById", id);
    }





}
