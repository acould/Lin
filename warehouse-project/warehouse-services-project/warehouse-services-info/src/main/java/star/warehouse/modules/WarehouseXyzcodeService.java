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
import star.facade.warehouse.vo.WarehouseXyzcodeVo;
import star.modules.cache.CachesKeyService;
import star.vo.BaseVo;
import star.warehouse.mapper.WarehouseXyzcodeMapper;
import star.warehouse.po.WarehouseXyzcode;

@Service
public class WarehouseXyzcodeService {
	private static final Logger logger = LoggerFactory.getLogger(WarehouseXyzcodeService.class);
	@Resource
	private WarehouseXyzcodeMapper currencyTypeMapper;
	@Resource
	private CachesKeyService cachesKeyService;

	/**
	 * 插入仓库xyz记录
	 * @param vo
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:22:55
	 */
	public Long insertWarehouseXyzcodeVo(Long merchantId,WarehouseXyzcodeVo vo) throws BizRuleException {
		BizRuleCheck.validateByUseridAnnotation(vo,"","");
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("insert 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		WarehouseXyzcode po =vo.copyTo(WarehouseXyzcode.class);
		currencyTypeMapper.insertWarehouseXyzcode(po);
		return po.getWarehouseId();
	}

	
	/**
	 * 根据主键得到仓库xyz表记录
	 * @param id
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:46:27
	 */
	public WarehouseXyzcodeVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		if(id==null || id<0) {
			logger.error("getByPrimaryKey id is null");
			throw new BizRuleException("参数id不能为空");
		}
		WarehouseXyzcode po = currencyTypeMapper.getByPrimaryKey(id);
		if(po==null) return null;
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(po.getMerchantId())) {
			logger.error("getByPrimaryKey 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		return po.copyTo(WarehouseXyzcodeVo.class);
	}


	/**
	 * 
	 * 更新仓库xyz记录
	 * @param vo
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午4:52:08
	 */
	public Long updateWarehouseXyzcode(Long merchantId,WarehouseXyzcodeVo vo) throws BizRuleException {
		if(vo==null || vo.getWarehouseId()==null || vo.getWarehouseId().longValue()<0) {
			logger.error("update id is null");
			throw new BizRuleException("参数id不能为空");
		}
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("update 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		WarehouseXyzcode po =vo.copyTo(WarehouseXyzcode.class);
		currencyTypeMapper.updateWarehouseXyzcode(po);
		return vo.getWarehouseId();
	}

	/**
	 * 搜索仓库xyz列表，带分页
	 * @throws BizRuleException 
	 *
	 * @haoxz11MyBatis
	 */
	public List<WarehouseXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap,int start,int size) throws BizRuleException{
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<WarehouseXyzcode> retlist= currencyTypeMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, WarehouseXyzcodeVo.class);
	}

	/**
	 * 
	 * 搜索仓库xyz列表
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:00:18
	 */
	public List<WarehouseXyzcodeVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException{
		
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<WarehouseXyzcode> retlist= currencyTypeMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, WarehouseXyzcodeVo.class);
		
	}

	/**
	 * 
	 *  得到搜索仓库xyz的记录数量
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
		return currencyTypeMapper.getCountByWhere(searchMap);
		
	}
	
	
	/**
	 * 删除仓库xyz
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
		WarehouseXyzcode po = currencyTypeMapper.getByPrimaryKey(id);
		if(po==null || !merchantId.equals(po.getMerchantId())) {
			logger.error("deleteBy 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
//		po = new WarehouseXyzcode();
//		po.setId(id);
//		po.setStatus(OnOffDelEnums.DEL.getType());//删除
//		currencyTypeMapper.updateWarehouseXyzcode(po);
	}
	
	
	
}
