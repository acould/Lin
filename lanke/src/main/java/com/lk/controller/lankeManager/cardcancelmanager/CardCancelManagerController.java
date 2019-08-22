package com.lk.controller.lankeManager.cardcancelmanager;

import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.lankeManager.cartcanclemanager.CartCancleService;
import com.lk.service.lankeManager.cartcanclemanager.impl.CartCancleServiceImpl;
import com.lk.service.lankeManager.officialmanager.OfficialService;
import com.lk.service.system.myop.MyopManager;
import com.lk.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * 卡券核销管理
 */
@Controller
@RequestMapping(value="/cardCancelManager")
public class CardCancelManagerController extends BaseController {

    @Resource
    private CartCancleService cartCancleService;
    /**
     * 进入后台卡券核销管理页面
     * @return
     */
    @RequestMapping(value = "/goList")
    public ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("lankemanager/cardcancelmanager/cardcancelmanager_list");
        return mv;
    }

    /**
     * 返回列表
     * layMessage封装了code（返回的代码）;msg（返回的信息）;
     * count（返回的数据数）;showCount（显示记录数）;private List<?> data（返回查询的数据）
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public LayMessage getList(Page page) throws Exception {
        PageData pd = this.getPageData();
        pd.put("page", page);
        LayMessage lms = cartCancleService.list(pd, page);
        //获取返回的数据
        List<PageData> varList = (List<PageData>) lms.getData();
        //将乱码转成中文
        for (int i = 0; i < varList.size(); i++) {
            if (varList.get(i).containsKey("NECK_NAME")) {
                varList.get(i).put("NECK_NAME", URLDecoder.decode(varList.get(i).getString("NECK_NAME"), "utf-8"));
            }
            if (varList.get(i).containsKey("STAFF_NECK_NAME")) {
                varList.get(i).put("STAFF_NECK_NAME", URLDecoder.decode(varList.get(i).getString("STAFF_NECK_NAME"), "utf-8"));
            }
        }
        return lms;
    }

    /**
     * 打印卡券核销管理全部数据
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel( ) throws Exception {

        logBefore(logger, Jurisdiction.getUsername() + "导出Cancel到excel");
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        this.getRequest().setCharacterEncoding("UTF-8");
        pd = this.getPageData();
        Page page = new Page();
        page.setPd(pd);
        pd.put("page", page);
        LayMessage lms = cartCancleService.list(pd, page);
        //获取返回的数据
        List<PageData> varOList = (List<PageData>) lms.getData();
        //将乱码转成中文
        for (int i = 0; i < varOList.size(); i++) {
            if (varOList.get(i).containsKey("NECK_NAME")) {
                varOList.get(i).put("NECK_NAME", URLDecoder.decode(varOList.get(i).getString("NECK_NAME"), "utf-8"));
            }
            if (varOList.get(i).containsKey("STAFF_NECK_NAME")) {
                varOList.get(i).put("STAFF_NECK_NAME", URLDecoder.decode(varOList.get(i).getString("STAFF_NECK_NAME"), "utf-8"));
            }
        }

        Map<String, Object> dataMap = new HashMap<String, Object>();
        //表头名称
        List<String> titles = new ArrayList<String>();
        titles.add("网吧名称");    //1
        titles.add("操作用户");    //2
        titles.add("卡券名称");    //3
        titles.add("优惠券码");    //4
        titles.add("会员卡号");    //5
        titles.add("会员名称");    //6
        titles.add("卡券类型");  //7
        titles.add("核销时间");  //8
        titles.add("状态");    //9
        titles.add("金额");    //10

        dataMap.put("titles", titles);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("INTENET_NAME")==null?"":varOList.get(i).get("INTENET_NAME").toString()); //1
            vpd.put("var2", varOList.get(i).getString("NAME")==null?"":varOList.get(i).get("NAME").toString());   //2
            String CONSUME_SOURCE = varOList.get(i).getString("CONSUME_SOURCE");
            if( "FROM_MOBILE_HELPER".equals(CONSUME_SOURCE)){
                vpd.put("var3", "核销员-"+varOList.get(i).getString("STAFF_NECK_NAME")==null?"":varOList.get(i).get("STAFF_NECK_NAME").toString());//3
            }else{
                vpd.put("var3", varOList.get(i).getString("NAME")==null?"":varOList.get(i).get("NAME").toString());//3
            }
            vpd.put("var4", varOList.get(i).getString("SUB_TITLE")==null?"":varOList.get(i).get("SUB_TITLE").toString());    //4
            vpd.put("var5", varOList.get(i).getString("CARDED")==null?"":varOList.get(i).get("CARDED").toString());    //5
            vpd.put("var6", URLDecoder.decode(varOList.get(i).getString("NECK_NAME"), "utf-8")==null?"":varOList.get(i).get("NECK_NAME").toString());    //6

            String CARD_TICKET = varOList.get(i).get("CARD_TICKET").toString()==null?"":varOList.get(i).get("CARD_TICKET").toString();
            if("1".equals(CARD_TICKET)){
                vpd.put("var7", "现金券");//7
            }else{
                vpd.put("var7","非现金券");//7
            }
            vpd.put("var8", varOList.get(i).get("UPDATE_TIME")==null?"":varOList.get(i).get("UPDATE_TIME").toString());    //8
            String STATE = varOList.get(i).getString("MONEY_STATE")==null?"":varOList.get(i).get("MONEY_STATE").toString();
            if("1".equals(STATE)){
                vpd.put("var9", "已核销");//9
            }else if("2".equals(STATE)){
                vpd.put("var9", "充值处理中");
            }else if("3".equals(STATE)){
                vpd.put("var9", "充值失败");
            }else{
                vpd.put("var9", "");
            }
            vpd.put("var10", varOList.get(i).getString("CASH_NUMBER")==null?"":varOList.get(i).get("CASH_NUMBER").toString());  //10
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }
}
