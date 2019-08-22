package com.lk.service.article;

import com.lk.entity.Page;
import com.lk.util.PageData;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author shkstart
 * @create 2018-10-25 11:19
 */
public interface ArticleService {

    /**
     * 查询揽客文章库列表
     * @param pd(包含搜索，分类，状态，分页查询)
     * @return
     * @throws Exception
     */
    JSONObject articleList(PageData pd, Page page) throws Exception;


    /**
     * 不分页加载文章库列表
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject articleList(PageData pd) throws Exception;

    /**
     * 获取分类列表
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> categoryList(PageData pd) throws Exception;

    /**
     * 根据主键id获取文章
     * @param pd
     * @return
     * @throws Exception
     */
    PageData findById(PageData pd) throws Exception;

    /**
     * 查询文章的数量(根据状态/类型/推荐度)
     * @param pd
     * @return
     * @throws Exception
     */
    Integer articleSize(PageData pd) throws Exception;


    /**
     * 图文合成器的数量
     * @param pd
     * @return
     * @throws Exception
     */
    Integer synthesizerSize(PageData pd) throws Exception;


    /**
     * 保存新增或修改的分类管理
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject saveCategory(PageData pd) throws Exception;


    /**
     * 删除分类管理
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject deleteCategory(PageData pd) throws Exception;


    /**
     * 删除文章
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject deleteArticle(PageData pd) throws Exception;


    /**
     * 保存新增或修改的文章
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject saveArticle(PageData pd) throws Exception;


    /**
     * 加载图文合成器的内容
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject loadSynthesizer(PageData pd) throws Exception;


    /**
     * 添加一篇图文到图文合成器
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject saveNews(PageData pd) throws Exception;


    /**
     * 删除一篇图文合成器的文章
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject deleteNews(PageData pd) throws Exception;


    /**
     * 上下移动，排序图文
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject updateMoveNews(PageData pd) throws Exception;


    /**
     * 统计文章库被合成数
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject totalNumber(PageData pd) throws Exception;


    /**
     * 上传图片
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject uploadImg(PageData pd) throws Exception;



}
