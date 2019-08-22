<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.lk.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

	request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	
	String rootPath = application.getRealPath( "/" );
	String result = new ActionEnter( request, rootPath ).exec();
	System.out.println("result:===="+result);
	String action = request.getParameter("action");
	if(action.equals("listimage") || action.equals("listfile")){
		rootPath = rootPath.replace("\\", "/");
		//System.out.println("rootPath=====:"+rootPath);
		
        result = result.replaceAll(rootPath, "/");
        //System.out.println("result----"+result);
	}
	out.write( result );

	
%>