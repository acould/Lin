package star.web.root;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import star.modules.cache.JedisService;
import star.util.ImageUtil;
import star.util.PasswordUtil;

/**
 * 
 * 图形验证码生成工具
 * 
 * Title:
 * 
 * Description:
 * 
 * Copyright: (c) 2016
 * 
 * @author haoxz11
 * @created 2016年7月1日下午12:01:05
 */

public class ImageCodeUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ImageCodeUtil.class);

	private static final int cacheTime = 30 * 60;
	private static final String IMAGE_CODE_TYPE = "image_code";

	public static void createImageCode(JedisService service, HttpServletResponse response, String key) {
		String code = PasswordUtil.getRandomNum(4).toUpperCase();
		service.putInCache(IMAGE_CODE_TYPE, key, code, cacheTime);
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		BufferedImage image = ImageUtil.twistImage(ImageUtil.getValidateImage(code), true, 3, 4);
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error("Print Response Image Error:" + e.getMessage());
		} finally {
			image.flush();
		}
	}

	public static String getImageCode(JedisService service, String key) {
		return (String) service.getFromCache(IMAGE_CODE_TYPE, key);
	}

	public static void clearImageCode(JedisService service, String key) {
		service.deleteCache(IMAGE_CODE_TYPE, key);
	}
}