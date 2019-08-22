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
    <title>选择对象</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
</head>
<style>
  .layui-form-label {padding-left: 0;width: auto!important;color: #888;}
  .layui-form-item .layui-input-inline {width: 120px!important;}
    .del_btn {border-color: #009688!important;color: #009688!important;}
  .layui-card-header .layui-icon {position: inherit!important;margin-top: 0!important;}
</style>
<body style="background-color: #f2f2f2;">
<div class="layui-fluid">
    <div class="layui-card" >
        <div class="layui-card-body">
            <%--搜索框--%>
            <div class="layui-form layui-card-header layuiadmin-card-header-auto" style="line-height: inherit">
                <form id="selForm">
                <div class="layui-form-item">
                    <label class="layui-form-label">群发对象</label>
                    <div class="layui-input-block" style="margin-left: 72px;">
                        <input type="radio" id="send_obj1" name="send_obj" value="member" title="全部会员" checked="checked" lay-filter="send_obj">
                        <input type="radio" id="send_obj2" name="send_obj" value="member" title="部分会员（以下条件可任意选择填写）" lay-filter="send_obj">
                    </div>
                </div>
                <div id="member_div" style="display: none;">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">会员姓名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_name" lay-reqtext=""
                                       placeholder="请输入" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">会员卡号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_card" lay-reqtext=""
                                       placeholder="请输入" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label">会员性别</label>
                            <div class="layui-input-inline">
                                <select name="mem_sex" lay-search="">
                                    <option value="">选择或搜索</option>
                                    <option value="1">男</option>
                                    <option value="2">女</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">会员等级</label>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_level"
                                       placeholder="请输入" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">卡余额区间</label>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_overage1" lay-reqtext="" min="0" lay-verify="moreZero"
                                       placeholder="起始值" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_overage2" lay-reqtext="" min="0" lay-verify="moreZero"
                                       placeholder="结束值" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label">日均消费金额区间</label>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_money_average1" lay-reqtext="" min="0" lay-verify="moreZero"
                                       placeholder="起始值" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_money_average2" lay-reqtext="" min="0" lay-verify="moreZero"
                                       placeholder="结束值" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">本金余额区间</label>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_principal1" lay-reqtext="" min="0" lay-verify="moreZero"
                                       placeholder="起始值" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_principal2" lay-reqtext="" min="0" lay-verify="moreZero"
                                       placeholder="结束值" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">日均活跃时长区间</label>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_time_average1" lay-reqtext="" min="0" lay-verify="moreZero"
                                       placeholder="起始值" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="mem_time_average2" lay-reqtext="" min="0" lay-verify="moreZero"
                                       placeholder="结束值" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                    <%--<div class="layui-inline">
                        <label class="layui-form-label">查询字段</label>
                        <div class="layui-input-inline">
                            <select name="modules" lay-verify="required" lay-search="">
                                <option value="">选择或搜索</option>
                                <option value="1">全部会员</option>
                                <option value="2">会员卡号</option>
                                <option value="3">会员姓名</option>
                                <option value="4">会员等级</option>
                                <option value="5">会员性别</option>
                                <option value="6">卡余额</option>
                                <option value="7">本金余额</option>
                                <option value="8">平均每天消费金额</option>
                                <option value="9">平均每天活跃时长</option>
                                <option value="10">近期无上网记录</option>
                            </select>
                        </div>
                    </div>--%>
                    <%--<div class="layui-inline">
                        <label class="layui-form-label">近期上网记录</label>
                        <div class="layui-input-inline">
                            <select name="mem_online_record" lay-filter="record_time">
                                <option value=""></option>
                                <option value="1">最近三天</option>
                                <option value="2">最近一周</option>
                                <option value="3">最近一月</option>
                                <option value="4">最近半年</option>
                                <option value="5">自定义时间</option>
                            </select>
                        </div>
                    </div>--%>
                    <div class="layui-inline" id="record_time">
                        <label class="layui-form-label"><font color="red">*</font>上网记录时间</label>
                        <div class="layui-input-inline">
                            <input type="text" id="begin_time" name="begin_time" placeholder="起始时间" readonly lay-verify="time_required"
                                   autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" id="end_time" name="end_time" placeholder="结束时间" readonly lay-verify="time_required"
                                   autocomplete="off" class="layui-input">
                        </div>
                    </div>

                </div>
                    <div class="layui-form-item" style="padding-top: 16px;">
                        <%--<button type="button" class="layui-btn layui-btn-primary layui-btn-sm">添加字段</button>--%>
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" lay-submit lay-filter="toSearch" id="toSearch">开始查询</button>
                        <span>（请先点击查询后再确定保存）</span>
                    </div>
                </div>

                </form>
                <div class="layui-form-item layui-hide">
                    <input type="button" lay-submit lay-filter="chooseSubmit" id="chooseSubmit" value="确认">
                </div>
            </div>

            <div class="layui-card-body" style="padding: 20px 20px">
                <%--表单--%>
                <p style="color: red;margin-bottom: 14px;display: none;" id="result_p">查询结果：有 <font id="mem_num">0</font> 位已绑定揽客会员满足条件</p>
                <input type="hidden" id="open_ids">
                <table class="layui-hide" id="layTable" lay-filter="layTable"></table>
                <div class="page-header position-relative" style="padding:20px 0 0 0">
                    <table style="width:100%;">
                        <tr>
                            <td style="vertical-align:top;">
                                <div id="layPage"  style="text-align: right"></div>
                            </td>
                        </tr>
                    </table>
                </div>
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
    var store_ids = '${pd.store_ids}';

    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/internet/' //静态资源所在路径
    }).extend({
        set_edit : 'msgManager/set_edit',
    }).use(['set_edit']);
</script>

</body>
</html>
