package star.web.rocketmq.test;

import java.util.Optional;

import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import star.facade.ecmanager.manager.vo.ManagerVo;
import star.mq.producer.DefaultProducer;
import star.web.BasicController;

/**
 * 
 * 
 * 
 * Title: 首页
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午10:41:10
 * @version $Id: IndexController.java 89113 2015-06-13 10:17:14Z zhjy $
 */
@Controller
public class TestRocketMqController extends BasicController {
	@Autowired
	@Qualifier("mqProducer")
	private DefaultProducer mqProducer;
	
	/**
	 * rocketmq 生产者ｔｅｓｔ
	 * @param modelMap
	 * @return
	 * @Author:chistar
	 * @Since : 2019年5月30日下午12:03:15
	 */
	@RequestMapping(value = { "/", "loginrocketmq" })
	public String loginrocketmq(ModelMap modelMap) {
		// 使用mq异步
		ManagerVo managervo = this.getSysUser();
		String userId = (managervo==null || managervo.getId()==null)?"1":managervo.getId().toString();
		logger.info("loginrocketmq...start...userId={}",userId);
		Optional<SendResult> result = mqProducer.sendMsg(userId, "TestGroupMq_Topic", userId);
		if (!result.isPresent()) {
			logger.error("mq producer send failed, userid:{}", userId);
		}
		
		logger.info("loginrocketmq...end...result={}",result);
		return "root/index";
	}

}
