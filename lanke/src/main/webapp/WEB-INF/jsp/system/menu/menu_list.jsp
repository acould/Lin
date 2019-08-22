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
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
	<script type="text/javascript">
		//刷新ztree
		function parentReload(){
			if(null != '${MSG}' && 'change' == '${MSG}'){
				parent.location.href="<%=basePath%>menu/listAllMenu.do?MENU_ID="+${MENU_ID};
			}
		}
		parentReload();
	</script>
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
							<table id="dynamic-table" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center" style="width: 60px;">序号</th>
										<th class='center'>名称</th>
										<th class='center'>资源路径</th>
										<th class='center' style="width: 50px;">状态</th>
										<th class='center' style="width: 120px;">操作</th>
									</tr>
								</thead>
								<tbody>
								<c:choose>
									<c:when test="${not empty menuList}">
									<c:forEach items="${menuList}" var="menu" varStatus="vs">
									<tr>
										<td class='center'>${menu.MENU_ORDER }</td>
										<td class='center'><i class="${menu.MENU_ICON }">&nbsp;</i>
											<a href="javascript:goSonmenu('${menu.MENU_ID }')">${menu.MENU_NAME }</a>
											&nbsp;
											<c:if test="${menu.PARENT_ID == '0' }">
											<c:if test="${menu.MENU_TYPE == '1' }">
											<span class="label label-success arrowed">系统</span>
											</c:if>
											<c:if test="${menu.MENU_TYPE != '1' }">
											<span class="label label-important arrowed-in">业务</span>
											</c:if>
											</c:if>
										</td>
										<td>${menu.MENU_URL == '#'? '': menu.MENU_URL}</td>
										<td class='center'><i class="ace-icon fa ${menu.MENU_STATE == 1? 'fa-eye': 'fa-eye-slash'}"></i></td>
										<td class='center'>
											<c:if test="${QX.edit != 1 && QX.del != 1 }">
											<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
											</c:if>
											<div class="hidden-sm hidden-xs action-buttons">
												<c:if test="${QX.edit == 1 }">
												<a class="blue" href="javascript:editTb('${menu.MENU_ID }');"> 
													<i class="ace-icon glyphicon glyphicon-picture bigger-130" title="编辑图标"></i>
												</a> 
												<a class="green" href="javascript:editmenu('${menu.MENU_ID }','${MENU_ID}');">
													<i class="ace-icon fa fa-pencil-square-o bigger-130" title="修改"></i>
												</a>
												</c:if>
												<c:if test="${QX.del == 1 }">
												<a class="red" href="javascript:delmenu('${menu.MENU_ID }');">
													<i class="ace-icon fa fa-trash-o bigger-130" title="删除"></i>
												</a>
												</c:if>
											</div>
											<div class="hidden-md hidden-lg">
												<div class="inline pos-rel">
													<button
														class="btn btn-minier btn-yellow dropdown-toggle"
														data-toggle="dropdown" data-position="auto">
														<i
															class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
													</button>

													<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
														<c:if test="${QX.edit == 1 }">
														<li><a href="javascript:editTb('${menu.MENU_ID }');" class="tooltip-info" data-rel="tooltip" title="View">
															<span class="blue">
																<i class="ace-icon glyphicon glyphicon-picture bigger-120" title="编辑图标"></i>
															</span>
														</a></li>
														<li><a href="javascript:editmenu('${menu.MENU_ID }');" class="tooltip-success" data-rel="tooltip" title="Edit">
															<span class="green">
																<i class="ace-icon fa fa-pencil-square-o bigger-120" title="修改"></i>
															</span>
														</a></li>
														</c:if>
														<c:if test="${QX.del == 1 }">
														<li><a href="javascript:delmenu('${menu.MENU_ID }');" class="tooltip-error" data-rel="tooltip" title="Delete">
															<span class="red"> <i class="ace-icon fa fa-trash-o bigger-120"  title="删除"></i>
															</span>
														</a></li>
														</c:if>
													</ul>
												</div>
											</div>
										</td>
									</tr>
									</c:forEach>
									</c:when>
										<c:otherwise>
											<tr>
											<td colspan="100" class='center'>没有相关数据</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							
							<div>
								&nbsp;&nbsp;
								<c:if test="${QX.add == 1 }"><a class="btn btn-sm btn-success" onclick="addmenu('${MENU_ID}');">新增</a></c:if>
								<c:if test="${null != pd.MENU_ID && pd.MENU_ID != '0'}">
									<a class="btn btn-sm btn-success" onclick="goSonmenu('${pd.PARENT_ID}');">返回</a>
								</c:if>
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

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(document).ready(function(){
			top.hangge();
		});	
		
		//去此ID下子菜单列表
		function goSonmenu(MENU_ID){
			window.location.href="<%=basePath%>menu.do?MENU_ID="+MENU_ID;
		};
		
		//新增菜单
		function addmenu(MENU_ID){
			layer.open({
				btn: ['保存','关闭'],
				btn1: function(index, layero){
					//按钮的回调
					var res = window["layui-layer-iframe" + index].save();
					if(res.msg){
						parent.location.href="<%=basePath%>menu/listAllMenu.do?MENU_ID="+MENU_ID;
					}
				},
				skin: 'demo-class',
		        btnAlign: 'c',
		        type: 2,
		        title: '新增门店',
		        shadeClose: false,
		        shade: 0.8,
		        area: ['800px', '500px'],
		        content:['<%=basePath%>menu/toAdd.do?MENU_ID='+MENU_ID],
			});
		};
		
		//编辑菜单
		function editmenu(MENU_ID1,MENU_ID2){
			layer.open({
				btn: ['保存','关闭'],
				btn1: function(index, layero){
					//按钮的回调
					var res = window["layui-layer-iframe" + index].save();
					if(res.msg){
						parent.location.href="<%=basePath%>menu/listAllMenu.do?MENU_ID="+MENU_ID2;
					}
				},
				skin: 'demo-class',
		        btnAlign: 'c',
		        type: 2,
		        title: '新增门店',
		        shadeClose: false,
		        shade: 0.8,
		        area: ['800px', '500px'],
		        content:["<%=basePath%>menu/toEdit.do?id="+MENU_ID1],
			});
		};
		
		//删除
		function delmenu(MENU_ID){
			layer.confirm('确定要删除此菜单吗?', {
	            btn: ['确定', '取消'],
	            title: "删除菜单",
	        }, function (){
	            $.ajax({
	                type: "POST",
	                url: "<%=basePath%>menu/delete.do",
	                data: {MENU_ID:MENU_ID},
	                dataType: 'json',
	                cache: false,
	                success: function (data) {
	                	message(data.message);
	                    if (data.result == "true") {
	                        setTimeout(function () {
	                            location.reload();
	                        }, 800)
	                    }
	                },
	                error: function () {
	                    message("系统繁忙，请稍后再试！");
	                    return false;
	                }
	            });
	        }, function () {
	            return
	        });
		}
		
		//编辑菜单图标
		function editTb(MENU_ID){
			layer.open({
	            btn: ['保存', '关闭'],
	            btn1: function (index, layero) {
	                //按钮的回调
	                var res = window["layui-layer-iframe" + index].save();
	            },
	            skin: 'demo-class',
	            btnAlign: 'c',
	            type: 2,
	            title: '编辑图标',
	            shadeClose: false,
	            shade: 0.8,
	            area: ['1020px', '520px'],
	            fixed: false, //不固定
	            maxmin: true,
	            content: ['<%=basePath%>menu/toEditicon.do?MENU_ID='+MENU_ID],
	        });
			
		}
	</script>


</body>
</html>