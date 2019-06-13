package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.WarehouseClassFacade;
import star.facade.warehouse.vo.WarehouseClassVo;
import star.facade.warehouse.vo.dto.WarehouseClassDTO;
import star.vo.search.SearchResult;
import star.warehouse.modules.WarehouseClassService;

public class WarehouseClassFacadeImpl implements WarehouseClassFacade{
	
	@Resource
	private WarehouseClassService warehouseClassService;

	@Override
	public Long insertWarehouseClassVo(Long merchantId,WarehouseClassVo vo) throws BizRuleException {
		return warehouseClassService.insertWarehouseClassVo(merchantId,vo);
	}

	@Override
	public WarehouseClassVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return warehouseClassService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateWarehouseClass(Long merchantId,WarehouseClassVo vo) throws BizRuleException {
		return warehouseClassService.updateWarehouseClass(merchantId,vo);
	}

	@Override
	public List<WarehouseClassVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return warehouseClassService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<WarehouseClassVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseClassService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseClassService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 warehouseClassService.deleteBy(merchantId,id);
	}

	@Override
	public List<WarehouseClassDTO> getCacheWarehouseClassDTOList(Long merchantId) throws BizRuleException {
		return warehouseClassService.getCacheWarehouseClassDTOList(merchantId);
	}
	@Override
	public SearchResult<WarehouseClassVo> getListTotalByWhere(Long merchantId,Map<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		long total = warehouseClassService.getCountByWhere(merchantId,(HashMap<String, Object>)searchMap);
		SearchResult<WarehouseClassVo> result = SearchResult.newResult(total);
		if(total>0) {
			result.setList(warehouseClassService.getListByWhere(merchantId,(HashMap<String, Object>)searchMap, start, size));
		}
		
		return result;
	}

}
