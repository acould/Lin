<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=yes, initial-scale=0.5, maximum-scale=1.0, minimum-scale=0.1">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/login.css">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/bootstrap.min.css">
    <title>关于网晶</title>
    <style>
    body {min-width:1100px;overflow:auto}
     #carousel-example-generic {
         display: inline-block;
     }
    </style>
</head>
<body class="about">
        <!--头部-->
        <div class="container-fluid borBot">
            <header class="container ">
                <img src="<%=basePath%>static/login/images/logo.png" alt="" style="position: relative;top: 12px;">
                <span class="lanke-ggy" style="top:18px">客服热线：0571-86622520 </span>
                <p class="nav_p">
                    <a href="<%=basePath%>login_toLogin" class="">首页</a>
                    <a href="<%=basePath%>login_toLogin" class="click-a">揽客介绍</a>
                    <a href="<%=basePath%>register/introduction.do"  class="">入驻流程</a>
                    <a href="<%=basePath%>register/helpCenter.do"  class="">帮助中心</a>
                    <a href="" style="margin: 0"  class="active">关于我们</a>
                </p>
            </header>
        </div>
        <!--头部-->

        <!--轮播图-->
        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="width: 100%;">
            <!-- Indicators -->
            <ol class="carousel-indicators">
               <!-- <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>-->
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">
                <div class="item item-about active">
                    
                </div>
            </div>

            <!-- Controls -->
            <a class="left-bj left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left centers" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right-bj right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right centers" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
        <!--轮播图-->

        <!--公司简介-->
        <div class="container-fluid">
            <div class="container" style="padding: 30px 0;">
                <div class="title">
                    <div class="xiantiao"></div>
                    <div>
                        <p class="cp-text-p1">企业简介</p>
                        <p class="cp-text-p2">COMPANY PROFILE</p>
                    </div>
                </div>
                <div class="row max-width">
                    <div class="col-md-6 col-xs-6 box-gs-img"><img src="<%=basePath%>static/login/images/gs-img.jpg" alt="" width="500px"></div>
                    <div class="col-md-6 col-xs-6 box-gs-text">
                        <p class="p-jieshao">"揽客"是杭州网晶科技有限公司基于微信公众平台为网吧行业定制的营销平台，公司成立于2005年，位于浙江省省会城市杭州，一直致力于网吧行业专业服务，作为网吧pubwin计费系统的杭州地区独家服务商,网晶以雄厚的技术实力、优质的服务理念、为杭
                            州千余家网吧提供了10余年稳定、安全、高效的维护服务。
                        </p>
                        <p class="p-jieshao">2015年起，网吧行业进入全面转型升级、网晶紧随“老行业焕发
                            新活力”的步伐,致力于打造网吧行业的垂直型一站式的服务平台“网吧易购”，网吧微信营销平台“揽客”等产品，通过线上线下同步整合网吧周边供应商力量，帮助
                            网吧迅速解决采购、技术维护、营销等经营需求，助力网吧轻松转型
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <!--公司简介结束-->

        <!--公司理念-->
        <div class="container-fluid cp-bj-colora">
            <div class="container" style="padding: 30px 0;">
                <div class="title">
                    <div class="xiantiao"></div>
                    <div>
                        <p class="cp-text-p1">企业文化</p>
                        <p class="cp-text-p2">CORPORATE CULTURE</p>
                    </div>
                </div>
                <div class="row box-wenhua">
                    <div class="col-md-4 col-xs-4 hover-box1">
                        <img src="<%=basePath%>static/login/images/dream.png" alt="" class="hover-opt1">
                        <div class="wenhua-text"><div><p>用揽客，让每位网吧业主都成为营销高手</p></div></div>
                    </div>
                    <div class="col-md-4 col-xs-4 hover-box2">
                        <img src="<%=basePath%>static/login/images/idea.png" alt="" class="hover-opt2">
                        <div class="wenhua-text"><div><p>不断超越，追求完美</p></div></div>
                    </div>
                    <div class="col-md-4 col-xs-4 hover-box3">
                        <img src="<%=basePath%>static/login/images/senseworth.png" alt="" class="hover-opt3">
                        <div class="wenhua-text"><div><p>诚信，敏锐，创新，坚韧</p></div></div>
                    </div>
                </div>
            </div>
        </div>
        <!--公司理念结束-->

        <!--核心团队开始-->
        <%-- <div class="container-fluid">
            <div class="container">
                <div class="title">
                    <div class="xiantiao"></div>
                    <div>
                        <p class="cp-text-p1">核心团队</p>
                        <p class="cp-text-p2">CORE TEAM</p>
                    </div>
                </div>
                <div class="row position" style="margin-right:7%;margin-left:7%;">
                    <div class="col-md-4 col-xs-4 img-center1" >
                        <img src="<%=basePath%>static/login/images/ceo.png" alt="">
                        <div class="team-box-a">
                            <div>
                                <p>谭宏亮<br/>创办网吧服务企业网晶科技，成为全国最大的计费管理软件——
                                   Pubwin的杭州总代理。深耕网吧行业16年，建立了完善的网吧服务商渠道，拥有良好的网吧软、硬件厂商与游戏公司资源。长期在网吧协会任职，在浙江省网吧业界具有较高的号召力。
                                </p>
                            </div>
                        </div>
                        <p class="p-top">ceo</p>
                    </div>
                    <div class="col-md-4 col-xs-4 img-center2">
                        <img src="<%=basePath%>static/login/images/coo.png" alt="" >
                        <div class="team-box-b">
                            <div>
                                <p>白平<br/>曾任职浙江沸蓝网盟市场总监（浙通服）、淘宝天下市场总监（阿里巴巴集团），
                                	具备10余年营销管理经验、6年网吧增值业务运营及4年电子商务运营经验，为中国首个电商营销大奖“金麦奖”创始人之一、淘宝天下千万级创新业务缔造者。
                                </p>
                            </div>
                        </div>
                        <p class="p-top">coo</p>
                    </div>
                    <div class="col-md-4 col-xs-4 img-center3">
                        <img src="<%=basePath%>static/login/images/cto.png" alt="">
                        <div class="team-box-c">
                            <div>
                                <p>洪智鹏<br/>曾就职于亚信创联科技、新锐信息技术等行业领军型研发企业，具备7年软件研发及研发管理经验，
                                	曾参与或主导10余个政务管理系统、要素在线交易拍卖系统、银行业务系统等大型项目的研发工作。
                                </p>
                            </div>
                        </div>
                        <p class="p-top">cto</p>
                    </div>
                  
                </div>
            </div>
        </div> --%>
        <!--核心团队结束-->

        <!--加入联系我们-->
        <div class="container-fluid">
            <div class="container" style="padding: 60px 0;">
                <div class="row title ">
                    <div class="col-md-6 col-xs-6">
                        <p class="cp-text-p1">加入我们</p>
                        <p class="cp-text-p2">JOIN US</p>
                    </div>
                    <div class="col-md-6 col-xs-6">
                        <p class="cp-text-p1">联系我们</p>
                        <p class="cp-text-p2">CONTACT US</p>
                    </div>
                </div>
                <div class="row us-bottom">
                    <div class="col-md-6 col-xs-6">
                        <p class="p1">我们不断努力，一步一步地向梦想靠近。此刻，我们期待你的加入。敢于挑战！敢于奋斗！只要你想要没有什么做不到！</p>
                        <p class="p2">诚邀</p>
                        <p class="p3">营销助理 </p>
                        <p class="p4">主要负责公司自有微信营销平台、托管微信公众号的内容运营,详情面议</p>
                    </div>
                    <div class="col-md-6 col-xs-6">
                        <p class="pa">公司地址：杭州市江干区九盛路9号东方电子商务园18栋508</p>
                        <p class="pb">公司电话：4000965099</p>
                        <p class="pc">公司邮箱：thl@365wj.cc</p>
                        <p class="pd">联系qq：70000476</p>
                        <p class="pf">上班时间：9:30-17:30（周末双休）</p>
                    </div>
                </div>
            </div>
        </div>
        <!--加入联系我们-->
        
<!--foot部分-->
    <div class="container-fluid foot-bj">
        <div class="widths">
            <div class="foot-bg-text"></div>
            <div class="some-xinxi">
                <div class="paddte">
                    <p class="float-left">公司地址：杭州市江干区九盛路9号东方电子商务园18幢508室 </p>
                    <p class="float-left">ICP许可证编号：浙ICP备16043821号-4</p>
                    <p class="float-left"><a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010502004493">浙公网安备 33010502004493号</a></p>
                </div>
            </div>
        </div>
    </div>
    <!--foot部分结束-->

<!--悬浮窗口-->
	<div class="navbar-fixed">
        <div class="fixed-a">
            <p>联系客服</p>
            <div class="show-box-a">
                <div class="tel-icon"><i></i><span>4000965099 </span></div>
                <div class="qq-icon"><i></i><span>52533958（同微信号）</span></div>
                <div class="email-icon"><i></i><span>52533958@qq.com</span></div>
            </div>
        </div>
        <div class="fixed-b" id="fixed_b">
            <p>了解揽客</p>
            <div class="show-box-b" id="show_box_b"><img src="<%=basePath%>static/login/images/lanke.png" alt=""></div>
        </div>
        <!--<div class="fixed-c" id="fixed_c">
            <p>关注易购</p>
            <div class="show-box-c"><img src="<%=basePath%>static/login/images/erweima.png" alt=""></div>
        </div>-->
        <%--<div class="fixed-d" id="fixed_d">--%>
            <%--<p>关注网维</p>--%>
            <%--<div class="show-box-d"><img src="<%=basePath%>static/login/images/wangwei.png" alt=""></div>--%>
        <%--</div>--%>
        <div class="fixed-e"> <p>回到顶部</p></div>
    </div>
<!--悬浮窗口结束-->
</body>
<script src="<%=basePath%>static/login/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>static/login/js/bootstrap.min.js"></script>
<script type="text/javascript">
    //读取站内访问量，动态加载百度的JS
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?99747536a31c41ed484b4f8c19896d9c";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();

</script>
<script>
		$(".fixed-e").click(function(){
        	$('html, body').animate({
          	 scrollTop : $(".borBot").offset().top
        	}, 750);
		})
</script>
</html>