package com.lk.service.tb.Message.impl;

import java.util.List;
import javax.annotation.Resource;

import com.lk.service.internet.jiaLian.JiaLianService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.payManager.PayOpenManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.tb.Message.MessageManager;
import com.lk.util.PageData;
import com.lk.util.StringUtil;

/**
 * 消息通知--业务层
 * @author myq
 *
 */
@Service("messageService")
public class MessageService implements MessageManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "payOpenService")
	private PayOpenManager payOpenService;

	@Autowired
	private JiaLianService jiaLianService;
	
	/**
	 * 保存数据
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception{
		dao.save("MessageMapper.save", pd);
	}
	
	
	/**
	 * 修改数据
	 * @param pdMessage
	 * @throws Exception
	 */
	public void edit(PageData pdMessage) throws Exception{
		dao.update("MessageMapper.edit", pdMessage);
	}
	
	
	/**
	 * 查询未读的消息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByStateOrType(PageData pd) throws Exception{
		List<PageData> list = (List<PageData>) dao.findForList("MessageMapper.findByStateOrType", pd);
		
		//处理数据
		for(PageData pdd : list){
			if(StringUtil.isNotEmpty(pdd.get("announce_time"))){
				pdd.put("announce_time", pdd.get("announce_time").toString());
			}
			if(StringUtil.isNotEmpty(pdd.get("read_time"))){
				pdd.put("read_time", pdd.get("read_time").toString());
			}
		}
		return list;
	}


	/**
	 * 加载首页数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadMessage(PageData pd, Page page) throws Exception{
		JSONObject result = new JSONObject();


		if(StringUtil.isEmpty(pd.get("state"))){
			pd.put("state", "0");
		}

		page.setPd(pd);
		List<PageData> varList = (List<PageData>) dao.findForList("MessageMapper.datalistPage", page);
		System.out.println("pageService2======"+page.toString());
		//处理数据
		for(PageData pdd : varList){
			if(StringUtil.isNotEmpty(pdd.get("announce_time"))){
				pdd.put("announce_time", pdd.get("announce_time").toString());
			}
			if(StringUtil.isNotEmpty(pdd.get("read_time"))){
				pdd.put("read_time", pdd.get("read_time").toString());
			}
			if(StringUtil.isNotEmpty(pdd.get("state"))){
				if(pdd.get("state").toString().equals("0")){
					pdd.put("state", "未读");
				}
				if(pdd.get("state").toString().equals("1")){
					pdd.put("state", "已读");
				}
			}
			if(StringUtil.isNotEmpty(pdd.get("type"))){
				if(pdd.get("type").toString().equals("pubwin")){
					pdd.put("type", "计费系统");
				}
				if(pdd.get("type").toString().equals("payonline")){
					pdd.put("type", "在线支付");
				}
				if(pdd.get("type").toString().equals("jl_pay")){
					pdd.put("type", "嘉联支付");
				}
			}
		}


		result.put("data", varList);
		result.put("count", page.getTotalResult());
		result.put("msg", "查询成功");
		result.put("showCount", page.getShowCount());
		result.put("code", "0");
		return result;
	}
	
	
	/**
	 * 查询消息数量
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Integer findNumber(PageData pd) throws Exception{
		return  (int) dao.findForObject("MessageMapper.totalNum", pd);
	}
	
	/**
	 * 获取消息的主表信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject showDetail(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		PageData pdMessage = new PageData();
		
		String message_id = pd.getString("message_id");
		String type = pd.getString("type");
		if(type.equals("计费系统") || type.equals("pubwin")){
			type = "pubwin";
			
			pdMessage.put("store_id", message_id);
			pdMessage = storeService.findByStoreIdAndState(pdMessage);
			
			result.put("STORE_ID", message_id);
			result.put("STATE", pdMessage.get("STATE"));
			result.put("backUrl", "storeReview/toEdit.do");
		}else if(type.equals("在线支付") || type.equals("payonline")){
			type = "payonline";
			
			pdMessage.put("id", message_id);
			pdMessage = payOpenService.findById(pdMessage);
			
			result.put("idd", pdMessage.getString("id"));
			result.put("store_idd", pdMessage.getString("store_id"));
			result.put("backUrl", "accountSettings/goPayOpen.do");
		}else if(type.equals("嘉联支付") || type.equals("jl_pay")){
			type = "payonline";

			pdMessage.put("id", message_id);
			pdMessage = jiaLianService.findById(message_id);

			result.put("idd", pdMessage.getString("id"));
			result.put("store_idd", pdMessage.getString("store_id"));
			result.put("backUrl", "jialian/goEdit.do");
		}else{
			result.put("result", "false");
			result.put("message", "参数不正确");
			return result;
		}
		
		int number = (int) dao.findForObject("MessageMapper.notReadNum", pd);
		
		result.put("number", number);
		result.put("type", type);
		result.put("result", "true");
		return result;
	}
	
	
	
	
	
}
