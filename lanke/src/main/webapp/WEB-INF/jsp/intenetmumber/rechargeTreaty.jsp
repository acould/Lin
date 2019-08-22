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
</head>

<body>
	<div class="rechargetreaty">
		<div class="item-box padding-box gray">
            尊敬的客户，为保障您的合法权益，请您在点击“立即充值”按钮前，完整、仔细地阅读本充值协议。当您继续点击“立即充值”按钮，即视为您已阅读、理解本协议，并同意按本协议规定执行。<br><br>
            1、充值方式<br>
            本充值操作采用微信支付方式完成。<br><br>
            2、充值赠送金额（如有）、充值金额、账户金额<br>
            充值赠送金额指：根据本网咖不时推出的充值优惠活动，您充值后，在您充值金额以外，额外赠予的金额。如，您充值100元人民币，根据当时的充值优惠活动赠送您20元，则您成功充值100元后，在您的上网账户内显示的充值金额为120元，超出的20元即为充值赠送金额。<br>
            充值金额指：您实际充值支付的金额（人民币）。<br>
            账户金额：即您APP账户中当前显示的金额，账户金额一般通过充值金额、充值赠送金额及其他赠送或补偿的金额获得。<br><br>
            3、充值后，账户余额使用不设有效期，不能转移、转赠。<br><br>
            5、充值后，账户余额不能退还。请您根据自己的消费情况充值，我们对充值次数不设限制。您在下单接受服务前，请认真查看价格提示，确认无异议后充值。
        </div> 
	</div>
</body>

</html>
