package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.ProductXyzcodeUser;

/**
 * 
 * 对广义仓库：商品与销售终端用户关联表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface ProductXyzcodeUserMapper {
	/**
	 * 插入广义仓库：商品与销售终端用户关联表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertProductXyzcodeUser(ProductXyzcodeUser record);

	/**
	 * 根据主键得到广义仓库：商品与销售终端用户关联表表记录
	 *
	 * @haoxz11MyBatis
	 */
	ProductXyzcodeUser getByPrimaryKey(Long id);

	/**
	 * 更新广义仓库：商品与销售终端用户关联表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateProductXyzcodeUser(ProductXyzcodeUser record);

	/**
	 * 搜索广义仓库：商品与销售终端用户关联表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<ProductXyzcodeUser> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索广义仓库：商品与销售终端用户关联表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<ProductXyzcodeUser> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索广义仓库：商品与销售终端用户关联表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}