package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.StoreInFacade;
import star.facade.warehouse.vo.StoreInVo;
import star.vo.search.SearchResult;
import star.warehouse.modules.StoreInService;

public class StoreInFacadeImpl implements StoreInFacade{
	
	@Resource
	private StoreInService storeInService;

	@Override
	public Long insertStoreInVo(Long merchantId,StoreInVo vo) throws BizRuleException {
		return storeInService.insertStoreInVo(merchantId,vo);
	}

	@Override
	public StoreInVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return storeInService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateStoreIn(Long merchantId,StoreInVo vo) throws BizRuleException {
		return storeInService.updateStoreIn(merchantId,vo);
	}

	@Override
	public List<StoreInVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return storeInService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<StoreInVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storeInService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storeInService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 storeInService.deleteBy(merchantId,id);
	}

	@Override
	public SearchResult<StoreInVo> getListTotalByWhere(Long merchantId, Map<String, Object> searchMap, int start,
			int size) throws BizRuleException {
		long total = storeInService.getCountByWhere(merchantId,(HashMap<String, Object>)searchMap);
		SearchResult<StoreInVo> result = SearchResult.newResult(total);
		if(total>0) {
			result.setList(storeInService.getListByWhere(merchantId,(HashMap<String, Object>)searchMap, start, size));
		}
		
		return result;
	}



}
