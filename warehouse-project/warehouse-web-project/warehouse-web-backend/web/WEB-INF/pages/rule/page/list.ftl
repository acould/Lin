<#assign webNav="Y" in model>
<#assign webTitle="运营管理-精准营销" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<script type="text/javascript" src="${model.static_domain}/js/lits/jquery.tools.min.js"></script>
<link rel="stylesheet" type="text/css" href="${model.static_domain}/js/plugins/datepicker/jquery-ui-datepicker.css">
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<div id="wrapper">
	<div class="content clearfix bigfs">
		<#include "/common/menu_left.ftl">
		<div id="wrap" class="clearfix pt30 pl30">
			<div id="breadcrumb" class="title">${(pageName)?if_exists}</div>
			<form action="/rule/page/list.html" method="post">
	        	<div class="mt20 form-inline">
	        		<span class="control-label">类型：</span>
	                <@model.cmdSelect obj="type" values=crmTypes names=crmTypes selectedvalue="${type!}"/>
					<span class="control-label">形式：</span>
					<@model.cmdSelect obj="sendType" values=["dx","sms","push"] names=["电销","短信","push"] selectedvalue="${sendType!}"/>
					<span class="control-label">红包配置：</span>
					<@model.cmdSelect obj="haveCoupon" values=["1","2"] names=["有红包","无红包"] selectedvalue="${haveCoupon!}"/>
					<span class="control-label">筛选开关：</span>
					<@model.cmdSelect obj="allowFilter" values=["y","n"] names=["开启","关闭"] selectedvalue="${allowFilter!}"/>
	        	</div>
	        	<div class="mt10 form-inline">
		        	<span class="control-label">推送开关：</span>
					<@model.cmdSelect obj="status" values=["y","n"] names=["开启","关闭"] selectedvalue="${status!}"/>
			        <input type="submit" class="ml20 btn btn-search" value="筛选" />
		        </div>
        	</form>
			
			<div class="clearfix mt30">
				<div class="table mb30">
					<table>
						<thead>
							<tr>
								<th width="5%">序号</th>
								<th width="10%">类型</th>
								<th width="15%">用例名称</th>
								<th width="10%">消息ID</th>
								<th width="10%">形式</th>
								<th width="15%">红包</th>
								<th width="10%">筛选开关</th>
								<th width="10%">推送开关</th>
								<th width="15%">用户列表下载</th>
							</tr>
						</thead>
						<tbody>
							<#if (page.result?exists && page.result?size > 0)>
								<#list page.result as item>
									<tr>
										<td>${item.id}</td>
										<td>${item.type}</td>
										<td>${item.title}</td>
										<td><@model.msgCodeStyle caseId="${item.id}" sendType="${item.sendType}" msgCode="${item.msgCode}"/></td>
										<td>
											<#if item.sendType == 'sms'>
												短信
											</#if>												
											<#if item.sendType == 'dx'>
												电销
											</#if>
											<#if (item.sendType?? && item.sendType=='push') && (item.pushType?? && item.pushType=='single')>
												push-单播
											</#if>
											<#if (item.sendType?? && item.sendType=='push') && (item.pushType?? && item.pushType=='file')>
												push-文件播
											</#if>
											<#if (item.sendType?? && item.sendType=='push') && !item.pushType??>
												push
											</#if>
										</td>
										<td><@model.getCoupon caseId="${item.id}" templateId="${item.templateId?default('点击配置')}" sendType="${item.sendType}"/></td>
										<td class="rule_status"><@model.crmStatus caseId="${item.id}" status="${item.allowFilter}" type="filter"/></td>
										<td class="rule_status"><@model.crmStatus caseId="${item.id}" status="${item.status}" type="push"/></td>
										<!--访问权限控制-->
										<!--@auth res="CRM_DOWNXLS"-->
											<td><@model.isDownLoad caseId="${item.id}" todayTotal="${item.dateTotal}" lastWeekTotal="${item.lastWeekTotal}"/>&nbsp;&nbsp;</td>
										<!--/@auth-->
									</tr>
								</#list>
							</#if>
						</tbody>
						<tfoot>
				 			<tr>
				 				<td colspan="100%" class="tac">
				 					<div class="pagination">
				 					 <@model.pages "/rule/page/list.html" />
				 					</div>
				 				</td>
				 			</tr>
				 		</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<style>
	.form-inline .control-label {
		width:90px;
		text-align:right;
	}
	.form-inline .base-input, .form-inline select {
		width:150px;
	}
	.rule_status a{
		text-decoration:none;
	}
	.rule_status input{
		width:100%;
		height:100%;
		opacity:0;
		border:#000 solid 1px;
	}
	.rule_status div{
		display:inline-block;
		width:72px;
		height:24px;
		line-height:24px;
		border-radius: 5px;
	}
	.block{
		position: relative;
		display:inline-block;
		width:45px;
		height:100%;
		float:left;
		top:-31px;
		background:#FFFFFF;
		border-radius: 5px;
		box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
	}

	.statusON{
		background:	#01B468;
	}
	.statusON a:before{
		content: '开启';
		color:black;
	}
	.statusON a{
		float:right;
	}
	.statusOFF{
		background:	#E63F00 ;
	}
	.statusOFF a:before{
		content: '关闭';
		color:black;
	}
	.dialogContent{
		display:inline-block;
		height:20px;
		line-height:20px;
		font-size:15px;
	}
</style>
<#assign webEnd in model>
<script>
	function crm_download(caseId,date){
		var exportDate="";
		var isformer=false;
		if(date=="today"){
			var now = new Date();
			var year = now.getFullYear();
			var month = now.getMonth() + 1;
			var day = now.getDate();
			exportDate=year + '-' + month + '-' + day ;
			isformer=false;
		}else if(date=="former"){
			var now = new Date();
			var date = new Date(now.getTime() - 7 * 24 * 3600 * 1000);
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			var day = date.getDate();
			exportDate=year + '-' + month + '-' + day ;
			isformer=true;
		}
		window.location.href="/case/export?caseId="+caseId+"&exportDate="+exportDate+"&isformer="+isformer;
	}
	function getMsgCodeTemp(caseId,sendType,MsgCode){
		$.ajax({
			url : "/msg/temp/get",
			type : "post",
			data : {"msgType":sendType,"code":MsgCode},
			success : function(data){
				if(data.success==true){
					var result=data.result;
					
					var outLink="";
					if(result.outLink!="null"){
						outLink=result.outLink;
					}
					
					var contentHTML=""
					if(sendType=="sms"){
						contentHTML ="<p class='dialogContent'>ID："+result.code+"</p><br/><p class='dialogContent'>内容："+result.content+"</p>";
					}else if(sendType=="push"){
						contentHTML ="<p class='dialogContent'>ID："+result.code+"</p><br/><p class='dialogContent'>标题："+result.title+"</p><br/><p class='dialogContent'>内容："+result.content+"</p><br/><p class='dialogContent'>链接：<input id='outLink' style='width: 400px; height: 20px; font-size: 15px; line-height: 28px; ' type='text' value='"+outLink+"'/></p>";
					}
					var tempId=result.id;
					 $.cpc.dialog.init({
			            id: "get_temp",
			            title: "提示",
			            html: contentHTML,
			            delDialog: true,
			            buttonTxt: {
			                save: '确定',
			                cancel: '取消'
			            },
			            callback: function () {
			            	$("#get_temp .J_Save").on("click", function() {
			            		if(sendType=="push"){
			            			var newOutLink=$("#outLink").val();
			            			if(newOutLink!==outLink){
			            				$.ajax({
											url : "/push/outLink/update",
											type : "post",
											data : {"caseId":caseId,"tempId":tempId,"outLinkUrl":newOutLink},
											success : function(data){
												if(typeof(data.success)!="undefined" && data.success=="true"){
													alert("修改成功");
												}else{
													alert("修改失败，原因："+data.ret.resultDes);
												}
											}
										})
			            			}
			            		}
			            		 $.cpc.dialog.closedialog();
			            	});
			            }
			        })
				}else if(data.success==false){
					alert("该用例未添加消息模板");
				}
				
			}
        })
	}
	
	function changeStatus(id,status,type){
	console.log(status)
		if(status=='y'){
			var html="<p class='dialogContent'>确定要关闭这条用例吗？</p>";
			var blockClass="statusOFF";
			if(type=="push"){
				var url="/rule/close";
			}else if(type=="filter"){
				var url="/rule/filter/close";
			}
		}else if(status=='n'){
			var html="<p class='dialogContent'>确定要开启这条用例吗？</p>";
   			var blockClass="statusON";
			if(type=="push"){
				var url="/rule/open";
			}else if(type=="filter"){
				var url="/rule/filter/open";
			}
		}
		
		$.cpc.dialog.init({
            id: "change_status",
            title: "提示",
            html: html,
            delDialog: true,
            buttonTxt: {
                save: '确定',
                cancel: '取消'
            },
            callback: function () {
            	$("#change_status .J_Save").on("click", function() {
					$.ajax({
						url : url,
						data : {"caseId":id},
						success : function(data){
							if(data.success==true && data.result==true){
								var obj = document.getElementById(type+"_case"+id);
								obj.setAttribute("class",blockClass);
								if(status=="n"){
									status="y";
									obj.innerHTML="<input type='checkbox' onchange=changeStatus("+id+",'"+status+"','"+type+"') /><a class='block'></a>";
								}else if(status=="y"){
									status="n";
									obj.innerHTML="<input type='checkbox' onchange=changeStatus("+id+",'"+status+"','"+type+"') checked/><a class='block'></a>";
							}
							}else{
								alert("修改失败");
							}
							$.cpc.dialog.closedialog();
						}
					})
				});
            }
        })
	}
	
	function couponOption(caseId,templateId){
		if(templateId=="点击配置" || templateId=="0" ){
			templateId="";
		}
		// 这里需要做一个输入检验 
		contentHTML ="<p class='dialogContent'>用例ID："+caseId+"</p><br/><p class='dialogContent'>红包模板ID：<input id='templateId' style='width: 400px; height: 20px; font-size: 15px; line-height: 28px;' type='text' value='"+templateId+"'/></p>";
		$.cpc.dialog.init({
			id: "update_coupon_template",
			title: "提示",
			html: contentHTML,
			delDialog: true,
			buttonTxt: {
				save: '确定',
				cancel: '取消'
			},
			callback: function () {
				$("#update_coupon_template .J_Save").on("click", function() {
					var newTemplateId=$("#templateId").val();
					if(newTemplateId!=templateId){
						$.ajax({
							url : "/rule/template/update",
							type : "post",
							data : {"caseId":caseId,"templateId":newTemplateId},
							success : function(data){
								if(data.success==true && data.result==true){
									alert("修改成功");
								}else{
									alert("修改失败");
								}
								if(newTemplateId==0){
									newTemplateId="点击配置";
								}
								$("#caseId_"+caseId).parent().html("<a id='caseId_"+caseId+"' href='javascript:;' onclick=couponOption('"+caseId+"','"+newTemplateId+"')>"+newTemplateId+"</a>")
								//$("#caseId_"+caseId).html(newTemplateId);
							}
						})
					}
					$.cpc.dialog.closedialog();
				})
			}
        })
	}
</script>
</#assign>
<@model.webend />
