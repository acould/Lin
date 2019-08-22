package com.lk.controller.lankeManager.vouchermanager;

import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.service.lankeManager.officialtemmanager.OfficialTemplateService;
import com.lk.service.lankeManager.vouchermanager.VoucherManagerService;
import com.lk.util.PageData;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/vouchermanager")
public class VoucherManagerController extends BaseController {

    @Resource
    private VoucherManagerService voucherManagerService;

    @RequestMapping("/goList")
    public ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("lankemanager/vouchermanager/vouchermanager_list");
        return mv;
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public LayMessage getList(Page page) throws Exception {
        PageData pd = this.getPageData();
        pd.put("page", page);
        LayMessage lms = voucherManagerService.list(pd, page);
        return lms;
    }

    @RequestMapping(value = "/send")
    @ResponseBody
    public JSONObject send() throws Exception {
        PageData pd = this.getPageData();
        JSONObject  sendResult= voucherManagerService.reSend(pd);
        return sendResult;
    }

}
