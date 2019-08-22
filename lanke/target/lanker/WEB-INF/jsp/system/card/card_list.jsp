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
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<style>
.demo-class .layui-layer-btn {
	border-top: 1px solid #E9E7E7;
	background-color: #fafafa
}
.main-container:before {
	background: #f3f3f3;
}
</style>
</head>
<body class="no-skin scroll">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<div class="lk-card-navbar" id="card_navbar">

		</div>
		<div class="lk-pages-main">
			<div class="lk-card-state" id="cardState">

			</div>
			<div class="lk-cardlist-content layui-clear">
				<div id="new-cardBox">

				</div>
				<div id="cardBox">

				</div>
			</div>
		</div>
		
		<div class="lk-massObject-box" id="massObject" style="display:none;">
			<div class="lk-massObject-item-card" data-type="fans">
				<div class="fans">
					<div>
						<i class="iconfont">&#xe62a;</i>
						<p>全部粉丝</p>
					</div>
				</div>
			</div>
			<div class="lk-massObject-item-card" data-type="member">
				<div class="member">
					<div>
						<i class="iconfont">&#xe629;</i>
						<p>仅会员</p>
					</div>
				</div>
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<div id="article_page" class="article_page">

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 业务JS -->
	<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-memberMake.js"></script>
	<script type="text/javascript">
        var layer = layui.layer,
            laypage = layui.laypage;
        var cardTypeDate = [{
                "cardType":"CURREN","name":"通用券","img":"<%=basePath%>newStyle/weixin/images/card/common_use.jpg",
                "intro":"适用于自创场景，领取限制由您自行设置，核销无限制",
                "getWay":"",
                "getLimit":"",
                "cancelLimit":"核销限制：卡券有效期之内才能核销成功"
            },{
                "cardType":"NEW","name":"新手券","img":"<%=basePath%>newStyle/weixin/images/card/new.jpg",
                "intro":"适用于为网吧微信公众号吸粉",
                "getWay":"领取方式：会员首次关注微信公众号时，公众号自动发送新手券。取关后重新关注的不会发券",
                "getLimit":"",
                "cancelLimit":"核销限制：卡券有效期之内才能核销成功"
            },{
                "cardType":"OLD","name":"老带新","img":"<%=basePath%>newStyle/weixin/images/card/old_and_new.jpg",
                "intro":"适用于激励老会员带朋友到网吧消费，为网吧带来新会员",
                "getWay":"领取方式： 新会员通过老会员的分享链接获得的新手券，在网吧办理会员卡并成功核销新手券后，微信公众号自动给老会员发送奖励券。如果链接发给了老会员，将不能获得奖励券",
                "getLimit":"",
                "cancelLimit":"核销限制：卡券有效期之内才能核销成功"
            },{
                "cardType":"MAN","name":"男神券","img":"<%=basePath%>newStyle/weixin/images/card/man.jpg",
                "intro":"适用于专门针对男性会员的营销，以身份证号为准",
                "getWay":"领取方式：券设置好后，会员在“会员福利”页面自行领取",
                "getLimit":"领取限制：身份证号码为男性的会员才能领取成功",
                "cancelLimit":"核销限制：身份证号码为男性的会员才能核销成功"
            },{
                "cardType":"WEM","name":"女神券","img":"<%=basePath%>newStyle/weixin/images/card/woman.jpg",
                "intro":"适用于专门针对女性的营销，以身份证号为准",
                "getWay":"领取方式：券设置好后，会员在“会员福利”页面自行领取",
                "getLimit":"领取限制：身份证号码为女性的会员才能领取成功",
                "cancelLimit":"核销限制：身份证号码为女性的会员才能核销成功"
            },{
                "cardType":"BIRTH","name":"生日券","img":"<%=basePath%>newStyle/weixin/images/card/birthday.jpg",
                "intro":"适用于生日当天的会员营销，以身份证号为准",
                "getWay":"领取方式：券设置好后，会员在“会员福利”页面自行领取",
                "getLimit":"领取限制：无",
                "cancelLimit":"核销限制：在生日当天（以身份证号码为准）才能核销成功"
            },{
                "cardType":"GRAB","name":"限时秒抢券","img":"<%=basePath%>newStyle/weixin/images/card/time_limit.jpg",
                "intro":"适用于网吧在特殊限定时间内抢券，刺激会员消费",
                "getWay":"领取方式：券设置好后，会员在“会员福利”页面自行领取",
                "getLimit":"领取限制：会员只能在规定时间内领取，如不在或者超出规定时间都不能领取，库存已被抢完也不能领取",
                "cancelLimit":"核销限制：卡券有效期之内才能核销成功"
            },{
                "cardType":"APPLY","name":"申请会员福利券","img":"<%=basePath%>newStyle/weixin/images/card/member.jpg",
                "intro":"适用于网吧线上办会员的引流活动，为网吧微信公众号吸粉",
                "getWay":"领取方式：用户在线申请会员成功后，系统自动推送，点击领取",
                "getLimit":"",
                "cancelLimit":"核销限制：卡券有效期之内才能核销成功"
            },{
                "cardType":"TERM","name":"上网满时长福利券","img":"<%=basePath%>newStyle/weixin/images/card/full_time.jpg",
                "intro":"适用于网吧给活跃度比较高的会员推送福利，增加会员粘性",
                "getWay":"领取方式：该卡券设置为连续上网满时长类型，则是会员满足时长下机后自动推送领取。设置一周满时长和一月满时长类型，则是会员在“会员福利”页面自行领取",
                "getLimit":"领取限制：连续上网满时长类型只有当会员满足要求时长后才会自动推送，一周、一月满时长类型满足要求才能领取成功",
                "cancelLimit":"核销限制：卡券有效期之内才能核销成功"
            },{
                "cardType":"RUSH","name":"会员抵用券","img":"<%=basePath%>newStyle/weixin/images/card/rush.png",
                "intro":"适用于网吧给充值满足条件的会员送券，提高会员优惠力度",
                "getWay":"领取方式：会员充值后满足条件，系统自动通过微信模板消息推送该卡券，会员手动点击领取",
                "getLimit":"",
                "cancelLimit":"核销限制：卡券有效期之内才能核销成功"
            }]

	function style(othis){
        $(othis).addClass("active");
        $(othis).siblings().removeClass("active")
	}
        
        //渲染分页
        function pageReload(count,showCount,currentPage,cardType,cardState,height) {
        	if(currentPage == 1){
        		laypage.render({
                    elem: 'article_page'
                    , count: count  //数据总数量
                    , limit: showCount
                    , limits: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
                    , curr: currentPage //获取起始页
                    , hash: 'fenye' //自定义hash值
                    , layout: ['prev', 'page', 'next', 'skip', 'count', 'limit']
                    , theme: '#41a7f0'
                    , jump: function (obj, first) {
                        //首次不执行
                        if (!first) {
                        	get_cardMsg(obj.curr,obj.limit,cardType,cardState,"",height);
                        }
                    }
                });
        	}else{
            laypage.render({
                elem: 'article_page'
                , count: count  //数据总数量
                , limit: showCount
                , limits: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
                , curr: location.hash.replace('#!fenye=', '') //获取起始页
                , hash: 'fenye' //自定义hash值
                , layout: ['prev', 'page', 'next', 'skip', 'count', 'limit']
                , theme: '#41a7f0'
                , jump: function (obj, first) {
                    //首次不执行
                    if (!first) {
                    	get_cardMsg(obj.curr,obj.limit,cardType,cardState,"",height);
                    }
                }
            });
        	}
            }
      

		//初始渲染
        get_cardType();
        get_cardList("",0);
		//渲染导航栏
        function get_cardType(){
            var length = cardTypeDate.length,
                div = "";
            for (var i = 0; i < length; i++){
                var className = '';
                if(i == 0)
                    className = 'active';
                div += '<span class="'+className+'" data-type="'+cardTypeDate[i].cardType+'" onclick="get_cardList(this,'+i+')">'+cardTypeDate[i].name+'</span>';
            }
            $("#card_navbar").html(div);
        }
        //渲染卡券基本信息
		function get_cardList(othis,num){
			style(othis);
            var grab_state_html ="";
            var card_box_height = "";
            if(cardTypeDate[num].cardType == "GRAB"){
                card_box_height = "long"
                grab_state_html = '<span onclick=get_cardMsg("","","'+cardTypeDate[num].cardType+'",3,this,"'+card_box_height+'")>未开始</span>'+
                                  '<span onclick=get_cardMsg("","","'+cardTypeDate[num].cardType+'",4,this,"'+card_box_height+'")>已结束</span>'+
                                  '<span onclick=get_cardMsg("","","'+cardTypeDate[num].cardType+'",5,this,"'+card_box_height+'")>正在抢</span>';
            }else {
                grab_state_html = "";
                card_box_height = "short";
            };

            // 新建卡券
            var new_card = '<div class="newcard-box '+card_box_height+'">' +
                '<div class="card-typeImg" style="background-image:url('+cardTypeDate[num].img+')"></div>'+
                '<p class="card-title-js">'+cardTypeDate[num].name+'</p>' +
                '<p class="card-js">'+cardTypeDate[num].intro+'</p>' +
                '<a class="btn btn-sm btn-primary add" onclick=add("'+cardTypeDate[num].cardType+'")>' +
                '<i class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增'+
                '</a>' +
                '<a class="introduce lk-btn lk-btn-sm lk-btn-line-default" onclick="cardDetails('+num+')">详情介绍</a>' +
                '</div>'

			// 卡券状态栏
            var state_html = '<span class="active" onclick=get_cardMsg("","","'+cardTypeDate[num].cardType+'","",this,"'+card_box_height+'")>全部</span>'+
							 '<span onclick=get_cardMsg("","","'+cardTypeDate[num].cardType+'",1,this,"'+card_box_height+'")>已失效</span>' +
							 '<span onclick=get_cardMsg("","","'+cardTypeDate[num].cardType+'",2,this,"'+card_box_height+'")>有效</span>'+grab_state_html;
			$("#cardState").html(state_html);
            $("#new-cardBox").html(new_card);

            //初始渲染卡券数量（全部：0）
            get_cardMsg("","",cardTypeDate[num].cardType,"","",card_box_height);
        }

        // 渲染卡券数量
		function get_cardMsg(currentPage,showCount,cardType,cardState,othis,height){
           $.ajax({
                type: "POST",
                url: '<%=basePath%>card/loadCard.do',
                data: {currentPage:currentPage,showCount:showCount,cardType:cardType,cardState:cardState},
                dataType: 'json',
                beforeSend:beforeSend,
                success: function(data){
                    style(othis)
                    layer.closeAll();
					if(data.result == "true"){
						var list = data.varList,
							state_className = "", //状态class名
							htmls = "",
                            valid_time = "", //有效期
                            get_limit = "", //领取限制
                            cash_card = "", //现金券
                            GRAB_time = "", //限时秒抢券的开始结束时间
                            Internet_time = "", //上网满时长
							btn = ""; //按钮

                        $(list).each(function (i){
						    // 判断卡券状态类型
							switch (list[i].cardState){
								case '1':  //已失效
									state_className = "lose-cardState";
									break;
								case '2':  //有效
									state_className = "";
									break;	
								case '3': //未开始
                                    state_className = "before-cardState";
                                    break;
                                case '4':  //已结束
                                    state_className = "end-cardState";
                                    break;
                                case '5':  //正在抢
                                    state_className = "ing-cardState";
                                    break;
                            }
                            // 判断卡券有效期类型
							if (list[i].TYPE == "DATE_TYPE_FIX_TIME_RANGE") {
                                valid_time = list[i].BEGIN_TIMESTAMP+"至"+list[i].END_TIMESTAMP;
                            }else {
                                if(list[i].FIXED_BEGIN_TERM == 0){
                                    valid_time = '领取后，当天生效'+list[i].FIXED_TERM+'天有效';
                                }else {
                                    valid_time = '领取后，'+list[i].FIXED_BEGIN_TERM+'天后生效'+list[i].FIXED_TERM+'天有效';
                                }
                            }

                            //判断卡券领取限制类型
                            switch (list[i].RECEIVE_DETIL){
                                case 'YEAR':  //年
                                    get_limit = '单人'+list[i].BLANK_NUMBER+'年限领'+list[i].RECEIVE_NUMBER+'次';
                                    break;
                                case 'MON': //月
                                    get_limit = '单人'+list[i].BLANK_NUMBER+'月限领'+list[i].RECEIVE_NUMBER+'次';
                                    break;
                                case 'WEEK':  //周
                                    get_limit = '单人'+list[i].BLANK_NUMBER+'周限领'+list[i].RECEIVE_NUMBER+'次';
                                    break;
                                case 'DAY':  //天
                                    get_limit = '单人'+list[i].BLANK_NUMBER+'天限领'+list[i].RECEIVE_NUMBER+'次';
                                    break;
                            }

                            // 是否是现金券类型
							if(list[i].CARD_TICKET == 1){
                                cash_card = '现金券：<span class="red">'+list[i].CASH_NUMBER+'元</span>';
							}else {
                                cash_card = "";
							}

							// 限时秒抢券含有开始结束时间
							if(cardType == "GRAB"){
                                GRAB_time = '<p>开始时间：'+list[i].garbStart_time+'</p>'+
                                    		'<p>结束时间：'+list[i].garbEnd_time+'</p>'
							}else {
                                GRAB_time == "";
							}

							// 判断上网满时长
                            switch (list[i].cardSum_type){
                                case 1: //连续上网
                                    Internet_time = '连续上网满时长：'+list[i].cardSum_time+'小时';
                                    break;
                                case 2:  //一周上网
                                    Internet_time = '一周上网满时长：'+list[i].cardSum_time+'小时';
                                    break;
                                case 3:  //一月上网
                                    Internet_time = '一月上网满时长：'+list[i].cardSum_time+'小时';
                                    break;
                            }
							
							
                            // 判断按钮数量（不可群发的券：新手券，老带新券，连续上网券，申请会员福利券,冲送券）
                            var ID = list[i].CARD_ID;
                            var is_all = list[i].IS_ALL;
                            var state = list[i].state;
                            var store_id = data.pd.storeIds;
                            var role = data.pd.role;
							if(cardType == "NEW" || cardType == "OLD" || cardType == "APPLY" || cardType == "RUSH" || (cardType == "TERM" && list[i].cardSum_type == 1) ){
                                btn =    '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
						                     '<a class="bghover-success card-btn" title="编辑" href="<%=basePath%>card/goAddCard.do?CARD_ID='+ID+'" target="_blank"><i class="layui-icon" >&#xe642;</i></a>' +
						                '</div>' +
										'<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
											'<span class="bghover-danger card-btn" title="删除" onclick=del("'+ID+'","'+cardType+'","'+height+'")><i class="layui-icon" >&#xe640;</i></span>' +
										'</div>';
							}else if((cardType == "GRAB" && list[i].cardState == 1) || (cardType == "GRAB" && list[i].cardState == 4) || (cardType == "GRAB" && list[i].cardState == 5)){
								if(list[i].cardState == 4){//秒抢券(已结束不能群发,不可编辑)
									btn =    '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
	                                            '<a class="bghover-success card-btn" title="编辑" onclick=edit("'+list[i].cardState+'")><i class="layui-icon" >&#xe642;</i></a>' +
	                                         '</div>' +
					                         '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
						                        '<span class="bghover-danger card-btn" title="删除" onclick=del("'+ID+'","'+cardType+'","'+height+'")><i class="layui-icon" >&#xe640;</i></span>' +
					                         '</div>';
								}else if(list[i].cardState == 1){//秒抢券(已失效不能群发,可以编辑)
								btn =    '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
			                                '<a class="bghover-success card-btn" title="编辑" href="<%=basePath%>card/goAddCard.do?CARD_ID='+ID+'" target="_blank"><i class="layui-icon" >&#xe642;</i></a>' +
			                             '</div>' +
							             '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
								             '<span class="bghover-danger card-btn" title="删除" onclick=del("'+ID+'","'+cardType+'","'+height+'")><i class="layui-icon" >&#xe640;</i></span>' +
							             '</div>';
								}else{//秒抢券(正在抢可以群发,不可编辑)
									 /*btn =   '<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border">' +
										'<span class="bghover-warning card-btn" title="群发" onclick=select_card("memberMarke/cardList.do",memberMarke/cardView.do","'+role+'","'+ID+'","'+store_id+'","'+is_all+'","'+state+'","'+cardType+'")><i class="layui-icon" >&#xe609;</i></span>' +
									'</div>' +*/
                                     btn =
                                     '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
                                        '<a class="bghover-success card-btn" title="编辑" onclick=edit("'+list[i].cardState+'")><i class="layui-icon" >&#xe642;</i></a>' +
                                    '</div>' +
									'<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
										'<span class="bghover-danger card-btn" title="删除" onclick=del("'+ID+'","'+cardType+'","'+height+'")><i class="layui-icon" >&#xe640;</i></span>' +
									'</div>'
								}
							}else{
								if(list[i].cardState == 1){//已失效卡券,不能群发
                                btn =   '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
									        '<a class="bghover-success card-btn" title="编辑" href="<%=basePath%>card/goAddCard.do?CARD_ID='+ID+'" target="_blank"><i class="layui-icon" >&#xe642;</i></a>' +
								        '</div>'+
								        '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
											'<span class="bghover-danger card-btn" title="删除" onclick=del("'+ID+'","'+cardType+'","'+height+'")><i class="layui-icon" >&#xe640;</i></span>' +
										'</div>'
								}else{
	                                /*btn =   '<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border">' +
									'<span class="bghover-warning card-btn" title="群发" onclick=select_card("memberMarke/cardList.do","memberMarke/cardView.do","'+role+'","'+ID+'","'+store_id+'","'+is_all+'","'+state+'","'+cardType+'")><i class="layui-icon" >&#xe609;</i></span>' +
								'</div>' +*/

                                    btn ='<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
							        '<a class="bghover-success card-btn" title="编辑" href="<%=basePath%>card/goAddCard.do?CARD_ID='+ID+'" target="_blank"><i class="layui-icon" >&#xe642;</i></a>' +
						        '</div>'+
								'<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">' +
									'<span class="bghover-danger card-btn" title="删除" onclick=del("'+ID+'","'+cardType+'","'+height+'")><i class="layui-icon" >&#xe640;</i></span>' +
								'</div>'
								}
							}

                            htmls += '<div class="card-box '+state_className+' '+height+'">'+
										'<p class="card-title">'+list[i].TITLE+'</p>'+
										'<div class="card-msg">'+
                                			  Internet_time +
											'<p>有效期：'+valid_time+'</p>'+
											'<p title="'+list[i].store+'">适用门店：'+list[i].store+'</p>'+
											'<p>领取限制：'+get_limit+'</p>'+
											'<p>'+cash_card+'</p>'+
 											'<p>卡券库存： <span class="red">'+list[i].QUANTITY+'</span>/'+(list[i].QUANTITY+list[i].cardSum)+'</p>' +
                                			 GRAB_time +
										'</div>' +
										'<div class="card-btn-box">' +
											'<div class="layui-row">' +
                                				btn +
											'</div>' +
										'</div>' +
                                	'</div>'
                        })

						$("#cardBox").html(htmls);
                        pageReload(data.count,data.showCount,data.currentPage,cardType,cardState,height)
					}
                },
                error: function(){
                    layer.closeAll();
                    message("系统繁忙，请稍后再试");
                }
            });
		}



    //新增卡券
    function add(FAV_TYPE) {
        window.open('<%=basePath%>card/goAddCard.do?FAV_TYPE='+FAV_TYPE);
    }

    function del(Id,cardType,height) {
        layer.confirm('确定要删除吗?', {
            btn: ['确定', '取消'],
            title: '删除',
        }, function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>card/delete.do',
                data: {CARD_ID: Id, tm: new Date().getTime(),cardType:cardType},
                dataType: 'json',
                cache: false,
                success: function (data) {
					layer.msg(data.message, {time: data.message.length*200});
                    if ("true" == data.result) {
						setTimeout(function () {
							get_cardMsg("","",cardType,"","",height);
						}, 500)
                    }
                }
            });
        }, function () {
            return
        });
    }

    //修改
    function edit(state) {
    	if(state == 4){
    		layer.alert("已结束的秒抢券不可编辑");
    	}else{
    		layer.alert("正在抢的秒抢券不可编辑");
    	}
    }
    
	// 卡券详情介绍
	function cardDetails(num) {
        layer.open({
            type: 1,
            title: false,
			area:'380px',
            closeBtn: 0,
            skin: 'layui-layer-nobg', //没有背景色
            shadeClose:true,
            shade:0.7,
            content: '<div class="lk-card-details" id="card-details">'+
						'<div style="background: #eee">'+
							'<img src="'+cardTypeDate[num].img+'" alt="" width="100%">'+
						'</div>'+
						'<p class="title">'+cardTypeDate[num].name+'</p>'+
                		'<p>'+cardTypeDate[num].intro+'</p>'+
                		'<p>'+cardTypeDate[num].getWay+'</p>'+
                		'<p>'+cardTypeDate[num].getLimit+'</p>'+
                		'<p>'+cardTypeDate[num].cancelLimit+'</p>'+
					'</div>',
            move : "#card-details"
        });
    }



</script>
</body>
</html>