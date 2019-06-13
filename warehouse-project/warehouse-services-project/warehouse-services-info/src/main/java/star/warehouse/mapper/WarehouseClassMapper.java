package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.WarehouseClass;

/**
 * 
 * 对仓库分类表操作
 * @author haoxz11MyBatis 
 * @created Wed Nov 21 16:19:49 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface WarehouseClassMapper {
	/**
	 * 插入仓库分类表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertWarehouseClass(WarehouseClass record);

	/**
	 * 根据主键得到仓库分类表表记录
	 *
	 * @haoxz11MyBatis
	 */
	WarehouseClass getByPrimaryKey(Long id);

	/**
	 * 更新仓库分类表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateWarehouseClass(WarehouseClass record);

	/**
	 * 搜索仓库分类表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<WarehouseClass> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索仓库分类表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<WarehouseClass> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索仓库分类表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}