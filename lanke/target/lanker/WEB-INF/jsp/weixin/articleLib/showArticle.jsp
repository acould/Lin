<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    
    <script type="text/javascript" src="<%=basePath%>newStyle/js/qrcode.min.js"></script>
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
		    max-width: 100%;
		    height: auto !important;
		}
    	
        body,p,h2 {
            margin: 0;
            padding: 0;
        }
        .content {
            width:740px;
            margin:0 auto;
        }
        .boxheader,.boxcon {
            padding: 20px;
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
         .qr_code_pc_outer {
            position: fixed;
            left: 0;
            right: 0;
            top: 20px;
            color: #717375;
            text-align: center;
        }
        .qr_code_pc_inner {
            position: relative;
            width: 740px;
            margin-left: auto;
            margin-right: auto;
        }
         .qr_code_pc {
            position: absolute;
            right: -140px;
            top: 0;
            width: 140px;
            padding: 16px;
            border: 1px solid #d9dadc;
            background-color: #fff;
            word-wrap: break-word;
            word-break: break-all;
        }
        .qr_code_pc_img {
            width: 102px;
            height: 102px;
        }
        .qr_code_pc p {
            font-size: 14px;
            line-height: 20px;
        }
        #qrcode {
        	width:90px;
        	margin: 0 auto;
        	margin-bottom:14px;
        }
    </style>
	
</head>
<body class="scroll">
		<div class=content>
	       <div class="boxheader">
	           <div class="boxcon">
	               <h2 class="title" id="title">${pd.title } </h2>
	               <div class="rich_media_meta_list">
	                   <em class="rich_media_meta rich_media_meta_text" id="time">${pd.update_time.toString().substring(0,19) }</em>
	                   <a class="rich_media_meta" id="author">${pd.author }</a>
	               </div>
	               <div id="content">${pd.content }</div>
	           </div>
	       </div>
	   </div>
	   <div class="qr_code_pc_outer">
	       <div class="qr_code_pc_inner">
	           <div class="qr_code_pc" >
	           	   <div id="qrcode" ></div>
	               <p>微信扫一扫<br>导入手机进行预览</p>
	           </div>
	       </div>
	   </div>
	   
	<div id="url" style="display: none;">${pd.phone_url }</div>

<script type="text/javascript">

	window.onload =function(){
	    var qrcode = new QRCode(document.getElementById("qrcode"), {
	        width : 90,//设置宽高
	        height : 90
	    });
	    //var url = document.URL;
	    var url = document.getElementById("url").innerHTML;
	    
	    qrcode.makeCode(url);
	    
	}
</script>


</body>
</html>