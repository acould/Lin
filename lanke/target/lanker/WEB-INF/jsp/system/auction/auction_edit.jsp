<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp" %>
</head>
<body class="no-skin scroll">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">

    <div style="padding: 30px">
        <form class="layui-form layui-form-pane" action="" enctype="multipart/form-data" name="Form" id="Form">
            <input type="hidden" name="Auction_ID" id="Auction_ID" value="${pd.Auction_ID}"/>
            <input type="hidden" name="MPICTURES_ID" id="MPICTURES_ID"
                   value="${pictures.MPICTURES_ID}"/>
            <input type="hidden" name="STATE" id="STATE">
            <div class="layui-form-item">
                <label class="layui-form-label">商品名称：</label>
                <div class="layui-input-block">
                    <input type="text" name="ANAME" placeholder="请输入商品名称,限16字" class="layui-input"
                           id="ANAME" value="${pd.ANAME}" maxlength="16" onblur="hasName()">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">商品图片：</label>
                <div class="layui-input-block">
                    <div style="padding: 4px 30px;">
                        <span class="layui-btn  layui-btn-sm" id="tailor_up"><i
                                class="layui-icon">&#xe67c;</i>裁剪上传</span>


                        <c:if test="${pictures != null }">
                            <div id="imagesbox" style="margin-top: 10px">
                                <a href="<%=basePath%>uploadFiles/uploadImgs/${pictures.PATH}" target="_blank">
                                    <img class="imgTd" name="imgTd" id="imgTd" width='200'
                                         height='180'
                                         src="<%=basePath%>uploadFiles/uploadImgs/${pictures.PATH}"/>
                                </a>
                            </div>
                        </c:if>
                        <c:if test="${pictures == null }">
                            <div id="imagesbox" style="margin-top: 10px">
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">

            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">门店：</label>
                <div class="layui-input-block">
                    <select name="STORE_ID" id="STORE_ID">
                <c:forEach items="${storeList}" var="var" varStatus="vs">
                    <option value="${var.STORE_ID }" >${var.STORE_NAME }</option>
                </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">发货方式：</label>
                <div class="layui-input-block">
                    <select name="DELIVERY" id="DELIVERY">
                        <option value="1">到店提</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">商品排序：</label>
                <div class="layui-input-block">
                    <input type="number" min="1" max="10000" name="STORING" id="STORING" class="layui-input"
                           placeholder="请输入商品序号，数字越小排序越前" value="${pd.STORING}">
                </div>
            </div>
            <div class="layui-form-item" pane="">
                <label class="layui-form-label">购买方式</label>
                <div class="layui-input-block" id="payInt">
                    <input id="isint" type="checkbox" name="payInt" title="积分购买" lay-skin="primary"
                           lay-filter="checkbox" value="I"
                           <c:if test="${pd.payInt == 'I'}">checked</c:if> >
                    <input id="iscash" type="checkbox" name="payCash" title="现金购买" lay-skin="primary"
                           lay-filter="checkbox" value="M"
                           <c:if test="${pd.payCash == 'M'}">checked</c:if> >
                </div>
            </div>
            <div class="layui-form-item" id="I" style="<c:if test="${pd.payInt != 'I'}">display:none</c:if>">
                <label class="layui-form-label">消耗积分：</label>
                <div class="layui-input-block">
                    <input id="iii2" value="${pd.INTEGRAL}" name="INTEGRAL" type="number" class="layui-input" min="0"
                           placeholder="请输入购买该商品应消耗积分数">
                </div>
            </div>
            <div class="layui-form-item" id="M" style="<c:if test="${pd.payCash != 'M'}">display:none</c:if>">
                <label class="layui-form-label">支付现金：</label>
                <div class="layui-input-block">
                    <input id="ppp2" value="${pd.price}" name="price" type="number" class="layui-input" min="0"
                           placeholder="请输入购买该商品应支付现金额">
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">商品详情：</label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入商品详情介绍，限50字" maxlength="50" class="layui-textarea" name="CONTENT"
                              id="CONTENT">${pd.CONTENT}</textarea>
                </div>
                <p id="zishu" style="color: #999;text-align: right;font-size: 12px;">50/剩余字数</p>
            </div>
        </form>
    </div>
</div>

<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp" %>
<!-- 下拉框 -->
<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script type="text/javascript">
    layui.use(['form', 'layer'], function () {
        var form = layui.form,
            layer = layui.layer;
        form.on('checkbox(checkbox)', function (data) {
            var id = "#" + data.elem.value;
            if (data.elem.checked) {
                $(id).show();
            } else {
                $(id).hide();
            }
        });
        $("#tailor_up").click(function () {
            parent.layer.open({
                btn: ['确定', '关闭'],
                btn1: function (index, layero) {
                    //按钮的回调
                    var res = parent.window["layui-layer-iframe" + index].save();
                    if (res != undefined) {
                        parent.layer.close(index);
                        var htmls = '<img src="' + res + '" alt="" width="200px" height="180px">' +
                            '<input type="hidden" value="' + res + '" id="DATA_IMAGE" name="DATA_IMAGE">';
                        $("#imagesbox").html(htmls);
                    }
                },
                type: 2,
                title: false,
                shadeClose: false,
                closeBtn: 0,
                shade: 0.8,
                area: ['800px', '500px'],
                fixed: false, //不固定
                content: ['<%=basePath%>/auction/goTailor'],
            })
        })
    })


    $(function () {
        var content = $("#CONTENT").val();
        var num = 50 - parseInt(content.length);
        $("#zishu").text(num + "/剩余字数");
    })

    function checkNum() {
        var content = $("#CONTENT").val();
        if (content.length > 50) {
            layer.tips('超出字数限制', '#CONTENT', {
                tips: 3
            });
            $("#CONTENT").focus();
        } else {
            var num = 50 - parseInt(content.length);
            $("#zishu").text(num + "/剩余字数");
        }
    }


    //保存
    var baocun = function (type) {
       /* if ($("#ANAME").val() == "") {
            layer.tips('请填写有效商品名称', '#ANAME', {
                tips: 3
            });
            $("#ANAME").focus();
            return false;
        }*/

        if ($("#imagesbox").html().trim() == "") {
            layer.tips('请添加商品图片', '#tailor_up', {
                tips: 3
            });
            return false;
        }
        if ($("#STORING").val() == "" || $("#STORING").val() < 0) {
            layer.tips('请填写有效排序', '#STORING', {
                tips: 3
            });
            $("#STORING").focus();
            return false;
        }
        if ($("#STORING").val() > 10000) {
            layer.tips('序号最大可设为10000', '#STORING', {
                tips: 3
            });
            $("#STORING").val(10000);
            $("#STORING").focus();
            return false;
        }

        var isint = $("#isint").get(0).checked;
        var iscash = $("#iscash").get(0).checked;
        if (isint == false && iscash == false) {
            layer.tips('请选择购买方式', '#payInt', {
                tips: 3
            });
            return false;
        }

        if (isint) {
            if ($("[name='INTEGRAL']").val() == "" || $("[name='INTEGRAL']").val() == 0) {
                layer.tips('请输入积分', '#iii2', {
                    tips: 3
                });
                return false;
            }
        }
        if (iscash) {
            if ($("[name='price']").val() == "" || $("[name='price']").val() == 0) {
                layer.tips('请输入金额', '#ppp2', {
                    tips: 3
                });
                return false;
            }

        }


        if (document.getElementById("CONTENT").value == '') {
            layer.tips('请填写商品详情', '#CONTENT', {
                tips: 3
            });
            $("#CONTENT").focus();
            return false;
        }
        if ($("#Auction_ID").val() != "") {
            //    取消勾选时 给他默认设为0
            if (isint == false) {
                $("[name='INTEGRAL']").val(0);
                $("input[name='payInt']").attr("checked",false);
            }

            if (iscash == false) {
                $("[name='price']").val(0);
                $("input[name='payCash']").attr("checked",false);
            }
        }


        if (type == "1") {
            $("#STATE").val("1");
        } else if (type == "2") {
            $("#STATE").val("2");
            if ($("#Auction_ID").val() != "") {
                //修改，仅保存时，不修改状态
                $("#STATE").val("");
            }
        }
        /*默认赋值 否则会报错妈的*/
        if ($("[name='INTEGRAL']").val() == "") {
            $("[name='INTEGRAL']").val(0);
        }
        if ($("[name='price']").val() == "") {
            $("[name='price']").val(0);
        }

        $.ajax({
            type: "POST",
            url: '<%=basePath%>auction/saveAuction.do',
            data: $('#Form').serialize(),
            dataType: 'json',
            cache: false,
            success: function (data) {
                layer.msg(data.message);
                if (data.result == "true") {
                    setTimeout(function () {
                        parent.location.reload();
                    }, 800)
                } else {
                    if (data.type == "name_repeat") {
                        $("#ANAME").focus();
                    }
                }
            },
            error: function () {
                layer.msg("系统繁忙，请稍后再试！");
                return false;
            }
        });

    }

    function hasName() {
        $.ajax({
            type: "POST",
            url: '<%=basePath%>auction/hasName.do',
            data: $('#Form').serialize(),
            dataType: 'json',
            cache: false,
            success: function (data) {
                if (data.result == "false") {
                    layer.msg(data.message);
                    $("#ANAME").focus();
                }
            },
            error: function () {
                layer.msg("系统繁忙，请稍后再试！");
                return false;
            }
        });
    }

</script>
</body>
</html>