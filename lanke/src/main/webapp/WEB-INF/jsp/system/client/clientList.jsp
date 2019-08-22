<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
<title>Document</title>
<style>
.borPadding { padding: 20px 30px; }
.layui-form-checkbox[lay-skin=primary] span { padding-right: 6px; }
.layui-icon {font-size: 14px;}
.laydate-theme-blue  .layui-this { background-color: #41a7f0!important; }
</style>
</head>
<body class="scroll" style="background-color: #f2f2f2;">
		
 		<div class="lk-table-height">
			<div class="layui-tab-content">
           		<form class="layui-form" action="" method="post" id="excel_form">
           		<input type="hidden" id="state" value="${state}"/>
            		<div class="demoTable table-seach">
 						<div class="layui-inline">
   					 		<input class="layui-input" name="keywords" id="keywords" autocomplete="off" placeholder="关键词搜索">
 					 	</div>
					     <div class="layui-inline">
							<label class="layui-form-label">在线状态：</label>
							<div class="layui-input-inline">
								<select lay-search="" lay-filter="filter" id="online_state" name="online_state">
									<option value=""></option>
									<option value="">全部</option>
										<option value="在线" >在线</option>
										<option value="离线" >离线</option>
								</select>
							</div>
						</div>
					 	<a class="btn btn-xm btn-success" data-type="search" id="toSearch" style="margin-left: 10px;">
					 		<i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
					 	</a>
						<a class="btn btn-xm btn-success" onclick="setAllQrParam()" style="margin-left: 10px;">
							<i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>一键设置二维码
						</a>
				   </div>
				   </form>
			 </div>
             <div style="padding:12px">
                	<table id="client_list" lay-filter="pay"></table>
             </div>
             <div class="page-header position-relative" style="padding:8px 10px 16px 10px">
					<table style="width:100%;">
						<tr>
							<td style="vertical-align:top;">
								<div style="float: right;padding-top: 0px;margin-top: 0px;" id="page"></div>
							</td>
						</tr>
					</table>
				</div>
		</div>
		

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
 	<script type="text/html" id="createtime">
		{{# 
			if(d.createtime.indexOf(".") > 0){
				return d.createtime.split(".")[0]
			}else {
				return d.createtime
			}	
		}}
	</script>
	<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
	<!-- 业务js -->
	<script src="<%=basePath%>newStyle/js/lk-clientList.js"></script>
</body>
</html>