<#assign webNav="Y" in model>
<#assign webTitle="平台管理-账户信息" in model>
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
<li><a href="/sys/user/list.html">账号管理</a></li>
<li class="active"><#if (sysUserVo.id)??>修改账号<#else>新增账号</#if></li>
</#assign>
<@model.mainPageContentTop />

		<div id="wrap" class="clearfix pt30 pl30">
			<div id="breadcrumb" class="title">${(pageName)?if_exists}</div>
			<div class="clearfix">
				<div class="row mt30">
					<label>账户名称：</label>
					<div class="text">${(sysUserVo.realname)?if_exists}</div>
				</div>
				<div class="row">
					<label>密码：</label>
					<div class="text">
					<span>***&nbsp;***&nbsp;***&nbsp;***&nbsp; </span>
					<a href="javascript:;" id="J_Password" userid="${sysUser.id!}" style="color:#ff8800">修改密码</a>
					</div>
				</div>
				<div class="row">
					<label>展示名称：</label>
					<div class="text">${(sysUserVo.name)?if_exists}</div>
				</div>
				<div class="row">
					<label>角色：</label>
					<div class="text">${(sysUserVo.roleName)?if_exists}</div>
				</div>
			</div>
		</div>
		
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/root/encrypt.min.js?v=${model.static_version}"></script>
<script src="${model.static_domain}/js/admin/platform/user/info.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />	
