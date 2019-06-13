package star.web.root;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import star.bizbase.exception.BizRuleException;
import star.config.Config;
import star.constant.SystemConstants;
import star.facade.ecmanager.enums.SysUserEnum;
import star.facade.ecmanager.manager.ManagerFacade;
import star.facade.ecmanager.manager.vo.ManagerVo;
import star.fw.web.util.CookieHelper;
import star.modules.cache.JedisService;
import star.share.util.Encrypt;
import star.share.util.Results;
import star.util.StringUtil;
import star.vo.result.ResultVo;
import star.web.BasicController;
import star.web.component.ManagerComponent;
import star.webtool.component.LoginComponent;
import star.webtool.struct.CookieEnum;

/**
 * 
 * 
 * 
 * Title: 登录页
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午11:20:51
 * @version $Id: LoginController.java 86424 2015-03-19 09:20:26Z yes $
 */
@Controller
public class LoginController extends BasicController {
	
	private final static String ENVIRONMENT = Config.getString("sys.env.tag");
	
	/**
	 * 管理员用户接口对象
	 */
	@Autowired
	private ManagerFacade sysUserFacade;
	@Autowired
	private LoginComponent loginComponent;
	@Autowired
	private JedisService jedisService;
	@Autowired
	private ManagerComponent managerComponent;

	
	/**
	 * 登录
	 * 注意：内部系统merchantId=0.商家系统merchantId>0.
	 * @param request
	 * @param model
	 * @param response
	 * @param username
	 * @param password
	 * @param code
	 * @return 
	 * @author chistar
	 * @throws BizRuleException 
	 * @since  2018年10月27日 下午10:30:25
	 */
	@ResponseBody
	@RequestMapping(value = { "/doLogin"})
	public Model doLogin(HttpServletRequest request, Model model, HttpServletResponse response, 
			String username, String password, String code) throws BizRuleException {
		ResultVo<ManagerVo> result=Results.newResultVo();	
		
		if ("dev".equals(ENVIRONMENT) || StringUtil.isNotEmpty(code)) {				
			String sessionId = request.getSession().getId();
			String captcha = ImageCodeUtil.getImageCode(jedisService, sessionId);	
			if ("dev".equals(ENVIRONMENT) || code.toUpperCase().equals(captcha)) {					
				ImageCodeUtil.clearImageCode(jedisService, sessionId);
				ManagerVo sysUser = sysUserFacade.getManagerByUserName(username);
				sysUser.setPlatform(SystemConstants.SYSTEM_PLATFORM);//必须加入.不同系统，设置不同
				if (sysUser != null && sysUser.getMerchantId()!=null && managerComponent.checkManagerValidByPlatForm(sysUser)) {//商家权限系统商家id>0
					if (sysUser.getDel().equals(SysUserEnum.DEL_DELETED.value())) {
						result = Results.setResult(false, "用户信息已经被删除");
						model.addAttribute("ret", result);
						return model;
					}				
					String desPassword = Encrypt.desEncrypt2(password);
					String hashPassword = StringUtil.getMD5(desPassword);
					logger.info("xxh-username={},password={}",username,hashPassword);
					if (null == hashPassword || !hashPassword.equalsIgnoreCase(sysUser.getPasswd())) {
						result = Results.setResult(false, "密码不正确");
						model.addAttribute("ret", result);
						logger.info("xxh-username={},sys-password={},result={}",username,sysUser.getPasswd(),result);
						return model;
					}
					
					ManagerVo managerVo = new ManagerVo();
					result = Results.setResult(true,managerVo);
					
					model.addAttribute("ret", result);
					logger.info("xxh-username={},result={},domain={},domains={}",username,result,CookieHelper.DOMAIN,CookieHelper.DOMAINS);
					loginComponent.saveLogin(sysUser);
				} else {
					result = Results.setResult(false, "用户信息不存在");
					model.addAttribute("ret", result);
				}

			} else {
				result = Results.setResult(false, "验证码错误");
				model.addAttribute("ret", result);
			}
		}
		
		return model;
	}
	
	/**
	 * 跳登录页
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request, Model model,String cmd,String url){
		
		if ("logout".equals(cmd)) {
			CookieHelper.cancelCookie(CookieEnum.LOGIN.getValue());
			ImageCodeUtil.clearImageCode(jedisService, request.getSession().getId());
		}
		
		return "/root/login";
	}
}
