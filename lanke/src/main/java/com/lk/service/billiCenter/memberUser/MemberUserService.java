package com.lk.service.billiCenter.memberUser;

import java.util.List;

import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.util.PageData;


public interface MemberUserService{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
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
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	
	/**
	 * 添加会员
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject addMember(String store_id, JSONObject param)throws Exception;
	
	/**
	 * card_id和store_id查
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCardIdAndStoreId(PageData pd)throws Exception;
	
}

