package com.lk.entity;

import com.lk.util.Const;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

/**
 * 分页类
 * @author 洪智鹏
 * 创建时间：2014年6月28日
 */
public class Page {
	
	private int showCount; //每页显示记录数
	private int totalPage;		//总页数
	private int totalResult;	//总记录数
	private int currentPage;	//当前页
	private int currentResult;	//当前记录起始索引
	private boolean entityOrField;	//true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private String pageStr;		//最终页面显示的底部翻页导航，详细见：getPageStr();
	private PageData pd = new PageData();
	

	
	public Page(){
		try {
			this.showCount = Integer.parseInt(Tools.readTxtFile(Const.PAGE));
		} catch (Exception e) {
			this.showCount = 10;
		}
	}
	
	public int getTotalPage() {
		if(totalResult%showCount==0)
			totalPage = totalResult/showCount;
		else
			totalPage = totalResult/showCount+1;
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getTotalResult() {
		return totalResult;
	}
	
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	
	public int getCurrentPage() {
		if(currentPage<=0)
			currentPage = 1;
		if(currentPage>getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	//拼接分页 页面及JS函数
	public String getPageStr() {
		StringBuffer sb = new StringBuffer();
		if(totalResult>0){
			sb.append("	<ul class=\"pagination pull-right no-margin\">\n");
			if(currentPage==1){
				sb.append("	<li><a style=\"cursor:pointer;font-size:12px;\">共<font color=red>"+totalResult+"</font>条</a></li>\n");
				sb.append("	<li><input type=\"number\" min=\"0\" max=\""+totalPage+"\" value=\"\" id=\"toGoPage\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" "
						+ "style=\"width:80px;padding:6px 12px;height:31px;text-align:center;float:left\" placeholder=\"页码\"/></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"toTZ();\"  class=\"btn btn-mini\" style=\"padding:6px 12px;margin: 0 -1px;font-size:12px;\">跳转</a></li>\n");
				sb.append("	<li ><a style=\"cursor:pointer;font-size:12px;\">首页</a></li>\n");
				sb.append("	<li><a style=\"cursor:pointer;font-size:12px;\">上页</a></li>\n");
			}else{
				sb.append("	<li><a style=\"font-size:12px;\">共<font color=red>"+totalResult+"</font>条</a></li>\n");
				sb.append("	<li><input type=\"number\" min=\"0\" max=\""+totalPage+"\" value=\"\" id=\"toGoPage\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" "
						+ "style=\"width:80px;padding:6px 12px;height:31px;text-align:center;float:left;\" placeholder=\"页码\"/></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"toTZ();\"  class=\"btn btn-mini\" style=\"padding:6px 12px;margin: 0 -1px;font-size:12px;\">跳转</a></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage(1)\" style=\"font-size:12px;\">首页</a></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+(currentPage-1)+")\" style=\"font-size:12px;\">上页</a></li>\n");
			}
			int showTag = 5;//分页标签显示数量
			int startTag = 1;
			int endTag = 5;
			if(currentPage >= 4){
				startTag = currentPage-2;
				endTag = startTag+4;
			}
			if((currentPage > totalPage - 2) && (totalPage-2) > 0 && (totalPage > 5)){
				startTag = totalPage -4;
				endTag = totalPage;
			}
			
			
			
			for(int i=startTag; i<=totalPage && i<=endTag; i++){
				if(currentPage==i)
					sb.append("<li class=\"active\"><a style=\"cursor:pointer;font-size:12px;\"><font color='white'>"+i+"</font></a></li>\n");
				else
					sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+i+")\" style=\"cursor:pointer;font-size:12px;\">"+i+"</a></li>\n");
			}
			if(currentPage==totalPage){
				sb.append("	<li><a style=\"font-size:12px;\">下页</a></li>\n");
				sb.append("	<li><a style=\"font-size:12px;\">尾页</a></li>\n");
			}else{
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+(currentPage+1)+")\" style=\"font-size:12px;\">下页</a></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+totalPage+")\" style=\"font-size:12px;\">尾页</a></li>\n");
			}
			sb.append("	<li><a style=\"font-size:12px;\">共"+totalPage+"页</a></li>\n");
			sb.append("	<li><select title='显示条数' style=\"width:62px;padding:6px 12px;height: 31px;float:left;font-size:12px;\" onchange=\"changeCount(this.value)\">\n");
			sb.append("	<option value='"+showCount+"'>"+showCount+"</option>\n");
			sb.append("	<option value='10'>10</option>\n");
			sb.append("	<option value='20'>20</option>\n");
			sb.append("	<option value='30'>30</option>\n");
			sb.append("	<option value='40'>40</option>\n");
			sb.append("	<option value='50'>50</option>\n");
			sb.append("	<option value='60'>60</option>\n");
			sb.append("	<option value='70'>70</option>\n");
			sb.append("	<option value='80'>80</option>\n");
			sb.append("	<option value='90'>90</option>\n");
			sb.append("	<option value='99'>99</option>\n");
			sb.append("	</select>\n");
			sb.append("	</li>\n");
			
			sb.append("</ul>\n");
			sb.append("<script type=\"text/javascript\">\n");
			
			//换页函数
			sb.append("function nextPage(page){");
			sb.append(" top.jzts();");
			sb.append("	if(true && document.forms[0]){\n");
			sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + page + \"&" +(entityOrField?"showCount":"page.showCount")+"="+showCount+"\";\n");
			sb.append("		document.forms[0].action = url;\n");
			sb.append("		document.forms[0].submit();\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + page + \"&" +(entityOrField?"showCount":"page.showCount")+"="+showCount+"\";\n");
			sb.append("		document.location = url;\n");
			sb.append("	}\n");
			sb.append("}\n");
			
			//调整每页显示条数
			sb.append("function changeCount(value){");
			sb.append(" top.jzts();");
			sb.append("	if(true && document.forms[0]){\n");
			sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + \"1&" +(entityOrField?"showCount":"page.showCount")+"=\"+value;\n");
			sb.append("		document.forms[0].action = url;\n");
			sb.append("		document.forms[0].submit();\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"1&"+(entityOrField?"currentPage":"page.currentPage")+"=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + \"&" +(entityOrField?"showCount":"page.showCount")+"=\"+value;\n");
			sb.append("		document.location = url;\n");
			sb.append("	}\n");
			sb.append("}\n");
			
			//跳转函数 
			sb.append("function toTZ(){");
			sb.append("var toPaggeVlue = document.getElementById(\"toGoPage\").value;");
			sb.append("if(toPaggeVlue > "+totalPage+"){toPaggeVlue = "+totalPage+";}");
			sb.append("if(toPaggeVlue == ''){document.getElementById(\"toGoPage\").value=1;return;}");
			sb.append("if(isNaN(Number(toPaggeVlue))){document.getElementById(\"toGoPage\").value=1;return;}");
			sb.append("nextPage(toPaggeVlue);");
			sb.append("}\n");
			sb.append("</script>\n");
		}
		pageStr = sb.toString();
		return pageStr;
	}
	
	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}
	
	public int getShowCount() {
		return showCount;
	}
	
	public void setShowCount(int showCount) {
		
		this.showCount = showCount;
	}
	
	public int getCurrentResult() {
		currentResult = (getCurrentPage()-1)*getShowCount();
		if(currentResult<0)
			currentResult = 0;
		return currentResult;
	}
	
	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}
	
	public boolean isEntityOrField() {
		return entityOrField;
	}
	
	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}
	
	public PageData getPd() {
		return pd;
	}

	public void setPd(PageData pd) {
		if(!StringUtil.isEmpty(pd.get("currentPage"))){
			this.currentPage = Integer.parseInt(pd.get("currentPage").toString());
		}
		if(!StringUtil.isEmpty(pd.get("showCount"))){
			this.showCount = Integer.parseInt(pd.get("showCount").toString());
		}

		this.pd = pd;
	}

	@Override
	public String toString() {
		return "Page{" +
				"showCount=" + showCount +
				", totalPage=" + totalPage +
				", totalResult=" + totalResult +
				", currentPage=" + currentPage +
				", currentResult=" + currentResult +
				", entityOrField=" + entityOrField +
				", pageStr='" + pageStr + '\'' +
				", pd=" + pd +
				'}';
	}
}
