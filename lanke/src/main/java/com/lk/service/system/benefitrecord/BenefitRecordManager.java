package com.lk.service.system.benefitrecord;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 会员福利记录表接口
 * 创建人：洪智鹏
 * 创建时间：2017-05-08
 * @version
 */
public interface BenefitRecordManager{

	/**新增
	 * @param pd 包含：会员福利记录表的信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd  包含：会员福利记录表的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd包含：会员福利记录表的信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd 包含：会员福利记录表的主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param 包含：会员福利记录表的主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 根据用户查找记录列表
	 * pd（BENEFITS_ID，USER_ID，START_TIME，END_TIME）
	 */
	public  List<PageData> findByUserList(PageData record)throws Exception;
	
	/**
	 * 获取用户领取时间
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public PageData findByTime(PageData record)throws Exception;
}

