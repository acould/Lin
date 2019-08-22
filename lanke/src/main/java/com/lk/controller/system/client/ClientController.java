package com.lk.controller.system.client;

import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.client.ClientService;
import com.lk.service.system.client.impl.ClientServiceImpl;
import com.lk.util.*;
import io.netty.channel.ChannelHandlerContext;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

/**
 * 客户端管理
 * @Title ClientController.java
 * @author 陈明阳
 * @date 2018年10月17日下午2:47:02
 */
@Controller
@RequestMapping(value="/client")
public class ClientController extends BaseController {

	String menuUrl = "client/clientList.do"; //菜单地址(权限用)
	
	@Resource(name = "clientService")
	private ClientService clientService;
	
	/**客户端管理跳转列表页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/clientList")
	public ModelAndView clientList1() throws Exception{
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		if(PublicManagerUtil.AGENTROLEID.equals(user.getROLE_ID())){
			mv.addObject("state", 1);
		}

		mv.setViewName("system/client/clientList");
		return mv;
	}
	
	/**
	 * ajax查询客户端管理列表信息
	 * @return json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/clientLists")
	public JSONObject clientList(Page page) throws Exception{
        //查询分页查询列表
        PageData pd = this.getPageData();
		User user = this.getUser();//得到用户
		if(PublicManagerUtil.AGENTROLEID.equals(user.getROLE_ID())){
			pd.put("agent_id",user.getANGET_ID());
		}
		JSONObject json = clientService.list(pd,page);// 获取网吧的充值报表信息
        return json;
	}

	/**
	 * liabiao
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/clientDetail")
	public ModelAndView clientDetail() throws Exception{
		ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        mv.addObject("pd", pd);
		mv.setViewName("system/client/client_detail");
		return mv;
	}



	@ResponseBody
	@RequestMapping(value="/loadClientDetail")
	public JSONObject loadClientDetail(Page page) throws Exception{
		//查询分页查询列表
        //传入store_id
		PageData pd = this.getPageData();


		pd.put("page", page);
		JSONObject json = clientService.loadClientDetail(pd);// 获取网吧的充值报表信息
		return json;
	}

    @ResponseBody
    @RequestMapping(value="/setParam")
    public JSONObject setParam() throws Exception{
        JSONObject result = this.getErrorJson();
        //查询分页查询列表
        //传入store_id
        PageData pd = this.getPageData();
        String store_id = pd.getString("store_id");
        if(StringUtil.isEmpty(store_id)){
            result.put("message", "缺少参数");
            return result;
        }

        if(!ChannelCache.channelMap.containsKey(store_id)){
            result.put("message", "揽客客户端已断开连接，请先连接");
            return result;
        }

        //设置扫码上机参数
        new Thread(new ClientServiceImpl.setQrParam(store_id)).start();

        result.put("result", "true");
        return result;
    }



	@ResponseBody
	@RequestMapping(value="/getOut")
	public JSONObject getOut() throws Exception{
		JSONObject result = this.getErrorJson();
		//传入store_id
		PageData pd = this.getPageData();

		String store_id = pd.getString("store_id");
		if(!ChannelCache.channelMap.containsKey(store_id)){
			result.put("message", store_id + "：缓存不存在");
			return result;
		}

		//去连接
		ChannelCache.channelMap.get(store_id).close();

		//去缓存
		ChannelCache.channelMap.remove(store_id);

		result.put("message", "踢出成功");
		return result;
	}




	@ResponseBody
	@RequestMapping(value="/setAllQrParam")
	public JSONObject setAllQrParam() throws Exception{
		JSONObject result = this.getErrorJson();

		Iterator<Map.Entry<String, ChannelHandlerContext>> iterator = ChannelCache.channelMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, ChannelHandlerContext> entry = iterator.next();
			String store_id = entry.getKey();
			//设置扫码上机参数
			new Thread(new ClientServiceImpl.setQrParam(store_id)).start();
		}

		result.put("result", "true");
		return result;
	}



	
}
