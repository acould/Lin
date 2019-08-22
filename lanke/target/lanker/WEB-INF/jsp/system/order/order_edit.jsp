<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="<%=basePath%>static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="order/${msg }.do?ORDER_ID=${pd.ORDER_ID}" name="Form" id="Form" method="post">
						<input type="hidden" name="Auction_ID" id="Auction_ID" value="${pd.Auction_ID}"/>
						<input type="hidden" name="ORDER_ID" id="ORDER_ID" value="${pd.ORDER_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
						<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">门店名称:</td>
								<td><input type="text" name="STORE_NAME" id="STORE_NAME" value="${pd.STORE_NAME}" maxlength="255" placeholder="这里门店名称" title="门店名称" style="width:98%;"/></td>
						</tr>
						<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">订单号:</td>
								<td><input type="text" name="ORDER_NUMBER" id="ORDER_NUMBER" value="${pd.ORDER_NUMBER}" maxlength="255" placeholder="这里订单号" title="订单号 " style="width:98%;"/></td>
						</tr>
						<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">购买时间:</td>
								<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="255" placeholder="这里购买时间" title="购买时间 " style="width:98%;"/></td>
						</tr>
						<tr>
							<td style="width:75px;text-align: right;padding-top: 13px;">发货方式:</td>
							<td>
								<select name="DELIVERY" id="DELIVERY" style="width:98%;">
									<option>请选择</option>
									<option value="1" <c:if test="${pd.DELIVERY =='1'}">selected="selected"</c:if>>到店提</option>
								</select>
							</td>	
						</tr>
						<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">会员昵称:</td>
								<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="255" placeholder="这里会员昵称" title="会员昵称 " style="width:98%;"/></td>
						</tr>
						<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">会员卡号:</td>
								<td><input type="text" name="CARDED" id="CARDED" value="${pd.CARDED}" maxlength="255" placeholder="这里会员卡号" title="会员卡号 " style="width:98%;"/></td>
						</tr>
						<tr>
							<td style="width:75px;text-align: right;padding-top: 13px;">订单状态:</td>
							<td>
								<select name="STATE" id="STATE" style="width:98%;">
									<option value="1" <c:if test="${pd.STATE =='1'}">selected="selected"</c:if>>未提货</option>
									<option value="2" <c:if test="${pd.STATE =='2'}">selected="selected"</c:if>>申请中</option>
									<option value="3" <c:if test="${pd.STATE =='3'}">selected="selected"</c:if>>提货成功</option>
								</select>
							</td>	
						</tr>
						<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">购买时间:</td>
								<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="255" placeholder="这里购买时间" title="购买时间 " style="width:98%;"/></td>
						</tr>
						<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">发货</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						
					</form>
	
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->



	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		//保存
		function save(){
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		</script>
</body>
</html>