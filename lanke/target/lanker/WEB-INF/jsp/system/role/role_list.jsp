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
<%@ include file="../index/top.jsp"%>

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
							<table style="margin-top: 8px;">
								<tr height="35">
									<c:if test="${QX.add == 1 }">
									<td style="width:90px;"><a href="javascript:addRole(0);" class="btn btn-xs btn-success">新增组</a></td>
									</c:if>
										<c:choose>
										<c:when test="${not empty roleList}">
										<c:forEach items="${roleList}" var="role" varStatus="vs">
											<td style="width:90px;" class="center" <c:choose><c:when test="${pd.ROLE_ID == role.ROLE_ID}">bgcolor="#FFC926" onMouseOut="javascript:this.bgColor='#FFC926';"</c:when><c:otherwise>bgcolor="#E5E5E5" onMouseOut="javascript:this.bgColor='#E5E5E5';"</c:otherwise></c:choose>  onMouseMove="javascript:this.bgColor='#FFC926';" >
												<a href="role.do?ROLE_ID=${role.ROLE_ID }" style="text-decoration:none; display:block;"><font color="#666666">${role.ROLE_NAME }</font></a>
											</td>
											<td style="width:5px;"></td>
										</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
											<td colspan="100">没有相关数据</td>
											</tr>
										</c:otherwise>
										</c:choose>
									<td></td>
								</tr>
							</table>
							
							<table>
								<tr height="7px;"><td colspan="100"></td></tr>
								<tr>
								<td><font color="#808080">本组：</font></td>
								<td>
								<c:if test="${QX.edit == 1 }">
								<a class="btn btn-mini btn-info" onclick="editRole('${pd.ROLE_ID }');">修改组名称<i class="icon-arrow-right  icon-on-right"></i></a>
								</c:if>
									<c:choose>
										<c:when test="${pd.ROLE_ID == '99'}">
										</c:when>
										<c:otherwise>
										<c:if test="${QX.edit == 1 }">
										<a class="btn btn-mini btn-purple" onclick="editRights('${pd.ROLE_ID }');"><i class="icon-pencil"></i>组菜单权限</a>
										</c:if>
										</c:otherwise>
									</c:choose>
									<c:choose> 
										<c:when test="${pd.ROLE_ID == '1' or pd.ROLE_ID == '2'}">
										</c:when>
										<c:otherwise>
										 <c:if test="${QX.del == 1 }">
										 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${pd.ROLE_ID }','z','${pd.ROLE_NAME }');"><i class='ace-icon fa fa-trash-o bigger-130'></i></a>
										 </c:if>
										</c:otherwise>
									</c:choose>
								</td>
								</tr>
							</table>
							
							<table id="dynamic-table" class="table table-striped table-bordered table-hover" style="margin-top:7px;">
								<thead>
								<tr>
									<th class="center" style="width: 60px;">序号</th>
									<th class='center'>角色</th>
									<c:if test="${QX.edit == 1 }">
									<th class="center">增</th>
									<th class="center">删</th>
									<th class="center">改</th>
									<th class="center">查</th>
									</c:if>
									<th class="center" style="width:230px;"  class="center">操作</th>
								</tr>
								</thead>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty roleList_z}">
										<c:if test="${QX.cha == 1 }">
										<c:forEach items="${roleList_z}" var="var" varStatus="vs">
										
										<tr>
											<td class='center' style="width:30px;vertical-align: middle">${vs.index+1}</td>
											<td class="center" id="ROLE_NAMETd${var.ROLE_ID }" style="vertical-align: middle">${var.ROLE_NAME }</td>
										<c:if test="${QX.edit == 1 }">
											<td class="center" style="width:30px;vertical-align: middle"><a onclick="roleButton('${var.ROLE_ID }','add_qx');" class="btn btn-warning btn-mini" title="分配新增权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
											<td class="center" style="width:30px;vertical-align: middle"><a onclick="roleButton('${var.ROLE_ID }','del_qx');" class="btn btn-warning btn-mini" title="分配删除权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
											<td class="center" style="width:30px;vertical-align: middle"><a onclick="roleButton('${var.ROLE_ID }','edit_qx');" class="btn btn-warning btn-mini" title="分配修改权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
											<td class="center" style="width:30px;vertical-align: middle"><a onclick="roleButton('${var.ROLE_ID }','cha_qx');" class="btn btn-warning btn-mini" title="分配查看权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
										</c:if>
											<td class="center" style="vertical-align: middle">
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<div style="width:100%;vertical-align: middle" class="center">
													<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</div>
										</c:if>
										<c:if test="${QX.edit == 1 }">
												<a class="btn btn-mini btn-purple" onclick="editRights('${var.ROLE_ID }');"><i class="icon-pencil"></i>菜单权限</a>
												<a class='btn btn-mini btn-info' title="编辑" onclick="editRole('${var.ROLE_ID }');"><i class='ace-icon fa fa-pencil-square-o bigger-130'></i></a>
										</c:if>
										<c:choose> 
											<c:when test="${var.ROLE_ID == '2' or var.ROLE_ID == '1'}">
											</c:when>
											<c:otherwise>
											 <c:if test="${QX.del == 1 }">
											 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${var.ROLE_ID }','c','${var.ROLE_NAME }');"><i class='ace-icon fa fa-trash-o bigger-130'></i></a>
											 </c:if>
											</c:otherwise>
										</c:choose>
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
										<tr>
										<td colspan="100" class="center" >没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
							<div>
							<c:if test="${QX.add == 1 }">
								&nbsp;&nbsp;<a class="btn btn-xs btn-primary" onclick="addRole('${pd.ROLE_ID }');">新增角色</a>
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
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="<%=basePath%>static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
	<script src="<%=basePath%>newStyle/layer/layer.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
		
		//新增组
		function addRole(pid){
			layer.open({
	            btn: ['保存', '关闭'],
	            btn1: function (index, layero) {
	                //按钮的回调
	                var res = window["layui-layer-iframe" + index].save();
	            },
	            skin: 'demo-class',
	            btnAlign: 'c',
	            type: 2,
	            title: '新增',
	            shadeClose: false,
	            shade: 0.8,
	            area: ['422px', '200px'],
	            fixed: false, //不固定
	            maxmin: true,
	            content: ['<%=basePath%>role/toAdd.do?parent_id='+pid],
	        });
			
		}
		
		//修改
		function editRole(ROLE_ID){
			layer.open({
	            btn: ['保存', '关闭'],
	            btn1: function (index, layero) {
	                //按钮的回调
	                var res = window["layui-layer-iframe" + index].save();
	            },
	            skin: 'demo-class',
	            btnAlign: 'c',
	            type: 2,
	            title: '编辑',
	            shadeClose: false,
	            shade: 0.8,
	            area: ['422px', '200px'],
	            fixed: false, //不固定
	            maxmin: true,
	            content: ['<%=basePath%>role/toEdit.do?ROLE_ID='+ROLE_ID],
	        });
			
		}
		
		//删除
		function delRole(ROLE_ID,msg,ROLE_NAME){
			layer.confirm("确定要删除["+ROLE_NAME+"]吗?", {
	            btn: ['确定', '取消'],
	            title: "删除",
	        }, function (){
	            $.ajax({
	                type: "POST",
	                url: "<%=basePath%>role/delete.do",
	                data: {ROLE_ID:ROLE_ID},
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
		
		//菜单权限
		function editRights(ROLE_ID){
			layer.open({
	            btn: ['保存', '关闭'],
	            btn1: function (index, layero) {
	                //按钮的回调
	                var res = window["layui-layer-iframe" + index].save();
	            },
	            skin: 'demo-class',
	            btnAlign: 'c',
	            type: 2,
	            title: '菜单权限',
	            shadeClose: false,
	            shade: 0.8,
	            area: ['328px', '514px'],
	            fixed: false, //不固定
	            maxmin: true,
	            content: ['<%=basePath%>role/menuqx.do?ROLE_ID='+ROLE_ID],
	        });
			
		}
		
		//按钮权限(增删改查)
		function roleButton(ROLE_ID,msg){
			if(msg == 'add_qx'){
				var Title = "授权新增权限(此角色下打勾的菜单拥有新增权限)";
			}else if(msg == 'del_qx'){
				Title = "授权删除权限(此角色下打勾的菜单拥有删除权限)";
			}else if(msg == 'edit_qx'){
				Title = "授权修改权限(此角色下打勾的菜单拥有修改权限)";
			}else if(msg == 'cha_qx'){
				Title = "授权查看权限(此角色下打勾的菜单拥有查看权限)";
			}
			layer.open({
	            btn: ['保存', '关闭'],
	            btn1: function (index, layero) {
	                //按钮的回调
	                var res = window["layui-layer-iframe" + index].save();
	            },
	            skin: 'demo-class',
	            btnAlign: 'c',
	            type: 2,
	            title: Title,
	            shadeClose: false,
	            shade: 0.8,
	            area: ['328px', '514px'],
	            fixed: false, //不固定
	            maxmin: true,
	            content: ['<%=basePath%>role/b4Button.do?ROLE_ID='+ROLE_ID+'&msg='+msg],
	        });
		}
	</script>


</body>
</html>