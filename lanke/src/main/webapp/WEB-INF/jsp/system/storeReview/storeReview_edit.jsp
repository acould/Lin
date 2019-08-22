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
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
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
			<div class="lanke-pbMsgSide">
				<fieldset class="layui-elem-field layui-field-title" style="">
					<legend>绑定信息如下</legend>
				</fieldset>
				<form class="layui-form layui-form-pane" action="" name="Form1" id="Form1">
					<div class="layui-form-item">
						<label class="layui-form-label">计费系统：</label>
						<div class="layui-input-block">
							<input type="text" name="chargingtype" id="chargingtype"
								class="layui-input" value="${map.chargingtype=="P"?"Pubwin OL":"万象"}"
								placeholder="" readonly="readonly">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>企业名称：</label>
						<div class="layui-input-block">
							<input type="hidden" name="STORE_ID" id="STORE_ID" value="${pd.STORE_ID}" /> 
							<input type="hidden" name="STATE" id="STATE" value="${pd.STATE}" /> 
							<input type="text" name="Company_Name" id="Company_Name" class="layui-input" value="${map.Company_Name}"
								placeholder="请输入当前门店在计费Pubwin OL上注册的企业全称" readonly="readonly">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign">*</span>用户ID：</label>
						<div class="layui-input-block">
							<input type="text" name="pubwin_store_id" id="pubwin_store_id"
								class="layui-input" value="${map.pubwin_store_id}"
								placeholder="请输入当前门店Pubwin OL的ID" readonly="readonly">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign"></span>服务商编号：</label>
						<div class="layui-input-block">
							<input type="text" name="SERVICE_ID" id="SERVICE_ID"
								class="layui-input" value="${map.SERVICE_ID}"
								placeholder="未填写服务商编号" readonly="readonly">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="lanke-sign"></span>付费套餐：</label>
						<div class="layui-input-block">
						<c:if test="${map.CHOOSE_PACKAGE==0}">
						<input type="text" name="CHOOSE_PACKAGE" id="CHOOSE_PACKAGE"class="layui-input" value="试用一个月" readonly="readonly">
						</c:if>
						<c:if test="${map.CHOOSE_PACKAGE==1}">
						<input type="text" name="CHOOSE_PACKAGE" id="CHOOSE_PACKAGE"class="layui-input" value="付费一年" readonly="readonly">
						</c:if>
							<c:if test="${map.CHOOSE_PACKAGE==2}">
								<input type="text" name="CHOOSE_PACKAGE" id="CHOOSE_PACKAGE"class="layui-input" value="试用7天" readonly="readonly">
							</c:if>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">手机号码：</label>
						<div class="layui-input-block">
							<input type="text" name="STORE_PHONE" id="STORE_PHONE" class="layui-input" value="${map.STORE_PHONE}" placeholder="未填写手机号" readonly="readonly">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">网维产品：</label>
						<div class="layui-input-block">
							<input type="text" name="producttype" id="producttype"
								class="layui-input" value="${map.producttype=="T"?"是顺网产品（网维大师、讯闪、信佑、顺网雲等）":"不是"}"
								placeholder="" readonly="readonly">
						</div>
					</div>
				</form>
			</div>
			<div class="layui-form-item" style="margin: 20px 0 30px 20px">
			<c:if test="${pd.STATE=='0'}">
				<!-- 去加V审核 -->
				<button class="layui-btn layui-btn-normal" lay-submit=""
					lay-filter="demo2" onclick="return pass('${pd.STORE_ID}')">通过</button>
				<!-- 修改 -->
				<button class="layui-btn layui-btn-danger" lay-submit=""
					lay-filter="demo2" onclick="return fail('${pd.STORE_ID}')">不通过</button>
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
											<li>请点击下载安装<a href="https://pan.baidu.com/s/1OkiOa7s_W4DrM_qC-d8ttQ" target="_blank">揽客网吧客户端</a>，下载密码：ac3s
											</li>
											<li>按照客户端安装教程，用您的门店ID：<font color="red">${pd.STORE_ID}</font>进行激活，即可正式使用
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
	<!-- 通过加V审核所需信息栏-->
	<div class="pass" style="display: none;padding: 20px">
		<form class="layui-form layui-form-pane" action="" method="post" name="Form" id="Form">
				<div class="layui-form-item">
				    <div class="layui-inline">
					      <label class="layui-form-label" >到期时间：</label>
					      <div class="layui-input-inline">
					        <input type="text" name="EXPIRATION_TIME" class="layui-input" id="EXPIRATION_TIME" value="${pd.EXPIRATION_TIME}">
					        <input type="hidden" name="ODDS" id="ODDS" value="${pd.ODDS}" />
					      </div>
				    </div>
			    </div>
			    <div class="layui-form-item layui-form-text">
				      <label class="layui-form-label" >日志备注：</label>
				      <div class="layui-input-block">
				         <textarea placeholder="请输入内容" id="Note" name="Note" class="layui-textarea" value="信息完善">信息完善</textarea>
				         <input type="hidden" id="STORE_ID" name="STORE_ID" value="${pd.STORE_ID}"> 
				         <input type="hidden" name="STATE" id="STATE" value="${pd.STATE}" /> 
				         <input type="hidden" id="DSTATE" name="DSTATE" value="1">
				      </div>
				</div>
		</form>
	</div>

	<!-- 未通过加V审核原因-->
	<div class="FAIL" style="display: none;padding: 20px">
		<form class="layui-form layui-form-pane" action="" method="post" name="FORM" id="FORM">
			<div class="layui-form-item layui-form-text">
			      <label class="layui-form-label" >不通过原因：</label>
			      <div class="layui-input-block">
			         <textarea placeholder="请输入内容" id="Note" name="Note" class="layui-textarea" value="信息不正确">信息不正确</textarea>
			         <input type="hidden" id="STORE_ID" name="STORE_ID" value="${pd.STORE_ID}"> 
			         <input type="hidden" name="STATE" id="STATE" value="${pd.STATE}" /> 
			         <input type="hidden" id="DSTATE" name="DSTATE" value="0">
			      </div>
			</div>
		</form>
	</div>

	<script>
	layui.use(["form","layer","laydate"],function(){
		  var form = layui.form
		  ,layer = layui.layer
		  ,laydate = layui.laydate;
		
		  //日期时间选择器
		  laydate.render({
		    elem: '#EXPIRATION_TIME'
		   
		  });
	})
			//通过门店加V审核(通过)
	       function pass(STORE_ID){
				if($("#EXPIRATION_TIME").val() == null){
					layer.msg("请选择到期时间");
					return false;
				}

	    	   layer.open({
	    		   btn: ['确定','取消'],
	    		   yes: function(index,layero){
	    			   var lock = false; //默认未锁定
	    			   layer.close(index);
	    			   layer.confirm('确定要通过该门店加V审核？', {
	    				   btn: ['确定','取消'] //按钮
	    				 }, function(){
	    					 if(!lock) {
	    	    	                lock = true; // 锁定
	    	    			   $.ajax({
	    	    	                 type: "POST",
	    	    	                 url: "<%=basePath%>storeReview/review.do",
								     data : $('#Form').serialize(),
								     dataType : 'json',
								     beforeSend: function () {  
									    	 layer.load(0, {
								        		  shade: [0.1,'#fff'] //0.1透明度的白色背景
								        	});
							            }, 
								     success : function(data) {
								    	 layer.msg(data.message, {
												time :data.message.length * 200
											}, function() {
												if ("true" == data.result) {
													layer.closeAll(index)
													setTimeout(function() {
														//关闭当前页面
														window.opener = null;
														window.open("", "_self");
														window.close();
													}, 700);
													opener.location.reload();//刷新父页面
												}
											});
								},
								error : function() {
									layer.closeAll(index) 
									layer.msg("系统繁忙，请稍后再试！");
									return false;
								}
							});
	    	    	       }
	    				 }, function(){
	    					 layer.closeAll(index);
	    				 });
				},
				type : 1,
				title : '请填写以下信息',
				skin : "demo-class",
				shadeClose : false,
				shade : 0.8,
				area : [ '438px', '358px' ],
				content : $('.pass')
			});
		}
		
	     //通过门店加V审核(不通过)
	       function fail(STORE_ID){
	    	   layer.open({
	    		   btn: ['确定','取消'],
	    		   yes: function(index,layero){
	    			   var lock = false; //默认未锁定
	    			   layer.close(index);
	    			   layer.confirm('确定不通过该门店加V审核？', {
	    				   btn: ['确定','取消'] //按钮
	    				 }, function(){
	    					 if(!lock) {
	    	    	                lock = true; // 锁定
	    	    			   $.ajax({
	    	    	                 type: "POST",
	    	    	                url: "<%=basePath%>storeReview/review.do",
									data : $('#FORM').serialize(),
									dataType : 'json',
									beforeSend: function () {  
								    	 layer.load(0, {
							        		  shade: [0.1,'#fff'] //0.1透明度的白色背景
							        	});
						            }, 
									success : function(data) {
										layer.closeAll(index)
										layer.msg(data.message, {
											time : data.message.length * 200
										}, function() {
											if ("true" == data.result) {
												setTimeout(function() {
													//关闭当前页面
													window.opener = null;
													window.open("", "_self");
													window.close();
												}, 700);
												opener.location.reload();//刷新父页面
											}
										});
									},
									error : function() {
										layer.msg("系统繁忙，请稍后再试！");
										return false;
									}
								});
		    	    	       }
		    				 }, function(){
		    					 layer.closeAll(index);
		    				 });
				},
				type : 1,
				title : '请填写不通过的原因',
				skin : "demo-class",
				shadeClose : false,
				shade : 0.8,
				area : [ '435px', '348px' ],
				content : $('.FAIL')
			});
		}
	    
	</script>
</body>
</html>