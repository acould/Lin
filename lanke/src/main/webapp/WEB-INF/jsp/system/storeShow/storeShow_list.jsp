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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
<title>Document</title>
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
							<form action="storeShow/list.do" method="post" name="Form"
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

											<div id="city">
												<lable class="lablepd leftpd">省:</lable>
												<select class="prov selectpd" id="province" name="province" style="vertical-align: middle; width: 120px;">
													<option value=""></option>
												</select>
												<lable class="lablepd leftpd">市:</lable>
												<select class="city selectpd" id="city" name="city" disabled="disabled" style="vertical-align: middle; width: 120px;">
													<option value=""></option>
												</select>
												<lable class="lablepd leftpd">区:</lable>
												<select class="dist selectpd" id="county" name="county" disabled="disabled" style="vertical-align: middle; width: 120px;">
													<option value=""></option>
												</select>
											</div>
										</td>
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
											<th class="center" style="max-width: 80px;">序号</th>
											<th class="left">门店名称</th>
											<th class="left">省-市-区</th>
											<th class="left">详细地址</th>
											<th class="left">联系电话</th>
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
															<td class='center' style="vertical-align: middle">
																<label class="pos-rel">
																	<input type='checkbox' name='ids' value="${var.STORE_ID}" class="ace" />
																 	<span class="lbl"></span>
																</label>
															</td>
															<td class='center' style="vertical-align: middle">${vs.index+1}</td>
															<td class='left' style="vertical-align: middle">${var.STORE_NAME}</td>
															<td class='left' style="vertical-align: middle">${var.PROVINCE} ${var.CITY} ${var.COUNTY}</td>
															<td class='left' style="vertical-align: middle">${var.ADDRESS}</td>
															<td class='left' style="vertical-align: middle">${var.TELEPHONE}</td>
															<td class="center" style="vertical-align: middle">
                                                                <c:if test="${QX.edit != 1 && QX.del != 1 }">
																	<span class="btn btn-default btn-sm"> 
																	    <i class="fa fa-lock" title="无权限" />
																	</span>
																</c:if>
																<div class="hidden-sm hidden-xs btn-group hidden-md">
																	<c:if test="${QX.edit == 1 }">
																		<a class="btn btn-sm btn-success" title="编辑"
																			onclick="store_Edit('${var.STORE_ID}');"> <i
																			class="layui-icon" style="padding-right: 2px">&#xe642;</i>
																		</a>
																	</c:if>
																	<a class="btn btn-sm btn-primary" title="会员智能分类"
																		onclick="store_EditTips('标签','${var.STORE_ID}');">
																		<i class="layui-icon" style="padding-right: 2px">&#xe66e;</i></a>
																	<c:if test="${QX.edit == 1 }">
																		<c:if test="${var.STATE == 1 }">
																			<a class="btn btn-sm btn-default" title="禁用"
																				onclick="storeShow_Statebt('${var.STORE_ID}','2','禁用');"> <i
																				class="layui-icon" style="padding-right: 2px">&#x1006;</i>
																			</a>
																		</c:if>
																	</c:if>
																	<c:if test="${QX.edit == 1 }">
																		<c:if test="${var.STATE == 2 }">
																			<a class="btn btn-sm btn-warning" title="启用"
																				onclick="storeShow_Statebt('${var.STORE_ID}','1','启用');"> <i
																				class="layui-icon" style="padding-right: 2px">&#xe605;</i>
																			</a>
																		</c:if>
																	</c:if>
																	<c:if test="${var.STATE == 3 }">
																		<a class="btn btn-sm btn-default" title="启用"
																			onclick="alert('该门店已被管理员禁用,请联系客服:4000965099');"> <i class="layui-icon"
																			style="padding-right: 2px">&#xe605;</i>
																		</a>
																	</c:if>
																	<a class="btn btn-sm" title="计费端连接情况" style="color: #1E9FFF;"
																	   onclick="client_detail('${var.STORE_ID}','${var.STORE_NAME}');">
                                                                        计费端连接情况
																	</a>
																	<a class="btn btn-sm" style="color: #1E9FFF;" title="请求桌面二维码"
																	   onclick="setQrParam('${var.STORE_ID}');">
																		请求桌面二维码
																	</a>
																</div>
																	<div class="hidden-lg">
																		<div class="inline pos-rel lk-ul-hoverShow">
																			<button class="btn btn-minier btn-primary dropdown-toggle"
																				data-toggle="dropdown" data-position="auto">
																				<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																			</button>
																			<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																				<c:if test="${QX.edit == 1 }">
																					<li>
																						<a style="cursor: pointer;" onclick="store_Edit('${var.STORE_ID}');"
																							class="tooltip-success" data-rel="tooltip" title="编辑">
																							<span class="text-success lk-text">
																								<i class="layui-icon" >&#xe642;</i>
																							</span>
																						</a>
																					</li>
																				</c:if>
																				<li>
																					<a style="cursor: pointer;" onclick="store_EditTips('标签','${var.STORE_ID}');"
																						class="tooltip-success" data-rel="tooltip" title="标签">
																						<span class="text-primary lk-text">
																							<i class="layui-icon" >&#xe66e;</i>
																						</span>
																					</a>
																				</li>
																				<c:if test="${QX.edit == 1 }">
																					<c:if test="${var.STATE == 1 }">
																						<li>
																							<a style="cursor: pointer;" onclick="storeShow_Statebt('${var.STORE_ID}','2','禁用');"
																								class="tooltip-success" data-rel="tooltip" title="禁用">
																								<span class="text-default lk-text">
																									<i class="layui-icon" >&#x1006;</i>
																								</span>
																							</a>
																						</li>
																					</c:if>
																				</c:if>
																				<c:if test="${QX.edit == 1 }">
																					<c:if test="${var.STATE == 2 }">
																						<li>
																							<a style="cursor: pointer;" onclick="storeShow_Statebt('${var.STORE_ID}','1','启用');"
																								class="tooltip-success" data-rel="tooltip" title="启用">
																								<span class="text-warning lk-text">
																									<i class="layui-icon" >&#xe605;</i>
																								</span>
																							</a>
																						</li>
																					</c:if>
																				</c:if>
																				<c:if test="${var.STATE == 3 }">
																					<li>
																						<a style="cursor: pointer;" onclick="alert('该门店已被管理员禁用,请联系客服:4000965099');"
																							class="tooltip-success" data-rel="tooltip" title="启用">
																							<span class="text-warning lk-text">
																								<i class="layui-icon" >&#xe605;</i>
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
											<c:if test="${QX.add == 1 }">
													<a class="btn btn-xm btn-primary" onclick="storeShow_Add();"> <i
														class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增
													</a>
												</c:if></td>
											<td style="vertical-align: top;">
												<div class="pagination"
													style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
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

	<!-- Modal 授权提示-->
	<div class="modal-body"
		style="padding: 40px; display: none;">
		<div style="display: inline-block;">
			<p style="font-size: 18px; color: #333; margin-bottom: 5px">每次新增门店后一定要去进行门店计费系统绑定</p>
			<p style="font-size: 18px; color: #333;">否则的话，有许多功能是无法使用的哦。</p>
			<p style="margin-top: 54px">
				<span style="font-size: 16px; color: #fff; background-color: #d2d2d2; padding: 8px 34px; border-radius: 50px; cursor: pointer; margin-right: 14px;"
					id="motai">我知道了</span>
				<span id="ud" style="font-size: 16px; color: #fff; background-color: #e44547; padding: 8px 34px; border-radius: 50px; cursor: pointer;">去绑定计费系统</span>
			</p>
		</div>
	</div>

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<!-- 城市下拉框 -->
	<script src="<%=basePath%>js/jquery.cityselect.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.js"></script>
	<!-- 业务JS -->
	<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-account.js"></script>
	<script type="text/javascript">
        //调用城市三级联动
        citySel('${pd.province}','${pd.county}','${pd.city}')
	</script>
    <!-- 业务js -->
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-clientList.js"></script>
</body>
</html>