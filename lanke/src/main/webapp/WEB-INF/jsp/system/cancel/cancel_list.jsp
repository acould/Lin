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
<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/datepicker.css" />
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
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
						<form action="cancel/list.do" method="post" name="Form" id="Form">
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
								<td style="vertical-align:middle;padding-left:15px;"><input value="${pd.lastLoginStart}" class="span10 date-picker" name="lastLoginStart" id="lastLoginStart" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始时间"/></td>
								<td style="vertical-align:middle;padding-left:4px;"><input value="${pd.lastLoginEnd}" class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束时间"/></td>
								
								<%-- <c:if test="${pd.ROLE_ID == '94b10e8f8b1c4ae39e13d1316813b1d4' }">
									<td style="vertical-align:middle;padding-left:15px;">
									<lable class="lablepd">门店：</lable>
									 	<select class="chosen-select form-control" name="STORE_ID" id="STORE_ID" data-placeholder="请选择" style="vertical-align:middle;width: 100px;">
										<option value=""></option>
										<option value="">全部</option>
										<c:forEach items="${storeList}" var="var" varStatus="vs">
													<option value="${var.STORE_ID}" <c:if test="${pd.STORE_ID == var.STORE_ID}">selected="selected"</c:if> >${var.STORE_NAME }</option>
												</c:forEach>
									  	</select>
									</td>
								</c:if> --%>
								
								<td style="vertical-align:middle;padding-left:15px;">
								<lable class="lablepd">操作用户：</lable>
								 	<select class="chosen-select form-control" name="USER_ID" id="USER_ID" data-placeholder="请选择" style="vertical-align:middle;width: 100px;">
									<option value=""></option>
									<option value="">全部</option>
									<c:forEach items="${userList}" var="var" varStatus="vs">
										<option value="${var.USER_ID}" <c:if test="${pd.USER_ID == var.USER_ID}">selected="selected"</c:if> >${var.NAME }</option>
									</c:forEach>
								  	</select>
								</td>
								<td style="vertical-align:middle;padding-left:15px;">
								<lable class="lablepd">优惠券名称：</lable>
								 	<select class="chosen-select form-control" name="CARD_ID" id="CARD_ID" data-placeholder="请选择" style="vertical-align:middle;width: 100px;">
									<option value=""></option>
									<option value="">全部</option>
									<c:forEach items="${cardList}" var="var" varStatus="vs">
												<option value="${var.CARD_ID }" <c:if test="${pd.CARD_ID == var.CARD_ID}">selected="selected"</c:if> >${var.SUB_TITLE }</option>
											</c:forEach>
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
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:80px;">序号</th>
									<th class="left">操作用户</th>
									<th class="left">核销时间</th>
									<th class="left">优惠券码</th>
									<th class="left">会员昵称</th>
									<th class="left">会员卡号</th>
									<th class="left">优惠券名称</th>
									<th class="left">卡券类型</th>
									<th class="left">金额</th>
									<th class="left">状态</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="vertical-align: middle">
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.CANCEL_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="vertical-align: middle">${vs.index+1}</td>
											<td class='left' style="vertical-align: middle">
                                                <c:if test="${var.CONSUME_SOURCE == 'FROM_MOBILE_HELPER'}">核销员-${var.STAFF_NECK_NAME}</c:if>
                                                <c:if test="${var.CONSUME_SOURCE != 'FROM_MOBILE_HELPER'}">${var.NAME}</c:if>
                                            </td>
											<td class='left' style="vertical-align: middle">${var.UPDATE_TIME}</td>
											<td class='left' style="vertical-align: middle">${var.CARD}</td>
											<td class='left' style="vertical-align: middle">${var.NECK_NAME}</td>
											<td class='left' style="vertical-align: middle">${var.CARDED}</td>
											<td class="left" style="vertical-align: middle">${var.TITLE}</td>
											<td class='left' style="vertical-align: middle"><%--卡券类型--%>
												<c:if test="${var.CARD_TICKET=='1'}">现金券</c:if>
												<c:if test="${var.CARD_TICKET=='2'}">非现金券</c:if>
											</td>
											<td class='left' style="vertical-align: middle">
													${var.CASH_NUMBER}
											</td>
											<td class='left' style="vertical-align: middle"><%--状态--%>
												<c:if test="${var.STATE=='1'}">未核销</c:if>
												<c:if test="${var.CARD_TICKET=='2'}">
													<c:if test="${var.STATE=='2'}">已核销</c:if>
												</c:if>
												<c:if test="${var.CARD_TICKET=='1'}">
													<c:if test="${var.STATE=='2' && var.MONEY_STATE == '1'}">已核销</c:if>
													<c:if test="${var.STATE=='2' && var.MONEY_STATE == '2'}">
														充值处理中
														<a onclick="refreshCancel('${var.CANCEL_ID}',this)" style="color: red;cursor: pointer">刷新</a>
													</c:if>
													<c:if test="${var.STATE=='2' && var.MONEY_STATE == '3'}">
														充值失败
														<a onclick="rechargeAgain('${var.CANCEL_ID}',this)" style="color: red;cursor: pointer">重新充值</a>
													</c:if>
												</c:if>
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
										<td colspan="100" class="center" >没有相关数据</td>
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
									<a class="btn btn-xm btn-danger" onclick="add();">
										<i class="layui-icon" style="padding-right: 4px">&#xe6b2;</i>核销卡券
									</a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
				/*.on('resize.chosen', function() {
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
				});*/
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
		
		//核销卡券
		function add(ID){
			layer.open({
                	btn: ['核销','关闭'],
                	btn1: function(index, layero){
                		//按钮的回调
                    	var res = window["layui-layer-iframe" + index].baocun();
                	},
                	skin: 'demo-class',
                	btnAlign: 'c',
                	type: 2,
                	title: '核销卡券',
                	shadeClose: false,
                	shade: 0.8,
                	area: ['550px', '340px'],
                	content:[ '<%=basePath%>cancel/goAdd.do' ],
            	});
			}

		//导出excel
		function toExcel(){
			var keywords = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			var USER_ID = $("#USER_ID").val();
			var CARD_ID = $("#CARD_ID").val();
			if(lastLoginStart != '' && lastLoginEnd != ''){
				window.location.href='<%=basePath%>cancel/excel.do?keywords='+keywords+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&USER_ID='+USER_ID+'&CARD_ID='+CARD_ID;
			}else{
				alert("请选择要导出的时间段");
			}
		}

		//刷新充值中的订单
		function refreshCancel(cancel_id,f){
            $.ajax({
                type	: "POST",
                url		:'<%=basePath%>cancel/refreshCancel.do',
                data	:{CANCEL_ID : cancel_id},
                dataType:'json',
                beforeSend: beforeSend("刷新中"),
                success: function(data) {
                    layer.closeAll();
                    if(data.result == "true"){
                        message(data.message);
                        $(f).parent().html("已核销");

                    }else{
                        layer.alert(data.message,function () {
                            tosearch();
                        });

                    }
                },
                error:function(){
                    layer.closeAll();
                    message("系统繁忙，请稍后再试！");
                    return;
                }
            });

		}

		//重新充值
		function rechargeAgain(cancel_id,f) {
            layer.confirm('确定要重新充值吗?', {
                btn: ['确定', '取消'],

            }, function (){
                $.ajax({
                    type	: "POST",
                    url		:'<%=basePath%>cancel/rechargeAgain.do',
                    data	:{CANCEL_ID : cancel_id},
                    dataType:'json',
                    beforeSend: beforeSend("充值中"),
                    success: function(data) {
                        layer.closeAll();
                        if(data.result == "true"){
                            message(data.message);
                            $(f).parent().html("已核销");
                        }else{
                            layer.alert(data.message,function () {
                                tosearch()
                            });
                        }
                    },
                    error:function(){
                        layer.closeAll();
                        message("系统繁忙，请稍后再试！");
                        return;
                    }
                });
            }, function () {
                return
            });


        }

</script>
</body>
</html>