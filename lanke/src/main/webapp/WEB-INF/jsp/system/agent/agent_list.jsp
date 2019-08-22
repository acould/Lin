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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">	
<title>代理商管理(后台)</title>
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
							<form action="agent/list.do" method="post" name="Form"id="Form">
								<table class="tablemargin">
									<tr>
										<td>
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
											
											<div id="city">
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
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: middle; padding-left: 2px;"><a
												class="btn btn-primary btn-sm" onclick="toExcel();"
												title="导出到EXCEL" style="margin-left: 4px"> <i
													class="layui-icon" style="padding-right: 4px">&#xe61f;</i>导出到EXCEL
											</a></td>
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
											<th class="center">序号</th>
											<th class="left">公司名称</th>
											<th class="left">省份</th>
											<th class="left">市</th>
											<th class="left">区</th>
											<th class="left">联系人</th>
											<th class="left">手机号码</th>
											<th class="left">代理商编号</th>
											<th class="center">操作</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty list}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${list}" var="var" varStatus="vs">
														<tr>
														    <td class='center' style="vertical-align: middle;">
															<label class="pos-rel">
															<input type='checkbox' name='ids' value="${var.id}" class="ace" />
															<span class="lbl"></span></label>
															</td>
														    <td class='center' style="vertical-align: middle;">${vs.index+1}</td>
															<td class='left' style="vertical-align: middle">${var.company_name}</td>
															<td class='left' style="vertical-align: middle">${var.province}</td>
															<td class='left' style="vertical-align: middle">${var.city}</td>
															<td class='left' style="vertical-align: middle">${var.county}</td>
															<td class='left' style="vertical-align: middle">${var.contacts_name}</td>
															<td class='left' style="vertical-align: middle">${var.phone}</td>
															<td class='left' style="vertical-align: middle">${var.agent_number}</td>
															<td class="center" style="vertical-align: middle">
																<c:if test="${QX.edit != 1 && QX.del != 1 }">
																	<span class="btn btn-default btn-sm"> 
																	<i class="fa fa-lock" title="无权限" />
																	</span>
																</c:if>
																<div class="hidden-sm hidden-xs btn-group">
																<c:if test="${QX.cha == 1}">
																		<a class="btn btn-sm btn-info" title="查看用户" onclick="show('${var.id}')"> <i class="iconfont"
																			style="padding-right: 4px">&#xe61e;</i>查看用户
																		</a>
																</c:if>
																
																<c:if test="${QX.edit == 1 }">
																		<a class="btn btn-sm btn-success" title="编辑"
																			href="<%=basePath%>agent/toEdit?id=${var.id}"
																			target="_blank"> <i class="layui-icon"
																			style="padding-right: 4px">&#xe642;</i>编辑
																		</a>
																</c:if> 
																</div>
																<div class="hidden-md hidden-lg">
																		<div class="inline pos-rel lk-ul-hoverShow">
																			<button class="btn btn-minier btn-primary dropdown-toggle"
																				data-toggle="dropdown" data-position="auto">
																				<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																			</button>
																			<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																				<c:if test="${QX.edit == 1 }">
																					<li>
																						<a style="cursor: pointer;" onclick="show('${var.id}')" onclick="show('${var.id}')" title="查看用户">
																							<span class="text-info lk-text">
																								<i class="iconfont" >&#xe61e;</i>
																							</span>
																						</a>
																					</li>
																				</c:if>
																				<c:if test="${QX.edit == 1 }">
																				<li>
																					<a style="cursor: pointer;" href="<%=basePath%>agent/toEdit?id=${var.id}" target="_blank"
																						class="tooltip-success" data-rel="tooltip" title="编辑">
																						<span class="text-primary lk-text">
																							<i class="layui-icon" >&#xe600;</i>
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
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: middle;">
											<c:if test="${QX.add == 1 }">
													<a class="btn btn-xm btn-primary" href="<%=basePath%>agent/toAdd" target="_blank"> <i
														class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增代理商
													</a>
											</c:if>
											</td>
											<td style="vertical-align: top;">
												<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
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
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<!-- 城市下拉框 -->
	<script type="text/javascript" src="js/jquery.cityselect.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.js"></script>
	<!-- 业务JS -->
	<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-account.js"></script>
	<script type="text/javascript">
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
	
	
    //调用城市三级联动
	citySel('${pd.province}','${pd.county}','${pd.city}')
	
	//导出excel
	function toExcel(){
		var keywords = $("#nav-search-input").val();
		var str = '';
		for(var i=0;i < document.getElementsByName('ids').length;i++){
		  if(document.getElementsByName('ids')[i].checked){
		  	if(str=='') str += document.getElementsByName('ids')[i].value;
		  	 else str += ',' + document.getElementsByName('ids')[i].value;
		  }
		}
		if(str==''){
			layer.msg('您没有选择任何内容！', {time: 800,icon: 1},function () {
     	});
		}
		else{window.location.href='<%=basePath%>agent/excel.do?keywords='+ keywords +'&str='+str;
			}
}
	//查看用户
    function show(id){
			layer.open({
             skin:"demo-class",
             btnAlign: 'c',
             type: 2,
             title: '该用户全部门店如下',
             shadeClose: false,
             shade: 0.8,
             area: ['90%', '560px'],
             fixed: false, //不固定
			maxmin: true,
             content:[ '<%=basePath%>agent/toShow?id='+id ],
         });
		}
	</script>
</body>
</html>