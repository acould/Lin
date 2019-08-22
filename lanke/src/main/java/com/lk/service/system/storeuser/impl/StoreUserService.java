package com.lk.service.system.storeuser.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.UuidUtil;

import net.sf.json.JSONObject;

import com.lk.service.system.storeuser.StoreUserManager;

/** 
 * 说明： 门店管理
 * 创建人：洪智鹏
 * 创建时间：2017-02-27
 * @version
 */
@Service("storeUserService")
public class StoreUserService implements StoreUserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**删除门店下的人员
	 * @param pd
	 * @throws Exception
	 */
	public void deleteStoreById(PageData pd)throws Exception{
		dao.delete("StoreUserMapper.deleteStoreById", pd);
	}
	
	
	/**
	 * 新增
	 * 将用户门店id存进关联表
	 * @param pd--门店id,用户id
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("StoreUserMapper.save", pd);
	}
	
	/**
	 * 删除
	 * 删除角色门店关联表信息
	 * @param pd--指定用户id
	 * @throws Exception
	 */
	public JSONObject delete(PageData pd)throws Exception{
		JSONObject json = new JSONObject();
		try {
			dao.delete("StoreUserMapper.delete", pd);
			json.put("result", PublicManagerUtil.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.ERR);
		}
		return json;
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("StoreUserMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StoreUserMapper.list", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoreUserMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StoreUserMapper.findById", pd);
	}
	
	/**
	 * 门店批量删除
	 * 通过用户id去删除指定用户门店关联表
	 * @param pd--ArrayDATA_IDS--用户id数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StoreUserMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**根据人员批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteUser(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StoreUserMapper.deleteUser", ArrayDATA_IDS);
	}
	
	/**
	 * 根据网吧id查询门店和用户的关系
	 * @param pd（必填：internetId）
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByIntenet(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StoreUserMapper.listByIntenet", pd);
	}
	
	/**
	 * 通过用户id获取相关门店id
	 * @param pdStore(必填:用户id--USER_ID)
	 * @return 所需门店用户相关信息
	 * @throws Exception
	 */
	public PageData findByUserId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("StoreUserMapper.findByUserId", pd);
	}
	
	
	/**
	 * 修改门店名称
	 * @param pd--保存修改后的门店信息
	 * @throws Exception
	 */
	public JSONObject editStore(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		String ids []= pd.getString("ids").split(",");
		
		try {
			dao.delete("StoreUserMapper.delete", pd.getString("USER_ID"));
			for(String id:ids){
				pd.put("STORE_ID", id);
				pd.put("STORE_USER_ID", UuidUtil.get32UUID());
				dao.save("StoreUserMapper.save", pd);
			}
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统繁忙,请稍后再试!");
		}
		return json;
	}
	
	/**
	 * 查询门店下的人员 
	 * ArrayDATA_IDS（门店id的数组）
	 */
	public List<PageData> listAllSU(String[] ArrayDATA_IDS) throws Exception {
		return (List<PageData>) dao.findForList("StoreUserMapper.listAllSU", ArrayDATA_IDS);
	}


	@Override
	public List<String> listfindstoreId(String userid) throws Exception {
		// TODO Auto-generated method stub
		return (List<String>) dao.findForList("StoreMapper.listfindstoreId", userid);
	}
		
}

