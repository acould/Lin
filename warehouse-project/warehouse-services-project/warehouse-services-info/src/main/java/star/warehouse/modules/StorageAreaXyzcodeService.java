package star.warehouse.modules;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import star.bizbase.enums.OnOffDelEnums;
import star.bizbase.exception.BizRuleException;
import star.bizbase.util.BizRuleCheck;
import star.bizbase.util.constant.RedisCacheWaitTimeDMO;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.facade.warehouse.vo.StorageAreaXyzcodeVo;
import star.facade.warehouse.vo.dto.StorageAreaXyzcodeDTO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import star.vo.BaseVo;
import star.warehouse.enums.CacheWarehouseEnum;
import star.warehouse.mapper.StorageAreaXyzcodeMapper;
import star.warehouse.po.StorageAreaXyzcode;

@Service
public class StorageAreaXyzcodeService {
	private static final Logger logger = LoggerFactory.getLogger(StorageAreaXyzcodeService.class);
	@Resource
	private StorageAreaXyzcodeMapper storageAreaXyzcodeMapper;
	@Resource
	private CachesKeyService cachesKeyService;

	/**
	 * 插入仓库记录
	 * @param vo
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:22:55
	 */
	public Long insertStorageAreaXyzcodeVo(Long merchantId,StorageAreaXyzcodeVo vo) throws BizRuleException {
		BizRuleCheck.validateByUseridAnnotation(vo,"","");
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("insert 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		StorageAreaXyzcode po =vo.copyTo(StorageAreaXyzcode.class);
		storageAreaXyzcodeMapper.insertStorageAreaXyzcode(po);
		return po.getStorageAreaId();
	}

	
	/**
	 * 根据主键得到仓库表记录
	 * @param id
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:46:27
	 */
	public StorageAreaXyzcodeVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		if(id==null || id<0) {
			logger.error("getByPrimaryKey id is null");
			throw new BizRuleException("参数id不能为空");
		}
		StorageAreaXyzcode po = storageAreaXyzcodeMapper.getByPrimaryKey(id);
		if(po==null) return null;
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(po.getMerchantId())) {
			logger.error("getByPrimaryKey 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		return po.copyTo(StorageAreaXyzcodeVo.class);
	}


	/**
	 * 
	 * 更新仓库记录
	 * @param vo
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午4:52:08
	 */
	public Long updateStorageAreaXyzcode(Long merchantId,StorageAreaXyzcodeVo vo) throws BizRuleException {
		if(vo==null || vo.getStorageAreaId()==null || vo.getStorageAreaId().longValue()<0) {
			logger.error("update id is null");
			throw new BizRuleException("参数id不能为空");
		}
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("update 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		StorageAreaXyzcode po =vo.copyTo(StorageAreaXyzcode.class);
		storageAreaXyzcodeMapper.updateStorageAreaXyzcode(po);
		return vo.getStorageAreaId();
	}

	/**
	 * 搜索仓库列表，带分页
	 * @throws BizRuleException 
	 *
	 * @haoxz11MyBatis
	 */
	public List<StorageAreaXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap,int start,int size) throws BizRuleException{
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<StorageAreaXyzcode> retlist= storageAreaXyzcodeMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, StorageAreaXyzcodeVo.class);
	}

	/**
	 * 
	 * 搜索仓库列表
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:00:18
	 */
	public List<StorageAreaXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException{
		
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<StorageAreaXyzcode> retlist= storageAreaXyzcodeMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, StorageAreaXyzcodeVo.class);
		
	}

	/**
	 * 
	 *  得到搜索仓库的记录数量
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:25:17
	 */
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException {
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getCountByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		return storageAreaXyzcodeMapper.getCountByWhere(searchMap);
		
	}
	
	
	/**
	 * 删除仓库
	 * @param id
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:35:43
	 */
	public void deleteBy(Long merchantId,Long id) throws BizRuleException {
		if(id==null || id.longValue()<0 || merchantId==null || merchantId.longValue()<0) {
			logger.error("deleteBy merchantId id is null");
			throw new BizRuleException("参数id不能为空");
		}
		StorageAreaXyzcode po = storageAreaXyzcodeMapper.getByPrimaryKey(id);
		if(po==null || !merchantId.equals(po.getMerchantId())) {
			logger.error("deleteBy 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
//TODO XXH		po = new StorageAreaXyzcode();
//		po.setStorageAreaId(id);
//		po.setStatus(OnOffDelEnums.DEL.getType());//删除
//		storageAreaXyzcodeMapper.updateStorageAreaXyzcode(po);
	}
	
	/**
	 * 获取 cache 货币列表 all
	 * @return
	 * @throws BizRuleException 
	 * @author chistar
	 * @since  2018年11月3日 上午9:20:02
	 */
	public List<StorageAreaXyzcodeDTO> getCacheStorageAreaXyzcodeDTOList(Long merchantId) throws BizRuleException{
		if(merchantId==null || merchantId.longValue()<0) {
			logger.error("getCacheStorageAreaXyzcodeDTOList merchantId is null");
			throw new BizRuleException("参数merchantId不能为空");
		}
		return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_30M) {//30分钟
			@Override
			protected Object doGetList(BaseCacheEnum type, String key) {
				HashMap<String, Object> searchMap = new HashMap<>();
				searchMap.put("status", OnOffDelEnums.ON.getType());//在线使用的
				searchMap.put("merchantId",merchantId);
				List<StorageAreaXyzcode> retlist= storageAreaXyzcodeMapper.getListByWhere(searchMap);
				if(CollectionUtils.isEmpty(retlist)) {
					return Collections.emptyList();
				}
				return BaseVo.copyListTo(retlist, StorageAreaXyzcodeDTO.class);
			}
		}.getCache(CacheWarehouseEnum.STORAGEAREAXYZCODEDTO_LIST,String.valueOf(merchantId), RedisCacheWaitTimeDMO.REDIS_TIMEOUT_1S);
		
	
		
	}
	
}
