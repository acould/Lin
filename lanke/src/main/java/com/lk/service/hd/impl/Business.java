package com.lk.service.hd.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.lk.service.hd.CallBack;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

/*
 * 业务处理层，发出信息并等待后续处理
 */
@Service("callBackService")
public class Business implements CallBack {
	@Resource(name="callBackService")
	private CallBack callBackService;
	public static Map<Integer, String> businesses = new HashMap<Integer, String>();

	/**
	 * 在缓存中创建key值，并加锁，等待往对应的key中传入数据
	 * @param a
	 * @param time
	 */
	public static void exc(int a,long time) {
		System.out.println("time1===="+Tools.date2Str(new Date()));
		businesses.put(a, null);
		lock(a,time);
	}

	/**
	 * 加锁，等待读取返回信息到缓存
	 * @param key
	 * @param time
	 */
	public static void lock(int key,long time) {
		if (businesses.containsKey(key) && StringUtil.isEmpty(businesses.get(key))) {
			try {
				Thread.sleep(time);
				if(StringUtil.isEmpty(businesses.get(key))){
					Thread.sleep(time*5);
					System.out.println("time3===="+Tools.date2Str(new Date()));
					Thread.sleep(time*3);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取缓存中对应key值的内容
	 */
	public String result(int result) {
		return businesses.get(result);
	}
	
	

}
