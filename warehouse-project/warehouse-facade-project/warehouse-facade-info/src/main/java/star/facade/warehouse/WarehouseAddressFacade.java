package star.facade.warehouse;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.vo.WarehouseAddressVo;


public interface WarehouseAddressFacade {
	
	/**
	 * 根据id获取仓库地址信息
	 * @since：2019年6月8日下午4:35:58
	 * @user： chenhang
	 * @param merchantId
	 * @param id
	 * @return
	 * @throws BizRuleException
	 */
	public WarehouseAddressVo getByPrimaryKey(Long merchantId,Long warehouseId) throws BizRuleException;
	
	
	/**
	 * 插入仓库位置(地址)信息记录
	 * @since：2019年6月8日下午9:31:40
	 * @user： chenhang
	 * @param merchantId
	 * @param vo
	 * @return
	 * @throws BizRuleException
	 */
	public Long insertWarehouseAddressVo(Long merchantId,WarehouseAddressVo vo) throws BizRuleException;


	/**
	 * 更新仓库位置(地址)信息记录
	 * @since：2019年6月8日下午9:43:51
	 * @user： chenhang
	 * @param merchantId
	 * @param warehouseAddressVo
	 * @return
	 * @throws BizRuleException
	 */
	public Long updateWarehouseAddress(Long merchantId, WarehouseAddressVo vo)throws BizRuleException ;
	
	/**
	 * 根据 warehouseId 删除仓库位置(地址)信息
	 * @since：2019年6月8日下午10:10:02
	 * @user： chenhang
	 * @param merchantId
	 * @param id
	 * @throws BizRuleException
	 */
	public void deleteBy(Long merchantId,Long warehouseId) throws BizRuleException;
}
