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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>newStyle/navbar/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>newStyle/navbar/css/default.css">
    <link rel='stylesheet prefetch' href='<%=basePath%>newStyle/navbar/css/foundation.css'>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>newStyle/navbar/css/styles.css">
</head>
<style>
    p {font-size: 14px;}
    .some-xinxi p {padding-right: 98px;}
    a {color: #2a91ef; }
    body{
        background: #e8e8e8;
    }
    .right-content .text{color: #333}
</style>
<body>
<!--头部-->
<div class="container-fluid borBot la-nav-bg layui-hide-xs" id="navbar">
    <header class="lk-container layui-container">
        <div class="lanke-logo"></div>
        <span class="lanke-ggy">客服热线：0571-86622520</span>
        <p class="nav_p">
            <a href="<%=basePath%>login_toLogin">首页</a>
            <a href="<%=basePath%>login_toLogin" class="click-a">揽客介绍</a>
            <a href="<%=basePath%>register/introduction.do"  class="">入驻流程</a>
            <a href="<%=basePath%>register/helpCenter.do"  class="active">帮助中心</a>
            <a href="<%=basePath%>register/about.do" style="margin: 0"  class="">关于我们</a>
        </p>
    </header>
</div>
<div class="blackTop" onclick="scrolled('navbar')">
    <i class="layui-icon">&#xe619;</i>
</div>
<div class="content">
    <div  class="left-nav" id="leftList">
        <!-- This is mtree list -->
        <ul class=mtree>
            <li><a href="#">热门问题</a>
                <ul>
                    <li onclick="scrolled('question1')"><a href="javascript:void(0);">揽客在网咖营销方面有什么特色</a></li>
                    <li onclick="scrolled('question2')"><a href="javascript:void(0);">使用揽客对网咖有什么帮助</a></li>
                    <li onclick="scrolled('question3')"><a href="javascript:void(0);">发出去那么多卡券，对我有好处吗</a></li>
                    <li onclick="scrolled('question4')"><a href="javascript:void(0);">公众号粉丝对我的网咖有哪些价值</a></li>
                    <li onclick="scrolled('question5')"><a href="javascript:void(0);">我已经在用其它营销软件了，为什么要用揽客</a></li>
                    <li onclick="scrolled('question6')"><a href="javascript:void(0);">怎么样才能使用揽客</a></li>
                    <li onclick="scrolled('question7')"><a href="javascript:void(0);">公众号对网咖有哪些作用？</a></li>
                    <li onclick="scrolled('question38')"><a href="javascript:void(0);">营销管理对网吧有何收益？</a></li>
                    <li onclick="scrolled('question39')"><a href="javascript:void(0);">在线申请会员对网咖的好处？</a></li>
                    <li onclick="scrolled('question40')"><a href="javascript:void(0);">门店及会员相关设置对网咖的意义？</a></li>
                </ul>
            </li>
            <li><a href="javascript:void(0);">注册问题</a>
                <ul>
                    <li><a href="javascript:void(0);">网吧微信服务号注册</a>
                        <ul>
                            <li onclick="scrolled('question8')"><a href="javascript:void(0);">如何注册微信服务号</a></li>
                            <li onclick="scrolled('question9')"><a href="javascript:void(0);">注册流程</a>
                            </li>
                        </ul>
                    </li>
                    <li onclick="scrolled('question10')"><a href="javascript:void(0);">微信服务号认证</a></li>
                    <li onclick="scrolled('question11')"><a href="javascript:void(0);">注册完成怎么开通卡券功能？</a></li>
                    <li><a href="javascript:void(0);">揽客注册</a>
                        <ul>
                            <li onclick="scrolled('question12')"><a href="javascript:void(0);">揽客注册流程</a></li>
                            <li onclick="scrolled('question13')"><a href="javascript:void(0);">授权失败处理办法</a></li>
                        </ul>
                    </li>
                    <li><a href="javascript:void(0);">VIP服务/打通计费数据</a>
                        <ul>
                            <li onclick="scrolled('question14')"><a href="javascript:void(0);">绑定计费系统</a></li>
                            <li onclick="scrolled('question15')"><a href="javascript:void(0);">开通在线支付</a></li>
                            <li onclick="scrolled('question16')"><a href="javascript:void(0);">开通扫码上机</a></li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li><a href="javascript:void(0);">功能模块</a>
                <ul>
                    <li><a href="javascript:void(0);">在线充值</a>
                        <ul>
                            <li onclick="scrolled('question17')"><a href="javascript:void(0);">功能介绍</a></li>
                            <li onclick="scrolled('question18')"><a href="javascript:void(0);">在线充值有何好处？</a>
                            </li>
                        </ul>
                    </li>
                    <li><a href="javascript:void(0);">营销管理</a>
                        <ul>
                            <li onclick="scrolled('question19')"><a href="javascript:void(0);">卡券功能</a></li>
                            <li onclick="scrolled('question20')"><a href="javascript:void(0);">卡券核销</a>
                            <li onclick="scrolled('question21')"><a href="javascript:void(0);">活动群发</a>
                            <li onclick="scrolled('question22')"><a href="javascript:void(0);">积分抽奖</a>
                            <li onclick="scrolled('question23')"><a href="javascript:void(0);">赛事系统</a>
                            <li onclick="scrolled('question24')"><a href="javascript:void(0);">拉新功能</a>
                            <li onclick="scrolled('question25')"><a href="javascript:void(0);">在线办理会员</a>
                            </li>
                        </ul>
                    </li>
                    <li><a href="javascript:void(0);">门店及会员管理</a>
                        <ul>
                            <li onclick="scrolled('question26')"><a href="javascript:void(0);">门店管理</a></li>
                            <li onclick="scrolled('question27')"><a href="javascript:void(0);">角色管理、人员管理</a></li>
                            <li onclick="scrolled('question28')"><a href="javascript:void(0);">会员管理</a></li>
                        </ul>
                    </li>
                    <li><a href="javascript:void(0);">公众号管理</a>
                        <ul>
                            <li onclick="scrolled('question29')"><a href="javascript:void(0);">图文发布</a></li>
                            <li onclick="scrolled('question30')"><a href="javascript:void(0);">自定义菜单</a></li>
                            <li onclick="scrolled('question31')"><a href="javascript:void(0);">自动回复</a></li>
                        </ul>
                    </li>
                    <li onclick="scrolled('question32')"><a href="javascript:void(0);">店内服务</a></li>
                </ul>
            </li>
            <li><a href="javascript:void(0);">更多问题</a>
                <ul>
                    <li onclick="scrolled('question33')"><a href="javascript:void(0);">网维产品说明</a></li>
                    <li onclick="scrolled('question34')"><a href="javascript:void(0);">PUBWIN版本不支持怎么办</a></li>
                    <li onclick="scrolled('question35')"><a href="javascript:void(0);">代理商使用说明</a></li>
                    <li onclick="scrolled('question36')"><a href="javascript:void(0);">如何使用卡余额购买自助机商品？</a></li>
                    <li onclick="scrolled('question37')"><a href="javascript:void(0);">使用卡余额有什么好处？</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="right-content">
        <div class="item" id="question1">
            <h1><font color="red">*</font>热门问题</h1>
            <h3>揽客在网咖营销方面有什么特色？</h3>
            <p class="text">揽客结合网晶科技十余年网吧行业服务经验，提出网吧多元化服务场景营销，在会员价值最大化的目标上专注于离店营销，盘活资源，让会员不再离开网吧就成陌生人。</p>
        </div>
        <div class="item" id="question2">
            <h3>使用揽客对网咖有什么帮助？</h3>
            <ul class="text">
                <li>打通万象、pubwinol计费，实现基于大数据分析的会员精准营销；</li>
                <li>线下流量变成线上流量，揽客让网咖收入提升15~20%；</li>
                <li>在线办会员、充值等，各种灵活的优惠券和抵扣券，营销服务场景延伸，可与其他行业（ktv，酒吧、大学等）互为引流；</li>
                <li>揽客有积分系统和赛事系统等功能，可以积分抽奖和组织电竞比赛，提高会员粘性。</li>
                <li>老带新、会员分享裂变等拉新手段上，充分利用移动互联网，拉新更简单。</li>
                <li>揽客文章库，让网吧发活动消息不再是难事，轻松搞定网吧微信运营；</li>
                <li>作为早一批接触揽客的网咖之一，艾格拉连锁经过两三月的运营后，用户到店消费人次和上网时长明显增加，带动水吧饮品消费，营收同比增长20%。</li>
            </ul>
        </div>
        <div class="item" id="question3">
            <h3>发出去那么多卡券，对我有好处吗？</h3>
            <p class="text">揽客根据会员充值能力、消费习惯等，给不同类型的会员发放对应的优惠券。网吧会员使用卡券在网吧消费，提高到店频次和上座率，活动期间现金流明显提升，大量数据实测证明，网咖通过揽客分发各种活动卡券，对门店业绩有较大提升。</p>
            <p class="text">比如，网吧下午时间都是营业低谷期，网咖推送特定时间折扣券，可以明显刺激会员下午空闲时段来店上网，减少机器空置率，提高收入。</p>
        </div>
        <div class="item" id="question4">
            <h3>公众号粉丝对我的网咖有哪些价值？</h3>
            <p class="text">网咖人流量越高，收益越大。粉丝作为准客户，是主要的消费群体。网咖在做活动和办赛事时，粉丝是第一接触群体，通过核心用户又能带来一连串的间接目标人群。在网吧的运营方面，粉丝热衷于交流互动，能提供有价值的参考意见。这些都是粉丝的价值。</p>
        </div>
        <div class="item" id="question5">
            <h3>我已经在用其它营销软件了，为什么要用揽客？</h3>
            <p class="text">现有网吧营销软件在用户体验方面做得已经不错了，但是对于离店营销还有一些欠缺。揽客在会员离店营销上具有丰富经验和专业水准，基于微信公众号打造网咖会员闭环，从会员上机到离开门店，揽客给会员带来极致体验。尤其是在离店营销上，揽客通过多种途径连接会员，达到提高会员回头率的目的。</p>
        </div>
        <div class="item" id="question6">
            <h3>怎么样才能使用揽客？</h3>
            <p class="text">揽客是基于微信公众号的营销工具，网吧只要注册微信公众号（服务号）即可使用。在计费服务器端需要安装揽客网吧接口程序，具体信息查看揽客注册流程。</p>
        </div>
        <div class="item" id="question7">
            <h3>公众号对网咖有哪些作用？</h3>
            <p class="text">公众号是网咖与会员连接的桥梁，在跟会员亲密互动上相比传统渠道有明显的优势。现在的公众号就是一个独立的品牌，网咖在线上通过公众号传达品牌价值、发送卡券等行为给网咖带来的效益显而易见。现在的杰拉、网鱼等连锁网咖都有自己的公众号，那么作为小而美的网咖，更应做出特色，做出精彩。</p>
        </div>
        <div class="item" id="question38">
            <h3>营销管理对网吧有何收益？</h3>
            <p class="text">营销管理是揽客系统的核心功能，所有的功能模块都是帮助网咖更简单更有效的进行营销，卡券的精准推送、积分系统的粘性福利、赛事系统的火热以及拉新功能的便利为网咖带来更多收益。</p>
        </div>
        <div class="item" id="question39">
            <h3>在线申请会员对网咖的好处？</h3>
            <p class="text">“移动吧台”可以在异业引流，如量贩式ktv，电影院、大学新生报到现场等，在线申请会员大幅减轻人工压力，获取大量新会员。</p>
        </div>
        <div class="item" id="question40">
            <h3>门店及会员相关设置对网咖的意义？</h3>
            <p class="text">多门店管理满足网咖旗下多家门店的需求，并且不同权限的管理员也能保证网咖管理的便捷性和实用性。会员管理是对会员进行统计和分析，通过数据可视化展现会员的消费情况和相关信息，对其个性化推送达到精准营销的目的。</p>
        </div>
        <div class="item" id="question8">
            <h1><font color="red">*</font>注册问题</h1>
            <h2><font color="red">*</font>网吧微信服务号注册：</h2>
            <p class="text">注册及认证时需要准备的材料有：工商营业执照、法人身份证、公司对公账号或法人银行卡信息（二选一）、未注册过微信公众号的邮箱。</p>
            <h3>如何注册微信服务号？</h3>
            <p class="text">登录微信公众平台官网：<a href="https://mp.weixin.qq.com/" target="_blank">https://mp.weixin.qq.com/</a>，点击右上角立即注册，选择服务号并填写相关信息。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/01.png" alt="" width="100%">
            </div>
            <p class="text">账号类型选择“服务号”进入注册流程</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/02.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question9">
            <h3>注册流程</h3>
            <p class="text">1、基本信息：填写邮箱和账号登录密码，注意：邮箱要使用未注册过微信公众号的邮箱</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/03.png" alt="" width="80%">
            </div>
            <p class="text">2、选择类型：选择默认的中国大陆即可</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/04.png" alt="" width="100%">
            </div>
            <p class="text">选择服务号即可</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/05.png" alt="" width="100%">
            </div>
            <p class="text">3、信息登记：这一步请注意，你需要先确认自己有没有对公账号，如果有对公账号，选择企业或个体商户都可，但如果没有对公账户，请务必选择个体商户。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/06.png" alt="" width="100%">
            </div>
            <p class="text">然后按要求填写企业名称、营业执照注册号等相关信息</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/07.png" alt="" width="100%">
            </div>
            <p class="text">这一步需要选择验证方式这里将显示三种验证方式：法人代表验证、支付验证、微信认证。<font color="red">看个人情况选择其中一种验证即可。</font></p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/08.png" alt="" width="100%">
            </div>
            <p style="text-align: center;color: red">法人代表验证</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/09.png" alt="" width="100%">
            </div>
            <p style="text-align: center;color: red">微信认证</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/10.png" alt="" width="100%">
            </div>
            <p style="text-align: center;color: red">支付认证</p>
            <p class="text">确认提交的信息</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/11.png" alt="" width="100%">
            </div>
            <p class="text">4、公众号信息：填写微信服务号名称以及服务号功能介绍</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/12.png" alt="" width="100%">
            </div>
            <p class="text">一切提交成功后，最晚会在5个工作日内审核完成，在此期间，你可以继续完成公众号的认证过程。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/13.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question10">
            <h3>微信服务号认证</h3>
            <p class="text">刚才的步骤完成后，你会进入到服务号的管理页面，如果刚才你退出了，可以直接打开微信公众号官网：<a href="https://mp.weixin.qq.com/"></a>https://mp.weixin.qq.com/，使用刚才注册的邮箱和密码登录。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/14.png" alt="" width="100%">
            </div>
            <p class="text">登录成功后会弹出微信服务号“未认证”的提示，点击去认证</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/15.png" alt="" width="100%">
            </div>
            <p class="text">点击“开通”</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/16.png" alt="" width="100%">
            </div>
            <p class="text">微信会提示你需要准备的资料，选择：我知道了，开始申请</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/17.png" alt="" width="100%">
            </div>
            <p class="text">同意协议并点击下一步</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/18.png" alt="" width="100%">
            </div>
            <p class="text">正式进入认证，这里请注意：“机构类型”选择请与<font color="red">注册时选择的类型一致</font>。如果你上面注册时
                使用的是企业，这里就必须选择企业法人认证，如果上面选择的是个体商户，这里就必须选择个体工商户认证。</p>
            <p class="text">剩余信息需要下载申请公函、填写、盖章、拍照上传，上传营业执照照片。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/19.png" alt="" width="100%">
            </div>
            <p class="text">如果选择“企业”，请填写对公账号相关信息，如果选择“个体工商户”，可以选择填写法人银行卡等相关信息。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/20.png" alt="" width="100%">
            </div>
            <p style="text-align: center;color: red">选择“企业”界面</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/21.png" alt="" width="100%">
            </div>
            <p style="text-align: center;color: red">选择“个体工商户”界面</p>
            <p class="text">请填写公众号昵称</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/22.png" alt="" width="100%">
            </div>
            <p class="text">填写发票信息，如果不需要开发票，就直接选择：不开具发票即可。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/23.png" alt="" width="100%">
            </div>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/24.png" alt="" width="100%">
            </div>
            <p class="text">最后支付费用，等待微信官方认证。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/weixin/25.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question11">
            <h3>注册完成怎么开通卡券功能？</h3>
            <p class="text">注册成功后，登录微信公众平台，然后在左侧导航栏“添加功能插件”中添加卡券功能。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_07.jpg" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question12">
            <h2><font color="red">*</font>揽客注册</h2>
            <h3>揽客注册流程</h3>
            <p class="text">登录<a href="www.lanke8.cc" target="_blank">www.lanke8.cc</a>。在首页选择注册，填写相应信息。</p>
            <p class="text">注册完成后，会弹出进行授权的提示框。</p>
            <p class="text">点击“去授权”，用注册服务号的微信账户扫码即可。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_08.jpg" alt="" width="60%">
            </div>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_09.jpg" alt="" width="60%">
            </div>
        </div>
        <div class="item" id="question13">
            <h3>授权失败处理办法</h3>
            <p class="text">如提示：<font color="red">很遗憾，授权失败！请不要自定义修改权限，该操作会导致部分功能失效，请取消授权后再次授权！</font></p>
            <p class="text">原因是用户在授权时手动修改了微信服务号提供给揽客的权限列表。</p>
            <p class="text">处理方法为：登录<font color="red">微信公众平台—公众号设置—授权管理—查看平台详情</font>进行取消授权，然后再去揽客官网重新登录授权。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_10.jpg" alt="" width="100%">
            </div>
            <p class="text">如提示：<font color="red">您当前绑定公众号类型为订阅号,无法正常使用功能,请换绑为服务号,如有疑问请联系客服,客服电话:4000965099！</font></p>
            <p class="text">原因是用户授权揽客的公众号类型非服务号。处理方式如上，需先取消授权，再用正确的服务号进行授权。</p>
        </div>
        <div class="item" id="question14">
            <h2><font color="red">*</font>VIP服务/打通计费数据</h2>
            <h3>绑定计费系统</h3>
            <div class="img" style="padding: 20px 0;">
                <img src="<%=basePath%>newStyle/navbar/images/lk_vip_js.jpg" alt="" width="100%">
            </div>
            <p class="text"> <font color="red">如您使用非顺网旗下产品，则无法开通计费系统，但不影响基础版功能使用。</font></p>
            <p class="text">开通计费系统，请先确定已经添加了自己的门店，添加门店请查看“揽客网吧后台使用说明--门店管理”。</p>
            <p class="text">如已经添加门店，请进入账户管理—用户中心，然后在门店列表，选择需要绑定的门店点击下方的“去绑定”按钮。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_11.jpg" alt="" width="100%">
            </div>
            <p class="text">开通流程：</p>
            <p class="text">（1）填写网吧基本信息</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_12.jpg" alt="" width="100%">
            </div>
            <p class="text">（2）揽客审核，一般在一个工作日完成</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_13.jpg" alt="" width="50%" style="border: 1px solid #f3f3f3">
            </div>
            <p class="text">（3）审核通过后从下载页面获取客户端</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_14.jpg" alt="" width="100%">
            </div>
            <p class="text">（4）按教程安装客户端(server2003请联系揽客客服)</p>
            <p class="text"><font color="red">接口程序支持版本：win7、winXP、winServer2007、winServer2003等</font></p>
        </div>
        <div class="item" id="question15">
            <h3>开通在线支付</h3>
            <p class="text">在线支付在用户中心页面进行开通，对私及对公均可支持。</p>
            <p class="text">第一次开通按要求填写即可。需注意，结算账户信息应与提交身份证信息一致。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/35.png" alt="" width="100%">
            </div>
            <p class="text">开通第二家门店可以选择新的账户信息进行填写，也可直接共用上一家开通成功的商户号（即共用同一张银行卡）。</p>
            <p class="text">如果选择新开通需注意：<br>
                1.手机号不能重复<br>
                2.身份证信息不能与上面相同<br>
                3.身份证图片信息需要与银行卡所有人信息一致</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/36.png" alt="" width="100%">
            </div>
            <p class="text">特别：如果网吧为连锁网吧，且账号信息需使用不同的银行卡或身份证信息与银行卡信息不一致等，可走线下授权开通方式（需提交各种材料），详细请咨询揽客客服。</p>
            <p class="text"><font color="red">备注：开通计费系统后，单笔充值收入将收取千分之四。</font></p>
        </div>
        <div class="item" id="question16">
            <h3>开通扫码上机（一码通功能）</h3>
            <p class="text">已在微信公众号中绑定了的网吧会员卡的用户，在相应网吧收银台激活会员卡后，在电脑前用微信扫码上机，不用再输入长长的身份证号码和密码啦！</p>
            <p style="padding-left: 15px;font-weight:900;font-size: 15px">功能介绍</p>
            <p class="text">设置好一码通后，用户可以通过两种方式进行扫码操作<br>
                1.电脑登录界面的二维码<br>
                2.网吧下载下来并做成桌标、立标等方式的二维码<br>
                以上第一种二维码只有扫码上机的功能，第二种扫码进入后可以使用扫码开机功能以及呼叫网管、在线商城、投诉等6个菜单。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_21.gif" alt="" width="100%">
            </div>
            <p style="padding-left: 15px;font-weight:900;font-size: 15px">操作流程</p>
            <p class="text">在门店中心点击去开通，进入开通流程页面，按照提示进行操作。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_18.jpg" alt="" width="100%">
            </div>
            <p style="padding-left: 15px;font-weight:900;font-size: 15px">开通方式</p>
            <p class="text">1.下载导入模板<br>
                2.按照要求在模板中填写电脑编号、电脑名称、电脑IP地址、电脑MAC地址，以上信息需一一对应，如果不开通扫码开机，IP及MAC地址可不填写。
                <br>3.将填写好的模板导入即可。<br>
                4.如果要使用扫码开机，需确认网吧电脑是否在bios中设置过网络唤醒功能。确认方式，可直接进入电脑的bios中查看，或者在收银台对电脑进行远程开机查看。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/39.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question17">
            <h1><font color="red">*</font>功能模块</h1>
            <h2><font color="red">*</font>在线充值</h2>
            <h3>功能介绍</h3>
            <p class="text">网吧自由设置充送规则，会员随时充值，网费自动充入。充值报表清晰简明，对账更加方便快捷。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_22.jpg" alt="" width="100%">
            </div>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_23.jpg" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question18">
            <h3>在线充值有何好处？</h3>
            <p class="text">在线充值避免了收银台高峰期人手不足的尴尬，大幅减轻人工压力。在线上拉新时，进一步降低会员充值门槛，充值更方便。</p>
        </div>
        <div class="item" id="question19">
            <h2><font color="red">*</font>营销管理</h2>
            <h3>卡券功能</h3>
<%--            <p style="font-size: 15px;font-weight: 900;padding-left: 15px">功能介绍</p>--%>
            <p class="text">揽客卡券按照实现方式分为现金券和非现金券<br>
                现金券是网吧核销后即可立马自动将设置的金额以奖励的形式充值到会员的卡余额内。<br>
                非现金券是网吧自定义设置，会员在核销后，网吧线下自行兑现。<br>
                卡券按照功能分类<br>
                1)通用券：网吧根据自己的情况自行设计运营的券。<br>
                2)新手券：用户第一次关注网吧公众号后可或得的卡券<br>
                3)老带新券：网吧进行过拉新设置、新手券设置、老带新设置后，老会员通过微信会员中心的链接拉取的新会员，在核销过新手券后，老会员会得到一张老带新券
                <br>4)男神券、女神券：按照会员身份证信息区分后，可进行核销的券<br>
                5)生日券：会员生日当天可核销的券。<br>
                6)限时秒抢券：设置好后在会员中心首页顶部展示的在特定时间领取的券<br>
                7)申请会员福利券：粉丝通过网吧公众号申请成为会员可获得的卡券。<br>
                8)上网满时长福利券：该券分为三种，单次上网满时长券、一周上网满时长券、一月上网满时长券。单次上网满时长券为会员单次下机满足条件后自动推送，一周，一月满时长券为会员满足条件后自己在会员福利中领取。需注意，每次领取时，会自动判断从点击领取时往前推一周活一月。
                <br>9)会员抵用券：该券为会员够买券后自动生效，网吧可自行设置分几次将金额赠送完毕。
            </p>
            <h3>分发卡券有什么效果？</h3>
            <p class="text">卡券是一种有效的营销手段，根据不同类型的会员，发放不同的优惠券，可以吸引会员到店消费。</p>
        </div>
        <div class="item" id="question20">
            <h3>卡券核销</h3>
            <p class="text">目前卡券拥有两种核销方式<br>
                1.在揽客后台卡券核销页面进行输入卡券码核销。<br>
                2.在微信公众号后台添加核销员，然后使用“卡券商户助手”微信公众号进行扫码或者输入核销。<br>
                添加核销员的方式：</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/52.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question21">
            <h3>活动群发</h3>
            <p class="text">1)、揽客为您分析Pubwin会员消费行为，智能生成会员标签<br>
                2)、网吧（故障、维护）事件一键通知，及时送达，为您的会员贴心服务<br>
                3)、营销活动自由编辑，分组群发，公众号精准推送，不再发愁</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/53.png" alt="" width="100%">
            </div>
            <p class="text">活动群发可对会员精准投放，可以根据会员的消费区间、卡余额区间、活跃时长区间等进行群发，并且可以按照会员卡，姓名等指定会员发送。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/54.png" alt="" width="100%">
            </div>
            <p style="padding-left: 15px;font-weight: 900;font-size: 15px">群发方式</p>
            <p class="text">群发方式分为两种，一种是群发通知，这种方式将直接以微信模板的方式展示在微信公众号内。另一种是群发活动，群发后会展示在微信的会员生活页面。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/55.png" alt="" width="50%">
            </div>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/56.png" alt="" width="50%">
            </div>
        </div>
        <div class="item" id="question22">
            <h3>积分抽奖</h3>
            <p class="text">设置积分抽奖需分别将积分奖励设置、积分消耗设置、抽奖设置三项设置完成。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/101.jpg" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question23">
            <h3>赛事系统</h3>
            <p class="text">当前的赛事系统完整包含未发布、报名中、报名结束、比赛中、比赛结束的所有阶段，可以让网吧有效的管理赛事的各个阶段。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/57.png" alt="" width="100%">
            </div>
            <p class="text">新增赛事时网吧可以选择举办的门店以及赛事报名类型（单人、多人组队），符合绝大多数的赛事报名条件。<br>
                新增的赛事报名可用图文的方式呈现在会员的赛事报名页面，让网吧尽可能的对赛事进行宣传和介绍。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/58.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question24">
            <h3>拉新功能</h3>
            <p class="text">会员拉新可有效的提高网吧对会员的管理和粘合程度</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/100.jpg" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question25">
            <h3>在线办理会员</h3>
            <p class="text">打通了计费系统的网吧，可以开通用户在线申请会员。用户不需要到网吧就可在线办理，并针对在线申请的会员给予额外赠送，达到网吧大量吸引新会员的目的。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_20.jpg" alt="" width="100%">
            </div>
            <p class="text">开通成功后，粉丝可以从公众号会员中心首页进行办理会员。</p>
        </div>
        <div class="item" id="question26">
            <h2><font color="red">*</font>门店及会员管理</h2>
            <h3>门店管理</h3>
            <p class="text">揽客所有功能均与门店有关，注册账号后，请第一时间将自己的门店添加在揽客管理后台中。揽客后台可以添加多个门店，以及设置多个不同权限的管理员，满足网咖的多样化需求。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/61.png" alt="" width="100%">
            </div>
            <p class="text">网吧可以在此页面设置会员的标签值、请求桌面二维码、查看计费接口连接情况、禁用（启用）门店等功能。</p>
        </div>
        <div class="item" id="question27">
            <h3>角色管理、人员管理</h3>
            <p class="text">这两项功能可以有效的帮助网吧老板为不同的员工分配不同的账号权限，总账号的管理者可以在角色管理页面为不同的角色分配权限，然后在人员管理页面创建角色下的人员账号。这样就避免了总账号下的其他页面暴露在所有账号下。。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/62.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question28">
            <h3>会员管理</h3>
            <p class="text">在会员列表页，微信端绑定过门店的微信用户会根据卡余额、活跃度、消费能力显示出不同的标签，以及可以查看某用户半年内所有上网和消费记录的会员详情页。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/63.png" alt="" width="100%">
            </div>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/64.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question29">
            <h2><font color="red">*</font>公众号管理</h2>
            <h3>图文发布</h3>
            <p class="text">图文发布功能可以让网吧自由编辑和群发资讯、活动图文；“揽客文章库”里有大量的活动推文模板，网吧只需改网吧相关内容，就变成一篇属于您网吧的图文消息；</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_28.jpg" alt="" width="100%">
            </div>
            <p class="text"><font color="red">备注：网吧公众号属于服务号，每个月只有四次群发机会</font></p>
        </div>
        <div class="item" id="question30">
            <h3>自定义菜单</h3>
            <p class="text">揽客将微信的自定义菜单完整的移植过来，除默认菜单外，用户可以新增和编辑其他菜单，并且揽客将所有揽客功能的菜单链接放到了该页面，以方便网吧自定义菜单内容。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/66.png" alt="" width="100%">
            </div>
            <p class="text"><font color="red">备注：如果短时间内多次发布菜单，需等待10分钟方可生效，如果需快速查看，可取消公众号关注，再重新关注即可。</font></p>
        </div>
        <div class="item" id="question31">
            <h3>自动回复</h3>
            <p class="text">自动回复功能可以根据关键词、收到消息、被关注分别设置不同的回复内容。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/67.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question32">
            <h2><font color="red">*</font>店内服务</h2>
            <p class="text">店内服务当前支持一键呼叫网管、一键投诉、商品发货通知。网吧可以设置对应的接收员，接收员的微信可以收到上面的所有通知。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/68.png" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question33">
            <h1><font color="red">*</font>更多问题</h1>
            <h3>网维产品说明</h3>
            <p class="text">目前揽客仅支持使用顺网网维产品的网咖，顺网网维产品为以下四个，网维大师、迅闪vip、信佑、顺网雲。</p>
        </div>
        <div class="item" id="question34">
            <h3>PUBWIN版本不支持怎么办</h3>
            <p class="text">目前揽客支持使用的pubwinol版本为<font color="red">6.3.30.1</font>及以上。如果版本低于要求版本，我司可以免费快速为网吧升级pubwinol版本。</p>
            <p class="text">如不了解自己的pubwinol版本，可以通过pubwinol系统的帮助菜单中的关于查看版本信息。</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_30.jpg" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question35">
            <h3>代理商使用说明</h3>
            <p class="text">揽客支持代理商营销模式，网吧及其他公司可以向揽客申请。</p>
            <p class="text">联系人：张经理--15867184793<font color="red">/</font>15397042382</p>
            <div class="img">
                <img src="<%=basePath%>newStyle/navbar/images/img_31.jpg" alt="" width="100%">
            </div>
        </div>
        <div class="item" id="question36">
            <h3>如何使用卡余额购买自助机商品？</h3>
            <p class="text">网吧首先需要购置与揽客相配套的自动售货机，与揽客后台进行配置，配置完成设置商品，才可使会员进行购买。用户通过微信扫码完成商品选择后，使用卡余额支付，当用户满足网吧会员和余额充足的情况下才能购买成功，如果不满足就会引导用户开通会员和充值。</p>
        </div>
        <div class="item" id="question37">
            <h3>使用卡余额有什么好处？</h3>
            <p class="text">通过卡余额支付可促进会员消耗卡内余额，促使会员积极向卡内充值。增加卡内余额消耗方式，卡余额支付的商品可比现金支付价格更高，让网吧营销活动赠出的奖励合理消耗，也可减少销售商品人员的工资成本，网咖运营成本。</p>
        </div>
    </div>
</div>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<script src='<%=basePath%>newStyle/navbar/js/stopExecutionOnTimeout.js?t=1'></script>
<script src='<%=basePath%>newStyle/navbar/js/jquery.min.js'></script>
<script src='<%=basePath%>newStyle/navbar/js/jquery.velocity.min.js'></script>
<script type="text/javascript">
    //读取站内访问量，动态加载百度的JS
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?99747536a31c41ed484b4f8c19896d9c";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
    $(window).scroll(function (){
        if ($(window).scrollTop() >= 90) {
            $("#leftList").css({position: "fixed",top:"0",height:"100vh"});
        }else{
            $("#leftList").css({position: "fixed",top:"90px",height:"calc(100vh - 90px)"});
        }
        if ($(window).scrollTop() >= 1000) {
            $(".blackTop").css({display: "block"});
        }else {
            $(".blackTop").css({display: "none"});
        }
    })
    function scrolled(id){
        $('html, body').animate({
            scrollTop : $("#"+id).offset().top
        }, 750);
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