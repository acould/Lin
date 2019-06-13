package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.StoreIn;

/**
 * 
 * 对入库单表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface StoreInMapper {
	/**
	 * 插入入库单表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertStoreIn(StoreIn record);

	/**
	 * 根据主键得到入库单表表记录
	 *
	 * @haoxz11MyBatis
	 */
	StoreIn getByPrimaryKey(Long id);

	/**
	 * 更新入库单表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateStoreIn(StoreIn record);

	/**
	 * 搜索入库单表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<StoreIn> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索入库单表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<StoreIn> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索入库单表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}