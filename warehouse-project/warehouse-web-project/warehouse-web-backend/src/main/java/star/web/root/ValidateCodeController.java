package star.web.root;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import star.fw.web.util.RequestUtil;
import star.modules.cache.JedisService;

/**
 * 
 * 
 * 
 * Title: 得到验证码
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午10:41:21
 * @version $Id: ValidateCodeController.java 85853 2015-03-06 09:07:40Z zhjy $
 */
@Controller
public class ValidateCodeController {
	
	@Autowired
	private JedisService jedisService;
	
	/**
	 * Logger for this class
	 */
//	private static final Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);

	@RequestMapping(value = "/vcode")
	public void show(HttpServletRequest request, HttpServletResponse response) {
		RequestUtil.setToAjax(request);
		ImageCodeUtil.createImageCode(jedisService, response, request.getSession().getId());
//		String code = PasswordUtil.getRandomNum(4).toUpperCase();
//		CookieHelper.saveToken2Cookie(CookieEnum.CODE.getValue(), code, CookieTime.TIME_CLIENT);
//		response.setContentType("image/jpeg");
//		response.setHeader("Pragma", "No-cache");
//		response.setHeader("Cache-Control", "no-cache");
//		response.setDateHeader("Expires", 0);
//		BufferedImage image = ImageUtil.TwistImage(ImageUtil.getValidateImage(code), true, 3, 4);
//		try {
//			ImageIO.write(image, "JPEG", response.getOutputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			logger.error("Print Response Image Error:" + e.getMessage());
//		} finally {
//			image.flush();
//		}
	}
}
