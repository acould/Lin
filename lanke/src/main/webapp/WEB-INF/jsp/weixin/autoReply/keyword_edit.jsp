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
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/custom.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <title>关键字回复设置</title>
    <style>
        .borPadding {
            padding:20px 30px;
        }
        .layui-this:after {
            border-bottom: none!important;
        }
        .layui-tab-title li {
            padding: 0!important;
            line-height: 38px;
            margin:0 15px;
        }
    </style>
</head>
<body style="background-color:#fff">
    <div class="borPadding">
        <form action="" class="layui-form layui-form-pane">
        	<input id="AUTOREPLY_ID" type="hidden" value="${pd.AUTOREPLY_ID}">
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">规则名称：</label>
                <div class="layui-input-block">
                    <input type="text" name="NAME" id="NAME" autocomplete="off" placeholder="请输入规则名称(60字以内)" class="layui-input" maxlength="60" value="${pd.NAME }">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">关键字：</label>
                <div class="layui-input-block">
                    <input type="text" name="KEYWORD" id="KEYWORD" autocomplete="off" placeholder="请输入关键字(30字以内)" class="layui-input" maxlength="30" value="${pd.KEYWORD }">
                </div>
            </div>
            <div class="layui-form-item layui-form-text" style="margin: 0">
                <label class="layui-form-label" >回复内容：</label>
            </div>
            <div class="layui-form-item">
                <div class="msg_sender">
                    <div class="msg_tab">
                        <div class="tab_navs_panel">
                            <div class="tab_navs_wrp">
                                <div class="layui-tab layui-tab-brief" lay-filter="keywordReply">
                                    <ul class="tab_navs layui-tab-title">
                                        <li id="material" class="tab_nav <c:if test="${pd.TYPE == 'material' }">layui-this actived</c:if>">
                                            <a href="javascript:void(0);">
                                                <i class="layui-icon tab_icon1">&#xe62a;</i>
                                                <span class="tab_span">图文消息</span>
                                            </a>
                                        </li>
                                        <li id="image" class="tab_nav <c:if test="${pd.TYPE == 'image' }">layui-this actived</c:if>" >
                                            <a href="javascript:void(0);">
                                                <i class="layui-icon tab_icon2">&#xe64a;</i>
                                                <span class="tab_span">图片</span>
                                            </a>
                                        </li>
                                        <li class="tab_nav <c:if test="${pd.TYPE == 'text' }">layui-this actived</c:if>" id="text">
                                            <a href="javascript:void(0);">
                                                <i class="layui-icon tab_icon3" style="font-size: 14px">&#xe648;</i>
                                                <span class="tab_span">文字</span>
                                            </a>
                                        </li>
                                        <%-- <li class="tab_nav <c:if test="${pd.TYPE == 'card' }">layui-this actived</c:if>" id="card">
                                            <a href="javascript:void(0);">
                                                <i class="layui-icon tab_icon3" >&#xe65e;</i>
                                                <span class="tab_span">卡券</span>
                                            </a>
                                        </li> --%>
                                    </ul>
                                    <div class="layui-tab-content">
                                    	<!-- 图文内容 -->
                                        <div class="layui-tab-item <c:if test="${pd.TYPE == 'material'}">layui-show</c:if>">
                                            <div class="inner">
                                                <div class="tab_cont_cover" id="newImg">
                                                    <div class="media_cover" id="opende">
                                                        <span class="create_access">
                                                           <a href="javascript:void(0);" >
                                                              <i class="layui-icon tab_icona">&#xe654;</i>
                                                              <strong>从素材库中选择</strong>
                                                           </a>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div id="textImg">
                                                    <div class="rowBoxCenter">
                                                        <p><span class="font-12 padding-30">保存于：${material.CREATE_TIME }</span></p>
                                                        <div class="widthImg">
                                                            <img src="<%=basePath%>uploadFiles/uploadImgs/${material.mList[0].PATH }" alt="">
                                                            <div class="wordTitle"><p>${material.mList[0].TITLE }</p></div>
                                                            <div class="imgyulan">
                                                                <p class="text-center" id="tuwen_0">预览</p>
                                                            </div>
                                                        </div>
                                                        <c:forEach items="${material.mList }" var="var" varStatus="vs" begin="1">
                                                        	<div class="borBottom">
	                                                          <table width="100%" >
	                                                              <tbody >
	                                                              <tr>
	                                                                  <td style="vertical-align: top"><p class="tarTileWid">${var.TITLE }</p></td>
	                                                                  <td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img src="<%=basePath%>uploadFiles/uploadImgs/${var.PATH }" alt=""></div></td>
	                                                              </tr>
	                                                              </tbody>
	                                                          </table>
	                                                          <div class="imgyulan" >
	                                                              <p class="text-center" id="tuwen_${vs.index}">预览</p>
	                                                          </div>
	                                                      </div>
                                                        </c:forEach>
                                                        
                                                    </div>
                                                    <input id="TUWEN_MEDIA_ID" type="hidden" value="${material.MEDIA_ID }">
                                                    <input id="TUWEN_SENDRECORD_ID" type="hidden" value="${material.SENDRECORD_ID }">
                                                    <p id="shanchu" class="shanchu">删除</p>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 图片内容 -->
                                        <div class="layui-tab-item <c:if test="${pd.TYPE == 'image' }">layui-show</c:if>" id="tupianItem" >
                                            <div class="flieImg">
                                             <div class="tab_cont_cover">
                                                 <div class="media_cover" id="beforeImg">
                                                    <span class="create_access">
                                                       <a href="javascript:void(0);">
                                                          <i class="layui-icon tab_icona">&#xe654;</i>
                                                          <strong>选择历史图片</strong>
                                                       </a>
                                                    </span>
                                                 </div>
                                                 <div class="media_cover" id="flieImg">
                                                 	<form action="<%=basePath%>wxMenu/uploadImg.do" method="post" name="Form" id="Form" enctype="multipart/form-data" target="nm_iframe">
                                                   		<input type="file" class="upFlie" id="upFlie" name="upFile">
                                                   	</form>
                                                   	<iframe id="nm_iframe" name="nm_iframe" style="display:none;" ></iframe> 
                                                    <span class="create_access">
                                                       <a href="javascript:void(0);" >
                                                          <i class="layui-icon tab_icona">&#xe654;</i>
                                                          <strong>选择本地上传</strong>
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
                                                     <span id="deleteImg" class="shanchu">删除</span>
                                                 </div>
                                             </div>
                                        	</div>
                                        </div>
                                        <!-- 文字内容 -->
                                        <div class="layui-tab-item <c:if test="${pd.TYPE == 'text' }">layui-show</c:if>" id="wenziItem" >
                                            <div class="layui-form-item layui-form-text" style="margin-top: 30px;">
                                                <!-- <label class="layui-form-label" style="padding: 0 22px;">文字内容</label> -->
                                                <div class="layui-input-block" >
                                                    <textarea placeholder="请输入文字内容" class="layui-textarea" style="width: 100%" id="CONTENT" rows="10" onkeyup="checkNum()" maxlength="300">${text.CONTENT}</textarea>
                                                    <p style="text-align: right">还可以输入<span id="num">300</span>字，按下Enter键换行</p>
                                                </div>
                                                <input id="TEXTMSG_ID" type="hidden" value="${text.TEXTMSG_ID }">
                                            </div> 
                                        </div>
                                        <!-- 卡券内容 -->
                                        <%-- <div class="layui-tab-item <c:if test="${pd.TYPE == 'card' }">layui-show</c:if>" id="kaquanItem">
                                           <div class="cardDiv">
                                               <div class="tab_cont_cover" id="xzCard">
                                                   <div class="media_cover" id="">
                                                          <span class="create_access">
                                                             <a href="javascript:void(0);">
                                                                <i class="layui-icon tab_icona">&#xe654;</i>
                                                                <strong>选择历史卡券</strong>
                                                             </a>
                                                          </span>
                                                   </div>
                                               </div>
                                               <div id="jsCard" >
                                                   <input id="CARD_ID" type="hidden" value="${card.CARD_ID }">
                                                   <div class="cardBox">
                                                       <h1 style="font-size: 18px;font-weight: 600;margin-bottom: 15px;color:#fff;padding: 14px 15px;background-color:#1E9FFF " id="title">${card.SUB_TITLE }</h1>
                                                       <p id="time">${card.AVAILABLE_TIME }</p>
                                                       <p id="adrss">${card.STORE_NAME }</p>
                                                       <p style="padding-bottom: 20px" id="xiangqing">${card.DESCRIPTION }</p>
                                                   </div>
                                                   <span id="deleteCard" class="shanchu">删除</span>
                                               </div>
                                           </div>
                                       </div> --%>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</body>
<script>

    layui.use(["form","layer",'element'],function(){
        var form = layui.form,
            layer = layui.layer,
            element = layui.element;
        
        element.on('tab(keywordReply)', function(data){
			  
			  /* var id = $(".layui-this").attr("id");
			  if(id == "material"){
				  deleteImg();
				  deleteWenzi();
				  deleteCard();
			  }else if(id == "image"){
				  deleteTuwen();
				  deleteWenzi();
				  deleteCard();
			  }else if(id == "text"){
				  deleteTuwen();
				  deleteImg();
				  deleteCard();
			  }else if(id = "card"){
				  deleteTuwen();
				  deleteImg();
				  deleteWenzi();
			  } */
		  })
        
        //选择图文消息
        function opende() {
            parent.layer.open({
                btn: ['确定', '关闭'],
                btn1: function(index, layero){
                    //按钮【按钮一】的回调
                    var res = parent.window["layui-layer-iframe" + index].callTuWen();
                	$(".padding-30").html(res.time);
                	$(".wordTitle").children(":first-child").html(res.ftitle);
                	$(".widthImg").children(":first-child").attr("src",res.fimg);
                	$("#TUWEN_MEDIA_ID").val(res.media_id);
                	$("#TUWEN_SENDRECORD_ID").val(res.sendRecord_id);
                
                	if(parseInt(res.length) > 0){
                		var all = "";
                		for(var i=0;i<res.length;i++){
                			var titleName = "title"+i;
                			var imgName = "img"+i;
                			var title = JSON.parse(res.last)[titleName];
                			var img = JSON.parse(res.last)[imgName];
	                		var div = '<div class="borBottom">'+
					                        '<table width="100%" >'+
					                        '<tbody >'+
					                        '<tr>'+
					                            '<td style="vertical-align: top"><p class="tarTileWid">'+title+'</p></td>'+
					                            '<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img src="'+img+'" alt=""></div></td>'+
					                        '</tr>'+
					                        '</tbody>'+
					                    '</table>'+
					                    '<div class="imgyulan" >'+
					                        '<p class="text-center" id="tuwen_'+(i+1)+'">预览</p>'+
					                    '</div>'+
					                '</div>';
					          all += div;
                		}
                		$(".widthImg").after(all);
                	}
                	
                    $("#newImg").hide();
                    $("#textImg").show();
                    parent.layer.close(index)
                },
                skin: '',
                btnAlign: 'c',
                type: 2,
                title: '添加图文消息',
                shadeClose: false,
                shade: 0.8,
                area: ['55%', '70%'],
                content:[ '<%=basePath%>wxMenu/tuwenList.do'], //iframe的url

            });
        }
        //预览图文
    	var previewTuWen = function(eve){
    		var id = $(eve).children().attr("id");
    		var sequence = id.split("_")[1];
    		var SENDRECORD_ID = $("#TUWEN_SENDRECORD_ID").val();
    		window.open('<%=basePath%>wxMenu/preview.do?SENDRECORD_ID='+SENDRECORD_ID+'&SEQUENCE='+sequence);
    	}
        //选择历史卡券
        function card() {
        	parent.layer.open({
                btn: ['确定', '关闭'],
                btn1: function(index, layero){
                    //按钮【按钮一】的回调
                    var res = parent.window["layui-layer-iframe" + index].callCard();
                    $("#title").html(res.title);
                    $("#title").attr("class",res.titleClass);
                    $("#time").html(res.time);
                    $("#adrss").html(res.adrss);
                    $("#xiangqing").html(res.xiangqing);
                    $("#xzCard").hide();
                    $("#jsCard").show();
                    $("#CARD_ID").val(res.cardId);
                    parent.layer.close(index);
                },
                skin: '',
                btnAlign: 'c',
                type: 2,
                title: '添加您的历史卡券',
                shadeClose: false,
                shade: 0.8,
                area: ['48%', '55%'],
                content:[ '<%=basePath%>wxMenu/cardList.do'] //iframe的url
            });
        }
        
//      选择历史图片
        function openImg() {
            parent.layer.open({
                btn: ['确定', '关闭'],
                btn1: function(index, layero){
                    //按钮的回调
                    var res = parent.window["layui-layer-iframe" + index].callbackdata();
                    $("#successImg").attr("src",res.url);
                    $("#IMG_MEDIA_ID").val(res.media_id);
                    flieImgsuccess();
                    parent.layer.close(index);
                },
                skin: '',
                btnAlign: 'c',
                type: 2,
                title: '选择历史图片',
                shadeClose: false,
                shade: 0.8,
                area: ['764px', '520px'],
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
			                      	    $("#IMG_MEDIA_ID").val(data.MEDIA_ID); 
			                      	    
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
//      预览图片
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


//      绑定事件
        $("body").on("click","#opende",function() {
            opende();
         });
        $("body").on("click","#beforeImg",function() {
            openImg();
        });
        $("body").on("click","#xzCard",function() {
            card();
        });
        $("body").on("change","#upFlie",function() {
            var files = this.files
            uploadImg(files);
        });
        $("body").on("click","#imgyulan",function() {
            yulanTuPian();
        });
        $("body").on("click",".imgyulan",function() {
        	previewTuWen(this);
        });
       
//      删除事件
        $("#shanchu").click(function () {
        	deleteTuwen();
        })
        $("#deleteImg").click(function () {
        	deleteImg();
        })
        $("#deleteCard").click(function () {
        	deleteCard();
        })
        var deleteTuwen = function(){
        	$("#textImg").hide();
            $("#newImg").show();
            $("#TUWEN_MEDIA_ID").val("");
            $("#TUWEN_SENDRECORD_ID").val("");
            $(".borBottom").remove();
		}
		var deleteImg = function(){
			$("#flieImg").show();
            $("#beforeImg").show();
            $("#divImg").hide();
            $("#divImg").children().children().attr("src","");
            $("#upFlie").val("");
            $("#IMG_MEDIA_ID").val("");
		}
		var deleteCard = function(){
			$("#jsCard").hide();
            $("#xzCard").show();
            $("#title").html("");
            $("#title").attr("class","");
            $("#time").html("");
            $("#adrss").html("");
            $("#xiangqing").html("");
            $("#CARD_ID").val("");
		}
		
		var deleteWenzi = function(){
			$("#CONTENT").val("");
			$("#TEXTMSG_ID").val("");
		}

//      点击选中
        $(".tab_navs").children(".tab_nav").click(function () {
            $(this).addClass("actived");
            $(this).siblings().removeClass("actived");
        })
    })
</script>

	<script type="text/javascript">
	$(function(){
		if($("#TUWEN_MEDIA_ID").val() == ""){
			$("#newImg").show();
	        $("#textImg").hide();
		}else{
			$("#newImg").hide();
	        $("#textImg").show();
		}
		
		if($("#IMG_MEDIA_ID").val() == ""){
			$("#flieImg").show();
	        $("#beforeImg").show();
	        $("#divImg").hide();
		}else{
			$("#flieImg").hide();
	        $("#beforeImg").hide();
	        $("#divImg").show();
		}
		
		if($("#CARD_ID").val() == ""){
			$("#jsCard").hide();
            $("#xzCard").show();
		}else{
			$("#jsCard").show();
            $("#xzCard").hide();
		}
		
		checkNum();
	})
	
		function save(){
			var name = $("#NAME").val();
			if(name == ""){
				layer.tips('请输入规则名称', '#NAME', {
			 		tips: 3,
			 		time: 1000
	        	});
				$("#NAME").focus();
				return false;
			}else if(name.length > 60){
				layer.tips('规则名称不能超过60个字', '#NAME', {
			 		tips: 3,
			 		time: 1000
	        	});
				$("#NAME").focus();
				return false;
			}
			
			var keyword = $("#KEYWORD").val();
			if(keyword == ""){
				layer.tips('请输入关键词', '#KEYWORD', {
			 		tips: 3,
			 		time: 1000
	        	});
				$("#KEYWORD").focus();
				return false;
			}else if(keyword.length > 30){
				layer.tips('关键词不能超过30个字', '#KEYWORD', {
			 		tips: 3,
			 		time: 1000
	        	});
				$("#KEYWORD").focus();
				return false;
			}
			
			
			var type = $(".layui-this").attr("id");
			var object_id = "";
			if(type == "material"){
				object_id = $("#TUWEN_MEDIA_ID").val();
			}else if(type == "image"){
				object_id = $("#IMG_MEDIA_ID").val();
			}else if(type == "text"){
				object_id = $("#CONTENT").val();
			}else if(type = "card"){
				object_id = $("#CARD_ID").val();
			}
			if(object_id == ""){
				layer.msg("回复内容不能为空！",{time:1000});
				return false;
			}
			var AUTOREPLY_ID = $("#AUTOREPLY_ID").val();
			
			var data = {
				name: name,
				keyword: keyword,
				type: type,
				object_id: object_id,
				AUTOREPLY_ID: AUTOREPLY_ID
			}
			return data;
		}
		
		function checkNum(){
			var length = $("#CONTENT").val().length;
			$("#num").html(300-length);
		}
		
	</script>
</html>