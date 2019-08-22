package com.lk.service.system.signin.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.signin.SignInManager;

/** 
 * 说明： 签到表
 * 创建人：洪智鹏
 * 创建时间：2016-12-14
 * @version
 */
@Service("signinService")
public class SignInService implements SignInManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd会员签到相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SignInMapper.save", pd);
	}
	
	/**删除
	 * @param pd会员签到相关信息的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SignInMapper.delete", pd);
	}
	
	/**修改
	 * @param pd会员签到相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SignInMapper.edit", pd);
	}
	
	/**列表
	 * @param page签到列表包含页面和检索字段信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SignInMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param  pd无，查询全部信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SignInMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd会员签到主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SignInMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS会员签到主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SignInMapper.deleteAll", ArrayDATA_IDS);
	}

	public PageData findByUser(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SignInMapper.findByUser", pd);
	}
	
}

