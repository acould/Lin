<#assign webNav="Y" in model>
<#assign webTitle="仓库管理系统-平台管理-文本字典资源管理" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<script type="text/javascript">
	jQuery.browser={};(function(){jQuery.browser.msie=false; jQuery.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)./)){ jQuery.browser.msie=true;jQuery.browser.version=RegExp.$1;}})();
</script>
<script type="text/javascript" src="${model.static_domain}/js/lits/jquery.tools.min.js"></script>
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<#assign yesNo = {'y':"是", 'n':'否'} />
<@model.mainContainerTop />
<#-- 菜单，内容，version底部 -->
<#include "/common/menu_left.ftl">
<#-- 内容导航 -->
<#assign mainPageContentTopNav in model>
<li><a href="/platform/mresourcetext/list.html">文本资源管理</a></li>
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
								<td><a href="/platform/mresourcetext/detail.html" class="btn btn-primary">添 加</a>
								</td>
								<td style="text-align:right" >
									 <form action="/platform/mresourcetext/list.html" method="get" class="form_search fr">
							        	 <table>
					                        <tr>
					                            <td>
					                                <label>资源类型：</label>
					                                <input type="text" placeholder="type" name="type" value="${type!}" class="base-input">
					                            </td>
					                            <td>
					                                <label>资源标识：</label>
					                                <input type="text" placeholder="key" name="key" value="${key!}" class="base-input">
					                            </td>
					                            <td>
					                                <label>资源值：</label>
					                                <input type="text" placeholder="value" name="value" value="${value!}" class="base-input">
					                            </td>
					                            <td>
					                                <label>自定义字段1：</label>
					                                <input type="text" placeholder="自定义字段1" name="customField1" value="${customField1!}" class="base-input">
					                            </td>
					                            <td colspan="2">
					                                <input type="submit" value="查 询" class="btn btn-warning btn-search editor">
					                            </td>
					                        </tr>
					                    </table>
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
									<th>资源类型</th>
									<th>资源标识</th>
									<th>资源值</th>
									<th>自定义字段1</th>
									<th>自定义字段2</th>
									<th>自定义字段3</th>
									<th>自定义字段4</th>
									<th>排序</th>
									<th>状态</th>
	
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
			
											<td>${r.type!}</td>
											<td>${r.key!}</td>
											<td>${r.value!}</td>
											<td>${r.customField1!}</td>
											<td>${r.customField2!}</td>
											<td>${r.customField3!}</td>
											<td>${r.customField4!}</td>
											<td>${r.ordering!}</td>
											<td>${r.statusStr!}</td>
											<td><#if r.createTime??>${r.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
			
											<td class="hidden-480">
												<#if r.modifyTime??>${r.modifyTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
											</td>
			
											<td>
												<div class="hidden-sm hidden-xs action-buttons">
													<a class="green" href="/platform/mresourcetext/detail.html?key=${r.key!}&type=${r.type!}">
														<i class="ace-icon fa fa-pencil bigger-130"></i>
													</a>
													<a href="javascript:;" class="mb5 btn del J_ResourceDel" data-key="${r.key}" data-type="${r.type}">设置无效</a>
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
<script src="${model.static_domain}/js/admin/platform/mresourcetext/list.min.js?v=${model.static_version}"></script>
</#assign>
<#include "/common/listcommon/listdataTables.ftl">
<@model.webend />

