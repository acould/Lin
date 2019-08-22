package com.lk.service.billiCenter.userSale.impl;

import java.util.ArrayList;
import java.util.List;


import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQuery;
import com.lk.entity.billecenter.SWUserConsume;
import com.lk.service.billiCenter.base.SWBaseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.service.billiCenter.userSale.UserSaleService;

import com.lk.util.ErrUtil;
import com.lk.util.MsgUtil;
import com.lk.util.StringUtil;

@Service("userSaleService")
public class UserSaleServiceImpl implements UserSaleService{



	@Autowired
	private SWBaseService swBaseService;


	@Override
	public Message2 sendSelUserSale(String store_id, SWQuery query) throws Exception {

		return swBaseService.sendToSW(store_id, query, MsgUtil.MSG_SEL_CONSUME);
	}

	@Override
	public Message2 getSelSWUserSaleByFlag(String msgFlag) throws Exception {

		List<SWUserConsume> list = new ArrayList<>();

		Message2 m2 = swBaseService.getMsgFromCache(msgFlag);
		if(m2.getErrcode() != 0){
			return m2;
		}

		if(StringUtil.isNotEmpty(m2.getData().get("arr"))){
			JSONArray arr = (JSONArray) m2.getData().get("arr");
			for (int i = 0; i < arr.size(); i++) {
				SWUserConsume userConsume = (SWUserConsume) JSONObject.toBean(arr.getJSONObject(i), SWUserConsume.class);
				list.add(userConsume);
			}
		}

		return Message2.ok().addData("list", list);
	}
}

