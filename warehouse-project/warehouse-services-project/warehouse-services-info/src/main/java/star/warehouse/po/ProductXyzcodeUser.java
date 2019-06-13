package star.warehouse.po;

import java.util.Date;

import star.vo.BaseVo;

/**
 * 
 * 广义仓库：商品与销售终端用户关联表
 * @author haoxz11MyBatis 
 * @created Sat Nov 03 17:16:58 CST 2018
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class ProductXyzcodeUser extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段:product_xyzcode_user.id
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
	 * 字段：商品ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long productId;

	/**
	 * 字段：商品skuID
	 *
	 * @haoxz11MyBatis
	 */
	private Long productSkuId;

	/**
	 * 字段：sku货品空间编码
	 *
	 * @haoxz11MyBatis
	 */
	private String productSkuXyzcode;

	/**
	 * 字段：会员用户ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long userId;

	/**
	 * 字段：收货地址ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long deliverId;

	/**
	 * 字段：收货地址
	 *
	 * @haoxz11MyBatis
	 */
	private String deliverAddress;

	/**
	 * 字段：联系人
	 *
	 * @haoxz11MyBatis
	 */
	private String userName;

	/**
	 * 字段：手机号码
	 *
	 * @haoxz11MyBatis
	 */
	private String mobilephone;

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
	 * 读取：product_xyzcode_user.id
	 *
	 * @return product_xyzcode_user.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：product_xyzcode_user.id
	 *
	 * @param id product_xyzcode_user.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：商家ID
	 *
	 * @return product_xyzcode_user.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：商家ID
	 *
	 * @param merchantId product_xyzcode_user.merchant_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 读取：商品ID
	 *
	 * @return product_xyzcode_user.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * 设置：商品ID
	 *
	 * @param productId product_xyzcode_user.product_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * 读取：商品skuID
	 *
	 * @return product_xyzcode_user.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProductSkuId() {
		return productSkuId;
	}

	/**
	 * 设置：商品skuID
	 *
	 * @param productSkuId product_xyzcode_user.product_sku_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	/**
	 * 读取：sku货品空间编码
	 *
	 * @return product_xyzcode_user.product_sku_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public String getProductSkuXyzcode() {
		return productSkuXyzcode;
	}

	/**
	 * 设置：sku货品空间编码
	 *
	 * @param productSkuXyzcode product_xyzcode_user.product_sku_xyzcode
	 *
	 * @haoxz11MyBatis
	 */
	public void setProductSkuXyzcode(String productSkuXyzcode) {
		this.productSkuXyzcode = productSkuXyzcode;
	}

	/**
	 * 读取：会员用户ID
	 *
	 * @return product_xyzcode_user.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置：会员用户ID
	 *
	 * @param userId product_xyzcode_user.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 读取：收货地址ID
	 *
	 * @return product_xyzcode_user.deliver_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getDeliverId() {
		return deliverId;
	}

	/**
	 * 设置：收货地址ID
	 *
	 * @param deliverId product_xyzcode_user.deliver_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setDeliverId(Long deliverId) {
		this.deliverId = deliverId;
	}

	/**
	 * 读取：收货地址
	 *
	 * @return product_xyzcode_user.deliver_address
	 *
	 * @haoxz11MyBatis
	 */
	public String getDeliverAddress() {
		return deliverAddress;
	}

	/**
	 * 设置：收货地址
	 *
	 * @param deliverAddress product_xyzcode_user.deliver_address
	 *
	 * @haoxz11MyBatis
	 */
	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}

	/**
	 * 读取：联系人
	 *
	 * @return product_xyzcode_user.user_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置：联系人
	 *
	 * @param userName product_xyzcode_user.user_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 读取：手机号码
	 *
	 * @return product_xyzcode_user.mobilephone
	 *
	 * @haoxz11MyBatis
	 */
	public String getMobilephone() {
		return mobilephone;
	}

	/**
	 * 设置：手机号码
	 *
	 * @param mobilephone product_xyzcode_user.mobilephone
	 *
	 * @haoxz11MyBatis
	 */
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return product_xyzcode_user.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime product_xyzcode_user.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return product_xyzcode_user.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime product_xyzcode_user.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}