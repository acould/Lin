package com.lk.service.lankeManager.signmanager.impl;

import com.lk.dao.DaoSupport;
import com.lk.service.lankeManager.signmanager.SignService;
import com.lk.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SignServiceImpl implements SignService {

    private static String mapperName = "SignMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public List<PageData> getSign(String user_id, String date,String store_id) throws Exception {
        Map map = new HashMap<String,Object>();
        map.put("user_id",user_id);
        map.put("date",date);
        map.put("store_id",store_id);
        return (List<PageData>) dao.findForList(mapperName + "getSign",map);
    }

    @Override
    public List<PageData> getSigns(PageData pd) throws Exception {
        return(List<PageData>) dao.findForList(mapperName + "getSigns",pd);
    }

    @Override
    public void addSign(String id, String signCount, String date, String store_id, String user_id,String integral) throws Exception {
        Map map = new HashMap();
        sign(id, signCount, date,store_id,user_id,integral,map);
        PageData pd = (PageData) dao.findForObject(mapperName + "Signs",map);
        int INTEGRAL =0;
        if(pd==null){
            INTEGRAL =0;
        }else{
            INTEGRAL =(int) pd.get("INTEGRAL");
        }
        int sum = Integer.parseInt(integral)+INTEGRAL;
        map.put("sum",sum);
        dao.update(mapperName + "updateSign",map);
    }

    @Override
    public void addSign2(String id, String signCount, String date, String store_id, String user_id, String integral) throws Exception {
        Map map = new HashMap();
        sign(id, signCount, date,store_id,user_id,integral,map);
    }

    public void  sign(String id, String signCount, String date, String store_id, String user_id, String integral,Map map) throws Exception{
        map.put("id", id);
        map.put("signCount", signCount);
        map.put("date", date);
        map.put("store_id", store_id);
        map.put("user_id", user_id);
        map.put("integral", integral);
        dao.save(mapperName + "addSign", map);
    }
}
