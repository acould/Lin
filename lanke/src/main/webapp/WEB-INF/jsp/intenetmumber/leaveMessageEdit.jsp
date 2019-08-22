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
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>${intenetName}</title>
    <link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
    <link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>assets/css/base.css">
    <script type="text/javascript" src="<%=basePath%>assets/js/zepto.min.js"></script>

    <link rel="stylesheet" href="<%=basePath%>assets/js/dialog.css">
    <script type="text/javascript" src="<%=basePath%>assets/js/dialog.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/js/iscroll.js"></script>
    <link rel="stylesheet" href="<%=basePath%>assets/js/swiper-3.2.7.min.css">
    <script type="text/javascript" src="<%=basePath%>assets/js/swiper-3.2.7.jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/js/mobile.js"></script>
	<!--移动端适配，px转化为rem-->
	<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
</head>

<body>
	<div>
		<form action="" name="Form" id="Form" method="post" enctype="multipart/form-data">
	        <div class="weui_cells_title">您好，欢迎您给我们提出服务中遇到的问题</div>
			<div class="weui_cells">
				<c:if test="${computer_name != null}">
					<div class="weui_cell">
						<div class="weui_cell_hd">上机电脑</div>
						<div class="weui_cell_bd weui_cell_primary">
							<input class="weui-input" type="text" value="${computer_name}" name="computer_name"
								   readonly style="border: none">
						</div>
					</div>
				</c:if>
				<c:choose>
					<c:when test="${not empty storeList}">
						<div class="weui_cell">
							<div class="weui_cell_hd">投诉门店</div>
							<div class="weui_cell_bd weui_cell_primary">
								<select class="weui_select" name="STORE_ID" id="STORE_ID" style="width:98%;" onblur="rule.empty($('#STORE_ID'))">
									<option value="">请选择</option>
									<c:forEach items="${storeList}" var="var" varStatus="vs">
										<option value="${var.STORE_ID}">${var.STORE_NAME }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</c:when>
				</c:choose>
	            <div class="weui_cell">
	                <div class="weui_cell_hd">投诉问题</div>
                     <div class="weui_cell_bd weui_cell_primary">
	                     <select class="weui_select" name="LM_TYPEID" id="LM_TYPEID" style="width:98%;">
	                     	<option>请选择</option>
								<c:forEach items="${tlist}" var="var" varStatus="vs">
									<option value="${var.DICT_CODE }">${var.DICT_VALUE }</option>
								</c:forEach>
						</select>
						 <input type="hidden" name="LM_TYPENAME" id="LM_TYPENAME">
                    </div>
	            </div>

	              <div class="weui_cell">
	                <div class="weui_cell_hd">备注内容</div>
                    <div class="weui_cell_bd weui_cell_primary">
	                     <textarea class="weui_textarea" rows="10" cols="10" name="LM_CONTENT" id="LM_CONTENT" title="内容" 
	                     	maxlength="100" placeholder="这里输入内容" onkeyup="checkNum();"></textarea>
						 <div><font color="#808080" id="num">0/100</font></div>
                    </div>
	            </div>
	             <div class="weui_cell">
                    <div class="weui_cell_hd">上传图片</div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <div class="upimgbox clearfix">
                            <div id="upfile">
                                <input type="file" id="filedd">
                            </div>
                        </div>
                    </div>
                </div>
	             </div>
			<div class="weic-bor" style="margin: 1.4rem 0">
				<div class="weic-dent-btn weic-btn-gradientBlue" onclick="save()">提交</div>
			</div>
        </form>
	</div>
<script type="text/javascript" src="<%=basePath%>static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
		<script type="text/javascript">

			$("#LM_TYPEID").change(function () {
                // console.log($(this).children(":selected").text());
                $("#LM_TYPENAME").val($(this).children(":selected").text());
            })
		//保存
		function save(){
			if($("#LM_TYPEID").val()=="请选择"){
				alert("请选择您要投诉的类型");
				return ;
			}
			
			if($("#STORE_ID").val()==""){
				alert("请选择您要投诉的对象分店");
				return ;
			}
			$.ajax({
				async:false,
				type	: "POST",
				url		:'<%=basePath %>indexMember/saveLm.do',
				data	:$('#Form').serialize(),
				dataType:'json',
				success: function(data) {
					layer.msg(data.message);
					if(data.result == "true"){
						setTimeout(function () { 
							window.location.href = '<%=path%>' + data.backUrl;
	                  	},800)
					}
				},
				error:function(){
					layer.msg("系统繁忙，请稍后再试！",{time:1500});
				}
			});
		}
		function checkNum(){
			var length = $("#LM_CONTENT").val().length;
			$("#num").text(length+"/100");
		}
		
		function $$(obj) {
		    return document.getElementById(obj);
		}
		
		$('#upfile input').change(function(evt){
            var files = evt.target.files;
            for(var i = 0, f; f = files[i]; i++){
                if(!f.type.match('image.*')) continue;
                
                var reader = new FileReader();
                reader.onload = (function(theFile){
                    return function(e){
						render2(e.target.result);
                    }
                })(f);
                reader.readAsDataURL(f);
            } 
        }).click(function(e){
            e.stopPropagation()
        })
	    $('#upfile').click(function () {
	        $(this).find('input').trigger('click') 
	    })
	    $('body').on({
	        click:function(){
	            $(this).parent().remove()
				$("#filedd").val("");
	        }
	    },'.upimgbox .imgli i')
		
		//设置压缩图片的最大高度
	    var MAX_HEIGHT = 1000;
		function render2(src){
		   // 创建一个 Image 对象
		   var image = new Image();
		   
		   // 绑定 load 事件处理器，加载完成后执行
		   image.onload = function() {
		       // 获取 canvas DOM 对象
		       var canvas = document.createElement("canvas");
		       // 如果高度超标
		       if (image.height > MAX_HEIGHT && image.height >= image.width) {
		           // 宽度等比例缩放 *=
		           image.width *= MAX_HEIGHT / image.height;
		           image.height = MAX_HEIGHT;
		       }
		      //考录到用户上传的有可能是横屏图片同样过滤下宽度的图片。
		       if (image.width > MAX_HEIGHT && image.width > image.height) {
		           // 宽度等比例缩放 *=
		           image.height *= MAX_HEIGHT / image.width;
		           image.width = MAX_HEIGHT;
		       }
		       // 获取 canvas的 2d 画布对象,
		       var ctx = canvas.getContext("2d");
		       // canvas清屏，并设置为上面宽高
		       ctx.clearRect(0, 0, canvas.width, canvas.height);
		       // 重置canvas宽高
		       canvas.width = image.width;
		       canvas.height = image.height;
		       // 将图像绘制到canvas上
		       ctx.drawImage(image, 0, 0, image.width, image.height);
		       // !!! 注意，image 没有加入到 dom之中
			   // document.getElementById('img').src = canvas.toDataURL("image/png");
		       var blob = canvas.toDataURL("image/jpeg");
			   $('#upfile').before('<div class="imgli flex-box"><img src="'+blob+'"/><input name="upimg" value="'+blob+'" type="hidden" /><i class="icon-roundclose"></i></div>');
		   };
		   image.src = src;
		   
		}
		</script>
</body>

</html>
