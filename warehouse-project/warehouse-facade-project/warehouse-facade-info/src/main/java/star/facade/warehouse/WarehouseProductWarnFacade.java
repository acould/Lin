package star.facade.warehouse;

import java.util.HashMap;
import java.util.List;

import star.bizbase.exception.BizRuleException;
import star.facade.warehouse.vo.WarehouseProductWarnInfoVo;
import star.facade.warehouse.vo.dto.WarehouseProductWarnInfoDTO;

public interface WarehouseProductWarnFacade {

	/**
	 * 插入仓库警戒记录
	 * @param vo
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:22:55
	 */
	public Long insertWarehouseProductWarnInfoVo(Long merchantId,WarehouseProductWarnInfoVo vo) throws BizRuleException;
	
	/**
	 * 根据主键得到仓库警戒表记录
	 * @param id
	 * @return 
	 * @author xxh
	 * @throws BizRuleException 
	 * @since  2018年10月29日 下午4:46:27
	 */
	public WarehouseProductWarnInfoVo getByPrimaryKey(Long merchantId,Long id) throws BizRuleException;


	/**
	 * 
	 * 更新仓库警戒记录
	 * @param vo
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午4:52:08
	 */
	public Long updateWarehouseProductWarnInfo(Long merchantId,WarehouseProductWarnInfoVo vo) throws BizRuleException ;

	/**
	 * 搜索仓库警戒列表，带分页
	 * @throws BizRuleException 
	 *
	 * @haoxz11MyBatis
	 */
	public List<WarehouseProductWarnInfoVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap,int start,int end) throws BizRuleException;

	/**
	 * 
	 * 搜索仓库警戒列表
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:00:18
	 */
	public List<WarehouseProductWarnInfoVo> getListByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException;

	/**
	 * 
	 *  得到搜索仓库警戒的记录数量
	 * @param searchMap
	 * @return
	 * @throws BizRuleException 
	 * @author xxh
	 * @since  2018年10月29日 下午5:25:17
	 */
	public int getCountByWhere(Long merchantId,HashMap<String, Object> searchMap) throws BizRuleException;
	
	
	/**
	 * 删除仓库警戒
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
	public List<WarehouseProductWarnInfoDTO> getCacheWarehouseProductWarnInfoDTOList(Long merchantId) throws BizRuleException;
}
