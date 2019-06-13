package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.ProductXyzcodeStorageFacade;
import star.facade.warehouse.vo.ProductXyzcodeStorageVo;
import star.facade.warehouse.vo.dto.ProductXyzcodeStorageDTO;
import star.vo.search.SearchResult;
import star.warehouse.modules.ProductXyzcodeStorageService;

public class ProductXyzcodeStorageFacadeImpl implements ProductXyzcodeStorageFacade{
	
	@Resource
	private ProductXyzcodeStorageService productXyzcodeStorageService;

	@Override
	public Long insertProductXyzcodeStorageVo(Long merchantId,ProductXyzcodeStorageVo vo) throws BizRuleException {
		return productXyzcodeStorageService.insertProductXyzcodeStorageVo(merchantId,vo);
	}

	@Override
	public ProductXyzcodeStorageVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return productXyzcodeStorageService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateProductXyzcodeStorage(Long merchantId,ProductXyzcodeStorageVo vo) throws BizRuleException {
		return productXyzcodeStorageService.updateProductXyzcodeStorage(merchantId,vo);
	}

	@Override
	public List<ProductXyzcodeStorageVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return productXyzcodeStorageService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<ProductXyzcodeStorageVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return productXyzcodeStorageService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return productXyzcodeStorageService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 productXyzcodeStorageService.deleteBy(merchantId,id);
	}

	@Override
	public List<ProductXyzcodeStorageDTO> getCacheProductXyzcodeStorageDTOList(Long merchantId) throws BizRuleException {
		return productXyzcodeStorageService.getCacheProductXyzcodeStorageDTOList(merchantId);
	}

	@Override
	public SearchResult<ProductXyzcodeStorageVo> getListTotalByWhere(Long merchantId, Map<String, Object> searchMap,
			int start, int size) throws BizRuleException {
		long total = productXyzcodeStorageService.getCountByWhere(merchantId,(HashMap<String, Object>)searchMap);
		SearchResult<ProductXyzcodeStorageVo> result = SearchResult.newResult(total);
		if(total>0) {
			result.setList(productXyzcodeStorageService.getListByWhere(merchantId,(HashMap<String, Object>)searchMap, start, size));
		}
		
		return result;
	}


}
