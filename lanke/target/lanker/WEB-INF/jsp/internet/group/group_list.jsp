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
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
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
						<form action="group/list.do" method="post" name="Form" id="Form">
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
								<td style="vertical-align:middle;padding-left:15px;"><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="vertical-align:middle;padding-left:6px;"><input class="span10 date-picker" name="lastLoginEnd" name="lastLoginEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								<td style="vertical-align:middle;padding-left:12px;">
								战局名称：
								 	<select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<option value="${var.GROUP_ID }"  >${var.NAME }</option>
									</c:forEach>
								  	</select>
								</td>
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:10px">
									<a class="btn btn-success btn-sm" onclick="tosearch();"  title="检索">
										<i class="layui-icon" style="padding-right: 4px">&#xe615;</i>搜索
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
									<th class="center" style="width:50px;">序号</th>
									<th class="center">战局名称</th>
									<th class="center">比赛开始时间</th>
									<th class="center">报名截止时间</th>
									<th class="center">战队数量</th>
									<th class="center">队伍最低人数</th>
									<th class="center">队伍最高人数</th>
									<th class="center">创建时间</th>
									<th class="center">创建用户</th>
									<th class="center">状态</th>
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
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.GROUP_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.NAME}</td>
											<td class='center'>${var.BEGIN_TIME}</td>
											<td class='center'>${var.BM_TIME}</td>
											<td class='center'>${var.TEAM_NUMBER}</td>
											<td class='center'>${var.MIN_NUMBER}</td>
											<td class='center'>${var.MAX_NUMBER}</td>
											<td class='center'>${var.CREATE_TIME}</td>
											<td class='center'>${var.USER_NAME}</td>
											<c:if test="${var.STATE == 1 }">
												<c:if test="${var.zhuangtai == 'jieshu' }">
													<td class='center'>已结束</td>
												</c:if>
												<c:if test="${var.zhuangtai == 'weijieshu' }">
													<td class='center'>未发布</td>
												</c:if>
												<c:if test="${var.zhuangtai == 'bisaizhong' }">
													<td class='center'>未发布</td>
												</c:if>
												<c:if test="${var.zhuangtai == 'weizhi' }">
													<td class='center'>请检查开始和截止时间！</td>
												</c:if>
											</c:if>
											<c:if test="${var.STATE == 2 }">
												<c:if test="${var.zhuangtai == 'jieshu' }">
													<td class='center'>已结束</td>
												</c:if>
												<c:if test="${var.zhuangtai == 'weijieshu' }">
													<td class='center'>已发布</td>
												</c:if>
												<c:if test="${var.zhuangtai == 'bisaizhong' }">
													<td class='center'>比赛中</td>
												</c:if>
												<c:if test="${var.zhuangtai == 'weizhi' }">
													<td class='center'>请检查开始和截止时间！</td>
												</c:if>
											</c:if>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
														<!--发布-->
														<c:if test="${var.STATE == 1 && var.zhuangtai == 'weijieshu'}">
															<a class="btn btn-sm btn-primary" title="发布" onclick="editState('${var.GROUP_ID}');">
																<i class="layui-icon" style="padding-right: 4px">&#xe609;</i>发布
															</a>
														</c:if>
														<!-- 取消发布 -->
														<c:if test="${var.STATE == 2 }">
															<c:if test="${var.zuju == 2 }">
																<a class="btn btn-sm btn-primary" title="取消发布" onclick="endState('${var.GROUP_ID}');">
																	<i class="layui-icon" style="padding-right: 4px">&#xe609;</i>取消发布
																</a>
															</c:if>
														</c:if>
														<a class="btn btn-sm btn-success" title="编辑" onclick="edit('${var.GROUP_ID}');">
															<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>编辑
														</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
														<c:if test="${var.STATE == 1 || var.zhuangtai == 'jieshu'}">
															<a class="btn btn-sm btn-danger" title="删除" onclick="del('${var.GROUP_ID}');">
																<i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
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
										<a class="btn btn-xm btn-primary" onclick="add();">
											<i class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增
										</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
										<a class="btn btn-xm btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >
											<i class="layui-icon" style="padding-right: 4px">&#xe640;</i>批量删除
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
	<!--提示框-->
	<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script>
	
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
		       
       //新增
       function add(){
			layer.open({
            btn: ['保存','发布','关闭'],
            btn1: function(index, layero){
                	//按钮的回调
                    var res = window["layui-layer-iframe" + index].baocun();
                    if(res.msg == true){
                    	setTimeout(function () { 
                    		location.reload();
                    	},500)
                    }
                },
                btn2: function(index, layero){
                	//按钮的回调
                    var res = window["layui-layer-iframe" + index].fabu();
                    if(res.msg == true){
                    	setTimeout(function () { 
                    		location.reload();
                    	},500)
                    }
                },
                skin: 'demo-class',
                btnAlign: 'c',
                type: 2,
                title: '新建组局',
                shadeClose: false,
                shade: 0.8,
                area: ['45%', '80%'],
                content:[ '<%=basePath%>group/goAdd.do '],
            });
		}
		
		//删除
		function del(Id){
			layer.confirm('确定要删除吗?', {
                    btn: ['确定','取消'],
                }, function(){
                	$.ajax({
							type: "POST",
							url: '<%=basePath%>group/delete.do',
		    				data: {GROUP_ID:Id,tm:new Date().getTime()},
							dataType:'json',
							cache: false,
							success: function(data){
								if("success" == data.result){
                    				location.reload();
					 			}else{
					 				layer.msg('删除失败！', {time: 800,icon: 1},function () {
					 					setTimeout(function () { 
                    						location.reload();
                    					},500)
                    				});
					 			}
							}
						});
                }, function(){
                    return
			});
		}
		
		//修改
		function edit(ID){
			layer.open({
                	btn: ['保存','关闭'],
                	btn1: function(index, layero){
                		//按钮的回调
                    	var res = window["layui-layer-iframe" + index].baocun();
                    	if(res.msg == true){
                    		layer.close(index);
                    		setTimeout(function () { 
                    			location.reload();
                    		},500)
                    	}
                	},
                	skin: 'demo-class',
                	btnAlign: 'c',
                	type: 2,
                	title: '修改组局',
                	shadeClose: false,
                	shade: 0.8,
                	area: ['45%', '80%'],
                	content:[ '<%=basePath%>group/goEdit.do?GROUP_ID='+ID ],
            	});
			}
		
		//发布
		function editState(Id){
			layer.confirm('确定发布吗？', {
                    btn: ['确定','取消'],
                }, function(){
                	$.ajax({
							type: "POST",
							url: '<%=basePath%>group/updateState.do',
		    				data: {GROUP_ID:Id},
							dataType:'json',
							cache: false,
							success: function(data){
								if("success" == data.result){
									layer.msg('发布成功', {time: 800,icon: 1},function () {
										setTimeout(function () { 
                    						location.reload();
                    					},400)
                    				});
					 			}else{
					 				layer.msg('发布失败，赛事已结束！', {time: 800,icon: 1},function () {
					 					setTimeout(function () {
                    						location.reload();
                    					},500)
                    				});
					 			}
							}
						});
                }, function(){
                    return
                });
		}
		
		//取消发布
		function endState(Id){
			layer.confirm('确定取消发布吗？', {
                    btn: ['确定','取消'],
                }, function(){
                	$.ajax({
							type: "POST",
							url: '<%=basePath%>group/endState.do',
		    				data: {GROUP_ID:Id},
							dataType:'json',
							cache: false,
							success: function(data){
								if("success" == data.result){
									layer.msg('取消发布成功', {time: 800,icon: 1},function () {
										setTimeout(function () { 
                    						location.reload();
                    					},400)
                    				});
					 			}else{
					 				layer.msg('取消发布失败', {time: 800,icon: 1},function () {
					 					setTimeout(function () { 
                    						location.reload();
                    					},500)
                    				});
					 			}
							}
						});
                }, function(){
                    return
                });
		}
		
		//批量操作
		function makeAll(msg){
			layer.confirm('确定要删除选中的数据吗?', {
                    btn: ['确定','取消'],
                }, function(){
                	var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						layer.msg('您没有选择任何内容！', {time: 800,icon: 1},function () {
                    	});
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>group/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
									 setTimeout(function () { 
                    					location.reload();
                    				},500)
								}
							});
						}
					}
              }, function(){
                    return
            });
		}
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>group/excel.do';
		}
</script>
</body>
</html>