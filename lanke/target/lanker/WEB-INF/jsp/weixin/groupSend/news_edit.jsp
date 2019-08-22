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
<html>
<head>
    <title>编辑图文</title>
    <base href="<%=basePath%>">
    <%@ include file="../../system/index/top.jsp"%>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>135Editor/themes/default/_css/ueditor.css" />
    <link rel="stylesheet" href="<%=basePath%>135Editor/themes/96619a5672.css" />
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/newtextimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/style.css">
    <style type="text/css">
        .modal-backdrop.in{
            z-index: 0!important;
            opacity:0.3;
        }
        .edui-default .edui-editor-iframeholder {
            height: calc(100vh - 64px)!important;
            overflow-y:auto!important;
        }
        #edui_fixedlayer {z-index: 1000!important}
        .div-flag:hover,.div-active {
            border:1px solid #2f8ceb;
        }
    </style>
</head>
<body>

<div class="bodyBox">
    <%-- <div class="navlist">
        <div class="row">
            <div class="col-md-3 lineHieght">
                <c:if test="${title == 'add'}"><p>新建图文 / <span>编辑</span></p></c:if>
                <c:if test="${title == 'edit'}"><p>修改图文 / <span>编辑</span></p></c:if>
            </div>
            <div class="col-md-2 col-md-offset-6 text-center" style="line-height: 60px;">
                <img src="${org.PATH }" alt="" width="35" style="padding-right:10px">${org.INTENET_NAME}
            </div>
        </div>
    </div> --%>
    <div class="height1">
        <div class="row rowes">
            <div class="col-md-3 maxWin">
                <div class="rowBoxCenter scroll" style="overflow: auto;">
                    <p class="font-14">图文列表</p>
                    <input type="hidden" id="sendRecordId" value="${sendRecordId}">
                    <div class="widthImg div-active div-flag" id="div0">
                        <img src="<%=basePath%>newStyle/images/normalImg1.png" alt="" class="div-img">
                        <div class="wordTitle">
                            <p class="tarTileWid">未命名</p>
                            <div class="hoverTar">
                                <div class="row" style="margin: 0;">
                                    <%--<div class="col-md-4 text-center"><p class="div-down" style="line-height: 40px;color:#fff;padding: 0">下移</p></div>--%>
                                    <div class="col-md-4 text-right"><p class="div-del" style="line-height: 40px;color:#fff;padding: 0">删除</p></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%--<div class="borBottom" id="div${vs.index }">
                        <table width="100%" class="tabHover">
                            <tbody>
                            <tr>
                                <td style="vertical-align: top"><p class="tarTileWid" id="title${vs.index }">${var.TITLE }</p></td>
                                <td style="vertical-align: top;text-align: right"><div class="tarImgWid"><!--此处添加img--><img src="<%=basePath %>uploadFiles/uploadImgs/${var.PATH}" alt="" id="image${vs.index }"></div></td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="wordTitle back">
                            <div class="hoverTar">
                                <div class="row" style="margin: 0;">
                                    <div class="col-md-4 text-left"><p style="line-height: 40px;color:#fff;padding: 0" onclick="mouseUp('${vs.index}')" id="up${vs.index }">上移</p></div>
                                    <div class="col-md-4 text-center"><p style="line-height: 40px;color:#fff;padding: 0" onclick="mouseDown('${vs.index}')" id="down${vs.index }">下移</p></div>
                                    <div class="col-md-4 text-right" ><p style="line-height: 40px;color:#fff;padding: 0" onclick="deleteNews('${vs.index}')" id="delete${vs.index }">删除</p></div>
                                </div>
                            </div>
                        </div>
                    </div>--%>

                    <div class="new" id="addDiv">
                        <div class="borshu"></div>
                        <div class="borheng"></div>
                    </div>
                </div>
            </div>

            <form method="post" enctype="multipart/form-data">
                <div class="col-md-6 zenxuid">
                    <div class="edit scroll" id="maxWin1">
                        <%--<span id="styleName" onclick="getStyle()">展开样式</span>--%>
                        <textarea id="show_content" maxlength="255" placeholder="这里输入图文内容"
                                   title="图文内容"  style="width: 870px; height: 400px; margin: 0 auto;" ></textarea>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="outside maxWin2 scroll" style="overflow-x: hidden;overflow-y:auto;">
                        <p class="widmar hegi" style="color:#000;">发布设置</p>
                        <p class="widmar hegi">封面<span style="color:#999;font-size: 12px">（建议尺寸：900像素*500像素）</span></p>
                        <p class="widmar imgheg"><img src="<%=basePath%>newStyle/images/normalImg1.png" alt="" width="90px" id="show_img"></p>

                        <input type="file" style="display: none" id="upFlie">
                        <p class="widmar btnPt" id="clickImg"><a class="phonot">上传图片</a></p>

                        <p class="widmar heighted marTop">文章标题</p>
                        <p class="widmar heighted"><input type="text" id="show_title" class="inputHe" onkeyup="rightToActive('')" maxlength="20"></p>
                        <p class="widmar heighted">作者</p>
                        <p class="widmar heighted"><input type="text" id="show_author" class="inputHe" maxlength="10"></p>
                        <p class="widmar heighted">摘要<span style="color:#999;font-size: 12px">（选填）</span></p>
                        <p class="widmar"><textarea id="show_digest" class="inputHe textrea" maxlength="100"></textarea></p>
                        <input type="hidden" id="show_source_url">
                    </div>
                </div>
                <input type="hidden" id="send_sequence" name="send_sequence">
            </form>

            <form id="Form" method="post" enctype="multipart/form-data">
                <input type="hidden" id="INTERNET_ID" name="INTERNET_ID" value="${org.INTENET_ID}">
                <input type="hidden" id="SENDRECORD_ID" name="SENDRECORD_ID" value="${org.SENDRECORD_ID}">
                <input type="hidden" id="INTERNETMATERIAL_ID" name="INTERNETMATERIAL_ID" value="${pd.INTERNETMATERIAL_ID }">
                <input type="hidden" id="SEQUENCE" name="SEQUENCE" value="0">
                <input type="hidden" id="PICTURE_ID_HIDDEN" name="PICTURE_ID_HIDDEN"  value="${var.PICTURE}">
            </form>
        </div>
    </div>
    <c:if test="${flag != 'record'}">
        <div class="footfixed" id="footfixed">
            <div class="footWid">
                <p class="footBtn " onclick="saveNews()"><span class="btn-success">保存</span></p>
                <p class="footBtn " onclick="preview()"><span class="btn-primary">预览</span></p>
                <p class="maright " onclick="groupSend()"><span class="btn-warning">群发</span></p>
            </div>
        </div>
    </c:if>

    <img src="" style="display: none;" id="preview_pic">
</div>

    <script src="<%=basePath%>135Editor/ueditor.config.js"></script>
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>135Editor/ueditor.all.min.js"></script>
    <script src="<%=basePath%>135Editor/lang/zh-cn/zh-cn.js"></script>
    <script src="<%=basePath%>135Editor/a92d301d77.js"> </script>

    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-message.js"></script>

<script>
    var basePath = '<%=basePath%>';
    var id = $("#sendRecordId").val();
    //赋初始值，提供给a92d301d77.js使用
    var BASEURL = 'https://www.135editor.com';
    var appkey = '59e710b8-db88-48f0-bb92-4f4a79291994';
    var domain_135 = BASEURL;
    //赋值给current_editor，提供给a92d301d77.js使用
    var current_editor = UE.getEditor('show_content',{
        initialFrameHeight:600,
        appkey		:appkey,
        plat_host   :'www.135editor.com',
        style_url 	: domain_135+'/editor_styles/open?inajax=1&v=page&appkey='+appkey,
        page_url 	: domain_135+'/editor_styles/open_styles?inajax=1&appkey='+appkey,
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
<script src="<%=basePath%>newStyle/layuiadmin/modules/groupSend.js"></script>
<script>
    current_editor.ready(function () {
        initNews(id);
    })
</script>

</body>
</html>
