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
							<form action="store/list.do" method="post" name="Form" id="Form">
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
											<lable class="lablepd"> 门店状态: </lable> <select
											class="chosen-select form-control" name="ASTATE" id="ASTATE"
											data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="1">正常</option>
												<option value="2">停用</option>
										</select>
										</td>

										<td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd"> 加V状态: </lable> <select
											class="chosen-select form-control" name="BSTATE" id="BSTATE"
											data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0">未绑定</option>
												<option value="1">已绑定</option>
												<option value="2">审核中</option>
												<option value="3">审核未通过</option>
										</select>
										</td> -->

										<!-- <td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd"> 加￥状态: </lable> <select
											class="chosen-select form-control" name="BSTATE"
											id="BSTATE" data-placeholder="请选择"
											style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0">已加￥</option>
												<option value="1">未加￥</option>
												<option value="2">审核中</option>
												<option value="3">审核未通过</option>
										</select>
										</td> -->

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
											<th class="center" style="width: 35px;"><label
												class="pos-rel"><input type="checkbox" class="ace"
													id="zcheckbox" /><span class="lbl"></span></label></th>
											<th class="center" style="width: 60px;">序号</th>
											<!-- <th class="center">企业全称</th> -->
											<th class="center">门店名称</th>
											<!-- <th class="center">公众号</th>
											<th class="center">计费绑定</th>
											<th class="center">到期时间</th>
											<th class="center">支付绑定</th>
											<th class="center">到期时间</th>
											<th class="center">门店状态</th> -->
											<th class="center">操作</th>
											<!--  <th class="center">支付绑定</th>
									        <th class="center">到期时间</th>
											<th class="center">门店所在的省份</th>
									        <th class="center">门店所在的城市</th>
									        <th class="center">门店所在的区</th>
									        <th class="center">门店所在的详细街道地址</th>
									        <th class="center">门店的电话</th> -->
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
																<label class="pos-rel"><input type='checkbox'
																	name='ids' value="${var.STORE_ID}" class="ace" /><span
																	class="lbl"></span></label>
															</td>
															<td class='center'
																style="width: 30px; vertical-align: middle;">${vs.index+1}</td>
															<%-- <td class='center' style="vertical-align: middle;">${var.Company_Name}</td> --%>
															<td class='center' style="vertical-align: middle;">${var.STORE_NAME}</td>
															<%-- <td class='center' style="vertical-align: middle;">${var.INTENET_NAME}</td>

															<td class='center' style="vertical-align: middle;">
																<c:if test="${var.BSTATE==0}">未绑定</c:if> <c:if
																	test="${var.BSTATE==1}">已绑定</c:if> <c:if
																	test="${var.BSTATE==2}">审核中</c:if> <c:if
																	test="${var.BSTATE==3}">未通过审核</c:if>
															</td>

															<td class='center' style="vertical-align: middle;">${var.EXPIRATION_TIME}</td>
															<td class='center' style="vertical-align: middle;">
																未开启</td>
															<td class='center' style="vertical-align: middle;">
																<c:if test="${var.ASTATE==1}">正常</c:if> 
																<c:if test="${var.ASTATE==2}">停用</c:if>
															</td> --%>
															<%-- <td class='center' style="vertical-align:middle;">${var.STORE_NAME}</td>
											
											<td class='center' style="vertical-align:middle;">${var.STORE_NAME}</td> --%>
															<%-- <td class='center'>${var.PROVINCE}</td>
											<td class='center'>${var.CITY}</td>
											<td class='center'>${var.COUNTY}</td>
											<td class='center'>${var.ADDRESS}</td>
											<td class='center'>${var.TELEPHONE}</td> --%>
															<td class="center"><c:if
																	test="${QX.edit != 1 && QX.del != 1 }">
																	<span
																		class="label label-large label-grey arrowed-in-right arrowed-in"><i
																		class="ace-icon fa fa-lock" title="无权限"></i></span>
																</c:if>
																<div class="hidden-sm hidden-xs btn-group">
																	<a class="btn btn-sm btn-success" title="编辑"
																		onclick="edit('${var.STORE_ID}');"> <i
																		class="layui-icon" style="padding-right: 4px"
																		title="编辑">&#xe642;</i>编辑
																	</a>
																	<!--
														<a class="btn btn-sx btn-danger" onclick="del('6fc86c26-f10b-4ac8-9d1f-c8365fd1e1f0');">
															<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
														</a>
													-->
																	<%-- <a class="btn btn-sm btn-success" title="查看详情"
																		onclick="show('${var.STORE_ID}');"> <i
																		class="layui-icon" style="padding-right: 4px">&#xe642;</i>查看详情
																	</a>
																	<c:if test="${var.ASTATE==1}">
																		<a id="stop" class="btn btn-sm btn-danger" title="停用"
																			onclick="stop('${var.STORE_ID}');"> <i
																			class="layui-icon" style="padding-right: 4px">&#xe642;</i>停用
																		</a>
																	</c:if>
																	<c:if test="${var.ASTATE==2}">
																		<a id="start" class="btn btn-sm btn-danger" title="启用"
																			onclick="start('${var.STORE_ID}');"> <i
																			class="layui-icon" style="padding-right: 4px">&#xe642;</i>启用
																		</a>
																	</c:if> --%>
																</div></td>
														</tr>

													</c:forEach>
												</c:if>
												<c:if test="${QX.cha == 0 }">
													<tr>
														<td colspan="100" class="center"
															style="vertical-align: middle;">您无权查看</td>
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
											<td style="vertical-align: middle;"><c:if
													test="${QX.add == 1 }">
													<a class="btn btn-xm btn-primary" onclick="add();"> <i
														class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增
													</a>
												</c:if></td>
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


	<!-- Modal 授权提示-->
	<div class="modal-body"
		style="padding: 20px 20px 0 20px; display: none;">
		<div
			style="display: inline-block; position: relative; top: -50px; margin-left: 10px">
			<img src="<%=basePath%>static/login/images/eimg.png" alt="">
		</div>
		<div
			style="display: inline-block; margin-left: 35px; margin-top: 48px;">
			<p style="font-size: 18px; color: #333; margin-bottom: 5px">每次新增门店后一定要去"网吧管理-->门店展示"去进行门店</p>
			<p style="font-size: 18px; color: #333;">地址等信息的补充哦~否则的话，别人是不知道你的位置。</p>
			<p style="margin-top: 54px">
				<span
					style="font-size: 16px; color: #fff; background-color: #d2d2d2; padding: 8px 34px; border-radius: 50px; cursor: pointer; margin-right: 14px;"
					id="motai">我知道了</span> <span id="ud"
					style="font-size: 16px; color: #fff; background-color: #e44547; padding: 8px 34px; border-radius: 50px; cursor: pointer;"
					onclick="edit_yindao();">去补充地址信息</span>
			</p>
		</div>
	</div>
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
		
	//新增方法
	var uid = uuuid();
	function add(){
		layer.open({
			btn: ['保存','关闭'],
			btn1: function(index, layero){
				//按钮的回调
				var res = window["layui-layer-iframe" + index].baocun();
				if(res.msg == true){
					layer.close(index);
					layer.open({	
	        			type:1
	                	,title:"新增门店提示"
	                	,shade:0.8
	                	,shadeClose:false
	                	,content:$(".modal-body")
	                	,success:function(layero,index){
	                		$("#motai").on("click",function(){
								layer.close(index);
	                		})
						}
						,area: ['580px','460px']
						,end:function(){
							location.reload();
						}
					})
				}
			},
			skin: 'demo-class',
	        btnAlign: 'c',
	        type: 2,
	        title: '新增门店',
	        shadeClose: false,
	        shade: 0.8,
	        area: ['35%', '30%'],
	        content:[ '<%=basePath%>store/goAdd.do?STORE_ID='+uid ],
		});
	}
	
		//修改
		function edit(STORE_ID){
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
                	title: '修改门店',
                	shadeClose: false,
                	shade: 0.8,
                	area: ['35%', '30%'],
                	content:[ '<%=basePath%>store/goEdit.do?STORE_ID='+STORE_ID ],
            	});
			}
		
		//删除
		function del(STORE_ID){
			layer.confirm('确定要删除此门店吗?', {
                    btn: ['确定','取消'],
                }, function(){
                	var url = "<%=basePath%>store/delete.do?STORE_ID="+STORE_ID+"&guid="+new Date().getTime();
					$.get(url,function(data){
						if("success" == data.result){
							layer.msg('删除成功', {time: 800,icon: 1},function () {
                    			location.reload();
                    		});
						}else if("false" == data.result){
							layer.msg('删除失败，请先删除人员管理中该门店的人员!', {time: 800,icon: 1},function () {
					 			setTimeout(function () {
								},500)
							});
						}
					});
         }, function(){
              return
            });
		}
		
		//门店地址引导设置
		function edit_yindao(){
			layer.open({
					btn: ['保存','关闭'],
                	btn1: function(index, layero){
                		//按钮的回调
                    	var res = window["layui-layer-iframe" + index].baocun();
                    	if(res.msg == true){
                    		layer.close(index);
                    	}
                	},
                	skin: 'demo-class',
                	btnAlign: 'c',
                	type: 2,
                	title: '门店地址引导',
                	shadeClose: false,
                	shade: 0.8,
                	area: ['50%', '70%'],
                	content:[ '<%=basePath%>storeShow/goEdit.do?STORE_ID='+uid],
                	end:function(){
                    	location.reload();
                	}
            	})
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
							$.ajax({
								type: "POST",
								url: '<%=basePath%>store/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								cache: false,
								success: function(data){
									if("success" == data.result){
										$.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									  	});
									}else if("false" == data.result){
										layer.msg('删除失败，请先删除该门店的人员!', {time: 2000,icon: 2},function () {
					 						setTimeout(function () {
					 							location.reload();
											},500)
										});
										
									}
								}
							});
						}
					 }
              },function(){
					return
					}
            );
		}
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>store/excel.do';
		}
		//页面生成 STORE_ID
		function uuuid() {
	    	var s = [];
		    var hexDigits = "0123456789abcdef";
		    for (var i = 0; i < 36; i++) {
		        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		    }
		    s[14] = "4";
		    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
		    s[8] = s[13] = s[18] = s[23] = "-";
		    var uuid = s.join("");
		    return uuid;
		}
</script>
</body>
</html>