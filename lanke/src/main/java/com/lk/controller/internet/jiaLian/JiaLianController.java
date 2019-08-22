package com.lk.controller.internet.jiaLian;

import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.jiaLian.AuditResult;
import com.lk.entity.system.User;
import com.lk.service.internet.jiaLian.JiaLianService;
import com.lk.service.internet.newPictures.NewPicturesService;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.tb.Message.MessageManager;
import com.lk.util.*;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * @author myq
 * @description 嘉联支付商户——控制层
 * @create 2019-04-29 16:50
 */
@Controller
@RequestMapping(value = "/jialian")
public class JiaLianController extends BaseController {

    @Autowired
    private JiaLianService jiaLianService;
    @Resource(name = "userService")
    private UserManager userService;

    @Autowired
    private NewPicturesService newPicturesService;
    @Resource(name = "messageService")
    private MessageManager messageService;
    @Resource(name = "storeService")
    private StoreManager storeService;

    /******************************************* 请求页面 ********************************/
    @RequestMapping(value = "/goList")
    public ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();

        mv.addObject("pd", this.getPageData());
        mv.setViewName("internet/jialian/list");
        return mv;
    }


    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception{
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        if(StringUtil.isNotEmpty(pd.getString("idd"))){
            pd.put("id", pd.getString("idd"));
        }
        if(StringUtil.isNotEmpty(pd.getString("store_idd"))){
            pd.put("store_id", pd.getString("store_idd"));
        }

        //判断操作用户和admin是否是同一家
        User user = this.getUser();//获取用户

        pd.put("user_type", "user");
        PageData pdUser = new PageData();
        pdUser.put("internet_id", user.getINTENET_ID());
        pdUser.put("user_name", "admin");
        pdUser = userService.findByUserNameAndInternetId(pdUser);
        if(StringUtil.isNotEmpty(pdUser)){
            pd.put("user_type", "system");
        }
        pd.put("name", user.getNAME());


        PageData pdStore = new PageData();
        pdStore.put("STORE_ID", pd.getString("store_id"));
        pdStore = storeService.findById(pdStore);
        pd.put("store_name", pdStore.getString("STORE_NAME"));

        PageData pdd = jiaLianService.findByStoreId(pd.getString("store_id"));
        if(StringUtil.isNotEmpty(pdd)){
            pd.put("id", pdd.getString("id"));
        }

        //查询是否已有开通的商户号
        List<PageData> openList = jiaLianService.getOpenList(user.getINTENET_ID());
        pd.put("shop_no_length", openList.size());

        mv.addObject("pd", pd);
        mv.setViewName("internet/jialian/edit");
        return mv;
    }


    /******************************************* 请求方法 ********************************/


    /**
     * 获取商户信息列表
     * @param page
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public LayMessage getList(Page page) throws Exception{
        PageData pd = this.getPageData();

        return jiaLianService.getList(pd, page);
    }

    /**
     * 根据主键id获取数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getById")
    public Message getById() throws Exception{
        PageData pd = this.getPageData();

        //判断传递的参数是否为空（主键id）
//        Message m = this.checkPdParam(pd);
//        if(m.getErrcode() != 0) return m;

        pd = jiaLianService.findByStoreId(pd.getString("store_id"));

        //获取图片
        PageData pdd = new PageData();
        pdd.put("store_id", pd.getString("store_id"));
        pdd.put("type", 5);//嘉联支付类的图片
        List<PageData> picList = newPicturesService.findByStoreIdAndType(pdd);
        for (PageData pdPic : picList) {
            pd.put(pdPic.getString("sort"), Const.FILEPATHIMG +  pdPic.getString("path"));
        }


        //判断操作用户和admin是否是同一家
        User user = this.getUser();//获取用户

        pd.put("user_type", "user");
        PageData pdUser = new PageData();
        pdUser.put("internet_id", user.getINTENET_ID());
        pdUser.put("user_name", "admin");
        pdUser = userService.findByUserNameAndInternetId(pdUser);
        if(StringUtil.isNotEmpty(pdUser)){
            pd.put("user_type", "system");
        }
        pd.put("name", user.getNAME());


        //记录已读
        if(StringUtil.isNotEmpty(pd.get("status")) && !pd.get("status").toString().equals("2")){
            PageData pdMessage = new PageData();
            pdMessage.put("message_id", pd.getString("id"));
            pdMessage.put("state", "1");
            pdMessage.put("user_id", user.getUSER_ID());
            pdMessage.put("read_time", Tools.date2Str(new Date()));
            messageService.edit(pdMessage);
        }

        return Message.ok().addData("pd", pd);
    }

    /**
     * 获取审核消息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getAuditInfo")
    public Message getAuditInfo() throws Exception{
        PageData pd = this.getPageData();

        //判断传递的参数是否为空（主键id）
        Message m = this.checkPdParam(pd);
        if(m.getErrcode() != 0) return m;

        return Message.ok().addData("infoList", jiaLianService.getAuditInfo(pd.getString("id")));
    }

    /**
     * 更新商户的审核状态
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/updateAuditStatus")
    public Message updateAuditStatus() throws Exception{
        PageData pd = this.getPageData();

        //判断传递的参数是否为空（主键id）
        Message m = this.checkPdParam(pd);
        if(m.getErrcode() != 0) return m;

        return jiaLianService.updateAuditStatus(pd);
    }


    /**
     * 新增或修改数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveShop")
    public Message saveShop() throws Exception{
        PageData pd = this.getPageData();

        String pre = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" +
                this.getRequest().getServerPort() + this.getRequest().getContextPath() + "/";
        pd.put("pre", pre);

        //判断传递的参数是否为空（表单信息）
//        if(pd.getString("save").equals("submit")){
//            Message m = this.checkPdParam(pd);
//            if(m.getErrcode() != 0) return m;
//        }

        return jiaLianService.saveShop(pd);
    }

    /**
     * 删除数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/delShop")
    public Message delShop() throws Exception{
        PageData pd = this.getPageData();

        //判断传递的参数是否为空（主键id）
        Message m = this.checkPdParam(pd);
        if(m.getErrcode() != 0) return m;

        return jiaLianService.delShop(pd);
    }

    /**
     * 删除图片
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/delImg")
    public Message delImg() throws Exception{
        PageData pd = this.getPageData();

        //判断传递的参数是否为空（主键id）
        Message m = this.checkPdParam(pd);
        if(m.getErrcode() != 0) return m;

        return jiaLianService.delImg(pd);
    }


    /**
     * 共享商户号
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/shareShopNo")
    public Message shareShopNo() throws Exception{
        PageData pd = this.getPageData();

        if(pd.get("shop_no") == null){
            return Message.error("请填写商户号");
        }
        if(pd.get("store_id") == null){
            return Message.error("门店信息不正确");
        }

        return jiaLianService.shareShopNo(pd);
    }


    /******************************************* 接收嘉联回调接口 ********************************/


    /**
     * 接收商户审核状态
     *
     * @param pw
     */
    @RequestMapping(value = "/recShopAudit", method= RequestMethod.POST)
    public void recShopAudit(PrintWriter pw, @RequestBody String body) throws Exception {

        //嘉联回调参数

        JSONObject obj = JSONObject.fromObject(body);
        logger.info("接收商户审核：" + obj.toString());

        //处理信息
        boolean isTrue = jiaLianService.handleShopAudit(obj);
        if (isTrue) {
            //回调后接受方成功返回success，失败返回fail，平台不做后续处理
            pw.write("success");
            return;
        }
        pw.write("fail");
    }


}
