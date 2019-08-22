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
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<title>Document</title>
<style>
.layui-form-checkbox[lay-skin=primary] span { padding-right: 6px; }
.layui-icon { font-size: 14px; }
.laydate-theme-blue  .layui-this { background-color: #41a7f0!important; }
body {background-color: #fff;padding: 6px 30px;}
</style>
</head>
<body class="no-skin scroll">
	<div class="layui-tab-content">
	<input type="hidden"  name="state" id="state" value="${pd.state}">
         	<form class="layui-form">
          		<div class="demoTable table-seach">
					<div class="layui-inline">
 					 	<input class="layui-input" name="keywords" id="keywords" autocomplete="off" placeholder="关键词搜索">
				 	</div>
				 	<div class="layui-inline">
					<label class="layui-form-label">门店：</label>
					<div class="layui-input-inline">
						<select name="modules" lay-search="" lay-filter="filter" id="store_id">
							<option value="">选择或搜索</option>
							<option value="">全部</option>
							<c:forEach items="${storeList}" var="var" varStatus="vs">
								<option value="${var.STORE_ID}" ${pd.STORE_ID == var.STORE_ID?"selected":""}>${var.STORE_NAME}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				 <div class="layui-inline" style="padding-top:12px;">
				    <div class="layui-input-inline">
				        <input type="text" class="layui-input" id="startTime" placeholder="开始时间" name="startTime" readonly>
				     </div>
				     -
				     <div class="layui-input-inline">
				        <input type="text" class="layui-input" id="endTime" placeholder="结束时间" name="endTime" readonly>
				     </div>
			     </div>
			 	<a class="btn btn-xm btn-success" data-type="search" id="toSearch" style="margin:12px 0 0 10px;">
			 		<i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
			 	</a>
		   </div>
		</form>
	 </div>
	 <div class="lk-pay-count">
	 	<div class="rincipal_balance_box">
	 		<h2>充值金额</h2>
	 		<h1 id="rincipal_balance">￥${pds.rincipal_sum}</h1>
	 	</div>
	 	<div class="reward_balance_box">
	 		<h2>奖励金额</h2>
	 		<h1 id="reward_balance">￥${pds.reward_sum}</h1>
	 	</div>
	 </div>
	
	<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
	<!-- 业务js -->
	<script>
		layui.use(['form','layer','laydate'], function(){
	  		var form = layui.form;
	  		var laydate = layui.laydate;
	  		
	  		 //日期范围
	  		laydate.render({
	    		elem: '#startTime'
                ,type: 'datetime'
	  		});
	  		laydate.render({
	    		elem: '#endTime'
                ,type: 'datetime'
	  		});
		});
		
		$("#toSearch").click(function(){
			var	keywords= $("#keywords").val();
			var	store_id= $("#store_id").val();
			var	startTime=$("#startTime").val();
			var	endTime=$("#endTime").val();
			var	state=$("#state").val();
			$.ajax({
				async:false,
    			type: "POST",
    			url: '<%=basePath%>rechargeReport/toCountManage.do',
    	    	data: {store_id:store_id,state:state,keywords:keywords,startTime:startTime,endTime:endTime},
    			dataType:'json',
    			cache: false,
    			success: function(data){
    				$('#rincipal_balance').html("￥"+data.rincipal_sum+".0");
    				$('#reward_balance').html("￥"+data.reward_sum+".0");
    			}
    		});
		})
		
	</script>
</body>
</html>