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
							<form action="userManage/lkUsers.do" method="post" name="Form"
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
											<lable class="lablepd">用户状态：</lable> <select
											class="chosen-select form-control" name="STATE" id="STATE"
											data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0" <c:if test="${pd.STATE == '0'}">selected="selected"</c:if>> 注册用户</option>
												<option value="2" <c:if test="${pd.STATE == '2'}">selected="selected"</c:if>> 正式用户</option>
												<option value="3" <c:if test="${pd.STATE == '3'}">selected="selected"</c:if>> 停用用户</option>
												<option value="4" <c:if test="${pd.STATE == '4'}">selected="selected"</c:if>> 流失用户</option>
												<option value="5" <c:if test="${pd.STATE == '5'}">selected="selected"</c:if>> 非法用户</option>
										</select>
										</td>

										<td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd">有无门店：</lable> <select
											class="chosen-select form-control" name="ASTATE" id="ASTATE"
											data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="IN" <c:if test="${pd.ASTATE == 'IN'}">selected="selected"</c:if>> 拥有门店</option>
												<option value="NOT IN" <c:if test="${pd.ASTATE == 'NOT IN'}">selected="selected"</c:if>> 没有门店</option>
										</select>
										</td>
										<c:if test="${QX.cha == 1 }">
										<td style="vertical-align: middle; padding-left: 15px"><a
											class="btn btn-success btn-sm" onclick="tosearch();"
											title="检索"> <i class="layui-icon"
												style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索
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
											<th class="center" style="width: 35px;">
												<label class="pos-rel">
													<input type="checkbox" class="ace" id="zcheckbox" />
													<span class="lbl"></span>
												</label>
											</th>
											<th class="center" style="width: 80px;">序号</th>
											<th class="left">用户名</th>
											<th class="left">手机号码</th>
											<th class="left">邮箱</th>
											<th class="left">授权公众号</th>
											<th class="left">注册时间</th>
											<th class="left">授权时间</th>
											<th class="left">用户状态</th>
											<th class="center">操作</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty userList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${userList}" var="var" varStatus="vs">
													<tr>
														<td class='center' style="vertical-align: middle;">
															<label class="pos-rel">
																<input type='checkbox' name='ids' value="${var.USER_ID}" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>
														<td class='center' style="vertical-align: middle;">${vs.index+1}</td>
														<td class='left' style="vertical-align: middle;">${var.USERNAME}</td>
														<td class='left' style="vertical-align: middle;">${var.PHONE}</td>
														<td class='left' style="vertical-align: middle;">${var.EMAIL}</td>
														<td class='left' style="vertical-align: middle;">${var.INTENET_NAME}</td>
														<td class='left' style="vertical-align: middle;">${var.CREATE_TIME}</td>
														<td class='left' style="vertical-align: middle;">${var.auth_time}</td>
														<td class='left' id="state" style="vertical-align: middle;">
																<c:if test="${var.STATE=='0'}">注册用户</c:if>
																<c:if test="${var.STATE=='2'}">正式用户</c:if>
																<c:if test="${var.STATE=='3'}">停用用户</c:if>
																<c:if test="${var.STATE=='4'}">流失用户</c:if>
																<c:if test="${var.STATE=='5'}">非法用户</c:if>
														</td>
														<td class="center" style="vertical-align: middle;">
															<div class="hidden-sm hidden-xs btn-group">
																<c:if test="${QX.cha == 1}">
																<c:if test="${var.STATE!='0' && var.STATE!='5'}">
																<a class="btn btn-sm btn-success" title="查看门店"
																	onclick="show('${var.INTENET_ID}');"> <i
																	class="layui-icon" style="padding-right: 4px">&#xe642;</i>查看门店
																</a>
																</c:if></c:if>
																<c:if test="${QX.edit == 1 }">
																<c:if test="${var.STATE==2}">
																	<a id="stop" class="btn btn-sm btn-danger" title="停用"
																		onclick="stop('${var.INTENET_ID}');"> <i
																		class="layui-icon" style="padding-right: 4px">&#xe642;</i>停用
																	</a>
																	</c:if>
																</c:if>
																<c:if test="${QX.edit == 1 }">
																<c:if test="${var.STATE==3}">
																	<a id="start" class="btn btn-sm btn-danger" title="启用"
																		onclick="start('${var.INTENET_ID}');"> <i
																		class="layui-icon" style="padding-right: 4px">&#xe642;</i>启用
																	</a>
																	</c:if>
																</c:if>
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
											<!-- <td style="vertical-align: top;"><a
												class="btn btn-xm btn-primary"
												onclick="startAll('确定要启用选中的用户吗?');" title="批量启用"> <i
													class="layui-icon" style="padding-right: 4px">&#xe654;</i>批量启用
											</a> <a class="btn btn-xm btn-danger"
												onclick="stopAll('确定要停用选中的用户吗?');" title="批量停用"> <i
													class="layui-icon" style="padding-right: 4px">&#xe640;</i>批量停用
											</a></td> -->
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
		
		//查询全部门店
	       function show(INTENET_ID){
	    	   top.mainFrame.tabAddHandler(INTENET_ID,'该用户全部门店如下','<%=basePath%>userManage/lkStoreShow.do?INTENET_ID='+INTENET_ID);
			}
	     //停用门店操作(需二次确认)
	       function stop(INTENET_ID){
	    	   layer.open({
	    		   btn: ['是','否'],
	    		   yes: function(index,layero){
	    			   layer.open({
	    	    		   btn: ['确定','取消'],
	    	    		   yes: function(index,layero){
	    	    			   //这里写停用门店方法
	    	    			   $.ajax({
									type: "POST",
									url: '<%=basePath%>userManage/disableUsers.do?INTENET_ID='+INTENET_ID,
									dataType:'json',
									cache: false,
									success: function(data){
										if("success" == data.result){
											layer.msg('停用成功', {time: 800,icon: 1},function () {
												setTimeout(function () { 
		                    						location.reload();
		                    					},500)
		                    				});
							 			}else{
							 				layer.msg('停用失败！', {time: 800,icon: 2},function () {
							 					setTimeout(function () { 
		                    						location.reload();
		                    					},500)
		                    				});
							 			}
									}
								});
	    	    		   },
	    	    		   btn2: function(index,layero){
	    	    			   location.reload();
	    	    		   },
	    	    		   type:1,
	    	    		   title: '停用操作',
	    	    		   skin:"demo-class",
	    	    		   shadeClose: false,
	    	               shade: 0.8,
	    	               area: ['300px', '200px'],
	    	    		   content: '您确定这样做吗?'
	    	               });
	    		   },
	    		   type:1,
	    		   title: '停用操作',
	    		   skin:"demo-class",
	    		   shadeClose: false,
	               shade: 0.8,
	               area: ['200px', '150px'],
	    		   content: '是否停用该用户'
	               });
			}
	     
	     //启用门店操作(需二次确认)
	       function start(INTENET_ID){
	    	   layer.open({
	    		   btn: ['是','否'],
	    		   yes: function(index,layero){
	    			   layer.open({
	    	    		   btn: ['确定','取消'],
	    	    		   yes: function(index,layero){
	    	    			   //这里写启用门店方法
	    	    			   $.ajax({
									type: "POST",
									url: '<%=basePath%>userManage/enableUser.do?INTENET_ID='+INTENET_ID,
									dataType:'json',
									cache: false,
									success: function(data){
										if("success" == data.result){
											layer.msg('启用成功', {time: 800,icon: 1},function () {
												setTimeout(function () { 
		                    						location.reload();
		                    					},500)
		                    				});
							 			}else{
							 				layer.msg('启用失败！', {time: 800,icon: 2},function () {
							 					setTimeout(function () { 
		                    						location.reload();
		                    					},500)
		                    				});
							 			}
									}
								});
	    	    		   },
	    	    		   btn2: function(index,layero){
	    	    			   location.reload();
	    	    		   },
	    	    		   type:1,
	    	    		   title: '启用操作',
	    	    		   skin:"demo-class",
	    	    		   shadeClose: false,
	    	               shade: 0.8,
	    	               area: ['300px', '200px'],
	    	    		   content: '您确定这样做吗?'
	    	               });
	    		   },
	    		   type:1,
	    		   title: '启用操作',
	    		   skin:"demo-class",
	    		   shadeClose: false,
	               shade: 0.8,
	               area: ['200px', '150px'],
	    		   content: '是否启用该用户'
	               });
			}
</script>
</body>
</html>