package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.ProductXyzcodeStorageLog;

/**
 * 
 * 对商品出入库、变更等记录表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface ProductXyzcodeStorageLogMapper {
	/**
	 * 插入商品出入库、变更等记录表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertProductXyzcodeStorageLog(ProductXyzcodeStorageLog record);

	/**
	 * 根据主键得到商品出入库、变更等记录表表记录
	 *
	 * @haoxz11MyBatis
	 */
	ProductXyzcodeStorageLog getByPrimaryKey(Long id);

	/**
	 * 更新商品出入库、变更等记录表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateProductXyzcodeStorageLog(ProductXyzcodeStorageLog record);

	/**
	 * 搜索商品出入库、变更等记录表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<ProductXyzcodeStorageLog> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索商品出入库、变更等记录表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<ProductXyzcodeStorageLog> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索商品出入库、变更等记录表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}