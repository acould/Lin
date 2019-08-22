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
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>

<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<title>Document</title>
<style>
.borPadding {
	padding: 20px 30px;
}
.layui-form-checkbox[lay-skin=primary] span {
	padding-right: 6px;
}
body {
	overflow-x: hidden;
}
input{border-color:#fff!important;}
input:hover {border-color:#fff!important;}
input[type="button"] {
	border: 1px solid #d5d5d5!important;
    background: #fff;
    padding: 4px 10px;
    border-radius: 2px;
}
</style>
</head>
<body class="no-skin scroll">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<form action="" name="Form" id="Form" method="post"
								enctype="multipart/form-data">
								<input type="hidden" name="STORE_ID" id="STORE_ID"
									value="${pd.STORE_ID}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width: 106px; padding-top: 13px;">门店名称:</td>
											<td><input type="text" name="STORE_NAME" id="STORE_NAME"
												value="${pd.STORE_NAME}"  maxlength="60"
												placeholder="请输入门店名称" title="门店名称" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 106px; padding-top: 13px;">门店所在区域:</td>
											<td>
											<input type="hidden" name="PROVINCE" id="PROVINCE" value="${pd.PROVINCE }"> 
											<input type="hidden" name="CITY" id="CITY" value="${pd.CITY }"> 
											<input type="hidden" name="COUNTY" id="COUNTY" value="${pd.COUNTY }">
												<div id="city">
													<span style="padding: 0 10px 0 0">省</span><select
														class="prov" id="s_province" name="s_province"></select> <span
														style="padding: 0 10px 0 10px">市</span><select
														class="city" id="s_city" name="s_city" disabled="disabled"></select>
													<span style="padding: 0 10px 0 10px">区</span><select
														class="dist" id="s_county" name="s_county"
														disabled="disabled"></select>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width: 106px; padding-top: 13px;">门店详细地址:</td>
											<td><input type="text" name="ADDRESS" id="ADDRESS"
												value="${pd.ADDRESS}" maxlength="100"
												placeholder="请输入门店详细地址" title="门店详细地址" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 106px; padding-top: 13px;">门店电话:</td>
											<td><input type="text" name="TELEPHONE" id="TELEPHONE"
												value="${pd.TELEPHONE}" maxlength="20" placeholder="请输入门店电话"
												title="门店电话" style="width: 98%;" /></td>
										</tr>
										<c:choose>
											<c:when test="${not empty picList}">
												<c:forEach items="${picList }" var="p" varStatus="vs">
													<input type="text" id="suoyin" hidden="hidden" value="${picList.size() }" />
													<tr height="220px">
														<td style="width: 106px; padding-top: 13px;">
														<div id="localImag">
														 <img id="preview" width=-1 height=-1 style="diplay: none" /><strong>上传图片:<br>
														 <font style="color: red; font-size: 12px">（375*250的大小）</font></strong>
														</div>
														</td>
														
														<td>
														<c:if test="${picList.size() == 0 }">
														 <input type="text" style="width: 98%;" value="没有上传图片">
														</c:if> 
														<input type="hidden" name="BRANCH_ID" id="BRANCH_ID" value="${p.BRANCH_ID }" /> 
														<a id="aaa${vs.index }" href="${p.picture_url}" target="_blank">
														<div style="width: 200px; height: 150px; overflow: hidden;">
														 <img class="imgTd" name="file" width="100%" src="${p.picture_url}" />
														</div>
														</a> 
														<input type="button" value="删除此图片" onclick="deleteIMG('${p.BRANCH_ID}','${pd.STORE_ID }')" />
														<div id="dd${vs.index }"></div>
														</td>
													</tr>
												</c:forEach>
											</c:when>
										</c:choose>
										<tr>
											<td colspan="2" align="center">
												<div class="btn bt" id="tupian" style="color: #5e89fd;">
													添加图片<small class="font-size10">（至少1张,最多可有6张）</small>
												</div>
											</td>
										</tr>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display: none">
									<br />
									<br />
									<br />
									<br />
									<br />
									<img src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
								</div>
							</form>
							<div id="zhongxin2" class="center" style="display: none">
								<img src="static/images/jzx.gif" style="width: 50px;" /><br />
								<h4 class="lighter block green"></h4>
							</div>
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
	<!-- ace scripts -->
	<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/jquery.cityselect.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		
		$(function(){
			var PRON = $("#PROVINCE").val();
			var CITY = $("#CITY").val();
			var COUNTY = $("#COUNTY").val();
			if(PRON != null && COUNTY != null && CITY != null){
				if(PRON==CITY){
					$("#city").citySelect({prov:PRON, city:COUNTY, dist:COUNTY});
				}
				else{
				$("#city").citySelect({prov:PRON, city:CITY, dist:COUNTY});
				}
			}else{
				$("#city").citySelect({
					nodata:"none",
					required:false
				}); 
			}
		});
		
		//删除
		function deleteIMG(id,store_id){
			var sum ='${picList.size()}'
			if(sum<=1){
				layer.msg('至少保留一张图片', {time :1000})
			}
			else{
			layer.confirm('确定要删除吗?如果删除，系统不做保留，需重新添加图片！', {
                btn: ['确定','取消'],
            }, function(){
            	$.ajax({
					type: "POST",
					url: '<%=basePath%>branch/delete.do',
		  			data: {BRANCH_ID:id,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" == data.result){
							layer.msg("删除成功！",{time:1000,icon:1},function(){
								window.location.href="<%=basePath%>storeShow/goEdit.do?STORE_ID="+store_id;								
							});
			 			}else{
			 				layer.msg('删除失败！', {time: 800,icon: 5});
			 			}
					}
				});
             }, function(){
                 return
			 });
			}
		}
		//保存
		var baocun = function(){
			
			if($("#STORE_NAME").val()=="" || $("#STORE_NAME").val()== null){
				layer.tips('请填写门店名称', '#STORE_NAME', {
    				tips: 3
    			});
				$("#STORE_NAME").focus();
				return false;
			}
			else if($("#STORE_NAME").val()!="" || $("#STORE_NAME").val()!= null){
				var STORE_NAME = $.trim($("#STORE_NAME").val());
				var STORE_ID='${pd.STORE_ID}';
				var flag=false;
				$.ajax({
					async:false,
					type: "POST",
					url: '<%=basePath%>storeShow/hasS.do',
		    		data: {STORE_NAME:STORE_NAME,STORE_ID:STORE_ID,STATE:2},
					dataType:'json',
					cache: false,
					success: function(data){
						if("error" == data.result){
							layer.tips('此门店存在！', '#STORE_NAME', {
		   						tips: 3
		   					});
							flag=true;
						 }
					}
				});
			}
			if(flag){
				return false;
			}
			
			if($("#s_province").val()=="" || $("#s_province").val()== null){
				layer.tips('请选择门店所在的省份', '#s_province', {
    				tips: 3
    			});
				$("#s_province").focus();
				return false;
			}
			if($("#s_province").val()=="北京" || $("#s_province").val()=="天津" || $("#s_province").val()=="上海" || $("#s_province").val()=="台湾" || $("#s_province").val()=="国外" || $("#s_province").val()=="澳门"){
				if($("#s_city").val()=="" || $("#s_city").val()== null){
					layer.tips('请选择门店所在的城市', '#s_city', {
    					tips: 3
    				});
					$("#s_city").focus();
					return false;
				}
			}else{
				if($("#s_city").val()=="" || $("#s_city").val()== null){
					layer.tips('请选择门店所在的城市', '#s_city', {
    					tips: 3
    				});
					$("#s_city").focus();
					return false;
				}
				if($("#s_county").val()=="" || $("#s_county").val()== null){
					layer.tips('请选择门店所在的区域', '#s_county', {
    					tips: 3
    				});
					$("#s_county").focus();
					return false;
				}
			}
			if($("#ADDRESS").val()==""){
				layer.tips('请输入门店详细地址', '#ADDRESS', {
    				tips: 3
    			});
				$("#ADDRESS").focus();
				return false;
			}
			if($("#TELEPHONE").val()==""){
				layer.tips('请输入门店电话', '#TELEPHONE', {
    				tips: 3
    			});
				$("#TELEPHONE").focus();
				return false;
			}
			//var myRege=/^0\d{2,3}-?\d{7,8}$/;//座机
			 var myReg = /^0\d{2,3}-?\d{7,8}|(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
			 var phone = $("#TELEPHONE").val();
			 if(!myReg.test(phone)){
			 	layer.tips('您的电话格式错误', '#TELEPHONE', {
    				tips: 3
    			});
				$("#TELEPHONE").focus();
				return false;
			}
			 
			
			if($("#aaa0").val() == null && $("#file").val() == undefined && $("#imgpand").val() == null || $("#file").val() == ''){
				layer.tips('门店图片至少一张', '#tupian', {
    				tips: 3
    			});
				$("#tupian").focus();
				return false;
			}
			console.log($('#Form').serialize())
			$.ajax({
				type: "POST",
				url: '<%=basePath%>storeShow/saveStoreShow.do',
				data: $('#Form').serialize(),
				dataType:'json',
				cache: false,
				success: function(data){
					layer.msg(data.message);
					if(data.result == "true"){
	                  	setTimeout(function () {
	                  		parent.location.reload();
	                  	},800)
		 			}
				},
				error:function(){
	               layer.msg("系统繁忙，请稍后再试！",{time:1200,icon:5});
	               return false;
	            }
			});
		}
			
		
		var hang = 0;			
		var suoyin = $("#suoyin").val();
		$('.bt').click(function(){
			if(suoyin == null || suoyin == ""){
				suoyin = 0;
			}
			
			var table =document.getElementById("table_report");
			var hang = table.rows.length-2;
			
   			if(suoyin > 5){
   				layer.msg('最多可有6张照片！', {time: 800,icon: 0});
               	return;
   			}
   			$('#table_report tr:eq('+hang+')').after('<tr class="imgTd">'+
   			         '<td style="width:75px;text-align: right;padding-top: 13px;"><div id="localImag"><img id="preview"   width=-1 height=-1 style="diplay:none"/><strong>上传图片:<br><font style="color: red;font-size: 12px">（375*250的大小）</font></strong></div></td>'+
   			         '<td>'+
   			         	'<input type="file" name="file'+suoyin+'" id="file'+suoyin+'" onchange="upload(this.files,'+suoyin+')" maxlength="255"  title="请上传375*250大小的图片" style="width:98%;" multiple/>'+
   							'<div id="dd'+suoyin+'"></div><input type="hidden" id="DATA_IMAGE'+suoyin+'" name="DATA_IMAGE'+suoyin+'">'+
	                '</td>'+
   	        '</tr>');
   			suoyin++;
   	        hang++;
   		});
		
		function upload(f,index){
			
		    var src = "";
    		var file = "#file" + index;
		    for(var i=0;i<f.length;i++){
		        var reader = new FileReader();
		        reader.readAsDataURL(f[i]);
		        var type = f[i].type;
		        reader.onload = function(e){
		        	src = e.target.result;
		        	var imageReg = /^(?:image\/jpeg|image\/png)$/i;
		        	if(!imageReg.test(type)){
		        		layer.msg("请上传jpg或png格式的图片！",function(){
		        			$(file).val("");
		        			return;	
		        		});
		        	}
		        	
		        	var image = new Image();
		        	image.src = src;
		        	image.onload = function(){
		        		if(image.width != "375" || image.height != "250"){
		        			layer.msg("请上传375*250尺寸的图片！",function(){
		        				$(file).val("");
			        			return;	
			        		});
		        		}else{
			        		var div = "<img id='imgpand' name='imgpand' src='"+src+"' width='200'/>";
			        		var dd = "dd" + index;
			        		var ii = "#DATA_IMAGE" + index;
			        		document.getElementById(dd).innerHTML = div;
			        		$(ii).val(src);
		        		}
		        	}
		        }
		    }
		    
		}
		
		
</script>
</body>
</html>