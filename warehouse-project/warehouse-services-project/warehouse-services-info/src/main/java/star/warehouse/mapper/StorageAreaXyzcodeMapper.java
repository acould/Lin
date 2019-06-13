package star.warehouse.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

import star.warehouse.po.StorageAreaXyzcode;

/**
 * 
 * 对存储区编码表操作
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public interface StorageAreaXyzcodeMapper {
	/**
	 * 插入存储区编码表记录
	 *
	 * @haoxz11MyBatis
	 */
	int insertStorageAreaXyzcode(StorageAreaXyzcode record);

	/**
	 * 根据主键得到存储区编码表表记录
	 *
	 * @haoxz11MyBatis
	 */
	StorageAreaXyzcode getByPrimaryKey(Long storageAreaId);

	/**
	 * 更新存储区编码表记录
	 *
	 * @haoxz11MyBatis
	 */
	int updateStorageAreaXyzcode(StorageAreaXyzcode record);

	/**
	 * 搜索存储区编码表列表，带分页
	 *
	 * @haoxz11MyBatis
	 */
	List<StorageAreaXyzcode> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);

	/**
	 * 搜索存储区编码表列表
	 *
	 * @haoxz11MyBatis
	 */
	List<StorageAreaXyzcode> getListByWhere(HashMap<String, Object> searchMap);

	/**
	 * 得到搜索存储区编码表的记录数量
	 *
	 * @haoxz11MyBatis
	 */
	int getCountByWhere(HashMap<String, Object> searchMap);
}