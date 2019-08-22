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
</style>
<body>
<div class="layui-row layui-col-space22" id="card_list" style="margin: 20px;">
<%--    <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">--%>
<%--        <div class="choose_cardBox">--%>
<%--            <h1 class="title Color100 layui-elip">生日</h1>--%>
<%--            <div class="card_content_msg">--%>
<%--                <p class="time layui-elip">有效期：自领取后当天生效，10天内有效</p>--%>
<%--                <p class="adrss layui-elip">适用门店：拓杰滨江天街店，拓杰下沙宝龙网咖，上网咖（浙大玉泉店），方网咖，Wj网咖，网咖杭州武林2店</p>--%>
<%--                <p class="xiangqing layui-elip">使用说明：1</p>--%>
<%--            </div>--%>
<%--            <div class="alier"></div>--%>
<%--            <div class="lay-iconAuto">--%>
<%--                <i class="layui-icon lay-iconStyle">&#x1005;</i>--%>
<%--            </div>--%>
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

        initialize()
        function initialize(){
            loading("加载中");
            $.post('card/selCardList.do', function (res){
                // console.log(res.data);
                layer.closeAll();
                var data = res.data.list;
                var htmls = "";
                if(res.errcode == 0){
                    if(data.length > 0){
                        for (var i = 0; i < data.length; i++){
                            htmls += '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">'+
                                '<div class="choose_cardBox">'+
                                '<input type="hidden" name="card_id" value="'+data[i].CARD_ID+'">'+
                                '<input type="hidden" name="stores_v" value="'+data[i].stores_v+'">'+
                                '<input type="hidden" name="store_ids_v" value="'+data[i].store_ids_v+'">'+
                                '<input type="hidden" name="stores_nov" value="'+data[i].stores_nov+'">'+
                                '<h1 class="title layui-elip '+data[i].COLOR+'" data-type="'+data[i].COLOR+'">'+data[i].SUB_TITLE+'</h1>'+
                                '<div class="card_content_msg">'+
                                '<p class="time layui-elip">有效期：'+data[i].avalid_time+'</p>'+
                                '<p class="adrss layui-elip">适用门店：'+data[i].stores+'</p>'+
                                '<p class="xiangqing layui-elip">使用说明：'+data[i].DESCRIPTION+'</p>'+
                                '</div>'+
                                '<div class="alier"></div>'+
                                '<div class="lay-iconAuto">'+
                                '<i class="layui-icon lay-iconStyle">&#x1005;</i>'+
                                '</div>'+
                                '</div>'+
                                '</div>'
                        }
                    }else {
                        htmls = '<div style="width:100%;text-align: center;margin-top:100px;">暂无可群发的卡券</br>仅通用券,男神券，女神券，生日券，秒抢券等场景券可群发</div>'
                    }
                    $("#card_list").html(htmls);

                    $('.choose_cardBox').click(function () {
                        var isthis = $(this).children(":last-child");
                        isthis.toggleClass("selected block");
                        isthis.prev().toggleClass("block");
                        var anther = $(this).parent().siblings().children().children(":last-child");
                        anther.removeClass("selected block");
                        anther.prev().removeClass("block");
                    })
                }else{
                    message(res.message);
                }
            })
        }
    })

    var save = function () {
        var cardbox = $(".selected").parent();
        var title = cardbox.children(".title").html();
        var titleClass = cardbox.children(".title").data("type");
        var time = cardbox.find(".time").html();
        var adrss = cardbox.find(".adrss").html();
        var xiangqing = cardbox.find(".xiangqing").html();
        var card_id = cardbox.children('input[name="card_id"]').val();
        var stores_v = cardbox.children('input[name="stores_v"]').val();
        var store_ids_v = cardbox.children('input[name="store_ids_v"]').val();
        var stores_nov = cardbox.children('input[name="stores_nov"]').val();



        var data  = {
            title:title,
            titleClass:titleClass,
            time:time,
            adrss:adrss,
            xiangqing:xiangqing,
            card_id:card_id,
            stores_v:stores_v,
            store_ids_v:store_ids_v,
            stores_nov:stores_nov
        }
        return data;
    }
</script>

</body>
</html>
