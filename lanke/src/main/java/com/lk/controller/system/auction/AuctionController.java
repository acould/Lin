package com.lk.controller.system.auction;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.auction.AuctionManager;
import com.lk.service.system.auctionpictures.AuctionPicturesManager;
import com.lk.service.system.order.OrderManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PathUtil;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

/**
 * 说明：商品管理
 */
@Controller
@RequestMapping(value = "/auction")
public class AuctionController extends BaseController {
    // TYPE
    public static final String TYPE = "name_repeat";
    // FILE(文件类型)
    public static final String FILE1 = "data:image/jpeg;base64,";
    public static final String FILE2 = "data:image/png;base64,";
    // SUFFIXNAME(图片尾缀)
    public static final String SUFFIXNAME1 = ".jpg";
    public static final String SUFFIXNAME2 = ".png";
    // D_STATE(商品auction逻辑删除；1表示未删除；2表示已删除)
    public static final String D_STATE1 = "1";
    public static final String D_STATE2 = "2";
    // STATE(商品auction表状态,1-上架,2-下架,3-有订单)
    public static final String STATE1 = "1";
    public static final String STATE2 = "2";
    public static final String STATE3 = "3";

    String menuUrl = "auction/list.do"; // 菜单地址(权限用)
    @Resource(name = "auctionService")
    private AuctionManager auctionService;
    @Resource(name = "auctionPicturesService")
    private AuctionPicturesManager auctionPicturesService;
    @Resource(name = "orderService")
    private OrderManager orderService;

    /**
     * 跳转到裁剪图片页面
     */
    @RequestMapping("/goTailor")
    public ModelAndView goTailor() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("system/auction/img_tailor");
        return mv;
    }


    /**
     * 首页列表
     *
     * @param page
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page, HttpServletRequest request) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "列表auction");
        ModelAndView mv = this.getModelAndView();

        User user = this.getUser();//获取用户

        PageData pd = this.getPageData();
        pd.put("INTERNET_ID", user.getINTENET_ID());

        //查询条件（关键词keywords）
        page.setPd(pd);
        List<PageData> varList = auctionService.list(page);
        for (PageData pageData : varList) {
            PageData pdd = auctionPicturesService.findByAuctionId(pageData.getString("Auction_ID"));
            pageData.put("PATH", pdd.get("PATH"));
//            PageData pdd2 =auctionPicturesService.findByStoreId(pageData.getString("store_id"));
//            pageData.put("STORE_NAME", pdd2.get("STORE_NAME")==null?"":pdd2.get("STORE_NAME"));
        }
        Session session = Jurisdiction.getSession();
        User user2 = new User();
        user2 = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();
        String intenet_id = user.getINTENET_ID();
        List<PageData> list= auctionPicturesService.getStoreList(intenet_id);
        mv.addObject("storeList",list);
        mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.setViewName("system/auction/auction_list");
        return mv;
    }

    /**
     * 去新增页面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("system/auction/auction_edit");
        Session session = Jurisdiction.getSession();
        User user = new User();
        user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();
        String intenet_id = user.getINTENET_ID();
        List<PageData> list= auctionPicturesService.getStoreList(intenet_id);
        mv.addObject("storeList",list);
        mv.addObject("msg", PublicManagerUtil.SAVE);
        return mv;
    }

    /**
     * 去修改页面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        //根据主键id查询
        pd = auctionService.findById(pd);
        //加载其图片信息
        PageData pictures = auctionPicturesService.findByAuctionId(pd.getString("Auction_ID"));

        mv.setViewName("system/auction/auction_edit");
        Session session = Jurisdiction.getSession();
        User user = new User();
        user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();
        String intenet_id = user.getINTENET_ID();
        List<PageData> list= auctionPicturesService.getStoreList(intenet_id);
        mv.addObject("storeList",list);
        mv.addObject("pd", pd);
        mv.addObject("pictures", pictures);
        mv.addObject("msg", PublicManagerUtil.EDIT);
        return mv;
    }

    /**
     * 检查商品名称是否存在
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/hasName")
    @ResponseBody
    public JSONObject hasName() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "检查商品名称hasName");
        JSONObject json = new JSONObject();
        User user = this.getUser();//获取用户

        PageData pd = this.getPageData();
        pd.put("INTENET_ID", user.getINTENET_ID());
        pd.put("INTERNET_ID", user.getINTENET_ID());
        if (StringUtil.isNotEmpty(pd.getString("Auction_ID"))) {
            if (auctionService.findByAname(pd) != null
                    && !auctionService.findByAname(pd).getString("Auction_ID").equals(pd.getString("Auction_ID"))) {
                json.put("result", PublicManagerUtil.FALSE);
                json.put("message", pd.getString("ANAME") + "商品名称已存在！");
                json.put("type", TYPE);
                return json;
            }
        } else {
            if (auctionService.findByAname(pd) != null) {
                json.put("result", PublicManagerUtil.FALSE);
                json.put("message", pd.getString("ANAME") + "商品名称已存在！");
                json.put("type", TYPE);
                return json;
            }
        }

        return json;
    }

    /**
     * 新增或修改保存
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/saveAuction")
    @ResponseBody
    public JSONObject saveAuction() throws Exception {

        logBefore(logger, Jurisdiction.getUsername() + "新增或修改保存saveAuction");
        JSONObject json = new JSONObject();
        User user = this.getUser();//获取用户

        PageData pd = this.getPageData();
        pd.put("INTENET_ID", user.getINTENET_ID());
        pd.put("INTERNET_ID", user.getINTENET_ID());
        pd.put("CREATETIME", Tools.date2Str(new Date()));

        String file = pd.getString("DATA_IMAGE");
        String fileName = "";
        String path = "";
        String filePath = "";
        if (StringUtil.isNotEmpty(file)) {
            String suffixName = "";
            if (file.contains(FILE1)) {
                suffixName = SUFFIXNAME1;
            } else if (file.contains(FILE2)) {
                suffixName = SUFFIXNAME2;
            } else {
                json.put("result", PublicManagerUtil.FALSE);
                json.put("message", "请上传jpg或者png格式的图片！");
                return json;
            }
            fileName = new Date().getTime() + suffixName;
            path = user.getINTENET_ID() + "/" + fileName;
            filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;
        }


        // 判断新增还是修改
        if (StringUtil.isNotEmpty(pd.getString("Auction_ID"))) {
            // 修改，校验权限
            if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
                json.put("result", PublicManagerUtil.FALSE);
                json.put("message", "您没有修改权限，请联系管理员！");
                return json;
            }
            if (auctionService.findByAname(pd) != null
                    && !auctionService.findByAname(pd).getString("Auction_ID").equals(pd.getString("Auction_ID"))) {
                json.put("result", PublicManagerUtil.FALSE);
                json.put("message", pd.getString("ANAME") + "商品名称已存在！");
                json.put("type", TYPE);
                return json;
            }
            auctionService.edit(pd);
            // 修改商品图片
            if (StringUtil.isNotEmpty(file)) {
                // 上传到本地
                Tools.pic(file, filePath);

                PageData pdImg = new PageData();
                pdImg.put("NAME", pd.getString("ANAME")); // 文件名
                pdImg.put("PATH", path); // 路径
                pdImg.put("Auction_ID", pd.get("Auction_ID"));
                auctionPicturesService.editByAuctionId(pdImg);
            }

        } else {
            // 新增
            if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
                json.put("result", PublicManagerUtil.FALSE);
                json.put("message", "您没有新增权限，请联系管理员！");
                return json;
            }
            if (auctionService.findByAname(pd) != null) {
                json.put("result", PublicManagerUtil.FALSE);
                json.put("message", pd.getString("ANAME") + "商品名称已存在！");
                json.put("type", TYPE);
                return json;
            }

            pd.put("Auction_ID", this.get32UUID()); // 主键
            // 1表示已上架；2表示已下架
            pd.put("D_STATE", D_STATE1);// 逻辑删除；1表示未删除；2表示已删除
            auctionService.save(pd);

            // 新增商品的图片
            if (StringUtil.isNotEmpty(file)) {
                // 上传到本地
                Tools.pic(file, filePath);

                PageData pdImg = new PageData();
                pdImg.put("MPICTURES_ID", this.get32UUID()); // 主键
                pdImg.put("TITLE", "商品图片"); // 标题
                pdImg.put("NAME", pd.getString("ANAME")); // 文件名
                pdImg.put("PATH", path); // 路径
                pdImg.put("CREATETIME", Tools.date2Str(new Date())); // 创建时间
                pdImg.put("Auction_ID", pd.get("Auction_ID"));
                pdImg.put("INTERNET_ID", user.getINTENET_ID()); // 网吧id
                auctionPicturesService.save(pdImg);
            }

        }
        json.put("result", PublicManagerUtil.TRUE);
        json.put("message", "保存成功！");
        return json;
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public JSONObject delete() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + " 删除Auction");
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();

        // 校验权限
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            json.put("result", PublicManagerUtil.FALSE);
            json.put("message", "您没有新增权限，请联系管理员！");
            return json;
        }

        PageData pdAuction = auctionService.findById(pd);
        if (pdAuction.getString("STATE").equals(STATE1)) {
            json.put("result", PublicManagerUtil.FALSE);
            json.put("message", "该商品已经上架，不能删除！");
            return json;
        }

//        List<PageData> orderList = orderService.findByAuctionId(pd);
//        for (PageData pdOrder : orderList) {
//            if (!pdOrder.getString("STATE").equals(STATE3)) {
//                json.put("result", PublicManagerUtil.FALSE);
//                json.put("message", "该商品尚有未完成的交易，暂时不能删除！");
//                return json;
//            }
//        }

        //现全部设为可逻辑删除
        pd.put("D_STATE", D_STATE2); // 2:逻辑删除
        auctionService.delete(pd);

        json.put("result", PublicManagerUtil.TRUE);
        json.put("message", "删除成功！");

        return json;
    }

    /**
     * 批量删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public JSONObject deleteAll() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + " 批量删除deleteAll");
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();

        // 校验权限
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            json.put("result", PublicManagerUtil.FALSE);
            json.put("message", "您没有新增权限，请联系管理员！");
            return json;
        }

        if (StringUtil.isNotEmpty(pd.getString("DATA_IDS"))) {
            String DATA_IDS = pd.getString("DATA_IDS");
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            for (int i = 0; i < ArrayDATA_IDS.length; i++) {
                PageData pdIsJin = new PageData();
                pdIsJin.put("Auction_ID", ArrayDATA_IDS[i]);
                pdIsJin = auctionService.findById(pdIsJin);
                if (pdIsJin.getString("STATE").equals(STATE1)) {
                    json.put("result", PublicManagerUtil.FALSE);
                    json.put("message", pdIsJin.getString("ANAME") + "商品已经上架，不能删除！");
                    return json;
                }

//                List<PageData> orderList = orderService.findByAuctionId(pdIsJin);
//                for (PageData pdOrder : orderList) {
//                    if (!pdOrder.getString("STATE").equals(STATE3)) {
//                        json.put("result", PublicManagerUtil.FALSE);
//                        json.put("message", pdIsJin.getString("ANAME") + "商品尚有未完成的交易，暂时不能删除！");
//                        return json;
//                    }
//                }
            }
            for (int i = 0; i < ArrayDATA_IDS.length; i++) {
                PageData pdIsJin = new PageData();
                pdIsJin.put("Auction_ID", ArrayDATA_IDS[i]);
                pdIsJin.put("D_STATE", D_STATE2); // 2:逻辑删除
                auctionService.delete(pdIsJin);
            }
            json.put("result", PublicManagerUtil.TRUE);
            json.put("message", "删除成功！");
        } else {
            json.put("result", PublicManagerUtil.FALSE);
            json.put("message", "请选择需要删除的选项！");
            return json;
        }

        return json;
    }

    /**
     * 上下架
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/xiajia")
    @ResponseBody
    public JSONObject xiajia() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + " 上下架Auction");
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        //wgh 2019/4/4 解决上下架之后 购买方式清空的问题
        auctionService.editState(pd);

        if (pd.getString("STATE").equals(STATE1)) {
            json.put("result", PublicManagerUtil.TRUE);
            json.put("message", "上架成功！");
        } else if (pd.getString("STATE").equals(STATE2)) {
            json.put("result", PublicManagerUtil.TRUE);
            json.put("message", "下架成功！");
        }
        return json;
    }


}
