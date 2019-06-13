<#assign webNav="Y" in model>
<#assign webTitle="仓库管理系统-自动回复消息设置" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<style>
select {width:130px !important; height:30px; padding:0;}
.form_search .base-input {width:200px;height: 24px;}
.form_search label {font-size:14px;}
</style>
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<#assign yesNo = {'y':"是", 'n':'否'} />
<@model.mainContainerTop />
<#-- 菜单，内容，version底部 -->
<#include "/common/menu_left.ftl">
<#-- 内容导航 -->
<#assign mainPageContentTopNav in model>
<li><a href="/wechat/autoresp/list.html">自动回复消息设置</a></li>
<li class="active">列表信息</li>
</#assign>
<@model.mainPageContentTop />


<#--  内容 -->
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
			<#--  表格内容bgn -->
			<div class="row">
				<div class="col-xs-12">
					<#--  主题内容txt
					<div class="table-header">
						Results for "Latest Registered Domains"
					</div>
					 -->
					
					
					<div class="page-header">
						<table style="width:98%;">
							<tr>
								<td><a href="/wechat/autoresp/edit.html" class="btn btn-primary">添 加</a>
								</td>
								<td style="text-align:right" >
									 <form action="/wechat/autoresp/list.html" method="get" class="form_search">
							        	<#if (gzhNamelist?exists && gzhNamelist?size>0) >
											<select name="appSettingId">
												<option value="">选择公众号</option>
												<#list gzhNamelist as r>
													<option value="${r.id!}" <#if (appSettingId?exists && (r.id == appSettingId) )>selected</#if>>${r.gzhName!}</option>
												</#list>
											</select>
										<#else>
											公众号选择
										</#if>
							        	<select name="msgType">
											<option value="">选择自动回复消息类型</option>
											<option value="subscribe" <#if (msgType)?? && msgType=="subscribe">selected</#if>>被关注</option>
											<option value="keyValue" <#if (msgType)?? && msgType=="keyValue">selected</#if>>关键词</option>
											<option value="receiveMsg" <#if (msgType)?? && msgType=="receiveMsg">selected</#if>>收到消息</option>
											<option value="preService" <#if (msgType)?? && msgType=="preService">selected</#if>>转人工前消息</option>
										</select>
							        	<select name="eventType">
											<option value="">选择事件类型</option>
											<option value="link" <#if (eventType)?? && eventType=="link">selected</#if>>跳转链接</option>
											<option value="miniapp" <#if (eventType)?? && eventType=="miniapp">selected</#if>>跳转小程序</option>
											<option value="txtmsg" <#if (eventType)?? && eventType=="txtmsg">selected</#if>>发送文本</option>
											<option value="picmsg" <#if (eventType)?? && eventType=="picmsg">selected</#if>>发送图片</option>
											<option value="picurlmsg" <#if (eventType)?? && eventType=="picurlmsg">selected</#if>>发送图文</option>		
										</select>
										<input type="submit" value="筛选" class="btn btn-warning btn-search editor">
							        </form>
								</td>
							</tr>
						</table>
					</div><!-- /.page-header -->
				
					<!-- div.table-responsive -->
	
					<!-- div.dataTables_borderWrap -->
					<div id="wrap">
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<#-- 表格标题 -->
							<thead>
								<tr>
									<th class="center">
										<label class="pos-rel">
											<input type="checkbox" class="ace" />
											<span class="lbl"></span>
										</label>
									</th>
									<th>公众号名称</th>
									<th>消息类型</th>
									<th>绑定用户</th>
									<th>关键词匹配类型</th>
									<th>关键词内容</th>
									<th>事件类型</th>
									<th>事件内容</th>
									<th>
										<i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
										创建时间
									</th>
									<th class="hidden-480">更新时间</th>
	
									<th>操作</th>
								</tr>
							</thead>
	
							<tbody>
							   <#if (bizObj.list?exists && bizObj.list?size>0)>
									<#list bizObj.list as r>
										<tr>
											<td class="center">
												<label class="pos-rel">
													<input type="checkbox" class="ace" />
													<span class="lbl"></span>
												</label>
											</td>
			
											<td>
												<a href="#">${r.gzhName!}</a>
											</td>
											<td>
												<#if (r.msgType??)>
													<#if  r.msgType=='subscribe'>被关注<#elseif  r.msgType=='keyValue'>关键词<#elseif  r.msgType=='receiveMsg'>收到消息<#elseif  r.msgType=='txtmsg'>转人工前消息</#if>
												</#if>
											</td>
											<td><#if (r.isBindUser?? && r.isBindUser==1)>是<#else>否</#if></td>
											<td><#if (r.keyType?? && r.keyType=='equal')>全匹配<#elseif ((r.keyType?? && r.keyType=='like'))>否</#if></td>
											<td style="text-align:left;">${r.contentKey!}</td>
											<td>
												<#if (r.eventType??)>
													<#if  r.eventType=='submenu'>子菜单<#elseif  r.eventType=='link'>跳转链接<#elseif  r.eventType=='miniapp'>跳转小程序<#elseif  r.eventType=='txtmsg'>发送文本
													<#elseif  r.eventType=='picmsg'>发送图片<#elseif  r.eventType=='picurlmsg'>发送图文</#if>
												</#if>
											</td>
											<td style="text-align:left;">${r.eventContent!}</td>
											<td><#if r.createTime??>${r.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
			
											<td class="hidden-480">
												<#if r.modifyTime??>${r.modifyTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
											</td>
			
											<td>
												<div class="hidden-sm hidden-xs action-buttons">
			
													<a class="green" href="/wechat/autoresp/edit.html?id=${r.id}">
														<i class="ace-icon fa fa-pencil bigger-130"></i>
													</a>
			
													<a  class="red J_Delete" href="javascript:;" data-id="${r.id!}" >
														<i class="ace-icon fa fa-trash-o bigger-130"></i>
													</a>
												</div>
											</td>
										</tr>
									</#list>
								</#if>
								
							</tbody>
						</table>
						
						<tfoot>
							<tr>
								<td colspan="100%">
									<#if (name)??><#assign searchParam="&gzhName=${gzhName!}"><#elseif (url)??><#assign searchParam="&url=${url?url}"></#if>
									<@model.showPage url=vm.getUrlByRemoveKey(thisUrl,['start','size']) p=bizObj.page parEnd="cmd=search&category=${category!}&firstGroupId=${firstGroupId!}&secondGroupId=${secondGroupId!}&thirdGroupId=${thirdGroupId!}${searchParam!}" />
								</td>
							</tr>
						</tfoot>
					</div>
				</div>
			</div>

		<#--  表格内容end -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->
						
						
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<#-- sys js -->
<script src="${model.static_domain}/js/admin/platform/wechat/autorespList.min.js?v=${model.static_version}"></script>
</#assign>
<#include "/common/listcommon/listdataTables.ftl">
<@model.webend />

