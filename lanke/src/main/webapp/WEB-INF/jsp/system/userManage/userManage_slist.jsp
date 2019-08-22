<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/datepicker.css" />
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<title>Document</title>
<style>
.borPadding {
	padding: 20px 30px;
}

.layui-form-checkbox[lay-skin=primary] span {
	padding-right: 6px;
}

.layui-icon {
	font-size: 14px;
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
							<form action="userManage/lkStoreShow.do" method="post" name="Form"
								id="Form">
								<table class="tablemargin">
									<tr>
										<td>
										<input type="hidden" name="INTENET_ID" value="${pd.INTENET_ID}">
											<div class="nav-search">
												<span class="input-icon"> <input type="text"
													placeholder="这里输入关键词" class="nav-search-input"
													id="nav-search-input" autocomplete="off" name="keywords"
													value="${pd.keywords }" placeholder="这里输入关键词" /> <i
													class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											</div>
										</td>

										<td style="vertical-align: middle; padding-left: 15px;">
											
											<div id="city2">
												<lable class="lablepd leftpd">省:</lable>
												<select class="prov selectpd" id="province" name="province" style="vertical-align: middle; width: 120px;">
													<option value=""></option>
												</select>
												<lable class="lablepd leftpd">市:</lable>
												<select class="city selectpd" id="city" name="city" disabled="disabled" style="vertical-align: middle; width: 120px;">
													<option value=""></option>
												</select>
												<lable class="lablepd leftpd">区:</lable>
												<select class="dist selectpd" id="county" name="county" disabled="disabled" style="vertical-align: middle; width: 120px;">
													<option value=""></option>
												</select>
											</div>
										</td>
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: middle; padding-left: 15px">
												<a class="btn btn-success btn-sm" onclick="tosearch();"
												title="检索"> <i class="layui-icon"
													style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索
											</a>
											</td>
										</c:if>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center" style="width: 35px;"><label
												class="pos-rel"><input type="checkbox" class="ace"
													id="zcheckbox" /><span class="lbl"></span></label></th>
											<th class="center" style="width: 60px;">序号</th>
											<th class="center">门店名称</th>
											<th class="center">门店所在的省份</th>
											<th class="center">门店所在的城市</th>
											<th class="center">门店所在区域</th>
											<th class="center">门店详细地址</th>
											<th class="center">门店电话</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
															<td class='center' style="vertical-align: middle"><label
																class="pos-rel"> <input type='checkbox'
																	name='ids' value="${var.STORE_ID}" class="ace" /> <span class="lbl" />
															</label></td>
															<td class='center'
																style="width: 30px; vertical-align: middle">${vs.index+1}</td>
															<td class='center' style="vertical-align: middle">${var.STORE_NAME}</td>
															<td class='center' style="vertical-align: middle">${var.PROVINCE}</td>
															<td class='center' style="vertical-align: middle">${var.CITY}</td>
															<td class='center' style="vertical-align: middle">${var.COUNTY}</td>
															<td class='center' style="vertical-align: middle">${var.ADDRESS}</td>
															<td class='center' style="vertical-align: middle">${var.TELEPHONE}</td>
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
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
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
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- Modal 授权提示-->
	<div class="modal-body"
		style="padding: 40px; display: none;">
		<div style="display: inline-block;">
			<p style="font-size: 18px; color: #333; margin-bottom: 5px">每次新增门店后一定要去进行门店计费系统绑定</p>
			<p style="font-size: 18px; color: #333;">否则的话，有许多功能是无法使用的哦。</p>
			<p style="margin-top: 54px">
				<span style="font-size: 16px; color: #fff; background-color: #d2d2d2; padding: 8px 34px; border-radius: 50px; cursor: pointer; margin-right: 14px;"
					id="motai">我知道了</span> 
				<span id="ud" style="font-size: 16px; color: #fff; background-color: #e44547; padding: 8px 34px; border-radius: 50px; cursor: pointer;">去绑定计费系统</span>
			</p>
		</div>
	</div>

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
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
	<!-- 城市下拉框 -->
	<script type="text/javascript" src="<%=basePath%>js/jquery.cityselect.js"></script>
	<script type="text/javascript">
    $(function () {
        var PRON = "${pd.province}";
        var COUNTY = "${pd.county}";
        var CITY = "${pd.city}";
        if (PRON != null && COUNTY != null && CITY != null) {
            $("#city2").citySelect({prov: PRON, city: CITY, dist: COUNTY});
        }
    });

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
    
  //新增方法
	var uid = uuid();
	function add(){
		layer.open({
			btn: ['保存','关闭'],
			btn1: function(index, layero){
				//按钮的回调
				var res = window["layui-layer-iframe" + index].baocun();
				if(res.msg == true){
					layer.close(index);
					layer.open({	
	        			type:1
	                	,title:"新增门店提示"
	                	,shade:0.8
	                	,shadeClose:false
	                	,content:$(".modal-body")
	                	,success:function(layero,index){
	                		$("#motai").on("click",function(){
								layer.close(index);
	                		})
	                		$("#ud").on("click",function(){
								layer.close(index);
								window.open('<%=basePath%>accountSettings/toAddv.do');
	                		})
						}
					,area: ['460px','270px']
						,end:function(){
							location.reload();
						}
					})
				}
			},
			skin: 'demo-class',
	        btnAlign: 'c',
	        type: 2,
	        title: '新增门店',
	        shadeClose: false,
	        shade: 0.8,
	        area: ['580px','460px'],
	        content:[ '<%=basePath%>storeShow/goAdd.do?STORE_ID='+uid ],
		});
	}
    //状态禁用/启用
    function statebt(Id, STATE, A) {
        layer.confirm('确定要' + A + '吗?', {
            btn: ['确定', '取消'],
            title: A,
        }, function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>storeShow/statebt.do',
                data: {STORE_ID: Id, STATE: STATE},
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

    //编辑标签
    function editTips(NAME, ID) {
        layer.open({
            btn: ['保存', '关闭'],
            btn1: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].save();
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '编辑' + NAME,
            shadeClose: false,
            shade: 0.8,
            area: ['580px', '460px'],
            content: ['<%=basePath%>storeShow/goTipsEdit.do?STORE_ID=' + ID],
        });
    }

    //修改
    function edit(Id) {
        layer.open({
            btn: ['保存', '关闭'],
            btn1: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].baocun();
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '编辑门店信息',
            shadeClose: false,
            shade: 0.8,
            area: ['52%', '70%'],
            fixed: false, //不固定
            maxmin: true,
            content: ['<%=basePath%>storeShow/goEdit.do?STORE_ID=' + Id],
        });
    }
function warn() {
	layer.msg('该门店已被管理员禁用,请联系客服:4000965099');
}
//页面生成 STORE_ID
function uuid() {
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