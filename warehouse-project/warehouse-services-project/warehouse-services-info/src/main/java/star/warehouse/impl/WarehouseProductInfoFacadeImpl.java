package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.WarehouseProductInfoFacade;
import star.facade.warehouse.vo.WarehouseProductInfoVo;
import star.facade.warehouse.vo.dto.WarehouseProductInfoDTO;
import star.warehouse.modules.WarehouseProductInfoService;

public class WarehouseProductInfoFacadeImpl implements WarehouseProductInfoFacade{
	
	@Resource
	private WarehouseProductInfoService warehouseProductInfoService;

	@Override
	public Long insertWarehouseProductInfoVo(Long merchantId,WarehouseProductInfoVo vo) throws BizRuleException {
		return warehouseProductInfoService.insertWarehouseProductInfoVo(merchantId,vo);
	}

	@Override
	public WarehouseProductInfoVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return warehouseProductInfoService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateWarehouseProductInfo(Long merchantId,WarehouseProductInfoVo vo) throws BizRuleException {
		return warehouseProductInfoService.updateWarehouseProductInfo(merchantId,vo);
	}

	@Override
	public List<WarehouseProductInfoVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return warehouseProductInfoService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<WarehouseProductInfoVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseProductInfoService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseProductInfoService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 warehouseProductInfoService.deleteBy(merchantId,id);
	}

	@Override
	public List<WarehouseProductInfoDTO> getCacheWarehouseProductInfoDTOList(Long merchantId) throws BizRuleException {
		return warehouseProductInfoService.getCacheWarehouseProductInfoDTOList(merchantId);
	}


}
