<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/card.css" media="all">
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <title>卡券设置</title>
    <style>
        .layui-btn-sm {height: 30px;line-height: 30px;padding: 0 10px;font-size: 12px;cursor: pointer;}
        .upFlie { position: absolute;top: 42px;left: 21px;width: 90px;opacity: 0;cursor: pointer;}
        .card-header {width: 100%;height: 48px;background-color: #19232d;}
        .card-header-msg {padding: 0 10px 0 30px}
        .card-header-msg .card-lankeIcon {position: relative;top: -6px;width: 80px;}
        .card-header-msg .card-header-title {color: #fff;font-size: 20px;line-height: 48px;padding-left: 10px;}
        .card-header-right {float: right;height: 48px;padding: 0 14px;cursor: pointer;}
        .card-header-right img {margin: -24px 8px 0 0;border-radius: 100%;max-width: 32px;}
        .card-header-right span {color: #fff;}
        .card-userName {display: inline-block;height: 40px;position: relative;top: 5px;}
        /*.layui-checkbox-disbaled span {background-color: #5FB878!important;}*/
        /*.layui-checkbox-disbaled {border-color: #5FB878!important;}*/
        .apply_title {padding-bottom: 12px;}
        .apply_img {width: 350px;height: 188px;background: #eee url('<%=basePath%>newStyle/images/internet_banner.png')no-repeat center center;background-size: 100%;
            border-radius: 10px;margin-bottom: 12px;}
        .apply_img h1 {color: #fefbad;font-size: 26px;border:none;padding: 0px 56px;padding-top: 36px;line-height: 35px;
            font-weight: 600;letter-spacing: 1px;word-break:break-all;}
        .hidden{
            width: 10rem;
            height:2rem;
            border:0;

        }
        .shezhi{
            background: #ffffff;
            margin-left: 0rem!important;
            margin-bottom:.3rem;

        }
        #start{
            padding-left:2.8rem;
        }
        #end{
            padding-left:1rem;
            padding-right:2.4rem;
        }
        .flex111{
            display:flex;
            justify-content: flex-start;
        }

        .jia{
            width:1.7rem;
            height:1.7rem;
            margin-left:.7rem;
            padding-top:.28rem;

        }
        .jia img{
            width:1.7rem;
            hieght:1.7rem;
            display: block;
        }
        .TimeD{
            font-size:.8rem;
            width: 7rem;
            padding-right:0;
        }
        .fath{
            position:relative;
        }
        .iconF{
            position:absolute;
            right: -2.48rem;
            transform: translateY(-2.4rem);
            font-size:1.8rem;
            top:2.4rem;
        }

    </style>
</head>
<body class="scroll">

<%--顶部栏--%>
<div class="card-header">
    <div class="card-header-msg">
        <img src="<%=basePath%>newStyle/images/logo2.png" class="card-lankeIcon"/><span class="card-header-title">网吧管理后台</span>
        <div class="card-header-right">
            <img alt="" src="<%=basePath %>static/ace/avatars/user.jpg" class="">
            <span class="card-userName layui-elip">
                <small>Welcome</small><br>
                ${pd.NAME}
            </span>
        </div>
    </div>
</div>

<%--内容--%>


<div class="card-width">
    <h1>优惠券设置</h1>
    <div class="card-content">
        <div class="card-phone" id="card-phone">
            <div class="card-phone-content">
                <div class="card-body">
                    <div class="card-eara">
                        <div class="card-shop">
                            <div class="card-logo-eara">
                                    <span class="card-logo-box">
                                        <img id="phoneLogo" src="" alt="" width="100%" >
                                    </span>
                                <p id="card-logo-title">lanker</p>
                                <p class="card-shop-title" id="card-shop-title"></p><a class="card-shop-cash"></a>
                                <span class="card-btn">立即使用</span>
                            </div>
                        </div>
                    </div>
                    <div class="card-list-item">
                        <p class="card-list-title">赠送给朋友<i class="layui-icon icon-right">&#xe602;</i></p>
                        <p class="card-list-title">兑换券详情<i class="layui-icon icon-right">&#xe602;</i></p>
                        <p class="card-list-title" style="border: none">公众号<i class="layui-icon icon-right">&#xe602;</i>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-msg" id="card-msg">
            <form class="layui-form" id="Form" lay-filter="Form">
                <input type="hidden" id="CARD_ID" name="CARD_ID" value="${pd.CARD_ID}">
                <input type="hidden" id="FAV_TYPE" name="FAV_TYPE" value="${pd.FAV_TYPE}">
                <input type="hidden" id="SCENE_ID" name="SCENE_ID" value="${pd.SCENE_ID}">
                <h2>基本信息</h2>
                <div class="msg-border">
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="padding-top: 0">商户名称：</label>
                        <div class="layui-input-block">
                            <img id="cardLogo" src="" alt="" width="80px" height="80px">
                            <span name="title" class="wx-title" id="base_title"><br></span>
                            <input id="LOGO_URL" name="LOGO_URL" type="hidden">
                            <input id="BRAND_NAME" name="BRAND_NAME" type="hidden">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">选择门店：</label>
                        <div class="layui-input-block" id="storeList">

                        </div>
                        <input type="hidden" id="STORE_LIST" name="STORE_LIST">
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">卡券场景：</label>
                        <div class="layui-input-block">
                            <input type="hidden" id="SCENE_NAME" name="SCENE_NAME">
                            <input type="text" autocomplete="off" id="show_scene"
                                   class="layui-input"disabled>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">卡券类型：</label>
                        <div class="layui-input-block" id="card">
                            <input type="checkbox" lay-filter="CARD_TICKET" name="CARD_TICKET" value="2"
                                   title="现金券" id = "CARD_TICKET">
                        </div>
                    </div>
                    <div class="layui-form-item" id="tip" style="display: none">
                        <label class="layui-form-label" style="width:100px">现金金额：</label>
                        <div class="layui-input-block">
                            <input type="number" name="CASH_NUMBER" id="CASH_NUMBER" min="0.00" class="layui-input" lay-verify="money"
                                   onkeyup="onumber(this)"/><p style="margin-top: 10px;color:#666">现金券核销时将直接充入会员卡奖励</p>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">卡券名称：</label>
                        <div class="layui-input-block">
                            <input type="text" id="SUB_TITLE" name="SUB_TITLE" lay-verify="required" autocomplete="off"
                                   class="layui-input text-field card-title" maxlength="9"
                                   placeholder="请输入卡券名" >
                            <span  class="text-left-tips tips-num">0</span>/9
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">卡券颜色：</label>
                        <div class="layui-input-block" style="width: 50%;max-width: 120px;">
                            <div class="layui-collapse" lay-filter="color">
                                <div class="layui-colla-item">
                                    <p class="layui-colla-title layui-input" style="border: none">
                                        <span class="card-color colla-color"></span>
                                    </p>
                                    <div class="layui-colla-content" style="padding: 10px 13px;background-color: #fff">
                                        <span class="card-color color1" name="#63b359" id="Color010"></span>
                                        <span class="card-color color2" name="#2c9f67" id="Color020"></span>
                                        <span class="card-color color3" name="#509fc9" id="Color030"></span>
                                        <span class="card-color color4" name="#5885cf" id="Color040"></span>
                                        <span class="card-color color5" name="#9062c0" id="Color050"></span>
                                        <span class="card-color color6" name="#d09a45" id="Color060"></span>
                                        <span class="card-color color7" name="#e4b138" id="Color070"></span>
                                        <span class="card-color color8" name="#ee903c" id="Color080"></span>
                                        <span class="card-color color9" name="#f08500" id="Color081"></span>
                                        <span class="card-color color10" name="#a9d92d" id="Color082"></span>
                                        <span class="card-color color11" name="#dd6549" id="Color090"></span>
                                        <span class="card-color color12" name="#cc463d" id="Color100"></span>
                                    </div>
                                    <input id="COLOR" name="COLOR" type="hidden" >
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">卡券库存：</label>
                            <div class="layui-input-block">
                                <input type="number" name="QUANTITY" id="QUANTITY" onkeyup="this.value=this.value.replace(/\D/g,'')"
                                       onafterpaste="this.value=this.value.replace(/\D/g,'')" lay-verify="required|quantity"
                                       min="0" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline" id="has_lq" style="display: none;">
                            <label class="layui-form-label">已领取数量： </label>
                            <div class="layui-input-block">
                                <input type="number" name="cardSum" id="cardSum" onkeyup="this.value=this.value.replace(/\D/g,'')"
                                       onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                       min="1" autocomplete="off" class="layui-input" disabled>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item" style="display: none;" id="grab_div">
                        <label class="layui-form-label">卡券秒抢时间：</label>
                        <div class="layui-inline">
                            <label class="layui-form-label">开始时间：</label>
                            <div class="layui-input-block">
                                <input type="text" name="garbStart_time" id="garbStart_time" autocomplete="off"
                                       class="layui-input" lay-verify="grabRequired">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label" style="width:70px">结束时间：</label>
                            <div class="layui-input-block" style="margin-left: 0;">
                                <input type="text" name="garbEnd_time" id="garbEnd_time" autocomplete="off"
                                       class="layui-input" lay-verify="grabRequired">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item" id="term_div1" style="display: none">
                        <input type="hidden" id="term_div111" value="0">
                        <label class="layui-form-label" style="width:100px">时长计算方式：</label>
                        <div class="layui-input-block">
                            <input type="radio" id="cardSumType1" name="cardSum_type" value="1" title="连续上网满时长" lay-filter="cardRadio">
                            <input type="radio" id="cardSumType2" name="cardSum_type" value="2" title="最近一周上网满时长" lay-filter="cardRadio">
                            <input type="radio" id="cardSumType3" name="cardSum_type" value="3" title="最近一月上网满时长" lay-filter="cardRadio">
                        </div>
                    </div>

                    <div class="layui-form-item" id="term_div2" style="display: none">
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label"></label>
                            <div class="layui-input-block" style="width: 80%;">
                                连续上网满<input class="layui-input" style="width: 10%; display: inline-block;margin: 0 10px"
                                            id="cardSum_time" name="cardSum_time" lay-verify="termRequired" onkeyup="checkParam()">
                                <font id="text1">小时,送券。(下机满足条件的会员将自动推送该卡券)</font><font id="text2" style="display: none">小时,才能领取。</font>
                            </div>
                        </div>
                        <div class="layui-form-item Jia">
                            <label class="layui-form-label TimeD">不可用时间段:</label>
                            <div class="flex111">
                                <div class="leftData">
                                    <div class="layui-input-block shezhi">
                                        <input type="text" name="unusetime" class="hidden unusetime" readonly style="text-align:center" lay-filter="noTime">
                                    </div>
                                </div>

                                <div class="jia"><img src="<%=basePath%>newStyle/images/jia.png"></div>
                            </div>
                        </div>

                    </div>



                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width:100px">有效期类型：</label>
                        <div class="layui-input-block">
                            <input id="type_range" name="TYPE" lay-filter="date_type" type="radio" value="DATE_TYPE_FIX_TIME_RANGE"
                                   title="固定日期区间" lay-filter="radio">
                            <input id="type_term" name="TYPE" lay-filter="date_type" type="radio" value="DATE_TYPE_FIX_TERM"
                                   title="领取后按天算（该类型不支持修改）" lay-filter="radio">
                        </div>
                    </div>

                    <div class="layui-form-item" id="date1box" style="display: none">
                        <div class="layui-inline">
                            <label class="layui-form-label">启用时间：</label>
                            <div class="layui-input-block">
                                <input type="text" name="BEGIN_TIMESTAMP" id="BEGIN_TIMESTAMP" autocomplete="off"
                                       class="layui-input" lay-verify="timeNotNull">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label" style="width:70px">结束时间：</label>
                            <div class="layui-input-block" style="margin-left: 0;">
                                <input type="text" name="END_TIMESTAMP" id="END_TIMESTAMP" autocomplete="off"
                                       class="layui-input" lay-verify="timeNotNull">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item" id="date2box" style="display: none">
                        <div class="layui-inline">
                            <label class="layui-form-label" style="padding: 0 15px;">自领取后几天内有效：</label>
                            <div class="layui-input-block">
                                <input type="number" name="FIXED_TERM" id="FIXED_TERM" min="1" lay-verify="moreZero2"
                                       onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                       autocomplete="off" class="layui-input fixedNum">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label" style="padding: 0 15px;">自领取后几天后生效：</label>
                            <div class="layui-input-block">
                                <input type="number" name="FIXED_BEGIN_TERM" id="FIXED_BEGIN_TERM" value="0"
                                       autocomplete="off" class="layui-input text-field" readonly="readonly"
                                       style="min-width: 180px;width: 45%" title="系统默认，暂不可修改" onclick="">
                                <span class="text-left-tips" style="color: #999;">0表示当天生效</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="rush_div" style="display: none;">
                    <h2>购买规则(金额单位：元)</h2>
                    <div class="msg-border">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="">购买金额：</label>
                                <div class="layui-input-block" style="width:80px">
                                    <input type="number" name="recharge" id="recharge" min="1" onkeyup="this.value=this.value.replace(/\D/g,'')"
                                           onafterpaste="this.value=this.value.replace(/\D/g,'')" lay-verify="rush_moreZero"
                                           autocomplete="off" class="layui-input fixedNum" onblur="judeCard()">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width:auto;margin-left: 20px;">实际获得金额：</label>
                                <div class="layui-input-block" style="width:80px;margin-left:80px">
                                    <input type="number" name="handsel" id="handsel" min="1" onkeyup="this.value=this.value.replace(/\D/g,'')"
                                           onafterpaste="this.value=this.value.replace(/\D/g,'')" lay-verify="rush_moreZero"
                                           autocomplete="off" class="layui-input fixedNum" onblur="judeCard()">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width:auto;margin-left: 20px;">几次送完：</label>
                                <div class="layui-input-block" style="width:80px;margin-left:80px">
                                    <input type="number" name="handsel_sum" id="handsel_sum" min="1" onkeyup="this.value=this.value.replace(/\D/g,'')"
                                           onafterpaste="this.value=this.value.replace(/\D/g,'')" lay-verify="rush_moreZero"
                                           autocomplete="off" class="layui-input fixedNum" onblur="judeCard()" >
                                </div>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label" style="width:100px">赠送方式：</label>
                            <div class="layui-input-block">
                                <input type="radio" name="handsel_type" id="handsel_type1" value="0" title="等额送"
                                       lay-filter="handsel_type">
                                <input type="radio" name="handsel_type" id="handsel_type2" value="1" title="自定义"
                                       lay-filter="handsel_type" >
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-inline" id="CASH_NUMBER_LIST">

                            </div>
                        </div>


                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">赠送间隔：</label>
                                <div class="layui-input-block">
                                    <input type="number" name="handsel_time" id="handsel_time" min="1" max="1000" onkeyup="this.value=this.value.replace(/\D/g,'')"
                                           onafterpaste="this.value=this.value.replace(/\D/g,'')" lay-verify="rush_moreZero"
                                           autocomplete="off" class="layui-input ">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <div class="layui-input-block" style="margin-left: 10px">
                                    <select name="handsel_times" id="handsel_times" lay-verify="rushNotNull">
                                        <option value=""></option>
                                        <option value="0">天</option>
                                        <option value="1">周</option>
                                        <option value="2">月</option>
                                        <option value="3">年</option>
                                    </select>
                                </div>
                            </div>
                            <p style="color: #666;margin-top: 10px;padding: 0 132px;">例如：一天推送一张指定金额现金券</p>
                        </div>
                    </div>
                </div>

                <h2 style="margin-top: 30px">优惠详情</h2>

                <div class="msg-border">
                    <div class="layui-form-item" id="receive_div">
                        <div class="layui-inline">
                            <label class="layui-form-label" style="width:100px">单人领取数量：</label>
                            <div class="layui-input-block">
                                <input type="number" name="RECEIVE_NUMBER" id="RECEIVE_NUMBER" min="1" onkeyup="this.value=this.value.replace(/\D/g,'')"
                                       onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                       lay-verify="required|moreZero" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item" id="revceive_blank_div">
                        <div class="layui-inline">
                            <label class="layui-form-label">领取间隔：</label>
                            <div class="layui-input-block">
                                <input type="number" name="BLANK_NUMBER" id="BLANK_NUMBER" min="1"
                                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                                       onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                       lay-verify="required|moreZero" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <div class="layui-input-block" style="margin-left: 10px">
                                <select name="RECEIVE_DETIL" lay-filter="aihao" id="RECEIVE_DETIL" lay-verify="required">
                                    <option value=""></option>
                                </select>
                            </div>
                        </div>
                        <p style="color: #666;margin-top: 10px;padding: 0 132px;">例如：单人一天限领1张，（领取数量和领取间隔结合使用）</p>
                    </div >


                    <%--抵用券购买限制--%>
                    <div class="layui-form-item" id="rush_buy_div" style="display:none;">
                        <div class="layui-inline">
                            <label class="layui-form-label" style="width:100px">单人购买数量：</label>
                            <div class="layui-input-block">
                                <input type="number" name="RUSH_BUY_NUMBER" id="RUSH_BUY_NUMBER" min="1"
                                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                                       onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                       lay-verify="rush_moreZero" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item" id="rush_time_div" style="display:none;">
                        <div class="layui-inline">
                            <label class="layui-form-label">购买间隔：</label>
                            <div class="layui-input-block">
                                <input type="number" name="RUSH_BUY_TIME" id="RUSH_BUY_TIME" min="1"
                                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                                       onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                       lay-verify="rush_moreZero" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <div class="layui-input-block" style="margin-left: 10px">
                                <select name="RUSH_BUY_TIME_UNITS" id="RUSH_BUY_TIME_UNITS" lay-verify="rushNotNull">
                                    <option value=""></option>
                                </select>
                            </div>
                        </div>
                        <p style="color: #666;margin-top: 10px;padding: 0 132px;">例如：单人一天限领1张，（领取数量和领取间隔结合使用）</p>
                    </div >


                    <!-- 申请会员福利券,新增领取限制 -->
                    <div class="layui-form-item layui-form-text" style="display: none;" id="apply_div">
                        <label class="layui-form-label">领取限制：</label>
                        <div class="layui-input-block" style="width: 80%;">
                            <input placeholder="领取限制" class="layui-input" style="width: 80%; display: inline-block" disabled="disabled"
                                   value="用户在线申请会员成功后自动推送,每个用户只推一次">
                        </div>
                    </div>


                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">优惠说明：</label>
                        <div class="layui-input-block" style="width: 80%;">
                                <textarea placeholder="请输入优惠说明" lay-verify="required" class="layui-textarea" style="width: 80%;display: inline-block"
                                          name="DESCRIPTION" id="DESCRIPTION" maxlength="1024"></textarea>
                            <span class="text-left-tips tips-textarea1">0</span>/1024
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">使用提醒：</label>
                        <div class="layui-input-block" style="width: 80%;">
                            <input placeholder="请输入使用提醒" class="layui-input" style="width: 80%;display: inline-block"
                                   name="NOTICE" id="NOTICE" maxlength="16" lay-verify="required">
                            <span class="text-left-tips tips-textarea2">0</span>/16
                        </div>
                    </div>

                </div>
                <div id="term_banner" style="display:none;">
                    <h2 style="margin-top: 30px">活动广告展示</h2>
                    <div class="msg-border">
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label" style="width: 100px;">编辑广告词：</label>
                            <div class="layui-input-block" style="width: 80%;">
                                <input placeholder="示例:连续上网满4小时送10元网费券一张" class="layui-input"
                                       style="width: 80%;display: inline-block" onkeyup="bannerText(this)"  maxlength="18" id="card_ad" name="card_ad">
                                <span class="text-left-tips tips-textarea2" id="numtext">0</span>/18
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="apply_banner" style="padding: 8px 14px;">
                                <p class="apply_title layui-form-item">首页广告展示：</p>
                                <div class="layui-form-item">
                                    <div class="apply_img">
                                        <h1 id="bannerText"></h1>
                                    </div>
                                    <p class="lk-textTip" style="padding-left: 0;">会员端活动广告示例：暂不支持换底图</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item" style="margin: 30px;">
                    <span class="layui-btn layui-btn-normal" lay-submit lay-filter="saveCard" id="saveCard">保存</span>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>
    var basePath = '<%=basePath%>';
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/internet/' //静态资源所在路径
    }).extend({
        card_edit : 'card/card_edit',
    }).use(['card_edit']);


    layui.use(['form', 'layedit', 'laydate', "jquery"], function() {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery,
            laydate = layui.laydate;

        $('.jia').on('click',function(){
            var div=document.querySelectorAll(".shezhi");
            if(div.length<3) {
                $(".leftData").append('<div class="layui-input-block shezhi"><div class="fath">' +
                    '<input type="text" id="unusetime'+div.length+'" name="unusetime" class="hidden unusetime" readonly style="text-align:center">' +
                    '</div> <i class="layui-icon layui-icon-delete iconF"></i></div>');
                laydate.render({
                    elem: '#unusetime'+div.length,
                    type: 'time',
                    range: true,
                    format: 'HH:mm', //显示格式：小时：分钟
                });
            }else{
                layer.msg("最多设置3个时间段")
            }
        });
        $("body").on("click",".iconF",function(){
            $(this).parent().remove();
        })

        function initTime() {
            //日期
            laydate.render({
                elem: '.unusetime',
                type: 'time',
                range: true,
                format: 'HH:mm', //显示格式：小时：分钟
            });
        }
    });





</script>


</body>
</html>
