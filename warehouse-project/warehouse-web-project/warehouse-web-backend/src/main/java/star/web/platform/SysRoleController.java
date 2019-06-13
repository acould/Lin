package star.web.platform;

import static star.share.util.Results.checkArguments;
import static star.share.util.Results.checkNotNull;
import static star.share.util.Results.setResult;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import star.facade.ecmanager.platform.SysPlatformFacade;
import star.facade.ecmanager.platform.vo.SysPlatformVo;
import star.facade.ecmanager.purview.PurviewFacade;
import star.facade.ecmanager.purview.vo.PermissionCount;
import star.facade.ecmanager.purview.vo.PermissionTreeVo;
import star.facade.ecmanager.purview.vo.RolePermissionVo;
import star.facade.ecmanager.purview.vo.SysPermissionVo;
import star.facade.ecmanager.purview.vo.SysRoleVo;
import star.modules.cache.CachesService;
import star.share.util.Results;
import star.util.StringUtil;
import star.vo.RolePermission;
import star.vo.result.ResultVo;
import star.web.BasicController;

/*
 * 1.角色详情页展示
 * 2.保存角色信息
 * 3.展示角色列表
 * 4.角色删除
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController extends BasicController {

	@Autowired
	private PurviewFacade purviewFacade;
	@Resource
	private CachesService cacheService;
	@Autowired
	private SysPlatformFacade sysPlatformFacade;
	
//	private final static String ROLE_PLATFORM="MZWX";

	/*
	 * 角色编辑(包括新增与修改)
	 */
	@RequestMapping("/edit")
	public String editRole(Model model,Long id){
		SysRoleVo roleVo=new SysRoleVo();
		if(id!=null&&id.intValue()!=0){//修改
			
			roleVo = purviewFacade.getRoleByPrimaryKey(id);									
		}
		List<SysPlatformVo> sysplatformlist = sysPlatformFacade.getAllList();
		model.addAttribute("sysplatformlist",sysplatformlist);	
		
		model.addAttribute("roleVo",roleVo);				
		return "/platform/role/edit";
	}
	
	/*
	 * 获取权限信息
	 */
	@ResponseBody
	@RequestMapping("/getPermission")
	public Model editRole(Long id,String platform){
		ExtendedModelMap model=new ExtendedModelMap();
		ResultVo<List<PermissionTreeVo>> result=new ResultVo<>();

		List<PermissionTreeVo> permissionTree=initPermissionTree(id,platform);
		
		result.setSuccess(true);
		result.setResult(permissionTree);
		model.addAttribute("ret",result);	
		return model;
	}
	/*
	 * 保存角色信息
	 */
	@ResponseBody
	@RequestMapping("/save")
	public ResultVo<String> saveRole(HttpServletRequest request,SysRoleVo roleVo,String permissionIds){
		ResultVo<String> result=Results.newResultVo();

		result=checkNotNull(String.class, roleVo, "角色基本信息为空！");
		if(result.isSuccess()){
			result=paramValidate(roleVo);
		}
		
		if(result.isSuccess()){
			List<String> permissionInputList=splitToList(permissionIds);
			List<Long> permissionList=stringToIntegerList(permissionInputList);
			
			int rowCount=0;
			try{
				if(roleVo.getId()==null||roleVo.getId()==0){
					rowCount=purviewFacade.saveRolePermission(roleVo, permissionList);
				}else{
					rowCount=purviewFacade.updateRolePermission(roleVo, permissionList);
				}
//				ManagerVo manager = this.getManageVo(request);
//				cacheService.deleteCache(PurviewConstant.CacheTypeEnum.purview,manager.getId());
			}catch(Exception e){
				result = Results.setResult(false,"角色名称重复");
				return result;
			}
			
					
			result=checkArguments(rowCount!=0, "保存角色信息失败！");
		}
		
		return result;
	}

	/*
	 * 参数验证
	 */
	public ResultVo<String> paramValidate(SysRoleVo sysRoleVo){
		ResultVo<String> result=Results.newResultVo();
		
		result=setResult(true);
		if(result.isSuccess()&&StringUtil.isEmpty(sysRoleVo.getPlatform())){
			result=Results.setResult(false, "平台类型为空！");
		}
		if(result.isSuccess()&&StringUtil.isEmpty(sysRoleVo.getName())){
			result=Results.setResult(false, "角色名称为空！");
		}

		return result;
	}
	
	/*
	 * 展示角色列表
	 */
	@RequestMapping(value={"/list.html"})
	public String roleList(Model model,String platform) throws IllegalArgumentException, IllegalAccessException{		
		List<SysRoleVo> roles=getRoleList(platform);

		List<RolePermission> rolePermission=secondGroupDataProcess(roles);
		model.addAttribute("rolePermission",rolePermission);
		
		List<SysPlatformVo> sysplatformlist = sysPlatformFacade.getAllList();
		model.addAttribute("sysplatformlist",sysplatformlist);
		
		return "/platform/role/list";
	}
    
	/*
	 * 删除角色
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Model delete(Model model, HttpServletRequest request, Long id) {
		ResultVo<Integer> result =Results.newResultVo();
		
		result=checkNotNull(Integer.class, id, "角色id为空！");		
		if(result.isSuccess()){
			
			int rowcount=purviewFacade.deleteRolePermission(id);		
			result=checkArguments(rowcount!=0, "删除失败！");
		}

		model.addAttribute("ret", result);
		return model;
	}
	
	/*
	 * 角色列表二级分组权限数据处理
	 * 根据角色分别处理
	 */
	private List<RolePermission> secondGroupDataProcess(List<SysRoleVo> roles) throws IllegalArgumentException, IllegalAccessException{
		List<RolePermission> rolePermissionList=Lists.newArrayList();
		RolePermission rolePermission=null;
		
		Map<Integer,Integer> countMap = getSecendPermissionCount();
		for(SysRoleVo vo:roles){		
			String permission="";
			rolePermission=new RolePermission();
			
			List<RolePermissionVo> rolePermissions=getPermissionModuleByRoleId(vo.getId());
			for(RolePermissionVo rpVo:rolePermissions){
				int secondGroupTotalPermissionCount= (countMap==null || rpVo==null || rpVo.getSecondGroupId()==null)?0:countMap.get(rpVo.getSecondGroupId());
				permission += rpVo.getSecondGroupName()+"("+rpVo.getPermissionCount()+"/"+secondGroupTotalPermissionCount+")"+";";
			}
			
			rolePermission.setId(vo.getId());
			rolePermission.setName(vo.getName());	
			rolePermission.setPlatform(vo.getPlatform());
			rolePermission.setPermission(permission);		
			rolePermissionList.add(rolePermission);
		}
		
		return rolePermissionList;
	}
	
	/*
	 * 获取角色列表
	 */
	private List<SysRoleVo> getRoleList(String platform){
		
		return purviewFacade.getRoleListByPlatform(platform);
	}
	
	public List<RolePermissionVo> getPermissionModuleByRoleId(Long roleId){
		
		 return purviewFacade.getSecondGroupPermissionByRoleId(roleId);
	}
	
	/*
	 * 获取二级分组下的权限数
	 */
	public Map<Integer,Integer> getSecendPermissionCount() throws IllegalArgumentException, IllegalAccessException{
		
		List<PermissionCount> permissionCount=purviewFacade.getSecondGroupPermissionCount();	
		return listToMap(permissionCount,"secondGroupId","permissionCount");
	}
	
	
	/*
	 * 根据指定的key、value字段将list转换成map
	 */
	@SuppressWarnings("unchecked")
	private <K,V> Map<K,V> listToMap(List<? extends Object> list,String keyField,String valueField) throws IllegalArgumentException, IllegalAccessException{
		Map<K,V> map=Maps.newHashMap();
		
		Iterator<?> iterator = list.iterator();		 
		while (iterator.hasNext()) {
			Object obj=iterator.next();
			Field[] fields = obj.getClass().getDeclaredFields();
			
			K key=null;
			V value=null;
			for (int i = 0; i < fields.length; i++) {  
	            fields[i].setAccessible(true);  	   
	            
	            if(keyField.equals(fields[i].getName())){
	            	key=(K) fields[i].get(obj);
	            }
	            if(valueField.equals(fields[i].getName())){
	            	value=(V)fields[i].get(obj);
	            }
			}

			if(key!=null){
				map.put(key, value);
			}
		}
		
		return map;
	}

	/*
	 * 初始化权限树
	 */
	private List<PermissionTreeVo> initPermissionTree(Long roleId,String platform){		
		List<PermissionTreeVo> ptList=getPermissionTree(platform);//获取某平台的权限数据
		
		if(roleId!=null&&roleId!=0){
			
			List<SysPermissionVo> spList=getPermissionByRoleId(roleId,platform);//获取某平台+role的权限数据
			ptList=setCheckState(ptList,spList);
		}
		
		return ptList;
	}
	
	/*
	 * 更改权限树选中状态
	 */
	public List<PermissionTreeVo> setCheckState(List<PermissionTreeVo> ptList,List<SysPermissionVo> spList){
		List<PermissionTreeVo> ptVos=Lists.newArrayList();
		PermissionTreeVo ptVo=null;
		
		for(PermissionTreeVo permissionTreeVo:ptList){
			ptVo=permissionTreeVo;
			for(SysPermissionVo permissionVo:spList){
				if(permissionTreeVo.getId().intValue()==permissionVo.getId().intValue()){
					//将权限选中状态设置为true
					ptVo.setCheck(true);
					break;
				}
			}
			ptVos.add(ptVo);
		}

		return ptVos;
	}
	
	/*
	 * 获取权限树
	 */
	private List<PermissionTreeVo> getPermissionTree(String platform){
		List<PermissionTreeVo> permissionTreeList=Lists.newArrayList();
		permissionTreeList=purviewFacade.getPermissionTree(platform);
		
		return permissionTreeList;
	}
	
	/*
	 * 根据角色id获取权限信息
	 */
	private List<SysPermissionVo> getPermissionByRoleId(Long roleId,String platform){
		
		List<SysPermissionVo> sysPermissionList=purviewFacade.getPermissionListByRoleId(roleId,platform);	
		return sysPermissionList;
	}
	
	/*
	 * String分隔，返回list
	 */
	private List<String> splitToList(String permissionIds) {
		if(StringUtil.isEmpty(permissionIds)){
			return null;
		}
		return Splitter.onPattern(",")
						.omitEmptyStrings()							
						.splitToList(permissionIds);
	}
	
	/*
	 * List类型转换,String转Integer
	 */
	private List<Long> stringToIntegerList(List<String> inputList){
		if(inputList==null||inputList.size()<1){
			return null;
		}
		List<Long> outputList =new ArrayList<Long>(inputList.size());
		
		CollectionUtils.collect(inputList, new Transformer(){			
		        public Object transform(Object input){
		          return new Long((String)input);
		        }
		} ,outputList );
		
		return outputList;
	}
	
}
