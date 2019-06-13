package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.StorageArea;

/**
 * 
 * 对存储区表（可以多级：一货架多货位等）操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface StorageAreaMapper {
	/**
	 * 插入存储区表（可以多级：一货架多货位等）记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertStorageArea(StorageArea record);

	/**
	 * 根据主键得到存储区表（可以多级：一货架多货位等）表记录
	 *
	 * @haoxz11MyBatis
	 */
	StorageArea getByPrimaryKey(Long id);

	/**
	 * 更新存储区表（可以多级：一货架多货位等）记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateStorageArea(StorageArea record);

	/**
	 * 搜索存储区表（可以多级：一货架多货位等）列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<StorageArea> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索存储区表（可以多级：一货架多货位等）列表
	 *
	 * @haoxz11MyBatis
	 */
	List<StorageArea> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索存储区表（可以多级：一货架多货位等）的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}