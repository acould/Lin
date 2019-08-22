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
	<link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
	<link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
	<link rel="stylesheet" href="<%=basePath%>assets/css/base.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/zepto.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/js/fastclick.js"></script>
	<link rel="stylesheet" href="<%=basePath%>assets/js/dialog.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/dialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/iscroll.js"></script>
	<link rel="stylesheet" href="<%=basePath%>assets/js/swiper-3.2.7.min.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/swiper-3.2.7.jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/mobile.js"></script>
<%-- 	<script type="text/javascript" src="<%=basePath%>assets/js/jweixin-1.0.0.js"></script> --%>
	
	<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<style>
		#mess_share{margin:15px 0;}
		#share_1{float:left;width:49%;}
		#share_2{float:right;width:49%;}
		#mess_share img{width:22px;height:22px;}
		#cover{display:none;position:absolute;left:0;top:0;z-index:18888;background-color:#000000;opacity:0.7;}
		#guide{display:none;position:absolute;right:18px;top:5px;z-index:19999;}
		#guide img{width:260px;height:180px;}
	</style>
</head>


<body class="white-bg" style="background: #2b26ca">
		<img src="<%=basePath%>newStyle/weixin/images/share.jpg">
		  <input type="hidden" id="timestamp" name="timestamp" value="${timestamp}"/>
          <input  type="hidden" id="nonceStr" name="nonceStr" value="${nonceStr}"/>
          <input  type="hidden" id="signature" name="signature" value="${signature}"/>
          <input  type="hidden" id="appId" name="appId" value="${appId}"/>
          <input  type="hidden" id="jsapi_ticket" name="jsapi_ticket" value="${jsapi_ticket}"/>
          <input  type="hidden" id="userId" name="userId" value="${userId}"/>
          		<input type="hidden" id="PATH" name="PATH" value="${pdIn.PATH}"/>
          		<input type="hidden" id="BARCODE" name="BARCODE" value="${pdIn.BARCODE}"/>
          		<input type="hidden" id="TITLE" name="TITLE" value="${pdIn.TITLE}"/>
          		<input type="hidden" id="DESCRIPTION" name="DESCRIPTION" value="${pdIn.DESCRIPTION}"/>
	
	<script>
		$(function() {
			var timestamp = $("#timestamp").val();//时间戳
			var nonceStr = $("#nonceStr").val();//随机串
			var signature = $("#signature").val();//签名
			var appId = $("#appId").val();//签名
			wx.config({
				 debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				 appId : appId, // 必填，公众号的唯一标识
				 timestamp : timestamp, // 必填，生成签名的时间戳
				 nonceStr : nonceStr, // 必填，生成签名的随机串
				 signature : signature,// 必填，签名，见附录1
				 jsApiList : [ 'onMenuShareAppMessage' ]
			 // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
			
		});
		//config配置信息失败，执行error
		wx.error(function(res){
			alert("参数错误");
		});
		var domain = "${domain}";
		var userId = $("#userId").val();//签名
		var PATH = $("#PATH").val();//商户logo
		var BARCODE = $("#BARCODE").val();//二维码
		var TITLE = $("#TITLE").val();//title
		var DESCRIPTION = $("#DESCRIPTION").val();//description
		var LINK = domain+'indexMember/laodaixin?userId='+userId;
		 // wx.hideOptionMenu();
		
		//config配置信息成功，执行ready
		wx.ready(function(){
			//新的分享到朋友/qq
			/* wx.updateAppMessageShareData({ 
		        title: TITLE, // 分享标题
		        desc: DESCRIPTION, // 分享描述
		        link: LINK, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		        imgUrl: PATH, // 分享图标
			}, function(res) { 
				//这里是回调函数 
			}); */
			
			//以下微信将要废弃
			wx.onMenuShareAppMessage({
				title: TITLE, // 分享标题
				desc: DESCRIPTION, // 分享描述
				link: LINK, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
				imgUrl: PATH, // 分享图标
				type: 'link', // 分享类型,music、video或link，不填默认为link
				dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
				success: function () {
					// 用户点击了分享后执行的回调函数
					//alert('分享成功');
				}
			});
		});
        /*  wx.onMenuShareTimeline({
             title: '这是一个测试的标题--程高伟的博客',
             link: 'http://blog.csdn.net/frankcheng5143',
             imgUrl: 'http://avatar.csdn.net/E/B/6/1_frankcheng5143.jpg',
             success: function () { 
                 // 用户确认分享后执行的回调函数
                  alert('分享到朋友圈成功');
             },
             cancel: function () { 
                 // 用户取消分享后执行的回调函数
                  alert('你没有分享到朋友圈');
             }
         });
         wx.onMenuShareAppMessage({
               title: '这是一个测试的标题--百度',
               desc: '这个是要分享内容的一些描述--百度一下，你就知道',
               link: 'http://www.baidu.com',
               imgUrl: 'https://www.baidu.com/img/bd_logo1.png',
               trigger: function (res) {
                 // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
               },
               success: function (res) {
                   alert('分享给朋友成功');
               },
               cancel: function (res) {
                 alert('你没有分享给朋友');
               },
               fail: function (res) {
                 alert(JSON.stringify(res));
               }
             }); 
		function shareTSina(title,rLink,site,pic) {
			var top = window.screen.height / 2 - 250;
			var left = window.screen.width / 2 - 300;

			window.open("http://service.weibo.com/share/share.php?pic=" +encodeURIComponent(pic) +"&title=" +
					encodeURIComponent(title.replace(/&nbsp;/g, " ").replace(/<br \/>/g, " "))+ "&url=" + encodeURIComponent(rLink),
					"分享至新浪微博",
					"height=500,width=600,top=" + top + ",left=" + left + ",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");

		}
		 function shareFriend() {
        	wx.onMenuShareAppMessage({
                title: '这是一个测试的标题--百度',
                desc: '这个是要分享内容的一些描述--百度一下，你就知道',
                link: 'http://www.baidu.com',
                imgUrl: 'https://www.baidu.com/img/bd_logo1.png',
                trigger: function (res) {
                  // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
                },
                success: function (res) {
                    alert('分享给朋友成功');
                },
                cancel: function (res) {
                  alert('你没有分享给朋友');
                },
                fail: function (res) {
                  alert(JSON.stringify(res));
                }
              });
        	 } */
		/* var _system={
			$:function(id){return document.getElementById(id);},
			_client:function(){
				return {w:document.documentElement.scrollWidth,h:document.documentElement.scrollHeight,bw:document.documentElement.clientWidth,bh:document.documentElement.clientHeight};
			},
			_scroll:function(){
				return {x:document.documentElement.scrollLeft?document.documentElement.scrollLeft:document.body.scrollLeft,y:document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop};
			},
			_cover:function(show){
				if(show){
					this.$("cover").style.display="block";
					this.$("cover").style.width=(this._client().bw>this._client().w?this._client().bw:this._client().w)+"px";
					this.$("cover").style.height=(this._client().bh>this._client().h?this._client().bh:this._client().h)+"px";
				}else{
					this.$("cover").style.display="none";
				}
			},
			_guide:function(click){
				this._cover(true);
				this.$("guide").style.display="block";
				this.$("guide").style.top=(_system._scroll().y+5)+"px";
				window.onresize=function(){_system._cover(true);_system.$("guide").style.top=(_system._scroll().y+5)+"px";};
				if(click){_system.$("cover").onclick=function(){
					_system._cover();
					_system.$("guide").style.display="none";
					_system.$("cover").onclick=null;
					window.onresize=null;
				};}
			},
			_zero:function(n){
				return n<0?0:n;
			}
		} */
	/* 	 $(".weixin-btn").click(function() {
	                window.location.href = $(this).parent("a").attr("rel");
	        });    */        
	</script>
           
</body>

</html>
