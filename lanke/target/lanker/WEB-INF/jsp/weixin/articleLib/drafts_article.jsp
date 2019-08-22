<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/textimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
</head>
<style>
    .navlist {
        position: relative;
    }
    .navlist p {
        line-height: 60px;
        color: #666;
        font-size: 14px;
        padding: 0 20px;
    }
    .come_back {
        color: #fff;
        background: #37a8ff;
        padding: 5px 20px;
        border-radius: 2px;
        cursor: pointer;
        position: absolute;
        right: 20px;
        font-size: 14px;
        top: 15px;
    }
</style>
<body>
    <div class="bodyBox" style="margin-left:0;">
        <div class="navlist">
            <p>揽客文章库/草稿箱</p>
            <div class="come_back" onclick="comeBack()">返回</div>
        </div>
        <form action="" method="post" name="Form" id="Form">
            <div id="main" style="padding: 20px">

            </div>
        </form>
    </div>
</body>
<%@ include file="../../system/index/foot.jsp"%>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>

<script>
    loadDraftsArticle();
    function loadDraftsArticle() {
        $.ajax({
            type	: "POST",
            url		:'<%=basePath%>articleLib/loadDraftsArticle.do',
            data	:{status:"2"},
            dataType:'json',
            beforeSend	: beforeSend(''),
            success: function(data){
                layer.closeAll();
                if(data.result == "true"){
                    var articleList = data.articleList;

                    var html = '';
                    for (var i = 0; i < articleList.length; i++) {
                        var pd = articleList[i];
                        html += '<div class="pin">'
                                    +'<div class="box">'
                                        +'<div class="rowBoxCenter">'
                                            +'<p class="text-p"><span class="font-12 padding-30">保存于: '+pd.update_time+'</span><span class="font-12 padding-80">未上传</span></p>'
                                            +'<div class="widthImg">'
                                                +'<img src="'+pd.picture_url+'" alt="">'
                                                +'<div class="wordTitle"><p>'+pd.title+'</p></div>'
                                            +'</div>'
                                            +'<div class="tarWid">'
                                                +'<div class="row">'
                                                    +'<div class="col-md-6 col-xs-6" onclick=edit("'+pd.id+'","edit")>'
                                                        +'<a class="tarHover"><img src="<%=basePath%>newStyle/images/bianji.png" alt="" width="15px"><span  class="tarStyle">编辑</span></a>'
                                                    +'</div>'
                                                    +'<div class="col-md-6 col-xs-6 text-right" onclick=del("'+pd.id+'")>'
                                                        +'<a class="tarHover"><img src="<%=basePath%>newStyle/images/shanchu.png" alt="" width="15px"><span  class="tarStyle">删除</span></a>'
                                                    +'</div>'
                                                +'</div>'
                                            +'</div>'
                                        +'</div>'
                                    +'</div>'
                                +'</div>';

                    }
                    if(articleList.length == 0){
                        html = '<div style="width:100%;text-align: center;margin-top:100px;">暂无数据</div>';
                    }
                    $("#main").html(html);
                }else{
                    message(data.message);
                }
            },
            error:function(){
                layer.closeAll();
                layer.msg("系统繁忙，请稍后再试");
            }
        });
    }
    function comeBack(id,operation){
        window.location.href = '<%=basePath%>articleLib/list.do';
    }
    function edit(id,operation){
        window.open('<%=basePath%>articleLib/goEditArticle.do?id='+id+'&operation='+operation)
    }
    function del(id){
        layer.confirm('确定要删除？', {
            btn: ['确定','取消']
        }, function(){
            $.ajax({
                type	: "POST",
                url		:'<%=basePath%>articleLib/deleteArticle.do',
                data	:{id:id},
                dataType:'json',
                beforeSend	: beforeSend(''),
                success: function(data){
                    layer.closeAll();
                    message(data.message);
                    if(data.result == "true"){
                        setTimeout(function(){
                            location.reload();
                        },800)
                    }
                },
                error:function(){
                    layer.closeAll();
                    message("系统繁忙，请稍后再试");
                }
            });

        })

    }
</script>
</html>
