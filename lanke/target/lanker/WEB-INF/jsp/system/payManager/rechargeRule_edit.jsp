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
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css"
	media="all">
<link rel="icon" type="text/css"
	href="<%=basePath%>newStyle/images/lk-icon.png">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/custom.css">
<title>新增充值规则</title>
<style>
body {
	background-color: #f6f8f9;
	min-width: 600px;
	overflow: auto
}
.borPadding {
	padding: 20px 30px;
}
.layui-form-pane .layui-form-label {
	background-color: #f3f3f3;
    font-weight: 500;
}
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
	-webkit-appearance: none;
}
input[type="number"] {
	-moz-appearance: textfield;
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
					${pd.user_name}
				</span>
			</div>
		</div>
	</div>
			
	<div class="card-width" style="min-width: 1100px;">
		<h1>新增充值规则</h1>
		<div class="lanke-pbMsg layui-clear" style="padding-bottom: 60px;">
			<div class="pay-left-phone">
				<div class="pay-left-content">
					<div class="lk-payHeader-box">
						<div class="lk-pay-header">
							<div id="lk-pay-userbox">
								<div class="lk-pay-bor">
									<div class="lk-pay-bg">
										<h1 id="CARDED"><i class="iconfont">&#xe620;</i>4304221997898900XX</h1>
										<p>当前绑定门店：${pd.nameList.split(" ")[0]}</p>
									</div>
								</div>
							</div>
						</div>
						<div class="bindIocn"><i class="iconfont">&#xe628;</i></div>
					</div>
					<div class="lk-pay-setMeal lk-pay-bor">
						<h1>充值金额</h1>
						<div class="layui-row layui-col-space8" id="agreementList">
						<c:forEach items="${ruleList}" var="var" varStatus="vs"> 
							<div class="layui-col-md4" id="phone_m${vs.index}">
								<div class="setMeal-det">
									<h3>￥<span>${var.recharge_money}</span></h3>
									<p>奖励<span>${var.reward_money}</span>元</p>
									<span class="active-icon"></span>
									<div class="lable-append"><c:if test="${var.label_name != null}"><span class="lk-pay-lable ${var.label_color}">${var.label_name}</span></c:if></div>
								</div>
							</div>
						</c:forEach>
						</div>
						<div class=""></div>
					</div>
					<div class="pay-btnBox">
						<div class="lk-pay-bor" >
							<p class="weic-pay-agreement">
								<span id="agreement-icon" class=""><i class="iconfont">&#xe6c1;</i></span>
								已阅读并同意<a herf="">《用户充值协议》</a>
							</p>
							<div class="weic-dent-btn weic-btn-gradientBlue" onclick="goPay_details()">立即充值</div>
						</div>
					</div>
					<div class="pay-nobody-box" <c:if test="${ruleList.size() > 0}">style="display:none;"</c:if>>
						<div class="pay-nobody-img"></div>
						<p>老板太懒了，没设置充值套餐</p>
					</div>
				</div>
				<p style="padding-top: 20px;text-align: center;">微信端界面展示</p>
			</div>
			<div style="float: left;width: 630px;position: relative;">
				<div class="lanke-pbMsgSide">
					<form class="layui-form lk-payRule-form layui-form-pane" id="rule_form">
						<div class="layui-form-item">
							<label class="layui-form-label">已选门店：</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input" value="${pd.nameList}" readonly>
								<input type="hidden" value="${pd.storeList}" id="storeList" name="storeList">
							</div>
						</div>
						<div class="lk-payRule-box layui-clear">
							<div class="module-box">
								<c:forEach items="${ruleList}" var="var" varStatus="vs"> 
									<div class="lk-payRule-module" id="module${vs.index}" onclick="module_avtive('${vs.index}')">
										<h1>￥<span>${var.recharge_money}</span></h1>
										<p>奖励<span>${var.reward_money}</span>元</p>
										<span class="module-active-icon"></span>
										<div class="lable-append">
											<c:if test="${var.label_name != null}"><span class="lk-pay-lable ${var.label_color}">${var.label_name}</span></c:if>
										</div>
										
										<input type="hidden" name="moduleName${vs.index}" id="moduleName${vs.index}" value="${var.recharge_money},${var.reward_money},<c:if test="${var.label_name == ''}">kong,kong</c:if><c:if test="${var.label_name != ''}">${var.label_name},${var.label_color}</c:if>">
									</div>
								</c:forEach>
							</div>
							<div class="lk-payRule-add" onclick="add_module()">
								<p style="margin-top: 22px;"><i class="layui-icon">&#xe654;</i></p>
								<p>添加规则</p>
							</div>
						</div>
						<input type="hidden" name="max_module" id="max_module">
					</form>
					<form class="layui-form lk-payRule-form layui-form-pane">
						<div class="lk-payModule-make">
							<div class="make-box">
								<p onclick="del_module()" class="del_module">删除规则</p>
								<!-- <p class="move_sort">拖拽排序</p> -->
								<p class="del_lable" onclick="del_lable()">删除标签</p>
							</div>
							<p class="make-save" style="display: none;">保存排序</p>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">充值金额：</label>
							<div class="layui-input-block">
								<input type="number" name="recharge_money" class="layui-input" id="recharge_money">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">奖励金额：</label>
							<div class="layui-input-block">
								<input type="number" name="reward_money" class="layui-input" id="reward_money">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">选择标签：</label>
							<div class="layui-input-block">
								<div class="lk-payLable-item">
									<span class="color09_s now_lable"  id="now_lable0" onclick="add_lable('color09','0')">HOT</span>
									<span class="color11_s now_lable"  id="now_lable1" onclick="add_lable('color11','1')">特惠</span>
									<c:forEach items="${labelList}" var="var" varStatus="vs" begin="2"> 
										<span class="${var.label_color}_s now_lable"  id="now_lable${vs.index}" onclick="add_lable('${var.label_color}','${vs.index}')">${var.label_name}</span>
									</c:forEach>
								</div>
								<div class="lk-payLable-make">
									<span id="lable-add" onclick="new_lable('<%=basePath%>')">添加新标签</span>
									<span id="lable-del" onclick="del_nowLable('<%=basePath%>')">删除标签</span>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="layui-form-item" style="padding: 20px;">
					<button class="layui-btn layui-btn-normal" onclick="saveRule('<%=basePath%>')">保存发布</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div id="new-lable" style="display: none;padding:30px;">
		<div class="layui-form-item">
			<label class="layui-form-label">标签名称：</label>
			<div class="layui-input-block">
				<input type="text" name="new_lable_name" class="layui-input" id="new_lable_name" maxlength="4" placeholder="最多四个字符">
			</div>
		</div>
		<div class="layui-form-item">
		   <input id="new_lable_color" name="new_lable_color" type="hidden" value="">
           <label class="layui-form-label">卡券颜色：</label>
           <div class="layui-input-block" style="max-width: 124px;">
               <div class="layui-collapse" lay-filter="color">
                   <div class="layui-colla-item">
                       <p class="layui-colla-title layui-input" style="border: none;height: 36px;">
                           <span class="lable-color colla-color" name=""></span>
                       </p>
                       <div class="layui-colla-content" style="padding: 10px 13px;background-color: #fff">
                           <span class="lable-color color01" name="color01" id="color01"></span>
                           <span class="lable-color color02" name="color02" id="color02"></span>
                           <span class="lable-color color03" name="color03" id="color03"></span>
                           <span class="lable-color color04" name="color04" id="color04"></span>
                           <span class="lable-color color05" name="color05" id="color05"></span>
                           <span class="lable-color color06" name="color06" id="color06"></span>
                           <span class="lable-color color07" name="color07" id="color07"></span>
                           <span class="lable-color color08" name="color08" id="color08"></span>
                           <span class="lable-color color09" name="color09" id="color09"></span>
                           <span class="lable-color color10" name="color10" id="color10"></span>
                           <span class="lable-color color11" name="color11" id="color11"></span>
                           <span class="lable-color color12" name="color12" id="color12"></span>
                       </div>
                   </div>
               </div>
           </div>
       </div>
	</div>
	<div id="lk-delLable-item" style="display: none;padding: 30px;">
		<c:forEach items="${labelList}" var="var" varStatus="vs" begin="2"> 
			<span id="del_lable${vs.index}" onclick="avtive_lable('${vs.index}')">${var.label_name}</span>
		</c:forEach>
	</div>
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/js/lk-pay.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
</html>





