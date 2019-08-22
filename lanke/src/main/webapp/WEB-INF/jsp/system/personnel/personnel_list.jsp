<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <%@ include file="../index/top.jsp" %>
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">	
    <title>Document</title>
    <style>
        .borPadding {
            padding: 20px 20px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
        .layui-icon {
            font-size: 14px;
        }
        .demo-class .layui-layer-btn {
            border-top: 1px solid #E9E7E7;
            background-color: #fafafa
        }
    </style>
</head>
<body class="no-skin scroll">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <table id="dynamic-table" class="table table-striped table-bordered table-hover"
                               style="margin-top:18px;">
                            <thead>
                            <tr>
                                <th class="center" style="width: 80px;">序号</th>
                                <th class='center'>角色名称</th>
                                <th class='center'>角色描述</th>
                                <th style="width:155px;" class="center">操作</th>
                            </tr>
                            </thead>
                            <c:choose>
                                <c:when test="${not empty roleList_z}">
                                    <c:if test="${QX.cha == 1 }">
                                        <c:forEach items="${roleList_z}" var="var" varStatus="vs">

                                            <tr>
                                                <td class='center' style="vertical-align: middle;">${vs.index+1}</td>
                                                <td class='center' id="ROLE_NAMETd${var.ROLE_ID }" style="vertical-align: middle;">${var.ROLE_NAME }</td>
                                                <td class='center' id="ROLE_NAMETd${var.ROLE_ID }" style="vertical-align: middle;">${var.ROLE_DESCRIBE}</td>
                                                <td style="vertical-align: middle;width:30%;text-align: center;">
                                                    <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                        <div style="width:100%;" class="center">
                                                            <span class="label label-large label-grey arrowed-in-right arrowed-in">
                                                            <i class="ace-icon fa fa-lock" title="无权限"></i></span>
                                                        </div>
                                                    </c:if>
                                                    <div class="hidden-sm hidden-xs btn-group">
	                                                    <c:if test="${QX.edit == 1 }">
	                                                        <a class='btn btn-mini btn-success ' title="编辑"
	                                                           onclick="editRole('${var.ROLE_ID }');">
	                                                            <i class="layui-icon" style="padding-right: 4px">&#xe642;</i>编辑
	                                                        </a>
<%-- 	                                                        <a class="btn btn-mini btn-info"
	                                                           onclick="editRights('${var.ROLE_ID }','add_qx','del_qx','edit_qx','cha_qx');">
	                                                            <i class="layui-icon"
	                                                               style="padding-right: 4px">&#xe602;</i>查看权限
	                                                        </a> --%>
	                                                    </c:if>
	                                                    <c:choose>
	                                                        <c:when test="${var.ROLE_ID == '2' or var.ROLE_ID == '1'}">
	                                                        </c:when>
	                                                        <c:otherwise>
	                                                            <c:if test="${QX.del == 1 }">
	                                                                <a class='btn btn-mini btn-danger' title="删除"
	                                                                   onclick="delRole('${var.ROLE_ID }','c','${var.ROLE_NAME }');">
	                                                                    <i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
	                                                                </a>
	                                                            </c:if>
	                                                        </c:otherwise>
	                                                    </c:choose>
                                                    </div>
                                                    <div class="hidden-lg hidden-md">
														<div class="inline pos-rel lk-ul-hoverShow">
															<button class="btn btn-minier btn-primary dropdown-toggle"
																data-toggle="dropdown" data-position="auto">
																<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
															</button>
															<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																<c:if test="${QX.edit == 1 }">
																	<li>
																		<a style="cursor: pointer;" onclick="editRole('${var.ROLE_ID }');"
																			class="tooltip-success" data-rel="tooltip" title="编辑">
																			<span class="text-success lk-text">
																				<i class="layui-icon" >&#xe642;</i>
																			</span>
																		</a>
																	</li>
																	<li>
																		<a style="cursor: pointer;" onclick="editRights('${var.ROLE_ID }','add_qx','del_qx','edit_qx','cha_qx');"
																			class="tooltip-success" data-rel="tooltip" title="查看权限">
																			<span class="text-info lk-text">
																				<i class="layui-icon" >&#xe602;</i>
																			</span>
																		</a>
																	</li>
																</c:if>
																 <c:choose>
			                                                        <c:when test="${var.ROLE_ID == '2' or var.ROLE_ID == '1'}">
			                                                        </c:when>
			                                                        <c:otherwise>
			                                                            <c:if test="${QX.del == 1 }">
			                                                                <li>
																				<a style="cursor: pointer;" onclick="delRole('${var.ROLE_ID }','c','${var.ROLE_NAME }');"
																					class="tooltip-success" data-rel="tooltip" title="删除">
																					<span class="text-danger lk-text">
																						<i class="layui-icon" >&#xe640;</i>
																					</span>
																				</a>
																			</li>
			                                                            </c:if>
			                                                        </c:otherwise>
			                                                    </c:choose>
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
                                    <tr>
                                        <td colspan="100" class="center">没有相关数据</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </table>
                        <div>
                            <c:if test="${QX.add == 1 }">
                                <a class="btn btn-xm btn-primary" onclick="addRole('${pd.ROLE_ID }');">
                                    <i class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增
                                </a>
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
<%@ include file="../index/foot.jsp" %>
<!-- ace scripts -->
<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    $(top.hangge());
    // 新增角色
    var uid = uuuid();

    function addRole(pid) {
		var res=[];
        layer.open({
            btn: ['保存', '关闭'],
            btn1: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].baocun();
				if (res.msg == true) {
                    layer.close(index);
                    setTimeout(function () {
                        location.reload();
                    }, 500)
                }
            },
            btnAlign: 'c',
            type: 2,
            title: '新增角色',
            shadeClose: false,
            shade: 0.8,
            area: ['700px', '80%'],
            content: ['<%=basePath%>personnel/toAdd.do?parent_id=' + pid + '&ROLE_ID=' + uid],
        });
    }

    //修改角色
    function editRole(ROLE_ID) {
        layer.open({
            btn: ['保存', '关闭'],
            btn1: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].baocun();
                if (res.msg == true) {
                    layer.close(index);
                    setTimeout(function () {
                        location.reload();
                    }, 500)
                }
            },
            btnAlign: 'c',
            type: 2,
            title: '修改角色',
            shadeClose: false,
            shade: 0.8,
            area: ['700px', '80%'],
            content: ['<%=basePath%>personnel/toEdit.do?ROLE_ID=' + ROLE_ID],
        });
    }

    //删除角色
    function delRole(ROLE_ID, msg, ROLE_NAME) {
        layer.confirm('确定要删除该角色吗?', {
            btn: ['确定', '取消'],
        }, function () {
            var url = "<%=basePath%>personnel/delete.do?ROLE_ID=" + ROLE_ID + "&guid=" + new Date().getTime();
            $.get(url, function (data) {
                if ("success" == data.result) {
                    if (msg == 'c') {
                        document.location.reload();
                    } else {
                        window.location.href = "role.do";
                    }
                } else if ("false2" == data.result) {
                    layer.msg('删除失败，此角色已被使用，请删除属于该角色的人员!', {
                        time: 20000,
                        icon: 2,
                        btn: ['知道了'],
                        btn1: function (index, layero) {
                            layer.close(index);
                        }
                    }, function () {
                    });
                }
            });
        }, function () {
            return
        });
    }


    function uuuid() {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4";
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
        s[8] = s[13] = s[18] = s[23] = "-";
        var uuid = s.join("");
        return uuid;
    }

</script>
</body>
</html>