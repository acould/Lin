<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-touch-fullscreen" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="address=no">
	<meta name="screen-orientation" content="portrait">
	<meta name="x5-orientation" content="portrait">
	<title>${intenetName}</title>
	<!--移动端适配，px转化为rem-->
	<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>

<body class="weic weic-body-bgf3f3f3 weic-parent-mar">
<input type="hidden" value="${pd.team_number}" id="team_number">
<form id="Form">
    <input type="hidden" id="matches_id" name="matches_id" value="${pd.matches_id}">
	<div class="weui-cells__title">仔细填写报名信息，才能顺利参赛哦！</div>
	<div class="weui-cells weui-cells_form">
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">战队名称</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" type="text" id="team_name" name="team_name" placeholder="请输入战队名称"
                       data-group-state="false" data-isblur="msg.name($(this))">
			</div>
		</div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">战队简介</label></div>
            <div class="weui-cell__bd">
                <textarea class="weui-textarea" rows="2" id="team_description" name="team_description"
                          placeholder="请输入战队简介" maxlength="20"></textarea>
            </div>
        </div>
	</div>
	<div class="weui-cells weui-cells_form">
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label" style="color: red">队长姓名</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" type="text" name="name" placeholder="请输入队长姓名"
                       data-group-state="false" data-isblur="msg.name($(this))">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">手机号</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" id="phone" name="phone" type="text" placeholder="请输入队长手机号"
                       data-isblur="msg.phone($(this))" data-group-state="false">
			</div>
		</div>
        <div class="weui-cell" id="isYzmShow">
            <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="text" id="yzm" name="yzm" maxlength="6"
                       data-isblur="msg.code($(this))"  data-group-state="false" placeholder="请输入验证码">
                <a class="btn-send" data-url="<%=basePath%>intenetmumber/getcode.do" id="getcode">获取验证码</a>
            </div>
        </div>
	</div>
	<div id="team_member">

	</div>


	<div style="margin:1rem 0;padding:0 0.4rem">
		<div class="weic-dent-btn weic-btn-gradientBlue" onclick="save('add')" id="save">确定</div>
	</div>
</form>
</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/roomes.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/mobile-check.js"></script>
<script type="text/javascript">
	var url = '<%=basePath%>wxMatches/buildTeam.do';
	var team_number = $("#team_number").val();
    var htmls = "";
	for (var i = 1; i < team_number; i++) {
		htmls += 	'<div class="weui-cells weui-cells_form">'+
				'<div class="weui-cell">'+
				'<div class="weui-cell__hd"><label class="weui-label"  style="color: #2283e5 ">队员姓名</label></div>'+
		'<div class="weui-cell__bd">'+
				'<input class="weui-input" type="text"  name="name" placeholder="请输入姓名" data-group-state="false" data-isblur="msg.name($(this),'+i+')">'+
				'</div>'+
				'</div>'+
				'<div class="weui-cell">'+
				'<div class="weui-cell__hd"><label class="weui-label">手机号</label></div>'+
		'<div class="weui-cell__bd">'+
				'<input class="weui-input" name="phone" type="text" placeholder="请输入手机号" data-isblur="msg.phone($(this),'+i+')" data-group-state="false">'+
				'</div>'+
				'</div>'+
				'</div>'
	}
	$("#team_member").html(htmls);

	function save(type){
		if( msg.checkstate()){//检查所有状态
			open_loading();
			$("#save").attr("onclick"," ");
			var data = $('#Form').serialize();
			if(type != "add"){  //修改操作
				url = ""
			}

            $.post(url, data, function (res) {

                layer.closeAll();
                message(res.errmsg);
                if (res.errcode == "0") {
                    setTimeout(function () {
                        window.location.href = "<%=basePath%>wxMatches/goMyEnroll"
                    }, 800);
                } else {
                    $("#save").attr("onclick", "save('add')");
                }
            });
		}
	}


    //初始化我的报名
    loadMyEnroll();
    function loadMyEnroll() {
        open_loading();
        var url = '<%=basePath%>wxMatches/loadMyEnroll.do';
        var field = new Object();
        field.matches_id = $("#matches_id").val();
        $.post(url, field, function (res) {
            layer.closeAll();
            if (res.errcode == 0) {
                console.log(res);

                var list = res.data.list;
                var data = res.data.pd;

                $("#team_name").val(data.team_name);
                $("#team_description").val(data.team_description);

                var i = 0;
                $("input[name='name']").each(function(index,item){
                    $(this).val(list[i].name);
                    i++;
                });
                i = 0;
                $("input[name='phone']").each(function(index,item){
                    $(this).val(list[i].phone);
                    i++;
                });

                $("#save").html("修改");

                if(data.status != 1){
                    $("#isYzmShow").remove();
                    $("#save").remove();
                }
            } else {
                // message(res.errmsg);
            }
        });
    }

    <%--var backHistoryUrl = '<%=basePath %>wxMatches/goGameDetail.do?matches_id='+$("#matches_id").val();--%>

</script>

<%--<script src="<%=basePath%>newStyle/weixin/js/backHistory.js"></script>--%>
</html>
