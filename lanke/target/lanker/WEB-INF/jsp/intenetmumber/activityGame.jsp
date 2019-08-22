<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.ArrayList"%>
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
	<title>手气抽奖</title>
	<link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
	<link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
	<link rel="stylesheet" href="<%=basePath%>assets/css/base.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/zepto.min.js"></script>
	<link rel="stylesheet" href="<%=basePath%>assets/js/dialog.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/dialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/iscroll.js"></script>
	<link rel="stylesheet" href="<%=basePath%>assets/js/swiper-3.2.7.min.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/swiper-3.2.7.jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/mobile.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/js/awardRotate.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/js/choujiang.js"></script>
</head>

<body class="zhongjiang-bg">
	 <div class="activitygame">
        <div class="ml-main" id="ml-main" style="background-image:url(<%=basePath%>assets/images/returnbg.jpg);background-size:100% 100%;">
        <p class="p2">每次抽奖消耗<c:if test="${number > 0}">${number }</c:if><c:if test="${number == 0 }">30</c:if>积分</p>
            <div class="kePublic">
                <div style="max-width:640px; margin:0 auto">
                <input type="hidden" name="cj" id="cj" value="${cj}"/>
                    <div class="banner">
                        <div class="turnplate" style="background-image:url(<%=basePath%>assets/images/turnplate-bg_2.png);background-size:100% 100%;">
                            <canvas class="item" id="wheelcanvas" width="516" height="516"></canvas>
                            <img id="tupBtn" class="pointer" src="<%=basePath%>assets/images/turnplate-pointer_2.png"/>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
                <!-- <div class="silider-txt">
                    sharow中了奖项A
                </div> -->
		        <p class="p3" style="text-align: center;margin-top:20px;color:#fff;font-size:12px">*活动解释权归网咖所有</p>
            </div>
            
        </div>
	</div>
	 <script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
	 <script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
	 <script src="<%=basePath%>newStyle/js/lk_wechat/lk_wechat.js"></script>
	<script type="text/javascript">
        // //检查用户绑定数据是否正确
        // checkSWUser("activityGame");
        // //将当前页面状态放入历史中
        // pushHistory2();
        // //点击返回时，触发popstate
        // popstate2();


	var cjzl = new Array();
	var cj=$("#cj").val();
	cjzl=cj.split(",");
		var turnplate={
			restaraunts:cjzl,				//大转盘奖品名称
			colors:["#fbf4a0", "#96fbff","#ffbe96","#afff96","#f9c5ec", "#96f5d7","#fdbfbf", "#d6abff","#fbf4a0", "#96fbff"], //大转盘奖品区块对应背景颜色
			//fontcolors:[],				//大转盘奖品区块对应文字颜色
			outsideRadius:222,			//大转盘外圆的半径
			textRadius:165,				//大转盘奖品位置距离圆心的距离
			insideRadius:1,			//大转盘内圆的半径
			startAngle:0,				//开始角度
			bRotate:false				//false:停止;ture:旋转
		};
$(function(){
	var lock = true;
	/********抽奖开始**********/
	$('#tupBtn').click(function (){
		if(lock){
			lock = false;
			$.ajax({
				url:'<%=basePath %>indexMember/choujiang.do',
				type:'POST',
				data:{},
				async:false,
				success:function(data){
					if(data.message=="success"){
						if(turnplate.bRotate)return;
						turnplate.bRotate = !turnplate.bRotate;
						rotateFn(data.num,data.name,data.type);
						sessionStorage.setItem("need-refresh", true);
					}else{
						 $.alert("积分不够抽奖"); 
					}
				},
				error:function(){
					$.alert("抽奖错误");
				}
			})
		}
		//获取中奖信息接口
	})


	//旋转转盘 item:奖品位置; txt：提示语;
	var rotateFn = function (item,itemnae,type){
		var txt=turnplate.restaraunts[item-1]
		var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length*2));
		if(angles<270){
			angles = 270 - angles; 
		}else{
			angles = 360 - angles + 270;
		}
		$('#wheelcanvas').stopRotate();
		$('#wheelcanvas').rotate({
			angle:0,
			animateTo:angles+1800,
			duration:6000,
			callback:function (){
				var h1="",h2="",state='';
				lock = true;
				if(type=="0"){
					 h1="恭喜你";h2="获得"+itemnae;
					 state = '<a class="btns look" href="<%=basePath %>myMember/mySpoil">查看我的奖品</a><div class="btns again">再来一次</div>';
					 
				}else{
					 h1="很遗憾";h2="没有中奖";
					state = '<div class="btn again">再来一次</div>';
				}
				
				turnplate.bRotate = !turnplate.bRotate;
				$.confirm('<div class="tit orange">'+h1+'</div>'+
					'<div class="con">'+h2+'</div>'+state,[],null,{className:'ui-qiandao ui-choujiang showClose ui-alert',width:'270px',beforeShow:function(e){
						var _this=this;
						e.find('.again').click(function(){
							_this.hide();$('#tupBtn').click();
						})
						e.find('.go').click(function(){
							_this.hide();$.alert('请稍等')
						})	
					}});						
				
			}
		});
	};



});
	</script>
</body>

</html>

</html>
