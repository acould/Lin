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
					
					<form action="scene/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="SCENE_ID" id="SCENE_ID" value="${pd.SCENE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
						<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">场景运用名称:</td>
								<td><input type="text" name="SCENE_NAME" id="SCENE_NAME" value="${pd.SCENE_NAME}" maxlength="10" placeholder="这里输入场景运用名称" title="场景运用名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">优惠类型:</td>
								<td>
								<select name="FAV_TYPE" id="FAV_TYPE" style="width:98%;">
								  <option>--请选择--</option>
								    <c:forEach items="${listSce}" var="var" varStatus="vs">
									  <option value="${var.DICT_CODE}" <c:if test="${pd.FAV_TYPE ==var.DICT_CODE}">selected="selected"</c:if>>${var.DICT_VALUE }</option>
									</c:forEach>
								</select>
								</td>
							</tr>
							
						<%-- 	<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">福利类型:</td>
								<td>
							      <select name="BENEFIT_TYPE" id="BENEFIT_TYPE" style="width:98%;">
								    <option>--请选择--</option>
									  <c:forEach items="${listFl}" var="var" varStatus="vs">
									    <option value="${var.DICT_CODE}" <c:if test="${pd.BENEFIT_TYPE ==var.DICT_CODE}">selected="selected"</c:if>>${var.DICT_VALUE }</option>
									  </c:forEach>
								 </select>
								</td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">领取数量:</td>
								<td><input type="number" name="RECEIVE_NUMBER" id="RECEIVE_NUMBER" value="${pd.RECEIVE_NUMBER}" maxlength="10" placeholder="这里输入领取数量" title="领取数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">领取间隔:</td>
								<td>
								<input type="number" name="BLANK_NUMBER" id="BLANK_NUMBER" value="${pd.BLANK_NUMBER}" maxlength="10" placeholder="这里输入间隔数量" title="这里输入间隔数量" style="width:48%;"/>
							      <select name="RECEIVE_DETIL" id="RECEIVE_DETIL" style="width:48%;">
								    <option>--请选择--</option>
									  <c:forEach items="${listJg}" var="var" varStatus="vs">
									    <option value="${var.DICT_CODE}" <c:if test="${pd.RECEIVE_DETIL ==var.DICT_CODE}">selected="selected"</c:if>>${var.DICT_VALUE }</option>
									  </c:forEach>
								 </select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否领取限制:</td>
								<td>
								<select name="RECEIVE_STATE" id="RECEIVE_STATE" style="width:98%;">
									<option>--请选择--</option>
									<c:forEach items="${listHx}" var="var" varStatus="vs">
									  	<option value="${var.DICT_CODE}" <c:if test="${pd.RECEIVE_STATE ==var.DICT_CODE}">selected="selected"</c:if>>${var.DICT_VALUE }</option> 
									</c:forEach>
								</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否核销限制:</td>
								<td>
								<select name="CANCEL_STATE" id="CANCEL_STATE" style="width:98%;">
									<option>--请选择--</option>
									<c:forEach items="${listHx}" var="var" varStatus="vs">
									  	<option value="${var.DICT_CODE}" <c:if test="${pd.CANCEL_STATE ==var.DICT_CODE}">selected="selected"</c:if>>${var.DICT_VALUE }</option>
									</c:forEach>
								</select></td>
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
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="<%=basePath%>static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#SCENE_NAME").val()==""){
				$("#SCENE_NAME").tips({
					side:3,
		            msg:'请输入场景运用名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SCENE_NAME").focus();
			return false;
			}
			if($("#FAV_TYPE").val()=="" || $("#FAV_TYPE").val()=="--请选择--"){
				$("#FAV_TYPE").tips({
					side:3,
		            msg:'请选择优惠类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FAV_TYPE").focus();
			return false;
			}
			if($("#BENEFIT_TYPE").val()==""){
				$("#BENEFIT_TYPE").tips({
					side:3,
		            msg:'请输入福利类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BENEFIT_TYPE").focus();
			return false;
			}
			if($("#RECEIVE_NUMBER").val()==""){
				$("#RECEIVE_NUMBER").tips({
					side:3,
		            msg:'请输入领取类型对应的数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#RECEIVE_NUMBER").focus();
			return false;
			}
			if($("#RECEIVE_NUMBER").val() < 1){
				$("#RECEIVE_NUMBER").tips({
					side:3,
		            msg:'请输入有效的领取类型对应的数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#RECEIVE_NUMBER").focus();
			return false;
			}
			if($("#BLANK_NUMBER").val()==""){
				$("#BLANK_NUMBER").tips({
					side:3,
		            msg:'请输入领取间隔',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BLANK_NUMBER").focus();
			return false;
			}
			if($("#BLANK_NUMBER").val() < 1){
				$("#BLANK_NUMBER").tips({
					side:3,
		            msg:'请输入有效的领取间隔',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BLANK_NUMBER").focus();
			return false;
			}
			if($("#RECEIVE_DETIL").val()=="" || $("#RECEIVE_DETIL").val()=="--请选择--"){
				$("#RECEIVE_DETIL").tips({
					side:3,
		            msg:'请选择领取详细说明',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#RECEIVE_DETIL").focus();
			return false;
			}
			if($("#RECEIVE_STATE").val()=="" || $("#RECEIVE_STATE").val()=="--请选择--"){
				$("#RECEIVE_STATE").tips({
					side:3,
		            msg:'请选择是否领取限制',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#RECEIVE_STATE").focus();
			return false;
			}
			if($("#CANCEL_STATE").val()=="" || $("#CANCEL_STATE").val()=="--请选择--"){
				$("#CANCEL_STATE").tips({
					side:3,
		            msg:'请选择是否核销限制',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CANCEL_STATE").focus();
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