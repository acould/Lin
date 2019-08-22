package com.lk.service.internet.enroll;

import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.util.PageData;

import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-05-21 11:41
 */
public interface EnrollService {


    int save(PageData pd)throws Exception;

    int delete(String id)throws Exception;

    int delByTeamId(String team_id)throws Exception;

    int edit(PageData pd)throws Exception;

    int editByTeamIdAndPhone(PageData pd)throws Exception;

    PageData findById(String id)throws Exception;

    int getEnrollNumByMatchesId(String matchesId)throws Exception;

    List<PageData> dataListpage(Page page)throws Exception;


    List<PageData> listExcel(PageData pd) throws Exception;


    List<PageData> findByTeamId(String team_id) throws Exception;


    PageData findByUserIdAndMatchesId(PageData pd) throws Exception;


    PageData findByPhoneAndMatchesId(PageData pdEnroll) throws Exception;


    /*******************************************************微信端业务***************************************************************/

    LayMessage getEnrollList(PageData pd, Page page)throws Exception;

    Message loadMatchesEnroll(String matches_id, int curr, int show) throws Exception;

    Message quitTeam(String matches_id)  throws Exception;
}
