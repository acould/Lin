package com.lk.dao;


import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.RowBounds;


/**
 * 
 * Datetime   ： 2016年1月4日 上午10:52:32<br>
 * Title      :  GenericDao.java<br>
 * Description:   DAO通用实现<br>
 * Company    :  hiwan<br>
 * @author cbj
 *
 */
public interface GenericDao<T,PK extends Serializable> extends BaseDao {

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T selectOne(PK pk);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T selectOne(T entity);
	
	/**
	 * 查询数据列表
	 * @param entity
	 * @return
	 */
	public List<T> selectList(T entity);
	
	/**
	 * 分页查询列表
	 * @param entity
	 * @return
	 */
	public List<T> selectListPage(T entity,RowBounds rowBounds);
	
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(T entity);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String id);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public int delete(T entity);

}
