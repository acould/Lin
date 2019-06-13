package star.web.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import star.bizbase.exception.BizRuleException;
import star.constant.SystemConstants;
import star.facade.ecbmerchant.EcbMerchantInfoFacade;
import star.facade.ecmanager.enums.YesOrNo;
import star.facade.ecmanager.manager.ManagerFacade;
import star.facade.ecmanager.manager.vo.ManagerVo;
import star.facade.ecmanager.manager.vo.SysUserInfoVo;
import star.facade.ecmanager.merchant.MerchantPlatformFacade;
import star.facade.ecmanager.purview.PurviewFacade;
import star.facade.ecmanager.purview.vo.SysRoleVo;
import star.fw.web.util.SearchUtil;
import star.fw.web.vo.SearchDataVo;
import star.fw.web.xss.XSSUtil;
import star.share.util.Encrypt;
import star.util.StringUtil;
import star.vo.result.ResultVo;
import star.vo.search.SearchQuery;
import star.vo.search.SearchResult;
import star.web.BasicController;
import star.webtool.component.LoginComponent;

@Controller
@RequestMapping("/sys/user")
public class SysUserController extends BasicController {

	@Autowired
	private ManagerFacade sysUserFacade;

	@Resource
	private PurviewFacade purviewFacade;
	
	@Autowired
	private LoginComponent loginComponent;
	@Autowired
	private EcbMerchantInfoFacade ecbMerchantInfoFacade;
	@Autowired
	private MerchantPlatformFacade merchantPlatformFacade;

	@RequestMapping("/doAdd")
	@ResponseBody
	public ModelMap doAdd(HttpServletRequest reqeust, ManagerVo sysUser, String roleIds) {
		ModelMap model = new ModelMap();
		ResultVo<Long> ret = new ResultVo<Long>();
		sysUser = XSSUtil.cleanXSS(sysUser);
		if (sysUser == null) {
			ret.setResultDes("sysUserVo is null");
			ret.setSuccess(false);
			model.addAttribute("ret", ret);
			return model;
		}
		
		if (StringUtil.isEmpty(sysUser.getPasswd())) {
			ret.setResultDes("密码不能空");
			ret.setSuccess(false);
			model.addAttribute("ret", ret);
			return model;
		}
		if (StringUtil.isEmpty(sysUser.getPasswd())) {
			ret.setResultDes("密码不能空");
			ret.setSuccess(false);
			model.addAttribute("ret", ret);
			return model;
		}
		String password = Encrypt.desEncrypt2(sysUser.getPasswd());
		if (password.length() < 6) {
			ret.setResultDes("密码长度必须大于6");
			ret.setSuccess(false);
			model.addAttribute("ret", ret);
			return model;
		}
		if (StringUtil.isEmpty(roleIds)) {
			ret.setResultDes("角色不能为空");
			ret.setSuccess(false);
			model.addAttribute("ret", ret);
			return model;
		}
		if (sysUserFacade.getManagerByUserName(sysUser.getName()) != null) {
			ret.setResultDes("该用户已存在");
			ret.setSuccess(false);
			model.addAttribute("ret", ret);
			return model;
		}
		String str[]=roleIds.split(",");
		List<Long> list=new ArrayList<Long>();
		for(int i=0;i<str.length;i++){
			list.add(Long.parseLong(str[i]));
		}
		ManagerVo muser = this.getSysUser();
		sysUser.setPasswd(password);
		sysUser.setMerchantId(muser.getMerchantId());
		sysUser.setPlatform(SystemConstants.SYSTEM_PLATFORM);
		logger.info("insertManager sysUser={},muser={}",sysUser,muser);
		ret = sysUserFacade.insertManager(sysUser, list);
		model.addAttribute("ret", ret);
		return model;
	}

	@RequestMapping("/add")
	public String add(Model model, HttpServletRequest reqeust) throws BizRuleException {
		ManagerVo sysUser = this.getSysUser();
		String platform = merchantPlatformFacade.getCacheMerchantPlatformStrByPMid(sysUser.getMerchantId(),SystemConstants.SYSTEM_PLATFORM);
		List<String> platformList = new ArrayList<>();	
		platformList.add(platform);
		List<SysRoleVo> sysRoles =purviewFacade.getRoleListByPlatformList(platformList);
		logger.info("useradd. mid={},uid={},platformList={},sysRoles={}",sysUser.getMerchantId(),sysUser.getId(),platformList,sysRoles);
		model.addAttribute("sysRoles", sysRoles);
		model.addAttribute("mNormalSimpleList", ecbMerchantInfoFacade.getCacheAllListByNormalLimit());//简化版，商家基本信息列表 TODO 待改。。
		
		return "/platform/user/add";
	}

	@RequestMapping("/edit")
	public String edit(Model model, Long id) throws BizRuleException {
		ManagerVo sysUser = sysUserFacade.getManagerAndRoleNameById(id);
		String platform = merchantPlatformFacade.getCacheMerchantPlatformStrByPMid(sysUser.getMerchantId(),SystemConstants.SYSTEM_PLATFORM);
		List<String> platformList = new ArrayList<>();	
		platformList.add(platform);
		List<SysRoleVo> sysRoles =purviewFacade.getRoleListByPlatformList(platformList);
		sysUser.setPlatform(SystemConstants.SYSTEM_PLATFORM);//设置本用户登录的系统。。
		this.convert2CheckRoleVo(sysRoles,sysUser);
		
		model.addAttribute("sysUserVo", sysUser);
		model.addAttribute("sysRoles", sysRoles);
		model.addAttribute("mNormalSimpleList", ecbMerchantInfoFacade.getCacheAllListByNormalLimit());//简化版，商家基本信息列表 TODO 待改。。
		
		return "/platform/user/add";
	}



	/**
	 * 账户信息，展示当前登录用户信息
	 * 
	 * @param model
	 * @param reqeust
	 * @return
	 */
	@RequestMapping("/info")
	public String info(Model model, HttpServletRequest reqeust) {
		Long sysUserId = loginComponent.getLoginUserId();
		ManagerVo sysUser = sysUserFacade.getManagerAndRoleNameById(sysUserId);

		model.addAttribute("sysUserVo", sysUser);
		return "/platform/user/info";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Model delete(Model model, HttpServletRequest reqeust, Long sysUserId) {
		ResultVo<Integer> ret = new ResultVo<Integer>();
		ManagerVo sysUser = this.getSysUser();
		ret = sysUserFacade.deleteManagerById(sysUserId,sysUser.getMerchantId());
		model.addAttribute("ret", ret);
		return model;
	}

	@RequestMapping("/list.html")
	public String accounts(Model model, HttpServletRequest reqeust,String name,String realname,String rolename) {
        SearchDataVo vo = SearchUtil.getVo();
        SearchQuery query = new SearchQuery();
        query.setStart(vo.getStart());
        query.setSize(vo.getSize());
        if (StringUtil.isNotEmpty(name)) {
			query.putQuery("name", name);
			vo.putQueryParam("name", name);
		}
        if (StringUtil.isNotEmpty(realname)) {
			query.putQuery("realname", realname);
			vo.putQueryParam("realname", realname);
		}
        if (StringUtil.isNotEmpty(rolename)) {
			query.putQuery("rolename", rolename);
			vo.putQueryParam("rolename", rolename);
		}
        //只能获取本企业内部员工信息
        ManagerVo sysUser = this.getSysUser();
        query.putQuery("merchantId", sysUser.getMerchantId());
        
        SearchResult<SysUserInfoVo> result = sysUserFacade.getSysuserInfo(query);
        vo.putSearchResult(result);
        SearchUtil.putToModel(model, vo);
		return "/platform/user/list";
	}

	@RequestMapping("/update")
	@ResponseBody
	public ModelMap update(HttpServletRequest reqeust, ManagerVo sysUser, String roleIds) {
		sysUser = XSSUtil.cleanXSS(sysUser);
		ModelMap model = new ModelMap();
		
		ResultVo<Integer> ret = new ResultVo<Integer>();
		if (StringUtil.isEmpty(roleIds)) {
			ret.setResultDes("角色不能为空");
			ret.setSuccess(false);
			model.addAttribute("ret", ret);
			return model;
		}
		ResultVo<Integer> cret =checkManagerMerchantId(sysUser.getMerchantId());
		if(cret!=null && !cret.isSuccess()) {
			model.addAttribute("ret", cret);
			return model;
		}
		String str[]=roleIds.split(",");
		List<Long> list=new ArrayList<Long>();
		for(int i=0;i<str.length;i++){
			list.add(Long.parseLong(str[i]));
		}
		ret = sysUserFacade.updateManagerByInfoRoleId(sysUser, list);
		
		model.addAttribute("ret", ret);
		return model;
	}


	/**
	 * 修改密码
	 */
	@RequestMapping("/updatePasswd")
	@ResponseBody
	public Model updatePasswd(Model model, Long sysUserId, String oldPasswd, String newPasswd) {
		ResultVo<Integer> ret = new ResultVo<Integer>();
		String oldPassword = Encrypt.desEncrypt2(oldPasswd);
		String newPassword = Encrypt.desEncrypt2(newPasswd);
		ret  = sysUserFacade.updatePassword(sysUserId, oldPassword, newPassword);
		model.addAttribute("ret", ret);
		return model;
	}

	@RequestMapping("/initPasswd")
	@ResponseBody
	public Model updatePasswd(Model model, Long sysUserId) {
		ResultVo<String> ret = new ResultVo<String>();
		ret = sysUserFacade.updateInitPassword(sysUserId);
		model.addAttribute("ret", ret);
		return model;
	}
	
	private void convert2CheckRoleVo(List<SysRoleVo> roleVos, ManagerVo sysUser) {
		List<SysRoleVo> checkRoles=purviewFacade.getRoleBy(sysUser);
		for(SysRoleVo roleVo:roleVos){
			if(isContainRoleVo(checkRoles,roleVo))
				roleVo.setCheck(YesOrNo.YES.getVal());
			else
				roleVo.setCheck(YesOrNo.NO.getVal());
			
		}
		
	}
	private boolean isContainRoleVo(List<SysRoleVo> roleVos,SysRoleVo roleVo){
		for(SysRoleVo r:roleVos){
			if(r.getId()==roleVo.getId())
				return true;
		}
		return false;
	}
}
