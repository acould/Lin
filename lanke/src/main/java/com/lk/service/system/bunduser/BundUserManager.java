package com.lk.service.system.bunduser;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 绑定信息表接口
 * 创建人：洪智鹏
 * 创建时间：2016-10-26
 * @version
 */
public interface BundUserManager{

	/**新增
	 * @param pd 包含绑定会员信息保存接口信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd 包含绑定会员信息表的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd  包含需要修改会员绑定信息
	 * @throws Exception
	 */
	public void editUser(PageData pd)throws Exception;
	
	/**
	 * 修改
	 * 充值成功，修改用户余额
	 * @param pd  包含需要修改会员绑定信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page 查看会员绑定信息包含检索字段
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd 根据主键查询会员绑定表信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS  由主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**通过id获取数据
	 * 获取绑定的微信用户信息
	 * @param pd 用户userId
	 * @throws Exception
	 */
	public PageData findByUser(PageData pd)throws Exception;
	
	/**
	 * 根据卡卷号来判断是否为绑定用户获取的(通过优惠券code来判断用户是否绑定获取数据)
	 * @throws Exception
	 */
	public PageData findByCode(PageData pdUser)throws Exception;

	/**
	 * 根据卡号和门店id获取绑定信息
	 * @param pd（必填：CARDED，STORE_ID）
	 */
	public PageData findByCard(PageData pd)throws Exception;

	/**
	 * 根据用户id和门店id获取绑定信息
	 * @param pd（必填：userId，STORE_ID）
	 */
	public PageData findByUserIdAndStoreId(PageData pdBingd2) throws Exception;

	/**
	 * 根据open_id和门店id获取绑定信息
	 * @param pd（必填：OPEN_ID，STORE_ID）
	 */
	public PageData findByOpenIdAndStoreId(PageData pdBund)throws Exception;

	/**
	 * 根据open_id获取绑定的数据和门店的加v状态
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public PageData findByOpenId(String openid)throws Exception;

	/**
	 * 根据open_id获取微信用户信息和绑定信息
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public PageData findUserByOpenId(String openid)throws Exception;
	public PageData findUserByUserId(String user_id)throws Exception;

    PageData findByCardedAndStoreId(PageData pdBund)throws Exception;

    int delByUserId(String user_id)throws Exception;

    List<PageData> listByStoreId(String store_id) throws Exception;

    List<PageData> listByStoreIds(PageData pdParam)  throws Exception;
}

