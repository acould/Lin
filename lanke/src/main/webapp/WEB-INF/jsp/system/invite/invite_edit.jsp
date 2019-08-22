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
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>

	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/jquery.form.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>Document</title>
    <style>
        .borPadding {
            padding:20px 30px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
    </style>
</head>
<body class="no-skin scroll">
<div class="borPadding">
	<form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="<%=basePath%>invite/${msg }.do" enctype="multipart/form-data">
		<input type="hidden" name="INTIVE_ID" id="INTIVE_ID" value="${pd.INTIVE_ID}"/>
        <div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">标题:</label>
			<div class="layui-input-block">
				<input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" autocomplete="off" placeholder="这里输入标题,最多12字" maxlength="12" class="layui-input">
			</div>
		</div>
        <div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">描述:</label>
            <div class="layui-input-block">
            	<input type="text" name="DESCRIPTION" id="DESCRIPTION" value="${pd.DESCRIPTION}" autocomplete="off" placeholder="这里输入描述,最多30字" title="描述" maxlength="30" class="layui-input">
            </div>
        </div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">二维码:</label>
            <div class="layui-input-block">
            	<c:if test="${pd.BARCODE != null }">
					<div id="dd2">
						<input type="hidden" id="barcode" name="barcode" value="${pd.BARCODE }">
						<a href="<%=basePath%>uploadFiles/uploadImgs/${pd.BARCODE}" target="_blank">
							<img class="imgTd" name="file" id="file" width='150' height='150' src="<%=basePath%>uploadFiles/uploadImgs/${pd.BARCODE}"/>
						</a>
					</div>
				</c:if>
				<input type="file" name="BARCODE" id="BARCODE" onchange="upload2(this.files)" maxlength="255"  title="二维码" style="width:98%;" multiple/>
					<div id="dd2"></div>
					<small style="color: red;font-size: x-small;">微信公众号的二维码</small>
            </div>
        </div>
	</form>
</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<script type="text/javascript">
		$(top.hangge());
		//保存
		var baocun = function(){
			if($("#TITLE").val()==""){
				layer.tips('请输入标题', '#TITLE', {
			 		tips: 3
	        	});
				$("#TITLE").focus();
				return false;
			}
			var DESCRIPTION = $.trim($("#DESCRIPTION").val());
			if(DESCRIPTION==""){
				layer.tips('请输入描述', '#DESCRIPTION', {
			 		tips: 3
	        	});
				$("#DESCRIPTION").focus();
				return false;
			}
			if($("#barcode").val() == null ){
				if($("#BARCODE").val() == "" ){
					layer.tips('请添加二维码图片', '#BARCODE', {
			 			tips: 3
	        		});
					$("#BARCODE").focus();
					return false;
				}
			}
			//异步提交
			var form = new FormData(document.getElementById("Form"));
			var dt;
            $.ajax({
            	async:false,
                url:"<%=basePath%>invite/${msg }.do",
                type:"post",
                dataType: "json",
                data:form,
                processData:false,
                contentType:false,
                success:function(data){
                    //window.clearInterval(timer);
                    //console.log("over..");
                    if(data.msg == "success"){
                    	dt = {
            				msg : true
            			};
                	}else if(data.msg == "error"){
                		layer.msg('保存错误!!', {time: 800,icon: 1},function () {
                    	});
                    	dt = {
            				msg : false
            			};
            			return
                	}
                },
                error:function(e){
                    alert("错误！！");
                    window.clearInterval(timer);
                    return false;
                }
            });
            return dt;
		}
		
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
		function $$(obj) {
		    return document.getElementById(obj);
		}
		
		function upload1(f){
		    var str = "";
		    for(var i=0;i<f.length;i++){
		        var reader = new FileReader();
		        reader.readAsDataURL(f[i]);
		        reader.onload = function(e){
		            str+="<img src='"+e.target.result+"' width='150' height='150'/>";
		            $$("dd1").innerHTML = str;
		        }
		    }
		}
		function upload2(f){
		    var str = "";
		    for(var i=0;i<f.length;i++){
		        var reader = new FileReader();
		        reader.readAsDataURL(f[i]);
		        reader.onload = function(e){
		            str+="<img src='"+e.target.result+"' width='150' height='150'/>";
		            $$("dd2").innerHTML = str;
		        }
		    }
		}
</script>
</body>
</html>