package com.lk.service.internet.enroll.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.internet.enroll.EnrollService;
import com.lk.service.internet.enroll.EnrollTeamService;
import com.lk.service.internet.matches.MatchesService;
import com.lk.service.internet.matches.impl.MatchesServiceImpl;
import com.lk.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author myq
 * @description 用户报名-业务处理
 * @create 2019-05-21 11:41
 */
@Service
public class EnrollServiceImpl implements EnrollService {

    private static final Logger log = LoggerFactory.getLogger(EnrollServiceImpl.class);
    private static String mapperName = "EnrollMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Autowired
    private EnrollTeamService enrollTeamService;

    @Autowired
    private MatchesService matchesService;

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
     * 根据队伍id删除
     * @param team_id
     * @return
     * @throws Exception
     */
    @Override
    public int delByTeamId(String team_id)throws Exception{
        return (int) dao.delete(mapperName + "delByTeamId", team_id);
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
     * 根据队伍id和手机号修改
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public int editByTeamIdAndPhone(PageData pd) throws Exception {
        return (int) dao.update(mapperName + "editByTeamIdAndPhone", pd);
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

    @Override
    public int getEnrollNumByMatchesId(String matchesId) throws Exception {
        return (int) dao.findForObject(mapperName + "getEnrollNumByMatchesId", matchesId);
    }

    @Override
    public List<PageData> dataListpage(Page page) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "datalistPage", page);
    }


    @Override
    public List<PageData> listExcel(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "listExcel", pd);
    }

    @Override
    public List<PageData> findByTeamId(String team_id) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "findByTeamId", team_id);
    }

    @Override
    public PageData findByUserIdAndMatchesId(PageData pd) throws Exception {
        return (PageData) dao.findForObject(mapperName + "findByUserIdAndMatchesId", pd);
    }
    @Override
    public PageData findByPhoneAndMatchesId(PageData pd) throws Exception {
        return (PageData) dao.findForObject(mapperName + "findByPhoneAndMatchesId", pd);
    }

    /***************************** 业务代码 **********************/



    /*******************************************************微信端业务***************************************************************/

    /**
     * 分页查询
     * @param pd（模糊查询：名字，手机号，队伍名称）
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public LayMessage getEnrollList(PageData pd, Page page) throws Exception {

        pd.put("matches_id", pd.getString("matches_id"));

        page.setPd(pd);
        List<PageData> list = this.dataListpage(page);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("create_time", list.get(i).get("create_time").toString());
        }

        return LayMessage.ok(page.getTotalResult(), list);
    }



    /**
     * 已报名的队伍
     * @param matches_id
     * @return
     */
    @Override
    public Message loadMatchesEnroll(String matches_id, int curr, int show)  throws Exception{

        PageData pd = new PageData();
        pd.put("matches_id", matches_id);

        Page page = new Page();
        page.setCurrentPage(curr);
        page.setShowCount(show);//每页展示20或者1

        //获取当前用户报名信息
        User user = Jurisdiction.getSessionUser().getUser();
        pd.put("user_id", user.getUSER_ID());
        PageData pdEnroll = this.findByUserIdAndMatchesId(pd);
        if(pdEnroll == null){
            pdEnroll = new PageData();
        }
        pd.put("team_id", pdEnroll.getString("team_id"));


        //获取报名的队伍列表（分页）
        List<PageData> list = enrollTeamService.findTeamByMatchesId(page, pd);
        for (int i = 0; i < list.size(); i++) {
            String team_id = list.get(i).getString("id");
            List<PageData> enrollList = this.findByTeamId(team_id);

            list.get(i).put("enrollList", enrollList);
        }

        pdEnroll.put("team_name", list.get(0).getString("team_name"));
        pdEnroll.put("team_description", list.get(0).getString("team_description"));
        pdEnroll.put("totalPage", page.getTotalPage());//总页数

        PageData pdMatches = matchesService.findById(matches_id);
        pdMatches = MatchesServiceImpl.getStatus(pdMatches);
        pdEnroll.put("status", pdMatches.get("status"));//赛事状态
        pdEnroll.put("team_number", pdMatches.get("team_number"));//组队人数


        return Message.ok().addData("list", list).addData("pd", pdEnroll);
    }


    /**
     * 退出队伍
     * @param matches_id
     * @return
     * @throws Exception
     */
    @Override
    public Message quitTeam(String matches_id) throws Exception{

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("matches_id", matches_id);
        PageData pdEnroll = this.findByUserIdAndMatchesId(pd);
        if(pdEnroll == null){
            return Message.error("您尚未报名");
        }

        PageData pdMatches = matchesService.findById(matches_id);
        pdMatches = MatchesServiceImpl.getStatus(pdMatches);
        if(!pdMatches.get("status").toString().equals("1")){
            return Message.error("报名已结束");
        }

        //删除报名信息
        this.delete(pdEnroll.getString("id"));


        //最后一个人退出则，删除队伍
        List<PageData> teamList = this.findByTeamId(pdEnroll.getString("team_id"));
        if(teamList.size() == 0){
            enrollTeamService.delete(pdEnroll.getString("team_id"));
        }else{
            //如果是队长退出,报名时间最近的人变成队长
            if(pdEnroll.get("position").toString().equals("1")){
                PageData pdEnroll2 = teamList.get(0);
                if(pdEnroll2.get("position").toString().equals("2")){
                    //队员升级为队长
                    pdEnroll2.put("position", "1");//队长
                    edit(pdEnroll2);
                }
            }
        }


        return Message.ok("退出成功");
    }
}
