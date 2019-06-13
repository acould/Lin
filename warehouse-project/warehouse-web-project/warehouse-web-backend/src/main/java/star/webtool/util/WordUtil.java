package star.webtool.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.poi.hwpf.HWPFDocument;  
import org.apache.poi.hwpf.usermodel.CharacterRun;  
import org.apache.poi.hwpf.usermodel.Paragraph;  
import org.apache.poi.hwpf.usermodel.Range;  
import org.apache.poi.hwpf.usermodel.Table;  
import org.apache.poi.hwpf.usermodel.TableCell;  
import org.apache.poi.hwpf.usermodel.TableIterator;  
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

 
/**
 * WORD操作工具类
 * 
 * @author xutao
 * @date 2016年12月7日
 */

public class WordUtil{  
    /** 
     * 回车符ASCII码 
     */  
    private static final short ENTER_ASCII = 13;  
  
    /** 
     * 空格符ASCII码 
     */  
    private static final short SPACE_ASCII = 32;  
  
    /** 
     * 水平制表符ASCII码 
     */  
    private static final short TABULATION_ASCII = 9;  
  
    private static String htmlTextTbl = "";  
    private static int counter = 0;  
    private static int beginPosi = 0;  
    private static int endPosi = 0;  
    private static int beginArray[];  
    private static int endArray[];  
    private static String htmlTextArray[];  
    private static boolean tblExist = false;      
  
    /**
     * 远程Word转HTML
     * 
     * @author xutao 
     * @date 2016年12月8日 下午2:56:42 
     * @param fileUrl
     * @return
     * @throws Exception String
     */
    public static String convertRemoteWordToHTML(String fileUrl) throws Exception {
    	URL url = new URL(fileUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(300 * 1000);
		InputStream inputStream = conn.getInputStream(); 
		String htmlText = "";
		if (isWord2003(fileUrl)) {
			htmlText = convertDocToHTML(fileUrl, inputStream);  
    	} else if (isWord2007(fileUrl)) {
    		htmlText = convertDocxToHTML(fileUrl, inputStream);
    	} else {
			htmlText = "文档格式错误";
		}	
		return htmlText;
    }
    
    /**
     * 本地Word转HTML
     * 
     * @author xutao 
     * @date 2016年12月7日 下午4:50:19 
     * @param filePath
     * @return String
     */    
     public static String convertLocalWordToHTML(String filePath) throws Exception {
    	FileInputStream inputStream = new FileInputStream(new File(filePath)); 
    	String htmlText = "";
    	if (isWord2003(filePath)) {
    		htmlText = convertDocToHTML(filePath, inputStream);  
    	} else if (isWord2007(filePath)) {
    		htmlText = convertDocxToHTML(filePath, inputStream);  
    	} else {
			htmlText = "文档格式错误";
		}
		return htmlText;
    }
    
    /** 
     * 读取每个文字样式 
     */    
    private static String convertDocToHTML(String fileUrl, InputStream inputStream) throws Exception {  		
    	String htmlText = "";
        HWPFDocument doc = new HWPFDocument(inputStream);  
        // 得到文档读取范围  
        Range tbRange = doc.getRange();           
  
        int num = 100;    
        beginArray = new int[num];  
        endArray = new int[num];  
        htmlTextArray = new String[num];  
        tblExist = false;  
  
        // 得到文档中字符总数  
        int length = doc.characterLength();  
 
        // 创建段落容器    
        htmlText = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>"  
                + "借款合同" 
                + "</title></head><body><div style='margin:60px;text-align:center;'><div style='width:620px;text-align:left;line-height:24px;'>";  
  
        TableIterator it = new TableIterator(tbRange);
        if (it.hasNext()) {  
            readTable(it, tbRange);  
        }  
  
        int cur = 0;  
        String tempString = "";  
        for (int i = 0; i < length - 1; i++) {  
            // 整篇的字符通过一个个字符的来判断,range为得到文档的范围  
            Range range = new Range(i, i + 1, doc);  
            CharacterRun cr = range.getCharacterRun(0);   
            if (tblExist) {  
                if (i == beginArray[cur]) {  
                    htmlText += tempString + htmlTextArray[cur];  
                    tempString = "";  
                    i = endArray[cur] - 1;  
                    cur++;  
                    continue;  
                }  
            }    
  
            Range range2 = new Range(i + 1, i + 2, doc);  
            // 第二个字符  
            CharacterRun cr2 = range2.getCharacterRun(0);  
            char c = cr.text().charAt(0);    
            
            if (c == ENTER_ASCII) { // 回车符  
                tempString += "<br/>";  
            } else if (c == SPACE_ASCII) { // 空格符  
                tempString += " ";  
            } else if (c == TABULATION_ASCII) { // 水平制表符
                tempString += "    ";  
            }
            // 比较前后2个字符是否具有相同的格式  
            boolean flag = compareCharStyle(cr, cr2);  
            if (flag) {  
                tempString += cr.text();  
            } else {  
                String fontStyle = "<span style=\"font-family:" + cr.getFontName() + ";font-size:"  
                        + cr.getFontSize() / 2 + "pt;";    
                if (cr.isBold()) { 
                	fontStyle += "font-weight:bold;";
                } 
                if (cr.isItalic()) { 
                    fontStyle += "font-style:italic;";  
                }
                if (cr.isStrikeThrough())  
                    fontStyle += "text-decoration:line-through;";   
                    int fontcolor = cr.getIco24();  
                    int[] rgb = new int[3];  
                    if (fontcolor != -1) {  
                        rgb[0] = (fontcolor >> 0) & 0xff; // red;  
                    rgb[1] = (fontcolor >> 8) & 0xff; // green  
                    rgb[2] = (fontcolor >> 16) & 0xff; // blue  
                }  
                fontStyle += "color: rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ");";  
                htmlText += fontStyle + "\">" + tempString + cr.text() + "</span>";  
                tempString = "";  
            }             
        }    
        htmlText += tempString + "</div></div></body></html>";  
        return htmlText;
    }  
    
    /**
     * WORD2007(docx)转HTML
     * 
     * @author xutao 
     * @date 2016年12月8日 下午2:52:40 
     * @param fileUrl
     * @return
     * @throws Exception String
     */
    private static String convertDocxToHTML(String fileUrl, InputStream inputStream) throws Exception {
    	try {
    		XWPFDocument document = new XWPFDocument(inputStream);
//    		File imageFolderFile = new File("D:/test");  
            XHTMLOptions options = XHTMLOptions.create();/*.URIResolver(  
                    new FileURIResolver(imageFolderFile)); */
//            options.setExtractor(new FileImageExtractor(imageFolderFile));  
            
    		OutputStream outputStream = new ByteArrayOutputStream();   	
//    		OutputStream out = new FileOutputStream(new File("D:\\out.html"));
    	    XHTMLConverter.getInstance().convert(document, outputStream, options);        	    
    	    String htmlText = outputStream.toString(); 
    	    outputStream.close();  
    	    return htmlText; 
    	    
//    		List<XWPFParagraph> paras = document.getParagraphs();  
//    		for (XWPFParagraph para : paras) { 
//    			int i = 0;
//    			for (XWPFRun r : para.getRuns()) {
//    				i+=1;
//    		        System.out.println(i+":"+r.getCTR());
//    		        System.out.println(i+":"+"~~Fontsize:"+r.getFontSize()+",CharacterSpacing:"+r.getCharacterSpacing()
//    		        +",Color:"+r.getColor()+",FontFamily:"+r.getFontFamily()+",FontName:"+r.getFontName());
//    			}
//    			System.out.println(para.getText());
//    			System.out.println(para.getVerticalAlignment());
//    			System.out.println(para.getAlignment());
//    		}
//    		document.close();
            
         } finally {
             
         }
    }
  
    /** 
     * 读写文档中的表格 
     */  
    private static void readTable(TableIterator it, Range tbRange) throws Exception {    
        htmlTextTbl = "";  
        // 迭代文档中的表格    
        counter = -1;  
        while (it.hasNext()) {  
            tblExist = true;  
            htmlTextTbl = "";  
            Table tb = (Table) it.next();  
            beginPosi = tb.getStartOffset();  
            endPosi = tb.getEndOffset();  
  
            counter = counter + 1;  
            // 迭代行，默认从0开始  
            beginArray[counter] = beginPosi;  
            endArray[counter] = endPosi;  
  
            htmlTextTbl += "<table border='1' cellpadding='0' cellspacing='0' >";  
            for (int i = 0; i < tb.numRows(); i++) {  
                TableRow tr = tb.getRow(i);  
  
                htmlTextTbl += "<tr align='center'>";  
                // 迭代列，默认从0开始  
                for (int j = 0; j < tr.numCells(); j++) {  
                    TableCell td = tr.getCell(j);// 取得单元格  
                    int cellWidth = td.getWidth();  
  
                    // 取得单元格的内容  
                    for (int k = 0; k < td.numParagraphs(); k++) {  
                        Paragraph para = td.getParagraph(k);  
                        CharacterRun crTemp = para.getCharacterRun(0);  
                        String fontStyle = "<span style=\"font-family:" + crTemp.getFontName() + ";font-size:"  
                                + crTemp.getFontSize() / 2 + "pt;color:" + crTemp.getColor() + ";";  
  
                        if (crTemp.isBold())  
                            fontStyle += "font-weight:bold;";  
                        if (crTemp.isItalic())  
                            fontStyle += "font-style:italic;";  
  
                        String s = fontStyle + "\">" + para.text().toString().trim() + "</span>";  
                        if (s == "") {  
                            s = " ";  
                        }  
                        htmlTextTbl += "<td width=" + cellWidth + ">" + s + "</td>";  
                    } 
                } 
            }  
            htmlTextTbl += "</table>";  
            htmlTextArray[counter] = htmlTextTbl;    
        } 
    }  
  
  
    private static boolean compareCharStyle(CharacterRun cr1, CharacterRun cr2) {  
        boolean flag = false;  
        if (cr1.isBold() == cr2.isBold() && cr1.isItalic() == cr2.isItalic()  
                && cr1.getFontName().equals(cr2.getFontName()) && cr1.getFontSize() == cr2.getFontSize()) {  
            flag = true;  
        }  
        return flag;  
    }  
  
    /** 
     * 写入文件
     */  
    private static boolean writeFile(String s, String filePath) {  
        FileOutputStream fos = null;  
        BufferedWriter bw = null;  
        s = s.replaceAll("EMBED", "").replaceAll("Equation.DSMT4", "");  
        try {  
            File file = new File(filePath);  
            if (file.exists()) {  
                return false;  
            }  
            fos = new FileOutputStream(file);  
            bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));  
            bw.write(s);  
        } catch (FileNotFoundException fnfe) {  
            fnfe.printStackTrace();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } finally {  
            try {  
                if (bw != null)  
                    bw.close();  
                if (fos != null)  
                    fos.close();  
            } catch (IOException ie) {  
                ie.printStackTrace();  
            }  
        }  
        return true;  
    }  
    
    public static boolean isWord2003(String fileName) {  
    	return fileName.matches("^.+\\.(?i)(doc)$");     
     }  
    
    public static boolean isWord2007(String fileName) {    
        return fileName.matches("^.+\\.(?i)(docx)$");   
    }  
    
    
    public static void main(String argv[]) {  
        try {  
            String docHtml = convertLocalWordToHTML("D:\\2.doc");  
            String docxHtml = convertLocalWordToHTML("D:\\3.docx");
            writeFile(docHtml, "D:\\doc.html");
            writeFile(docxHtml, "D:\\docx.html");
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  