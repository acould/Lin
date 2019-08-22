<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
						<form action="intenet/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="请输入网吧名称" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastLoginEnd" name="lastLoginEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="">1</option>
									<option value="">2</option>
								  	</select>
								</td>
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
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
									<th class="center" style="width:60px;">序号</th>
									<th class="center">网吧名称</th>
									<th class="center">微信key</th>
									<th class="center">省</th>
									<th class="center">城市</th>
									<th class="center">地址</th>
									<th class="center">电话号码</th>
									<th class="center">支付宝账户</th>
									<th class="center">邮箱</th>
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
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.INTENET_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.INTENET_NAME}</td>
											<td class='center'>${var.WECHAT_ID}</td>
											<td class='center'>
									 <c:if test="${var.PROVINCE =='1'}">北京市</c:if> 
								    <c:if test="${var.PROVINCE =='2'}">广东省 </c:if>
								    <c:if test="${var.PROVINCE =='3'}">上海市</c:if>
								   <c:if test="${var.PROVINCE =='4'}">天津市</c:if>
								   <c:if test="${var.PROVINCE =='5'}">重庆市</c:if>
								   <c:if test="${var.PROVINCE =='6'}">辽宁省</c:if>
								  <c:if test="${var.PROVINCE =='7'}">江苏省</c:if>
								  <c:if test="${var.PROVINCE =='8'}">湖北省</c:if>
								 <c:if test="${var.PROVINCE =='9'}">四川省</c:if>
								 <c:if test="${var.PROVINCE =='10'}">陕西省</c:if>
								<c:if test="${var.PROVINCE =='11'}">河北省</c:if>
								 <c:if test="${var.PROVINCE =='12'}">山西省</c:if>
								<c:if test="${var.PROVINCE =='13'}">河南省</c:if>
								 <c:if test="${var.PROVINCE =='14'}">吉林省</c:if>
								<c:if test="${var.PROVINCE =='15'}">黑龙江 </c:if>
								<c:if test="${var.PROVINCE =='16'}">内蒙古</c:if> 
								 <c:if test="${var.PROVINCE =='17'}">山东省 </c:if>
								<c:if test="${var.PROVINCE =='18'}">安徽省</c:if> 
								<c:if test="${var.PROVINCE =='19'}">浙江省 </c:if>
								<c:if test="${var.PROVINCE =='20'}">福建省 </c:if>
								 <c:if test="${var.PROVINCE =='21'}">湖南省</c:if> 
								 <c:if test="${var.PROVINCE =='22'}">广西省 </c:if>
								 <c:if test="${var.PROVINCE =='23'}">江西省 </c:if>
								<c:if test="${var.PROVINCE =='24'}">贵州省</c:if> 
								 <c:if test="${var.PROVINCE =='25'}">云南省 </c:if>
								<c:if test="${var.PROVINCE =='26'}">西藏 </c:if>
								 <c:if test="${var.PROVINCE =='27'}">海南省 </c:if>
								 <c:if test="${var.PROVINCE =='28'}">甘肃省 </c:if>
								 <c:if test="${var.PROVINCE =='29'}">宁夏 </c:if>
								 <c:if test="${var.PROVINCE =='30'}">青海省</c:if> 
								<c:if test="${var.PROVINCE =='31'}">新疆</c:if> 
								<c:if test="${var.PROVINCE =='32'}">香港 </c:if>
								<c:if test="${var.PROVINCE =='33'}">澳门 </c:if>
								 <c:if test="${var.PROVINCE =='34'}">台湾 </c:if>
								 <c:if test="${var.PROVINCE =='35'}">海外 </c:if>
											</td>
											<td class='center'>${var.CITY}</td>
											<td class='center'>${var.ADDRESS}</td>
											<td class='center'>${var.PHONE}</td>
											<td class='center'>${var.TAO_BAO}</td>
											<td class='center'>${var.EMAIL}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.INTENET_ID}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" onclick="del('${var.INTENET_ID}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
													</c:if>
													<c:if test="${cdsz == 1 }">
													<a  class="btn btn-xs btn-purple" onclick="cdsz('${var.INTENET_ID}');">
														<i class="ace-icon fa fa-qrcode bigger-120" title="设置公众号菜单"></i>
													</a>
													<a  class="btn btn-xs btn-purple" onclick="cdsc('${var.INTENET_ID}');">
														<i class="ace-icon fa fa-qrcode bigger-120" title="删除公众号菜单"></i>
													</a>
													</c:if>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.INTENET_ID}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.INTENET_ID}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${cdsz == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="cdsz('${var.INTENET_ID}');" class="btn btn-mini btn-purple" data-rel="tooltip" title="设置公众号菜单">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															<li>
																<a style="cursor:pointer;" onclick="cdsc('${var.INTENET_ID}');" class="btn btn-mini btn-purple" data-rel="tooltip" title="删除公众号菜单">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
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
									<a class="btn btn-sm btn-success" <c:if test="${abcd == 1 }">onclick="add();"</c:if><c:if test="${abcd != 1 }">onclick="check();"</c:if>>新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-sm btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
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
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
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
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="新增";
				 diag.URL = '<%=basePath%>intenet/goAdd.do';
				 diag.Width = 650;
				 diag.Height = 755;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 if('${page.currentPage}' == '0'){
							 top.jzts();
							 setTimeout("self.location=self.location",100);
						 }else{
							 nextPage(${page.currentPage});
						 }
					}
					diag.close();
				 };
				 diag.show();
		}
		
		function check(){
			alert("购买创建帐号权限已经达到上限，无法再次创建网吧");
		}
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>intenet/delete.do?INTENET_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		function cdsz(Id){
			bootbox.confirm("确定要设置公众号菜单吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>intenet/setupMenu.do?INTENET_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		function cdsc(Id){
			bootbox.confirm("确定要删除公众号菜单吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>intenet/deleteMenu.do?INTENET_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>intenet/goEdit.do?INTENET_ID='+Id;
			 diag.Width = 650;
			 diag.Height = 755;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
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
								url: '<%=basePath%>intenet/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>intenet/excel.do';
		}
	</script>


</body>
</html>