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
	<form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="" enctype="multipart/form-data" >
								
		<input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID }"/>
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">用户名:</label>
			<div class="layui-input-block">
				<input type="text" name="USERNAME" id="USERNAME" value="${pd.USERNAME }" maxlength="20" placeholder="请输入用户名" autocomplete="off" class="layui-input"  <c:if test="${pd.USER_ID != null}">readonly="readonly"</c:if>/>
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">姓名:</label>
			<div class="layui-input-block">
				<input type="text" name="NAME" id="NAME" value="${pd.NAME }" maxlength="20" placeholder="请输入姓名" autocomplete="off" class="layui-input"/>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">角色:</label>
			<div class="layui-input-block">
				<select name="ROLE_ID" id="ROLE_ID" >
					<c:forEach items="${roleList}" var="role">
						<option value="${role.ROLE_ID}" <c:if test="${role.ROLE_ID == pd.ROLE_ID}">selected="selected"</c:if>>${role.ROLE_NAME } </option>
					</c:forEach>
					
				</select>
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">手机号:</label>
			<div class="layui-input-block">
				<input type="text" name="PHONE" id="PHONE" value="${pd.PHONE }" maxlength="20" placeholder="请输入手机号" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">邮箱:</label>
			<div class="layui-input-block">
				<input type="text" name="EMAIL" id="EMAIL" value="${pd.EMAIL }" maxlength="20" placeholder="请输入邮箱" autocomplete="off" class="layui-input" />
			</div>
		</div>
		
		
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">密码:</label>
			<div class="layui-input-block">
				<input type="password" name="PASSWORD" id="PASSWORD" value="" maxlength="20" placeholder="请输入密码" autocomplete="off" class="layui-input"/>
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">确认密码:</label>
			<div class="layui-input-block">
				<input type="password" name="chkpwd" id="chkpwd" value="" maxlength="20" placeholder="请输入确认密码" autocomplete="off" class="layui-input" onblur="checkPsd()"/>
			</div>
		</div>
		<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">备注:</label>
            	<div class="layui-input-block">
					<textarea class="layui-textarea" name="BZ" id="BZ" value="${pd.BZ}" maxlength="50" placeholder="最多输入50个字">${pd.BZ}</textarea>
				</div>
			</div>
		
	</form>
	<!-- /.main-container -->
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
	layui.use('form', function(){
		var form = layui.form;
		form.render();
	});
	$(top.hangge());
	//保存
	function baocun(){
		if(!check()){
			return false;
		}
		
		$.ajax({
			type	: "POST",
			url		: "<%=basePath%>user/saveU.do",
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
	
	function check(){
		
		if($("#USER_ID").val() == ''){
			if(!checkPsd()){
				return false;
			}
		}
		
 	    var reg = /^(?!\d{4,16}$)(?:[0-9a-zA-Z_]{4,16}|![\u4E00-\u9FA5])$/;
		if($("#USERNAME").val().trim() == ""){
			layer.msg("请输入用户名",{time:1000});
			$("#USERNAME").focus();
			return false;
		}else if(! reg.test($("#USERNAME").val().trim())){
			layer.msg("用户名格式不正确",{time:1000});
			$("#USERNAME").focus();
			return false;
		}
		
		if($("#NAME").val().trim() == ""){
			layer.msg("请输入姓名",{time:1000});
			$("#NAME").focus();
			return false;
		}
		if($("#ROLE_ID").val().trim() == ""){
			layer.msg("请选择用户角色",{time:1000});
			$("#ROLE_ID").focus();
			return false;
		}
		
		
		
		var phoneReg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
		if($("#PHONE").val().trim() == ""){
			layer.msg("请输入手机号",{time:1000});
			$("#PHONE").focus();
			return false;
		}else if(! phoneReg.test($("#PHONE").val().trim())){
			layer.msg("手机号格式不正确",{time:1000});
			$("#PHONE").focus();
			return false;
		}
		
		var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		if($("#EMAIL").val().trim() == ""){
			layer.msg("请输入邮箱",{time:1000});
			$("#EMAIL").focus();
			return false;
		}else if(! emailReg.test($("#EMAIL").val().trim())){
			layer.msg("邮箱格式不正确",{time:1000});
			$("#EMAIL").focus();
			return false;
		}
		
		return true;
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
		if($("#PASSWORD").val().trim() == ""){
			layer.msg("请输入密码",{time:1000});
			$("#PASSWORD").focus();
			return false;
		}
		if($("#PASSWORD").val().length < 6){
			layer.msg("新密码至少6位",{time:1000});
			$("#PASSWORD").focus();
			return false;
		}
		if($("#chkpwd").val().trim() == ""){
			layer.msg("请输入确认密码",{time:1000});
			$("#chkpwd").focus();
			return false;
		}
		
		if($("#PASSWORD").val().trim() != $("#chkpwd").val().trim()){
			layer.msg("两次密码输入不一致",{time:1000});
			$("#chkpwd").focus();
			return false;
		}
		
		return true;
	}
	function beforeSend(){
		layer.load(0, {shade: false});
	}
</script>
</html>