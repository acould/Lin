package com.lk.controller.lankeManager.officialtemplatemanager;

import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.service.lankeManager.officialtemmanager.OfficialTemplateService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.PageData;
import com.lk.wechat.util.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/templatemanager")
public class OfficialTemManagerController extends BaseController {

    @Autowired
    private AutoReplyService autoReplyService;

    @Resource
    private OfficialTemplateService officialTemplateService;

    /**
     * 跳转到模板信息页面
     * @return
     */
    @RequestMapping("/goList")
    public ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("lankemanager/officialtemmanager/officialtemmanager_list");
        return mv;
    }

    /**
     * 更新模板消息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public  JSONObject getTemplate() throws Exception{
        JSONObject json2 = new JSONObject();
        List<PageData> internet_ids = null;
        try {
            internet_ids = officialTemplateService.getInternetList();
            String [] strs = new String[internet_ids.size()];//创建一个string数组
            for(int i=0;i<internet_ids.size();i++){//将公众号id存在string数组中
                String internet_id = internet_ids.get(i).getString("INTENET_ID");
                strs[i]=internet_id;
            }
            //officialTemplateService.getExist2(strs);//验证公众号是否存在
            officialTemplateService.deleteTemplate2();
            List<Map<String,Object>>  list = new ArrayList();
            for(int i=0;i<internet_ids.size();i++){
                String internet_id = internet_ids.get(i).getString("INTENET_ID");
                String token = null;
                try{
                    token = autoReplyService.getAuthToken(internet_id);
                }catch (Exception e){
                    token = "";
                }
                String urlStr = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + token;
                JSONObject json = HttpUtil.wechatRequest(urlStr, "POST", null, "模板消息--获取所有模板消息");
                if(json.size()==1){
                    //template_list
                    JSONArray jsonArr = json.getJSONArray("template_list");
                    list = JSONArray.toList(jsonArr);
                    officialTemplateService.getinternetById(internet_id,list);//更新数据库数据
                    //更新完了数据库，再查找第三方平台里的公众号和模板消息在微信公众号里是否存在
                   // officialTemplateService.getExist(internet_id,list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json2.put("message",false);
        }
        json2.put("message",true);
       // String internet_id = "42d593519e6349a48825af3d89272898";
        return json2;
    }

    /**
     * 展示模板信息列表
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public LayMessage getList(Page page) throws Exception {
        PageData pd = this.getPageData();
        pd.put("page", page);
        LayMessage lms = officialTemplateService.list(pd, page);
        return lms;
    }

    /**
     * 删除微信端模板信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public JSONObject delete() throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();

        try {
            officialTemplateService.deleteTemplate(pd);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("message",false);
        }
        json.put("message",true);
        return json;
    }

}
