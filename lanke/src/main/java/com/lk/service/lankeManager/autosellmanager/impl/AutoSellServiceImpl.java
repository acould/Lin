package com.lk.service.lankeManager.autosellmanager.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.service.lankeManager.autosellmanager.AutoSellService;
import com.lk.util.PageData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AutoSellServiceImpl implements AutoSellService {

    private static String mapperName = "AutoSellManagerMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public LayMessage list(PageData pd, Page page) throws Exception {
        page.setPd(pd);
        List<PageData> list = this.dataListpage(page);//查询列表
        int count = list.size();
        for(int i=0;i<list.size();i++){
            Map map = new HashMap();
            String store_id = list.get(i).getString("store_id");
            Map map2 = new HashMap();
            map2.put("store_id",store_id);
            List<PageData> list2 = (List<PageData>) dao.findForList(mapperName + "getSellNo", map2);
            System.out.println(list2);
            if(list2.size()==0){
               map.put("sm_no","");
            }else if(list2.size()==1) {
                String sm_no = list2.get(0).getString("sm_no");
                map.put("sm_no",sm_no);
            }else {
                String sm_no = "";
                for (int j=0;j<list2.size();j++){
                    if(j!=(list2.size()-1)) {
                        sm_no += list2.get(j).getString("sm_no") + "、";
                    }else{
                        sm_no += list2.get(j).getString("sm_no");
                    }
                }
                map.put("sm_no",sm_no);
            }
            list.get(i).putAll(map);
        }
        String state = pd.getString("state");
        if("0".equals(state)){//有售货机
            for(int j=0;j<list.size();j++){
                String no = list.get(j).getString("sm_no");
                if("".equals(no)){
                    list.remove(j);
                    j--;
                }
            }
        }else if("1".equals(state)){//没有售货机
            for(int j=0;j<list.size();j++){
                String no = list.get(j).getString("sm_no");
                if(!"".equals(no)){
                    list.remove(j);
                    j--;
                }
            }
        }

        return LayMessage.ok(page.getTotalResult(), list);
    }

    @Override
    public LayMessage listInternetsell(PageData pd, Page page) throws Exception {
        page.setPd(pd);
        List<PageData> list = this.dataListpage2(page);//查询列表
        int count = list.size();
        for(int i=0;i<list.size();i++){
            Map map = new HashMap();
            String store_id = list.get(i).getString("store_id");
            Map map2 = new HashMap();
            map2.put("store_id",store_id);
            List<PageData> list2 = (List<PageData>) dao.findForList(mapperName + "getSellNo", map2);
            System.out.println(list2);
            if(list2.size()==0){
                list.remove(i);
                i--;
            }
            if(list2.size()>0){
                if(list2.size()==1) {
                    String sm_no = list2.get(0).getString("sm_no");
                    map.put("sm_no",sm_no);
                }else {
                    String sm_no = "";
                    for (int j=0;j<list2.size();j++){
                        if(j!=(list2.size()-1)) {
                            sm_no += list2.get(j).getString("sm_no") + "、";
                        }else{
                            sm_no += list2.get(j).getString("sm_no");
                        }
                    }
                    map.put("sm_no",sm_no);
                }
                list.get(i).putAll(map);
            }
        }
        return LayMessage.ok(page.getTotalResult(), list);
    }

    @Override
    public void delete(PageData pd) throws Exception {
       dao.delete(mapperName + "deleteSM",pd);
    }

    @Override
    public List getNoByStore_id(PageData pd) throws Exception {
        List list = (List) dao.findForList(mapperName + "getNoByStore_id", pd);
        return list;
    }

    @Override
    public void deleteAll(PageData pd) throws Exception {
        dao.delete(mapperName + "deleteAll", pd);
    }

    @Override
    public void save(@Param(value = "list") List<Map<String,Object>> list) throws Exception {
        dao.save(mapperName + "save",list);
    }

    @Override
    public Map getAppid(Map map) throws Exception {
        Map map2 = new HashMap<String,Object>();
        List<Map<String,Object>> list = (List<Map<String,Object>>) dao.findForList(mapperName + "getAppId", map);
        if(list.size()>0){
            String appid = (String) list.get(0).get("wechat_id");
            map2.put("appid",appid);
            List<Map<String,Object>> list2 = (List<Map<String,Object>>) dao.findForList(mapperName + "getStore_id", map);
            String store_id = (String) list2.get(0).get("store_id");
            map2.put("store_id",store_id);
        }

        return map2;
    }

    @Override
    public List<PageData> getMsgByopen_id(String open_id) throws Exception {
        Map map = new HashMap();
        map.put("open_id",open_id);
        return (List<PageData>) dao.findForList(mapperName + "getMsgByopen_id",map);
    }

    @Override
    public List<PageData> getMember(String user_id) throws Exception {
        Map map = new HashMap();
        map.put("user_id",user_id);
        return (List<PageData>) dao.findForList(mapperName + "getMember",map);
    }

    @Override
    public void getPay(int price) throws Exception {

    }

    @Override
    public String getUsername(String store_id) throws Exception{
        Map map = new HashMap();
        map.put("store_id",store_id);
        List<PageData> list = (List<PageData>) dao.findForList(mapperName + "getUserName",map);
        String autosell_username = (String) list.get(0).get("autosell_username");
        return autosell_username;
    }

    @Override
    public List<PageData> getStoreAndId(String out_order_no) throws Exception {
        Map map = new HashMap();
        map.put("out_order_no",out_order_no);
        return (List<PageData>) dao.findForList(mapperName + "getStoreAndId",map);
    }

    @Override
    public void insertRefundMsg(PageData pd) throws Exception {
        dao.save(mapperName + "insertRefundMsg",pd);
    }

    @Override
    public String getInternet(String store_id) throws Exception {
        Map map = new HashMap();
        map.put("store_id",store_id);
        List<PageData> list = (List<PageData>) dao.findForList(mapperName + "getInternet",map);
        String internet_id = (String) list.get(0).get("INTERNET_ID");
        return internet_id;
    }

    @Override
    public String template_Id(String internet_id, String temple_name) throws Exception{
        Map map = new HashMap();
        map.put("internet_id",internet_id);
        map.put("temple_name",temple_name);
        List<PageData> list = (List<PageData>) dao.findForList(mapperName + "getTemplate",map);
        String template_id = (String) list.get(0).get("template_id");
        return template_id;
    }

    @Override
    public void addOrder(Map<String, Object> map) throws Exception {
        dao.save(mapperName + "addOrder",map);
    }

    @Override
    public List<PageData> getOrderById(String id) throws Exception {
        Map map = new HashMap();
        map.put("id",id);
        return (List<PageData>) dao.findForList(mapperName + "getOrderById",map);
    }

    @Override
    public void addUserMsg(Map<String, Object> map) throws Exception {
        dao.update(mapperName + "addUserMsg",map);
    }

    @Override
    public void addState(Map<String, Object> map2) throws Exception {
        dao.update(mapperName + "addState",map2);
    }

    @Override
    public List<PageData> findOrder(String order_no) throws Exception {
        Map map = new HashMap();
        map.put("order_no",order_no);
        return (List<PageData>) dao.findForList(mapperName + "findOrder",map);
    }

    @Override
    public void addUserMsg2(Map<String, Object> map) throws Exception {
        dao.update(mapperName + "addUserMsg2",map);
    }

    @Override
    public PageData selectRefundMsg(String out_order_no) throws Exception {
        Map map = new HashMap();
        map.put("out_order_no",out_order_no);
        return (PageData) dao.findForObject(mapperName +"selectRefundMsg",map);
    }

    @Override
    public void addState2(String lanke_order_no) throws Exception {
        Map map = new HashMap();
        map.put("lanke_order_no",lanke_order_no);
        map.put("order_state","2");
        dao.update(mapperName + "addState2",map);
    }

    @Override
    public PageData getOrderResult(String out_order_no) throws Exception {
        Map map = new HashMap();
        map.put("out_order_no",out_order_no);
        return (PageData) dao.findForObject(mapperName+"getOrderResult",map);
    }

    @Override
    public PageData getStoreName(String store_id) throws Exception {
        Map map = new HashMap();
        map.put("store_id",store_id);
        return (PageData) dao.findForObject(mapperName+"getStoreName",map);
    }

    @Override
    public PageData check(String sm_no) throws Exception {
        Map map = new HashMap();
        map.put("sm_no",sm_no);
        return (PageData) dao.findForObject(mapperName+"check",map);
    }

    @Override
    public PageData checkOrder(String order_no) throws Exception {
        Map map = new HashMap();
        map.put("order_no",order_no);
        return (PageData) dao.findForObject(mapperName+"checkOrder",map);
    }

    private List<PageData> dataListpage(Page page) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "datalistPage", page);
    }

    public List<PageData> dataListpage2(Page page) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "datalistPage2", page);
    }
}
