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
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/ace/css/chosen.css"/>
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp" %>
    <!-- 日期框 -->
    <link rel="stylesheet" href="<%=basePath%>static/ace/css/datepicker.css"/>
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <style>
        .demo-class .layui-layer-btn {
            border-top: 1px solid #E9E7E7;
            background-color: #fafafa
        }

        .layui-icon {
            font-size: 14px;
        }

        #store {
            position: relative;
            left: 9px;
            top: 1px;
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

                        <!-- 检索  -->
                        <form action="auction/list.do" method="post" name="Form" id="Form">
                            <table class="tablemargin">
                                <tr>
                                    <td>
                                        <div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="请输入商品名称" class="nav-search-input"
                                                   id="nav-search-input" autocomplete="off" name="keywords"
                                                   value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
                                        </div>
                                    </td>

                                    <td>
                                        <div class="nav-search" id="store">
										<span class="input-icon">
                                            <label>门店：</label>
											<select name="STORE_ID" id="STORE_ID" style="width:150px">
                                                <option >请选择门店</option>
                                                <c:forEach items="${storeList}" var="var" varStatus="vs">
                                                    <option value="${var.STORE_ID }" >${var.STORE_NAME }</option>
                                                </c:forEach>
                                            </select>
										</span>
                                        </div>
                                    </td>

                                    <td style="vertical-align:middle;padding-left:15px">
                                        <a class="btn btn-success btn-sm" onclick="tosearch();" title="检索">
                                            <i class="layui-icon" style="padding-right: 4px">&#xe615;</i>搜索
                                        </a>
                                    </td>
                                </tr>
                                <p style="color: red;float: right;position: relative;top: 3px;">注：商品需要先下架才能删除</p>
                            </table>
                            <!-- 检索  -->

                            <table id="simple-table" class="table table-striped table-bordered table-hover"
                                   style="margin-top:5px;">
                                <thead>
                                <tr>
                                    <th class="center" style="width:35px;">
                                        <label class="pos-rel">
                                            <input type="checkbox" class="ace" id="zcheckbox"/>
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                    <th class="center" style="width:80px;">序号</th>
                                    <th class="center">商品图片</th>
                                    <th class="left">商品名称</th>
                                    <th class="left" style="width:12%;">商品排序（从小到大）</th>
                                    <th class="left">门店</th>
                                    <th class="left">发货方式</th>
                                    <th class="left">购买方式</th>
                                    <th class="left">商品价格</th>
                                    <th class="left">积分消耗</th>
                                    <th class="center">操作</th>
                                </tr>
                                </thead>

                                <tbody>
                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty varList}">

                                        <c:forEach items="${varList}" var="var" varStatus="vs">
                                            <tr>
                                                <td class='center'>
                                                    <label class="pos-rel"><input type='checkbox' name='ids'
                                                                                  value="${var.Auction_ID}"
                                                                                  class="ace"/><span class="lbl"></span></label>
                                                </td>
                                                <td class='center' style="vertical-align: middle;">${vs.index+1}</td>
                                                <td class='center' style="vertical-align: middle;">
                                                    <img width="40" height="40"
                                                         src="uploadFiles/uploadImgs/${var.PATH }"/>
                                                </td>
                                                <td class='left' style="vertical-align: middle;">${var.ANAME }</td>
                                                <td class='left' style="vertical-align: middle;">${var.STORING }</td>
                                                <td class='left' style="vertical-align: middle;">${var.STORE_NAME}</td>
                                                <td class='left' style="vertical-align: middle;">
                                                    <c:if test="${var.DELIVERY == '1'}">
                                                        到店提
                                                    </c:if>
                                                </td>
                                                <td class='left' style="vertical-align: middle;">
                                                    <c:if test="${var.payCash == 'M'}">现金支付</c:if>
                                                    <c:if test="${var.payInt == 'I'}">积分兑换</c:if>
                                                </td>
                                                <td class='left' style="vertical-align: middle;"><c:if test="${var.price ==0.0}">/</c:if><c:if test="${var.price !=0.0}">${var.price}</c:if></td>
                                                <td class='left' style="vertical-align: middle;"><c:if test="${var.INTEGRAL ==0}">/</c:if><c:if test="${var.INTEGRAL !=0}">${var.INTEGRAL}</c:if></td>
                                                <td class="center" style="vertical-align: middle;">
                                                    <c:if test="${QX.edit != 1 && QX.del != 1 }">
													<span class="btn btn-default btn-sm">
														<i class="fa fa-lock" title="无权限"></i>
													</span>
                                                    </c:if>
                                                    <div class="hidden-sm hidden-xs btn-group">
                                                        <c:if test="${var.STATE == '1' }">
                                                            <a class="btn btn-sm btn-primary" title="下架"
                                                               onclick="D_STATE('${var.Auction_ID}','2','下架');">
                                                                <i class="layui-icon" style="padding-right: 4px">&#xe625;</i>下架
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${var.STATE == '2' }">
                                                            <a class="btn btn-sm btn-info" title="上架"
                                                               onclick="D_STATE('${var.Auction_ID}','1','上架');">
                                                                <i class="layui-icon" style="padding-right: 4px">&#xe657;</i>上架
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${QX.edit == 1 }">
                                                            <a class="btn btn-sm btn-success" title="编辑"
                                                               onclick="edit('${var.Auction_ID}');">
                                                                <i class="layui-icon" style="padding-right: 4px">&#xe642;</i>编辑
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${QX.del == 1 && var.STATE == '2'}">
                                                            <a class="btn btn-sm btn-danger"
                                                               onclick="del('${var.Auction_ID}');">
                                                                <i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
                                                            </a>
                                                        </c:if>
                                                    </div>
                                                    <div class="hidden-lg hidden-md">
                                                        <div class="inline pos-rel lk-ul-hoverShow">
                                                            <button class="btn btn-minier btn-primary dropdown-toggle"
                                                                    data-toggle="dropdown" data-position="auto">
                                                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                                            </button>
                                                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                                                <c:if test="${var.STATE == '1' }">
                                                                    <li>
                                                                        <a style="cursor: pointer;" title="下架"
                                                                           onclick="D_STATE('${var.Auction_ID}','2','下架');"
                                                                           class="tooltip-success" data-rel="tooltip">
																	<span class="text-primary lk-text">
																		 <i class="layui-icon">&#xe625;</i>
																	</span>
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${var.STATE == '2' }">
                                                                    <li>
                                                                        <a style="cursor: pointer;" title="上架"
                                                                           onclick="D_STATE('${var.Auction_ID}','1','上架');"
                                                                           class="tooltip-success" data-rel="tooltip">
																	<span class="text-info lk-text">
																		 <i class="layui-icon">&#xe657;</i>
																	</span>
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${QX.edit == 1 }">
                                                                    <li>
                                                                        <a style="cursor: pointer;" title="编辑"
                                                                           onclick="edit('${var.Auction_ID}');"
                                                                           class="tooltip-success" data-rel="tooltip">
																	<span class="text-success lk-text">
																		 <i class="layui-icon">&#xe642;</i>
																	</span>
                                                                        </a>
                                                                    </li>
                                                                </c:if>

                                                                <c:if test="${QX.del == 1 }">
                                                                    <li>
                                                                        <a style="cursor: pointer;" title="删除"
                                                                           onclick="del('${var.Auction_ID}');"
                                                                           class="tooltip-success" data-rel="tooltip">
																		<span class="text-danger lk-text">
																			 <i class="layui-icon">&#xe640;</i>
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
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="main_info">
                                            <td colspan="100" class="center">没有相关数据</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                                </tbody>
                            </table>
                            <div class="page-header position-relative">
                                <table style="width:100%;">
                                    <tr>
                                        <td style="vertical-align:top;">
                                            <c:if test="${QX.add == 1 }">
                                                <a class="btn btn-xm btn-primary" onclick="add();"><i class="layui-icon"
                                                                                                      style="padding-right: 4px">&#xe654;</i>新增</a>
                                            </c:if>
                                            <c:if test="${QX.del == 1 }">
                                                <a class="btn btn-xm btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"
                                                   title="批量删除">
                                                    <i class="layui-icon" style="padding-right: 4px">&#xe640;</i>批量删除
                                                </a>
                                            </c:if>
                                        </td>
                                        <td style="vertical-align:top;">
                                            <div class="pagination"
                                                 style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
                                        </td>
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
<!-- /.main-container -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp" %>
<!-- 删除时确认窗口 -->
<script src="<%=basePath%>static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
<!-- 下拉框 -->
<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
<!-- 日期框 -->
<script src="<%=basePath%>static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script>
<script type="text/javascript">
    $(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        top.jzts();
        $("#Form").submit();
    }

    $(function () {

        //日期框
        $('.date-picker').datepicker({
            autoclose: true,
            todayHighlight: true
        });

        //下拉框
        if (!ace.vars['touch']) {
            $('.chosen-select').chosen({allow_single_deselect: true});
            $(window)
                .off('resize.chosen')
                .on('resize.chosen', function () {
                    $('.chosen-select').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    });
                }).trigger('resize.chosen');
            $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
                if (event_name != 'sidebar_collapsed') return;
                $('.chosen-select').each(function () {
                    var $this = $(this);
                    $this.next().css({'width': $this.parent().width()});
                });
            });
            $('#chosen-multiple-style .btn').on('click', function (e) {
                var target = $(this).find('input[type=radio]');
                var which = parseInt(target.val());
                if (which == 2) $('#form-field-select-4').addClass('tag-input-style');
                else $('#form-field-select-4').removeClass('tag-input-style');
            });
        }


        //复选框全选控制
        var active_class = 'active';
        $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header
            $(this).closest('table').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
                else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
            });
        });
    });

    //新增
    function add() {
        layer.open({
            btn: ['仅保存', '保存并上架', '关闭'],
            btn1: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].baocun(2);
            },
            btn2: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].baocun(1);
                return false;
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '新增商品',
            shadeClose: false,
            shade: 0.8,
            area: ['700px', '90%'],
            fixed: false, //不固定
            maxmin: true,
            content: ['<%=basePath%>auction/goAdd.do '],
        });
    }

    //删除
    function del(Id) {
        layer.confirm('确定要删除吗?', {
            btn: ['确定', '取消'],
        }, function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>auction/delete.do',
                data: {Auction_ID: Id},
                dataType: 'json',
                cache: false,
                success: function (data) {
                    layer.msg(data.message);
                    if (data.result == "true") {
                        setTimeout(function () {
                            location.reload();
                        }, 800)
                    }
                },
                error: function () {
                    layer.msg("系统繁忙，请稍后再试！");
                    return false;
                }
            });
        }, function () {
            return
        });
    }


    //修改
    function edit(ID) {
        layer.open({
            btn: ['仅保存', '保存并上架', '关闭'],
            btn1: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].baocun(2);
            },
            btn2: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].baocun(1);
                return false;
            },
           /* 回调函数
           success: function (layero, index) {
                var form = layui.form,
                    layer = layui.layer;
                form.on('checkbox(checkbox)', function(data){
                    var id = "#"+data.elem.value;
                    if(data.elem.checked){
                        $(id).show();
                    }else {
                        $(id).hide();
                    }
                });
            },*/
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '修改商品信息',
            shadeClose: false,
            shade: 0.8,
            area: ['700px', '90%'],
            fixed: false, //不固定
            maxmin: true,
            content: ['<%=basePath%>auction/goEdit.do?Auction_ID=' + ID],
        });
    }

    //1.上架；2.下架
    function D_STATE(Id, state, msg) {
        layer.confirm('确定' + msg + '吗?', {
            btn: ['确定', '取消'],
        }, function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>auction/xiajia.do',
                data: {Auction_ID: Id, STATE: state},
                dataType: 'json',
                cache: false,
                success: function (data) {
                    layer.msg(data.message);
                    if (data.result == "true") {
                        setTimeout(function () {
                            location.reload();
                        }, 800)
                    }
                },
                error: function () {
                    layer.msg("系统繁忙，请稍后再试！");
                    return false;
                }
            });
        }, function () {
            return
        });
    }


    //批量操作
    function makeAll(msg) {
        layer.confirm('确定要删除选中的数据吗?', {
            btn: ['确定', '取消'],
        }, function () {
            var str = '';
            for (var i = 0; i < document.getElementsByName('ids').length; i++) {
                if (document.getElementsByName('ids')[i].checked) {
                    if (str == '') str += document.getElementsByName('ids')[i].value;
                    else str += ',' + document.getElementsByName('ids')[i].value;
                }
            }
            if (str == '') {
                layer.msg('您没有选择任何内容！', {time: 800, icon: 1});
            } else {
                if (msg == '确定要删除选中的数据吗?') {
                    $.ajax({
                        type: "POST",
                        url: '<%=basePath%>auction/deleteAll.do',
                        data: {DATA_IDS: str},
                        dataType: 'json',
                        cache: false,
                        success: function (data) {
                            layer.msg(data.message);
                            if (data.result == "true") {
                                setTimeout(function () {
                                    location.reload();
                                }, 800)
                            }
                        },
                        error: function () {
                            layer.msg("系统繁忙，请稍后再试！");
                            return false;
                        }
                    });
                }
            }
        }, function () {
            return
        });
    }

    //导出excel
    function toExcel() {
        var keywords = $("#nav-search-input").val();
        var lastLoginStart = $("#lastLoginStart").val();
        var lastLoginEnd = $("#lastLoginEnd").val();
        var ROLE_ID = $("#role_id").val();
        window.location.href = '<%=basePath%>lm/excel.do?keywords=' + keywords + '&lastLoginStart=' + lastLoginStart + '&lastLoginEnd=' + lastLoginEnd + '&ROLE_ID=' + ROLE_ID;
    }


</script>
</body>
</html>