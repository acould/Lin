package star.facade.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.vo.StoreOutVo;
import star.vo.search.SearchResult;

public interface StoreOutFacade {

	/**
	 * 插入出库记录
	 * @param vo
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:22:55
	 */
	public Long insertStoreOutVo(Long merchantId,StoreOutVo vo) throws BizRuleException;
	
	/**
	 * 根据主键得到出库表记录
	 * @param id
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:46:27
	 */
	public StoreOutVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException;


	/**
	 * 
	 * 更新出库记录
	 * @param vo
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午4:52:08
	 */
	public Long updateStoreOut(Long merchantId,StoreOutVo vo) throws BizRuleException ;

	/**
	 * 搜索出库列表，带分页
	 * @throws BizRuleException 
	 *
	 * @haoxz11MyBatis
	 */
	public List<StoreOutVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap,int start,int end) throws BizRuleException;

	/**
	 * 
	 * 搜索出库列表
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:00:18
	 */
	public List<StoreOutVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException;

	/**
	 * 
	 *  得到搜索出库的记录数量
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:25:17
	 */
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException;
	
	
	/**
	 * 删除出库
	 * @param id
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:35:43
	 */
	public void deleteBy(Long merchantId,Long id) throws BizRuleException;
	
	/**
	 * 分页查询 列表
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 * @Author:chistar
	 * @Since : 2018年11月18日下午12:51:44
	 */
	public SearchResult<StoreOutVo> getListTotalByWhere(Long merchantId,Map<String, Object> searchMap,int start,int size)  throws BizRuleException;
	
	
}
