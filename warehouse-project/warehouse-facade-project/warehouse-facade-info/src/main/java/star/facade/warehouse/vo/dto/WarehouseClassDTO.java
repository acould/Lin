package star.facade.warehouse.vo.dto;

import star.vo.BaseVo;

/**
 * 
 * 仓库分类表
 * @author haoxz11MyBatis 
 * @created Wed Nov 21 16:19:49 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class WarehouseClassDTO extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:warehouse_class.id
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：分类名称
	 *
	 * @haoxz11MyBatis
	 */
	private String name;

	/**
	 * 字段：分类图片url
	 *
	 * @haoxz11MyBatis
	 */
	private String url;

	/**
	 * 字段：排序
	 *
	 * @haoxz11MyBatis
	 */
	private Integer orderby;


	/**
	 * 字段：父级ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long parentId;

	/**
	 * 读取：warehouse_class.id
	 *
	 * @return warehouse_class.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：warehouse_class.id
	 *
	 * @param id warehouse_class.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * 读取：分类名称
	 *
	 * @return warehouse_class.name
	 *
	 * @haoxz11MyBatis
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：分类名称
	 *
	 * @param name warehouse_class.name
	 *
	 * @haoxz11MyBatis
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取：分类图片url
	 *
	 * @return warehouse_class.url
	 *
	 * @haoxz11MyBatis
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置：分类图片url
	 *
	 * @param url warehouse_class.url
	 *
	 * @haoxz11MyBatis
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 读取：排序
	 *
	 * @return warehouse_class.orderby
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getOrderby() {
		return orderby;
	}

	/**
	 * 设置：排序
	 *
	 * @param orderby warehouse_class.orderby
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	

	/**
	 * 读取：父级ID
	 *
	 * @return warehouse_class.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：父级ID
	 *
	 * @param parentId warehouse_class.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}