package star.facade.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.vo.WarehouseAndAddressVo;
import star.facade.warehouse.vo.WarehouseVo;
import star.facade.warehouse.vo.dto.WarehouseDTO;
import star.vo.search.SearchResult;

public interface WarehouseFacade {

	/**
	 * 插入仓库记录
	 * @param vo
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:22:55
	 */
	public Long insertWarehouseVo(Long merchantId,WarehouseVo vo) throws BizRuleException;
	
	/**
	 * 新增仓库基本信息和地址信息
	 * @param merchantId
	 * @param vo
	 * @return
	 * @throws BizRuleException
	 * @Author:chistar
	 * @Since : 2019年6月9日上午11:24:34
	 */
	public Long saveWarehouseAndAddressVo(Long merchantId,WarehouseAndAddressVo vo) throws BizRuleException;
	
	/**
	 * 根据主键得到仓库表记录
	 * @param id
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:46:27
	 */
	public WarehouseVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException;


	/**
	 * 
	 * 更新仓库记录
	 * @param vo
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午4:52:08
	 */
	public Long updateWarehouse(Long merchantId,WarehouseVo vo) throws BizRuleException ;

	/**
	 * 搜索仓库列表，带分页
	 * @throws BizRuleException 
	 *
	 * @haoxz11MyBatis
	 */
	public List<WarehouseVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap,int start,int end) throws BizRuleException;

	/**
	 * 
	 * 搜索仓库列表
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:00:18
	 */
	public List<WarehouseVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException;

	/**
	 * 
	 *  得到搜索仓库的记录数量
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:25:17
	 */
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException;
	
	
	/**
	 * 删除仓库
	 * @param id
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:35:43
	 */
	public void deleteBy(Long merchantId,Long id) throws BizRuleException;
	
	/**
	 * 获取 cache 货币列表 all
	 * @return
	 * @throws BizRuleException 
	 * @author chistar
	 * @since  2018年11月3日 上午9:20:02
	 */
	public List<WarehouseDTO> getCacheWarehouseDTOList(Long merchantId) throws BizRuleException;
	/**
	 * 分页查询 列表
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 * @Author:chistar
	 * @Since : 2018年11月18日下午12:51:44
	 */
	public SearchResult<WarehouseVo> getListTotalByWhere(Long merchantId,Map<String, Object> searchMap,int start,int size)  throws BizRuleException;

	/**
	 * 删除仓库基本信息和地址信息
	 * @since：2019年6月9日下午9:00:49
	 * @user： chenhang
	 * @param merchantId
	 * @param id
	 */
	public void deleteWarehouseAndAddressBy(Long merchantId, Long id)throws BizRuleException;
}
