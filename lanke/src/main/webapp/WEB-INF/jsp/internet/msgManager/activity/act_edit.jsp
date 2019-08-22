<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title>活动群发</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
</head>
<style>
    .layui-form-label {width: 100px!important;}
    .blocks {margin-left: 130px!important;max-width: 500px!important;}
    .openUp-action {width: 430px;margin: 0 20px;}
    #card_item .choose_cardBox{width: 320px;margin-bottom: 50px;!important;}
    .match_box{box-shadow: 0px 4px 20px #e2e2e2;margin-left: 0!important;}
    .lk_storeChoose_list div, .choosable_content div, .noChoose_content div {padding: 4px 18px}
    .lk-title-text {margin-left: 20px!important;}
</style>
<body style="background-color: #f2f2f2;">
<input type="hidden" value="${pd.send_id}" id="send_id" name="send_id">
<input type="hidden" value="${send_token}" id="send_token" name="send_token">
<input type="hidden" value="${pd.act_type}" id="act_type" name="act_type">
<div class="layui-fluid">
    <div class="layui-card" >
        <div class="layui-card-body" style="padding: 20px 20px">
            <%--表单--%>
            <div class="layui-form-item"><span id="goback" onclick="window.history.go(-1)">返回上个页面》</span></div>
            <div class="layui-form-item">
                <div class="layui-tab layui-tab-card" style="max-width: 1000px">
                    <ul class="layui-tab-title">
                        <li data-type="card" id="setDefault1">群发卡券</li>
                        <li data-type="matches">群发赛事</li>
                        <li data-type="msg">群发消息</li>
                    </ul>
                    <div class="layui-tab-content" style="padding: 50px 10px">
                        <%--群发卡券--%>
                        <div class="layui-tab-item" id="setDefault2">
                            <form class="layui-form"  lay-filter="form_card">
                                <input name="act_type" type="hidden" value="card">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">群发的卡券：</label>
                                    <div class="layui-input-block">
                                        <span class="layui-btn layui-btn-sm layui-btn-primary" onclick="chooseCard()"><i class="layui-icon">&#xe654;</i>选择卡券</span>
                                        <div class="lk-title-text" id="card_item">

                                        </div>
                                    </div>
                                </div>
                                <div id="card_store_list">

                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">群发对象：</label>
                                    <div class="layui-input-block">
                                        <span class="layui-btn layui-btn-sm layui-btn-primary" onclick="chooseObj('card')"><i class="layui-icon">&#xe654;</i>设置</span>
                                        <div class="lk-title-text" id="cardMember_item">
                                            <p>默认群发门店里的全部会员</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">备注广告词：</label>
                                    <div class="layui-input-block blocks" >
                                        <input type="text" id="card_ad_content" name="ad_content" autocomplete="off" placeholder="请输入广告词限20字" class="layui-input" maxlength="20">
                                    </div>
                                </div>
                                <input type="hidden" lay-submit  lay-filter="card_submit" value="" id="card_save">
                            </form>
                        </div>

                        <%--群发赛事--%>
                        <div class="layui-tab-item">
                            <form class="layui-form"  lay-filter="form_matches">
                                <input name="act_type" type="hidden" value="matches">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">群发的赛事：</label>
                                    <div class="layui-input-block">
                                        <span class="layui-btn layui-btn-sm layui-btn-primary" onclick="chooseMatches()"><i class="layui-icon">&#xe654;</i>选择赛事</span>
                                        <div class="lk-title-text" id="matches_item">

                                        </div>
                                    </div>
                                </div>
                                <div id="matches_store_list">

                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">群发对象：</label>
                                    <div class="layui-input-block">
                                        <span class="layui-btn layui-btn-sm layui-btn-primary" onclick="chooseObj('matches')"><i class="layui-icon">&#xe654;</i>设置</span>
                                        <div class="lk-title-text" id="matchesMember_item">
                                            <p>默认群发门店里的全部会员</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">备注广告词：</label>
                                    <div class="layui-input-block blocks">
                                        <input type="text" id="matches_ad_content" name="ad_content" autocomplete="off" placeholder="请输入广告词限20字" class="layui-input" maxlength="20">
                                    </div>
                                </div>
                                <input type="hidden" lay-submit  lay-filter="matches_submit" value="" id="matches_save">
                            </form>
                        </div>

                        <%--群发消息--%>
                        <div class="layui-tab-item">
                            <form class="layui-form"  lay-filter="form_msg">
                                <input name="act_type" type="hidden" value="msg">
                                <div class="layui-form-item" id="store_box">
                                    <label class="layui-form-label">群发门店：</label>
                                    <div class="layui-input-block">
                                        <span class="layui-btn layui-btn-sm layui-btn-primary" onclick="chooseStore_v('black')"><i class="layui-icon">&#xe654;</i>选择门店</span>
                                        <div class="lk-title-text" id="store_item">

                                        </div>
                                    </div>
                                </div>
                                <div id="msg_store_list">

                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">群发对象：</label>
                                    <div class="layui-input-block">
                                        <span class="layui-btn layui-btn-sm layui-btn-primary" onclick="chooseObj('msg')"><i class="layui-icon">&#xe654;</i>设置</span>
                                        <div class="lk-title-text" id="msgMember_item">
                                            <p>默认群发门店里的全部会员</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">群发标题：</label>
                                    <div class="layui-input-block blocks">
                                        <input type="text" name="title" id="titile" autocomplete="off" lay-verify="required" placeholder="请输入标题" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item layui-form-text">
                                    <label class="layui-form-label">群发内容：</label>
                                    <div class="layui-input-block blocks">
                                        <textarea placeholder="请输入内容" name="content" id="content" lay-verify="required" class="layui-textarea" maxlength="150"></textarea>
                                        <p style="color: #c7c7c7;text-align: right">字数限制：150</p>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">备注广告词：</label>
                                    <div class="layui-input-block blocks">
                                        <input type="text" id="msg_ad_content" name="ad_content" autocomplete="off" placeholder="请输入广告词限20字" class="layui-input" maxlength="20">
                                    </div>
                                </div>
                                <input type="hidden" lay-submit  lay-filter="msg_submit" value="" id="msg_save">
                            </form>
                            <div class="lk_storeChoose_list_v" id="lk_storeChoose_list_v" style="display: none">
                                <div class="choosable_box">
                                    <div class="choosable_title">可选门店</div>
                                    <div class="choosable_content layui-clear">
                                        <div id="all_store">全选</div>

                                    </div>
                                </div>
                                <div class="noChoose_box" id="noChoose_box">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" style="padding: 28px">
                <button class="layui-btn btn-primary mail-btn">确定群发</button>
            </div>
        </div>
    </div>
</div>


<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-chooseStore/lk-chooseStore.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>
    var basePath = '<%=basePath%>';
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/internet/' //静态资源所在路径
    }).extend({
        act_edit : 'msgManager/act_edit',
    }).use(['act_edit']);
</script>

</body>
</html>
