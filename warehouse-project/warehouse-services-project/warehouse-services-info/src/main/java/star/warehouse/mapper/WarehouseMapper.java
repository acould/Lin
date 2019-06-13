package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.Warehouse;

/**
 * 
 * 对仓库信息表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface WarehouseMapper {
	/**
	 * 插入仓库信息表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertWarehouse(Warehouse record);

	/**
	 * 根据主键得到仓库信息表表记录
	 *
	 * @haoxz11MyBatis
	 */
	Warehouse getByPrimaryKey(Long id);

	/**
	 * 更新仓库信息表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateWarehouse(Warehouse record);

	/**
	 * 搜索仓库信息表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<Warehouse> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索仓库信息表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<Warehouse> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索仓库信息表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}