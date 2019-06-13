package star.test.warehouse;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import star.exception.RuleException;
import star.facade.warehouse.WarehouseClassFacade;
import star.facade.warehouse.vo.WarehouseClassVo;

/**
 *仓库测试类
 * Copyright: Copyright guang.com(c) 2019
 * @since：2019年6月2日上午11:17:57
 * @user： chenhang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-modules.xml")
public class WareHourseTest {

	@Autowired
	private WarehouseClassFacade warehouseClassFacade;
	
	/**
	 * 根据商家id,status获取仓库类型列表
	 * @since：2019年6月2日上午11:18:28
	 * @user： chenhang
	 * @throws RuleException
	 */
	@Test
	public void testgetListByWhere() throws RuleException {
		Long merchantId=1L;
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("status","ON");
		
		List<WarehouseClassVo> list =warehouseClassFacade.getListByWhere(merchantId, searchMap);
		System.out.println(list);
	}
	

	
}
