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
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>留言回复</title>
    <style>
        .borPadding {
            padding:20px 30px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
    </style>
</head>
<body class="no-skin scroll">
<!-- /section:basics/navbar.layout -->
  <div class="borPadding">
        <form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="" enctype="multipart/form-data" >
			<input type="hidden" name="LM_ID" id="LM_ID" value="${pd.LM_ID}"/>
			<div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">投诉人:</label>
                <div class="layui-input-block">
					
                	<input type="text" name="LM_NAME" id="LM_NAME" 
					<c:if test="${flag == 1}">value="${pd.LM_USERNAME }"</c:if><c:if test="${flag == 0}">value="***"</c:if> 
					 maxlength="20"  autocomplete="off" class="layui-input" readonly="readonly"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">投诉时间:</label>
                <div class="layui-input-block">
                	<input type="text" name="LM_DATE" id="LM_DATE" value="${pd.LM_DATE }" maxlength="20" autocomplete="off" class="layui-input" readonly="readonly"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">投诉类型:</label>
                <div class="layui-input-block">
                	<input type="text" name="LM_TYPE_NAME" id="LM_TYPE_NAME" value="${pdType.DICT_VALUE }" maxlength="20" autocomplete="off" class="layui-input" readonly="readonly"/>
                </div>
            </div>
            <c:if test="${pd.LM_STROE_NAME != null and pd.LM_STROE_NAME != ''}">
            	<div class="layui-form-item">
                	<label class="layui-form-label" style="margin:0">对象分店:</label>
                	<div class="layui-input-block">
                		<input type="text" name="LM_STROE_NAME" id="LM_STROE_NAME" value="${pd.LM_STROE_NAME}" maxlength="20" autocomplete="off" class="layui-input" readonly="readonly"/>
                	</div>
            	</div>
            </c:if>
			<div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">
                	<div id="localImag">
						<img id="preview" width=-1 height=-1 style="diplay:none"/><strong>投诉图片:</strong>
					</div>
                </label>
                <div class="layui-input-block">
                	<c:if test="${picList.size() ==0 }">
						<input type="text" autocomplete="off" class="layui-input" value="没有提交投诉图片" readonly="readonly">
					</c:if>
					<c:choose>
						<c:when test="${not empty picList}">
							<c:forEach items="${picList }" var="p">
								<a id="aaa${vs.index }" href="<%=basePath %>uploadFiles/uploadImgs/${p.PATH}" target="_blank"><img width="150" height="150" src="<%=basePath %>uploadFiles/uploadImgs/${p.PATH }"/></a>
							</c:forEach>
						</c:when>
					</c:choose>
                </div>
            </div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">投诉内容:</label>
            	<div class="layui-input-block">
					<textarea class="layui-textarea" name="LM_CONTENT" id="LM_CONTENT" value="${pd.LM_CONTENT}" readonly="readonly" placeholder="" title="">${pd.LM_CONTENT}</textarea>
				</div>
			</div>
            <div class="layui-form-item" <c:if test="${msg == 'backLm'}">style="display: none"</c:if> >
				<label class="layui-form-label">回复人:</label>
            	<div class="layui-input-block">
            		<input type="text" name="NAME" id="NAME" value="${pduser.NAME }" maxlength="20" autocomplete="off" class="layui-input" readonly="readonly"/>
				</div>
			</div>
			<div class="layui-form-item" <c:if test="${msg == 'backLm'}">style="display: none"</c:if>>
				<label class="layui-form-label">回复时间:</label>
            	<div class="layui-input-block">
					<input type="text" name="BACKTIME" id="BACKTIME" value="${pd.LM_BACKTIME }" maxlength="20" autocomplete="off" class="layui-input" readonly="readonly"/>
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">回复内容:</label>
            	<div class="layui-input-block">
					<textarea class="layui-textarea" name="BACKCONTENT" id="BACKCONTENT" value="${pd.BACKCONTENT}" <c:if test="${msg == 'edit'}">readonly="readonly"</c:if>>${pd.BACKCONTENT}</textarea>
				</div>
			</div>
	</form>
</div>

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		//保存
		var baocun = function(){
			var BACKCONTENT = $.trim($("#BACKCONTENT").val());
			var LM_ID = $("#LM_ID").val();
			if(BACKCONTENT==""){
				layer.tips('请输入回复内容', '#BACKCONTENT', {
    				tips: 3
    			});
				$("#BACKCONTENT").focus();
				return false;
			}
			$.ajax({
				type: "POST",
				url: '<%=basePath%>lm/backLm.do',
				data: {BACKCONTENT:BACKCONTENT, LM_ID:LM_ID},
				dataType:'json',
				cache: false,
				success: function(data){
					layer.msg(data.message);
					if(data.result == "true"){
						setTimeout(function () { 
							parent.location.reload();
						},500)
					}
				},
				error:function(){
				   layer.msg("系统繁忙，请稍后再试！");
				   return false;
				}
			});
		}
	</script>
</body>
</html>