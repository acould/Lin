<#assign webNav="Y" in model>
<#assign webTitle="仓库管理系统-新增菜单" in model>
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
<li><a href="/wechat/menu/list.html">自定义菜单</a></li>
<li class="active"><#if (dataVo.id)??>修改菜单<#else>新增菜单</#if></li>
</#assign>
<@model.mainPageContentTop />

		<div id="wrap" class="clearfix pt30 pl30">
			<div class="clearfix">
				<div class="row mt30">
					<label>公众号名称：</label>
					<#if (gzhNamelist?exists && gzhNamelist?size>0) >
						<select name="appSettingId">
							<#list gzhNamelist as r>
								<option value="${r.id!}" <#if (dataVo?exists && (r.id == dataVo.appSettingId) )>selected</#if>>${r.gzhName!}</option>
							</#list>
						</select>
					<#else>
						请先编辑好公众号名称
					</#if>
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>菜单名称：</label>
					<input type="text"   class="base-input" name="menuName" value="${(dataVo.menuName)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>菜单类型：</label>
					<select name="menuType">
						<option value="">选择类型</option>
						<option value="main" <#if (dataVo.menuType)?? && dataVo.menuType=="main">selected</#if>>主菜单</option>
						<option value="sub" <#if (dataVo.menuType)?? && dataVo.menuType=="sub">selected</#if>>子菜单</option>
					</select>
					<b>* 必填</b>
				</div>
				<div class="row mt30 parentMenuWrap dn">
					<label> 主菜单名称：</label>
					<select name="parentId" data-id="${(dataVo.parentId)!}"></select>
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>菜单事件类型：</label>
					<select name="eventType">
						<option value="">选择类型</option>
						<option value="submenu" <#if (dataVo.eventType)?? && dataVo.eventType=="submenu">selected</#if>>子菜单</option>
						<option value="link" <#if (dataVo.eventType)?? && dataVo.eventType=="link">selected</#if>>跳转链接</option>
					<#-- 	<option value="miniapp" <#if (dataVo.eventType)?? && dataVo.eventType=="miniapp">selected</#if>>跳转小程序</option>  -->
						<option value="txtmsg" <#if (dataVo.eventType)?? && dataVo.eventType=="txtmsg">selected</#if>>发送文本</option>
						<option value="picmsg" <#if (dataVo.eventType)?? && dataVo.eventType=="picmsg">selected</#if>>发送图片</option>
						<option value="picurlmsg" <#if (dataVo.eventType)?? && dataVo.eventType=="picurlmsg">selected</#if>>发送图文</option>			
					</select>
					<b>* 必填</b>
				</div>
				<div class="row mt30 needWxoauthWrap dn">
					<label>是否需要微信授权：</label>
					<select name="needWxoauth">
						<option value="y" <#if (dataVo.needWxoauth)?? && dataVo.needWxoauth=="y">selected</#if>>是</option>
						<option value="n" <#if (dataVo.needWxoauth)?? && dataVo.needWxoauth=="n">selected</#if>>否</option>
					</select>
				</div>
				<div class="row mt30 needWxoauthScopeWrap dn">
					<label>选择获取授权方式：</label>
					<select name="oauth2Scope">
						<#--  <option value="snsapi_base" <#if (dataVo.oauth2Scope)?? && dataVo.oauth2Scope=="snsapi_base">selected</#if>>静默获取</option> 
						<option value="snsapi_userinfo" <#if (dataVo.oauth2Scope)?? && dataVo.oauth2Scope=="snsapi_userinfo">selected</#if>>显示获取</option>
						 -->
						 <option value="snsapi_userinfo" selected>显示获取</option>
					</select>
				</div>
				<div class="row mt30">
					<label>菜单事件内容：</label>
					<input type="text" class="base-input" name="eventContent" value="${(dataVo.eventContent)?if_exists}">
				</div>
				
				<div class="action pb30">
					<label>&nbsp;</label>
					<#if (dataVo.id)??>
						<a href="javascript:;" class="btn confirm J_Save" data-id="${(dataVo.id)?if_exists}">更新</a>
					<#else>
						<a href="javascript:;" class="btn btn-lg btn-success J_Save">新增菜单</a>
					</#if>
				</div>
			</div>
		</div>
		
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/platform/wechat/menu.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />	
