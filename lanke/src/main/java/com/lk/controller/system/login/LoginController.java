package com.lk.controller.system.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lk.entity.system.SessionUser;
import com.lk.service.system.sysuser.SysUserManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.Menu;
import com.lk.entity.system.Role;
import com.lk.entity.system.User;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.agent.AgentManager;
import com.lk.service.system.buttonrights.ButtonrightsManager;
import com.lk.service.system.fhbutton.FhbuttonManager;
import com.lk.service.system.home.HomeManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.menu.MenuManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.productNews.ProductNewsManager;
import com.lk.service.system.productVersion.ProductVersionManager;
import com.lk.service.system.role.RoleManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechatuser.impl.WeChatUserService;
import com.lk.service.tb.Message.MessageManager;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.DateUtil;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.RightsHelper;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

import net.sf.json.JSONObject;

/**
 * 总入口
 *
 * @author 修改日期：2015/11/2
 *
 *         修改日期：2015/11/2
 */
@Controller
public class LoginController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

	// 虚拟的admin的网吧id--INTENET_ID
	public static final String INTENETID = "d29ef1b0c9e3401ebd2fe8dbbcdec409";
	// REPLACEALL替换指令
	public static final String REPLACEALL1 = "qq313596790fh";
	public static final String REPLACEALL2 = "QQ978336446fh";

	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "menuService")
	private MenuManager menuService;
	@Resource(name = "roleService")
	private RoleManager roleService;
	@Resource(name = "buttonrightsService")
	private ButtonrightsManager buttonrightsService;
	@Resource(name = "fhbuttonService")
	private FhbuttonManager fhbuttonService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	@Resource(name = "terraceService")
	private TerraceManager terraceService;
	@Resource(name = "agentService")
	private AgentManager agentService;
	@Resource(name = "homeService")
	private HomeManager homeService;
	@Resource(name = "productNewsService")
	private ProductNewsManager productNewsService;
	@Resource(name = "productVersionService")
	private ProductVersionManager productVersionService;

	@Resource(name = "sysuserService")
	private SysUserManager sysuserService;


	/**
	 * 访问登录页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_toLogin")
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/index/login");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 请求登录，验证用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_login", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject login() throws Exception {
        JSONObject result = this.getErrorJson();

        //传入参数
        PageData pd = this.getPageData();
        String KEYDATA[] = pd.getString("KEYDATA")
                        .replaceAll(REPLACEALL1, "")
                        .replaceAll(REPLACEALL2, "")
                        .split(",fh,");
        //返回信息
        String errInfo = "";
        String flag = "1";

        //判断参数
        if (null == KEYDATA || KEYDATA.length != 3) {
            errInfo = "error";//参数错误
            result.put("result", errInfo);
            return result;
        }

        // 判断效验码
        String code = KEYDATA[2];
        if (StringUtil.isEmpty(code)) {
            errInfo = "nullcode";//验证码为空
            result.put("result", errInfo);
            return result;
        }

        Session session = Jurisdiction.getSession();
        // 获取session中的验证码
        String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE);
//        设置测试万能验证码
        if(code.equals("zhangqiang123")) { sessionCode = code; }

        // 判断登录验证码
        if (StringUtil.isEmpty(sessionCode) || !sessionCode.equalsIgnoreCase(code)) {
            errInfo = "codeerror"; // 验证码有误
            result.put("result", errInfo);
            return result;
        }

        String USERNAME = KEYDATA[0]; // 登录过来的用户名或手机号
        String PASSWORD = KEYDATA[1]; // 登录过来的密码
        pd.put("USERNAME", USERNAME);
        pd.put("PHONE", USERNAME); // 手机号可登陆

        //是否为加密密码
        if (StringUtil.isNotEmpty(pd.getString("flag"))) {
            flag = pd.getString("flag");
        }
        String passwd = flag.equals("2") ? PASSWORD :
                new SimpleHash("SHA-1", PASSWORD).toString(); // 密码加密

        pd.put("PASSWORD", passwd);
        // 根据用户名和密码去读取用户信息
        pd = userService.getUserByNameAndPwd(pd);
        if(null == pd){
            log.info(USERNAME + ":::用户名或密码有误");
            errInfo = "usererror"; // 用户名或密码有误
            result.put("result", errInfo);
            return result;
        }

        //更新登陆时间
        pd.put("LAST_LOGIN", DateUtil.getTime());
        userService.updateLastLogin(pd);

        //将用户信息放入缓存
        User user = sysuserService.getByUserId(pd.getString("USER_ID"));
        result.put("internet_id", user.getINTENET_ID());

        // 清除登录验证码的session
        session.removeAttribute(Const.SESSION_SECURITY_CODE);

        // shiro加入身份验证
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            errInfo = "身份验证失败！";
            result.put("result", errInfo);
            return result;
        }


        pd.put("INTENET_ID", user.getINTENET_ID());
        PageData pdIneternet = intenetService.findById(pd);// 登陆判断用户状态(状态 0注册 2正式 3禁用 4流失 5订阅号)
        Integer b = Integer.parseInt(pdIneternet.get("STATE").toString());
        // 去判断用户是否被停用（lzh）
        if (b == PublicManagerUtil.SYS_INTENET_STATE1) { // 0表示注册
            errInfo = "authorize";
        }
        if (b == PublicManagerUtil.SYS_INTENET_STATE3) {// 3表示用户已停用
            errInfo = "userstop"; // 用户已被停用
            result.put("result", errInfo);
            return result;
        }
        if (b == PublicManagerUtil.SYS_INTENET_STATE5) {// 5表示订阅号
            errInfo = "subscribe"; // 订阅号不能使用本系统
            result.put("result", errInfo);
            return result;
        }
        if (b == PublicManagerUtil.SYS_INTENET_STATE4) { // 4表示流失用户
            errInfo = "authorize1";
        }

        // 判断是否授权
        PageData pdd = new PageData();
        pdd.put("INTENET_ID", user.getINTENET_ID());
        PageData pdpd = terraceService.findByInternetId(pdd);
        if ((pdpd != null && b == PublicManagerUtil.SYS_INTENET_STATE2)
                || user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)) {
            // 授权表有数据,且为正常状态(2)
            log.info(USERNAME + ":::登录系统");
            errInfo = "success"; // 验证成功
            result.put("ppd", user.getPASSWORD());
        }

        result.put("result", errInfo);
        return result;
	}

	/**
	 * 访问系统首页
	 * 
	 * @param changeMenu：切换菜单参数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/{changeMenu}")
	@SuppressWarnings("unchecked")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu) throws Exception {
		ModelAndView mv = this.getModelAndView();
		//传入参数
		PageData pd = this.getPageData();
        pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称

		try {
			Session session = Jurisdiction.getSession();
            User user = this.getUser();
            if(user == null){
                mv.addObject("pd", pd);
                mv.setViewName("system/index/login");// session失效后跳转登录页面
                return mv;
            }
            User userrole = (User) session.getAttribute(Const.SESSION_USERROL); // 读取session中的用户信息(含角色信息)
            if(userrole == null){
                userrole = userService.getUserAndRoleById(user.getUSER_ID()); // 通过用户ID读取用户信息和角色信息
                session.setAttribute(Const.SESSION_USERROL, userrole); // 存入session
            }

            String USERNAME = user.getUSERNAME();
            Role role = user.getRole(); // 获取用户角色
            String roleRights = role != null ? role.getRIGHTS() : ""; // 角色权限(菜单权限)
            session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); // 将角色权限存入session
            List<Menu> allmenuList = new ArrayList<Menu>();
            if (null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)) {
                allmenuList = menuService.listAllMenuQx("0"); // 获取所有菜单
                if (Tools.notEmpty(roleRights)) {
                    allmenuList = this.readMenu(allmenuList, roleRights); // 根据角色权限获取本权限的菜单列表
                }
                session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);// 菜单权限放入session中
            } else {
                allmenuList = (List<Menu>) session.getAttribute(USERNAME + Const.SESSION_allmenuList);
            }
            // 切换菜单处理=====start
            List<Menu> menuList = new ArrayList<Menu>();
            if (null == session.getAttribute(USERNAME + Const.SESSION_menuList) || ("yes".equals(changeMenu))) {
                List<Menu> menuList1 = new ArrayList<Menu>();
                List<Menu> menuList2 = new ArrayList<Menu>();
                // 拆分菜单
                for (int i = 0; i < allmenuList.size(); i++) {
                    Menu menu = allmenuList.get(i);
                    if ("1".equals(menu.getMENU_TYPE())) {
                        menuList1.add(menu);
                    } else {
                        menuList2.add(menu);
                    }
                }
                session.removeAttribute(USERNAME + Const.SESSION_menuList);
                if (role.getROLE_ID().equals("1")) {
                    session.setAttribute("changeMenu", "2");
                }
                if ("2".equals(session.getAttribute("changeMenu"))) {
                    session.setAttribute(USERNAME + Const.SESSION_menuList, menuList1);
                    session.removeAttribute("changeMenu");
                    session.setAttribute("changeMenu", "1");
                    menuList = menuList1;
                } else {
                    session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
                    session.removeAttribute("changeMenu");
                    session.setAttribute("changeMenu", "2");
                    menuList = menuList2;
                }
            } else {
                menuList = (List<Menu>) session.getAttribute(USERNAME + Const.SESSION_menuList);
            }
            // 切换菜单处理=====end
            if (null == session.getAttribute(USERNAME + Const.SESSION_QX)) {
                session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME)); // 按钮权限放到session中
            }
            this.getRemortIP(USERNAME); // 更新登录IP
            mv.setViewName("system/index/main");
            mv.addObject("user", user);
            mv.addObject("menuList", menuList);
            if (role.getROLE_ID().equals("1")) {// 为admin时
                mv.addObject("role", "1");
            }
            if (role.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)) {
                mv.addObject("msg_role", "internet");
            }

		} catch (Exception e) {
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}

		// 获取消息通知菜单的菜单id
		PageData pdMessage = new PageData();
		pdMessage.put("menu_name", "消息通知");
		pdMessage = menuService.findByName(pdMessage);
		mv.addObject("pdMessage", pdMessage);


		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 根据角色权限获取本权限的菜单列表(递归处理)
	 * 
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList, String roleRights) {
		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			if (menuList.get(i).isHasMenu()) { // 判断是否有此菜单权限
				this.readMenu(menuList.get(i).getSubMenu(), roleRights);// 是：继续排查其子菜单
			} else {
				menuList.remove(i);
				i--;
			}
		}
		return menuList;
	}

	/**
	 * 进入tab标签
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tab")
	public String tab() {
		return "system/index/tab";
	}

	/**
	 * 后台首页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_default")
	public ModelAndView defaultPage(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "首页列表Store");
		ModelAndView mv = this.getModelAndView();
        User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		List<PageData> varList = null;
		String ROLE_ID = user.getROLE_ID();
		String INTENET_ID = user.getINTENET_ID();
		String state = null;
		// 通过角色id去获取角色名称
		pd.put("ROLE_ID", ROLE_ID);
		List<PageData> roleList = roleService.getRoleName(pd);
		String ROLE_NAME = roleList.get(0).getString("ROLE_NAME");
		if (ROLE_ID.equals(PublicManagerUtil.INTERNETROLEID)) {// 当为网吧老板时
			pd.put("internetId", INTENET_ID);
			page.setPd(pd);
			
			// 查询用户信息和待办事项
			Map<String, Object> map = this.count(page);
			mv.addObject("map", map);
			
			//计算角色相关门店
			List<PageData> storeList = homeService.storeLists(page);
			pd.put("list", null);
			mv.addObject("storeList", storeList);
			
			//查看揽客产品动态
			PageData pd1 =productNewsService.list();
			if(StringUtil.isEmpty(pd1)) {//如果版本预告为空,则去查询版本日志
				PageData pd2=productVersionService.selectId();//查询最新版本id
				PageData pd3 = new PageData();
				if(StringUtil.isEmpty(pd2)) {//没有版本日志
				}else {//有版本日志
					pd2.put("version_id", pd2.get("id").toString());
					pd3= productVersionService.showVersion(pd2);//查询指定版本信息
					List<PageData> list = productVersionService.showDetail(pd2);//查询指定版本详情
					mv.addObject("pd3", pd3);
					mv.addObject("list", list);
				}
			}else {//版本预告不为空
				mv.addObject("pd1", pd1);
			}
			mv.addObject("name", user.getUSERNAME());//用户名
			mv.addObject("roleName", ROLE_NAME);     //角色名
			mv.addObject("store_state", "全部门店");        
			mv.addObject("pd", pd);
			mv.setViewName("system/index/home");//首页页面
			return mv;
		} else if (ROLE_ID.equals(PublicManagerUtil.ADMINROLEID) || ROLE_ID.equals(PublicManagerUtil.LKROLEID)
				|| ROLE_NAME.equals("后台管理员") && INTENET_ID.equals(INTENETID)) {// admin或揽客运营者或后台管理员
			pd.put("USER_ID", user.getUSER_ID());
			page.setPd(pd);
			varList = intenetService.IntenetlistPage(page); // 列出admin Intenet列表
			state = "1";
		} else if (ROLE_ID.equals(PublicManagerUtil.AGENTROLEID)) {// 为代理商时
			pd.put("USER_ID", user.getUSER_ID());
			page.setPd(pd);
			varList = agentService.selStore(page);// 查询代理商代理的门店
			state = "3";
		} else {// 店长,店员等
			varList = storeService.getStoreList(user.getUSER_ID()); // 通过user_id去找关联门店id和name的集合
			List<String> list = new ArrayList<>();
			for (int i = 0; i < varList.size(); i++) {
				list.add(i,varList.get(i).get("STORE_ID").toString());
			}
			pd.put("list", Joiner.on("','").join(list)); //门店id集合
			pd.put("internetId", INTENET_ID);
			page.setPd(pd);
			Map<String, Object> map = this.count(page);
			mv.addObject("map", map);
			List<PageData> storeList = homeService.storeList(page);
			mv.addObject("storeList", storeList);     //门店列表
			//查看版本预告
			PageData pd1 =productNewsService.list();
			if(StringUtil.isEmpty(pd1)) {//如果版本预告为空,则去查询版本日志
				PageData pd2=productVersionService.selectId();//查询最新版本id
				PageData pd3 = new PageData();
				if(StringUtil.isEmpty(pd2)) {//没有版本日志
					
				}else {//有版本日志
					pd2.put("version_id", pd2.get("id").toString());
					pd3= productVersionService.showVersion(pd2);//查询指定版本信息
					List<PageData> list1 = productVersionService.showDetail(pd2);//查询指定版本详情
					mv.addObject("pd3", pd3);
					mv.addObject("list", list1);
				}
			}else {//版本预告不为空
				mv.addObject("pd1", pd1);
			}
			mv.addObject("name", user.getUSERNAME());//用户名
			mv.addObject("roleName", ROLE_NAME);     //角色名
			mv.addObject("store_state", storeList.get(0).get("STORE_NAME"));    
			mv.addObject("pd", pd);
			mv.setViewName("system/index/home");
			return mv;
		}
		mv.setViewName("system/index/default");
		mv.addObject("varList", varList);
		mv.addObject("state", state);
		return mv;
	}

	/**
	 * 用户注销
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout() {
		String USERNAME = Jurisdiction.getUsername(); // 当前登录的用户名
		logBefore(logger, USERNAME + "退出系统");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Session session = Jurisdiction.getSession(); // 以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		// shiro销毁登录
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		pd = this.getPageData();
		pd.put("msg", pd.getString("msg"));
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/index/login");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 获取用户权限
	 * 
	 * @param USERNAME
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME) {
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put("USERNAME", USERNAME);
			pd.put("ROLE_ID", userService.findByUsername(pd).get("ROLE_ID").toString());// 获取角色ID
			pd = roleService.findObjectById(pd); // 获取角色信息
			map.put("adds", pd.getString("ADD_QX")); // 增
			map.put("dels", pd.getString("DEL_QX")); // 删
			map.put("edits", pd.getString("EDIT_QX")); // 改
			map.put("chas", pd.getString("CHA_QX")); // 查
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if ("admin".equals(USERNAME)) {
				buttonQXnamelist = fhbuttonService.listAll(pd); // admin用户拥有所有按钮权限
			} else {
				buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd); // 此角色拥有的按钮权限标识列表
			}
			for (int i = 0; i < buttonQXnamelist.size(); i++) {
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"), "1"); // 按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return map;
	}

	/**
	 * 更新登录用户的IP
	 * 
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}

	/**
	 * 计算待查看消息,待发货商品,待核销卡券,待回复留言
	 * 
	 * @param page
	 * @throws Exception
	 */
	public Map<String, Object> count(Page page) throws Exception {
		Map<String, Object> map = homeService.count(page);
		return map;
	}

	/**
	 * 通过条件查询收入
	 * 
	 * @param page
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/incomeCount")
	public JSONObject incomeCount(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "收入条件查询");
		JSONObject json = new JSONObject();
        User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		pd.put("internetId", user.getINTENET_ID());
		page.setPd(pd);
		// chart_title--标题,categories--X轴数据(日,周,月,年),data--收入值(每个时间段对应收入)
		json = homeService.incomeCount(page);
		return json;
	}

	/**
	 * 通过条件查询粉丝/会员数量
	 * 
	 * @param page
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/userCount")
	public JSONObject userCount(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "粉丝/会员条件查询");
		JSONObject json = new JSONObject();
        User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		pd.put("internetId", user.getINTENET_ID());
		page.setPd(pd);
		// chart_title--标题,categories--X轴数据(周,月,年),fans--粉丝数,member--会员数
		json = homeService.userCount(page);
		return json;
	}
	
	/**
	 * 通过条件查询待办事项
	 * 
	 * @param page
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/home")
	public JSONObject home(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "首页按钮权限判断");
		JSONObject json = new JSONObject();
        User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		PageData pd1= homeService.selectMenu(pd);//去获取该菜单
		if(RightsHelper.testRights(Jurisdiction.getHC().get("chas"), pd1.get("MENU_ID").toString())) {//判断是否拥有权限
			json.put("result", PublicManagerUtil.TRUE);
			json.put("INTENET_ID", user.getINTENET_ID());
		}else if(pd.get("menuUrl").toString().equals("newerGuide/messageList.do")){//消息通知,老板可见,店员不可见
				json.put("result", PublicManagerUtil.TRUE);
				json.put("INTENET_ID", user.getINTENET_ID());
		}else {
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "没有权限");
		}
		return json;
	}
}
