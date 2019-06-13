<#assign webNav="Y" in model>
<#assign webTitle="平台管理-角色管理" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<script type="text/javascript" src="${model.static_domain}/js/lits/jquery.tools.min.js"></script>
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<@model.mainContainerTop />
<#-- 菜单，内容，version底部 -->
<#include "/common/menu_left.ftl">
<#-- 内容导航 -->
<#assign mainPageContentTopNav in model>
<li><a href="/sys/role/list.html">角色管理</a></li>
<li class="active">列表信息</li>
</#assign>
<@model.mainPageContentTop />


<#-- 
<#include "/common/page/mainPageContentTop.ftl">
 -->


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
								<td><a href="/sys/role/edit.html" class="btn btn-primary">新建角色</a>
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
									<th>序号</th>
									<th>角色</th>
									<th>权限模块</th>
									<th>操作</th>
								</tr>
							</thead>
	
							<tbody>
							   <#if (rolePermission?exists && rolePermission?size>0)>
									<#list rolePermission as r>
										<tr>
											<td class="center">
												<label class="pos-rel">
													<input type="checkbox" class="ace" />
													<span class="lbl"></span>
												</label>
											</td>
											<td>${r.id!}</td>
											<td class="tal">
												<a href="#">${r.name!}</a>
											</td>
											<td>${r.permission!}</td>
			
											<td>
												<div class="hidden-sm hidden-xs action-buttons">
													<a class="green" href="/sys/role/edit.html?id=${r.id}">
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
<script src="${model.static_domain}/js/admin/platform/role/roleList.min.js?v=${model.static_version}"></script>
</#assign>
<#include "/common/listcommon/listdataTables.ftl">
<@model.webend />

