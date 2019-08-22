<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <title>Document</title>
    <style>
        .borPadding {
            padding:20px 20px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
		.checkbox_item {
			line-height: 36px;
			margin-left: 126px!important;
		}
    </style>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="borPadding">
	<form class="layui-form layui-form-pane" name="form1" id="form1" action="<%=basePath%>personnel/${msg }.do" method="post">
		<input type="hidden" name="ids" id="ids" value=""/>
		<input type="hidden" name="ROLE_ID" id="id" value="${pd.ROLE_ID}"/>
		<input name="PARENT_ID" id="parent_id" value="${pd.parent_id }" type="hidden">
			<div class="layui-form-item" id="receive">
				<label class="layui-form-label" style="margin:0;text-align: left">角色名称:</label>
				<div class="layui-input-block">
					<input type="text" name="ROLE_NAME" id="roleName" placeholder="这里输入角色名" value="${pd.ROLE_NAME}"  title="名称" autocomplete="off" class="layui-input"/>
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
			    <label class="layui-form-label" style="margin-bottom:0px;">角色描述</label>
			    <div class="layui-input-block">
			      <textarea placeholder="请输入内容" class="layui-textarea" name="ROLE_DESCRIBE" style="min-height:70px" id="role_describe" onkeyup="onText()" maxlength="50">${pd.ROLE_DESCRIBE }</textarea>
					<p style="text-align: right">描述限数：<span id="textarea_num">0</span>/50</p>
			    </div>
			</div>
			<div class="layui-item">
				<p>设置角色菜单权限：<font color="red">未勾选则无权限查看</font></p>			
						${sb}
			</div>
	</form>
</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
	<script type="text/javascript">
	top.hangge();
	//保存
	layui.use(["form","layer"],function(){
		var form = layui.form,layer = layui.layer;
	})
	function onText(){
		var num = $("#role_describe").val().length;
		$("#textarea_num").html(num)
    }
	var baocun = function(){
        //总菜单
		var checked = [];
		//一级
		var parent_arr=[];
		//二级
		var two = [];
		
        $("input:checkbox[name='limit']:checked").each(function(i){
            checked[i] = $(this).attr('data-id');
			var type = $(this).data("type");
			if(type != ""){
				two[i] = type;
			}
        })
		$.unique(two);
		$("input:checkbox[name='limit']:checked").parent().each(function(i){
			parent_arr[i] = $(this).attr('data-id');
		})
		checked.push(parent_arr);
		checked.push(two);

		$("#ids").val(checked);
		
		if($("#roleName").val().trim()==""){
			layer.tips('角色名不能为空', '#roleName', {
    			tips: 3
    		});
			$("#roleName").focus();
			return false;
		}else if($("#role_describe").val().trim() == ""){
            layer.tips('角色描述不能为空', '#role_describe', {
                tips: 3
            });
            $("#role_describe").focus();
            return false;
		}else if(checked == ""){
            layer.msg('请设置角色权限');
            return false;
		}
		if('${msg }'=='add'){
		var flag=false;
		var roleName = $.trim($("#roleName").val());
			$.ajax({
				async:false,
				type: "POST",
				url: '<%=basePath%>personnel/haha.do',
		    	data: {ROLE_NAME:roleName},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						layer.tips('角色名不可重复！', '#roleName', {
	    					tips: 3
	    				});
						flag=true;
					 }
					}
			});
		if(flag){
			return false;
		}
		}
		$("#form1").submit();
		var data = {
            msg : true
		};
		return data;
	}
	</script>
</body>
</html>