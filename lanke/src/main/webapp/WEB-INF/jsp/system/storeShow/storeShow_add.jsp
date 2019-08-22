<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
<style>
	.layui-form-pane .layui-form-text .layui-form-label {width:100%!important};

	#layui-layer100001{
		top:56.5px;
	}
</style>
</head>
<body class="scroll borPadding" style="background: #fff">
	<!-- /section:basics/navbar.layout -->
	<form action="" name="Form" id="Form" method="post" enctype="multipart/form-data" class="layui-form layui-form-pane">
		  <input type="hidden" name="STORE_ID" id="STORE_ID" value="${pd.STORE_ID}" />
		  <input type="hidden" name="STATE" id="STATE" value="${pd.STATE}" />
		  <input type="hidden" id="imgList" name="imgList">
		  <c:if test="${pd.STORE_ID == null}">
		  	<blockquote class="layui-elem-quote layui-quote-nm">
		  		<p><font color="#f93232">温馨提示：</font><font color="#666">如需便捷新建门店，只需填写门店名称，新建完成后记得补充地址等信息，否则的话，别人是不知道你的位置</font></p>
		 	</blockquote>
		 </c:if>	
		 <div class="layui-form-item">
		    <label class="layui-form-label"><font color="red">*</font>门店名称:</label>
		    <div class="layui-input-block">
		      <input type="text" name="STORE_NAME" id="STORE_NAME" autocomplete="off" placeholder="请输入门店名称" class="layui-input" maxlength="60" value="${pd.STORE_NAME}">
		    </div>
		 </div>
		 <div class="layui-form-item">
		    <label class="layui-form-label">所在区域:</label>
		    <div class="layui-input-block">
		    	<input type="hidden" name="PROVINCE" id="PROVINCE" value="${pd.PROVINCE }"> 
				<input type="hidden" name="CITY" id="CITY" value="${pd.CITY }"> 
				<input type="hidden" name="COUNTY" id="COUNTY" value="${pd.COUNTY }">
				<div id="city" style="padding: 4px 18px;">
					<span style="padding: 0 10px 0 0">省</span><select class="prov" id="s_province" name="s_province"></select> 
					<span style="padding: 0 10px 0 10px">市</span><select class="city" id="s_city" name="s_city" disabled="disabled"></select>
					<span style="padding: 0 10px 0 10px">区</span><select class="dist" id="s_county" name="s_county" disabled="disabled"></select>
				</div>
		    </div>
		 </div>
	 	 <div class="layui-form-item">
	    	 <label class="layui-form-label">详细地址:</label>
		     <div class="layui-input-block">
		          <input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="100" placeholder="请输入门店详细地址" class="layui-input">
		     </div>
		 </div>
		 <div class="layui-form-item">
	    	 <label class="layui-form-label">门店电话:</label>
		     <div class="layui-input-block">
		          <input type="text" name="TELEPHONE" id="TELEPHONE" value="${pd.TELEPHONE}" maxlength="20" placeholder="请输入门店电话" class="layui-input">
		     </div>
		 </div>
		 <div class="layui-form-item">
	    	 <label class="layui-form-label">会员级别:</label>
		     <div class="layui-input-block">
				  <input type="text" name="member_level" id="member_level" value="${pd.member_level}" maxlength="20" placeholder="请输入基础会员级别" class="layui-input">
					<p style="margin-top:10px;color:red;font-size:12px">若使用在线申请会员功能：基础会员级别必填，且名称必须pubwin计费系统保持一致</p>
			 </div>

		 </div>
		 <div class="layui-form-item layui-form-text">
		     <label class="layui-form-label">门店展示图片:</label>
		     <div class="layui-input-block">
		          <div class="layui-upload">
				      <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
				      		<div>
					  			<span  class="layui-btn btn-primary layui-btn-sm" id="testBtn"><i class="layui-icon">&#xe67c;</i>多图片上传
					  				<input type="file" onchange="uploadImg()" id="file" accept="image/*" multiple="multiple"  name="file">
					  			</span> 
					  			<span style="font-size: 13px">为了美观，请上传的图片尺寸保持一致，建议为<font color="#f93232">横版照片</font></span>
				      		</div>
				      		
					   		<div class="layui-upload-list layui-clear" id="upImg" >
					   			<c:choose>
					   				<c:when test="${not empty picList}">
					   					<c:forEach items="${picList }" var="p" varStatus="vs">
					   						<div class="lk-store-upload" id="div${vs.index}" style="background: url(${p.picture_url}) no-repeat center center;background-size: 200%;">
							   				 	<div class="img-operate" >
								   			 	 	<p style="margin: 20px 0 10px 0;" onclick="delImg(${vs.index})">删除</p>
								   			 	 	<p onclick="seeImg('${p.picture_url}')"><a>查看</a></p>'+
							   			 		 </div>
							   			 		<input type="hidden"  value="${p.URL}">
						   			  		</div>
					   					</c:forEach>
					   				</c:when>
					   			</c:choose>
					   		</div>
					  </blockquote>
				 </div>
		     </div>
		 </div>
	</form>

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<!-- ace scripts -->
	<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js">  </script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.cityselect.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.js"></script>
	<!-- 业务JS -->
	<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-at-storeAdd.js"></script>
	<script type="text/javascript">
		//调用城市三级联动
		citySel($("#PROVINCE").val(),$("#COUNTY").val(),$("#CITY").val())	
	</script>
</body>
</html>