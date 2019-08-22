package com.lk.service.lankeManager.officialtemmanager.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.service.internet.enroll.impl.EnrollServiceImpl;
import com.lk.service.lankeManager.officialtemmanager.OfficialTemplateService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.wechat.util.TemplateMsgUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class OfficialTemplateServiceImpl implements OfficialTemplateService {

    public static final String user_up_short = "网吧会员上机通知";// 1网吧会员上机通知
    public static final String user_down_short = "网吧会员下机通知";// 2网吧会员下机通知
    public static final String member_mark_short = "系统事件通知";// 3会员营销（系统事件通知）
    public static final String money_change_short = "账户资金变动提醒";// 4账户资金变动提醒
    public static final String server_guard_short = "服务通知";// 5服务通知
    public static final String card_buy_success = "购买成功通知";// 6购买成功通知
    public static final String server_plan_notify = "服务进度提醒";// 7服务进度提醒

    //网吧服务人员接受模板
    public static final String user_complain_short = "投诉通知";// 8投诉通知
    public static final String user_call_short = "呼叫服务提醒";// 9呼叫服务提醒
    public static final String user_order_short = "新订单通知";// 10新订单通知

    public static final String server_maintenance_short = "服务器维护通知";//11服务器维护通知
    public static final String equipment_failure_short = "设备故障通知";// 12设备故障通知
    public static final String failure_recovery_short = "故障恢复通知";// 13故障恢复通知

    private static final Logger log = LoggerFactory.getLogger(EnrollServiceImpl.class);
    private static String mapperName = "OfficialTemplateMapper.";
/*
*  5GXk3RQnffEcQygvp_P-zGMWJLjTlrGFy9nkOvUcgO4  Sg0E4EkIEI15lUoqxLK92LlDFZZg0SlOhGuFLJbHN9E oDMsySe9VQmH7LC0jo553m-SIvtdpLbIHT0qu_DdtH0
*
* */
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Autowired
    private AutoReplyService autoReplyService;

    /**
     * 遍历公众号模板列表
     * @param pd
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public LayMessage list(PageData pd, Page page) throws Exception {
        page.setPd(pd);
        List<PageData> list = this.dataListpage(page);
        return LayMessage.ok(page.getTotalResult(), list);
    }

    /**
     * 得到所有公众号的id
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> getInternetList() throws Exception {
        return  (List<PageData>) dao.findForList(mapperName + "listAll",null);
    }

    /**
     * 根据微信公众号的信息更新第三方平台模板消息
     * @param id
     * @param list
     * @throws Exception
     */
    @Override
    public void getinternetById(String id, List<Map<String, Object>> list) throws Exception {

        int count = (int) dao.findForObject(mapperName+"getInternetId",id);//查询该公众号是否存在

        for(int i=0;i<list.size();i++){
            Map<String,Object> map = new HashMap<String,Object>();//创建一个map集合
            JSONObject jsonObj=JSONObject.fromObject(list.get(i));//转成将集合转成json
            String mid =  StringUtil.get32UUID();
            map.put("mid",mid);
            String template_id =  jsonObj.getString("template_id");//1.模板id
            getInsertMap(id,template_id,map,jsonObj);
            map.put("internet_id",id);//8、公众号ID
            //list2.add(map);
            dao.save(mapperName +"inserttemplateId",map);
        }
       /* List<Map<String,Object>> list2 = new ArrayList<>();//转map的集合list
        if(list.size()!=0){
            for(int i=0;i<list.size();i++){
                Map<String,Object> map = new HashMap<String,Object>();//创建一个map集合
                JSONObject jsonObj=JSONObject.fromObject(list.get(i));//转成将集合转成json
                String mid =  StringUtil.get32UUID();
                map.put("mid",mid);
                String template_id =  jsonObj.getString("template_id");//1.模板id
                getInsertMap(id,template_id,map,jsonObj);
                map.put("internet_id",id);//8、公众号ID
                //list2.add(map);
            }
        }*/

        /*if(count==0){//如果第三方平台没有这个公众号，那么就全部插入
            //dao.save(mapperName +"insertForeach", list2);
            for(int i=0;i<list.size();i++){
                Map<String,Object> map = new HashMap<String,Object>();//创建一个map集合
                JSONObject jsonObj=JSONObject.fromObject(list.get(i));//转成将集合转成json
                String mid =  StringUtil.get32UUID();
                map.put("mid",mid);
                String template_id =  jsonObj.getString("template_id");//1.模板id
                getInsertMap(id,template_id,map,jsonObj);
                map.put("internet_id",id);//8、公众号ID
                //list2.add(map);
                dao.save(mapperName +"inserttemplateId",map);
            }
        }else{//如果第三平台有这个公众号，那么查询模板，有该模板，则更新，没有该模板，则插入
            for(int i=0;i<list.size();i++) {
                JSONObject jsonObj=JSONObject.fromObject(list.get(i));
                String template_id =  jsonObj.getString("template_id");
                int count2 = (int)dao.findForObject(mapperName+"getTemplateId",template_id);
                if(count2==0){//如果没有这个模板，则插入
                    Map<String,Object> map = new HashMap<String,Object>();//创建一个map集合
                    String mid =  StringUtil.get32UUID();
                    map.put("mid",mid);
                    getInsertMap(id,template_id,map,jsonObj);
                    map.put("internet_id",id);//8、公众号ID
                    dao.save(mapperName + "inserttemplateId",map);
                }else{//有这个模板则更新
                    Map<String,Object> map = new HashMap<String,Object>();//创建一个map集合
                    getInsertMap(id,template_id,map,jsonObj);
                    dao.update(mapperName + "updateTemplate",map);
                }
            }
        }*/
    }

    /**
     * 删除第三方平台在微信公众号下面的所有不存在的模板
     * @param internet_id
     * @param list
     * @throws Exception
     */
    @Override
    public void getExist(String internet_id, List<Map<String, Object>> list) throws Exception {
        String[] template_ids  = new String[list.size()];
        if(list.size()!=0) {
            for (int i = 0; i < list.size(); i++) {
                JSONObject jsonObj=JSONObject.fromObject(list.get(i));//转成将集合转成json
                template_ids[i] =  jsonObj.getString("template_id");
            }
        }
        dao.delete(mapperName+"deleteTemplate", template_ids);
    }

    /**
     * 批量删除第三方平台多余的公众号id数据
     * @param strs
     * @throws Exception
     */
    @Override
    public void getExist2(String[] strs) throws Exception {
        dao.delete(mapperName+"deleteAll", strs);
    }

    /**
     * 删除微信端的模板信息
     * @param pd
     * @throws Exception
     */
    @Override
    public void deleteTemplate(PageData pd) throws Exception {//template_id -> GeUOWNUis1sdQHuV6WalgwrYqLiqzQRXvw28KnNTG5A3
        String template_id = pd.getString("template_id");
        String internet_id = pd.getString("internet_id");
        String token = autoReplyService.getAuthToken(internet_id);
        dao.delete(mapperName+"deleteT",template_id);
        TemplateMsgUtil.delTemplate(token,template_id);//删除微信端的模板消息
    }

    @Override
    public void deleteTemplate2() throws Exception {
        dao.delete(mapperName+"deleteT2",null);
    }

    /**
     * 查询数据库
     * @param page
     * @return
     * @throws Exception
     */
    private List<PageData> dataListpage(Page page) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "datalistPage", page);
    }

    /**
     * 将获取的数据装入到一个map中
     * @param id
     * @param template_id
     * @param map
     * @param jsonObj
     */
    private void getInsertMap(String id,String template_id,Map<String,Object> map,JSONObject jsonObj){
        map.put("template_id",template_id);
        String title =  jsonObj.getString("title");//2.标题
        map.put("title",title);
        switch (title) {//判断状态
            case user_up_short://1
                map.put("type","user_up");
                break;
            case user_down_short://2
                map.put("type","user_down");
                break;
            case member_mark_short://3
                map.put("type","member_mark");
                break;
            case money_change_short://4
                map.put("type","money_change");
                break;
            case server_guard_short://5
                map.put("type","server_guard");
                break;
            case server_plan_notify://6
                map.put("type","server_plan_notify");
                break;
            case user_complain_short://7
                map.put("type","user_complain");
                break;
            case user_call_short://8
                map.put("type","user_call");
                break;
            case user_order_short://9
                map.put("type","user_order");
                break;
            case server_maintenance_short://10
                map.put("type","server_maintenance");
                break;
            case card_buy_success://11
                map.put("type","card_buy_success");
                break;
            case equipment_failure_short://12
                map.put("type","equipment_failure");
                break;
            case failure_recovery_short://13
                map.put("type","failure_recovery");
                break;
        }
        String content =  jsonObj.getString("content");//3.内容
        map.put("content",content);
        String primary_industry =  jsonObj.getString("primary_industry");//4.一级行业
        map.put("primary_industry",primary_industry);
        String deputy_industry =  jsonObj.getString("deputy_industry");//5.二级行业
        map.put("deputy_industry",deputy_industry);
        String example =  jsonObj.getString("example");//6.例子
        map.put("example",example);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转格式
        String dateStr = sdf.format(date);
        map.put("create_time",dateStr);//7、创建时间
    }

}
