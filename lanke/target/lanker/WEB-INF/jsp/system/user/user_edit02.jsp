<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
</head>
<body class="no-skin" style="padding: 26px;">
	<!-- /section:basics/navbar.layout -->
	
							<form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="" enctype="multipart/form-data" >
								
								
								<input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID }"/>
								<div class="layui-form-item">
									<label class="layui-form-label" style="margin:0">用户名:</label>
									<div class="layui-input-block">
										<input type="text" name="USERNAME" id="USERNAME" value="${pd.USERNAME }" maxlength="20" placeholder="请输入用户名" autocomplete="off" class="layui-input" readonly="readonly"/>
									</div>
								</div>
								
								<div class="layui-form-item">
									<label class="layui-form-label" style="margin:0">姓名:</label>
									<div class="layui-input-block">
										<input type="text" name="NAME" id="NAME" value="${pd.NAME }" maxlength="20" placeholder="请输入姓名" autocomplete="off" class="layui-input"/>
									</div>
								</div>
								<!--
								<c:if test="${fx != 'head'}">
									<div class="layui-form-item">
										<label class="layui-form-label" style="margin:0">角色:</label>
										<div class="layui-input-block">
											<select name="ROLE_ID" id="ROLE_ID" style="width:98%;">
												<c:forEach items="${roleList}" var="role">
													<option value="${role.ROLE_ID}" <c:if test="${role.ROLE_ID == pd.ROLE_ID}">selected="selected"</c:if>>${role.ROLE_NAME } </option>
												</c:forEach>
												
												
											</select>
										</div>
									</div>
								</c:if>
								<c:if test="${fx == 'head'}">
									<input name="ROLE_ID" id="ROLE_ID" value="${pd.ROLE_ID }" type="hidden" />
								</c:if>
								
								<div class="layui-form-item">
									<label class="layui-form-label" style="margin:0">手机号:</label>
									<div class="layui-input-block">
										<input type="text" name="PHONE" id="PHONE" value="${pd.PHONE }" maxlength="20" placeholder="请输入手机号" autocomplete="off" class="layui-input" readonly="readonly"/>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label" style="margin:0">备注:</label>
									<div class="layui-input-block">
										<input type="text" name="BZ" id="BZ" value="${pd.BZ }" maxlength="20" placeholder="请输入姓名" autocomplete="off" class="layui-input" readonly="readonly"/>
									</div>
								</div>
								-->
								
								<div class="layui-form-item">
									<label class="layui-form-label" style="margin:0">原密码:</label>
									<div class="layui-input-block">
										<input type="password" name="OLD_PASSWORD" id="OLD_PASSWORD" value="" maxlength="20" placeholder="请输入原密码" autocomplete="off" class="layui-input" />
									</div>
								</div>
								
								<div class="layui-form-item">
									<label class="layui-form-label" style="margin:0">新密码:</label>
									<div class="layui-input-block">
										<input type="password" name="PASSWORD" id="PASSWORD" value="" maxlength="20" placeholder="请输入新密码" autocomplete="off" class="layui-input" />
									</div>
								</div>
								
								<div class="layui-form-item">
									<label class="layui-form-label" style="margin:0">确认密码:</label>
									<div class="layui-input-block">
										<input type="password" name="chkpwd" id="chkpwd" value="" maxlength="20" placeholder="请输入确认密码" autocomplete="off" class="layui-input" onblur="checkPsd()"/>
									</div>
								</div>
								
							</form>
								
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	
	//保存
	function save(){
		if(!check()){
			return false;
		}
		
		$.ajax({
			type	: "POST",
			url		: "<%=basePath%>user/editU.do",
			data	: $('#Form').serialize(),
			dataType:'json',
			async: false,
			beforeSend: beforeSend,
			success: function(data) {
				layer.close(layer.index);
				layer.msg(data.message,{time:data.message.length*200});
				if(data.result == "true"){
					setTimeout(function () { 
						parent.location.reload();
					},700)
				}
			},
			error:function(){
				layer.close(layer.index);
				layer.msg("系统繁忙，请稍后再试！",{time:1500});
				return false;
			}
		});
	}
	function newPsd(){
		if($("#PASSWORD").val().length < 6){
			layer.msg("新密码至少6位",{time:1000});
			$("#PASSWORD").focus();
			return false;
		}
	}
	
	//判断俩次密码是否相同
	function checkPsd(){
		if($("#PASSWORD").val() == ''){
			layer.msg("请输入新密码",{time:1000});
			return false;
		}
		if($("#PASSWORD").val().length < 6){
			layer.msg("新密码至少6位",{time:1000});
			$("#PASSWORD").focus();
			return false;
		}
		
		if($("#chkpwd").val() == ''){
			layer.msg("请输入确认密码",{time:1000});
			return false;
		}
		if($("#PASSWORD").val() != $("#chkpwd").val()){
			layer.msg("两次密码输入不一致",{time:1500});
			return false;
		}
		return true;
	}
	
	function check(){
		if($("#OLD_PASSWORD").val() == ''){
			layer.msg("请输入原密码",{time:1000});
			return false;
		}
		
		
		
		if(!checkPsd()){
			return false;
		}
		
		
		return true;
	}
	function beforeSend(){
		layer.load(0, {shade: false});
	}
	
</script>
</html>