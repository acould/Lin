package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.WarehouseAddress;

/**
 * 
 * 对仓库位置表操作
 * @author haoxz11MyBatis 
 * @created Sun Jun 09 23:14:13 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface WarehouseAddressMapper {
	/**
	 * 插入仓库位置表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertWarehouseAddress(WarehouseAddress record);

	/**
	 * 根据主键得到仓库位置表表记录
	 *
	 * @haoxz11MyBatis
	 */
	WarehouseAddress getByPrimaryKey(Long warehouseId);

	/**
	 * 更新仓库位置表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateWarehouseAddress(WarehouseAddress record);

	/**
	 * 搜索仓库位置表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<WarehouseAddress> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索仓库位置表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<WarehouseAddress> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索仓库位置表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}