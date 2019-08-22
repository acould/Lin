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
<meta charset="UTF-8">
<title>我的明细</title>
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<meta http-equiv="X-UA-Compatible" content="ie=edge" chrome="1">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="address=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<!--移动端适配，px转化为rem-->
<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">

</head>
<body class="weic weic-money weic-bgfff">
	<div class="weic-navbar">
		<a class="weui-cell  weic-relative weic-gradient-blue weic-borderNone"
			href="javascript:;" style="width: 100%;">
			<div class="weui-cell__bd">
				<p class="weic-money-month">
					<strong><span id="weic-ym">一年内</span>总明细</strong>
				</p>
				<p class="weic-money-total">
					总消费：￥
					<sapn id="totalConsume">${0-totalConsume}</sapn>
					<sapn style="padding-left: 0.4rem">总奖励：￥ <sapn id="totalReward">${totalReward}</sapn> </sapn>
				</p>
				<p class="weic-money-total">
					总充值：￥<span id="totalRechage">${totalRechage}</span>
				</p>
			</div>
			<div class="weic-cell-ft weic-relative weic-date">
				<input type="text" value="" id="weic-time" class="weui-input">
				<i class="iconfont weic-icon-calendar">&#xe65b;</i>
			</div>
		</a>
	</div>

	<div style="padding: 2.7rem 0 1.3rem 0;" id="money-list">
		
	</div>
	<div class="weic-footbar">
		<a class="weui-navbar__item weui-bar__item--on weic-threeline"  href="#tab1" id="all" onclick="change(0);"> 全部 <span class="weic-bottom-line"></span>
		</a> 
		<a class="weui-navbar__item weic-threeline" href="#tab2" onclick="change(1);"> 充值 <span class="weic-bottom-line"></span>
		</a> 
		<a class="weui-navbar__item weic-threeline" href="#tab3" onclick="change(3);"> 消费<span class="weic-bottom-line"></span>
		</a> 
	</div>
</body>


<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="http://at.alicdn.com/t/font_622913_aswvqplxm0lik9.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/js/lk_wechat/lk_wechat.js"></script>
<script>
    $(document).on("click", ".show-alert", function () {
        $.alert("程序员大哥正在飞速开发中，敬请期待！");
    });



	var flow_type = 0;
    var flowTime = ''; //流水时间  年-月
    
    $(function () {
	    //获得日期
        var date=new Date;
        var year=date.getFullYear();
        var month=date.getMonth()+1;
        var monthArr = new Array(12);
        var yearArr = new Array();
        var k = 0;
        var j = 12-month;
        //判断是否有去年
        if(j > 0){
            yearArr[0] = year-1;
            yearArr[1] = year;
        }else{
            yearArr[0] = year;
        }
        //获取去年的几个月份
        for(var i=month+1;i<=12;i++){
            monthArr[k] =(i<10?"0"+i:i);
            k++;
        }
        //获取今年的几个月份
        for(var i=1;i<=month;i++){
            monthArr[j] =(i<10 ? "0"+i:i);
            j++;
        }
        //初始化赋值
        month = month<10?"0"+month:month;
        var dat = year+" "+"-"+" "+month
        $("#weic-time").val(dat);
        //弹出方法
        $("#weic-time").picker({
            title: '当前只能查询最近一年',
            cols: [
                {textAlign: 'center',values: yearArr,},
                {textAlign: 'center',values: ["-"]},
                {textAlign: 'center',values: monthArr,}
            ],
            //关闭
            onClose: function(p, v, dv){
                var sss =$("#weic-time").val();
                sss = sss.replace(/\s+/g,"");
                $("#weic-ym").text(sss+"月")
                flowTime = sss;
				search("time",flow_type);
            },
            //拖拽
            onChange: function(p, v, dv) {
                //月份为两位数的类型转为number，否则找不到数组索引
                if(v[2] >= 10){
                    v[2] = parseInt(v[2])
                }
                var div = document.getElementsByClassName("picker-items-col-wrapper")[0]
                var thisHeight = $(".picker-selected")[0].offsetHeight
                var old_position = monthArr.indexOf(12);
                var position = monthArr.indexOf(v[2]);
                if(month < 12){
                    if(position <= old_position){
                        div.style.transform ='translate3d(0px, '+thisHeight*2+'px, 0px)'
                        div.children[0].classList.add("picker-selected")
                        div.children[1].classList.remove("picker-selected")
                        p.value[0] = (year-1);
                        p.displayValue[0] = (year-1)
                    }else{
                        div.style.transform ='translate3d(0px, '+thisHeight+'px, 0px)'
                        div.children[0].classList.remove("picker-selected")
                        div.children[1].classList.add("picker-selected")
                        p.value[0] = year;
                        p.displayValue[0] = year;
                    }
                }
            }
        });
    })
    //页面加载后先查询所有信息
    $(function () {
		//0-全部  1-充值  2-奖励  3-消费
		search("time",0);
	})
    
    //切换类型时
	function change(type){
		flow_type = type;
		search("type",type);
	}
	//查询全局信息
	var flowList = '';
    function search(flag, type) {
		if(flag == 'time'){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>myMember/myStatementsJson.do',
				data: {flow_time:flowTime},
				dataType:'json',
				cache: false,
				beforeSend: beforeSend,
				success: function(data){
					layer.closeAll();
					flowList = data.flowList;
					$('#totalRechage').text(data.totalRechage);
					$('#totalConsume').text(0-data.totalConsume);
					$('#totalReward').text(data.totalReward);
					
					commonSerach(type,flowList);
				},
				error:function(){
					layer.closeAll();
					message("系统繁忙，请稍后再试");
					return;
				}
			}); 
		}else if(flag == 'type'){
			commonSerach(type,flowList);
		}
		
	}
    function beforeSend(){
		open_loading("加载中")
	}
    
    function commonSerach(flowType,flowList) {
    	flowList.sort(function(a,b){
    	    return Date.parse(b.flow_time) - Date.parse(a.flow_time);//时间正序
    	});
    	//0-全部  1-充值  2-奖励  3-消费
		if(flowType ==0){
			all(flowList);
		}else if(flowType ==1){
			charge(flowList);
		}else if(flowType ==2){
			reward(flowList);
		}else if(flowType ==3){
			consume(flowList);
		}
	}
    
    //flow_type 4-消费   8-收银端充值或扣费  16-第三方充值   32-第三方扣费
    //页面加载查询全部类型数据
    function all(flowList) {
    	var htmls='';
		if(flowList){
			for (var i = 0; i < flowList.length; i++) {
				var a = flowList[i];
				var flowId = a.flow_id;
				
				if(a.flow_type==2 || a.flow_type==4 || a.flow_type==32){
					htmls +='<a class="weui-cell weic-relative weui-cell_access" href="<%=basePath%>myMember/statementDetail?flowId='+flowId+'">'+
					'<div class="weui-cell__bd">'+
						'<p class="weic-money-title">'+a.flow_desc+'-'+a.store_name+'</p>'+
						'<p class="weic-money-time">'+a.flow_time.substring(5,9)+ '<span>'+a.flow_time.substring(9,16)+'</span></p>'+
					'</div>'+
					'<div class="weic-cell-ft">'+
						'<p class="weic-money-sum">'+ a.amount +'</p>'+
						'<p class="weic-money-type">消费</p>'+
					'</div>'+
					'</a>'
				}
				
				<%-- if(a.flow_type==8 && a.reward != 0){
					var desc = "收银端"+a.flow_desc;
					var rew = '<span class="weic-money-sum weic-red">+'+ a.reward +'</span>'
					if(a.reward < 0){
						rew = '<span class="weic-money-sum">'+ a.reward +'</span>'
					}
					htmls +='<a class="weui-cell weic-relative weui-cell_access" href="<%=basePath%>myMember/statementDetail?flowId='+flowId+'">'+
					'<div class="weui-cell__hd weic-relative weic-bg-orange weic-money-leble">奖励</div>'+
					'<div class="weui-cell__bd">'+
						'<p class="weic-money-title">'+desc+"-"+a.store_name+'</p>'+
						'<p class="weic-money-time">'+a.flow_time.substring(5,9)+ '<span>'+a.flow_time.substring(9,16)+'</span></p>'+
					'</div>'+
					'<div class="weic-cell-ft">'+
						rew+
					'</div>'+
					'</a>'
				} --%>
				
				if((a.flow_type==16||a.flow_type==8)){
					var desc = a.flow_desc;
					var paydesc=a.pay_desc;
					if(a.flow_type==16){
						desc = a.memo;
					}else{
						desc = "收银端充值";
					}
					htmls +='<a class="weui-cell weic-relative weui-cell_access" href="<%=basePath%>myMember/statementDetail?desc='+paydesc+'&flowId='+flowId+'">'+
					'<div class="weui-cell__bd">'+
						'<p class="weic-money-title">'+desc+"-"+a.store_name+'</p>'+
						'<p class="weic-money-time">'+a.flow_time.substring(5,9)+ '<span>'+a.flow_time.substring(9,16)+'</span></p>';
						if(paydesc=='b'){
						htmls +='<span class="weic-waiting">充值处理中</span>';
						}
					htmls +='</div>';
					htmls +='<div class="weic-cell-ft" style="padding:0 0.62rem">'+
						'<p class="weic-money-sum weic-orange">+'+ a.reward +'</p>'+
						'<p class="weic-money-type">奖励</p>'+
					'</div>'+
					'<div class="weic-cell-ft">'+
						'<p class="weic-money-sum weic-red">+'+ a.amount +'</p>'+
						'<p class="weic-money-type">充值</p>'+
					'</div>'+
					'</a>'
				}
				
			}
		}
    	if(htmls==''){
        	htmls +='<div class="weic-noboby">'+
        		'<div class="weic-nobody-img"></div>'+
        		'<p class="weic-nobody-text">暂无数据</p>'+
   				'</div>'
    	}
    	$("#money-list").html(htmls);
	}
    
    //查询充值类型数据
    function charge(flowList) {
        var htmls='';
		if(flowList){
			for (var i = 0; i < flowList.length; i++) {
				var a =flowList[i];
				var flowId = a.flow_id;
				if(((a.flow_type==16||a.flow_type==8) && a.amount>0) || a.memo=='揽客优惠券'){
					var desc = a.flow_desc;
					if(a.flow_type==16){
						desc = a.memo;
					}else{
						desc = "收银端充值";
					}
					htmls +='<a class="weui-cell weic-relative weui-cell_access" href="<%=basePath%>myMember/statementDetail?flowId='+flowId+'">'+
					'<div class="weui-cell__bd">'+
						'<p class="weic-money-title">'+desc+"-"+a.store_name+'</p>'+
						'<p class="weic-money-time">'+a.flow_time.substring(5,9)+ '<span>'+a.flow_time.substring(9,16)+'</span></p>'+
					'</div>'+
					'<div class="weic-cell-ft" style="padding:0 0.62rem">'+
						'<p class="weic-money-sum weic-orange">+'+ a.reward +'</p>'+
						'<p class="weic-money-type">奖励</p>'+
					'</div>'+
					'<div class="weic-cell-ft">'+
						'<p class="weic-money-sum weic-red">+'+ a.amount +'</p>'+
						'<p class="weic-money-type">充值</p>'+
					'</div>'+
					'</a>'
				}
			}
		}
        if(htmls==''){
        	htmls +='<div class="weic-noboby">'+
        		'<div class="weic-nobody-img"></div>'+
        		'<p class="weic-nobody-text">暂无数据</p>'+
   				'</div>'
    	}
        $("#money-list").html(htmls);
	}
    
    //查询消费类型数据
    function consume(flowList) {
    	var htmls='';
		if(flowList){
			for (var i = 0; i < flowList.length; i++) {
				var a =flowList[i];
				var flowId = a.flow_id;
				if(a.flow_type==2 || a.flow_type==4 || a.flow_type==32){
					htmls +='<a class="weui-cell weic-relative weui-cell_access" href="<%=basePath%>myMember/statementDetail?flowId='+flowId+'">'+
					'<div class="weui-cell__bd">'+
						'<p class="weic-money-title">'+a.flow_desc+'-'+a.store_name+'</p>'+
						'<p class="weic-money-time">'+a.flow_time.substring(5,9)+ '<span>'+a.flow_time.substring(9,16)+'</span></p>'+
					'</div>'+
					'<div class="weic-cell-ft">'+
						'<p class="weic-money-sum ">'+ a.amount +'</p>'+
						'<p class="weic-money-type">消费</p>'+
					'</div>'+
					'</a>'
				}
			}
		}
    	if(htmls==''){
        	htmls +='<div class="weic-noboby">'+
        		'<div class="weic-nobody-img"></div>'+
        		'<p class="weic-nobody-text">暂无数据</p>'+
   				'</div>'
    	}
    	$("#money-list").html(htmls);
	}
    
   
</script>
</html>