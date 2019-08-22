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
    <title>揽客 –网咖线上营销利器/专注于网咖的线上营销工具</title>
    <meta charset="UTF-8" />
    <meta name="keywords" content="揽客，营销，网咖营销，微信公众号" >
    <meta name="description" content="基于微信公众平台，专注于会员离店营销、网吧多元化服务场景营销等功能，为网吧行业定制的微信营销工具。让网吧轻松实现微信营销，盘活会员资源，获取增值收益。"  >
    <meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=0.5, maximum-scale=1.0, minimum-scale=0.1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/login.css">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/animate.min.css" media="screen and (min-width: 828px)">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/awesome-bootstrap-checkbox.css">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/font-awesome.min.css">
    <style>
        body .layui-layer-nobg iframe{border-radius: 4px;}
        body .layui-layer-nobg .layui-layer-setwin {right: 220px;top: 638px;}
    </style>
    <script type="text/javascript">
        www()
        function www(){
            //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
            var curWwwPath=window.document.location.href;
            var n=curWwwPath.indexOf("/");
            var l=curWwwPath.lastIndexOf("/");
            var localhostPaht=curWwwPath.substring(n+2,n+5);
            var name = curWwwPath.substring(n+2,l);
            if(localhostPaht == "www"){
                var herf = name.substring(4)
                window.location.href = "https://"+herf;
            }else{
                return
            }
        }
    </script>
</head>
<body style="background: rgba(36,39,47,1);min-width: 1270px">
<!--头部-->
<div class="container-fluid borBot la-nav-bg">
    <header class="lk-container">
        <div class="lanke-logo"></div>
        <span class="lanke-ggy">客服热线：0571-86622520</span>
        <p class="nav_p">
            <a href="" class="active">首页</a>
            <a href="javascript:void(0);" class="click-a">揽客介绍</a>
            <a href="<%=basePath%>register/introduction.do"  class="">入驻流程</a>
            <a href="<%=basePath%>register/helpCenter.do"  class="">帮助中心</a>
            <a href="<%=basePath%>register/about.do" style="margin: 0"  class="">关于我们</a>
        </p>
    </header>
</div>
<div class="firstbanner">
    <div class="lk-container">
        <div class="lk-banner float-left">
            <!--轮播图-->
            <div id="carousel-example-generic" class="carousel slide bannerHeight" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                </ol>
                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active" onclick="scrolled('smsj')">
                        <a href="javascript:void(0);"><img src="<%=basePath%>static/login/images/web_banner1.jpg" alt="..." class="bannerImg"></a>
                    </div>
<%--                    <div class="item">--%>
<%--                        <a href=""><img src="<%=basePath%>static/login/images/new-banner33.png" alt="..." class="bannerImg"></a>--%>
<%--                    </div>--%>
                    <div class="item" onclick="scrolled('zxcz')">
                        <a href="javascript:void(0);"><img src="<%=basePath%>static/login/images/new-banner4.jpg" alt="..." class="bannerImg"></a>
                    </div>
                    <div class="item" onclick="scrolled('wzys')">
                        <a href="javascript:void(0);"><img src="<%=basePath%>static/login/images/new-banner22.jpg" alt="..." class="bannerImg"></a>
                    </div>
                    <div class="item">
                        <a href=""><img src="<%=basePath%>static/login/images/new-banner1.jpg" alt="..." class="bannerImg"></a>
                    </div>
                    <%--<div class="item">
                        <a href=""><img src="<%=basePath%>static/login/images/new-banner3.jpg" alt="..." class="bannerImg"></a>
                    </div>--%>
                </div>
                <!-- Controls -->
                <!--<a class="left-bj left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left center1" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right-bj right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right center1" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>-->
            </div>
            <!--轮播图-->
        </div>
        <div class="lk-userbox float-right">
            <div class="userBox">
                <p class="userBox-title">用户登入</p>
                <div class="lk-user-input">
                    <i class="name"></i>
                    <input type="text" name="loginname" id="loginname" value="" placeholder="请输入用户名" required>
                </div>
                <div class="lk-user-input">
                    <i class="password"></i>
                    <input type="password" name="password" onkeyup="checkPsd()" id="password" placeholder="请输入密码" value=""  required />
                    <input type="hidden" id="hiddenPPd">
                </div>
                <div class="lk-user-input">
                    <i class="password"></i>
                    <input type="text" name="code" id="code" class="code" placeholder="验证码" />
                    <img id="codeImgA" alt="点击更换" title="点击更换" src="" width="130px" height="40px">
                </div>
                <div class="lk-user-input">
                    <div class="checkbox checkbox-danger" style="margin: 0;display: inline-block">
                        <input name="form-field-checkbox" id="saveid" type="checkbox" class="styled outline" />
                        <label for="saveid" style="color:#81848c" class="font-12">
                            记住密码
                        </label>
                    </div>
                    <a href="<%=basePath%>register/gotoretrieve.do" style="color:#81848c"  class="float-right" target="_blank">忘记密码</a>
                </div>
                <div class="lk-user-input">
                    <input type="button" class="btna" id="to-recover" value="登入" onclick="severCheck();" />
                </div>
                <div class="lk-user-input">
                    <input type="button" class="btnb" id="to-register" value="注册" onclick="goRegister();" />
                </div>
            </div>
        </div>
    </div>
</div>

<input id="internet_id" type="hidden">
<!-- Modal 授权提示-->
<div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog " role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">授权提示</h4>
            </div>
            <div class="modal-body">
                <div style="display: inline-block; position: absolute;">
                    <img src="<%=basePath%>static/login/images/eimg.png" alt="">
                </div>
                <div style="display: inline-block; margin: 20px 0 20px 296px;">
                    <p style="font-size: 20px; color: #333;">很抱歉您当前的账号没有进行</p>
                    <p style="font-size: 20px; color: #333;">微信公众号授权</p>
                    <p style="color: #999; margin-top: 10px;">导致无法正常登入揽客后台...</p>
                    <p style="margin-top: 36px">
                        <a href="javascript:void(0);" id="shou"
                           style="font-size: 16px; color: #fff; background-color: #e44547; padding: 8px 66px; border-radius: 50px"
                           onclick="goAuthoriz()">去授权</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal 授权提示(流失用户)-->
<div class="modal fade bs-example-modal-lg" id="myModal1" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog " role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">授权提示</h4>
            </div>
            <div class="modal-body">
                <div style="display: inline-block; position: absolute;">
                    <img src="<%=basePath%>static/login/images/eimg.png" alt="">
                </div>
                <div style="display: inline-block; margin: 20px 0 20px 296px;">
                    <p style="font-size: 15px; color: #333;">请用原公众号授权,如用新公众号授权</p>
                    <p style="font-size: 15px; color: #333;">将会导致原公众号相关信息丢失</p>
                    <p style="color: #999; margin-top: 10px;">建议使用原公众号,请谨慎操作...</p>
                    <p style="margin-top: 36px">
                        <a href="javascript:void(0);" id="shou1"
                           style="font-size: 16px; color: #fff; background-color: #e44547; padding: 8px 66px; border-radius: 50px"
                           onclick="goAuthoriz()">去授权</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<div  class="container-fluid cp-bj" id="intro">
    <div class="lk-container lk-item1">
        <div class="lk-col-md wow bounceInLeft left">
            <img src="<%=basePath%>static/login/images/new-lanke-index.png" alt="">
        </div>
        <div class="wow bounceInRight right lk-col-md" style="">
            <div style="padding: 0 30px 0 36px">
                <p class="lankejs">什么是揽客？</p>
                <p class="lankecenten lk-text">
                    “揽客”是网晶科技基于微信公众平台、结合网晶十余年网吧行业服务经验、围绕会员价值最大化的目标，为网吧行业定制的专属微信营销工具。
                </p>
                <p class="lk-text">
                    “揽客”专注于会员离店营销、网吧多元化服务场景营销等功能，并将持续整合增值业务，让网吧轻松实现微信营销，盘活会员资源，提高营业收入。
                </p>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid" style="padding: 60px 0;" id="smsj">
    <div class="widths" style="">
        <div class="lk-col-md wow bounceInLeft gnjsIcon-left" style="padding-left:90px">
            <p class="lankejs lkmartt" style="margin-top: 148px;">微信扫码轻松完成上机</p>
            <div class="lkmarpt lkmarbt">
                <span class="gnjsIcon gnjsIcon9"></span>
                <p class="disp"><span>自动开机</span></br>后台完成设置，扫码一键开机</p>
            </div>
            <div class="lkmarbt">
                <span class="gnjsIcon gnjsIcon10"></span>
                <p class="disp"><span>一键登录</span></br>登录一键直达，无需输入卡号密码，便捷又快速</p>
            </div>
            <div class="lkmarbt">
                <span class="gnjsIcon gnjsIcon11"></span>
                <p class="disp"><span>远程操控</span></br>扫码自由换、下机，随时随地操作，轻松便捷</p>
            </div>
        </div>
        <div class="lk-col-md wow bounceInRight lk-col-66" style="text-align: right;width:56%;float:right;">
            <div class="lkpoh-img">
                <img src="<%=basePath%>static/login/images/weixin_smsj.png" alt="" style="width: 71%;margin-right: 36px;">
            </div>
        </div>
    </div>
</div>
<div class="container-fluid cp-bj" style="padding: 72px 0;">
    <div class="widths">
        <div class="lk-col-md wow bounceInDown lk-col-50" style="margin-left: -45px;">
            <div class="lkpoh-img">
                <img src="<%=basePath%>static/login/images/weixin_gmsp.png" alt="">
            </div>
        </div>
        <div class="lk-col-md wow  bounceInUp lk-col-50" style="text-align: right;">
            <p class="lankejs lkmartt">全面提升网咖服务质量</p>
            <div class="lkmarpt lkmarbt">
                <p class="disp"><span>网吧商城</span></br>后台自由添加商品，会员在线下单，轻松购物</p>
                <span class="gnjsIcon gnjsIcon12" style="margin-bottom: 0"></span>
            </div>
            <div class="lkmarbt">
                <p class="disp"><span>一键反馈</span></br>会员一键呼叫网管、投诉，随时反馈问题上网更舒心</p>
                <span class="gnjsIcon gnjsIcon13" style="margin-bottom: 0"></span>
            </div>
            <div class="lkmarbt">
                <p class="disp"><span>及时处理</span></br>服务人员手机微信接收反馈信息，处理不遗漏</p>
                <span class="gnjsIcon gnjsIcon14" style="margin-bottom: 0"></span>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid" style="padding: 60px 0;" id="zxcz">
        <div class="widths" style="">
                <div class="lk-col-md wow bounceInLeft gnjsIcon-left" style="padding-left:90px">
                    <p class="lankejs lkmartt" style="margin-top: 170px;">轻松完成在线充值</p>
                    <div class="lkmarpt lkmarbt">
                        <span class="gnjsIcon gnjsIcon6"></span>
                        <p class="disp"><span>充值管理</span></br>可全程线上操作，大幅减轻人工压力、快捷又安全 </p>
                    </div>
                    <div class="lkmarbt">
                        <span class="gnjsIcon gnjsIcon7"></span>
                        <p class="disp"><span>会员充值</span></br>不用到店，省时又便利，不用网管，全自助操作</p>
                    </div>
                    <div class="lkmarbt">
                        <span class="gnjsIcon gnjsIcon8"></span>
                        <p class="disp"><span>资金明细</span></br>线上查看流水账单，清晰又方便</p>
                    </div>
                </div>
                <div class="lk-col-md wow bounceInRight lk-col-66" style="text-align: right;width:56%;float:right;">
                    <div class="lkpoh-img">
                        <img src="<%=basePath%>static/login/images/recharge-gnjs.png" alt="">
                    </div>
                </div>
        </div>
    </div>
<!-- vip功能
    <div class="container-fluid">
        <div class="widths"style="padding-bottom: 90px">
            <p class="lankejs text-center margin wow zoomInDown">VIP版重磅功能免费体验</p>
            <div class="">
                <div class="float-left">
                    <img src="<%=basePath%>static/login/images/web-picture.jpg" alt="">
                </div>
                <div class="float-left" style="width: 905px">
                    <img src="<%=basePath%>static/login/images/web-line.png" alt="">
                    <div class="width33 vipTest float-left">
                        <div class="vipTest-icon">
                             <div class="icon icon1"></div>
                            <div class="six">
                                <div class="box1"></div>
                                <div class="box2"></div>
                                <div class="box3"></div>
                            </div>
                            <div class="box-showdown"></div>
                        </div>
                        <p class="vipTest-text">PUBWIN万象数据打通</p>
                    </div>
                    <div class="width33 vipTest float-left">
                        <div class="vipTest-icon">
                            <div class="icon icon2"></div>
                            <div class="six">
                                <div class="box1"></div>
                                <div class="box2"></div>
                                <div class="box3"></div>
                            </div>
                            <div class="box-showdown"></div>
                        </div>
                        <p class="vipTest-text">现金优惠券直充到账</p>
                    </div>
                    <div class="width33 vipTest float-left">
                        <div class="vipTest-icon">
                            <div class="icon icon3"></div>
                            <div class="six">
                                <div class="box1"></div>
                                <div class="box2"></div>
                                <div class="box3"></div>
                            </div>
                            <div class="box-showdown"></div>
                        </div>
                        <p class="vipTest-text">会员分组 精准营销</p>
                    </div>
                    <div class="width33 vipTest float-left">
                        <div class="vipTest-icon">
                            <div class="icon icon4"></div>
                            <div class="six">
                                <div class="box1"></div>
                                <div class="box2"></div>
                                <div class="box3"></div>
                            </div>
                            <div class="box-showdown"></div>
                        </div>
                        <p class="vipTest-text">会员消费记录随身查</p>
                    </div>
                    <div class="width33 vipTest float-left">
                        <div class="vipTest-icon">
                            <div class="icon icon5"></div>
                            <div class="six">
                                <div class="box1"></div>
                                <div class="box2"></div>
                                <div class="box3"></div>
                            </div>
                            <div class="box-showdown"></div>
                        </div>
                        <p class="vipTest-text">用网费轻松购买商品（即将上线）</p>
                    </div>
                    <div class="width33 vipTest float-left">
                        <div class="vipTest-icon">
                            <div class="icon icon6"></div>
                            <div class="six">
                                <div class="box1"></div>
                                <div class="box2"></div>
                                <div class="box3"></div>
                            </div>
                            <div class="box-showdown"></div>
                        </div>
                        <p class="vipTest-text">在线充值，轻松便利（重磅推出）</p>
                    </div>
                </div>
            </div>   
        </div>
    </div>-->

<div class="container-fluid cp-bj" style="padding: 72px 0;">
    <div class="widths">
        <div class="lk-col-md wow bounceInDown lk-col-50" style="margin-left: -45px;">
            <div class="lkpoh-img">
                <img src="<%=basePath%>static/login/images/lanke-baobiao.png" alt="">
            </div>
        </div>
        <div class="lk-col-md wow  bounceInUp lk-col-50" style="text-align: right;">
            <p class="lankejs lkmartt">轻松管理网吧营销报表</p>
            <div class="lkmarpt lkmarbt">
                <p class="disp"><span>门店管理</span></br>支持连锁多门店，分店管理功能强大</p>
                <span class="gnjsIcon" style="margin-bottom: 0"></span>
            </div>
            <div class="lkmarbt">
                <p class="disp"><span>人员管理</span></br>支持配置人员和权限，安全又灵活</p>
                <span class="gnjsIcon1 gnjsIcon" style="margin-bottom: 0"></span>
            </div>
            <div class="lkmarbt">
                <p class="disp"><span>报表体系</span></br>支持营销报表筛选导出，细化到人，轻松交班</p>
                <span class="gnjsIcon gnjsIcon2" style="margin-bottom: 0"></span>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid" style="padding: 60px 0;" id="wzys">
    <div class="widths" style="">
        <div class="lk-col-md wow bounceInLeft gnjsIcon-left">
            <p class="lankejs lkmartt" style="margin-top: 190px;">轻松打理微信公众号</p>
            <div class="lkmarpt lkmarbt">
                <span class="gnjsIcon gnjsIcon3"></span>
                <p class="disp"><span>扫码授权</span></br>微信扫码授权，一键导入粉丝，简单两步， 您的微信公众号立刻焕新 </p>
            </div>
            <div class="lkmarbt">
                <span class="gnjsIcon gnjsIcon4"></span>
                <p class="disp"><span>全面对接</span></br>揽客后台与微信全面对接，微信营销、群发图文只需登录揽客后台</p>
            </div>
            <div class="lkmarbt">
                <span class="gnjsIcon gnjsIcon5"></span>
                <p class="disp"><span>丰富样式</span></br>揽客编辑器，整合丰富样式，轻松美化您 的微信图文</p>
            </div>
        </div>
        <div class="lk-col-md wow bounceInRight lk-col-66" style="text-align: right;width:56%;float:right;">
            <div class="lkpoh-img">
                <img src="<%=basePath%>static/login/images/weinxin-gnjs.png" alt="">
            </div>
        </div>
    </div>
</div>
<div class="container-fluid cp-bj" style="padding: 69px 0;">
    <div class="widths"style="padding-bottom: 90px;width: 1202px;">
        <p class="lankejs text-center margin wow zoomInDown">轻松玩转会员离店营销</p>
        <table>
            <tbody>
            <tr>
                <td style="vertical-align: top">
                    <div class="lk-box1 wow fadeInLeft">
                        <div class="text-center marTop30">
                            <img src="<%=basePath%>static/login/images/icon-quan.png" alt="">
                        </div>
                        <h3 class="text-center five-title">优惠券</h3>
                        <p class="five-text text-center">比微信优惠券更丰富的营销场景，让您随心设置优惠活动，会员营销更精准、更有趣</p>
                    </div>
                </td>
                <td style="vertical-align: top">
                    <div class="lk-box1 wow fadeInLeft">
                        <div class="text-center marTop30">
                            <img src="<%=basePath%>static/login/images/icon-chong.png" alt="">
                        </div>
                        <h3 class="text-center five-title">在线充值</h3>
                        <p class="five-text text-center">网吧会员不用到店，即可在线充值会员卡，提高上网费的预存率，为您回笼更多资金</p>
                    </div>
                </td>
                <td style="vertical-align: top">
                    <div class="lk-box1 wow fadeInUp">
                        <div class="text-center marTop30">
                            <img src="<%=basePath%>static/login/images/icon-jifeng.png" alt="">
                        </div>
                        <h3 class="text-center five-title">积分系统</h3>
                        <p class="five-text text-center">会员用微信赢积分、换礼品，既有趣味又有利益，会员常常看微信，不再错过您的营销</p>
                    </div>
                </td>
                <td style="vertical-align: top">
                    <div class="lk-box1 wow fadeInRight">
                        <div class="text-center marTop30">
                            <img src="<%=basePath%>static/login/images/icon-yao.png" alt="">
                        </div>
                        <h3 class="text-center five-title">邀请好友</h3>
                        <p class="five-text text-center">把线下的老带新活动做到线上，会员分享更方便，赢取奖励更快捷，为您带来新的客源</p>
                    </div>
                </td>
                <td style="vertical-align: top">
                    <div class="lk-box1 wow fadeInRight" style="margin-right: 0">
                        <div class="text-center marTop30">
                            <img src="<%=basePath%>static/login/images/icon-zuju.png" alt="">
                        </div>
                        <h3 class="text-center five-title">赛事群发</h3>
                        <p class="five-text text-center">尽情发布网吧游戏赛事，发起热门赛事组局，打造网吧小社交，把您的会员再度拉回来</p>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<!--合作伙伴-->
<div class="container-fluid" style="padding: 0">
    <div class="widths hz-margin">
        <p class="lankejs text-center wow zoomInUp ">他们都在用</p>
        <div class="row hz-auto">
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk1 wow rotateIn"></div>
                </div>
                <p class=" wk-name">艾上网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo ">
                    <div class="hz-wk2 wow rotateIn"></div>
                </div>
                <p class=" wk-name">大神网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk3 wow rotateIn"></div>
                </div>
                <p class=" wk-name">雅鱼网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk4 wow rotateIn"></div>
                </div>
                <p class=" wk-name">豆豆网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk5 wow rotateIn"></div>
                </div>
                <p class=" wk-name">黑玉网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk6 wow rotateIn"></div>
                </div>
                <p class=" wk-name">优乐屋网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk7 wow rotateIn"></div>
                </div>
                <p class=" wk-name">许多鱼网咖</p>
            </div>
        </div>
        <div class="row hz-auto" style="margin-top: -30px!important;">
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk8 wow rotateIn"></div>
                </div>
                <p class=" wk-name">龙城网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo ">
                    <div class="hz-wk9 wow rotateIn"></div>
                </div>
                <p class=" wk-name">LF网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk10 wow rotateIn"></div>
                </div>
                <p class=" wk-name">中兴网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk13 wow rotateIn"></div>
                </div>
                <p class=" wk-name">奇乐速订</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk12 wow rotateIn"></div>
                </div>
                <p class=" wk-name">新概念网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk14 wow rotateIn"></div>
                </div>
                <p class=" wk-name">网客网咖</p>
            </div>
            <div class="hz-wkbox">
                <div class="wk-logo">
                    <div class="hz-wk11 wow rotateIn"></div>
                </div>
                <p class=" wk-name">瘾橙网咖</p>
            </div>
        </div>
    </div>
</div>
<!--合作伙伴结束-->

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

<script src="<%=basePath%>static/login/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/jquery.base64.js"></script>
<script src="<%=basePath%>static/login/js/wow.js"></script>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/js/lk-login.js"></script>
<script src="<%=basePath%>static/login/js/jquery.cookie.js"></script>
<script src="<%=basePath%>static/login/js/bootstrap.min.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>

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
    //TOCMAT重启之后 点击左侧列表跳转登录首页
    if (window != top) {
        top.location.href = location.href;
    }

    function scrolled(id){
        $('html, body').animate({
            scrollTop : $("#"+id).offset().top
        }, 750);
    }
</script>
<%-- 	<c:if test="${'1' == pd.msg}">
		<script type="text/javascript">
			$(tsMsg());
			function tsMsg() {
				alert('此用户在其它终端已经早于您登录,您暂时无法登录');
			}
		</script>
	</c:if> --%>
<c:if test="${'2' == pd.msg}">
    <script type="text/javascript">
        $(tsMsg());
        function tsMsg() {
            alert('您被系统管理员强制下线');
        }
    </script>
</c:if>
</body>
</html>