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
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
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
					
					<form action="integralchange/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="INTEGRALCHANGE_ID" id="INTEGRALCHANGE_ID" value="${pd.INTEGRALCHANGE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">积分数量:</td>
								<td><input type="number" name="CONSUME" id="CONSUME" value="${pd.CONSUME}" maxlength="32" placeholder="这里输入积分数量" title="积分数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">变化类型:</td>
								<td><input type="text" name="COMSUME_TYPE" id="COMSUME_TYPE" value="${pd.COMSUME_TYPE}" maxlength="4" placeholder="这里输入变化类型" title="变化类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">变化类别:</td>
								<td><input type="text" name="COMSUME_STATE" id="COMSUME_STATE" value="${pd.COMSUME_STATE}" maxlength="4" placeholder="这里输入变化类别" title="变化类别" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">变化前积分:</td>
								<td><input type="number" name="COSUME_START" id="COSUME_START" value="${pd.COSUME_START}" maxlength="32" placeholder="这里输入变化前积分" title="变化前积分" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">变化后积分:</td>
								<td><input type="number" name="COMSUME_END" id="COMSUME_END" value="${pd.COMSUME_END}" maxlength="32" placeholder="这里输入变化后积分" title="变化后积分" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
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
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#CONSUME").val()==""){
				$("#CONSUME").tips({
					side:3,
		            msg:'请输入积分数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CONSUME").focus();
			return false;
			}
			if($("#COMSUME_TYPE").val()==""){
				$("#COMSUME_TYPE").tips({
					side:3,
		            msg:'请输入变化类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COMSUME_TYPE").focus();
			return false;
			}
			if($("#COMSUME_STATE").val()==""){
				$("#COMSUME_STATE").tips({
					side:3,
		            msg:'请输入变化类别',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COMSUME_STATE").focus();
			return false;
			}
			if($("#COSUME_START").val()==""){
				$("#COSUME_START").tips({
					side:3,
		            msg:'请输入变化前积分',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COSUME_START").focus();
			return false;
			}
			if($("#COMSUME_END").val()==""){
				$("#COMSUME_END").tips({
					side:3,
		            msg:'请输入变化后积分',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COMSUME_END").focus();
			return false;
			}
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