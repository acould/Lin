package com.lk.service.internet.enroll;

import com.lk.entity.Page;
import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-05-21 11:41
 */
public interface EnrollTeamService {


    int save(PageData pd)throws Exception;

    int delete(String id)throws Exception;

    int edit(PageData pd)throws Exception;

    PageData findById(String id)throws Exception;

    List<PageData> findTeamByMatchesId(Page page, PageData pd) throws Exception;

    PageData finByTeamName(PageData pd) throws Exception;
}
