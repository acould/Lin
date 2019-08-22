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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="address=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>${intenetName}</title>
<link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/base.css">
<script type="text/javascript" src="<%=basePath%>assets/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/fastclick.js"></script>
<link rel="stylesheet" href="<%=basePath%>assets/js/dialog.css">
<script type="text/javascript" src="<%=basePath%>assets/js/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/iscroll.js"></script>
<link rel="stylesheet" href="<%=basePath%>assets/js/swiper-3.2.7.min.css">
<script type="text/javascript" src="<%=basePath%>assets/js/swiper-3.2.7.jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/mobile.js"></script>
</head>
<style>
	.layui-m-layerbtn {border-top: 1px solid #eee!important;border-radius: 0 0 2px 2px!important;}
</style>
<body style="background-color:#f3f3f3;">
	<div class="wrap">
		<ul class="g-list g-list4">
			<c:choose>
				<c:when test="${not empty varList}">			
					<c:forEach items="${varList}" var="var" varStatus="vs">
						<li class="flex-box">
							<div class="info">
								<p class="tit">投诉门店：${var.LM_STROE_NAME}</p>
								<p class="p5">投诉类型：${var.LM_TYPENAME}</p>
								<p class="p5">投诉日期：${var.LM_DATE}</p>
							</div>
							<c:if test="${var.LM_STATE =='2'}">
								<div class="btn-act">待反馈</div>
							</c:if>
							<c:if test="${var.LM_STATE =='1'}">
								<div class="activebox" onclick="ckhf('${var.LM_ID}')">
									<div class="btn-act btn-disabled" >已反馈</div>
									<p class="p2">查看网吧反馈</p>
								</div>
							</c:if>
							</li>
					</c:forEach>
				</c:when>
				
				<c:when test="${empty varList}">
					<div class="loading_more">
					<div class="gray">暂无数据</div>
					</div>
				</c:when>
			</c:choose>
		</ul>
	</div>
</body>

<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script>
   
 function ckhf(id){
	 var _this=this;
	 $.ajax({
        type: "POST",
        url:'<%=basePath%>myMember/ckhf.do',
		data : {LM_ID : id},
		dataType:'json',
		success : function(data) {
			//$.alert("反馈信息:" + data.message);
			layer.open({
				title: [
				  '反馈信息:',
				  'background-color: #3a95ff; color:#fff;height:45px;line-height:45px;border-radius: 2px 2px 0 0;'
				]
				,btn: '我知道了'
				,content: data.message
				,anim: "scale"
			  });
		},
		error : function() {
			$.alert("系统繁忙，请稍后再试");
			//removeDis();
		}
	});

	};

	function go() {
		window.location.reload();
	};
</script>

<script type="text/javascript">
	$('#upfile input')
			.change(
					function(evt) {
						var files = evt.target.files;
						for ( var i = 0, f; f = files[i]; i++) {
							if (!f.type.match('image.*'))
								continue;

							var reader = new FileReader();
							reader.onload = (function(theFile) {
								return function(e) {
									$('#upfile')
											.before(
													'<div class="imgli flex-box"><img src="'+e.target.result+'" title="'+theFile.name+'" /><input name="upimg" value="'+e.target.result+'" type="hidden" /><i class="icon-roundclose"></i></div>')
								}
							})(f);
							reader.readAsDataURL(f);
						}
					}).click(function(e) {
				e.stopPropagation()
			})
	$('#upfile').click(function() {
		$(this).find('input').trigger('click')
	})
	$('body').on({
		click : function() {
			$(this).parent().remove()
		}
	}, '.upimgbox .imgli i')
</script>


</html>
