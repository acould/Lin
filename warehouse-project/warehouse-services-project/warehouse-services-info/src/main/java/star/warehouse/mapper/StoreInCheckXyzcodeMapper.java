package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.StoreInCheckXyzcode;

/**
 * 
 * 对验货单明细表或扫码明细log表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface StoreInCheckXyzcodeMapper {
	/**
	 * 插入验货单明细表或扫码明细log表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertStoreInCheckXyzcode(StoreInCheckXyzcode record);

	/**
	 * 根据主键得到验货单明细表或扫码明细log表表记录
	 *
	 * @haoxz11MyBatis
	 */
	StoreInCheckXyzcode getByPrimaryKey(Long id);

	/**
	 * 更新验货单明细表或扫码明细log表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateStoreInCheckXyzcode(StoreInCheckXyzcode record);

	/**
	 * 搜索验货单明细表或扫码明细log表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<StoreInCheckXyzcode> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索验货单明细表或扫码明细log表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<StoreInCheckXyzcode> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索验货单明细表或扫码明细log表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}