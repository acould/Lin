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
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
</head>
<style>
    body {background-color: #f2f2f2;}
    .match_box {position: relative}
</style>
<body>
<div class="layui-row" id="match_list" style="margin: 20px 0 0 10px;">
<%--    <div class="match_box" id="420f5f0ba0554b568323afd9ded6cf4b">--%>
<%--        <div class="img">--%>
<%--            <img src="http://localhost:80/lanker/uploadFiles/uploadImgs/321dfm54lod846579985e1ss2135cvbn/undefined/1560063713107.jpg" alt="" width="100%" height="100%">--%>
<%--        </div>--%>
<%--        <div class="content">--%>
<%--            <p class="title">大萨达撒多撒</p>--%>
<%--            <p class="store">主办门店：ttttt,新测试2,d,新测试3,新测试1,123123,测试1,代理1,代理商编号不存在,33,测试5,dwdwdwd,测试卡券,测试2,测试加V到期,测试7,111,xin1,测试4,2,22,代理2,测试代理商编号,代理3,新测试4,测试6,测试123,1</p>--%>
<%--            <p class="store">报名类型：仅单人报名</p>--%>
<%--            <p class="store">报名时间：2019-06-08&nbsp;至&nbsp;2019-06-15</p>--%>
<%--            <p class="store">比赛时间：2019-06-16&nbsp;至&nbsp;2019-06-22</p>--%>
<%--        </div>--%>
<%--        <div class="alier"></div>--%>
<%--        <div class="lay-iconAuto">--%>
<%--           <i class="layui-icon lay-iconStyle">&#x1005;</i>--%>
<%--        </div>--%>
<%--    </div>--%>

</div>

<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-chooseStore/lk-chooseStore.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>
    layui.use(["layer","form"],function () {
        var layer = layui.layer
            ,form = layui.form;

        var basePath = '<%=basePath%>';
        initialize()
        function initialize(){
            loading("加载中");
            $.post('matches/selMatchesList.do', function (res){
                layer.closeAll();
                var data = res.data.list;
                var htmls = "";
                var enroll_type = "";
                console.log(data);
                if(res.errcode == 0){
                    if(data.length > 0){
                        for (var i = 0; i < data.length; i++) {
                            enroll_type = data[i].enroll_type==1?"仅单人报名":"组队报名";
                            htmls += '<div class="match_box" data-id="'+data[i].id+'">'+
                                '<input type="hidden" name="stores_v" value="'+data[i].stores_v+'">'+
                                '<input type="hidden" name="store_ids_v" value="'+data[i].store_ids_v+'">'+
                                '<input type="hidden" name="stores_nov" value="'+data[i].stores_nov+'">'+
                                '<div class="img">'+
                                '<img src="'+data[i].url+'" alt="" width="100%" height="100%">'+
                                '</div>'+
                                '<div class="content">'+
                                '<p class="title">'+data[i].name+'</p>'+
                                '<p class="store the_stores">主办门店：'+data[i].stores+'</p>'+
                                '<p class="store apply_type">报名类型：'+enroll_type+'</p>'+
                                '<p class="store apply_time">报名时间：'+data[i].enroll_start_time.substring(0,10)+'&nbsp;至&nbsp;'+data[i].enroll_end_time.substring(0,10)+'</p>'+
                                '<p class="store match_time">比赛时间：'+data[i].start_time.substring(0,10)+'&nbsp;至&nbsp;'+data[i].end_time.substring(0,10)+'</p>'+
                                '</div>'+
                                '<div class="alier"></div>'+
                                '<div class="lay-iconAuto">'+
                                '<i class="layui-icon lay-iconStyle">&#x1005;</i>'+
                                '</div>'+
                                '</div>'
                        }
                    }else {
                        htmls = '<div style="width:100%;text-align: center;margin-top:100px;">暂无可群发的赛事</br>仅报名中的赛事可群发</div>'
                    }
                    $("#match_list").html(htmls);
                    $('.match_box').click(function () {
                        var isthis = $(this).children(":last-child");
                        isthis.toggleClass("selected block");
                        isthis.prev().toggleClass("block");
                        var anther = $(this).siblings().children(":last-child");
                        anther.removeClass("selected block");
                        anther.prev().removeClass("block");
                    })
                }else {
                    message(res.message);
                }
            })
        }

    })

    var save = function (){
        var match_box = $(".selected").parent(),
            match_id = match_box.data("id"),
            match_img = match_box.find("img").attr("src"),
            match_title = match_box.find(".title").html(),
            the_stores = match_box.find(".the_stores").html(),
            apply_type = match_box.find(".apply_type").html(),
            apply_time = match_box.find(".apply_time").html(),
            match_time = match_box.find(".match_time").html(),
            stores_v = match_box.children('input[name="stores_v"]').val(),
            store_ids_v = match_box.children('input[name="store_ids_v"]').val(),
            stores_nov = match_box.children('input[name="stores_nov"]').val();

        var data  = {
            match_id:match_id,
            match_img:match_img,
            match_title:match_title,
            the_stores:the_stores,
            apply_type:apply_type,
            apply_time:apply_time,
            match_time:match_time,
            stores_v:stores_v,
            store_ids_v:store_ids_v,
            stores_nov:stores_nov
        }
        return data;
    }
</script>

</body>
</html>
