package star.warehouse.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.StorageAreaXyzcodeFacade;
import star.facade.warehouse.vo.StorageAreaXyzcodeVo;
import star.facade.warehouse.vo.dto.StorageAreaXyzcodeDTO;
import star.warehouse.modules.StorageAreaXyzcodeService;

public class StorageAreaXyzcodeFacadeImpl implements StorageAreaXyzcodeFacade{
	
	@Resource
	private StorageAreaXyzcodeService storageAreaXyzcodeService;

	@Override
	public Long insertStorageAreaXyzcodeVo(Long merchantId,StorageAreaXyzcodeVo vo) throws BizRuleException {
		return storageAreaXyzcodeService.insertStorageAreaXyzcodeVo(merchantId,vo);
	}

	@Override
	public StorageAreaXyzcodeVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		return storageAreaXyzcodeService.getByPrimaryKey(merchantId,id);
	}

	@Override
	public Long updateStorageAreaXyzcode(Long merchantId,StorageAreaXyzcodeVo vo) throws BizRuleException {
		return storageAreaXyzcodeService.updateStorageAreaXyzcode(merchantId,vo);
	}

	@Override
	public List<StorageAreaXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap, int start, int size)
			throws BizRuleException {
		return storageAreaXyzcodeService.getListByWhere(merchantId,searchMap, start, size);
	}

	@Override
	public List<StorageAreaXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storageAreaXyzcodeService.getListByWhere(merchantId,searchMap);
	}

	@Override
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		return storageAreaXyzcodeService.getCountByWhere(merchantId,searchMap);
	}

	@Override
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		 storageAreaXyzcodeService.deleteBy(merchantId,id);
	}

	@Override
	public List<StorageAreaXyzcodeDTO> getCacheStorageAreaXyzcodeDTOList(Long merchantId) throws BizRuleException {
		return storageAreaXyzcodeService.getCacheStorageAreaXyzcodeDTOList(merchantId);
	}


}
