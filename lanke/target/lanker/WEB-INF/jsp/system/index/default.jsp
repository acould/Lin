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
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/ace/css/bootstrap.css">

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
							<form action="login_default.do" method="post" name="Form"
								id="Form">
								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 18px;">
									<thead>
										<tr>

											<th class="center" style="width: 60px;">序号</th>
											<c:if test="${state == 1}">
												<th class="center">公众号名称</th>
												<th class="center">公众号appid</th>
												<th class="center">公众号的原始ID</th>
												<th class="center">公众号类型</th>
											</c:if>
											<c:if test="${state == 2}">
												<th class="center">分店名称</th>
											</c:if>
											<c:if test="${state == 3}">
												<th class="center">代理门店</th>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														<td class='center'
															style="width: 30px; vertical-align: middle;">${vs.index+1}</td>
														<c:if test="${state == 1}">
															<td class='center' style="vertical-align: middle;">${var.INTENET_NAME}</td>
															<td class='center' style="vertical-align: middle;">${var.WECHAT_ID}</td>
															<td class='center' style="vertical-align: middle;">${var.WECHAT_KET}</td>
															<td class='center' style="vertical-align: middle;">${var.STATE}</td>
														</c:if>
														<c:if test="${state == 2}">
															<td class='center' style="vertical-align: middle;">${var.STORE_NAME}</td>
														</c:if>
														<c:if test="${state == 3}">
															<td class='center' style="vertical-align: middle;">${var.STORE_NAME}</td>
														</c:if>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="100" class="center"
														style="vertical-align: middle;">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: middle;">
												<div class="pagination"
													style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
											</td>
										</tr>
									</table>
								</div>
							</form>
							
							<form action="accountSettings/toEdit" id="toShowFrom" name="toShowFrom" target="_blank" method="post">
			                <input type="hidden" id="store_id" name="STORE_ID" value="">
			                <input type="hidden" id="store_name" name="STORE_NAME" value="">
			                <input type="hidden" id="state1" name="STATE" value="">
			                </form>
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

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<script src="<%=basePath%>newStyle/layer/layer.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>

		<script type="text/javascript">
		loadMessage();
		function loadMessage(){
			$.ajax({
				type	: "POST",
				url		:'<%=basePath%>newerGuide/getMessage.do',
				data	:{},
				dataType:'json',
				beforeSend: beforeSend(''),
				success: function(data) {
					layer.close(layer.index);
					if(data.result == "true"){
						//返回结果正确时
						var varList = data.varList;
						var html = '';
						for(var i=0;i<varList.length;i++){
							var time = varList[i].announce_time;
							if(time.indexOf(".") > 0){
								time = time.split(" ")[0];
							}
							html += '<div class="lk-msg-body">'+
										'<h2>'+varList[i].title+'</h2>'+
										'<span class="lk-msg-timeline">'+time+'</span>'+
										'<p class="">'+varList[i].content+'</p >'+
									'</div>';
						}
						if(html != ''){
							top.layer.open({
								type: 1,
								title: false,
								closeBtn: 0,
								area: '360px',
								skin: 'layui-layer-nobg', //没有背景色
								shadeClose: false,
								offset: '20%',
								shade:0.7,
								content: '<div id="message-box">'+
											'<image src="<%=basePath%>newStyle/images/lk-msg-header.png" id="msg_img" width="100%">'+
											'<div class=" lk-message-content">'+
												'<div class="lk-message-list scroll">'+
													html+
												'</div>'+
												'<div class=" lk-message-btn">'+
													'<span class="lk-msg-close" onclick="msg_close()">暂时不了</span>'+
													'<span class="lk-msg-see" onclick="go_newMsg()">查看消息</span>'+
												'</div>'+
											'</div>'+
									   '</div>',
								move :"#msg_img"
							});
						}
						
					}
				},
				error:function(){
					layer.close(layer.index);
					message("系统繁忙，请稍后再试！");
					return;
				}
			});
			
		}
			
	</script>
	
	
</body>
</html>