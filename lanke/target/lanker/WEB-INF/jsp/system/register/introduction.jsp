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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=0.8, maximum-scale=1.0, minimum-scale=0.1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>揽客帮助中心</title>
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/login.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
</head>
<style>
    p {font-size: 14px;}
    @media screen and (min-width: 992px){
        .layui-col-md2 {width: 20%;}
    }
    @media screen and (min-width: 768px){
       /* p {font-size: 14px;}*/
    }
    .some-xinxi p {padding-right: 98px;}
    a {color: #2a91ef; }
</style>
<body>
<!--头部-->
<div class="container-fluid borBot la-nav-bg layui-hide-xs">
    <header class="lk-container layui-container">
        <div class="lanke-logo"></div>
        <span class="lanke-ggy">客服热线：0571-86622520</span>
        <p class="nav_p">
            <a href="<%=basePath%>login_toLogin">首页</a>
            <a href="<%=basePath%>login_toLogin" class="click-a">揽客介绍</a>
            <a href="<%=basePath%>register/introduction.do"  class="active">入驻流程</a>
            <a href="<%=basePath%>register/helpCenter.do"  class="">帮助中心</a>
            <a href="<%=basePath%>register/about.do" style="margin: 0"  class="">关于我们</a>
        </p>
    </header>
</div>

<div class="introduction-banner"></div>
<div class="maxWidth1100 layui-container">
    <div class="introduction-item">
        <div class="introduction-title">
            <p>网吧入驻流程</p>
            <p class="line"></p>
        </div>
        <div class="layui-row">
            <div class="layui-col-md2 layui-col-xs6 layui-col-sm4">
                <div class="flow-icon flow-icon1"></div>
                <p class="flow">1.申请微信服务号</p>
            </div>
            <div class="layui-col-md2 layui-col-xs6 layui-col-sm4">
                <div class="flow-icon flow-icon2"></div>
                <p class="flow">2.pubwin版本升级</p>
            </div>
            <div class="layui-col-md2 layui-col-xs6 layui-col-sm4">
                <div class="flow-icon flow-icon3"></div>
                <p class="flow">3.注册揽客账号</p>
            </div>
            <div class="layui-col-md2 layui-col-xs6 layui-col-sm4">
                <div class="flow-icon flow-icon4"></div>
                <p class="flow">4.服务号授权成功</p>
            </div>
            <div class="layui-col-md2 layui-col-xs6 layui-col-sm4">
                <div class="flow-icon flow-icon5"></div>
                <p class="flow">5.登入后台开始使用</p>
            </div>
        </div>
    </div>
</div>
<div class="introduction-bg">
    <div class="maxWidth1100 layui-container">
        <div class="introduction-item">
            <div class="introduction-title">
                <p>网吧授权准备</p>
                <p class="line"></p>
            </div>
            <div class="introduction-setout">
                <div class="layui-row">
                    <div class="layui-col-md6">
                        <div class="setout">
                            <p class="setout-title">申请服务号</p>
                            <p class="setout-content">“揽客”分别由后台管理系统和微信用户端组成，其中微信用户需要统一关注网吧的服务号方可使用。</p>
                            <p class="setout-content">网吧管理者要拥有自己的服务号，如没有，请从微信公众平台官网：<a href="https://mp.weixin.qq.com/">https://mp.weixin.qq.com/</a>进行注册，注册类型必须为服务号。</p>
                            <p class="setout-content">注册成功后需注意两点：微信服务号必须要有头像；服务号需开通卡券功能。卡券功能开通请登录微信公众平台，然后在“添加功能插件”中添加卡券功</p>
                            <span class="setout-btn" onclick="seeImg('#img1','700px')">查看案列</span>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <div class="setout">
                            <p class="setout-title">pubwin版本升级</p>
                            <p class="setout-content">揽客管理后台是基于当前通用的pubwinol版本进行开发的，所以，目前揽客支持使用的pubwinol版本为6.3.30.1及以上。</p>
                            <p class="setout-content">如果版本低于要求版本，我公司可以免费快速为网吧升级pubwinol版本(限杭州地区)。
                                如不了解自己的pubwinol版本，可以通过pubwinol系统的“帮助”菜单中的“关于”查看。</p>
                            <p class="setout-content">如需版本升级请联系客服：电话：4000965099</p>
                            <span class="setout-btn" onclick="seeImg('#img2','700px')">查看案列</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="maxWidth1100 layui-container">
    <div class="introduction-item">
        <div class="introduction-title">
            <p>注册揽客账号及授权</p>
            <p class="line"></p>
        </div>
        <div class="introduction-register">
            <p class="register-title">注册揽客账号</p>
            <p class="register-content">注册揽客请登录：<a href="https://www.lanke8.cc">https://www.lanke8.cc</a>。在该网站首页选择注册，填写完成相应的信息。注册完成后，会弹出进行授权的提示框，此处的授权就是将揽客和微信服务号进行绑定的过程。点击“去授权”后，页面会弹到微信的二维码页面，用注册服务号的微信扫码授权即可。
                如果用户在这里点击了“不了”也不必担心，在用刚才注册的账号登陆时同样会有提示授权的弹窗。</p>
            <span class="setout-btn" onclick="seeImg('#img3','450px')">查看案列</span>
        </div>
        <div class="introduction-register">
            <p class="register-title">授权失败的原因</p>
            <p class="register-content">若失败提示为：“很遗憾，授权失败！请不要自定义修改权限，该操作会导致部分功能失效，请取消授权后再次授权！”</p>
            <p class="register-content">该失败原因是用户在授权时手动修改了微信服务号提供给揽客的权限列表。处理方法为：去微信公众平台—公众号设置—授权管理—查看平台详情进行取消授权，然后再去揽客官网重新登录授权。</p>
            <span class="setout-btn" onclick="seeImg('#img4','750px')">查看案列</span>
        </div>
        <div class="introduction-register">
            <p class="register-title">授权成功，无法正常登录</p>
            <p class="register-content">若登入提示为：“您当前绑定公众号类型为订阅号,无法正常使用功能,请换绑为服务号,如有疑问请联系客服,客服电话:4000965099！”</p>
            <p class="register-content">提示如上，则用户授权揽客的公众号为非服务号类型。处理方式如上，需先取消授权，然后用正确的服务号进行授权。</p>
        </div>
        <div class="introduction-end"></div>
        <div class="introduction-go"><a href="https://www.lanke8.cc">GO</a></div>
        <p style="text-align: center;padding: 0 20px">一切准备就绪，开始使用揽客吧，为您的网吧带来更多的收益！</p>
    </div>
</div>
<!--foot部分-->
<div class="container-fluid foot-bj layui-hide-xs">
    <div class="widths  layui-container">
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
<img src="<%=basePath%>static/login/images/introduction1.jpg" alt="" style="display: none;width: 100%" id="img1">
<img src="<%=basePath%>static/login/images/introduction2.jpg" alt="" style="display: none;width: 100%" id="img2">
<img src="<%=basePath%>static/login/images/introduction3.jpg" alt="" style="display: none;width: 100%" id="img3">
<img src="<%=basePath%>static/login/images/introduction4.jpg" alt="" style="display: none;width: 100%" id="img4">
<!--foot部分结束-->
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
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
    function seeImg(obj,px){
        var widths = $(window).width()
        if(widths < 560){
            px = '100%'
        }
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            area: px,
            skin: 'layui-layer-nobg', //没有背景色
            shadeClose: true,
            move: 'img',
            moveOut: true,
            content: $(obj)
        });
    }
</script>

<script>
    ;(function ($, window, document, undefined) {
        if ($('ul.mtree').length) {
            var collapsed = true;
            var close_same_level = false;
            var duration = 400;
            var listAnim = true;
            var easing = 'easeOutQuart';
            $('.mtree ul').css({
                'overflow': 'hidden',
                'height': collapsed ? 0 : 'auto',
                'display': collapsed ? 'none' : 'block'
            });
            var node = $('.mtree li:has(ul)');
            node.each(function (index, val) {
                $(this).children(':first-child').css('cursor', 'pointer');
                $(this).addClass('mtree-node mtree-' + (collapsed ? 'closed' : 'open'));
                $(this).children('ul').addClass('mtree-level-' + ($(this).parentsUntil($('ul.mtree'), 'ul').length + 1));
            });
            $('.mtree li > *:first-child').on('click.mtree-active', function (e) {
                if ($(this).parent().hasClass('mtree-closed')) {
                    $('.mtree-active').not($(this).parent()).removeClass('mtree-active');
                    $(this).parent().addClass('mtree-active');
                } else if ($(this).parent().hasClass('mtree-open')) {
                    $(this).parent().removeClass('mtree-active');
                } else {
                    $('.mtree-active').not($(this).parent()).removeClass('mtree-active');
                    $(this).parent().toggleClass('mtree-active');
                }
            });
            node.children(':first-child').on('click.mtree', function (e) {
                var el = $(this).parent().children('ul').first();
                var isOpen = $(this).parent().hasClass('mtree-open');
                if ((close_same_level || $('.csl').hasClass('active')) && !isOpen) {
                    var close_items = $(this).closest('ul').children('.mtree-open').not($(this).parent()).children('ul');
                    if ($.Velocity) {
                        close_items.velocity({ height: 0 }, {
                            duration: duration,
                            easing: easing,
                            display: 'none',
                            delay: 100,
                            complete: function () {
                                setNodeClass($(this).parent(), true);
                            }
                        });
                    } else {
                        close_items.delay(100).slideToggle(duration, function () {
                            setNodeClass($(this).parent(), true);
                        });
                    }
                }
                el.css({ 'height': 'auto' });
                if (!isOpen && $.Velocity && listAnim)
                    el.find(' > li, li.mtree-open > ul > li').css({ 'opacity': 0 }).velocity('stop').velocity('list');
                if ($.Velocity) {
                    el.velocity('stop').velocity({
                        height: isOpen ? [
                            0,
                            el.outerHeight()
                        ] : [
                            el.outerHeight(),
                            0
                        ]
                    }, {
                        queue: false,
                        duration: duration,
                        easing: easing,
                        display: isOpen ? 'none' : 'block',
                        begin: setNodeClass($(this).parent(), isOpen),
                        complete: function () {
                            if (!isOpen)
                                $(this).css('height', 'auto');
                        }
                    });
                } else {
                    setNodeClass($(this).parent(), isOpen);
                    el.slideToggle(duration);
                }
                e.preventDefault();
            });
            function setNodeClass(el, isOpen) {
                if (isOpen) {
                    el.removeClass('mtree-open').addClass('mtree-closed');
                } else {
                    el.removeClass('mtree-closed').addClass('mtree-open');
                }
            }
            if ($.Velocity && listAnim) {
                $.Velocity.Sequences.list = function (element, options, index, size) {
                    $.Velocity.animate(element, {
                        opacity: [
                            1,
                            0
                        ],
                        translateY: [
                            0,
                            -(index + 1)
                        ]
                    }, {
                        delay: index * (duration / size / 2),
                        duration: duration,
                        easing: easing
                    });
                };
            }
            if ($('.mtree').css('opacity') == 0) {
                if ($.Velocity) {
                    $('.mtree').css('opacity', 1).children().css('opacity', 0).velocity('list');
                } else {
                    $('.mtree').show(200);
                }
            }
        }
    }(jQuery, this, this.document));
    $(document).ready(function () {
        var mtree = $('ul.mtree');
        mtree.wrap('<div class=mtree-demo></div>');
        var skins = [
            'bubba',
            'skinny',
            'transit',
            'jet',
            'nix'
        ];
        mtree.addClass(skins[2]);
        // $('body').prepend('<div class="mtree-skin-selector"><ul class="button-group radius"></ul></div>');
        // var s = $('.mtree-skin-selector');
        // $.each(skins, function (index, val) {
        //     s.find('ul').append('<li><button class="small skin">' + val + '</button></li>');
        // });
        // s.find('ul').append('<li><button class="small csl active">Close Same Level</button></li>');
        // s.find('button.skin').each(function (index) {
        //     $(this).on('click.mtree-skin-selector', function () {
        //         s.find('button.skin.active').removeClass('active');
        //         $(this).addClass('active');
        //         mtree.removeClass(skins.join(' ')).addClass(skins[index]);
        //     });
        // });
        // s.find('button:first').addClass('active');
        // s.find('.csl').on('click.mtree-close-same-level', function () {
        //     $(this).toggleClass('active');
        // });
    });
</script>
</body>
</html>