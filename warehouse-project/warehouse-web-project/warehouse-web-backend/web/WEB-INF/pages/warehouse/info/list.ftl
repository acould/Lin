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
<li><a href="/warehouse/info/list.html">仓库管理</a></li>
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
								<td><a href="/warehouse/info/edit.html" class="btn btn-primary">新建仓库</a>
								</td>
								<td style="text-align:right" >
									 <form action="/warehouse/info/list.html" method="get" class="form_search">
									   <#--  
									 	<#if (warehouseClassDTOList?exists && warehouseClassDTOList?size>0) >
											<select name="warehouseClassId">
												<option value="">选择仓库类别</option> -->
												<#-- <#list warehouseClassDTOList as r> -->
												<#-- 	  				
													<option value="${r.id!}" <#if (warehouseClassId?? && warehouseClassId!='' && (r.id == (warehouseClassId?number)) )>selected</#if>>${r.name!}</option>
												
												</#list>
											</select>   
										</#if>
										-->
										<select name="warehouseType">
											<option value="">选择仓库货品性质</option>
											<option value="product_ok" <#if (warehouseType)?? && warehouseType=="product_ok">selected</#if>>成品</option>
											<option value="product_bug" <#if (warehouseType)?? && warehouseType=="product_bug">selected</#if>>残次品仓</option>
											<option value="material" <#if (warehouseType)?? && warehouseType=="material">selected</#if>>材料</option>
											<option value="other" <#if (warehouseType)?? && warehouseType=="other">selected</#if>>其他</option>
										</select>
										<select name="searchKey">
											<option>选择查询条件</option>
											<option value="name" <#if searchKey?? && searchKey=='name'>selected</#if> >按仓库名称查询</option>
											<option value="code" <#if searchKey?? && searchKey=='code'>selected</#if> >按仓库编码查询</option>
										</select>	
						
										<input type="text" name="keyValue" value="${keyValue!}" placeholder="请输入查询条件"  class="searchField base-input">  	
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
									<th>仓库名称</th>
									<th>仓库编号</th>
									<th class="hidden-480">仓库货品性质</th>
									<th>仓库类别</th>
									<th>排序</th>
									<th>备注说明</th>
									<th>
										<i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
										创建时间
									</th>
									<th class="hidden-480">更新时间</th>
	
									<th>操作</th>
								</tr>
							</thead>
	
							<tbody>
							<#-- bizObj对应controller里的model -->
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
											<#-- 仓库名称 -->
												<a href="#">${r.name!}</a>
											</td>
											<#-- 仓库编码-->
											<td>${r.code!}</td>
											<#-- 仓库类别 -->
											<td class="hidden-480">
												<#if (r.warehouseType??)> <#if r.warehouseType=="product_ok">成品
													<#elseif r.warehouseType=="product_bug">残次品仓
													<#elseif r.warehouseType=="material">材料
													<#elseif r.warehouseType=="other">其他</#if>
												</#if>
											</td>
											<#-- 仓库分类 -->
											<td>${r.warehouseClassId!}</td>
											<td>${r.orderby!}</td>
											<td>${r.remark!}</td>
											<td><#if r.createTime??>${r.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
			
											<td class="hidden-480">
												<#if r.modifyTime??>${r.modifyTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
											</td>
			
											<td>
												<div class="hidden-sm hidden-xs action-buttons">
												<#-- 编辑仓库 -->
													<a class="green" href="/warehouse/info/edit.html?id=${r.id}">
														<i class="ace-icon fa fa-pencil bigger-130"></i>
													</a>
												<#-- 删除仓库 -->
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
									<#if (name)??><#assign searchParam="&warehouseType=${warehouseType!}&warehouseClassId=${warehouseClassId!}&name=${name!}"><#elseif (url)??><#assign searchParam="&url=${url?url}"></#if>
									<@model.showPage url=vm.getUrlByRemoveKey(thisUrl,['start','size']) p=bizObj.page parEnd="cmd=search&warehouseType=${warehouseType!}&warehouseClassId=${warehouseClassId!}&name=${name!}${searchParam!}" />
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
<script src="${model.static_domain}/js/admin/warehouse/info/list.min.js?v=${model.static_version}"></script>
</#assign>
<#include "/common/listcommon/listdataTables.ftl">
<@model.webend />

