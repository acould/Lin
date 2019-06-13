<#assign webNav="Y" in model>
<#assign webTitle="多智啦仓库管理系统" in model>
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
<li><a href="/store/in/list.html">仓库入库单管理</a></li>
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
					
					<div class="page-header">
						<table style="width:98%;">
							<tr>
								<td><a href="/store/in/edit.html" class="btn btn-primary">添 加</a>
								</td>
								<td style="text-align:right" >
									 <form action="/store/in/list.html" method="get" class="form_search">
										
										<select name="searchKey">
											<option>选择查询条件</option>
											<option value="code" <#if searchKey?? && searchKey=='code'>selected</#if> >单号</option>
											<option value="cause" <#if searchKey?? && searchKey=='cause'>selected</#if>  >原因</option>
											<option value="warehouseName" <#if searchKey?? && searchKey=='warehouseName'>selected</#if>  >仓库</option>
											<option value="supplierName" <#if searchKey?? && searchKey=='supplierName'>selected</#if>  >供应商</option>
										</select>	
										<#-- 这里怎么把查询条件传给控制器 -->
										<input type="text" name="keyValue" value="${keyValue!}" placeholder="请输入..."  class="searchField base-input">
										<input type="submit" value="查询" class="btn btn-warning btn-search editor">
									 
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
									<th>入库单号</th>
									<th>单号类型</th>
									<th><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>制单时间</th>
									<th>备注</th>
									<th>仓库</th>
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
												<a href="#">${r.code!}</a>
											</td>
											<td><#if r.outType??>
												<#if r.outType=="purchase">采购单<#elseif r.outType=="reback">退款单<#elseif r.outType=="produce">生产单
												<#else>其他</#if>
											</#if></td>
											<td><#if r.operateTime??>${r.operateTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
											<td>${r.remark!}</td>
											<td>${r.warehouseName!}</td>
			
											
											<td>
												<div class="hidden-sm hidden-xs action-buttons">
													<a class="green" href="/store/in/edit.html?id=${r.id!}">
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
									<@model.showPage url=vm.getUrlByRemoveKey(thisUrl,['start','size']) p=bizObj.page parEnd="cmd=search&name=${name!}${searchParam!}" />
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
<script src="${model.static_domain}/js/admin/store/in/list.min.js?v=${model.static_version}"></script>
</#assign>
<#include "/common/listcommon/listdataTables.ftl">
<@model.webend />

