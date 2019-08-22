package com.lk.controller.internet.matches;

import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.internet.matches.MatchesService;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.intenet.impl.IntenetService;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author myq
 * @description 赛事管理-控制层
 * @create 2019-05-21 11:24
 */
@Controller
@RequestMapping(value = "/matches")
public class MatchesController extends BaseController {

    @Autowired
    private MatchesService matchesService;

    @Resource(name = "intenetService")
    private IntenetManager internetService;


    @Resource(name = "indexMemberService")
    private IndexMemberManager indexMemberService;


    @Resource(name = "sysuserService")
    private SysUserManager sysuserService;


    /******************************************* 请求页面 ********************************/

    /**
     * 赛事列表页面
     * @return
     */
    @RequestMapping(value = "/goList")
    public ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();

        mv.addObject("pd", this.getPageData());
        mv.setViewName("internet/matches/matches_list");
        return mv;
    }


    /**
     * 编辑页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();

        User user = this.getUser();//得到用户
        pd.put("username", user.getNAME());

        mv.addObject("pd", pd);
        mv.setViewName("internet/matches/matches_edit");
        return mv;
    }

    /**
     * 去分享页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goMatchesShare")
    public ModelAndView goMatchesShare() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();

        //判断是否关注公众号,并把user放入缓存
        if(StringUtil.isNotEmpty(pd.getString("open_id"))){
            sysuserService.getUserByOpenId(pd.getString("open_id"));
        }else{
            indexMemberService.checkUserSubscribe(pd.getString("appid"), pd.getString("code"), "qr_subscribe_", "");//无场景值
        }


        String state = pd.getString("state");
        if(state.equals("matches_share")){
            mv.setViewName("internet/matches/matches_share");
        }else if(state.equals("matches_preview")){
            mv.setViewName("intenetmumber/matches/gameCenter");
        }

        String type_url = "matches_" + state + "_url";
        Session session = Jurisdiction.getSession();
        if(session.getAttribute(type_url) != null){
            session.removeAttribute(type_url);
        }

        mv.addObject("pd", pd);
        return mv;
    }


    /******************************************* 赛事请求方法 ********************************/

    /**
     * 赛事分页列表
     * @param page
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public LayMessage getList(Page page) throws Exception{
        PageData pd = this.getPageData();

        return matchesService.getList(pd, page);
    }


    /**
     * 根据主键id获取数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getById")
    public Message getById() throws Exception {
        PageData pd = this.getPageData();

        String flag = pd.getString("flag");
        pd = matchesService.getById(pd.getString("id"));
        if(pd == null){
            return Message.error("找不到数据");
        }

        if(StringUtil.isNotEmpty(flag) && flag.equals("matches_share")){
            pd.put("INTENET_ID", pd.getString("internet_id"));
            PageData pdInternet = internetService.findById(pd);
            pd.put("internet_img", pdInternet.getString("HEAD_IMG"));
        }

        return Message.ok().addData("pd", pd);
    }


    /**
     * 新增/编辑赛事
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveMatches")
    public Message saveMatches() throws Exception {
        PageData pd = this.getPageData();

        String pre = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" +
                this.getRequest().getServerPort() + this.getRequest().getContextPath() + "/";
        pd.put("pre", pre);

        if(pd.getString("store_ids").split(",").length < 1){
            return Message.error("请选择门店");
        }

        return matchesService.saveMatches(pd);
    }


    /**
     * 删除赛事
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/delMatches")
    public Message delMatches() throws Exception {
        PageData pd = this.getPageData();

        return matchesService.delMatches(pd);
    }


    /**
     * 获取分享或赛事预览的二维码
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getQrCode")
    public Message getQrCode() throws Exception{

        //传入参数(matches_id)
        PageData pd = this.getPageData();
        pd.put("pre", this.getPre());
        pd.put("internet_id", this.getUser().getINTENET_ID());

        return matchesService.getQrCode(pd);
    }


    /******************************************* 可通用方法 ********************************/

    /**
     * 选择门店页面
     * @return
     */
    @RequestMapping(value = "/goChooseStore")
    public ModelAndView goChooseStore(){
        ModelAndView mv = this.getModelAndView();

        mv.addObject("pd", this.getPageData());
        mv.setViewName("internet/matches/choose_store");
        return mv;
    }

    /**
     * 加载可用的门店列表 //默认查找有效的门店，加v门店，开通银联门店，开通嘉联门店
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/chooseStore")
    public Message chooseStore() throws Exception {
        PageData pd = this.getPageData();//type=pub_yl_jl  noType=noPub_noYl_noJl

        return matchesService.chooseStore(pd);
    }


    /**
     * 去可选择赛事列表页面
     * @return
     */
    @RequestMapping(value = "/goSelMatches")
    public ModelAndView goSelMatches(){
        ModelAndView mv = this.getModelAndView();

        mv.setViewName("internet/msgManager/matches_list");
        return mv;
    }

    /**
     * 查找赛事列表方法
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/selMatchesList")
    public Message selMatchesList() throws Exception{
        PageData pd = this.getPageData();


        User user = this.getUser();
        pd.put("internet_id", user.getINTENET_ID());

        return matchesService.selMatchesList(pd);
    }






}
