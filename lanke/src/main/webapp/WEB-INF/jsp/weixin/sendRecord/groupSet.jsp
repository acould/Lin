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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>群发设置</title>
		
	<link rel="stylesheet" href="<%=basePath%>newStyle/css/custom.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/css/newtextimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
</head>
<body class="scroll">
	<div style="padding: 20px">
		<form action="" class="layui-form" style="width:310px;margin:0 auto;">
			<input type="hidden" id="SENDRECORD_ID" name="SENDRECORD_ID" value="${pd.SENDRECORD_ID }">
		  <div class="layui-form-item">
		      <label class="layui-form-label">群发对象：</label>
		      <div class="layui-input-inline">
		        <select name="quiz" id="GROUP_ID">

		                <option value="0" <c:if test="${pd.GROUP_ID == '0' }">selected=selected</c:if> >全部</option>
		                <option value="1" <c:if test="${pd.GROUP_ID == '1' }">selected=selected</c:if>>会员</option>
		                <option value="2" <c:if test="${pd.GROUP_ID == '2' }">selected=selected</c:if>>非会员</option>
		        </select>
		      </div>
		  </div>
		  <div class="layui-form-item">
		      <label class="layui-form-label">群发性别：</label>
		      <div class="layui-input-inline">
		        <select name="quiz" id="GROUP_SEX">

	                 <option value="0" <c:if test="${pd.GROUP_SEX == '0' }">selected=selected</c:if>>全部</option>
	                 <option value="1" <c:if test="${pd.GROUP_SEX == '1' }">selected=selected</c:if> >男生</option>
	                 <option value="2" <c:if test="${pd.GROUP_SEX == '2' }">selected=selected</c:if>>女生</option>
		        </select>
		      </div>
		  </div>
		  <div class="layui-form-item">
		      <label class="layui-form-label">群发地区：</label>
		      <div class="layui-input-inline">
		        <select name="modules" lay-verify="required" lay-search="" id="GROUP_PROVINCE">

		          <option value="all" <c:if test="${pd.GROUP_PROVINCE == 'all' }">selected=selected</c:if> >全部</option>
	                 <c:forEach items="${proList }" var="par" varStatus="ps">
	                 	<option value="${par.PROVICNE }" <c:if test="${pd.GROUP_PROVINCE == par.PROVICNE}">selected=selected</c:if> >${par.PROVICNE }</option>
	                 </c:forEach>
		        </select>
		      </div>
		  </div>
		</form>
	</div>
	

</body>

<script type="text/javascript">
	layui.use(["layer","form"],function(){
		var layer = layui.layer,
		    form = layui.form
		    
	})
	
	//保存群发设置
	function setParam(){
		var sendRecordId = $("#SENDRECORD_ID").val();
		var groupId = $("#GROUP_ID").val();
		var groupSex = $("#GROUP_SEX").val();
		var groupProvince = $("#GROUP_PROVINCE").val();
		
		var url = "<%=basePath%>sendRecord/setParam.do?groupId="+groupId+"&groupSex="+groupSex+"&groupProvince="+groupProvince+"&sendRecordId="+sendRecordId;
		var data = {
			url: url
		}
		return data;
	}

</script>


</html>