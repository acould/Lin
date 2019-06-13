<#assign webNav="Y" in model>
<#assign webTitle="仓库管理系统-新增权限" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<script type="text/javascript" src="${model.static_domain}/js/lits/jquery.tools.min.js"></script>
<style>
select {width:260px !important; height:30px; padding:0;}
.group {width:120px !important;margin-left:3px;}
b {color:#c00;}
</style>
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<@model.mainContainerTop />
<#-- 菜单，内容，version底部 -->
<#include "/common/menu_left.ftl">
<#-- 内容导航 -->
<#assign mainPageContentTopNav in model>
<li><a href="/sys/permission/list.html">公众号管理</a></li>
<li class="active"><#if (permission.id)??>修改权限<#else>新增权限</#if></li>
</#assign>
<@model.mainPageContentTop />

		<div id="wrap" class="clearfix pt30 pl30">
			<div class="clearfix">
				<div class="row mt30">
					<label>权限类型：</label>
					<select name="category">
						<option value="">选择权限类型</option>
						<option value="menu" <#if (permission.category)?? && permission.category=="menu">selected</#if>>菜单</option>
						<option value="page" <#if (permission.category)?? && permission.category=="page">selected</#if>>页面</option>
						<option value="operate" <#if (permission.category)?? && permission.category=="operate">selected</#if>>操作</option>
					</select>
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>所属分组：</label>
					<select name="firstGroupId" data-id="${(permission.firstGroupId)?if_exists}" class="group level1">
						<option value="">选择一级分组</option>
					</select>
					<select name="secondGroupId" data-id="${(permission.secondGroupId)?if_exists}" class="group level2">
						<option value="">选择二级分组</option>
					</select>
					<select name="thirdGroupId" data-id="${(permission.thirdGroupId)?if_exists}" class="group level3">
						<option value="">选择三级分组</option>
					</select>
				</div>
				<div class="row mt30">
					<label>权限名：</label>
					<input type="text" class="base-input" name="name" value="${(permission.name)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>平台类型：</label>
					<select name="platform">
						<option value="">选择平台类型</option>
						<option value="CPSBK" <#if (permission.platform)?? && permission.platform=="CPSBK">selected</#if>>CPSBK</option>
						<option value="CPSMC" <#if (permission.platform)?? && permission.platform=="CPSMC">selected</#if>>CPSMC</option>
					</select>
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>URL：</label>
					<input type="text" class="base-input" name="url" value="${(permission.url)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>是否验证：</label>
					<label class="tal">
						<input type="radio" name="verify" value="y" <#if !(permission??) || ((permission.verify)?? && permission.verify=="y")>checked</#if> >
						<span>是</span>
					</label>
					<label class="tal">
						<input type="radio" name="verify" value="n" <#if ((permission.verify)?? && permission.verify=="n")>checked</#if>>
						<span>否</span>
					</label>
				</div>		
						
				<div class="action pb30">
					<label>&nbsp;</label>
					<#if (dataVo.id)??>
						<a href="javascript:;" class="btn confirm J_Save" data-id="${(dataVo.id)?if_exists}">更新</a>
					<#else>
						<a href="javascript:;" class="btn btn-lg btn-success J_Save">新增权限</a>
					</#if>
				</div>
			</div>
		</div>
		
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/platform/permission/list.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />	
