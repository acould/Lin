package com.lk.service.lankeManager.cusmanager.Impl;

import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.service.lankeManager.cusmanager.CusManagerService;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import io.netty.channel.ChannelHandlerContext;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cusManagerService")
public class CusManagerServiceImpl implements CusManagerService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public LayMessage list(PageData pd, Page page) throws Exception{
        Page pa = (Page) pd.get("page");
        String vesion = (String) pd.get("vesion");//0为轻营销，1为轻营销+V，2为专业版
        String paystatus = (String)  pd.get("paystatus");
        List<PageData> list = null;
        if("1".equals(vesion) && ("0".equals(paystatus) || "1".equals(paystatus))){
            pd.put("state",1);
            pd.put("status",0);
            pd.put("status2",0);
            pa.setPd(pd);
            list = (List<PageData>) dao.findForList("CustomerManager.datalistPage", pa);
        }else if("2".equals(vesion) && ("0".equals(paystatus) || "1".equals(paystatus))){
            pd.put("state",1);
            pd.put("status",1);
            pd.put("status2",1);
            pa.setPd(pd);
            list = (List<PageData>) dao.findForList("CustomerManager.datalistPage2", pa);
        }else if("0".equals(vesion) && ("0".equals(paystatus) || "1".equals(paystatus))){
            pd.put("state",0);
            pd.put("status",0);
            pd.put("status2",0);
            pa.setPd(pd);
            list = (List<PageData>) dao.findForList("CustomerManager.datalistPage3", pa);
        }else if("0".equals(vesion)){//基础版
            pd.put("state",0);
            pd.put("status",0);
            pd.put("status2",0);
            pa.setPd(pd);
            list = (List<PageData>) dao.findForList("CustomerManager.datalistPage5", pa);
        }else if("1".equals(vesion)){//加v版
            pd.put("state",1);
            pd.put("status",0);
            pd.put("status2",0);
            pa.setPd(pd);
            list = (List<PageData>) dao.findForList("CustomerManager.datalistPage6", pa);
        }else if("2".equals(vesion)){//专业版
            pd.put("state",1);
            pd.put("status",1);
            pd.put("status2",1);
            pa.setPd(pd);
            list = (List<PageData>) dao.findForList("CustomerManager.datalistPage7", pa);
        }else{
            pa.setPd(pd);
            list = (List<PageData>) dao.findForList("CustomerManager.datalistPage4", pa);
        }

        for(PageData pdd : list){
            String store_id = (String) pdd.get("STORE_ID");
            ChannelHandlerContext flag = ChannelCache.channelMap.get("store_id");
             if(flag!=null){
                 pdd.put("link_state","在线");
                 pdd.put("link_state","在线");
             }else{
                 pdd.put("link_state","离线");
             }
             int status = pdd.get("status")==null?-1:(int) pdd.get("status");
             int status2 = pdd.get("status2")==null?-1:(int) pdd.get("status2");
             int state = pdd.get("state")==null?-1:(int) pdd.get("state");
             if((status==1 || status2==1) && state==1  ){
                 pdd.put("lanker_version","专业版");
             }else if((status!=1 && status2!=1) && state==1){
                 pdd.put("lanker_version","轻营销+V");
             }else{
                 pdd.put("lanker_version","轻营销版");
             }
        }
        return LayMessage.ok(page.getTotalResult(), list);
    }

    @Override
    public List<PageData> listExcel(Page page) throws Exception {
        return (List<PageData>) dao.findForList("CustomerManager.listUU", page);
    }

    @Override
    public JSONObject listSum(Page page) throws Exception {
        JSONObject result = new JSONObject();
        PageData pd = page.getPd();
        String vesion = (String) pd.get("vesion");//0为基础版，1为加v版，2为专业版
        String paystatus = (String)  pd.get("paystatus");//0为试用一个月，1为付费一年
        String keywords = (String)  pd.get("keywords");
        List<PageData> list =null;
        List<PageData> list2 =null;
        page.setPd(pd);

        if("1".equals(vesion)&& ("0".equals(paystatus) || "1".equals(paystatus))){
            pd.put("state",1);
            pd.put("status",0);
            pd.put("status2",0);
            list = (List<PageData>) dao.findForList("CustomerManager.listSum", page);
            list2 = (List<PageData>) dao.findForList("CustomerManager.listSum11", page);
        }else if("2".equals(vesion)&& ("0".equals(paystatus) || "1".equals(paystatus))){
            pd.put("state",1);
            pd.put("status",1);
            pd.put("status2",1);
            list = (List<PageData>) dao.findForList("CustomerManager.listSum2", page);
            list2 = (List<PageData>) dao.findForList("CustomerManager.listSum22", page);
        }else if("0".equals(vesion) && ("0".equals(paystatus) || "1".equals(paystatus))){
            pd.put("state",0);
            pd.put("status",0);
            pd.put("status2",0);
            list = (List<PageData>) dao.findForList("CustomerManager.listSum3", page);
            list2 = (List<PageData>) dao.findForList("CustomerManager.listSum33", page);

        }else if("0".equals(vesion)){
            pd.put("state",0);
            pd.put("status",0);
            pd.put("status2",0);
            list = (List<PageData>) dao.findForList("CustomerManager.listSum5", page);
            list2 = (List<PageData>) dao.findForList("CustomerManager.listSum55", page);
        }else if("1".equals(vesion)){
            pd.put("state",1);
            pd.put("status",0);
            pd.put("status2",0);
            list = (List<PageData>) dao.findForList("CustomerManager.listSum6", page);
            list2 = (List<PageData>) dao.findForList("CustomerManager.listSum66", page);
        }else if("2".equals(vesion)){
            pd.put("state",1);
            pd.put("status",1);
            pd.put("status2",1);
            list = (List<PageData>) dao.findForList("CustomerManager.listSum7", page);
            list2 = (List<PageData>) dao.findForList("CustomerManager.listSum77", page);
        }else{
            list = (List<PageData>) dao.findForList("CustomerManager.listSum4", page);
            list2 = (List<PageData>) dao.findForList("CustomerManager.listSum44", page);
        }
        int sum = 0;
        Map map = new HashMap();
        for(int i=0;i<list2.size();i++){
            Long fannums = (Long) list2.get(i).get("fannums");
            sum += fannums;
        }
        if(list.get(0)==null){//[{"sumfans":216,"sumstore":84,"sumprice":26.0}]
            List listb = new ArrayList();
            Map map2 = new HashMap<String, Object>();
            map.put("sumfans",0);
            map.put("sumstore",0);
            map.put("sumprice",0);
            listb.add(map);
            result.put("data",listb);
        }else{
            List listb = new ArrayList();
            Map map2 = new HashMap<String, Object>();
            map.put("sumfans",sum);
            map.put("sumstore",list.get(0).get("sumstore")==null?'0':list.get(0).get("sumstore"));
            map.put("sumprice",list.get(0).get("sumprice")==null?'0':list.get(0).get("sumprice"));
            listb.add(map);
            result.put("data",listb);
        }
        return result;
    }
}
