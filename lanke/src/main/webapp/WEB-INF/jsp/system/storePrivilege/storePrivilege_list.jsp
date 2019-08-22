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
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/datepicker.css" />
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<style>
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
							<!-- 检索  -->
							<form action="storePrivilege/list.do" method="post" name="Form"
								id="Form">
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
											<lable class="lablepd">计费系统绑定:</lable> 
											<select class="chosen-select form-control" name="STATE"
											id="STATE" data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0">未绑定</option>
												<option value="1">已绑定</option>
												<option value="2">审核中</option>
												<option value="3">未通过</option>
												<!-- <option value="4">已过期</option> -->
										</select>
										</td>

										<!-- <td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd">银行账号绑定:</lable> <select
											class="chosen-select form-control" name="storeState"
											id="storeState" data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0">拥有门店</option>
												<option value="1">没有门店</option>
										</select>
										</td> -->
										<td style="vertical-align: middle; padding-left: 15px"><a
											class="btn btn-success btn-sm" onclick="tosearch();"
											title="检索"> <i class="layui-icon"
												style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索
										</a></td>
										<td colspan="3"><font color="red">&nbsp;&nbsp;&nbsp;注意:如果您还不是Pubwin
												OL用户，请联系客服升级,客服电话:4000965099</font></td>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center" style="width: 60px;">序号</th>
											<th class="center">门店名称</th>
											<th class="center">计费系统绑定</th>
											<th class="center">银行账号绑定</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														<td class='center'
															style="width: 30px; vertical-align: middle;">${vs.index+1}</td>
														<td class='center' style="vertical-align: middle;">${var.STORE_NAME}</td>

														<td class='center' id="state"
															style="vertical-align: middle;">
															<c:if test="${var.STATE=='0'}">
																<a href="<%=basePath%>storePrivilege/toEdit.do?STORE_ID=${var.STORE_ID}&STORE_NAME=${var.STORE_NAME}&STATE=0" target="_blank"><font
																	color="blue">去绑定</font>
																</a></c:if> 
																<c:if test="${var.STATE=='1'}">已绑定&nbsp;
																<a href="<%=basePath%>storePrivilege/toEdit.do?STORE_ID=${var.STORE_ID}&STORE_NAME=${var.STORE_NAME}&STATE=1" target="_blank"><font
																	color="blue">修改</font>
																</a>
															</c:if> <c:if test="${var.STATE=='2'}">审核中</c:if> <c:if
																test="${var.STATE=='3'}">审核未通过&nbsp;
															<a href="<%=basePath%>storePrivilege/toEdit.do?STORE_ID=${var.STORE_ID}&STORE_NAME=${var.STORE_NAME}&STATE=2" target="_blank"><font
																	color="blue">重新提交</font>
																</a>
															</c:if>
															<%-- <c:if
																test="${var.STATE=='4'}">审核未通过&nbsp;
															<a onclick="resubmit('${var.STORE_ID}');"><font
																	color="blue">再次申请</font></a>
															</c:if> --%>
															</td>
														<td class='center' style="vertical-align: middle;">未开启</td>
													</tr>
												</c:forEach>
											</c:when>
										</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: middle;">
												<div class="pagination"
													style="float: right; padding-top: 0px;">${page.pageStr}</div>
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
	<!-- 删除时确认窗口 -->
	<script src="<%=basePath%>static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="<%=basePath%>static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch() {
			top.jzts();
			$("#Form").submit();
		}
		$(function() {

			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});

			//下拉框
			if (!ace.vars['touch']) {
				$('.chosen-select').chosen({
					allow_single_deselect : true
				});
				$(window).off('resize.chosen').on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({
							'width' : $this.parent().width()
						});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen',
						function(e, event_name, event_val) {
							if (event_name != 'sidebar_collapsed')
								return;
							$('.chosen-select').each(function() {
								var $this = $(this);
								$this.next().css({
									'width' : $this.parent().width()
								});
							});
						});
				$('#chosen-multiple-style .btn').on(
						'click',
						function(e) {
							var target = $(this).find('input[type=radio]');
							var which = parseInt(target.val());
							if (which == 2)
								$('#form-field-select-4').addClass(
										'tag-input-style');
							else
								$('#form-field-select-4').removeClass(
										'tag-input-style');
						});
			}

			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on(
					'click',
					function() {
						var th_checked = this.checked;//checkbox inside "TH" table header
						$(this).closest('table').find('tbody > tr').each(
								function() {
									var row = this;
									if (th_checked)
										$(row).addClass(active_class).find(
												'input[type=checkbox]').eq(0)
												.prop('checked', true);
									else
										$(row).removeClass(active_class).find(
												'input[type=checkbox]').eq(0)
												.prop('checked', false);
								});
					});
		});
	<%-- 	//用户提交审核信息(去绑定)
	       function debound(STORE_ID){
				layer.open({
	            btn: ['提交审核','关闭'],
	            btn1: function(index, layero){
	                	//按钮的回调
	                    var res = window["layui-layer-iframe" + index].baocun();
	                    if(res.msg == true){
	                    	setTimeout(function () { 
	                    		location.reload();
	                    	},500)
	                    }
	                },
	                skin:"demo-class",
	                btnAlign: 'c',
	                type: 2,
	                title: '请填写审核信息',
	                shadeClose: false,
	                shade: 0.8,
	                area: ['578px', '450px'],
	                fixed: false, //不固定
	  				maxmin: true,
	                content:[ '<%=basePath%>storePrivilege/toAdd.do?STORE_ID='+STORE_ID ],
	            });
			} --%>
	<%--    	//用户修改审核信息(修改)
	       function update(STORE_ID){
				layer.open({
	            btn: ['提交','关闭'],
	            btn1: function(index, layero){
	                	//按钮的回调
	                    var res = window["layui-layer-iframe" + index].baocun();
	                    if(res.msg == true){
	                    	setTimeout(function () { 
	                    		location.reload();
	                    	},500)
	                    }
	                },
	                skin:"demo-class",
	                btnAlign: 'c',
	                type: 2,
	                title: '请填写审核信息',
	                shadeClose: false,
	                shade: 0.8,
	                area: ['578px', '450px'],
	                fixed: false, //不固定
	  				maxmin: true,
	                content:[ '<%=basePath%>storePrivilege/toShow.do?STORE_ID='+STORE_ID+'&STATE='+1],
	            });
			}
	   	//用户修改审核信息(重新提交)
	       function resubmit(STORE_ID){
				layer.open({
	            btn: ['重新提交','关闭'],
	            btn1: function(index, layero){
	                	//按钮的回调
	                    var res = window["layui-layer-iframe" + index].baocun();
	                    if(res.msg == true){
	                    	setTimeout(function () { 
	                    		location.reload();
	                    	},500)
	                    }
	                },
	                skin:"demo-class",
	                btnAlign: 'c',
	                type: 2,
	                title: '请填写审核信息',
	                shadeClose: false,
	                shade: 0.8,
	                area: ['578px', '450px'],
	                fixed: false, //不固定
	  				maxmin: true,
	                content:[ '<%=basePath%>storePrivilege/toShow.do?STORE_ID='+STORE_ID+'&STATE='+3],
	            });
			} --%>
	</script>
</body>
</html>