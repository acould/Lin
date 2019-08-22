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

<body style="background-color:#f3f3f3;">
	<div class="orderlist">
		<ul class="g-tab flex-box">
          <li class="active" data-type="1">未兑奖</li>
          <li data-type="2">兑奖中</li>
          <li data-type="3">已兑奖</li>
          <li data-type="4">已失效</li>
        </ul>
	</div>
    <ul class="g-list">
    </ul>
    <script>
    
    var appendHtml=function(res,_obj){
        var btns = "";
        var htmls = "";
        $.each(res.data.subjectList,function(i,item){
            var type = $(".active").data('type');
            if(item.now_date > item.available_time){
                btns = '<div class="activebox">'+
                            '<p class="btn-act">已失效</p>'+
                            '<p class="p2">已超过奖品有效兑奖日期</p>'+
                        '</div>'
            }
            if(item.STATE == type){
                if(item.STATE == "1" && (item.now_date < item.available_time)){
                    btns = '<div class="activebox" onclick="sqdj(&apos;'+item.MEMBERLOTTERY_ID+'&apos;)">'+
                                '<p class="btn-act">去兑奖</p>'+
                                '<p class="p2">请到收银台兑奖</p>'+
                            '</div>'
                }else if(item.STATE == "2" && (item.now_date < item.available_time)){
                    btns = '<div class="activebox">'+
                                '<p class="btn-act">兑奖中</p>'+
                                '<p class="p2">请等待收银台确认</p>'+
                            '</div>'
                }else if(item.STATE == "3" && (item.now_date < item.available_time)){
                    btns = '<div class="activebox">'+
                                '<p class="btn-act">已兑奖</p>'+
                            '</div>'
                }
                getHtml()
            }else {
                getHtml()
            }
            function getHtml(){
                htmls += '<li class="flex-box">'+
                        '<div class="info">'+
                            '<p class="tit ellipsis">'+item.LOTTERY_NAME+'</p>'+
                            '<p class="time">有效期：'+item.youxiaoqi+'</p>'+
                            '<p class="p5">使用说明：'+item.CONTENT+'</p>'+
                        '</div>'+
                        btns +
                    '</li>'
            }
        });
        _obj.html(htmls);
    } 
    
    var argument={'url' : '<%=basePath%>myMember/ckcj.json','res' : {'type':$('.g-tab .active').data('type')},'success' : appendHtml,'mainbox':$('.g-list')};
    $(function(){ 
        addData(argument);
        $(".g-tab li").click(function() {
        	$(this).parent().find('li').removeClass('active');
        	$(this).addClass('active');
            argument.res.type=$(this).data('type');
            argument.mainbox.empty();
            addData(argument);
        });
    });
    
   function sqdj(id){
		$.confirm('<div class="con">是否确定兑换奖品</div>',[{no:"取消"},{yes:"确定"}],function(type){
		    if(type=="yes"){
		    	var _this=this;
		    	 $.ajax({
 		            type: "POST",
 		            url:'<%=basePath%>myMember/sqdj.do',
 		            data:{mayId:id},
 		            success: function(data) {
 		                data.message = data.data || data.message;
 		               $.alert(data.message);
 		              _this.hide();
						setTimeout(function () { 
							location.reload();
						},800)
 		                },
 		            error:function(){
 		                $.alert("系统繁忙，请稍后再试");
 		                removeDis();
 		            }
 		        });
		    }else if(type=="no"){
		        this.hide();  
		    }
		});
	   
   };
    </script>
</body>

</html>
