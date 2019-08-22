<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
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
    <title>图文群发</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="icon" href="data:;base64,=">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">

    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/default/_css/ueditor.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>135Editor/themes/96619a5672.css" />
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/newtextimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/style.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">

    <style type="text/css">
        .modal-backdrop.in{z-index: 0!important;opacity:0.3;}
        body {overflow-x:auto!important;}
        .edui-default .edui-editor-iframeholder {height: calc(100vh - 180px)!important;overflow-y:auto!important;}
        #edui_fixedlayer {z-index: 1000!important}
        #edui12_sidebar{float: inherit!important;}
        #color-plan {left: auto!important;top:160px!important;}
        .layui-form-checkbox span {height: auto!important;}
        .footBtn span,.maright span{ padding: 0;width: 90px;display: inline-block;height: 40px;line-height: 40px;text-align: center;margin-bottom: 16px;}
        .lk-title-text {margin-left: 0!important;}
        .lk-articleAdd-box .edit {width: 500px!important;}
        .article_setBox {float: none}
    </style>

</head>
<body style="background-color: #f2f2f2;">

<div class="card-header">
    <div class="card-header-msg">
        <img src="<%=basePath%>newStyle/images/logo2.png"class="card-lankeIcon">
        <span class="card-header-title">网吧管理后台</span>
        <div class="card-header-right">
            <img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
            <span class="card-userName layui-elip"> <small>Welcome</small><br>
                ${pd.username}
             </span>
        </div>
    </div>
</div>

<div class="lk-articleAdd-box" style="width: 1020px">
    <form class="layui-form" id="editForm" lay-filter="editForm">
        <input id="id" name="id" type="hidden" value="${pd.id}">
        <div class="edit scroll" id="maxWin1">
					<textarea  name="content" id="content" maxlength="255" placeholder="这里输入图文内容"
                               title="图文内容"  style="width: 870px; height: 400px; margin: 0 auto;" lay-verify="required"></textarea>
        </div>
        <div class="article_setBox" style="width: 500px">
            <p class="lk-card-header">图文设置</p>
            <div class="lk-card-content" style="position: relative">
                <textarea type="text" class="lk-article-title" placeholder="请输入图文标题" id="name" name="name"  lay-verify="required"></textarea>

                <div class="lk-picture-box" id="picture_img" style="width: 280px"></div>
                <input id="cover" name="cover" type="hidden">
                <p class="lk-tip-text">推文封面尺寸：900*500</p>
                <input type="file" style="display: none" id="file" accept="image/*">
                <span class="lk-picture-btn" id="picture-btn">上传</span>

                <p class="lk-title-text">作者：</p>
                <input type="text"  class="layui-input"  name="author" lay-verify="required">

                <p class="lk-title-text">阅读原文链接：</p>
                <input type="text"  class="layui-input" readonly  name="author" lay-verify="required">
                <p class="lk-title-text">摘要：</p>
                <textarea placeholder="选填图文摘要" maxlength="" class="layui-textarea" id="description" name="description"></textarea>

                <p class="lk-title-text">可发送门店：</p>
                <div class="lk-title-text" id="store_item">
                    <span class="store_name_style layui-btn layui-btn-sm layui-btn-primary">XXXXX门店<i class="layui-icon" onclick=delStore(this,"store'+i+'")>&#x1007;</i></span>
                    <span class="store_name_style layui-btn layui-btn-sm layui-btn-primary">XXXXX门店<i class="layui-icon" onclick=delStore(this,"store'+i+'")>&#x1007;</i></span>
                </div>

                <div class="layui-form-item">
                    <span class="layui-btn layui-btn-sm layui-btn-primary" onclick="chooseObj()"><i class="layui-icon">&#xe654;</i>群发会员设置</span>
                    <div class="lk-title-text" id="msgMember_item">

                    </div>
                </div>


            </div>
            <div class="footfixed">
                <div class="footWid">
                    <p class="footBtn " ><span class="btn-success" lay-submit lay-filter="save" data-type="save" id="save">保存</span></p>
                    <p class="footBtn " ><span class="btn-primary" lay-submit lay-filter="preview" data-type="preview" id="preview">预览</span></p>
                    <p class="maright " ><span class="btn-warning" lay-submit lay-filter="issue" data-type="issue" id="issue">发布</span></p>
                </div>
            </div>

            <div id="qrcode" style="width: 210px;margin: 30px auto;display:none">

            </div>
        </div>
    </form>
</div>

<div class="lk_storeChoose_list" id="lk_storeChoose_list" style="display: none">
    <div id="all_store">全选</div>
</div>


<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/third-party/jquery-1.10.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>135Editor/a92d301d77.js"> </script>
<script type="text/javascript">
    //赋初始值，提供给a92d301d77.js使用
    var BASEURL = 'https://www.135editor.com';
    var appkey = '59e710b8-db88-48f0-bb92-4f4a79291994';
    var domain_135 = BASEURL;

    //赋值给current_editor，提供给a92d301d77.js使用
    var current_editor = UE.getEditor('content',{
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

<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script src="<%=basePath%>newStyle/js/lk-chooseStore/lk-chooseStore.js"></script>
<script src="<%=basePath%>newStyle/js/lk-sysmatch/lk-matchEdit.js"></script>
<script src="<%=basePath%>js/qrcode.min.js"></script>
<script>
    var matchedFlag = "edit";
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/internet/' //静态资源所在路径
    }).extend({
        matches : 'matches/matches',
    }).use(['matches']);
</script>

</body>
</html>
