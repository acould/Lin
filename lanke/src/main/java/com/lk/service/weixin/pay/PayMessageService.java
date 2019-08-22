package com.lk.service.weixin.pay;

import com.lk.util.PageData;

public interface PayMessageService {
	/**创建支付记录*/
	public Boolean add(String id) throws Exception;
	
	/**修改支付记录*/
	public Boolean edit(PageData pd) throws Exception;
}
