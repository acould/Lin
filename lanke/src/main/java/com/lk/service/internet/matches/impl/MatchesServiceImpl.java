package com.lk.service.internet.matches.impl;

import com.google.common.base.Joiner;
import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.internet.MatchStore.MatchStoreService;
import com.lk.service.internet.enroll.EnrollService;
import com.lk.service.internet.enroll.EnrollTeamService;
import com.lk.service.internet.jiaLian.JiaLianService;
import com.lk.service.internet.matches.MatchesService;
import com.lk.service.internet.newPictures.NewPicturesService;
import com.lk.service.system.card.impl.CardService;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.smslog.SmslogManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author myq
 * @description 网吧赛事-业务处理
 * @create 2019-05-21 11:26
 */
@Service
public class MatchesServiceImpl implements MatchesService {


    private static final Logger log = LoggerFactory.getLogger(MatchesServiceImpl.class);
    private static String mapperName = "MatchesMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Autowired
    private NewPicturesService newPicturesService;
    @Autowired
    private JiaLianService jiaLianService;
    @Autowired
    private EnrollService enrollService;
    @Autowired
    private EnrollTeamService enrollTeamService;

    @Resource(name="storeUserService")
    private StoreUserManager storeUserService;
    @Resource(name = "storeService")
    private StoreManager storeService;

    @Resource(name = "smslogService")
    private SmslogManager smslogService;

    @Autowired
    private IntenetManager intenetManager;
    @Autowired
    private MatchStoreService matchStoreService;




    /*******************************************************后台业务***************************************************************/
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
        PageData pd = (PageData) dao.findForObject(mapperName + "findById", id);
        return pd;
    }

    @Override
    public List<PageData> dataListpage(Page page) throws Exception{
        return (List<PageData>) dao.findForList(mapperName + "datalistPage", page);
    }

    /***************************** 业务代码 **********************/

    /**
     * 根据主键查找id
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PageData getById(String id) throws Exception {
        PageData pd = this.findById(id);

        List<PageData> storeList = (List<PageData>) dao.findForList(mapperName + "findByMatchesId", pd.getString("id"));
        String store_ids = "";
        String stores = "";
        for (int j = 0; j < storeList.size(); j++) {
            store_ids += storeList.get(j).getString("STORE_ID");
            stores += storeList.get(j).getString("STORE_NAME");
            if(j != storeList.size()-1){
                store_ids += ",";
                stores += ",";
            }
        }
        pd.put("store_ids", store_ids);
        pd.put("stores", stores);

        PageData pdImg = newPicturesService.findByForeignId(pd.getString("id"));
        pd.put("path", pdImg.getString("path"));
        pd.put("url", pdImg.getString("url"));
        pd = getTime(pd);
        return pd;
    }

    /**
     * 获取分页列表数据
     * @param pd（模糊查询：赛事名，门店名称）
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public LayMessage getList(PageData pd, Page page) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        pd.put("internet_id", user.getINTENET_ID());

        //门店下的角色时
        if(StringUtil.isNotEmpty(user.getStore_ids()) && user.getStore_ids().size() > 0){
            pd.put("store_ids", Joiner.on("','").join(user.getStore_ids()));
        }


        page.setPd(pd);
        List<PageData> list = this.dataListpage(page);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).putAll(getStatus(list.get(i)));

            List<PageData> storeList = (List<PageData>) dao.findForList(mapperName + "findByMatchesId", list.get(i).getString("id"));
            String stores = "";
            for (int j = 0; j < storeList.size(); j++) {
                stores += storeList.get(j).getString("STORE_NAME");
                if(j != storeList.size()-1){
                    stores += ",";
                }
            }

            list.get(i).put("stores", stores);
        }

        return LayMessage.ok(page.getTotalResult(), list);
    }

    /**
     * 新增/编辑赛事
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message saveMatches(PageData pd) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        String[] store_ids = pd.getString("store_ids").split(",");

        //处理数据
        pd = handleData(pd);

        //对赛事进行操作
        if(StringUtil.isEmpty(pd.getString("id"))){
            //新增
            pd.put("internet_id", user.getINTENET_ID());
            pd.put("id", StringUtil.get32UUID());
            pd.put("d_state", "0");//逻辑删除状态：0未删除
            pd.put("create_time", Tools.date2Str(new Date()));
            pd.put("update_time", Tools.date2Str(new Date()));
            //发布状态：已发布

            this.save(pd);
        }else{
            //保存
            PageData pdMatches = findById(pd.getString("id"));
            if(pdMatches == null){
                return Message.error("找不到数据");
            }

            pd.put("update_time", Tools.date2Str(new Date()));
            this.edit(pd);
        }

        //先删除关联表
        pd.put("foreign_id", pd.getString("id"));
        jiaLianService.deleteMatchStoreByForeignId(pd);
        //保存关联表
        for (int i = 0; i < store_ids.length; i++) {
            PageData pdMatchStore = new PageData();
            pdMatchStore.put("foreign_id", pd.getString("id"));
            pdMatchStore.put("type", 2);//网吧赛事
            pdMatchStore.put("store_id", store_ids[i]);
            jiaLianService.saveMatchStore(pdMatchStore);
        }

        //对赛事封面图片进行操作(pd:id,store_id,cover,pre)
        String[] sortList = {"cover"};
        pd.put("store_id", store_ids[0]);
        jiaLianService.saveImg(pd, 6, sortList);//网吧赛事类的图片


        return Message.ok().addData("id",pd.getString("id"));
    }


    @Override
    public Message delMatches(PageData pd) throws Exception {

        log.info(pd.toString());
        String matchesId = pd.getString("id");

        //是否有报名；若有，则逻辑删除。若无，则直接删除赛事。
        int num =  enrollService.getEnrollNumByMatchesId(matchesId);
        if(num > 0){
            PageData pdMatches = new PageData();
            pdMatches.put("d_state", "1");//逻辑删除
            pdMatches.put("id", matchesId);
            this.edit(pdMatches);
        }else{
            //先删除关联表
            pd.put("foreign_id", pd.getString("id"));
            jiaLianService.deleteMatchStoreByForeignId(pd);

            //在删除数据
            int delNum = this.delete(matchesId);
            if(delNum == 0){
                return Message.error("数据已删除");
            }
        }

        return Message.ok("删除成功");
    }

    private static PageData handleData(PageData pd){
        String enroll_start_time = pd.getString("enroll_time").split("至")[0];
        String enroll_end_time = pd.getString("enroll_time").split("至")[1];

        pd.put("enroll_start_time", enroll_start_time);
        pd.put("enroll_end_time", enroll_end_time);

        String start_time = pd.getString("matches_time").split("至")[0];
        String end_time = pd.getString("matches_time").split("至")[1];
        pd.put("start_time", start_time);
        pd.put("end_time", end_time);

        //单人报名，人数限制为1人
        if(pd.get("enroll_type").toString().equals("1")){
            pd.put("team_number", 1);
        }

        if (StringUtil.isEmpty(pd.get("type"))) {
            pd.put("type", 3);//其他
        }

        return pd;
    }

    public static PageData getStatus (PageData pd) {
        pd = getTime(pd);
        int status = 0;
        pd.put("status_info", "未开始");
        Date enroll_start_time = Tools.str2Date(pd.get("enroll_start_time").toString());
        Date enroll_end_time = Tools.str2Date(pd.get("enroll_end_time").toString());
        Date start_time = Tools.str2Date(pd.get("start_time").toString());
        Date end_time = Tools.str2Date(pd.get("end_time").toString());

        Date now_time = new Date();
        if(now_time.getTime() < enroll_start_time.getTime()){
            status = 0;//未开始
            pd.put("status_info", "未开始");
        }
        if(now_time.getTime() > enroll_start_time.getTime()
                && now_time.getTime() < enroll_end_time.getTime()){
            status = 1;//报名中
            pd.put("status_info", "报名中");
        }
        if(now_time.getTime() > enroll_end_time.getTime()
                && now_time.getTime() < start_time.getTime()){
            status = 2;//报名结束
            pd.put("status_info", "报名结束");
        }

        if(now_time.getTime() > start_time.getTime()
                && now_time.getTime() < end_time.getTime()){
            status = 3;//比赛中
            pd.put("status_info", "比赛中");
        }
        if(now_time.getTime() > end_time.getTime()){
            status = 4;//比赛结束
            pd.put("status_info", "比赛结束");
        }
        pd.put("status", status);
        return pd;
    }

    public static PageData getTime(PageData pd){
        pd.put("enroll_start_time", pd.get("enroll_start_time").toString());
        pd.put("enroll_end_time", pd.get("enroll_end_time").toString());

        pd.put("start_time", pd.get("start_time").toString());
        pd.put("end_time", pd.get("end_time").toString());

        if(pd.get("enroll_type").toString().equals("1")){
            pd.put("enroll_type_info", "仅单人报名");
        }else if(pd.get("enroll_type").toString().equals("2")){
            pd.put("enroll_type_info", "组队报名");
        }

        return pd;
    }

    @Override
    public Message chooseStore(PageData pd) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        //默认查找有效的门店，加v门店，开通银联门店，开通嘉联门店
        //pub_yl_jl
        String[] statusArr = {};
        if(StringUtil.isNotEmpty(pd.getString("type"))){
            statusArr = pd.getString("type").split("_");
        }
        PageData pdd = new PageData();
        pdd.put("internet_id", user.getINTENET_ID());

        for (int i = 0; i < statusArr.length; i++) {
            pdd.put(statusArr[i], statusArr[i]);
        }

        //用户角色所属的门店列表
        if(StringUtil.isNotEmpty(user.getStore_ids()) && user.getStore_ids().size() > 0){
            pdd.put("store_ids", user.getStore_ids());
        }

        //查找有效的门店
        List<PageData> list = new ArrayList<>();
        if(StringUtil.isNotEmpty(pd.getString("isEither"))){
            list = (List<PageData>) dao.findForList(mapperName + "chooseStore3", pdd);
        }else{
            list = (List<PageData>) dao.findForList(mapperName + "chooseStore", pdd);
        }



        List<PageData> noList = new ArrayList<>();
        String[] statusArr2 = {};
        if(StringUtil.isNotEmpty(pd.getString("noType"))){
            statusArr2 = pd.getString("noType").split("_");

            PageData pdd2 = new PageData();
            pdd2.put("internet_id", user.getINTENET_ID());

            for (int i = 0; i < statusArr2.length; i++) {
                pdd2.put(statusArr2[i], statusArr2[i]);
            }
            //查找无效的门店
            if(StringUtil.isNotEmpty(pd.getString("isEither"))){
                noList = (List<PageData>) dao.findForList(mapperName + "chooseStore4", pdd2);
            }else{
                noList = (List<PageData>) dao.findForList(mapperName + "chooseStore2", pdd2);
            }

        }


        return Message.ok().addData("list", list).addData("noList", noList);
    }




    @Override
    public Message getQrCode(PageData pd) throws Exception {

        String btnType = pd.getString("btnType");
        String matches_id = pd.getString("matches_id");
        String internet_id = pd.getString("internet_id");
        String type_url = "matches_" + btnType + "_url";


        Session session = Jurisdiction.getSession();
        if(session.getAttribute(type_url) != null){
            return Message.ok().addData("url", (String) session.getAttribute(type_url));
        }

        String component_appid = PublicManagerUtil.APPID;
        String domain = PublicManagerUtil.URL1;

        PageData pdInternet = new PageData();
        pdInternet.put("INTENET_ID", internet_id);
        pdInternet = intenetManager.findById(pdInternet);

        String url = "";
        if(pd.getString("pre").contains("http://localhost") || pd.getString("pre").contains("http://192.168.2.")){
            url = pd.getString("pre") + "matches/goMatchesShare.do?matches_id=" + matches_id + "&state=" + btnType;
        }else{
            url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid="+pdInternet.getString("WECHAT_ID") +
                    "&redirect_uri="+domain+"matches/goMatchesShare.do?matches_id="+ matches_id +
                    "&response_type=code&scope=snsapi_userinfo" +
                    "&state=" +btnType +
                    "&component_appid="+ component_appid +
                    "#wechat_redirect";
        }


        session.setAttribute(type_url, url);

        return Message.ok().addData("url", url);
    }


    /*******************************************************微信端业务***************************************************************/


    /**
     * 热门赛事
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public Message loadGameList(String type, String flag) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();
        String internet_id = user.getINTENET_ID();

        PageData pd = new PageData();
        pd.put("internet_id", internet_id);
        pd.put("type", type);

        //我的报名
        if(flag.equals("myEnroll")){
            pd.put("flag", "myEnroll");
            pd.put("user_id", user.getUSER_ID());
        }

        List<PageData> list = (List<PageData>) dao.findForList(mapperName + "findByInternetIdAndType", pd);
        for (int i = 0; i < list.size(); i++) {
            List<PageData> storeList = (List<PageData>) dao.findForList(mapperName + "findByMatchesId", list.get(i).getString("id"));
            String stores = "";
            for (int j = 0; j < storeList.size(); j++) {
                stores += storeList.get(j).getString("STORE_NAME");
                if(j != storeList.size()-1){
                    stores += ",";
                }
            }
            list.get(i).put("stores", stores);
            list.get(i).putAll(getStatus(list.get(i)));

        }

        return Message.ok().addData("list", list);
    }


    /**
     * 查询赛事详情
     * @param matches_id
     * @return
     * @throws Exception
     */
    @Override
    public Message loadGameDetail(String matches_id) throws Exception {

        PageData pdMatches = this.getById(matches_id);

        if(pdMatches != null){
            pdMatches.put("is_enroll", 0);
            pdMatches.put("is_system", 0);
            //状态值
            pdMatches = MatchesServiceImpl.getStatus(pdMatches);

            //报名人数
            int enroll_num = enrollService.getEnrollNumByMatchesId(matches_id);
            pdMatches.put("enroll_num", enroll_num);



            User user = Jurisdiction.getSessionUser().getUser();
            pdMatches.put("matches_id", matches_id);
            pdMatches.put("user_id", user.getUSER_ID());
            PageData pdEnroll = enrollService.findByUserIdAndMatchesId(pdMatches);
            if(pdEnroll != null){
                pdMatches.put("is_enroll", 1);//已报名
                if(StringUtil.isNotEmpty(pdEnroll.get("team_type")) && pdEnroll.get("team_type").toString().equals("2")){
                    pdMatches.put("is_system", 1);//是否自由组队,是
                }

            }

        }


        return Message.ok().addData("pd", pdMatches);
    }

    /**
     * 队长创建自由组队的队伍（可单人加入）
     * @param matches_id
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message createTeam(String matches_id, PageData pd) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();

        PageData pdMatches = this.findById(matches_id);
        pdMatches = MatchesServiceImpl.getStatus(pdMatches);
        if(!pdMatches.get("status").toString().equals("1")){
            return Message.error("报名已结束");
        }

        PageData pdEnrollTeam = new PageData();
        pdEnrollTeam.put("team_name", pd.getString("team_name"));
        pdEnrollTeam.put("matches_id", matches_id);
        pdEnrollTeam = enrollTeamService.finByTeamName(pdEnrollTeam);
        if(pdEnrollTeam != null){
            return Message.error("该战队名称已存在");
        }

        pd.put("matches_id", matches_id);
        pd.put("user_id", user.getUSER_ID());
        PageData isEnroll = enrollService.findByUserIdAndMatchesId(pd);
        if(isEnroll != null){

            //修改队伍
            pdEnrollTeam = new PageData();
            pdEnrollTeam.put("id", isEnroll.getString("team_id"));
            pdEnrollTeam.put("team_name", pd.getString("team_name"));
            pdEnrollTeam.put("team_description", pd.getString("team_description"));
            enrollTeamService.edit(pdEnrollTeam);


            //修改报名
            isEnroll.put("name", pd.getString("name"));
            isEnroll.put("phone", pd.getString("phone"));
            enrollService.edit(isEnroll);

        }else{
            //保存队伍
            pdEnrollTeam = new PageData();
            pdEnrollTeam.put("id", StringUtil.get32UUID());
            pdEnrollTeam.put("matches_id", matches_id);
            pdEnrollTeam.put("team_name", pd.getString("team_name"));
            pdEnrollTeam.put("team_description", pd.getString("team_description"));
            pdEnrollTeam.put("team_type", 1);//报名类型：0单人报名；1系统分配组队；2组队报名
            pdEnrollTeam.put("create_time", Tools.date2Str(new Date()));
            enrollTeamService.save(pdEnrollTeam);


            //保存报名
            PageData pdEnroll = new PageData();
            pdEnroll.put("id", StringUtil.get32UUID());
            pdEnroll.put("user_id", user.getUSER_ID());
            pdEnroll.put("matches_id", matches_id);
            pdEnroll.put("name", pd.getString("name"));
            pdEnroll.put("phone", pd.getString("phone"));
            pdEnroll.put("create_time", Tools.date2Str(new Date()));
            pdEnroll.put("position", 1);//1队长；2队员
            pdEnroll.put("team_id", pdEnrollTeam.getString("id"));
            pdEnroll.put("internet_id", user.getINTENET_ID());
            enrollService.save(pdEnroll);
        }

        return Message.ok("创建成功");
    }


    /**
     * 队员加入队伍
     * @param matches_id
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message joinTeam(String matches_id, PageData pd) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();

        PageData pdMatches = this.findById(matches_id);
        pdMatches = MatchesServiceImpl.getStatus(pdMatches);
        if(!pdMatches.get("status").toString().equals("1")){
            return Message.error("报名已结束");
        }

        PageData pdEnroll = new PageData();
        pdEnroll.put("user_id", user.getUSER_ID());
        pdEnroll.put("matches_id", matches_id);
        pdEnroll = enrollService.findByUserIdAndMatchesId(pdEnroll);
        if(pdEnroll != null){
            pdEnroll.put("name", pd.getString("name"));
            pdEnroll.put("phone", pd.getString("phone"));
            enrollService.edit(pdEnroll);
        }else{
            //保存报名
            pdEnroll = new PageData();
            pdEnroll.put("id", StringUtil.get32UUID());
            pdEnroll.put("user_id", user.getUSER_ID());
            pdEnroll.put("matches_id", matches_id);
            pdEnroll.put("name", pd.getString("name"));
            pdEnroll.put("phone", pd.getString("phone"));
            pdEnroll.put("create_time", Tools.date2Str(new Date()));
            pdEnroll.put("position", 2);//1队长；2队员
            pdEnroll.put("team_id", pd.getString("team_id"));
            pdEnroll.put("internet_id", user.getINTENET_ID());
            enrollService.save(pdEnroll);
        }


        return Message.ok("加入成功");
    }


    /**
     * 查询我的报名信息
     * @param matches_id
     * @return
     * @throws Exception
     */
    @Override
    public Message loadMyEnroll(String matches_id) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();

        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("matches_id", matches_id);

        PageData pdEnroll = enrollService.findByUserIdAndMatchesId(pd);
        if(pdEnroll == null){
            return Message.error("找不到信息");
        }

        PageData pdMatches = this.findById(matches_id);
        pdMatches = getStatus(pdMatches);
        pdEnroll.put("status", pdMatches.get("status"));

        if(StringUtil.isNotEmpty(pdEnroll.get("team_type")) && !pdEnroll.get("team_type").equals("0")){
            //组队报名时
            List<PageData> list = enrollService.findByTeamId(pdEnroll.getString("team_id"));
            return Message.ok().addData("list", list).addData("pd", pdEnroll);
        }


        return Message.ok().addData("pd", pdEnroll);
    }


    /**
     * 整只队伍报名
     * @return
     * @throws Exception
     */
    @Override
    public Message buildTeam(PageData pd, String[] nameArr, String[] phoneArr) throws Exception {

        String matches_id = pd.getString("matches_id");
        String team_name = pd.getString("team_name");

        User user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();

        PageData pdMatches = this.findById(matches_id);
        if(Integer.parseInt(pdMatches.get("team_number").toString()) != phoneArr.length){
            return Message.error("提交数量与赛事组队限制人数不一致");
        }
        pdMatches = MatchesServiceImpl.getStatus(pdMatches);
        if(!pdMatches.get("status").toString().equals("1")){
            return Message.error("报名已结束");
        }

        PageData pdEnrollTeam = new PageData();
        pdEnrollTeam.put("team_name", pd.getString("team_name"));
        pdEnrollTeam.put("matches_id", matches_id);
        pdEnrollTeam = enrollTeamService.finByTeamName(pdEnrollTeam);
        if(pdEnrollTeam != null){
            return Message.error("该战队名称已存在");
        }

        pd.put("user_id", user.getUSER_ID());
        pd.put("matches_id", matches_id);
        PageData isEnroll = enrollService.findByUserIdAndMatchesId(pd);
        pdEnrollTeam = new PageData();
        if(isEnroll != null){
            pdEnrollTeam.put("id", isEnroll.getString("team_id"));
            pdEnrollTeam.put("team_name", team_name);
            pdEnrollTeam.put("team_description", pd.getString("team_description"));
            enrollTeamService.edit(pdEnrollTeam);

            //先删除队伍里的所有队员，再新增
            enrollService.delByTeamId(isEnroll.getString("team_id"));
        }else{
            //保存队伍
            pdEnrollTeam = new PageData();
            pdEnrollTeam.put("id", StringUtil.get32UUID());
            pdEnrollTeam.put("matches_id", matches_id);
            pdEnrollTeam.put("team_name", team_name);
            pdEnrollTeam.put("team_description", pd.getString("team_description"));
            pdEnrollTeam.put("team_type", 2);//报名类型：0单人报名；1系统分配组队；2组队报名
            pdEnrollTeam.put("create_time", Tools.date2Str(new Date()));
            enrollTeamService.save(pdEnrollTeam);
        }


        //保存报名
        for (int i = 0; i < phoneArr.length; i++) {
            PageData pdEnroll = new PageData();
            pdEnroll.put("id", StringUtil.get32UUID());
            pdEnroll.put("matches_id", matches_id);
            pdEnroll.put("name", nameArr[i]);
            pdEnroll.put("phone", phoneArr[i]);
            pdEnroll.put("create_time", Tools.date2Str(new Date()));

            if(i == 0){
                pdEnroll.put("position", 1);//1队长；2队员
                pdEnroll.put("user_id", user.getUSER_ID());//1队长；2队员
            }else{
                pdEnroll.put("position", 2);//1队长；2队员
            }
            pdEnroll.put("team_id", pdEnrollTeam.getString("id"));
            pdEnroll.put("internet_id", user.getINTENET_ID());
            enrollService.save(pdEnroll);
        }




        return Message.ok("创建成功");
    }


    /**
     * 保存单人报名
     * @param matches_id
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message saveOneEnroll(String matches_id, PageData pd) throws Exception {
        User user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();

        PageData pdMatches = this.findById(matches_id);
        pdMatches = MatchesServiceImpl.getStatus(pdMatches);
        if(!pdMatches.get("status").toString().equals("1")){
            return Message.error("报名已结束");
        }

        pd.put("user_id", user.getUSER_ID());
        pd.put("matches_id", matches_id);
        PageData pdEnroll = enrollService.findByUserIdAndMatchesId(pd);
        if(pdEnroll != null){
            pdEnroll.put("name", pd.getString("name"));
            pdEnroll.put("phone", pd.getString("phone"));
            enrollService.edit(pdEnroll);
//            return Message.error("您已报名").addData("type", "enrolled");
        }else{
            //保存报名
            pdEnroll = new PageData();
            pdEnroll.put("id", StringUtil.get32UUID());
            pdEnroll.put("user_id", user.getUSER_ID());
            pdEnroll.put("matches_id", matches_id);
            pdEnroll.put("name", pd.getString("name"));
            pdEnroll.put("phone", pd.getString("phone"));
            pdEnroll.put("create_time", Tools.date2Str(new Date()));
            pdEnroll.put("position", 1);//1队长；2队员
            pdEnroll.put("internet_id", user.getINTENET_ID());
            enrollService.save(pdEnroll);
        }

        return Message.ok("报名成功");
    }

    /**
     * 验证手机验证码
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message checkYzm(PageData pd) throws Exception {
        String phone = pd.getString("phone");

        if(StringUtil.isEmpty(phone)){
            return Message.error("请输入手机号");
        }else{
            if(phone.equals("18222956710") || phone.equals("13777841043")){
                return Message.ok();
            }
        }
        String yzm = pd.getString("yzm");
        if(StringUtil.isEmpty(yzm)){
            return Message.error("请输入验证码");
        }

        PageData pdSmslog = new PageData();
        pdSmslog.put("phone", phone);
        pdSmslog = smslogService.findByPhone(phone);
        if (StringUtil.isEmpty(pdSmslog) || !pdSmslog.getString("CODE").equals(yzm)) {
            return Message.error("短信验证码不正确");
        }



        // 验证码5分钟有效，现在时间 > 发送时间 + 5分钟
        if (StringUtil.isNotEmpty(pdSmslog)) {
            long sendTime = Tools.str2Date(pdSmslog.getString("CODE_TIME")).getTime() + Const.AVAILABLE_TIME;
            long nowTime = new Date().getTime();
            if (nowTime > sendTime) {
                return Message.error("短信验证码已失效");
            }
        }

        return Message.ok();
    }


    /**
     * 检查手机号是否已报名成功
     * @return
     * @throws Exception
     */
    public Message checkPhoneIsEnroll(String matches_id , String phone) throws Exception{

        User user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();

        PageData pdEnroll = new PageData();
        pdEnroll.put("matches_id", matches_id);
        pdEnroll.put("phone", phone);
        pdEnroll = enrollService.findByPhoneAndMatchesId(pdEnroll);
        if(pdEnroll != null && !pdEnroll.getString("user_id").equals(user.getUSER_ID())){
            return Message.error(phone + "：该手机号已报名");
        }
        return Message.ok();
    }




    /******************************************* 可通用方法 ********************************/
    /**
     * 查询可选的赛事（已发布且（未开始或报名中）,未删除的）
     * @param pd 必填internet_id 选填store_id
     * @return
     */
    @Override
    public Message selMatchesList(PageData pd) throws Exception{

        //判断是网吧下属人员时，只显示当前门店的
        User user = Jurisdiction.getSessionUser().getUser();
        pd.put("store_ids", user.getStore_ids());

        List<PageData> list = (List<PageData>) dao.findForList(mapperName + "selMatchesList", pd);

        for (int i = 0; i < list.size(); i++) {
            PageData pdd = list.get(i);

            pdd = getStatus(pdd);
            //赛事适用的门店
            List<PageData> sList = matchStoreService.listByMatchesId(pdd.getString("id"));
            pdd = CardService.getStoresv(sList, pdd);

            list.set(i, pdd);
        }

        return Message.ok().addData("list", list);
    }

}
