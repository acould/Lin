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
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-touch-fullscreen" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="address=no">
	<meta name="screen-orientation" content="portrait">
	<meta name="x5-orientation" content="portrait">
	<title>${intenetName}</title>
	<link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
	<link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
	<link rel="stylesheet" href="<%=basePath%>assets/css/base.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/zepto.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/js/fastclick.js"></script>
	<link rel="stylesheet" href="<%=basePath%>assets/js/dialog.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/dialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/iscroll.js"></script>
	<link rel="stylesheet" href="<%=basePath%>assets/js/swiper-3.2.7.min.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/swiper-3.2.7.jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/mobile.js"></script>
</head>

<body>
	<div class="fixed-bottom schedule">
        <form class="g-form" method="post" data-posturl="<%=basePath%>indexMember/userbook.do" data-dialogmes="提交成功！" data-gotourl="<%=basePath%>intenetmumber/index.do">
		<div class="weui_cells">
            <div class="weui_cell">
                <div class="weui_cell_hd">座位数</div>
                <div class="weui_cell_bd weui_cell_primary text-right">
                    <div class="chose-quantity">
                        <span onclick="setAmount.reduce('#exc-number')" class="minus"><i class="icon-move"></i></span>
                        <input type="text" value="1" name="num" id="exc-number" class="exc-number" readonly>
                        <span onclick="setAmount.add('#exc-number')" class="add"><i class="icon-add"></i></span>
                    </div>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">连座</div>
                <div class="weui_cell_bd weui_cell_primary text-right">
                    <div class="weui-cell__ft">
                        <input class="weui-switch" name="lian" type="checkbox">
                    </div>
                </div>
            </div>
            <label class="weui_cell" for="date">
                <div class="weui_cell_hd">入座时间</div>
                <div class="weui_cell_bd weui_cell_primary text-right">
                    <input class="weui_input needsclick" type="date"  name="rzsj" id="date" onblur="rule.empty($(this),'请选择入座时间')" data-group-state="false">
                </div>
            </label>
        </div>
        <div class="item-box padding-box">
            <div class="tit">可选座位类型</div>
            <div data-gold="30" for="x1" class="flex-box list">
                <input type="radio" class="weui-check" name="radio2" id="x1" checked="checked">
                <a><div class="name">大厅座位</div> <i class="icon-roundcheckfill"></i></a>
            </div>
            <div data-gold="30" for="x2" class="flex-box list">
                <input type="radio" class="weui-check" name="radio2" id="x2">
                <a><div class="name">轻奢卡座</div> <i class="icon-roundcheckfill"></i></a>
            </div>
            <div data-gold="30" for="x3" class="flex-box list">
                <input type="radio" class="weui-check" name="radio2" id="x3">
                <a><div class="name">豪华包间</div> <i class="icon-roundcheckfill"></i></a>
            </div>
        </div>

        <div class="surebtn">
            <div class="btn">立即订座</small></div>
        </div>
        </form>
        <script src="<%=basePath%>newStyle/js/lk_wechat/lk_wechat.js"></script>
        <script type="text/javascript">
            //检查用户绑定数据是否正确
            checkSWUser("book");


            $(function(){
              $('.list').click(function(){
                    $('.weui-check').removeAttr('checked')
                    $(this).find('input').attr('checked','checked')
              }) 
            })
            $('#date').val(new Date().format('yyyy-MM-dd'));
            var setAmount = {
                min:1,
                max:Infinity,
                reg:function(x) {
                    return new RegExp("^[0-9]\\d*$").test(x);
                },
                amount:function(obj, mode) {
                    var x = $(obj).val();
                    if (this.reg(x)) {
                        if (mode) {
                            x++;
                        } else {
                            x--;
                        }
                    }
                    return x;
                },
                reduce:function(obj) {
                    var x = this.amount(obj, false);
                    if (x >= this.min) {
                        $(obj).val(x);
                    } else {
                        $.alert('您最少需订' + this.min+'个座位');
                        $(obj).val(this.min);
                    }
                },
                add:function(obj) {
                    var x = this.amount(obj, true);
                    if (x <= this.max) {
                        $(obj).val(x);                  
                    } else {
                        $.alert('很遗憾,库存不足','','',1000,{animate:'zoomIn',animateout:'zoomOut',className:'favorpop'},false);
                        $(obj).val(this.max);
                    }
                   
                }
            }
        </script>
	</div>
</body>

</html>
