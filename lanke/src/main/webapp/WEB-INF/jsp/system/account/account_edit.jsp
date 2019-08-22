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
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>

<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<title>Document</title>
<style>
.borPadding {
	padding: 20px 30px;
}

.layui-form-checkbox[lay-skin=primary] span {
	padding-right: 6px;
}

.demo-class .layui-layer-btn {
	border-top: 1px solid #E9E7E7;
	background-color: #fafafa
}
</style>
</head>
<body class="no-skin scroll">
	<!-- /section:basics/navbar.layout -->
	<div class="borPadding">
		<form class="layui-form layui-form-pane" name="From" id="From"
			method="post" action="">
			<input type="hidden" name="USER_ID" id="USER_ID"
				value="${pd.USER_ID}" />
				<input type="hidden"  id="ids" name="ids" value="" />
			<div class="layui-form-item">
				<label class="layui-form-label" style="margin: 0">姓名:</label>
				<div class="layui-input-block">
					<input type="text" name="NAME" id="NAME" value="${pd.NAME}"
						autocomplete="off" placeholder="这里输入姓名" maxlength="10"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="margin: 0">手机登录:</label>
				<div class="layui-input-block">
					<input type="text" name="USER_NAME" id="USER_NAME"
						value="${pd.USERNAME}" autocomplete="off" maxlength="11"
						class="layui-input" placeholder="请输入用于登录的手机号码" title="手机号登录">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="margin: 0">门店:</label>
				<div class="layui-input-block" id="idss">
				<c:forEach items="${plist}" var="var" varStatus="vs">
				<input type="checkbox" data-type="" value="${var.STORE_ID }" ${var.falg} name="STORE_IDs" id="STORE_IDs" lay-skin="primary" title="${var.STORE_NAME }">
				</c:forEach>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="margin: 0">角色:</label>
				<div class="layui-input-block" id="role_id">
					<select class="chosen-select form-control" name="ROLE_ID"
						id="ROLE_ID" data-placeholder="请选择"
						style="vertical-align: top; width: 120px;">
						<option value="">请选择</option>
						<c:forEach items="${userlist}" var="var" varStatus="vs">
							<option value="${var.ROLE_ID}"
								<c:if test="${pd.ROLE_ID == var.ROLE_ID}">selected="selected"</c:if>>${var.ROLE_NAME }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="margin: 0">登录密码:</label>
				<div class="layui-input-block">
					<input type="password" name="POSSWARD" id="POSSWARD" value=""
						autocomplete="off" maxlength="20" class="layui-input"
						placeholder="请输入登录密码" title="账号密码">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="margin: 0">确认密码:</label>
				<div class="layui-input-block">
					<input type="password" name="chkpwd" id="chkpwd" value=""
						autocomplete="off" maxlength="20" class="layui-input"
						placeholder="请再次输入登录密码" title="确认密码">
				</div>
			</div>
		</form>
	</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		
		//保存
		var baocun = function(){
			
			var checked = [];
			$("input:checkbox[name='STORE_IDs']:checked").each(function(i){
				checked[i] = $(this).attr('value');
			})
		    $("#ids").val(checked);
			
			
			 if($("#NAME").val()==""){
	        	layer.tips('请输入姓名', '#NAME', {
	        		tips: 3
	        	});
	        return false;
	        }
			 
	        if($("#USER_NAME").val()==""){
	        	layer.tips('输入手机号', '#USER_NAME', {
	        		tips: 3
	        	});
	        	
				$("#USER_NAME").val('');
				$("#USER_NAME").css("background-color","white");
				return false;
			}else{
				$("#USER_NAME").val(jQuery.trim($('#USER_NAME').val()));
			}
	        
			if($("#STORE_ID").val()==""){
		        layer.tips('请选择门店', '#store_id', {
	        		tips: 3
	        	});
				return false;
			}
			 if($("#ROLE_ID").val()==""){
			 	layer.tips('请选择角色', '#role_id', {
			 		tips: 3
	        	});
				return false;
				}
			 
			 //判断有无密码
			 if($("#POSSWARD").val()=="" && $("#chkpwd").val() != ""){
			 	layer.tips('输入密码', '#POSSWARD', {
			 		tips: 3
	        	});
				return false;
				}
			 if($("#POSSWARD").val().length>0 && $("#POSSWARD").val().length<6||$("#POSSWARD").val().length>20){
					layer.tips('密码长度6-20个字符', '#POSSWARD', {
					 	tips: 3
			        });
					return false;
				}
			 
		if ($("#chkpwd").val() == "" && $("#POSSWARD").val()!="") {
				layer.tips('输入确认密码', '#chkpwd', {
					tips : 3
				});
				$("#chkpwd").val('');
				$("#chkpwd").css("background-color", "white");
				return false;
			}
			if ($("#POSSWARD").val() != $("#chkpwd").val()) {
				layer.tips('两次密码不相同', '#chkpwd', {
					tips : 3
				});
				$("#chkpwd").focus();
				return false;
			}
			
			//判断手机号是否重复
			var USER_NAME = $.trim($("#USER_NAME").val());
			var STATE='${STATE}';
			var USER_ID='${pd.USER_ID}';
			if (USER_NAME != "") {
				$.ajax({
					type : "POST",
					url : '<%=basePath%>account/hasU.do',
						data : {USER_NAME : USER_NAME,STATE	:STATE,USER_ID:USER_ID},
						dataType : 'json',
						cache : false,
						success : function(data) {
							if ("error" == data.result) {
								layer.tips('该手机号已存在', '#USER_NAME', {
					        		tips: 3
					        	});
								return false;
							}
							if ("error1" == data.result) {
								layer.tips('手机格式不正确', '#USER_NAME', {
					        		tips: 3
					        	});
								return false;
							}
							else{
						
								$.ajax({
									type: "POST",
									url: '<%=basePath%>account/${msg }.do',
								    data : $('#From').serialize(),
								    dataType : 'json',
								    cache : false,
								    success : function(data) {
									    layer.msg(data.message, {
										time : data.message.length * 200
									}, function() {
										if ("true" == data.result) {
											parent.location.reload();
										}
									});
								}
							});
						}
					}
				});
			}

		}

		layui.use('form', function() {
			var form = layui.form;
			//监听提交
			form.on('submit(formDemo)', function(data) {
				layer.msg(JSON.stringify(data.field));
				return false;
			});
		});
	</script>
</body>
</html>