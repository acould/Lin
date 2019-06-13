package star.web.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import star.fw.task.component.AddTaskMessageService;
import star.task.enums.TaskTypeEnum;
import star.task.vo.user.TestLoginRequest;
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
public class IndexController extends BasicController {
	@Autowired
	private AddTaskMessageService addTaskMessageService;

	@RequestMapping(value = { "/", "index" })
	public String index(ModelMap modelMap) {
		return "root/index";
	}
	
	@RequestMapping(value = { "/", "logintask" })
	public String logintask(ModelMap modelMap) {
		logger.info("logintask...start...");
		TestLoginRequest request = new TestLoginRequest();
		request.setEma("3333test---ema");
		request.setUserid(1L);
		request.setSource("test");
		
		addTaskMessageService.addTaskTimeOut300(TaskTypeEnum.StarUserLoginTask, request, "1");
		logger.info("logintask...end...");
		return "root/index";
	}

}
