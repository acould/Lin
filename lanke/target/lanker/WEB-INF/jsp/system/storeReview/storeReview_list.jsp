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
.demo-class .layui-layer-btn {
	border-top: 1px solid #E9E7E7;
	background-color: #fafafa
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
							<form action="storeReview/list.do" method="post" name="form"
								id="form">
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
											<lable class="lablepd">审核状态:</lable> 
											<select class="chosen-select form-control" name="STATE" id="STATE"
											data-placeholder="请选择" style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="1" <c:if test="${pd.STATE == '1'}">selected="selected"</c:if>> 已通过</option>
												<option value="2" <c:if test="${pd.STATE == '2'}">selected="selected"</c:if>> 未审核</option>
												<option value="3" <c:if test="${pd.STATE == '3'}">selected="selected"</c:if>> 未通过</option>
										</select>
										</td>
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: middle; padding-left: 15px"><a
												class="btn btn-success btn-sm" onclick="tosearch();"
												title="检索"> <i class="layui-icon"
													style="padding-right: 4px">&#xe615;</i>搜索
											</a></td>
										</c:if>
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: middle; padding-left: 2px;"><a
												class="btn btn-primary btn-sm" onclick="toExcel();"
												title="导出到EXCEL" style="margin-left: 4px"> <i
													class="layui-icon" style="padding-right: 4px">&#xe61f;</i>导出到EXCEL
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
											<th class="left">企业全称</th>
											<th class="left">门店名称</th>
											<th class="left">公众号</th>
											<th class="left">服务商编号</th>
											<th class="left">付费套餐</th>
											<th class="left">审核状态</th>
											<th class="left">申请时间</th>
											<th class="left">到期时间</th>
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
															<td class='left' style="vertical-align: middle;">${var.Company_Name}</td>
															<td class='left' style="vertical-align: middle;">${var.STORE_NAME}</td>
															<td class='left' style="vertical-align: middle;">${var.INTENET_NAME}</td>
															<td class='left' style="vertical-align: middle;">${var.SERVICE_ID}</td>
															<td class='left' style="vertical-align: middle;">
																<c:if test="${var.CHOOSE_PACKAGE=='0'}">试用一个月</c:if>
																<c:if test="${var.CHOOSE_PACKAGE=='1'}">付费一年</c:if>
															</td>
															<td class='left' id="state" style="vertical-align: middle;">
																<c:if test="${var.STATE=='1'}">已通过</c:if> 
																<c:if test="${var.STATE=='2'}">未审核</c:if> 
																<c:if test="${var.STATE=='3'}">未通过</c:if>
															</td>
															<td class='left' id="addtime" style="vertical-align: middle;">
																${var.ADDTIME}
															</td>
															<td class='left' id="addtime" style="vertical-align: middle;">
																	${var.EXPIRATION_TIME}
															</td>
															<td class='center' id="state"
																style="vertical-align: middle;">
																<c:if test="${QX.cha == 1}">
																	<c:if test="${var.STATE=='1'}">
																		<a class="btn btn-sm btn-success" title="查看" href="<%=basePath%>storeReview/toEdit.do?STORE_ID=${var.STORE_ID}&STATE=1" target="_blank">
																		 	<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>查看
																		</a>
																	</c:if>
																</c:if>
																
																<c:if test="${QX.edit == 1 }">
																	<c:if test="${var.STATE=='2'}">
																		<a class="btn btn-sm btn-danger" title="审核" href="<%=basePath%>storeReview/toEdit.do?STORE_ID=${var.STORE_ID}&STATE=0" target="_blank">
																			<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>审核
																		</a>
																	</c:if>
																</c:if> 
																
																<c:if test="${QX.cha == 1}">
																	<c:if test="${var.STATE=='3'}">
																		<a class="btn btn-sm btn-success" title="查看" href="<%=basePath%>storeReview/toEdit.do?STORE_ID=${var.STORE_ID}&STATE=2" target="_blank">
																		 	<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>查看
																		</a>
																	</c:if>
																</c:if>
																
														<%-- 		<c:if test="${QX.edit == 1 }">
																	<c:if test="${var.STATE=='4'}">
																		<a class="btn btn-sm btn-danger" title="审核"
																			href="<%=basePath%>storeReview/toEdit.do?STORE_ID=${var.STORE_ID}&STATE=3"
																			target="_blank"> <i class="layui-icon"
																			style="padding-right: 4px">&#xe642;</i>审核
																		</a>
																	</c:if>
																</c:if>
																
																<c:if test="${QX.cha == 1}">
																	<c:if test="${var.STATE=='5'}">
																		<a class="btn btn-sm btn-success" title="查看"
																			href="<%=basePath%>storeReview/toEdit.do?STORE_ID=${var.STORE_ID}&STATE=4"
																			target="_blank"> <i class="layui-icon"
																			style="padding-right: 4px">&#xe642;</i>查看
																		</a>
																	</c:if>
																</c:if>  --%>
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
												<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
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
    function tosearch() {
        top.jzts();
        $("#form").submit();
    }

    $(function () {

        //日期框
        $('.date-picker').datepicker({
            autoclose: true,
            todayHighlight: true
        });

        //下拉框
        if (!ace.vars['touch']) {
            $('.chosen-select').chosen({allow_single_deselect: true});
            $(window)
                .off('resize.chosen')
                .on('resize.chosen', function () {
                    $('.chosen-select').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    });
                }).trigger('resize.chosen');
            $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
                if (event_name != 'sidebar_collapsed') return;
                $('.chosen-select').each(function () {
                    var $this = $(this);
                    $this.next().css({'width': $this.parent().width()});
                });
            });
            $('#chosen-multiple-style .btn').on('click', function (e) {
                var target = $(this).find('input[type=radio]');
                var which = parseInt(target.val());
                if (which == 2) $('#form-field-select-4').addClass('tag-input-style');
                else $('#form-field-select-4').removeClass('tag-input-style');
            });
        }


        //复选框全选控制
        var active_class = 'active';
        $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header
            $(this).closest('table').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
                else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
            });
        });
    });
    
  //导出excel
		function toExcel(){
			var keywords = $("#nav-search-input").val();
			var STATE = $("#STATE").val();
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
			}
			else{window.location.href='<%=basePath%>storeReview/excel.do?keywords='+ keywords + '&STATE=' + STATE+'&str='+str;
				}
	}
</script>
</body>
</html>