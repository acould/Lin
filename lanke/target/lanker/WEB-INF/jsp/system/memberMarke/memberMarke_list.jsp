<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css"
	media="all">
<title>网吧管理后台</title>
<style>
body {
	background: #f3f3f3;
	min-width: 1160px;
	overflow: auto;
	padding: 12px;
}

.layui-form-label {
	padding: 9px 14px;
	width: auto;
	text-align: left;
}
</style>
</head>
<body class="scroll">
	<div class="introduce-box layui-clear">
		<div class="lk-introduce-content">
			<img src="<%=basePath%>newStyle/images/memberMarke.png" width="540px">
		</div>
		<div class="lk-introduce-content">
			<h1 class="lk-introduce-title">活动群发介绍</h1>
			<p>智能分组，自由编辑、微信群发，会员营销更精准！</p>
			<p>1)、揽客为您分析Pubwin会员消费行为，智能生成会员标签</p>
			<p>2)、您可以根据智能分组，为不同属性的会员制定个性化营销策略</p>
			<p>3)、自由编辑模板消息，分组群发营销活动、优惠券，精准直达会员微信</p>
			<div class="lk-help-btn">
				<a href="<%=basePath%>register/user_marketing.do" target="_blank">如何玩转活动群发</a>
			</div>
		</div>
		<div class="lk-introduce-content">
			<div>
				<a class="btn btn-xm btn-primary"
					onclick="memberMake_select('<%=basePath%>memberMarke/toSelect.do','<%=basePath%>memberMarke/toAdd.do')">
					<i class="layui-icon" style="padding-right: 4px">&#xe654;</i>群发消息
				</a>
			</div>
			<div style="margin-top: 14px;">
				<a class="btn btn-xm btn-success" target="_blank"
					onclick="select_card('<%=basePath%>memberMarke/cardList.do','<%=basePath%>memberMarke/cardView.do','${pd.role}','','${pd.storeIds}','','')">
					<i class="layui-icon" style="padding-right: 4px">&#xe654;</i>群发卡券
				</a>
			</div>
		</div>
	</div>
	<div class="introduce-box introduce-formList">
		<form action="memberMarke/list.do" method="post" name="Form" id="Form" class="layui-form">
			<table>
				<tr>
					<td>
						<div class="nav-search">
							<span class="input-icon"> 
							<input type="text" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词" /> 
							<i class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
						</div>
					</td>
					<td style="vertical-align: middle; padding-left: 15px;">
						<div class="layui-inline">
							<label class="layui-form-label">门店：</label>
							<div class="layui-input-inline">
								<select name="store_id" lay-search="" lay-filter="filter">
									<option value="">选择或搜索</option>
									<c:forEach items="${storeList}" var="var" varStatus="vs">
										<option value="${var.store_id}" <c:if test="${pd.store_id == var.store_id }">selected="selected"</c:if> >${var.store_name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</td>
					<td style="vertical-align: middle; padding-left: 15px;">
						<div class="layui-inline">
							<label class="layui-form-label">群发类型：</label>
							<div class="layui-input-inline">
								<select name="type" lay-search="" lay-filter="filter">
									<option value="">选择或搜索</option>
									<option value="">全部</option>
									<option value="material" <c:if test="${pd.type == 'material' }">selected="selected"</c:if>>图文消息</option>
									<option value="image" <c:if test="${pd.type == 'image' }">selected="selected"</c:if>>图片</option>
									<option value="text" <c:if test="${pd.type == 'text' }">selected="selected"</c:if>>文字</option>
									<option value="card" <c:if test="${pd.type == 'card' }">selected="selected"</c:if>>卡券</option>
									<option value="link" <c:if test="${pd.type == 'link' }">selected="selected"</c:if>>链接</option>
									<option value="notice" <c:if test="${pd.type == 'notice' }">selected="selected"</c:if>>消息通知</option>
								</select>
							</div>
						</div>
					</td>
					<td style="vertical-align: middle; padding-left: 15px"><a
						class="btn btn-success btn-sm" onclick="tosearch();" title="检索">
							<i class="layui-icon"
							style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索
					</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="introduce-box" style="min-height: calc(100vh - 350px);">
		<!-- 检索  -->
		<table id="simple-table"
			class="table table-striped table-bordered table-hover"
			style="margin-top: 5px;">
			<thead>
				<tr>
					<th class="center" style="width: 35px;"><label class="pos-rel">
							<input type="checkbox" class="ace" id="zcheckbox" /><span
							class="lbl"></span>
					</label></th>
					<th class="center" style="width: 80px;">序号</th>
					<th class="left">门店名称</th>
					<th class="left">群发标题</th>
					<th class="left">群发类型</th>
					<th class="left">群发对象</th>
					<th class="left">群发时间</th>
					<th class="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty list}">
						<c:if test="${QX.cha == 1 }">
							<c:forEach items="${list}" var="var" varStatus="vs">
								<tr>
									<td class='center' style="vertical-align: middle"><label
										class="pos-rel"> <input type='checkbox' name='ids'
											value="${var.id}" class="ace" /> <span class="lbl"></span></label></td>
									<td class='center' style="vertical-align: middle;">${vs.index+1}</td>
									<td class='left' style="vertical-align: middle"
										title="<c:forEach items="${var.sList}" var="storeLists" varStatus="vs">${storeLists.STORE_NAME }&nbsp;&nbsp;</c:forEach>">
										<c:if test="${var.sList.size() <= 3 }">
											<c:forEach items="${var.sList}" var="sList" varStatus="vs"
												begin="0" end="2">
                                        ${sList.STORE_NAME }
                                     </c:forEach>
										</c:if> <c:if test="${var.sList.size() > 3 }">
											<c:forEach items="${var.sList}" var="sList" varStatus="vs"
												begin="0" end="2">
                                        ${sList.STORE_NAME }
                                     </c:forEach>
                                     ...
                                     </c:if>
									</td>
									<td class='left' style="vertical-align: middle">${var.title}</td>
									<td class='left' style="vertical-align: middle"><c:if
											test="${var.type =='material'}">图文消息</c:if> <c:if
											test="${var.type =='image'}">图片</c:if> <c:if
											test="${var.type =='text'}">文字</c:if> <c:if
											test="${var.type =='card'}">卡券</c:if> <c:if
											test="${var.type =='link'}">链接</c:if> <c:if
											test="${var.type =='notice'}">消息通知</c:if></td>
									<td class='left' style="vertical-align: middle"><c:if
											test="${var.mass_object == 'fans'}">粉丝、性别<c:if
												test="${var.sex == ''}">全部</c:if>
											<c:if test="${var.sex == 1}">男</c:if>
											<c:if test="${var.sex == 2}">女</c:if>
										</c:if> <c:if test="${var.mass_object == 'member'}">会员、性别<c:if
												test="${var.sex == ''}">全部</c:if>
											<c:if test="${var.sex == 1}">男</c:if>
											<c:if test="${var.sex == 2}">女</c:if>、
										<c:if test="${var.member == '全部会员'}">${var.member}</c:if>
											<c:if test="${var.member != '全部会员'}">${var.member}${var.quiz2}</c:if>
										</c:if></td>
									<td class="left">${var.marketime}</td>
									<td class="center" style="vertical-align: middle">
										<div class="hidden-sm hidden-xs btn-group">
											<c:if test="${QX.cha == 1 }">
												<a class="btn btn-sm btn-success" title="查看"
													href="<%=basePath%>memberMarke/toView?id=${var.id}"
													target="_blank"> <i class="layui-icon"
													style="padding-right: 2px">&#xe642;</i>查看
												</a>
											</c:if>
											<c:if test="${QX.del == 1 }">
												<a class='btn btn-mini btn-danger' title="删除"
													onclick="deleteMark('${var.id}')"> <i
													class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
												</a>
											</c:if>
										</div>
										<div class="hidden-md hidden-lg">
											<div class="inline pos-rel lk-ul-hoverShow">
												<button class="btn btn-minier btn-primary dropdown-toggle"
													data-toggle="dropdown" data-position="auto">
													<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
												</button>
												<ul
													class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
													<c:if test="${QX.cha == 1 }">
														<li><a style="cursor: pointer;" title="编辑"
															target="_blank"
															href="<%=basePath%>memberMarke/toView?id=${var.id}"
															class="tooltip-success" data-rel="tooltip"> <span
																class="text-success lk-text"> <i
																	class="layui-icon">&#xe642;</i>
															</span>
														</a></li>
													</c:if>
													<c:if test="${QX.del == 1 }">
														<li><a style="cursor: pointer;" title="删除"
															onclick="deleteMark('${var.id}')" class="tooltip-success"
															data-rel="tooltip"> <span class="text-danger lk-text">
																	<i class="layui-icon">&#xe640;</i>
															</span>
														</a></li>
													</c:if>
												</ul>
											</div>
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div class="page-header position-relative">
			<table style="width: 100%;">
				<tr>
					<c:if test="${QX.del == 1 }">
						<td style="vertical-align: top;"><a title="批量删除"
							class="btn btn-xm btn-danger" onclick="makeAll('确定要删除选中的数据吗?');">
								<i class='ace-icon fa fa-trash-o bigger-120'></i>批量删除
						</a></td>
					</c:if>
					<td style="vertical-align: top;">
						<div class="pagination"
							style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div class="lk-massObject-box" id="massObject" style="display: none;">
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
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script src="<%=basePath%>newStyle/js/lk-memberMake.js"></script>
<script type="text/javascript">
function deleteMark(id) {
	layui.layer.confirm('确定要删除该条营销活动吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				async : false,
				type : "POST",
				url : '<%=basePath%>/memberMarke/delete.do',
				data : {
					id : id
				},
				dataType : 'json',
				beforeSend : loading("删除中"),
				success : function(data) {
					layer.closeAll();
					layer.msg(data.message, {
						time : data.message.length * 200
					}, function() {
						if ("success" == data.result) {
							location.reload();// 刷新父页面
						}
					});
				},
				error : function() {
					layer.closeAll();
					layer.msg("系统繁忙，请稍后再试！");
				}
			});
		}, function() {
		});
	}
	
//批量删除
function makeAll(msg) {
    layer.confirm('确定要删除选中的数据吗?', {
        btn: ['确定', '取消'],
        title: '批量删除',
    }, function () {
        var str = '';
        for (var i = 0; i < document.getElementsByName('ids').length; i++) {
            if (document.getElementsByName('ids')[i].checked) {
                if (str == '') str += document.getElementsByName('ids')[i].value;
                else str += ',' + document.getElementsByName('ids')[i].value;
            }
        }
        if (str == '') {
            layer.msg('您没有选择任何内容！', {time: 800, icon: 1}, function () {
            });
        } else {
            if (msg == '确定要删除选中的数据吗?') {
                top.jzts();
                $.ajax({
                    type: "POST",
                    url: '<%=basePath%>/memberMarke/deleteAll.do',
                    data: {DATA_IDS: str},
                    dataType: 'json',
                    cache: false,
                    success : function(data) {
    					layer.msg(data.message, {
    						time : data.message.length * 200
    					}, function() {
    						if ("success" == data.result) {
    							location.reload();// 刷新父页面
    						}
    					});
    				},
    				error : function() {
    					layer.msg("系统繁忙，请稍后再试！");
    				}
                });
            }
        }
    }, function () {
        return
    });
}	
</script>
</html>