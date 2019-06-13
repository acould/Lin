package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.WarehouseFacade;
import star.facade.warehouse.vo.WarehouseAndAddressVo;
import star.facade.warehouse.vo.WarehouseVo;
import star.facade.warehouse.vo.dto.WarehouseDTO;
import star.vo.search.SearchResult;
import star.warehouse.modules.WarehouseService;

public class WarehouseFacadeImpl implements WarehouseFacade{
	
	@Resource
	private WarehouseService warehouseService;

	@Override
	public Long insertWarehouseVo(Long merchantId,WarehouseVo vo) throws BizRuleException {
		return warehouseService.insertWarehouseVo(merchantId,vo);
	}
	
	@Override
	public Long saveWarehouseAndAddressVo(Long merchantId,WarehouseAndAddressVo vo) throws BizRuleException{
		return warehouseService.saveWarehouseAndAddressVo(merchantId, vo);
	}

	@Override
	public WarehouseVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return warehouseService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateWarehouse(Long merchantId,WarehouseVo vo) throws BizRuleException {
		return warehouseService.updateWarehouse(merchantId,vo);
	}

	@Override
	public List<WarehouseVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return warehouseService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<WarehouseVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 warehouseService.deleteBy(merchantId,id);
	}

	@Override
	public List<WarehouseDTO> getCacheWarehouseDTOList(Long merchantId) throws BizRuleException {
		return warehouseService.getCacheWarehouseDTOList(merchantId);
	}
	@Override
	public SearchResult<WarehouseVo> getListTotalByWhere(Long merchantId,Map<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		long total = warehouseService.getCountByWhere(merchantId,(HashMap<String, Object>)searchMap);
		SearchResult<WarehouseVo> result = SearchResult.newResult(total);
		if(total>0) {
			result.setList(warehouseService.getListByWhere(merchantId,(HashMap<String, Object>)searchMap, start, size));
		}
		
		return result;
	}

	@Override
	public void deleteWarehouseAndAddressBy(Long merchantId, Long id)throws BizRuleException {
		warehouseService.deleteWarehouseAndAddressBy(merchantId, id);
		
	}

}
