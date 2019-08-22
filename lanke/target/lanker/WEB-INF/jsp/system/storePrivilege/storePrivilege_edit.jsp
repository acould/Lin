<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
<title>网吧管理后台</title>
<style>
body {
	background-color: #f6f8f9;
	min-width: 600px;
	overflow: auto
}
</style>
</head>
<body class="scroll">
	<div class="card-header">
		<div class="card-header-msg">
			<img src="<%=basePath%>newStyle/images/logo2.png" class="card-lankeIcon"></img>
			<span class="card-header-title">网吧管理后台</span>
			<div class="card-header-right">
				<img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
				<span class="card-userName layui-elip"> <small>Welcome</small><br>
					${pd.INTENET_NAME}
				</span>
			</div>
		</div>
	</div>
	<div class="card-width">
		<h1>
			当前开通计费系统<span style="font-weight: 300;">—</span><span>${pd.STORE_NAME}</span>
		</h1>
		<div class="lanke-pbMsg">
			<c:if test="${pd.states ==1}">
				<div class="overtime">
					<p><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>您当前的计费系统绑定已到期，揽客通讯客户端无法正确使用，请续费</p>
				</div>
			</c:if>
			<div class="lanke-pbMsgSide">
				<fieldset class="layui-elem-field layui-field-title" style="">
					<legend>填写绑定信息</legend>
				</fieldset>
				<form class="layui-form layui-form-pane" action="" name="Form" id="Form">
					<div class="layui-form-item">
						<label class="layui-form-label">计费系统：</label>
						<div class="layui-input-block">
							<input type="radio" name="chargingtype" id="chargingtype" value="P" title="Pubwin OL" lay-filter="radio" ${map.chargingtype=="P"?"checked":(map.chargingtype!="p"?"checked":"")} >
                            <input type="radio" name="chargingtype" id="chargingtype" value="W" title="万象" lay-filter="radio" ${map.chargingtype=="W"?"checked":""} >
							<input type="hidden" name="STATE1" id="STATE1" value="${pd.STATE}">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>企业名称：</label>
						<div class="layui-input-block">
							<input type="hidden" name="STORE_ID" id="STORE_ID" value="${pd.STORE_ID}" /> <input type="hidden" name="STATE" id="STATE" value="${pd.STATE}" />
							<!-- 0表示去绑定,2表示重新提交 -->
							<c:if test="${pd.STATE==0 or pd.STATE==2}">
								<input type="text" name="Company_Name" id="Company_Name" class="layui-input" value="${map.Company_Name}" placeholder="请输入当前门店在计费Pubwin OL上注册的企业全称">
							</c:if>
							<!-- 1表示去查看 -->
							<c:if test="${pd.STATE==1}">
								<input type="text" name="Company_Name" id="Company_Name" class="layui-input" value="${map.Company_Name}" placeholder="请输入当前门店在计费Pubwin OL上注册的企业全称" readonly="readonly">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>用户ID：</label>
						<div class="layui-input-block">
							<c:if test="${pd.STATE==0 or pd.STATE==2}">
								<input type="text" name="pubwin_store_id" id="pubwin_store_id" class="layui-input" value="${map.pubwin_store_id}" placeholder="请输入当前门店Pubwin OL的ID">
							</c:if>
							<c:if test="${pd.STATE==1}">
								<input type="text" name="pubwin_store_id" id="pubwin_store_id" class="layui-input" value="${map.pubwin_store_id}" placeholder="请输入当前门店Pubwin OL的ID" readonly="readonly">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">服务商编号：</label>
						<div class="layui-input-block">
							<c:if test="${pd.STATE==0 or pd.STATE==2}">
								<input type="text" name="SERVICE_ID" id="SERVICE_ID" class="layui-input" value="${map.SERVICE_ID}" 
								maxlength="8" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请输入当前门店的网维服务商编号,如无可不填写">
								<input type="hidden" name="ODDS" id="ODDS" class="layui-input"value="${map.ODDS}">
							</c:if>
							<c:if test="${pd.STATE==1}">
								<input type="text" name="SERVICE_ID" id="SERVICE_ID" class="layui-input" value="${map.SERVICE_ID}" 
								maxlength="8" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请输入当前门店的网维服务商编号,如无可不填写" readonly="readonly">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">手机号码：</label>
						<div class="layui-input-block">
							<c:if test="${pd.STATE==0 or pd.STATE==2}">
								<input type="text" name="STORE_PHONE" id="STORE_PHONE" class="layui-input" value="${map.PHONE}"
									pattern="^1[3,5,6,7,8,9][3,5,6,7,8,9]\d{8}$" placeholder="请输入接收审核结果的手机号">
							</c:if>
							<c:if test="${pd.STATE==1}">
								<input type="text" name="STORE_PHONE" id="STORE_PHONE" class="layui-input" value="${map.STORE_PHONE}"  readonly="readonly">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">网维产品：</label>
						<div class="layui-input-block">
							<input type="radio" name="producttype" id="producttype" value="T" title="是顺网产品（网维大师、讯闪、信佑、顺网雲等）" lay-filter="radio" ${map.producttype=="T"?"checked":(map.producttype!="F"?"checked":"")} >
                            <input type="radio" name="producttype" id="producttype" value="F" title="不是" lay-filter="radio" ${map.producttype=="F"?"checked":""} >
						</div>
					</div>
					<c:if test="${pd.STATE==0 or pd.STATE==2}">
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">验证码：</label>
								<div class="layui-input-inline">
									<input type="tel" name="code" id="code" lay-verify="required|code" autocomplete="off" class="layui-input" placeholder="手机验证码">
								</div>
								<span class="layui-btn layui-btn-primary user-btn-yzm" id="btn" onclick="return verification()">发送验证码</span>
							</div>
						</div>
					</c:if>

					<fieldset class="layui-elem-field layui-field-title" style="margin-top: 40px;">
						<legend>选择付费套餐</legend>
					</fieldset>
					<c:if test="${pd.STATE==0 or pd.STATE==2}">
						<input type="hidden" name="CHOOSE_PACKAGE" id="CHOOSE_PACKAGE" class="layui-input" value="">
						<div class="layui-clear" style="margin-bottom: 15px;">
						<!-- 如果有免费机会,则显示两种套餐选择 -->
							<c:if test="${map.ODDS==0}">
								<%--<div class="lanke-pay text-center" id="pay0">
									<div class="lanke-pay-set">
										<p class="lanke-pay-title">免费体验</p>
										<p class="lanke-pay-years">一个月，每门店限1次</p>
									</div>
									<span class="lanke-pay-lab">活动</span> 
									<i class="pay-active-icon"></i>
								</div>--%>
								<div class="lanke-pay text-center" id="pay2">
									<div class="lanke-pay-set">
										<p class="lanke-pay-title">免费体验</p>
										<p class="lanke-pay-years">7天，每门店限1次</p>
									</div>
									<span class="lanke-pay-lab">活动</span>
									<i class="pay-active-icon"></i>
								</div>

								<div class="lanke-pay text-center" onclick="open_pay()" id="pay1">
									<div class="lanke-pay-set">
										<p class="lanke-pay-title">1800元</p>
										<p class="lanke-pay-years">1年</p>
									</div>
									<span class="lanke-pay-lab recommend">推荐</span> 
									<i class="pay-active-icon"></i>
								</div>
							</c:if>
							<!-- 如果没有免费机会,则只显示1年的套餐 -->
							<c:if test="${map.ODDS==1}">
								<div class="lanke-pay text-center" onclick="open_pay()" id="pay1">
									<div class="lanke-pay-set">
										<p class="lanke-pay-title">1800元</p>
										<p class="lanke-pay-years">1年</p>
									</div>
									<span class="lanke-pay-lab recommend">推荐</span> 
									<i class="pay-active-icon"></i>
								</div>
							</c:if>
						</div>
					</c:if>
                    <!-- 查看时只展示选择的套餐 -->
					<c:if test="${pd.STATE==1}">
						<div class="layui-clear" style="margin-bottom: 15px;">
							<c:if test="${map.CHOOSE_PACKAGE==2}">
								<div class="lanke-pay text-center" id="pay2">
									<div class="lanke-pay-set">
										<p class="lanke-pay-title">免费体验</p>
										<p class="lanke-pay-years">7天，每门店限1次</p>
									</div>
									<span class="lanke-pay-lab">活动</span>
									<i class="pay-active-icon"></i>
								</div>
							</c:if>
							<c:if test="${map.CHOOSE_PACKAGE==0}">
								<div class="lanke-pay text-center" id="pay0">
									<div class="lanke-pay-set">
										<p class="lanke-pay-title">免费体验</p>
										<p class="lanke-pay-years">一个月，每门店限1次</p>
									</div>
									<span class="lanke-pay-lab">活动</span> 
									<i class="pay-active-icon"></i>
								</div>
							</c:if>
							<c:if test="${map.CHOOSE_PACKAGE==1}">
								<div class="lanke-pay text-center" id="pay1">
									<div class="lanke-pay-set">
										<p class="lanke-pay-title">1800元</p>
										<p class="lanke-pay-years">1年</p>
									</div>
									<span class="lanke-pay-lab recommend">推荐</span> 
									<i class="pay-active-icon"></i>
								</div>
							</c:if>
						</div>
					</c:if>
				</form>
			</div>
			<div class="layui-form-item" style="margin: 20px 0 30px 20px">
				<!-- 去绑定/重新提交 -->
				<c:if test="${pd.STATE==0 or pd.STATE==2}">
					<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="demo2" onclick="return baocun()">提交</button>
				</c:if>
			</div>
			<c:if test="${not empty List}">
				<div class="lanke-audit">
					<fieldset class="layui-elem-field layui-field-title" style="">
						<legend>审核日志</legend>
					</fieldset>
					<c:forEach items="${List}" var="var" varStatus="vs">
						<c:if test="${var.ASTATE==0}">
							<ul class="layui-timeline">
								<li class="layui-timeline-item"><i
									class="layui-icon layui-timeline-axis">&#xe63f;</i>
									<div class="layui-timeline-content layui-text">
										<div class="layui-timeline-title">
											<h2>审核未通过</h2>
											<span class="layui-badge-rim">${var.TIME}</span>
										</div>
										<ul>
											<li>失败原因为:${var.Note}</li>
										</ul>
									</div></li>
							</ul>
						</c:if>
						<c:if test="${var.ASTATE==1}">
							<ul class="layui-timeline">
								<li class="layui-timeline-item"><i
									class="layui-icon layui-timeline-axis">&#xe63f;</i>
									<div class="layui-timeline-content layui-text">
										<div class="layui-timeline-title">
											<h2>审核通过</h2>
											<span class="layui-badge-rim">${var.TIME}</span>
										</div>
										<ul>
											<li>请点击下载安装<a href="https://pan.baidu.com/s/1OkiOa7s_W4DrM_qC-d8ttQ" target="_blank">揽客网吧接口程序</a>，下载密码：ac3s
											</li>
											<li>按照接口程序安装教程，用您的门店ID：<font color="red">${pd.STORE_ID}</font>进行激活，即可正式使用
											</li>
											<li>门店ID非常重要，请妥善保管，勿告知他人，以免造成不必要的损失。</li>
										</ul>
									</div></li>
							</ul>
						</c:if>
					</c:forEach>
				</div>
			</c:if>
		</div>
	</div>

	<div class="lanke-footer">
		© <a href="http://lanke8.cc">lanke8.cc</a>&nbsp;揽客
	</div>
	<div id="open-pay" style="display:none">
	    <p class="title">支付方式</p>
	    <div class="content">
	    	<p style="margin-bottom:20px"><font color="red">温馨提醒：</font>支付时请备注门店名称，以免信息核对错误</p>
	    	<div class="pay-bank">
	    		<p class="pay-bank-title">企业支付宝转账</p>
	    		<p>企业支付宝账号：52533958@qq.com</p>
	    		<p>账号名：杭州网晶科技有限公司</p>
	    		<p>支付金额：1800元</p>
	    	</div>
	    	<div class="pay-bank">
	    		<p class="pay-bank-title">银行卡转账</p>
	    		<p>银行卡号：74218100250735</p>
	    		<p>开户名：杭州网晶科技有限公司，开户行：杭州银行学院路支行</p>
	    		<p>支付金额：1800元</p>
	    	</div>
	    </div>
	</div>

	<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
	<script>

	
	$(".lanke-pay").click(function(){
		$(this).addClass("lanke-pay-active");
		$(this).siblings().removeClass("lanke-pay-active");
		var pay_id = $(this).attr("id");
		if(pay_id == "pay0"){
			$("#CHOOSE_PACKAGE").val("0")
		}else if (pay_id == "pay1"){
			$("#CHOOSE_PACKAGE").val("1")
		}else if (pay_id == "pay2"){
			$("#CHOOSE_PACKAGE").val("2")
		}
	})
	function open_pay(){
		layer.open({
			  type: 1,
			  title: false,
			  area: ['534px', '490px'],
			  btn:["已支付","再看看"],
			  shade: 0.4,
			  closeBtn: 0,
			  move:".title",
			  moveOut: true,
			  shadeClose: false,
			  content: $("#open-pay")
		})
	}
	
	 var countdown=60; //定义全局变量(计时用)
	 function settime(obj) { //发送验证码倒计时
	     if (countdown == 0) { 
	         obj.attr('disabled',false); 
	         //obj.removeattr("disabled"); 
	         obj.html("免费获取验证码");
	         countdown = 60; 
	         return;
	     } else { 
	         obj.attr('disabled',true);
	         obj.html("发送成功,重新发送(" + countdown + ")");
	         countdown--;
	     } 
	 setTimeout(function() { 
	     settime(obj) }
	     ,1000);
	 }
	
		//判断验证码是否发送成功(去绑定/重新提交)
		function verification(){
			var PHONE = $.trim($("#STORE_PHONE").val());
			var STORE_ID ='${pd.STORE_ID}';
			$.ajax({
				type: "post",
				url: '<%=basePath%>storePrivilege/getcodeA.do',
		    	data: {PHONE:PHONE,STORE_ID:STORE_ID},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("error" == data.result){
					 	layer.tips('手机号码格式不匹配，请重试', '#STORE_PHONE', {
				 			tips: 3
		        		});
					 }else if("frequent"==data.result){
					 	layer.tips('发送验证码太频繁，请稍候再试', '#btn', {
				 			tips: 3
		        		});
					 }else if("success"==data.result){
						 $("#STORE_PHONE").val(data.phone);
						 var obj = $("#btn");
						 settime(obj);
					 }else if("fail"==data.result){
					 	layer.tips('发送失败，请重试', '#btn', {
				 			tips: 3
		        		});
					 }
				}
			});
			return false;
		}
		
		//保存(去绑定/重新提交)
		function baocun() {
			var STATE="${pd.STATE}";
			if ($("#Company_Name").val() == "") {
				layer.tips('请输入企业全称', '#Company_Name', {
					tips : 3
				});
				return false;
			}

			if ($("#pubwin_store_id").val() == "") {
				layer.tips('请输入门店的ID', '#pubwin_store_id', {
					tips : 3
				});
				return false;
			}
			
			if ($("#SERVICE_ID").val() !== "") {
				if($("#SERVICE_ID").val().length != 8){
					layer.tips('服务商编号格式不正确', '#SERVICE_ID', {
				 		tips: 3
		        	});
					return false;
				}
			}
			
			//判断企业名称是否唯一
			var flag=false;
			var store_id='${pd.STORE_ID}';
			var name=$("#Company_Name").val();
			$.ajax({
				async:false,
				type: "POST",
				url: '<%=basePath%>storePrivilege/hasName.do',
		    	data: {STORE_ID:store_id,name:name},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						layer.tips('该企业名称已存在', '#Company_Name', {
			 				tips: 3
	        			});
						flag=true;
					 }
				}
			});
			
			if(flag){
				return false;
			}
			
			//判断id是否唯一
			var store_id='${pd.STORE_ID}';
			var id=$("#pubwin_store_id").val();
			$.ajax({
				async:false,
				type: "POST",
				url: '<%=basePath%>storePrivilege/hasID.do',
		    	data: {id:id,STORE_ID:store_id},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						layer.tips('该门店id已存在', '#pubwin_store_id', {
			 				tips: 3
	        			});
						flag=true;
					 }
				}
			});
			
			if(flag){
				return false;
			}
			
			//判断服务商号格式,并判断该服务商编号是否存在
			var SERVICE_ID=$("#SERVICE_ID").val();
			if (SERVICE_ID !== "") {
			$.ajax({
				async:false,
				type: "POST",
				url: '<%=basePath%>storePrivilege/hasSERVICE_ID.do',
		    	data: {SERVICE_ID:SERVICE_ID},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						layer.tips('不存在的服务商编号', '#SERVICE_ID', {
			 				tips: 3
	        			});
						flag=true;
					 }
				}
			});
			
			if(flag){
				return false;
			}
			}
			
			
			//判断手机格式(手机号不为空时)
			if ($("#STORE_PHONE").val() !== "") {
				var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
					if($("#STORE_PHONE").val().length != 11 && !myreg.test($("#STORE_PHONE").val())){
						layer.tips('手机号格式不正确', '#STORE_PHONE', {
					 		tips: 3
			        	});
						return false;
					}else{
						if($("#code").val() == "") {
							layer.tips('请输入验证码', '#code', {
								tips : 3
							});
							return false;
						}
					}
			}	
			
			//判断验证码是否为空
			if ($("#code").val() == "") {
				layer.tips('请输入验证码', '#code', {
			 		tips: 3
	        	});
				return false;
			}
			
			/* 验证验证码是否正确 */
			if($("code").val()!=""||$("#code").val()!=null){ 
				var yzm = $.trim($("#code").val());
				var PHONE = $.trim($("#STORE_PHONE").val());
				var STORE_ID ='${pd.STORE_ID}';
				$.ajax({
					async:false,
					type: "POST",
					url: '<%=basePath%>storePrivilege/hasY.do',
			    	data: {yzm:yzm,PHONE:PHONE,STORE_ID:STORE_ID},
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.result == "code_invalid"){
							layer.tips('验证码已失效，请重新发送', '#code', {
				 				tips: 3
		        			});
							flag=true;
						}else if(data.result == "error"){
							layer.tips('输入验证码错误', '#code', {
				 				tips: 3
		        			});
							flag=true;
							
						}
					}
				});
			}
			
			if(flag){
				return false;
			}
			
			//判断是否选择套餐
			if ($("#CHOOSE_PACKAGE").val() == "") {
				layer.tips('请选择套餐', '.lanke-pay', {
					tips : 3
				});
				return false;
			}
			console.log($('#Form').serialize());

			layer.confirm('确定要提交吗？', {
				  btn: ['确定','再看看'] //按钮
				}, function(){
					$.ajax({
						type: "POST",
						url: "<%=basePath%>storePrivilege/save.do",
						data : $('#Form').serialize(),
						dataType : 'json',
						beforeSend: beforeSend(''),
						success : function(data) {
							layer.closeAll();
							if(data.result == "true"){
								opener.location.reload();//刷新父页面
								layer.open({
						  			type: 1,
						 			btn:["确定"],
						  			yes:function(){
						  				//关闭当前页面
										setTimeout(function(){ 
										//关闭当前页面
										window.opener = null;
										window.open("","_self");
										window.close();
										},500)
						  			},
						 			area: '450px',
						  			title:false,
						  			closeBtn: 0, //不显示关闭按钮
						  			shadeClose: false, //开启遮罩关闭
						  			content: '<div style="text-align:center;padding:68px">'+
						  						'<i class="iconfont" style="color:#1ab394;font-size:60px;">&#xe66a;</i>'+
						  						'<p style="padding-top:20px;">提交成功，审核结果将会在1-3个工作日内发至您的手机上，请耐心等待~</p>'+
						  					'</div>'
									});
							}else{
								message(data.message);
							}
						},
						error: function(){
							layer.closeAll();
							message("系统繁忙，请稍后再试！");
							return false;
						}
				});
			}, function() {
				layer.close();
			});
		}
	</script>
</body>
</html>