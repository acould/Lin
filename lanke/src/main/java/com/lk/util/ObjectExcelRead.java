package com.lk.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;


/**
 * 从EXCEL导入到数据库
 * 创建人：洪智鹏
 * 创建时间：2014年12月23日
 * @version
 */
public class ObjectExcelRead {

	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi);
			HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) {					//行循环开始
				
				PageData varpd = new PageData();
				HSSFRow row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) {				//列循环开始
					
					HSSFCell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {
						switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
						case 0:
							//日期格式的处理
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
							}else{
								//cellValue = String.valueOf((int) cell.getNumericCellValue());
								//手机号格式
								DecimalFormat df = new DecimalFormat("#");
								cellValue = df.format(cell.getNumericCellValue());
							}

							break;
						case 1:
							//字符串
							cellValue = cell.getStringCellValue();
							break;
						case 2:
							// 公式
							cellValue = cell.getNumericCellValue() + "";
							// cellValue = String.valueOf(cell.getDateCellValue());
							break;
						case 3:
							// 空白
							cellValue = "";
							break;
						case 4:
							// 布尔取值
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							//错误类型
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;
						}
					} else {
						cellValue = "";
					}
					
					varpd.put("var"+j, cellValue);
					
				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return varList;
	}
}
