package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.WarehouseXyzcode;

/**
 * 
 * 对仓库空间编码表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface WarehouseXyzcodeMapper {
	/**
	 * 插入仓库空间编码表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertWarehouseXyzcode(WarehouseXyzcode record);

	/**
	 * 根据主键得到仓库空间编码表表记录
	 *
	 * @haoxz11MyBatis
	 */
	WarehouseXyzcode getByPrimaryKey(Long warehouseId);

	/**
	 * 更新仓库空间编码表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateWarehouseXyzcode(WarehouseXyzcode record);

	/**
	 * 搜索仓库空间编码表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<WarehouseXyzcode> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索仓库空间编码表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<WarehouseXyzcode> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索仓库空间编码表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}