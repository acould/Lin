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
						<form action="role/add.do" name="form1" id="form1"  method="post">
						<input type="hidden" name="ROLE_ID" id="id" value="${pd.ROLE_ID}"/>
						<input name="PARENT_ID" id="parent_id" value="${pd.parent_id }" type="hidden">
							<div id="zhongxin" style="padding-top:13px;">
							<table class="center" style="width:100%;">
								<tr style="text-align: center;">
									<td><input type="text" name="ROLE_NAME" id="roleName" placeholder="这里输入名称" value="${pd.ROLE_NAME}"  title="名称" style="width:99%;"/></td>
								</tr>
							</table>
							</div>
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
	<%@ include file="../index/foot.jsp"%>
	<!--提示框-->
	<script src="<%=basePath%>newStyle/layer/layer.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
	<script type="text/javascript">
	top.hangge();
	//保存
	function save(){
		if($("#roleName").val()==""){
			msg_tips("请输入","#roleName");
			$("#roleName").focus();
			return false;
		}
		$.ajax({
            type: "POST",
            url: '<%=basePath%>role/add.do',
            data: $('#form1').serialize(),
            dataType: 'json',
            cache: false,
            success: function (data) {
            	message(data.message);
                if (data.result == "true") {
                    setTimeout(function () {
                        parent.location.reload();
                    }, 800)
                }
            },
            error: function () {
            	message("系统繁忙，请稍后再试！");
                return false;
            }
        });
	}
	
	
	</script>
</body>
</html>

