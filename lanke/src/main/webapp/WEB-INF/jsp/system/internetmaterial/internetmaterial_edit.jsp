<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	<script type="text/javascript" src="<%=basePath%>/ueditorNew/ueditor.config.js"></script>
<script type="text/javascript" src="<%=basePath%>/ueditorNew/ueditor.all.js"></script>
<script type="text/javascript" src="<%=basePath%>/ueditorNew/lang/zh-cn/zh-cn.js"></script>
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
					
					<form action="internetmaterial/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="INTERNETMATERIAL_ID" id="INTERNETMATERIAL_ID" value="${pd.INTERNETMATERIAL_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标题:</td>
								<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="60" placeholder="这里输入标题" title="标题" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">作者:</td>
								<td><input type="text" name="CREATE_USER" id="CREATE_USER" value="${pd.CREATE_USER}" maxlength="60" placeholder="这里输入作者" title="作者" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">摘要:</td>
								<td><input type="text" name="DIGEST" id="DIGEST" value="${pd.DIGEST}" maxlength="255" placeholder="这里输入摘要" title="摘要" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">图文信息封面图片:</td>
								<td>
								 <select name="LOGO_URL" id="LOGO_URL"   style="width:98%;">
								 <c:forEach items="${varList}" var="var" varStatus="vs">
								 <option value="${var.MATERIAL_ID}" <c:if test="${pd.THUMB_MEDIA_ID ==var.MATERIAL_ID}">selected="selected"</c:if>>${var.TITLE} </option>
								 </c:forEach>
								 </select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">原文链接地址:</td>
								<td><input type="text" name="CONTENT_SOURCE_URL" id="CONTENT_SOURCE_URL" value="${pd.CONTENT_SOURCE_URL}" maxlength="255" placeholder="这里输入原文链接地址" title="原文链接地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">图文内容:</td>
								<td><textarea  name="CONTENT" id="CONTENT" maxlength="255" placeholder="这里输入图文内容" title="图文内容"  style="width: 800px; height: 400px; margin: 0 auto;">${pd.CONTENT}</textarea></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						
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
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		
		
		function upload(f){
			var aa=document.getElementById("file").value.toLowerCase().split('.');//以“.”分隔上传文件字符串
			    if(document.getElementById("file").value=="") {
			        alert('图片不能为空！');
			        return false;
			    } else{
				    if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='bmp'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg') {
					    var imagSize =  document.getElementById("file").files[0].size;
					    if(imagSize<1024*1024*3)
					    	  var str = "";
					    for(var i=0;i<f.length;i++){
					        var reader = new FileReader();
					        reader.readAsDataURL(f[i]);
					        reader.onload = function(e){
					            str+="<img src='"+e.target.result+"' />";
					            document.getElementById("dd").innerHTML = str;
					        }
					    }
					} else{
					        alert('请选择格式为*.jpg、*.gif、*.bmp、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，
					        return false;
					}
			    }
		  
		}
		
		function limitImg(){ 
			var img=document.getElementById(arguments[0]);//显示图片的对象 
			var maxSize=arguments[1];// 
			var allowGIF=arguments[2]||false; 
			var maxWidth=arguments[3]||0; 
			var maxHeight=arguments[4]||0; 
			var postfix=getPostfix(img.src); 
			var str=".jpg"; 
			if(allowGIF){str+=".gif"} 
			if(str.indexOf(postfix.toLowerCase())==-1){ 
			if(allowGIF){return "图片格式不对，只能上传jpg或gif图像";}else{return "图片格式不对，只能上传jpg图像";} 
			}else if(img.fileSize>maxSize*1024){ 
			return "图片大小超过限制,请限制在"+maxSize+"K以内"; 
			}else{ 
			if(img.fileSize==-1){ 
			return "图片格式错误，可能是已经损坏或者更改扩展名导致，请重新选择一张图片"; 
			}else{ 
			if(maxWidth>0){ 
			if(img.width>maxWidth){ 
			return "图片宽度超过限制，请保持在"+maxWidth+"像素内"; 
			}else{ 
			if(img.height>maxHeight){ 
			return "图片高度超过限制，请保持在"+maxHeight+"像素内"; 
			}else{ 
			return ""; 
			} 
			} 
			}else{ 
			return ""; 
			} 
			} 
			} 
			} 
			//根据路径获取文件扩展名 
			function getPostfix(path){ 
			return path.substring(path.lastIndexOf("."),path.length); 
			} 
		function save(){
			if($("#TITLE").val()==""){
				$("#TITLE").tips({
					side:3,
		            msg:'请输入标题',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TITLE").focus();
			return false;
			}
			if($("#CREATE_USER").val()==""){
				$("#CREATE_USER").tips({
					side:3,
		            msg:'请输入作者',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATE_USER").focus();
			return false;
			}
			if($("#CONTENT_SOURCE_URL").val()==""){
				$("#CONTENT_SOURCE_URL").tips({
					side:3,
		            msg:'请输入原文链接地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CONTENT_SOURCE_URL").focus();
			return false;
			}
			
			if($("#DIGEST").val()==""){
				$("#DIGEST").tips({
					side:3,
		            msg:'请输入摘要',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DIGEST").focus();
			return false;
			}
			if($("#THUMB_MEDIA_ID").val()==""){
				$("#THUMB_MEDIA_ID").tips({
					side:3,
		            msg:'请输入图文信息封面图片',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#THUMB_MEDIA_ID").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		</script>
		<script type="text/javascript">
 UE.getEditor("CONTENT");
</script>
</body>
</html>