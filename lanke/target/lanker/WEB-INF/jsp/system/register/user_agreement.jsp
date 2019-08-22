<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<title>用户协议</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=0.5, maximum-scale=1.0, minimum-scale=0.1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/login.css">
	<link rel="stylesheet" href="<%=basePath%>static/login/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>

</head>
<body class="cp-bj-color" style="padding:0;">
	<!--头部-->
   <div class="container-fluid borBot la-nav-bg">
        <header class="lk-container">
            <div class="lanke-logo"></div>
            <span class="lanke-ggy">客服热线：4000965099 </span>
            <p class="nav_p">
                <a href="<%=basePath%>login_toLogin" class="active">首页</a>
                <a href="<%=basePath%>login_toLogin" class="click-a">揽客介绍</a>
                <a href="<%=basePath%>register/about.do" style="margin: 0" class="">关于我们</a>
            </p>
        </header>
    </div>
	
    <div class="container" style="position: relative;margin-top: 13px">
        <div class="row">
            <div class="" style="position: relative;padding: 0">
                <div class="fixedleft" id="leftList" style="position: fixed;top:84px">
                    <div class="p-left-width">
                    	<a class="left-p" href="<%=basePath%>register/pay_set.do">如何设置充值</a>
                    	<a class="left-p" href="<%=basePath%>register/user_pay.do">会员如何充值</a>
                    	<a class="left-p" href="<%=basePath%>register/account_check.do">如何对账</a>
                    	<a class="left-p" href="<%=basePath%>register/user_marketing.do">如何会员营销</a>
                        <a class="left-p left-p-active" href="user_agreement.do">用户协议</a>
                    </div>
                </div>
            </div>
            <div class="col-md-12 col-xs-12 widthmax" style="background-color: #fff;padding: 0;margin-left: 162px" >
                <div class="pdbianju">
                    <h3 class="text-center">用户协议</h3>
                    <p class="margin-top20 textindex28">本《用户协议》是您（下称"用户"）与杭州网晶科技有限公司(下称“网晶科技”)之间在使用网晶科技出品的各类产品之前，注册用户(又名“帐号”，下统称“帐号”)和使用产品时签署的协议。</p>
                    <p class="margin-top20  titlesezi18">一、重要须知---在签署本协议之前，网晶科技正式提醒用户：</p>
                    <p class="margin-top10 textindex28">1.1、您应认真阅读、充分理解本《用户协议》中各条款，特别是免除或者限制网晶科技责任的免责条款，用户的权利限制条款，约定争议解决方式、司法管辖、法律适用的条款。</p>
                    <p class="margin-top10 textindex28">1.2、除非您接受本协议，否则用户无权也无必要继续接受网晶科技的服务，可以退出本次注册。用户点击接受并继续使用网晶科技的服务，视为用户已完全的接受本协议。</p>
                    <p class="margin-top10 textindex28">1.3、本协议在您开始使用网晶科技的服务，并注册成为网晶科技产品的用户时即产生法律效力，请您慎重考虑是否接受本协议，如不接受本协议的任一条款，请自动退出并不再接受网晶科技的任何服务。</p>
                    <p class="margin-top10 textindex28">1.4、在您签署本协议之后，此文本可能因国家政策、产品以及履行本协议的环境发生变化而进行修改，我们会将修改后的协议发布在本网站上，若您对修改后的协议有异议的，请立即停止登录、使用网晶科技产品及服务，若您登录或继续使用网晶科技产品，视为认可修改后的协议。</p>
                    <p class="margin-top20  titlesezi18">二、关于“帐号”及“付费会员”资格</p>
                    <p class="margin-top10 textindex28">2.1、网晶科技在旗下业务平台（包括但不限于揽客平台）提供用户注册通道，用户在认可并接受本协议之后，有权选择未被其他用户使用过的字母符号组合作为用户的帐号，并自行设置符合安全要求的密码。用户设置的帐号、密码是用户用以登录网晶科技产品，接受网晶科技服务的凭证。</p>
                    <p class="margin-top10 textindex64">2.1.1、用户可通过各种已有和未来新增的渠道注册账号及加入付费会员。</p>
                    <p class="margin-top10 textindex64">2.1.2、在用户使用具体某种方式加入付费会员时，须阅读并确认相关的用户协议和使用方法。</p>
                    <p class="margin-top10 textindex64">2.1.3、用户通过网络填写并提交注册表，表中所填写的内容与个人资料必须真实有效，否则网晶科技有权拒绝其申请或撤销其账号或付费会员资格，并不予任何赔偿或退还会员费。用户的个人资料发生变化，应及时修改相关资料，否则由此造成的会员权力不能全面有效行使的责任由会员自己承担，网晶科技有权因此取消其会员资格，并不予任何赔偿或退还会员费。</p>
                    <p class="margin-top10 textindex64">2.1.4、成为付费会员后，会员有权利不接受网晶科技的服务，可申请取消会员服务，但不获得任何服务费用的退还。</p>
                    <p class="margin-top20 textindex28">2.2、用户在注册了网晶科技帐号并不意味获得全部网晶科技产品的授权，仅仅是取得了接受网晶科技服务的身份，用户在登录相关网页、加载应用、下载安装软件时需要另行签署单个产品的授权协议。</p>
                    <p class="margin-top10 textindex28">2.3、网晶科技账户仅限于在网晶科技网站上注册用户本人使用，禁止赠与、借用、租用、转让或售卖。如果网晶科技发现或者有理由怀疑使用者并非帐号初始注册人，则有权在未经通知的情况下，暂停或终止向用户提供服务，并有权注销该帐号，而无需向该帐号使用人承担法律责任，由此带来的包括并不限于用户通讯中断、用户资料和信息等清空等损失由用户自行承担。</p>
                    <p class="margin-top10 textindex28">2.4、用户有责任维护个人帐号、密码的安全性与保密性，用户就其帐号及密码项下之一切活动负全部责任，包括用户数据的修改，发表的言论以及其他所有的损失。用户应重视网晶科技帐号密码保护。用户如发现他人未经许可使用其帐号或发生其他任何安全漏洞问题时立即通知网晶科技。如果用户在使用网晶科技服务时违反上述规则而产生任何损失或损害，网晶科技不承担任何责任。</p>
                    <p class="margin-top10 textindex28">2.5、用户帐号在丢失或遗忘密码后，可遵照网晶科技的申诉途径及时申诉请求找回帐号。用户可以凭初始注册资料填写申诉单向网晶科技申请找回帐号，网晶科技的密码找回机制仅负责识别申诉单上所填资料与系统记录资料的正确性，而无法识别申诉人是否系真正帐号有权使用人。对用户因被他人冒名申诉而致的任何损失，网晶科技不承担任何责任，用户知晓帐号及密码保管责任在于用户，网晶科技并无义务保证帐号丢失或遗忘密码后用户一定能通过申诉找回帐号。</p>
                    <p class="margin-top10 textindex28">2.6、用户应保证注册网晶科技帐号时填写的身份信息的真实性，任何由于非法、不真实、不准确的用户信息所产生的责任由用户承担。用户应不断根据实际情况更新注册资料，符合及时、详尽、真实、准确的要求。所有原始键入的资料将引用用户的帐号注册资料。如果因用户的注册信息不真实而引起的问题，以及对问题发生所带来的后果，网晶科技不负任何责任。如果用户提供的信息不准确、不真实、不合法或者网晶科技有理由怀疑为错误、不实或不完整的资料或在个人资料中发布广告、不严肃内容及无关信息，网晶科技有权暂停或终止向用户提供服务，注销该帐号，并拒绝用户现在和未来使用网晶科技服务之全部或任何部分。因此产生的一切损失由用户自行承担。</p>
                    <p class="margin-top20  titlesezi18">三、用户在使用网晶科技产品时，应当遵守《中华人民共和国宪法》、《中华人民共和国刑法》、《中华人民共和国民法通则》、《中华人民共和国合同法》、《中华人民共和国著作权法》、《中华人民共和国电信条例》、《互联网信息服务管理办法》、《互联网电子公告服务管理规定》、《计算机信息网络国际互联网安全保护管理办法》等相关规定。用户不得利用网晶科技服务产品从事违反法律法规、政策以及侵犯他人合法权益的行为，包括但不限于下列行为：</p>
                    <p class="margin-top10 textindex28">3.1、利用网晶科技服务产品发表、传送、传播、储存反对宪法所确定的基本原则的、危害国家安全、国家统一、社会稳定的、煽动民族仇恨、民族歧视、破坏民族团结的内容，或侮辱诽谤、色情、暴力、引起他人不安及任何违反国家法律法规政策的内容或者设置含有上述内容的二级用户名、角色名。</p>
                    <p class="margin-top10 textindex28">3.2、利用网晶科技服务发表、传送、传播、储存侵害他人知识产权、商业机密、肖像权、隐私权等合法权利或其他道德上令人反感的内容。</p>
                    <p class="margin-top10 textindex28">3.3、进行任何危害计算机网络安全的行为，包括但不限于：使用未经许可的数据或进入未经许可的服务器/帐户;未经允许进入公众计算机网络或者他人计算机系统并删除、修改、增加存储信息;未经许可，企图探查、扫描、测试本“软件”系统或网络的弱点或其它实施破坏网络安全的行为;企图干涉、破坏本“软件”系统或网站的正常运行，故意传播恶意程序或病毒以及其他破坏干扰正常网络信息服务的行为;伪造TCP/IP数据包名称或部分名称;自行或利用其他软件对网晶科技提供产品进行反向破解等违法行为。</p>
                    <p class="margin-top10 textindex28">3.4、进行任何诸如发布广告、推广信息、销售商品的行为，或者进行任何非法的侵害网晶科技利益的行为。</p>
                    <p class="margin-top10 textindex28">3.5、进行其他任何违法以及侵犯其他个人、公司、社会团体、组织的合法权益的行为或者法律、行政法规、规章、条例以及任何具有法律效力之规范所限制或禁止的其他行为。</p>
                    <p class="margin-top10 textindex28">3.6、在任何情况下，如果网晶科技有理由认为用户的任何行为，包括但不限于用户的任何言论和其它行为违反或可能违反法律法规、国家政策以及本协议的任何规定，网晶科技可在任何时候不经任何事先通知，即有权终止向用户提供服务。</p>
                    <p class="margin-top20  titlesezi18">四、网晶科技声明</p>
                    <p class="margin-top10 textindex28">4.1、用户须知，在使用网晶科技服务可能存在有来自任何他人的包括威胁性的、诽谤性的、令人反感的或非法的内容或行为或对他人权利的侵犯(包括知识产权)的匿名或冒名的信息的风险，用户须承担以上风险，网晶科技对服务不作担保，不论是明确的或隐含的，包括所有有关信息真实性、适当性、适于某一特定用途、所有权和非侵权性的默示担保和条件，对因此导致任何因用户不正当或非法使用服务产生的直接、间接、偶然、特殊及后续的损害，网晶科技不承担任何责任。</p>
                    <p class="margin-top10 textindex28">4.2、使用网晶科技服务必须遵守国家有关法律和政策等，维护国家利益，保护国家安全，并遵守本协议，对于用户违法行为或违反本协议的使用(包括但不限于言论发表、传送等)而引起的一切责任，由用户承担全部责任。</p>
                    <p class="margin-top10 textindex28">4.3、网晶科技提供的所有信息、资讯、内容和服务均来自互联网，并不代表网晶科技的观点，网晶科技对其真实性、合法性概不负责，亦不承担任何法律责任。</p>
                    <p class="margin-top10 textindex28">4.4、网晶科技所提供的产品和服务也属于互联网范畴，也易受到各种安全问题的困扰，包括但不限于：</p>
                    <p class="margin-top10 textindex64">4.4.1、个人资料被不法分子利用，造成现实生活中的骚扰；</p>
                    <p class="margin-top10 textindex64">4.4.2、哄骗、破译密码；</p>
                    <p class="margin-top10 textindex64">4.4.3、下载安装的其它软件中含有“特洛伊木马”等病毒程序，威胁到个人计算机上信息和数据的安全，继而威胁对本服务的使用。</p>
                    <p class="margin-top10 textindex64">4.4.4、以及其他类网络安全困扰问题对于发生上述情况的，用户应当自行承担责任。 </p>
                    <p class="margin-top10 textindex28">4.5、用户须明白，网晶科技为了整体运营的需要，有权在公告通知后，在不事先通知用户的情况下修改、中断、中止或终止服务，而无须向用户或第三方负责，网晶科技不承担任何赔偿责任。 </p>
                    <p class="margin-top10 textindex28">4.6、用户应理解，互联网技术存在不稳定性，可能导致政府管制、政策限制、病毒入侵、黑客攻击、服务器系统崩溃或者其他现今技术无法解决的风险发生。由以上原因可能导致网晶科技服务中断或帐号信息损失，对此非人为因素引起的用户损失由用户自行承担责任。 </p>
                    <p class="margin-top20  titlesezi18">五、知识产权 </p>
                    <p class="margin-top10 textindex28">5.1、网晶科技对其旗下运营的网页、应用、软件等产品和服务享有知识产权。受中国法律的保护。 </p>
                    <p class="margin-top10 textindex28">5.2、用户不得对网晶科技服务涉及的相关网页、应用、软件等产品进行反向工程、反向汇编、反向编译等。 </p>
                    <p class="margin-top10 textindex28">5.3、用户只能在本《用户协议》以及相应的授权许可协议授权的范围内使用网晶科技知识产权，未经授权超范围使用的，构成对网晶科技的侵权。</p>
                    <p class="margin-top10 textindex28">5.4、用户应保证，在使用网晶科技产品服务时上传的文字、图片、视频、软件以及表演等的信息不侵犯任何第三方知识产权，包括但不限于商标权、著作权等。若用户在使用网晶科技产品服务时上传的文字、图片、视频、软件以及表演等的信息中侵犯第三方知识产权，网晶科技有权移除该侵权产品，并对此不负任何责任。用户应当负责处理前述第三方的权利主张，承担由此产生的全部费用，包括但不限于侵权赔偿、律师费及其他合理费用，并保证网晶科技不会因此而遭受任何损失。</p>
                    <p class="margin-top10 textindex28">5.5、任何单位或个人认为通过网晶科技提供服务的内容可能涉嫌侵犯其知识产权或信息网络传播权，应该及时向网晶科技提出书面权利通知投诉，并提供身份证明、权属证明及详细侵权情况证明。网晶科技在收到上述法律文件后，将会依法尽快断开相关链接内容。网晶科技提供投诉通道，hzwjkj@aliyun.com。如投诉中未向网晶科技提供合法有效的证明材料，网晶科技有权不采取任何措施。</p>
                    <p class="margin-top20  titlesezi18">六、隐私保护</p>
                    <p class="margin-top10 textindex28">网晶科技非常重视用户的隐私权，用户在享受网晶科技提供的服务时可能涉及用户的隐私，因此请用户仔细阅读本隐私保护条款。</p>
                    <p class="margin-top10 textindex28">6.1、请用户注意勿在使用网晶科技服务中透露自己的各类财产帐户、银行卡、信用卡、第三方支付账户及对应密码等重要信息资料，否则由此带来的任何损失由用户自行承担。</p>
                    <p class="margin-top10 textindex28">6.2、用户的帐号、密码属于保密信息，网晶科技会努力采取积极的措施保护用户帐号、密码的安全。</p>
                    <p class="margin-top10 textindex28">6.3、互联网的开放性以及技术更新速度快，因非网晶科技可控制的因素导致用户信息泄漏的，网晶科技不承担任何责任。</p>
                    <p class="margin-top10 textindex28">6.4、6.4	用户在使用网晶科技服务时不应将自认为隐私的信息发表、上传至网晶科技，也不应将该等信息通过网晶科技的服务传播给其他人，由于用户的行为引起的隐私泄漏，由用户自行承担责任。</p>
                </div>
        </div>
        </div>
    </div>
    <script>
        $(function () {
            $(".widthmax").width();
            var widths = $(".widthmax").width()-162;
            $(".widthmax").width(widths);
            
            $(window).scroll(function (){
				if ($(window).scrollTop() >= 84) {
					$("#leftList").css({position: "fixed",top:"0"});
				}else{
					$("#leftList").css({position: "fixed",top:"84px"});
				}
            })
        })

    </script>
</body>
</html>