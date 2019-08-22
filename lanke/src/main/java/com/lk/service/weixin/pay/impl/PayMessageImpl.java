package com.lk.service.weixin.pay.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.service.weixin.pay.PayMessageService;
import com.lk.util.PageData;


@Service("payMessageService")
public class PayMessageImpl implements PayMessageService{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**
	 * 创建支付记录
	 */
	@Override
	public Boolean add(String id) {
		Boolean result=true;
		try {
			dao.save("PayMessageMapper.addMessage", id);
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		}
		return result; 
	}
	
	/**
	 * 修改支付记录
	 */
	@Override
	public Boolean edit(PageData pd) {
		Boolean result=true;
		try {
			dao.save("PayMessageMapper.editMessage", pd);
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		}
		return result; 
	}
}
