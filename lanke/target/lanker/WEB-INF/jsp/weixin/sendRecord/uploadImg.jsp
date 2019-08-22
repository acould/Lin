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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传图片</title>

    <link rel="stylesheet" href="<%=basePath%>newStyle/css/custom.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
</head>
<body>

		<div class="flieImg">
			<div class="tab_cont_cover">
				<div class="media_cover" id="beforeImg" style="padding-left: 46px;">
					<span class="create_access"> <a href="javascript:void(0);">
							<i class="layui-icon tab_icona">&#xe654;</i> <strong>选择历史图片</strong>
					</a>
					</span>
				</div>
				<div class="media_cover" id="flieImg">
					<form action="<%=basePath%>wxMenu/uploadImg.do" method="post" name="Form" id="Form" enctype="multipart/form-data"
						target="nm_iframe">
						<input type="file" class="upFlie" id="upFlie" name="upFile">
					</form>
					<iframe id="nm_iframe" name="nm_iframe" style="display: none;"></iframe>
					<span class="create_access"> <a href="javascript:void(0);">
							<i class="layui-icon tab_icona">&#xe654;</i> <strong>选择本地上传</strong>
					</a>
					</span>
				</div>
				<div id="divImg">
                    <div class="divImg ">
                        <img src="<%=basePath%>uploadFiles/uploadImgs/${image.PATH }"  width="100%" id="successImg" class="">
                        <div class="imgyulan" id="imgyulan">
                            <p class="text-center">预览</p>
                        </div>
                    </div>
                    <input id="IMG_MEDIA_ID" type="hidden" value="${image.MEDIA_ID }">
                    <input id="PICTURE_ID" type="hidden" value="${image.PICTURE_ID }">
                    <span id="deleteImg" class="shanchu">删除</span>
                </div>
			</div>
		</div>

</body>

<script type="text/javascript">
	layui.use(["form","layer",'element'],function(){
	    var form = layui.form,
	        layer = layui.layer,
	        element = layui.element;
		
//      选择历史图片
        function openImg() {
            parent.layer.open({
                btn: ['确定', '关闭'],
                btn1: function(index, layero){
                    //按钮的回调
                    var res = parent.window["layui-layer-iframe" + index].callbackdata();
                    $("#successImg").attr("src",res.url);
                    $("#IMG_MEDIA_ID").val(res.media_id);
					$("#PICTURE_ID").val(res.picture_id);
                    flieImgsuccess();
                    parent.layer.close(index);
                },
                skin: '',
                btnAlign: 'c',
                type: 2,
                title: '选择历史图片',
                shadeClose: false,
                shade: 0.8,
                area: ['778px', '560px'],
                content:[ '<%=basePath%>wxMenu/tupianList.do'] //iframe的url
               
            });
        }
        var flieImgsuccess = function () {
            $("#divImg").show()
            $("#flieImg").hide()
            $("#beforeImg").hide()
        }
		//上传图片
		function uploadImg(f){
				for(var i=0;i<f.length;i++){
		        var reader = new FileReader();
		        reader.readAsDataURL(f[i]);
		    	var tt = f[i].type;
		    	var size = f[i].size;
		        reader.onload = function(e){
		   	   		var srces = e.target.result;
		   	   		var rule = /^(?:image\/jpeg|image\/png)$/i;
		   	   		if(rule.test(tt)){
		       	   		if(size > (2*1024*1024)){
		   	   				layer.msg("图片大小不能超过2M",{time:1500});
		   	   			}else{
		       	   			$.ajax({
			    	            type	: "POST",
			    	            url		:"<%=basePath%>wxAutoReply/uploadImg.do",
			    	            data	:{upFile:srces},
			    	            dataType:'json',
			    	            beforeSend	: beforeSend,
			    	            success: function(data) {
			                  		layer.closeAll('loading');
			                  		layer.msg(data.message,{time:1000});
			    	            	if(data.result == "true"){
										console.log(data);

										$("#IMG_MEDIA_ID").val(data.MEDIA_ID);
			                      	    $("#PICTURE_ID").val(data.PICTURE_ID);

			                      	  	$("#successImg").attr("src",srces);
			                     		flieImgsuccess();
			                  		}
			    	            },
			    	            error:function(){
			    	            	layer.closeAll('loading');
			    	               layer.msg("系统繁忙，请稍后再试！");
			    	               return false;
			    	            }
			    	        });
		   	   			}
		   	   		}else{
		   	   			layer.msg("请上传jpg或者png格式的图片",{time:1500});
		   	   		}
		        }
		    }
		}
		function beforeSend(){
			layer.load(2);
		}
		//预览图片
		function yulanTuPian() {
		    console.log()
		    parent.layer.open({
		        type: 1,
		        title: false,
		        closeBtn: 0,
		        area: '30%',
		        closeBtn:1,
		        skin: '', //没有背景色
		        shadeClose: true,
		        content: $(".divImg").html()
		    });
		}
		
		$("body").on("change","#upFlie",function() {
	        var files = this.files
	        uploadImg(files);
	    });
		$("body").on("click","#beforeImg",function() {
	        openImg();
	    });
		$("body").on("click","#imgyulan",function() {
            yulanTuPian();
        });
		$("#deleteImg").click(function () {
        	deleteImg();
        })
        var deleteImg = function(){
			$("#flieImg").show();
            $("#beforeImg").show();
            $("#divImg").hide();
            $("#divImg").children().children().attr("src","");
            $("#upFlie").val("");
            $("#IMG_MEDIA_ID").val("");
		}
	});
	$(function(){
		
		if($("#IMG_MEDIA_ID").val() == ""){
			$("#flieImg").show();
	        $("#beforeImg").show();
	        $("#divImg").hide();
		}else{
			$("#flieImg").hide();
	        $("#beforeImg").hide();
	        $("#divImg").show();
		}
		
	})
	
	function getImg(){
		var mediaId = $("#IMG_MEDIA_ID").val();
		var picture_id = $("#PICTURE_ID").val();
		var img = $("#successImg").attr("src");
		var data = {
			media_id : mediaId,
			picture_id : picture_id,
			imgPath : img
		}
		return data;
	}
</script>


</html>