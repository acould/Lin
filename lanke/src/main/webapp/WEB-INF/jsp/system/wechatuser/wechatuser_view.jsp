<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
<link rel="icon" type="text/css"
	href="<%=basePath%>newStyle/images/lk-icon.png">
<title>会员详情</title>
<style>
body {
	background-color: #f6f8f9;
	min-width: 1100px;
	overflow: auto
}

.lanke-sel {
	vertical-align: middle;
	width: 88px;
	display: inline-block;
	height: 26px;
}

.lanke-member-tab .layui-form {
	width: 100%;
}

.layui-table td, .layui-table th {
	text-align: center;
}

.layui-table-page .layui-laypage {
	margin: 0;
	float: right;
}
</style>
</head>
<body class="lanke-userDate lanke lanke-selectUser">
	<div class="card-header">
		<div class="card-header-msg">
			<img src="<%=basePath%>newStyle/images/logo2.png" class="card-lankeIcon"></img><span
				class="card-header-title">网吧管理后台</span>
			<div class="card-header-right">
				<img alt="" src="<%=basePath %>static/ace/avatars/user.jpg" class="">
				<span class="card-userName layui-elip"> <small>Welcome</small><br>
					${pd.INTENET_NAME}
				</span>
			</div>
		</div>
	</div>
	<div class="lanke-member-width">
		<h1>会员详情</h1>
		<div class="lanke-member-msg lanke-member">
			<div class="layui-row layui-col-space15">
				<div
					class="layui-col-md3 layui-col-xs3 layui-col-sm3 layui-col-lg3  lanke-dis-tab">
					<div class="lanke-member-i lanker-i-mem">
						<i class="iconfont" style="font-size: 24px !important">&#xe620;</i>
					</div>
					<div class="lanke-member-txt">
						<p>会员姓名：${user.NAME}</p>
						<p>微信昵称：${pd.NECK_NAME}</p>
					</div>
				</div>
				<div
					class="layui-col-md5 layui-col-xs5 layui-col-sm5 layui-col-lg5  lanke-dis-tab"
					style="padding-left: 35px;">
					<div class="lanke-member-i lanker-i-memcard">
						<i class="iconfont">&#xe640;</i>
					</div>
					<div class="lanke-member-txt">
						<p>会员卡号：${user.CARDED}</p>
						<p>身份证号：${user.CARDED}</p>
					</div>
				</div>
				<div
					class="layui-col-md4 layui-col-xs4 layui-col-sm4 layui-col-lg4  lanke-dis-tab">
					<div class="lanke-member-i lanker-i-grade">
						<i class="iconfont" style="font-size: 22px !important;">&#xe68a;</i>
					</div>
					<div class="lanke-member-txt">
						<p>会员级别：${user.MEMBER_LEVEL}级</p>
						<p>手机号码：${user.PHONE}</p>
					</div>
				</div>
				<div
					class="layui-col-md3 layui-col-xs3 layui-col-sm3 layui-col-lg3 lanke-dis-tab">
					<div class="lanke-member-i lanker-i-sum">
						<i class="iconfont">&#xe631;</i>
					</div>
					<div class="lanke-member-num">
						<p>
							￥${user.OVERAGE}<span>卡余额</span>
						</p>
					</div>
				</div>
				<div
					class="layui-col-md5 layui-col-xs5 layui-col-sm5 layui-col-lg5  lanke-dis-tab"
					style="padding-left: 35px;">
					<div class="lanke-member-i lanker-i-award">
						<i class="iconfont">&#xe614;</i>
					</div>
					<div class="lanke-member-num">
						<p>
							￥${user.REWARD}<span>奖励金额</span>
						</p>
					</div>
				</div>
				<div
					class="layui-col-md4 layui-col-xs4 layui-col-sm4 layui-col-lg4  lanke-dis-tab">
					<div class="lanke-member-i lanker-i-integral">
						<i class="iconfont">&#xe609;</i>
					</div>
					<div class="lanke-member-num">
						<p>${user.USABLE_INTEGRAL}<span>可用积分</span>
						</p>
					</div>
				</div>
				<div
					class="layui-col-md12 layui-col-xs12 layui-col-sm12 layui-col-lg12  lanke-dis-tab">
					<div class="lanke-member-i lanker-i-lable">
						<i class="iconfont">&#xe63c;</i>
					</div>
					<div class="lanke-member-lable">
						<div class="lanke-lable-item">
							会员标签： <span>消费能力${consume}</span> <span>余额${balance}</span> <span>活跃度${live}</span>
						</div>
					</div>
				</div>
				<div
					class="layui-col-md12 layui-col-xs12 layui-col-sm12 layui-col-lg12  lanke-dis-tab">
					<div class="lanke-member-i lanker-i-yearsMoney">
						<i class="iconfont">&#xe616;</i>
					</div>
					<div class="lanke-member-lable">
						<div class="lanke-lable-item">
							半年累计总明细： <span class="totalConsume2" id="totalConsume2">总消费：￥</span> <span  id="totalReward2"
								class="totalReward2">总奖励：￥</span>
						</div>
					</div>
				</div>
				<div
					class="layui-col-md12 layui-col-xs12 layui-col-sm12 layui-col-lg12  lanke-dis-tab">
					<div class="lanke-member-i lanker-i-accRecords">
						<i class="iconfont">&#xe619;</i>
					</div>
					<div class="lanke-member-lable">
						<div class="lanke-lable-item">
							半年累计上网： <span class="time"></span> <span class="times"></span> <span
								class="onlineConsume"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<h1>流水详情</h1>
		<div class="lanke-member-tab">
			<div class="lanke-tab-lf">
				<span id="month2">半年内</span>流水总计 <span class="totalConsume" ></span> <span class="totalReward"></span>
			</div>
			<table class="tablemargin lanke-tab-rt" style="margin-top: 0;">
				<tr>
					<td>
						<div class="nav-search">
							<span class="input-icon">
								<input type="text" placeholder="这里输入订单号" class="nav-search-input" id="nav-search-input" value="" autocomplete="off" name="keywords" />
								<i class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
						</div>
					</td>
					<td style="vertical-align: middle; padding-left: 15px;">
						<lable class="lablepd">时间：</lable>
						<input type="text" class="span10 date-picker" name="flowTime" readonly="readonly" title="消费时间" id="flowTime" placeholder="消费时间" style="width: 88px;">
					<td style="vertical-align: middle; padding-left: 15px;">
						<form class="layui-form" action="">
							<div class="layui-inline">
								<lable class="lablepd">类型：</lable>
								<div class="layui-input-inline">
									<select name="modules" lay-verify="required" lay-search="" lay-filter="filter">
										<option value="">选择或搜索</option>
										<option value="">全部</option>
										<option value="2">上机</option>
										<option value="4">消费</option>
										<option value="8">收银端操作</option>
										<option value="16">第三方充值</option>
										<option value="32">第三方扣费</option>
									</select>
								</div>
							</div>
						</form>
					</td>
					<td style="vertical-align: middle; padding-left: 10px">
						<a class="btn btn-success btn-sm" title="检索" id="flowSerach" >
							<i class="layui-icon" style="padding-right: 4px">&#xe615;</i>搜索
						</a>
					</td>
				</tr>
			</table>
			<!-- 流水表单 -->
			<div style="width: 100%;padding-top: 40px">
				<table id="flow-table" lay-filter="test"></table>
			</div>

		</div>
		<h1>上机详情</h1>
		<div class="lanke-member-tab">
			<div class="lanke-tab-lf">
				<span id="month3">3个月内</span>上网总计 <span class="time"></span> <span class="times"></span><span
					class="onlineConsume"></span>
			</div>
			<table class="tablemargin lanke-tab-rt" style="margin-top: 0;">
				<tr>
					<td style="vertical-align: middle; padding-left: 15px;"><lable
							class="lablepd">时间：</lable><input value="${pd.lastLoginStart}"
						class="span10 date-picker" name="onlineStart" id="onlineStart"
						type="text" readonly="readonly" style="width: 88px;"
						placeholder="上机日期" title="上机日期" /></td>
					<td style="vertical-align: middle; padding-left: 10px"><a
						class="btn btn-success btn-sm" id="search" title="检索"> <i
							class="layui-icon" style="padding-right: 4px">&#xe615;</i>搜索
					</a></td>
				</tr>
			</table>
			<!-- 检索  -->
			<!-- 上机流水表 -->
			<div style="width: 100%;padding-top: 40px">
				<table id="online-table" lay-filter="test"></table>
			</div>
		</div>
	</div>
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
	<script src="<%=basePath%>newStyle/layui/layui.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
	<script>

var storeId = '${user.STORE_ID}';
var USER_ID = '${user.USER_ID}'
var card = '${user.CARDED}';
var userFlow;
var time;
var select;
var keyword;
var time1 = "";
var time2 = "";

$('#nav-search-input').keyup(function() {
    keyword = $(this).val();
})

//换算时间
function toString (mins) {
    var stringTime = '';
    if (mins < 60) {
        stringTime = mins + '分钟';
    } else {
        var minString = mins%60 ? ( mins%60 + '分钟') : '';
        var hour = parseInt(mins/60);
        if (hour < 24) {
            stringTime = hour + '小时' +  minString;
        } else {
            var dayString = parseInt(hour/24);
            var hourString = hour%24 ? ( hour%24 + '小时') : '';
            stringTime = dayString + '天' + hourString +  minString;
        }
    }
    return stringTime;
}


var sub = false,num = false;
layui.use(['laydate','form','table'], function(){
	var laydate = layui.laydate,
		form = layui.form,
	   table = layui.table;

    $('#search').click(function() {
        search();
    });
    $('#flowSerach').click(function(){
        tosearch(2)
    });

	tosearch(1);//搜索用户流水
	search();//搜索用户上机信息
	function closeLoading(){
	    if(sub == true && num == true){
	        layer.closeAll()
		}
	}
	laydate.render({
		elem: '#flowTime'
		,type: 'month'
		,done: function(value, date, endDate){
			time1=value;
		}
	});
	laydate.render({
		elem: '#onlineStart'
		,type: 'month'
		,done: function(value, date, endDate){
			time2=date.year+"-"+date.month;
		}
	});

	form.on('select(filter)', function(data){
		select = data.value;
	});

	function tosearch(flag){
		//获取流水
		$.ajax({
			type: "POST",
			url: '<%=basePath%>wechatuser/userFlow.do',
			data: {USER_ID:USER_ID,card_id:card,flow_time:time1,flow_type:select,order_id:keyword},
			dataType:'json',
			beforeSend: loading('加载中'),
			success: function(data){
				var flowList = data.flowList;
				if (!flowList){
					$(".totalConsume").text("总消费：￥ 无");
					$(".totalReward").text("总奖励：￥ 无");
					if(flag == 1){
						$("#totalConsume2").text("总消费：￥ 无");
						$("#totalReward2").text("总奖励：￥ 无");
					}
				}else{
					$(".totalConsume").text("总消费：￥"+data.amount);
					$(".totalReward").text("总奖励：￥"+data.reward);
					if(flag == 1){
						$("#totalConsume2").text("总消费：￥"+data.amount);
						$("#totalReward2").text("总奖励：￥"+data.reward);
					}
				}
				if(time1 != ""){
					$("#month2").text(time1.split("-")[1]+"月");
				}else{
					$("#month2").text("半年内");
				}
				table.render({
					elem: '#flow-table'
					,limit:10
					,data: flowList //数据接口
					,page: true //开启分页
					,cols: [[ //表头
						{field: 'flow_desc', title: '流水类型' }
						,{field: 'store_name', title: '消费门店' }
						,{field: 'amount', title: '流水金额'}
						,{field: 'reward', title: '奖励金额' }
						,{field: 'flow_time', title: '消费时间',width:174 }
						,{field: 'order_id', title: '订单号'}
						,{field: 'pay_desc', title: '付款方式'}
						,{field: 'memo', title: '详情' }
					]]
					,done: function(res, curr, count){
                        sub = true;
                        closeLoading();
					}
				});
			},
			error:function(){
                sub = true;
                closeLoading();
				layer.msg("系统繁忙，请稍后再试！",{time:1500});
			}
		});
	}

	function search() {
		//获取上网记录
		$.ajax({
			type: "POST",
			url: '<%=basePath%>wechatuser/userOnline.do',
			data: {card_id:card,up_time:time2},
			dataType:'json',
			beforeSend: loading('加载中'),
			success: function(data){
				var onlineList = data.list;
				if (data.time == null){
					$(".time").text("总上网时长：0 分钟");
				}else{
					$(".time").text("总上网时长："+toString (data.time));
				}
				if (data.times == null){
					$(".times").text("总次数：0 次");
				}else{
					$(".times").text("总次数："+data.times+"次");
				}
				if (data.money == null){
					$(".onlineConsume").text("总消费：￥ 无");
				}else{
					$(".onlineConsume").text("总消费：￥"+data.money);
				}
				if(time2 != "" && time2 != "undefined-undefined"){
					$("#month3").text(time2.split("-")[1]+"月");
				}else{
					$("#month3").text("3个月内");
				}
				table.render({
					elem: '#online-table'
					,limit:10
					,data: onlineList //数据接口
					,page: true //开启分页
					,cols: [[ //表头
						{field: 'store_name', title: '消费门店' }
						,{field: 'up_time', title: '上机时间'}
						,{field: 'down_time', title: '下机时间' }
						,{field: 'up_duration', title: '上网时长',width:174 }
						,{field: 'consume_fee', title: '消费金额'}
					]]
					,done: function(res, curr, count){
                        num = true;
                        closeLoading();
					}
				});
			},
			error:function(){
                num = true;
                closeLoading();
				layer.msg("系统繁忙，请稍后再试！",{time:1500});
				return;
			}
		});
	}
})
</script>
</body>
</html>