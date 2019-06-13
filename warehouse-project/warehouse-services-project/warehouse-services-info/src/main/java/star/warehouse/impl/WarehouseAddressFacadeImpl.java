package star.warehouse.impl;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.WarehouseAddressFacade;
import star.facade.warehouse.vo.WarehouseAddressVo;
import star.warehouse.modules.WarehouseAddressService;

public class WarehouseAddressFacadeImpl implements WarehouseAddressFacade {
	@Resource
	private WarehouseAddressService warehouseAddressService;
	@Override
	public WarehouseAddressVo getByPrimaryKey(Long merchantId, Long warehouseId) throws BizRuleException {
		return warehouseAddressService.getByPrimaryKey(merchantId, warehouseId);
	}
	@Override
	public Long insertWarehouseAddressVo(Long merchantId, WarehouseAddressVo vo) throws BizRuleException {
		
		return warehouseAddressService.insertWarehouseAddressVo(merchantId, vo);
	}
	@Override
	public Long updateWarehouseAddress(Long merchantId, WarehouseAddressVo vo) throws BizRuleException {
		return warehouseAddressService.updateWarehouseAddress(merchantId, vo);
	}
	@Override
	public void deleteBy(Long merchantId,Long warehouseId) throws BizRuleException {
		 warehouseAddressService.deleteBy(merchantId,warehouseId);
	}
	

}
