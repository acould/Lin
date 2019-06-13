package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.WarehouseXyzcodeFacade;
import star.facade.warehouse.vo.WarehouseXyzcodeVo;
import star.warehouse.modules.WarehouseXyzcodeService;

public class WarehouseXyzcodeFacadeImpl implements WarehouseXyzcodeFacade{
	
	@Resource
	private WarehouseXyzcodeService warehouseXyzcodeService;

	@Override
	public Long insertWarehouseXyzcodeVo(Long merchantId,WarehouseXyzcodeVo vo) throws BizRuleException {
		return warehouseXyzcodeService.insertWarehouseXyzcodeVo(merchantId,vo);
	}

	@Override
	public WarehouseXyzcodeVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return warehouseXyzcodeService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateWarehouseXyzcode(Long merchantId,WarehouseXyzcodeVo vo) throws BizRuleException {
		return warehouseXyzcodeService.updateWarehouseXyzcode(merchantId,vo);
	}

	@Override
	public List<WarehouseXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return warehouseXyzcodeService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<WarehouseXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseXyzcodeService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseXyzcodeService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 warehouseXyzcodeService.deleteBy(merchantId,id);
	}



}
