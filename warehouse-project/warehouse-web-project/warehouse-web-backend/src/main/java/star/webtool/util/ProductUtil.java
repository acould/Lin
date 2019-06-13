package star.webtool.util;

import java.math.BigDecimal;


/**
 * 产品工具类
 * 
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright star.com(c) 2015
 * 
 * @author qhxu
 * @created 2015年7月30日 下午8:26:26
 * @version $Id$
 */
public class ProductUtil {
	
	/**
	 * 获取月供金额
	 * @param price--总价
	 * @return
	 * @author qhxu
	 * @since 2015年7月30日 下午8:22:32
	 */
	public static Long getPriceMonth(BigDecimal price){
		long retprice = 0;
		if(price==null) return retprice;
		
		// 计算月供 日供   到元  并向上取整    月供： price / 24  + price * 0.0099
		BigDecimal monthpay = price.divide(new BigDecimal("24"),2).add(price.multiply(new BigDecimal("0.0099"))).setScale(0,BigDecimal.ROUND_UP);
	    
		return monthpay==null?0:monthpay.longValue();			
				
	}
	

	/**
	 * 获取月供金额
	 * @param price--总价
	 * @return
	 * @author qhxu
	 * @since 2015年7月30日 下午8:22:32
	 */
	public static Long getPriceDay(BigDecimal price){
		long retprice = 0;
		if(price==null) return retprice;
		
		// 计算月供 日供   到元  并向上取整
		Long mpay = ProductUtil.getPriceMonth(price);
		BigDecimal monthpay = new BigDecimal(mpay==null?0:mpay.longValue());
	    BigDecimal daypay = monthpay.divide(new BigDecimal("30"),0,BigDecimal.ROUND_UP);
		return daypay==null?0:daypay.longValue();			
				
	}
}
