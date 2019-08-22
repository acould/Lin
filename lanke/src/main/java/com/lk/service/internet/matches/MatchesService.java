package com.lk.service.internet.matches;

import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-05-21 11:25
 */
public interface MatchesService {


    /*******************************************************后台业务***************************************************************/
    int save(PageData pd)throws Exception;

    int delete(String id)throws Exception;

    int edit(PageData pd)throws Exception;

    PageData findById(String id)throws Exception;

    PageData getById(String id)throws Exception;

    List<PageData> dataListpage(Page page)throws Exception;

    LayMessage getList(PageData pd, Page page) throws Exception;

    Message saveMatches(PageData pd)throws Exception;

    Message delMatches(PageData pd)throws Exception;

    Message chooseStore(PageData pd)throws Exception;

    Message getQrCode(PageData pd)throws Exception;

    /*******************************************************微信端业务***************************************************************/
    Message loadGameList(String type, String flag)throws Exception;

    Message loadGameDetail(String matches_id)throws Exception;


    Message createTeam(String matches_id, PageData pd)throws Exception;

    Message joinTeam(String matches_id, PageData pd)throws Exception;

    Message loadMyEnroll(String matches_id)throws Exception;

    Message buildTeam(PageData pd, String[] nameArr, String[] phoneArr)throws Exception;


    Message checkYzm(PageData pd)throws Exception;

    Message checkPhoneIsEnroll(String maches_id, String phone) throws Exception;

    Message saveOneEnroll(String matches_id, PageData pd)throws Exception;


    Message selMatchesList(PageData pd)throws Exception;
}
