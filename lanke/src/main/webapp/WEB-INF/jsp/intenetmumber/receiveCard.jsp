<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="address=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>会员福利</title>
<!--移动端适配，px转化为rem-->
<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
<link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<style>
body {
	background: #6191f7;
}
</style>
<body class="weic card">
	<img src="<%=basePath%>newStyle/weixin/images/cardlist_banner.png"
		alt="" width="100%" style="display: block">
	<!-- 检索  -->
	<form action="<%=basePath%>indexMember/benefits.do" method="post" name="Form" id="Form">
		<div class="weui-row weui-no-gutter">
			<c:if test="${pd.FAV_TYPE == ''}">
				<div class="weui-col-33 active" onclick="cardType(this,'')">
					<div class="weic-navbar-text">全部卡券</div>
				</div>
			</c:if>
			<c:if test="${pd.FAV_TYPE != ''}">
				<div class="weui-col-33" onclick="cardType(this,'')">
					<div class="weic-navbar-text">全部卡券</div>
				</div>
			</c:if>
			<c:if test="${pd.FAV_TYPE == 'GRAB'}">
				<div class="weui-col-33 active" onclick="cardType(this,'GRAB')">
					<div class="weic-navbar-text">限时秒抢</div>
				</div>
			</c:if>
			<c:if test="${pd.FAV_TYPE != 'GRAB'}">
				<div class="weui-col-33" onclick="cardType(this,'GRAB')">
					<div class="weic-navbar-text">限时秒抢</div>
				</div>
			</c:if>
			<c:if test="${pd.FAV_TYPE == 'TERM'}">
				<div class="weui-col-33 active" onclick="cardType(this,'TERM')">
					<div class="weic-navbar-text">满时长送</div>
				</div>
			</c:if>
			<c:if test="${pd.FAV_TYPE != 'TERM'}">
				<div class="weui-col-33" onclick="cardType(this,'TERM')">
					<div class="weic-navbar-text">满时长送</div>
				</div>
			</c:if>
			<input type="hidden" id="FAV_TYPE" name="FAV_TYPE"
				value="${pd.FAV_TYPE}">
		</div>
		<div style="padding-bottom: 0.4rem">
			<c:choose>
				<c:when test="${not empty varList}">
					<c:forEach items="${varList}" var="var" varStatus="vs">
						<div class="weic-cardlistbox weic-bgfff">
							<a class="weui-cell weic-relative" href="javasrcipt:">
								<div class="weui-cell__hd weic-card-img">
									<c:if test="${var.FAV_TYPE == 'CURREN'}">
										<img src="<%=basePath%>newStyle/weixin/images/card/img_9.png" alt="" width="100%">
									</c:if>
									<c:if test="${var.FAV_TYPE == 'NEW'}">
										<img src="<%=basePath%>newStyle/weixin/images/card/img_4.png" alt="" width="100%">
									</c:if>
									<c:if test="${var.FAV_TYPE == 'OLD'}">
										<img src="<%=basePath%>newStyle/weixin/images/card/img_6.png" alt="" width="100%">
									</c:if>
									<c:if test="${var.FAV_TYPE == 'MAN'}">
										<img src="<%=basePath%>newStyle/weixin/images/card/img_5.png" alt="" width="100%">
									</c:if>
									<c:if test="${var.FAV_TYPE == 'WEN'}">
										<img src="<%=basePath%>newStyle/weixin/images/card/img_1.png" alt="" width="100%">
									</c:if>
									<c:if test="${var.FAV_TYPE == 'BIRTH'}">
										<img src="<%=basePath%>newStyle/weixin/images/card/img_7.png" alt="" width="100%">
									</c:if>
									<c:if test="${var.FAV_TYPE == 'GRAB'}">
										<img src="<%=basePath%>newStyle/weixin/images/card/img_8.png" alt="" width="100%">
									</c:if>
									<c:if test="${var.FAV_TYPE == 'APPLY'}">
										<img src="<%=basePath%>newStyle/weixin/images/card/img_3.png" alt="" width="100%">
									</c:if>
									<c:if test="${var.FAV_TYPE == 'TERM'}">
										<img src="<%=basePath%>newStyle/weixin/images/card/img_2.png" alt="" width="100%">
									</c:if>
								</div>
								<div class="weui-cell__bd">
									<div class="weic-card-msg">
										<p class="weic-card-title">${var.SUB_TITLE}</p>
										<p class="weic-card-time">
											<c:if test="${var.TYPE == 'DATE_TYPE_FIX_TIME_RANGE' }">
												<!-- 固定日期 -->
								有效期：${var.BEGIN_TIMESTAMP }至${var.END_TIMESTAMP }
							</c:if>
											<c:if test="${var.TYPE == 'DATE_TYPE_FIX_TERM' }">
												<!-- 固定时长 -->
								有效期：<c:if test="${var.FIXED_BEGIN_TERM == '0'}">领取后当天生效,</c:if>
												<c:if test="${var.FIXED_BEGIN_TERM != '0'}">领取后${var.FIXED_BEGIN_TERM }天生效,</c:if>领取后${var.FIXED_TERM}天内有效
							</c:if>
										</p>
										<p class="weic-card-time">
											适用门店：
											<c:forEach items="${var.sList}" var="sList" varStatus="vs">
								${sList.STORE_NAME}
								<c:if test="${!vs.last}">,</c:if>
											</c:forEach>
										</p>
									</div>
									<c:if test="${var.FAV_TYPE == 'GRAB'}">
										<c:if test="${var.cardState == 3}">
											<p class="weic-card-tips">${var.garbStart_time}开抢</p>
										</c:if>
										<c:if test="${var.cardState == 5}">
											<p class="weic-card-tips">正在抢</p>
										</c:if>
									</c:if>
									<c:if test="${var.FAV_TYPE == 'TERM'}">
										<c:if test="${var.cardSum_type == 2}">
											<p class="weic-card-tips">一周上网时长满 ${var.cardSum_time}小时可领</p>
										</c:if>
										<c:if test="${var.cardSum_type == 3}">
											<p class="weic-card-tips">一月上网时长满 ${var.cardSum_time}小时可领</p>
										</c:if>
									</c:if>
								</div>
								<div class="weui-cell__ft">
									<p class="weic-card-repertory">
										<font color="red">${var.QUANTITY }</font>/${var.cardSum + var.QUANTITY }
									</p>
									
									<c:if test="${var.FAV_TYPE == 'GRAB'}">
									    <c:if test="${var.cardState != 5}">
										<div class="weic-card-btn weic-btn-gradientGray">
											<span>
											<c:if test="${var.cardState == 3}">即将开枪</c:if>
											<c:if test="${var.cardState == 4}">已结束</c:if>
											</span>
										</div>
										</c:if>
										
										<c:if test="${var.cardState == 5}">
											<div class="weic-card-btn weic-btn-gradientOrange"
												onclick="openCard('${var.SUB_TITLE}','${var.NOTICE}','${var.DESCRIPTION }','${var.CONTENT}','${var.CARD_ID}','${var.BEGIN_TIMESTAMP }','${var.END_TIMESTAMP }','${var.FIXED_BEGIN_TERM }','${var.FIXED_TERM}','${var.TYPE}','${var.store}','${var.FAV_TYPE}')">
												<span>抢券</span>
											</div>
										</c:if>
									</c:if>
									
									<c:if test="${var.FAV_TYPE != 'GRAB'}">
										<div class="weic-card-btn weic-btn-gradientOrange"
											onclick="openCard('${var.SUB_TITLE}','${var.NOTICE}','${var.DESCRIPTION }','${var.CONTENT}','${var.CARD_ID}','${var.BEGIN_TIMESTAMP }','${var.END_TIMESTAMP }','${var.FIXED_BEGIN_TERM }','${var.FIXED_TERM}','${var.TYPE}','${var.store}','${var.FAV_TYPE}')">
											<span>领取</span>
										</div>
									</c:if>
								</div>
							</a>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="no-card">
						<div class="img"></div>
						<p class="text">暂无卡券</p>
					</div>
				</c:otherwise>
			</c:choose>
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
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script>
		$(function() {
            open_loading("加载中")
            setTimeout(function(){
                $("#btnback").show();
                layer.closeAll();
                }, 2000);
			var timestamp = "${wx.timestamp}";//时间戳
			var nonceStr = "${wx.nonceStr}";//随机串
			var signature = "${wx.signature}";//签名
			var appId = "${wx.appid}";//app_id
			wx.config({
				 debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				 appId : appId, // 必填，公众号的唯一标识
				 timestamp : timestamp, // 必填，生成签名的时间戳
				 nonceStr : nonceStr, // 必填，生成签名的随机串
				 signature : signature,// 必填，签名，见附录1
				 jsApiList : [ "addCard" ]
			 // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
			
		});
		//config配置信息失败，执行error
		wx.error(function(res){
			alert("参数错误");
		});
		//config配置信息成功，执行ready
		wx.ready(function(){
			var cardId = '${wx.card_id}';
			var timestamp = '${wx.config.timestamp}';
			var signature = '${wx.config.signature}';
			var cardExt = '{"timestamp":"'+timestamp+'","signature":"'+signature+'","card_id":"'+cardId+'"}';
			wx.addCard({
				cardList: [{
					cardId:cardId,
					cardExt:cardExt
				}], // 需要添加的卡券列表
				success: function (res){
					var cardList = res.cardList; // 添加的卡券列表信息
				}
			});
		});
	</script>
<script type="text/javascript">
    $(function(){
        FastClick.attach(document.body);
    });

    function style(othis){
        $(othis).addClass("active");
        $(othis).siblings().removeClass("active")
    }
    function cardType(othis,type){
        style(othis);
        $("#FAV_TYPE").attr("value",type);
        console.log(type);
        $("#Form").submit();
    }
	var img = {
        "CURREN":["<%=basePath%>newStyle/weixin/images/card/img_9.png","<%=basePath%>newStyle/weixin/images/card/common_use.jpg"],
        "NEW":["<%=basePath%>newStyle/weixin/images/card/img_4.png","<%=basePath%>newStyle/weixin/images/card/new.jpg"],
        "OLD":["<%=basePath%>newStyle/weixin/images/card/img_6.png","<%=basePath%>newStyle/weixin/images/card/old_and_new.jpg"],
        "MAN":["<%=basePath%>newStyle/weixin/images/card/img_5.png","<%=basePath%>newStyle/weixin/images/card/man.jpg"],
        "WEN":["<%=basePath%>newStyle/weixin/images/card/img_1.png","<%=basePath%>newStyle/weixin/images/card/woman.jpg"],
        "BIRTH":["<%=basePath%>newStyle/weixin/images/card/img_7.png","<%=basePath%>newStyle/weixin/images/card/birthday.jpg"],
        "GRAB":["<%=basePath%>newStyle/weixin/images/card/img_8.png","<%=basePath%>newStyle/weixin/images/card/time_limit.jpg"],
        "APPLY":["<%=basePath%>newStyle/weixin/images/card/img_3.png","<%=basePath%>newStyle/weixin/images/card/member.jpg"],
        "TERM":["<%=basePath%>newStyle/weixin/images/card/img_2.png","<%=basePath%>newStyle/weixin/images/card/full_time.jpg"]
	}
    function openCard(title,notice,description,content,id,begin,end,sheng,you,type,store,card_type){
    	 var time ='';
    		if(type == "DATE_TYPE_FIX_TIME_RANGE"){//固定日期
    			time='<p class="weic-card-time"><font color="#333">有效期：   </font>'+begin+"至"+end+'</p>'
    		}else if(type == "DATE_TYPE_FIX_TERM"){
    			var tian = sheng;
    			if(sheng == "0"){
    				tian = "当";
    			}
    			time='<p class="weic-card-time"><font color="#333">有效期：   </font>领取后'+tian+"天生效，领取后"+you+'天内有效</p>';
    		}
    		var html ='<div class="open-card-box"><div class="card-img"><img src="'+img[card_type][1]+'" alt="" width="100%"></div>'+
    				'<p class="weic-card-title">'+title+'</p>'+time+
    				'<p class="weic-card-time"><font color="#333">适用门店：</font>'+store+'</p>'+
    				'<p class="weic-card-time"><font color="#333">优惠说明：</font>'+description+'</p>'+
    				'<p class="weic-card-time"><font color="#333">使用提醒：</font>'+notice+'</p>'+
    				'<div style="padding: 1rem 0 0.5rem 0">'+
    				'<div class="weic-recharge-btn weic-btn-gradientOrange" style="width: 75%;" onclick=lingqu("'+id+'")>确定</div>'+
    			'</div>'+
    		'</div>'
    		layer.open({
    	          content: html
    	          ,className: 'signIn_box'
    	          ,style:"background: none;box-shadow: none;width:85%"
				  ,shadeClose:true
                  ,end:function(){

    	          }
    	      });	
 	};
    
    
    function lingqu(id){
		confirm("是否确定领取该商品",function(){
				var _this=this;
				var flag = true;
				if(flag){
					var flag = false;
					$.ajax({
 		            type: "POST",
 		            async:false,
 		            url:'<%=basePath%>indexMember/receiveBenefits.do',
					data : {mayId : id},
					success: function(data) {
 		                flag = true;
 		                if(data.card_result == "true"){
                            sqhx(id);
						}else{
                            message(data.message);
	                        layer.close(index);
						}
 		            },
 		            error:function(){
                        flag = true;
 		                message("系统繁忙，请稍后再试");
 		                removeDis();
 		            }
				})
			}
		})
	};
    function sqhx(card_id) {
        var url = window.location.href;
        $.ajax({
            type: "POST",
            url: '<%=basePath%>indexMember/goWechatCard1.do',
            data: {url: url, card_id: card_id,state:'benefit'},
            dataType: 'json',
            async: false,
            beforeSend: open_loading(''),
            success: function (data) {
                if(data.result == "true"){
                    wx.config({
                        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                        appId: data.wx.appid, // 必填，公众号的唯一标识
                        timestamp: data.wx.timestamp, // 必填，生成签名的时间戳
                        nonceStr: data.wx.nonceStr, // 必填，生成签名的随机串
                        signature: data.wx.signature,// 必填，签名，见附录1
                        jsApiList: ['addCard']
                        // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                    });
                    layer.closeAll();
                    wx.ready(function(){
                        var cardId = data.wx.card_id;
                        var cardExt = '{"timestamp":"'+data.wx.config.timestamp+'","signature":"'+data.wx.config.signature+'","card_id":"'+cardId+'"}';
                        wx.addCard({
                            cardList: [{
                                cardId:cardId,
                                cardExt:cardExt
                            }], // 需要添加的卡券列表
                            success: function (res){
                                var cardList = res.cardList; // 添加的卡券列表信息
                            }
                        });
                    });
                }else{
                    message(data.message);
                }

            },
            error: function () {
                layer.closeAll();
                message("系统繁忙，请稍后再试！");
                return false;
            }
        });
    }

</script>
</html>
