package com.lk.service.system.sysuser;

import java.util.List;
import com.lk.entity.Page;
import com.lk.entity.system.BundUser;
import com.lk.entity.system.User;
import com.lk.util.PageData;
import com.lk.wechat.response.WechatUser;

/** 
 * 说明： 用户代码接口
 * 创建人：洪智鹏
 * 创建时间：2016-10-07
 * @version
 */
public interface SysUserManager{

	/**新增
	 * @param pd 包含系统用户相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd 包含系统用户表主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd 包含系统用户相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page 包含检索字段
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd 包含系统用户表主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS 包含系统用户表主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;




	User getByUserId(String user_id) throws Exception;

    User getUserByOpenId(String openid) throws Exception;

	WechatUser getByOpenId(String opeid) throws Exception;

	BundUser getBundUserByUserId(String user_id) throws Exception;

	BundUser getBundUserByOpenId(String opeid) throws Exception;

	void clearSessionUser(boolean sysType, boolean wechatType, boolean bundType) throws Exception;

}

