<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../index/top.jsp"%>
	<script type="text/javascript" src="<%=basePath%>static/js/jquery-1.7.2.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>plugins/zTree/2.6/zTreeStyle.css"/>
	<script type="text/javascript" src="<%=basePath%>plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
	
	<style type="text/css">
	footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
	</style>
	
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
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
<body class="no-skin scroll">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12" style="padding: 0;">
							<div id="zhongxin" style="margin-top:10px">
								 <table width="100%" style="margin-bottom:50px">
								 <tbody style="margin-top:10px">
										<tr style="font-size:14px;color:#333;background-color:#eee;text-align:center;">
											<td width="20%" style="padding:14px 0;">菜单权限</td>
											<td width="20%" style="padding:14px 0;">增</td>
											<td width="20%" style="padding:14px 0;">删</td>
											<td width="20%" style="padding:14px 0;">改</td>
											<td width="20%" style="padding:14px 0;">查</td>
										</tr>
										<tr style="text-align:center;vertical-align:top;">
											<td><ul id="tree" class="tree" style="overflow:auto;margin:0 auto;display:inline-block;"></ul></td>
											<td><ul id="treeAdd" class="tree" style="overflow:auto;margin:0 auto;display:inline-block;"></ul></td>
											<td><ul id="treeDel" class="tree" style="overflow:auto;margin:0 auto;display:inline-block;"></ul></td>
											<td><ul id="treeEdit" class="tree" style="overflow:auto;margin:0 auto;display:inline-block;"></ul></td>
											<td><ul id="treeCha" class="tree" style="overflow:auto;margin:0 auto;display:inline-block;"></ul></td>
										</tr>
									</tbody>
								</table>
								<!-- <div style="height:750px;width: 1000px;position:relative">
								</div> -->
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">正在保存...</h4></div>
							</div>
						<!-- /.col -->
						</div>
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		
		<!--<div style="width: 100%;padding-top: 5px;position:fixed;bottom:0;" class="center">
			<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
			<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
		</div>-->
	
	<script type="text/javascript">
			
			
		$(top.hangge());
		var zTree;
		$(document).ready(function(){
			var setting = {
			    showLine: true,
			    checkable: true,
			    open: true
			};
			var zn = '{zTreeNodes=[{"MENU_TYPE":"2","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"151","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"115","target":"treeFrame","MENU_URL":"accountSettings/list.do","MENU_STATE":"1","name":"用户中心","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"90","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"115","target":"treeFrame","MENU_URL":"storeShow/list.do","MENU_STATE":"1","name":"门店管理","MENU_ORDER":"2","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"116","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"115","target":"treeFrame","MENU_URL":"personnel/list.do","MENU_STATE":"1","name":"角色管理","MENU_ORDER":"2","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"117","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"115","target":"treeFrame","MENU_URL":"account/list.do","MENU_STATE":"1","name":"人员管理","MENU_ORDER":"3","checked":true}],"id":"115","MENU_ICON":"menu-icon fa fa-bell-o orange","parentMenu":null,"pId":"0","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"账户管理","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"2","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"80","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"78","target":"treeFrame","MENU_URL":"miniWeb/list.do","MENU_STATE":"1","name":"微官网","MENU_ORDER":"2","checked":true}],"id":"78","MENU_ICON":"menu-icon fa fa-bookmark blue","parentMenu":null,"pId":"0","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"网吧管理","MENU_ORDER":"2","checked":true},{"MENU_TYPE":"2","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"119","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"27","target":"treeFrame","MENU_URL":"sendRecord/list.do","MENU_STATE":"1","name":"新建图文","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"150","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"27","target":"treeFrame","MENU_URL":"wxMenu/list.do","MENU_STATE":"1","name":"自定义菜单","MENU_ORDER":"2","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"152","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"27","target":"treeFrame","MENU_URL":"wxAutoReply/list.do","MENU_STATE":"1","name":"自动回复","MENU_ORDER":"3","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"240","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"27","target":"treeFrame","MENU_URL":"articleLib/list.do","MENU_STATE":"1","name":"揽客文章库","MENU_ORDER":"4","checked":true}],"id":"27","MENU_ICON":"menu-icon fa fa-home black","parentMenu":null,"pId":"0","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"公众号管理","MENU_ORDER":"3","checked":true},{"MENU_TYPE":"2","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"64","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"63","target":"treeFrame","MENU_URL":"wechatuser/list.do","MENU_STATE":"1","name":"会员列表","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"230","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"63","target":"treeFrame","MENU_URL":"memberMarke/list.do","MENU_STATE":"1","name":"会员营销","MENU_ORDER":"2","checked":true}],"id":"63","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"0","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"会员管理","MENU_ORDER":"4","checked":true},{"MENU_TYPE":"2","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"227","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"226","target":"treeFrame","MENU_URL":"rechargeRule/list.do","MENU_STATE":"1","name":"充值规则","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"231","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"226","target":"treeFrame","MENU_URL":"rechargeReport/listManage.do?state=internet","MENU_STATE":"1","name":"充值报表","MENU_ORDER":"2","checked":true}],"id":"226","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"0","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"充值管理","MENU_ORDER":"4","checked":true},{"MENU_TYPE":"2","nodes":[{"MENU_TYPE":"1","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"72","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"38","target":"treeFrame","MENU_URL":"card/list.do","MENU_STATE":"1","name":"卡券设置","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"73","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"38","target":"treeFrame","MENU_URL":"cancel/list.do","MENU_STATE":"1","name":"卡劵核销","MENU_ORDER":"2","checked":true}],"id":"38","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"37","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"优惠券管理","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"146","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"66","target":"treeFrame","MENU_URL":"intintegral/list.do","MENU_STATE":"1","name":"积分奖励设置","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"147","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"66","target":"treeFrame","MENU_URL":"consumption/list.do","MENU_STATE":"1","name":"积分消耗设置","MENU_ORDER":"2","checked":true}],"id":"66","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"37","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"积分管理","MENU_ORDER":"2","checked":true},{"MENU_TYPE":"1","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"68","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"67","target":"treeFrame","MENU_URL":"lottery/list.do","MENU_STATE":"1","name":"抽奖设置","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"69","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"67","target":"treeFrame","MENU_URL":"memberlottery/list.do","MENU_STATE":"1","name":"兑奖管理","MENU_ORDER":"2","checked":true}],"id":"67","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"37","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"抽奖管理","MENU_ORDER":"3","checked":true},{"MENU_TYPE":"1","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"75","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"41","target":"treeFrame","MENU_URL":"sysmatch/list.do","MENU_STATE":"1","name":"赛事设置","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"76","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"41","target":"treeFrame","MENU_URL":"enroll/list.do","MENU_STATE":"1","name":"赛事报名","MENU_ORDER":"2","checked":true}],"id":"41","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"37","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"赛事管理","MENU_ORDER":"4","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"153","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"37","target":"treeFrame","MENU_URL":"invite/list.do","MENU_STATE":"1","name":"拉新设置","MENU_ORDER":"6","checked":true}],"id":"37","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"0","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"营销管理","MENU_ORDER":"5","checked":true},{"MENU_TYPE":"2","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"144","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"143","target":"treeFrame","MENU_URL":"auction/list.do","MENU_STATE":"1","name":"商品管理","MENU_ORDER":"1","checked":true},{"MENU_TYPE":"1","nodes":[],"id":"145","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"143","target":"treeFrame","MENU_URL":"order/list.do","MENU_STATE":"1","name":"订单管理","MENU_ORDER":"2","checked":true}],"id":"143","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"0","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"商城管理","MENU_ORDER":"6","checked":true},{"MENU_TYPE":"2","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"62","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"61","target":"treeFrame","MENU_URL":"lm/list.do","MENU_STATE":"1","name":"查看留言","MENU_ORDER":"1","checked":true}],"id":"61","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"0","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"留言管理","MENU_ORDER":"7","checked":true},{"MENU_TYPE":"2","nodes":[{"MENU_TYPE":"1","nodes":[],"id":"237","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"235","target":"treeFrame","MENU_URL":"productNews/list.do","MENU_STATE":"1","name":"产品日志","MENU_ORDER":"2","checked":true}],"id":"235","MENU_ICON":"menu-icon fa fa-leaf black","parentMenu":null,"pId":"0","target":"treeFrame","MENU_URL":"#","MENU_STATE":"1","name":"产品动态","MENU_ORDER":"12","checked":true}]}';
			var zTreeNodes = eval(zn);
			zTree = $("#tree").zTree(setting, zTreeNodes);
			zTree.expandAll(true);
		});

		
		
		var zTreeAdd;
		$(document).ready(function(){
			var settingAdd = {
			    showLine: true,
			    checkable: true
			};
			var znAdd = '${zTreeNodesAdd}';			
			var zTreeNodesAdd = eval(znAdd);
			zTreeAdd = $("#treeAdd").zTree(settingAdd, zTreeNodesAdd);
			zTreeAdd.expandAll(true);
		});
		
		var zTreeDel;
		$(document).ready(function(){
			var settingDel = {
					showLine:true,
					checkable:true
			};
			var znDel = '${zTreeNodesDel}';
			var zTreeNodesDel = eval(znDel);
			zTreeDel = $("#treeDel").zTree(settingDel,zTreeNodesDel);
			zTreeDel.expandAll(true);
		});
		
		var zTreeEdit;
		$(document).ready(function(){
			var settingEdit = {
					showLine:true,
					checkable:true
			};
			var znEdit = '${zTreeNodesEdit}';
			var zTreeNodesEdit = eval(znEdit);
			zTreeEdit = $("#treeEdit").zTree(settingEdit,zTreeNodesEdit);
			zTreeEdit.expandAll(true);
		});
		
		var zTreeCha;
		$(document).ready(function(){
			var settingCha = {
					showLine:true,
					checkable:true
			};
			var znCha = '${zTreeNodesCha}';
			var zTreeNodesCha = eval(znCha);
			zTreeCha = $("#treeCha").zTree(settingCha,zTreeNodesCha);
			zTreeCha.expandAll(true);
		});
	
		
		//保存
		 var baocun = function(){
			/* 菜单权限 */
			var nodes = zTree.getCheckedNodes();
			var tmpNode;
			var ids = "";
			for(var i=0; i<nodes.length; i++){
				tmpNode = nodes[i];
				if(i!=nodes.length-1){
					ids += tmpNode.id+",";
				}else{
					ids += tmpNode.id;
				}
			}
			/* 增 */
			var nodesAdd = zTreeAdd.getCheckedNodes();
			var tmpNodeAdd;
			var idsAdd = "";
			for ( var i = 0; i < nodesAdd.length; i++) {
				tmpNodeAdd = nodesAdd[i];
				if(i!=nodesAdd.length-1){
					idsAdd += tmpNodeAdd.id+",";
				}else{
					idsAdd += tmpNodeAdd.id;
				}
			}
			/* 删 */
			var nodesDel = zTreeDel.getCheckedNodes();
			var tmpNodeDel;
			var idsDel = "";
			for ( var i = 0; i < nodesDel.length; i++) {
				tmpNodeDel = nodesDel[i];
				if(i!=nodesDel.length-1){
					idsDel += tmpNodeDel.id+",";
				}else{
					idsDel += tmpNodeDel.id;
				}
			}
			/* 改 */
			var nodesEdit = zTreeEdit.getCheckedNodes();
			var tmpNodeEdit;
			var idsEdit = "";
			for ( var i = 0; i < nodesEdit.length; i++) {
				tmpNodeEdit = nodesEdit[i];
				if(i!=nodesEdit.length-1){
					idsEdit += tmpNodeEdit.id+",";
				}else{
					idsEdit += tmpNodeEdit.id;
				}
			}
			/* 查 */
			var nodesCha = zTreeCha.getCheckedNodes();
			var tmpNodeCha;
			var idsCha = "";
			for ( var i = 0; i < nodesCha.length; i++) {
				tmpNodeCha = nodesCha[i];
				if(i!=nodesCha.length-1){
					idsCha += tmpNodeCha.id+",";
				}else{
					idsCha += tmpNodeCha.id;
				}
			}
			
			var ROLE_ID = "${ROLE_ID}";
			var url = "<%=basePath%>personnel/saveMenuqx.do";
			var postData;
			postData = {"ROLE_ID":ROLE_ID,"menuIds":ids,"menuIdsAdd":idsAdd,"menuIdsDel":idsDel,"menuIdsEdit":idsEdit,"menuIdsCha":idsCha};
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.post(url,postData,function(data){
				top.Dialog.close();
			});
			var data = {
            	msg : true
            };
            return data;
		 }
		
		var firstAsyncSuccessFlag = 0;
		function zTreeOnAsyncSuccess(event, treeId, msg) {
		if (firstAsyncSuccessFlag == 0) {  

          		try {  
                 		//调用默认展开第一个结点  
                 		var selectedNode = zTree.getSelectedNodes();
                 		var nodes = zTree.getNodes();
                 		zTree.expandNode(nodes[0], true);
              
                 		var childNodes = zTree.transformToArray(nodes[0]);
                 		zTree.expandNode(childNodes[1], true);
                 		zTree.selectNode(childNodes[1]);
                 		var childNodes1 = zTree.transformToArray(childNodes[1]);
                 		zTree.checkNode(childNodes1[1], true, true);
                 		firstAsyncSuccessFlag = 1;
           		} catch (err) {
           	}
     }
}
		
	</script>
</body>
</html>