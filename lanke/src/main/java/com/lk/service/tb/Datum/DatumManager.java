package com.lk.service.tb.Datum;

import java.util.List;

import com.lk.util.PageData;

public interface DatumManager {

	
	/**
	 * 获取网吧的图片
	 * @param pd（intenetdatum_id，sort,type）
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByInternet(PageData pd) throws Exception;
	
	
	
}
