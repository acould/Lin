package com.lk.service.billiCenter.consume.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lk.cache.CacheManager;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWConsume;
import com.lk.entity.billecenter.SWRechargeReturn;
import com.lk.service.billiCenter.base.SWBaseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.billiCenter.consume.ConsumeService;
import com.lk.service.billiCenter.request.RequestService;
import com.lk.service.billiCenter.response.ResponseService;
import com.lk.service.hd.CallBack;
import com.lk.service.hd.impl.Business;
import com.lk.service.system.store.StoreManager;
import com.lk.util.ErrUtil;
import com.lk.util.MsgUtil;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

@Service("consumeService")
public class ConsumeServiceImpl implements ConsumeService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="callBackService")
	private CallBack callBackService;
	@Resource(name = "requestService")
	private RequestService requestService;
	@Resource(name = "responseService")
	private ResponseService responseService;
	
	@Resource(name = "storeService")
	private StoreManager storeService;

	@Autowired
	private SWBaseService swBaseService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("RecordConsumeMapper.save", pd);
	}

	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("RecordConsumeMapper.edit", pd);
	}



	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RecordConsumeMapper.findById", pd);
	}


	@Override
	public Message2 sendConsume(String store_id, SWConsume consume) throws Exception {
        return swBaseService.sendToSW(store_id, consume, MsgUtil.MSG_CONSUME);
	}

	@Override
	public Message2 getConsumeByFlag(String msgFlag) throws Exception {
		List<PageData> list = new ArrayList();

		Message2 m2 = swBaseService.getMsgFromCache(msgFlag);
		if(m2.getErrcode() != 0){
			return m2;
		}

		if(StringUtil.isNotEmpty(m2.getData().get("arr"))){
			JSONArray arr = (JSONArray) m2.getData().get("arr");
			PageData pageData = (PageData) JSONObject.toBean(arr.getJSONObject(0), PageData.class);
			list.add(pageData);
		}
		return Message2.ok().addData("list", list);
	}


    /**
     * 减钱操作
     * @param store_id
     * @param consume
     * @return
     * @throws Exception
     */
    @Override
    public Message2 newConsume(String store_id, SWConsume consume) throws Exception{

        Message2 m2 = this.sendConsume(store_id, consume);
        if(m2.getErrcode() != 0){
            return m2;
        }

        Message2 m22 = this.getConsumeByFlag(m2.getData().get("msg_flag").toString());
        if(m22.getErrcode() != 0){
            return m22;
        }

        Object obj = new Object();
        if(((List<SWRechargeReturn>) m22.getData().get("list")).size() > 0){
            obj = ((List<Object>) m22.getData().get("list")).get(0);
            if(obj != null){
                return Message2.ok().addData("Object", obj);
            }
        }

        return Message2.ok().addData("Object", obj);
    }
}

