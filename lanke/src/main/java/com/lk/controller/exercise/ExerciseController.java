package com.lk.controller.exercise;

import com.lk.controller.base.BaseController;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/exercise")
public class ExerciseController extends BaseController {

    /**
     * 联系demo
     * @return
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public JSONObject info(){
        JSONObject json = new JSONObject();
        json.put("msg","获取成功");
        return json;
    }

    @RequestMapping(value="/goList")
    public ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("system/exercise/exercise_list");
        return mv;
    }
}
