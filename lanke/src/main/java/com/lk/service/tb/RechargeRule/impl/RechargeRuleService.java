package com.lk.service.tb.RechargeRule.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.menu.MenuManager;
import com.lk.service.tb.RechargeRule.RechargeRuleManager;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

/**
 * 充值规则--业务层
 * @author myq
 *
 */
@Service("rechargeRuleService")
public class RechargeRuleService implements RechargeRuleManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "menuService")
	private MenuManager menuService;

	/**
	 * 首页列表
	 * @return
	 * @throws Exception
	 */
	public List<PageData> indexList(PageData pd) throws Exception{
		
		Page page = (Page) pd.get("page");
		User user = (User) pd.get("user");
		
		pd.put("internet_id", user.getINTENET_ID());
		System.out.println(pd.toString());
		page.setPd(pd);
		return (List<PageData>) dao.findForList("RechargeRuleMapper.datalistPage", page);
	}

	/**
	 *
	 * @param pd internet_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PageData> addedRulesStores(PageData pd) throws Exception {
		//已创建规则门店
		List<PageData> addedStoreList = (List<PageData>) dao.findForList("RechargeRuleMapper.findStoreId", pd);

		return addedStoreList;
	}

	
	/**
	 * 获取标签列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> labelList(PageData pd) throws Exception{
		
		return (List<PageData>) dao.findForList("LabelMapper.labelList", pd);
	}
	
	
	/**
	 * 新增标签
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveLabel(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		User user = (User) pd.get("user");
		if(StringUtil.isEmpty(user)){
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}
		
		if(StringUtil.isEmpty(pd.getString("label_name").trim())){
			result.put("result", "false");
			result.put("message", "请输入标签名");
			return result;
		}else if(pd.getString("label_name").length() > 4){
			result.put("result", "false");
			result.put("message", "标签名最多4个字符");
			return result;
		}
		
		if(StringUtil.isEmpty(pd.getString("label_color").trim())){
			result.put("result", "false");
			result.put("message", "请选择标签颜色");
			return result;
		}else if(!pd.getString("label_color").startsWith("color")){
			result.put("result", "false");
			result.put("message", "请选择正确的标签颜色");
			return result;
		}
		//默认保存HOT和特惠两个标签
		saveDefaultLabel(user.getINTENET_ID());
		//检查标签名是否存在
		PageData pdLabel = new PageData();
		pdLabel.put("label_name", pd.getString("label_name"));
		pdLabel.put("internet_id", user.getINTENET_ID());
		pdLabel = (PageData) dao.findForObject("LabelMapper.findByName", pdLabel);
		if(StringUtil.isNotEmpty(pdLabel)){
			result.put("result", "false");
			result.put("message", "该标签名已存在");
			return result;
		}
		
		pdLabel = new PageData();
		pdLabel.put("id", StringUtil.get32UUID());
		pdLabel.put("label_name", pd.getString("label_name"));
		pdLabel.put("label_color", pd.getString("label_color"));
		pdLabel.put("status", "0");
		pdLabel.put("internet_id", user.getINTENET_ID());
		dao.save("LabelMapper.save", pdLabel);
		
		
		result.put("result", "true");
		result.put("message", "添加成功");
		return result;
	}
	
	/**
	 * 保存默认标签（HOT,特惠）
	 * @param internet_id
	 * @throws Exception
	 */
	public void saveDefaultLabel(String internet_id) throws Exception{
		PageData pdLabel = new PageData();
		pdLabel.put("label_name", "HOT");
		pdLabel.put("internet_id", internet_id);
		pdLabel = (PageData) dao.findForObject("LabelMapper.findByName", pdLabel);
		if(StringUtil.isEmpty(pdLabel)){
			pdLabel = new PageData();
			pdLabel.put("id", StringUtil.get32UUID());
			pdLabel.put("label_name", "HOT");
			pdLabel.put("label_color", "color09");
			pdLabel.put("status", "0");
			pdLabel.put("internet_id", internet_id);
			dao.save("LabelMapper.save", pdLabel);
		}
		
		PageData pdLabel2 = new PageData();
		pdLabel2.put("label_name", "特惠");
		pdLabel2.put("internet_id", internet_id);
		pdLabel2 = (PageData) dao.findForObject("LabelMapper.findByName", pdLabel2);
		if(StringUtil.isEmpty(pdLabel2)){
			pdLabel2 = new PageData();
			pdLabel2.put("id", StringUtil.get32UUID());
			pdLabel2.put("label_name", "特惠");
			pdLabel2.put("label_color", "color11");
			pdLabel2.put("status", "0");
			pdLabel2.put("internet_id", internet_id);
			dao.save("LabelMapper.save", pdLabel2);
		}
	}
	
	
	/**
	 * 删除标签
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteLabel(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		User user = (User) pd.get("user");
		if(StringUtil.isEmpty(user)){
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}
		
		String[] nameList = pd.getString("nameList").split(",");
		
		for(String str : nameList){
			dao.delete("LabelMapper.deleteByName", str);
		}
		
		result.put("result", "true");
		result.put("message", "删除成功");
		return result;
	}
	
	
	/**
	 * 保存规则
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveRule(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		//判断条件
		result = check(pd);
		if(result.getString("result").equals("false")){
			return result;
		}
		
		User user = (User) pd.get("user");
		int max_module = Integer.parseInt(pd.getString("max_module"));
		String[] storeList = pd.getString("storeList").split(",");
		
		for(String str : storeList){
			PageData pdRule = new PageData();
			pdRule.put("internet_id", user.getINTENET_ID());
			pdRule.put("store_id", str);
			pdRule.put("status", "0");//未删除
			
			//判断是否是新增还是修改操作
			PageData isAddOrUpdate = new PageData();
			isAddOrUpdate.put("store_id", str);
			List<PageData> ruleList = findByStoreId(isAddOrUpdate);
			if(ruleList.size() > 0){
				//修改操作
				int sequence = 0;
				int number = 0;
				for(int i=0;i<=max_module;i++){
					String module_name = "moduleName"+i;
					if(StringUtil.isEmpty(pd.getString(module_name))){
						continue;
					}
					String[] module = pd.getString(module_name).split(",");
					//封装数据
					if(!module[2].equals("kong")){
						pdRule.put("label_name", module[2]);
						pdRule.put("label_color", module[3]);
						
						//判断该标签是否保存，没有就新增
						PageData pdLabel = new PageData();
						pdLabel.put("label_name", module[2]);
						pdLabel.put("internet_id", user.getINTENET_ID());
						pdLabel = (PageData) dao.findForObject("LabelMapper.findByName", pdLabel);
						if(StringUtil.isEmpty(pdLabel)){
							pdLabel = new PageData();
							pdLabel.put("id", StringUtil.get32UUID());
							pdLabel.put("label_name", module[2]);
							pdLabel.put("label_color", module[3]);
							pdLabel.put("status", "0");
							pdLabel.put("internet_id", user.getINTENET_ID());
							dao.save("LabelMapper.save", pdLabel);
						}
						pdRule.put("label_id", pdLabel.get("id"));
					}else{
						pdRule.put("label_name", "kong");
						pdRule.put("label_color", "kong");
						pdRule.put("label_id", "kong");
					}
					pdRule.put("recharge_money", module[0]);
					pdRule.put("reward_money", module[1]);
					pdRule.put("createtime", Tools.date2Str(new Date()));
					pdRule.put("sequence", sequence);
					
					
					number += 1;
					
					//判断该序号的未删除的规则是否有保存过
					PageData pdIsAdd = (PageData) dao.findForObject("RechargeRuleMapper.findByStoreIdAndSequence", pdRule);
					if(StringUtil.isEmpty(pdIsAdd)){
						pdRule.put("id", StringUtil.get32UUID());
						pdRule.put("status", "0");//未删除
						dao.save("RechargeRuleMapper.save", pdRule);
					}
					
					//判断该规则有没有被订单使用
					//没有，直接修改该规则，有，原规则状态改为已删除，再新增一条规则
					PageData pddRule = new PageData();
					pddRule.put("store_id", str);
					pddRule.put("sequence", sequence);
					pddRule = (PageData) dao.findForObject("RechargeRuleMapper.checkIsUsed", pddRule);
					if(pddRule.get("number1").toString().equals("0") && pddRule.get("number2").toString().equals("0")){
						dao.update("RechargeRuleMapper.editByStoreIdAndSequence", pdRule);
					}else{
						pdRule.put("status", "1");//已删除
						dao.update("RechargeRuleMapper.editByStoreIdAndSequence", pdRule);
						
						pdRule.put("id", StringUtil.get32UUID());
						pdRule.put("status", "0");//未删除
						dao.save("RechargeRuleMapper.save", pdRule);
					}
					
					//判断数据库中，规则数量 > 提交的数量，是，将数据库中多余的数据删除
					if(i == max_module){
						PageData pdExtra = new PageData();
						pdExtra.put("store_id", str);
						for(int j=number;j<9;j++){
							pdExtra.put("item", j);
							
							//判断该规则有没有被订单使用
							//没有，直接删除该规则；有，状态改为已删除，
							PageData pddRule2 = new PageData();
							pddRule2.put("store_id", str);
							pddRule2.put("sequence", j);
							pddRule2 = (PageData) dao.findForObject("RechargeRuleMapper.checkIsUsed", pddRule2);
							if(pddRule2.get("number1").toString().equals("0") && pddRule2.get("number2").toString().equals("0")){
								dao.delete("RechargeRuleMapper.deleteExtra", pdExtra);
							}else{
								pdExtra.put("status", "1");//已删除
								pdExtra.put("sequence", j);
								dao.update("RechargeRuleMapper.editByStoreIdAndSequence", pdExtra);
							}
						}
					}
					//序号自增1
					sequence += 1;
				}
			}else{
				//新增操作
				int sequence = 0;
				for(int i=0;i<=max_module;i++){
					String module_name = "moduleName"+i;
					if(StringUtil.isEmpty(pd.getString(module_name))){
						continue;
					}
					String[] module = pd.getString(module_name).split(",");
					//封装数据
					if(!module[2].equals("kong")){
						pdRule.put("label_name", module[2]);
						pdRule.put("label_color", module[3]);
						
						//判断该标签是否保存，没有就新增
						PageData pdLabel = new PageData();
						pdLabel.put("label_name", module[2]);
						pdLabel.put("internet_id", user.getINTENET_ID());
						pdLabel = (PageData) dao.findForObject("LabelMapper.findByName", pdLabel);
						if(StringUtil.isEmpty(pdLabel)){
							pdLabel = new PageData();
							pdLabel.put("id", StringUtil.get32UUID());
							pdLabel.put("label_name", module[2]);
							pdLabel.put("label_color", module[3]);
							pdLabel.put("status", "0");
							pdLabel.put("internet_id", user.getINTENET_ID());
							dao.save("LabelMapper.save", pdLabel);
						}
						pdRule.put("label_id", pdLabel.get("id"));
					}else{
						pdRule.put("label_name", "kong");
						pdRule.put("label_color", "kong");
						pdRule.put("label_id", "kong");
					}
					pdRule.put("recharge_money", module[0]);
					pdRule.put("reward_money", module[1]);
					pdRule.put("createtime", Tools.date2Str(new Date()));
					pdRule.put("sequence", sequence);
					
					pdRule.put("id", StringUtil.get32UUID());
					if(pdRule.get("label_id").equals("kong")){
						pdRule.put("label_id", "");
					}
					if(pdRule.get("label_name").equals("kong")){
						pdRule.put("label_name", "");
					}
					if(pdRule.get("label_color").equals("kong")){
						pdRule.put("label_color", "");
					}
					
					dao.save("RechargeRuleMapper.save", pdRule);
					//序号自增1
					sequence += 1;
				}
			}
			
		}
		
		
		result.put("result", "true");
		result.put("message", "保存成功");
		return result;
	}
	
	
	/**
	 * 新增规则时，检查参数是否正常
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject check(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		User user = (User) pd.get("user");
		if(StringUtil.isEmpty(user)){
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}
		
		int max_module = Integer.parseInt(pd.getString("max_module"));
		String[] storeList = pd.getString("storeList").split(",");
		
		if(storeList.length == 0){
			result.put("result", "false");
			result.put("message", "尚未选择门店");
			return result;
		}
		
		if(max_module == 0 && StringUtil.isEmpty(pd.getString("moduleName0"))){
			result.put("result", "false");
			result.put("message", "请先添加模块");
			return result;
		}
		
		//判断金额是否正确
		for(int i=0;i<=max_module;i++){
			String module_name = "moduleName"+i;
			if(StringUtil.isEmpty(pd.getString(module_name))){
				continue;
			}
			String[] module = pd.getString(module_name).split(",");
			
			int recharge = Integer.parseInt(module[0]);
			int reward = Integer.parseInt(module[1]);
			if(recharge <= 0){
				result.put("result", "false");
				result.put("message", "请输入正确的充值金额");
				result.put("flag", "money");
				result.put("sequence", i);
				return result;
			}
			if(reward < 0){
				result.put("result", "false");
				result.put("message", "请输入正确的奖励金额");
				result.put("flag", "money");
				result.put("sequence", i);
				return result;
			}
		}
		
		result.put("result", "true");
		return result;
	}
	
	
	
	/**
	 * 查找门店的规则
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByStoreId(PageData pd) throws Exception{
		
		return (List<PageData>) dao.findForList("RechargeRuleMapper.findByStoreId", pd);
	}
	
	
	/**
	 * 删除门店的规则列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteRules(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		String str = pd.getString("store_id");
		
		if(StringUtil.isEmpty(str)){
			result.put("result", "false");
			result.put("message", "参数错误");
			return result;
		}
		
		PageData pdExtra = new PageData();
		pdExtra.put("store_id", str);
		for(int j=0;j<9;j++){
			pdExtra.put("item", j);
			
			//判断该规则有没有被订单使用
			//没有，直接删除该规则；有，状态改为已删除，
			PageData pddRule2 = new PageData();
			pddRule2.put("store_id", str);
			pddRule2.put("sequence", j);
			pddRule2 = (PageData) dao.findForObject("RechargeRuleMapper.checkIsUsed", pddRule2);
			if(pddRule2.get("number1").toString().equals("0") && pddRule2.get("number2").toString().equals("0")){
				dao.delete("RechargeRuleMapper.deleteExtra", pdExtra);
			}else{
				pdExtra.put("status", "1");//已删除
				pdExtra.put("sequence", j);
				dao.update("RechargeRuleMapper.editByStoreIdAndSequence", pdExtra);
			}
		}
		
		result.put("result", "true");
		result.put("message", "删除成功");
		return result;
	}
	
	
	
	
}
