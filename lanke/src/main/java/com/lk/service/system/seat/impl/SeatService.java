package com.lk.service.system.seat.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.seat.SeatManager;

/** 
 * 说明： 网吧座位设置
 * 创建人：洪智鹏
 * 创建时间：2017-02-20
 * @version
 */
@Service("seatService")
public class SeatService implements SeatManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd网吧座位设置相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SeatMapper.save", pd);
	}
	
	/**删除
	 * @param pd包含网吧座位设置主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SeatMapper.delete", pd);
	}
	
	/**修改
	 * @param pd网吧座位设置相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SeatMapper.edit", pd);
	}
	
	/**列表
	 * @param page页面信息加检索字段信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SeatMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd无 查询全部
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SeatMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd根据主键进行查询包含网吧座位设置的主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SeatMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS网吧座位设置主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SeatMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

