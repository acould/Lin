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
	<title>网吧联盟</title>
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
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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


<body class="gray-bg">
	<div class="fixed-bottom share">
		<img src="<%=basePath%>assets/images/sare.png" alt="">
		<div class="flex-box">
		   <a class="btn" href="javascript:void(0)" rel="${url}"><i class="c-icon weixin-btn">获取邀请码pp-;</i></a>
		</div>
		<div class="flex-box"><div class="weixin" onclick="_system._guide(true)"><img src="<%=basePath%>assets/images/sarebtn1.png" alt=""></div><div class="friend" onclick="_system._guide(true)"><img src="<%=basePath%>assets/images/sarebtn2.png" alt=""></div><div onclick="shareTSina('标题','rLink链接','分享来源','pic分享图片路径')" class="sina"><img src="<%=basePath%>assets/images/sarebtn3.png" alt=""></div>
		</div>
		
	</div>
	<div id="cover"></div>
	<div id="guide"><img src="<%=basePath%>assets/images/guide1.png"></div>
	<script>
		function shareTSina(title,rLink,site,pic) {
			var top = window.screen.height / 2 - 250;
			var left = window.screen.width / 2 - 300;

			window.open("http://service.weibo.com/share/share.php?pic=" +encodeURIComponent(pic) +"&title=" +
					encodeURIComponent(title.replace(/&nbsp;/g, " ").replace(/<br \/>/g, " "))+ "&url=" + encodeURIComponent(rLink),
					"分享至新浪微博",
					"height=500,width=600,top=" + top + ",left=" + left + ",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");

		}

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
		 $(".weixin-btn").click(function() {
	                window.location.href = $(this).parent("a").attr("rel");
	        });           
	</script>
	 <script type="text/javascript">
        var config = {
            appId: 'wx60f4d8e12795a596',
      		timestamp: '$timestamp',
      		nonceStr: '$nonceStr',
      		signature: '$signature'
        }
        var pengyouquanData = {
            title: '这是一条测试信息',
            desc: '这里是测试信息内容',
            link: 'http://msiyun.com',
            imgUrl: 'http://static.msiyun.com/assets/images/msiyun_logo.png'
        };
    </script>
</body>

</html>
