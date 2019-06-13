<#assign webNav="Y" in model>
<#assign webTitle="仓库管理系统" in model>
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
<li><a href="/sys/permission/list.html">权限管理</a></li>
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
								<td><a href="/sys/permission/edit.html" class="btn btn-primary">添 加</a>
								</td>
								<td style="text-align:right" >
									  <form action="/sys/permission/list.html" method="get" class="form_search">
								        	<label>权限类型：</label>
								        	<select name="category">
												<option value="">选择权限类型</option>
												<option value="menu" <#if (category)?? && category=="menu">selected</#if>>菜单</option>
												<option value="page" <#if (category)?? && category=="page">selected</#if>>页面</option>
												<option value="operate" <#if (category)?? && category=="operate">selected</#if>>操作</option>
											</select>
											<select name="firstGroupId" data-id="${(firstGroupId)?if_exists}" class="select2-search__field group level1">
												<option value="">选择一级分组</option>
											</select>
											<select name="secondGroupId" data-id="${(secondGroupId)?if_exists}" class="select2-search__field group level2">
												<option value="">选择二级分组</option>
											</select>
											<select name="thirdGroupId" data-id="${(thirdGroupId)?if_exists}" class="select2-search__field group level3">
												<option value="">选择三级分组</option>
											</select>
									        <input type="text" name="name" value="${name!}${url!}" placeholder="请输入权限名或url地址..." class="searchField base-input">
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
									<th>平台</th>
									<th>权限类型</th>
									<th class="hidden-480">权限名</th>
									<th>链接地址</th>
									<th>是否验证</th>
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
												<a href="#">${r.platform}</a>
											</td>
											<#assign typeObj = {"menu":"菜单", "page":"页面", "operate":"操作"}>
											<td>${typeObj[r.category!]}</td>
											<td class="hidden-480">${r.name!} </td>
											<td>${r.url!}</td>
											<td>${yesNo[r.verify!'n']}</td>
											<td><#if r.createTime??>${r.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
			
											<td class="hidden-480">
												<#if r.modifyTime??>${r.modifyTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
											</td>
			
											<td>
												<div class="hidden-sm hidden-xs action-buttons">
													<a href="javascript:;" class="blue J_PublishMenu" data-id="${r.id!}">
														<i class="ace-icon fa fa-search-plus bigger-130">发布</i>
													</a>
			
													<a class="green" href="/sys/permission/edit.html?id=${r.id}">
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
									<#if (name)??><#assign searchParam="&name=${name!}"><#elseif (url)??><#assign searchParam="&url=${url?url}"></#if>
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
<script src="${model.static_domain}/js/admin/platform/permission/list.min.js?v=${model.static_version}"></script>
</#assign>
<#include "/common/listcommon/listdataTables.ftl">
<@model.webend />
