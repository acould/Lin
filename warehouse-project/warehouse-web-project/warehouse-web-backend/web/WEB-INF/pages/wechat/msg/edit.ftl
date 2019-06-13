<#assign webNav="Y" in model>
<#assign webTitle="仓库管理系统-新增修改" in model>
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
<li><a href="/wechat/msg/list.html">消息管理</a></li>
<li class="active"><#if (dataVo.id)??>修改消息<#else>新增消息</#if></li>
</#assign>
<@model.mainPageContentTop />

<div id="wrap" class="clearfix pt30 pl30">
			<div class="clearfix">
				<div class="row mt30">
					<label>企业名称：</label>
					<input type="text"   class="base-input" name="orgName" value="<#if orgvo?exists>${(orgvo.orgName)?if_exists}</#if>">
					<input type="hidden" class="base-input" name="companyId" value="${(orgvo.id)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>appId：</label>
					<input type="text" class="base-input" name="appId" value="${(dataVo.appId)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>appSecret：</label>
					<input type="text" class="base-input" name="appSecret" value="${(dataVo.appSecret)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>token：</label>
					<input type="text" class="base-input" name="token" value="${(dataVo.token)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>aeskey：</label>
					<input type="text" class="base-input" name="aeskey" value="${(dataVo.aeskey)?if_exists}">
					<b>* 必填</b>
				</div>
				
				<div class="row mt30">
					<label>微信号：</label>
					<input type="text" class="base-input" name="weixinNo" value="${(dataVo.weixinNo)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>微信号原始id：</label>
					<input type="text" class="base-input" name="originId" value="${(dataVo.originId)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>登录邮箱：</label>
					<input type="text" class="base-input" name="email" value="${(dataVo.email)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>公众号类型：</label>
					<select name="gzhType">
						<option value="">选择类型</option>
						<option value="gzh_fwh" <#if (dataVo.gzhType)?? && dataVo.gzhType=="gzh_fwh">selected</#if>>服务号</option>
						<option value="gzh_dyh" <#if (dataVo.gzhType)?? && dataVo.gzhType=="gzh_dyh">selected</#if>>订阅号</option>
						<option value="gzh_qyh" <#if (dataVo.gzhType)?? && dataVo.gzhType=="gzh_qyh">selected</#if>>企业号</option>
						<option value="gzh_xcx" <#if (dataVo.gzhType)?? && dataVo.gzhType=="gzh_xcx">selected</#if>>小程序</option>
					</select>
					<b>* 必填</b>
				</div>
				
				<div class="row mt30">
					<label>公众号名称：</label>
					<input type="text" class="base-input" name="gzhName" value="${(dataVo.gzhName)?if_exists}">
					<b>* 必填</b>
				</div>
						
				<div class="action pb30">
					<label>&nbsp;</label>
					<#if (dataVo.id)??>
						<a href="javascript:;" class="btn confirm J_Save" data-id="${(dataVo.id)?if_exists}">更新</a>
					<#else>
						<a href="javascript:;" class="btn btn-lg btn-success J_Save">新增配置</a>
					</#if>
				</div>
			</div>
		</div>
		
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/platform/wechat/msg.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />	

