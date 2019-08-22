<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/style.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <title>图片库</title>
    <style>
        .imgDiv {
            width: 160px;
            height: 200px;
            overflow: hidden;
            margin: 10px;
        }
        .filerow {
            margin: 20px;
        }
    </style>
</head>
<body class="scroll">
    <div class="row filerow" id="ifrom">
    	<c:choose>
    		<c:when test="${not empty imgList }">
		    	<c:forEach items="${imgList }" var="var">
		            <div class="imgDiv clickBox scroll">
		                <img src="<%=basePath %>uploadFiles/uploadImgs/${var.PATH }" />
						<input id="${var.PICTURE_ID }" type="hidden" value="${var.PICTURE_ID }">
						<input id="${var.MEDIA_ID }" type="hidden" value="${var.MEDIA_ID }">
		            </div>

		    	</c:forEach>
    		</c:when>
    		<c:otherwise>
    			<div style="text-align: center;padding-top: 50px;">未上传图片</div>
    		</c:otherwise>
    	</c:choose>
    </div>
    <script>
        $('.clickBox').click(function () {
            $(this).toggleClass("selected")
            $($(this).siblings()).removeClass('selected');
        })
        var callbackdata = function () {
            var url = $(".selected").children()[0].src;
            var media_id = $(".selected").children(":last-child").val();
            var picture_id = $(".selected").children(":last-child").prev().val();
            var data = {
            	url : url,
            	media_id : media_id,
				picture_id : picture_id
            };
            return data;
        }
    </script>
</body>
</html>