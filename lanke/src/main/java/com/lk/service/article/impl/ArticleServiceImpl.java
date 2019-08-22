package com.lk.service.article.impl;

import com.alibaba.druid.sql.visitor.functions.Lpad;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.article.ArticleService;
import com.lk.service.system.internetPictures.impl.InternetPicturesService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.*;
import com.lk.wechat.util.WechatMaterialUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.swing.StringUIClientPropertyKey;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author shkstart
 * @create 2018-10-25 11:19
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static String url = "articleLib/article?article_id=";
    private static String phone_url = "articleLib/phoneArticle?article_id=";

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Autowired
    private AutoReplyService autoReplyService;

    @Autowired
    private InternetPicturesService internetPicturesService;

    @Override
    public JSONObject articleList(PageData pd, Page page) throws Exception{
        JSONObject result = new JSONObject();
        page.setPd(pd);
        List<PageData> articleList = (List<PageData>) dao.findForList("ArticleLibsMapper.articlelistPage",page);
        
        //拼接文章链接
        for (PageData pdArticle : articleList) {
            String article_url = pd.getString("pre") + url + pdArticle.getString("id");
            String picture_url = replaceLanker(pd.getString("pre")) + Const.FILEPATHIMG +pdArticle.getString("path");
            pdArticle.put("article_url", article_url);
            pdArticle.put("picture_url", picture_url);
            if(pdArticle.get("upload_time") != null){
                pdArticle.put("upload_time", pdArticle.get("upload_time").toString().substring(0,10));
            }
        }

        result.put("data", articleList);
        result.put("count", page.getTotalResult());
        result.put("msg", "查询成功");
        result.put("showCount", page.getShowCount());
        result.put("code", "0");
        return result;
    }

    @Override
    public JSONObject articleList(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        List<PageData> articleList = (List<PageData>) dao.findForList("ArticleLibsMapper.articlelist",pd);
        //拼接文章链接

        for (PageData pdArticle : articleList) {
            String article_url = pd.getString("pre") + url + pdArticle.getString("id");
            String picture_url = replaceLanker(pd.getString("pre")) + Const.FILEPATHIMG +pdArticle.getString("path");
            pdArticle.put("article_url", article_url);
            pdArticle.put("picture_url", picture_url);
            pdArticle.put("update_time", pdArticle.get("update_time").toString().substring(0,19));
            if(pdArticle.get("upload_time") != null){
                pdArticle.put("upload_time", pdArticle.get("upload_time").toString().substring(0,10));
            }

        }

        result.put("articleList", articleList);
        result.put("result", "true");
        result.put("message", "查询成功");
        return result;
    }

    @Override
    public JSONObject saveArticle(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        if(user == null){
            result.put("result", "false");
            result.put("message", "无效的用户");
            return result;
        }

        //判断保存的数据是否完整(上传时判断)
        result = checkArticleParams(result,pd);
        if(result.getString("result").equals("false")){
            return result;
        }

        if(StringUtil.isEmpty(pd.getString("popularity"))){
            pd.put("popularity",0.0);
        }else{
            pd.put("popularity",Double.parseDouble(pd.getString("popularity")));
        }
        String status = pd.getString("status");
        if(StringUtil.isNotEmpty(pd.getString("id"))){
            //修改
            if(pd.getString("status").equals("1")){
                pd.put("upload_time", Tools.date2Str(new Date()));
            }

            PageData pda = new PageData();
            pda.put("id", pd.get("id"));
            pda = findById(pda);
            if(pda != null && StringUtil.isNotEmpty(pda.get("status"))){
                //已上传的状态不更改
                if(pda.get("status").toString().equals("1"))
                    pd.remove("status");
            }

            pd.put("update_time", Tools.date2Str(new Date()));

            dao.update("ArticleLibsMapper.edit", pd);
        }else{
            //新增

            pd.put("id", StringUtil.get32UUID());
            pd.put("create_time", Tools.date2Str(new Date()));
            pd.put("update_time", Tools.date2Str(new Date()));
            pd.put("used_number", 0);
            if(pd.getString("status").equals("1")){
                pd.put("upload_time", Tools.date2Str(new Date()));
            }


            dao.save("ArticleLibsMapper.save", pd);

        }
        //保存图片
        String picture_url = pd.getString("picture_url");
        if(StringUtil.isNotEmpty(picture_url)){
            PageData pdPicture = new PageData();
            pdPicture.put("PICTURE_ID", pd.getString("picture_id"));
            pdPicture = internetPicturesService.findById(pdPicture);
            if(pdPicture == null){
                pdPicture = new PageData();
                pdPicture.put("PICTURE_ID", pd.getString("picture_id"));
                pdPicture.put("NAME", picture_url.split("/")[picture_url.split("/").length-1]);

                pdPicture.put("PATH", user.getINTENET_ID() + picture_url.split(user.getINTENET_ID())[1]);
                pdPicture.put("CREATE_TIME", Tools.date2Str(new Date()));
                pdPicture.put("INTERNET_ID", user.getINTENET_ID());
                internetPicturesService.save(pdPicture);
            }else{
                pdPicture.put("PATH", pd.getString("path"));
                internetPicturesService.edit(pdPicture);
            }
        }

        result.put("id",pd.getString("id"));
        result.put("result", "true");
        result.put("message", "保存成功");
        System.out.println("status==================="+pd.getString("status"));
        if(status.equals("1")){
            result.put("message", "发布成功");
        }
        return result;
    }

    private JSONObject checkArticleParams(JSONObject result, PageData pd){
        if(pd.getString("status").equals("2")){
            //仅保存时，不判断
            result.put("result", "true");
            return result;
        }

        if(StringUtil.isEmpty(pd.getString("category_id"))){
            result.put("result", "false");
            result.put("message", "请选择分类类型");
            return result;
        }

        if(StringUtil.isEmpty(pd.getString("popularity"))){
            result.put("result", "false");
            result.put("message", "请选择推荐度");
            return result;
        }

        result.put("result", "true");
        return result;
    }

    @Override
    public JSONObject deleteArticle(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        if(StringUtil.isEmpty(pd.getString("id"))){
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }

        pd = this.findById(pd);
        if(StringUtil.isEmpty(pd)){
            result.put("result", "false");
            result.put("message", "参数错误");
            return result;
        }

        PageData pdArticle = new PageData();
        pdArticle.put("id", pd.getString("id"));
        pdArticle.put("status", 3);//删除
        int number = (int) dao.update("ArticleLibsMapper.edit", pdArticle);
        if(number == 0){
            result.put("result", "false");
            result.put("message", "删除失败");
            return result;
        }

        result.put("result", "true");
        result.put("message", "删除成功");
        return result;
    }







    @Override
    public JSONObject saveCategory(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        PageData pdCategory = new PageData();

        //判断名称是否有重复
        pdCategory.put("name", pd.getString("name"));
        pdCategory = (PageData) dao.findForObject("ArticleCategoryMapper.findByName", pdCategory);

        if(StringUtil.isNotEmpty(pd.getString("id"))){
            //修改
            if (pdCategory != null  && !pdCategory.getString("id").equals(pd.getString("id"))) {
                result.put("result", "false");
                result.put("message", "该名称已存在");
                return result;
            }
            pdCategory = new PageData();
            pdCategory.put("category_id", pd.getString("id"));
            pdCategory.put("status", "1");//已上传
            int size = (int) dao.findForObject("ArticleLibsMapper.articleSize", pdCategory);
            pdCategory.put("id", pd.getString("id"));
            pdCategory = (PageData) dao.findForObject("ArticleCategoryMapper.findById",pdCategory);
            //序号可以改，名称不能改
            //文章已删除，可以修改类型
            if(size > 0 && !pdCategory.getString("name").equals(pd.getString("name"))){
                result.put("result", "false");
                result.put("message", "该类型已被使用，不能编辑名称");
                return result;
            }
            dao.update("ArticleCategoryMapper.edit", pd);

        }else{
            //新增
            if(StringUtil.isNotEmpty(pdCategory)){
                result.put("result", "false");
                result.put("message", "该名称已存在");
                return result;
            }
            pd.put("id", StringUtil.get32UUID());

            dao.save("ArticleCategoryMapper.save", pd);

        }

        result.put("id", pd.getString("id"));
        result.put("result", "true");
        result.put("message", "保存成功");

        return result;
    }

    @Override
    public JSONObject deleteCategory(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        if(StringUtil.isEmpty(pd.getString("id"))){
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }
        pd.put("category_id", pd.getString("id"));
        pd.put("status", "1");//已上传
        int size = (int) dao.findForObject("ArticleLibsMapper.articleSize", pd);
        if(size > 0){
            result.put("result", "false");
            result.put("message", "该类型已被使用，不能删除");
            return result;
        }

        int number = (int) dao.delete("ArticleCategoryMapper.delete", pd);
        if(number == 0){
            result.put("result", "false");
            result.put("message", "参数错误");
            return result;
        }

        result.put("result", "true");
        result.put("message", "删除成功");
        return result;
    }







    @Override
    public JSONObject loadSynthesizer(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        if(user == null){
            result.put("result", "false");
            result.put("message", "无效的用户");
            return result;
        }

        pd.put("user_id", user.getUSER_ID());

        List<PageData> list = (List<PageData>) dao.findForList("ArticleSynthesizerMapper.listByUser", pd);
        for(PageData pdd : list){
            pdd.put("picture_url", replaceLanker(pd.getString("pre")) + Const.FILEPATHIMG + pdd.getString("path"));
        }

        //图文合成器确定，跳转新建图文，加载文章列表
        if(StringUtil.isNotEmpty(pd.getString("flag")) &&
                pd.getString("flag").equals("synthesizer")){
            //由图文合成器转发过来
            String token = autoReplyService.getAuthToken(user.getINTENET_ID());
            for (PageData pda : list) {
                //上传图片到微信服务器
                String path = pda.getString("path");
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;
                JSONObject pictureResult = WechatMaterialUtil.addMaterialEver(token, filePath, "image");
                if(StringUtil.isNotEmpty(pictureResult.get("media_id"))){

                    PageData pdPicture = new PageData();
                    pdPicture.put("PICTURE_ID", StringUtil.get32UUID());
                    pdPicture.put("NAME", path.split("/")[path.split("/").length-1]);
                    pdPicture.put("PATH", path);
                    pdPicture.put("CREATE_TIME", Tools.date2Str(new Date()));
                    pdPicture.put("INTERNET_ID", user.getINTENET_ID());
                    pdPicture.put("MEDIA_ID", pictureResult.get("media_id"));
                    pdPicture.put("URL", pictureResult.get("url"));
                    internetPicturesService.save(pdPicture);

                    pda.put("THUMB_MEDIA_ID", pictureResult.get("media_id"));
                }

                pda.put("TITLE", pda.getString("title"));
                pda.put("PATH", path);
                pda.put("CONTENT", pda.get("content"));
                pda.put("CREATE_USER", pda.getString("author"));
                pda.put("DIGEST", pda.getString("digest"));

            }

            PageData pdMaterial = new PageData();
            pdMaterial.put("TITLE", list.get(0).getString("title"));
            pdMaterial.put("PATH", list.get(0).getString("path"));
            pdMaterial.put("THUMB_MEDIA_ID", list.get(0).get("THUMB_MEDIA_ID"));
            pdMaterial.put("CONTENT", list.get(0).get("content"));
            pdMaterial.put("CREATE_USER", list.get(0).getString("author"));
            pdMaterial.put("DIGEST", list.get(0).getString("digest"));
            pdMaterial.put("CONTENT", list.get(0).get("content"));
            result.put("pdMaterial", pdMaterial);

        }

        result.put("list", list);
        result.put("result", "true");

        return result;
    }

    @Override
    public JSONObject saveNews(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        if(user == null){
            result.put("result", "false");
            result.put("message", "无效的用户");
            return result;
        }

        if (StringUtil.isEmpty(pd.getString("article_id"))) {
            result.put("result", "false");
            result.put("message", "无效的参数");
            return result;
        }
        PageData pdd = new PageData();


        //判断是否是同一篇图文
        pdd.put("user_id", user.getUSER_ID());
        pdd.put("article_id", pd.getString("article_id"));
        pdd = (PageData) dao.findForObject("ArticleSynthesizerMapper.findByArticleId", pdd);
        if(pdd != null){
            result.put("result", "false");
            result.put("message", "不能添加同一篇文章");
            return result;
        }

        pdd = new PageData();
        pdd.put("user_id", user.getUSER_ID());
        pdd = (PageData) dao.findForObject("ArticleSynthesizerMapper.findOneByUserId", pdd);
        int sequence = 0;
        if(pdd != null && pdd.get("sequence").toString().equals("8")){
            result.put("result", "false");
            result.put("message", "最多添加八篇图文");
            return result;
        }
        if(pdd != null && StringUtil.isNotEmpty(pdd.get("sequence"))){
            sequence = Integer.parseInt(pdd.get("sequence").toString());
        }

        pd.put("id", StringUtil.get32UUID());
        pd.put("sequence", sequence+1);
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        int number = (int) dao.save("ArticleSynthesizerMapper.save", pd);
        if(number == 0){
            result.put("result", "false");
            result.put("message", "图文添加失败");
            return result;
        }

        result.put("result", "true");
        result.put("message", "添加成功");
        result.put("sequence", sequence + 1);
        return result;
    }

    @Override
    public JSONObject deleteNews(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        if(user == null){
            result.put("result", "false");
            result.put("message", "无效的用户");
            return result;
        }
        if (StringUtil.isEmpty(pd.getString("flag"))){
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }

        if (pd.getString("flag").equals("delOne") &&
                StringUtil.isEmpty(pd.getString("sequence")) &&
                    StringUtil.isEmpty(pd.getString("article_id"))) {
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }

        pd.put("user_id", user.getUSER_ID());
        int number = (int) dao.delete("ArticleSynthesizerMapper.delByUserId", pd);
        if(number == 0){
            result.put("result", "false");
            result.put("message", "数据不存在");
            return result;
        }

        //如果是删除单篇，修改后续的序号(往上移动)
        List<PageData> list = (List<PageData>) dao.findForList("ArticleSynthesizerMapper.findLasts", pd);
        for(PageData pda : list){
            int sequence = Integer.parseInt(pda.get("sequence").toString()) - 1;
            pda.put("sequence", sequence);
            dao.update("ArticleSynthesizerMapper.edit", pda);
        }

        result.put("result", "true");
        result.put("message", "删除成功");
        if(pd.getString("flag").equals("delAll"))
            result.put("message", "已清空");
        return result;
    }

    @Override
    public JSONObject updateMoveNews(PageData pd) throws Exception{
        JSONObject result = new JSONObject();


        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        if(user == null){
            result.put("result", "false");
            result.put("message", "无效的用户");
            return result;
        }
        if (StringUtil.isEmpty(pd.getString("flag")) &&
                StringUtil.isEmpty(pd.getString("sequence"))){
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }

        pd.put("user_id", user.getUSER_ID());
        PageData pdArticle = (PageData) dao.findForObject("ArticleSynthesizerMapper.findBySequence", pd);

        int sequence = (int) pdArticle.get("sequence");
        if(pd.getString("flag").equals("up")){
            //上移
            if(pdArticle.get("sequence").toString().equals("0")){
                result.put("result", "false");
                result.put("message", "已经是最顶部了");
                return result;
            }
            //修改上一篇的序号+1
            pd.put("sequence", sequence - 1);
            PageData pdUpArticle = (PageData) dao.findForObject("ArticleSynthesizerMapper.findBySequence", pd);
            pdUpArticle.put("sequence", sequence);
            dao.update("ArticleSynthesizerMapper.edit",pdUpArticle);

            //修改该篇的序号-1
            pdArticle.put("sequence", sequence - 1);
            dao.update("ArticleSynthesizerMapper.edit",pdArticle);

        }else if (pd.getString("flag").equals("down")){
            //下移
            if(pdArticle.get("sequence").toString().equals("7")){
                result.put("result", "false");
                result.put("message", "已经是最底部了");
                return result;
            }

            //修改下一篇的序号-1
            pd.put("sequence", sequence + 1);
            PageData pdDownArticle = (PageData) dao.findForObject("ArticleSynthesizerMapper.findBySequence", pd);
            pdDownArticle.put("sequence", sequence);
            dao.update("ArticleSynthesizerMapper.edit",pdDownArticle);

            //修改该篇的序号+1
            pdArticle.put("sequence", sequence + 1);
            dao.update("ArticleSynthesizerMapper.edit",pdArticle);

        }

        result.put("result", "true");
        return result;
    }

    @Override
    public JSONObject totalNumber(PageData pd) throws Exception{
        JSONObject result = new JSONObject();
        result.put("result", "false");

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        if(user == null){
            result.put("result", "false");
            result.put("message", "无效的用户");
            return result;
        }
        pd.put("user_id", user.getUSER_ID());

        List<PageData> list = (List<PageData>) dao.findForList("ArticleSynthesizerMapper.listByUser", pd);
        for(PageData pdd : list){
            int used_number = 0;
            if(pdd.get("used_number") != null)
                used_number = (int) pdd.get("used_number");

            //使用数+1
            PageData pda = new PageData();
            pda.put("id", pdd.getString("article_id"));
            pda.put("used_number", used_number + 1);
            dao.update("ArticleLibsMapper.edit", pda);

            //统计网吧合成图文的情况
            PageData pdTotal = new PageData();
            pdTotal.put("id", StringUtil.get32UUID());
            pdTotal.put("user_id", user.getUSER_ID());
            pdTotal.put("internet_id", user.getINTENET_ID());
            pdTotal.put("article_id", pdd.getString("article_id"));
            dao.save("ArticleTotalInternetMapper.save", pdTotal);

        }

        result.put("result", "true");
        return result;
    }



    @Override
    public JSONObject uploadImg(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        if(user == null){
            result.put("result", "false");
            result.put("message", "无效的用户");
            return result;
        }

        String src = pd.getString("src");
        if(StringUtil.isEmpty(src)){
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }else if(!src.startsWith("data:image/")){
            result.put("result", "false");
            result.put("message", "非法参数");
            return result;
        }


        String suffixName = ".jpg";
        String fileName = new Date().getTime() + suffixName;
        String path = user.getINTENET_ID() + "/" + fileName ;
        String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;

        String picture_url = replaceLanker(pd.getString("pre")) + Const.FILEPATHIMG + path;

        //上传到本地
        System.out.println(filePath);
        System.out.println(picture_url);
        Tools.pic(src,filePath);

        result.put("picture_url", picture_url);
        result.put("picture_id", StringUtil.get32UUID());
        result.put("result", "true");
        return result;
    }




    @Override
    public List<PageData> categoryList(PageData pd) throws Exception{
        return (List<PageData>) dao.findForList("ArticleCategoryMapper.categoryList", pd);
    }

    @Override
    public PageData findById(PageData pd) throws Exception {
        PageData pda = (PageData) dao.findForObject("ArticleLibsMapper.findById",pd);
        if(StringUtil.isNotEmpty(pd.getString("pre")) && StringUtil.isNotEmpty(pda)){
            pda.put("picture_url", replaceLanker(pd.getString("pre")) + Const.FILEPATHIMG + pda.getString("path"));
            pda.put("url", pd.getString("pre") + url + pda.getString("id"));
            pda.put("phone_url", pd.getString("pre") + phone_url + pda.getString("id"));
        }
        return pda;
    }

    @Override
    public Integer articleSize(PageData pd) throws Exception {
        return (Integer) dao.findForObject("ArticleLibsMapper.articleSize", pd);
    }

    @Override
    public Integer synthesizerSize(PageData pd) throws Exception {
        return (Integer) dao.findForObject("ArticleSynthesizerMapper.synthesizerSize", pd);
    }

    private String replaceLanker(String pre){
        return pre.replaceAll("/lanker", "");
    }
}
