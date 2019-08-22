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
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
<title>网吧管理后台</title>
<style>
body {background: #f3f3f3;min-width: 1150px;overflow: auto;padding: 12px;}
.layui-form-label {width:110px;}
.zhuyi {color:red;font-size:13px;padding-left:10px;font-weight:500;float: right;}
.nav-search .nav-search-input,.form-control, select  {border-color:#e1e1e1!important;}

</style>
</head>
<body class="scroll">
	<div class="layui-row layui-col-space15" style="margin-bottom: 7px">
		<div class="layui-col-md6 layui-col-xs6 layui-col-sm6">
			<div class="lk-bg-wihte lk-account-box">
				<div class="lk-account-headerImg"><img src="<%=basePath%>newStyle/images/lk-system-icon.png" alt="" width="52px"></div>
				<div class="lk-account-headerText">
					<h4>打通计费系统，会员精准营销</h4>
					<p>与PubwinOL全面对接，解锁更多营销工具，盘活会员资源</p>
				</div>
                <a href="<%=basePath%>accountSettings/toAddv.do" target="_blank">查看特权</a>
				<c:if test="${QX.edit == 1}"></c:if>
				<%--<c:if test="${QX.edit != 1}"><a href="javascript:void(0)" onclick="remind('您没有权限哦')">查看特权</a></c:if>--%>
			</div>
		</div>
		<div class="layui-col-md6 layui-col-xs6 layui-col-sm6">
			<div class="lk-bg-wihte lk-account-box">
				<div class="lk-account-headerImg">
					<img src="<%=basePath%>newStyle/images/lk-recharge-icon.png" alt="" width="52px">
				</div>
				<div class="lk-account-headerText">
					<h4>开通银行卡账户，会员线上充值</h4>
					<p>会员充值省时又便利，后台查看流水账单，清晰又方便</p>
				</div>
                <a href="<%=basePath%>accountSettings/toAddv.do" target="_blank">查看特权</a>
				<c:if test="${QX.edit == 1}"></c:if>
				<!--<c:if test="${QX.edit != 1}"><a href="javascript:void(0)" onclick="remind('您没有权限哦')">查看特权</a></c:if>-->
			</div>
		</div>
	</div>

	<div class="lanke-userbox">
		<blockquote class="layui-elem-quote">用户基础信息列表</blockquote>
		<div class=" user-collapse" lay-filter="color" style="min-height: calc(100vh - 172px);background-color: #fff">
			<div class="lk-user-img">
				<div class="user-img">
					<img src="static/ace/avatars/user.jpg" width="100%"/> 
				</div>
				<p class="user-name">${pd.NAME}</p>
			</div>
			<div class="user-msg">
				<div class="layui-colla-item" id="password" style="border: none">
					<p class="layui-colla-title user-colla-title">
						<i class="user-icon icon-userName"></i> <label
							class="layui-form-label">用户名：</label> <a id="USERNAME">${pd.USERNAME}</a>
						<input type="hidden" id="USER_ID" value="${pd.USER_ID}">  <span class="">修改密码</span>
					</p>
				</div>
				<div class="hr-line-dashed"></div>
				<%--<div class="layui-colla-item" style="border: none">--%>
					<%--<p class="layui-colla-title user-colla-title">--%>
						<%--<i class="user-icon icon-userState"></i> <label--%>
							<%--class="layui-form-label">帐户状态：</label> <a>轻营销版用户</a> --%>
							<%--<c:if test="${QX.edit == 1}">--%>
							<%--<span class=""><a href="<%=basePath%>accountSettings/toAddv.do?" target="_blank">查看套餐</a></span>--%>
							<%--</c:if>--%>
							<%--<c:if test="${QX.edit != 1}">--%>
							<%--<span class="" onclick="remind('您没有权限哦')" style="color:#999">查看套餐</span>--%>
							<%--</c:if>--%>
					<%--</p>--%>
				<%--</div>--%>
	
				<div class="hr-line-dashed"></div>
				<div class="layui-colla-item" id="phone" style="border: none">
					<p class="layui-colla-title user-colla-title">
						<i class="user-icon icon-phone"></i> <label class="layui-form-label">手机号码：</label> <a id="newPhone">${pd.PHONE}</a>
						<span class="">更换手机</span>
					</p>
				</div>
	
				<div class="hr-line-dashed"></div>
				<div class="layui-colla-item" id="email" style="border: none">
					<p class="layui-colla-title user-colla-title">
						<i class="user-icon icon-email"></i> <label
							class="layui-form-label">邮箱账号：</label> <a id="newEmail">${pd.EMAIL}</a>
						<span class="">修改邮箱</span>
					</p>
				</div>
	
				<div class="hr-line-dashed"></div>
				<div class="layui-colla-item" style="border: none">
					<p class="layui-colla-title user-colla-title">
						<i class="user-icon icon-time"></i> <label class="layui-form-label">认证时间：</label>
						<a>${pd.auth_time}</a>
					</p>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="layui-colla-item" style="border: none">
					<p class="layui-colla-title user-colla-title">
						<i class="user-icon icon-weixin"></i> <label
							class="layui-form-label">授权公众号：</label> <a>${pd.INTENET_NAME}</a>
					</p>
				</div>
			</div>
		</div>

	</div>
	<div class="lanke-storeList">
		<blockquote class="layui-elem-quote" style="border-bottom: 1px solid #f3f3f3;">门店特权列表
			<span class="zhuyi">注意：如果您还不是PubwinOL6.3.30.1及以上用户，请联系客服升级,客服电话:4000965099</span>
		</blockquote>
		<div class="lanke-storeTab">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<!-- 检索  -->
						<form action="accountSettings/list.do" method="post" name="Form" id="Form">
								<div class="nav-search search-box">
									<span class="input-icon">
									 <input type="text"placeholder="请输入门店名称" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords"
										value="${pd.keywords }" placeholder="这里输入关键词" />
										<i class="ace-icon fa fa-search nav-search-icon"></i>
									</span>
								</div>
								<div class="search-box">
									<lable class="lablepd">计费系统:</lable>
									<select class="chosen-select form-control lanke-sel" name="STATE" id="STATE" data-placeholder="请选择">
										<option value=""></option>
										<option value="">全部</option>
										<option value="0" <c:if test="${pd.STATE == '0'}">selected="selected"</c:if>>未绑定</option>
										<option value="1" <c:if test="${pd.STATE == '1'}">selected="selected"</c:if>>已绑定</option>
										<option value="2" <c:if test="${pd.STATE == '2'}">selected="selected"</c:if>>审核中</option>
										<option value="3" <c:if test="${pd.STATE == '3'}">selected="selected"</c:if>>未通过</option>
									</select>
								</div>
							<%--银联支付的--%>
								<%--<div class="search-box">
									<lable class="lablepd">在线支付:</lable>
									<select class="chosen-select form-control lanke-sel" name="YH_STATUS" id="YH_STATUS" data-placeholder="请选择">
										<option value=""></option>
										<option value="">全部</option>
										<option value="6" <c:if test="${pd.YH_STATUS == '6'}">selected="selected"</c:if>>未开通</option>
										<option value="0" <c:if test="${pd.YH_STATUS == '0'}">selected="selected"</c:if>>资料未通过</option>
										<option value="2" <c:if test="${pd.YH_STATUS == '2'}">selected="selected"</c:if>>资料审核中</option>
										<option value="3" <c:if test="${pd.YH_STATUS == '3'}">selected="selected"</c:if>>资料已通过</option>
										<option value="4" <c:if test="${pd.YH_STATUS == '4'}">selected="selected"</c:if>>待开通</option>
										<option value="5" <c:if test="${pd.YH_STATUS == '5'}">selected="selected"</c:if>>已开通</option>
									</select>
								</div>--%>
							<%--嘉联支付的--%>
								<div class="search-box">
									<lable class="lablepd">在线支付:</lable>
									<select class="chosen-select form-control lanke-sel" name="JL_STATUS" id="JL_STATUS" data-placeholder="请选择">
										<option value=""></option>
										<option value="">全部</option>
										<option value="1" <c:if test="${pd.JL_STATUS == 1}">selected="selected"</c:if>>已开通</option>
										<option value="2" <c:if test="${pd.JL_STATUS == 2}">selected="selected"</c:if>>待开通</option>
										<option value="3" <c:if test="${pd.JL_STATUS == 3}">selected="selected"</c:if>>审核中</option>
										<option value="4" <c:if test="${pd.JL_STATUS == 4}">selected="selected"</c:if>>未通过</option>
									</select>
								</div>


								<%--<div class="search-box">
									<lable class="lablepd">网吧一码通:</lable>
									<select class="chosen-select form-control lanke-sel" name="" id="" data-placeholder="请选择">
										<option value=""></option>
										<option value="">全部</option>
										<option value="6" <c:if test="${pd.YH_STATUS == '6'}">selected="selected"</c:if>>未开通</option>
										<option value="5" <c:if test="${pd.YH_STATUS == '5'}">selected="selected"</c:if>>已开通</option>
									</select>
								</div>--%>
								<div class="search-box">
									<a class="btn btn-success btn-sm" onclick="tosearch();" title="检索"> <i class="layui-icon" style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索</a>
								</div>
							<!-- 检索  -->

							<table id="simple-table" class="table table-striped table-bordered table-hover lanke-tableTextBtn"style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">序号</th>
										<th class="center">门店名称</th>
										<th class="center">计费系统绑定</th>
										<th class="center">在线支付开通</th>
										<th class="center">网吧一码通</th>
									</tr>
								</thead>
								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:forEach items="${varList}" var="var" varStatus="vs">
												<tr>
													<td class='center' style="vertical-align: middle;">${vs.index+1}</td>
													<td class='center' style="vertical-align: middle;">${var.STORE_NAME}</td>
													<td class='center' id="state" style="vertical-align: middle;">
														<c:if test="${var.STATE=='0'}">
															<a class="btn btn-sm btn-primary" onclick="flow('${var.STORE_ID}','${var.STORE_NAME}','0')">去绑定</a>
														</c:if>
														<c:if test="${var.STATE=='1'}">
															<a class="btn btn-sm btn-success"
																onclick="toShow('${var.STORE_ID}','${var.STORE_NAME}','1')">已绑定
															</a>
														</c:if>
														<c:if test="${var.STATE=='2'}">
															<a class="btn btn-sm btn-default" href="javascript:void(0);">审核中</a>
															</c:if> 
														<c:if test="${var.STATE=='3'}">
															<a class="btn btn-sm btn-danger"
																onclick="flow('${var.STORE_ID}','${var.STORE_NAME}','2')">未通过
															</a>
														</c:if>
													</td>
												<!-- 银联绑定情况 -->
													<%--<td class='center padding-lr' style="vertical-align: middle;">
														<c:if test="${empty var.YH_STATE}">
															<a class="btn btn-sm btn-primary" onclick="goPayOpen('${var.YH_ID}','${var.STORE_ID}')" >去开通</a>
														</c:if>
														<c:if test="${var.YH_STATE=='0'}">
															<a class="btn btn-sm btn-danger" onclick="goPayOpen('${var.YH_ID}','${var.STORE_ID}')">未通过
															</a>
														</c:if> 
														<c:if test="${var.YH_STATE=='2'}">
															<a class="btn btn-sm btn-default" href="javascript:void(0);">审核中</a>
														</c:if>
														<c:if test="${var.YH_STATE=='1'}">
															<c:if test="${var.YH_STATUS=='0'}">
																<a class="btn btn-sm btn-success" onclick="goPayOpen('${var.YH_ID}','${var.STORE_ID}')">待开通
																</a>
															</c:if> 
															<c:if test="${var.YH_STATUS=='1'}">
																<a class="btn btn-sm btn-success" onclick="goPayOpen('${var.YH_ID}','${var.STORE_ID}')">已开通
																</a>
															</c:if> 
															<c:if test="${var.YH_STATUS=='2'}">
																<a class="btn btn-sm btn-warning" onclick="goPayOpen('${var.YH_ID}','${var.STORE_ID}')"" target="_blank">填快递
																</a>	
															</c:if> 
														</c:if> 
													</td>--%>
                                                    <%--嘉联支付--%>
                                                    <td class='center padding-lr' style="vertical-align: middle;">
														<c:if test="${empty var.JL_STATUS or var.JL_STATUS == 2}">
															<a class="btn btn-sm btn-primary" onclick="goJLOpen('${var.JL_ID}','${var.STORE_ID}')" >去开通</a>
														</c:if>
														<c:if test="${var.JL_STATUS == 1}">
															<a class="btn btn-sm btn-danger" onclick="goJLOpen('${var.JL_ID}','${var.STORE_ID}')">已开通</a>
														</c:if>
														<c:if test="${var.JL_STATUS == 3}">
															<a class="btn btn-sm btn-default">审核中</a>
														</c:if>
                                                        <c:if test="${var.JL_STATUS == 4}">
                                                            <a class="btn btn-sm btn-danger" onclick="goJLOpen('${var.JL_ID}','${var.STORE_ID}')">未通过
                                                            </a>
                                                        </c:if>
													</td>

                                                    <%--一码通列表--%>
													<td class='center padding-lr' style="vertical-align: middle;">
														<c:if test="${var.QR_STATE == null}">
															<a class="btn btn-sm btn-primary" onclick="goQrList('${var.STORE_ID}')">去开通</a>
														</c:if>
														<c:if test="${var.QR_STATE != null}">
															<a class="btn btn-sm btn-success" onclick="goQrList('${var.STORE_ID}')">去编辑</a>
														</c:if>
													</td>
												</tr>
											</c:forEach>
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
		<form id="YH_FORM" method="post" target="_blank">
			<input type="hidden" id="idd" name="idd">
			<input type="hidden" id="store_idd" name="store_idd">
		</form>
		<div class="layui-colla-content" id="lanke-email" style="">
			<div style="padding: 20px 0">
				<form action="" id="newEmailFrom" class="layui-form">
					<div class="layui-form-item" style="margin-bottom:0">
						<div class="layui-inline">
							<label class="layui-form-label">新邮箱：</label>
							<div class="layui-input-inline">
								<input type="text" name="newEmail" id="newemail" autocomplete="off"
									class="layui-input">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="layui-colla-content" id="lanke-phone" style="">
			<div style="padding: 20px 0">
				<form action="" class="layui-form">
					<div class="layui-form-item" style="margin-bottom:0">
						<div class="layui-inline">
							<label class="layui-form-label">新手机号：</label>
							<div class="layui-input-inline">
								<input type="text" name="newphone" id="newphone"
									autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>

					<div class="layui-form-item" style="margin-bottom:0">
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
			
			<form action="storePrivilege/toEdit" id="toShowFrom" name="toShowFrom" target="_blank" method="post">
			<input type="hidden" id="store_id" name="STORE_ID" value="">
			<input type="hidden" id="store_name" name="STORE_NAME" value="">
			<input type="hidden" id="state1" name="STATE" value="">
			</form>
			
				<form action="" class="layui-form">
					<div class="layui-form-item" style="margin-bottom:0">
						<div class="layui-inline">
							<label class="layui-form-label">手机号：</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" value="${pd.PHONE}" disabled="disabled">
							</div>
						</div>
					</div>
					<div class="layui-form-item" style="margin-bottom:0">
						<div class="layui-inline">
							<label class="layui-form-label">旧密码：</label>
							<div class="layui-input-inline">
								<input type="password" name="oldPw" id="oldPassword"
									autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item" style="margin-bottom:0">
						<div class="layui-inline">
							<label class="layui-form-label">新密码：</label>
							<div class="layui-input-inline">
								<input type="password" name="newPw" id="newPassword"
									autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item" style="margin-bottom:0">
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
		
		<div id="flow" style="display: none">
			 <input type="hidden"id="flow-src1">
			 <input type="hidden"id="flow-src2">
			 <input type="hidden"id="flow-src3">
			  <h1 class="lk-flow-title">
		      	计费系统开通流程
			  </h1>
			  <i class="layui-icon close close-layer">&#x1006;</i>
			  <div class="lk-flow-body layui-clear">
			      <div class="lk-flow-img">
			          <img src="<%=basePath%>newStyle/images/flow-chart.jpg" alt="">
			      </div>
			      <div class="lk-flow-text" style="margin-right: 50px;">
			          	填写绑定信息</br>并付费
			      </div>
			      <div class="lk-flow-text" style="margin-right: 50px;">
			          	审核通过
			      </div>
			      <div class="lk-flow-text" style="margin-right: 50px;">
					  在审核通过页面中获取下载揽客网吧接口程序链接以及激活码
			      </div>
			      <div class="lk-flow-text" >
					  安装并使用揽客网吧接口程序
			      </div>
			  </div>
			  <div class="lk-flow-btn">
			      <span class="btn-close close-layer" >关闭</span>
			      <span class="btn-sure">确定</span>
			  </div>
		</div>
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script src="<%=basePath%>newStyle/js/lk-account.js"></script>
<script>
	$('.btn-sure').on('click', function(){
    	var store_id = $("#flow-src1").val()
    	var store_name = $("#flow-src2").val()
    	var state = $("#flow-src3").val()
    	layer.close(layer.index)
    	$("#store_id").val(store_id);
    	$("#store_name").val(store_name);
    	$("#state1").val(state);
    	document.getElementById("toShowFrom").submit();
	});
</script>
<script type="text/javascript">
function toShow(store_id,store_name,state) {
	$("#store_id").val(store_id);
	$("#store_name").val(store_name);
	$("#state1").val(state);
	document.getElementById("toShowFrom").submit();
}

function goPayOpen(id,store_id){
	$("#idd").val(id);
	$("#store_idd").val(store_id);
	$("#YH_FORM").attr("action","accountSettings/goPayOpen.do");
	$("#YH_FORM").submit();
	getMessageNum(id);
}

function goJLOpen(id,store_id) {

    $("#idd").val(id);
    $("#store_idd").val(store_id);
    $("#YH_FORM").attr("action","jialian/goEdit.do");
    $("#YH_FORM").submit();
    getMessageNum(id);
}

function getMessageNum(id){
	$.ajax({
		type: "POST",
		url: 'newerGuide/getMessageNum.do',
		data: {message_id:id},
		dataType:'json',
		cache: false,
		success: function(data){
			var aId = window.parent.parent.document.getElementById("aId");
			if(data.number > 0){
				aId.innerHTML = '消息通知<span class="layui-badge" id="msgNum">'+data.number+'</span>';
			}else if(data.number == 0){
				aId.innerHTML = '消息通知';
			}
		}
	});
}

function goQrList(store_id) {
    window.open("<%=basePath%>qrCode/general.do?store_id="+store_id);
}



</script>
</html>