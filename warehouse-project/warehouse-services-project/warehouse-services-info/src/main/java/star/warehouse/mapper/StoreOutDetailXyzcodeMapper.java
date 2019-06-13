package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.StoreOutDetailXyzcode;

/**
 * 
 * 对出库单明细sku编码明细表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface StoreOutDetailXyzcodeMapper {
	/**
	 * 插入出库单明细sku编码明细表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertStoreOutDetailXyzcode(StoreOutDetailXyzcode record);

	/**
	 * 根据主键得到出库单明细sku编码明细表表记录
	 *
	 * @haoxz11MyBatis
	 */
	StoreOutDetailXyzcode getByPrimaryKey(Long id);

	/**
	 * 更新出库单明细sku编码明细表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateStoreOutDetailXyzcode(StoreOutDetailXyzcode record);

	/**
	 * 搜索出库单明细sku编码明细表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<StoreOutDetailXyzcode> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索出库单明细sku编码明细表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<StoreOutDetailXyzcode> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索出库单明细sku编码明细表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}