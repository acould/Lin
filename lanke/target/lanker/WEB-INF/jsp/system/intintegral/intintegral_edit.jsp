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
	<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>Document</title>
    <style>
        .borPadding {
            padding:20px 30px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
        .layui-form-checkbox i {
        	height:30px;
        }
		section {
			margin-top: 20px;
			text-align: center;
			border-left: 1px solid #ececec;
			border-bottom: 1px solid #ececec;
			line-height:30px;
		}

		section .top {
			display: flex;
			background: #fbfbfb;
		}

		section .top .left {
			width: 16%;
			border-right: 1px solid #ececec;
			border-top: 1px solid #ececec;

		}

		section .right {
			flex: 1;
			display: flex;
			margin-bottom:0;
		}

		section .right li {
			flex: 1;
			list-style: none;
			border-right: 1px solid #ececec;
			border-top: 1px solid #ececec;
		}

		section .bottom {
			display: flex;
			background: #fbfbfb;
		}

		section .bottom .left {
			width: 16%;
			border-right: 1px solid #ececec;
			border-top: 1px solid #ececec;

		}
		section .bottom .right{
			margin-bottom:0;
		}

		section .bottom .right li {
			background: #fff
		}
		.foot {
			margin-top: 20px;
			font-size: 12px;
			line-height: 20px;
		}
		.INTEGRAL_SEND{
			width:100%;
			height:100%;
			border:0;
			background:#fff;
			text-align: center;
		}


	</style>

</head>
<body class="no-skin scroll">
<!-- /section:basics/navbar.layout -->
		<div class="borPadding">
					<form  class="layui-form layui-form-pane" action="intintegral/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="INTINTEGRAL_ID" id="INTINTEGRAL_ID" value="${pd.INTINTEGRAL_ID}"/>
						<div class="layui-form-item">
							<label class="layui-form-label" style="margin:0">奖励类别:</label>
							<div class="layui-input-block">
								<select name="CATEGRORY" id="CATEGRORY" style="width:98%;">
									<option value="2" <c:if test="${pd.CATEGRORY =='2'}">selected="selected"</c:if> class="xz">签到 </option>
									<%--
										<option value="1" <c:if test="${pd.CATEGRORY=='1'}">selected="selected"</c:if>>充值</option>
								      <option value="3" <c:if test="${pd.CATEGRORY =='3'}">selected="selected"</c:if>>参赛</option>
								      <option value="4" <c:if test="${pd.CATEGRORY =='4'}">selected="selected"</c:if>>老带新 </option>
									 --%>
									<option value="5" <c:if test="${pd.CATEGRORY =='5'}">selected="selected" disabled="disabled"</c:if> class="xz">扫码上机 </option>
								</select>

							</div>
						</div>
						<footer id="QIANDAO">
							<small style="font-size: 10px;" <c:if test="${pd.CATEGRORY =='5'}"></c:if>>*活动主办方将在法律允许的范围内，对活动作出必要的说明和解释。如遇到不可抗拒因素，活动主办方拥有取消本次活动的权利</small>
						<div class="layui-form-item">
							<label class="layui-form-label" style="margin:0">奖励积分:</label>
							<div class="layui-input-block">
								<input type="number" name="INTEGRAL_SEND" id="INTEGRAL_SEND" min="1" value="${pd.INTEGRAL_SEND}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
									   maxlength="32" placeholder="这里输入奖励积分" title="奖励积分" class="layui-input"/>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="margin:0" title="可选积分">可选积分:</label>
							<div class="layui-input-block">
								<c:if test="${pd.WEEKEND_SEND == null || pd.WEEKEND_SEND == '' }">
									<input type="checkbox" name="rdozhoumo" id="rdozhoumo" value="周末 " title="周末" lay-filter="weekend">
									<input type="number" name="WEEKEND_SEND" id="WEEKEND_SEND" style="display: none;width: 77%" min="0" value="${pd.WEEKEND_SEND}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
										   maxlength="32" placeholder="这输入周末积分，可选填" title="周末积分" class="layui-input" />

								</c:if>
								<c:if test="${pd.WEEKEND_SEND != null && pd.WEEKEND_SEND != '' }">
									<input type="checkbox" name="rdozhoumo" id="rdozhoumo" checked value="周末 " title="周末" lay-filter="weekend">
									<input type="number" name="WEEKEND_SEND" id="WEEKEND_SEND" value="${pd.WEEKEND_SEND}" min="0" maxlength="32" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
										   placeholder="这输入加倍时的奖励积分 必须为整数" title="加倍奖励积分" class="layui-input" style="width: 77%;display: inline-block;"/>

								</c:if>
							</div>
						</div>
						</footer>
					</form>
						<footer id="SAOMA">

							<section>
								<div class="top">
									<div class="left">累计扫码</div>
									<ul class="right">
										<li>第一天</li>
										<li>第二天</li>
										<li>第三天</li>
										<li>第四天</li>
										<li>第五天</li>
										<li>第六天</li>
										<li>第七天</li>
									</ul>
								</div>
								<div class="bottom">
									<div class="left">奖励积分</div>
									<ul class="right">
										<li><input name="INTEGRAL_SEND1" id="INTEGRAL_SEND0" min="1" value="${days.day1}" class="INTEGRAL_SEND"></li>
										<li><input name="INTEGRAL_SEND2" id="INTEGRAL_SEND1" min="1" value="${days.day2}" class="INTEGRAL_SEND"></li>
										<li><input name="INTEGRAL_SEND3" id="INTEGRAL_SEND2" min="1" value="${days.day3}" class="INTEGRAL_SEND"></li>
										<li><input name="INTEGRAL_SEND4" id="INTEGRAL_SEND3" min="1" value="${days.day4}" class="INTEGRAL_SEND"></li>
										<li><input name="INTEGRAL_SEND1" id="INTEGRAL_SEND4" min="1" value="${days.day5}" class="INTEGRAL_SEND"></li>
										<li><input name="INTEGRAL_SEND6" id="INTEGRAL_SEND5" min="1" value="${days.day6}" class="INTEGRAL_SEND"></li>
										<li><input name="INTEGRAL_SEND7" id="INTEGRAL_SEND6" min="1" value="${days.day7}" class="INTEGRAL_SEND"></li>
									</ul>
								</div>


							</section>
							<footer class="foot">
								*活动主办方将在法律允许的情况内,对活动做出必要的说明和解释,如遇到不可抗拒的因素因素，活动主办方拥有取消本次活动的权利
							</footer>


						</footer>

						

		</div>
<!-- /.main-container -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<script type="text/javascript">
		   // var options=$("#select option:selected");
		   var val=0;
		  setInterval(function(){
			   val=$("#CATEGRORY").val();
			  if(val==2){
				  $("#QIANDAO").css("display",'block')
				  $("#SAOMA").css("display","none")
			  }else{
				  $("#SAOMA").css("display","block")
				  $("#QIANDAO").css("display",'none')
			  };
		  },1000)

			var categ="${pd.CATEGRORY}";
			<%--console.log("day1"+${days.day1});--%>
			var days="${days.day1}";
			console.log(categ);
			console.log(days);
			var Days = ["${days.day1}","${days.day2}","${days.day3}","${days.day4}","${days.day5}","${days.day6}","${days.day7}"];
			console.log(Days);
			if(categ==2){
				$("#SAOMA").css("display","none");
				$("#QIANDAO").css("display","block");
			}else if(categ==5){
				$("#SAOMA").css("display","block");
				$("#QIANDAO").css("display","none");
			}
	layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate;

        form.on('checkbox(weekend)', function(data){
            chuxian();
        });
        
    });
   $("body").on("click","xz",function(){
   	$(".xz").removeAttr("selected");
   	$(this).attr("selected","selected");
   })
	function chuxian(){
		if (document.getElementById('rdozhoumo').checked == true) {
			document.getElementById('WEEKEND_SEND').style.display = 'inline-block';
			
		} else {
			$("#WEEKEND_SEND").val(0);
			document.getElementById('WEEKEND_SEND').style.display = 'none';
		}
	}
	$(top.hangge());
            //保存

            var baocun = function(){
                var data=$('#Form').serialize();
                console.log(data,'data');
                console.log(${pd.CATEGRORY});
                console.log(${days.day1});
                if("${pd.CATEGRORY}"==5||val==5){
                    //扫码上机

					console.log(Days);
					var Days={"day1":$("#INTEGRAL_SEND0").val(),"day2":$("#INTEGRAL_SEND1").val(),"day3":$("#INTEGRAL_SEND2").val(),"day4":$("#INTEGRAL_SEND3").val(),"day5":$("#INTEGRAL_SEND4").val(),"day6":$("#INTEGRAL_SEND5").val(),"day7":$("#INTEGRAL_SEND6").val(),}
					console.log(Days);
					var num = {
						"type":"edit",
						"CATEGRORY":5,
						"sign_time_set":Days

					};
					var jsonStr=JSON.stringify(num)
					console.log(jsonStr)
					$.ajax({
						type: "POST",
						url: '<%=basePath%>intintegral/signset.do',
						data: jsonStr,
						dataType:'json',
						contentType: "application/json;charset=utf-8",
						cache: false,
						success: function(data){
							console.log(data,'data')
							layer.msg('保存成功');
							// if(data.result == "true"){
								setTimeout(function () {
									parent.location.reload();
								},500)
							// }
						},
						error:function(){
							layer.msg("系统繁忙，请稍后再试！");
							return false;
						}
					});
        }else{
				if($("#INTEGRAL_SEND").val()=="" || $("#INTEGRAL_SEND").val() <= 0){
					layer.tips('请输入有效的奖励积分', '#INTEGRAL_SEND', {
						tips: 3
					});
					$("#INTEGRAL_SEND").focus();
					return false;
				}
				if($("#INTEGRAL_SEND").val() > 10000){
					layer.tips('奖励积分最大可设置为10000', '#INTEGRAL_SEND', {
						tips: 3
					});
					$("#INTEGRAL_SEND").val(10000);
					$("#INTEGRAL_SEND").focus();
					return false;
				}

				if(document.getElementById('rdozhoumo').checked == true){
					if($("#WEEKEND_SEND").val()== "" || $("#WEEKEND_SEND").val() < 0){
						layer.tips('请输入有效的周末积分', '#WEEKEND_SEND', {
							tips: 3
						});
						$("#WEEKEND_SEND").focus();
						return false;
					}

					if($("#WEEKEND_SEND").val() > 10000){
						layer.tips('周末积分最大可设置为10000', '#WEEKEND_SEND', {
							tips: 3
						});
						$("#WEEKEND_SEND").val(10000);
						$("#WEEKEND_SEND").focus();
						return false;
					}
				}
				$.ajax({
					type: "POST",
					url: '<%=basePath%>intintegral/saveIntegral.do',
					data: $('#Form').serialize(),
					dataType:'json',
					cache: false,
					success: function(data){
						console.log(data,'data')
						layer.msg(data.message);
						if(data.result == "true"){
							setTimeout(function () {
								parent.location.reload();
							},500)
						}
					},
					error:function(){
						layer.msg("系统繁忙，请稍后再试！");
						return false;
					}
				});
        }







	}

	</script>
</body>
</html>