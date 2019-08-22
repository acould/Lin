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
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
<title>揽客特权对比</title>
<style>
</style>
</head>
<body style="background: #f6f7f8" class="lanke-vip">
	<div class="lanke-vipHead">
		<div class="lanke-vipNav">
			<div class="lanke-tab">
				<div class="lanke-vipNav-letf">
					<img src="<%=basePath%>newStyle/images/logo2.png" alt=""> <span>客服电话：4000965099</span>
				</div>
				<div class="lanke-vip-sele">
					<form action="" class="layui-form">
						<div class="layui-form-item">
							<label class="layui-form-label">更换门店</label>
							<div class="layui-input-block">
								<select name="modules" lay-verify="required" lay-search="" lay-filter="test" name="STORE_ID" id="STORE_ID">
									<c:if test="${storeList.size() == 0}">
										<option value="">直接选择或搜索选择</option>
									</c:if>
									<c:if test="${storeList.size() > 0}">
										<option value="${storeList[0].store_id}">${storeList[0].store_name}</option>
									</c:if>
									<c:forEach items="${storeList}" var="var" begin="1">
										<option value="${var.store_id}">${var.store_name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="lanke-vipNav-right">
					<i class="layui-icon">&#xe611;</i> <span>专业版正在火热上线，实现会员在线充值、精准营销</span>
				</div>
			</div>
		</div>
		<div class="lanke-tab lanke-vipContent">
			<div class="lanke-vip-msg">
				<h1>Hi，${pd.intenet_name}&nbsp;&nbsp;-&nbsp;&nbsp;<span id="lanke-store"></span>
				</h1>
				<h2 id="lanke-VSTATE"></h2>
				<h2 id="Through_Time"></h2>
				<h2 id="updatetime"></h2>
			<!-- 	<h2 id="EXPIRATION_TIME">
					<span style="padding-left: 30px" id="EXPIRATION_TIME"></span>
				</h2> -->

			</div>
			<div class="lanke-vip-btn">
				<p><span class="btn_type1">计费系统绑定状态：</span><span id="lanke-addV"></span></p>
				<p><span class="btn_type2">在线支付开通状态：</span><span id="lanke-bank"></span></p>
			</div>
		</div>
	</div>
	<div class="layui-form lanke-tab lk-vip-msg" style="height:auto;">
		<fieldset class="layui-elem-field layui-field-title" style="">
			<legend>功能特权对比</legend>
		</fieldset>
		<div class="lk-major-box">
			<div class="head">
				专业版<br><span>绑定银行账号+计费系统</span>
			</div>
			<div class="content">
				<div class="haveText">识别错误会员卡</div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div class="haveText">直充会员卡</div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
				<div><i class="lk-vipIcon-yes"></i></div>
			</div>
		</div>
		<table class="layui-table">
			<thead>
				<tr>
					<th class="lk-vip-th">功能名称</th>
					<th class="lk-vip-th">轻营销版<br><span>注册即开通</span></th>
					<th class="lk-vip-th">轻营销+V<br><span>绑定计费系统</span></th>
					<th class="lk-vip-th">专业版<br><span>绑定银行账号+计费系统</span></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>会员卡号识别</td>
					<td>绑定不识别</td>
					<td>识别错误会员卡</td>
					<td>识别错误会员卡</td>
				</tr>
				<tr>
					<td>公众号管理</td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>会员明细查询</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>会员智能分类</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>会员群发营销</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>在线申请会员</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>扫码上机</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>在线充值</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>基础优惠券</td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>上网满时长福利券</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>申请会员福利券</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>会员抵用券</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>现金券核销</td>
					<td>手动充值</td>
					<td>直充会员卡</td>
					<td>直充会员卡</td>
				</tr>
				<tr>
					<td>基础营销功能</td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>基础报表分析</td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
				<tr>
					<td>充值报表</td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-no"></i></td>
					<td><i class="lk-vipIcon-yes"></i></td>
				</tr>
			</tbody>
		</table>
		<div style="margin-top: 30px" class="layui-clear">
			<div class="lk-vip-base">
				<h3><i class="base1"></i>公众号管理</h3>
				<p>自定义菜单、自动回复、新建图文、揽客文章库</p>
			</div>
			<div class="lk-vip-base">
				<h3><i class="base2"></i>基础优惠券</h3>
				<p>通用券、新手券、老带新券、生日券、男神券、女神券、限时秒抢券</p>
			</div>
			<div class="lk-vip-base">
				<h3><i class="base3"></i>基础营销功能</h3>
				<p>优惠券、积分管理、抽奖管理、赛事管理、老带新、积分商城、会员留言</p>
			</div>
			<div class="lk-vip-base">
				<h3><i class="base4"></i>基础报表统计</h3>
				<p>会员报表、卡券报表、商品报表、兑奖报表</p>
			</div>
		</div>
		<div class="lanke-footer">
			© <a href="http://lanke8.cc">lanke8.cc</a>&nbsp;揽客
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
			      <div class="lk-flow-text">
			          	填写绑定信息</br>并付费
			      </div>
			      <div class="lk-flow-text">
			          	审核通过
			      </div>
			      <div class="lk-flow-text">
			         	 在审核通过页面中获取下载客户端链接以及激活码
			      </div>
			      <div class="lk-flow-text">
			         	 安装并使用揽客客户端
			      </div>
			  </div>
			  <div class="lk-flow-btn">
			      <span class="btn-close close-layer" >关闭</span>
			      <span class="btn-sure">确定</span>
			  </div>
		</div>
		<form action="toEdit" id="toShowFrom" name="toShowFrom" target="_blank" method="post">
			<input type="hidden" id="store_id" name="STORE_ID" value="">
			<input type="hidden" id="store_name" name="STORE_NAME" value="">
			<input type="hidden" id="state1" name="STATE" value="">
		</form>
		
		<form id="YH_FORM" method="post" target="_blank">
			<input type="hidden" id="idd" name="idd">
			<input type="hidden" id="store_idd" name="store_idd">
		</form>
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script src="<%=basePath%>newStyle/js/lk-account.js"></script>

<script>
	$(function(){
		var storeId='${storeList[0].store_id}';
		//初始化
		search('<%=basePath%>',storeId);
		layui.use([ "layer", "form", "element" ], function() {
			var layer = layui.layer, form = layui.form, $ = layui.jquery;
			form.render('select', 'test');
			//特权列表页面，下拉框监听
			form.on('select(test)', function(data) {
				search('<%=basePath%>',data.value);
			});
		})	
	})
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


</script>
</html>