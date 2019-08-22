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
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>

    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/custom.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">

    <title>自定义菜单</title>
    <style>
        body{
            background: #fff;
        }
        .layui-this:after {
            border-bottom: none!important;
        }
        .layui-tab-title li {
            line-height: 38px;
        }
        .demo-class .layui-layer-btn{border-top:1px solid #E9E7E7;background-color: #fafafa}
        .copytext{
            border:1px solid #e7e7eb;
            height: 580px;
            width:573px;
            position: absolute;
            left: 962px;
            top: 0px;
        }
        .htitle{
            margin-left:10px;
            margin-top:8px;
            color: #79C6FF;
            font-size:13px;
        }
    </style>
</head>
<body class="scroll">
<div class="bodyBox" style="margin-left:0;">
    <%-- <div class="listBox">
        <p class="text-center">全部</p>
        <p class="text-center" title="${internet.INTENET_NAME}">
            <c:if test="${internet.INTENET_NAME.length() > 6}">
                ${internet.INTENET_NAME.substring(0,6)}...
            </c:if>
            <c:if test="${internet.INTENET_NAME.length() <= 6}">
                ${internet.INTENET_NAME}
            </c:if>
        </p>
    </div> --%>
    <div class="navlist" style="border-bottom:none;padding:0 12px;">
        <%-- <div class="row">
            <div class="col-md-3 lineHieght"><p>全部/ <span>${internet.INTENET_NAME}</span></p></div>
        </div> --%>
        <p class="cus-fristp">
            <i class="layui-icon ">&#xe60b;</i>
            菜单未发布，请确认菜单编辑完成后点击“保存并发布”同步到手机。
        </p>
        <div class="certert">
            <div class="listcustom">
                <div class="menu_preview_area">
                    <div style="background-color: #fafafa">
                        <div class="mobile_menu_preview">
                            <div class="mobile_hd">
                                ${internet.INTENET_NAME}
                            </div>
                            <div class="mobile_bd">
                                <ul class="pre_menu_list" id="ulId">
                                    <li class="pre_menu_item  size1of3  redered"  id="redered0" onclick="seled(0)">
                                        <a href="javascript:void(0);" class="pre_menu_link linker"><span id="name0">${fuList[0].NAME }</span></a>
                                        <div class="sub_pre_menu_box" id="divZnoe">
                                            <i class="arrow arrow_out"></i>
                                            <i class="arrow arrow_in"></i>
                                            <div class="sub_pre_menu_list">
                                                <c:choose>
                                                    <c:when test="${not empty fList}">
                                                        <c:forEach items="${fList}" var="var">
                                                            <div class="sub_pre_menu_list_div0" id="div0_${var.SEQUENCE }" onclick="activeButton(0,${var.SEQUENCE })">
                                                                <a href="javascript:void(0);">
		                                            				<span class="sub_pre_menu_inner" id="text0_${var.SEQUENCE }">
                                                                            ${var.NAME }
                                                                    </span>
                                                                </a>

                                                            </div>
                                                        </c:forEach>
                                                    </c:when>
                                                </c:choose>
                                                <div id="new0" onclick="newAdd(0)" <c:if test="${fList.size() == 5 }">style="display: none"</c:if></div>
                                            <span class="sub_pre_menu_inner">
                                                        <i class="layui-icon" style="color:#999;">&#xe654;</i>
                                                    </span>
                                        </div>
                            </div>
                        </div>
                        </li>
                        <li class="pre_menu_item size1of3" onclick="seled(1)" id="redered1">
                            <a href="javascript:void(0);" class="pre_menu_link linker"><span id="name1">${fuList[1].NAME }</span></a>
                        </li>
                        <li class="pre_menu_item size1of3" onclick="seled(2)" id="redered2">
                            <a href="javascript:void(0);" class="pre_menu_link linker"><span id="name2">${fuList[2].NAME }</span></a>
                            <div class="sub_pre_menu_box" style="display: none" id="divTwo">
                                <i class="arrow arrow_out"></i>
                                <i class="arrow arrow_in"></i>
                                <div class="sub_pre_menu_list">
                                    <c:choose>
                                        <c:when test="${not empty tList}">
                                            <c:forEach items="${tList}" var="var">
                                                <div class="sub_pre_menu_list_div2" id="div2_${var.SEQUENCE }" onclick="activeButton(2,${var.SEQUENCE })">
                                                    <a href="javascript:void(0);">
		                                            				<span class="sub_pre_menu_inner" id="text2_${var.SEQUENCE }">
                                                                            ${var.NAME }
                                                                    </span>
                                                    </a>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                    </c:choose>
                                    <div id="new2" onclick="newAdd(2)" <c:if test="${tList.size() == 5 }">style="display: none"</c:if>>
                                                    <span class="sub_pre_menu_inner">
                                                        <i class="layui-icon" style="color:#999;">&#xe654;</i>
                                                    </span>
                                    </div>
                                </div>
                            </div>
                        </li>
                        </ul>
                    </div>
                </div>
            </div>
            <p class="caozuo">
                <span class="caidan">菜单排序：</span>
                <span class="cztype" id="moverUp">上移</span>
                <span class="cztype" id="moverDown">下移</span>
                <span class="cztype" id="delete">删除</span>
            </p>
        </div>
        <div class="menu_form_area">
            <input id="SELECT_MENU" name="SELECT_MENU" type="hidden" value="${pd.SELECT_MENU }">
            <input id="TYPE" type="hidden" value="${pd.TYPE }">

            <div class="menu_initial_tips tips_global" style="display: none" id="none">点击左侧菜单进行编辑操作</div>
            <div class="portable_editor to_left" id="leftBox">
                <div class="editor_inner">
                    <div class="menu_form_hd">
                        <h4 class="global_info">当前正在编辑/<span style="color:#459ae9">菜单名称</span></h4>
                    </div>
                    <div class="menu_form_bd">
                        <div class="msg_sender_tips tips_global" style="display: none" id="onlyZcd">已添加子菜单，仅可设置菜单名称。</div>
                        <div class="frm_control_group ">
                            <label for class="frm_label">
                                <strong class="title">菜单名称</strong>
                            </label>
                            <div class="frm_controls">
                                            <span class="frm_input_box">
                                                <input type="text" class="frm_input " value="${pd.NAME }" id="NAME" onkeyup="checkName('lie')">
                                            </span>
                                <p class="frm_msg" style="display: none">字数超过上限</p>
                                <p class="frm_msg" style="display: none">请输入子菜单名称</p>
                                <p class="frm_msg" style="color:#8d8d8d" id="xianzhi">字数不超过7个汉字或16个字母</p>
                            </div>
                        </div>
                    </div>
                    <div class="frm_control_group" style="margin-top: 40px;" id="cooder">
                        <ul class="nav nav-tabs" role="tablist">
                            <li>
                                <label for class="frm_label">
                                    <strong class="title" style=" position: relative;top: 8px;">菜单内容</strong>
                                </label>
                            </li>
                            <li role="presentation" id="xiaoxi" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">发送消息</a></li>
                            <li role="presentation" id="wangye"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">跳转网页</a></li>
                            <li role="presentation" id="xiaochengxu"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">跳转小程序</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <!-- 发送消息的四个子内容type=click/media_id -->
                            <div role="tabpanel" class="tab-pane active" id="home">
                                <div class="msg_sender">
                                    <div class="msg_tab">
                                        <div class="tab_navs_panel">
                                            <div class="tab_navs_wrp">
                                                <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">

                                                    <ul class="tab_navs layui-tab-title">
                                                        <li class="tab_nav" id="tuwen">
                                                            <a href="javascript:void(0);">
                                                                <i class="layui-icon tab_icon1">&#xe62a;</i>
                                                                <span class="tab_span">图文消息</span>
                                                            </a>
                                                        </li>
                                                        <li class="tab_nav" id="tupian">
                                                            <a href="javascript:void(0);">
                                                                <i class="layui-icon tab_icon2">&#xe64a;</i>
                                                                <span class="tab_span">图片</span>
                                                            </a>
                                                        </li>
                                                        <li class="tab_nav" id="wenzi">
                                                            <a href="javascript:void(0);">
                                                                <i class="layui-icon tab_icon3" style="font-size: 14px">&#xe648;</i>
                                                                <span class="tab_span">文字</span>
                                                            </a>
                                                        </li>
                                                        <li class="tab_nav" id="kaquan">
                                                            <a href="javascript:void(0);">
                                                                <i class="layui-icon tab_icon3" >&#xe65e;</i>
                                                                <span class="tab_span">卡券</span>
                                                            </a>
                                                        </li>
                                                    </ul>
                                                    <div class="layui-tab-content">
                                                        <!-- 选择图文 -->
                                                        <div class="layui-tab-item" id="tuwenItem">
                                                            <div class="inner scroll">
                                                                <div class="tab_cont_cover" id="newImg">
                                                                    <div class="media_cover">
                                                                                   <span class="create_access">
                                                                                      <a href="javascript:void(0);" onclick="opende()">
                                                                                         <i class="layui-icon tab_icona">&#xe654;</i>
                                                                                         <strong>从素材库中选择</strong>
                                                                                      </a>
                                                                                   </span>
                                                                    </div>
                                                                </div>
                                                                <div id="textImg" style="display: none" >
                                                                    <div class="rowBoxCenter">
                                                                        <p><span class="font-12 padding-30"></span></p>
                                                                        <div class="widthImg">
                                                                            <img src="" alt="">
                                                                            <div class="wordTitle"><p></p></div>
                                                                            <div class="imgyulan" onclick="previewTuWen(this)">
                                                                                <p class="text-center" id="tuwen_0">预览</p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <input id="TUWEN_MEDIA_ID" type="hidden">
                                                                    <input id="TUWEN_SENDRECORD_ID" type="hidden">
                                                                    <p id="shanchu" class="shanchu">删除</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- 选择图片或本地上传 -->
                                                        <div class="layui-tab-item" id="tupianItem">
                                                            <div class="flieImg scroll" style="overflow-y: scroll;">
                                                                <div class="tab_cont_cover">
                                                                    <div class="media_cover" id="beforeImg">
	                                                                               <span class="create_access">
	                                                                                  <a href="javascript:void(0);" onclick="openImg()">
	                                                                                     <i class="layui-icon tab_icona">&#xe654;</i>
	                                                                                     <strong>选择历史图片</strong>
	                                                                                  </a>
	                                                                               </span>
                                                                    </div>
                                                                    <div class="media_cover" id="flieImg">
                                                                        <form action="<%=basePath%>wxMenu/uploadImg.do" method="post" name="Form" id="Form" enctype="multipart/form-data" target="nm_iframe">
                                                                            <input id="SELECT_MENU2" name="SELECT_MENU2" type="hidden" value="${pd.SELECT_MENU }">
                                                                            <input type="file" class="upFlie" onchange="uploadImg(this.files)" id="upFlie" name="upFile">
                                                                        </form>
                                                                        <iframe id="nm_iframe" name="nm_iframe" style="display:none;" ></iframe>
                                                                        <span class="create_access">
	                                                                                  <a href="javascript:void(0);" onclick="">
	                                                                                     <i class="layui-icon tab_icona">&#xe654;</i>
	                                                                                     <strong>选择本地上传</strong>
	                                                                                  </a>
	                                                                               </span>
                                                                    </div>
                                                                    <div id="divImg" style="display: none">
                                                                        <div class="divImg ">
                                                                            <img src=""  width="100%" id="successImg" class="">
                                                                            <div class="imgyulan">
                                                                                <p class="text-center" onclick="yulanTuPian()">预览</p>
                                                                            </div>
                                                                        </div>
                                                                        <input id="IMG_MEDIA_ID" type="hidden">
                                                                        <span id="deleteImg" class="shanchu">删除</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- 文字消息 -->
                                                        <div class="layui-tab-item" id="wenziItem">
                                                            <div class="layui-form-item layui-form-text" style="margin-top: 30px;">
                                                                <label class="layui-form-label" style="padding: 0 22px;">文字内容</label>
                                                                <div class="layui-input-block" style="margin-left: 80px;">
                                                                    <textarea placeholder="请输入内容" class="layui-textarea" style="width: 80%" id="CONTENT"></textarea>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- 选择卡券 -->
                                                        <div class="layui-tab-item" id="kaquanItem">
                                                            <div class="cardDiv scroll">
                                                                <div class="tab_cont_cover" id="xzCard">
                                                                    <div class="media_cover" id="">
                                                                                       <span class="create_access">
                                                                                          <a href="javascript:void(0);" onclick="card()">
                                                                                             <i class="layui-icon tab_icona">&#xe654;</i>
                                                                                             <strong>选择历史卡券</strong>
                                                                                          </a>
                                                                                       </span>
                                                                    </div>
                                                                </div>
                                                                <div id="jsCard" style="display: none">
                                                                    <input id="CARD_ID" type="hidden">
                                                                    <div class="cardBox">
                                                                        <h1 style="font-size: 18px;font-weight: 600;margin-bottom: 15px;color:#fff;padding: 14px 15px " id="title" ></h1>
                                                                        <p id="time"></p>
                                                                        <p id="adrss"></p>
                                                                        <p style="padding-bottom: 20px" id="xiangqing"></p>
                                                                    </div>
                                                                    <span id="deleteCard" class="shanchu">删除</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 跳转链接type=view -->
                            <div role="tabpanel" class="tab-pane" id="profile">
                                <div class="frm_control_group ">
                                    <label for class="frm_label">
                                        <strong class="title">网页链接</strong>
                                    </label>
                                    <div class="frm_controls">
                                                    <span class="frm_input_box" style="width:350px">
                                                        <input type="text" class="frm_input " id="URL" onkeyup="checkUrl('URL')" placeholder="以http://或https://开头">
                                                    </span>
                                    </div>
                                </div>
                            </div>
                            <!-- 跳转小程序type=miniprogram -->
                            <div role="tabpanel" class="tab-pane" id="messages">
                                <div class="frm_control_group ">
                                    <label for class="frm_label">
                                        <strong class="title">appId</strong>
                                    </label>
                                    <div class="frm_controls">
                                                    <span class="frm_input_box" style="width:350px">
                                                        <input type="text" class="frm_input " id="MINI_APPID" placeholder="请输入与该公众号相关联的小程序APPID">
                                                    </span>
                                    </div>
                                </div>
                                <div class="frm_control_group ">
                                    <label for class="frm_label">
                                        <strong class="title">页面路径</strong>
                                    </label>
                                    <div class="frm_controls">
                                                    <span class="frm_input_box" style="width:350px">
                                                        <input type="text" class="frm_input " id="MINI_PAGEPATH">
                                                    </span>
                                    </div>
                                </div>
                                <div class="frm_control_group ">
                                    <label for class="frm_label">
                                        <strong class="title">网页链接</strong>
                                    </label>
                                    <div class="frm_controls">
                                                    <span class="frm_input_box" style="width:350px">
                                                        <input type="text" class="frm_input " id="MINI_URL" onkeyup="checkUrl('MINI_URL')" placeholder="请输入以http://开头的网页链接">
                                                    </span>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div style="margin-top: 30px;margin-left: 40px;margin-bottom: 50px">
                    <button class="layui-btn" onclick="save(1)">仅保存</button>
                    <button class="layui-btn layui-btn-normal" onclick="issue()">保存并发布</button>
                </div>
            </div>
        </div>
            <div class="copytext">
                <h4 class="htitle">揽客功能跳转链接</h4>
                <%--表单--%>
                <table class="layui-hide" id="layTable" lay-filter="layTable"></table>
            </div>

    </div>
</div>
</div>
</div>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<%--添加行操作功能--%>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit" >复制</a>
</script>
<script>
    $(top.hangge());
    var tableId = "layTable";
    layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function() {
        var laydate = layui.laydate //日期
            , laypage = layui.laypage //分页
            , layer = layui.layer //弹层
            , table = layui.table //表格
            , carousel = layui.carousel //轮播
            , upload = layui.upload //上传
            , element = layui.element //元素操作
            , slider = layui.slider //滑块
        //执行一个 table 实例
        table.render({
                elem: '#layTable',
                data:[{"functions":"会员申请","lianjie":"https://lanke8.cc/intenetmumber/Membership0"}
                    ,{"functions":"会员绑定","lianjie":"https://lanke8.cc/intenetmumber/bindCard?label=index"}
                    ,{"functions":"会员生活","lianjie":"https://lanke8.cc/wxML/memberLife.do"}
                    ,{"functions":"充值页面","lianjie":"https://lanke8.cc/indexMember/recharge"}
                    ,{"functions":"网吧商城","lianjie":"https://lanke8.cc/indexMember/mall"}
                    ,{"functions":"投诉建议","lianjie":"https://lanke8.cc/indexMember/gotoLm"}
                    ,{"functions":"卡券领取","lianjie":"https://lanke8.cc/indexMember/benefits"}
                    ,{"functions":"手气抽奖","lianjie":"https://lanke8.cc/indexMember/activityGame.do"}
                    ,{"functions":"邀请好友","lianjie":"https://lanke8.cc/indexMember/share.do"}
                    ,{"functions":"门店展示","lianjie":"https://lanke8.cc/intenetmumber/introduction"}
                    ,{"functions":"会员个人页面","lianjie":"https://lanke8.cc/myMember/gotoUserCenter"}
                    ,{"functions":"我的明细","lianjie":"https://lanke8.cc/myMember/myStatements"}
                    ,{"functions":"上网记录","lianjie":"https://lanke8.cc/myMember/internetRecord"}
                    ,{"functions":"我的抽奖","lianjie":"https://lanke8.cc/myMember/mySpoil"}
                    ,{"functions":"我的卡券","lianjie":"https://lanke8.cc/myMember/myCard"}
                    ,{"functions":"我的订单","lianjie":"https://lanke8.cc/myMember/myDingDan"}
                    ,{"functions":"我的报名","lianjie":"https://lanke8.cc/myMember/myMatch"}
                    ,{"functions":"我的投诉","lianjie":"https://lanke8.cc/myMember/myLm"}],
                align: "center",
                cols: [[ //表头
                {type: 'numbers', title: '序号',width:80},
                {field:'functions', title: '功能', width:120},
                {field:'lianjie', title: '链接', width:287},
                {field:'make',title: '操作', align:'center', toolbar: '#barDemo',fixed: 'right'}
            ]],
            page: true ,//是否显示分页
            limits: [6,10,12],
            limit: 12,
        })

        table.on('tool(layTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data ;//获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            var text = data.lianjie;
            //创建复制对象
            var textArea = document.createElement("textarea");
            textArea.style.position = 'fixed';
            textArea.style.top = 0;
            textArea.style.left = 0;
            textArea.style.width = '2em';
            textArea.style.height = '2em';
            textArea.style.padding = 0;
            textArea.style.border = 'none';
            textArea.style.outline = 'none';
            textArea.style.boxShadow = 'none';
            textArea.style.background = 'transparent';
            textArea.value =text;
            document.body.appendChild(textArea);//将复制的对象添加到body下
            textArea.select();
            try {
                var msg = document.execCommand('copy') ? '成功' : '失败';
                layer.msg("复制完成");
            } catch (err) {
                layer.msg('复制失败');
            }
            document.body.removeChild(textArea);//移除文本
        });
    });
</script>

<script>
    $(top.hangge());
    //      判断页面显示
    jubge();
    function jubge() {
        var row = $(".current").text();
        var der = $(".redered").text();
        var mun = $(".redered").children(".sub_pre_menu_box").children(".sub_pre_menu_list ").children().length;
        if(row == "" & der == ""){
            //$("#leftBox").hide();
            //$("#none").show();
        }else if(row != ""){
            $("#none").hide();
            $("#leftBox").show();
            $("#cooder").show();
            $("#onlyZcd").hide();
        }else if(der != "" & mun == 1){
            $("#none").hide();
            $("#leftBox").show();
            $("#onlyZcd").hide();
            $("#cooder").show();
        }else{
            $("#none").hide();
            $("#leftBox").show();
            $("#onlyZcd").show();
            $("#cooder").hide();
        }

    }
    //      选择历史卡券
    function card() {
        layer.open({
            btn: ['确定', '关闭'],
            btn1: function(index, layero){
                //按钮【按钮一】的回调
                var res = window["layui-layer-iframe" + index].callCard();
                $("#title").html(res.title);
                $("#title").attr("class",res.titleClass);
                $("#time").html(res.time);
                $("#adrss").html(res.adrss);
                $("#xiangqing").html(res.xiangqing);
                $("#xzCard").hide();
                $("#jsCard").show();
                $("#CARD_ID").val(res.cardId);
                layer.close(index);
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '添加您的历史卡券',
            shadeClose: false,
            shade: 0.8,
            area: ['800px', '470px'],
            fixed: false, //不固定
            maxmin: true,
            content:[ '<%=basePath%>wxMenu/cardList.do'] //iframe的url
        });
    }
    //上传图片
    function uploadImg(f){
        for(var i=0;i<f.length;i++){
            var reader = new FileReader();
            reader.readAsDataURL(f[i]);
            var tt = f[i].type;
            var size = f[i].size;
            reader.onload = function(e){
                var srces = e.target.result;
                var rule = /^(?:image\/jpeg|image\/png)$/i;
                if(rule.test(tt)){
                    if(size > (2*1024*1024)){
                        layer.msg("图片大小不能超过2M",{time:1500});
                    }else{
                        $.ajax({
                            type	: "POST",
                            url		:"<%=basePath%>wxAutoReply/uploadImg.do",
                            data	:{upFile:srces},
                            dataType:'json',
                            beforeSend	: beforeSend,
                            success: function(data) {
                                layer.closeAll('loading');
                                layer.msg(data.message,{time:1000});
                                if(data.result == "true"){
                                    $("#IMG_MEDIA_ID").val(data.MEDIA_ID);

                                    $("#successImg").attr("src",data.PATH);
                                    flieImgsuccess();
                                }
                            },
                            error:function(){
                                layer.closeAll('loading');
                                layer.msg("系统繁忙，请稍后再试！");
                                return false;
                            }
                        });
                    }
                }else{
                    layer.msg("请上传jpg或者png格式的图片",{time:1500});
                }

            }
        }
    }
    function beforeSend(){
        layer.load(2);
    }
    //预览图片
    var yulanTuPian = function () {
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            area: '30%',
            closeBtn:1,
            skin: '', //没有背景色
            shadeClose: true,
            content: $("#successImg")
        });
    }
    //预览图文
    var previewTuWen = function(eve){
        var id = $(eve).children().attr("id");
        var sequence = id.split("_")[1];
        var SENDRECORD_ID = $("#TUWEN_SENDRECORD_ID").val();

        layer.open({
            btn: ['关闭'],
            btn1: function(index, layero){
                layer.close(index);
            },
            skin: '',
            btnAlign: 'c',
            type: 2,
            title: '预览图文',
            shadeClose: false,
            shade: 0.8,
            area: ['60%', '90%'],
            fixed: false, //不固定
            maxmin: true,
            content:[ '<%=basePath%>wxMenu/preview.do?SENDRECORD_ID='+SENDRECORD_ID+'&SEQUENCE='+sequence]
        });

    }
    //删除事件(图文，图片，卡券)
    $("#shanchu").click(function () {
        deleteTuwen();
    })
    $("#deleteImg").click(function () {
        deleteImg();
    })
    $("#deleteCard").click(function () {
        deleteCard();
    })
    var deleteTuwen = function(){
        $("#textImg").hide();
        $("#newImg").show();
        $("#TUWEN_MEDIA_ID").val("");
        $("#TUWEN_SENDRECORD_ID").val("");
        $(".borBottom").remove();
    }
    var deleteImg = function(){
        $("#flieImg").show();
        $("#beforeImg").show();
        $("#divImg").hide();
        $("#divImg").children().children().attr("src","");
        $("#upFlie").val("");
        $("#IMG_MEDIA_ID").val("");
    }
    var deleteCard = function(){
        $("#jsCard").hide();
        $("#xzCard").show();
        $("#title").html("");
        $("#title").attr("class","");
        $("#time").html("");
        $("#adrss").html("");
        $("#xiangqing").html("");
        $("#CARD_ID").val("");
    }
    //      选择历史图文
    var opende = function () {

        layer.open({
            btn: ['确定', '关闭'],
            btn1: function(index, layero){
                //按钮【按钮一】的回调
                var res = window["layui-layer-iframe" + index].callTuWen();
                $(".padding-30").html(res.time);
                $(".wordTitle").children(":first-child").html(res.ftitle);
                $(".widthImg").children(":first-child").attr("src",res.fimg);
                $("#TUWEN_MEDIA_ID").val(res.media_id);
                $("#TUWEN_SENDRECORD_ID").val(res.sendRecord_id);

                if(parseInt(res.length) > 0){
                    var all = "";
                    for(var i=0;i<res.length;i++){
                        var titleName = "title"+i;
                        var imgName = "img"+i;
                        var title = JSON.parse(res.last)[titleName];
                        var img = JSON.parse(res.last)[imgName];
                        var div = '<div class="borBottom">'+
                            '<table width="100%" >'+
                            '<tbody >'+
                            '<tr>'+
                            '<td style="vertical-align: top"><p class="tarTileWid">'+title+'</p></td>'+
                            '<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img src="'+img+'" alt=""></div></td>'+
                            '</tr>'+
                            '</tbody>'+
                            '</table>'+
                            '<div class="imgyulan" onclick="previewTuWen(this)">'+
                            '<p class="text-center" id="tuwen_'+(i+1)+'">预览</p>'+
                            '</div>'+
                            '</div>';
                        all += div;
                    }
                    $(".widthImg").after(all);
                }

                $("#newImg").hide();
                $("#textImg").show();
                layer.close(index);
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '添加图文消息',
            shadeClose: false,
            shade: 0.8,
            area: ['800px', '500px'],
            fixed: false, //不固定
            maxmin: true,
            content:[ '<%=basePath%>wxMenu/tuwenList.do'] //iframe的url
        });
    }
    //      选择历史图片
    var openImg = function () {
        layer.open({
            btn: ['确定', '关闭'],
            btn1: function(index, layero){
                //按钮的回调
                var res = window["layui-layer-iframe" + index].callbackdata();
                $("#successImg").attr("src",res.url);
                $("#IMG_MEDIA_ID").val(res.media_id);
                flieImgsuccess();
                layer.close(index);
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '选择历史图片',
            shadeClose: false,
            shade: 0.8,
            fixed: false, //不固定
            maxmin: true,
            area: ['764px', '520px'],
            fixed: false, //不固定
            maxmin: true,
            content:[ '<%=basePath%>wxMenu/tupianList.do'] //iframe的url
        });
    }
    var flieImgsuccess = function () {
        $("#divImg").show();
        $("#flieImg").hide();
        $("#beforeImg").hide();
    }

    //      点击选中
    $(".tab_navs").children(".tab_nav").click(function () {
        $(this).addClass("actived");
        $(this).siblings().removeClass("actived");
    })

    //删除某lie的某个菜单
    $("#delete").click(function () {
        var node = $("#divTwo");
        if(node.is(':hidden')){
            deleted(0);
        }else {
            deleted(2);
        }
    })
    //判断上移哪一列
    $("#moverUp").click(function () {
        var node = $("#divTwo");
        if(node.is(':hidden')){
            moverUp(0);
        }else {
            moverUp(2);
        }
    })

    //新增二级菜单
    var newAdd = function (lie) {
        var nnew = "#"+"new"+lie;
        //获取动态添加的div的个数
        var lenghbox = "."+"sub_pre_menu_list_div"+lie;
        var sub = $(".sub_pre_menu_list").children(lenghbox).length;
        var i = 0;
        i += sub ;
        //大于5个不能添加第元素了，最多只能发布5个子菜单
        if( sub < 5){
            //动态添加div的结构
            var htmltext = '<div class="sub_pre_menu_list_div'+lie+'" id="div'+lie+'_'+i+'" onclick="activeButton('+lie+','+i+')">'+
                '<a href="javascript:void(0);"title="最多添加5个子菜单">'+'<span class="sub_pre_menu_inner" id="text'+lie+'_'+i+'">'+'子菜单名称'+'</span></a></div>';
            $(nnew).before(htmltext);//           节点后面添加div

            //默认新增的为图文类型的
            $("#xiaoxi").addClass("active");
            $("#wangye").removeClass("active");
            $("#xiaochengxu").removeClass("active");

            $("#home").addClass("active");
            $("#profile").removeClass("active");
            $("#messages").removeClass("active");

            $("#tuwen").addClass("layui-this actived");
            $("#wenzi").removeClass("layui-this actived");
            $("#kaquan").removeClass("layui-this actived");
            $("#tupian").removeClass("layui-this actived");

            $("#tuwenItem").addClass("layui-show");
            $("#wenziItem").removeClass("layui-show");
            $("#kaquanItem").removeClass("layui-show");
            $("#tupianItem").removeClass("layui-show");

            //清空其他
            $("#URL").val("");
            $("#MINI_APPID").val("");
            $("#MINI_PAGEPATH").val("");
            $("#MINI_URL").val("");

            $("#CONTENT").val("");
            deleteImg();
            deleteCard();
            deleteTuwen();

            $("#newImg").show();
            $("#textImg").hide();

            //默认数据
            $("#NAME").val("子菜单名称");
            $("#SELECT_MENU").val(lie+"_"+i);
            $("#SELECT_MENU2").val(lie+"_"+i);
            activeButton(lie,i);
            jubge();
            if(sub == 4){
                $(nnew).hide(); //超过5个hide
            }
        }else {
            $(nnew).show();
        }
    }
    //切换二级菜单
    var activeButton =  function (lie,i) {
        //页面选中效果
        var subDiv = "#"+"div"+lie+"_"+i;
        $(subDiv).addClass("current");
        $(subDiv).siblings().removeClass("current");

        var rederId = "#redered"+lie;
        $(rederId).removeClass("redered");
        if(event != undefined){
            event.stopPropagation();
        }
        jubge();

        var name = $("#NAME").val();
        var type = "";

        var media_id = "";
        var path = "";
        var content = "";
        var card_id = "";
        var url = "";
        var mini_appid = "";
        var mini_pagepath = "";
        var mini_url = "";
        var activeType = $(".active").children().html();
        if(activeType == "发送消息"){
            var tt = $(".actived").children().children().last().html();
            if(tt == "图文消息"){
                type = "media_id";
                media_id = $("#TUWEN_MEDIA_ID").val();
            }else if(tt == "图片"){
                type = "media_id";
                media_id = $("#IMG_MEDIA_ID").val();
                path = $("#successImg")[0].src;
            }else if(tt == "文字"){
                type = "click";
                content = $("#CONTENT").val();
            }else if(tt == "卡券"){
                type = "click";
                card_id = $("#CARD_ID").val();
            }
        }else if(activeType == "跳转网页"){
            type = "view";
            url = $("#URL").val();
        }else if(activeType == "跳转小程序"){
            type = "miniprogram";
            mini_appid = $("#MINI_APPID").val();
            mini_pagepath = $("#MINI_PAGEPATH").val();
            mini_url = $("#MINI_URL").val();
        }

        var before = $("#SELECT_MENU").val();
        var arr = '';
        if(before.indexOf("_") > 0){
            //切换前的菜单为二级菜单
            arr = before.split("_");
            var id = "#text"+before;
            if(name.length == 0){
                name = "子菜单名称";
                $("#NAME").val("子菜单名称");
            }
            var length2 = 60;
            for(var j=0;j<name.length;j++){
                if(length2 == 0){
                    name = name.substring(0,j);
                    break;
                }
                if(name.charCodeAt(j) > 255){
                    length2 -= 2;
                }else{
                    length2 --;
                }
            }
            $(id).html(name);
        }else{
            //切换前的菜单为一级菜单
            arr = [before , ""];
            var id = "#name"+before;
            if(name.length == 0){
                name = "菜单名称";
                $("#NAME").val("菜单名称");
            }
            var length2 = 16;
            for(var j=0;j<name.length;j++){
                if(length2 == 0){
                    name = name.substring(0,j);
                    break;
                }
                if(name.charCodeAt(j) > 255){
                    length2 -= 2;
                }else{
                    length2 --;
                }
            }
            $(id).html(name);
        }

        $.ajax({
            type	: "POST",
            url		:'<%=basePath%>wxMenu/change.do',
            data	:{LIE:arr[0],HANG:arr[1],LIE2:lie,HANG2:i,NAME:name,TYPE:type,MEDIA_ID:media_id,PATH:path,CONTENT:content,CARD_ID:card_id,URL:url,MINI_APPID:mini_appid,MINI_PAGEPATH:mini_pagepath,MINI_URL:mini_url},
            dataType:'json',
            success: function(data) {
                $("#SELECT_MENU").val(lie+"_"+i);
                $("#SELECT_MENU2").val(lie+"_"+i);
                if(data.STATE == "0"){
                    layer.msg("当前菜单为系统菜单，不可更改！",{time: 1500,icon:0});
                    $("#leftBox").hide();//隐藏系统菜单
                    $("#none").show();
                    $("#delete").off("click");
                }else{
                    $("#delete").off("click");
                    $("#delete").on("click",deleted);
                }

                $("#NAME").val(data.NAME);	//切换文字
                $("#NAME").attr("onkeyup","checkName('hang')");
                $("#xianzhi").html("字数不超过8个汉字或16个字母");

                typeSel(data.TYPE,data.DETAIL,data);//切换类型,切换内容



            },
            error:function(){
                layer.msg("系统繁忙，请稍后再试！");
                return;
            }
        });
    }
    //切换一级菜单
    var seled = function (lie) {
        //页面选中效果
        var seledId = "#redered"+lie;
        $(seledId).addClass("redered");
        $(seledId).siblings().removeClass("redered");
        $(seledId).children(".sub_pre_menu_box").show();
        $(seledId).siblings().children(".sub_pre_menu_box").hide();
        $(".current").removeClass("current");
        //$("#redered1").removeClass("redered");
        jubge();

        var name = $("#NAME").val();

        var type = "";
        var media_id = "";
        var path = "";
        var content = "";
        var card_id = "";
        var url = "";
        var mini_appid = "";
        var mini_pagepath = "";
        var mini_url = "";
        var activeType = $(".active").children().html();
        if(activeType == "发送消息"){
            var tt = $(".actived").children().children().last().html();
            if(tt == "图文消息"){
                type = "media_id";
                media_id = $("#TUWEN_MEDIA_ID").val();
            }else if(tt == "图片"){
                type = "media_id";
                media_id = $("#IMG_MEDIA_ID").val();
                path = $("#successImg")[0].src;
            }else if(tt == "文字"){
                type = "click";
                content = $("#CONTENT").val();
            }else if(tt == "卡券"){
                type = "click";
                card_id = $("#CARD_ID").val();
            }
        }else if(activeType == "跳转网页"){
            type = "view";
            url = $("#URL").val();
        }else if(activeType == "跳转小程序"){
            type = "miniprogram";
            mini_appid = $("#MINI_APPID").val();
            mini_pagepath = $("#MINI_PAGEPATH").val();
            mini_url = $("#MINI_URL").val();
        }

        var before = $("#SELECT_MENU").val();
        var arr = '';
        if(before.indexOf("_") > 0){
            //切换前的菜单为二级菜单
            arr = before.split("_");
            var id = "#text"+before;
            if(name.length == 0){
                name = "子菜单名称";
                $("#NAME").val("子菜单名称");
            }
            var length2 = 60;
            for(var j=0;j<name.length;j++){
                if(length2 == 0){
                    name = name.substring(0,j);
                    break;
                }
                if(name.charCodeAt(j) > 255){
                    length2 -= 2;
                }else{
                    length2 --;
                }
            }

            $(id).html(name);
        }else{
            //切换前的菜单为一级菜单
            arr = [before , ""];
            var id = "#name"+before;
            if(name.length == 0){
                name = "菜单名称";
                $("#NAME").val("菜单名称");
            }
            var length2 = 16;
            for(var j=0;j<name.length;j++){
                if(length2 == 0){
                    name = name.substring(0,j);
                    break;
                }
                if(name.charCodeAt(j) > 255){
                    length2 -= 2;
                }else{
                    length2 --;
                }
            }
            $(id).html(name);
        }


        $.ajax({
            type	: "POST",
            url		:'<%=basePath%>wxMenu/change.do',
            data	:{LIE:arr[0],HANG:arr[1],LIE2:lie,HANG2:'',NAME:name,TYPE:type,MEDIA_ID:media_id,PATH:path,CONTENT:content,CARD_ID:card_id,URL:url,MINI_APPID:mini_appid,MINI_PAGEPATH:mini_pagepath,MINI_URL:mini_url},
            dataType:'json',
            success: function(data) {
                $("#SELECT_MENU").val(lie);
                $("#SELECT_MENU2").val(lie);
                if(data.STATE == "0"){
                    layer.msg("当前菜单为系统菜单，不可更改！",{time: 1500,icon:0});
                    $("#leftBox").hide();//隐藏系统菜单
                    $("#none").show();
                }

                $("#NAME").val(data.NAME);		//切换文字
                $("#NAME").attr("onkeyup","checkName('lie')");
                $("#xianzhi").html("字数不超过4个汉字或8个字母");
                typeSel(data.TYPE,data.DETAIL,data);//切换类型,切换内容



            },
            error:function(){
                layer.msg("系统繁忙，请稍后再试！");
                return;
            }
        });

    }

    //       删除方法
    var deleted = function (lie) {
        var nnew = "#"+"new"+lie;
        var sup = $(".sub_pre_menu_list").children('.current').length;
        if(sup > 0){
            layer.confirm('您确定删除此菜单？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                //取到当前盒子的ID
                var d = $(".current");
                d.next().addClass("current");
                $("#new0").removeClass("current");
                $("#new2").removeClass("current");
                var length = d.nextAll().length;
                $(d).remove();

                var id = d.attr("id").replace("div","");
                var arr = id.split("_");
                $.ajax({
                    type	: "POST",
                    url		:'<%=basePath%>wxMenu/deleteByOrder.do',
                    data	:{LIE:arr[0],HANG:arr[1]},
                    dataType:'json',
                    success: function(data) {
                        layer.msg(data.message, {time: 800,icon: 1},function () {
                            window.location.reload();//删除刷新
                            /* var subb = $(".sub_pre_menu_list").children('.sub_pre_menu_list_div').length;
                            if(subb < 5){
                                nnew = "#"+"new"+arr[0];
                                $(nnew).show();
                            }
                            edit(arr[1],length,arr[0]);
                            var i = parseInt(arr[1]);
                            if(i == 0){
                                i++;
                            }else if(i == subb-1){
                                i--;
                            }
                            var idd = "#text"+arr[0]+"_"+i;
                            $("#NAME").val($(idd).html());
                            activeButton(arr[0],i);
                            jubge(); */
                        });
                    },
                    error:function(){
                        layer.msg("系统繁忙，请稍后再试！");
                        return;
                    }
                });

            }, function(){
                return;
            });
        }
    }
    //     恢复排序
    var edit = function(number,length,lie) {
        for(var i=0;i<length-1;i++) {
            var mun = parseInt(number)+i;//当前的序号
            var nowDiv = "div"+lie+"_" + mun;
            var nowText = "text"+lie+"_" + mun;
            var row = mun+1;//下一个的序号
            console.log(row);
            var afterDiv = "#"+"div"+lie+"_" + row;
            var afterText = "#"+"text"+lie+"_" + row;
            $(afterDiv).attr("onclick","activeButton("+lie+","+mun+")");
            $(afterDiv).attr("id",nowDiv);
            $(afterText).attr("id",nowText);
        }
    }

    //        上移方法
    var moverUp = function (lie) {
        var d = $(".current");
        var firstId = "div"+lie+"_"+"0"
        if( $(d).attr("id") == firstId){
            layer.msg("别点了,已经最上了",{time: 1000,icon:0})
        }else {
            var id = d.attr("id").replace("div","");
            var arr = id.split("_");

            $.ajax({
                type	: "POST",
                url		:'<%=basePath%>wxMenu/move.do',
                data	:{FLAG:"up",LIE:arr[0],HANG:arr[1]},
                dataType:'json',
                success: function(data) {
                    if(data.result == "false"){
                        layer.msg(data.message,{time: 1000,icon:0});
                        return;
                    }
                    var now = $(".current").children().children();
                    var befor = $(".current").prev().children().children();
                    var nowText = now.text().trim();
                    var beforeText = befor.text().trim();
                    now.text(beforeText);
                    befor.text(nowText);
                    d.removeClass("current");
                    d.prev().addClass("current");

                    var i = parseInt(arr[1]) -1;
                    $("#SELECT_MENU").val(arr[0]+"_"+i);
                    $("#SELECT_MENU2").val(arr[0]+"_"+i);
                },
                error:function(){
                    layer.msg("系统繁忙，请稍后再试！");
                    return;
                }
            });
        }
    }

    //        下移方法
    $("#moverDown").click(function () {
        var d = $(".current");
        var next = $(".current").nextAll().length;
        if(next <= 1){
            layer.msg("别点了,已经到底了",{time: 1000,icon:0})
        }else {
            var now = d.children().children();
            var next = d.next().children().children();
            var nowText = now.text().trim();
            var nextText = next.text().trim();
            now.text(nextText);
            next.text(nowText);
            d.removeClass("current");
            d.next().addClass("current");

            var id = d.attr("id").replace("div","");
            var arr = id.split("_");
            $.ajax({
                type	: "POST",
                url		:'<%=basePath%>wxMenu/move.do',
                data	:{FLAG:"down",LIE:arr[0],HANG:arr[1]},
                dataType:'json',
                success: function(data) {
                    if(data.result == "false"){
                        layer.msg(data.message,{time: 1000,icon:0});
                    }
                    var i = parseInt(arr[1]) +1;
                    $("#SELECT_MENU").val(arr[0]+"_"+i);
                    $("#SELECT_MENU2").val(arr[0]+"_"+i);
                },
                error:function(){
                    layer.msg("系统繁忙，请稍后再试！");
                    return;
                }
            });
        }
    })
</script>

<script type="text/javascript">
    //发布
    var issue = function(){
        var cc = $("#SELECT_MENU").val();
        if(cc.indexOf("_") > 0){
            //保存的菜单为二级菜单
            if(!checkName('hang')){
                return false;
            }
        }else{
            //保存的菜单为一级菜单
            if(!checkName('lie')){
                return false;
            }
        }
        save(2);
        setTimeout(function () {
            $.ajax({
                type	: "POST",
                url		:'<%=basePath%>wxMenu/issue.do',
                data	:{},
                dataType:'json',
                beforeSend:beforeSend(''),
                success: function(data) {
                    layer.closeAll();
                    if(data.result == "false"){
                        if(data.hang == undefined && data.lie == undefined){

                        }else if(parseInt(data.lie) >=0 && data.hang == undefined){
                            seled(data.lie);
                            jubge();
                        }else if(parseInt(data.lie) >=0 && parseInt(data.hang) >=0){
                            seled(data.lie);
                            activeButton(data.lie,data.hang);
                            jubge();
                        }
                    }
                    layer.msg(data.message);
                },
                error:function(){
                    layer.closeAll();
                    layer.msg("系统繁忙，请稍后再试！");
                    return;
                }
            });
        },800)

    }

    var save = function(flag){
        var cc = $("#SELECT_MENU").val();
        if(cc.indexOf("_") > 0){
            //保存的菜单为二级菜单
            if(!checkName('hang')){
                return false;
            }
        }else{
            //保存的菜单为一级菜单
            if(!checkName('lie')){
                return false;
            }
        }

        var name = $("#NAME").val();
        var type = "";

        var media_id = "";
        var path = "";
        var content = "";
        var card_id = "";
        var url = "";
        var mini_appid = "";
        var mini_pagepath = "";
        var mini_url = "";
        var activeType = $(".active").children().html();
        if(activeType == "发送消息"){
            var tt = $(".actived").children().children().last().html();
            if(tt == "图文消息"){
                type = "media_id";
                media_id = $("#TUWEN_MEDIA_ID").val();
            }else if(tt == "图片"){
                type = "media_id";
                media_id = $("#IMG_MEDIA_ID").val();
                path = $("#successImg")[0].src;
            }else if(tt == "文字"){
                type = "click";
                content = $("#CONTENT").val();
            }else if(tt == "卡券"){
                type = "click";
                card_id = $("#CARD_ID").val();
            }
        }else if(activeType == "跳转网页"){
            type = "view";
            url = $("#URL").val();
            if(!checkUrl("URL")){
                return false;
            }
        }else if(activeType == "跳转小程序"){
            type = "miniprogram";
            mini_appid = $("#MINI_APPID").val();
            mini_pagepath = $("#MINI_PAGEPATH").val();
            mini_url = $("#MINI_URL").val();
            if(!checkUrl("MINI_URL")){
                return false;
            }
        }

        var before = $("#SELECT_MENU").val();
        var arr = '';
        if(before.indexOf("_") > 0){
            //保存的菜单为二级菜单
            arr = before.split("_");
            var id = "#text"+before;
            $(id).html(name);
        }else{
            //保存的菜单为一级菜单
            arr = [before , ""];
            var id = "#name"+before;
            $(id).html(name);
        }


        $.ajax({
            type	: "POST",
            url		:'<%=basePath%>wxMenu/save.do',
            data	:{LIE:arr[0],HANG:arr[1],NAME:name,TYPE:type,MEDIA_ID:media_id,PATH:path,CONTENT:content,CARD_ID:card_id,URL:url,MINI_APPID:mini_appid,MINI_PAGEPATH:mini_pagepath,MINI_URL:mini_url},
            dataType:'json',
            success: function(data) {
                if(flag == "1"){//1表示正常点击保存按钮有提示
                    layer.msg(data.message);
                }
                return true;
            },
            error:function(){
                layer.msg("系统繁忙，请稍后再试！");
                return;
            }
        });
    }

    var typeSel = function(type,detail,data){
        console.log(data);


        if(type == "click" || type == "media_id"){
            $("#xiaoxi").addClass("active");
            $("#wangye").removeClass("active");
            $("#xiaochengxu").removeClass("active");

            $("#home").addClass("active");
            $("#profile").removeClass("active");
            $("#messages").removeClass("active");


            if(data.CONTENT == "" && data.CARD_ID == "" || data.CONTENT == undefined && data.CARD_ID == undefined){
                $("#wenzi").addClass("layui-this actived");
                $("#kaquan").removeClass("layui-this actived");
                $("#tuwen").removeClass("layui-this actived");
                $("#tupian").removeClass("layui-this actived");

                $("#wenziItem").addClass("layui-show");
                $("#kaquanItem").removeClass("layui-show");
                $("#tuwenItem").removeClass("layui-show");
                $("#tupianItem").removeClass("layui-show");

                $("#CONTENT").val("");
                //清空其他
                deleteTuwen();
                deleteImg();
                deleteCard();
            }
            //清空其他
            $("#URL").val("");
            $("#MINI_APPID").val("");
            $("#MINI_PAGEPATH").val("");
            $("#MINI_URL").val("");

        }else if(type == "view"){
            $("#wangye").addClass("active");
            $("#xiaoxi").removeClass("active");
            $("#xiaochengxu").removeClass("active");

            $("#profile").addClass("active");
            $("#home").removeClass("active");
            $("#messages").removeClass("active");


            $("#URL").val(data.URL);


            //清空其他
            $("#MINI_APPID").val("");
            $("#MINI_PAGEPATH").val("");
            $("#MINI_URL").val("");

            $("#tuwen").addClass("layui-this actived");
            $("#tupian").removeClass("layui-this actived");
            $("#wenzi").removeClass("layui-this actived");
            $("#kaquan").removeClass("layui-this actived");

            $("#tuwenItem").addClass("layui-show");
            $("#tupianItem").removeClass("layui-show");
            $("#wenziItem").removeClass("layui-show");
            $("#kaquanItem").removeClass("layui-show");

            deleteTuwen();
            deleteImg();
            $("#CONTENT").val("");
            deleteCard();

        }else if(type == "miniprogram"){
            $("#xiaochengxu").addClass("active");
            $("#wangye").removeClass("active");
            $("#xiaoxi").removeClass("active");

            $("#messages").addClass("active");
            $("#profile").removeClass("active");
            $("#home").removeClass("active");

            $("#MINI_APPID").val(data.MINI_APPID);
            $("#MINI_PAGEPATH").val(data.MINI_PAGEPATH);
            $("#MINI_URL").val(data.URL);

            //清空其他
            $("#URL").val("");

            $("#tuwen").addClass("layui-this actived");
            $("#tupian").removeClass("layui-this actived");
            $("#wenzi").removeClass("layui-this actived");
            $("#kaquan").removeClass("layui-this actived");

            $("#tuwenItem").addClass("layui-show");
            $("#tupianItem").removeClass("layui-show");
            $("#wenziItem").removeClass("layui-show");
            $("#kaquanItem").removeClass("layui-show");

            deleteTuwen();
            deleteImg();
            $("#CONTENT").val("");
            deleteCard();

        }else if(type == "" || type == null || type == undefined){
            $("#xiaochengxu").removeClass("active");
            $("#wangye").removeClass("active");
            $("#xiaoxi").removeClass("active");

            $("#messages").removeClass("active");
            $("#profile").removeClass("active");
            $("#home").removeClass("active");


            //清空其他
            $("#tuwen").removeClass("layui-this actived");
            $("#tupian").removeClass("layui-this actived");
            $("#wenzi").removeClass("layui-this actived");
            $("#kaquan").removeClass("layui-this actived");

            $("#tuwenItem").removeClass("layui-show");
            $("#tupianItem").removeClass("layui-show");
            $("#wenziItem").removeClass("layui-show");
            $("#kaquanItem").removeClass("layui-show");

            deleteTuwen();
            deleteImg();
            $("#CONTENT").val("");
            deleteCard();

            $("#URL").val("");
            $("#MINI_APPID").val("");
            $("#MINI_PAGEPATH").val("");
            $("#MINI_URL").val("");
        }

        if(detail == "WENZI"){
            $("#wenzi").addClass("layui-this actived");
            $("#kaquan").removeClass("layui-this actived");
            $("#tuwen").removeClass("layui-this actived");
            $("#tupian").removeClass("layui-this actived");

            $("#wenziItem").addClass("layui-show");
            $("#kaquanItem").removeClass("layui-show");
            $("#tuwenItem").removeClass("layui-show");
            $("#tupianItem").removeClass("layui-show");

            $("#CONTENT").val(data.CONTENT);
            //清空其他
            deleteTuwen();
            deleteImg();
            deleteCard();

        }else if(detail == "KAQUAN"){
            $("#kaquan").addClass("layui-this actived");
            $("#wenzi").removeClass("layui-this actived");
            $("#tuwen").removeClass("layui-this actived");
            $("#tupian").removeClass("layui-this actived");

            $("#kaquanItem").addClass("layui-show");
            $("#wenziItem").removeClass("layui-show");
            $("#tuwenItem").removeClass("layui-show");
            $("#tupianItem").removeClass("layui-show");

            if(data.CARD_ID == "" || data.CARD_ID == undefined){
                deleteCard();
            }else{
                $("#CARD_ID").val(data.CARD_ID);
                $("#title").html(data.SUB_TITLE);
                $("#title").attr("class","blue title");
                $("#time").html("有效期："+data.AVAILABLE_TIME);
                $("#adrss").html("使用门店："+data.STORE_NAME);
                $("#xiangqing").html("使用说明："+data.DESCRIPTION);
                $("#xzCard").hide();
                $("#jsCard").show();
            }
            //清空其他
            $("#CONTENT").val("");
            deleteTuwen();
            deleteImg();

        }else if(detail == "TUWEN"){
            $("#tuwen").addClass("layui-this actived");
            $("#wenzi").removeClass("layui-this actived");
            $("#kaquan").removeClass("layui-this actived");
            $("#tupian").removeClass("layui-this actived");

            $("#tuwenItem").addClass("layui-show");
            $("#wenziItem").removeClass("layui-show");
            $("#kaquanItem").removeClass("layui-show");
            $("#tupianItem").removeClass("layui-show");

            if(data.MEDIA_ID == "" || data.MEDIA_ID == undefined){
                deleteTuwen();
            }else{
                $("#TUWEN_MEDIA_ID").val(data.MEDIA_ID);
                $("#TUWEN_SENDRECORD_ID").val(data.SENDRECORD_ID);
                $(".padding-30").html("更新于："+data.CREATE_TIME);
                $(".wordTitle").children(":first-child").html(data.mList[0].TITLE);
                $(".widthImg").children(":first-child").attr("src",'<%=basePath%>uploadFiles/uploadImgs/'+data.mList[0].PATH);
                if(data.mList.length > 1){
                    var all = "";
                    for(var i=1;i<data.mList.length;i++){
                        var title = data.mList[i].TITLE;
                        var img = '<%=basePath%>uploadFiles/uploadImgs'+data.mList[i].PATH;
                        var div = '<div class="borBottom">'+
                            '<table width="100%" >'+
                            '<tbody >'+
                            '<tr>'+
                            '<td style="vertical-align: top"><p class="tarTileWid">'+title+'</p></td>'+
                            '<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img src="'+img+'" alt=""></div></td>'+
                            '</tr>'+
                            '</tbody>'+
                            '</table>'+
                            '<div class="imgyulan" onclick="previewTuWen(this)">'+
                            '<p class="text-center" id="tuwen_'+i+'">预览</p>'+
                            '</div>'+
                            '</div>';
                        all += div;
                    }
                    $(".widthImg").after(all);
                }
                $("#newImg").hide();
                $("#textImg").show();
            }
            //清空其他
            $("#CONTENT").val("");
            deleteImg();
            deleteCard();
        }else if(detail == "TUPIAN"){
            $("#tupian").addClass("layui-this actived");
            $("#wenzi").removeClass("layui-this actived");
            $("#tuwen").removeClass("layui-this actived");
            $("#kaquan").removeClass("layui-this actived");

            $("#tupianItem").addClass("layui-show");
            $("#wenziItem").removeClass("layui-show");
            $("#kaquanItem").removeClass("layui-show");
            $("#tuwenItem").removeClass("layui-show");

            if(data.MEDIA_ID == "" || data.MEDIA_ID == undefined){
                deleteImg();
            }else{
                $("#successImg").attr("src","<%=basePath%>uploadFiles/uploadImgs/"+data.PATH);
                $("#IMG_MEDIA_ID").val(data.MEDIA_ID);
                flieImgsuccess();
            }
            //清空其他
            $("#CONTENT").val("");
            deleteTuwen();
            deleteCard();

        }else if(detail == ""){
            $("#tuwen").removeClass("layui-this actived");
            $("#tupian").removeClass("layui-this actived");
            $("#wenzi").removeClass("layui-this actived");
            $("#kaquan").removeClass("layui-this actived");

            $("#tuwenItem").removeClass("layui-show");
            $("#tupianItem").removeClass("layui-show");
            $("#wenziItem").removeClass("layui-show");
            $("#kaquanItem").removeClass("layui-show");

            deleteTuwen();
            deleteImg();
            $("#CONTENT").val("");
            deleteCard();

        }
    }

    var checkName = function(type){
        var name = $("#NAME").val();
        var length = 0;
        for(var i=0;i<name.length;i++){
            if(name.charCodeAt(i) > 255){
                //如果是汉字，则长度+1
                length ++;
            }
        }
        length += name.length;
        if(length == 0){
            layer.tips('请输入菜单名称', '#NAME', {
                tips: 3,
                time: 1000
            });
            return false;
        }else if(length > 10 && type == 'lie'){
            layer.tips('字数超出限制', '#NAME', {
                tips: 3,
                time: 1000
            });
            return false;
        }else if(length > 14 && type == 'hang'){
            layer.tips('字数超出限制', '#NAME', {
                tips: 3,
                time: 1000
            });
            return false;
        }
        return true;
    }

    var checkUrl = function(type){
        var id = "#"+type;
        var url = $(id).val();
        if(url != ""){
            if(url.indexOf("http://") != 0 && url.indexOf("https://") != 0){
                /*
                layer.tips('以http://或https://开头', id, {
                    tips: 3,
                    time: 2500
                });*/
                return false;
            }
        }
        return true;
    }

</script>

</body>
</html>