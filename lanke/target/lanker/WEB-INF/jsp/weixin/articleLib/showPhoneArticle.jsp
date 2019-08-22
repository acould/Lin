<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <title>手机预览</title>
    <style>
    	* {
		    max-width: 100%;
		    margin: 0;
		    padding: 0;
		    font-style: normal;
		    box-sizing: border-box!important;
		    -webkit-box-sizing: border-box!important;
		    word-wrap: break-word!important;
		}
		
		img:hover {
		    z-index: -1;
		    cursor: pointer;
		}
		
		img {
		    max-width: 100%!important;
		    height: auto !important;
		}
    	body{
    		overflow-y:scroll!important; 
    		padding: 0 8px 0 8px;
    		overflow: hidden;
    	}
        p,h2 {
            margin: 0;
            padding: 0;
        }
        .content {
            width:100%;
            margin:0 auto;
        }
        .boxheader{
            padding: 10px;
        }
        .title {
            font-size: 24px;
            font-weight:400;
            padding-bottom: 10px;
            margin-bottom: 14px;
            border-bottom: 1px solid #e7e7eb;

        }
        .rich_media_meta_list {
            margin-bottom: 18px;
            line-height: 20px;
            font-size: 0;
        }
        .rich_media_meta {
            display: inline-block;
            vertical-align: middle;
            margin-right: 8px;
            margin-bottom: 10px;
            font-size: 16px;
            color: #8c8c8c;

        }
        .rich_media_meta_text {
            font-style: normal;
        }
        a {
            color: #607fa6!important;
            text-decoration: none;
        }
        .qr_code_pc p {
            font-size: 14px;
            line-height: 20px;
        }
        
    </style>
</head>
<body>
<div class=content>
    <div class="boxheader">
        <div class="boxcon">
            <h2 class="title">${pd.title } </h2>
            <div class="rich_media_meta_list">
                <em class="rich_media_meta rich_media_meta_text">${pd.update_time.toString().substring(0,19) }</em>
                <a class="rich_media_meta">${pd.author }</a>
            </div>
            <div>${pd.content }</div>
        </div>
    </div>
</div>
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript">
	    function imgWidth(){
	    	$("img").attr("max-width","100%");
	    	$("img").css("max-width","100%");
	    	$("img").removeAttr("height");
	    	$("img").css("height","auto");
	    	$("section").css("max-width","100%");
	    	
	    	$("video").attr("max-width","100%");
	    	$("video").css("max-width","100%");
	    	$("video").removeAttr("height");
	    	$("video").css("height","auto");
	    }
	    imgWidth();	
    </script>
</html>