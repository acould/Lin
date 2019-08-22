package com.lk.service.system.wechatuser.impl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.Resource;

import com.google.common.base.Joiner;
import com.lk.entity.LayMessage;
import com.lk.entity.system.User;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.util.*;
import com.lk.wechat.response.WechatUser;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.wechatuser.WeChatUserManager;

/** 
 * 说明： 微信用户
 * 创建人：洪智鹏
 * 创建时间：2016-10-31
 * @version
 */
@Service("wechatuserService")
@SuppressWarnings("unchecked")
public class WeChatUserService implements WeChatUserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

    @Resource(name = "storeUserService")
    private StoreUserManager storeUserService;
    @Resource(name = "userFlowService")
    private UserFlowService userFlowService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("WeChatUserMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("WeChatUserMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WeChatUserMapper.edit", pd);
	}
	
	/**列表
	 * 通过网吧id和门店id去查询会员信息
	 * @param page 网吧id--intenetId,门店id--storeId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WeChatUserMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WeChatUserMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WeChatUserMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("WeChatUserMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 根据open_id查询微信用户信息
	 * @param pd（必填OPEN_ID）
	 */
	public PageData findByOpenId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("WeChatUserMapper.findByOpenId", pd);
	}

    /**
     * 根据open_id查询微信用户信息
     * @param openid
     */
    public WechatUser findByOpenId(String openid) throws Exception {
        return (WechatUser)dao.findForObject("WeChatUserMapper.findWechatUserByOpenId", openid);
    }

	
	/**
	 * 通过用户id获取微信用户的open_id
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUserId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("WeChatUserMapper.findByUserId", pd);
	}

	/**
	 * 通过新用户openid查找推荐用户获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByOldwithId(PageData pd) throws Exception {
		 return (PageData)dao.findForObject("WeChatUserMapper.findByOldwithId", pd);
	}
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getAppUserCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WeChatUserMapper.getAppUserCount", pd);
	}

	
	/**
	 * （当pd中的area=province时，统计获取所有微信用户的省份列表；area=city时，统计获取所有微信用户的城市列表）
	 * @param pd（必填：area，INTERNET_ID）
	 */
	public List<PageData> findArea(PageData pd) throws Exception {
		String area = pd.getString("area");
		if(area.equals("province")){
			return (List<PageData>)dao.findForList("WeChatUserMapper.findAreaByProvince", pd);
		}else if(area.equals("city")){
			return (List<PageData>)dao.findForList("WeChatUserMapper.findAreaByCity", pd);
		}
		return null;
	}



	/**
	 * 发送图文时，根据群发设置查找范围内的微信用户列表
	 * @param pd（必填：INTERNET_ID，选填：GROUP_ID是否会员设置，GROUP_SEX性别设置，GROUP_PROVINCE所在省份设置）
	 */
	public List<PageData> findBySend(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("WeChatUserMapper.findBySend", pd);
	}
	
	public List<PageData> findByCondition(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("WeChatUserMapper.findByCondition", pd);
	}

    /**
     * 根据store_id数组，获取已关注的会员列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> findByStoreIds(PageData pd) throws Exception{
        return (List<PageData>)dao.findForList("WeChatUserMapper.findByStoreIds", pd);
    }

    @Override
	public LayMessage getWechatUserList(PageData pd, Page page) throws Exception {
        User user = Jurisdiction.getSessionUser().getUser();//得到用户


        pd.put("intenetId", user.getINTENET_ID());
        pd.put("internet_id", user.getINTENET_ID());
        if (user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)
                || user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)) {
            page.setPd(pd);
        } else {
            List<String> storeid = storeUserService.listfindstoreId(user.getUSER_ID());
            pd.put("storeId", storeid.get(0));
            page.setPd(pd);
        }
        if(StringUtil.isNotEmpty(pd.getString("keywords"))){
            pd.put("encrypt", URLEncoder.encode(pd.getString("keywords"), "utf-8"));
        }
        page.setPd(pd);
        List<PageData> list = this.list(page); // 列出WeChatUser列表

        for (int i = 0; i < list.size(); i++) {
            PageData pdWechatUser = list.get(i);

            //昵称解密
            if (pdWechatUser.containsKey("NECK_NAME")) {
                pdWechatUser.put("NECK_NAME", URLDecoder.decode(pdWechatUser.getString("NECK_NAME"), "utf-8"));
            }

            //活跃度、卡余额、消费能力
            if (StringUtil.isNotEmpty(pdWechatUser.get("CARDED"))) {
                pdWechatUser = calMember(pdWechatUser);
            }
        }

        // 消费能力：consume ，卡余额：balance ， 活跃度：live （hign,mid,low）
        String[] member_label = PublicManagerUtil.member_label;
        String[] label = PublicManagerUtil.label;
        String[] label_info = PublicManagerUtil.label_info;

        int totalResult = page.getTotalResult();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < member_label.length; j++) {
                String member_label_info = pd.getString(member_label[j]);// 搜索条件：high，mid，low
                String var_member_label_info = list.get(i).getString(member_label[j]);// list中存在的值：高，中，低

                if (StringUtil.isNotEmpty(member_label_info)) {
                    // list中不存在标签，直接去除
                    if (StringUtil.isEmpty(var_member_label_info)) {
                        list.remove(i);
                        i--;
                        totalResult--;
                        break;
                    }

                    // 找出搜索条件对应的中文信息
                    String kk = "";
                    for (int k = 0; k < label.length; k++) {
                        if (member_label_info.equals(label[k])) {
                            kk = label_info[k];
                        }
                    }
                    // 不匹配，从list中去除
                    if (!var_member_label_info.equals(kk)) {
                        list.remove(i);
                        i--;
                        totalResult--;
                        break;
                    }
                }
            }
        }
        return LayMessage.ok(totalResult, list, page.getShowCount());
	}

    /**
     * 计算会员情况
     * @param pdWechatUser
     * @return
     * @throws Exception
     */
	private PageData calMember(PageData pdWechatUser) throws Exception{
        PageData pageData = pdWechatUser;
        PageData cardIdResult = userFlowService.findByCardId(pdWechatUser);// 获取活跃度/消费能力
        // 根据卡号 获得前6个月消费次数 判断活跃度
        double count = Double.parseDouble(cardIdResult.get("count")+"");// 活跃度
        if (pageData.containsKey("live_high")) {
            if (count > 0 && count <= (double) pageData.get("live_high")) {
                pdWechatUser.put("live", "高");
            } else if (count > (double) pageData.get("live_low")) {
                pdWechatUser.put("live", "低");
            } else {
                pdWechatUser.put("live", "中");
            }
        } else {
            if (count > 0 && count <= 7) {
                pdWechatUser.put("live", "高");
            } else if (count > 30) {
                pdWechatUser.put("live", "低");
            } else {
                pdWechatUser.put("live", "中");
            }
        }

        // 获得前6个月消费金额
        double sum = (double) cardIdResult.get("sum");// 消费能力
        if (pageData.containsKey("consume_high")) {
            if (sum > (double) pageData.get("consume_high")) {
                pdWechatUser.put("consume", "高");
            } else if (sum < (double) pageData.get("consume_low")) {
                pdWechatUser.put("consume", "低");
            } else {
                pdWechatUser.put("consume", "中");
            }
        } else {
            if (sum >= 50) {
                pdWechatUser.put("consume", "高");
            } else if (sum <= 20) {
                pdWechatUser.put("consume", "低");
            } else {
                pdWechatUser.put("consume", "中");
            }
        }

        // 判断余额系数
        if (pageData.containsKey("OVERAGE")) {
            double balance = Double.parseDouble(pageData.getString("OVERAGE"));// 获取会员卡余额
            if (pageData.containsKey("balance_high")) {
                if (balance >= (double) pageData.get("balance_high")) {
                    pdWechatUser.put("balance", "高");
                } else if (balance <= (double) pageData.get("balance_low")) {
                    pdWechatUser.put("balance", "低");
                } else {
                    pdWechatUser.put("balance", "中");
                }
            } else {
                if (balance >= 100) {
                    pdWechatUser.put("balance", "高");
                } else if (balance <= 20) {
                    pdWechatUser.put("balance", "低");
                } else {
                    pdWechatUser.put("balance", "中");
                }
            }
        }

        return pdWechatUser;
    }
}

