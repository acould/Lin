package star.facade.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.vo.StorageAreaVo;
import star.facade.warehouse.vo.dto.StorageAreaDTO;
import star.vo.search.SearchResult;

public interface StorageAreaFacade {

	/**
	 * 插入存储区记录
	 * @param vo
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:22:55
	 */
	public Long insertStorageAreaVo(Long merchantId,StorageAreaVo vo) throws BizRuleException;
	
	/**
	 * 根据主键得到存储区表记录
	 * @param id
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:46:27
	 */
	public StorageAreaVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException;


	/**
	 * 
	 * 更新存储区记录
	 * @param vo
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午4:52:08
	 */
	public Long updateStorageArea(Long merchantId,StorageAreaVo vo) throws BizRuleException ;

	/**
	 * 搜索存储区列表，带分页
	 * @throws BizRuleException 
	 *
	 * @haoxz11MyBatis
	 */
	public List<StorageAreaVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap,int start,int end) throws BizRuleException;

	/**
	 * 
	 * 搜索存储区列表
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:00:18
	 */
	public List<StorageAreaVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException;

	/**
	 * 
	 *  得到搜索存储区的记录数量
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:25:17
	 */
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException;
	
	
	/**
	 * 删除存储区
	 * @param id
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:35:43
	 */
	public void deleteBy(Long merchantId,Long id) throws BizRuleException;
	
	/**
	 * 获取 cache 存储区 all
	 * @return
	 * @throws BizRuleException 
	 * @author chistar
	 * @since  2018年11月3日 上午9:20:02
	 */
	public List<StorageAreaDTO> getCacheStorageAreaDTOList(Long merchantId) throws BizRuleException;
	/**
	 * 分页查询 列表
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 * @Author:chistar
	 * @Since : 2018年11月18日下午12:51:44
	 */
	public SearchResult<StorageAreaVo> getListTotalByWhere(Long merchantId,Map<String, Object> searchMap,int start,int size)  throws BizRuleException;
}
