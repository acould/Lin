package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.StoreOutFacade;
import star.facade.warehouse.vo.StoreOutVo;
import star.vo.search.SearchResult;
import star.warehouse.modules.StoreOutService;

public class StoreOutFacadeImpl implements StoreOutFacade{
	
	@Resource
	private StoreOutService storeOutService;

	@Override
	public Long insertStoreOutVo(Long merchantId,StoreOutVo vo) throws BizRuleException {
		return storeOutService.insertStoreOutVo(merchantId,vo);
	}

	@Override
	public StoreOutVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return storeOutService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateStoreOut(Long merchantId,StoreOutVo vo) throws BizRuleException {
		return storeOutService.updateStoreOut(merchantId,vo);
	}

	@Override
	public List<StoreOutVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return storeOutService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<StoreOutVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storeOutService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storeOutService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 storeOutService.deleteBy(merchantId,id);
	}

	@Override
	public SearchResult<StoreOutVo> getListTotalByWhere(Long merchantId, Map<String, Object> searchMap, int start,
			int size) throws BizRuleException {
		long total = storeOutService.getCountByWhere(merchantId,(HashMap<String, Object>)searchMap);
		SearchResult<StoreOutVo> result = SearchResult.newResult(total);
		if(total>0) {
			result.setList(storeOutService.getListByWhere(merchantId,(HashMap<String, Object>)searchMap, start, size));
		}
		
		return result;
	}



}
