package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.StorageProductXyzRelated;

/**
 * 
 * 对仓库存储区与商品存放关联表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface StorageProductXyzRelatedMapper {
	/**
	 * 插入仓库存储区与商品存放关联表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertStorageProductXyzRelated(StorageProductXyzRelated record);

	/**
	 * 根据主键得到仓库存储区与商品存放关联表表记录
	 *
	 * @haoxz11MyBatis
	 */
	StorageProductXyzRelated getByPrimaryKey(Long id);

	/**
	 * 更新仓库存储区与商品存放关联表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateStorageProductXyzRelated(StorageProductXyzRelated record);

	/**
	 * 搜索仓库存储区与商品存放关联表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<StorageProductXyzRelated> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索仓库存储区与商品存放关联表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<StorageProductXyzRelated> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索仓库存储区与商品存放关联表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}