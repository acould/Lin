package com.lk.service.system.wechat.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.wechat.WechatManager;
import com.lk.util.PageData;

/** 
 * 说明： 获取微信通讯口令
 * 创建人：洪智鹏
 * 创建时间：2017-03-22
 * @version
 */
@Service("wechatService")
public class WechatService implements WechatManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd包含微信通讯口令需要的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("WechatMapper.save", pd);
	}
	
	/**删除
	 * @param pd包含微信通讯口令的主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("WechatMapper.delete", pd);
	}
	
	/**修改
	 * @param pd包含微信通讯口令需要的相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WechatMapper.edit", pd);
	}
	
	/**列表
	 * @param page检索字段和页面部分信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WechatMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param  pd无，查询全部
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WechatMapper.listAll", pd);
	}
	
	
	
	/**通过id获取数据
	 * @param pd包含网吧id
	 * @throws Exception
	 */
	public PageData findByInternetId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WechatMapper.findByInternetId", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS包含微信通讯口令主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("WechatMapper.deleteAll", ArrayDATA_IDS);
	}

	
	
}

