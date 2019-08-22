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
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css"
	media="all">
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css"
	media="all">
<title>代理商管理后台</title>
<style>
body {
	background: #f3f3f3;
	min-width: 1150px;
	overflow: auto;
	padding: 12px;
}
.lanke-userbox {
	width: 100%;
	margin-right:0;
	background-color: #fff;
	margin-bottom: 15px;
}
.user-collapse {width: 50%;float: right;padding: 20px 0;}
.user-header {width: 49%;display: inline-block;text-align: center;}
.layui-form-label{width:100px;}
</style>
</head>
<body class="scroll">
	<div class="lanke-userbox">
		<blockquote class="layui-elem-quote">用户基础信息列表</blockquote>
		<div class="user-header">
			<div class="lk-user-img">
				<div class="user-img">
					<img src="static/ace/avatars/user.jpg" width="100%"/> 
				</div>
				<p class="user-name">${pd.USERNAME}</p>
			</div>
			<a class="btn btn-sm btn-success" title="查看代理商信息" href="<%=basePath%>agentAccount/show" target="_blank">
			 	<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>查看代理商信息
			</a>
		</div>
		<div class=" user-collapse" lay-filter="color">
			<div class="layui-colla-item" id="password" style="border: none">
				<p class="layui-colla-title user-colla-title">
					<i class="user-icon icon-userName"></i> <label
						class="layui-form-label">用户名：</label> <a id="USERNAME">${pd.USERNAME}</a>
					<input type="hidden" id="USER_ID" value="${pd.USER_ID}"> <span
						class="">修改密码</span>
				</p>
			</div>

			<div class="hr-line-dashed"></div>
			<div class="layui-colla-item" id="phone" style="border: none">
				<p class="layui-colla-title user-colla-title">
					<i class="user-icon icon-phone"></i> <label
						class="layui-form-label">手机号码：</label> <a id="newPhone">${pd.PHONE}</a>
					<span class="">更换手机</span>
				</p>
			</div>

			<div class="hr-line-dashed"></div>
			<div class="layui-colla-item" style="border: none">
				<p class="layui-colla-title user-colla-title">
					<i class="user-icon icon-userState"></i> <label
						class="layui-form-label">帐户状态：</label> <a>代理商</a>
				</p>
			</div>

			<div class="hr-line-dashed"></div>
			<div class="layui-colla-item" style="border: none">
				<p class="layui-colla-title user-colla-title">
					<i class="user-icon icon-time"></i> <label class="layui-form-label">注册时间：</label>
					<a>${pd.createtime}</a>
				</p>
			</div>
		</div>
	</div>
	<div class="">
		<blockquote class="layui-elem-quote">代理门店</blockquote>
		<div class="lanke-storeTab" style="padding-top:0;min-height: calc(100vh - 365px);">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<!-- 检索  -->
						<!-- 检索  -->
						<form action="agentAccount/list.do" method="post" name="Form"
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
									<td style="vertical-align: middle; padding-left: 5px;">

										<div id="city">
											<lable class="lablepd leftpd">省:</lable>
											<select class="prov selectpd" id="province" name="province"
												style="vertical-align: middle; width: 100px;">
												<option value=""></option>
											</select>
											<lable class="lablepd leftpd">市:</lable>
											<select class="city selectpd" id="city" name="city"
												disabled="disabled"
												style="vertical-align: middle; width: 100px;">
												<option value=""></option>
											</select>
											<lable class="lablepd leftpd">区:</lable>
											<select class="dist selectpd" id="county" name="county"
												disabled="disabled"
												style="vertical-align: middle; width: 100px;">
												<option value=""></option>
											</select>
										</div>
									</td>

									<td style="vertical-align: middle; padding-left: 15px;"><lable
											class="lablepd">计费系统绑定:</lable> <select
										class="chosen-select form-control lanke-sel" name="STATE"
										id="STATE" data-placeholder="请选择">
											<option value=""></option>
											<option value="">全部</option>
											<option value="0"
												<c:if test="${pd.STATE == '0'}">selected="selected"</c:if>>未绑定</option>
											<option value="1"
												<c:if test="${pd.STATE == '1'}">selected="selected"</c:if>>已绑定</option>
											<option value="2"
												<c:if test="${pd.STATE == '2'}">selected="selected"</c:if>>加V审核中</option>
											<option value="3"
												<c:if test="${pd.STATE == '3'}">selected="selected"</c:if>>加V未通过</option>
									</select></td>

									<td style="vertical-align: middle; padding-left: 15px;"><lable
											class="lablepd">银联支付绑定:</lable> <select
										class="chosen-select form-control lanke-sel" name="STATUS"
										id="STATUS" data-placeholder="请选择">
											<option value=""></option>
											<option value="">全部</option>
											<option value="0"
												<c:if test="${pd.STATUS == '0'}">selected="selected"</c:if>>未开通</option>
											<option value="1"
												<c:if test="${pd.STATUS == '1'}">selected="selected"</c:if>>已开通</option>
											<option value="2"
												<c:if test="${pd.STATUS == '2'}">selected="selected"</c:if>>等待快递</option>
									</select></td>

                                    <td style="vertical-align: middle; padding-left: 15px;"><lable class="lablepd">嘉联支付绑定:</lable>
                                        <select class="chosen-select form-control lanke-sel" name="JL_STATUS"
                                            id="JL_STATUS" data-placeholder="请选择">
                                        <option value=""></option>
                                        <option value="">全部</option>
                                        <option value="1" <c:if test="${pd.JL_STATUS == '1'}">selected="selected"</c:if>>已开通</option>
                                        <option value="2" <c:if test="${pd.JL_STATUS == '2'}">selected="selected"</c:if>>未开通</option>
                                        <option value="3" <c:if test="${pd.JL_STATUS == '3'}">selected="selected"</c:if>>审核中</option>
                                        <option value="4" <c:if test="${pd.JL_STATUS == '4'}">selected="selected"</c:if>>未通过</option>
                                    </select></td>

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
										<th class="center">序号</th>
										<th class="left">代理的门店</th>
										<th class="left">门店ID</th>
										<th class="left">所在省份</th>
										<th class="left">所在城市</th>
										<th class="left">所在区域</th>
										<th class="left">详情地址</th>
										<th class="left">门店电话</th>
										<th class="left">计费系统绑定</th>
										<th class="left">银联支付开通</th>
										<th class="left">嘉联支付开通</th>
									</tr>
								</thead>

								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty storeList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${storeList}" var="var" varStatus="vs">
													<tr>
														<td class='center'
															style="width: 80px; vertical-align: middle;">${vs.index+1}</td>
														<td class='left' style="vertical-align: middle">${var.STORE_NAME}</td>
														<td class='left' style="vertical-align: middle">${var.STORE_ID}</td>
														<td class='left' style="vertical-align: middle">${var.PROVINCE}</td>
														<td class='left' style="vertical-align: middle">${var.CITY}</td>
														<td class='left' style="vertical-align: middle">${var.COUNTY}</td>
														<td class='left' style="vertical-align: middle">${var.ADDRESS}</td>
														<td class='left' style="vertical-align: middle">${var.TELEPHONE}</td>
														<td class='left' style="vertical-align: middle"><c:if
																test="${var.STATE==0}">未绑定</c:if> <c:if
																test="${var.STATE==1}">已绑定</c:if> <c:if
																test="${var.STATE==2}">审核中</c:if> <c:if
																test="${var.STATE==3}">未通过</c:if></td>
														<td class='left' style="vertical-align: middle"><c:if
																test="${var.STATUS==0}">未开通</c:if> <c:if
																test="${var.STATUS==1}">已开通</c:if> <c:if
																test="${var.STATUS==2}">等待快递</c:if></td>
														<td class='left' style="vertical-align: middle"><c:if
																test="${var.JL_STATUS==1}">已开通</c:if> <c:if
																test="${var.JL_STATUS==2}">未开通</c:if> <c:if
																test="${var.JL_STATUS==3}">审核中</c:if> <c:if
																test="${var.JL_STATUS==4}">未通过</c:if></td>
													</tr>
												</c:forEach>
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
		<div class="layui-colla-content" id="lanke-phone" style="">
			<div style="padding: 20px 0">
				<form action="" class="layui-form">
					<div class="layui-form-item" style="margin-bottom: 0">
						<div class="layui-inline">
							<label class="layui-form-label">新手机号：</label>
							<div class="layui-input-inline">
								<input type="text" name="newphone" id="newphone"
									autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>

					<div class="layui-form-item" style="margin-bottom: 0">
						<div class="layui-inline">
							<label class="layui-form-label">验证码：</label>
							<div class="layui-input-inline">
								<input type="tel" name="code" id="code"
									lay-verify="required|phone" autocomplete="off"
									class="layui-input">
							</div>
							<span class="layui-btn layui-btn-primary user-btn-yzm"
								id="phonebtn" onclick="return updatephone()">获取验证码</span>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="layui-colla-content" id="lanke-password" style="">
			<div style="padding: 20px 0">

				<form action="storePrivilege/toEdit" id="toShowFrom"
					name="toShowFrom" target="_blank" method="post">
					<input type="hidden" id="store_id" name="STORE_ID" value="">
					<input type="hidden" id="store_name" name="STORE_NAME" value="">
					<input type="hidden" id="state1" name="STATE" value="">
				</form>

				<form action="" class="layui-form">
					<div class="layui-form-item" style="margin-bottom: 0">
						<div class="layui-inline">
							<label class="layui-form-label">手机号：</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input"
									value="${pd.PHONE}" disabled="disabled">
							</div>
						</div>
					</div>
					<div class="layui-form-item" style="margin-bottom: 0">
						<div class="layui-inline">
							<label class="layui-form-label">旧密码：</label>
							<div class="layui-input-inline">
								<input type="password" name="oldPw" id="oldPassword"
									autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item" style="margin-bottom: 0">
						<div class="layui-inline">
							<label class="layui-form-label">新密码：</label>
							<div class="layui-input-inline">
								<input type="password" name="newPw" id="newPassword"
									autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item" style="margin-bottom: 0">
						<div class="layui-inline">
							<label class="layui-form-label">验证码：</label>
							<div class="layui-input-inline">
								<input type="tel" name="codephone" id="codephone"
									lay-verify="required|phone" autocomplete="off"
									class="layui-input">
							</div>
							<span class="layui-btn layui-btn-primary user-btn-yzm"
								id="codebtn" onclick="return updatepassword()">获取验证码</span>
						</div>
					</div>
				</form>
			</div>
		</div>
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script src="<%=basePath%>newStyle/js/lk-agentAccount.js"></script>
<!-- 城市下拉框 -->
<script type="text/javascript" src="js/jquery.cityselect.js"></script>
<script type="text/javascript">
	//调用城市三级联动
	citySel('${pd.province}', '${pd.county}', '${pd.city}')
</script>
</html>