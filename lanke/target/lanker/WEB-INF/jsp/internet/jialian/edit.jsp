<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <title>支付开通流程</title>
    <style>
        body {
            background-color: #f6f8f9;
            min-width: 600px;
            overflow: auto
        }
        .layui-form-pane .layui-form-label {padding: 8px 10px;}
        .layui-form-item .layui-input-inline {width:218px;}
        .layui-form-item {margin-bottom: 18px;}
    </style>
</head>
<body>
<input type="hidden" value="${pd.id}" id="module_id">
<input type="hidden" value="${pd.store_id}" id="store_id">
<input type="hidden" value="${pd.user_type}" id="user_type">
<input type="hidden" value="${pd.shop_no_length}" id="shop_no_length">
<div class="card-header">
    <div class="card-header-msg">
        <img src="<%=basePath%>newStyle/images/logo2.png"class="card-lankeIcon">
        <span class="card-header-title">网吧管理后台</span>
        <div class="card-header-right">
            <img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
            <span class="card-userName layui-elip" > <small>Welcome</small><br>
                ${pd.name}
			</span>
        </div>
    </div>
</div>
<div class="card-width" style="min-width: 950px;">
    <h1>
        当前开通计费系统<span style="font-weight: 300;">—</span><span id="store_name">${pd.store_name}</span>
    </h1>
    <div class="lanke-pbMsg" style="padding-bottom:100px;">
        <!-- 流程图 -->
        <div class="lanke-pbMsgSide">
            <fieldset class="layui-elem-field layui-field-title" style="">
                <legend>在线支付开通流程</legend>
            </fieldset>
            <div class="lk-pay-flow layui-clear">
                <div class="flow-box">
                    <div class="lk-payFlow-icon">
                        <i class="iconfont">&#xe623;</i>
                    </div>
                    <p class="text-center">填写网吧资料</p>
                </div>
                <div class="flow-box">
                    <div class="lk-payFlow-icon">
                        <i class="iconfont">&#xe624;</i>
                    </div>
                    <p class="text-center">网吧资料审核</p>
                </div>
                <div class="flow-box">
                    <div class="lk-payFlow-icon">
                        <i class="iconfont">&#xe627;</i>
                    </div>
                    <p class="text-center">审核通过</br>在线支付开通完成</p>
                </div>
            </div>
        </div>

        <!-- 日志 -->
        <div id="audit_box">

        </div>
        <form class="layui-form layui-form-pane" action="" lay-filter="Form_x" id="Form_x">
            <div class="lanke-pbMsgSide" style="margin-top:20px;background: none;padding-bottom: 0">
                <div class="layui-form-item">
                    <label class="layui-form-label">共用商户号：</label>
                    <div class="layui-input-block">
                        <input type="radio" name="sex" value="0" title="不要" lay-filter="together">
                        <input type="radio" name="sex" value="1" title="要" checked lay-filter="together">
                    </div>
                </div>
                <div id="save_ok_box">
                    <div class="layui-form-item">
                        <label class="layui-form-label">商户号：</label>
                        <div class="layui-input-block" id="save_ok_shop_no">
                            <input type="text" name="shop_no" class="layui-input" placeholder="请输入已有的商户号">
                        </div>
                    </div>
                    <input type="hidden" lay-submit  lay-filter="submit_ok" value="" id="save_ok">
                    <div class="layui-form-item">
                        <span class="layui-btn save" data-type="ok">确定</span>
                    </div>
                </div>
            </div>
        </form>
        <!-- 填写信息 -->
        <form class="layui-form layui-form-pane" action="" lay-filter="Form" id="Form">
            <div class="lanke-pbMsgSide" style="background: none">
                <fieldset class="layui-elem-field layui-field-title" style="">
                    <legend>进件商户信息</legend>
                </fieldset>
                <div class="layui-form-item">
                    <label class="layui-form-label">商户名称：</label>
                    <div class="layui-input-block">
                        <input type="text" name="merch_name" lay-verify="must|lk_zhongwen" class="layui-input" placeholder=" 请输入您的网吧名称"  value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">商户联系人：</label>
                    <div class="layui-input-block">
                        <input type="text" name="shop_contact_name" lay-verify="must|lk_zhongwen" placeholder=" 请输入商户联系人姓名" class="layui-input" value=""/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系人身份证：</label>
                    <div class="layui-input-block">
                        <input type="text" name="cert_no" lay-verify="must|lk_identity"   placeholder=" 请输入商户联系人身份证号码" class="layui-input" value=""/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label" title="身份证有效起始日期">身份证有效起始日期：</label>
                        <div class="layui-input-inline">
                            <input type="text" name="cert_start_date" lay-verify="must" id="cert_start_date" value="" class="layui-input" readonly placeholder="有效起始日期">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label" title="身份证有效截止日期">身份证有效截止日期：</label>
                        <div class="layui-input-inline">
                            <input type="text" name="cert_expire_date" lay-verify="must" value="" class="layui-input" id="cert_expire_date" readonly placeholder="有效截止日期">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">手机号码：</label>
                        <div class="layui-input-inline">
                            <input type="text" name="mobile"  id="phone" class="layui-input" lay-verify="must|lk_phone" placeholder="请输入接收审核结果的手机号"  value=""/>
                        </div>
                    </div>
                    <div class="layui-inline" id="code_box">
                        <label class="layui-form-label">验证码：</label>
                        <div class="layui-input-inline">
                            <input type="text" name="code" id="code" lay-verify="must" value="" class="layui-input" placeholder="手机验证码" >
                        </div>
                        <span class="layui-btn layui-btn-primary user-btn-yzm" id="codeBtn" onclick="sendCode()">发送验证码</span>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">联系人邮箱：</label>
                    <div class="layui-input-block">
                        <input type="text" name="email" lay-verify="must|lk_email"  placeholder=" 请输入商户联系人邮箱" class="layui-input" value=""/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" title="省市区行政编码">所在省市区：</label>
                    <div class="layui-input-inline layui-form" lay-filter="provinceBox">
                        <select name="prov_code" id="province_selector" lay-filter="province" lay-verify="must">

                        </select>
                    </div>
                    <div class="layui-input-inline layui-form" lay-filter="cityBox">
                        <select name="city_code" id="city_selector" lay-filter="city" lay-verify="must">

                        </select>
                    </div>
                    <div class="layui-input-inline layui-form" lay-filter="districtBox">
                        <select name="area_code" id="district_selector" lay-filter="district" lay-verify="must">

                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">详细地址：</label>
                    <div class="layui-input-block">
                        <input type="text" name="det_address" lay-verify="must"  placeholder=" 请输入商户详细地址" class="layui-input" value=""/>
                    </div>
                </div>
            </div>
            <div class="lanke-pbMsgSide" style="background: none">
                <fieldset class="layui-elem-field layui-field-title" style="">
                    <legend>结算账户信息</legend>
                </fieldset>
                <div class="layui-form-item" style="color: #666">
                    注：每笔费率为千分之四
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">结算账户类型</label>
                    <div class="layui-input-block">
                        <input type="radio" name="account_type" value="0" title="对私" lay-filter="account_type">
                        <input type="radio" name="account_type" value="1" title="对公" lay-filter="account_type">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">结算账号：</label>
                    <div class="layui-input-block">
                        <input type="number" name="account_no" lay-verify="must" placeholder="请输入结算银行卡账号" class="layui-input" value=""/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label" title="">结算账号户名：</label>
                        <div class="layui-input-inline">
                            <input type="text"   name="account_name" lay-verify="must|lk_zhongwen" class="layui-input" value="" >
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label" title="" style="width: 165px;">开户行（具体支行）：</label>
                        <div class="layui-input-inline">
                            <input type="text"  name="union_bank_name" lay-verify="must|lk_zhongwen" class="layui-input" value="">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item" style="display: none" id="union_bank_box">
                    <label class="layui-form-label">联行号：</label>
                    <div class="layui-input-block">
                        <input type="text" name="union_bank_no" lay-verify=""  placeholder="请输入对公账户的联行号" class="layui-input" value=""/>
                    </div>
                </div>

            </div>
            <div class="lanke-pbMsgSide" style="background: none">
                <fieldset class="layui-elem-field layui-field-title" style="">
                    <legend>文件资料：</legend>
                </fieldset>
                <div class="layui-form-item" style="color: #666">
                    注：单张照片不要超过2M
                </div>
                <input type="file" name="file" id="file" style="display:none;" accept="image/*">
                <input type="hidden" name="xwshsfzz" value="" lay-verify="xwshsfzz" id="xwshsfzz">
                <input type="hidden" name="xwshsfzb" value="" lay-verify="xwshsfzb" id="xwshsfzb">
                <input type="hidden" name="xwshscsfzzp" value="" lay-verify="xwshscsfzzp" id="xwshscsfzzp">
                <input type="hidden" name="xwshyhkzm" value="" lay-verify="xwshyhkzm" id="xwshyhkzm">
                <div class="layui-form-item" style="margin-bottom:24px;">
                    <div class="layui-inline">
                        <label class="layui-form-label">上传文件：</label>
                        <div class="layui-input-inline">
                            <span class="layui-input lk-pay-upload" data-type="xwshsfzz">身份证正面</span>
                        </div>
                        <div class="layui-input-inline">
                            <span class="layui-input lk-pay-upload" data-type="xwshsfzb">身份证背面</span>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item lk-payFlow-upItem" id="lk-payUp-upper">
                    <div class="layui-input-inline pay-imgBox" id="img1">

                    </div>
                    <div class="layui-input-inline pay-imgBox" id="img2">

                    </div>
                </div>
                <div class="layui-form-item lk-payFlow-upItem" style="margin-bottom:24px;">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <span class="layui-input lk-pay-upload"  data-type="xwshscsfzzp">手持身份证</span>
                        </div>
                        <div class="layui-input-inline">
                            <span class="layui-input lk-pay-upload" data-type="xwshyhkzm">银行卡正面</span>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item lk-payFlow-upItem" id="lk-payUp-lower">
                    <div class="layui-input-inline pay-imgBox" id="img3">

                    </div>
                    <div class="layui-input-inline pay-imgBox" id="img4">

                    </div>
                </div>
            </div>
            <input type="hidden" lay-submit  lay-filter="submit" value="" id="save">
            <div class="layui-form-item" style="margin: 20px 0 30px 20px" id="btn_box">
                <span class="layui-btn save" data-type="onlySave">仅保存</span>
                <span class="layui-btn layui-btn-normal save" data-type="submit">提交</span>
            </div>
            <div class="layui-form-item" style="margin: 20px 0 30px 20px;display: none" id="editBtn">
                <span class="layui-btn layui-btn-normal save" data-type="edit">修改</span>
            </div>

        </form>


</body>
</html>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-citySelect.js"></script>
<script src="<%=basePath%>newStyle/js/lk-jialianPay.js"></script>
<script src="<%=basePath%>newStyle/js/lk-sendCode.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>
    window.onbeforeunload = function(event) {
        event.returnValue = "系统可能不会保存您所做的更改";
    };
</script>





