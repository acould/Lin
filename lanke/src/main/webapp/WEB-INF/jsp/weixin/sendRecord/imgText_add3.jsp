<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>网吧管理后台</title>
    <base href="<%=basePath%>">
    <%@ include file="../../system/index/top.jsp"%>

    <!-- 删除时确认窗口 -->
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.all.min.js"></script>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js">
    </script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/ace/js/bootbox.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/a92d301d77.js"> </script>

    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-message.js"></script>
    <script src="<%=basePath%>js/qrcode.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">

    <link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/default/_css/ueditor.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/96619a5672.css" />
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/newtextimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/style.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <style type="text/css">

        * {
            text-decoration: none;
            list-style: none;
        }

        body {
            background: rgba(1, 1, 1, .05);
            position: relative;
            overflow: hidden;
            SCROLLBAR-FACE-COLOR: #ff99cc ;
            SCROLLBAR-HIGHLIGHT-COLOR: #FFFFFF;
            SCROLLBAR-SHADOW-COLOR: #ff0000;
            SCROLLBAR-3DLIGHT-COLOR: #ff0000;
            SCROLLBAR-ARROW-COLOR: #ff0000;
            SCROLLBAR-TRACK-COLOR: #ffcccc;
            SCROLLBAR-DARKSHADOW-COLOR: #ffffff
        }
        scrollbar {
            -moz-appearance: none !important;
            background: #00ff00 !important;
        }
       /* #edui-default {*/
       /*     height: 600px;*/
       /* }*/
       /*#edui12_toolbarbox{*/
       /*    position:absolute;*/
       /*    top:0;*/
       /*}*/
        #edui12_mainbar{
            display: flex;
            flex-direction: column;
        }
        .guide{
            overflow-y: scroll!important;
            height: 60rem!important;
        }
        #flex {
            display: flex;
            position: fixed;
            top: 0rem;
            bottom:0rem;
            left: 50%;
            transform: translate(-50%);
            width: 90rem;
            margin: 0 auto!important;
        }

        #flex #left {
            width: 20rem;
            /* border:1px solid black; */
            margin-right: 4.8rem;
            background: #fff;
            margin-top:4.3rem;
            margin-bottom:4.5rem;
        }

        #flex #right {
            flex: 1;
            /* border:1px solid black; */
            /*margin-left: 40px;*/
            background: #fff;
            padding-top:10rem;

        }
        .section{
            width: 19rem;
            /*max-height:47rem;*/
            /*overflow-y:auto;*/
            overflow-x: hidden;
        }
        /*.section::-webkit-scrollbar {display:none}*/
        #lcont {
            width: 20rem;
            /*overflow-y: scroll;*/
            /* height: 700px; */
        }
        .img{
            display: block;
            width: 100%;
            height:auto;

        }
        #flex #left li {
            margin: 0 14px 5px 14px;
            height: 70px;
            position: relative;
            overflow: hidden!important;
            border-bottom: 1px solid rgba(1, 1, 1, .2);
            /* border:5px solid green */
        }

        #flex #left li:first-child {
            background: rgba(1, 1, 1, .15);
            height: 100px;
        }

        #flex #left li footer {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            top: 100px;
            background: rgba(1, 1, 1, .4);
            display: flex;
        }

        #flex #left li footer div {
            flex: 1;
            text-align: center;
            line-height: 30px;
            display: none
        }

        #flex #left header {
            height: 40px;
            line-height: 40px;
            margin-left: 5px;
            font-size: 14px;
        }

        #flex #left>footer {
            margin: 1rem 14px 5px 14px;
            height: 100px;
            border: 1px dotted rgba(1, 1, 1, .4)
        }

        #editor {
            width: 500px!important;
            height: 100%!important;
            /* position: absolute!important;
               left:50%;
               transform: translate(-50%);
               bottom:0; */
            /* margin:0 auto!important; */
            margin: 0!important;
        }

        #edui12 {
            height: 100%!important;
            display: flex!important;
            flex-direction: column!important;
        }

        #edui12_iframeholder {
            height: 900px!important
        }
#CONTENT{
    height: 100%!important;
}
#edui12_mainbar {
            width: 100%!important;
            border-right: 0!important;
            margin-right: 0!important;
            height: 100%!important;
        }

        .textrea {
            background: rgba(1, 1, 1, .05)
        }

        #eduiedui12-styles {
            width: 0!important;
            height: 0!important;
            overflow: hidden!important;
            border: 0!important;
        }

        #color-plan {
            position: fixed!important;
            bottom: 100px!important;
            right: 0px!important;
            z-index: 3000!important;
        }

        #color-choosen {
            position: fixed!important;
        }

        .one {
            border: 1px solid blue;
        }
        .showQf{
            display: none;
        }
        .tianjia{
            position:fixed;
            top:0;
            bottom: 0;
            left:0;
            right:0;
            z-index:1000;
        }
        .titles{
            position:absolute;
            top:10px;
            left:0;
            right:0;
            /*bottom:5rem;*/
            background:transparent;
            padding:1rem 2rem 0 1rem;
            z-index: 100;
        }
        .titles input{
            background:transparent!important;
        }
        .up{
            cursor: pointer
        }
        .down{
            cursor: pointer
        }
        .del{
            cursor: pointer
        }
        .bq{
            text-align: center;
            letter-spacing: .4rem;
            width: 5rem;
            border:0;
            border-radius: 30% 50%;
            font-size:.5rem!important;
            height:2rem;
        }
        .Bq img{
            width: 1.4rem;
            height:1.4rem;
        }
        .Bq{
            display:table;
            border:1px solid rgba(1,1,1,.12);
            float: left;
            margin:.4rem;
            padding-right:.6rem;
        }
        .marBot{
            margin-bottom:.8rem!important;
        }
    </style>

</head>
<html class="no-skin scroll">

<!-- 页面底部jsp -->
<%@ include file="../../system/index/foot.jsp"%>

<script src="<%=basePath%>static/js/myjs/head.js"></script>
<script>

    var basePath = '<%=basePath%>';
    $(function() {
        var data = {
            "content": [],
            "title": [],
            "auto": [],
            "description": [],
            "picture_id":[],
            "media_id":[],
            "url":[],
            "groupId":'',
            "groupSex":'',
            "groupProvince":''
        };
        var ind = 0;
        var pD=1;
        var Image='image1';
        var bc=0;
        var flag=0;
        var sendrecord_id = $("#SENDRECORD_ID").val();
        var I = I;
        var S=S;
        var P=P;
        loading("加载中...")
        //初始化数据
        init();
        function init(){
            var field = new Object();
            var initUrl = '';
            if("${pd.flag}" != "synthesizer" && sendrecord_id != ''){
                field.sendrecord_id = sendrecord_id;
                initUrl = 'sendRecord/loadRecord.do';
            }else if("${pd.flag}" == "synthesizer"){
                field.flag = "synthesizer";
                initUrl = 'sendRecord/loadFromArticle.do';
            }

            if(initUrl == ''){
                layer.closeAll();
                return false;
            }
            $.post(basePath + initUrl, field, function (res) {
                layer.closeAll();
                console.log(res);
                if(res.errcode == 0){
                    var mList = res.data.mList;
                    var pdRecord = res.data.pdRecord;
                    if(res.STATE_INFO=='已群发'){
                        bc=1;
                        $(".zhezhao").addClass("tianjia")
                    };

                    //将输入存入data变量中
                    for(var i=0;i<mList.length;i++){
                        data.content[i] = mList[i].CONTENT;
                        data.auto[i] = mList[i].CREATE_USER;
                        data.description[i] = mList[i].DIGEST;
                        data.media_id[i] = mList[i].THUMB_MEDIA_ID;
                        data.picture_id[i] = mList[i].PICTURE_ID;
                        data.title[i] = mList[i].TITLE;
                        data.url[i] = mList[i].URL;
                    }

                    if(pdRecord != null){
                        data.groupProvince = pdRecord.GROUP_PROVINCE != null ? pdRecord.GROUP_PROVINCE : "all";
                        data.groupSex = pdRecord.GROUP_SEX != null ? pdRecord.GROUP_SEX : "";
                        data.groupId = pdRecord.GROUP_ID != null ? pdRecord.GROUP_ID : "";
                        if (pdRecord.group_province) {
                            flag = 1;
                        }
                        if(pdRecord.STATE == 2){
                            //已群发，隐藏按钮
                            $(".footfixed").hide();
                        }
                    }


                    //初始化图文内容,群发设置
                    setTimeout(function(){
                        var ue = UE.getEditor("CONTENT");
                        ue.setContent(data.content[0]);
                        $('#TITLE').val(data.title[0]);
                        $('#AUTHOR').val(data.auto[0]);
                        $('#DESCRIPTION').val(data.description[0]);
                        $('#loadImg').attr("src",data.url[0]);

                        for(var i=1;i<res.data.mList.length;i++){
                            $('#lcont').append(' <li class="li"><div class="titles" style="width: 100%;"><input readonly="readonly" style="border: 0;width: 100%; background: rgba(1, 1, 1, .01);color:black" value=""></div> <img alt="" class="img"/><footer class="foot"  style="color:#fff"><div class="up">上移</div><div class="down">下移</div><div class="del">删除</div></footer></li>')
                        };
                        var li = document.querySelectorAll('.li')

                        var Num=li.length;
                        for(var i=0;i<Num;i++){
                            $(".titles").eq(i).find("input").val(data.title[i]);
                            $(".img").eq(i).attr("src",data.url[i]);
                        }

                        showGroupSet();



                    },500)

                }else{
                    layer.msg(res.errmsg);
                }
            })
        }

        //保存图文
        function saveNews(){
            var li = document.querySelectorAll('.li');
            pD=1;
            var textNumber=li.length;
            for(var i =0;i<li.length;i++){
                if(!data.content[i]){
                    pD=0;
                    var numb = i+1;
                    var num = '第'+numb+'篇内容不能为空';
                    layer.msg(num);
                };
                if(!data.title[i]){
                    pD=0;
                    var numb = i+1;
                    var num = '第'+numb+'篇标题不能为空';
                    layer.msg(num);
                };
                if(!data.auto[i]){
                    pD=0;
                    var numb = i+1;
                    var num = '第'+numb+'篇作者不能为空';
                    layer.msg(num);
                };
                if(!data.picture_id[i]){
                    pD=0;
                    var numb = i+1;
                    var num = '第'+numb+'篇封面不能为空';
                    layer.msg(num);
                }
            }
            if(pD){
                var title=[];
                var content=[];
                var description=[];
                var picture_id=[];
                var media_id=[];
                var url=[];
                var author=[];
                var num={};
                for(var i=0;i<textNumber;i++){
                    title[i] = 'title' + i;
                    content[i] = 'content' + i;
                    description[i] = 'description' + i;
                    picture_id[i] = 'picture_id' + i;
                    media_id[i] = 'media_id' + i;
                    url[i] = 'url' + i;
                    author[i] = 'author' + i;
                }
                for(var i=0;i<title.length;i++){
                    eval("num." + title[i] + "='" + data.title[i] + "'");

                    eval("num." + content[i] + "='" + data.content[i] + "'");

                    eval("num." + description[i] + "='" + data.description[i] + "'");

                    eval("num." + picture_id[i] + "='" + data.picture_id[i] + "'");

                    eval("num." + media_id[i] + "='" + data.media_id[i] + "'");

                    eval("num." + url[i] + "='" + data.url[i] + "'");

                    eval("num." + author[i] + "='" + data.auto[i] + "'");

                }
                num.sendrecord_id = sendrecord_id;
                num.total_num = data.content.length;
                num.group_id = data.groupId;
                num.group_sex = data.groupSex;
                num.group_province = data.groupProvince;


                console.log(num);
                $.post("<%=basePath%>sendRecord/saveRecord.do", num, function (res) {
                    // console.log(res);
                    layer.msg(res.errmsg);
                    $("#saveBtn").addClass("save");
                    if(res.errcode == 0){
                        sendrecord_id = res.data.sendrecord_id;
                        $("#SENDRECORD_ID").val(res.data.sendrecord_id);
                        bc=1;
                    }
                })
            }else{
                $("#saveBtn").addClass("save");
            }
        }
        $('body').on('click', '.save', function() {
            $("#saveBtn").removeClass("save");
            saveNews();
        });

        //预览图文
        $('body').on('click', '.preview333', function() {
            //先保存图文
            saveNews();
            $("#previewBtn").removeClass("preview333");
            setTimeout(function () {
                loading('加载中...');
                var field = new Object();
                field.sendrecord_id = sendrecord_id;
                field.curr_sequence = ind;
                $.post(basePath + 'sendRecord/getPreviewQr.do', field, function (res) {
                    $("#previewBtn").addClass("preview333");
                    console.log(res);
                    if(res.errcode == 0){
                        var url = res.data.url;
                        addcode(url);
                    }else{
                        layer.msg(res.errmsg);
                    }
                });
                layer.closeAll();
            }, 800);
        });
        //创建二维码
        function addcode(url){
            $("#qrcode").html('');
            var qrcode = new QRCode(document.getElementById("qrcode"), { // 创建一个二维码
                width : 210,//设置宽高
                height : 210,
                correctLevel: 1 //设置级别
            })
            qrcode.makeCode(url);

            $("#qrcode").show();
            layer.open({
                type: 1,
                shade: false,
                title: false, //不显示标题
                area: ['290px', '270px'], //宽高
                content: $("#qrcode"), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                cancel: function(){
                    $("#qrcode").hide();
                }
            });
        }

        //群发图文
        $('body').on('click', '.groupSend', function() {
            layer.confirm('确定要群发吗?<br>注意：每个月只能发四条!', {
                btn: ['确定','取消'],
            }, function(){
                //先保存图文
                saveNews();
                $("#sendBtn").removeClass("groupSend");
                setTimeout(function () {
                    loading('加载中...');
                    var field = new Object();
                    field.sendrecord_id = sendrecord_id;
                    $.post(basePath + 'sendRecord/groupSend.do', field, function (res) {
                        layer.msg(res.errmsg);
                        $("#sendBtn").addClass("groupSend");
                        if(res.errcode == 0){
                            //已群发，隐藏按钮
                            $(".footfixed").hide();
                        }
                    })
                }, 800);
                layer.closeAll();
            }, function(){
                return ;
            })
        });

        //打开上传图文页面
        $('body').on('click', '.modalesd', function() {
            layer.open({
                btn: ['确定', '关闭'],
                btn1: function (index, layero) {
                    //按钮的回调
                    var res = window["layui-layer-iframe" + index].getImg();
                    $("#loadImg").attr("src", res.imgPath);
                    var number = $("#SEQUENCE").val();
                    var imgId = "#" + Image;
                    data.media_id[ind] = res.media_id;
                    $(imgId).attr("src", res.imgPath);
                    data.picture_id[ind] = res.picture_id;
                    data.url[ind] = res.imgPath;

                    layer.close(index);
                },
                skin: '',
                btnAlign: 'c',
                type: 2,
                title: '上传图片',
                shadeClose: false,
                shade: 0.8,
                area: ['600px', '410px'],
                content: ['<%=basePath%>sendRecord/goUploadImg.do'] //iframe的url

            });
        });

        //打开群发设置页面
        $('body').on('click', '.modelteep', function() {
            layer.open({
                btn: ['确定', '关闭'],
                btn1: function (index, layero) {
                    //按钮的回调
                    var res = window["layui-layer-iframe" + index].setParam();
                    var res=res.url.split("?")[1].split("&");

                    res.splice(res.length-1,1);
                    var num=[];
                    for( var i=0;i<res.length;i++){
                        num[i]=res[i].split("=");
                    }
                    data.groupId = num[0][1];
                    data.groupSex = num[1][1];
                    data.groupProvince = num[2][1];
                    $(".Bq").css("display",'table');
                    flag=1;

                    showGroupSet();
                    layer.closeAll();
                },
                skin: '',
                btnAlign: 'c',
                type: 2,
                title: '群发设置',
                shadeClose: false,
                shade: 0.8,
                area: ['500px', '400px'],
                content: ['<%=basePath%>sendRecord/goGroupSet.do'] //iframe的url

            });
        });
        function showGroupSet(){
            $(".showQf").css("display","block");
            if(data.groupId==0){
                $('#group1').val("全部");
            }else if(data.groupId==1){
                $('#group1').val("会员");
            }else{
                $('#group1').val("非会员");
            };
            if(data.groupSex==0){
                $('#group2').val("全部");
            }else if(data.groupSex==1){
                $('#group2').val("男");
            }else{
                $('#group2').val("女");
            };
            if(data.groupProvince=="all"){
                $('#group3').val("全部")
            }else{
                $('#group3').val(data.groupProvince);
            }
        }

        //删除群发设置的标签
        $(".dels").click(function(){
            var i = $(this).parent().index();
            $('.Bq').eq(i).css('display','none');
            if(i=='0'){
                data.groupId = 0
                I=0;
            }else if(i=='1'){
                data.groupSex = 0
                S=0;
            }else{
                data.groupProvince = 'all';
                P=0;
            }
            console.log(data);
        })





        //鼠标进入
        $('#left').on('mouseenter', "#lcont .li", function() {
            $(this).find('footer div').css('display', 'block');
            $('#lcont .li').css({'border': '0'});
            $('#lcont .li').css('border-bottom', '1px solid rgba(1, 1, 1, .2)');
            $(this).css({'border':'1px solid blue','box-sizing':'border-box'});
            $(this).find('footer').css('top', '40px');
            if ($(this).index() == 0) {
                $(this).find('footer .up').css('display', 'none');
                $(this).find('footer').css('top', '70px');
            } else if ($(this).index() == $('.li').length - 1) {
                $(this).find('footer .down').css('display', 'none');
                $(this).find('footer').css('top', '40px');
            }
        });

        //鼠标离开
        $('#left').on('mouseleave', "#lcont .li", function() {
            $(this).find('footer').css('top', '100px');
            $('#lcont .li').css({'border': '0',});
            $('#lcont .li').css('border-bottom', '1px solid rgba(1, 1, 1, .2)')
            $(this).find('footer div').css('display', 'none');

            $('#lcont .li').eq(ind).css({'border':'1px solid blue'})
        });

        //点击
        $('#left').on('click', "#lcont .li", function() {
            ind = $(this).index();
            $('#TITLE').val(data.title[ind]);
            $('#AUTHOR').val(data.auto[ind]);
            $('#DESCRIPTION').val(data.description[ind]);
            if(data.picture_id[ind]){
                $('#loadImg').attr("src",data.url[ind]);
                $(this).find('img').attr("src",data.url[ind]);
            }else{
                $('#loadImg').attr("src","<%=basePath%>newStyle/images/normalImg1.png");
                $(this).find('img').attr("src","<%=basePath%>newStyle/images/normalImg1.png");
            };
            var inds=ind+1;

            Image = "image"+inds;
            $(this).find('img').attr("id",Image);

            $('#lcont .li').css({'border': '0'});
            $('#lcont .li').css('border-bottom', '1px solid rgba(1, 1, 1, .2)')
            $(this).css({'border':'1px solid blue'});
            if (data.content[ind] + '' == 'undefined') {
                var ue = UE.getEditor("CONTENT");
                ue.setContent('');
            } else {
                var ue = UE.getEditor("CONTENT");
                ue.setContent(data.content[ind]);
            }
        });

        //最多图文
        $('body').on('click', '.zj', function() {
            if ($('.li').length < 8) {
                $('#lcont').append(' <li class="li"> <div class="titles" style="width: 100%;"><input readonly="readonly" style="border: 0;width: 100%; background: rgba(1, 1, 1, .01);color:black" value=""></div><img alt="" class="img"/><footer class="foot"  style="color:#fff"><div class="up">上移</div><div class="down">下移</div><div class="del">删除</div></footer></li>')
            }else{
                layer.msg("最多添加8篇图文")
            }
        });

        //上移
        $('#left').on('click', ".up", function() {
            setTimeout(function(){
                // console.log('上移')
                var up = $(this).parent().parent()
                if (up.index() != 0) {
                    up.fadeOut().fadeIn();
                    up.prev().before(up);
                    // console.log(ind)
                    var num1 = data.content[ind - 1];
                    data.content[ind - 1] = data.content[ind];
                    data.content[ind] = num1;
                    var num2 = data.title[ind - 1];
                    data.title[ind - 1] = data.title[ind];
                    data.title[ind] = num2;
                    var num3 = data.auto[ind - 1];
                    data.auto[ind - 1] = data.auto[ind];
                    data.auto[ind] = num3;
                    var num4 = data.description[ind - 1];
                    data.description[ind - 1] = data.description[ind];
                    data.description[ind] = num4;
                    var num5 = data.picture_id[ind-1];
                    data.picture_id[ind-1]=data.picture_id[ind];
                    data.picture_id[ind]=num5;
                    var num6 = data.media_id[ind-1];
                    data.media_id[ind-1]=data.media_id[ind];
                    data.media_id[ind]=num6;
                    var num6 = data.url[ind-1];
                    data.url[ind-1]=data.url[ind];
                    data.url[ind]=num6;

                    var li = document.querySelectorAll('.li')

                    var Num=li.length;
                    for(var i=0;i<Num;i++){
                        $(".titles").eq(i).find("input").val(data.title[i]);
                        $(".img").eq(i).attr("src",data.url[i]);
                    }
                }
            },500)






        });

        //下移
        $('#left').on('click', ".down", function() {

            setTimeout(function(){
                // console.log('下移')
                var $down = $(".down");
                var len = $down.length;
                var down = $(this).parent().parent()
                if (down.index() != len - 1) {
                    down.fadeOut().fadeIn();
                    down.next().after(down);
                    var num1 = data.content[ind + 1];
                    data.content[ind + 1] = data.content[ind];
                    data.content[ind] = num1
                    var num2 = data.title[ind + 1];
                    data.title[ind + 1] = data.title[ind];
                    data.title[ind] = num2;
                    var num3 = data.auto[ind + 1];
                    data.auto[ind + 1] = data.auto[ind];
                    data.auto[ind] = num3;
                    var num4 = data.description[ind + 1];
                    data.description[ind + 1] = data.description[ind];
                    data.description[ind] = num4;
                    var num5 = data.picture_id[ind+1];
                    data.picture_id[ind+1]=data.picture_id[ind];
                    data.picture_id[ind]=num5;
                    var num6 = data.media_id[ind+1];
                    data.media_id[ind+1]=data.media_id[ind];
                    data.media_id[ind]=num6;
                    var num6 = data.url[ind+1];
                    data.url[ind+1]=data.url[ind];
                    data.url[ind]=num6;
                    var li = document.querySelectorAll('.li')

                    var Num=li.length;
                    for(var i=0;i<Num;i++){
                        $(".titles").eq(i).find("input").val(data.title[i]);
                        $(".img").eq(i).attr("src",data.url[i]);
                    }
                }
            },500)
        });

        //删除图文
        $('#left').on('click', ".del", function() {

              setTimeout(function(){
                  $('#lcont .li').eq(ind).remove();
                  // // ind = $(this).parent().parent().index();
                  // console.log(ind);
                  data.content.splice(ind, 1);
                  data.title.splice(ind, 1);
                  data.auto.splice(ind, 1);
                  data.description.splice(ind, 1);
                  data.picture_id.splice(ind,1);
                  data.media_id.splice(ind,1);
                  data.url.splice(ind,1);
                  $('#TITLE').val(data.title[ind]);
                  $('#AUTHOR').val(data.auto[ind]);
                  $('#DESCRIPTION').val(data.description[ind]);
                  if(data.picture_id[ind]){
                      $('#loadImg').attr("src",data.url[ind]);
                      $(this).find('img').attr("src",data.url[ind]);
                  }else{
                      $('#loadImg').attr("src","<%=basePath%>newStyle/images/normalImg1.png");
                      $(this).find('img').attr("src","<%=basePath%>newStyle/images/normalImg1.png");
                  };
                  if (data.content[ind] + '' == 'undefined') {
                      var ue = UE.getEditor("CONTENT");
                      ue.setContent('');
                  } else {
                      var ue = UE.getEditor("CONTENT");
                      ue.setContent(data.content[ind]);
                  }
                  var Num=li.length;
                  for(var i=0;i<Num;i++){
                      $(".titles").eq(i).find("input").val(data.title[i]);
                      $(".img").eq(i).attr("src",data.url[i]);
                  }
              },500)



        });

        $('body').on('click', ".zhezhao", function() {//点击
            layer.msg("你已群发，不能继续操作");
        });

        UE.getEditor("CONTENT").addListener('contentChange', function(editor) {
            var ue = UE.getEditor("CONTENT");
            var oldContent = ue.getContent();
            if (oldContent) {
                data.content[ind] = oldContent;
            }
        });



        $('body').on('keyup', '.cunru', function() {
            data.title[ind] = $('#TITLE').val();
            data.auto[ind] = $('#AUTHOR').val();
            data.description[ind] = $('#DESCRIPTION').val();

            $(".titles").eq(ind).find("input").val($('#TITLE').val());
        });

    });
</script>


<body>
<div>
    <!-- <h1>demo</h1> -->
    <!-- 指定编辑器区域的宽度，像素或者100% -->
    <div class="card-header">
        <div class="card-header-msg">
            <img src="<%=basePath%>newStyle/images/logo2.png"class="card-lankeIcon">
            <span class="card-header-title">网吧管理后台</span>
            <div class="card-header-right">
                <img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
                <span class="card-userName layui-elip"> <small>Welcome</small><br>
					${pd.NAME}
			</span>
            </div>
        </div>
    </div>
    <div class="zhezhao">

    </div>
    <div id="flex">
        <div id="left">
            <header>图文列表</header>
            <div class="section">
            <section id="lcont">
                <li class="li one">
                    <div class="titles" style="width: 100%;"><input readonly="readonly" style="border: 0;width: 100%; background: rgba(1, 1, 1, .01);color:black" value=""></div>
                    <img alt="" id="image1" class="img" src="<%=basePath%>newStyle/images/normalImg1.png"/>
                    <footer class="foot" style="color:#fff">
                        <div class="up">上移</div>
                        <div class="down">下移</div>
                        <div class="del">删除</div>
                    </footer>
                </li>
            </section>
            </div>
            <footer class="zj new">
                <div class="borshu"></div>
                <div class="borheng"></div>
            </footer>
        </div>
        <form name="preview" id="preview" method="post" enctype="multipart/form-data" >
            <div class="zenxuid">
                <div class="edit scroll" id="maxWin1">
                    <!-- <script id="editor" type="text/plain" style="width:1024px;"></script> -->
                    <!-- <%--<span id="styleName" onclick="getStyle()">展开样式</span>--%> -->
                    <textarea name="CONTENT" id="CONTENT" maxlength="255" placeholder="这里输入图文内容" title="图文内容" style="width:auto; height: 600px; margin: 0 auto;"></textarea>
                </div>
            </div>
            <div class="left" style="overflow: hidden;">
                <div class="outside maxWin2 scroll" style="overflow-x: hidden;overflow-y:auto;right:-70px">
                    <a>
                        <p class="outp" onclick="siMenu('z240','lm27','揽客文章库','articleLib/list.do')">去选择揽客文章库</p>
                    </a>
                    <p class="widmar hegi" style="color:#000;">发布设置</p>
                    <p class="widmar hegi">封面<span style="color:#999;font-size: 12px">（建议尺寸：900像素*500像素）</span></p>
                    <p class="widmar imgheg"><img src="<%=basePath%>newStyle/images/normalImg1.png" alt="" width="90px" id="loadImg"></p>

                    <p class="widmar btnPt"><a class="phonot modalesd">上传图片</a></p>
                    <input type="hidden" id="SENDRECORD_ID" name="SENDRECORD_ID" value="${pd.sendRecordId}">

                    <p class="widmar heighted marTop">文章标题</p>
                    <p class="widmar heighted"><input type="text" id="TITLE" name="TITLE" class="inputHe cunru title" ></p>
                    <p class="widmar heighted">作者</p>
                    <p class="widmar heighted"><input type="text" id="AUTHOR" name="AUTHOR" class="inputHe cunru author" ></p>
                    <p class="widmar heighted">摘要<span style="color:#999;font-size: 12px">（选填）</span></p>
                    <p class="widmar"><textarea id="DESCRIPTION" name="DESCRIPTION" class="inputHe textrea cunru"></textarea></p>
                    <input type="hidden" id="SOURCE_URL" name="SOURCE_URL" >
                    <p class="widmar btnPt marTop marBot" style="margin-top: 25px!important"><a class="phonot modelteep" style="margin-bottom:.2rem!important;">群发设置</a></p>
<%--                    111<input type="text" value= "0" id="group">--%>
                    <div class="showQf Qf" style="padding-left:0.8rem;overflow: hidden;margin-top: -25px">
                        <div class="Bq">
                            <input class="bq" id="group1" readonly><%--<i class="layui-icon layui-icon-close dels"></i>--%>
                        </div>
                        <div class="Bq">
                            <input class="bq" id="group2" readonly><%--<i class="layui-icon layui-icon-close dels"></i>--%>
                        </div>
                        <div class="Bq">
                            <input class="bq" id="group3" readonly><%--<i class="layui-icon layui-icon-close dels"></i>--%>
                        </div>
                    </div>

                </div>
            </div>
            <input type="hidden" id="send_sequence" name="send_sequence">
        </form>

    </div>
    <div class="footfixed">
        <div class="footWid">
            <p class="footBtn save" id="saveBtn"><span class="btn-success">保存</span></p>
            <p class="footBtn preview333" id="previewBtn"><span class="btn-primary">预览</span></p>
            <p class="maright groupSend" id="sendBtn"><span class="btn-warning">群发</span></p>
        </div>
    </div>
</div>
<div id="qrcode" style="width: 210px;margin: 30px auto;display:none">

</div>
<script>
    var appkey = '566d34e0-4bac-443b-8a80-37f89fde689a';
    /**
     *
     * 1. 必需初始化至 window.current_editor,一个页面仅支持加载一个带样式的编辑器
     * 2. 直接调用JS，不要使用vue等import的方式
     * 3. 在ueditor.config.js文件中，serverUrl为上传文件接口地址，
     *    根据服务器语言选择对应的接口程序。
     * 4. 在ueditor.config.js文件中，toolbars为编辑器的工具栏按钮项，
     *    可根据需要自行删减
     */
    window.BASEURL = 'https://www.135editor.com';
    window.current_editor = UE.getEditor('CONTENT', {
        initialFrameHeight: 600,
        plat_host: 'www.135editor.com',
        appkey: appkey,
        style_url: BASEURL + '/editor_styles/open?inajax=1&v=page&appkey=' + appkey,
        page_url: BASEURL + '/editor_styles/open_styles?inajax=1&appkey=' + appkey,
        style_width: 340,
        zIndex: 1000,
        pageLoad: true,
        open_editor: true,
        focus: true,
        focusInEnd: true
    });
</script>

</body>

</html>