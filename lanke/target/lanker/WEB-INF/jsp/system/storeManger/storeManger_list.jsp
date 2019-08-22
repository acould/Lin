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
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<title>Document</title>
<style>
.borPadding {
	padding: 20px 20px;
}
.layui-form-checkbox[lay-skin=primary] span {
	padding-right: 6px;
}
.layui-icon {
	font-size: 14px;
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
							<form action="storeManger/list.do" method="post" name="Form" id="Form">
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
											<lable class="lablepd"> 门店状态: </lable> <select
											class="chosen-select form-control" name="ASTATE" id="ASTATE"
											data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="1" <c:if test="${pd.ASTATE == '1'}">selected="selected"</c:if>> 正常</option>
												<option value="2" <c:if test="${pd.ASTATE == '2'}">selected="selected"</c:if>> 用户停用</option>
												<option value="3" <c:if test="${pd.ASTATE == '3'}">selected="selected"</c:if>> 管理停用</option>
										</select>
										</td>
										<td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd"> 计费系统绑定: </lable> <select
											class="chosen-select form-control" name="BSTATE" id="BSTATE"
											data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0" <c:if test="${pd.BSTATE == '0'}">selected="selected"</c:if>> 未绑定</option>
												<option value="1" <c:if test="${pd.BSTATE == '1'}">selected="selected"</c:if>> 已绑定</option>
												<option value="2" <c:if test="${pd.BSTATE == '2'}">selected="selected"</c:if>> 审核中</option>
												<option value="3" <c:if test="${pd.BSTATE == '3'}">selected="selected"</c:if>> 未通过</option>
										</select>
										</td>
										<td style="vertical-align: middle; padding-left: 15px;">
											 <lable class="lablepd"> 在线支付开通状态: </lable>
											 <select class="chosen-select form-control" name="CSTATE" id="CSTATE" data-placeholder="请选择" style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="6" <c:if test="${pd.CSTATE == '6'}">selected="selected"</c:if>>未开通</option>
												<option value="0" <c:if test="${pd.CSTATE == '0'}">selected="selected"</c:if>>资料审核未通过</option>
												<option value="2" <c:if test="${pd.CSTATE == '2'}">selected="selected"</c:if>>资料审核中</option>
												<option value="1" <c:if test="${pd.CSTATE == '1'}">selected="selected"</c:if>>资料审核已通过</option>
												<option value="3" <c:if test="${pd.CSTATE == '3'}">selected="selected"</c:if>>等待快递</option>
												<option value="4" <c:if test="${pd.CSTATE == '4'}">selected="selected"</c:if>>待开通</option>
												<option value="5" <c:if test="${pd.CSTATE == '5'}">selected="selected"</c:if>>已开通</option>
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
											<th class="left">公众号</th>
											<th class="left">门店名称</th>
											<th class="left">企业全称</th>
											<th class="left">计费系统绑定</th>
											<th class="left">到期时间</th>
											<th class="left">在线支付开通</th>
											<th class="left">门店状态</th>
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
														<td class='center' style="vertical-align: middle;">
															<label class="pos-rel">
																<input type='checkbox' name='ids' value="${var.STORE_ID}" class="ace" />
																<span class="lbl"></span>
															</label>
														</td>
														<td class='center' style="vertical-align: middle;">${vs.index+1}</td>
														<td class='left' style="vertical-align: middle;">${var.INTENET_NAME}</td>
														<td class='left' style="vertical-align: middle;">${var.STORE_NAME}</td>
														<td class='left' style="vertical-align: middle;">${var.Company_Name}</td>
														
														
														<td class='left' style="vertical-align: middle;">
															<c:if test="${var.BSTATE==0}">未绑定</c:if>
															<c:if test="${var.BSTATE==1}">已绑定</c:if> 
															<c:if test="${var.BSTATE==2}">审核中</c:if> 
															<c:if test="${var.BSTATE==3}">未通过</c:if>
														</td>
														<td class='left' style="vertical-align: middle;">${var.EXPIRATION_TIME}</td>
														<td class='left' style="vertical-align: middle;">
															<c:if test="${empty var.CSTATE}">未开通</c:if>
															<c:if test="${var.CSTATE==0}">资料审核未通过</c:if>
															<c:if test="${var.CSTATE==2}">资料审核中</c:if> 
															<c:if test="${var.CSTATE==1 && var.CSTATUS == null}">资料审核已通过</c:if> 
															<c:if test="${var.CSTATE==1 && var.CSTATUS == 2}">等待快递</c:if>
															<c:if test="${var.CSTATE==1 && var.CSTATUS == 0}">待开通</c:if>
															<c:if test="${var.CSTATE==1 && var.CSTATUS == 1}">已开通</c:if>
														</td>
														
														<td class='left' style="vertical-align: middle;">
															<c:if test="${var.ASTATE==1}">正常</c:if>
															<c:if test="${var.ASTATE!=1}">停用</c:if>
														</td>
														<td class="center">
															<div class="hidden-sm hidden-xs btn-group">
																<c:if test="${QX.cha == 1}">
																<a class="btn btn-sm btn-success" title="查看详情"
																	onclick="show('${var.STORE_ID}');"> <i
																	class="layui-icon" style="padding-right: 4px">&#xe642;</i>查看详情
																</a>
																</c:if>
																<c:if test="${QX.edit == 1 }">
																<c:if test="${var.ASTATE==1}">
																	<a id="stop" class="btn btn-sm btn-default" title="停用"
																		onclick="stop('${var.STORE_ID}');"> <i
																		class="layui-icon" style="padding-right: 4px">&#xe642;</i>停用
																	</a>
																</c:if>
																</c:if>
																<c:if test="${QX.edit == 1 }">
																<c:if test="${var.ASTATE==2}">
																	<a id="start" class="btn btn-sm btn-default" title="启用"> <i
																		class="layui-icon" style="padding-right: 4px">&#xe642;</i>
																		<font color="white">启用</font>
																	</a>
																</c:if>
																</c:if>
																<c:if test="${QX.edit == 1 }">
																<c:if test="${var.ASTATE==3}">
																	<a id="start" class="btn btn-sm btn-warning" title="启用"
																		onclick="start('${var.STORE_ID}');"> <i
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
													<td colspan="100" class="center"
														style="vertical-align: middle;">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: middle;"><div
													class="pagination" style="float: right; padding-top: 0px;">${page.pageStr}</div></td>
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
	<%@ include file="../index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<script type="text/javascript">
	$(function(){
		var PRON = $("#PROV").val();
		var COUNTY = $("#COUNTY").val();
		var CITY = $("#CITY").val();
		if(PRON != null && COUNTY != null && CITY != null){
			$("#city").citySelect({prov:PRON, city:CITY, dist:COUNTY});
		}
	}); 
	$(top.hangge());//关闭加载状态
	//检索
	function tosearch(){
		top.jzts();
		$("#Form").submit();
	}
	$(function() {
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
	       function show(STORE_ID){
				layer.open({
	                skin:"demo-class",
	                btnAlign: 'c',
	                type: 2,
	                title: '该门店详情',
	                shadeClose: false,
	                shade: 0.8,
	                area: ['80%', '150px'],
	                fixed: false, //不固定
	  				maxmin: true,
	                content:[ '<%=basePath%>storeManger/storeDetails.do?STORE_ID='+STORE_ID ],
	            });
			}
	     //停用门店操作(需二次确认)
	       function stop(STORE_ID){
	    	   layer.open({
	    		   btn: ['是','否'],
	    		   yes: function(index,layero){
	    			   layer.open({
	    	    		   btn: ['确定','取消'],
	    	    		   yes: function(index,layero){
	    	    			   //这里写停用门店方法
	    	    			   $.ajax({
									type: "POST",
									url: '<%=basePath%>storeManger/disableUsers.do?STORE_ID='+STORE_ID,
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
							 				layer.msg('停用失败！', {time: 800,icon: 1},function () {
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
	    		   content: '是否停用该门店'
	               });
			}
	     
	     //启用门店操作(需二次确认)
	       function start(STORE_ID){
	    	   layer.open({
	    		   btn: ['是','否'],
	    		   yes: function(index,layero){
	    			   layer.open({
	    	    		   btn: ['确定','取消'],
	    	    		   yes: function(index,layero){
	    	    			   //这里写启用门店方法
	    	    			   $.ajax({
									type: "POST",
									url: '<%=basePath%>storeManger/enableUser.do?STORE_ID='+ STORE_ID,
														dataType : 'json',
														cache : false,
														success : function(data) {
															if ("success" == data.result) {
																layer
																		.msg(
																				'启用成功',
																				{
																					time : 800,
																					icon : 1
																				},
																				function() {
																					setTimeout(
																							function() {
																								location
																										.reload();
																							},
																							500)
																				});
															} else {
																layer
																		.msg(
																				'启用失败！',
																				{
																					time : 800,
																					icon : 1
																				},
																				function() {
																					setTimeout(
																							function() {
																								location
																										.reload();
																							},
																							500)
																				});
															}
														}
													});
										},
										btn2 : function(index, layero) {
											location.reload();
										},
										type : 1,
										title : '启用操作',
										skin : "demo-class",
										shadeClose : false,
										shade : 0.8,
										area : [ '300px', '200px' ],
										content : '您确定这样做吗?'
									});
						},
						type : 1,
						title : '启用操作',
						skin : "demo-class",
						shadeClose : false,
						shade : 0.8,
						area : [ '200px', '150px' ],
						content : '是否启用该门店'
					});
		}
	</script>
</body>
</html>