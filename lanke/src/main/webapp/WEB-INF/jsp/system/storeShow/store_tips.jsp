<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->
    <%@ include file="../index/top.jsp" %>
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <style>
        .borPadding {
            padding: 20px 30px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
		.tab-input-width{
			100px;
		}
        body {
            overflow: hidden
        }
        .layui-form-item .layui-inline {margin-bottom:0px;}
    </style>
</head>
<body class="no-skin scroll">
<div class="borPadding">
    <form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="<%=basePath%>storeShow/${msg}.do">
        <input type="hidden" name="STORE_ID" id="STORE_ID" value="${pd.store_id}"/>
        <div class="layui-tab layui-tab-card" style="box-shadow:none">
            <ul class="layui-tab-title">
            <c:if test="${pd.state != 'marke'}">
                <li class="layui-this" id="consumeId" <c:if test="${pd.memberId == 'consumeId'}">class="layui-this"</c:if>>消费能力</li>
            </c:if>
             <c:if test="${pd.state == 'marke'}">
                <li id="consumeId" <c:if test="${pd.memberId == 'consumeId'}">class="layui-this"</c:if>>消费能力</li>
            </c:if>      
                <li id="liveId" <c:if test="${pd.memberId == 'liveId'}">class="layui-this"</c:if>>活跃度</li>
                <li id="amountId" <c:if test="${pd.memberId == 'amountId'}">class="layui-this"</c:if>>会员卡余额</li>
            </ul>
            <%--消费--%>
            <div class="layui-tab-content" style="height: 280px;">
                <div <c:if test="${pd.state != 'marke'}">class="layui-tab-item layui-show"</c:if> <c:if test="${pd.state == 'marke'}">class="${pd.memberId == 'consumeId'?'layui-tab-item layui-show':'layui-tab-item'}"</c:if> id="consumeId2">
                	<div class="layui-form-item lanke-bd-lf">
	                	<label>该会员半年内在您门店的平均消费金额。</label><BR>
	                    <label style="margin-bottom:0; font-weight:normal;">您可以修改您认为合适的最高、最低消费金额。</label>
                	</div>
                	<div class="layui-form-item">
	                	<label class="" style="padding-right: 10px">消费能力高:</label>
	                    <div class="layui-inline">  
	                                             平均消费金额高于<input type="number" name="consume_high" id="consume_high" min="0" required lay-verify="required" autocomplete="off" maxlength="3" class="tab-input-width" value="${pd.consume_high !=null?pd.consume_high:50}" 
							onkeyup="put(this)" onchange="put(this)" style="margin-left: 15px;">
	                    </div>
                	</div>
                    <div class="layui-form-item">
                    	<label class="" style="padding-right: 10px">消费能力中:</label>
                    	<div class="layui-inline">
	                                             平均消费金额在<input type="number" style="background-color: WhiteSmoke;width: 80px; margin-left: 29px;" name="consume_mid" id="consume_mid_bef" min="1" readonly required
	                               lay-verify="required" autocomplete="off" maxlength="3" class="tab-input-width" value="${pd.consume_high !=null?pd.consume_high:50}">
	                        -
	                        <input type="number" style="background-color: WhiteSmoke;width: 80px;margin-right: 15px;" name="consume_mid" id="consume_mid_aft" min="1" readonly required
	                               lay-verify="required" autocomplete="off" maxlength="3" class="tab-input-width" value="${pd.consume_low !=null?pd.consume_low:20}">之间
                    	</div>
                    </div>
                    <div class="layui-form-item">
                    	<label class="" style="padding-right: 10px">消费能力低:</label>
	                    <div class="layui-inline">
	                                             平均消费金额低于<input type="number" name="consume_low" id="consume_low" required lay-verify="required" min="0"
	                               autocomplete="off" maxlength="3" class="tab-input-width"
	                                value="${pd.consume_low !=null?pd.consume_low:20}"
	                               onkeyup="put(this)" onchange="put(this)" style="margin-left: 15px;">
	                    </div>
                    </div>
                </div>
                
                <%--活跃--%>
                <div  class="${pd.memberId == 'liveId'?'layui-tab-item layui-show':'layui-tab-item'}" id="liveId2">
                	<div class="layui-form-item lanke-bd-lf1">
	                	<label>该会员半年内到您门店的平均间隔时间。</label><BR>
	                    <label style="margin-bottom:0;font-weight:normal;">您可以修改您认为合适的最高、最低间隔时间。</label>
                	</div>
                	<div class="layui-form-item"> 	
	                	<label class="" style="padding-right: 10px">活跃度高:</label>
	                    <div class="layui-inline">
	                                             平均间隔时间小于<input type="number" name="live_high" id="live_high" min="0" required 
	                               lay-verify="required" autocomplete="off" maxlength="3" class="tab-input-width"
	                            value="${pd.live_high !=null?pd.live_high:7}"
	                               onkeyup="put(this)" onchange="put(this)" style="margin-left: 15px;">
	                    </div>
                	</div>
                	<div class="layui-form-item">
	                	<label style="padding-right: 10px">活跃度中:</label>
	                    <div class="layui-inline">
	                                             平均间隔时间在<input type="number" style="background-color: WhiteSmoke;width: 80px;margin-left: 29px;" name="live_mid" id="live_mid_bef" min="1" readonly required
	                               lay-verify="required" autocomplete="off" maxlength="3" class="tab-input-width" value="${pd.live_low !=null?pd.live_low:30}">
	                      	-
	                        <input type="number" style="background-color: WhiteSmoke;width: 80px;margin-right: 15px;" name="live_mid" id="live_mid_aft" min="1" readonly required
	                               lay-verify="required" autocomplete="off" maxlength="3" class="tab-input-width" value="${pd.live_high !=null?pd.live_high:7}">之间
	                    </div>
                	</div>
                	<div class="layui-form-item">
	                	<label class="" style="padding-right: 10px">活跃度低:</label>
	                    <div class="layui-inline">
	                                             平均间隔时间大于<input type="number" name="live_low" id="live_low" required lay-verify="required"
	                                   autocomplete="off" maxlength="3" class="tab-input-width"
	                    value="${pd.live_low !=null?pd.live_low:30}" onkeyup="put(this)" onchange="put(this)" min="0" style="margin-left: 15px;">
	                    </div>
                	</div>
                </div>
                
                <%--余额--%>
                <div  class="${pd.memberId == 'amountId'?'layui-tab-item layui-show':'layui-tab-item'}" id="amountId2">
                	<div class="layui-form-item lanke-bd-lf2">
	                	<label>该会员卡当前的实际余额。</label><BR>
	                    <label style="margin-bottom:0;font-weight:normal;">您可以修改您认为合适的最高、最低余额。</label>
                	</div>
                	<div class="layui-form-item">
                		<label class="" style="padding-right: 10px">余额高:</label>
	                    <div class="layui-inline">
	                                             当前余额高于<input type="number" name="balance_high" id="balance_high" min="0" required
	                                   lay-verify="required" autocomplete="off" maxlength="3" class="tab-input-width"
	                                value="${pd.balance_high !=null?pd.balance_high:100}" onkeyup="put(this)" onchange="put(this)" style="margin-left: 15px;">
	                    </div>
                	</div>
                    <div class="layui-form-item">
                    	<label class="" style="padding-right: 10px">余额中:</label>
	                    <div class="layui-inline">
	                                              当前余额在<input type="number" style="background-color: WhiteSmoke;width: 80px;margin-left: 29px;" name="balance_mid" id="balance_mid_bef" min="1" readonly required
	                               lay-verify="required" autocomplete="off" maxlength="3" class="tab-input-width" value="${pd.balance_high !=null?pd.balance_high:100}">
	                        -
	                        <input type="number" style="background-color: WhiteSmoke;width: 80px;margin-right: 15px;" name="balance_mid" id="balance_mid_aft" min="1" readonly required
	                               lay-verify="required" autocomplete="off" maxlength="3" class="tab-input-width" value="${pd.balance_low !=null?pd.balance_low:20}">之间
	                    </div>
                    </div>
                    <div class="layui-form-item">
                    	<label class="" style="padding-right: 10px">余额低:</label>
	                    <div class="layui-inline">
	                                              当前余额低于<input type="number" name="balance_low" id="balance_low" required lay-verify="required"
	                               autocomplete="off" maxlength="3"  min="0" class="tab-input-width"
	                                value="${pd.balance_low !=null?pd.balance_low:20}"
	                               onkeyup="put(this)" onchange="put(this)" style="margin-left:15px;">
	                    </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- 页面底部js¨ -->
<%@ include file="../index/foot.jsp" %>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
<!-- 下拉框 -->
<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script type="text/javascript">

    function put(_this) {
        if (_this.id == 'consume_high') {
            $('#consume_mid_bef').val(_this.value);
        } else if (_this.id == 'consume_low') {
            $('#consume_mid_aft').val(_this.value);
        }else if (_this.id == 'live_high') {
            $('#live_mid_aft').val(_this.value);
        }else if (_this.id == 'live_low') {
            $('#live_mid_bef').val(_this.value);
        }else if (_this.id == 'balance_high') {
            $('#balance_mid_bef').val(_this.value);
        }else if (_this.id == 'balance_low') {
            $('#balance_mid_aft').val(_this.value);
        }
        
    }

    
    
    function onlyNonNegative(obj) {  
    	 var inputChar = event.keyCode;  
    	   
    	 //1.判断是否有多于一个小数点  
    	 if(inputChar==190 ) {//输入的是否为.  
    	  var index1 = obj.value.indexOf(".") + 1;//取第一次出现.的后一个位置  
    	  if(index1==1){//如果第一个值就是点
    	obj.value = obj.value.replace(/[^\d]/g,'')
    	  }
    	  var index2 = obj.value.indexOf(".",index1);  
    	  while(index2!=-1) {  
    	   //alert("有多个.");  
    	     
    	   obj.value = obj.value.substring(0,index2);  
    	   index2 = obj.value.indexOf(".",index1);  
    	  }  
    	 }  
    	 //2.如果输入的不是.或者不是数字，替换 g:全局替换  
    	 obj.value = obj.value.replace(/[^\d.]/g,'');  
    }
    
    
    //保存
    var save = function () {
    	var state='${pd.state}';
        if(!check()){
			return false;
		}
		
		$.ajax({
			type: "POST",
			url: "<%=basePath%>storeShow/tips.do",
			data: $('#Form').serialize(),
			dataType: 'json',
			async: false,
			success: function (data) {
				layer.msg(data.message, {time: 1000});
				if (data.result == "true") {
					if(state !='marke'){
					setTimeout(function () {
					parent.location.reload();//刷新父页面	       
					},700);
					}else{
						parent.layer.closeAll();
						msg=true;
					}
				}
			},
			error: function () {
				layer.msg("系统繁忙，请稍后再试！");
				return false;
			}
		});
		if(msg){
		var data = {msg : true};
	    return data;
		}
    }
	
	function check(){
		var consumeHigh = Number($('#consume_high').val());
        var consumeLow = Number($('#consume_low').val());
        var liveHigh = Number($('#live_high').val());
        var liveLow = Number($('#live_low').val());
        var balanceHigh = Number($('#balance_high').val());
        var balanceLow = Number($('#balance_low').val());

        if(consumeHigh<0 || consumeLow<0){
			error("消费能力不能小于0");
			$(".layui-tab-title li").removeClass("layui-this");
			$("#consumeId").addClass("layui-this");
			$(".layui-tab-content div").removeClass("layui-show");
			$("#consumeId2").addClass("layui-show");
            return false;
        }else if(consumeHigh <= consumeLow){
			error("消费能力：请输入有效范围");
			$(".layui-tab-title li").removeClass("layui-this");
			$("#consumeId").addClass("layui-this");
			$(".layui-tab-content div").removeClass("layui-show");
			$("#consumeId2").addClass("layui-show");
            return false;
		}
        
        if(liveHigh<1 || liveLow<1){
			error("活跃度必须大于1");
			$(".layui-tab-title li").removeClass("layui-this");
			$("#liveId").addClass("layui-this");
			$(".layui-tab-content div").removeClass("layui-show");
			$("#liveId2").addClass("layui-show");
            return false;
        }else if(liveHigh>=liveLow){
			error("活跃度：请输入有效范围");
			$(".layui-tab-title li").removeClass("layui-this");
			$("#liveId").addClass("layui-this");
			$(".layui-tab-content div").removeClass("layui-show");
			$("#liveId2").addClass("layui-show");
            return false;
		}
        
        if(balanceHigh<0 || balanceLow<0){
			error("会员卡余额不能小于0");
			$(".layui-tab-title li").removeClass("layui-this");
			$("#amountId").addClass("layui-this");
			$(".layui-tab-content div").removeClass("layui-show");
			$("#amountId2").addClass("layui-show");
            return false;
        }else if(balanceHigh<=balanceLow){
			error("会员卡余额：请输入有效范围");
			$(".layui-tab-title li").removeClass("layui-this");
			$("#amountId").addClass("layui-this");
			$(".layui-tab-content div").removeClass("layui-show");
			$("#amountId2").addClass("layui-show");
            return false;
		}
		return true;
	}

</script>
</body>
</html>