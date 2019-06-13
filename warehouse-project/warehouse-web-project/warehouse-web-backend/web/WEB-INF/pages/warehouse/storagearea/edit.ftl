<#assign webNav="Y" in model>
<#assign webTitle="多智啦仓库管理系统-新增仓库存储区" in model>
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
<li><a href="/warehouse/storagearea/list.html">仓库存储区管理</a></li>
<li class="active"><#if (dataVo.id)??>修改仓库存储区<#else>新增仓库存储区</#if></li>
</#assign>
<@model.mainPageContentTop />

		<div id="wrap" class="clearfix pt30 pl30">
			<div class="clearfix">
				<div class="row mt30">
					<label>货币名称：</label>
					<input type="text" class="base-input" name="currencyName" value="${(dataVo.currencyName)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>货币编号：</label>
					<input type="text" class="base-input" name="code" value="${(dataVo.code)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>基准货币：</label>
					<input type="text" class="base-input" name="baseCurrencyCode" value="${(dataVo.baseCurrencyCode)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>仓库存储区：</label>
					<input type="text" class="base-input" name="exchangeRate" value="${(dataVo.exchangeRate)?if_exists}">
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>排序：</label>
					<input type="text" class="base-input" name="orderby" value="${(dataVo.orderby)?if_exists}">
				</div>
				
				<div class="action pb30">
					<label>&nbsp;</label>
					<#if (dataVo.id)??>
						<a href="javascript:;" class="btn confirm btn-lg btn-success J_Save" data-id="${(dataVo.id)?if_exists}">更 新</a>
					<#else>
						<a href="javascript:;" class="btn confirm btn-lg btn-success J_Save">新 增</a>
					</#if>
				</div>
			</div>
		</div>
		
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/warehouse/storagearea/edit.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />	
