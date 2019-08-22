package com.lk.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.lk.util.PageData;
import com.lk.util.Tools;
/**
* 导入到EXCEL
* 类名称：ObjectExcelView.java
* @author FH Q313596790
* @version 1.0
 */
public class ObjectExcelView extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
		sheet = workbook.createSheet("sheet1");
		
		//第一行表头
		PageData pdHead = (PageData) model.get("pdHead");
		//第二行标题
		List<String> titles = (List<String>) model.get("titles");
		int row = 0;
		
		if(StringUtil.isNotEmpty(pdHead)) {
			HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			HSSFFont headerFont = workbook.createFont();	//标题字体
			headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headerFont.setFontHeightInPoints((short)11);
			headerStyle.setFont(headerFont);
			
			CellRangeAddress region = new CellRangeAddress(row, row, (short) 0, (short) titles.size());   
			
			String title = pdHead.getString("name");
			cell = getCell(sheet, row, 0);
			cell.setCellStyle(headerStyle);
			setText(cell,title);
			short height=25*18*2;
			sheet.getRow(row).setHeight(height);
			sheet.addMergedRegion(region);
			
			row = 1;
		}
		
		
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont();	//标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short)11);
		headerStyle.setFont(headerFont);
		short width = 20,height=25*20;
		sheet.setDefaultColumnWidth(width);
		for(int i=0; i<len; i++){ //设置标题
			String title = titles.get(i);
			cell = getCell(sheet, row, i);
			cell.setCellStyle(headerStyle);
			setText(cell,title);
		}
		
		sheet.getRow(row).setHeight(height);
		
		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		List<PageData> varList = (List<PageData>) model.get("varList");
		int varCount = varList.size();
		for(int i=0; i<varCount; i++){
			row++;
			PageData vpd = varList.get(i);
			for(int j=0;j<len;j++){
				String varstr = vpd.get("var"+(j+1)).toString() != null ? vpd.get("var"+(j+1)).toString() : "";


				cell = getCell(sheet, row, j);
				cell.setCellStyle(contentStyle);
				setText(cell,varstr);
			}
		}
		
	}

}
