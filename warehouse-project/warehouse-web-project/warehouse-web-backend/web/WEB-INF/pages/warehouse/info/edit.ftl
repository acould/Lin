<#assign webNav="Y" in model>
<#assign webTitle="多智啦仓库管理系统-新增仓库" in model>
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
<li><a href="/warehouse/info/list.html">仓库管理</a></li>
<li class="active"><#if (dataVo.id)??>修改仓库<#else>新增仓库</#if></li>
</#assign>
<@model.mainPageContentTop />

		<div id="wrap" class="clearfix pt30 pl30">
			<div class="clearfix">
			<div class="row mt30">
					<label>基本信息</label>	
				</div>
				<div class="row mt30 ">
					<label>仓库名称：</label>
					<input type="text" class="base-input" name="name" value="${(dataVo.name)?if_exists}">
					<b>* 必填</b>
					
					<label>仓库货品性质：</label>
					<select name="warehouseType">
						<option value="">选择仓库货品性质</option>
						<option value="product_ok" <#if (dataVo.warehouseType)?? && dataVo.warehouseType=="product_ok">selected</#if>>成品</option>
						<option value="product_bug" <#if (dataVo.warehouseType)?? && dataVo.warehouseType=="product_bug">selected</#if>>残次品仓</option>
						<option value="material" <#if (dataVo.warehouseType)?? && dataVo.warehouseType=="material">selected</#if>>材料</option>
						<option value="other" <#if (dataVo.warehouseType)?? && dataVo.warehouseType=="other">selected</#if>>其他</option>
					</select>
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>仓库编号：</label>
					<input type="text" class="base-input" name="code" value="${(dataVo.code)?if_exists}">
					<b>* 必填</b>
					
					<label>仓库类别：</label>
					<#if (warehouseClassDTOList?exists && warehouseClassDTOList?size>0) >
						<select name="warehouseClassId">
							<#list warehouseClassDTOList as r>
								<option value="${r.id!}" <#if (dataVo?exists && dataVo.warehouseClassId?? && (r.id == dataVo.warehouseClassId) )>selected</#if>>${r.name!}</option>
							</#list>
						</select>
					<#else>
						
					</#if>
					<b>* 必填</b>
				</div>
				
				<div class="row mt30">
					<label>排序：</label>
					<input type="text" class="base-input" name="orderby" value="${(dataVo.orderby)?if_exists}">
					<label>停用：</label>
					<input name="status" type="checkbox" class="base-input"　value="${(dataVo.status)?if_exists}">
					&nbsp;&nbsp;
					<label>是否委外：</label>
					<input name="isOutsource" type="checkbox" class="base-input"　value="${(dataVo.isOutsource)?if_exists}">
				</div>
				
				<div class="row mt30">
					<label>委外名称：</label>
					<input type="text" class="base-input" name="outsourceName" value="${(dataVo.outsourceName)?if_exists}">
					<label>委外接口：</label>
					<input type="text" class="base-input" name="outsourceUrl" value="${(dataVo.outsourceUrl)?if_exists}">
				</div>
				<div class="row mt30">
					<label>备注说明：</label>
					<textarea name="remark" rows="3" cols="53" class="base-input" >${(dataVo.remark)?if_exists}</textarea>
				</div>
				
				
				
				<hr>
				
				<div class="row mt30">
					<label>地址信息</label>	
				</div>
				
				<div class="row mt30">
					<label>国家：</label>
					<input type="text" class="base-input" name="country" value="${(dataVo.country)?if_exists}">
					<label>州省：</label>
					<input type="text" class="base-input" name="province" value="${(dataVo.province)?if_exists}">
				</div>
				
				
				<div class="row mt30">
					<label>区市：</label>
					<input type="text" class="base-input" name="city" value="${(dataVo.city)?if_exists}">
					<label>区县：</label>
					<input type="text" class="base-input" name="county" value="${(dataVo.county)?if_exists}">
				</div>
				<div class="row mt30">
					<label>街道：</label>
					<input type="text" class="base-input" name="street" value="${(dataVo.street)?if_exists}">
					<label>详细地址：</label>
					<input type="text" class="base-input" name="address" value="${(dataVo.address)?if_exists}">
				</div>
				<div class="row mt30">
					<label>邮编：</label>
					<input type="text" class="base-input" name="postCode" value="${(dataVo.postCode)?if_exists}">
					<label>联系电话：</label>
					<input type="text" class="base-input" name="telphone" value="${(dataVo.telphone)?if_exists}">
				</div>
				<div class="row mt30">
					<label>联系人：</label>
					<input type="text" class="base-input" name="manager" value="${(dataVo.manager)?if_exists}">
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
<script src="${model.static_domain}/js/admin/warehouse/info/edit.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />	
