package com.lk.service.system.agent.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.agent.AgentManager;
import com.lk.service.system.user.UserManager;
import com.lk.util.Const;
import com.lk.util.PageData;
import com.lk.util.PathUtil;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.SmsLogUtil;

import net.sf.json.JSONObject;

/**
 * 说明：代理商管理--业务层
 */
@Service("agentService")
public class AgentService implements AgentManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "userService")
	private UserManager userService;

	/**
	 * 代理商列表
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page, PageData pd) throws Exception {
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String PROVINCE = pd.getString("province");
		if (StringUtil.isNotEmpty(PROVINCE)) {
			if (PROVINCE.equals("北京") || PROVINCE.equals("天津") || PROVINCE.equals("上海") || PROVINCE.equals("台湾")
					|| PROVINCE.equals("重庆") || PROVINCE.equals("香港") || PROVINCE.equals("澳门")) {
				pd.put("province", pd.getString("province"));
				pd.put("county", pd.getString("city"));
				pd.put("city", pd.getString("province"));
			}
		}
		List<PageData> list = (List<PageData>) dao.findForList("AgentMapper.datalistPages", page);
		return list;
	}

	/**
	 * 通过代理商id去查询其代理门店信息
	 * 
	 * @param page
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> show(Page page, PageData pd) throws Exception {
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String PROVINCE = pd.getString("province");
		if (StringUtil.isNotEmpty(PROVINCE)) {
			if (PROVINCE.equals("北京") || PROVINCE.equals("天津") || PROVINCE.equals("上海") || PROVINCE.equals("台湾")
					|| PROVINCE.equals("重庆") || PROVINCE.equals("香港") || PROVINCE.equals("澳门")) {
				pd.put("province", pd.getString("province"));
				pd.put("county", pd.getString("city"));
				pd.put("city", pd.getString("province"));
			}
		}
		List<PageData> list = (List<PageData>) dao.findForList("AgentMapper.showlistPage", page);
		return list;
	}

	/**
	 * 新增代理商信息
	 * 
	 * @param pd
	 * @return json
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd) throws Exception {
		String PROVINCE = pd.getString("s_province");
		if (StringUtil.isNotEmpty(PROVINCE)) {
			if (PROVINCE.equals("北京") || PROVINCE.equals("天津") || PROVINCE.equals("上海") || PROVINCE.equals("台湾")
					|| PROVINCE.equals("重庆") || PROVINCE.equals("香港") || PROVINCE.equals("澳门")) {
				pd.put("province", pd.getString("s_province"));
				pd.put("county", pd.getString("s_city"));
				pd.put("city", pd.getString("s_province"));
			} else {
				pd.put("province", pd.getString("s_province"));
				pd.put("city", pd.getString("s_city"));
				pd.put("county", pd.getString("s_county"));
			}
		}
		dao.save("AgentMapper.save", pd);// 新增代理商信息
	}

	/**
	 * 编辑代理商信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		String PROVINCE = pd.getString("s_province");
		if (StringUtil.isNotEmpty(PROVINCE)) {
			if (PROVINCE.equals("北京") || PROVINCE.equals("天津") || PROVINCE.equals("上海") || PROVINCE.equals("台湾")
					|| PROVINCE.equals("重庆") || PROVINCE.equals("香港") || PROVINCE.equals("澳门")) {
				pd.put("province", pd.getString("s_province"));
				pd.put("county", pd.getString("s_city"));
				pd.put("city", pd.getString("s_province"));
			} else {
				pd.put("province", pd.getString("s_province"));
				pd.put("city", pd.getString("s_city"));
				pd.put("county", pd.getString("s_county"));
			}
		}
		dao.save("AgentMapper.edit", pd);// 编辑代理商信息
	}

	/**
	 * 判断代理商编号是否存在
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> exist(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("AgentMapper.exist", pd);
		return list;
	}

	/**
	 * 通过id获取代理商信息
	 * 
	 * @param pd
	 * @return pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AgentMapper.findById", pd);
	}

	/**
	 * 通过用户id去查询代理商代理的门店信息
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selStore(Page page) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("AgentMapper.selStore", page);
		return list;
	}

	/**
	 * 新增或修改代理商信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject toEdit(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		String state = pd.getString("state");
		if (state.equals("add")) {// 新增
			// 先去判断代理商编号是否存在
			List<PageData> list = this.exist(pd);
			if (list.size() != 0) {
				json.put("result", PublicManagerUtil.FAIL);
				json.put("message", "该代理商编号已存在");
				return json;
			}
			// 再去判断手机号是否存在
			pd.put("PHONE", pd.get("phone")); // 手机号
			PageData pd1 = new PageData();
			pd1 = userService.findByUserPhone(pd);
			if (StringUtil.isNotEmpty(pd1)) {
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "该手机号已存在");
				return json;
			}
			// 不存在则去保存信息
			pd.put("createtime", Tools.date2Str(new Date()));
			pd.put("updatetime", Tools.date2Str(new Date()));
			pd.put("id", StringUtil.get32UUID()); // 代理商id
			this.save(pd); // 保存代理商信息

			// 以下为代理商用户信息
			pd.put("USER_ID", StringUtil.get32UUID());
			pd.put("SKIN", "default");
			pd.put("PASSWORD", new SimpleHash("SHA-1", "123456").toString()); // 密码加密
			pd.put("USERNAME", pd.getString("agent_number")); // 用户名
			pd.put("INTEGRAL", 0); // 积分
			pd.put("STATUS", "0");
			pd.put("ANGET_ID", pd.getString("id")); // 代理商id
			pd.put("NAME", pd.get("contacts_name")); // 姓名
			pd.put("ROLE_ID", PublicManagerUtil.AGENTROLEID); // 代理商角色
			userService.saveU(pd); // 保存代理商用户信息

			// 以下为代理商图片信息
			pd.put("img_flag", "add");
			try {
				this.addImg(pd);// 新增资料图片
				json.put("result", PublicManagerUtil.SUCCESS);
				json.put("message", "操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("result", PublicManagerUtil.FAILED);
				json.put("message", "系统繁忙,请稍后再试");
			}
			// 新建账户完成后发送短信提醒(您的代理商账户以创建完成,可通过代理商编号或手机进行登录,默认密码为123456,请尽快修改密码)
			SmsLogUtil.sendMessagePass(pd.getString("phone"));// 然后发送短信(代理商账户创建完成)

		} else if (state.equals("edit")) {// 修改
			// 先去判断代理商编号是否存在
			List<PageData> list = this.exist(pd);
			if (list.size() != 0 && list.size() == 1) {// 存在且不为原代理商编号
				if (!list.get(0).get("id").equals(pd.getString("id"))) {
					json.put("result", PublicManagerUtil.FAIL);
					json.put("message", "该代理商编号已存在");
					return json;
				}
			}
			// 再去判断手机号是否存在
			pd.put("PHONE", pd.get("phone")); // 手机号
			PageData pd2 = new PageData();
			PageData pd3 = new PageData();
			pd2 = userService.findByUserPhone(pd);// 新手机号查数据
			pd3 = userService.findeByAgentId(pd);// 获取原手机号
			if (StringUtil.isNotEmpty(pd2) && !pd3.getString("PHONE").equals(pd.getString("PHONE"))) {// 手机号已存在且不为原手机号
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "该手机号已存在");
				return json;
			}

			PageData pd1 = this.findById(pd);// 通过id获取代理商修改前信息
			String agent_numberOld = pd1.getString("agent_number");
			pd.put("updatetime", Tools.date2Str(new Date()));
			this.edit(pd); // 编辑代理商信息

			// 修改代理商信息
			pd.put("agent_numberOld", agent_numberOld);// 将原代理商编号存入
			pd.put("USERNAME", pd.getString("agent_number")); // 用户名
			pd.put("NAME", pd.get("contacts_name")); // 姓名
			pd.put("PHONE", pd.get("phone")); // 手机号
			userService.editAgent(pd);// 修改代理商信息
			
			// 先删除原代理商图片,再次添加
			PageData pdImg = new PageData();
			pdImg.put("sort", "2");
			pdImg.put("intenetdatum_id", pd.getString("id"));
			dao.delete("DatumMapper.deleteAll", pdImg);
			
			// 以下为代理商图片信息
			pd.put("img_flag", "add");
			try {
				this.addImg(pd);// 新增资料图片
				json.put("result", PublicManagerUtil.SUCCESS);
				json.put("message", "操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("result", PublicManagerUtil.FAILED);
				json.put("message", "系统繁忙,请稍后再试");
			}
		}
		return json;
	}

	// 新增或修改3种类型图片
	public void addImg(PageData pd) throws Exception {
		PageData pdImg = new PageData();
		pdImg.put("intenetdatum_id", pd.getString("id"));// 业务id(代理商id)
		String[] typeList = PublicManagerUtil.typeAgentList;

		for (int i = 0; i < typeList.length; i++) {
			String type = typeList[i];
			if (StringUtil.isNotEmpty(pd.getString(type)) && pd.getString(type).startsWith("data:image")) {// 未处理的图片去处理
				pdImg.put("type", type);
				pdImg.put("flag", "add");
				pdImg.put("sort", "2");// 资料类别为2--代理商
				pdImg.put("createtime", pd.get("updatetime"));// 创建时间
				pdImg = this.putImg(pd, pdImg);
				dao.save("DatumMapper.save", pdImg);
			} else {// 处理过的直接保存
				pdImg.put("type", type);
				pdImg.put("flag", "add");
				pdImg.put("sort", "2");
				pdImg.put("createtime", pd.get("updatetime"));
				pdImg.put("id", StringUtil.get32UUID());
				pdImg.put("url", pd.getString(type));
				dao.save("DatumMapper.save", pdImg);
			}
		}
	}

	public PageData putImg(PageData pd, PageData pdImg) throws Exception {
		if (pdImg.getString("flag").equals("add")) {
			pdImg.put("id", StringUtil.get32UUID());
		}

		// 上传到本地
		String file = pd.getString(pdImg.getString("type"));
		String suffixName = "";
		if (file.contains(PublicManagerUtil.BASE64_JPEG)) {
			suffixName = PublicManagerUtil.SUFFIXNAME_JPG;
		} else if (file.contains(PublicManagerUtil.BASE64_PNG)) {
			suffixName = PublicManagerUtil.SUFFIXNAME_PNG;
		} else {
			suffixName = PublicManagerUtil.SUFFIXNAME_JPG;
		}

		String fileName = new Date().getTime() + suffixName;
		String path = fileName;
		String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;
		Tools.pic(file, filePath);
		pdImg.put("url", path);

		return pdImg;
	}

	/**
	 * 代理商编号为空时,删除代理商和门店关系表信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteAgentStore(PageData pd) throws Exception {
		dao.delete("AgentMapper.deleteAgentStore", pd);
	}

	/**
	 * 通过门店id去查看关系表信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selAgentStore(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("AgentMapper.selAgentStore", pd);
		return list;
	}

	/**
	 * 新增门店和代理商关系
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addAgentStore(PageData pd) throws Exception {
		dao.save("AgentMapper.addAgentStore", pd);

	}

	/**
	 * 更新门店和代理商关系
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public void editAgentStore(PageData pd) throws Exception {
		dao.update("AgentMapper.editAgentStore", pd);
	}

}
