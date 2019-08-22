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
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
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
							<form action="payOpenReview/list.do" method="post" name="form"
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
											<lable class="lablepd">资料审核:</lable> 
											<select class="chosen-select form-control" name="state" id="state"
												data-placeholder="请选择" style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0" <c:if test="${pd.state == '0'}">selected="selected"</c:if>> 未通过</option>
												<option value="1" <c:if test="${pd.state == '1'}">selected="selected"</c:if>> 已通过</option>
												<option value="2" <c:if test="${pd.state == '2'}">selected="selected"</c:if>> 待审核</option>
											</select>
										
										</td>
										<td style="vertical-align: middle; padding-left: 15px;">
											<lable class="lablepd">开通状态:</lable> 
											<select class="chosen-select form-control" name="status" id="status"
												data-placeholder="请选择" style="vertical-align: middle; width: 100px;">
												<option value=""></option>
												<option value="">全部</option>
												<option value="0" <c:if test="${pd.status == '0'}">selected="selected"</c:if>> 待开通</option>
												<option value="1" <c:if test="${pd.status == '1'}">selected="selected"</c:if>> 已开通</option>
												<option value="2" <c:if test="${pd.status == '2'}">selected="selected"</c:if>> 等待快递</option>
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
											<td style="vertical-align: middle; padding-left: 2px;">
												<a class="btn btn-primary btn-sm" onclick="toExcel();" style="margin-left: 4px">
												 	<i class="layui-icon" style="padding-right: 4px">&#xe61f;</i>导出到EXCEL
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
											<th class="center" style="width: 80px;">序号</th>
											<th class="left">公众号</th>
											<th class="left">门店名称</th>
											<th class="left">企业名称</th>
											<th class="left">法人姓名</th>
											<th class="left">申请时间</th>
											<th class="left">快递单号</th>
											<th class="left">资料审核</th>
											<th class="left">开通状态</th>
											<th class="left">商户号</th>
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
																	<input type='checkbox' name='ids' value="${var.id}" class="ace" />
																	<span class="lbl"></span>
																</label>
															</td>
															<td class='center' style="vertical-align: middle;">${vs.index+1}</td>
															<td class='left' style="vertical-align: middle;">${var.intenet_name}</td>
															<td class='left' style="vertical-align: middle;">${var.store_name}</td>
															<td class='left' style="vertical-align: middle;">${var.enterprise_name}</td>
															<td class='left' style="vertical-align: middle;">${var.corporate_name}</td>
															<td class='left' style="vertical-align: middle;">${var.updatetime}</td>
															<td class='left' style="vertical-align: middle;">
																<c:if test="${var.state == '1'}">
																	<c:if test="${var.express_number == null || var.express_number == ''}">无</c:if>
																	<c:if test="${var.express_number != null}">${var.express_number}</c:if>
																</c:if>
															</td>
															<td class='left' style="vertical-align: middle;">
																<c:if test="${var.state=='0'}">未通过</c:if>
																<c:if test="${var.state=='1'}">已通过</c:if> 
																<c:if test="${var.state=='2'}">待审核</c:if>
															</td>
															<td class='left' style="vertical-align: middle;">
																<c:if test="${var.status=='0'}">待开通</c:if>
																<c:if test="${var.status=='1'}">已开通</c:if> 
																<c:if test="${var.status=='2'}">等待快递</c:if>
															</td>
															<td class='left' style="vertical-align: middle;">${var.business_number}</td>
															<td class='center' style="vertical-align: middle;">
																<c:if test="${var.state != '2'}">
																	<a class="btn btn-sm btn-success" href="accountSettings/goPayOpen.do?id=${var.id}&store_id=${var.store_id}" target="_blank"> 
																		<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>查看
																	</a>
																</c:if>
																<c:if test="${var.state == '2'}">
																	<a class="btn btn-sm btn-danger" target="_blank" href="accountSettings/goPayOpen.do?id=${var.id}&store_id=${var.store_id}"> 
																		<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>审核
																	</a>
																</c:if>
																<c:if test="${var.status == '0'}">
																	<a class="btn btn-sm btn-warning" onclick="submitOpen('${var.id}','<%=basePath%>')"> 
																		<i class="layui-icon" style="padding-right: 4px">&#xe605;</i>开通
																	</a>
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
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
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

		<div class="FAIL" style="display: none;padding: 20px">
			<p style="font-size:12px;color:#666666;padding-bottom: 13px;">请确保与银联已经进行了线下测试报告</p>
			<form class="layui-form layui-form-pane" action="" method="post" id="OPEN_FORM">
				<input type="hidden" id="intenetdatum_id" name="intenetdatum_id">
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="lanke-sign">*</span>商户编号：</label>
					<div class="layui-input-block">
						<input type="text" name="business_number" class="layui-input" placeholder=" 这里输入当前开通门店的商户编号" id="business_number">
					</div>
				</div>
			</form>
		</div>
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
	<script src="<%=basePath%>newStyle/js/lk-pay.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
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
		/*
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
		}*/
</script>
</body>
</html>