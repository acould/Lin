<#assign webTitle="平台管理-操作日志" in model>
<#assign webMenu="sys" in model>
<@model.webhead />
<#include "/common/menu_top.ftl">
<div id="wrapper">
	<div class="content clearfix bigfs">
		<#include "/common/menu_left.ftl">
		<div id="wrap" class="clearfix pt30 pl30">
			<div id="breadcrumb" class="title">${(pageName)?if_exists}</div>
			<div class="clearfix">
				<div class="row mt30">
					<label>操作人员：</label>
					<input type="text" class="base-input">
				</div>
				<div class="row">
					<label>操作页面：</label>
					<input type="text" class="base-input">
				</div>
				<div class="row">
					<label>操作类型：</label>
					<input type="text" class="base-input">
				</div>
				<div class="row">
					<label>操作表：</label>
					<input type="text" class="base-input">
				</div>
				<div class="action">
					<label>&nbsp;</label>
					<input type="submit" value="查 询" class="btn confirm">
				</div>
			</div>
			<div class="clearfix mt30">
			<div class="table">
				<table>
					<thead>
						<tr>
							<th width="100" class="tal">操作人员</th>
							<th width="20%" class="tal">操作页面</th>
							<th width="100">操作类型</th>
							<th width="100">操作表</th>
						</tr>
					</thead>
				<tfoot>
					<tr>
					<td colspan="100%">
						<div class="pagination"><a href="#" class="current">1</a><a href="#">2</a><span>……</span><a href="#">100</a><a href="#">下一页</a></div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<td class="tal">花名-天涯何处无芳草</td>
						<td class="tal">/index.html</td>
						<td>删除</td>
						<td>data</td>
					</tr>
					<tr>
						<td class="tal">花名-天涯何处无芳草</td>
						<td class="tal">/index.html</td>
						<td>删除</td>
						<td>data</td>
					</tr>
					<tr>
						<td class="tal">花名-天涯何处无芳草</td>
						<td class="tal">/index.html</td>
						<td>删除</td>
						<td>data</td>
					</tr>
					<tr>
						<td class="tal">花名-天涯何处无芳草</td>
						<td class="tal">/index.html</td>
						<td>删除</td>
						<td>data</td>
					</tr>
					<tr>
						<td class="tal">花名-天涯何处无芳草</td>
						<td class="tal">/index.html</td>
						<td>删除</td>
						<td>data1</td>
					</tr>
				</tbody>
				</table>
			</div>
			</div>
		</div>
	</div>
</div>
<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/platform/user/add.min.js?v=${model.static_version}"></script>
</#assign>
<@model.webend />

