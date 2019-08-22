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
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>留言列表</title>
    <style>
        .borPadding {
            padding:20px 30px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
        .layui-icon {font-size:14px;}
        .demo-class .layui-layer-btn{border-top:1px solid #E9E7E7;background-color: #fafafa}
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
						<form action="lm/list.do" method="post" name="Form" id="Form">
						<table class="tablemargin">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="请输入会员昵称" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:10px;"><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" value="${pd.lastLoginStart }" style="width:88px;" placeholder="开始日期" title="开始时间"/></td>
								<td style="padding-left:10px;"><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" value="${pd.lastLoginEnd }" style="width:88px;" placeholder="结束日期" title="结束时间"/></td>
									<td style="vertical-align:top;padding-left:10px;">
									门店：
									 	<select class="chosen-select form-control" name="STORE_ID" id="STORE_ID" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
											<option value=""></option>
											<option value="">全部</option>
											<c:forEach items="${plist}" var="var" varStatus="vs">
												<option value="${var.STORE_ID }"  <c:if test="${pd.STORE_ID == var.STORE_ID }">selected="selected"</c:if> >${var.STORE_NAME }</option>
											</c:forEach>
									  	</select>
									</td>
								<td style="vertical-align:top;padding-left:10px;">
								状态：
								 	<select class="chosen-select form-control" name="LM_STATE" id="LM_STATE" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="1" <c:if test="${pd.LM_STATE=='1'}">selected</c:if> >已回复</option>
									<option value="2" <c:if test="${pd.LM_STATE=='2'}">selected</c:if> >未回复</option>
								  	</select>
								</td>
								<td style="vertical-align:top;padding-left:10px">
									<a class="btn btn-success btn-sm" onclick="tosearch();"  title="检索">
										<i class="layui-icon" style="padding-right: 4px">&#xe615;</i>搜索
									</a>
								</td>
								<c:if test="${QX.toExcel == 1 }">
									<td style="vertical-align:top;padding-left:4px;">
										<a class="btn btn-primary btn-sm" onclick="toExcel();" title="导出到EXCEL">
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
									<th class="left">门店名称</th>
									<th class="left">会员昵称</th>
									<th class="left">留言时间</th>
									<th class="left">回复时间</th>
									<th class="left">回复人</th>
									<th class="left">状态</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty lmList}">
									<c:forEach items="${lmList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="vertical-align: middle">
												<label class="pos-rel"><input type='checkbox' name='LM_ID' value="${var.LM_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="vertical-align: middle">${vs.index+1}</td>
											<td class='left' style="vertical-align: middle">${var.LM_STROE_NAME }</td>
											<td class='left' style="vertical-align: middle">${var.LM_USERNAME}</td>
											<td class='left' style="vertical-align: middle">${var.LM_DATE }</td>
											<td class='left' style="vertical-align: middle">${var.LM_BACKTIME }</td>			
											<td class='left' >${var.pdUser.NAME }</td>
											<td class='left' style="vertical-align: middle">
												<c:if test="${var.LM_STATE == '1' }">
													已回复
												</c:if>
												<c:if test="${var.LM_STATE == '2' }">
													待回复
												</c:if>
											</td>
											<td class="center" style="vertical-align: middle">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
													<span class="btn btn-danger btn-sm">
														<i class="fa fa-lock" title="无权限"></i>
													</span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${var.LM_STATE == '1' }">
														<a class="btn btn-sm btn-info" title="详情" onclick="edit('${var.LM_ID}');">
															<i class="layui-icon" style="padding-right: 4px">&#xe602;</i>查看
														</a>
													</c:if>
													<c:if test="${var.LM_STATE == '2' && QX.edit ==1 }">
														<a class="btn btn-sm btn-success" title="回复" onclick="reback('${var.LM_ID}');">
															<i class="layui-icon" style="padding-right: 4px">&#xe606;</i>回复
														</a>
													</c:if>
												</div>
												<div class="hidden-lg hidden-md">
													<div class="inline pos-rel lk-ul-hoverShow">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${var.LM_STATE == '1' }">
																<li>
																	<a style="cursor: pointer;" title="查看" onclick="edit('${var.LM_ID}');" class="tooltip-success" data-rel="tooltip">
																		<span class="text-info lk-text">
																			 <i class="layui-icon" >&#xe602;</i> 
																		</span>
																	</a>
																</li>
															</c:if>
															<c:if test="${var.LM_STATE == '2' && QX.edit ==1 }">
																<li>
																	<a style="cursor: pointer;" title="回复" onclick="reback('${var.LM_ID}');" class="tooltip-success" data-rel="tooltip">
																		<span class="text-success lk-text">
																			 <i class="layui-icon" >&#xe606;</i> 
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
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						
						
						<!--新增分页功能bug-->
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
						 $this.next().css({'width': '120px'});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': '120px'});
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
		
		//查看详情
		function edit(Id){
			layer.open({
               	btn: ['关闭'],
               	skin: 'demo-class',
               	btnAlign: 'c',
               	type: 2,
               	title: '查看详情',
               	shadeClose: false,
               	shade: 0.8,
               	area: ['650px', '80%'],
               	fixed: false, //不固定
 					maxmin: true,
               	content:[ '<%=basePath%>lm/goLmEdit.do?lm_id='+Id ],
           	});
		}
		
		//回复
       function reback(Id){
			layer.open({
            btn: ['回复','关闭'],
            btn1: function(index, layero){
               	//按钮的回调
                var res = window["layui-layer-iframe" + index].baocun();
				
               },
               skin: 'demo-class',
               btnAlign: 'c',
               type: 2,
               title: '回复',
               shadeClose: false,
               shade: 0.8,
               area: ['650px', '80%'],
               fixed: false, //不固定
 				maxmin: true,
               content:[ '<%=basePath%>lm/goLmback.do?LM_ID='+Id],
            });
		}
		
		//导出excel
		function toExcel(){
			var keywords = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			if(lastLoginStart != '' && lastLoginEnd != ''){
				window.location.href='<%=basePath%>lm/excel.do?keywords='+keywords+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd;
			}else{
				alert("请选择要导出的时间段");
			}
			var ROLE_ID = $("#role_id").val();
		}
	</script>
</body>
</html>