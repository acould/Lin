<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <title>新手引导</title>
    <style>
        .tal-h1 {padding: 16px; background-color: #1a242e; font-size: 24px;text-align: center;
            font-weight: 600; color: white; letter-spacing: 4px; }
        .f71515 { color: #f71515}
        .tal-h2 {font-weight: 600; font-size: 24px;padding-top: 30px;}
        .tal-content {padding: 0 20px 100px 20px; max-width: 1140px; margin: 0 auto;}
        .tal-indent {text-indent: 20px;font-size: 16px}
        .tal-indent2 {text-indent: 90px;font-size: 16px}
        .tal-p-height {line-height: 28px;}
        .tal-imgBox {margin: 60px 0; text-align: center; background-color: #fff;}
		.toplist {width:170px;height:260px;background-color: #fff;box-shadow: 0px 10px 30px 0px rgba(0,0,0,0.08);
			border-radius:6px;overflow: hidden;margin-right: 20px;display: inline-block;cursor:pointer;}
		.toplist h1 {font-size: 38px;font-weight: bold;}
		.toplist p {font-size: 18px;}
		.toplist .top01,.toplist .top02,.toplist .top03,.toplist .top04,.toplist .top05,.toplist .top06 {color:#fff;padding: 14px 0;}
		.topcont {padding: 30px 20px;text-align:justify;}
		.toplist:last-child {margin-right: 0px;}
		.top01 {background: linear-gradient(to right, #f63434, #ff3578); }
		.top02 {background: linear-gradient(to right, #f65029, #f59b30); }
		.top03 {background: linear-gradient(to right, #e4285c, #f34da1); }
		.top04 {background: linear-gradient(to right, #7a38f3, #af23e9); }
		.top05 {background: linear-gradient(to right, #246ef4, #4ea1fd); }
		.top06 {background: linear-gradient(to right, #1db9d0, #29eba4); }
        .tal-p-top {margin-top: 10px}
        .font-14 {font-size: 14px}
        .a-01AAED {color: #01AAED;text-decoration: none; cursor: pointer}
        .text-center {text-align: center;}
        .title-h2 {padding: 40px 0;background-color: #f3f3f3}
        .padding-left40 {padding-left:40px;}
        
                /*悬浮窗口*/
        .navbar-fixed {
                position: fixed;
    			left: 0;
    			top: 65px;
    			bottom: 0;
    			background-color: #fff;
    			box-shadow: 4px 18px 30px #eee;
    			width: 130px;
        }
        .navbar-center {
        	width: 130px;
    		height: 440px;
   	 		position: absolute;
    		top: 0;
    		bottom: 0;
    		margin: auto;
        }
        .navbar-fixed div p {
            width:100px;
            font-size: 14px;
            margin:0 15px;
            padding:17px 0;
        }
         @media only screen and (max-width:1366px){
         	.navbar-fixed {
                position: fixed;
    			left: 0;
    			top: 65px;
    			bottom: 0;
    			background-color: #fff;
    			box-shadow: 4px 18px 30px #eee;
    			width: 48px;
        	}
            .navbar-fixed div p {
                width:24px;
                font-size: 12px;
                margin:0 10px;
                padding:8px 0;
            }
            .navbar-fixed div p a {
                font-size: 12px;
            }
            .navbar-center {
        		width: 48px;
        		height:426px;
   	 			position: absolute;
    			top: 0;
    			bottom: 0;
    			margin: auto;
        	}
        }
        .fixed-a,.fixed-b,.fixed-c,.fixed-d,.fixed-e {
            border-bottom:1px solid #eee;
            cursor:pointer;
            position:relative;
        }
        .fixed-a:hover,.fixed-b:hover,.fixed-c:hover,.fixed-d:hover,.fixed-e:hover {
            background-color: #eee;
            color:#000!important;
        }
        /*悬浮窗口结束*/
    </style>
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
</head>
<body>
	<!--悬浮窗口-->
    <div class="navbar-fixed">
    	<div class="navbar-center">
    		<div  style="border-top:1px solid #eee;border-bottom:1px solid #eee;">
            	<p><font color="red">*</font>直通车<font color="red">*</font></p>
        	</div>
        	<div class="fixed-a" onclick="offset('top01')">
           	<p>导入会员</p>
        	</div>
       	 	<div class="fixed-b"  onclick="offset('top02')">
            	<p>新增门店</p>
        	</div>
        	<div class="fixed-c" onclick="offset('top03')">
            	<p>新增角色</p>
        	</div>
        	<div class="fixed-d"  onclick="offset('top04')">
            	<p>新增人员</p>
        	</div>
       	 	<div class="fixed-d" onclick="offset('top05')">
           	 <p>绑定计费系统</p>
        	</div>
       	 	<div class="fixed-d"  onclick="offset('top06')">
            	<p>开通在线支付</p>
        	</div>
        	<div class="fixed-d"  onclick="offset('top07')">
            	<p>营销设置</p>
        	</div>
        	<div class="fixed-e" onclick="offset('tal-h1')"> 
        		<p>回到顶部</p>
        	</div>
    	</div>
    </div>
    <!--悬浮窗口结束-->
	
	<h1 class="tal-h1" id="tal-h1">
    	揽客网吧微信营销平台新用户向导
	</h1>
	<div class="text-center title-h2">
    	<h2 class="tal-h2" style="margin-top: 0">
        	<span class="f71515">*</span>新用户必读 <span class="f71515">*</span>
    	</h2>
    	<p class="tal-indent tal-p-height font-14">新注册揽客的用户，请注意按以下流程完成您的揽客设置</p>
	</div>
<div class="tal-content">
    <div class="tal-imgBox">
        <!-- <img src="<%=basePath%>newStyle/images/newerGuide/lanker-process.jpg" alt="" width="380px" class=""
             ondragstart="return false;"> -->
         <div class="toplist" title="点击直达" onclick="offset('top01')">
         	<div class="top01">
         	   <h1>01</h1>
         	   <p>导入会员</p>
         	</div>
         	<div class="topcont">
         		授权后必须先导入您公众号中原有的粉丝，不然粉丝会看到系统错误。
         	</div>
         </div>
         <div class="toplist" title="点击直达" onclick="offset('top02')">
         	<div class="top02">
         	   <h1>02</h1>
         	   <p>新增门店</p>
         	</div>
         	<div class="topcont">
         		新建门店后，才能继续设置门店的所有操作。
         	</div>
         </div>
         <div class="toplist" title="点击直达" onclick="offset('top03')">
         	<div class="top03">
         	   <h1>03</h1>
         	   <p>新增角色</p>
         	</div>
         	<div class="topcont">
         		设置店长、收银员等不同的角色，有了门店、角色，才能新建人员。
         	</div>
         </div>
         <div class="toplist" title="点击直达" onclick="offset('top04')">
         	<div class="top04">
         	   <h1>04</h1>
         	   <p>新增人员</p>
         	</div>
         	<div class="topcont">
         		设置店长、收银员等不同的角色，有了门店、角色，才能新建人员。
         	</div>
         </div>
          <div class="toplist" title="点击直达" onclick="offset('top05')">
         	<div class="top05">
         	   <h1>05</h1>
         	   <p>绑定计费系统</p>
         	</div>
         	<div class="topcont">
         		将您的门店与网吧计费系统的账号进行绑定，才能打通微信会员与计费系统的会员数据
         	</div>
         </div>
         <div class="toplist" title="点击直达" onclick="offset('top06')">
         	<div class="top06">
         	   <h1>06</h1>
         	   <p>开通在线支付</p>
         	</div>
         	<div class="topcont">
         		会员在线充值功能，必须要满足绑定计费系统、开通在线支付两个条件。
         	</div>
         </div>
    </div>
    <h2 class="tal-h2" id="top01">
        <span class="f71515">TOP1、</span>导入会员
    </h2>
    <p class="tal-indent tal-p-height tal-p-top">微信公众号托管到揽客平台后，揽客将即时更新您的公众号菜单，将所有功能自动添加到公众号。需要在会员管理-<a class="a-01AAED"
                                                                                                       title="去导入会员">会员列表</a>中，用“公众号粉丝导入”功能将公众号中原有的粉丝导入到揽客平台，粉丝不需重新关注，即可使用公众号的新功能。
    </p>
    <div class="tal-imgBox" ondragstart="return false;" style="margin: 30px 0">
        <img src="<%=basePath%>newStyle/images/newerGuide/lanke-hydr.jpg" alt="" width="900px" class="" ondragstart="return false;">
    </div>
    <p class="tal-indent tal-p-height tal-p-top font-14"><span class="f71515">重要提醒：</span>1、若不导入会员，粉丝打开新公众号可能会看到“系统错误”
    </p>
    <p class="tal-indent2 tal-p-height font-14">2、没有粉丝的新公众号，不需要做会员导入操作。</p>
    <h2 class="tal-h2" id="top02">
        <span class="f71515">TOP2、</span>新增门店
    </h2>
    <p class="tal-indent tal-p-height tal-p-top">新增门店的重要性：</p>
    <p class="tal-indent tal-p-height tal-p-top">
        1、新增门店后，粉丝才能在微信端找到您的门店，绑定门店成为会员。如果没有绑定会员，很多营销功能都不能使用，如：领取到的优惠券不能在门店核销、不能用积分抽奖和兑换商品等。</p>
    <p class="tal-indent tal-p-height tal-p-top">2、新增门店是人员管理的基础，新增门店并设置角色后，您才可以添加每个店的店长、收银员等职位，让员工进行不同权限的后台操作。</p>
    <p class="tal-indent tal-p-height tal-p-top">在后台菜单“帐户管理-<a class="a-01AAED" title="去新建门店">门店管理</a>”中新增门店。</p>
    <div class="tal-imgBox" ondragstart="return false;" style="margin: 30px 0">
        <img src="<%=basePath%>newStyle/images/newerGuide/lanke-xzmd.jpg" alt="" width="900px" class="" ondragstart="return false;">
    </div>
    <p class="tal-indent tal-p-height tal-p-top font-14"><span class="f71515">重要提醒：</span>1、即使您只有1家门店，也要新增这家门店，并且补齐门店信息。
    </p>
    <p class="tal-p-height font-14" style="padding-left: 90px">2、如有暂时不需要再使用的门店，请在后台选择网吧管理-<a class="a-01AAED"
                                                                                             title="去禁用门店信息">门店管理</a>中操作“禁用”门店，系统将保留您的门店会员数据，并提示会员去绑定您的其它门店。在门店再次启用时，您会员所领取的优惠券等都不会受到影响。
    </p>
    <h2 class="tal-h2" id="top03">
        <span class="f71515">TOP3、</span>新增角色
    </h2>
    <p class="tal-indent tal-p-height tal-p-top">
        为您的门店添加一些管理角色，如：店长、收银员、营销、财务，并为每个角色设置指定的权限，把职责分配给不同的角色，也能保护您的管理员帐号安全。</p>
    <p class="tal-indent tal-p-height tal-p-top">在后台菜单“帐户管理-<a class="a-01AAED" title="去新建角色">角色管理</a>”中新增角色，新增完成后系统会提示请您去为这个角色设置权限，为防止忘记操作，您可以在新增完角色后立即去设置权限。
    </p>
    <div class="tal-imgBox" ondragstart="return false;" style="margin: 30px 0">
        <img src="<%=basePath%>newStyle/images/newerGuide/lanke-xzjs.jpg" alt="" width="900px" class="" ondragstart="return false;">
    </div>
    <h2 class="tal-h2" id="top04">
        <span class="f71515">TOP4、</span>新增人员
    </h2>
    <p class="tal-indent tal-p-height tal-p-top">
        为您的员工们开子帐号，可以方便的查询到后台所有的操作是由谁进行的。员工帐号是挂在门店下的，并且在开的时候指定了角色，所以先确保您已新增门店和角色，员工帐号才能够正常设立。</p>
    <p class="tal-indent tal-p-height tal-p-top">在后台菜单“帐户管理-<a class="a-01AAED" title="去新建人员">人员管理</a>”中新增人员，他们将以手机号码登陆。
    </p>
    <div class="tal-imgBox" ondragstart="return false;" style="margin: 30px 0">
        <img src="<%=basePath%>newStyle/images/newerGuide/lanke-xzry.jpg" alt="" width="900px" class="" ondragstart="return false;">
    </div>
    <p class="tal-indent tal-p-height tal-p-top font-14"><span class="f71515">重要提醒：</span>1、目前登录用的手机号码都是唯一的，若有同一位人员管理多家门店，请使用管理员账号，或用不同的手机号码登录多家门店。
    </p>
    <h2 class="tal-h2" id="top05">
        <span class="f71515">TOP5、</span>绑定计费系统
    </h2>
    <p class="tal-indent tal-p-height tal-p-top">每一家门店都有唯一的计费系统ID，需要按门店逐个开通。开通后：</p>
    <p class="tal-indent tal-p-height tal-p-top padding-left40">1、微信会员绑定门店时，自动验证会员卡号，在计费系统中存在的会员方可绑定</p>
    <p class="tal-indent tal-p-height tal-p-top padding-left40">2、您的会员可以在微信中查看余额、上网记录、消费记录</p>
    <p class="tal-indent tal-p-height tal-p-top padding-left40">3、您给会员发放的现金优惠券可以直充到会员卡奖励金额中，不需人工加余额</p>
    <p class="tal-indent tal-p-height tal-p-top padding-left40">4、根据您会员的消费行为，自动给会员打上分组标签，实现精准营销</p>
    <p class="tal-indent tal-p-height tal-p-top">在后台菜单“账户管理-用户中心”中选择门店进行绑定申请。提交绑定资料后，计费接口在3个工作日内开通。开通后下载安装揽客通讯客户端（内有安装说明书），安装成功即生效</p>
    <div class="tal-imgBox" ondragstart="return false;" style="margin: 30px 0">
        <img src="<%=basePath%>newStyle/images/newerGuide/userCenter.jpg" alt="" width="900px" class="" ondragstart="return false;" style="border: 1px solid #eee;">
    </div>
    <h2 class="tal-h2">
                        【开通流程图】
    </h2>
    <p class="tal-indent tal-p-height tal-p-top font-14"><span class="f71515">重要提醒：</span>申请时提交的计费系统ID请与门店实际使用的ID一致，否则会出现会员数据错误。
    <div class="tal-imgBox" ondragstart="return false;" style="margin: 30px 0">
        <img src="<%=basePath%>newStyle/images/newerGuide/lk-flow.jpg" alt=""  class="" ondragstart="return false;">
    </div>
    <h2 class="tal-h2" id="top06">
        <span class="f71515">TOP6、</span>开通在线支付
    </h2>
    <p class="tal-indent tal-p-height tal-p-top">揽客的“在线充值”功能需要门店逐个开通在线支付并绑定计费系统：</p>
    <p class="tal-indent tal-p-height tal-p-top padding-left40">1、 网吧设置在线充值规则后，会员可实现微信在线直充到账。</p>
    <p class="tal-indent tal-p-height tal-p-top padding-left40">2、 揽客采用银联支付接口，T+1结算周期直接到商户关联的银行账户内，资金安全有保障。</p>
    <p class="tal-indent tal-p-height tal-p-top">在后台菜单“账户管理-用户中心”中选择门店进行开通申请，并根据开通流程的引导，一步步完成银联商户开通</p>
    <div class="tal-imgBox" ondragstart="return false;" style="margin: 40px 0">
     	<img src="<%=basePath%>newStyle/images/newerGuide/pay.jpg" alt="" width="700px" class="" ondragstart="return false;">
    </div>
    <h2 class="tal-h2" id="top07">
        <span class="f71515">TOP7、</span>设置营销功能
    </h2>
    <p class="tal-indent tal-p-height tal-p-top">
        以上几步新手必做的工作完成后，您就可以开始设置您的营销功能了。揽客为您提供了几种网吧特定场景的优惠券、会员积分抽奖兑换、拉新营销、赛事展示等功能，您可以在后台逐个设置。</p>
    <h2 class="tal-h2" style="font-size: 18px;padding-left: 20px;padding-top: 18px;">
        <span class="f71515">设置卡券功能提醒</span>
    </h2>
     <p class="tal-indent tal-p-height tal-p-top">使用卡券功能的前提是：微信卡券功能已开通，否则无法正常使用卡券</p>
    <div class="tal-imgBox" ondragstart="return false;" style="margin: 26px 0">
     	<img src="<%=basePath%>newStyle/images/newerGuide/weixin-card.jpg" alt="" width="700px" class="" ondragstart="return false;">
    </div>
    <p class="tal-indent tal-p-height tal-p-top">效果案例请关注官方微信公众号“揽客”进行查看。</p>
    <div class="tal-imgBox" ondragstart="return false;" style="margin-bottom: 20px">
        <img src="<%=basePath%>newStyle/images/newerGuide/lanker-we.jpg" alt="" width="" class=""
             ondragstart="return false;">
    </div>
    <p class="tal-indent tal-p-height tal-p-top">联系方式：</p>
    <p class="tal-indent tal-p-height tal-p-top">售前客服：揽客客服 15397046172</p>
    <p class="tal-indent tal-p-height tal-p-top">售后客服：豆沙姐 13958146307</p>
</div>


</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script type="text/javascript">
    function offset(id){
    	var id = "#"+id
   		$('html, body').animate({
          	scrollTop : $(id).offset().top
        }, 750);
    }
       $(window).scroll(function (){
				if ($(window).scrollTop() > 65) {
					$(".navbar-fixed").css({position: "fixed",top:"0"});
				}else{
					$(".navbar-fixed").css({position: "fixed",top:"65px"});
				}
            })  		
        	
</script>


</html>