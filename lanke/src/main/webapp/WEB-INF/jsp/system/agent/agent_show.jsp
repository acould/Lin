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
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css"
	media="all">
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
<title>代理商详情</title>
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
							<form action="agent/toShow.do" method="post" name="Form"
								id="Form">
								<input type="hidden" id="id" name="id" value="${pd.id}">
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
												<select class="prov selectpd" id="province" name="province"
													style="vertical-align: middle; width: 120px;">
													<option value=""></option>
												</select>
												<lable class="lablepd leftpd">市:</lable>
												<select class="city selectpd" id="city" name="city"
													disabled="disabled"
													style="vertical-align: middle; width: 120px;">
													<option value=""></option>
												</select>
												<lable class="lablepd leftpd">区:</lable>
												<select class="dist selectpd" id="county" name="county"
													disabled="disabled"
													style="vertical-align: middle; width: 120px;">
													<option value=""></option>
												</select>
											</div>
										</td>

										<td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd">计费系统绑定:</lable> <select
											class="chosen-select form-control lanke-sel" name="STATE"
											id="STATE" data-placeholder="请选择">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0"
													<c:if test="${pd.STATE == '0'}">selected="selected"</c:if>>未绑定</option>
												<option value="1"
													<c:if test="${pd.STATE == '1'}">selected="selected"</c:if>>已绑定</option>
												<option value="2"
													<c:if test="${pd.STATE == '2'}">selected="selected"</c:if>>加V审核中</option>
												<option value="3"
													<c:if test="${pd.STATE == '3'}">selected="selected"</c:if>>加V未通过</option>
										</select>
										</td>

										<td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd">在线支付绑定:</lable> <select
											class="chosen-select form-control lanke-sel" name="STATUS"
											id="STATUS" data-placeholder="请选择">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0"
													<c:if test="${pd.STATUS == '0'}">selected="selected"</c:if>>未开通</option>
												<option value="1"
													<c:if test="${pd.STATUS == '1'}">selected="selected"</c:if>>已开通</option>
												<option value="2"
													<c:if test="${pd.STATUS == '2'}">selected="selected"</c:if>>等待快递</option>
										</select>
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
											<th class="center">序号</th>
											<th class="left">代理的门店</th>
											<th class="left">门店ID</th>
											<th class="left">所在省份</th>
											<th class="left">所在城市</th>
											<th class="left">所在区域</th>
											<th class="left">详情地址</th>
											<th class="left">门店电话</th>
											<th class="left">计费系统绑定</th>
											<th class="left">在线支付开通</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty list}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${list}" var="var" varStatus="vs">
														<tr>
															<td class='center' style="vertical-align: middle;">${vs.index+1}</td>
															<td class='left' style="vertical-align: middle">${var.STORE_NAME}</td>
															<td class='left' style="vertical-align: middle">${var.STORE_ID}</td>
															<td class='left' style="vertical-align: middle">${var.PROVINCE}</td>
															<td class='left' style="vertical-align: middle">${var.CITY}</td>
															<td class='left' style="vertical-align: middle">${var.COUNTY}</td>
															<td class='left' style="vertical-align: middle">${var.ADDRESS}</td>
															<td class='left' style="vertical-align: middle">${var.TELEPHONE}</td>
															<td class='left' style="vertical-align: middle"><c:if
																	test="${var.STATE==0}">未绑定</c:if> <c:if
																	test="${var.STATE==1}">已绑定</c:if> <c:if
																	test="${var.STATE==2}">审核中</c:if> <c:if
																	test="${var.STATE==3}">未通过</c:if></td>
															<td class='left' style="vertical-align: middle"><c:if
																	test="${var.STATUS==0}">未开通</c:if> <c:if
																	test="${var.STATUS==1}">已开通</c:if> <c:if
																	test="${var.STATUS==2}">等待快递</c:if></td>
														</tr>
													</c:forEach>
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
												<div class="pagination" style="float: right; padding-top: 0px;">${page.pageStr}</div>
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
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
	<!-- 城市下拉框 -->
	<script type="text/javascript" src="js/jquery.cityselect.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.js"></script>
	<!-- 业务JS -->
	<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-account.js"></script>
	<script type="text/javascript">
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
		});
	</script>
	<script type="text/javascript">
		//调用城市三级联动
		citySel('${pd.province}', '${pd.county}', '${pd.city}')
	</script>
</body>
</html>