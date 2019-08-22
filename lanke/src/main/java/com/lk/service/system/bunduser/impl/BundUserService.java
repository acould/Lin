package com.lk.service.system.bunduser.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.bunduser.BundUserManager;

/** 
 * 说明： 绑定信息表
 * 创建人：洪智鹏
 * 创建时间：2016-10-26
 * @version
 */
@Service("bunduserService")
public class BundUserService implements BundUserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd 包含绑定会员信息保存接口信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("BundUserMapper.save", pd);
	}
	
	/**删除
	 * @param pd 包含绑定会员信息表的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("BundUserMapper.delete", pd);
	}
	
	/**
	 * 修改
	 * 充值成功，修改用户余额
	 * @param pd  包含需要修改会员绑定信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("BundUserMapper.edit", pd);
	}
	/**修改
	 * @param pd  包含需要修改会员绑定信息
	 * @throws Exception
	 */
	public void editUser(PageData pd)throws Exception{
		dao.update("BundUserMapper.editUser", pd);
	}
	
	/**列表
	 * @param page 查看会员绑定信息包含检索字段
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BundUserMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BundUserMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd  根据主键查询会员绑定表信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BundUserMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS  由主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BundUserMapper.deleteAll", ArrayDATA_IDS);
	}

	/**通过id获取数据
	 * 获取绑定的微信用户信息
	 * @param pd 用户userId
	 * @throws Exception
	 */
	public PageData findByUser(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BundUserMapper.findByUser", pd);
	}

	/**
	 * 根据卡卷号来判断是否为绑定用户获取的(通过优惠券code来判断用户是否绑定获取数据)
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception {
		return (PageData)dao.findForObject("BundUserMapper.findByCode", pd);
	}

	/**
	 * 根据卡号和门店id获取绑定信息
	 * @param pd（必填：CARDED，STORE_ID）
	 */
	public PageData findByCard(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BundUserMapper.findByCard", pd);
	}

	/**
	 * 根据用户id和门店id获取绑定信息
	 * @param pd（必填：userId，STORE_ID）
	 */
	public PageData findByUserIdAndStoreId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BundUserMapper.findByUserIdAndStoreId", pd);
	}

	/**
	 * 根据open_id和门店id获取绑定信息
	 * @param pd（必填：OPEN_ID，STORE_ID）
	 */
	public PageData findByOpenIdAndStoreId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BundUserMapper.findByOpenIdAndStoreId", pd);
	}

	public PageData findByOpenId(String openid)throws Exception{
		return (PageData)dao.findForObject("BundUserMapper.findByOpenId", openid);
	}

	@Override
	public PageData findUserByOpenId(String openid) throws Exception{
		return (PageData)dao.findForObject("BundUserMapper.findUserByOpenId", openid);
	}
	@Override
	public PageData findUserByUserId(String user_id) throws Exception{
		return (PageData)dao.findForObject("BundUserMapper.findUserByUserId", user_id);
	}

    @Override
    public PageData findByCardedAndStoreId(PageData pdBund) throws Exception{
        return (PageData)dao.findForObject("BundUserMapper.findByCardedAndStoreId", pdBund);
    }

    @Override
    public int delByUserId(String user_id) throws Exception{
        return (int) dao.delete("BundUserMapper.delByUserId", user_id);
    }

    @Override
    public List<PageData> listByStoreId(String store_id) throws Exception{
        return (List<PageData>) dao.findForList("BundUserMapper.listByStoreId", store_id);
    }
    @Override
    public List<PageData> listByStoreIds(PageData pd) throws Exception{
        return (List<PageData>) dao.findForList("BundUserMapper.listByStoreIds", pd);
    }

}

