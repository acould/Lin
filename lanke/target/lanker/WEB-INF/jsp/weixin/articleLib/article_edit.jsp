<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%--<%@ include file="../../system/index/top.jsp"%>--%>
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
<link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">

<link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/default/_css/ueditor.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/96619a5672.css" />
<link rel="stylesheet" href="<%=basePath%>newStyle/css/newtextimg.css">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/style.css">
<head>
    <title>${operation}文章</title>
</head>
<style type="text/css">
    .modal-backdrop.in{
        z-index: 0!important;
        opacity:0.3;
    }
    .edui-default .edui-editor-iframeholder {
        height: calc(100vh - 180px)!important;
        overflow-y:auto!important;
    }
    #edui_fixedlayer {z-index: 1000!important}
    #edui12_mainbar{margin-left:360px!important;margin-right: 0px!important;}
    #edui12_sidebar{float: inherit!important;}
    #color-plan {left: auto!important;top:160px!important;}
</style>
<body style="background-color: #f2f2f2;">
    <div class="card-header">
        <div class="card-header-msg">
            <img src="<%=basePath%>newStyle/images/logo2.png"class="card-lankeIcon">
            <span class="card-header-title">网吧管理后台</span>
            <div class="card-header-right">
                <img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
                <span class="card-userName layui-elip"> <small>Welcome</small><br>
                        ${username}
                </span>
            </div>
        </div>
    </div>
    <div class="lk-articleAdd-box">
        <form action="sendRecord/preview.do" name="preview" id="preview" method="post" enctype="multipart/form-data" target="_blank" class="layui-form">
            <div class="edit scroll" id="maxWin1">
                <textarea  name="CONTENT" id="content_write" maxlength="255" placeholder="这里输入图文内容"
                           title="图文内容"  style="width: 870px; height: 400px; margin: 0 auto;" >${pd.content}</textarea>
            </div>
            <div class="article_setBox">
                <p class="lk-card-header">文章设置</p>
                <div class="lk-card-content">
                    <textarea type="text" class="lk-article-title" placeholder="请输入文章标题" id="title-write" name="title-write">${pd.title}</textarea>
                    <div class="lk-picture-box" id="picture_img"
                            <c:if test="${pd.picture_url != null}">style="background:url(${pd.picture_url}) no-repeat center center;"</c:if> ></div>
                    <p class="lk-tip-text">封面尺寸：900*500</p>
                    <input type="file" style="display: none" id="file" accept="image/*">
                    <span class="lk-picture-btn" id="picture-btn">上传</span>
                    <div style="margin-top: 38px">
                        <p class="lk-title-text">文章作者：</p>
                        <input type="text" value="揽客" readonly class="lk-article-input" id="author-write">
                        <p class="lk-title-text">文章摘要：（选填）</p>
                        <textarea type="text" class="lk-article-digest" placeholder="请输入文章摘要" id="digest_write" name="digest">${pd.digest}</textarea>
                        <p class="lk-title-text">文章类型：</p>
                        <div>
                            <select name="interest" lay-filter="category">
                                <option value=""></option>
                                <c:forEach items="${categoryList}" var="var" varStatus="vs">
                                    <option value="${var.id}" <c:if test="${pd.category_id == var.id}">selected</c:if> >${var.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <p class="lk-title-text">文章推荐度：</p>
                        </div><div id="rate" name="${pd.popularity}"></div></div>
                    </div>
                </div>
            </div>
        </form>
        <div class="footfixed">
            <div class="footWid">
                <p class="footBtn " onclick="new_articleAdd(2)"><span class="btn-success">保存</span></p>
                <p class="footBtn " onclick="preview333()"><span class="btn-primary">预览</span></p>
                <p class="maright " onclick="new_articleAdd(1)"><span class="btn-warning">发布</span></p>
            </div>
        </div>
        <form action="" id="article_form">
            <input type="hidden" value="${pd.title}" id="title" name="title"><%--文章标题--%>
            <input type="hidden" value="${pd.picture_id}" id="picture_id" name="picture_id"><%--封面图片--%>
            <input type="hidden" value="${pd.picture_url}" id="picture_url" name="picture_url"><%--封面图片--%>

            <input type="hidden" value="${pd.author}" id="author" name="author"><%--文章作者--%>
            <input type="hidden" value="${pd.digest}" id="digest" name="digest"><%--文章摘要--%>
            <input type="hidden" value="${pd.category_id}" id="category_id" name="category_id"><%--文章类型--%>
            <input type="hidden" value="${pd.popularity}" id="popularity" name="popularity"><%--文章推荐度--%>
            <input type="hidden" value="" id="content" name="content"><%--文章内容--%>
            <input type="hidden" value="${pd.status}" id="status" name="status"><%--文章状态--%>
            <input type="hidden" value="${pd.id}" id="id" name="id"><%--文章id--%>
        </form>
    </div>
</body>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/third-party/jquery-1.10.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/a92d301d77.js"> </script>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'article']);
</script>
<script type="text/javascript">
    //赋初始值，提供给a92d301d77.js使用
    var BASEURL = 'https://www.135editor.com';
    var appkey = '59e710b8-db88-48f0-bb92-4f4a79291994';
    var domain_135 = BASEURL;

    //赋值给current_editor，提供给a92d301d77.js使用
    var current_editor = UE.getEditor('content_write',{
        initialFrameHeight:600,

        appkey		:appkey,
        plat_host   :'www.135editor.com',
        style_url 	: BASEURL+'/editor_styles/open?inajax=1&v=page&appkey='+appkey,
        page_url 	: BASEURL+'/editor_styles/open_styles?inajax=1&appkey='+appkey,
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
</html>
