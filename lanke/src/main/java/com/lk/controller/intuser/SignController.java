package com.lk.controller.intuser;

import com.lk.controller.base.BaseController;
import com.lk.entity.system.User;
import com.lk.service.lankeManager.signmanager.SignService;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/signmanager")
public class SignController extends BaseController {

    private  static Logger logger = LoggerFactory.getLogger(SignController.class);

    @Autowired
    private SignService signService;

    @RequestMapping(value = "/sign")
    @ResponseBody
    public JSONObject sign() throws Exception{

        logger.info("================================================进入签到");
        JSONObject json = new JSONObject();
        User user = this.getUser();
        String user_id = user.getUSER_ID();
        PageData pd = this.getPageData();
        String INTENET_ID = user.getINTENET_ID();
        pd.put("INTENET_ID",INTENET_ID);//42d593519e6349a48825af3d89272898
        String store_id = pd.getString("store_id");
        pd.put("store_id",store_id);
        logger.info("================================store_id"+store_id);
        if(store_id==null||"".equals(store_id)){
            return null;
        }
        Date time = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(time);
        //判断该用户当天是否签到过
        List<PageData> list = signService.getSign(user_id,date,store_id);
        String signCount = null;
        List<PageData> list2  = signService.getSigns(pd);
        String sign_time_set =null;
        String[] strs =null;
        if(list2.size()>0){
            sign_time_set = list2.get(0).getString("sign_time_set");
            strs = sign_time_set.split(",");
        }
        String integral = null;
        String id = StringUtil.get32UUID();
        if(sign_time_set==null){
            json.put("result_code","1");
            json.put("msg","该门店没有设置扫码上机");
            return json;
        }
        json.put("sign_time_set",sign_time_set);
        if(list.size()>0){
            json.put("result_code","0");
            //签到了，则返回签到次数和积分
            signCount = list.get(0).getString("signCount");
            json.put("signCount",signCount);
        }else{
            json.put("result_code","0");
            //没有签到，在查看昨天是否签到过
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(calendar.DATE, -1);
            time = calendar.getTime();
            SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = format.format(time);
            List<PageData> list3 = signService.getSign(user_id,dateStr,store_id);
            if(list3.size()>0){
                //查看签到表中签到的天数，获取相应的积分，开始签到
                signCount = list3.get(0).getString("signCount");
                int num = 0;
                if("7".equals(signCount)){//签到天数为7，更新为1
                    signCount ="1";
                }else{
                    num = Integer.parseInt(signCount);
                    signCount= String.valueOf(num+1);
                }
                integral = strs[num];//积分数
                signService.addSign(id,signCount,date,store_id,user_id,integral);
                json.put("signCount",signCount);
            }else{
                //开始第一天签到
                integral = strs[0];
                signCount = "1";
                signService.addSign(id,signCount,date,store_id,user_id,integral);
                json.put("signCount",String.valueOf(Integer.parseInt(signCount)+1));
            }
        }
        logger.info("=====================签到返回结果============"+json.toString());
        return json;
    }
}
