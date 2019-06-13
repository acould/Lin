package star.web.platform;

import static star.share.util.Results.checkArguments;
import static star.share.util.Results.setResult;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import star.constant.ManagerCacheEnum;
import star.constant.SystemConstants;
import star.facade.ecmanager.menu.MenuFacade;
import star.facade.ecmanager.purview.vo.SysMenuVo;
import star.modules.cache.CachesService;
import star.share.util.Results;
import star.vo.result.ResultVo;
import star.web.BasicController;
import star.webtool.component.LoginComponent;

/*
 * 1.菜单加载
 * 2.一级菜单显示
 * 3.点击父菜单加载子菜单
 * 4.菜单新增与编辑
 * 5.菜单隐藏
 * 6.菜单排序
 */
@Controller
@RequestMapping(value="/platform/menu")
public class SysMenuController extends BasicController{

	@Autowired
	private MenuFacade menuFacade;
	@Autowired
	private LoginComponent loginComponent;
	@Resource
	private CachesService cacheService;
//	@Autowired
//	private SysPlatformFacade sysPlatformFacade;
	
	/*
	 * 菜单动态加载
	 */
	@ResponseBody
	@RequestMapping("/load")
	public Model loadMenu(Model model){
		ResultVo<List<SysMenuVo>> result=Results.newResultVo();		
		List<SysMenuVo> menuList=Lists.newArrayList();

		Long userId=loginComponent.getLoginUserId();
		boolean isLogin=userId!=null&&userId.intValue()!=0;
		result=checkArguments(isLogin, menuList, "10010", "用户id为空！");
		
		if(result.isSuccess()){
			String cacheKey = SystemConstants.SYSTEM_PLATFORM+"_"+userId;
			menuList=cacheService.getFromCache(ManagerCacheEnum.SYS_MENU, cacheKey);
			if(menuList==null){
				menuList=menuFacade.getMenuByUserId(SystemConstants.SYSTEM_PLATFORM,userId);
				logger.info("loadMenu platform={},menuList.userId={},size={},menuList={}",SystemConstants.SYSTEM_PLATFORM,userId,menuList.size(),menuList);
				cacheService.putInCache(ManagerCacheEnum.SYS_MENU, cacheKey, menuList);
			}
			
			result=Results.setResult(true, menuList);
		}	
		
		model.addAttribute("ret",result);
		return model;
	}
	
//	/*
//	 * 跳转到菜单管理
//	 */
//	@RequestMapping("/list.html")
//	public String firstMenuList(Model model){		
//		List<SysPlatformVo> sysplatformlist = sysPlatformFacade.getAllList();
//		model.addAttribute("sysplatformlist",sysplatformlist);	
//		return "/platform/menu/list";
//	}
	
	/*
	 * 根据父菜单id获取子菜单列表
	*/ 
	@ResponseBody
	@RequestMapping("/getListById")
	public Model getMenuListByParentId(Long id,String platform){	
		logger.info("getListById id={},platform={} ",id,platform);
		ResultVo<List<SysMenuVo>> result=Results.newResultVo();
		ExtendedModelMap model=new ExtendedModelMap();
		List<SysMenuVo> menus=Lists.newArrayList();

		if(id!=null){
			result=setResult(true);
		}
		
		if(result.isSuccess()){
			menus=menuFacade.getMenuListByPlatformParentId(id,platform);
			result=setResult(true,menus);
		}
		
		model.addAttribute("ret",result);
		return model;
	}
	
//	/*
//	 * 菜单详细信息
//	 */
//	@ResponseBody
//	@RequestMapping("/edit")
//	public Model editMenu(Long id){
//		ResultVo<SysMenuVo> result=Results.newResultVo();
//		ExtendedModelMap model=new ExtendedModelMap();
//		
//		SysMenuVo sysMenuVo=new SysMenuVo();
//		if(id!=null){
//			sysMenuVo=menuFacade.getMenuById(id);
//		}
//		result=Results.setResult(true,sysMenuVo);
//		
//		model.addAttribute("ret",result);
//		return model;
//	}
//	/*
//	 * 保存菜单信息
//	 */
//	@ResponseBody
//	@RequestMapping("/save")
//	public Model saveMenu(SysMenuVo sysMenu,String platform){
//		ResultVo<String> result=Results.newResultVo();
//		ExtendedModelMap model=new ExtendedModelMap();	
//		
//		result=saveParamValidate(sysMenu);	
//		if(result.isSuccess() && sysMenu != null){			
//			int rowcount=0;		
//			sysMenu.setPlatform(platform);//设置平台
//			if(sysMenu.getId()!=null&&sysMenu.getId().intValue()!=0){			
//				rowcount=menuFacade.updateMenu(sysMenu);
//			}else{
//				sysMenu.setPriority(getPriority(sysMenu.getParentId()));
//				rowcount=menuFacade.insertMenu(sysMenu);
//			}
//			
//			result=checkArguments( rowcount != 0 , "保存菜单失败！");
//		}
//		
//		model.addAttribute("ret",result);
//		return model;
//	}
	
//	/*
//	 * 菜单保存参数验证
//	 */
//	private ResultVo<String> saveParamValidate(SysMenuVo sysMenu){
//		ResultVo<String> result=Results.newResultVo();
//		
//		result=Results.setResult(true);
//		if(result.isSuccess()&&(sysMenu==null)){
//			result=Results.setResult(false,"菜单信息不能为空！");
//			return result;
//		}
//		if(result.isSuccess()&&(sysMenu.getParentId()==null||sysMenu.getParentId()==0)){
//			result=Results.setResult(false,"父菜单id不能为空！");
//			return result;
//		}
//		if(result.isSuccess()&&(StringUtil.isEmpty(sysMenu.getName()))){
//			result=Results.setResult(false,"菜单名称不能为空！");
//		}
//		
//		return result;
//	}
	
	/*
	 * 获取位置权重
	 */
	public int getPriority(Long parentId){
		return menuFacade.getPriorityByParentId(parentId);
	}
	
//	/*
//	 * 更改菜单显示状态
//	 */
//	@ResponseBody
//	@RequestMapping("/changeDisplayState")
//	public Model changeMenuDisplayState(Long id,String display){		
//		ResultVo<String> result=Results.newResultVo();
//		ExtendedModelMap model=new ExtendedModelMap();
//		
//		boolean expression=id!=null&&StringUtil.isNotEmpty(display);
//		result=checkArguments(expression, "参数错误！");
//		
//		if(result.isSuccess()){
//			
//			int rowCount=menuFacade.changeMenuDisplayState(id, display);
//			result=checkArguments(rowCount!=0, "更改菜单显示状态失败！");
//		}
//			
//		model.addAttribute("ret",result);
//		return model;
//	}
	
//	/*
//	 * 菜单排序,交换两个菜单项位置权重
//	 */
//	@ResponseBody
//	@RequestMapping("/updatePriority")
//	public Model updateMenuPriority(Long id1, Long id2){
//		ResultVo<String> result=Results.newResultVo();
//		ExtendedModelMap model=new ExtendedModelMap();
//
//		boolean expression=id1!=null&&id2!=null;
//		result=checkArguments(expression, "参数错误！");
//		
//		if(result.isSuccess()){
//			SysMenuVo sysMenuVo1=menuFacade.getMenuById(id1);
//			SysMenuVo sysMenuVo2=menuFacade.getMenuById(id2);
//			int priority1=sysMenuVo1.getPriority();
//			int priority2=sysMenuVo2.getPriority();
//			sysMenuVo1.setPriority(priority2);
//			sysMenuVo2.setPriority(priority1);
//			int rowcount1=menuFacade.updateMenu(sysMenuVo1);
//			int rowCount2=menuFacade.updateMenu(sysMenuVo2);
//			
//			result=checkArguments(rowcount1!=0&&rowCount2!=0, "更改菜单顺序失败！");
//		}
//		
//		model.addAttribute("ret",result);
//		return model;
//	}

}
