package com.lk.service.weixin.sendRecord;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lk.entity.Message;
import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.util.PageData;

public interface SendRecordManager {
	
	public void save(PageData pd)throws Exception;
	public void delete(PageData pd)throws Exception;
	public void edit(PageData pd)throws Exception;
	
	public List<PageData> list(Page page)throws Exception;
	public PageData findById(PageData pd)throws Exception;
	public PageData findByMediaId(PageData pdMaterial)throws Exception;
	public List<PageData> findByInternetId(PageData pd)throws Exception;
	
	public void deleteAll(String[] ids)throws Exception;
	public List<PageData> isOutSend(PageData pd)throws Exception;


	


	/**
	 * 预览图文（上传图文素材，获取其url）
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject preview(PageData pd) throws Exception;


	/**
	 * 加载图文列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
    Message loadRecordNews(PageData pd, Page page) throws Exception;


	List<PageData> listByInternetId(PageData pd)  throws Exception;

    Message loadRecord(PageData pd)  throws Exception;



	Message loadWxImgs(PageData pd)  throws Exception;

    Message delRecord(PageData pd) throws Exception;

	Message saveRecord(PageData pd) throws Exception;

    Message sendNews(PageData pd) throws Exception;

	Message getPreviewQr(PageData pd)  throws Exception;

	Message sendPreviewNews(PageData pd) throws Exception;
}
