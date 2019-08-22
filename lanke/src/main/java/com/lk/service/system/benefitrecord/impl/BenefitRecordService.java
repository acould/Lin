package com.lk.service.system.benefitrecord.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.benefitrecord.BenefitRecordManager;

/** 
 * 说明： 会员福利记录表
 * 创建人：洪智鹏
 * 创建时间：2017-05-08
 * @version
 */
@Service("benefitrecordService")
public class BenefitRecordService implements BenefitRecordManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd包含：会员福利记录表的信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("BenefitRecordMapper.save", pd);
	}
	
	/**删除
	 * @param pd 包含：会员福利记录表的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("BenefitRecordMapper.delete", pd);
	}
	
	/**修改
	 * @param pd 包含：会员福利记录表的信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("BenefitRecordMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BenefitRecordMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd  没有任何信息查找全部（后期根据需要传入网吧id等信息）
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BenefitRecordMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd  pd 包含：会员福利记录表的主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BenefitRecordMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS  包含：会员福利记录表的主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BenefitRecordMapper.deleteAll", ArrayDATA_IDS);
	}


	/**
	 * 根据用户查找记录列表
	 * pd（BENEFITS_ID，USER_ID，START_TIME，END_TIME）
	 */
	public List<PageData> findByUserList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("BenefitRecordMapper.findByUserList", pd);
	}

	/**
	 * 获取用户领取时间
	 * @param pd（BENEFITS_ID，USER_ID）
	 */
	public PageData findByTime(PageData record) throws Exception {
		return (PageData)dao.findForObject("BenefitRecordMapper.findByTime", record);
	}
	
}

