package com.lk.service.system.menu;

import java.util.List;

import com.lk.entity.system.Menu;
import com.lk.util.PageData;


/**说明：MenuService 菜单处理接口
 * @author fh 313596790
 */
public interface MenuManager {

	/**查询是否有子菜单
	 * @param parentId 查询子菜单
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listSubMenuByParentId(String parentId)throws Exception;
	
	/** 查询菜单信息
	 * @param pd包含菜单主键
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuById(PageData pd) throws Exception;
	
	/**新增菜单
	 * @param menu 包含menu所有字段信息
	 * @throws Exception
	 */
	public void saveMenu(Menu menu) throws Exception;
	
	/**查询当前表中最大的主键id
	 * @param pd 无
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pd) throws Exception;
	
	/**删除菜单
	 * @param MENU_ID
	 * @throws Exception
	 */
	public void deleteMenuById(String MENU_ID) throws Exception;
	
	/**修改菜单
	 * @param menu
	 * @throws Exception
	 */
	public void edit(Menu menu) throws Exception;
	
	/**修改菜单图标
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData editicon(PageData pd) throws Exception;
	
	/** 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu(String MENU_ID) throws Exception;
	
	/**获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenuQx(String MENU_ID) throws Exception;
	
	/**查询全部菜单
	 * @param pd无
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listMenu(PageData pd) throws Exception;

	/**
	 * 根据菜单名称获取菜单
	 * @param pdMessage
	 * @return
	 * @throws Exception
	 */
	public PageData findByName(PageData pdMessage) throws Exception;


	public PageData findByUrlAndName(PageData pdMessage) throws Exception;

	
}
