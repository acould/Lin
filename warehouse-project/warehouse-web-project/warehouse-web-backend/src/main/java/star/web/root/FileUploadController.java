package star.web.root;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.ByteStreams;

import star.config.Config;
import star.util.StringUtil;
import star.vo.result.ResultVo;
import star.web.BasicController;
import star.webtool.util.FileUploadUtil;
import star.webtool.vo.FileUploadPathVo;

/**
 * 
 * 
 * 通用文件上传controller
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright guang.com(c) 2015
 * 
 * @author yes
 * @created 2015年2月2日 上午11:50:54
 * @version $Id: FileUploadController.java 87886 2015-05-19 12:08:57Z zhjy $
 */
@Controller
public class FileUploadController extends BasicController {
	//存放上传文件的路径
	private static String LOCAL_IMG_DIR = Config.getRootPath() + "/upload/";
//	private static String IMG_DIR="http://192.168.4.100:10300/merchant/";
	private static String SAMPLE_IMG = Config.getRootPath() + "/conf/resource/cert_identity_sample.jpg";
	private static String NOT_FOUND_IMG = Config.getRootPath() + "/conf/resource/not_found.jpg";
	
	//private static final ImmutableSet<String> DEFAULT_ALLOW_WIDTH_HIGH = ImmutableSet.of("470x470");
	private static final ImmutableSet<String> DEFAULT_ALLOW_FILE_TYPE = ImmutableSet.of(".jpg",".jpeg",".gif",".png",".xls",".xlsx", ".webp");
	private static final Long MAX_FILE_SIZE = 1024*1024*4L;//最多4MB文件
	
	@RequestMapping(value = { "/upload" }, method = RequestMethod.POST)
	@ResponseBody
	public Model uploadProcess(@RequestParam("imageFile") MultipartFile file,String uploadType, Model model) {
		ResultVo<String> result = new ResultVo<String>();
		model.addAttribute("ret", result);
		if(file == null || StringUtil.isEmpty(file.getOriginalFilename()) || StringUtil.isEmpty(uploadType)){
			result.setSuccess(false);
			result.setCode("1000");
			result.setResultDes("上传的文件未获取到或者参数错误，请刷新重试！");
			return model;
		}
		
		if(file.getSize()>MAX_FILE_SIZE){
			result.setSuccess(false);
			result.setCode("1001");
			result.setResultDes("上传的文件太大，不允许上传！");
			return model;
		}
		
		if(!file.getOriginalFilename().contains(".") || ! DEFAULT_ALLOW_FILE_TYPE.contains(
				file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")))){
			result.setSuccess(false);
			result.setCode("1002");
			result.setResultDes("上传的文件类型不允许！");
			return model;
		}
		
		try{
			
//			verifai(file.getInputStream(),result);
//			if(! result.isSuccess()) return model;
			
			//获取文件存储路径
			FileUploadPathVo pathVo = FileUploadUtil.getHttpSrcFilePathVo(file.getOriginalFilename(),uploadType);
			
			//保存原始图片
			File destFile = new File(LOCAL_IMG_DIR+pathVo.getPath());
			if(!destFile.getParentFile().exists()){
				destFile.getParentFile().mkdirs();
			}
			FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
			
			result.setSuccess(true);
			result.setResult(pathVo.getPath());
			result.setResultDes("上传的文件成功！");
		}catch(Exception e){
			result.setCode("1003");
			result.setSuccess(false);
			result.setResultDes("上传文件过程中发生异常，请联系管理员！");
			return model;
		}
		return model;
	}
	
	/**
	 * 通过url请求返回图像的字节流
	 */
	@RequestMapping("/show")
	public void showImage(String imgPath,HttpServletResponse response) {
		if(StringUtil.isEmpty(imgPath)) imgPath = "";
		try{
			File file = new File(LOCAL_IMG_DIR+imgPath);
			//判断文件是否存在如果不存在就返回默认图标
			if(!(file.exists() && file.canRead())) {
				file = new File(NOT_FOUND_IMG);
			}
			
			response.setContentType("image/jpeg");
			OutputStream stream = response.getOutputStream();
			stream.write(ByteStreams.toByteArray(new FileInputStream(file)));
			stream.flush();
			stream.close();
			
		}catch(Exception e){
			//ignore
		}
		
	}
	
	@RequestMapping("/sample")
	public void sampleImage(HttpServletResponse response) {
		
		try{
			File file = new File(SAMPLE_IMG);
			//判断文件是否存在如果不存在就返回默认图标
			if(!(file.exists() && file.canRead())) {
				file = new File(NOT_FOUND_IMG);
			}
			
			response.setContentType("image/jpeg");
			OutputStream stream = response.getOutputStream();
			stream.write(ByteStreams.toByteArray(new FileInputStream(file)));
			stream.flush();
			stream.close();
			
		}catch(Exception e){
			//ignore
		}
	}
	
	/**
	 * 文件信息校验
	 * @param result
	 */
//	private void verifai(InputStream inputStream,ResultVo<String> result){
//		try{
//			BufferedImage sourceImg = ImageIO.read(inputStream);
//			for(String wh : DEFAULT_ALLOW_WIDTH_HIGH){
//				if(StringUtil.isEquals(wh, sourceImg.getWidth()+"x"+sourceImg.getHeight())){
//					result.setSuccess(true);
//					return;
//				}
//			}
//			result.setSuccess(false);
//			result.setResultDes("文件的尺寸不对,不允许上传，请修改重试！");
//		}catch(IOException ioe){
//			result.setSuccess(false);
//			result.setResultDes("校验文件尺寸异常，请修改重试！");
//		}
//	}
}
