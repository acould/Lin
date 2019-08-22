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
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<style>
	.demo-class .layui-layer-btn{border-top:1px solid #E9E7E7;background-color: #fafafa}
	.layui-icon {font-size:14px;}
	.scroll .layui-elem-quote {margin-bottom:5px;font-size: 14px;margin-top:18px;background-color:#f3f3f3;border-color:#e44547;color:#666}
	.hoverCursor {cursor: pointer;}
</style>
</head>
<body class="no-skin scroll">
	
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<blockquote class="layui-elem-quote">
        				<i class="layui-icon" style="color: #e41313;font-size:16px">&#xe60b;</i>
        				拉新功能必要的三个条件为：<font style="color:#ff0b0b"><span class="hoverCursor" onclick="translation();">设置新手券</span>、<span class="hoverCursor" onclick="translation();">设置老带新奖励券</span>、完成分享设置</font>，请确保设置完整，否则无法完成拉新功能。
    				</blockquote>
					<div class="row">
						<div class="col-xs-12">
						<form action="invite/list.do" method="post" name="Form" id="Form">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:8px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center">分享标题</th>
									<th class="center">分享描述</th>
									<th class="center">商户logo</th>
									<th class="center">二维码</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="vertical-align: middle">
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.INTIVE_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="vertical-align: middle">${var.TITLE}</td>
											<td class='center' style="vertical-align: middle">${var.DESCRIPTION}</td>
											<td class='center' style="vertical-align: middle"><img width="50" height="50" src="${var.PATH }"/></td>
											<td class='center' style="vertical-align: middle"><img width="50" height="50" src="<%=basePath %>uploadFiles/uploadImgs/${var.BARCODE }"/></td>
											
											<td class="center" style="vertical-align: middle">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-sm btn-success" title="编辑" onclick="edit('${var.INTIVE_ID}');">
														<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>编辑
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-sm btn-danger" onclick="del('${var.INTIVE_ID}');">
														<i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
													</a>
													</c:if>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel lk-ul-hoverShow">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor: pointer;" title="编辑" onclick="edit('${var.INTIVE_ID}');" class="tooltip-success" data-rel="tooltip">
																	<span class="text-success lk-text">
																		 <i class="layui-icon" >&#xe642;</i> 
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor: pointer;" title="删除" onclick="del('${var.INTIVE_ID}');" class="tooltip-success" data-rel="tooltip">
																	<span class="text-danger lk-text">
																		 <i class="layui-icon" >&#xe640;</i> 
																	</span>
																</a>
															</li>
															</c:if>
														</ul>
													</div>
												</div>
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 && varList.size() == 0}">
									<a class="btn btn-xm btn-primary" onclick="add();">
										<i class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增
									</a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						</form>
					
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	
	<!-- Modal 授权提示-->
	<div class="modal-body" style="padding: 20px 20px 0 20px;display:none;">
 		<div style="display: inline-block;position: relative;top:-50px;margin-left: 10px">
        	<img src="<%=basePath%>static/login/images/eimg.png" alt="">
    	</div>
    	<div style="display: inline-block;margin-left:35px;margin-top: 48px;width:330px">
     		<p style="font-size: 18px;color:#333;margin-bottom:5px">请确定已设置新手券和老带新奖励券，否则分享后无法给新、老会员发券</p>
     		<p style="margin-top: 54px">
     			<span style="font-size: 16px;color:#fff;background-color: #d2d2d2;padding: 8px 34px;border-radius: 50px;cursor:pointer;margin-right: 14px;" id="motai">关闭</span>
     			<span id="ud"  style="font-size: 16px;color:#fff;background-color: #e44547;padding: 8px 34px;border-radius: 50px;cursor:pointer;" onclick="translation();">去设置</span>
     		</p>
    	</div>
	</div>
	
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
	
		$(function() {
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		//新增
		function add(ID){
			layer.open({
                	btn: ['保存','关闭'],
                	btn1: function(index, layero){
                		//按钮的回调
                    	var res = window["layui-layer-iframe" + index].baocun();
                    	if(res.msg == true){
							layer.close(index);
							layer.open({	
	        					type:1
	                			,title:"新增分享设置提示"
	                			,shade:0.8
	                			,shadeClose:false
	                			,content:$(".modal-body")
	                			,success:function(layero,index){
	                				$("#motai").on("click",function(){
										layer.close(index);
	                				})
								}
								,area: ['700px','330px']
								,end:function(){
									location.reload();
								}
							})
						}
                	},
                	skin: 'demo-class',
                	btnAlign: 'c',
                	type: 2,
                	title: '新增分享',
                	shadeClose: false,
                	shade: 0.8,
                	area: ['570px', '470px'],
                	content:[ '<%=basePath%>invite/goAdd.do' ],
            	});
			}
		
		//新增分享后设置新老券引导提示
		function translation(){
			layer.open({
            btn: ['确定','关闭'],
            btn1: function(index, layero){
                	//按钮的回调
                    var url = window["layui-layer-iframe" + index].getScene();
                	window.open(url.url);
                    layer.close(index);
                },
                skin: 'demo-class',
                btnAlign: 'c',
                type: 2,
                title: '创建卡券--选择场景',
                shadeClose: false,
                shade: 0.8,
                area: ['768px', '546px'],
                content:[ '<%=basePath%>card/goAddScene.do '],
                end:function(){
                    location.reload();
                }
            });
		}
		
		//删除
		function del(Id){
			layer.confirm('确定要删除吗?', {
                    btn: ['确定','取消'],
                }, function(){
                	var url = "<%=basePath%>invite/delete.do?INTIVE_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						layer.msg('删除成功', {time: 800,icon: 1},function () {
							setTimeout(function () { 
                    			location.reload();
                    		},400)
                    	});
					});
                }, function(){
                    return
                });
		}
		
		//修改
		function edit(ID){
			layer.open({
                	btn: ['保存','关闭'],
                	btn1: function(index, layero){
                		//按钮的回调
                    	var res = window["layui-layer-iframe" + index].baocun();
                    	if(res.msg == true){
                    		layer.close(index);
                    		setTimeout(function () { 
                    			location.reload();
                    		},500)
                    	}
                	},
                	skin: 'demo-class',
                	btnAlign: 'c',
                	type: 2,
                	title: '修改分享信息',
                	shadeClose: false,
                	shade: 0.8,
                	area: ['570px', '470px'],
                	content:[ '<%=basePath%>invite/goEdit.do?INTIVE_ID='+ID ],
            	});
			}
	</script>
</body>
</html>