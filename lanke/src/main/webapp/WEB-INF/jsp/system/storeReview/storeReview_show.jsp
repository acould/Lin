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
							<form action="" method="post" name="form" id="form">
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

										<!-- <td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd">用户状态:</lable> <select
											class="chosen-select form-control" name="STATE" id="STATE"
											data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0">注册用户</option>
												<option value="1">正式用户</option>
												<option value="2">停用用户</option>
												<option value="3">流失用户</option>
										</select>
										</td>

										<td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd">有无门店:</lable> <select
											class="chosen-select form-control" name="ASTATE" id="ASTATE"
											data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="IN">拥有门店</option>
												<option value="NOT IN">没有门店</option>
										</select>
										</td> -->
										<td style="vertical-align: middle; padding-left: 15px"><a
											class="btn btn-success btn-sm" onclick="tosearch();"
											title="检索"> <i class="layui-icon"
												style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索
										</a></td>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center" style="width: 50px;">序号</th>
											<th class="center">企业名称</th>
											<th class="center">门店名称</th>
											<th class="center">用户ID</th>
											<th class="center">门店IP地址</th>
											<th class="center">手机号码</th>
											<th class="center">申请人</th>
											<th class="center">申请时间</th>
											<th class="center">审核时间</th>
											<th class="center">状态</th>
											<th class="center">到期时间</th>
											<th class="center">备注</th>
											<th class="center">操作</th>
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
														<td class='center' style="vertical-align: middle;">${var.Company_Name}</td>
														<td class='center' style="vertical-align: middle;">${var.STORE_NAME}</td>
														<td class='center' style="vertical-align: middle;">${var.pubwin_store_id}</td>
														<td class='center' style="vertical-align: middle;">${var.pubwin_ip}</td>
														<td class='center' style="vertical-align: middle;">${var.STORE_PHONE}</td>
														<td class='center' style="vertical-align: middle;">${var.UPDATE_USERNAME}</td>
														<td class='center' style="vertical-align: middle;">${var.UPDATETIME}</td>
														<td class='center' style="vertical-align: middle;">${var.TIME}</td>
														<td class='center' id="state"
															style="vertical-align: middle;"><c:if
																test="${var.CSTATE=='0'}">未通过</c:if> <c:if
																test="${var.CSTATE=='1'}">已通过</c:if></td>
														<td class='center' style="vertical-align: middle;">
														<c:if
																test="${var.CSTATE=='0'}"></c:if> <c:if
																test="${var.CSTATE=='1'}">${var.EXPIRATION_TIME}</c:if>
														</td>
														<td class='center' style="vertical-align: middle;">${var.NOTE}</td>
														<td class="center" style="vertical-align: middle;">
															<div class="hidden-sm hidden-xs btn-group">
																<c:if test="${var.CSTATE==null}">
																	<a class="btn btn-sm btn-success" title="通过"
																		onclick="pass('${var.STORE_ID}');"> <i
																		class="layui-icon" style="padding-right: 4px">&#xe642;</i>通过
																	</a>
																	<a class="btn btn-sm btn-danger" title="不通过"
																		onclick="fail('${var.STORE_ID}');"> <i
																		class="layui-icon" style="padding-right: 4px">&#xe642;</i>不通过
																	</a>
																</c:if>
															</div>
														</td>
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
	<!-- 通过审核所需信息栏-->
	<div class="pass" style="display: none;">
		<form action="" method="post" name="Form" id="Form">
			<table>
				<tr>
					<th class="center">过期日期：</th>
					<td><input type="date" id="EXPIRATION_TIME"
						name="EXPIRATION_TIME" value="${map.EXPIRATION_TIME}"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<th class="center">备注：</th>
					<td><input type="text" id="Note" name="Note" value="">
						<input type="hidden" id="STORE_ID" name="STORE_ID"
						value="${map.STORE_ID}"> <input type="hidden" id="STATE"
						name="STATE" value="1"></td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 未通过审核原因-->
	<div class="FAIL" style="display: none;">
		<form action="" method="post" name="FORM" id="FORM">
			<table>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<th class="center">失败原因：</th>
					<td><input type="text" id="Note" name="Note" value="">
						<input type="hidden" id="STORE_ID" name="STORE_ID"
						value="${map.STORE_ID}"> <input type="hidden" id="STATE"
						name="STATE" value="0"></td>
				</tr>
			</table>
		</form>
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
	<script type="text/javascript">
	
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
		
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
			
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
		
		//通过门店加V审核(通过)
	       function pass(STORE_ID){
	    	   layer.open({
	    		   btn: ['是','否'],
	    		   yes: function(index,layero){
	    			   layer.open({
	    	    		   btn: ['确定','取消'],
	    	    		   yes: function(index,layero){
	    	    			   $.ajax({
	    	    	                 type: "POST",
	    	    	                url: "<%=basePath%>storeReview/review.do",
								data : $('#Form').serialize(),
								dataType : 'json',
								success : function(data) {
									layer.msg(data.message, {
										time : 1000
									});
									if (data.result == "true") {
										setTimeout(function() {
											//关闭当前页面
											window.opener = null;
											window.open("", "_self");
											window.close();
										}, 700);
										opener.location.reload();//刷新父页面
									}

								},
								error : function() {
									layer.msg("系统繁忙，请稍后再试！");
									return false;
								}
							});
						},
						btn2 : function(index, layero) {
							location.reload();
						},
						type : 1,
						title : '加V审核',
						skin : "demo-class",
						shadeClose : false,
						shade : 0.8,
						area : [ '300px', '200px' ],
						content : '确定通过该门店加V?'
					});
				},
				type : 1,
				title : '请填写以下信息',
				skin : "demo-class",
				shadeClose : false,
				shade : 0.8,
				area : [ '300px', '200px' ],
				content : $('.pass')
			});
		}
		
	     //通过门店加V审核(不通过)
	       function fail(STORE_ID){
	    	   layer.open({
	    		   btn: ['是','否'],
	    		   yes: function(index,layero){
	    			   layer.open({
	    	    		   btn: ['确定','取消'],
	    	    		   yes: function(index,layero){
	    	    			   $.ajax({
	    	    	                 type: "POST",
	    	    	                url: "<%=basePath%>storeReview/review.do",
								data : $('#FORM').serialize(),
								dataType : 'json',
								success : function(data) {
									layer.msg(data.message, {
										time : 1000
									});
									if (data.result == "true") {
										setTimeout(function() {
											//关闭当前页面
											window.opener = null;
											window.open("", "_self");
											window.close();
										}, 700);
										opener.location.reload();//刷新父页面
									}

								},
								error : function() {
									layer.msg("系统繁忙，请稍后再试！");
									return false;
								}
							});
						},
						btn2 : function(index, layero) {
							location.reload();
						},
						type : 1,
						title : '加V审核',
						skin : "demo-class",
						shadeClose : false,
						shade : 0.8,
						area : [ '300px', '200px' ],
						content : '确定不通过该门店加V?'
					});
				},
				type : 1,
				title : '请填写不通过的原因',
				skin : "demo-class",
				shadeClose : false,
				shade : 0.8,
				area : [ '300px', '200px' ],
				content : $('.FAIL')
			});
		}
	</script>
</body>
</html>