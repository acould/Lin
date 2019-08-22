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
	 
	<link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/default/_css/ueditor.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/96619a5672.css" />
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/newtextimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/style.css">
    <style type="text/css">
    	.modal-backdrop.in{
    		z-index: 0!important;
    		opacity:0.3;
    	}
    </style>
</head>
<body class="no-skin scroll">
	
	<div class="bodyBox">
        <div class="navlist">
            <div class="row">
                <div class="col-md-3 lineHieght">
                	<c:if test="${title == 'add'}"><p>新建图文 / <span>编辑</span></p></c:if>
					<c:if test="${title == 'edit'}"><p>修改图文 / <span>编辑</span></p></c:if>
                </div>
                <div class="col-md-2 col-md-offset-6 text-center" style="line-height: 60px;">
                	<img src="${org.PATH }" alt="" width="35" style="padding-right:10px">${org.INTENET_NAME}
                </div>
            </div>
        </div>
        <div class="height1">
            <div class="row rowes">
                <div class="col-md-3 maxWin">
                    <div class="rowBoxCenter">
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
	                    <div class="edit" id="maxWin1">
<%--	                    	<span id="styleName" onclick="getStyle()">展开样式</span>--%>
		                    <textarea  name="CONTENT" id="CONTENT" maxlength="255" placeholder="这里输入图文内容" 
		                    	title="图文内容"  style="width: 870px; height: 400px; margin: 0 auto;" >${pd.CONTENT}</textarea>
	                    </div>
		            </div>
	                <div class="col-md-3">
	                    <div class="outside maxWin2">
	                        <a ><p class="outp">去选择揽客文章库</p></a>
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
				</form>
	                <!-- Modal 上传图片-->
	                <form action="sendRecord/uploadImg.do" name="Form" id="Form" method="post" enctype="multipart/form-data" target="nm_iframe">
	                <input type="hidden" id="INTERNET_ID" name="INTERNET_ID" value="${org.INTENET_ID}">
					<input type="hidden" id="SENDRECORD_ID" name="SENDRECORD_ID" value="${org.SENDRECORD_ID}">
					<input type="hidden" id="INTERNETMATERIAL_ID" name="INTERNETMATERIAL_ID" value="${pd.INTERNETMATERIAL_ID }">
	                <input type="hidden" id="SEQUENCE" name="SEQUENCE" value="0"><!-- 新增/修改，初始值 = 0 -->
	                <input type="hidden" id="PICTURE_ID_HIDDEN" name="PICTURE_ID_HIDDEN"  value="${var.PICTURE}">
	                
			        <div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			            <div class="modal-dialog  modal-lg" role="document">
			                <div class="modal-content zindexTop">
			                    <div class="modal-header">
			                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			                        <h4 class="modal-title" id="myModalLabel">上传图片</h4>
			                    </div>
			                    <div class="modal-body">
			                        <p id="btnsol"><span class="btn-bd1 btnActive">本地上传</span><span class="btn-bd">公众号历史图片</span></p>
			                        <div class="file" id="bendi">
			                            <div class="row ">
			                                <div class="col-md-2 filechid" >
			                                    <div class="imgDiv shangchuanImg">
			                                    	
			                                    </div>
			                                </div>
			                            </div>
			                            <input type="file" id="file" name="file" onchange="uploadImg(this.files)">
			                        </div> 
			                       <div class="file " id="history" style="display: none">
			                            <div class="row filerow" >
		                                	<c:forEach items="${imgList }" var="var" varStatus="vs">
				                                <div class="col-md-2 filechid">
				                                    <div class="imgDiv removeactive">
				                                    	<div style="display:none">${var.PICTURE_ID }</div>
				                                    	<img alt="" src="<%=basePath %>uploadFiles/uploadImgs/${var.PATH }">
				                                    </div>
				                                </div>
		                                	</c:forEach>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="modal-footer">
			                        <button type="button" class="btn btn-default modal-Btn marleft" data-dismiss="modal">取消</button>
			                    </div>
			                </div>
			            </div>
			        </div>
			        </form>
            </div>
        </div>
        <iframe id="nm_iframe" name="nm_iframe" style="display:none;" ></iframe> 
        <div class="tishi" id="t" style="display: none"><p>最多只能发布八篇图文哦~</p></div>
        <div class="message" id="m" style="display: none"><p id="message">最多只能发布八篇图文哦~</p></div>
        <div class="footfixed">
            <div class="footWid">
                <p class="footBtn" onclick="preview333()"><span>预览</span></p>
            </div>
        </div>
        
      	
        <!-- Modal  群发设置-->
        <div class="modal fade bs-example-modal-lg" id="identifier" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
            <div class="modal-dialog  modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel2">群发设置</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row paddingRow">
                            <div class="col-xs-4" id="obj">
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td width="30%"><span>群发对象：</span></td>
                                            <td width="70%">
                                                <select class="col-xs-3 form-control" id="GROUP_ID">
                                                    <option value=""><span style="color:#999">--&nbsp;请选择&nbsp;--</span></option>
                                                    <option value="0" <c:if test="${pd.GROUP_ID == '0' }">selected=selected</c:if> >全部</option>
                                                    <option value="1" <c:if test="${pd.GROUP_ID == '1' }">selected=selected</c:if>>会员</option>
                                                    <option value="2" <c:if test="${pd.GROUP_ID == '2' }">selected=selected</c:if>>普通用户</option>
                                                </select>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-xs-4">
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td width="30%"><span>群发性别：</span></td>
                                                <td width="70%">
                                                    <select class="col-xs-3 form-control" id="GROUP_SEX">
                                                        <option value=""><span style="color:#999">--&nbsp;请选择&nbsp; --</span></option>
                                                        <option value="0" <c:if test="${pd.GROUP_SEX == '0' }">selected=selected</c:if>>全部</option>
                                                        <option value="1" <c:if test="${pd.GROUP_SEX == '1' }">selected=selected</c:if> >男生</option>
                                                        <option value="2" <c:if test="${pd.GROUP_SEX == '2' }">selected=selected</c:if>>女生</option>
                                                    </select>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                             </div>
                            <div class="col-xs-4">
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td width="30%"><span>群发地区：</span></td>
                                            <td width="70%">
                                                <select class="col-xs-3 form-control" id="GROUP_PROVINCE">
                                                    <option value=""><span style="color:#999">--&nbsp;请选择&nbsp;--</span></option>
                                                    <option value="all" <c:if test="${pd.GROUP_PROVINCE == 'all' }">selected=selected</c:if> >全部</option>
                                                    <c:forEach items="${proList }" var="par" varStatus="ps">
                                                    	<option value="${par.PROVICNE }" <c:if test="${pd.GROUP_PROVINCE == par.PROVICNE}">selected=selected</c:if> >${par.PROVICNE }</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default modal-Btn marleft" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
	
	
	
	<!-- 页面底部jsp -->
	<%@ include file="../../system/index/foot.jsp"%>
    
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
				$("#eduiedui14-styles")[0].style.display = "block";
				$("#styleName").html("收起样式");
				$("#copyright").hide();
			}else if(styleName == "收起样式"){
				$("#maxWin1").removeClass("maxWin1");
				$("#eduiedui14-styles")[0].style.display = "none";
				$("#styleName").html("展开样式");
				$("#copyright").hide();
			}
		}	
	
		
		//保存群发设置
		function setParam(){
			var sendRecordId = $("#SENDRECORD_ID").val();
			var groupId = $("#GROUP_ID").val();
			var groupSex = $("#GROUP_SEX").val();
			var groupProvince = $("#GROUP_PROVINCE").val();
			
			var url = "<%=basePath%>sendRecord/setParam.do?groupId="+groupId+"&groupSex="+groupSex+"&groupProvince="+groupProvince+"&sendRecordId="+sendRecordId;
			$.get(url,function(data){
				if(data.result == "true"){
					message(data.message);
				}else if(data.result == "false"){
					message(data.message);
				}
			});
		}
		
		//提示框
		function message(msg){
			$("#message").html(msg);
			$(".message").show();
            $(".message").addClass("fadeIn   animated");
            setTimeout(function(){
                $(".message").removeClass("fadeIn   animated");
                        setTimeout(function(){
                            $(".message").addClass("fadeOut   animated");
                                setTimeout(function(){
                                    $(".message").removeClass("fadeOut   animated");
                                    $(".message").hide();
                                },300);
                        },1000);
                },500);
		};
		
		
		
		//预览，打开新窗口
		function preview333(){
			$("#preview").submit();
		}
		
		
		//切换图文,自动保存上一幅图文
		function changeNews(number){ 
			$("#SEQUENCE").val(number);
			var ue = UE.getEditor("CONTENT");
			
			var imgId = "#image" + number; 
			var titleId = "#title" + number;
			var contentId = "#content" + number;
			var authorId = "#author" + number;
			var descriptionId = "#description" + number;
			var source_urlId = "#source_url" + number;
			
			var img = $(imgId)[0].src;
			var title = $(titleId).text();
			var content = $(contentId).html();
			var author = $(authorId).html();
			var description = $(descriptionId).html();
			var source_url = $(source_urlId).html();
			
			if(title == "未命名"){
				title = "";
			}
			
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
			UE.getEditor('CONTENT',{
			    initialFrameHeight:600,
			    
			    appkey		:'59e710b8-db88-48f0-bb92-4f4a79291994',
			   // style_width	:360,
			    style_url 	: 'http://www.135editor.com/editor_styles/open?inajax=1&v=page&appkey=59e710b8-db88-48f0-bb92-4f4a79291994',
			    page_url 	: 'http://www.135editor.com/editor_styles/open_styles?inajax=1&appkey=59e710b8-db88-48f0-bb92-4f4a79291994',
			   // zIndex 		: 900,
			    
			    pageLoad	:true, 
			    open_editor	:true,
			    //focus		:true,
			    //scaleEnabled:true,
			    //autoHeightEnabled:true,
			  	//minFrameWidth:800    //编辑器拖动时最小宽度,默认800
		        //minFrameHeight:220  //编辑器拖动时最小高度,默认220
			    
			});
			
			UE.getEditor("CONTENT").addListener( 'contentChange', function( editor ) {
			    //console.log('选区发生改变');
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
        
        $("#btnsol>span").each(function () {
            $(this).click(function(){
                $(this).addClass("btnActive");
                $(this).siblings().removeClass("btnActive");
                var span1 = $("#btnsol").children(":first").text()
               var spanlength =  $(this).text();
                if(spanlength == span1){
                    $("#file").show();
                    $("#history").hide();
                    $("#bendi").show();
                }else {
                    $("#file").hide();
                    $("#bendi").hide();
                    $("#history").show();
                }
            })
        })
        //上传封面图片
        function uploadImg(f){
            if($("#file").val() != ""){
                var str = "";
                for(var i=0;i<f.length;i++){
                    var reader = new FileReader();
                    reader.readAsDataURL(f[i]);
                    reader.onload = function(e){
                        var srces = e.target.result;
                        str += "<img alt='封面图片' src='"+srces+"' id='image' width='220'/>";
                        $(".shangchuanImg").html(str);
                        subloadImg(srces);
                        
                    }
                }
            }
        }
        
        function subloadImg(ppd) {
            $("#btnload").click(function () {
                if($("#bendi").is(':visible')){
                    $("#loadImg").attr("src",ppd);
                    $("#bendi").children().children().children().children().remove();
                    $(".removeactive").removeClass('selected');
                    
                }else{
                    $("#loadImg").attr("src",ppd);
                    $("#bendi").children().children().children().children().remove();
                    var file = $("#file");
                    file.after(file.clone().val(""));
                    file.remove();
                }
                
                var number = $("#SEQUENCE").val();
              	var imgId = "#image" + number;
              	$(imgId).attr("src",ppd);
              	
                //更换图片的就把图片保存到本地服务器
                $("#Form").submit();
            })
        }

        //模态框遮罩层禁用
        function modalesd(){
        	$('#myModal').modal({backdrop: 'static', keyboard: false});
        }
        function modelteep(){
        	var sendId = $("#SENDRECORD_ID").val();
			$.ajax({
	            type	: "POST",
	            url		:'<%=basePath%>sendRecord/checkRecord.do',
	            data	:{SENDRECORD_ID:sendId},
	            dataType:'json',
	            success: function(data) {
	            	if(data.result == "false"){
                        message(data.message);
	            	}else if(data.result == "true"){
			        	$('#identifier').modal({backdrop: 'static', keyboard: false});
	            	}
	            },
	            error:function(){
	               message("系统繁忙，请稍后再试！");
	               return;
	            }
	        });
        }
        $('.modal-backdrop').each(function() {
        	$(this).attr('id', 'id_' + Math.random());
        });
        
        //图片选中方法
        $('.imgDiv').click(function () {
        	$(this).toggleClass("selected")
            $(this).parent().siblings().children().removeClass('selected');
        	
        	var url = $(this).children("img")[0].src;
        	
        	var div = $(this).children("div").html();
        	$("#PICTURE_ID_HIDDEN").val(div);
        	
            subloadImg(url);
   		});
        
    </script>
    
		
	</body>
</html>

