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
import star.facade.warehouse.vo.StoreInCheckVo;
import star.modules.cache.CachesKeyService;
import star.vo.BaseVo;
import star.warehouse.mapper.StoreInCheckMapper;
import star.warehouse.po.StoreInCheck;

@Service
public class StoreInOutCheckService {
	private static final Logger logger = LoggerFactory.getLogger(StoreInOutCheckService.class);
	@Resource
	private StoreInCheckMapper storeInCheckMapper;
	@Resource
	private CachesKeyService cachesKeyService;

	/**
	 * 插入入库审核记录
	 * @param vo
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:22:55
	 */
	public Long insertStoreInCheckVo(Long merchantId,StoreInCheckVo vo) throws BizRuleException {
		BizRuleCheck.validateByUseridAnnotation(vo,"","");
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("insert 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		StoreInCheck po =vo.copyTo(StoreInCheck.class);
		storeInCheckMapper.insertStoreInCheck(po);
		return po.getId();
	}

	
	/**
	 * 根据主键得到入库审核表记录
	 * @param id
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:46:27
	 */
	public StoreInCheckVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		if(id==null || id<0) {
			logger.error("getByPrimaryKey id is null");
			throw new BizRuleException("参数id不能为空");
		}
		StoreInCheck po = storeInCheckMapper.getByPrimaryKey(id);
		if(po==null) return null;
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(po.getMerchantId())) {
			logger.error("getByPrimaryKey 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		return po.copyTo(StoreInCheckVo.class);
	}


	/**
	 * 
	 * 更新入库审核记录
	 * @param vo
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午4:52:08
	 */
	public Long updateStoreInCheck(Long merchantId,StoreInCheckVo vo) throws BizRuleException {
		if(vo==null || vo.getId()==null || vo.getId().longValue()<0) {
			logger.error("update id is null");
			throw new BizRuleException("参数id不能为空");
		}
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("update 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		StoreInCheck po =vo.copyTo(StoreInCheck.class);
		storeInCheckMapper.updateStoreInCheck(po);
		return vo.getId();
	}

	/**
	 * 搜索入库审核列表，带分页
	 * @throws BizRuleException 
	 *
	 * @haoxz11MyBatis
	 */
	public List<StoreInCheckVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap,int start,int size) throws BizRuleException{
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<StoreInCheck> retlist= storeInCheckMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, StoreInCheckVo.class);
	}

	/**
	 * 
	 * 搜索入库审核列表
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:00:18
	 */
	public List<StoreInCheckVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException{
		
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<StoreInCheck> retlist= storeInCheckMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, StoreInCheckVo.class);
		
	}

	/**
	 * 
	 *  得到搜索入库审核的记录数量
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
		return storeInCheckMapper.getCountByWhere(searchMap);
		
	}
	
	
	/**
	 * 删除入库审核
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
		StoreInCheck po = storeInCheckMapper.getByPrimaryKey(id);
		if(po==null || !merchantId.equals(po.getMerchantId())) {
			logger.error("deleteBy 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
//		po = new StoreInCheck();
//		po.setId(id);
//		po.setStatus(OnOffDelEnums.DEL.getType());//删除
//		storeInCheckMapper.updateStoreInCheck(po);
	}
	
	
	
}
