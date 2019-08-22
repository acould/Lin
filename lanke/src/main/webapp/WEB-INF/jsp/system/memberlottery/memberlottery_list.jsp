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
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <title>兑奖记录</title>
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
						<form action="memberlottery/list.do" method="post" name="Form" id="Form">
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
								<td style="vertical-align:middle;padding-left:15px;"><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="vertical-align:middle;padding-left:4px;"><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd"  value="${pd.lastLoginEnd }" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								<c:if test="${user.ROLE_ID == '94b10e8f8b1c4ae39e13d1316813b1d4' }">
									<td style="vertical-align:middle;padding-left:15px;">
										<lable class="lablepd">兑奖门店：</lable>
										<select class="chosen-select form-control" name="DUIHUAN_STORE" id="DUIHUAN_STORE" data-placeholder="请选择" style="vertical-align:middle;width: 100px;">
											<option value=""></option>
											<c:forEach items="${sList}" var="var" varStatus="vs">
												<option value="${var.STORE_ID }" <c:if test="${pd.DUIHUAN_STORE == var.STORE_ID}">selected="selected"</c:if> >${var.STORE_NAME }</option>
											</c:forEach>
										</select>
								    </td>
							  	</c:if>
								<td style="vertical-align:middle;padding-left:15px;">
									<lable class="lablepd">中奖状态：</lable>
								 	<select class="chosen-select form-control" name="STATE" id="STATE" data-placeholder="请选择" style="vertical-align:middle;width: 100px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="1" <c:if test="${pd.STATE=='1'}">selected</c:if> >未兑奖</option>
									<option value="2" <c:if test="${pd.STATE=='2'}">selected</c:if>>兑奖中</option>
									<option value="3" <c:if test="${pd.STATE=='3'}">selected</c:if>>兑奖成功</option>
									<option value="4" <c:if test="${pd.STATE=='4'}">selected</c:if>>已失效</option>
								  	</select>
								</td>
								<c:if test="${QX.cha == 1 }">
									<td style="vertical-align:middle;padding-left:15px">
										<a class="btn btn-success btn-sm" onclick="tosearch();"  title="检索">
											<i class="layui-icon" style="padding-right: 4px">&#xe615;</i>搜索
										</a>
									</td>
								</c:if>
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
									<th class="left">兑奖门店</th>
									<th class="left">会员昵称</th>
									<th class="left">会员卡号</th>
									<th class="left">奖品</th>
									<th class="left">状态</th>
									<th class="left">中奖时间</th>
									<th class="left">兑奖时间</th>
									<th class="center">操作</th>
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${vs.index+1}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="vertical-align: middle">${vs.index+1}</td>
											<td class='left' style="vertical-align: middle">${var.STORE_NAME}</td>
											<td class='left' style="vertical-align: middle">${var.NAME}</td>
											<td class='left' style="vertical-align: middle">${var.CARDED}</td>
											<td class='left' style="vertical-align: middle">${var.LOTTERY_NAME}</td>
											<td class='left' style="vertical-align: middle">
												<c:choose>
													<c:when test="${var.now_date > var.available_time}">已失效</c:when>
													<c:otherwise>
														<c:if test="${var.STATE =='1'}">未兑奖</c:if>
														<c:if test="${var.STATE =='2'}">兑奖中</c:if>
														<c:if test="${var.STATE =='3'}">兑奖成功</c:if>
													 	
													</c:otherwise>
												</c:choose>
											</td>
											<td class='left' style="vertical-align: middle">${var.LOTTERY_TIME}</td>
											<td class='left' style="vertical-align: middle">${var.CONVERT_TIME}</td>
											<td class="center" style="vertical-align: middle">
												<c:if test="${var.STATE == 2 && var.now_date < var.available_time}">
													<div class="hidden-sm hidden-xs btn-group">
														<a class="btn btn-sm btn-success" onclick="exchange('${var.MEMBERLOTTERY_ID}');">
															<i class="layui-icon" style="padding-right: 4px">&#xe735;</i>兑奖
														</a>
													</div>
													<div class="hidden-md hidden-lg">
														<div class="inline pos-rel lk-ul-hoverShow">
															<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
																<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
															</button>
															<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																<li>
																	<a style="cursor: pointer;" onclick="exchange('${var.MEMBERLOTTERY_ID}');" class="tooltip-success" data-rel="tooltip" title="兑奖">
																		<span class="text-success lk-text">
																			 <i class="layui-icon" >&#xe735;</i>
																		</span>
																	</a>
																</li>
															</ul>
														</div>
													</div>
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
		
		//兑换奖品
		function exchange(Id){
			layer.confirm('确定要兑换给会员吗?', {
                btn: ['确定','取消'],
            }, function(){
            	$.ajax({
					type: "POST",
					url: '<%=basePath%>memberlottery/exchange.do',
    				data: {MEMBERLOTTERY_ID:Id},
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
			var DUIHUAN_STORE = $("#DUIHUAN_STORE").val();
			var STATE = $("#STATE").val();
			if(lastLoginStart != '' && lastLoginEnd != ''){
				window.location.href='<%=basePath%>memberlottery/excel.do?keywords='+keywords+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&DUIHUAN_STORE='+DUIHUAN_STORE+'&STATE='+STATE;
			}else{
				alert("请选择要导出的时间段");
			}
		}
	</script>
</body>
</html>