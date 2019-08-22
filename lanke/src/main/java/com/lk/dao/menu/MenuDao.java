package com.lk.dao.menu;


import java.util.List;

import com.lk.dao.GenericDao;
import com.lk.dao.MyBatisDao;
import com.lk.entity.system.Menu;

@MyBatisDao
public interface MenuDao extends GenericDao<Menu, String> {
	
	public List<String> selectUserMenuList(String userId);
	
	public List<Menu> selectUserMenus(String userId);
	
	/*public List<MenuInfo> selectMenusInfo(List<String> menuIdList,String roleId) {
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put("menuIdList", menuIdList);
		map.put("roleId", roleId);
		List<MenuInfo> menus = selectList("selectMenusInfo",map,
				MenuInfo.class);
		if (menus == null) {
			return new ArrayList<MenuInfo>();
		} else {
			return menus;
		}
	}*/
	public List<Menu> selectMenusInfo(List<String> menuIdList,String roleId);
	
	public List<Menu> selectMenuList(String parentId);

	public List<Menu> selectAllMenusInfo(String parent_menu);

	public Menu selectMenuInfo(String menuId);
	
	public int insertMenuInfo(Menu menuInfo);

	public int deleteMenuInfoByPK(String menuId);
	
	public int updateMenuInfo(Menu menuInfo);
	
}
