package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.StoreInCheck;

/**
 * 
 * 对验货确认单表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface StoreInCheckMapper {
	/**
	 * 插入验货确认单表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertStoreInCheck(StoreInCheck record);

	/**
	 * 根据主键得到验货确认单表表记录
	 *
	 * @haoxz11MyBatis
	 */
	StoreInCheck getByPrimaryKey(Long id);

	/**
	 * 更新验货确认单表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateStoreInCheck(StoreInCheck record);

	/**
	 * 搜索验货确认单表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<StoreInCheck> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索验货确认单表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<StoreInCheck> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索验货确认单表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}