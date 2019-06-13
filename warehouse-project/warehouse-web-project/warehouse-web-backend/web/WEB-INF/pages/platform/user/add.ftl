<#assign webNav="Y" in model>
<#assign webTitle="平台管理-新增账户" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<style>
label{ min-width: 88px; width: auto; padding-right: 10px;}
.role-list {width: 89%;}
.role-list label {text-align: left;}
input:disabled {background-color:#efefef;}
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
<li><a href="/sys/user/list.html">账号管理</a></li>
<li class="active"><#if (sysUserVo.id)??>修改账号<#else>新增账号</#if></li>
</#assign>
<@model.mainPageContentTop />
<#--  内容 -->
<div class="row clearfix pt30 pl30" id="wrap">
	<div class="col-xs-12 clearfix">
		<#-- PAGE CONTENT BEGINS -->	
			<form class="form-horizontal" role="form">		
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">用户名:</label>
						<#if (sysUserVo.id)?exists >
						<input type="text" name="name" class="base-input col-xs-10 col-sm-2" value="${(sysUserVo.name)?if_exists}" disabled>
						<#else>
						<input type="text" name="name" class="base-input col-xs-10 col-sm-2">
						</#if>
						<b>* 必填</b>
					</div>
					<#if !(sysUserVo.id)?exists >
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1">密码：</label>
							<input type="password" name="passwd" class="base-input col-xs-10 col-sm-2" >
							<b>* 必填</b>
						</div>
					</#if>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">展示名称：</label>
						<input type="text" name="realname" class="base-input col-xs-10 col-sm-2" value="${(sysUserVo.realname)?if_exists}">
						<b>* 必填</b>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">角色：</label>
						<div class="dib role-list">
							<#if (sysUserVo.id)?exists >
								<#if (sysRoles?exists && sysRoles?size>0)>
									<#list sysRoles as item>
									<label>
										<input type="checkbox" name="role" value="${item.id}" <#if item.check=="y">checked</#if>>
										<span>${item.name}</span>
									</label>
									</#list>
								</#if>
							<#else>
								<#if (sysRoles?exists && sysRoles?size>0)>
									<#list sysRoles as item>
										<label>
											<input type="checkbox" name="role" value="${item.id}">
											<span>${item.platform}-${item.name}</span>
										</label>
									</#list>
								</#if>
							</#if>
						</div>
					</div>
					<div class="action pb30">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">&nbsp;</label>
						<input type="submit" value="提 交" class="btn btn-lg btn-success J_Save" data-id="${(sysUserVo.id)?if_exists}">
					</div>
			</form>
	</div>
</div>
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/root/encrypt.min.js?v=${model.static_version}"></script>
<script src="${model.static_domain}/js/admin/platform/user/add.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />
