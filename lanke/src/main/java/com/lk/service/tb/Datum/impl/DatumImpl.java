package com.lk.service.tb.Datum.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.service.tb.Datum.DatumManager;
import com.lk.util.PageData;

/**
 * 附件资料--业务层
 * @author myq
 *
 */
@Service("datumService")
public class DatumImpl implements DatumManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取网吧的图片
	 * @param pd（intenetdatum_id，sort,type）
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByInternet(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("DatumMapper.findByInternet", pd);
	}
	
}
