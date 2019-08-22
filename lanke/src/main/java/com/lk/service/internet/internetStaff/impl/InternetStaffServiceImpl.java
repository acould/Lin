package com.lk.service.internet.internetStaff.impl;

import com.google.common.base.Joiner;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.internet.internetStaff.InternetStaffService;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.util.*;
import com.lk.wechat.response.WechatUser;
import com.lk.wechat.util.WeChatOpenUtil;
import com.lk.wechat.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author myq
 * @description
 * @create 2019-04-10 18:12
 */
@Service
public class InternetStaffServiceImpl implements InternetStaffService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Autowired
    private IntenetManager intenetManager;
    @Resource(name = "storeService")
    private StoreManager storeService;


    @Override
    public JSONObject saveStaff(PageData pd, String[] perms) throws Exception {
        JSONObject result = new JSONObject();

        if(perms.length == 0){
            result.put("result", "false");
            result.put("message", "参数为空");
            return result;
        }

        String string = "";
        for(String str : perms){
            string += str + ";";
        }
        pd.put("perms", string);

        if(StringUtil.isEmpty(pd.getString("id"))){
            //新增
            pd.put("id", StringUtil.get32UUID());
            pd.put("create_time", new Date());
            pd.put("state", "1");//默认1启用


            dao.save("InternetStaffMapper.save", pd);
        }else{
            //修改
            pd.remove("name");//不允许更改名字

            dao.update("InternetStaffMapper.edit", pd);
        }

        result.put("result", "true");
        result.put("message", "保存成功");
        return result;
    }

    @Override
    public JSONObject delStaff(String id) throws Exception {
        JSONObject result = new JSONObject();

        dao.delete("InternetStaffMapper.delete", id);

        result.put("result", "true");
        result.put("message", "删除成功");
        return result;
    }

    @Override
    public PageData findById(String id) throws Exception {
        return (PageData) dao.findForObject("InternetStaffMapper.findById", id);
    }


    @Override
    public String getQrcodeUrl(String internet_id) throws Exception{
        Session session = Jurisdiction.getSession();
        if(session.getAttribute("user_server_qr_url") != null){
            return (String) session.getAttribute("user_server_qr_url");
        }

        String component_appid = PublicManagerUtil.APPID;
        String domain = PublicManagerUtil.URL1;

        PageData pdInternet = new PageData();
        pdInternet.put("INTENET_ID", internet_id);
        pdInternet = intenetManager.findById(pdInternet);

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid="+pdInternet.getString("WECHAT_ID") +
                "&redirect_uri="+domain+"indexMember/userStaffBind.do?uuid="+ StringUtil.get32UUID()+
                "&response_type=code&scope=snsapi_userinfo" +
                "&state=0" +
                "&component_appid="+ component_appid +
                "#wechat_redirect";

        session.setAttribute("user_server_qr_url", url);
        return url;
    }


    @Override
    public JSONObject loadStaffList(PageData pd, Page page) throws Exception{
        JSONObject result = new JSONObject();

        page.setPd(pd);
        List<PageData> pdList = (List<PageData>) dao.findForList("InternetStaffMapper.datalistPage", page);
        for (PageData pdd : pdList) {
            pdd.put("neck_name", URLDecoder.decode(pdd.getString("neck_name"), "utf-8"));
            String[] str = pdd.getString("perms").split(";");
            String info = "";
            for(String ss : str){
                if(ss.equals("user_call"))
                    info += "呼叫网管、";
                if(ss.equals("user_complain"))
                    info += "一键投诉、";
                if(ss.equals("user_order"))
                    info += "商品发货通知、";
            }
            pdd.put("info", info.substring(0, info.length() - 1));
        }

        result.put("code", "0");
        result.put("msg", "查询成功");
        result.put("data", pdList);
        result.put("count", page.getTotalResult());
        result.put("showCount", page.getShowCount());
        return result;
    }

    @Override
    public List<PageData> getInternetStaff(String internet_id) throws Exception{

        PageData pd = new PageData();
        pd.put("internet_id", internet_id);

        List<PageData> pdList = (List<PageData>) dao.findForList("InternetStaffMapper.getInternetStaff", pd);
        return pdList;
    }

    @Override
    public List<PageData> getStoreList(String internet_id, String roleId, String userId) throws Exception{
        // 判断角色及其相关联门店信息
        List<PageData> storeList = new ArrayList<>();

        PageData pd = new PageData();
        pd.put("internetId", internet_id);
        if (roleId.equals(PublicManagerUtil.INTERNETROLEID)) {// 网吧老板
            storeList = storeService.listByIntenet(pd);// 筛选门店列表展示
        } else {
            storeList = storeService.getStoreList(userId); // 通过user_id去找关联门店id和name的集合
        }

        return storeList;
    }

    @Override
    public JSONObject editState(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        PageData pdd = findById(pd.getString("id"));
        if(StringUtil.isEmpty(pdd)){
            result.put("result", "false");
            result.put("message", "找不到数据");
            return result;
        }

        if(pdd.getString("state").equals("1")){
            pd.put("state", "2");
        }
        if(pdd.getString("state").equals("2")){
            pd.put("state", "1");
        }

        dao.update("InternetStaffMapper.editState", pd);
        result.put("result", "true");
        result.put("message", "修改成功");
        return result;
    }
}
