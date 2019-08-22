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
					<form action="dictenty/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="DICTENTY_ID" id="DICTENTY_ID" value="${pd.DICTENTY_ID}"/>
						<input type="hidden" name="DICT_TYPE" id="DICT_TYPE" value="${pd.dictType}"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">字典码:</td>
								<td><input type="text" name="DICT_CODE" id="DICT_CODE" value="${pd.DICT_CODE}" maxlength="30" placeholder="这里输入字典码" title="字典码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">字典编码值:</td>
								<td><input type="text" name="DICT_VALUE" id="DICT_VALUE" value="${pd.DICT_VALUE}" maxlength="100" placeholder="这里输入字典编码值" title="字典编码值" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">字典排序:</td>
								<td><input type="number" name="DICT_ORDER" id="DICT_ORDER" value="${pd.DICT_ORDER}" maxlength="32" placeholder="这里输入字典排序" title="字典排序" style="width:98%;"/></td>
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
			if($("#DICT_CODE").val()==""){
				$("#DICT_CODE").tips({
					side:3,
		            msg:'请输入字典码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DICT_CODE").focus();
			return false;
			}
			if($("#DICT_VALUE").val()==""){
				$("#DICT_VALUE").tips({
					side:3,
		            msg:'请输入字典编码值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DICT_VALUE").focus();
			return false;
			}
			if($("#DICT_ORDER").val()==""){
				$("#DICT_ORDER").tips({
					side:3,
		            msg:'请输入字典排序',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DICT_ORDER").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			return true;
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>