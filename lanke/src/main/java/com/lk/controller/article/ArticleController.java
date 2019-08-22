package com.lk.controller.article;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.article.ArticleService;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author shkstart
 * @create 2018-10-23 14:27
 */
@Controller
@RequestMapping(value = "/articleLib")
public class ArticleController extends BaseController {

    private static String menu_url = "articleLib/list.do";
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;


    /*****************************************************文章库列表--start***********************************************/
    /**
     * 前往文章库列表页面
     * @param page
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page){
        ModelAndView mv = this.getModelAndView();

        //判断用户的角色
        //揽客运营者（可新增、编辑、删除文章；可新增、编辑、删除分类；可显示草稿箱）
        //网吧运营者（可操作图文合成器）
        User user = getUser();//得到用户

        String role = "";
        if(user.getINTENET_ID().equals("d29ef1b0c9e3401ebd2fe8dbbcdec409")){
            //揽客运营者
            role = "lanker";
        }else{
            //网吧运营者
            role = "internet";

        }

        //草稿箱的数量
        PageData pdSize = new PageData();
        pdSize.put("status", "2");
        pdSize.put("user_id", user.getUSER_ID());
        Integer drafts = 0;
        Integer synthesizer = 0;
        try {
            drafts = articleService.articleSize(pdSize);
            synthesizer = articleService.synthesizerSize(pdSize);

            List<PageData> categoryList = articleService.categoryList(pdSize);
            mv.addObject("categoryList", categoryList);

        } catch (Exception e) {
            e.printStackTrace();
        }



        mv.addObject("drafts", drafts);
        mv.addObject("synthesizer", synthesizer);
        mv.addObject("role", role);
        mv.setViewName("weixin/articleLib/lib_list");
        return mv;
    }


    /**
     * 分页加载文章列表
     * @param page
     * @return
     */
    @RequestMapping(value = "/loadArticle")
    @ResponseBody
    public JSONObject loadArticle(Page page){
        JSONObject result = new JSONObject();

        String pre = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" +
                this.getRequest().getServerPort() + this.getRequest().getContextPath()+"/";
        //传入参数（搜索，分类，状态，分页）
        PageData pd = this.getPageData();
        pd.put("pre", pre);
        pd.put("status", "1");
        try{
            result = articleService.articleList(pd,page);
        }catch (Exception e){
            log.info("loadArticle===数据加载失败");
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 前往新增或编辑文章页面
     * @return
     */
    @RequestMapping(value = "/goEditArticle")
    public ModelAndView goEditArticle(){
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        String operation = pd.getString("operation");
        String pre = this.getPre();

        try {
            User user = getUser();//得到用户
            mv.addObject("username", user.getUSERNAME());

            //加载分类列表
            List<PageData> categoryList = articleService.categoryList(pd);
            mv.addObject("categoryList", categoryList);

            if(operation.equals("add")){
                mv.addObject("operation", "新增");
            }else if(operation.equals("edit")){

                pd.put("pre", pre);
                PageData pdArticle = articleService.findById(pd);
                mv.addObject("pd", pdArticle);
                mv.addObject("operation", "编辑");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("weixin/articleLib/article_edit");
        return mv;
    }


    /**
     * 保存新增或修改文章
     * @return
     */
    @RequestMapping(value = "/saveArticle")
    @ResponseBody
    public JSONObject saveArticle(){
        JSONObject result = new JSONObject();

        PageData pd = this.getPageData();

        try {
            result = articleService.saveArticle(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 删除文章
     * @return
     */
    @RequestMapping(value = "/deleteArticle")
    @ResponseBody
    public JSONObject deleteArticle(){
        JSONObject result = new JSONObject();

        //传入参数
        PageData pd = this.getPageData();

        try {
            result = articleService.deleteArticle(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 查看文章
     * @return
     */
    @RequestMapping(value = "/article")
    public ModelAndView article(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();

        String pre = this.getPre();
        try {
            pd.put("pre", pre);
            pd.put("id", pd.getString("article_id"));
            pd = articleService.findById(pd);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("weixin/articleLib/showArticle");
        return mv;
    }

    /**
     * 手机端查看文章
     * @return
     */
    @RequestMapping(value = "/phoneArticle")
    public ModelAndView phoneArticle(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();

        String pre = this.getPre();
        try {
            pd.put("id", pd.getString("article_id"));
            pd = articleService.findById(pd);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("weixin/articleLib/showPhoneArticle");
        return mv;
    }


    @RequestMapping(value = "draftsArticle")
    public ModelAndView draftsArticle(){
        ModelAndView mv = this.getModelAndView();

        mv.setViewName("weixin/articleLib/drafts_article");
        return mv;
    }

    @RequestMapping(value = "/loadDraftsArticle")
    @ResponseBody
    public JSONObject loadDraftsArticle(){
        JSONObject result = new JSONObject();

        String pre = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" +
                this.getRequest().getServerPort() + this.getRequest().getContextPath()+"/";
        //传入参数（状态）
        PageData pd = this.getPageData();
        pd.put("pre", pre);

        try{
            result = articleService.articleList(pd);
        }catch (Exception e){
            log.info("loadArticle===数据加载失败");
            e.printStackTrace();
        }

        return result;
    }

    /*****************************************************文章库列表--end***********************************************/


    /*****************************************************文章类型--start***********************************************/
    /**
     * 前往新增或编辑文章类型
     * @return
     */
    @RequestMapping(value = "/goEditCategory")
    public ModelAndView goEditCategory(){
        ModelAndView mv = this.getModelAndView();

        mv.setViewName("weixin/articleLib/category_edit");
        return mv;
    }

    /**
     * 加载文章类型
     * @return
     */
    @RequestMapping(value = "/loadCategory")
    @ResponseBody
    public JSONObject loadCategory() {
        JSONObject result = new JSONObject();

        PageData pd = this.getPageData();

        try {
            List<PageData> categoryList = articleService.categoryList(pd);
            result.put("result", "true");
            result.put("categoryList", categoryList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 保存新增或编辑的文章类型
     * @return
     */
    @RequestMapping(value = "/saveCategory")
    @ResponseBody
    public JSONObject saveCategory(){
        JSONObject result = new JSONObject();

        //传入参数
        PageData pd = this.getPageData();

        try {
            result = articleService.saveCategory(pd);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "false");
            result.put("message", "-1，系统繁忙，请稍后再试");
        }


        return result;
    }


    /**
     * 删除文章类型
     * @return
     */
    @RequestMapping(value = "/deleteCategory")
    @ResponseBody
    public JSONObject deleteCategory(){
        JSONObject result = new JSONObject();

        PageData pd = this.getPageData();

        try {
            result = articleService.deleteCategory(pd);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "false");
            result.put("message", "-1，系统繁忙，请稍后再试");
        }

        return result;
    }



    /*****************************************************文章类型--end*************************************************/


    /*****************************************************图文合成器--start**********************************************/

    /**
     * 加载图文合成器的图文
     * @return
     */
    @RequestMapping(value = "/loadSynthesizer")
    @ResponseBody
    public JSONObject loadSynthesizer(){
        JSONObject result = new JSONObject();

        String pre = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" +
                this.getRequest().getServerPort() + this.getRequest().getContextPath()+"/";

        PageData pd = this.getPageData();
        pd.put("pre", pre);
        try {
            result = articleService.loadSynthesizer(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 将图文添加进图文合成器
     * @return
     */
    @RequestMapping(value = "/addNews")
    @ResponseBody
    public JSONObject addNews(){
        JSONObject result = new JSONObject();

        PageData pd = this.getPageData();

        try {
            result = articleService.saveNews(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    /**
     * 去除图文合成器中的一篇or多篇文章
     * @return
     */
    @RequestMapping(value = "/delNews")
    @ResponseBody
    public JSONObject delNews(){
        JSONObject result = new JSONObject();

        PageData pd = this.getPageData();

        try {
            result = articleService.deleteNews(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    /**
     * 上下移动图文合成器中的文章（排序）
     * @return
     */
    @RequestMapping(value = "/moveNews")
    @ResponseBody
    public JSONObject moveNews(){
        JSONObject result = new JSONObject();

        PageData pd = this.getPageData();


        try {
            result = articleService.updateMoveNews(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 统计图文使用数
     * @return
     */
    @RequestMapping(value = "/totalNumber")
    @ResponseBody
    public JSONObject totalNumber(){
        JSONObject result = new JSONObject();

        PageData pd = this.getPageData();

        try {
            result = articleService.totalNumber(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /*****************************************************图文合成器--end***********************************************/


    /**
     * 上传图片
     * @return
     */
    @RequestMapping(value = "/uploadImg")
    @ResponseBody
    public JSONObject uploadImg(){
        JSONObject result = new JSONObject();

        String pre = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" +
                this.getRequest().getServerPort() + this.getRequest().getContextPath()+"/";
        PageData pd = this.getPageData();
        pd.put("pre", pre);


        try {
            result = articleService.uploadImg(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
