package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.WarehouseProductWarnFacade;
import star.facade.warehouse.vo.WarehouseProductWarnInfoVo;
import star.facade.warehouse.vo.dto.WarehouseProductWarnInfoDTO;
import star.warehouse.modules.WarehouseProductWarnService;

public class WarehouseProductWarnFacadeImpl implements WarehouseProductWarnFacade{
	
	@Resource
	private WarehouseProductWarnService warehouseProductWarnService;

	@Override
	public Long insertWarehouseProductWarnInfoVo(Long merchantId,WarehouseProductWarnInfoVo vo) throws BizRuleException {
		return warehouseProductWarnService.insertWarehouseProductWarnInfoVo(merchantId,vo);
	}

	@Override
	public WarehouseProductWarnInfoVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return warehouseProductWarnService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateWarehouseProductWarnInfo(Long merchantId,WarehouseProductWarnInfoVo vo) throws BizRuleException {
		return warehouseProductWarnService.updateWarehouseProductWarnInfo(merchantId,vo);
	}

	@Override
	public List<WarehouseProductWarnInfoVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return warehouseProductWarnService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<WarehouseProductWarnInfoVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseProductWarnService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return warehouseProductWarnService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 warehouseProductWarnService.deleteBy(merchantId,id);
	}

	@Override
	public List<WarehouseProductWarnInfoDTO> getCacheWarehouseProductWarnInfoDTOList(Long merchantId) throws BizRuleException {
		return warehouseProductWarnService.getCacheWarehouseProductWarnInfoDTOList(merchantId);
	}


}
