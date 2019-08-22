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
           		<form class="layui-form" action="rechargeReport/excel.do" method="post" id="excel_form">
					<input type="hidden"  name="state" id="state" value="${pd.state}">
            		<div class="demoTable table-seach">
 						<div class="layui-inline">
   					 		<input class="layui-input" name="keywords" id="keywords" autocomplete="off" placeholder="关键词搜索">
 					 	</div>
 					 	<div class="layui-inline">
							<label class="layui-form-label">门店：</label>
							<div class="layui-input-inline">
								<select name="store_id" lay-search="" lay-filter="filter" id="store_id">
									<option value="">选择或搜索</option>
									<option value="">全部</option>
									<c:forEach items="${storeList}" var="var" varStatus="vs">
										<option value="${var.STORE_ID}" >${var.STORE_NAME}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						 <div class="layui-inline" style="padding-left:10px;">
						    <div class="layui-input-inline">
						        <input type="text" class="layui-input" id="startTime" placeholder="开始时间" name="startTime" readonly>
						     </div>
						     -
						     <div class="layui-input-inline">
						        <input type="text" class="layui-input" id="endTime" placeholder="结束时间" name="endTime" readonly>
						     </div>
					     </div>
					     <div class="layui-inline">
							<label class="layui-form-label">支付状态：</label>
							<div class="layui-input-inline">
								<select lay-search="" lay-filter="filter" id="pay_state_jsp" name="pay_state_jsp">
									<option value=""></option>
									<option value="">全部</option>
									<c:if test="${pd.state == 'admin'}">
										<option value="2" >支付成功</option>
										<option value="a" >充值成功</option>
										<option value="b" >充值处理中</option>
									</c:if>
									<c:if test="${pd.state == 'internet'}">
										<option value="b" >充值处理中</option>
										<option value="a" >充值成功</option>
									</c:if>
								</select>
							</div>
						</div>
						
					    <c:if test="${QX.cha == 1 }">
					 	<a class="btn btn-xm btn-success" data-type="search" id="toSearch" style="margin-left: 10px;">
					 		<i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
					 	</a>
					 	<a class="btn btn-xm btn-primary" data-type="Excel" id="Excel" onclick="exal()" style="margin-left: 10px;">
					 		<i class="layui-icon" style="padding-right: 4px">&#xe61f;</i>导出EXCEL
					 	</a>
					 	<a class="btn btn-xm btn-warning" data-type="SUM" style="margin-left: 10px;" id="pay_count">
					 		<i class="layui-icon" style="padding-right: 4px">&#xe65e;</i>金额统计
					 	</a>
					 	</c:if>
				   </div>
				   </form>
			 </div>
             <div style="padding:12px">
                	<table id="rechargeReport_list" lay-filter="pay"></table>
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
	<script src="<%=basePath%>newStyle/js/lk-rechargeReport.js"></script>
	<script type="text/javascript">
	
	//导出excel
	function exal(){
		console.log($("#excel_form").attr("id"));
		$("#excel_form").submit();
	}
	var state = $("#state").val();
	$("#pay_count").on("click",function(){
		layer.open({
			btn: ['关闭'],
			skin: 'demo-class',
    		btnAlign: 'c',
    		type: 2,
    		title: '金额统计',
    		shadeClose: false,
    		shade: 0.8,
    		area: ['540px', '530px'],
    		content:[ '<%=basePath%>rechargeReport/listManage.do?state='+state+'&falg=SUM'],
		});
	})
		
	
	</script>
</body>
</html>