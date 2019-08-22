package com.lk.controller.lankeManager.cusmanager;

import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.controller.base.BaseController;
import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.lankeManager.cusmanager.CusManagerService;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import io.netty.channel.ChannelHandlerContext;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping(value = "/cusManager")
public class CusManagerController extends BaseController {

    @Resource(name = "cusManagerService")
    private CusManagerService cusManagerService;
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @RequestMapping(value = "/goList")
    public  ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("lankemanager/lankercusmanager/cusmanager_list");
        return mv;
    }

 /*   @RequestMapping(value="/listManages")
    @ResponseBody
    public JSONObject listManages(Page page) throws Exception {
        User user = this.getUser();
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        // 后台查看所有
        pd.put("page", page);
        long starttime = System.currentTimeMillis();
        json = cusManagerService.list(pd);
        long endtime = System.currentTimeMillis();
        System.out.println("==========="+(endtime-starttime)/1000.0);
        System.out.println(json);
        return json;
    }*/

    @RequestMapping(value="/listManages")
    @ResponseBody
    public LayMessage getList(Page page) throws Exception {
        PageData pd = this.getPageData();
        pd.put("page", page);
        LayMessage lms = cusManagerService.list(pd,page);
        return lms;
    }


    /**
     * 导出到excel 将选中信息导出为excel
     *
     * @param
     * @return mv--返回指定信息和视图
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "导出门店数据到excel");

        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        this.getRequest().setCharacterEncoding("UTF-8");
        pd = this.getPageData();

        Page page = new Page();
        page.setPd(pd);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("公众号"); // 1
        titles.add("门店"); // 2
        titles.add("揽客版本"); // 3
        titles.add("入住时间"); // 4
        titles.add("加V到期时间"); // 5
        titles.add("付费状态"); // 6
        titles.add("计费链接状态"); // 7
        titles.add("粉丝数"); // 8
        titles.add("会员数"); // 9
        titles.add("总充值金额"); // 10
        dataMap.put("titles", titles);

        String keywords = new String(pd.getString("keywords").getBytes("iso-8859-1"), "utf-8");// 关键词检索条件
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
        }
        String vesion = this.getRequest().getParameter("vesion");
        pd.put("vesion", vesion);
        String str = this.getRequest().getParameter("str");
        List<String> list = Arrays.asList(str.split(","));
        List<PageData> varList = new ArrayList<PageData>();
        for (int j = 0; j < list.size(); j++) {
            pd.put("STORE_ID", list.get(j));
            List<PageData> varOList = cusManagerService.listExcel(page); // 加V信息导出为excel
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                String store_id = varOList.get(i).getString("STORE_ID");
                ChannelHandlerContext flag = ChannelCache.channelMap.get("store_id");
                if(flag!=null){
                    vpd.put("var7", "在线"); // 7
                }else{
                    vpd.put("var7", "离线"); // 7
                }
                int status =  dao.findForObject("CustomerManager.findyinlianBystoreId", store_id)==null ? -1:(int)dao.findForObject("CustomerManager.findyinlianBystoreId", store_id);
                int status2 = dao.findForObject("CustomerManager.findyinlianBystoreId", store_id)==null? -1:(int) dao.findForObject("CustomerManager.findyinlianBystoreId", store_id);
                int state = dao.findForObject("CustomerManager.findjiavBystoreId", store_id)==null? -1:(int) dao.findForObject("CustomerManager.findjiavBystoreId", store_id);
                if((status==1 || status2==1) && state==1 ){
                    vpd.put("var3", "专业版"); // 3
                }else if((status!=1 && status2!=1) && state==1){
                    vpd.put("var3", "轻营销+V"); // 3
                }else{
                    vpd.put("var3", "轻营销版"); // 3
                }
                String choose_package = varOList.get(i).getString("CHOOSE_PACKAGE")==null?"":varOList.get(i).getString("CHOOSE_PACKAGE");
                if(choose_package.equals("0")){
                    vpd.put("var6", "免费一个月"); // 6
                }else if(choose_package.equals("1")){
                    vpd.put("var6", "付费一年"); // 6
                }else{
                    vpd.put("var6", "");
                }
                vpd.put("var1", varOList.get(i).getString("INTENET_NAME")==null?"":varOList.get(i).getString("INTENET_NAME")); // 1
                vpd.put("var2", varOList.get(i).getString("STORE_NAME")==null?"":varOList.get(i).getString("STORE_NAME")); // 2
                vpd.put("var4", varOList.get(i).getString("CREATE_TIME")==null?"":varOList.get(i).getString("CREATE_TIME")); // 4
                vpd.put("var5", varOList.get(i).getString("EXPIRATION_TIME")==null?"":varOList.get(i).getString("EXPIRATION_TIME")); // 5
                vpd.put("var8", varOList.get(i).get("fannums")==null?"":varOList.get(i).get("fannums").toString()); // 8
                vpd.put("var9", varOList.get(i).get("storenums")==null?"":varOList.get(i).get("storenums").toString()); // 9
                vpd.put("var10", varOList.get(i).get("totalprice")==null?"":varOList.get(i).get("totalprice").toString()); // 10
                varList.add(vpd);
            }
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }

    @RequestMapping(value = "/getSum")
    @ResponseBody
    public JSONObject listManages() throws Exception {
        PageData pd = this.getPageData();
        Page page = new Page();
        page.setPd(pd);
        JSONObject json = new JSONObject();
        json = cusManagerService.listSum(page);
        return json;
    }
}
