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

import star.bizbase.exception.BizRuleException;
import star.bizbase.util.BizRuleCheck;
import star.facade.warehouse.vo.StoreInCheckXyzcodeVo;
import star.modules.cache.CachesKeyService;
import star.vo.BaseVo;
import star.warehouse.mapper.StoreInCheckXyzcodeMapper;
import star.warehouse.po.StoreInCheckXyzcode;

@Service
public class StoreInCheckXyzcodeService {
	private static final Logger logger = LoggerFactory.getLogger(StoreInCheckXyzcodeService.class);
	@Resource
	private StoreInCheckXyzcodeMapper storeInCheckXyzcodeMapper;
	@Resource
	private CachesKeyService cachesKeyService;

	/**
	 * 插入入库xyz记录
	 * @param vo
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:22:55
	 */
	public Long insertStoreInCheckXyzcodeVo(Long merchantId,StoreInCheckXyzcodeVo vo) throws BizRuleException {
		BizRuleCheck.validateByUseridAnnotation(vo,"","");
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("insert 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		StoreInCheckXyzcode po =vo.copyTo(StoreInCheckXyzcode.class);
		storeInCheckXyzcodeMapper.insertStoreInCheckXyzcode(po);
		return po.getId();
	}

	
	/**
	 * 根据主键得到入库xyz表记录
	 * @param id
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:46:27
	 */
	public StoreInCheckXyzcodeVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		if(id==null || id<0) {
			logger.error("getByPrimaryKey id is null");
			throw new BizRuleException("参数id不能为空");
		}
		StoreInCheckXyzcode po = storeInCheckXyzcodeMapper.getByPrimaryKey(id);
		if(po==null) return null;
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(po.getMerchantId())) {
			logger.error("getByPrimaryKey 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		return po.copyTo(StoreInCheckXyzcodeVo.class);
	}


	/**
	 * 
	 * 更新入库xyz记录
	 * @param vo
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午4:52:08
	 */
	public Long updateStoreInCheckXyzcode(Long merchantId,StoreInCheckXyzcodeVo vo) throws BizRuleException {
		if(vo==null || vo.getId()==null || vo.getId().longValue()<0) {
			logger.error("update id is null");
			throw new BizRuleException("参数id不能为空");
		}
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("update 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		StoreInCheckXyzcode po =vo.copyTo(StoreInCheckXyzcode.class);
		storeInCheckXyzcodeMapper.updateStoreInCheckXyzcode(po);
		return vo.getId();
	}

	/**
	 * 搜索入库xyz列表，带分页
	 * @throws BizRuleException 
	 *
	 * @haoxz11MyBatis
	 */
	public List<StoreInCheckXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap,int start,int size) throws BizRuleException{
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<StoreInCheckXyzcode> retlist= storeInCheckXyzcodeMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, StoreInCheckXyzcodeVo.class);
	}

	/**
	 * 
	 * 搜索入库xyz列表
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:00:18
	 */
	public List<StoreInCheckXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException{
		
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<StoreInCheckXyzcode> retlist= storeInCheckXyzcodeMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, StoreInCheckXyzcodeVo.class);
		
	}

	/**
	 * 
	 *  得到搜索入库xyz的记录数量
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
		return storeInCheckXyzcodeMapper.getCountByWhere(searchMap);
		
	}
	
	
	/**
	 * 删除入库xyz
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
		StoreInCheckXyzcode po = storeInCheckXyzcodeMapper.getByPrimaryKey(id);
		if(po==null || !merchantId.equals(po.getMerchantId())) {
			logger.error("deleteBy 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
//		po = new StoreInCheckXyzcode();
//		po.setId(id);
//		po.setStatus(OnOffDelEnums.DEL.getType());//删除
//		storeInCheckXyzcodeMapper.updateStoreInCheckXyzcode(po);
	}
	
	
	
}
