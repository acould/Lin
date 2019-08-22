<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lk" uri="/WEB-INF/tld/lk-sys.tld" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tld/fns.tld" %>
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
					
					<form action="sysuser/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="SYSUSER_ID" id="SYSUSER_ID" value="${pd.SYSUSER_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户id:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="16" placeholder="这里输入用户id" title="用户id" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">姓名:</td>
								<td><input type="text" name="USER_NAME" id="USER_NAME" value="${pd.USER_NAME}" maxlength="255" placeholder="这里输入姓名" title="姓名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">机构号:</td>
								<td><input type="text" name="ANGET_ID" id="ANGET_ID" value="${pd.ANGET_ID}" maxlength="255" placeholder="这里输入机构号" title="机构号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">登录密码:</td>
								<td><input type="text" name="PASSWORD" id="PASSWORD" value="${pd.PASSWORD}" maxlength="255" placeholder="这里输入登录密码" title="登录密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">最后登入时间:</td>
								<td><input class="span10 date-picker" name="LAST_LOGIN" id="LAST_LOGIN" value="${pd.LAST_LOGIN}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="最后登入时间" title="最后登入时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">登入ip:</td>
								<td><input type="text" name="IP" id="IP" value="${pd.IP}" maxlength="30" placeholder="这里输入登入ip" title="登入ip" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="2" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">积分:</td>
								<td><input type="number" name="INTEGRAL" id="INTEGRAL" value="${pd.INTEGRAL}" maxlength="32" placeholder="这里输入积分" title="积分" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">网吧id:</td>
								<td><input type="text" name="INTEGER_ID" id="INTEGER_ID" value="${pd.INTEGER_ID}" maxlength="40" placeholder="这里输入网吧id" title="网吧id" style="width:98%;"/></td>
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
			if($("#USER_ID").val()==""){
				$("#USER_ID").tips({
					side:3,
		            msg:'请输入用户id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_ID").focus();
			return false;
			}
			if($("#USER_NAME").val()==""){
				$("#USER_NAME").tips({
					side:3,
		            msg:'请输入姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_NAME").focus();
			return false;
			}
			if($("#ANGET_ID").val()==""){
				$("#ANGET_ID").tips({
					side:3,
		            msg:'请输入机构号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ANGET_ID").focus();
			return false;
			}
			if($("#PASSWORD").val()==""){
				$("#PASSWORD").tips({
					side:3,
		            msg:'请输入登录密码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PASSWORD").focus();
			return false;
			}
			if($("#LAST_LOGIN").val()==""){
				$("#LAST_LOGIN").tips({
					side:3,
		            msg:'请输入最后登入时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LAST_LOGIN").focus();
			return false;
			}
			if($("#IP").val()==""){
				$("#IP").tips({
					side:3,
		            msg:'请输入登入ip',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#IP").focus();
			return false;
			}
			if($("#STATUS").val()==""){
				$("#STATUS").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATUS").focus();
			return false;
			}
			if($("#INTEGRAL").val()==""){
				$("#INTEGRAL").tips({
					side:3,
		            msg:'请输入积分',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#INTEGRAL").focus();
			return false;
			}
			if($("#INTEGER_ID").val()==""){
				$("#INTEGER_ID").tips({
					side:3,
		            msg:'请输入网吧id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#INTEGER_ID").focus();
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