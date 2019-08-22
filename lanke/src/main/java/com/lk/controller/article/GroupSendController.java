package com.lk.controller.article;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.service.article.GroupSendService;
import com.lk.util.PageData;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author myq
 * @description 后台代网吧执行公众号群发
 * @create 2019-01-11 15:17
 */
@Controller
@RequestMapping("/groupSend")
public class GroupSendController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(GroupSendController.class);
    //菜单连接
    String menuUrl = "groupSend/list.do";

    @Autowired
    private GroupSendService groupSendService;


    /**
     * 首页
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(){
        ModelAndView mv = this.getModelAndView();

        mv.setViewName("/weixin/groupSend/list");
        return mv;
    }

    /**
     * 加载“首页”的数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/loadIndex")
    public JSONObject loadIndex(Page page) throws Exception{

        //传入参数（keywords）
        PageData pd = this.getPageData();

        return groupSendService.loadIndex(pd, page);
    }


    /**
     * 新增图文
     * @return
     */
    @RequestMapping("/addNews")
    public ModelAndView addNews() {
        ModelAndView mv = this.getModelAndView();

        logger.info("新增图文");
        mv.setViewName("/weixin/groupSend/news_edit");
        return mv;
    }


    /**
     * 编辑图文
     * @return
     */
    @RequestMapping("/editNews")
    public ModelAndView editNews() {
        ModelAndView mv = this.getModelAndView();

        logger.info("编辑图文");
        mv.addObject("flag", this.getRequest().getParameter("flag"));
        mv.addObject("sendRecordId", this.getRequest().getParameter("id"));
        mv.setViewName("/weixin/groupSend/news_edit");
        return mv;
    }


    /**
     * 加载“新增/编辑图文”的数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/loadNews")
    public JSONObject loadNews() throws Exception{
        logger.info("加载“新增/编辑图文”的数据");

        //传入参数（id）
        PageData pd = this.getPageData();

        return groupSendService.loadNews(pd);
    }

    @ResponseBody
    @RequestMapping("/delNews")
    public JSONObject delNews() throws Exception{
        logger.info("删除记录");

        //传入参数（id）
        PageData pd = this.getPageData();

        return groupSendService.delNews(pd);
    }


    /**
     * 保存图文
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/saveNews")
    public JSONObject saveNews() throws Exception{

        //传入参数（id,item）
        PageData pd = this.getPageData();
        return groupSendService.saveNews(pd);
    }


    /**
     * 预览
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/preview")
    public JSONObject preview(HttpServletRequest request) throws Exception{

        //传入参数（id,当前图文item[sequence]）
        PageData pd = this.getPageData();
        String pre = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        pd.put("pre", pre);

        return groupSendService.preview(pd);
    }


    /**
     * 去设置群发
     * @return
     */
    @RequestMapping("/goGroupSend")
    public ModelAndView goGroupSend() throws Exception{
        ModelAndView mv = this.getModelAndView();

        //传入参数（id），获取已经授权的公众号列表
        PageData pd = this.getPageData();
        JSONObject result = groupSendService.getInternetList(pd);

        mv.addObject("id", pd.getString("id"));
        mv.addObject("result", result);
        mv.setViewName("/weixin/groupSend/group_send");
        return mv;
    }


    /**
     * 群发
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/groupSend")
    public JSONObject groupSend(HttpServletRequest request) throws Exception{

        //传入参数（id，网吧id列表）
        PageData pd = this.getPageData();
        String pre = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        pd.put("pre", pre);
        String[] a = this.getRequest().getParameterValues("internet_id");
        pd.put("internetList", a);

        return groupSendService.groupSend(pd);
    }



    /**
     * 上传图片
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/uploadImg")
    public JSONObject uploadImg() throws Exception{
        // base64图片
        String file = this.getRequest().getParameter("upFile");

        return groupSendService.saveImgs(file);
    }



    /****************************************图文消息--分割线****************************************/




    /**
     * 加载模板消息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/loadMessageIndex")
    public JSONObject loadMessageIndex(Page page) throws Exception{

        //传入参数（keywords）
        PageData pd = this.getPageData();

        return groupSendService.loadMessageIndex(pd, page);
    }


    /**
     * 新增模板
     * @return
     */
    @RequestMapping("/addMessage")
    public ModelAndView addMessage() {
        ModelAndView mv = this.getModelAndView();

        mv.setViewName("/weixin/groupSend/message_edit");
        return mv;
    }


    /**
     * 编辑模板
     * @return
     */
    @RequestMapping("/editMessage")
    public ModelAndView editMessage() throws Exception{
        ModelAndView mv = this.getModelAndView();

        //传入参数（id）
        PageData pd = this.getPageData();

        JSONObject result = groupSendService.findByMessageId(pd);

        JSONObject pdMessage = result.getJSONObject("pd");
        pdMessage.put("keyword1", JSONObject.fromObject(pdMessage.getString("keyword_data")).getString("keyword1"));
        pdMessage.put("keyword2", JSONObject.fromObject(pdMessage.getString("keyword_data")).getString("keyword2"));
        pdMessage.put("keyword3", JSONObject.fromObject(pdMessage.getString("keyword_data")).getString("keyword3"));

        mv.addObject("pd", pdMessage);
        mv.setViewName("/weixin/groupSend/message_edit");
        return mv;
    }


    /**
     * 保存模板消息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/saveMessage")
    public JSONObject saveMessage() throws Exception{

        //传入参数（）
        PageData pd = this.getPageData();

        return groupSendService.saveMessage(pd);
    }

    /**
     * 删除模板消息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/delMessage")
    public JSONObject delMessage() throws Exception{

        //传入参数（）
        PageData pd = this.getPageData();

        return groupSendService.delMessage(pd);
    }

    /**
     * 群发模板消息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/sendMessage")
    public JSONObject sendMessage() throws Exception{


        //传入参数（）
        PageData pd = this.getPageData();
        String[] a = this.getRequest().getParameterValues("internet_id");
        pd.put("internetList", a);

        return groupSendService.sendMessage(pd);
    }



    /****************************************群发记录--分割线****************************************/



    /**
     * 加载模板消息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/loadRecordIndex")
    public JSONObject loadRecordIndex(Page page) throws Exception{

        //传入参数（keywords）
        PageData pd = this.getPageData();

        System.out.println(pd.toString()+"================");

        return groupSendService.loadRecordIndex(pd, page);
    }



}
