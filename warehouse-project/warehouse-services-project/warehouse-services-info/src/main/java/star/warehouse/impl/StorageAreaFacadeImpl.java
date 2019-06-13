package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.StorageAreaFacade;
import star.facade.warehouse.vo.StorageAreaVo;
import star.facade.warehouse.vo.dto.StorageAreaDTO;
import star.vo.search.SearchResult;
import star.warehouse.modules.StorageAreaService;

public class StorageAreaFacadeImpl implements StorageAreaFacade{
	
	@Resource
	private StorageAreaService storageAreaService;

	@Override
	public Long insertStorageAreaVo(Long merchantId,StorageAreaVo vo) throws BizRuleException {
		return storageAreaService.insertStorageAreaVo(merchantId,vo);
	}

	@Override
	public StorageAreaVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return storageAreaService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateStorageArea(Long merchantId,StorageAreaVo vo) throws BizRuleException {
		return storageAreaService.updateStorageArea(merchantId,vo);
	}

	@Override
	public List<StorageAreaVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return storageAreaService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<StorageAreaVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storageAreaService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storageAreaService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 storageAreaService.deleteBy(merchantId,id);
	}

	@Override
	public List<StorageAreaDTO> getCacheStorageAreaDTOList(Long merchantId) throws BizRuleException {
		return storageAreaService.getCacheStorageAreaDTOList(merchantId);
	}
	@Override
	public SearchResult<StorageAreaVo> getListTotalByWhere(Long merchantId,Map<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		long total = storageAreaService.getCountByWhere(merchantId,(HashMap<String, Object>)searchMap);
		SearchResult<StorageAreaVo> result = SearchResult.newResult(total);
		if(total>0) {
			result.setList(storageAreaService.getListByWhere(merchantId,(HashMap<String, Object>)searchMap, start, size));
		}
		
		return result;
	}

}
