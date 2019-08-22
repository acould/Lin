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
					
					<form action="introduc/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data" >
						<input type="hidden" name="INTRODUC_ID" id="INTRODUC_ID" value="${pd.INTRODUC_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="255" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系电话:</td>
								<td><input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="20" placeholder="这里输入联系电话" title="联系电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"><div id="localImag"><img id="preview"   width=-1 height=-1 style="diplay:none"/><strong>网吧图片1</strong></div></td>
								<td><input type="file" name="PIC_ONE" id="PIC_ONE" value="${pd.PIC_ONE}" maxlength="255"  onchange="javascript:setImagePreview();" title="网吧图片1" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">图片2:</td>
								<td><input type="file" name="PIC_TWO" id="PIC_TWO" value="${pd.PIC_TWO}" maxlength="255" placeholder="这里输入图片2" title="图片2" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">图片3:</td>
								<td><input type="file" name="PIC_THREE" id="PIC_THREE" value="${pd.PIC_THREE}" maxlength="255" placeholder="这里输入图片3" title="图片3" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">图片4:</td>
								<td><input type="file" name="PIC_FOUR" id="PIC_FOUR" value="${pd.PIC_FOUR}" maxlength="255" placeholder="这里输入图片4" title="图片4" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">网吧简介:</td>
								<td>
								<textarea style="width:95%;height:100px;" rows="10" cols="10" name="CONTENT" id="CONTENT" title="内容" maxlength="1000" placeholder="这里输入内容">${pd.CONTENT}</textarea>
								<div><font color="#808080">请不输入过多文字</font></div>
								</td>
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
		function save(){
			if($("#ADDRESS").val()==""){
				$("#ADDRESS").tips({
					side:3,
		            msg:'请输入地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ADDRESS").focus();
			return false;
			}
			if($("#PHONE").val()==""){
				$("#PHONE").tips({
					side:3,
		            msg:'请输入联系电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PHONE").focus();
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
		function setImagePreview() { 
			var docObj=document.getElementById("doc");
			var imgObjPreview=document.getElementById("PIC_ONE"); 
			if(docObj.files && docObj.files[0]){ 
			//火狐下，直接设img属性 
			imgObjPreview.style.display = 'block'; 
			imgObjPreview.style.width = '6.875rem'; 
			imgObjPreview.style.height = '4.225rem'; 
			//imgObjPreview.src = docObj.files[0].getAsDataURL(); 
			//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式 
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]); 
			}else{ 
			//IE下，使用滤镜 
			docObj.select(); 
			var imgSrc = document.selection.createRange().text; 
			var localImagId = document.getElementById("localImag"); 
			//必须设置初始大小 
			localImagId.style.width = "6.875rem"; 
			localImagId.style.height = "4.225rem"; 
			//图片异常的捕捉，防止用户修改后缀来伪造图片 
			try{ 
			localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)"; 
			localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc; 
			}catch(e){ 
			alert("您上传的图片格式不正确，请重新选择!"); 
			return false; 
			} 
			imgObjPreview.style.display = 'none'; 
			document.selection.empty(); 
			}
			var localImg=document.getElementById('localImag');
			var localp=localImg.getElementsByTagName('strong')[0];
			localp.innerHTML="";
			return true; 
			} 
		</script>
</body>
</html>