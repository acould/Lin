package star.warehouse.po;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 仓库分类表
 * @author haoxz11MyBatis 
 * @created Wed Nov 21 16:19:49 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class WarehouseClass extends BaseVo {
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
	 * 字段：商家ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long merchantId;

	/**
	 * 字段：状态 ON上线，OFF下线,DEL删除
	 *
	 * @haoxz11MyBatis
	 */
	private String status;

	/**
	 * 字段：创建时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date createTime;

	/**
	 * 字段：修改时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date modifyTime;

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
	 * 字段：创建者
	 *
	 * @haoxz11MyBatis
	 */
	private Long createrMid;

	/**
	 * 字段：修改者
	 *
	 * @haoxz11MyBatis
	 */
	private Long updaterMid;

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
	 * 读取：商家ID
	 *
	 * @return warehouse_class.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId warehouse_class.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：状态 ON上线，OFF下线,DEL删除
	 *
	 * @return warehouse_class.status
	 *
	 * @haoxz11MyBatis
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置：状态 ON上线，OFF下线,DEL删除
	 *
	 * @param status warehouse_class.status
	 *
	 * @haoxz11MyBatis
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return warehouse_class.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime warehouse_class.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return warehouse_class.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime warehouse_class.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
	 * 读取：创建者
	 *
	 * @return warehouse_class.creater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public Long getCreaterMid() {
		return createrMid;
	}

	/**
	 * 设置：创建者
	 *
	 * @param createrMid warehouse_class.creater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreaterMid(Long createrMid) {
		this.createrMid = createrMid;
	}

	/**
	 * 读取：修改者
	 *
	 * @return warehouse_class.updater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public Long getUpdaterMid() {
		return updaterMid;
	}

	/**
	 * 设置：修改者
	 *
	 * @param updaterMid warehouse_class.updater_mid
	 *
	 * @haoxz11MyBatis
	 */
	public void setUpdaterMid(Long updaterMid) {
		this.updaterMid = updaterMid;
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