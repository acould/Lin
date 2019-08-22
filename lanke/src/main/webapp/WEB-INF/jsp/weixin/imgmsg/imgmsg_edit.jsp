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
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<!-- jsp文件头和头部 -->
		<%@ include file="../../system/index/top.jsp"%>
		<script type="text/javascript" src="<%=basePath%>/ueditorNew/ueditor.config.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ueditorNew/ueditor.all.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ueditorNew/lang/zh-cn/zh-cn.js"></script>
	</head>
<body>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="imgmsg/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="IMGMSG_ID" id="IMGMSG_ID" value="${pd.IMGMSG_ID}"/>
						<input type="hidden" name="STATUS" id="STATUS" value="${pd.STATUS}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover" style="width:100%;">
							<tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">关键词:</td>
								<td><input style="width:95%;" type="text" name="KEYWORD" id="KEYWORD" value="${pd.KEYWORD}" maxlength="200" placeholder="这里输入关键词" title="关键词"/></td>
								<td style="width:70px;text-align: right;padding-top: 13px;">序号:</td>
								<td><input style="width:95%;" type="text" name="REMARKS" id="REMARKS" value="${pd.REMARKS}" maxlength="200" placeholder="这里输入序号" title="备注"/></td>
								<td style="width:70px;text-align: right;padding-top: 13px;">状态:</td>
								<td>
									<label style="float:left;padding-left: 12px;"><input class="ace" name="form-field-radio" id="form-field-radio1" onclick="setType('1');" <c:if test="${pd.STATUS == '1' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">有效</span></label>
									<label style="float:left;padding-left: 5px;"><input class="ace" name="form-field-radio" id="form-field-radio2" onclick="setType('2');" <c:if test="${pd.STATUS == '2' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">无效</span></label>
								</td>
							</tr>
						</table>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 12px;">标题:</td>
								<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="60" placeholder="这里输入标题" title="标题" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 12px;">作者:</td>
								<td><input type="text" name="AUTHOR" id="AUTHOR" value="${pd.AUTHOR}" maxlength="60" placeholder="这里输入作者" title="作者" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 12px;">描述:</td>
								<td><input type="text" name="DESCRIPTION" id="DESCRIPTION" value="${pd.DESCRIPTION}" maxlength="255" placeholder="这里输入描述" title="描述" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">原文链接:</td>
								<td><input type="text" name="SOURCE_URL" id="SOURCE_URL" value="${pd.SOURCE_URL}" maxlength="255" placeholder="这里输入原文链接地址" title="原文链接地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 12px;">封面图片:</td>
								<td>
								 <select name="IMG_URL" id="IMG_URL" style="width:98%;">
								 	<c:forEach items="${imgList}" var="var" varStatus="vs">
								 		<option value="${var.MATERIAL_ID}" <c:if test="${pd.THUMB_MEDIA_ID ==var.MATERIAL_ID}">selected="selected"</c:if>>${var.TITLE} </option>
								 	</c:forEach>
								 </select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">图文内容:</td>
								<td><textarea  name="CONTENT" id="CONTENT" maxlength="255" placeholder="这里输入图文内容" title="图文内容"  style="width: 800px; height: 400px; margin: 0 auto;">${pd.CONTENT}</textarea></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="/static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						
					</form>

					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>	
</div>
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>	
	<script type="text/javascript">
	$(top.hangge());
	</script>
	
	<script type="text/javascript">
	
	
	//保存
	function save(){
			
		if ($("#KEYWORD").val() == "") {
			$("#KEYWORD").tips({
				side : 3,
				msg : '请输入关键词',
				bg : '#AE81FF',
				time : 4
			});
			$("#KEYWORD").focus();
			return false;
		}
		if ($("#REMARKS").val() == "") {
			$("#REMARKS").tips({
				side : 3,
				msg : '请输入序号',
				bg : '#AE81FF',
				time : 4
			});
			$("#REMARKS").focus();
			return false;
		}
		if(isNaN($("#REMARKS").val())){
			$("#REMARKS").tips({
				side : 3,
				msg : '序号请输入数字',
				bg : '#AE81FF',
				time : 4
			});
			$("#REMARKS").focus();
			return false;
		}
		
		if ($("#STATUS").val() == "") {
			$("#form-field-radio1").tips({
				side : 3,
				msg : '请选择状态',
				bg : '#AE81FF',
				time : 4
			});
			$("#STATUS").focus();
			return false;
		}
		
		if ($("#TITLE").val() == "") {
			$("#TITLE").tips({
				side : 3,
				msg : '请输入标题',
				bg : '#AE81FF',
				time : 4
			});
			$("#TITLE").focus();
			return false;
		}
		if ($("#AUTHOR").val() == "") {
			$("#AUTHOR").tips({
				side : 3,
				msg : '请输入作者',
				bg : '#AE81FF',
				time : 4
			});
			$("#AUTHOR").focus();
			return false;
		}
		if ($("#DESCRIPTION").val() == "") {
			$("#DESCRIPTION").tips({
				side : 3,
				msg : '请输入描述',
				bg : '#AE81FF',
				time : 4
			});
			$("#DESCRIPTION").focus();
			return false;
		}
		if ($("#IMG_URL").val() == "") {
			$("#IMG_URL").tips({
				side : 3,
				msg : '请选择图片',
				bg : '#AE81FF',
				time : 4
			});
			$("#IMGURL").focus();
			return false;
		}
		hasK();
	}

		//判断关键词是否存在
		function hasK() {
			var KEYWORD = $("#KEYWORD").val();
			var IMGMSG_ID = "${pd.IMGMSG_ID}";
			$.ajax({
				type : "POST",
				url : '<%=basePath%>imgmsg/hasK.do',
		    	data: {KEYWORD:KEYWORD,IMGMSG_ID:IMGMSG_ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data.result){
						$("#Form").submit();
						$("#zhongxin").hide();
						$("#zhongxin2").show();
					 }else if("fail" == data.result){
						 bootbox.alert("最多回复8个图文信息！");
						 return false;
					 }else if("exist" == data.result){
						 $("#KEYWORD").tips({
							side:3,
				            msg:'此关键词已存在(全局)!',
				            bg:'#AE81FF',
				            time:10
				        });
						return false;
					 }else{
						bootbox.confirm("已有该关键字的信息，是否继续添加?", function(result) {
							if(result) {
								top.jzts();
								$("#Form").submit();
								$("#zhongxin").hide();
								$("#zhongxin2").show();
							}
						});
					 }
				}
		});
	}
	
	function setType(value){
		$("#STATUS").val(value);
	}
</script>
	<script type="text/javascript">
		UE.getEditor("CONTENT");
	</script>
</body>
</html>