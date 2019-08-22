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
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<title>${intenetName}</title>
</head>

<body>
<body style="background: url(http://oo9t47b94.bkt.clouddn.com/bg.jpg);">
		<div style="margin-top: 3rem;color: red;">
			<img src="<%=basePath %>uploadFiles/uploadImgs/${BARCODE }" style="width: 2.94rem;height: 2.94rem;display: block;margin:0 auto 0.23rem">
			<span style="font-size: 0.35rem;display: block; margin: 0 auto;text-align: center;line-height: 0.53rem">
				请长按二维码关注${intenetName}微信公众号，完成最后一步操作
			</span>
			
		</div>
<script type="text/javascript">
function fontAuto(width){//width表示效果图的宽度
	var screenWidth=document.documentElement.clientWidth;//屏幕的宽度
	//document.documentElement表示html标签
	if(screenWidth>=width)//当设备的宽度大于等于效果图的宽度
	{
		document.documentElement.style.fontSize="530%";
	}
	else{//当设备的宽度小于效果图的宽度
		document.documentElement.style.fontSize=(530*screenWidth/width)+"%";
	}
	
}

fontAuto(640);//网页加载的时候触发

window.onresize=function(){//当网页宽度发生变化时触发函数
	fontAuto(640);
}
</script>
</body>

</html>
