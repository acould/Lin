package star.webtool.util;

import java.util.Date;

import star.util.DateUtil;
import star.util.StringUtil;
import star.webtool.vo.FileUploadPathVo;


public class FileUploadUtil {
	//存放上传文件的路径
	private static final String UPLOAD_SUB_DIR = "/s/";
	
	public static String getHttpSrcFilePath(String fileName,String uploadType){
		StringBuilder destFilePathBuilder = new StringBuilder();
		
		destFilePathBuilder.append(UPLOAD_SUB_DIR);
		if(StringUtil.isNotEmpty(uploadType)){
			destFilePathBuilder.append(uploadType + "/");
		}
		
		String currentDateStr = DateUtil.dateFormate(new Date(),"yyyyMMdd");
		destFilePathBuilder.append(currentDateStr.substring(0, 4) + "/");
		destFilePathBuilder.append(currentDateStr.substring(4, 6) + "/");
		destFilePathBuilder.append(currentDateStr.substring(6, 8) + "/");
		
		destFilePathBuilder.append(System.currentTimeMillis());
		destFilePathBuilder.append((int)(Math.random()*100000));
		
		if(StringUtil.isNotEmpty(fileName) && fileName.contains(".")){
			destFilePathBuilder.append(fileName.substring(fileName.lastIndexOf(".")));
		}
		
		return destFilePathBuilder.toString();
	}
	
	public static FileUploadPathVo getHttpSrcFilePathVo(String fileName,String uploadType){
		FileUploadPathVo pathVo = new FileUploadPathVo();
		
		StringBuilder destFilePathBuilder = new StringBuilder();
		
		if(StringUtil.isNotEmpty(uploadType)){
			destFilePathBuilder.append(uploadType + "/");
		}
		
		String currentDateStr = DateUtil.dateFormate(new Date(),"yyyyMMdd");
		destFilePathBuilder.append(currentDateStr.substring(0, 4) + "/");
		destFilePathBuilder.append(currentDateStr.substring(4, 6) + "/");
		destFilePathBuilder.append(currentDateStr.substring(6, 8) + "/");
		
		StringBuilder fileNameBuilder = new StringBuilder();
		String suffix = "";
		
		fileNameBuilder.append(System.currentTimeMillis());
		fileNameBuilder.append((int)(Math.random()*100000));
		
		if(StringUtil.isNotEmpty(fileName) && fileName.contains(".")){
			suffix = fileName.substring(fileName.lastIndexOf("."));
		}
		
		pathVo.setSuffix(suffix);
		pathVo.setFileName(fileNameBuilder.toString()+suffix);
		pathVo.setPath(destFilePathBuilder.toString()+pathVo.getFileName());
		
		return pathVo;
	}
	
}
