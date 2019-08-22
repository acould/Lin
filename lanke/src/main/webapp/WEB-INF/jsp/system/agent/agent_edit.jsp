<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css"
	media="all">
<link rel="icon" type="text/css"
	href="<%=basePath%>newStyle/images/lk-icon.png">
<title>新增代理商</title>
<style>
body {
	background-color: #f6f8f9;
	min-width: 600px;
	overflow: auto
}

.layui-form-item .layui-input-inline {
	width: 218px;
}
</style>
</head>
<body>
	<div class="card-header">
		<div class="card-header-msg">
			<img src="<%=basePath%>newStyle/images/logo2.png"
				class="card-lankeIcon"> <span class="card-header-title">网吧管理后台</span>
			<div class="card-header-right">
				<img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
				<span class="card-userName layui-elip"> <small>Welcome</small><br>
					${pd.NAME}
				</span>
			</div>
		</div>
	</div>
	<div class="card-width" style="min-width: 950px;">
		<c:if test="${pd.state == 'add'}">
			<h1>新增代理商</h1>
		</c:if>
		<c:if test="${pd.state == 'edit'}">
			<h1>编辑代理商</h1>
		</c:if>
		<c:if test="${pd.state == 'show'}">
			<h1>查看代理商</h1>
		</c:if>
		<div class="lanke-pbMsg">
			<fieldset class="layui-elem-field layui-field-title">
				<c:if test="${pd.state != 'show'}">
					<legend style="width: auto; border-bottom: 0; margin-bottom: 0;">填写代理商信息</legend>
				</c:if>
				<c:if test="${pd.state == 'show'}">
					<legend style="width: auto; border-bottom: 0; margin-bottom: 0;">代理商信息如下</legend>
				</c:if>
			</fieldset>
			<div class="lanke-pbMsgSide">
				<form action="" name="Form" id="Form" method="post"
					enctype="multipart/form-data" class="layui-form lk-agent-from">
					<input type="hidden" name="id" id="id" value="${pd.id}"> <input
						type="hidden" name="state" id="state" value="${pd.state}">
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>企业名称:</label>
						<div class="layui-input-block">
							<c:if test="${pd.state != 'show'}">
								<input type="text" name="company_name" id="company_name"
									placeholder="请填写营业执照上的企业全称" class="layui-input"
									value="${pd.company_name}" style="min-width: 440px;">
							</c:if>
							<c:if test="${pd.state == 'show'}">
								<input type="text" name="company_name" id="company_name"
									placeholder="请填写营业执照上的企业全称" class="layui-input"
									value="${pd.company_name}" style="min-width: 440px;"
									disabled="disabled">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>企业地址:</label>
						<div class="layui-input-block">
							<input type="hidden" name="PROVINCE" id="PROVINCE"
								value="${pd.province }"> <input type="hidden"
								name="CITY" id="CITY" value="${pd.city }"> <input
								type="hidden" name="COUNTY" id="COUNTY" value="${pd.county }">
							<div id="city" style="padding: 4px 18px;">

								<span style="padding: 0 10px 0 0">省</span>
								<c:if test="${pd.state != 'show'}">
									<select class="prov" id="s_province" name="s_province"></select>
								</c:if>
								<c:if test="${pd.state == 'show'}">
									<select class="prov" id="s_province" name="s_province"
										disabled="disabled"></select>
								</c:if>

								<span style="padding: 0 10px 0 10px">市</span>
								<c:if test="${pd.state != 'show'}">
									<select class="city" id="s_city" name="s_city"></select>
								</c:if>
								<c:if test="${pd.state == 'show'}">
									<select class="city" id="s_city" name="s_city"
										disabled="disabled"></select>
								</c:if>

								<span style="padding: 0 10px 0 10px">区</span>
								<c:if test="${pd.state != 'show'}">
									<select class="dist" id="s_county" name="s_county"></select>
								</c:if>
								<c:if test="${pd.state == 'show'}">
									<select class="dist" id="s_county" name="s_county"
										disabled="disabled"></select>
								</c:if>

							</div>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>详细地址:</label>
						<div class="layui-input-block" style="display: block">
							<c:if test="${pd.state != 'show'}">
								<input type="text" name="company_address" id="company_address"
									value="${pd.company_address}" placeholder=""
									class="layui-input" style="width: 100%;">
							</c:if>
							<c:if test="${pd.state == 'show'}">
								<input type="text" name="company_address" id="company_address"
									value="${pd.company_address}" placeholder=""
									class="layui-input" style="width: 100%;" disabled="disabled">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>法人姓名:</label>
						<div class="layui-input-block">
							<c:if test="${pd.state != 'show'}">
								<input type="text" name="corporate_name" id="corporate_name"
									value="${pd.corporate_name}" maxlength="20"
									placeholder="请填写营业执照上的法人姓名" class="layui-input"
									style="min-width: 200px;">
							</c:if>
							<c:if test="${pd.state == 'show'}">
								<input type="text" name="corporate_name" id="corporate_name"
									value="${pd.corporate_name}" maxlength="20"
									placeholder="请填写营业执照上的法人姓名" class="layui-input"
									style="min-width: 200px;" disabled="disabled">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>法人身份证:</label>
						<div class="layui-input-block">
							<c:if test="${pd.state != 'show'}">
								<input type="text" name="corporate_id" id="corporate_id"
									value="${pd.corporate_id}" maxlength="20" class="layui-input">
							</c:if>
							<c:if test="${pd.state == 'show'}">
								<input type="text" name="corporate_id" id="corporate_id"
									value="${pd.corporate_id}" maxlength="20" class="layui-input"
									disabled="disabled">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label" style="width: 130px;"><font
							color="red">*</font>统一社会信用代码证编号:</label>
						<div class="layui-input-block">
							<c:if test="${pd.state != 'show'}">
								<input type="text" name="credit_number" id="credit_number"
									value="${pd.credit_number}" maxlength="20" placeholder=""
									class="layui-input" style="margin-top: 8px;">
							</c:if>
							<c:if test="${pd.state == 'show'}">
								<input type="text" name="credit_number" id="credit_number"
									value="${pd.credit_number}" maxlength="20" placeholder=""
									class="layui-input" style="margin-top: 8px;"
									disabled="disabled">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>联系人:</label>
						<div class="layui-input-block">
							<c:if test="${pd.state != 'show'}">
								<input type="text" name="contacts_name" id="contacts_name"
									value="${pd.contacts_name}" maxlength="20" placeholder=""
									class="layui-input" style="min-width: 200px;">
							</c:if>
							<c:if test="${pd.state == 'show'}">
								<input type="text" name="contacts_name" id="contacts_name"
									value="${pd.contacts_name}" maxlength="20" placeholder=""
									class="layui-input" style="min-width: 200px;"
									disabled="disabled">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>手机号码:</label>
						<div class="layui-input-block">
							<c:if test="${pd.state != 'show'}">
								<input type="text" name="phone" id="phone" value="${pd.phone}"
									placeholder="请填写联系人的手机号码" class="layui-input"
									onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="11">
							</c:if>
							<c:if test="${pd.state == 'show'}">
								<input type="text" name="phone" id="phone" value="${pd.phone}"
									placeholder="请填写联系人的手机号码" class="layui-input"
									disabled="disabled">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>代理商编号:</label>
						<div class="layui-input-block">
							<c:if test="${pd.state != 'show'}">
								<input type="text" name="agent_number" id="agent_number"
									value="${pd.agent_number}" maxlength="8"
									onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="8位数"
									class="layui-input">
							</c:if>
							<c:if test="${pd.state == 'show'}">
								<input type="text" name="agent_number" id="agent_number"
									value="${pd.agent_number}" class="layui-input"
									disabled="disabled">
							</c:if>
						</div>
					</div>

					<input type="file" name="file" id="file" style="display: none;"
						accept="image/*">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label"><span class="lanke-sign">*</span>上传文件：</label>
							<div class="layui-input-inline" style="margin-left: 20px;">
								<span class="layui-input lk-pay-upload" data-type="agent_business">营业执照副本</span>
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
						<c:if test="${pd.state == 'add'}">
							<div class="layui-input-inline pay-imgBox" id="img1"></div>
							<div class="layui-input-inline pay-imgBox" id="img2"></div>
							<div class="layui-input-inline pay-imgBox" id="img3"></div>
						</c:if>

						<c:if test="${pd.state != 'add'}">
							<div class="layui-input-inline pay-imgBox" id="img1">
								<c:if test="${pd.agent_business_url != null}">
									<div class="lk-pay-upImg"
										style="background: url(<%=basePath%>uploadFiles/uploadImgs/${pd.agent_business_url}) no-repeat center center;background-size:120%;"
										id="div1">
										<div class="img-operate">
											<c:if test="${pd.state != 'show'}">
												<p style="margin: 36px 0 20px 0;" onclick="delImg(1)">删除</p>
											</c:if>
											<p
												onclick="seeImg('<%=basePath%>uploadFiles/uploadImgs/${pd.agent_business_url}')">查看</p>
										</div>
										<input type="hidden" name="agent_business" id="agent_business"
											value="${pd.agent_business_url}">
									</div>
								</c:if>
							</div>
							<div class="layui-input-inline pay-imgBox" id="img2">
								<c:if test="${pd.legal_front_url != null}">
									<div class="lk-pay-upImg"
										style="background: url(<%=basePath%>uploadFiles/uploadImgs/${pd.legal_front_url}) no-repeat center center;background-size:120%;"
										id="div2">
										<div class="img-operate">
											<c:if test="${pd.state != 'show'}">
												<p style="margin: 36px 0 20px 0;" onclick="delImg(2)">删除</p>
											</c:if>
											<p
												onclick="seeImg('<%=basePath%>uploadFiles/uploadImgs/${pd.legal_front_url}')">查看</p>
										</div>
										<input type="hidden" name="legal_front" id="legal_front"
											value="${pd.legal_front_url}">
									</div>
								</c:if>
							</div>
							<div class="layui-input-inline pay-imgBox" id="img3">
								<c:if test="${pd.legal_side_url != null}">
									<div class="lk-pay-upImg"
										style="background: url(<%=basePath%>uploadFiles/uploadImgs/${pd.legal_side_url}) no-repeat center center;background-size:120%;"
										id="div3">
										<div class="img-operate">
											<c:if test="${pd.state != 'show'}">
												<p style="margin: 36px 0 20px 0;" onclick="delImg(3)">删除</p>
											</c:if>
											<p onclick="seeImg('<%=basePath%>uploadFiles/uploadImgs/${pd.legal_side_url}')">查看</p>
										</div>
										<input type="hidden" name="legal_side" id="legal_side"
											value="${pd.legal_side_url}">
									</div>
								</c:if>
							</div>
						</c:if>
					</div>

				</form>
			</div>
			<div class="layui-form-item" style="margin: 20px 0 30px 40px">
				<c:if test="${pd.state != 'show'}">
				<c:if test="${QX.add == 1 }">
					<button class="layui-btn layui-btn-normal" onclick="edit()">提交</button>
				</c:if>	
				</c:if>
			</div>
		</div>
	</div>

	<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
	<script src="<%=basePath%>js/jquery.cityselect.js"></script>
	<script src="<%=basePath%>newStyle/layer/layer.js"></script>
	<!-- 业务JS -->
	<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-agent.js"></script>
	<script type="text/javascript">
		//调用城市三级联动
		citySel($("#PROVINCE").val(), $("#COUNTY").val(), $("#CITY").val())
	</script>
</body>
</html>