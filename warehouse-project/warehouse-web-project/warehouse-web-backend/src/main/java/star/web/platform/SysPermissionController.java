package star.web.platform;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import star.facade.ecmanager.menu.MenuFacade;
import star.facade.ecmanager.purview.PurviewFacade;
import star.facade.ecmanager.purview.vo.SysMenuVo;
import star.share.util.Results;
import star.util.StringUtil;
import star.vo.result.ResultVo;
import star.web.BasicController;

@Controller
@RequestMapping("/sys/permission")
public class SysPermissionController extends BasicController {

	@Resource
	private PurviewFacade purviewFacade;
	@Resource
	private MenuFacade menuFacade;
//	@Autowired
//	private SysPlatformFacade sysPlatformFacade;
//	
//	private final static String MENU_PERMISSION="menu";//分类为菜单时，进行唯一判断
	
//	@RequestMapping(value = "/list.html")
//	public String list(Model model, SysPermissionVo sysPermissionVo) {
//		SearchDataVo vo = SearchUtil.getVo();
//		SearchResult<SysPermissionVo> searchResult = purviewFacade.getPermissionList(sysPermissionVo,vo.getStart(),vo.getSize());
//
//		vo.setList(searchResult.getList());
//		vo.setCount(searchResult.getTotal());
//		SearchUtil.putToModel(model, vo);
//		List<SysPlatformVo> sysplatformlist = sysPlatformFacade.getAllList();
//		model.addAttribute("sysplatformlist",sysplatformlist);	
//		
//		return "/platform/permission/list";
//
//	}
//
//	@RequestMapping(value = "/edit")
//	public String edit(Model model, Long id) {
//		if (null != id) {
//			SysPermissionVo permissionVo = purviewFacade.getPermissionByPrimaryId(id);
//			model.addAttribute("permission", permissionVo);
//		}
//		List<SysPlatformVo> sysplatformlist = sysPlatformFacade.getAllList();
//		model.addAttribute("sysplatformlist",sysplatformlist);	
//		return "/platform/permission/edit";
//	}
//
//	@ResponseBody
//	@RequestMapping("/save")
//	public Model add(HttpServletRequest reqeust,
//			 SysPermissionVo sysPermissionVo) {	
//		ResultVo<String> result = Results.newResultVo();
//		ExtendedModelMap model = new ExtendedModelMap();
//		//保存前校验
//		sysPermissionVo=XSSUtil.cleanXSS(sysPermissionVo);
//		result = prePermissionSaveCheck(sysPermissionVo);
//
//		if(result.isSuccess() && sysPermissionVo != null){		
//			int rowCount=0;		
//			if(sysPermissionVo.getId() == null){
//				rowCount = purviewFacade.insertPermission(sysPermissionVo);
//			}else{
//				rowCount = purviewFacade.updatePermission(sysPermissionVo);
//			}				
//			result = Results.checkArguments(rowCount!=0, "保存权限失败！");
//		}
//
//		model.addAttribute("ret", result);
//		return model;
//	}
//	
//	/**
//	 * 菜单保存前校验
//	 */
//	private ResultVo<String> prePermissionSaveCheck(SysPermissionVo sysPermissionVo){
//		ResultVo<String> result = Results.newResultVo();
//		//参数是否为空校验
//		result = verifyPermissionVo(sysPermissionVo); 
//		if(result.isSuccess()){
//			//url是否重复校验
//			boolean repeatFlag = checkRepeat(sysPermissionVo); 
//			if(repeatFlag){
//				result = Results.setResult(false, "输入的url重复");
//				return result;  
//			}
//			//分组下菜单类型权限是否唯一校验
//			if(MENU_PERMISSION.equals(sysPermissionVo.getCategory())){
//				boolean uniqueFlag = checkMenuIsUnique(sysPermissionVo);
//				if(!uniqueFlag){
//					result = Results.setResult(false, "该分组下已存在权限类型为菜单的权限");
//					return result;
//				}
//			}		
//		}
//		return result;
//	}
//	
//	/**
//	 * 判断分组下菜单权限是否唯一
//	 */
//	private boolean checkMenuIsUnique(SysPermissionVo sysPermissionVo){
//		//更新情况判断是否为自身
//		if(isSelfGroup(sysPermissionVo)){
//			return true;
//		}
//		return purviewFacade.menuCategoryIsUnique(sysPermissionVo.getFirstGroupId()
//				,sysPermissionVo.getSecondGroupId(),sysPermissionVo.getThirdGroupId());
//	}
//	
//	/**
//	 * 判断url是否重复
//	 */
//	private boolean checkRepeat(SysPermissionVo sysPermissionVo){
//		//更新情况判断是否为自身
//		if(isSelfUrl(sysPermissionVo)){
//			return false;
//		}
//		//url重复判断
//		return purviewFacade.checkUrlIsRepeat(sysPermissionVo.getUrl(),sysPermissionVo.getPlatform());
//	}
//	
//	/**
//	 * 更新操作排除当前记录
//	 */
//	private boolean isSelfUrl(SysPermissionVo sysPermissionVo){
//		//菜单id
//		Long id = sysPermissionVo.getId();
//		if(id != null && id != 0){
//			SysPermissionVo permissionVo = purviewFacade.getPermissionByPrimaryId(id);
//			if(permissionVo.getUrl() != null && sysPermissionVo.getUrl() != null){
//				String noParamSourceUrl = getNoParamUrl(sysPermissionVo.getUrl());
//				String noParamDesUrl = getNoParamUrl(permissionVo.getUrl());
//				if(noParamSourceUrl.equals(noParamDesUrl)){//如果url未修改,则判断为不重复
//					return true;
//				}
//			}			
//		}
//		return false;
//	}
//	
//	/**
//	 * 更新操作排除当前记录
//	 */
//	private boolean isSelfGroup(SysPermissionVo sysPermissionVo){
//		//菜单id
//		Long id = sysPermissionVo.getId();
//		if(id != null && id != 0){
//			SysPermissionVo permissionVo = purviewFacade.getPermissionByPrimaryId(id);
//			if(isEqual(permissionVo.getFirstGroupId(),sysPermissionVo.getFirstGroupId()) &&
//					isEqual(permissionVo.getSecondGroupId(),sysPermissionVo.getSecondGroupId()) &&
//					isEqual(permissionVo.getThirdGroupId(),sysPermissionVo.getThirdGroupId())){
//				return true;
//			}			
//		}
//		return false;
//	}
//	
//	private boolean isEqual(Long l1,Long l2){
//		if(l1 == null && l2 == null){
//			return true;
//		}
//		if(l1 == null || l2 == null){
//			return false;
//		}
//		if(l1 == l2 || l1.intValue() == l2.intValue()){
//			return true;
//		}
//		return false;
//	}
//	/**
//	 *url去参数
//	 */
//	private String getNoParamUrl(String url){
//		int endPosition = url.indexOf("?");
//		String noParamUrl = url;
//		if(endPosition != -1){
//			noParamUrl = url.substring(0, endPosition);
//		}
//		return noParamUrl;
//	}
//	
//	@RequestMapping("/delete")
//	@ResponseBody
//	public Model delete(Model model, HttpServletRequest reqeust, Long id) {
//		ResultVo<Boolean> ret = new ResultVo<Boolean>();
//		ret.setSuccess(false);
//		if (id == null || id < 1) {
//			ret.setResultDes("id 不能为空");
//			ret.setSuccess(false);
//			model.addAttribute("ret", ret);
//			return model;
//		}
//		if (0l == purviewFacade.deletePermission(id)) {
//			ret.setResultDes("删除失败");
//			ret.setSuccess(false);
//			model.addAttribute("ret", ret);
//		}
//		ret.setSuccess(true);
//		model.addAttribute("ret", ret);
//		return model;
//	}
//
//	private ResultVo<String> verifyPermissionVo(SysPermissionVo permissionVo) {
//
//		ResultVo<String> ret = Results.newResultVo();
//		ret=Results.setResult(true);
//		if (permissionVo == null) {
//			ret=Results.setResult(false,"权限不能为空");
//			return ret;
//		}
//		if (permissionVo.getFirstGroupId()==null) {
//			ret=Results.setResult(false,"一级分组不能为空");
//			return ret;
//		}
//		if (permissionVo.getSecondGroupId()==null) {
//			ret=Results.setResult(false,"二级分组不能为空");
//			return ret;
//		}
//		if (StringUtil.isEmpty(permissionVo.getName())) {
//			ret=Results.setResult(false,"权限名不能为空");
//			return ret;
//		}
//		if (StringUtil.isEmpty(permissionVo.getPlatform())) {
//			ret=Results.setResult(false,"平台不能为空");
//			return ret;
//		}
//		if (StringUtil.isEmpty(permissionVo.getUrl())) {
//			ret=Results.setResult(false,"访问URL不能为空");
//			return ret;
//		}
//		if (StringUtil.isEmpty(permissionVo.getVerify())) {
//			ret=Results.setResult(false,"是否验证不能为空");
//			return ret;
//		}
//		
//		return ret;
//	}

	/*
	 * 获取分组信息
	 */
	@ResponseBody
	@RequestMapping("/getGroup")
	public ResultVo<List<SysMenuVo>> getGroupByParentId(Long groupId,String platform){
		ResultVo<List<SysMenuVo>> result=Results.newResultVo();
		List<SysMenuVo> menuList=Lists.newArrayList();
		
		result.setSuccess(true);
		if(groupId==null || StringUtil.isEmpty(platform)){
			logger.info("getGroupByParentId  groupId={},platform={}",groupId,platform );
			result=Results.setResult(false,"参数错误！");
		}
		if(result.isSuccess()){		
			menuList=menuFacade.getMenuListByPlatformParentId(groupId,platform);
			result=Results.setResult(true,menuList);
		}
		
		return result;
	}
	
}
