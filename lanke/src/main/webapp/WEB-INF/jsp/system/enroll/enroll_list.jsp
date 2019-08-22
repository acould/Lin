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
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
</head>
<style>
.layui-icon {
	font-size: 14px;
}
</style>
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
							<form action="enroll/list.do" method="post" name="Form" id="Form">
								<table class="tablemargin">
									<tr>
										<td>
											<div class="nav-search">
												<span class="input-icon">
													 <input type="text" placeholder="用户名称或联系电话" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords"
													value="${pd.keywords }" placeholder="这里输入关键词" /> 
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											</div>
										</td>
										<td style="vertical-align: middle; padding-left: 10px;">
											<input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart" value="${pd.lastLoginStart }" type="text"
											data-date-format="yyyy-mm-dd" readonly="readonly" style="width: 88px;" placeholder="开始日期" title="开始日期" />
										</td>
										<td style="vertical-align: middle; padding-left: 10px;">
											<input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd" value="${pd.lastLoginEnd }" type="text"
											data-date-format="yyyy-mm-dd" readonly="readonly" style="width: 88px;" placeholder="结束日期" title="结束日期" />
										</td>
										<c:if test="${user.ROLE_ID == '94b10e8f8b1c4ae39e13d1316813b1d4' }">
											<td style="vertical-align: middle; padding-left: 10px;">
												<lable class="lablepd">门店：</lable>
												<select class="chosen-select form-control" name="STORE_ID" id="STORE_ID" data-placeholder="请选择" style="vertical-align: middle; width: 80px;">
													<option value=""></option>
													<c:choose>
														<c:when test="${not empty sList}">
															<c:forEach items="${sList}" var="sList" varStatus="vs">
																<option value="${sList.STORE_ID }"
																<c:if test="${pd.STORE_ID == sList.STORE_ID }">selected="selected"</c:if>>${sList.STORE_NAME }</option>
															</c:forEach>
														</c:when>
													</c:choose>
											</select>
											</td>
										</c:if>
										<td style="vertical-align: middle; padding-left: 10px;">
											<lable class="lablepd">赛事名称：</lable> 
											<select class="chosen-select form-control" name="SYSMATCH_ID" id="SYSMATCH_ID" data-placeholder="请选择" style="vertical-align: middle; width: 80px;">
												<option value=""></option>
												<c:choose>
													<c:when test="${not empty gList}">
														<c:forEach items="${gList}" var="gList" varStatus="vs">
															<option value="${gList.SYSMATCH_ID }"
															<c:if test="${pd.SYSMATCH_ID == gList.SYSMATCH_ID}">selected="selected"</c:if>>${gList.MATCH_NAME }</option>
														</c:forEach>
													</c:when>
												</c:choose>
										</select>
										</td>
										<td style="vertical-align: middle; padding-left: 10px;">
											<lable class="lablepd">状态：</lable>
											<select class="chosen-select form-control" name="STATE" id="STATE" data-placeholder="请选择" style="vertical-align: middle; width: 80px;">
												<option value=""></option>
												<option value="1"
													<c:if test="${pd.STATE == '1'}">selected = "selected"</c:if>>报名成功</option>
												<option value="2"
													<c:if test="${pd.STATE == '2'}">selected = "selected"</c:if>>已失效</option>
											</select>
										</td>
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: middle; padding-left: 10px">
												<a class="btn btn-success btn-sm" onclick="tosearch();" title="检索">
												 	<i class="layui-icon" style="padding-right: 4px; font-size: 14px">&#xe615;</i>搜索
												</a>
											</td>
										</c:if>
										<c:if test="${QX.toExcel == 1 }">
											<td style="vertical-align: middle; padding-left: 2px;">
												<a class="btn btn-primary btn-sm" onclick="toExcel();" title="导出到EXCEL" style="margin-left: 4px">
												 	<i class="layui-icon" style="padding-right: 4px">&#xe61f;</i>导出到EXCEL
												</a>
											</td>
										</c:if>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center" style="width: 35px;">
												<label class="pos-rel">
													<input type="checkbox" class="ace" id="zcheckbox" />
													<span class="lbl"></span>
												</label>
											</th>
											<th class="center" style="width: 80px;">序号</th>
											<th class="left">门店</th>
											<th class="left">赛事名称</th>
											<th class="left">报名类型</th>
											<th class="left">用户名称</th>
											<th class="left">联系电话</th>
											<th class="left">报名时间</th>
											<th class="left">状态</th>
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
																<label class="pos-rel">
																	<input type='checkbox' name='ids' value="${var.ENROLL_ID}" class="ace" />
																	<span class="lbl"></span>
																</label>
															</td>
															<td class='center' style="vertical-align: middle">${vs.index+1}</td>
															<td class='left' title="<c:forEach items="${var.matchStoreList}" var="storeLists" varStatus="vs">${storeLists.STORE_NAME }&nbsp;&nbsp;</c:forEach>">
																<c:choose>
																	<c:when test="${sList.size() == var.matchStoreList.size() }">通用</c:when>
																	<c:otherwise>
																		<c:forEach items="${var.matchStoreList}"
																			var="storeLists" varStatus="vs" begin="0" end="1">
																			${storeLists.STORE_NAME } 
																		</c:forEach>
																		<c:if test="${var.matchStoreList.size() > 2 }">...</c:if>
																	</c:otherwise>
																</c:choose>
															</td>
															<td class='left' style="vertical-align: middle">${var.MATCH_NAME}</td>
															<td class='left' style="vertical-align: middle">
																<c:if test="${var.TYPE =='1'}">单人报名</c:if>
																<c:if test="${var.TYPE =='2'}">组队报名
																	<a href="javascript:zhanDui('${var.ENROLL_ID}')" style="color: #41a7f0;">（详情）</a>
																</c:if>
															</td>
															<td class='left' style="vertical-align: middle">${var.NAME}</td>
															<td class='left' style="vertical-align: middle">${var.PHONE}</td>
															<td class='left' style="vertical-align: middle">${var.START_TIME}</td>
															<td class='left' style="vertical-align: middle">
																<c:if test="${date < var.END_TIME }">报名成功</c:if>
																<c:if test="${date > var.END_TIME }">已失效</c:if>
															</td>
															<td class="center" style="vertical-align: middle">
																<c:if test="${QX.edit != 1 && QX.del != 1 }">
																	<span class="btn btn-default btn-sm">
																	 	<i class="fa fa-lock" title="无权限"></i>
																	</span>
																</c:if>
																<div class="hidden-sm hidden-xs btn-group hidden-md">
																	<c:if test="${date > var.END_TIME }">
																		<c:if test="${QX.del == 1 }">
																			<a class="btn btn-sm btn-danger" onclick="del('${var.ENROLL_ID}');">
																			 	<i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
																			</a>
																		</c:if>
																	</c:if>
																</div>
																<div class="hidden-lg">
																	<div class="inline pos-rel lk-ul-hoverShow">
																		<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
																			<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																		</button>
																		<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																			<c:if test="${date > var.END_TIME }">
																				<c:if test="${QX.del == 1 }">
																					<li>
																						<a style="cursor: pointer;" title="删除" onclick="del('${var.ENROLL_ID}');" class="tooltip-success" data-rel="tooltip">
																							<span class="text-danger lk-text">
																								 <i class="layui-icon" >&#xe640;</i> 
																							</span>
																						</a>
																					</li>
																				</c:if>
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
											<td style="vertical-align: top;">
												<%-- <c:if test="${QX.del == 1 }">
									<a class="btn btn-sm btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if> --%>
											</td>
											<td style="vertical-align: top;"><div class="pagination"
													style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
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
	<script
		src="<%=basePath%>static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
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
		
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					 $.ajax({
				        type: "POST",
				        url:'<%=basePath%>enroll/delete.do?ENROLL_ID='+Id+'&tm='+new Date().getTime(),
				        data:{ENROLL_ID:Id},
				        success: function(data) {
				        	data.message = data.data || data.message;
				        	if(data.message=="false"){
								layer.msg("已失效的报名才能删除");
				        	}else{
								layer.msg("删除成功！",{time:800},function(){
									location.reload();
								});
								
							}
				        },
				        error:function(){
				        	alert("系统繁忙，请稍后再试");
				            removeDis();
				        }
				    });
				}
			});
		}
		
		//去此ID下子菜单列表
		function zhanDui(ENROLL_ID){
			top.jzts();
			window.location.href="<%=basePath%>enrollteam/list.do?ENROLL_ID="+ENROLL_ID;
		};
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>enroll/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									location.reload();
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(){
			var keywords = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			var STORE_ID = $("#STORE_ID").val();
			var SYSMATCH_ID = $("#SYSMATCH_ID").val();
			var STATE = $("#STATE").val();
			if(lastLoginStart != '' && lastLoginEnd != ''){
				window.location.href='<%=basePath%>enroll/excel.do?keywords='
						+ keywords + '&lastLoginStart=' + lastLoginStart
						+ '&STORE_ID=' + STORE_ID + '&SYSMATCH_ID='
						+ SYSMATCH_ID + '&STATE=' + STATE + '&lastLoginEnd='
						+ lastLoginEnd;
			} else {
				alert("请选择要导出的时间段");
			}
		}
	</script>


</body>
</html>