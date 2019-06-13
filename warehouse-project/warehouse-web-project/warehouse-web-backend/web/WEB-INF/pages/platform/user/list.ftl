<#assign webNav="Y" in model>
<#assign webTitle="仓库管理系统-账号管理" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<script type="text/javascript">
	jQuery.browser={};(function(){jQuery.browser.msie=false; jQuery.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)./)){ jQuery.browser.msie=true;jQuery.browser.version=RegExp.$1;}})();
</script>
<script type="text/javascript" src="${model.static_domain}/js/lits/jquery.tools.min.js"></script>
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<@model.mainContainerTop />
<#-- 菜单，内容，version底部 -->
<#include "/common/menu_left.ftl">
<#-- 内容导航 -->
<#assign mainPageContentTopNav in model>
<li><a href="/sys/user/list.html">账号管理</a></li>
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
								<td><a href="/sys/user/add.html" class="btn btn-primary">添 加</a>
								</td>
								<td style="text-align:right" >
							        <form action="/sys/user/list.html" method="get" class="form_search">
										<span class="ml10">账号：</span>
										<input type="text" value="${name!}"  class="base-input" name="name" placeholder="账号"/>
										<span class="ml10">展示名称：</span>
										<input type="text" value="${realname!}" class="base-input" name="realname" placeholder="展示名称"/>
										<span class="ml10">角色名称：</span>
										<input type="text" value="${rolename!}" class="base-input" name="rolename" placeholder="角色名称"/>
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
									<th>账号</th>
									<th>展示名称</th>
									<th class="hidden-480">角色</th>
	
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
			
											<td  class="tal">
												<a href="#">${r.name!}</a>
											</td>
											<td class="realname tal">${r.realname!}</td>
											<td>${r.rolename!} </td>
											<td>
												<div class="hidden-sm hidden-xs action-buttons">
													<a href="javascript:;" class="red J_initpasswdWrap" data-id="${r.id?if_exists}">密码初始化</a>
													<a class="green" href="/sys/user/edit.html?id=${r.id}">
														<i class="ace-icon fa fa-pencil bigger-130">编辑</i>
													</a>
			
													<a  class="red J_Delete" href="javascript:;" data-id="${r.id!}" >
														<i class="ace-icon fa fa-trash-o bigger-130">注销</i>
													</a>
												</div>
											</td>
										</tr>
									</#list>
								</#if>
								
							</tbody>
						</table>
						
						<tfoot>
							<@model.showPage url=vm.getUrlByRemoveKey(thisUrl,['start','size']) p=bizObj.page parEnd="cmd=search" />
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
<script src="${model.static_domain}/js/admin/platform/user/userlist.min.js?v=${model.static_version}"></script>
</#assign>
<#include "/common/listcommon/listdataTables.ftl">
<@model.webend />

