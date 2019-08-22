package com.lk.service.system.integralchange.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.integralchange.IntegralChangeManager;

/** 
 * 说明： 积分变动表
 * 创建人：洪智鹏
 * 创建时间：2016-10-26
 * @version
 */
@Service("integralchangeService")
public class IntegralChangeService implements IntegralChangeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param  pd包含会员积分变动相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("IntegralChangeMapper.save", pd);
	}
	
	/**删除
	 * @param pd包含主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("IntegralChangeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd包含会员积分变动相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("IntegralChangeMapper.edit", pd);
	}
	
	/**列表
	 * @param page 包含页面传递的检索信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("IntegralChangeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("IntegralChangeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd包含主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("IntegralChangeMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("IntegralChangeMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

