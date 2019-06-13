package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.StoreInOutCheckFacade;
import star.facade.warehouse.vo.StoreInCheckVo;
import star.warehouse.modules.StoreInOutCheckService;

public class StoreInOutCheckFacadeImpl implements StoreInOutCheckFacade{
	
	@Resource
	private StoreInOutCheckService storeInOutCheckService;

	@Override
	public Long insertStoreInCheckVo(Long merchantId,StoreInCheckVo vo) throws BizRuleException {
		return storeInOutCheckService.insertStoreInCheckVo(merchantId,vo);
	}

	@Override
	public StoreInCheckVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return storeInOutCheckService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateStoreInCheck(Long merchantId,StoreInCheckVo vo) throws BizRuleException {
		return storeInOutCheckService.updateStoreInCheck(merchantId,vo);
	}

	@Override
	public List<StoreInCheckVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return storeInOutCheckService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<StoreInCheckVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storeInOutCheckService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storeInOutCheckService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 storeInOutCheckService.deleteBy(merchantId,id);
	}



}
