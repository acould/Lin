package com.lk.service.system.benefits.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.benefits.BenefitsManager;

/** 
 * 说明： 会员福利
 * 创建人：洪智鹏
 * 创建时间：2017-05-08
 * @version
 */
@Service("benefitsService")
public class BenefitsService implements BenefitsManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd新增会员福利（会员能够领取哪些福利）的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("BenefitsMapper.save", pd);
	}
	
	/**删除
	 * @param pd  删除福利数据（会员能够领取哪些福利）主键
	 * @throws Exception 
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("BenefitsMapper.delete", pd);
	}
	
	/**修改
	 * @param pd  修改福利项（会员能够领取哪些福利）的相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("BenefitsMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BenefitsMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BenefitsMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd  福利数据（会员能够领取哪些福利）主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BenefitsMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS  福利数据（会员能够领取哪些福利）主键组成的数组
	 * @throws Exception 
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BenefitsMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

