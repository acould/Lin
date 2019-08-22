package com.lk.controller.intuser;

import com.lk.controller.base.BaseController;
import com.lk.entity.Message;
import com.lk.entity.system.User;
import com.lk.entity.weixin.JSAPI;
import com.lk.service.internet.enroll.EnrollService;
import com.lk.service.internet.enroll.EnrollTeamService;
import com.lk.service.internet.matches.MatchesService;
import com.lk.service.internet.matches.impl.MatchesServiceImpl;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.intuser.internetMember.InternetMemberManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author myq
 * @description 微信——赛事管理
 * @create 2019-05-24 16:07
 */
@Controller
@RequestMapping("/wxMatches")
public class WxMatchesController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WxMatchesController.class);

    @Resource(name = "internetMemberService")
    private InternetMemberManager internetMemberService;

    @Resource(name = "indexMemberService")
    private IndexMemberManager indexMemberService;
    @Resource(name = "intenetService")
    private IntenetManager intenetService;

    @Autowired
    private MatchesService matchesService;
    @Autowired
    private EnrollService enrollService;

    @Autowired
    private EnrollTeamService enrollTeamService;


    /******************************************* 请求页面 ********************************/
    /**
     * 去赛事列表页面
     * @return
     */
    @RequestMapping(value = "/goGamesList")
    public ModelAndView goGamesList() throws Exception{
        logger.info("赛事列表页面");
        ModelAndView mv = this.getModelAndView();
        // 测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }

        mv.setViewName("intenetmumber/matches/gamesList");
        return mv;
    }


    /**
     * 去赛事详情页面
     * @throws Exception
     */
    @RequestMapping(value = "/goGameDetail")
    public ModelAndView goGameDetail() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 测试传入open_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        // 判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }

        if (StringUtil.isEmpty(pdUser.getString("flag"))) {
            pdUser.put("flag", "normal");
        }

        mv.addObject("pd", pdUser);
        mv.setViewName("intenetmumber/matches/gameCenter");
        return mv;

    }


    /**
     * 中间跳转——去赛事详情页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goGameDetailShare")
    public ModelAndView goGameDetailShare() throws Exception{
        PageData pd = this.getPageData();//appid，matches_id
        String share_url = "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + pd.getString("appid") +
                "&redirect_uri=" + PublicManagerUtil.URL1 + "wxMatches/goGameDetail.do?matches_id=" + pd.getString("matches_id") +
                "&response_type=code&scope=snsapi_userinfo" +
                "&state=0" +
                "&component_appid=" + PublicManagerUtil.APPID +
                "#wechat_redirect";

        return new ModelAndView(share_url);
    }

    /**
     * 去全员创建队伍页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goBuildTeam")
    public ModelAndView goBuildTeam() throws Exception {
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();

        Message m = this.checkMatches(pd.getString("matches_id"));
        if(m.getErrcode() == 0){
            PageData pdMatches = (PageData) m.getData().get("pdMatches");
            pdMatches.put("matches_id", pdMatches.getString("id"));
            pd = pdMatches;
        }


        mv.addObject("pd", pd);
        mv.setViewName("intenetmumber/matches/gamesTeamJoin");
        return mv;
    }


    /**
     * 去单人报名页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goOneEnroll")
    public ModelAndView goOneEnroll() throws Exception {
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();


        mv.addObject("pd", pd);
        mv.setViewName("intenetmumber/matches/gamesOneJoin");
        return mv;
    }


    /**
     * 去队员选择队伍加入页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goChooseTeam")
    public ModelAndView goChooseTeam() throws Exception {
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        User user = this.getUser();
        pd.put("user_id", user.getUSER_ID());

        PageData pdEnroll = enrollService.findByUserIdAndMatchesId(pd);
        if(pdEnroll == null){
            pdEnroll = new PageData();
        }
        pdEnroll.put("matches_id", pd.getString("matches_id"));
        pdEnroll.put("team_id", pd.getString("team_id"));

        mv.addObject("pd", pdEnroll);
        mv.setViewName("intenetmumber/matches/chooseTeam");
        return mv;
    }


    /**
     * 去队长创建队伍页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goCreateTeam")
    public ModelAndView goCreateTeam() throws Exception {
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        User user = this.getUser();
        pd.put("user_id", user.getUSER_ID());

        PageData pdEnroll = enrollService.findByUserIdAndMatchesId(pd);
        if(pdEnroll == null){
            pdEnroll = new PageData();
        }
        pdEnroll.put("matches_id", pd.getString("matches_id"));
        pdEnroll.put("team_id", pd.getString("team_id"));

        mv.addObject("pd", pdEnroll);
        mv.setViewName("intenetmumber/matches/createTeam");
        return mv;
    }


    /**
     * 去我的队伍页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goMyTeam")
    public ModelAndView goMyTeam() throws Exception {
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();

        mv.addObject("pd", pd);
        mv.setViewName("intenetmumber/matches/myTeam");
        return mv;
    }

    /**
     * 去我的报名页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goMyEnroll")
    public ModelAndView goMyEnroll() throws Exception {
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();

        mv.addObject("pd", pd);
        mv.setViewName("intenetmumber/matches/myEnroll");
        return mv;
    }



    /******************************************* 请求方法 ********************************/


    /**
     * 加载网吧的赛事列表(网吧下所有门店的赛事)
     * @param type : 全部比赛all,进行中ing,已结束end
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadGameList")
    public Message loadGameList(@RequestParam(value = "type") String type,
                                @RequestParam(value = "flag") String flag) throws Exception{

        return matchesService.loadGameList(type, flag);
    }


    /**
     * 加载赛事详情
     * @param matches_id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadGameDetail")
    public Message loadGameDetail(@RequestParam(value = "matches_id") String matches_id) throws Exception{

        String url = this.getRequest().getParameter("url");
        JSAPI jsapi = indexMemberService.getWXJSdata(url, null);

        String share_url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + jsapi.getAppid() +
                "&redirect_uri=" + PublicManagerUtil.URL1 + "wxMatches/goGameDetail.do?matches_id=" + matches_id +
                "&response_type=code&scope=snsapi_userinfo" +
                "&state=0" +
                "&component_appid=" + PublicManagerUtil.APPID +
                "#wechat_redirect";
        jsapi.setShare_url(share_url);

        url = PublicManagerUtil.URL1 + "wxMatches/goGameDetailShare.do?matches_id=" + matches_id + "&appid=" + jsapi.getAppid();
        jsapi.setUrl(url);


        return matchesService.loadGameDetail(matches_id).addData("jsapi", jsapi);

    }


    /**
     *  加载赛事的所有报名信息
     * @param matches_id 赛事id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadMatchesEnroll")
    public Message loadMatchesEnroll(@RequestParam(value = "matches_id") String matches_id,
                                     @RequestParam(value = "curr") int curr) throws Exception{

        Message m = checkMatches(matches_id);
        if(m.getErrcode() != 0){
            return m;
        }

        return enrollService.loadMatchesEnroll(matches_id, curr, 20);
    }


    /**
     * 创建自由组队的队伍（可单人加入）
     * @param matches_id 赛事id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/createTeam")
    public Message createTeam(@RequestParam(value = "matches_id") String matches_id) throws Exception{

        Message m = checkMatches(matches_id);
        if(m.getErrcode() != 0){
            return m;
        }

        //传入参数(name,phone,yzm, team_name,team_type)
        PageData pd = this.getPageData();

        //判断手机验证码是否正确
        m = matchesService.checkYzm(pd);
        if(m.getErrcode() != 0){
            return m;
        }

        //检查手机号是否已报名
        m = matchesService.checkPhoneIsEnroll(matches_id, pd.getString("phone"));
        if(m.getErrcode() != 0){
            return m;
        }

        //创建队伍
        return matchesService.createTeam(matches_id, pd);
    }


    /**
     * 加入已有报名队伍队伍
     * @param matches_id 赛事id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/joinTeam")
    public Message joinTeam(@RequestParam(value = "matches_id") String matches_id) throws Exception{

        Message m = checkMatches(matches_id);
        if(m.getErrcode() != 0){
            return m;
        }

        //传入参数(team_id,name,phone,yzm)
        PageData pd = this.getPageData();

        //判断手机验证码是否正确
        m = matchesService.checkYzm(pd);
        if(m.getErrcode() != 0){
            return m;
        }

        //检查手机号是否已报名
        m = matchesService.checkPhoneIsEnroll(matches_id, pd.getString("phone"));
        if(m.getErrcode() != 0){
            return m;
        }

        //加入队伍
        return matchesService.joinTeam(matches_id, pd);
    }


    /**
     * 加载 赛事 & 我的报名信息
     * @param matches_id 赛事id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadMyEnroll")
    public Message loadMyEnroll(@RequestParam(value = "matches_id") String matches_id) throws Exception{

        Message m = checkMatches(matches_id);
        if(m.getErrcode() != 0){
            return m;
        }

        return matchesService.loadMyEnroll(matches_id);
    }


    /**
     * 加载我的队伍信息
     * @param team_id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadMyTeam")
    public Message loadMyTeam(@RequestParam(value = "team_id") String team_id) throws Exception{

        PageData pdTeam = enrollTeamService.findById(team_id);

        if(StringUtil.isNotEmpty(pdTeam)){
            PageData pdMatches = matchesService.findById(pdTeam.getString("matches_id"));
            pdMatches = MatchesServiceImpl.getStatus(pdMatches);
            pdTeam.put("status", pdMatches.get("status"));
        }


        return Message.ok().addData("pd", pdTeam);
    }


    /**
     * 加载我的队伍信息 & 报名信息
     * @param matches_id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadMyAllTeam")
    public Message loadMyAllTeam(@RequestParam(value = "matches_id") String matches_id) throws Exception{

        return enrollService.loadMatchesEnroll(matches_id, 1, 1);
    }


    /**
     * 创建组队报名的队伍（多个人一起报名）
     * @param matches_id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/buildTeam")
    public Message buildTeam(@RequestParam(value = "matches_id") String matches_id
                             ,@RequestParam(value = "name") String[] nameArr
                             ,@RequestParam(value = "phone") String[] phoneArr
                             ,@RequestParam(value = "team_name") String team_name
                             ,@RequestParam(value = "team_description") String team_description
                             ) throws Exception{

        Message m = checkMatches(matches_id);
        if(m.getErrcode() != 0){
            return m;
        }

        //传入参数(enrollList,phone,yzm,team_description)
        PageData pd = new PageData();
        pd.put("phone", phoneArr[0]);
        pd.put("yzm", this.getRequest().getParameter("yzm"));
        //判断手机验证码是否正确
        m = matchesService.checkYzm(pd);
        if(m.getErrcode() != 0){
            return m;
        }

        //检查手机号是否已报名
        for (int i = 0; i < phoneArr.length; i++) {
            m = matchesService.checkPhoneIsEnroll(matches_id, phoneArr[i]);
            if(m.getErrcode() != 0){
                return m;
            }
        }


        pd.put("team_name", team_name);
        pd.put("team_description", team_description);
        pd.put("matches_id", matches_id);

        System.out.println("asdkfjasldfj::::::::::::::::::" + pd.toString());
        return matchesService.buildTeam(pd, nameArr, phoneArr);
    }


    /**
     * 单人报名
     * @param matches_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/oneEnroll")
    public Message oneEnroll(@RequestParam(value = "matches_id") String matches_id) throws Exception{
        Message m = checkMatches(matches_id);
        if(m.getErrcode() != 0){
            return m;
        }

        //传入参数(enrollList,phone,yzm)
        PageData pd = this.getPageData();
        //判断手机验证码是否正确
        m = matchesService.checkYzm(pd);
        if(m.getErrcode() != 0){
            return m;
        }

        //检查手机号是否已报名
        m = matchesService.checkPhoneIsEnroll(matches_id, pd.getString("phone"));
        if(m.getErrcode() != 0){
            return m;
        }

        return matchesService.saveOneEnroll(matches_id, pd);
    }


    /**
     * 退出队伍
     * @param matches_id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/quitTeam")
    public Message quitTeam(@RequestParam(value = "matches_id") String matches_id) throws Exception{


        return enrollService.quitTeam(matches_id);
    }


    /**
     * 检查赛事是否存在
     * @param matches_id
     * @return
     * @throws Exception
     */
    private Message checkMatches(String matches_id) throws Exception{
        PageData pdMatches = matchesService.findById(matches_id);
        if(pdMatches == null){
            return Message.error("该赛事不存在");
        }
        if(pdMatches.get("state").toString().equals("0")){
            return Message.error("该赛事尚未发布");
        }
        if(pdMatches.get("d_state").toString().equals("1")){
            return Message.error("该赛事已删除");
        }



        return Message.ok().addData("pdMatches", pdMatches);
    }


    /**
     * 检查用户是否报名
     * @return
     * @throws Exception
     */
    private Message checkIsEnroll(String matches_id) throws Exception{
        User user = this.getUser();

        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("matches_id", matches_id);

        pd = enrollService.findByUserIdAndMatchesId(pd);
        if(pd != null){
            String url = "";
            if(StringUtil.isEmpty(pd.get("team_type")) || pd.get("team_type").toString().equals("0")){
                url = "redirect:/wxMatches/goOneEnroll.do?" +
                        "matches_id="+ matches_id;
            }else if (pd.get("team_type").toString().equals("1")){
                url = "redirect:/wxMatches/goBuildTeam.do?" +
                        "matches_id="+ matches_id;
            }else if (pd.get("team_type").toString().equals("2")){
                if(pd.get("position").toString().equals("1")){
                    url = "redirect:/wxMatches/goCreateTeam.do?" +
                            "matches_id="+ matches_id;
                }else if(pd.get("position").toString().equals("2")){
                    url = "redirect:/wxMatches/goChooseTeam.do?" +
                            "matches_id="+ matches_id;
                }
            }

            return Message.error("您已报名").addData("url", url);
        }

        return Message.ok();
    }


}
