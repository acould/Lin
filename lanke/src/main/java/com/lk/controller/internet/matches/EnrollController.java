package com.lk.controller.internet.matches;

import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.internet.enroll.EnrollService;
import com.lk.service.internet.matches.MatchesService;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.util.*;

/**
 * @author myq
 * @description
 * @create 2019-05-23 17:10
 */
@Controller
@RequestMapping(value="/enroll")
public class EnrollController extends BaseController{


    @Autowired
    private EnrollService enrollService;


    @Autowired
    private MatchesService matchesService;

    /**
     * 报名列表页面
     * @return
     */
    @RequestMapping(value = "/goList")
    public ModelAndView goList() throws Exception{
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();

        PageData pdMatches = matchesService.findById(pd.getString("matches_id"));
        pd.put("pdMatches", pdMatches);

        User user = this.getUser();
        pd.put("NAME", URLDecoder.decode(user.getNAME(), "UTF-8"));

        mv.addObject("pd", pd);
        mv.setViewName("internet/matches/enroll_list");
        return mv;
    }




    /******************************************* 报名请求方法 ********************************/


    /**
     * 报名分页列表
     * @param page
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public LayMessage getList(Page page) throws Exception{
        PageData pd = this.getPageData();


        return enrollService.getEnrollList(pd, page);
    }


    /**
     * 导出到excel 将选中信息导出为excel
     *
     * @param
     * @return mv--返回指定信息和视图
     * @throws Exception
     */
    @RequestMapping(value = "/toExcel")
    public ModelAndView toExcel() throws Exception {

        this.getRequest().setCharacterEncoding("UTF-8");
        PageData pd = this.getPageData();

        String str = this.getRequest().getParameter("str");
        String[] arr = str.split(",");

        pd.put("arr", arr);
        List<PageData> excelList = enrollService.listExcel(pd); // 加V信息导出为excel

        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("队伍类型"); // 1
        titles.add("队伍名称"); // 2
        titles.add("用户名称"); // 3
        titles.add("联系电话"); // 4
        titles.add("报名时间"); // 5
        dataMap.put("titles", titles);

        List<PageData> varList = new ArrayList<>();
        for (int i = 0; i < excelList.size(); i++) {
            PageData vpd = new PageData();
            PageData pdEnroll = excelList.get(i);

            String team_type_info = "";
            if(StringUtil.isEmpty(pdEnroll.get("team_type"))){
                team_type_info = "单人报名";
            }else{
                if(pdEnroll.get("team_type").toString().equals("0")){
                    team_type_info = "单人报名";
                }else if(pdEnroll.get("team_type").toString().equals("1")){
                    team_type_info = "自由组队";
                }else if(pdEnroll.get("team_type").toString().equals("2")){
                    team_type_info = "组队报名";
                }
            }

            vpd.put("var1" , team_type_info);
            vpd.put("var2" , pdEnroll.getString("team_name"));
            vpd.put("var3" , pdEnroll.getString("name"));
            vpd.put("var4" , pdEnroll.getString("phone"));
            vpd.put("var5" , pdEnroll.get("create_time"));
            varList.add(vpd);

        }

        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        ModelAndView mv = new ModelAndView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }


}
