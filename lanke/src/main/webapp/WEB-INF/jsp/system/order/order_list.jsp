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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/datepicker.css" />
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>Document</title>
    <style>
        .borPadding {
            padding:20px 30px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
        .layui-icon {font-size:14px;}
    </style>
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
							
						<!-- 检索  -->
						<form action="order/list.do" method="post" name="Form" id="Form">
						<table class="tablemargin">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="vertical-align:middle;padding-left:15px;"><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" value="${pd.lastLoginStart}" style="width:88px;" placeholder="开始日期" title="开始时间"/></td>
								<td style="vertical-align:middle;padding-left:4px;"><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" value="${pd.lastLoginEnd }" style="width:88px;" placeholder="结束日期" title="结束时间"/></td>
								<td style="vertical-align:middle;padding-left:15px;">
									<lable class="lablepd">门店：</lable>
									 	<select class="chosen-select form-control" name="STORE_ID" id="STORE_ID" data-placeholder="请选择" style="vertical-align:middle;width: 120px;">
											<option value=""></option>
											<option value="">全部</option>
											<c:forEach items="${storeList}" var="var" varStatus="vs">
												<option value="${var.STORE_ID }" <c:if test="${pd.STORE_ID == var.STORE_ID}">selected="selected"</c:if> >${var.STORE_NAME }</option>
											</c:forEach>
									  	</select>
								</td>
								<td style="vertical-align:middle;padding-left:15px;">
								<lable class="lablepd">状态：</lable>
								 	<select class="chosen-select form-control" name="STATE" id="STATE" data-placeholder="请选择" style="vertical-align:middle;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="1" <c:if test="${pd.STATE == 1}">selected="selected"</c:if>>未提货</option>
									<option value="2" <c:if test="${pd.STATE == 2}">selected="selected"</c:if>>申请中</option>
									<option value="3" <c:if test="${pd.STATE == 3}">selected="selected"</c:if>>提货成功</option>
								  	</select>
								</td>
								<td style="vertical-align:middle;padding-left:15px">
									<a class="btn btn-success btn-sm" onclick="tosearch();"  title="检索">
										<i class="layui-icon" style="padding-right: 4px">&#xe615;</i>搜索
									</a>
								</td>
								<c:if test="${QX.toExcel == 1 }">
									<td style="vertical-align:middle;padding-left:2px;">
										<a class="btn btn-primary btn-sm" onclick="toExcel();" title="导出到EXCEL" style="margin-left:4px">
											<i class="layui-icon" style="padding-right: 4px">&#xe61f;</i>导出到EXCEL
										</a>
									</td>
								</c:if>
							</tr>
						</table>
						<!-- 检索  -->
						<div style="overflow:auto;">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;min-width:1400px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:80px;">序号</th>
									<th class="left">门店名称</th>
									<th class="left">订单号</th>
									<th class="left">下单日期</th>
									<th class="left">商品名称</th>
									<th class="left">购买数量</th>
									<th class="left">发货方式</th>
									<th class="left">购买方式</th>
									<th class="left">会员昵称</th>
									<th class="left">会员卡号</th>
									<th class="left">订单状态</th>
									<th class="left">发货时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="vertical-align: middle">
												<label class="pos-rel"><input type='checkbox' name='' value="${var.ORDER_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="vertical-align: middle">${vs.index+1}</td>
											<td class='left' style="vertical-align: middle">${var.STORE_NAME }</td>
											<td class='left' style="vertical-align: middle">${var.ORDER_NUMBER }</td>
											<td class='left' style="vertical-align: middle">${var.CREATE_TIME }</td>
											<td class='left' style="vertical-align: middle">${var.ANAME }</td>
											<td class='left' style="vertical-align: middle">${var.COUNT }</td>
											<td class='left' style="vertical-align: middle">
												<c:if test="${var.DELIVERY=='1' }">
													到店提
												</c:if>
											</td>
											<td class='left' style="vertical-align: middle">
												<c:if test="${var.payCash == 'M'}">现金支付</c:if>
												<c:if test="${var.payInt == 'I'}">积分兑换</c:if></td>
											<td class='left' style="vertical-align: middle">${var.NAME }</td>
											<td class='left' style="vertical-align: middle">${var.CARDED }</td>
											<td class='left' style="vertical-align: middle">
												<c:if test="${var.STATE=='1' }">
													未提货
												</c:if>
												<c:if test="${var.STATE=='2' }">
													申请中
												</c:if>
												<c:if test="${var.STATE=='3' }">
													提货成功
												</c:if>
											</td>
											<td class='left' style="vertical-align: middle">${var.SEND_TIME }</td>
											<td class="center" style="vertical-align: middle">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
													<span class="btn btn-default btn-sm">
														<i class="fa fa-lock" title="无权限"></i>
													</span>
												</c:if>
												<div class="btn-group">
													<c:if test="${var.STATE == 2 }">
														<c:if test="${QX.edit == 1 }">
															<a class="btn btn-sm btn-info" title="详情" onclick="edit('${var.ORDER_ID}');">
																<i class="layui-icon" style="padding-right: 4px">&#xe609;</i>发货
															</a>
														</c:if>
													</c:if>
												</div>
											</td>
										</tr>
									
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						</div>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
								<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
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
					 $this.next().css({'width': '100px'});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': '100px'});
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
	
	//发货
	function edit(Id){
		layer.confirm('确定发货吗?', {
            btn: ['确定','取消'],
        }, function(){
        	$.ajax({
				type: "POST",
				url: '<%=basePath%>order/edit.do',
				data: {ORDER_ID:Id},
				dataType:'json',
				cache: false,
				success: function(data){
					layer.msg(data.message);
					if(data.result == "true"){
	                  	setTimeout(function () { 
	                  		location.reload();
	                  	},800)
		 			}
				},
				error:function(){
	               layer.msg("系统繁忙，请稍后再试！");
	               return false;
	            }
			});
        }, function(){
            return
        });
	}
	
	//导出excel
	function toExcel(){
		var keywords = $("#nav-search-input").val();
		var lastLoginStart = $("#lastLoginStart").val();
		var lastLoginEnd = $("#lastLoginEnd").val();
		var STORE_ID = $("#STORE_ID").val();
		var STATE = $("#STATE").val();
		
		if(lastLoginStart != '' && lastLoginEnd != ''){
			window.location.href='<%=basePath%>order/excel.do?keywords='+keywords+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&STORE_ID='+STORE_ID+'&STATE='+STATE;
		}else{
			alert("请选择要导出的时间段");
		}
	}
	</script>
</body>
</html>