<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
<title>支付开通流程</title>
<style>
body {
	background-color: #f6f8f9;
	min-width: 600px;
	overflow: auto
}
.layui-form-item .layui-input-inline {
	width:218px;
}
</style>
</head>
<body>
	<div class="card-header">
		<div class="card-header-msg">
			<img src="<%=basePath%>newStyle/images/logo2.png"class="card-lankeIcon">
			<span class="card-header-title">网吧管理后台</span>
			<div class="card-header-right">
				<img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
				<span class="card-userName layui-elip"> <small>Welcome</small><br>
					${pdStore.user_name}
				</span>
			</div>
		</div>
	</div>
	
	<div class="card-width" style="min-width: 950px;">
		<h1>
			当前开通计费系统<span style="font-weight: 300;">—</span><span>${pdStore.store_name}</span>
		</h1>
		
		<div class="lanke-pbMsg" style="padding-bottom:100px;">
		<input type="hidden" id="sstate" value="${pd.state}">
		<input type="hidden" id="ssuser_type" value="${pd.user_type}">
		<!-- 流程图 -->
			<div class="lanke-pbMsgSide">
				<fieldset class="layui-elem-field layui-field-title" style="">
					<legend>在线支付开通流程</legend>
				</fieldset>
				<div class="lk-pay-flow layui-clear">
					<div class="flow-box flow-box-active">
					    <div class="lk-payFlow-icon">
					    	<i class="iconfont">&#xe623;</i>
					    </div>
					    <p class="text-center">填写网吧资料</p>
					</div>
					<div class="flow-box <c:if test="${pd.state == '1'}">flow-box-active</c:if>">
					    <div class="lk-payFlow-icon">
					    	<i class="iconfont">&#xe624;</i>
					    </div>
					    <p class="text-center">网吧资料预审核</p>
					</div>
					<div class="flow-box <c:if test="${pd.state == '1' && (pd.status == '2' || pd.status == '0' || pd.status == '1')}">flow-box-active</c:if>">
					    <div class="lk-payFlow-icon">
					    	<i class="iconfont">&#xe625;</i>
					    </div>
					    <p class="text-center">下载入网清单</p>
					</div>
					<div class="flow-box <c:if test="${pd.state == '1' && (pd.status == '0' || pd.status == '1')}">flow-box-active</c:if>">
					    <div class="lk-payFlow-icon">
					    	<i class="iconfont">&#xe621;</i>
					    </div>
					    <p class="text-center">快递入网资料<br>并填写快递单号</p>
					</div>
					<div class="flow-box <c:if test="${pd.state == '1' && pd.status == '1'}">flow-box-active</c:if>">
					    <div class="lk-payFlow-icon">
					    	<i class="iconfont">&#xe627;</i>
					    </div>
					    <p class="text-center">在线支付开通完成</p>
					</div>
				</div>
			</div>
		
		<!-- 日志 -->
			<c:if test="${not empty logList}">
				<div class="lanke-audit">
					<fieldset class="layui-elem-field layui-field-title" style="margin-top:30px">
						<legend>审核日志</legend>
					</fieldset>
					<c:if test="${pd.state == '1' && pd.status == '1'}">
						<ul class="layui-timeline">
						<li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
							<div class="layui-timeline-content layui-text">
								<div class="layui-timeline-title">
									<h2>开通成功</h2>
									<span class="layui-badge-rim">${pd.updatetime}</span>
								</div>
								<ul>
									<li style="margin: 10px 0;">您的门店${pdStore.store_name}在线支付已开通成功，银联商户号为：<font color="red">${pd.business_number}</font></li>
									<li style="margin: 10px 0;">可凭此商户号到
										<a href="https://service.chinaums.com/uis/uisWebLogin/login" target="_blank">银联对账平台</a>
										注册账号，即可开始对账。
										<a class="layui-btn layui-btn-xs lk-pay-download" href="<%=basePath%>register/account_check.do" target="_blank">更多对账信息</a>
									</li>
								</ul>
							</div>
						</li>
					</ul>
					</c:if>
					<c:forEach items="${logList}" var="var" varStatus="vs">
						<c:if test="${var.state == '1'}">
							<ul class="layui-timeline">
								<li class="layui-timeline-item"><i
									class="layui-icon layui-timeline-axis">&#xe63f;</i>
									<div class="layui-timeline-content layui-text">
										<div class="layui-timeline-title">
											<h2>审核通过</h2>
											<span class="layui-badge-rim">${var.examine_time}</span>
										</div>
										<ul>
											<li>网吧基本资料已通过：请<font color="#01AAED">下载入网清单</font>，准备好入网资料并且发送快递到清单内指定地址，<font color="red">下载密码：kr58</font><a class="layui-btn layui-btn-xs lk-pay-download" href="https://pan.baidu.com/s/1uZKH4Hnm2f-MKcKlorgUmg" target="_blank">下载</a></li>
											<li style="margin-top:10px;">若快递已寄出，填写快递信息：
												<form class="layui-form layui-form-pane" name="EXPRESS_FORM" id="EXPRESS_FORM" style="margin-top:20px">
													<input type="hidden" id="intenetdatum_id2" name="intenetdatum_id2" value="${pd.id}"> 
													<div class="layui-form-item">
														<label class="layui-form-label" style="color: #333;">快递公司：</label>
														<div class="layui-input-inline">
															<input type="text" name="express_company" class="layui-input" placeholder=" " id="express_company" value="${pd.express_company}">
														</div>
													</div>
													<div class="layui-form-item">
														<label class="layui-form-label" style="color: #333;">快递单号：</label>
														<div class="layui-input-inline">
															<input type="text" name="express_number" class="layui-input" placeholder=" " id="express_number" value="${pd.express_number}">
														</div>
													</div>
													<c:if test="${pd.status != '1' && pd.status != '0' && pd.user_type == 'user'}">
														<div class="layui-form-item" id="express_div">
															<span class="layui-btn layui-btn-normal" onclick="submitExpress('<%=basePath%>')">提交</span>
														</div>
													</c:if>
												</form>
											</li>
										</ul>
									</div>
								</li>
							</ul>
						</c:if>
						
						<c:if test="${var.state == '0'}">
							<ul class="layui-timeline">
								<li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
									<div class="layui-timeline-content layui-text">
										<div class="layui-timeline-title">
											<h2>审核未通过</h2>
											<span class="layui-badge-rim">${var.examine_time}</span>
										</div>
										<ul>
											<li>失败原因为:${var.opinion}</li>
										</ul>
									</div>
								</li>
							</ul>
						</c:if>
					</c:forEach>
					
					
				</div>
			</c:if>
			
		
		<!-- 填写信息 -->
			<div class="lanke-pbMsgSide" style="margin-top:20px">
				<fieldset class="layui-elem-field layui-field-title" style="">
					<legend>填写绑定信息</legend>
				</fieldset>
				<form class="layui-form layui-form-pane" action="" name="Form" id="Form">
					<input type="hidden" id="id" name="id" value="${pd.id}">
					<input type="hidden" id="store_id" name="store_id" value="${pd.store_id}">
					<input type="hidden" id="data_type" name="data_type" value="${pd.data_type}">
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>企业名称：</label>
						<div class="layui-input-block">
							<input type="text" name="enterprise_name" class="layui-input" placeholder=" 请输入营业执照上的企业名称" id="enterprise_name" value="${pd.enterprise_name}">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>法人姓名：</label>
						<div class="layui-input-block">
							<input type="text" name="corporate_name" id="corporate_name" placeholder=" 请输入营业执照上的法人姓名" class="layui-input" value="${pd.corporate_name}"/>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>法人身份证：</label>
						<div class="layui-input-block">
							<input type="text" name="corporate_id" id="corporate_id" placeholder=" 请输入营业执照上的法人身份证号码" class="layui-input" value="${pd.corporate_id}"/>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">手机号码：</label>
						<div class="layui-input-block">
							<input type="text" name="phone" id="phone" class="layui-input" 
									pattern="^1[3,5,6,7,8,9][3,5,6,7,8,9]\d{8}$"  placeholder="请输入接收审核结果的手机号"  
									value="${pd.phone}"/>
						</div>
					</div>
					<c:if test="${(pd.state == null || pd.state == '' || pd.state == '0') && pd.user_type == 'user'}">
						<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">验证码：</label>
									<div class="layui-input-inline">
										<input type="text" name="code" id="code" lay-verify="required|code" autocomplete="off" class="layui-input" placeholder="手机验证码" >
									</div>
									<span class="layui-btn layui-btn-primary user-btn-yzm" id="codeBtn" onclick="sendCode()">发送验证码</span>
								</div>
						</div>
					</c:if>
					<input type="file" name="file" id="file" style="display:none;" accept="image/*">   
					<div class="layui-form-item" style="margin-bottom:24px;">
						<div class="layui-inline">
							<label class="layui-form-label"><span class="lanke-sign">*</span>上传文件：</label>
							<div class="layui-input-inline">
								<span class="layui-input lk-pay-upload" data-type="business">营业执照副本</span>
							</div>
							<div class="layui-input-inline">
								<span class="layui-input lk-pay-upload" data-type="legal_front">法人身份证正面</span>
							</div>
							<div class="layui-input-inline">
								<span class="layui-input lk-pay-upload" data-type="legal_side">法人身份证反面</span>
							</div>
						</div>
					</div>
					<div class="layui-form-item lk-payFlow-upItem" id="lk-payUp-upper">
						<div class="layui-input-inline pay-imgBox" id="img1">
							<c:if test="${pd.business_url != null}">
								<div class="lk-pay-upImg" style="background: url(<%=basePath%>uploadFiles/uploadImgs/${pd.business_url}) no-repeat center center;background-size:120%;" id="div1">
									<div class="img-operate" >
										<p style="margin: 36px 0 20px 0;" onclick="delImg(1)">
											<c:if test="${(pd.state == null || pd.state == '' || pd.state == '0') && pd.user_type == 'user'}">删除</c:if></p>
										<p onclick=seeImg("<%=basePath%>uploadFiles/uploadImgs/${pd.business_url}")>查看</p>
									</div>
									<input type="hidden" name="business" value="${pd.business_url}">
								</div>
							</c:if>
						</div>
						<div class="layui-input-inline pay-imgBox" id="img2">
							<c:if test="${pd.legal_front_url != null}">
								<div class="lk-pay-upImg" style="background: url(<%=basePath%>uploadFiles/uploadImgs/${pd.legal_front_url}) no-repeat center center;background-size:120%;" id="div2">
									<div class="img-operate" >
										<p style="margin: 36px 0 20px 0;" onclick="delImg(2)">
										<c:if test="${(pd.state == null || pd.state == '' || pd.state == '0') && pd.user_type == 'user'}">删除</c:if></p>
										<p onclick=seeImg("<%=basePath%>uploadFiles/uploadImgs/${pd.legal_front_url}")>查看</p>
									</div>
									<input type="hidden" name="legal_front" value="${pd.legal_front_url}">
								</div>
							</c:if>
						</div>
						<div class="layui-input-inline pay-imgBox" id="img3">
							<c:if test="${pd.legal_side_url != null}">
								<div class="lk-pay-upImg" style="background: url(<%=basePath%>uploadFiles/uploadImgs/${pd.legal_side_url}) no-repeat center center;background-size:120%;" id="div3">
									<div class="img-operate" >
										<p style="margin: 36px 0 20px 0;" onclick="delImg(3)">
										<c:if test="${(pd.state == null || pd.state == '' || pd.state == '0') && pd.user_type == 'user'}">删除</c:if></p>
										<p onclick=seeImg("<%=basePath%>uploadFiles/uploadImgs/${pd.legal_side_url}")>查看</p>
									</div>
									<input type="hidden" name="legal_side" value="${pd.legal_side_url}">
								</div>
							</c:if>
						</div>
					</div>
					<div class="layui-form-item lk-payFlow-upItem" style="margin-bottom:24px;">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<span class="layui-input lk-pay-upload" id="test4" data-type="open_bank">对公账户信息证明</span>
							</div>
							<div class="layui-input-inline">
								<span class="layui-input lk-pay-upload" id="test5" data-type="internet_culture">网络文化经营许可证</span>
							</div>
						</div>
					</div>
					<div class="layui-form-item lk-payFlow-upItem" id="lk-payUp-lower">
						<div class="layui-input-inline pay-imgBox" id="img4">
							<c:if test="${pd.open_bank_url != null}">
								<div class="lk-pay-upImg" style="background: url(<%=basePath%>uploadFiles/uploadImgs/${pd.open_bank_url}) no-repeat center center;background-size:120%;" id="div4">
									<div class="img-operate" >
										<p style="margin: 36px 0 20px 0;" onclick="delImg(4)">
											<c:if test="${(pd.state == null || pd.state == '' || pd.state == '0') && pd.user_type == 'user'}">删除</c:if></p>
										<p onclick=seeImg("<%=basePath%>uploadFiles/uploadImgs/${pd.open_bank_url}")>查看</p>
									</div>
									<input type="hidden" name="open_bank" value="${pd.open_bank_url}">
								</div>
							</c:if>
						</div>
						<div class="layui-input-inline pay-imgBox" id="img5">
							<c:if test="${pd.internet_culture_url != null}">
								<div class="lk-pay-upImg" style="background: url(<%=basePath%>uploadFiles/uploadImgs/${pd.internet_culture_url}) no-repeat center center;background-size:120%;" id="div5">
									<div class="img-operate" >
										<p style="margin: 36px 0 20px 0;" onclick="delImg(5)">
										<c:if test="${(pd.state == null || pd.state == '' || pd.state == '0') && pd.user_type == 'user'}">删除</c:if></p>
										<p onclick=seeImg("<%=basePath%>uploadFiles/uploadImgs/${pd.internet_culture_url}")>查看</p>
									</div>
									<input type="hidden" name="internet_culture" value="${pd.internet_culture_url}">
								</div>
							</c:if>
						</div>
					</div>
				</form>
			</div>
			
			<c:if test="${(pd.state == null || pd.state == '' || pd.state == '0') && pd.user_type == 'user'}">
				<div class="layui-form-item" style="margin: 20px 0 30px 20px">
					<button class="layui-btn" onclick="save('<%=basePath%>','1')">仅保存</button>
					<button class="layui-btn layui-btn-normal" onclick="save('<%=basePath%>','0')">提交</button>
				</div>
			</c:if>
			<c:if test="${pd.state == '2' && pd.user_type == 'system'}">
				<div class="layui-form-item" style="margin: 20px 0 30px 20px">
					<button class="layui-btn" onclick="review('<%=basePath%>','0')">审核不通过</button>
					<button class="layui-btn layui-btn-normal" onclick="review('<%=basePath%>','1')">审核通过</button>
				</div>
			</c:if>
			<div class="FAIL" style="display: none;padding: 20px">
				<form class="layui-form layui-form-pane" action="" method="post" id="REVIEW_FORM">
					<div class="layui-form-item layui-form-text">
						  <label class="layui-form-label" >不通过原因：</label>
						  <div class="layui-input-block">
							 <textarea placeholder="请输入内容" id="opinion" name="opinion" class="layui-textarea"></textarea>
							 <input type="hidden" id="review_state" name="review_state" value="0"> 
							 <input type="hidden" id="intenetdatum_id" name="intenetdatum_id" value="${pd.id}"> 
						  </div>
					</div>
				</form>
			</div>
			
</body>
</html>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-pay.js"></script>
<script src="<%=basePath%>newStyle/js/lk-sendCode.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>
	 window.onbeforeunload = function(event) {
		event.returnValue = "系统可能不会保存您所做的更改";
     };
</script>





