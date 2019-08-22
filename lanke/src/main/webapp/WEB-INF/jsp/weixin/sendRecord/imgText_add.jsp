<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    
	<base href="<%=basePath%>">
	<%@ include file="../../system/index/top.jsp"%>
	
	<!-- 删除时确认窗口 -->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/third-party/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/ace/js/bootbox.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/a92d301d77.js"> </script>
	
	<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
	<script src="<%=basePath%>newStyle/layer/layer.js"></script>
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
 
	<link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/default/_css/ueditor.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/96619a5672.css" />
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/newtextimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/style.css">
    <style type="text/css">
    	.modal-backdrop.in{
    		z-index: 0!important;
    		opacity:0.3;
    	}
    	.edui-default .edui-editor-iframeholder {
		    height: calc(100vh - 64px)!important;
		    overflow-y:auto!important; 
		}
		#edui_fixedlayer {z-index: 1000!important}
    </style>
</head>
<body class="no-skin scroll">
	
	<div class="bodyBox">
        <%-- <div class="navlist">
            <div class="row">
                <div class="col-md-3 lineHieght">
                	<c:if test="${title == 'add'}"><p>新建图文 / <span>编辑</span></p></c:if>
					<c:if test="${title == 'edit'}"><p>修改图文 / <span>编辑</span></p></c:if>
                </div>
                <div class="col-md-2 col-md-offset-6 text-center" style="line-height: 60px;">
                	<img src="${org.PATH }" alt="" width="35" style="padding-right:10px">${org.INTENET_NAME}
                </div>
            </div>
        </div> --%>
        <div class="height1">
            <div class="row rowes">
                <div class="col-md-3 maxWin">
                    <div class="rowBoxCenter scroll" style="overflow: auto;">
                        <p class="font-14">图文列表</p>
                        <div class="widthImg" id="div0" onclick="changeNews(0)">
                            <!--此处添加img-->
                       		<c:if test="${pd.PATH != null and pd.PATH != '' }">
								<img alt="" src="<%=basePath %>uploadFiles/uploadImgs/${pd.PATH }" id="image0"/>
                           	</c:if>
                           	<c:if test="${pd.PATH == null or pd.PATH == '' }">
	                            <img src="<%=basePath%>newStyle/images/normalImg1.png" alt="" id="image0">
                           	</c:if>
                            <div class="wordTitle">
                            	<c:if test="${pd.TITLE != null and pd.TITLE != '' }">
	                                <p id="title0" >${pd.TITLE }</p>
                            	</c:if>
                            	<c:if test="${pd.TITLE == null or pd.TITLE == '' }">
                        			<p id="title0" >未命名</p>
                            	</c:if>
                                <div class="hoverTar">
                                    <div class="row" style="margin: 0;">
                                        <div class="col-md-4 text-left"><p style="line-height: 40px;color:#fff;padding: 0"  onclick="mouseUp(0)" id="up0">上移</p></div>
                                        <div class="col-md-4 text-center"><p style="line-height: 40px;color:#fff;padding: 0" onclick="mouseDown(0)" id="down0">下移</p></div>
                                        <div class="col-md-4 text-right" id="boxSc"><p style="line-height: 40px;color:#fff;padding: 0" onclick="deleteNews(0)" id="delete0">删除</p></div>
                                        <div class="hiddened" id="content0">${pd.CONTENT}</div>
                                        <div class="hiddened" id="author0">${pd.CREATE_USER }</div>
                                        <div class="hiddened" id="description0">${pd.DIGEST }</div>
                                        <div class="hiddened" id="source_url0">${pd.CONTENT_SOURCE_URL }</div>
                                        <div class="hiddened" id="media_id0">${pd.THUMB_MEDIA_ID }</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <c:choose>
                        	<c:when test="${not empty mList}">
                        		<c:forEach items="${mList }" begin="1" var="var" varStatus="vs">
			                        <div class="borBottom" id="div${vs.index }" onclick="changeNews('${vs.index}')">
			                            <table width="100%" class="tabHover">
			                                <tbody>
			                                <tr>
			                                    <td style="vertical-align: top"><p class="tarTileWid" id="title${vs.index }">${var.TITLE }</p></td>
			                                    <td style="vertical-align: top;text-align: right"><div class="tarImgWid"><!--此处添加img--><img src="<%=basePath %>uploadFiles/uploadImgs/${var.PATH}" alt="" id="image${vs.index }"></div></td>
			                                </tr>
			                                </tbody>
			                            </table>
			                            <div class="wordTitle back">
			                                <div class="hoverTar">
			                                    <div class="row" style="margin: 0;">
			                                        <div class="col-md-4 text-left"><p style="line-height: 40px;color:#fff;padding: 0" onclick="mouseUp('${vs.index}')" id="up${vs.index }">上移</p></div>
			                                        <div class="col-md-4 text-center"><p style="line-height: 40px;color:#fff;padding: 0" onclick="mouseDown('${vs.index}')" id="down${vs.index }">下移</p></div>
			                                        <div class="col-md-4 text-right" ><p style="line-height: 40px;color:#fff;padding: 0" onclick="deleteNews('${vs.index}')" id="delete${vs.index }">删除</p></div>
			                                        <div class="hiddened" id="content${vs.index }">${var.CONTENT }</div>
			                                        <div class="hiddened" id="author${vs.index }">${var.CREATE_USER }</div>
			                                        <div class="hiddened" id="description${vs.index }">${var.DIGEST }</div>
			                                        <div class="hiddened" id="source_url${vs.index }">${var.CONTENT_SOURCE_URL }</div>
			                                        <div class="hiddened" id="media_id${vs.index }">${var.THUMB_MEDIA_ID }</div>
			                                    </div>
			                                </div>
			                            </div>
			                        </div>
                        		</c:forEach>
                        	</c:when>
                        </c:choose>
                        <div class="new">
                            <div class="borshu"></div>
                            <div class="borheng"></div>
                        </div>
                    </div>
                </div>
                
                <form action="sendRecord/preview.do" name="preview" id="preview" method="post" enctype="multipart/form-data" target="_blank">
	                <div class="col-md-6 zenxuid">
	                    <div class="edit scroll" id="maxWin1">
<%--	                    	<span id="styleName" onclick="getStyle()">展开样式</span>--%>
		                    <textarea  name="CONTENT" id="CONTENT" maxlength="255" placeholder="这里输入图文内容" 
		                    	title="图文内容"  style="width: 870px; height: 400px; margin: 0 auto;" >${pd.CONTENT}</textarea>
	                    </div>
		            </div>
	                <div class="col-md-3">
	                    <div class="outside maxWin2 scroll" style="overflow-x: hidden;overflow-y:auto;">
	                        <a ><p class="outp" onclick="siMenu('z240','lm27','揽客文章库','articleLib/list.do')">去选择揽客文章库</p></a>
	                        <p class="widmar hegi" style="color:#000;">发布设置</p>
	                        <p class="widmar hegi">封面<span style="color:#999;font-size: 12px">（建议尺寸：900像素*500像素）</span></p>
	                        
	                        <c:choose>
                        		<c:when test="${pd.PATH != null and pd.PATH != ''}">
									<p class="widmar imgheg"><img alt="" src="<%=basePath %>uploadFiles/uploadImgs/${pd.PATH }" width="90px" id="loadImg" name="loadImg"/></p>
                        		</c:when>
                        		<c:otherwise>
			                        <p class="widmar imgheg"><img src="<%=basePath%>newStyle/images/normalImg1.png" alt="" width="90px" id="loadImg"></p>
                        		</c:otherwise>
                        	</c:choose>
                        	<p class="widmar btnPt"><a class="phonot"  onclick="modalesd()">上传图片</a></p>
	                        
	                        <p class="widmar heighted marTop">文章标题</p>
	                        <p class="widmar heighted"><input type="text" id="TITLE" name="TITLE" class="inputHe" value="${pd.TITLE }" onkeyup="saveData('title')"></p>
	                        <p class="widmar heighted">作者</p>
	                        <p class="widmar heighted"><input type="text" id="AUTHOR" name="AUTHOR" class="inputHe" value="${pd.CREATE_USER }" onkeyup="saveData('author')"></p>
	                        <p class="widmar heighted">摘要<span style="color:#999;font-size: 12px">（选填）</span></p>
	                        <p class="widmar"><textarea id="DESCRIPTION" name="DESCRIPTION" class="inputHe textrea" onkeyup="saveData('description')">${pd.DIGEST}</textarea></p>
	                        <input type="hidden" id="SOURCE_URL" name="SOURCE_URL" value="${pd.CONTENT_SOURCE_URL }" onkeyup="saveData('source_url')">
	                        <p class="widmar btnPt marTop marBot"><a class="phonot" onclick="modelteep()">群发设置</a></p>
	                    </div>
	                </div>
					<input type="hidden" id="send_sequence" name="send_sequence">
				</form>
	                <!-- Modal 上传图片-->
	                <form action="sendRecord/uploadImg.do" name="Form" id="Form" method="post" enctype="multipart/form-data" target="nm_iframe">
	                <input type="hidden" id="INTERNET_ID" name="INTERNET_ID" value="${org.INTENET_ID}">
					<input type="hidden" id="SENDRECORD_ID" name="SENDRECORD_ID" value="${org.SENDRECORD_ID}">
					<input type="hidden" id="INTERNETMATERIAL_ID" name="INTERNETMATERIAL_ID" value="${pd.INTERNETMATERIAL_ID }">
	                <input type="hidden" id="SEQUENCE" name="SEQUENCE" value="0"><!-- 新增/修改，初始值 = 0 -->
	                <input type="hidden" id="PICTURE_ID_HIDDEN" name="PICTURE_ID_HIDDEN"  value="${var.PICTURE}">
	                
			        
			        </form>
            </div>
        </div>
        <iframe id="nm_iframe" name="nm_iframe" style="display:none;" ></iframe> 
        <div class="footfixed">
            <div class="footWid">
                <p class="footBtn " onclick="save(1)"><span class="btn-success">保存</span></p>
                <p class="footBtn " onclick="preview333()"><span class="btn-primary">预览</span></p>
                <p class="maright " onclick="groupSend()"><span class="btn-warning">群发</span></p>
            </div>
        </div>
        
      	
        
    </div>
	
	
	
	<!-- 页面底部jsp -->
	<%@ include file="../../system/index/foot.jsp"%>

	<script src="<%=basePath%>static/js/myjs/head.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#m").css("display","none");
			$("#t").css("display","none");
		})
		//展开/收起样式
		function getStyle(){
			var styleName = $("#styleName").html();
			if(styleName == "展开样式"){
				$("#maxWin1").addClass("maxWin1");
				$("#eduiedui12-styles")[0].style.display = "block";
				$("#styleName").html("收起样式");
				$("#copyright").hide();
			}else if(styleName == "收起样式"){
				$("#maxWin1").removeClass("maxWin1");
				$("#eduiedui12-styles")[0].style.display = "none";
				$("#styleName").html("展开样式");
				$("#copyright").hide();
			}
		}	
	
		
		
		
		//保存
		function save(type){
			//将现展示的图文保存到当前图文序号中
			var ue = UE.getEditor("CONTENT");
            var oldContent = ue.getContent();
			var number = $("#SEQUENCE").val();
			var contentId = "#content" + number;
			$(contentId).html(oldContent);
			
			//判断是否为空
			var map = "[";
			var length = parseInt($("#div0").nextAll().length)  ;//取盒子的数量
			for(var i=0;i<length;i++){
				var title = "#title" + i;
				var author = "#author" + i;
				var image = "#media_id" + i;
				var content = "#content" + i;
				var description = "#description" + i;
				var source_url = "#source_url" + i;
				
				var p = parseInt(i) + 1;
				if($(title).html().trim() == ""){
					layer.msg("第"+p+"篇的标题不能为空！",{time:1500});
					return;
				}
				if($(author).html().trim() ==""){
					layer.msg("第"+p+"篇的作者不能为空！",{time:1500});
					return;
				}
				if($(image).html().trim() ==  ""){
					layer.msg("第"+p+"篇的封面图片不能为空！",{time:1500});
					return;
				}
				var con = $(content).html().trim();
				if($(content).html().trim() == ""){
					layer.msg("第"+p+"篇的内容不能为空！",{time:1500});
					return;
				}
				
				
				con = con.replace(/'/g,"&#39;");
				
				map += "{'title"+i+"':'"+$(title).html()+"',"+
					"'author"+i+"':'"+$(author).html()+"',"+
					"'description"+i+"':'"+$(description).html()+"',"+
					"'source_url"+i+"':'"+$(source_url).html()+"',"+
					"'content"+i+"':'"+con+"',"+
					"'media_id"+i+"':'"+$(image).html()+"'"+
				"}";
				
				if(i != (parseInt(length)-1)){
					map += ","
				}
			}
			map += "]";
			
			var sendId = $("#SENDRECORD_ID").val();
			$.ajax({
	            type	: "POST",
	            url		:'<%=basePath%>sendRecord/add.do',
	            data	:{map:map,SENDRECORD_ID:sendId},
	            dataType:'json',
	            success: function(data) {
            		if(type == "1"){
            			layer.msg(data.message,{time:1500});
						if(data.result == "true"){
							return true;
						}
            		}
	            },
	            error:function(){
	               layer.msg("系统繁忙，请稍后再试！",{time:1500});
	               return;
	            }
	        });
			
		}
		
		//一键群发,群发之前先保存一遍
		function groupSend(){
			save(2);
			layer.confirm('确定要群发吗?<br>注意：每个月只能发四条!', {
				btn: ['确定','取消'],
			}, function(){
				var sendRecordId = $("#SENDRECORD_ID").val();
				$.ajax({
		            type	: "POST",
		            url		:'<%=basePath%>sendRecord/groupSend.do',
		            data	:{sendRecordId:sendRecordId},
		            dataType:'json',
		            beforeSend	: beforeSend,
		            success: function(data) {
		            	layer.closeAll('loading');
		            	layer.msg(data.message,{time:1500});
						if(data.result == "true"){
							
						}
		            },
		            error:function(){
		            	layer.closeAll('loading');
		               layer.msg("系统繁忙，请稍后再试！",{time:1500});
		               return;
		            }
		        });
			}, function(){
				return
			})
		}
		function beforeSend(){
	    	layer.load(2);
	    }
		//预览，打开新窗口
		function preview333(){
            save(2);

            setTimeout(function () {
                var sendRecordId = $("#SENDRECORD_ID").val();
                var number = $("#SEQUENCE").val();

                var media_id = "#media_id" + number;
                var media = $(media_id).html();

                if(media == ''){
                    layer.msg("请上传图片");
                    return false;
                }else{
                    $("#send_sequence").val(sendRecordId+";"+number+";"+media);
                    $("#preview").submit();
                }
            },800)

		}
		
		//键盘弹起时，保存当前图文的数据
		function saveData(type){
			var number = $("#SEQUENCE").val();
			
			var titleId = "#title" + number;
			var authorId = "#author" + number;
			var descriptionId = "#description" + number;
			var source_urlId = "#source_url" + number;
			
			$(titleId).text($("#TITLE").val());
			$(authorId).html($("#AUTHOR").val());
			$(descriptionId).html($("#DESCRIPTION").val());
			$(source_urlId).html($("#SOURCE_URL").val());
		}
		
		//切换图文
		function changeNews(number){ 
			//老图文
			var ue = UE.getEditor("CONTENT");
			var oldContent = ue.getContent();
			var oldNumber = $("#SEQUENCE").val();
			var oldContentId = "#content" + oldNumber;
			$(oldContentId).html(oldContent);
			
			//number 为即将切换过去的图文
			$("#SEQUENCE").val(number);
			
			var imgId = "#image" + number; 
			var titleId = "#title" + number;
			var contentId = "#content" + number;
			var authorId = "#author" + number;
			var descriptionId = "#description" + number;
			var source_urlId = "#source_url" + number;
			
			//已保存的图文各个内容
			var img = $(imgId)[0].src;
			var title = $(titleId).text();
			var content = $(contentId).html();
			var author = $(authorId).html();
			var description = $(descriptionId).html();
			var source_url = $(source_urlId).html();
			
			if(title == "未命名"){
				title = "";
			}
			
			//展示的内容
			$("#loadImg").attr("src",img);
			$("#TITLE").attr("value",title);
			$("#TITLE").val(title);
			$("#AUTHOR").attr("value",author);
			$("#AUTHOR").val(author);
			$("#DESCRIPTION").val(description);
			
			$("#SOURCE_URL").attr("value",source_url);
			$("#SOURCE_URL").val(source_url);
			ue.setContent(content);
			
			$("#SEQUENCE").val(number);
		}
		
		
		
	</script>
	
		<script type="text/javascript">
            //赋初始值，提供给a92d301d77.js使用
			var BASEURL = 'https://www.135editor.com';
			var appkey = '59e710b8-db88-48f0-bb92-4f4a79291994';
			var domain_135 = BASEURL;

            //赋值给current_editor，提供给a92d301d77.js使用
			var current_editor = UE.getEditor('CONTENT',{
			    initialFrameHeight:600,
			    
			    appkey		:appkey,
                plat_host   :'www.135editor.com',
			    style_url 	: domain_135+'/editor_styles/open?inajax=1&v=page&appkey='+appkey,
			    page_url 	: domain_135+'/editor_styles/open_styles?inajax=1&appkey='+appkey,
			   // style_width	:360,
			   // zIndex 		: 900,
			    
			    pageLoad	:true, 
			    open_editor	:true,
			    focus		:true,
                focusInEnd	:true
			    //scaleEnabled:true,
			    //autoHeightEnabled:true,
			  	//minFrameWidth:800    //编辑器拖动时最小宽度,默认800
		        //minFrameHeight:220  //编辑器拖动时最小高度,默认220
			    
			});
			
			UE.getEditor("CONTENT").addListener( 'contentChange', function( editor ) {
			    
			    var ue = UE.getEditor("CONTENT");
			    var number = $("#SEQUENCE").val();
				var contentId = "#content" + number;
				$(contentId).html(ue.getContent());
			});

		</script>
		
		
		<script>
        $(function () {
            buleBor();
            panduan();
                $(".new").click(function () {
                	var sendId = $("#SENDRECORD_ID").val();
                	$.ajax({
        	            type	: "POST",
        	            url		:'<%=basePath%>sendRecord/checkIsSend.do',
        	            data	:{SENDRECORD_ID:sendId},
        	            dataType:'json',
        	            success: function(data) {
        	            	if(data.result == "false"){
                                layer.msg("当前图文已群发，不能再操作",{time:1500});
        	            	}else if(data.result == "true"){
			//                    获取动态添加的div的个数
			                    var borBottom = $(".rowBoxCenter").children('.borBottom').length;
			                    var i = borBottom + 1;
			//                    大于7个不能添加第元素了，最多只能发布8篇文章，包含第一个固定存在的
			                    if( borBottom < 7){
			//                        添加按钮的前一个兄弟节点
			                        var preSb = $(".new").prev();
			//                        动态添加div的结构
			                        var htmltext = '<div class="borBottom" id="div'+i+'" onclick="changeNews('+i+')">'+
			                            '<table width="100%" class="tabHover">'+'<tbody>'+'<tr>'+'<td style="vertical-align: top"><p class="tarTileWid" id="title'+i+'">未命名</p></td>'+
			                            '<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><!--此处添加img--><img src="<%=basePath%>newStyle/images/normalImg1.png" alt="" id="image'+i+'"></div></td>'+
			                            '</tr>'+'</tbody>'+'</table>'+'<div class="wordTitle back">'+'<div class="hoverTar">'+'<div class="row" style="margin: 0;">'+
			                            '<div class="col-md-4 text-left"><p style="line-height: 40px;color:#fff;padding: 0" onclick="mouseUp('+i+')" id="up'+i+'">上移</p></div>'+
			                            '<div class="col-md-4 text-center"><p style="line-height: 40px;color:#fff;padding: 0" onclick="mouseDown('+i+')" id="down'+i+'">下移</p></div>'+
			                            '<div class="col-md-4 text-right"><p style="line-height: 40px;color:#fff;padding: 0" onclick="deleteNews('+i+')" id="delete'+i+'">删除</p></div>'+
				                        '<div class="hiddened" id="content'+i+'"></div><div class="hiddened" id="author'+i+'"></div><div class="hiddened" id="description'+i+'"></div><div class="hiddened" id="source_url'+i+'"></div><div class="hiddened" id="media_id'+i+'"></div>'+
			                        	'</div>'+'</div>'+'</div>'+'</div>'
			//                        节点后面添加div
			                        preSb.after(htmltext);
			                        buleBor();
			                        panduan();
			                    }else {
			//                        超过7个弹出提示
			                        layer.msg("最多只能发布八篇图文哦~",{time:1500});
			                    }
        	            	}
        	            },
        	            error:function(){
        	               layer.msg("系统繁忙，请稍后再试！",{time:1500});
        	               return;
        	            }
        	        });
                })
        })
//       每一个添加后的div绑定移入事情,显示蓝色边框
        var buleBor = function () {
            $(".rowBoxCenter .borBottom").each(function () {
                var borBox = $(this).children(":first");
                borBox.mouseover(function () {
                    $(this).parent().css("border","1px solid #a3d8f5")
                })
                borBox.mouseout(function () {
                    $(this).parent().css("border","none")
                    $(this).parent().css("border-bottom","1px solid #e1e1e1")
                })
            });
        }
 
        
        //删除方法
		var deleteNews = function (number){
			layer.confirm('确定要删除吗?', {
				btn: ['确定','取消'],
				title: '删除',
			}, function(){
				var sendRecordId = $("#SENDRECORD_ID").val();
				var url = "<%=basePath%>sendRecord/deleteNews.do?sendRecordId="+sendRecordId+"&number="+number;
				$.ajax({
					async:false,
					type: "POST",
					url: url,
				   	data: {},
					dataType:'json',
					cache: false,
					success: function(data){
						layer.msg(data.message, {time :1000,icon :1});
						//取到当前盒子的ID
						var d = "#"+"div" + number;
						//判断除开第一个外的盒子个数
						var borBottom = $(".rowBoxCenter").children('.borBottom').length;
						//如果除开第一个外的盒子点击删除，移出该盒子.
						if(number > 0){
							edit(number,$(d).nextAll().length);
							$(d).remove();
						}else if(number == 0  && borBottom == 0){
							$("#image0").attr("src","<%=basePath%>newStyle/images/normalImg.png")
							$("#title0").text("未命名");
							$("#content0").html("");
							$("#author0").html("");
							$("#description0").html("");
							$("#source_url0").html("");
							$("#media_id0").html("");
						}else if(number == 0 && borBottom > 0){
							var src = $("#image1")[0].src;
							var tex = $("#title1").text();
							var content = $("#content1").html();
							var author = $("#author1").html();
							var description = $("#description1").html();
							var source_url = $("#source_url1").html();
							var media_id = $("#media_id1").html();
							$("#image0").attr("src",src);
							$("#title0").text(tex);
							$("#content0").html(content);
							$("#author0").html(author);
							$("#description0").html(description);
							$("#source_url0").html(source_url);
							$("#media_id0").html(media_id);
							 
							$("#div1").remove();
							edit(number,$(d).nextAll().length);
						}
						panduan();
						if(number == 0 && data.result == "true"){
							setTimeout(function(){
								var internetId = $("#INTERNET_ID").val();
								window.location.href = "<%=basePath%>sendRecord/goAdd.do?internetId="+internetId;
							},500)
							
							
							
						}else{
							
						}
					}
				});
			}, function(){
				return
				}
			);
		}
        
        
//      恢复id排序方法
        var edit = function(number,length){
            if(number > 0){
                number += 1;
            }else if(number == 0){
                number += 2;
            }
            for(var i=0;i<length-1;i++) {
                var mun = number+i;//这个加1是删除节点的下一个DIV序号  加i下一个div的循环查找
                var row = mun-1;//删除后上移的序号
                var after = "#"+"div" + mun;
                var bofer = "div" + row;
                $(after).attr("id",bofer);
                $(after).attr("onclick","changeNews("+row+")");

                var aftImg = "#"+"image" + mun;
                var bofImg = "image" + row;
                $(aftImg).attr("id",bofImg);

                var aftTitle = "#"+"title" + mun;
                var bofTitle = "title" + row;
                $(aftTitle).attr("id",bofTitle);

                var aftDetele = "#"+"delete" + mun;
                var bofDetele = "delete" + row;
                $(aftDetele).attr("onclick","deleteNews("+row+")");
                $(aftDetele).attr("id",bofDetele);

                var aftdown = "#"+"down" + mun;
                var bofdown = "down" + row;
                $(aftdown).attr("onclick","mouseDown("+row+")");
                $(aftdown).attr("id",bofdown);

                var aftup = "#"+"up" + mun;
                var bofup = "up" + row;
                $(aftup).attr("onclick","mouseUp("+row+")");
                $(aftup).attr("id",bofup);
                
                var aftcontent = "#"+"content" + mun;
                var bofcontent = "content" + row;
                $(aftcontent).attr("id",bofcontent);
                
                var aftauthor = "#"+"author" + mun;
                var bofauthor = "author" + row;
                $(aftauthor).attr("id",bofauthor);
                
                var aftdescription = "#"+"description" + mun;
                var bofdescription = "description" + row;
                $(aftdescription).attr("id",bofdescription);
                
                var aftsource_url = "#"+"source_url" + mun;
                var bofsource_url = "source_url" + row;
                $(aftsource_url).attr("id",bofsource_url);
                
                var aftmedia_id = "#"+"media_id" + mun;
                var bofmedia_id = "media_id" + row;
                $(aftmedia_id).attr("id",bofmedia_id);
            }
        }

//        下移方法
        var mouseDown = function(number){
            var rep = parseInt(number)+1;
            mouse(number,rep);
        }

//        上移的方法
        var mouseUp = function(number){
            var tep = parseInt(number)-1;
            mouse(number,tep);
        }
        var mouse = function (number,set) {
            var img = "#"+"image" + number;
            var tit = "#"+"title" + number;
          	var content = "#"+"content" + number;
            var author = "#"+"author" + number;
            var description = "#"+"description" + number;
            var source_url = "#"+"source_url" + number; 
            var media_id = "#"+"media_id" + number; 
            
            var nextimg = "#"+"image" + set;
            var nexttit = "#"+"title" + set;
          	var nextcontent = "#"+"content" + set;
            var nextauthor = "#"+"author" + set;
            var nextdescription = "#"+"description" + set;
            var nextsource_url = "#"+"source_url" + set; 
            var nextmedia_id = "#"+"media_id" + set;
            
            var imgSrc = $(img)[0].src;
            var titText = $(tit).text();
            var contentHtml = $(content).html();
            var authorHtml = $(author).html();
            var descriptionHtml = $(description).html();
            var source_urlHtml = $(source_url).html();  
            var media_idHtml = $(media_id).html(); 
            
            var nextimgSrc = $(nextimg)[0].src;
            var nexttitText = $(nexttit).text();
         	var nextcontentHtml = $(nextcontent).html();
            var nextauthorHtml = $(nextauthor).html();
            var nextdescriptionHtml = $(nextdescription).html();
            var nextsource_urlHtml = $(nextsource_url).html();
            var nextmedia_idHtml = $(nextmedia_id).html();
            
            $(img).attr("src",nextimgSrc);
            $(nextimg).attr("src",imgSrc);
            
            $(tit).text(nexttitText);
            $(nexttit).text(titText); 
            
            $(content).html(nextcontentHtml);
            $(nextcontent).html(contentHtml);
            
            $(author).html(nextauthorHtml);
            $(nextauthor).html(authorHtml);
            
            $(description).html(nextdescriptionHtml);
            $(nextdescription).html(descriptionHtml);
            
            $(source_url).html(nextsource_urlHtml);
            $(nextsource_url).html(source_urlHtml); 
            
            $(media_id).html(nextmedia_idHtml);
            $(nextmedia_id).html(media_idHtml); 
            
            
            changeNews(number);
        }
        var panduan = function(){
            var i = $(".rowBoxCenter").children(".borBottom").length;
            if(i == 0 ){
                $("#down0").hide()
                $("#up0").hide()
            }else if(i > 0 ){
                $("#up0").hide()
            }
            var lastIs = $(".new").prev().attr("id");
            var num = lastIs.charAt(lastIs.length - 1);
            var Id = "#"+"down"+num;
            var red = num-1;
            var bef = "#"+"down"+red;
            if(i == num){
                $(Id).hide();
                $(bef).show();
            }
        }
  

        //上传图片
        function modalesd(){
        	var sendId = $("#SENDRECORD_ID").val();
        	$.ajax({
	            type	: "POST",
	            url		:'<%=basePath%>sendRecord/checkIsSend.do',
	            data	:{SENDRECORD_ID:sendId},
	            dataType:'json',
	            success: function(data) {
	            	if(data.result == "false"){
                        layer.msg(data.message,{time:1500});
	            	}else if(data.result == "true"){
			        	layer.open({
			                btn: ['确定', '关闭'],
			                btn1: function(index, layero){
			                    //按钮的回调
			                    var res = window["layui-layer-iframe" + index].getImg();
			                    $("#loadImg").attr("src",res.imgPath);
			                    
			                    var number = $("#SEQUENCE").val();
			                  	var imgId = "#image" + number;
			                  	var media_id = "#media_id" + number;
			                  	$(imgId).attr("src",res.imgPath);
			                  	$(media_id).html(res.media_id);
			                  	
			                    layer.close(index);
			                },
			                skin: '',
			                btnAlign: 'c',
			                type: 2,
			                title: '上传图片',
			                shadeClose: false,
			                shade: 0.8,
			                area: ['600px', '410px'],
			                content:[ '<%=basePath%>sendRecord/goUploadImg.do'] //iframe的url
			               
			            });
	            	}
	            },
	            error:function(){
	               layer.msg("系统繁忙，请稍后再试！",{time:1500});
	               return;
	            }
	        });
        }
        //群发设置
        function modelteep(){
        	var sendId = $("#SENDRECORD_ID").val();
			$.ajax({
	            type	: "POST",
	            url		:'<%=basePath%>sendRecord/checkRecord.do',
	            data	:{SENDRECORD_ID:sendId},
	            dataType:'json',
	            success: function(data){
	            	
	            	if(data.result == "false"){
                        layer.msg(data.message,{time:1500});
	            	}else if(data.result == "true"){
			        	layer.open({
			                btn: ['保存', '关闭'],
			                btn1: function(index, layero){
			                    //按钮的回调
			                    var res = window["layui-layer-iframe" + index].setParam();
			                    $.get(res.url,function(data){
		            				layer.msg(data.message,{time:1000});
			            			if(data.result == "true"){
			            				setTimeout(function () {layer.close(index);},500);
			            			}
			            		});
			                  	
			                },
			                skin: '',
			                btnAlign: 'c',
			                type: 2,
			                title: '群发设置',
			                shadeClose: false,
			                shade: 0.8,
			                area: ['500px', '400px'],
			                content:[ '<%=basePath%>sendRecord/goGroupSet.do?SENDRECORD_ID='+sendId] //iframe的url
			               
			            });
	            	}
	            	
	            },
	            error:function(){
	               layer.msg("系统繁忙，请稍后再试！",{time:1500});
	               return;
	            }
	        });
        }
        $('.modal-backdrop').each(function() {
        	$(this).attr('id', 'id_' + Math.random());
        });
        
   
        
    </script>
    
		
	</body>
</html>

