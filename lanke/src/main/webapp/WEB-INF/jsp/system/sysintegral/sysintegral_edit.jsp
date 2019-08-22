<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="sysintegral/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="INTEGRAL_ID" id="INTEGRAL_ID" value="${pd.INTEGRAL_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							
								<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">积分类型:</td>
								<td>	
								<select name="INTEGRAL_TYPE" id="INTEGRAL_TYPE"   style="width:98%;">
								  <option value="1" <c:if test="${pd.INTEGRAL_TYPE=='1'}">selected="selected"</c:if>>普通</option>
							     <%-- <option value="2" <c:if test="${pd.INTEGRAL_TYPE =='2'}">selected="selected"</c:if>>节假日 </option>
							      <option value="3" <c:if test="${pd.INTEGRAL_TYPE =='3'}">selected="selected"</c:if>>新店开张</option>--%>
							   </select>
							</td>
							</tr>
							 
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">积分类别:</td>
								<td>	
								<select name="CATEGRORY" id="CATEGRORY"   style="width:98%;">
								<option value="2" <c:if test="${pd.CATEGRORY =='2'}">selected="selected"</c:if>>签到 </option>
								<%--
									<option value="1" <c:if test="${pd.CATEGRORY=='1'}">selected="selected"</c:if>>充值</option>
							      <option value="3" <c:if test="${pd.CATEGRORY =='3'}">selected="selected"</c:if>>参赛</option>
							      <option value="4" <c:if test="${pd.CATEGRORY =='4'}">selected="selected"</c:if>>老带新 </option>
								 --%>  
							   </select>
							   <small style="font-size: 10px;">*活动主办方将在法律允许的范围内，对活动作出必要的说明和解释。如遇到不可抗拒因素，活动主办方拥有取消本次活动的权利</small>
							</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">奖励积分:<small style="font-size: 8px;">(整数)</small></td>
								<td><input type="number" name="INTEGRAL_SEND" id="INTEGRAL_SEND" value="${pd.INTEGRAL_SEND}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  maxlength="32" placeholder="这里输入奖励积分 必须为整数" title="奖励积分" style="width:98%;"/></td>
							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;" >状态:</td>
								<td><select name="STATE" id="STATE"  style="width:98%;" maxlength="4" placeholder="这里输入状态" title="状态">
								  <option <c:if test="${pd.STATE =='1'}">selected="selected"</c:if>>有效</option>
							      <option value="0" <c:if test="${pd.STATE =='1'}">selected="selected"</c:if>>无效 </option>
							    </select>
							</tr> --%>
							<tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">积分加倍:<small style="font-size: 8px;">(整数)</small></td>
								<td>
									<c:if test="${pd.WEEKEND_SEND== null }">
										<input type="radio" name="rdozhoumo" id="rdozhoumo" onclick="chuxian();">周末 
										<input type="number" name="WEEKEND_SEND" id="WEEKEND_SEND" style="display: none;" value="${pd.WEEKEND_SEND}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" maxlength="32" placeholder="这输入加倍时的奖励积分 必须为整数" title="加倍奖励积分" style="width:98%;"/>
										<input id="quxiao" type="button" value="取消" style="display: none">
									</c:if>
									<c:if test="${pd.WEEKEND_SEND !=null}">
										<input type="number" name="WEEKEND_SEND" id="WEEKEND_SEND" value="${pd.WEEKEND_SEND}" maxlength="32" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  placeholder="这输入加倍时的奖励积分 必须为整数" title="加倍奖励积分" style="width:98%;"/>
									</c:if>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						
					</form>
	
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$("#quxiao").click(function(){
			document.getElementById('WEEKEND_SEND').style.display = 'none';
			document.getElementById('quxiao').style.display = 'none';
			$("#rdozhoumo").attr("checked" , false);
		});
		
		function chuxian(){
			if (document.getElementById('rdozhoumo').checked == true) {
				document.getElementById('WEEKEND_SEND').style.display = 'block';
				document.getElementById('quxiao').style.display = 'block';
			} else {
				document.getElementById('WEEKEND_SEND').style.display = 'none';
			}
		}
		
		
		//新增
		function add(){
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>intintegral/addAll.do?tm='+new Date().getTime(),
				    	data: {},
						dataType:'json',
						cache: false,
						success: function(data){
	    		              alert(data.message);
	    		              if('${page.currentPage}' == '0'){
	    							 setTimeout("self.location=self.location",100);
	    						 }else{
	    							 nextPage(${page.currentPage});
	    						 }
						}
					});
		}
		
		$(top.hangge());
		//保存
		function save(){
			if($("#INTEGRAL_NAME").val()==""){
				$("#INTEGRAL_NAME").tips({
					side:3,
		            msg:'请输入积分名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#INTEGRAL_NAME").focus();
			return false;
			}
			if($("#INTEGRAL_TYPE").val()==""){
				$("#INTEGRAL_TYPE").tips({
					side:3,
		            msg:'请输入积分类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#INTEGRAL_TYPE").focus();
			return false;
			}
			if($("#INTEGRAL_SEND").val()==""){
				$("#INTEGRAL_SEND").tips({
					side:3,
		            msg:'请输入奖励积分',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#INTEGRAL_SEND").focus();
			return false;
			}
			if($("#STATE").val()==""){
				$("#STATE").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>