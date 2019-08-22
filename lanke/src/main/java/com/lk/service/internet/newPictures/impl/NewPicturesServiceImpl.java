package com.lk.service.internet.newPictures.impl;

import com.lk.dao.DaoSupport;
import com.lk.service.internet.newPictures.NewPicturesService;
import com.lk.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author myq
 * @description 图片操作——实现
 * @create 2019-04-29 16:47
 */
@Service
public class NewPicturesServiceImpl implements NewPicturesService {


    private static final Logger log = LoggerFactory.getLogger(NewPicturesServiceImpl.class);
    private static String mapperName = "NewPicturesMapper.";

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
    public int delByForeignIdAndSort(PageData pd) throws Exception {
        return (int) dao.delete(mapperName + "delByForeignIdAndSort", pd);
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
    public PageData findByStoreIdAndSort(PageData pdImg) throws Exception{
        return (PageData) dao.findForObject(mapperName + "findByStoreIdAndSort", pdImg);
    }

    @Override
    public List<PageData> findByStoreIdAndType(PageData pdImg) throws Exception{
        return (List<PageData>) dao.findForList(mapperName + "findByStoreIdAndType", pdImg);
    }

    @Override
    public PageData findByForeignId(String id) throws Exception{
        return (PageData) dao.findForObject(mapperName + "findByForeignId", id);
    }

    @Override
    public List<PageData> findByForeignIdAndType(PageData pdImg) throws Exception{
        return (List<PageData>) dao.findForList(mapperName + "findByForeignIdAndType", pdImg);
    }
}
