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
    <title>服务器维护通知</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/default/_css/ueditor.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/96619a5672.css" />
</head>
<style>
    body .demo-class {box-shadow: 1px 1px 20px rgba(0,0,0,0.2)!important}
</style>
<body>
<form class="layui-form" action="" style="padding:20px 30px" lay-filter="form" id="form">
    <input type="hidden" value="${pd.send_id}" id="send_id" name="send_id">
    <input type="hidden" value="${send_token}" id="send_token" name="send_token">
    <input type="hidden" value="server_maintenance" id="temp_type" name="temp_type">
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: auto;color: red">模板消息严禁发送营销相关内容，否则有被微信封禁该模板的风险</label>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">群发对象：</label>
        <div class="layui-input-block">
            <input type="radio" name="send_obj" value="fans" title="全部粉丝" lay-filter="object">
            <input type="radio" name="send_obj" value="member" title="会员" lay-filter="object">
        </div>
    </div>
    <div class="layui-form-item" id="store_box" style="display: none">
        <label class="layui-form-label">群发门店：</label>
        <div class="layui-input-block">
            <span class="layui-btn layui-btn-sm layui-btn-primary" onclick="chooseStore_v()">
                <i class="layui-icon">&#xe654;</i>选择门店</span>
            <div class="lk-title-text" id="store_item">

            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">群发标题：</label>
        <div class="layui-input-block">
            <input type="text" name="first_data" id="first_data" autocomplete="off" lay-verify="required" placeholder="请输入标题"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">维护时间：</label>
        <div class="layui-input-block">
            <input type="text" name="maintain_time" id="maintain_time" lay-verify="required" placeholder="请选择维护时间"
                   readonly class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">恢复时间：</label>
        <div class="layui-input-block">
            <input type="text" name="reply_time"  id="reply_time" lay-verify="required" placeholder="请选择维护时间"
                   readonly class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">维护内容：</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入内容" name="content" id="content" lay-verify="required"
                      class="layui-textarea" maxlength="150"></textarea>
            <p style="color: #c7c7c7;text-align: right">字数限制：150</p>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注：</label>
        <div class="layui-input-block">
            <input type="text" name="remark_data" id="remark_data" autocomplete="off" placeholder="请输入备注（选填）"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否编辑图文：</label>
        <div class="layui-input-block">
            <input type="radio" id="hasUrl" name="is_has_url" value="true" title="是" lay-filter="is_has_url" checked>
            <input type="radio" id="noUrl" name="is_has_url" value="false" title="否" lay-filter="is_has_url">
        </div>
    </div>
    <div class="layui-form-item" id="temp_content_div">
        <label class="layui-form-label">图文内容：</label>
        <div class="layui-input-block">
            <textarea id="temp_content" name="temp_content" lay-verify="hasChoose"></textarea>
        </div>
    </div>
    <input type="hidden" lay-submit  lay-filter="submit" value="" id="save">
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

<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-chooseStore/lk-chooseStore.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>

<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/third-party/jquery-1.10.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/a92d301d77.js"> </script>
<script type="text/javascript">
    //赋初始值，提供给a92d301d77.js使用
    var BASEURL = 'https://www.135editor.com';
    var domain_135 = BASEURL;

    //赋值给current_editor，提供给a92d301d77.js使用
    var current_editor = UE.getEditor('temp_content',{
        initialFrameHeight:600,
        plat_host   :'www.135editor.com',
        // style_width	:360,
        // zIndex 		: 900,

        pageLoad	:true,
        open_editor	:true,
        focus		:true,
        focusInEnd	:true
        //scaleEnabled:true,
        //autoHeightEnabled:true,
        //minFrameWidth:800    //编辑器拖动时最小宽度,默认800
        //minFrameHeight:220  //编辑器拖动时最小高度,默认220

    });
</script>
<script>
    layui.use(["form","layer","laydate"],function (){
        var form = layui.form
            ,layer = layui.layer
            ,laydate = layui.laydate

        laydate.render({
            elem: '#maintain_time'
        });
        laydate.render({
            elem: '#reply_time'
        });
        form.on('radio(object)', function(data){
            if(data.value == "member"){
                $("#store_box").show()
            }else {
                $("#store_box").hide()
            }
        });
        form.on('radio(is_has_url)', function(data){
            if(data.value == "true"){
                $("#temp_content_div").show()
            }else {
                $("#temp_content_div").hide()
            }
        });

        form.verify({
            hasChoose: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (current_editor.getContent().trim() == '' && ( $("#hasUrl").is(":checked") )) {
                    return '请输入图文内容';
                }
            },
        })

        //初始化
        loadStores_v();//加载门店
        setTimeout(function () {
            initialize();
        },200)
        function initialize(){
            loading("加载中");
            var id = $("#send_id").val();
            if(id == ""){

                form.val("form", { "send_obj":"fans" });
                layer.closeAll();
                return false;
            }

            var field = new Object();
            field.send_id = id;
            $.post('<%=basePath%>msgManager/loadTempMsg.do', field, function (res){
                layer.closeAll();
                if(res.errcode != 0){
                    message(res.errmsg);
                    return false;
                }
                var data = res.data.pd;
                var keyword_data = JSON.parse(data.keyword_data);
                form.val("form",{
                    "send_obj":JSON.parse(data.sel_json).send_obj
                    ,"first_data":data.first_data
                    ,"maintain_time":keyword_data.keyword1
                    ,"reply_time":keyword_data.keyword2
                    ,"content":keyword_data.keyword3
                    ,"remark_data":data.remark_data
                });


                if(data.url == '' || data.url == null || data.url == undefined || data.url == 'undefined'){
                    $("#hasUrl").prop("checked",false);
                    $("#hasUrl").removeAttr("checked");

                    $("#noUrl").attr("checked",true);
                    $("#temp_content_div").hide();
                    form.render();
                }
                if(data.temp_content != null && data.temp_content != ''){
                    var ue = UE.getEditor("temp_content");
                    ue.setContent(data.temp_content);
                }

                $("form :input").attr("disabled", true);

                if(JSON.parse(data.sel_json).send_obj == "member"){
                    var store_ids = data.store_ids.split(",");
                    var stores = $(".store");
                    for (var i = 0; i < store_ids.length; i++) {
                        for (var j = 0; j < stores.length; j++) {
                            if(store_ids[i] == $(stores[j]).data("id")){
                                $(stores[j]).addClass("active");
                                break;
                            }
                        }
                    }
                    if(store_ids.length == stores.length){
                        $("#all_store").addClass("allActive");
                    }
                    getStores();
                    $("#store_box").show();
                }


            })
        }

        // 提交保存
        form.on('submit(submit)', function(data){
            $("#save").attr("lay-filter", "");

            var field =data.field;
            loading("提交中");
            if(field.send_obj == "member"){
                var store_ids = '';
                var store_names = '';
                var length = $(".active").length;
                $(".active").each(function (e){
                    store_ids += $(this).data("id");
                    store_names += $(this).text();
                    if(length-1 != e){
                        store_ids += ",";
                        store_names += ", ";
                    }
                })
                if(store_ids == ''){
                    $("#save").attr("lay-filter", "submit");
                    message('请选择门店');
                    return false;
                }
                field.store_ids = store_ids;
                field.store_names = store_names;
            }

            var keyword_data = new Object();
            keyword_data.keyword1 = $("#maintain_time").val();
            keyword_data.keyword2 = $("#reply_time").val();
            keyword_data.keyword3 = $("#content").val();
            field.keyword_data = JSON.stringify(keyword_data);

            field.temp_content = current_editor.getContent();
            layer.confirm("确认要群发吗？", {
                btn: ['确定', '取消'],
            }, function () {
                $.post('<%=basePath%>msgManager/saveNotify.do', field, function (res){
                    layer.closeAll();
                    if(res.errcode == 0){
                        message("群发成功");
                        setTimeout(function () {
                            parent.location.reload();
                        }, 800)
                    }else {
                        $("#save").attr("lay-filter", "submit");
                        message(res.errmsg);
                    }
                })
            }, function () {
                $("#save").attr("lay-filter", "submit");
                return
            });

        })

    })
    var save = function () {
        $("#save").trigger('click');
    }
</script>
</body>
</html>
