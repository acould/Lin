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
    <title>Document</title>
    <style>
        .borPadding {
            padding:20px 30px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
        .layui-icon {font-size:14px;}
		.tan{
			position:absolute;
			top:0;
			bottom:0;
			left:0;
			right:0;
			z-index:100;
			background: rgba(1,1,1,.4);
			overflow: hidden;
			display: none;
		}
		#del{
			position:absolute;
			top:0;
			bottom:0;
			left:0;
			right:0;
			z-index:100;
			background: rgba(1,1,1,.06);
			overflow: hidden;
			display: flex;
			align-items: center;
            display: none;
		}
		#del .layui-form{
			margin:0 auto;
		}
		#del .layui-form .layui-unselect{
			margin:1rem;
		}
		#del .choose{
			position:absolute;
			top:45%;
			left:50%;
			transform: translate(-50%,50%);
		}
		.tan .box{
			width: 45rem;
			background: #fff;
			margin:0 auto;
			position:absolute;
			top:50%;
			left:50%;
			transform: translate(-50%,-50%);
			mso-border-shadow: yes;
		}
		.tan .box section{
			padding:1rem 2rem;
			margin-bottom:8rem;
		}
		.tan .box footer{
			background: rgba(1,1,1,.04);
			border-top:1px solid rgba(1,1,1,.1);
			height:5rem;
			display:flex;
			justify-content: center;
			align-items: center;
		}
		.tan .box footer div{
			height:2.4rem;
			width: 5rem;
			text-align: center;
			line-height: 2.4rem;
			background: #fff;
			margin:1rem;
			border:1px solid rgba(1,1,1,.1);
			cursor:pointer;
		}
		.sa{
			background-color:#0e90d2!important;
			color:#fff;
		}
		.tan .box header{
			height:4rem;
			background: rgba(1,1,1,.04);
			line-height:4rem;
			padding-left:1rem;
			border-bottom:1px solid rgba(1,1,1,.1);
		}
		.layui-form-item{
			margin:1rem auto 0;
			display:flex;
			border:1px solid rgba(1,1,1,.1);
			height:3.2rem;
		}
		.layui-form-item label{
			width: 26%;
			text-align: center;
			border-right:1px solid rgba(1,1,1,.1);
			background: rgba(1,1,1,.04);
			margin-bottom:0;
		}
		.layui-input-block{
			margin-left:0;
			flex: 1;
		}
		.layui-input-block select{
			width: 100%;
			height:100%;
			border:0;
		}
		.layui-input-block  .jifen{
			width: 100%;
			height:100%;
			border:0;
		}
		.text{
			display:flex;
			padding-top:.5rem;
			line-height:1.4rem;
			height:4.6rem;
			border-bottom:1px solid rgba(1,1,1,.1);
		}
		.text div{
			width: 20rem;
		}
		.layui-input-block{
			display:flex;
		}
		.layui-input-block div{
			height:100%;
			margin:0;
			margin-top:0!important;
		}
		.layui-input-block i{
			height:100%;
		}
		#WEEKEND_SEND{
			height: 90%;
			margin-left:1rem;
			margin-top:.1rem;
		}
		.saoma {
			margin-top: 20px;
			text-align: center;
			border-left: 1px solid #ececec;
			border-bottom: 1px solid #ececec;
			line-height:3rem;
		}

		.saoma .top {
			display: flex;
			background: #fbfbfb;
		}

		.saoma .top .left {
			width: 16%;
			border-right: 1px solid #ececec;
			border-top: 1px solid #ececec;

		}

		.saoma .right {
			flex: 1;
			display: flex;
			margin-bottom:0;
		}

		.saoma .right li {
			flex: 1!important;
			list-style: none;
			border-right: 1px solid #ececec;
			border-top: 1px solid #ececec;
		}

		.saoma .bottom {
			display: flex;
			background: #fbfbfb;
		}

		.saoma .bottom .left {
			width: 16%;
			border-right: 1px solid #ececec;
			border-top: 1px solid #ececec;

		}
		.saoma .bottom .right{
			margin-bottom:0;
			flex: 1;
			display: flex;
		}

		.saoma .bottom .right li {
			background: #fff;
		}
		.INTEGRAL_SEND{
			width:100%;
			height:100%;
			border:0;
			background:#fff;
			text-align: center;
		}
		.fuxuan{
			border: 0!important;
		}
		.fuxuan1{
			border:1px solid rgba(1,1,1,.1);
		}
		.i{
			border:0;
			width: 60%;
			text-align: center;

		}
        .mendian h3{
			font-size:1.5rem;
			font-weight: 600;
			line-height:3.6rem;
		}
		.mendian .xuanze{
			width: 80%;
			display: block;
		}
		.mendian .xuanze div{
			padding-bottom:.8rem;
		}
		.gongneng{
			color:red;
			font-size:1.2rem;
			overflow: hidden;
			height:1.6rem;
			line-height: 1.6rem;
			margin-bottom: 4rem;
			display:none;
		}
		.gongneng span{
			color:red;
			display:inline-block;
			width:5.4rem;
			height:1.6rem;
			text-align:center;
			line-height:1.6rem;
			border:1px solid red;
			float: right;
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
							
						<form action="intintegral/list.do" method="post" name="Form" id="Form">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:18px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:80px;">序号</th>
									<%--<th class="center">积分类型</th> --%>
									<th class="center">网吧名称</th>
									<th class="center">奖励类别</th>
									<%--<th class="center">状态</th> --%>
<%--									<th class="center">周末积分</th>--%>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody class="tbody">
							<!-- 开始循环 -->	
<%--							<c:choose>--%>
<%--								<c:when test="${not empty varList}">--%>
<%--									<c:if test="${QX.cha == 1 }">--%>
<%--									<c:forEach items="${varList}" var="var" varStatus="vs">--%>
<%--										<tr>--%>
<%--											<td class='center' style="vertical-align: middle">--%>
<%--												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.INTINTEGRAL_ID}" class="ace" /><span class="lbl"></span></label>--%>
<%--											</td>--%>
<%--											<td class='center' style="width: 30px;vertical-align: middle">${vs.index+1}</td>--%>
<%--											&lt;%&ndash;<td class='center'>--%>
<%--												<c:if test="${var.INTEGRAL_TYPE =='1'}">普通</c:if>--%>
<%--											    <c:if test="${var.INTEGRAL_TYPE =='2'}">节假日</c:if>--%>
<%--											    <c:if test="${var.INTEGRAL_TYPE =='3'}">新店开张</c:if>--%>
<%--										    </td> &ndash;%&gt;--%>
<%--										    <td class='center' style="vertical-align: middle">--%>
<%--											    <c:if test="${var.CATEGRORY =='1'}">充值</c:if>--%>
<%--											    <c:if test="${var.CATEGRORY =='2'}">签到</c:if>--%>
<%--												<c:if test="${var.CATEGRORY =='3'}">参赛</c:if>--%>
<%--												<c:if test="${var.CATEGRORY =='4'}">老带新</c:if>--%>
<%--												<c:if test="${var.CATEGRORY =='5'}">扫码开机</c:if>--%>
<%--										     </td>--%>
<%--											<td class='center' style="vertical-align: middle">--%>
<%--												<c:if test="${var.CATEGRORY =='1'}">${var.INTEGRAL_SEND}</c:if>--%>
<%--												<c:if test="${var.CATEGRORY =='2'}">${var.INTEGRAL_SEND}</c:if>--%>
<%--												<c:if test="${var.CATEGRORY =='3'}">${var.INTEGRAL_SEND}</c:if>--%>
<%--												<c:if test="${var.CATEGRORY =='4'}">${var.INTEGRAL_SEND}</c:if>--%>
<%--												<c:if test="${var.CATEGRORY =='5'}">${var.integralnums}</c:if>--%>
<%--											</td>--%>
<%--&lt;%&ndash;											<td class='center' style="vertical-align: middle">&ndash;%&gt;--%>
<%--&lt;%&ndash;												<c:if test="${var.CATEGRORY =='1'}">${var.WEEKEND_SEND}}</c:if>&ndash;%&gt;--%>
<%--&lt;%&ndash;												<c:if test="${var.CATEGRORY =='2'}">${var.WEEKEND_SEND}</c:if>&ndash;%&gt;--%>
<%--&lt;%&ndash;												<c:if test="${var.CATEGRORY =='3'}">${var.WEEKEND_SEND}</c:if>&ndash;%&gt;--%>
<%--&lt;%&ndash;												<c:if test="${var.CATEGRORY =='4'}">${var.WEEKEND_SEND}</c:if>&ndash;%&gt;--%>
<%--&lt;%&ndash;												<c:if test="${var.CATEGRORY =='5'}">/</c:if>&ndash;%&gt;--%>
<%--&lt;%&ndash;											</td>&ndash;%&gt;--%>
<%--											&lt;%&ndash;<td class='center'>${var.STATE}</td> &ndash;%&gt;--%>
<%--											<td class="center" style="vertical-align: middle">--%>
<%--												<c:if test="${QX.edit != 1 && QX.del != 1 }">--%>
<%--												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>--%>
<%--												</c:if>--%>
<%--												<div class="hidden-sm hidden-xs btn-group">--%>
<%--													<c:if test="${QX.edit == 1 }">--%>
<%--													<a class="btn btn-sm btn-success" title="编辑" onclick="edit('${var.INTINTEGRAL_ID}');">--%>
<%--														<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>编辑--%>
<%--													</a>--%>
<%--													</c:if>--%>
<%--													<c:if test="${QX.del == 1 }">--%>
<%--													<a class="btn btn-sm btn-danger" onclick="del('${var.INTINTEGRAL_ID}');">--%>
<%--														<i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除--%>
<%--													</a>--%>
<%--													</c:if>--%>
<%--												</div>--%>
<%--												<div class="hidden-md hidden-lg">--%>
<%--													<div class="inline pos-rel lk-ul-hoverShow">--%>
<%--														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">--%>
<%--																<i class="ace-icon fa fa-cog icon-only bigger-110"></i>--%>
<%--														</button>--%>
<%--														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">--%>
<%--														<c:if test="${QX.edit == 1 }">--%>
<%--															<li>--%>
<%--																<a style="cursor: pointer;" onclick="edit('${var.INTINTEGRAL_ID}');" class="tooltip-success" data-rel="tooltip" title="编辑">--%>
<%--																	<span class="text-success lk-text">--%>
<%--																		 <i class="layui-icon" >&#xe642;</i>--%>
<%--																	</span>--%>
<%--																</a>--%>
<%--															</li>--%>
<%--														</c:if>--%>
<%--														<c:if test="${QX.del == 1 }">--%>
<%--															<li>--%>
<%--																<a style="cursor: pointer;" onclick="del('${var.INTINTEGRAL_ID}');" class="tooltip-success" data-rel="tooltip" title="删除">--%>
<%--																	<span class="text-danger lk-text">--%>
<%--																		 <i class="layui-icon" >&#xe640;</i>--%>
<%--																	</span>--%>
<%--																</a>--%>
<%--															</li>--%>
<%--														</c:if>--%>
<%--														</ul>--%>
<%--													</div>--%>
<%--												</div>--%>
<%--											</td>--%>
<%--										</tr>--%>

<%--									</c:forEach>--%>
<%--									</c:if>--%>
<%--									<c:if test="${QX.cha == 0 }">--%>
<%--										<tr>--%>
<%--											<td colspan="100" class="center">您无权查看</td>--%>
<%--										</tr>--%>
<%--									</c:if>--%>
<%--								</c:when>--%>
<%--								<c:otherwise>--%>
<%--									<tr class="main_info">--%>
<%--										<td colspan="100" class="center" >没有相关数据</td>--%>
<%--									</tr>--%>
<%--								</c:otherwise>--%>
<%--							</c:choose>--%>
							</tbody>
						</table>
						<small style="font-size: 10px;color: red;">*每种类型的积分设置只能存在一条设置</small>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<!-- <c:if test="${QX.add == 1 }">
									<a class="btn btn-sm btn-success" onclick="add();">一键设置</a>
									</c:if> -->
									<%--<c:if test="${varList.size() == 0 }">--%>
<%--										<c:if test="${QX.add == 1 }">--%>
											<a class="btn btn-xm btn-primary add">
												<i class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增
											</a>
<%--										</c:if> --%>
								<%--	</c:if>--%>
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
        <div class="tan">
			<div class="box">
				<header style="overflow: hidden">
					积分设置<div style="float: right;text-align: center;line-height:4rem;width: 4rem " class="clos"><i class="layui-icon layui-icon-close"></i></div>
				</header>
				<section>
					<div class="layui-form-item">
						<label class="layui-form-label">奖励类型:</label>
						<div class="layui-input-block">
							<select id="IsTurnOut" lay-verify="required">
								<option value="2" class="two">签到</option>
								<option value="5" class="five">上机积分</option>
							</select>
						</div>
					</div>
					<div class="text">
					<div></div>
					<p style="font-size: 10px;" >*活动主办方将在法律允许的范围内，对活动作出必要的说明和解释。如遇到不可抗拒因素，活动主办方拥有取消本次活动的权利</p>
					</div>
					<form class="layui-form mendian">
						<h3>可选门店</h3>
						<div class="layui-input-block xuanze">
						</div>
						<div class="gongneng">
							扫码开机积分需开通计费系统,未开通则无法使用此功能
							<span>去开通</span>
						</div>
					</form>
					<form class="layui-form qiandao">
					<div class="layui-form-item">
						<label class="layui-form-label">奖励积分:</label>
						<div class="layui-input-block">
							<input type="text" name="jifen" class="jifen" required  lay-verify="required" placeholder="请输入奖励积分" autocomplete="off" class="layui-input">
						</div>
					</div>
						<div class="layui-form-item fuxuan">
							<label class="layui-form-label fuxuan1">周末积分:</label>
							<div class="layui-input-block">
								<div class="zhoumo"><input type="checkbox" name="like[read]" title="周末" checked id="rdozhoumo"></div>
								<input type="number" name="WEEKEND_SEND" id="WEEKEND_SEND" min="0" maxlength="32" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
									   placeholder="这输入加倍时的奖励积分 必须为整数" title="加倍奖励积分" class="layui-input" style="width: 77%;display: inline-block;"/>
							</div>
						</div>
					</form>
					<div class="saoma">
						<div class="top">
							<div class="left">累计扫码</div>
							<ul class="right">
								<li>第一天</li>
								<li>第二天</li>
								<li>第三天</li>
								<li>第四天</li>
								<li>第五天</li>
								<li>第六天</li>
								<li>第七天</li>
							</ul>
						</div>
						<div class="bottom">
							<div class="left">奖励积分</div>
							<ul class="right">
								<li><input name="INTEGRAL_SEND1" id="INTEGRAL_SEND0" min="1" value="${days.day1}" class="INTEGRAL_SEND"></li>
								<li><input name="INTEGRAL_SEND2" id="INTEGRAL_SEND1" min="1" value="${days.day2}" class="INTEGRAL_SEND"></li>
								<li><input name="INTEGRAL_SEND3" id="INTEGRAL_SEND2" min="1" value="${days.day3}" class="INTEGRAL_SEND"></li>
								<li><input name="INTEGRAL_SEND4" id="INTEGRAL_SEND3" min="1" value="${days.day4}" class="INTEGRAL_SEND"></li>
								<li><input name="INTEGRAL_SEND1" id="INTEGRAL_SEND4" min="1" value="${days.day5}" class="INTEGRAL_SEND"></li>
								<li><input name="INTEGRAL_SEND6" id="INTEGRAL_SEND5" min="1" value="${days.day6}" class="INTEGRAL_SEND"></li>
								<li><input name="INTEGRAL_SEND7" id="INTEGRAL_SEND6" min="1" value="${days.day7}" class="INTEGRAL_SEND"></li>
							</ul>
						</div>


					</div>
				</section>
				<footer>
					<div class="sa">保存</div>
					<div class="cl clos">关闭</div>
				</footer>
			</div>
		</div>
		<div id="del">
			<i class="layui-icon layui-icon-close-fill dels" style="font-size:6rem!important;position:absolute;top:35%;left:55%"></i>
			<div class="layui-form" action="">
			<div class="layui-input-block choose">
				<div style="display: inline-block" class="Left">
					<input type="checkbox" name="like[write]" title="签到" class="twos">
				</div>
				<div style="display: inline-block" class="Right">
					<input type="checkbox" name="like[read]" title="上机积分" class="fives">
				</div>

			</div>
			</div>
			<footer style="position:absolute;top:60%;left:55%;left:50%;transform: translateX(-50%)">
			<button type="button" class="layui-btn layui-btn-radius shanchu">确认删除</button>
			<button type="button" class="layui-btn layui-btn-radius layui-btn-primary dels">取消</button>
			</footer>
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
	<script type="text/javascript">
        $(top.hangge());//关闭加载状态
		console.log(((${data}).data));
		var data = ((${data}).data);
		<%--console.log(data)--%>
		// for(var i=0;i<data.length;i++){
        //  $("tbody").append()
		// }
		//检索
        <%--$.post("<%=basePath%>/intintegral/list.do",function(data){--%>
        <%--    console.log(data);--%>
        <%--    var data=data;--%>
        <%--})--%>
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
            btn: ['保存','关闭'],
            btn1: function(index, layero){
               	//按钮的回调
                   var res = window["layui-layer-iframe" + index].baocun();
    
               },
               skin: 'demo-class',
               btnAlign: 'c',
               type: 2,
               title: '新增积分奖励',
               shadeClose: false,
               shade: 0.8,
               area: ['580px', '380px'],
               content:[ '<%=basePath%>intintegral/goAdd.do '],
           });
		}
		
		//删除
		<%--function del(Id){--%>
		<%--	layer.confirm('确定要删除吗?', {--%>
        <%--            btn: ['确定','取消'],--%>
        <%--        }, function(){--%>
        <%--        	$.ajax({--%>
		<%--				type: "POST",--%>
		<%--				url: '<%=basePath%>intintegral/delete.do',--%>
	    <%--				data: {INTINTEGRAL_ID:Id},--%>
		<%--				dataType:'json',--%>
		<%--				cache: false,--%>
		<%--				success: function(data){--%>
		<%--					layer.msg(data.message);--%>
		<%--					if(data.result == "true"){--%>
		<%--	                  	setTimeout(function () { --%>
		<%--	                  		location.reload();--%>
		<%--	                  	},500)--%>
		<%--		 			}--%>
		<%--				},--%>
		<%--				error:function(){--%>
		<%--	               layer.msg("系统繁忙，请稍后再试！");--%>
		<%--	               return false;--%>
		<%--	            }--%>
		<%--			});--%>
        <%--        }, function(){--%>
        <%--            return--%>
        <%--        });--%>
		<%--}--%>
		
		//修改
		<%--function edit(Id){--%>
		<%--	layer.open({--%>
        <%--       	btn: ['保存','关闭'],--%>
        <%--       	btn1: function(index, layero){--%>
        <%--       		//按钮的回调--%>
        <%--           	var res = window["layui-layer-iframe" + index].baocun();--%>
        <%--       	},--%>
        <%--       	skin: 'demo-class',--%>
        <%--       	btnAlign: 'c',--%>
        <%--       	type: 2,--%>
        <%--       	title: '修改积分奖励',--%>
        <%--       	shadeClose: false,--%>
        <%--       	shade: 0.8,--%>
        <%--       	area: ['580px', '380px'],--%>
        <%--       	content:[ '<%=basePath%>intintegral/goEdit?INTINTEGRAL_ID='+Id ],--%>
        <%--   	});--%>
		<%--}--%>
	</script>
	<script>
		// var ss = $(this).children('option:selected').val();
		// if (ss == "2") {
		// 	$(".saoma").css("display","none");
		// 	$(".qiandao").css("display","block");
		// } else if (ss == "5") {
		// 	$(".qiandao").css("display","none");
		// 	$(".saoma").css("display","block");
		// }
		//Demo
		var ind=2;
		layui.use('form', function() {
			var form = layui.form;
			form.render();
		});
		$(".zhoumo").on("click",function(){
			if($(this).children().prop("checked")){
				$("#WEEKEND_SEND").css("display","block")
			}else{
				$("#WEEKEND_SEND").css("display","none")
			}
		});
		$("body").on("click",".clos",function(){
			console.log(111)
			$(".tan").css("display","none");
			location.reload()
		})
		$("body").on("click",".dels",function(){
			console.log(111)
			$("#del").css("display","none");
			location.reload();
		})

	</script>

	<script src="<%=basePath%>js/intintegral_list.js"></script>
</body>
</html>