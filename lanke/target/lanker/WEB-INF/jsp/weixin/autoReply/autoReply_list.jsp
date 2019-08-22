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
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
	<link rel="stylesheet" href="<%=basePath%>newStyle/css/custom.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">	
	<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
	<script src="<%=basePath%>newStyle/js/jquery.form.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <style>
        .demo-class .layui-layer-btn{border-top:1px solid #E9E7E7;background-color: #fafafa}
        .layui-tab-brief>.layui-tab-title .layui-this {color:#1c84c6;}
        .layui-tab-brief>.layui-tab-title .layui-this a {color:#1c84c6;}
        .layui-tab-brief>.layui-tab-more li.layui-this:after, .layui-tab-brief>.layui-tab-title .layui-this:after{border-bottom:2px solid #1c84c6}
        .font-see{color:#41a7f0;font-size:12px;}
        .font-see:hover {text-decoration:none;color:#1c84c6}
        .cardH1{font-size: 18px;font-weight: 600;margin-bottom: 15px;color:#fff!important;padding: 14px 15px;background-color:#1E9FFF }
        .layui-tab-title li {margin:0 15px;padding:0;}
    </style>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
					
							<!-- 正文内容--开始 -->
							<div class="layui-tab layui-tab-brief" lay-filter="event" style="padding-top: 10px;">
								<ul class="layui-tab-title">
									<li lay-id="keyword" <c:if test="${pd.EVENT == 'keyword'}">class="layui-this"</c:if> >关键词回复</li>
									<li lay-id="message" <c:if test="${pd.EVENT == 'message'}">class="layui-this"</c:if> >收到消息回复</li>
									<li lay-id="subscribe" <c:if test="${pd.EVENT == 'subscribe'}">class="layui-this"</c:if> >被关注回复</li>
								</ul>
								<form action="<%=basePath %>wxAutoReply/list.do" id="Form" name="Form" method="post">
									<input id="EVENT" name="EVENT" type="hidden" value="${pd.EVENT }">
								</form>
								
								<div class="layui-tab-content" style="height: 100px;">
									<!-- 关键词回复 -->
									<div class="layui-tab-item <c:if test="${pd.EVENT == 'keyword'}">layui-show</c:if>" >
										<form action="wxAutoReply/list.do" method="post" name="keywordForm" id="keywordForm">
										<!-- 检索条件 -->
											<table class="tablemargin">
												<tr>
													<td>
														<div class="nav-search">
															<span class="input-icon"> 
																<input type="text" class="nav-search-input" id="KEYWORDS" value="${pd.KEYWORDS }" autocomplete="off" 
																	name="KEYWORDS" placeholder="输入关键词/规则名称" />
																<i class="ace-icon fa fa-search nav-search-icon"></i>
															</span>
														</div>
													</td>
													<td style="vertical-align: middle; padding-left: 15px">
														<a class="btn btn-success btn-sm" onclick="toSearch();" title="检索">
															<i class="layui-icon" style="padding-right: 4px;font-size: 14px;">&#xe615;</i>搜索
														</a>
													</td>
												</tr>
											</table>
										<!-- 检索结果  -->
											<table id="simple-table" class="table table-striped table-bordered table-hover"
												style="margin-top: 5px;">
												<thead>
													<tr>
														<th class="center" style="width: 35px;"><label class="pos-rel"><input type="checkbox"
																class="ace" id="zcheckbox" /><span class="lbl"></span></label>
														</th>
														<th class="center">序号</th>
														<th class="left">规则名称</th>
														<th class="left">关键词</th>
														<th class="left">回复类型</th>
														<th class="left">创建时间</th>
														<th class="center">操作</th>
													</tr>
												</thead>

												<tbody>
													<c:choose>
														<c:when test="${not empty keywordList}">
															<c:forEach items="${keywordList}" var="var" varStatus="vs">
																<tr>
																	<td class='center' style="vertical-align: middle">
																		<label class="pos-rel"><input type='checkbox' name='ids' value="${var.AUTOREPLY_ID}" class="ace" /><span
																			class="lbl"></span></label>
																	</td>
																	<td class='center' style="vertical-align: middle">${vs.index+1}</td>
																	<td class='left' style="vertical-align: middle">${var.NAME}</td>
																	<td class='left' style="vertical-align: middle">${var.KEYWORD}</td>
																	<td class='left' style="vertical-align: middle">
																		<c:if test="${var.TYPE =='text'}">文本</c:if>
																		<c:if test="${var.TYPE =='image'}">图片</c:if>
																		<c:if test="${var.TYPE =='card'}">卡券</c:if>
																		<c:if test="${var.TYPE =='material'}">图文信息</c:if>
																		<a href="javascript:void(0)" onclick="edit('${var.AUTOREPLY_ID}');" class="font-see">(查看详情)</a>
																	</td>
																	<td class='left' style="vertical-align: middle">${var.CREATE_TIME}</td>
																	<td class='center' style="vertical-align: middle">
																		<div class="hidden-sm hidden-xs btn-group">
																				<a class="btn btn-sm btn-success" title="编辑" onclick="edit('${var.AUTOREPLY_ID}');">
																					<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>编辑
																				</a>
																				<a class="btn btn-sm btn-danger" onclick="del('${var.AUTOREPLY_ID}');">
																					<i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
																				</a>
																		</div>
																		<div class="hidden-lg hidden-md">
																			<div class="inline pos-rel lk-ul-hoverShow">
																				<button class="btn btn-minier btn-primary dropdown-toggle"
																					data-toggle="dropdown" data-position="auto">
																					<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																				</button>
																				<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																						<li>
																							<a style="cursor: pointer;" onclick="edit('${var.AUTOREPLY_ID}');"
																								class="tooltip-success" data-rel="tooltip" title="编辑">
																								<span class="text-success lk-text">
																									<i class="layui-icon" >&#xe642;</i>
																								</span>
																							</a>
																						</li>
																						<li>
																							<a style="cursor: pointer;" onclick="del('${var.AUTOREPLY_ID}');"
																								class="tooltip-success" data-rel="tooltip" title="删除">
																								<span class="text-danger lk-text">
																									<i class="layui-icon" >&#xe640;</i>
																								</span>
																							</a>
																						</li>
																				</ul>
																			</div>
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr class="main_info">
																<td colspan="100" class="center">未添加自动回复</td>
															</tr>
														</c:otherwise>
													</c:choose>
												</tbody>
											</table>
											<div class="page-header position-relative">
												<table style="width: 100%;">
													<tr>
														<td style="vertical-align: top;">
																<a class="btn btn-xm btn-primary" onclick="add();">
																	<i class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增
																</a>
																<a class="btn btn-sx btn-danger" onclick="delAll();" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i>批量删除</a>
														</td>
														<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
													</tr>
												</table>
											</div>
										</form>
									</div>
									<!-- 收到消息回复 -->
									<div class="layui-tab-item <c:if test="${pd.EVENT == 'message' || pd.EVENT == 'subscribe'}">layui-show</c:if>">
										<input id="AUTOREPLY_ID" name="AUTOREPLY_ID" type="hidden" value="${pd.AUTOREPLY_ID}">
										    <div class="msg_sender">
										        <div class="msg_tab">
										            <div class="tab_navs_panel">
										                <div class="tab_navs_wrp">
										                    <div class="layui-tab layui-tab-brief" lay-filter="messageReply">
										                        <ul class="tab_navs layui-tab-title">
										                        	<input id="TYPE" name="TYPE" type="hidden" value="${pd.TYPE }">
										                            <li lay-id="material" style="padding:0" class="tab_nav <c:if test="${pd.TYPE == 'material' }">layui-this</c:if>">
										                                <a href="javascript:void(0);">
										                                    <i class="layui-icon tab_icon1">&#xe62a;</i>
										                                    <span class="tab_span">图文消息</span>
										                                </a>
										                            </li>
										                            <li lay-id="image" style="padding:0" class="tab_nav <c:if test="${pd.TYPE == 'image' }">layui-this</c:if>">
										                                <a href="javascript:void(0);">
										                                    <i class="layui-icon tab_icon2">&#xe64a;</i>
										                                    <span class="tab_span">图片</span>
										                                </a>
										                            </li>
										                            <li lay-id="text" style="padding:0" class="tab_nav <c:if test="${pd.TYPE == 'text' }">layui-this</c:if>">
										                                <a href="javascript:void(0);">
										                                    <i class="layui-icon tab_icon3" style="font-size: 14px">&#xe648;</i>
										                                    <span class="tab_span">文字</span>
										                                </a>
										                            </li>
										                            <%-- <li lay-id="card" style="padding:0" class="tab_nav <c:if test="${pd.TYPE == 'card' }">layui-this</c:if>">
										                                <a href="javascript:void(0);">
										                                    <i class="layui-icon tab_icon3" >&#xe65e;</i>
										                                    <span class="tab_span">卡券</span>
										                                </a>
										                            </li> --%>
										                        </ul>
										                        <div class="layui-tab-content">
										                            <div class="layui-tab-item <c:if test="${pd.TYPE == 'material' }">layui-show</c:if>">
										                                <div class="inner">
										                                    <div class="tab_cont_cover" id="newImg">
										                                        <div class="media_cover" id="opende">
							                                                        <span class="create_access">
							                                                           <a href="javascript:void(0);">
							                                                              <i class="layui-icon tab_icona">&#xe654;</i>
							                                                              <strong>从素材库中选择</strong>
							                                                           </a>
							                                                        </span>
										                                        </div>
										                                    </div>
										                                    <div id="textImg">
							                                                    <div class="rowBoxCenter" >
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
										                            <div class="layui-tab-item <c:if test="${pd.TYPE == 'image' }">layui-show</c:if>">
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
								                                                 	<form action="<%=basePath%>wxAutoReply/uploadImg.do" method="post" name="imgForm" id="imgForm" enctype="multipart/form-data" >
								                                                 		<input type="hidden" id="iid" name="iid" value="iid">
									                                                   	<input type="file" class="upFlie" id="upFlie" name="upFile" onchange="uploadImg(this.files)">
								                                                   	</form>
								                                                   	<iframe id="nm_iframe" name="nm_iframe" style="display:none;" ></iframe>
								                                                    <span class="create_access">
								                                                       <a href="javascript:void(0);" >
								                                                          <i class="layui-icon tab_icona">&#xe654;</i>
								                                                          <strong>选择本地上传</strong>
								                                                       </a>
								                                                    </span>
								                                                 </div>
								                                                 <div id="divImg" style="height:200px;overflow: auto;">
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
										                            <div class="layui-tab-item <c:if test="${pd.TYPE == 'text' }">layui-show</c:if>">
										                                <div class="layui-form-item layui-form-text" style="margin-top: 30px;">
							                                                <!-- <label class="layui-form-label" style="padding: 0 22px;">文字内容</label> -->
							                                                <div class="layui-input-block" style="margin-left: 0px;">
							                                                    <textarea placeholder="请输入文字内容" class="layui-textarea" style="width: 100%;color: black" id="CONTENT" rows="10" onkeyup="checkNum()" maxlength="300">${text.CONTENT}</textarea>
							                                                    <p style="text-align: right">还可以输入<span id="num">300</span>字，按下Enter键换行</p>
							                                                </div>
							                                                <input id="TEXTMSG_ID" type="hidden" value="${text.TEXTMSG_ID }">
							                                            </div> 
										                            </div>
										                            <%-- <div class="layui-tab-item <c:if test="${pd.TYPE == 'card' }">layui-show</c:if>">
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
							                                                       <h1 class="cardH1" id="title">${card.SUB_TITLE }</h1>
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
										    <div style="margin-top: 50px">
										        <botton class="layui-btn layui-btn-normal" >保存</botton>
										        <botton class="layui-btn  layui-btn-primary">删除回复</botton>
										    </div>
									</div>
									<!-- 被关注回复 -->
									<!-- <div class="layui-tab-item">
									jj
									</div> -->
								</div>
							</div>


							<!-- 正文内容--结束 -->
							
						</div>
					</div>
				</div>
			</div>
		</div>


		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<script type="text/javascript">
		//关闭加载状态
		$(top.hangge());

		//layui渲染
		layui.use(['form', 'element','layer'], function(){
			  var form = layui.form,
			  	layer = layui.layer,
			  	element = layui.element;
			  
			  element.on('tab(event)', function(data){
				  /* console.log(this); //当前Tab标题所在的原始DOM元素
				  console.log(data.index); //得到当前Tab的所在下标
				  console.log(data.elem); //得到当前的Tab大容器
				  
				  location.hash = 'event=' + $(this).attr('lay-id'); */
				  $("#EVENT").val($(this).attr('lay-id'));
				  $("#Form").submit();
			  })
			  element.on('tab(messageReply)', function(data){
				  $("#TYPE").val($(this).attr('lay-id'));
			  })
		});
        
		//检索
		function toSearch(){
			$("#keywordForm").submit();
		}
		
		//新增关键词回复
		function add(){
			layer.open({
                btn: ['保存', '关闭'],
                btn1: function(index, layero){
                    //按钮【按钮一】的回调
                  	var res = window["layui-layer-iframe" + index].save() ;
                  	if(res != false){
		                ajaxSave("keyword",res);
	                }
                },
                skin:"demo-class",
                btnAlign: 'c',
                type: 2,
                title: '新建关键字回复',
                shadeClose: false,
                shade: 0.8,
                fixed: false, //不固定
                maxmin: true,
                area: ['48%', '75%'],
                content:[ '<%=basePath%>wxAutoReply/goAddKeyword.do'],
            })
		}

		//编辑关键词回复
		function edit(id) {
			layer.open({
                btn: ['保存', '关闭'],
                btn1: function(index, layero){
                    //按钮【按钮一】的回调
                    var res = window["layui-layer-iframe" + index].save() ;
	                //layer.close(index);
	                if(res != false){
		                ajaxSave("keyword",res);
	                }
	               
                },
                skin:"demo-class",
                btnAlign: 'c',
                type: 2,
                title: '编辑关键字回复',
                shadeClose: false,
                shade: 0.8,
                fixed: false, //不固定
                maxmin: true,
                area: ['48%', '75%'],
                content:[ '<%=basePath%>wxAutoReply/goEditKeyword.do?AUTOREPLY_ID='+id],
            })
		}
		
		function ajaxSave(event,res){
			
			$.ajax({
	            type	: "POST",
	            url		:'<%=basePath%>wxAutoReply/saveKeyword.do',
	            data	:{EVENT:event, NAME:res.name, KEYWORD:res.keyword, TYPE:res.type, OBJECT_ID:res.object_id, AUTOREPLY_ID:res.AUTOREPLY_ID},
	            dataType:'json',
	            success: function(data) {
               		layer.msg(data.message,{time:1000});
                	if(data.result == "true"){
                		if(event == "keyword"){
		                	setTimeout(function () {$("#keywordForm").submit();},500);	                	                			
                		}else{
                			setTimeout(function () {$("#Form").submit();},500);
                		}
                	}else{
	                	layer.msg(data.message,{time:1000});
	                	return false;
                	}
	            },
	            error:function(){
	               layer.msg("系统繁忙，请稍后再试！");
	               return false;
	            }
	        });
		}

		function ajaxDel(event,content,id){
			layer.confirm(content, {
                btn: ['确定','取消'],
            }, function(){
            	$.ajax({
    	            type	: "POST",
    	            url		:'<%=basePath%>wxAutoReply/deleteKeyword.do',
    	            data	:{AUTOREPLY_ID:id},
    	            dataType:'json',
    	            success: function(data) {
    	                layer.msg(data.message,{time:1000});
    	            	if(data.result == "true"){
	    	                if(event == "keyword"){          	                			
		    	                setTimeout(function () {$("#keywordForm").submit();
		    	                	//nextPage(${page.currentPage});
		    	                	},500);
	                		}else{
	                			setTimeout(function () {$("#Form").submit();},500);
                			}
    	            	}
    	            },
    	            error:function(){
    	               layer.msg("系统繁忙，请稍后再试！");
    	               return;
    	            }
    	        });
     		}, function(){
          		return
        	});
		}
		//删除关键词回复
		function del(id) {
			ajaxDel("keyword","确定要删除这条关键词回复吗？",id);
			
		}

		//批量删除关键词回复
		function delAll() {
			layer.confirm('确定要删除选中的数据吗?', {
                btn: ['确定','取消'],
            }, function(){
            	var str = '';
				for(var i=0;i < document.getElementsByName('ids').length;i++){
				  if(document.getElementsByName('ids')[i].checked){
				  	if(str=='') str += document.getElementsByName('ids')[i].value;
				  	else str += ',' + document.getElementsByName('ids')[i].value;
				  }
				}
				if(str==''){
					layer.msg('您没有选择任何内容！', {time: 800,icon: 1});
				}else{
					top.jzts();
					$.ajax({
			            type	: "POST",
			            url		:'<%=basePath%>wxAutoReply/deleteAllKeyword.do',
			            data	:{DATA_IDS:str},
			            dataType:'json',
			            success: function(data) {
			                layer.msg(data.message,{time:1000});
			                setTimeout(function (){location.reload();},500)
			            },
			            error:function(){
			               layer.msg("系统繁忙，请稍后再试！");
			               return;
			            }
			        });
				}
          }, function(){
                return
          });
			
		}
	</script>

<!-- 收到消息回复js -->
	<script>
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
        //添加图文消息
        function opende() {
            layer.open({
                btn: ['确定', '关闭'],
                btn1: function(index, layero){
                    //按钮【按钮一】的回调
                     var res = window["layui-layer-iframe" + index].callTuWen();
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
                    layer.close(index);
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
    		
    		layer.open({
                btn: ['关闭'],
                btn1: function(index, layero){
                    layer.close(index);
                },
                skin: '',
                btnAlign: 'c',
                type: 2,
                title: '预览图文',
                shadeClose: false,
                shade: 0.8,
                area: ['60%', '90%'],
                content:[ '<%=basePath%>wxMenu/preview.do?SENDRECORD_ID='+SENDRECORD_ID+'&SEQUENCE='+sequence]
            });
    		
    	}
        //选择历史卡券
        function card() {
            layer.open({
                btn: ['确定', '关闭'],
                btn1: function(index, layero){
                    //按钮【按钮一】的回调
                	var res = window["layui-layer-iframe" + index].callCard();
                    $("#title").html(res.title);
                    //$("#title").attr("class",res.titleClass);
                    $("#time").html(res.time);
                    $("#adrss").html(res.adrss);
                    $("#xiangqing").html(res.xiangqing);
                    $("#xzCard").hide();
                    $("#jsCard").show();
                    $("#CARD_ID").val(res.cardId);
                    layer.close(index);
               },
               skin: '',
               btnAlign: 'c',
               type: 2,
               title: '添加您的历史卡券',
               shadeClose: false,
               shade: 0.8,
               area: ['50%', '55%'],
               content:[ '<%=basePath%>wxMenu/cardList.do'] //iframe的url
           });
       }
    //选择历史图片
        function openImg() {
            layer.open({
                btn: ['确定', '关闭'],
                btn1: function(index, layero){
                    //按钮的回调
                	var res = window["layui-layer-iframe" + index].callbackdata();
                    $("#successImg").attr("src",res.url);
                    $("#IMG_MEDIA_ID").val(res.media_id);
                    flieImgsuccess();
                    layer.close(index);
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
    //上传图片
       function uploadImg(f){
    	   for(var i=0;i<f.length;i++){
               var reader = new FileReader();
               reader.readAsDataURL(f[i]);
               console.log(f[i]);
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
	//预览图片
       function yulanTuPian() {
           layer.open({
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
       var flieImgsuccess = function () {
           $("#divImg").show()
           $("#flieImg").hide()
           $("#beforeImg").hide()
       };
       
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
       /* $("body").on("change","#upFlie",function() {
           var files = this.files
           $("#upFile").val(files);
           console.log(this.files);
           
           uploadImg(files);
       }); */
       $("body").on("click","#imgyulan",function() {
           yulanTuPian();
       });
       $("body").on("click",".imgyulan",function() {
       		previewTuWen(this);
       });
       $("body").on("click",".layui-btn-normal",function() {
      		saveMessage();
       });
       $("body").on("click",".layui-btn-primary",function() {
    	   deleteMessage();
       });
       
//     删除事件
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
		function checkNum(){
			var length = $("#CONTENT").val().length;
			$("#num").html(300-length);
		}
		
		function saveMessage(){
			var type = $("#TYPE").val();
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
				type: type,
				object_id: object_id,
				AUTOREPLY_ID: AUTOREPLY_ID
			}
			var event = $("#EVENT").val();
			
			ajaxSave(event,data);
		}
		
		function deleteMessage(){
			var AUTOREPLY_ID = $("#AUTOREPLY_ID").val();
			var event = $("#EVENT").val();
			if(AUTOREPLY_ID == ""){
				layer.msg("请先保存回复内容",{time:1000});
			}else{
				ajaxDel(event,"关注该公众号的用户将不再接收该回复，确定删除？",AUTOREPLY_ID);
			}
			
		}
		
		function toSend(){
			var TOKEN = "6_uEvBwFcehT0o7pn-bdIAkEg9NHo7LWZJVOmZ-7gJ-APqkbigv4oEHUCIe_KpFTnZmBCLEDjACJGGLjWWSaB-4j7ca9Dty_6PT5bo--B8w8R0g3iAjlv5RWjN666duQyGnKDG57udaMxIqtReHFZiAJDNLF";
			var EVENT = "keyword";
			$.ajax({
	            type	: "POST",
	            url		:"<%=basePath%>wxAutoReply/toSend.do",
	            data	:{TOKEN:TOKEN,EVENT:EVENT},
	            dataType:'json',
	            beforeSend	: beforeSend,
	            success: function(data) {
              		layer.closeAll('loading');
              		layer.msg(data.message,{time:1000});
	            },
	            error:function(){
	            	layer.closeAll('loading');
	               layer.msg("系统繁忙，请稍后再试！");
	               return false;
	            }
	        });
		}

	</script>
	
</body>
</html>