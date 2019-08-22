<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
<title>网吧管理后台</title>
<style>
body {background: #f3f3f3;min-width: 1160px;overflow: auto;padding: 12px;}
.layui-form-label {
    padding: 9px 14px;
    width: auto;
    text-align: left;
}
.layui-layer-nobg {box-shadow:none!important;}
.layui-form-checkbox[lay-skin=primary] {
    margin: 6px 0 2px 2px;
}
</style>
</head>
<body class="scroll">
	<div class="introduce-box layui-clear">
    	<div class="lk-introduce-content">
      		  <img src="<%=basePath%>newStyle/images/payManager.png" width="540px">
      	</div>
      	<div class="lk-introduce-content">
      		 <h1 class="lk-introduce-title">揽客充值介绍</h1>
      		 <p>1)、会员随时随地可充值，网费自动充入会员卡</p>
      		 <p>2)、充值金额直接到网吧指定银行账户，高度保障资金安全</p>
      		 <p>3)、财务报表清晰简明，对账方便快捷</p>
      		 <p>4)、网吧自由设置充送规则，多门店可批量设置</p>
      		 <div class="lk-help-btn">
      		 	<a href="<%=basePath%>register/pay_set.do" target="_blank">如何设置</a>
      		 	<a href="<%=basePath%>register/user_pay.do" target="_blank">会员如何充值</a>
      		 	<a href="<%=basePath%>register/account_check.do" target="_blank">如何对账</a>
      		 </div>
      	</div>
      	<div class="lk-introduce-content">
      		<a class="btn btn-xm btn-primary" onclick="go_rechargeEdit('<%=basePath%>')">
      		 	<i class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增充值规则
			</a>
      	</div>
	</div>
		<div class="introduce-box introduce-formList">
			<form action="rechargeRule/list.do" method="post" name="Form" id="Form" class="layui-form">
				 <table>
					<tr>
						<td>
							<div class="nav-search">
								<span class="input-icon">
								    <input type="text" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords"
									value="${pd.keywords }" placeholder="这里输入关键词" /> 
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</div>
						</td>
						<td style="vertical-align: middle; padding-left: 15px;">
						     <div class="layui-inline">
							      <label class="layui-form-label">门店：</label>
							      <div class="layui-input-inline">
							        <select name="store_id" lay-search="" lay-filter="filter">
										<option value="">选择或搜索</option>
										<c:forEach items="${storeList}" var="var">
											<option value="${var.STORE_ID}" <c:if test="${pd.store_id == var.STORE_ID}">selected=selected</c:if>>${var.STORE_NAME}</option>	
										</c:forEach>
							        </select>
							      </div>
						     </div>
						</td>
						<td style="vertical-align: middle; padding-left: 15px">
							<a class="btn btn-success btn-sm" onclick="tosearch();" title="检索">
							 	<i class="layui-icon" style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索
							</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="introduce-box" style="min-height: calc(100vh - 350px);">
			<!-- 检索  -->
			<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: 5px;">
				<thead>
					<tr>
						<th class="center" style="width: 35px;">
							<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center" style="width: 80px;">序号</th>
						<th class="center">门店名称</th>
						<th class="center">充值规则数量</th>
						<th class="center">充值规则详情</th>
						<th class="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${varList}" var="var" varStatus="vs"> 
					 <tr>
					 	<td class='center' style="vertical-align: middle">
							<label class="pos-rel">
								<input type='checkbox' name='ids' value="${var.id}" class="ace" /> 
								<span class="lbl"></span>
							</label>
						</td>
						<td class="center">${vs.index+1}</td>
						<td class="center">${var.store_name}</td>
						<td class="center">${var.number}</td>
						<td class="center"><a class="btn" onclick="showDetail('<%=basePath%>','${var.store_id}')"><font color="#41a7f0">详情</font></a></td>
						<td class="center" style="vertical-align: middle">
							<div class="hidden-sm hidden-xs btn-group">
								<a class="btn btn-sm btn-success" title="编辑" onclick="editDetail('<%=basePath%>','${var.store_id}')"> 
									<i class="layui-icon" style="padding-right: 2px">&#xe642;</i>编辑
								</a>
								<a class='btn btn-mini btn-danger' title="删除" onclick="delRules('<%=basePath%>','${var.store_id}')">
                                    <i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
                                </a>
							</div>
							<div class="hidden-md hidden-lg">
								<div class="inline pos-rel lk-ul-hoverShow">
									<button class="btn btn-minier btn-primary dropdown-toggle"
										data-toggle="dropdown" data-position="auto">
										<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
									</button>
									<ul  class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
										<li>
											<a style="cursor: pointer;" class="tooltip-success" data-rel="tooltip" title="编辑">
												<span class="text-success lk-text">
													<i class="layui-icon" >&#xe642;</i>
												</span>
											</a>
										</li>
										<li>
											<a style="cursor: pointer;" class="tooltip-success" data-rel="tooltip" title="删除">
												<span class="text-danger lk-text">
													<i class="layui-icon" >&#xe640;</i>
												</span>
											</a>
										</li>
									</ul>
								</div>
							</div>
						</td>
					 </tr>
					</c:forEach>
					<c:if test="${empty varList}">
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<div class="page-header position-relative">
				<table style="width: 100%;">
					<tr>
						<td style="vertical-align: top;">
							
						</td>
						<td style="vertical-align: top;">
							<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	<form target="_blank" method="post" id="edit_form">
		<input type="hidden" id="store_id" name="store_id">
	</form>
	<form class="layui-form" id="store_form" style="display:none" target="_blank" method="post">
		<div class="lk-chooseStore-box" style="padding-top: 0;">
			<div class="choosable-box" id="openBox">
				<h2>可选门店</h2>
				<div class="choose-store" id="openStoreList">
					<input type="checkbox"  title="全选" lay-skin="primary" checked lay-filter="tufure" id="allChecked"> 
				</div>
			</div>
			<div class="choosable-box" id="addedBox">
				<h2>已创建规则门店</h2>
				<div class="choose-store" id="addedStoreList">
					<p class="openUp-action">以上门店已创建充值规则，无需重新新建</p>
				</div>
			</div>
			<div class="choosable-box" id="notOpenBox">
				<h2>不可选门店</h2>
				<div class="choose-store" id="notOpenStoreList">
					<p class="openUp-action">以上门店因未开通计费系统或者未开通在线支付，无法实现<br>充值功能
						<span onclick="siMenu('z234','lm154','消息通知','newerGuide/list.do')">去开通</span>
					</p> 
				</div>
			</div>
		</div>
		<input type="hidden" name="nameList" id="nameList">
	</form>
	
	
	
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script src="<%=basePath%>newStyle/js/lk-pay.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/myjs/head.js"></script>
<script>
	
</script>
</html>