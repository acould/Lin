package com.lk.controller.base;

import com.lk.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author myq
 * @description
 * @create 2019-04-24 18:04
 */
@Controller
@RequestMapping(value = "/opera")
public class OperationController extends BaseController{

    @RequestMapping(value = "/info")
    public ModelAndView info(){
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        mv.addObject("pd", pd);
        mv.setViewName("internet/infoTips/info");
        return mv;
    }


    @RequestMapping(value = "/tips")
    public ModelAndView tips(){
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();

        mv.addObject("pd", pd);
        mv.setViewName("internet/infoTips/tips");
        return mv;
    }
}
