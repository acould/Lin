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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<!-- jsp文件头和头部 -->
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css"
	media="all">
<link rel="stylesheet"
	href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
<style>
.layui-form-pane .introduce {
	width: 100%;
	text-align: left;
	margin-left: 0px;
	padding-left: 20px;
	position: relative;
}

.bnt-add {
	font-weight: 500;
	float: right;
	padding: 3px 10px;
	border-radius: 3px;
	position: relative;
	top: -4px;
	cursor: pointer;
}

.pro_select {
	width: 100px !important;
	margin: 0 !important;
	position: absolute;
	right: 0;
	top: 0;
}

.relative {
	position: relative
}
</style>
<body>
	<form class="layui-form layui-form-pane" id="Form">
		<input type="hidden" id="version_id" name="version_id" value="${pd.id}"> 
		<c:if test="${empty pd.sum}"><input type="hidden" id="sum" name="sum" value="0"></c:if>
		<c:if test="${not empty pd.sum}"><input type="hidden" id="sum" name="sum" value="${pd.sum}"></c:if>
		<input type="hidden" id="type" name="type" value="${pd.type}">
		<div style="padding: 20px 30px" id="htmls">
			<div class="layui-form-item">
				<label class="layui-form-label">版本号：</label>
				<div class="layui-input-block">
					<input type="text" id="version" name="version" autocomplete="off"
						placeholder="请输入标题" class="layui-input" value="${pd.version}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">上线时间：</label>
				<div class="layui-input-block">
					<input type="text" id="updatetime" name="updatetime" autocomplete="off" placeholder="请选择时间" class="layui-input" value="${pd.updatetime}">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-form-label introduce">
					版本介绍： <a class="bnt-add layui-bg-orange" id="add_select"> <i
						class="layui-icon" style="padding-right: 4px">&#xe654;</i>添加
					</a>
				</div>
			</div>

			<c:if test="${pd.type == 'add'}">
				<div class="layui-form-item relative">
					<div class="layui-input-block" style="margin: 0 110px 0 0">
						<input type="text" name="content1" autocomplete="off"
							placeholder="请逐条填写..." class="layui-input">
					</div>
					<div class="layui-input-inline pro_select">
						<select name="state1">
							<option value="">选择类型</option>
							<option value="0">新增</option>
							<option value="1">优化</option>
							<option value="2">修复</option>
						</select>
					</div>
				</div>
			</c:if>

			<c:if test="${pd.type == 'edit'}">
				<c:choose>
					<c:when test="${not empty list}">
						<c:forEach items="${list}" var="l" varStatus="vs">
							<div class="layui-form-item relative">
								<div class="layui-input-block" style="margin: 0 110px 0 0">
									<input type="text" name="content${vs.index+1}" autocomplete="off"
										placeholder="请逐条填写..." class="layui-input"
										value="${l.content}">
								</div>
								<div class="layui-input-inline pro_select">
									<select name="state${vs.index+1}">
										<option value="">选择类型</option>
										<option value="0"
											<c:if test="${l.type == '0'}">selected="selected"</c:if>>新增</option>
										<option value="1"
											<c:if test="${l.type == '1'}">selected="selected"</c:if>>优化</option>
										<option value="2"
											<c:if test="${l.type == '2'}">selected="selected"</c:if>>修复</option>
									</select>
								</div>
							</div>
						</c:forEach>
					</c:when>
				</c:choose>
			</c:if>
		</div>
	</form>
</body>
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script type="text/javascript">
	$(top.hangge());//关闭加载状态
	var sum = $("#sum").val();
	var type = '${pd.type}'; 
	if(sum == 0){
		sum = 2;
	}
	if(type == 'edit'){
		sum=parseInt(sum)+1;
	}
	layui.use([ 'layer', 'form', 'laydate' ],
		function() {var $ = layui.$, layer = layui.layer, form = layui.form, laydate = layui.laydate
			laydate.render({elem : '#updatetime'});
			$("#add_select").click(function() {
				var hmtls = '<div class="layui-form-item relative">'
					+ '<div class="layui-input-block" style="margin: 0 110px 0 0">'
					+ '<input type="text" name="content'+sum+'" autocomplete="off" placeholder="请逐条填写..." class="layui-input">'
					+ '</div>'
					+ '<div class="layui-input-inline pro_select">'
					+ '<select name="state'+sum+'">'
					+ '<option value="">选择类型</option>'
					+ '<option value="0">新增</option>'
					+ '<option value="1">优化</option>'
					+ '<option value="2">修复</option>'
					+ '</select>'
					+ '</div>'
					+ '</div>'
					$("#htmls").append(hmtls);
					form.render('select');
					$("#sum").val(sum);
					sum = sum+1;
			})
			
		})

	//页面校正
	function check() {
		var version = $("#version").val();
		var updatetime = $("#updatetime").val();
		var element_id= ["#version","#updatetime"];
		var element_tip =["版本号不能为空","上线时间不能为空"];
		for (var i = 0; i < element_id.length; i++) {
			if($(element_id[i]).val()==""){
				layer.msg(element_tip[i]);
				return false;
			}
		}
		var sum = $("#sum").val();
		if(sum == 0){
			if($("input[name='content1']").val() == ""){
				layer.msg("版本介绍不能为空");
				return false;
			}
			if($("select[name='state1']").val() == ""){
				layer.msg("版本介绍类型不能为空");
				return false;
			}
		}else{
			for (var i = 1; i <= sum; i++) {
				if($("input[name='content"+i+"']").val() == ""){
					layer.msg("版本介绍不能为空");
					return false;
				}
				if($("select[name='state"+i+"']").val() == ""){
					layer.msg("版本介绍类型不能为空");
					return false;
				}
			}
		}
		
		return true;
	}

	function store_submit() {
		//服务器校正保存方法
		 if (!check()) {
			return false;
		}
		$.ajax({
			async : false,
			type : "POST",
			url : '<%=basePath%>/productNews/editVersion.do',
			data : $('#Form').serialize(),
			dataType : 'json',
			success : function(data) {
				layer.msg(data.message, {
					time : data.message.length * 200
				}, function() {
					if ("true" == data.result) {
						layer.closeAll();
						parent.location.reload();//刷新父页面	   
					}
				});
			},
			error : function() {
				layer.msg("系统繁忙，请稍后再试！");
			}
		});
	}
</script>
</html>