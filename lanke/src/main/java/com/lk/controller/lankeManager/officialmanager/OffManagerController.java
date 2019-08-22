package com.lk.controller.lankeManager.officialmanager;

import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.service.lankeManager.cusmanager.CusManagerService;
import com.lk.service.lankeManager.officialmanager.OfficialService;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.WeChatOpenUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/officialmanager")
public class OffManagerController extends BaseController {

    @Autowired
    public OfficialService officialService;

    @Resource(name = "myopService")
    private MyopManager myopService;

    @Resource
    private AutoReplyService autoReplyService;

    /**
     * 跳转到公众号管理页面
     * @return
     */
    @RequestMapping(value="/goList")
    public ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("lankemanager/lankeroffimanager/officialmanager_list");
        return mv;
    }

    /**
     * 查找公众号的所有信息
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public LayMessage getList(Page page) throws Exception {
        PageData pd = this.getPageData();
        return officialService.list(pd, page);
    }

    /**
     * 更新公众号相关信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toUpdate")
    @ResponseBody
    public JSONObject toUpdate() throws Exception {

        JSONObject json2 = new JSONObject();//定义一个json对象，用于存储返回的数据
        try {
            PageData pd = this.getPageData();
            PageData pdMyop = new PageData();
            pdMyop.put("APPID", PublicManagerUtil.APPID);
            pdMyop.put("TOKEN_TIME", Tools.sAddHours(new Date(), -1));
            pdMyop = myopService.findByAppId(pdMyop);
            String componentAccessToken = pdMyop.getString("COMPONENT_ACCESS_TOKEN");
            PageData pdInternet = new PageData();
            //nick_name （昵称） head_img(头像)  qrcode_url(二维码）
            JSONObject json = WeChatOpenUtil.getAuthorizerInfo(PublicManagerUtil.APPID,pd.getString("WECHAT_ID"), componentAccessToken);
            pdInternet.put("HEAD_IMG", json.getJSONObject("authorizer_info").get("head_img"));//头像
            pdInternet.put("NICK_NAME", json.getJSONObject("authorizer_info").get("nick_name"));//昵称
            pdInternet.put("QRCODE_URL", json.getJSONObject("authorizer_info").get("qrcode_url"));//二维码
            pdInternet.put("WECHAT_ID", pd.getString("WECHAT_ID"));//公众号ID
            pdInternet.put("authorizer_refresh_token", json.getJSONObject("authorization_info").get("authorizer_refresh_token"));//头像
            officialService.update(pdInternet);//根据微信接口更新第三方数据库
            json2.put("message",true);
        }catch (Exception e){
            json2.put("message",false);
        }
        return json2;
    }

    /**
     * 更新Token
     * @return
     * @throws Exception
     */
    @RequestMapping(value="getUpdateToken")
    @ResponseBody
    public JSONObject updateToken() throws Exception{
        JSONObject json = new JSONObject();
        try{
            PageData pd = this.getPageData();
            String INTENET_ID = pd.getString("INTENET_ID");
            String authToken = autoReplyService.getAuthToken(INTENET_ID);
            officialService.updateToken(INTENET_ID,authToken);
            json.put("message",true);
        }catch (Exception ex){
            json.put("message",false);
        }
        return json;
    }
}
