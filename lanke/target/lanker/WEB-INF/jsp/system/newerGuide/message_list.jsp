<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <base href="<%=basePath%>">
     <%@ include file="../../system/index/top.jsp"%>
    <title>消息通知</title>
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
</head>
<style>
	.layui-table-cell .layui-form-checkbox[lay-skin=primary]{top:6px;}
	.layui-tab-title li {margin: 0 15px;padding:0;}
</style>
<body class="scroll" style="background-color: #f2f2f2;">
	<input type="hidden" value="" id="batch_data">
	<div style="padding:12px;">
		<div class="layui-card" style="height: calc(100vh - 24px);">
        	<div class="layui-tab layui-tab-brief layadmin-latestData">
             	<ul class="layui-tab-title">
                 	<li class="layui-this" id="noRead">未读消息</li>
                 	<li id="readed">已读消息</li>
             	</ul>
             	<div class="layui-tab-content">
             		<form class="layui-form">
						<input type="hidden" id="state" value="0">
						<div class="demoTable table-seach">
							<div class="layui-inline">
								<input class="layui-input" name="keywords" id="keywords" autocomplete="off" placeholder="关键词搜索">
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">消息类型：</label>
								<div class="layui-input-inline">
									<select name="modules" lay-search="" lay-filter="filter" name="type" id="type">
										<option value=""></option>
										<option value="pubwin">计费系统</option>
										<option value="payonline">在线支付</option>
										<option value="jl_pay">嘉联支付</option>
									</select>
								</div>
							</div>
							<span class="btn btn-xm btn-success" style="margin-left: 10px;" id="toSearch">
								<i class="layui-icon" style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索
							</span>
						</div>
					</form>
                 	<div class="layui-tab-item layui-show">
                     	<table id="unread_msg" lay-filter="msg"></table>
					</div>
                 	<div class="layui-tab-item">
                     	<table id="read_msg" lay-filter="msg"></table>
                 	</div>
             	</div>
             	<div class="page-header position-relative" style="padding:8px 10px 16px 10px">
					<table style="width:100%;">
						<tr>
							<td style="vertical-align:top;">
								<div style="float: right;padding-top: 0px;margin-top: 0px;" id="page"></div>
							</td>
						</tr>
					</table>
				</div>
        	</div>
    	</div>
    </div>	
		<form method="post" target="_blank" id="jump_form"></form>
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script type="text/html" id="barDemo">
  	<a class="btn btn-sm btn-success" lay-event="detail">
  	 	<i class="layui-icon" style="padding-right: 2px">&#xe642;</i>查看
  	</a>
</script>
<script type="text/javascript">
	var msg_type = "unread_msg";
	var show = 0;
	var total = 0;
	layui.use(['table','element','laypage','form'], function(){
  		var table = layui.table;
  		var element = layui.element;
  		var form = layui.form;
  		var laypage = layui.laypage;
  		  table.render({
  		  	 id: 'msgReload'
    		,elem: "#"+msg_type
    		,url:'<%=basePath%>newerGuide/loadMessage.do'
    		,cellMinWidth: 60
			,request:{
				pageName:'currentPage',
				limitName:'showCount'
			}
    		,cols: [[
      				 {type:'checkbox', fixed: 'left'}
      				,{type:'numbers', width:80, title: '序号', sort: true, fixed: 'left'}
      				,{field:'title', title: '消息标题', fixed: 'left'}
      				,{field:'type', title: '消息类型', sort: true}
      				,{field:'content', title: '消息内容'}
      				,{field:'announce_time',title: '通知时间',sort: true}
      				,{field:'make',title: '操作', align:'center', toolbar: '#barDemo',fixed: 'right'}
    				]]
    		,text: {none: '暂无相关数据'}
    		,done: function(res, curr, count){
    				//如果是异步请求数据方式，res即为你接口返回的信息。
    				//如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
					$("td[data-field=announce_time]").each(function(i){
						var hh = $(this).children();
						if(hh.html().indexOf(".") > 0){
							hh.html(hh.html().split(".")[0]);
						}
					})
					$("td[data-field=read_time]").each(function(i){
						var hh = $(this).children();
						if(hh.html().indexOf(".") > 0){
							hh.html(hh.html().split(".")[0]);
						}
					})
					pageReload(count,res.showCount);
  				}
  		  });
  		  
  		  table.on('tool(msg)', function(obj){
  				var data = obj.data;
  				var layEvent = obj.event; 
  				var tr = obj.tr; 
  				if(layEvent === 'detail'){ //查看
    				layer.alert(data.content,function(index){
						
						$.ajax({
							type: "POST",
							url: '<%=basePath%>newerGuide/showDetail.do',
							data : {message_id:data.message_id,type:data.type},
							dataType : 'json',
							async:false,
							success : function(data2) {
								layer.close(index);
								if(data2.result == "true"){
									var html = "";
									if(data2.type == "pubwin"){
										html = '<input type="hidden" id="STORE_ID" name="STORE_ID" value="'+data2.STORE_ID+'"><input type="hidden" id="STATE" name="STATE" value="'+data2.STATE+'">';
									}else if(data2.type == "payonline"){
										html = '<input type="hidden" id="idd" name="idd" value="'+data2.idd+'"><input type="hidden" id="store_idd" name="store_idd" value="'+data2.store_idd+'">';
									}else if(data2.type == "jl_pay"){
                                        html = '<input type="hidden" id="idd" name="idd" value="'+data2.idd+'"><input type="hidden" id="store_idd" name="store_idd" value="'+data2.store_idd+'">';
                                    }
									$("#jump_form").html(html);
									$("#jump_form").attr("action", '<%=basePath%>'+data2.backUrl);
									$("#jump_form").submit();
									//清空form
									$("#jump_form").html("");
									$("#jump_form").attr("action", '');
									
									var number = data2.number;
									var aId = window.parent.parent.document.getElementById("aId");
									if(number > 0){
										aId.innerHTML = '消息通知<span class="layui-badge" id="msgNum">'+number+'</span>';
									}else if(number == 0){
										aId.innerHTML = '消息通知';
									}
									
									table.reload('msgReload', {
										elem: "#"+msg_type,
										where: { //设定异步数据接口的额外参数，任意设
											state: $("#state").val(),
											keywords: $("#keywords").val(),
											type: $("#type").val()
										}
									});
								}else{
									message(data2.message);
								}
							},
							error:function(){
								layer.close(index);
								message("系统繁忙，请稍后再试！");
							}
						});
					})
  				}
			});
			
			function pageReload(count, showCount){
				laypage.render({
					elem: 'page' 
					,count: count  //数据总数量
					,limit:showCount
					,limits:[10, 20, 30, 40, 50,60,70,80,90,100]
					,curr: location.hash.replace('#!fenye=', '') //获取起始页
					,hash: 'fenye' //自定义hash值
					,layout:['prev', 'page', 'next','skip','count','limit']
					,theme: '#41a7f0' 
					,jump: function(obj, first){
						//首次不执行
						if(!first){
							//console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
							//console.log(obj.limit); //得到每页显示的条数
							
							table.reload('msgReload', {
								where: { //设定异步数据接口的额外参数，任意设
									state: $("#state").val(),
									keywords: $("#keywords").val(),
									type: $("#type").val(),
									currentPage:obj.curr,
									showCount:obj.limit
								}
							});
									
						}
					}
				});
			}
			$("#toSearch").click(function(){
				table.reload('msgReload', {
					where: { //设定异步数据接口的额外参数，任意设
						state: $("#state").val(),
						keywords: $("#keywords").val(),
						type: $("#type").val()
					}
				});
			})
			$("#noRead").click(function(){
				if($(".layui-this").html() == "已读消息"){
					msg_type = "unread_msg";
					$("#state").val("0");
					table.reload('msgReload', {
						elem: "#"+msg_type,
						where: { //设定异步数据接口的额外参数，任意设
							state: $("#state").val(),
							keywords: $("#keywords").val(),
							type: $("#type").val()
						}
					});
				}
				
			})
			$("#readed").click(function(){
				if($(".layui-this").html() == "未读消息"){
					msg_type = "read_msg";
					$("#state").val("1");
					table.reload('msgReload', {
						elem: "#"+msg_type,
						where: { //设定异步数据接口的额外参数，任意设
							state: $("#state").val(),
							keywords: $("#keywords").val(),
							type: $("#type").val()
						}
					});
				}
			})
			
			
  		})
		
		
  		
</script>
</html>