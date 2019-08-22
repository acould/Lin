<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>Document</title>
    <style>
        .borPadding {
            padding:20px 30px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
    </style>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
  <div class="borPadding">
        <form class="layui-form layui-form-pane" name="From" id="From" action="<%=basePath%>group/${msg }.do">
        	<input type="hidden" name="msgg" id="msgg" value="${msg }"/>
        	<input type="hidden" name="STATE" id="STATE" value="${pd.STATE}"/>
        	<input type="hidden" name="GROUP_ID" id="GROUP_ID" value="${pd.GROUP_ID}"/>
            <div class="layui-form-item" pane="" id="xuan">
                <label class="layui-form-label">适用门店:</label>
                <div class="layui-input-block" id="checker">
					<c:choose>
						<c:when test="${not empty storeList}">
							<c:forEach items="${storeList}" var="var" varStatus="vs">
									<!--<input type="checkbox" name="STORE_ID" id="STORE_ID" value="${var.STORE_ID }" title="${var.STORE_NAME }" lay-filter="store" lay-skin="primary" />-->
									<c:if test="${sb != null }">
										<input type="checkbox" name="STORE_ID" id="STORE_ID" value="${var.STORE_ID }" <c:if test="${sb.indexOf(var.STORE_ID)>-1 }">checked="checked" </c:if> title="${var.STORE_NAME }" lay-filter="store" lay-skin="primary" />
									</c:if>
									<c:if test="${sb == null }">
										<input type="checkbox" checked="checked" name="STORE_ID" id="STORE_ID" value="${var.STORE_ID }" title="${var.STORE_NAME }" lay-filter="store" lay-skin="primary"/>
									</c:if>
							</c:forEach>
						</c:when>
					</c:choose>
						<input type="checkbox" name="allChecked" id="allChecked" onclick="DoCheck()" lay-skin="primary" title="全选" lay-filter="tufure" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">战局名称：</label>
                <div class="layui-input-block">
                    <input type="text" name="NAME" id="NAME" value="${pd.NAME}" autocomplete="off" placeholder="请输入标题" class="layui-input" />
                </div>
            </div>
            <!--<div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">服务器名：</label>
                <div class="layui-input-block">
                    <input type="text" name="title" autocomplete="off" placeholder="请输入战场名称" class="layui-input" />
                </div>
            </div>-->
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">比赛时间：</label>
                <div class="layui-input-block" >
                    <input type="text" class="layui-input" name="BEGIN_TIME" id="BEGIN_TIME" value="${pd.BEGIN_TIME}" placeholder="yyyy-MM-dd HH:mm:ss" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">报名截止：</label>
                <div class="layui-input-block" >
                    <input type="text" class="layui-input" name="BM_TIME" id="BM_TIME" value="${pd.BM_TIME}" placeholder="yyyy-MM-dd HH:mm:ss" />
                </div>
            </div>
           
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">战队数量：</label>
                <div class="layui-input-block">
                    <input type="number" name="TEAM_NUMBER" id="TEAM_NUMBER" value="${pd.TEAM_NUMBER}" autocomplete="off" placeholder="请输入战队数量" class="layui-input" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">每队人数：</label>
                <div class="layui-input-inline" >
                    <input type="number" name="MIN_NUMBER" id="MIN_NUMBER" value="${pd.MIN_NUMBER}" autocomplete="off" placeholder="请输入最低人数" class="layui-input" />
                </div>
                <div class="layui-input-inline" >
                    <input type="number" name="MAX_NUMBER" id="MAX_NUMBER" value="${pd.MAX_NUMBER}" autocomplete="off" placeholder="请输入最高人数" class="layui-input" />
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
				<label class="layui-form-label">比赛详情：</label>
            	<div class="layui-input-block">
            		<textarea class="layui-textarea" name="CONTENT" id="CONTENT" value="${pd.CONTENT}" placeholder="这里输入比赛详情,限50字" title="请输入内容" maxlength="50" onkeyup="checkNum()">${pd.CONTENT }</textarea>
					<div>
						<p id="zishu" style="color: #999;text-align: right;font-size: 12px;">50/剩余字数</p>
					</div>
				</div>
			</div>
        </form>
    </div>

<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<script type="text/javascript">
		$(top.hangge());
		if($("#msgg").val()=="save"){//新增时，默认通用
			document.getElementById("allChecked").checked = true;
			var ch = document.getElementsByName("STORE_ID");
			for (var i = 0; i < ch.length; i++) {
				ch[i].checked = true;
			}
		}
		
		$(function(){
			var content = $("#CONTENT").val();
			var num = 50 - parseInt(content.length);
			$("#zishu").text(num+"/剩余字数");
		})
		function checkNum(){
			var content = $("#CONTENT").val();
			if(content.length > 50){
				layer.tips('超出字数限制', '#CONTENT', {
    				tips: 3
    			});
				$("#CONTENT").focus();
			}else{
				var num = 50 - parseInt(content.length);
				$("#zishu").text(num+"/剩余字数");
			}
		}
		
		//保存
		var baocun = function () {
			document.getElementById("STATE").value="baocun";
			if($("input:checkbox[name='STORE_ID']:checked").length <1 ){
				layer.tips('请选择适用门店', '#xuan', {
    					tips: 3
    				});
				return false;
			}
            if($("#NAME").val()==""){
		        layer.tips('请输入战局名称', '#NAME', {
    					tips: 3
    				});
				return false;
			}
			if($("#BEGIN_TIME").val()==""){
				layer.tips('请输入比赛开始时间', '#BEGIN_TIME', {
    					tips: 3
    				});
				return false;
			}
			if($("#BM_TIME").val()==""){
				layer.tips('请输入报名截止时间', '#BM_TIME', {
    					tips: 3
    				});
				return false;
			}
			if($("#TEAM_NUMBER").val()==""){
				layer.tips('请输入战队数量', '#TEAM_NUMBER', {
    					tips: 3
    				});
				return false;
			}
			if($("#MIN_NUMBER").val()==""){
				layer.tips('请输入队伍最低人数', '#MIN_NUMBER', {
    					tips: 3
    				});
				return false;
			}
			if($("#MAX_NUMBER").val()==""){
				layer.tips('请输入队伍最高人数', '#MAX_NUMBER', {
    					tips: 3
    				});
				return false;
			}
			if($("#CONTENT").val()==""){
				layer.tips('请输入比赛详情', '#CONTENT', {
    					tips: 3
    				});
				return false;
			}
			$("#From").submit();
            var data = {
            	msg : true
            };
            return data;
        }
        
        //发布
		var fabu = function () {
			document.getElementById("STATE").value="fabu";
            if($("#NAME").val()==""){
		        layer.tips('请输入战局名称', '#NAME', {
    					tips: 3
    				});
				return false;
			}
			if($("#BEGIN_TIME").val()==""){
				layer.tips('请输入比赛开始时间', '#BEGIN_TIME', {
    					tips: 3
    				});
				return false;
			}
			if($("#BM_TIME").val()==""){
				layer.tips('请输入报名截止时间', '#BM_TIME', {
    					tips: 3
    				});
				return false;
			}
			if($("#TEAM_NUMBER").val()==""){
				layer.tips('请输入战队数量', '#TEAM_NUMBER', {
    					tips: 3
    				});
				return false;
			}
			if($("#MIN_NUMBER").val()==""){
				layer.tips('请输入队伍最低人数', '#MIN_NUMBER', {
    					tips: 3
    				});
				return false;
			}
			if($("#MAX_NUMBER").val()==""){
				layer.tips('请输入队伍最高人数', '#MAX_NUMBER', {
    					tips: 3
    				});
				return false;
			}
			if($("#CONTENT").val()==""){
				layer.tips('请输入比赛详情', '#CONTENT', {
    					tips: 3
    				});
				return false;
			}
			$("#From").submit();
            var data = {
            	msg : true
            };
            return data;
        }
		
		//门店通用
		function DoCheck() {
			var ch = document.getElementsByName("STORE_ID");
			if (document.getElementsByName("allChecked")[0].checked == true) {
				for (var i = 0; i < ch.length; i++) {
					ch[i].checked = true;
				}
			}
		}
		
		 layui.use(['form', 'layedit', 'laydate'], function(){
	            var form = layui.form
	                ,layer = layui.layer
	                ,layedit = layui.layedit
	                ,laydate = layui.laydate;

	            //创建一个编辑器
	            //var editIndex = layedit.build('CONTENT');
	            //日期
	            laydate.render({
	                elem: '#BEGIN_TIME'
	                , type: 'datetime'
	            });
	            laydate.render({
	                elem: '#BM_TIME'
	                , type: 'datetime'
	            });
	           
	            form.on('checkbox(tufure)', function(data){
	                console.log(data.elem); //得到checkbox原始DOM对象
	                console.log(data.elem.checked); //是否被选中，true或者false
	                console.log(data.value); //复选框value值，也可以通过data.elem.value得到
	                console.log(data.othis); //得到美化后的DOM对象
	                if(data.elem.checked == true){
	                    $(data.othis).prevAll(".layui-form-checkbox").addClass("layui-form-checked")
	                    $(data.othis).prevAll("input").prop("checked","checked");
	                }else {
	                    $(data.othis).prevAll(".layui-form-checkbox").removeClass("layui-form-checked")
	                    $(data.othis).prevAll("input").removeAttr("checked");
	                }
	                DoCheck();
	            });
	            
	            form.on('checkbox(store)', function(data){
  					var stores = document.getElementsByName("STORE_ID");
					for(var i=0;i<stores.length;i++){
						if(stores[i].checked == false){
							//console.log(data.elem); 得到select原始DOM对象
							$("#allChecked").next().removeClass("layui-form-checked")
	                    	$("#allChecked").removeAttr("checked");
							return ;
						}
						if(i == stores.length-1){
							console.log(data.elem); //得到select原始DOM对象
							$("#allChecked").next().addClass("layui-form-checked")
	                    	$("#allChecked").prop("checked","checked");
							return ;
						}
					}
	            });
	            
	        });
   </script>
</body>
</html>