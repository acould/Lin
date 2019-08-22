<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>

<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="icon" type="text/css"
	href="<%=basePath%>newStyle/images/lk-icon.png">
<title>网吧管理后台</title>
<style>
body {
	background-color: #f6f8f9;
	min-width: 600px;
	overflow: auto
}
</style>
</head>

<body class="scroll">
	<div class="card-header">
		<div class="card-header-msg">
			<img src="<%=basePath%>newStyle/images/logo2.png" class="card-lankeIcon"></img><span
				class="card-header-title">网吧管理后台</span>
			<div class="card-header-right">
				<img alt="" src="/static/ace/avatars/user.jpg" class=""> <span
					class="card-userName layui-elip"> <small>Welcome</small><br>
					${pd.INTENET_NAME}
				</span>
			</div>
		</div>
	</div>
	<div class="card-width">
		<h1>
			当前开通计费系统<span style="font-weight: 300;">—</span><span>${pd.STORE_NAME}</span>
		</h1>
		<div class="lanke-pbMsg">
			<div class="lanke-pbMsgSide">
				<fieldset class="layui-elem-field layui-field-title" style="">
					<legend>填写绑定信息</legend>
				</fieldset>
				<form class="layui-form layui-form-pane" action="" name="Form"
					id="Form">
					<div class="layui-form-item">
						<label class="layui-form-label">计费系统：</label>
						<div class="layui-input-block">
							<input type="text" name="title" class="layui-input"
								value="Pubwin OL" disabled>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>企业名称：</label>
						<div class="layui-input-block">
							<input type="hidden" name="STORE_ID" id="STORE_ID"
								value="${pd.STORE_ID}" /> <input type="hidden" name="STATE"
								id="STATE" value="${pd.STATE}" />
							<c:if test="${pd.STATE!=1}">
								<input type="text" name="Company_Name" id="Company_Name"
									class="layui-input" value="${map.Company_Name}"
									placeholder="请输入当前门店在计费Pubwin OL上注册的企业全称">
							</c:if>
							<c:if test="${pd.STATE==1}">
								<input type="text" name="Company_Name" id="Company_Name"
									class="layui-input" value="${map.Company_Name}"
									placeholder="请输入当前门店在计费Pubwin OL上注册的企业全称" readonly="readonly">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>用户ID：</label>
						<div class="layui-input-block">
							<c:if test="${pd.STATE!=1}">
								<input type="text" name="pubwin_store_id" id="pubwin_store_id"
									class="layui-input" value="${map.pubwin_store_id}"
									placeholder="请输入当前门店Pubwin OL的ID">
							</c:if>
							<c:if test="${pd.STATE==1}">
								<input type="text" name="pubwin_store_id" id="pubwin_store_id"
									class="layui-input" value="${map.pubwin_store_id}"
									placeholder="请输入当前门店Pubwin OL的ID" readonly="readonly">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>门店IP地址：</label>
						<div class="layui-input-block">
							<input type="text" name="pubwin_ip" id="pubwin_ip"
								class="layui-input" value="${map.pubwin_ip}"
								placeholder="请输入当前门店的计费服务器IP地址">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">手机号码：</label>
						<div class="layui-input-block">
							<c:if test="${pd.STATE!=1}">
								<input type="text" name="STORE_PHONE" id="STORE_PHONE"
									class="layui-input" value="${map.STORE_PHONE}"
									pattern="^1[3,5,6,7,8,9][3,5,6,7,8,9]\d{8}$" onblur="hasP()"
									placeholder="如不填，审核结果将发送到揽客绑定手机">
							</c:if>
							<c:if test="${pd.STATE==1}">
								<input type="text" name="STORE_PHONE" id="STORE_PHONE"
									class="layui-input" value="${map.STORE_PHONE}"
									placeholder="如不填，审核结果将发送到揽客绑定手机" readonly="readonly">
							</c:if>
						</div>
					</div>
					<c:if test="${pd.STATE!=1}">
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">验证码：</label>
								<div class="layui-input-inline">
									<input type="tel" name="code" id="code"
										lay-verify="required|code" autocomplete="off"
										class="layui-input">
								</div>
								<button class="layui-btn layui-btn-primary user-btn-yzm">
									<input type="button" id="btn" value="获取验证码" onclick="return verification()" style="background:none;border:none;"/>
									</button>
							</div>
						</div>
					</c:if>
				</form>
			</div>
			<div class="layui-form-item" style="margin: 20px 0 30px 20px">
				<button class="layui-btn layui-btn-normal" lay-submit=""
					lay-filter="demo2" onclick="return baocun()">提交</button>
			</div>
			<c:if test="${pd.STATE!=0}">
				<div class="lanke-audit">
					<fieldset class="layui-elem-field layui-field-title" style="">
						<legend>审核日志</legend>
					</fieldset>
					<c:forEach items="${List}" var="var" varStatus="vs">
						<c:if test="${var.ASTATE==0}">
							<ul class="layui-timeline">
								<li class="layui-timeline-item"><i
									class="layui-icon layui-timeline-axis">&#xe63f;</i>
									<div class="layui-timeline-content layui-text">
										<div class="layui-timeline-title">
											<h2>审核未通过</h2>
											<span class="layui-badge-rim">${var.TIME}</span>
										</div>
										<ul>
											<li>失败原因为:${var.Note}</li>
										</ul>
									</div></li>
							</ul>
						</c:if>
						<c:if test="${var.ASTATE==1}">
							<ul class="layui-timeline">
								<li class="layui-timeline-item"><i
									class="layui-icon layui-timeline-axis">&#xe63f;</i>
									<div class="layui-timeline-content layui-text">
										<div class="layui-timeline-title">
											<h2>审核通过</h2>
											<span class="layui-badge-rim">${var.TIME}</span>
										</div>
										<ul>
											<li>备注:${var.Note}</li>
										</ul>
									</div></li>
							</ul>
						</c:if>
					</c:forEach>
				</div>
			</c:if>
		</div>
	</div>

	<div class="lanke-footer">
		© <a href="http://lanke8.cc">lanke8.cc</a>&nbsp;揽客
	</div>
	<script>
		//判断验证码是否发送成功
		function verification(){
			if($("#STORE_PHONE").val()==""){
				layer.tips('请输入手机号', '#STORE_PHONE', {
				 	tips: 3
		        });
				return false;
			}
		
			var PHONE = $.trim($("#STORE_PHONE").val());
			$.ajax({
				type: "post",
				url: '<%=basePath%>register/getcodeA.do',
		    	data: {PHONE:PHONE},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("error" == data.result){
					 	layer.tips('手机号码格式不匹配，请重试..', '#STORE_PHONE', {
				 			tips: 3
		        		});
					 }else if("frequent"==data.result){
					 	layer.tips('发送验证码太频繁，请稍候再试!!', '#btn', {
				 			tips: 3
		        		});
					 }else if("success"==data.result){
						 var countdown=60; 
						 var obj = $("#btn");
						 settime(obj);
						 function settime(obj) { //发送验证码倒计时
						     if (countdown == 0) { 
						         obj.attr('disabled',false); 
						         //obj.removeattr("disabled"); 
						         obj.val("免费获取验证码");
						         countdown = 60; 
						         return;
						     } else { 
						         obj.attr('disabled',true);
						         obj.val("发送成功,重新发送(" + countdown + ")");
						         countdown--;
						     } 
						 setTimeout(function() { 
						     settime(obj) }
						     ,1000);
						 }
					 }else if("fail"==data.result){
					 	layer.tips('发送失败，请重试..', '#btn', {
				 			tips: 3
		        		});
					 }
				}
			});
			return false;
		}
		
		//保存
		function baocun() {
			if ($("#Company_Name").val() == "") {
				layer.tips('请输入当前门店在计费Pubwin OL上注册的企业全称', '#Company_Name', {
					tips : 3
				});
				return false;
			}

			if ($("#pubwin_store_id").val() == "") {
				layer.tips('请输入当前门店Pubwin OL的ID', '#pubwin_store_id', {
					tips : 3
				});
				return false;
			}

			if ($("#pubwin_ip").val() == "") {
				layer.tips('请输入当前门店的计费服务器IP地址', '#pubwin_ip', {
					tips : 3
				});
				return false;
			}
			
			if ($("#STORE_PHONE").val() !== "") {
				var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
					if($("#STORE_PHONE").val().length != 11 && !myreg.test($("#STORE_PHONE").val())){
						layer.tips('手机号格式不正确', '#STORE_PHONE', {
					 		tips: 3
			        	});
						return false;
					}
					else{
						if ($("#code").val() == "") {
							layer.tips('请输入验证码', '#code', {
								tips : 3
							});
							return false;
						}
					}
			var flag=false;
			if($("code").val()!=""||$("#code").val()!=null){ /* 验证验证码是否正确 */
				var yzm = $.trim($("#code").val());
				var PHONE = $.trim($("#STORE_PHONE").val());
				$.ajax({
					async:false,
					type: "POST",
					url: '<%=basePath%>register/hasY.do',
			    	data: {yzm:yzm,PHONE:PHONE},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" != data.result){
							layer.tips('输入验证码错误', '#code', {
				 				tips: 3
		        			});
							flag=true;
						 }
					}
				});
			}}
			if(flag){
				return false;
			}
			$.ajax({
				type: "POST",
				url: "<%=basePath%>storePrivilege/save.do",
				data : $('#Form').serialize(),
				dataType : 'json',
				success : function(data) {
					layer.msg(data.message, {
						time : 1000
					});
					if (data.result == "true") {
						setTimeout(function() {
							//关闭当前页面
							window.opener = null;
							window.open("", "_self");
							window.close();
						}, 700);
						opener.location.reload();//刷新父页面
					}
				},
				error : function() {
					layer.msg("系统繁忙，请稍后再试！");
					return false;
				}
			});
		}
	</script>
</body>
</html>