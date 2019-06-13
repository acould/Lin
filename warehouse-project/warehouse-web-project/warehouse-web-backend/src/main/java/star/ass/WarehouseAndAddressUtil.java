package star.ass;

import star.facade.warehouse.vo.WarehouseAddressVo;
import star.facade.warehouse.vo.WarehouseAndAddressVo;
import star.facade.warehouse.vo.WarehouseVo;

/**
 *	WarehouseVo 和WarehouseAddressVo 数据分离和合并工具类
 * Copyright: Copyright guang.com(c) 2019
 * @since：2019年6月8日下午5:17:14
 * @user： chenhang
 */
public class WarehouseAndAddressUtil {
	/**
	 * 将WarehouseVo 和WarehouseAddressVo 数据合并给WarehouseAndAddressVo
	 * @since：2019年6月8日下午5:23:38
	 * @user： chenhang
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static WarehouseAndAddressVo mergeToWarehouseAndAddressVo(
			WarehouseVo v1,WarehouseAddressVo v2 ) {
		WarehouseAndAddressVo vo=new WarehouseAndAddressVo();
		if(v1!=null) {
			vo.setId(v1.getId());
			vo.setMerchantId(v1.getMerchantId());
			vo.setStatus(v1.getStatus());
//			vo.setCreateTime(v1.getCreateTime());
//			vo.setModifyTime(v1.getModifyTime());
			vo.setWarehouseType(v1.getWarehouseType());
			vo.setName(v1.getName());
			vo.setCode(v1.getCode());
			vo.setOrderby(v1.getOrderby());
			vo.setCreaterMid(v1.getCreaterMid());
			vo.setUpdaterMid(v1.getUpdaterMid());
			vo.setIsOutsource(v1.getIsOutsource());
			vo.setOutsourceName(v1.getOutsourceName());
			vo.setOutsourceUrl(v1.getOutsourceUrl());
			vo.setRemark(v1.getRemark());
			vo.setWarehouseClassId(v1.getWarehouseClassId());
		}
		if(v2!=null) {
			vo.setCountry(v2.getCountry());
			vo.setProvince(v2.getProvince());
			vo.setCity(v2.getCity());
			vo.setCounty(v2.getCounty());
			vo.setStreet(v2.getStreet());
			vo.setAddress(v2.getAddress());
			vo.setPostCode(v2.getPostCode());
			vo.setTelphone(v2.getTelphone());	
		}
		
		return vo;
	}
	/**
	 * 将WarehouseAndAddressVo数据分离给 WarehouseVo
	 * @since：2019年6月8日下午5:24:15
	 * @user： chenhang
	 * @param vo
	 * @return
	 */
	public static WarehouseVo separateToWarehouseVo(WarehouseAndAddressVo vo) {
		WarehouseVo w=new WarehouseVo();
		if(vo!= null) {
			w.setId(vo.getId());
			w.setMerchantId(vo.getMerchantId());
			w.setStatus(vo.getStatus());
			w.setCreateTime(vo.getCreateTime());
			w.setModifyTime(vo.getModifyTime());
			w.setWarehouseType(vo.getWarehouseType());
			w.setName(vo.getName());
			w.setCode(vo.getCode());
			w.setOrderby(vo.getOrderby());
			w.setCreaterMid(vo.getCreaterMid());
			w.setUpdaterMid(vo.getUpdaterMid());
			w.setIsOutsource(vo.getIsOutsource());
			w.setOutsourceName(vo.getOutsourceName());
			w.setOutsourceUrl(vo.getOutsourceUrl());
			w.setRemark(vo.getRemark());
			w.setWarehouseClassId(vo.getWarehouseClassId());	
		}
		
		return w;
	}
	
	
	
	/**
	 * 将WarehouseAndAddressVo数据分离给 WarehouseAddressVo
	 * @since：2019年6月8日下午5:24:41
	 * @user： chenhang
	 * @param vo
	 * @return
	 */
	public static WarehouseAddressVo separateToWarehouseAddressVo(WarehouseAndAddressVo vo) {
		WarehouseAddressVo  w=new WarehouseAddressVo();
		if(vo!=null) {
			w.setWarehouseId(vo.getId());//此处是WarehouseId
			w.setMerchantId(vo.getMerchantId());
			w.setCreateTime(vo.getCreateTime());
			w.setModifyTime(vo.getModifyTime());		
			w.setCountry(vo.getCountry());
			w.setProvince(vo.getProvince());
			w.setCity(vo.getCity());
			w.setCounty(vo.getCounty());
			w.setStreet(vo.getStreet());
			w.setAddress(vo.getAddress());
			w.setPostCode(vo.getPostCode());
			w.setTelphone(vo.getTelphone());	
		}
		
		return w;
	}
}
