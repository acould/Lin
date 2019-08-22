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
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/textimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/js/waterfall.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>图文库</title>
    <style>
        body {
            background: #fff;
        }
        #main{
            position: relative;
        }
        .pin{
            padding: 15px 0 0 15px;
            float:left;
            cursor: pointer;
        }
        .borBottom {
            border-bottom: none;
        }
        .box {
            position: relative;
            border: 1px solid #e1e1e1;
            background-color: #fff;
        }
        .alier {
            background: rgba(0,0,0,0.7);
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            display: none;
        }
        .rowBoxCenter {
            margin-top:0!important;
        }
        .lay-iconAuto {
            width: 40px;
            height: 43px;
            margin: auto;
            position: absolute;
            top: 0; left: 0; bottom: 0; right: 0;
            display: none;
        }
        .box .block {
            display: inline-block;
        }
        .box:hover .lay-iconAuto, .box:hover .alier {
            display: inline-block;
        }
        .lay-iconStyle {
            color:#fff;
            font-size: 40px;
            cursor: pointer;
        }
        .selected {
            animation:        selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
            -o-animation:     selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
            -ms-animation:    selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
            -moz-animation:   selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
            -webkit-animation: selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
        }
        .selected .lay-iconStyle{
            color:#00c09e;
        }

        @keyframes selected {
            0% { border-color: #fff; }
            50% { transform: scale(0.5); opacity: 0.8;}
            80%,100% {width: 40px;height: 43px;}
        }
        @-o-keyframes selected {
            0% { box-shadow: 0 0 0 4px #fff; }
            50% { -o-transform: scale(0.5); opacity: 0.8; }
            80%,100% {width: 40px;height: 43px;}
        }
        @-ms-keyframes selected {
            0% { box-shadow: 0 0 0 4px #fff; }
            50% {width: 40px;height: 43px; }
            80%,100% { width: 40px;height: 43px; }
        }
        @-moz-transition selected {
            0% { box-shadow: 0 0 0 4px #fff; }
            50% { -moz-transform: scale(0.5); opacity: 0.8; }
            80%,100% { width: 40px;height: 43px; }
        }
        @-webkit-keyframes selected {
            0% { box-shadow: 0 0 0 4px #fff; }
            50% { -webkit-transform: scale(0.5); opacity: 0.8;}
            80%,100% { width: 40px;height: 43px;}
        }
        .wdth {
            width:100%;
            margin-top: 50px;
        }
        .inputbox {
            height: 49px;
            padding-top: 10px;
            padding-left: 10px;
        }
        .sreach {
            font-size: 18px;
            padding-left: 10px;
            cursor: pointer;
        }
        .topfixed {
            width:100%;
            background-color: #fff;
            position: fixed;
            top:0;
            z-index: 1000;
            border-bottom: 1px solid #e1e1e1;
        }
        .padding-30 {
		    float:left;
		    margin-left: 13px;
		}
		.padding-80 {
		    float: right;
		    margin-right: 13px;
		}
    </style>
</head>
<body class="scroll" style="background-color: #fff">
    <div class="topfixed" >
        <form action="<%=basePath%>wxMenu/tuwenList.do" method="post" name="Form" id="Form">
            <div class="inputbox">
                <input type="text" class="layui-input" id="TITLE" name="TITLE" placeholder="请输入标题" style="width: 50%;height: 30px;display: inline-block" value="${pd.MENUTITLE }">
                <i class="layui-icon sreach" onclick="search()">&#xe615;</i>
            </div>
        </form>
    </div>
    <div class="wdth">
        <div id="main scroll">
        	<c:choose>
        		<c:when test="${not empty tuwenList }">
		       		<c:forEach items="${tuwenList }" var="var">
			            <div class="pin">
			                <div class="box">
			                    <div class="rowBoxCenter">
			                        <p>
				                        <span class="font-12 padding-30">保存于：${var.CREATE_TIME }</span>
				                        <span class="font-12 padding-80">已群发</span>
			                        </p>
			                        <div class="widthImg">
			                            <img src="<%=basePath%>uploadFiles/uploadImgs/${var.mList[0].PATH }" alt="">
			                            <div class="wordTitle"><p>${var.mList[0].TITLE }</p></div>
			                        </div>
			                        <c:forEach items="${var.mList }" var="mar" begin="1">
				                        <div class="borBottom">
				                            <table width="100%">
				                                <tbody>
				                                <tr>
				                                    <td style="vertical-align: top"><p class="tarTileWid">${mar.TITLE }</p></td>
				                                    <td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img src="<%=basePath%>uploadFiles/uploadImgs/${mar.PATH }" alt=""></div></td>
				                                </tr>
				                                </tbody>
				                            </table>
				                        </div>
			                        </c:forEach>
			                        
			                    </div>
			                    <input id="${var.SENDRECORD_ID }" type="hidden" value="${var.SENDRECORD_ID }">
			                    <input id="${var.MEDIA_ID }" type="hidden" value="${var.MEDIA_ID }">
			                    <div class="alier"></div>
			                    <div class="lay-iconAuto">
			                        <i class="layui-icon lay-iconStyle">&#x1005;</i>
			                    </div>
			                </div>
			            </div>
		       		</c:forEach>
        		</c:when>
        		<c:otherwise>
        			<div style="text-align: center;padding-top: 50px;">未创建图文</div>
        		</c:otherwise>
        	</c:choose>
        </div>
    </div>
    <script>
            $('.pin').click(function () {
                var isthis = $(this).children().children(":last-child");
                isthis.toggleClass("selected block");
                isthis.prev().toggleClass("block");
                var anther = $(this).siblings().children().children(":last-child");
                anther.removeClass("selected block");
                anther.prev().removeClass("block");
            })
            var callTuWen = function () {
	            var tuwenBox = $(".selected").parent();
	            var time = tuwenBox.children().children(":first-child").children().first().html();
	            
	            var ftitle = tuwenBox.children().children(".widthImg").children().last().html();
	            var fimg = tuwenBox.children().children(".widthImg").children().first()[0].src;
	            
	            var length = tuwenBox.children().children(".borBottom").length;
	            var last = '{';
	            if(parseInt(length) > 0){
	            	for(var i=0;i<length;i++){
	            		var tt = tuwenBox.children().children(".borBottom").eq(i).text().trim();
	            		var ii = tuwenBox.children().children(".borBottom").eq(i).find("img")[0].src;
	            		last += '"title' + i + '":"' + tt + '","img' + i + '":"' + ii +'"';
	            		if(i != length -1){
	            			last += ',';
	            		}
	            	}
	            }
	            last += '}';
	            
	            var sendRecord_id = $(".selected").prev().prev().prev().val();
	            var media_id = $(".selected").prev().prev().val();
	            var data = {
	            	time: time,
	                ftitle: ftitle,
	                fimg: fimg,
	                length : length,
	                last: last,
	                media_id: media_id,
	                sendRecord_id: sendRecord_id
	            };
	            return data;
	        }
		var search = function(){
			$("#Form").submit();
		}
            
    </script>
</body>
</html>