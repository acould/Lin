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
import org.springframework.transaction.annotation.Transactional;

import star.bizbase.enums.OnOffDelEnums;
import star.bizbase.exception.BizRuleException;
import star.bizbase.util.BizRuleCheck;
import star.bizbase.util.constant.RedisCacheWaitTimeDMO;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.facade.warehouse.vo.WarehouseAddressVo;
import star.facade.warehouse.vo.WarehouseAndAddressVo;
import star.facade.warehouse.vo.WarehouseVo;
import star.facade.warehouse.vo.dto.WarehouseDTO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import star.vo.BaseVo;
import star.warehouse.ass.WarehouseAndAddressUtil;
import star.warehouse.enums.CacheWarehouseEnum;
import star.warehouse.mapper.WarehouseAddressMapper;
import star.warehouse.mapper.WarehouseMapper;
import star.warehouse.po.Warehouse;
import star.warehouse.po.WarehouseAddress;

@Service
public class WarehouseService {
	private static final Logger logger = LoggerFactory.getLogger(WarehouseService.class);
	@Resource
	private WarehouseMapper warehouseMapper;
	@Resource
	private WarehouseAddressMapper warehouseAddressMapper;
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
	public Long insertWarehouseVo(Long merchantId,WarehouseVo vo) throws BizRuleException {
		BizRuleCheck.validateByUseridAnnotation(vo,"","");
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("insert 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		Warehouse po =vo.copyTo(Warehouse.class);
		warehouseMapper.insertWarehouse(po);
		return po.getId();
	}
	
	/**
	 * 新增仓库基本信息和地址信息
	 * @param merchantId
	 * @param vo
	 * @return
	 * @throws BizRuleException
	 * @Author:chistar
	 * @Since : 2019年6月9日上午11:24:34
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long saveWarehouseAndAddressVo(Long merchantId,WarehouseAndAddressVo dataVo) throws BizRuleException{
		BizRuleCheck.validateByUseridAnnotation(dataVo,"","");
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(dataVo.getMerchantId())) {
			logger.error("insert 异常，vo={}不属于merchantid={}",dataVo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		WarehouseVo warehousevo = WarehouseAndAddressUtil.separateToWarehouseVo(dataVo);
		WarehouseAddressVo warehouseAddressVo = WarehouseAndAddressUtil.separateToWarehouseAddressVo(dataVo);
		BizRuleCheck.validateByUseridAnnotation(warehousevo,"","");
		//将下列的逻辑放在service里,重新建一个方法,获取方法的返回值给model	
		if(warehouseAddressVo==null) {
			warehouseAddressVo = new WarehouseAddressVo();
		}
		Long dataid =null;
		if(dataVo.getId() == null){//新增
			Warehouse warehousepo =warehousevo.copyTo(Warehouse.class);
			dataid = (long) warehouseMapper.insertWarehouse(warehousepo);
			warehouseAddressVo.setWarehouseId(dataid);
			WarehouseAddress addresspo = warehouseAddressVo.copyTo(WarehouseAddress.class);
			warehouseAddressMapper.insertWarehouseAddress(addresspo);
		}else{//修改
			Warehouse warehousepo =warehousevo.copyTo(Warehouse.class);
			warehouseMapper.updateWarehouse(warehousepo);
			dataid = warehousepo.getId();
			WarehouseAddress addresspo = warehouseAddressVo.copyTo(WarehouseAddress.class);
			WarehouseAddress addexistpo = warehouseAddressMapper.getByPrimaryKey(warehousepo.getId());
			if(addexistpo!=null) {
				warehouseAddressMapper.updateWarehouseAddress(addresspo);
			}else {
				addresspo.setWarehouseId(warehousepo.getId());
				warehouseAddressMapper.insertWarehouseAddress(addresspo);
			}
			
		}	
		
		return dataid;
	}

	
	/**
	 * 根据主键得到仓库表记录
	 * @param id
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:46:27
	 */
	public WarehouseVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException {
		if(id==null || id<0) {
			logger.error("getByPrimaryKey id is null");
			throw new BizRuleException("参数id不能为空");
		}
		Warehouse po = warehouseMapper.getByPrimaryKey(id);
		if(po==null) return null;
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(po.getMerchantId())) {
			logger.error("getByPrimaryKey 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		return po.copyTo(WarehouseVo.class);
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
	public Long updateWarehouse(Long merchantId,WarehouseVo vo) throws BizRuleException {
		if(vo==null || vo.getId()==null || vo.getId().longValue()<0) {
			logger.error("update id is null");
			throw new BizRuleException("参数id不能为空");
		}
		if(merchantId==null || merchantId.longValue()<0 || !merchantId.equals(vo.getMerchantId())) {
			logger.error("update 异常，vo={}不属于merchantid={}",vo,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		Warehouse po =vo.copyTo(Warehouse.class);
		warehouseMapper.updateWarehouse(po);
		return vo.getId();
	}

	/**
	 * 搜索仓库列表，带分页
	 * @throws BizRuleException 
	 *
	 * @haoxz11MyBatis
	 */
	public List<WarehouseVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap,int start,int size) throws BizRuleException{
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<Warehouse> retlist= warehouseMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, WarehouseVo.class);
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
	public List<WarehouseVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException{
		
		if(searchMap==null || searchMap.isEmpty()) {
			logger.error("getListByWhere searchMap is empty");
			throw new BizRuleException("参数map不能为空");
		}
		searchMap.put("merchantId", merchantId);
		List<Warehouse> retlist= warehouseMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(retlist)) {
			return Collections.emptyList();
		}
		return BaseVo.copyListTo(retlist, WarehouseVo.class);
		
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
		return warehouseMapper.getCountByWhere(searchMap);
		
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
		Warehouse po = warehouseMapper.getByPrimaryKey(id);
		if(po==null || !merchantId.equals(po.getMerchantId())) {
			logger.error("deleteBy 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		po = new Warehouse();
		po.setId(id);
		po.setStatus(OnOffDelEnums.DEL.getType());//删除
		warehouseMapper.updateWarehouse(po);
	}
	
	/**
	 * 获取 cache 货币列表 all
	 * @return
	 * @throws BizRuleException 
	 * @author chistar
	 * @since  2018年11月3日 上午9:20:02
	 */
	public List<WarehouseDTO> getCacheWarehouseDTOList(Long merchantId) throws BizRuleException{
		if(merchantId==null || merchantId.longValue()<0) {
			logger.error("getCacheWarehouseDTOList merchantId is null");
			throw new BizRuleException("参数merchantId不能为空");
		}
		return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_30M) {//30分钟
			@Override
			protected Object doGetList(BaseCacheEnum type, String key) {
				HashMap<String, Object> searchMap = new HashMap<>();
				searchMap.put("status", OnOffDelEnums.ON.getType());//在线使用的
				searchMap.put("merchantId",merchantId);
				List<Warehouse> retlist= warehouseMapper.getListByWhere(searchMap);
				if(CollectionUtils.isEmpty(retlist)) {
					return Collections.emptyList();
				}
				return BaseVo.copyListTo(retlist, WarehouseDTO.class);
			}
		}.getCache(CacheWarehouseEnum.WAREHOUSEDTO_LIST,String.valueOf(merchantId), RedisCacheWaitTimeDMO.REDIS_TIMEOUT_1S);
		
	
		
	}

	public boolean deleteWarehouseAndAddressBy(Long merchantId, Long id)throws BizRuleException {
		/*warehouseFacade.deleteBy(sysUser.getMerchantId(),id);
		warehouseAddressFacade.deleteBy(sysUser.getMerchantId(),id);*/
		if(id==null || id.longValue()<0 || merchantId==null || merchantId.longValue()<0) {
			logger.error("deleteBy merchantId id is null");
			throw new BizRuleException("参数id不能为空");
		}
		Warehouse po = warehouseMapper.getByPrimaryKey(id);
		WarehouseAddress wpo=warehouseAddressMapper.getByPrimaryKey(id);
		if(po==null || !merchantId.equals(po.getMerchantId())) {
			logger.error("deleteBy 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		if(wpo==null || !merchantId.equals(wpo.getMerchantId())) {
			logger.error("deleteBy 异常，id={}不属于merchantid={}",id,merchantId);
			throw new BizRuleException("merchantId 参数异常。");
		}
		po = new Warehouse();
		po.setId(id);
		po.setStatus(OnOffDelEnums.DEL.getType());//删除
		warehouseMapper.updateWarehouse(po);
		return true;
	}
	
}
