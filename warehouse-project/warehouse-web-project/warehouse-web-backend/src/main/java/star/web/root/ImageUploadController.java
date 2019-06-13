package star.web.root;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import star.config.Config;
import star.facade.ecmanager.vo.ResponseResult;
import star.util.DateUtil;
import star.util.ExceptionUtil;
import star.util.HttpUtil;
import star.util.StringUtil;
import star.vo.result.ResultVo;
import star.web.BasicController;

/**
 * 
 * 
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright star.com(c) 2015
 * 
 * @author zyt
 * @created 2015年6月25日 下午3:42:31
 * @version $Id$
 */
@Controller
public class ImageUploadController extends BasicController {

	private static final Gson gson = new Gson();
	
	public static final String CHARSET = "UTF-8";
	 /**
     * key
     */
//    private static final String token;

	private static String IMG_URL ;

	private static final ImmutableSet<String> DEFAULT_ALLOW_APK_TYPE = ImmutableSet
			.of(".apk",".xls",".xlsx",".doc",".docx",".zip");
	private static final ImmutableSet<String> DEFAULT_ALLOW_PRO_TYPE = ImmutableSet
			.of(".jpg", ".jpeg", ".gif", ".png",".doc",".txt",".xls",".ppt",".pdf",".docx",".xlsx",".pptx", ".webp");
	
	private static final ImmutableSet<String> DEFAULT_ALLOW_FILE_TYPE = ImmutableSet
			.of(".jpg", ".jpeg", ".gif", ".png" ,".JPG", ".JPEG", ".GIF", ".PNG", ".webp");
	
	private static final Long MAX_FILE_SIZE = 1024 * 1024 * 4L;// 最多4MB文件
	
	private static final Long MAX_IMG_SIZE = 1024 * 1204 *1L;
	
	private static final Long NEW_MAX_FILE_SIZE = 1024 * 1024 *3L;

	private static final String DefaultUserAgent = "GuangCPS ApiSdk Client v1.0";
	
	private static final String ImgSeparator = "x"; //分辨率宽高分隔符
	
	private static final String FileImgSeparator = "_"; 
	
	 static
	    {
//	        token = Config.getString("star.token");
	        IMG_URL = Config.getString("star.server.url")+"/external/common/pic/upload";
	    }

	@RequestMapping(value = { "/uploadimg" }, method = RequestMethod.POST)
	@ResponseBody
	public Model uploadProcess(@RequestParam("imageFile") MultipartFile file,@RequestParam(defaultValue="0") int imgwidth, 
			@RequestParam(defaultValue="0") int imgheight, @RequestParam(defaultValue="") String banner,Model model,
			HttpServletRequest request) {
		ResultVo<String> result = new ResultVo<String>();
		model.addAttribute("ret", result);
		if (file == null || StringUtil.isEmpty(file.getOriginalFilename())) {
			result.setSuccess(false);
			result.setResultDes("上传的文件未获取到或者参数错误，请刷新重试！");
			return model;
		}

		if (file.getSize() > MAX_FILE_SIZE) {
			result.setSuccess(false);
			result.setResultDes("上传的文件太大，不允许上传！");
			return model;
		}

		if (!file.getOriginalFilename().contains(".")
				|| !DEFAULT_ALLOW_FILE_TYPE
						.contains(file.getOriginalFilename().substring(
								file.getOriginalFilename().lastIndexOf(".")))) {
			result.setSuccess(false);
			result.setResultDes("上传的文件类型不允许！");
			return model;
		}

		String filename = file.getOriginalFilename();

		FileOutputStream fos = null;
		InputStream fis = null;
		File newFile = null;
		File uploadFile = null;
		try {
			String time = DateUtil.dateFormate(new Date(), "yyyyMMddHHmmss");
			String oper = System.getProperty("file.separator");
			String newFileName = time
					+ (filename.substring(filename.lastIndexOf("."),
							filename.length()));
			//上传文件获取路径方法改进：尽量不要用getRealPath； xxh bgn
			ServletContext sc = request.getSession().getServletContext();
			//sc.getResource("/").getPath()
			String newpath =sc.getResource("/").getPath()+ oper + "static" + oper + "images" + oper+ "uploadTemp" + oper;

			File filePath =new File(newpath);  
			//如果文件夹不存在则创建
			if  (!filePath.exists()  && !filePath.isDirectory())      
			{         
				filePath.mkdir();    
			}
			newpath += newFileName;//文件路径
			newFile = new File(newpath);
			//上传文件获取路径方法改进：尽量不要用getRealPath； xxh end
			//newFile = new File(sc.getRealPath(newpath));
			/*newFile = new File(request
					.getSession()
					.getServletContext()
					.getRealPath(
							oper + "static" + oper + "images" + oper
									+ "uploadTemp" + oper + newFileName));*/

			fos = new FileOutputStream(newFile);
			fis = file.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			// 判断图片长宽
			BufferedImage bis = ImageIO.read(newFile);
			if(bis == null) {
				result.setSuccess(false);
				result.setResultDes("请检查图片格式！");
				return model;
			}
			int w = bis.getWidth();
			int h = bis.getHeight();

			// 使长宽支持 *-number 格式   modify by liutuo 2016-11-11 begin
			boolean correctSize = true;
			if (imgwidth != 0 && w != imgwidth) {
				correctSize = false;
			}
			if (imgheight != 0 && h != imgheight) {
				correctSize = false;
			}

			if (!correctSize) {
				result.setSuccess(false);
				result.setResultDes("上传图片大小不符！");
				return model;
			}
			// 使长宽支持 *-number 格式   modify by liutuo 2016-11-11 end

			// for YYHD-1350 start
			// 图片上传增加图片大小
			String uploadFilename = newFileName.substring(0, newFileName.lastIndexOf("."))+FileImgSeparator+w+ImgSeparator+h
					+newFileName.substring(newFileName.lastIndexOf("."),newFileName.length());
			//文件路径
			String uploadPath =sc.getResource("/").getPath()+ oper + "static" + oper + "images" + oper+ "uploadTemp" + oper+uploadFilename;
			uploadFile = new File(uploadPath);
			fos.flush();
			fos.close();
			//renameTo之前需要关闭流
			newFile.renameTo(uploadFile);
			// for YYHD-1350 end
			
			// 图片上传开始...
			HttpPost post = HttpUtil.getPostRequest(IMG_URL);
			post.addHeader("User-Agent", DefaultUserAgent);

			// 把文件转换成流对象FileBody
			FileBody bin = new FileBody(uploadFile);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("Filedata", bin).build();

			post.setEntity(reqEntity);
			ResponseResult<String> ret = gson.fromJson(HttpUtil.execute(post, CHARSET),
					new TypeToken<ResponseResult<String>>() {
					}.getType());
			if (ret != null && ret.getStatus() == 1) {
				result.setSuccess(true);
				//modify by shirenchuang  把图片的高宽待在链接上
				String imgurl = ret.getResponse()+"?height="+h+"&width="+w;
				result.setResult(imgurl);
				if(StringUtil.isNotEmpty(banner) && "true".equals(banner)){
					result.setResultDes(h+"");
				}else{
					result.setResultDes("上传的文件成功！");
				}
			}else {
				result.setSuccess(false);
				result.setResultDes("上传文件基础服务异常！");
			}
			

		} 
		catch (IIOException e) {
			logger.error(ExceptionUtil.getMessage(e));
			result.setSuccess(false);
			result.setResultDes("该图片是CMYK模式，请切换成RGB模式后上传");
			return model;
		}
		catch (Exception e) {
			logger.error(ExceptionUtil.getMessage(e));
			result.setSuccess(false);
			result.setResultDes("上传文件过程中发生异常，请联系管理员！");
			return model;
		} finally {
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(ExceptionUtil.getMessage(e));
				}
			}
			if(fos!=null){
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					logger.error(ExceptionUtil.getMessage(e));
				}
			}
			
			// 删除服务器上临时文件
			if (uploadFile!=null && uploadFile.exists()) {
				uploadFile.delete();
			}
		}
		return model;
	}
	/**
	 * @param nameType 上传文件命名方式  </br>
	 * FIRSTDOT：系统随机 + 原文件名第一个.后的文件名) </br>
	 * 其他：系统自定义，原文件名不保留
	 */
	@RequestMapping(value = { "/uploadFile" }, method = RequestMethod.POST)
	@ResponseBody
	public Model uploadFile(@RequestParam("file") MultipartFile file,String nameType,
			 Model model,
			HttpServletRequest request) {
		ResultVo<String> result = new ResultVo<String>();
		
//		String dPath = DateUtil.dateFormate(new Date(), "yyyyMM");
//		StringBuilder path =  new StringBuilder();
//		path.append(dPath.substring(0, 4)+"/");
//		path.append(dPath.substring(4, 6));
		//服务器目录下的相对路径
		final String SAVE_APK_PATH = "/upload/apk";
		String webroot = "/web"+SAVE_APK_PATH;
		model.addAttribute("ret", result);
		if (file == null || StringUtil.isEmpty(file.getOriginalFilename())) {
			result.setCode("1000");
			result.setSuccess(false);
			result.setResultDes("上传的文件未获取到或者参数错误，请刷新重试！");
			return model;
		}

		//限制的文件大小
		if (!StringUtil.isEmpty(request.getParameter("maxSize"))) {
			Long maxSize = Long.valueOf(request.getParameter("maxSize")) * 1024 * 1024L; //单位M
			if (file.getSize() > maxSize) {
				result.setCode("1001");
				result.setSuccess(false);
				result.setResultDes("上传的文件太大，不允许上传！");
				return model;
			}
		}

		//限制文件类型
		String fileSuffix = file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf("."));
		String fileType = request.getParameter("fileType");
		if (!file.getOriginalFilename().contains(".")
				|| !DEFAULT_ALLOW_APK_TYPE.contains(fileSuffix)
				|| (!StringUtil.isEmpty(fileType) && !fileType.contains(fileSuffix))) {
			result.setSuccess(false);
			result.setCode("1002");
			result.setResultDes("上传的文件类型不允许！");
			return model;
		}
		String filename = file.getOriginalFilename();

		FileOutputStream fos = null;
		InputStream fis = null;
		File newFile = null;
		try {
			String time = DateUtil.dateFormate(new Date(), "yyyyMMddHHmmss");
//			String oper = System.getProperty("file.separator");
			
			//更改原文件名称
			String newFileName = "";
			if ("FIRSTDOT".equals(nameType)) {
				newFileName = time + (filename.substring(filename.indexOf("."),filename.length()));
			}else {
				newFileName = time + (filename.substring(filename.lastIndexOf("."),filename.length()));
			}
			
			newFile = new File(Config.getRootPath()+webroot);
			newFile.mkdirs();
			File apkFile = new File(newFile.getPath(),newFileName);
			apkFile.createNewFile();

			fos = new FileOutputStream(apkFile);
			fis = file.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
            
			// 图片上传开始...
			HttpPost post = HttpUtil.getPostRequest(IMG_URL+"?name=app");
			post.addHeader("User-Agent", DefaultUserAgent);

			// 把文件转换成流对象FileBody
			FileBody bin = new FileBody(apkFile);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("Filedata", bin).build();

			post.setEntity(reqEntity);
			ResponseResult<String> ret = gson.fromJson(HttpUtil.execute(post, CHARSET),
					new TypeToken<ResponseResult<String>>() {
					}.getType());
			result.setSuccess(true);
            result.setCode("100");
            result.setResult(ret.getResponse());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getMessage(e));
			result.setSuccess(false);
			result.setCode("1003");
			result.setResultDes("上传文件过程中发生异常，请联系管理员！");
			return model;
		} finally {
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(ExceptionUtil.getMessage(e));
				}
			}
			if(fos!=null){
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					logger.error(ExceptionUtil.getMessage(e));
				}
			}
			
			// 删除服务器上临时文件
			if (newFile!=null && newFile.exists()) {
				newFile.delete();
			}
		}
		return model;
	}
	
	@RequestMapping(value = { "/uploadproductFile" }, method = RequestMethod.POST)
	@ResponseBody
	public Model uploadProductFile(@RequestParam("file") MultipartFile file,
			 Model model,
			HttpServletRequest request) {
		ResultVo<String> result = new ResultVo<String>();
		
//		String dPath = DateUtil.dateFormate(new Date(), "yyyyMM");
//		StringBuilder path =  new StringBuilder();
//		path.append(dPath.substring(0, 4)+"/");
//		path.append(dPath.substring(4, 6));
		//服务器目录下的相对路径
		final String SAVE_APK_PATH = "/upload/apk";
		String webroot = "/web"+SAVE_APK_PATH;
		model.addAttribute("ret", result);
		if (file == null || StringUtil.isEmpty(file.getOriginalFilename())) {
			result.setSuccess(false);
			result.setResultDes("上传的文件未获取到或者参数错误，请刷新重试！");
			return model;
		}

//		限制文件类型
		if (!file.getOriginalFilename().contains(".")
				|| !DEFAULT_ALLOW_PRO_TYPE
						.contains(file.getOriginalFilename().substring(
								file.getOriginalFilename().lastIndexOf(".")))) {
			result.setSuccess(false);
			result.setResultDes("上传的文件类型不允许！");
			return model;
		}
		//如果是图片类型文件则限制大小为1M
		if (DEFAULT_ALLOW_FILE_TYPE
						.contains(file.getOriginalFilename().substring(
								file.getOriginalFilename().lastIndexOf(".")))) {
//			限制的文件大小
			if (file.getSize() > MAX_IMG_SIZE) {
				result.setSuccess(false);
				result.setResultDes("上传的文件太大，不允许上传！");
				return model;
			}
			//如果是文件类型则最大大小为3M
		}else {
			if (file.getSize() > NEW_MAX_FILE_SIZE) {
				result.setSuccess(false);
				result.setResultDes("上传的文件太大，不允许上传！");
				return model;
			}
		}
		String filename = file.getOriginalFilename();

		FileOutputStream fos = null;
		InputStream fis = null;
		File newFile = null;
		try {
			String time = DateUtil.dateFormate(new Date(), "yyyyMMddHHmmss");
//			String oper = System.getProperty("file.separator");
			
			//更改原文件名称
			String newFileName = time
					+ (filename.substring(filename.indexOf("."),
							filename.length()));
			newFile = new File(Config.getRootPath()+webroot);
			newFile.mkdirs();
			File apkFile = new File(newFile.getPath(),newFileName);
			apkFile.createNewFile();

			fos = new FileOutputStream(apkFile);
			fis = file.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
            
			// 图片上传开始...
			HttpPost post = HttpUtil.getPostRequest(IMG_URL+"?name=product");
			post.addHeader("User-Agent", DefaultUserAgent);

			// 把文件转换成流对象FileBody
			FileBody bin = new FileBody(apkFile);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("Filedata", bin).build();

			post.setEntity(reqEntity);
			ResponseResult<String> ret = gson.fromJson(HttpUtil.execute(post, CHARSET),
					new TypeToken<ResponseResult<String>>() {
					}.getType());
			result.setSuccess(true);
            result.setCode("100");
            result.setResult(ret.getResponse());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getMessage(e));
			result.setSuccess(false);
			result.setResultDes("上传文件过程中发生异常，请联系管理员！");
			return model;
		} finally {
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(ExceptionUtil.getMessage(e));
				}
			}
			if(fos!=null){
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					logger.error(ExceptionUtil.getMessage(e));
				}
			}
			
			// 删除服务器上临时文件
			if (newFile!=null && newFile.exists()) {
				newFile.delete();
			}
		}
		return model;
	}
	

}
