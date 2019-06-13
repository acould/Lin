package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.ProductXyzcodeStorage;

/**
 * 
 * 对商品与仓库空间关联表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface ProductXyzcodeStorageMapper {
	/**
	 * 插入商品与仓库空间关联表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertProductXyzcodeStorage(ProductXyzcodeStorage record);

	/**
	 * 根据主键得到商品与仓库空间关联表表记录
	 *
	 * @haoxz11MyBatis
	 */
	ProductXyzcodeStorage getByPrimaryKey(Long id);

	/**
	 * 更新商品与仓库空间关联表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateProductXyzcodeStorage(ProductXyzcodeStorage record);

	/**
	 * 搜索商品与仓库空间关联表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<ProductXyzcodeStorage> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索商品与仓库空间关联表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<ProductXyzcodeStorage> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索商品与仓库空间关联表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}