package com.lk.service.internet.scene;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 优惠券使用场景接口
 * 创建人：洪智鹏
 * 创建时间：2017-06-08
 * @version
 */
public interface SceneManager{

	/**新增
	 * @param pd包含优惠券使用场景相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * 通过卡劵id删除卡劵使用场景
	 * @param pd包含主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 修改场景数据
	 * @param pd包含优惠券使用场景相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page页面检索字段信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd无，查询全部
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd场景主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS场景主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**根据名称判断是否重复
	 * @param pd包含 场景名称，场景id和网吧id
	 * @throws Exception
	 */
	public PageData findByName(PageData pd)throws Exception;
	
	/**
	 * 根据名称判断是否重复
	 * @param pd 传入的是cardid，优惠券主键
	 * @throws Exception
	 */
	public PageData findByCard(PageData pd)throws Exception;
	/**
	 * 根据场景核销优惠券的判断
	 * @param pdSce 场景信息
	 * @param pd 卡卷信息
	 * @param pdUser 优惠券对应的用户信息(粉丝信息)
	 * @throws Exception
	 */
	public JSONObject SceneCancel( PageData pdSce,PageData pd,PageData pdUser)throws Exception;
	/**
	 * 判断是否已经设置过场景
	 * @param pd 包含网吧id和状态字段
	 * @throws Exception
	 */
	public List<PageData> listByState(PageData pd)throws Exception;

	
}

