package com.lk.service.system.agent;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

import net.sf.json.JSONObject;

/**
 * 代理商管理
 * @author lzh
 *
 */
public interface AgentManager {
	/**
	 * 代理商列表
	 * @param page,pd 
	 * @throws Exception
	 */
	public List<PageData> list(Page page, PageData pd) throws Exception;

	/**
	 * 新增代理商信息
	 * @param pd 
	 * @return 
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;

	/**
	 * 判断代理商编号是否存在
	 * @param pd 
	 * @return 
	 * @throws Exception
	 */
	public List<PageData> exist(PageData pd) throws Exception;

	/**
	 * 通过id获取代理商信息
	 * @param pd 
	 * @return pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 编辑代理商信息
	 * @param pd 
	 * @return 
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;

	/**
	 * 通过代理商id去查询其代理门店信息
	 * @param page
	 * @param pd 
	 * @return 
	 * @throws Exception
	 */
	public List<PageData> show(Page page,PageData pd) throws Exception;

	/**
	 * 通过用户id去查询代理商代理的门店信息
	 * @param page
	 * @return 
	 * @throws Exception
	 */
	public List<PageData> selStore(Page page) throws Exception;

	/**
	 * 新增或修改代理商信息
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public JSONObject toEdit(PageData pd) throws Exception;

	/**
	 * 代理商编号为空时,删除代理商和门店关系表信息
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public void deleteAgentStore(PageData pd) throws Exception;

	/**
	 * 通过门店id去查看关系表信息
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public List<PageData> selAgentStore(PageData pd) throws Exception;

	/**
	 * 新增门店和代理商关系
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public void addAgentStore(PageData pd) throws Exception;

	/**
	 * 更新门店和代理商关系
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public void editAgentStore(PageData pd) throws Exception;


}
