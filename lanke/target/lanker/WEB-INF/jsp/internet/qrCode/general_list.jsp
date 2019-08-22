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
    <title>网吧一码通</title>
    <style>
        body {background-color: #f6f8f9; min-width: 600px; overflow: auto}
        body .downloadQrs {border-radius: 5px;overflow: hidden;}
        .general_details {float: right;color: #1E9FFF;font-size: 14px;font-weight: 500;padding: 6px 20px 0px 0px;cursor: pointer;}
        .layui-table-cell .layui-form-checkbox[lay-skin=primary] {top: 5px;padding: 0;}
    </style>
</head>
<body>

<div class="card-header">
    <div class="card-header-msg">
        <img src="<%=basePath%>newStyle/images/logo2.png"class="card-lankeIcon">
        <span class="card-header-title">网吧管理后台</span>
        <div class="card-header-right">
            <img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
            <span class="card-userName layui-elip"> <small>Welcome</small><br>
					${pdStore.STORE_NAME}
			</span>
        </div>
    </div>
</div>

<div class="card-width" style="min-width: 950px;">
    <h1>
        当前开通网吧一码通<span style="font-weight: 300;">—</span><span>${pdStore.STORE_NAME}</span>
        <span class="general_details" onclick="qrcode_details()">网吧一码通详情了解</span>
    </h1>
    <div class="lanke-pbMsg" style="padding-bottom:100px;">
        <!-- 流程图 -->
        <div class="lanke-pbMsgSide">
            <fieldset class="layui-elem-field layui-field-title" style="">
                <legend>网吧一码通开通流程</legend>
            </fieldset>
            <div class="lk-pay-flow layui-clear five">
                <div class="flow-box">
                    <div class="lk-payFlow-icon lk_general_icon">
                        <i class="i1"></i>
                    </div>
                    <p class="text-center">下载导入模板</p>
                </div>
                <div class="flow-box">
                    <div class="lk-payFlow-icon lk_general_icon">
                        <i class="i2"></i>
                    </div>
                    <p class="text-center">导入网吧电脑信息</p>
                </div>
                <div class="flow-box">
                    <div class="lk-payFlow-icon lk_general_icon">
                        <i class="i3"></i>
                    </div>
                    <p class="text-center">批量下载二维码</p>
                </div>
                <div class="flow-box">
                    <div class="lk-payFlow-icon lk_general_icon">
                        <i class="i4"></i>
                    </div>
                    <p class="text-center">将二维码打印贴至<br>电脑桌上<br></p>
                </div>
                <div class="flow-box">
                    <div class="lk-payFlow-icon lk_general_icon">
                        <i class="i5"></i>
                    </div>
                    <p class="text-center">会员即可扫码上机</p>
                </div>
            </div>
        </div>

        <!-- 填写信息 -->
        <div class="lanke-pbMsgSide" style="margin-top:20px">
            <fieldset class="layui-elem-field layui-field-title" style="margin: 0;">
                <legend>电脑二维码信息</legend>
            </fieldset>
        </div>
        <div style="margin: 20px 0 0 0">
            <div class="layui-form-item" style="margin-bottom: 14px;">
                <div class="layui-inline" style="margin-bottom: 0;">
                    <input class="layui-input" name="keywords" id="keywords" autocomplete="off" placeholder="关键词搜索" style="height: 32px">
                </div>
                <span class="layui-btn layui-btn-sm btn-success" style="margin-left: 10px;" id="toSearch">
                    <i class="layui-icon" style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索
                </span>
                <div style="float: right;margin-top: 1px;">
                    <form id="Form" method="post" class="layui-form layui-form-pane">
                        <input type="hidden" id="store_id" name="store_id" value="${pdStore.STORE_ID}">
                        <input type="file" id="file" name="file" style="display: none" accept="application/vnd.ms-excel">
                    </form>
                    <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="importCN()">导入电脑</button>
                    <button class="layui-btn layui-btn-sm layui-btn-primary" onclick="downloadTemp()">下载导入模板</button>
                    <button class="layui-btn layui-btn-sm" onclick="downloadQrs()">批量下载二维码</button>
                    <button class="layui-btn layui-btn-sm layui-btn-primary" onclick="editQrcode('add','${pdStore.STORE_ID}','')">新增单个二维码</button>
                    <button class="layui-btn layui-btn-sm layui-btn-primary" onclick="banAll('2')">禁用全部电脑</button>
                    <button class="layui-btn layui-btn-sm layui-btn-primary" onclick="banAll('1')">启用全部电脑</button>
                </div>
            </div>
        </div>
        <p style="color: #666;font-size: 12px;margin-bottom: 24px;text-align: right"><font color="red">*</font>如需二维码定制化需求请联系客服：15397046172</p>
        <div id="general_table" lay-filter="code">

        </div>
        <div class="page-header position-relative" style="padding:8px 0 50px 0">
            <table style="width:100%;">
                <tr>
                    <td>
                        <button class="layui-btn layui-btn-sm layui-btn-primary" id="closeAll">
                            <i class="layui-icon" style="padding-right: 4px">&#xe640;</i>批量删除
                        </button>
                    </td>
                    <td style="vertical-align:top;">
                        <div style="float: right;padding-top: 0px;margin-top: 0px;" id="page"></div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="qrcode-js" id="qrcode-js" style="display: none">
            <h1 class="title">网吧一码通功能介绍：</h1>
            <p>
                网吧设置：根据开通流程，先下载导入电脑模板，填写正确的电脑编号和电脑名称。然后导入电脑，
                再批量下载二维码。二维码主页含有“会员上机”、“会员签到”、“会员领券”、“会员充值”、“会员抽奖”等功能</p>
            <p>
                会员使用：已在微信公众号中绑定了的网吧会员卡的用户，在相应网吧收银台激活会员卡后，能在电脑前
                用微信扫码上机。会员上机后可查看实时时长和实时费用，并可在微信公众号中进行自助下机结算、自助换机。</p>
            <p>禁用电脑：当电脑被禁用了，会员扫码上机功能则被禁用了，其他功能不影响正常使用。启用后可正常使用</p>
            <p style="margin-bottom: 30px">删除电脑：当电脑被删除了，会导致电脑二维码变成无效</p>
            <h1 class="title">网吧一码通页面展示：</h1>
            <img src="<%=basePath%>newStyle/images/newerGuide/qrcode-js.jpg" alt="">
        </div>
</body>
</html>
<script type="text/html" id="barDemo">
    <div class="">
        <i class="iconfont lk_table_icon lk_table_icon_success" title="编辑" style="font-size: 15px;" lay-event="edit">&#xe637;</i>
        {{# if(d.status == 1){ }}
            <i class="iconfont lk_table_icon lk_table_icon_warning" title="禁用" style="font-size: 16px;" lay-event="ban">&#xe635;</i>
        {{# }else{ }}
            <i class="iconfont lk_table_icon lk_table_icon_warning" title="启用" style="font-size: 16px;" lay-event="ban">&#xe65c;</i>
        {{# } }}
        <i class="iconfont lk_table_icon lk_table_icon_danger" title="删除" style="font-size: 17px;" lay-event="del">&#xe645;</i>
    </div>
</script>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-pay.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script type="text/javascript" src="<%=basePath%>newStyle/js/jszip.min.js"></script>


<script>
    var store_id = $("#store_id").val();
    layui.use(['layer','table','laypage','element'],function (){
        var layer = layui.layer,
            table = layui.table,
            laypage  = layui.laypage;
            element  = layui.element;

        table.render({
            id:"qrcode",
            elem: "#general_table"
            ,url:'<%=basePath%>qrCode/loadQrList.do?store_id='+store_id
            ,request:{
                pageName:'currentPage',
                limitName:'showCount'
            }
            ,cols: [[
                {type:'checkbox'}
                ,{field:'serial_number', title: '电脑编号', align:'center'}
                ,{field:'computer_name', title: '电脑名称', align:'center'}
                ,{field:'status_info', title: '电脑状态', align:'center'}
                ,{field:'ip', title: '电脑IP', align:'center'}
                ,{field:'mac_address', title: 'Mac地址', align:'center'}
                ,{field:'make',title: '操作', align:'center',toolbar: '#barDemo'}
            ]]
            ,text: {none: '暂无相关数据'}
            ,done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                pageReload(count, res.showCount);
            }
        });

        //表格操作监听
        table.on('tool(code)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent == 'edit') { //编辑
                editQrcode("edit", store_id,data.id);
            } else if (layEvent == "del") {// 删除
                delQrcode(data.id, obj);
            } else if (layEvent == "ban") {//启用或禁用
                operation(data.id, data.status, obj);
            }
        });

        //渲染分页
        function pageReload(count, showCount) {
            laypage.render({
                elem: 'page'
                , count: count  //数据总数量
                , limit: showCount
                , limits: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
                , curr: location.hash.replace('#!fenye=', '') //获取起始页
                , hash: 'fenye' //自定义hash值
                , layout: ['prev', 'page', 'next', 'skip', 'count', 'limit']
                , theme: '#41a7f0'
                , jump: function (obj, first) {
                    //首次不执行
                    if (!first) {
                        //console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                        //console.log(obj.limit); //得到每页显示的条数
                        table.reload('qrcode', {
                            where: { //设定异步数据接口的额外参数，任意设
                                keywords: $("#keywords").val(),
                                currentPage: obj.curr,
                                showCount: obj.limit,
                                store_id:store_id

                            }
                        });

                    }
                }
            });
        }

        //搜索
        $("#toSearch").click(function () {
            table.reload('qrcode', {
                elem: "#general_table",
                where: { //设定异步数据接口的额外参数，任意设
                    keywords: $("#keywords").val(),
                    store_id:store_id
                }
            });
        });
        // 批量删除
        $("#closeAll").click(function () {
            var checkStatus = table.checkStatus('qrcode');
            var ids = '';
            var num = checkStatus.data.length;
            if( num<= 0){
                message("请选择数据");
            }else {
                layer.confirm("确定删除这"+num+"项", {
                    btn: ['确定', '取消']
                }, function () {
                    for (var i = 0; i < num; i++) {
                        ids += checkStatus.data[i].id;
                        if(i != num-1){
                            ids += ",";
                        }
                    }
                    loading();
                    $.ajax({
                        type: "POST",
                        url: '<%=basePath%>qrCode/delMore.do',
                        data: {ids:ids, store_id:store_id},
                        dataType: 'json',
                        success: function (data) {
                            console.log(data);
                            layer.closeAll();
                            message(data.message);
                            if (data.result == "true") {
                                table.reload('qrcode', {
                                    where: { //设定异步数据接口的额外参数，任意设
                                        keywords: $("#keywords").val(),
                                        store_id:store_id
                                    }
                                });
                            }
                        },
                        error: function () {
                            message("系统繁忙，请稍后再试！");
                        }
                    });
                });
            }
        })

        //启用或禁用
        function operation(id, status, obj) {
            var confirm = "确认要启用吗？";
            if(status == 1){
                confirm = "确认要禁用吗？";
            }
            layer.confirm(confirm, {
                btn: ['确定', '取消']
            }, function () {
                $.ajax({
                    type: "POST",
                    url: '<%=basePath%>qrCode/operation.do',
                    data: {id:id,status:status},
                    dataType: 'json',
                    cache: false,
                    success: function (data) {
                        layer.msg(data.message);
                        if (data.result == "true") {
                            setTimeout(function () {
                                table.reload('qrcode', {
                                    where: { //设定异步数据接口的额外参数，任意设
                                        keywords: $("#keywords").val(),
                                        currentPage: obj.curr,
                                        showCount: obj.limit,
                                        store_id:store_id
                                    }
                                });
                            }, 800)
                        }
                    },
                    error: function () {
                        layer.msg("系统繁忙，请稍后再试！");
                        return false;
                    }
                });
            });

        }

        function delQrcode(id, obj) {
            layer.confirm('确定要删除？', {
                btn: ['确定', '取消']
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "<%=basePath%>qrCode/delMachine.do",
                    data: {id: id},
                    dataType: 'json',
                    beforeSend: beforeSend(''),
                    success: function (data) {
                        layer.closeAll();
                        layer.msg(data.message);
                        if (data.result == "true") {
                            setTimeout(function () {
                                table.reload('qrcode', {
                                    where: { //设定异步数据接口的额外参数，任意设
                                        keywords: $("#keywords").val(),
                                        currentPage: obj.curr,
                                        showCount: obj.limit,
                                        store_id:store_id
                                    }
                                });
                            }, 800)
                        }
                    },
                    error: function () {

                        layer.msg("系统繁忙，请稍后再试！");
                        return false;
                    }
                });
            });
        }


    })


    //下载模板
    function downloadTemp() {
        <%--window.location.href = "<%=basePath%>qrCode/downloadTemp.do";--%>
        window.location.href = "<%=basePath%>uploadFiles/二维码导入模板.xls";
    }

    //导入电脑
    function importCN() {
       layer.open({
            btn: ['确定','关闭'],
            btn1: function(index){
                //按钮的回调
                window["layui-layer-iframe" + index].upload();
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '导入电脑',
            shadeClose: false,
            shade: 0.8,
            area: ['564px', '436px'],
            content:['<%=basePath%>qrCode/goImport.do?store_id='+$("#store_id").val()],
        });
    }

    //新增或编辑单个二维码
    function editQrcode(flag,store_id,id) {
        var title = "新增单个二维码";
        if(flag == "edit"){
            title = "编辑电脑信息";
        }
        layer.open({
            btn: ['仅保存', '保存并下载二维码', '关闭'],
            btn1: function(index, layero){
                //按钮的回调
                var res = window["layui-layer-iframe" + index].save("save");
            },
            btn2: function(index, layero){
                //按钮的回调
                var res = window["layui-layer-iframe" + index].save("down");
                return false;
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: title,
            shadeClose: false,
            shade: 0.8,
            area: ['550px', '450px'],
            content:['<%=basePath%>qrCode/goEditQr.do?store_id='+store_id+'&id='+id],
        });
    }

    //禁用所有
    function banAll(status) {
        var info = '';
        if(status == '1'){
            info = "启用";
        }else if(status == '2'){
            info = "禁用";
        }
        if($(".layui-none").val() != null){
            layer.msg("请先导入电脑");
        }else{
            layer.confirm('确定要'+info+'所有电脑吗？', {
                btn: ['确定', '取消']
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "<%=basePath%>qrCode/banAll.do",
                    data: {store_id: $("#store_id").val(),status:status},
                    dataType: 'json',
                    beforeSend: beforeSend(''),
                    success: function (data) {
                        layer.closeAll();
                        if (data.result == "true") {
                            layer.msg(info+"成功");
                            setTimeout(function () {
                                location.reload();
                            }, 800)
                        }else{
                            layer.msg(data.message);
                        }
                    },
                    error: function () {
                        layer.closeAll();
                        layer.msg("系统繁忙，请稍后再试！");
                        return false;
                    }
                });
            });
        }

    }

    // 批量下载二维码
    function downloadQrs() {

        if($(".layui-none").val() != null){
            layer.msg("请先导入电脑");
        }else{
            layer.open({
                type: 2,
                title: false,
                shadeClose: false,
                shade: 0.8,
                area: ['950px', '660px'],
                resize:false,
                closeBtn: 0,
                skin: 'layui-layer-nobg downloadQrs',
                content:['<%=basePath%>qrCode/goGeneralCode.do?store_id='+$("#store_id").val()],
            });
        }

}
function qrcode_details() {
    layer.open({
        btn: [ '关闭'],
        btn1: function(index, layero){
            //按钮的回调
            layer.closeAll()
        },
        type: 1,
        title: "网吧一码通详情介绍",
        shadeClose: true,
        shade: 0.8,
        area: ['800', '80%'],
        resize:false,
        content:$("#qrcode-js"),
    });
}

</script>





