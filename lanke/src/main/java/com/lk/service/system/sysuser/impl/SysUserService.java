package com.lk.service.system.sysuser.impl;

import java.util.List;
import javax.annotation.Resource;

import com.google.common.base.Joiner;
import com.lk.entity.system.BundUser;
import com.lk.entity.system.Role;
import com.lk.entity.system.SessionUser;
import com.lk.entity.system.User;
import com.lk.service.system.role.RoleManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.util.*;
import com.lk.wechat.response.WechatUser;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.sysuser.SysUserManager;

/** 
 * 创建时间：2016-10-07
 * @version
 */
@Service("sysuserService")
public class SysUserService implements SysUserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

    @Resource(name = "wechatuserService")
    private WeChatUserManager wechatuserService;
    @Resource(name = "roleService")
    private RoleManager roleService;

    @Resource(name="storeUserService")
    private StoreUserManager storeUserService;

	/**新增
	 * @param pd 包含系统用户相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysUserMapper.save", pd);
	}
	
	/**删除
	 * @param pd 包含系统用户表主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysUserMapper.delete", pd);
	}
	
	/**修改
	 * @param pd 包含系统用户相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysUserMapper.edit", pd);
	}
	
	/**列表
	 * @param page 包含检索字段
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysUserMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysUserMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd 包含系统用户表主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysUserMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS 包含系统用户表主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysUserMapper.deleteAll", ArrayDATA_IDS);
	}



    /********************************* 获取用户信息 *********************************************/
    /**
     * 根据userId获取用户，并放入缓存
     * @return
     * @throws Exception
     */
    @Override
    public User getByUserId(String user_id) throws Exception {
        User user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();
		if(user != null && user.getUSER_ID().equals(user_id)){
			return user;
		}

        user = (User) dao.findForObject("SysUserMapper.getByUserId", user_id);

        //获取系统用户
        if(user != null && StringUtil.isNotEmpty(user.getUSERNAME())){
            Role role = roleService.getRoleById(user.getROLE_ID());
            user.setRole(role);
            Jurisdiction.setSessionUser(user, null,null);

            if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) &&
                    !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID) &&
                    !user.getROLE_ID().equals(PublicManagerUtil.PROMOTERROLEID)){
                //非网吧管理员，admin，微信用户，查找用户所在的门店id列表
                List<String> store_ids = storeUserService.listfindstoreId(user.getUSER_ID());
                if(store_ids.size() > 0){
                    System.out.println("下属门店");
                    user.setStore_ids(store_ids);
                }
            }
		}

        return user;
    }


    /**
     * 根据openid 获取user， 并放入缓存
     * @param openid
     * @return
     * @throws Exception
     */
    public User getUserByOpenId(String openid) throws Exception {
        User user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();
		if(user != null && user.getOPEN_ID().equals(openid)){
    		return user;
		}

        user = (User) dao.findForObject("UserMapper.getUserByOpenId", openid);

        //获取系统用户
        if(user != null && StringUtil.isNotEmpty(user.getUSERNAME())){
            Role role = roleService.getRoleById(user.getROLE_ID());
            user.setRole(role);
            Jurisdiction.setSessionUser(user, null,null);

            if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) &&
                    !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID) &&
                    !user.getROLE_ID().equals(PublicManagerUtil.PROMOTERROLEID)){
                //非网吧管理员，admin，微信用户，查找用户所在的门店id列表
                List<String> store_ids = storeUserService.listfindstoreId(user.getUSER_ID());
                if(store_ids.size() > 0){
                    user.setStore_ids(store_ids);
                }
            }
        }
        return user;
    }



    /**
     * 根据openid获取WechatUser，并放入缓存
     * @param opeid
     * @return
     * @throws Exception
     */
    @Override
    public WechatUser getByOpenId(String opeid) throws Exception{
        WechatUser wechatUser = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getWechatUser();
		if (wechatUser != null && wechatUser.getOPEN_ID().equals(opeid))
			return wechatUser;


		//获取微信用户
        wechatUser = wechatuserService.findByOpenId(opeid);
        if(wechatUser != null && StringUtil.isNotEmpty(wechatUser.getOPEN_ID())){
            Jurisdiction.setSessionUser(null, wechatUser, null);
        }

        return wechatUser;
    }


    /**
     * 根据user_id获取绑定用户信息，并放入缓存
     * @param user_id
     * @return
     * @throws Exception
     */
    @Override
    public BundUser getBundUserByUserId(String user_id) throws Exception{
        BundUser bundUser = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getBundUser();
        if (bundUser != null && bundUser.getUser_id().equals(user_id))
            return bundUser;

        bundUser = (BundUser) dao.findForObject("BundUserMapper.getBundUserByUserId", user_id);
        if(bundUser != null && StringUtil.isNotEmpty(bundUser.getCarded())){
            Jurisdiction.setSessionUser(null, null, bundUser);
        }

        return bundUser;
    }

    /**
     * 根据opeid获取绑定用户信息，并放入缓存
     * @param opeid
     * @return
     * @throws Exception
     */
    @Override
    public BundUser getBundUserByOpenId(String opeid) throws Exception{
        BundUser bundUser = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getBundUser();
        if (bundUser != null && bundUser.getOpen_id().equals(opeid))
            return bundUser;

        bundUser = (BundUser) dao.findForObject("BundUserMapper.getBundUserByOpenId", opeid);
        if(bundUser != null && StringUtil.isNotEmpty(bundUser.getCarded())){
            Jurisdiction.setSessionUser(null, null, bundUser);
        }

        return bundUser;
    }


    /**
     * 清理sessionUser缓存
     * @param sysType
     * @param wechatType
     * @param bundType
     */
    @Override
    public void clearSessionUser(boolean sysType, boolean wechatType, boolean bundType) {
        SessionUser sessionUser = Jurisdiction.getSessionUser();
        if(sysType){
            sessionUser.setUser(null);
        }
        if(wechatType){
            sessionUser.setWechatUser(null);
        }
        if(bundType){
            sessionUser.setBundUser(null);
        }
        Jurisdiction.getSession().removeAttribute(Const.SESSION_USER);
        Jurisdiction.getSession().setAttribute(Const.SESSION_USER, sessionUser);
    }


}

