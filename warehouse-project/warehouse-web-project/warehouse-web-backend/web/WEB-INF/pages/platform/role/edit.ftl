<#assign webNav="Y" in model>
<#assign webTitle="平台管理-角色管理-新增角色" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<script type="text/javascript" src="${model.static_domain}/js/lits/jquery.tools.min.js"></script>
<style>
select {width:260px !important; height:30px; padding:0;}
.J_role {display: none;}
.role {position: relative; color: #666;}
.role .tab {position: absolute;left: 95px; top: -33px;}
.role .tab li {float:left; margin-right: -1px; padding: 6px 14px; border: 1px solid #ccc; border-radius: 6px 6px 0 0; font-size:14px; cursor:pointer;}
.role .tab li.cur {padding-bottom: 7px; font-weight:bold; background: #efefef;}
.role .block {margin: 40px 0 0 95px; min-width: 900px; height: 350px; overflow:auto; border:1px solid #ddd;}
.role .block li {display:none; padding: 8px; }
.role .block li.cur {display:block;}
.role .block li .item {margin: 10px 0;}
.role .block li .item label {width: 16%; overflow: hidden; text-overflow: ellipsis; white-space:nowrap;}
.role .block li .item a {color:#666; font-weight: bold; font-size: 13px;}
.role .block li .item a:hover {text-decoration:none;}
.role .block li .item.tree {background: url(${model.static_domain}/images/admin/ui/tree.png) no-repeat 5px 25px;}
.role .block li .item.tree .level3 {display: block;}
.role .block li .level2 {margin-bottom: 0;}
.role .block li .level2 label {height: 25px;}
.role .block li .level3 {display:none; margin: 0 0 0 27px; color: #666;}
</style>
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<@model.mainContainerTop />
<#-- 菜单，内容，version底部 -->
<#include "/common/menu_left.ftl">
<#-- 内容导航 -->
<#assign mainPageContentTopNav in model>
<li><a href="/sys/role/list.html">角色管理</a></li>
<li class="active"><#if (dataVo.id)??>修改角色<#else>新增角色</#if></li>
</#assign>
<@model.mainPageContentTop />

		<div id="wrap" class="clearfix pt30 pl30">
			<div id="breadcrumb" class="title"><#if roleVo.id??><em>编辑角色</em><#else><em>新增角色</em></#if></div>
			<div class="clearfix">
				<div class="row mt30">
					<label>平台类型：</label>
					<select name="platform" data-id="${(roleVo.id)!}">
						<option value="-1">选择平台类型</option>
						<option value="CPSBK" <#if (roleVo.platform?? && roleVo.platform=="CPSBK")>selected</#if>>CPSBK</option>
						<option value="CPSMC" <#if (roleVo.platform?? && roleVo.platform=="CPSMC")>selected</#if>>CPSMC</option>
					</select>
				</div>
				<div class="row mt30">
					<label>角色名称：</label>
					<input type="text" class="base-input" name="name" value="${(roleVo.name)!}">
				</div>
				<div class="row J_role">
			        <label>角色权限：</label>
			        <div class="role">
			        	<ul class="tab clearfix"></ul>
			        	<ul class="block"></ul>
			        </div>
			    </div>
						
				<div class="action pb30">
					<label>&nbsp;</label>
					<#if (roleVo.id)??>
						<a href="javascript:;" class="btn confirm J_Save" data-id="${(roleVo.id)?if_exists}">更新</a>
					<#else>
						<a href="javascript:;" class="btn btn-lg btn-success J_Save">新增角色</a>
					</#if>
				</div>
			</div>
		</div>
		
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/platform/role/roleEdit.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />	
