<#assign webNav="Y" in model>
<#assign webTitle="仓库管理系统-平台管理-资源管理-新增资源" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<style>
#result { padding: 1em; }
.fail { background-color: #fee; color: #933; border: 2px solid #933; }
.pass { background-color: #efe; color: #393; border: 2px solid #393; }
</style>
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<@model.mainContainerTop />
<#-- 菜单，内容，version底部 -->
<#include "/common/menu_left.ftl">
<#-- 内容导航 -->
<#assign mainPageContentTopNav in model>
<li><a href="/platform/mresourcetext/list.html">字典资源管理</a></li>
<li class="active"><#if (objectBean.key)??>修改字典资源<#else>新增字典资源</#if></li>
</#assign>
<@model.mainPageContentTop />
<#--  内容 -->
<div class="row clearfix pt30 pl30" id="wrap">
	<div class="col-xs-12 clearfix">
		<#-- PAGE CONTENT BEGINS -->	
			<form class="form-horizontal" role="form">		
				<div id="breadcrumb" class="title">${(pageName)?if_exists}</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">资源类型：</label>
						<input type="text" name="type" class="base-input col-xs-10 col-sm-2" value="${(objectBean.type)?if_exists}">
						<b>* 必填</b>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">资源标识：</label>
						<input type="text" name="key" class="base-input col-xs-10 col-sm-2" value="${(objectBean.key)?if_exists}">
						<b>* 必填</b>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">资源值：<input type="checkbox" name="jsonlint">&nbsp;&nbsp;校验JSON数据<br/><sapn style="color:#c00">勾选表示校验</span></label>
						<textarea style="width:80%;height:200px;"  type="text" class="base-input  col-xs-10 col-sm-2" name="value">${(objectBean.value)?if_exists}</textarea>
						<b>* 必填</b>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">自定义字段1：</label>
						<input type="text" name="customField1" class="base-input col-xs-10 col-sm-2" value="${(objectBean.customField1)?if_exists}">
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">自定义字段2：</label>
						<input type="text" name="customField2" class="base-input col-xs-10 col-sm-2" value="${(objectBean.customField2)?if_exists}">
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">自定义字段3：</label>
						<input type="text" name="customField3" class="base-input col-xs-10 col-sm-2" value="${(objectBean.customField3)?if_exists}">
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">自定义字段4：</label>
						<input type="text" name="customField4" class="base-input col-xs-10 col-sm-2" value="${(objectBean.customField4)?if_exists}">
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">排序：</label>
						<input type="text" name="ordering" class="base-input col-xs-10 col-sm-2" value="${(objectBean.ordering)?if_exists}">
					</div>
					
					
					<#if (objectBean.key)??>
				<div class="row mt30">
					<label>状态：</label>
					<#if (objectBean.key)??>
					<label class="radio">
						<input type="radio" name="status" value="y" <#if (objectBean.status)??&&objectBean.status=="y">checked</#if>>
						<span>有效</span>
					</label>
					<label class="radio">
						<input type="radio" name="status" value="n" <#if (objectBean.status)??&&objectBean.status=="n">checked</#if>>
						<span>无效</span>
					</label>
					<#else>
					<label class="radio">
						<input type="radio" name="status" value="y" checked="">
						<span>有效</span>
					</label>
					<label class="radio">
						<input type="radio" name="status" value="n">
						<span>无效</span>
					</label>
					</#if>
				</div>
				<div class="row mt30">
					<label>创建时间：</label>
					<input type="text" class="base-input" name="createTime" readonly="readonly" value="<#if (objectBean.createTime)??>${(objectBean.createTime)?string('yyyy-MM-dd HH:mm:ss')}</#if>">
				</div>
				<div class="row mt30">
					<label>修改时间：</label>
					<input type="text" class="base-input" name="createTime" readonly="readonly" value="<#if (objectBean.modifyTime)??>${(objectBean.modifyTime)?string('yyyy-MM-dd HH:mm:ss')}</#if>">
				</div>
				</#if>
				<pre id="result"></pre>
				
				<div class="action pb30">
					<label>&nbsp;</label>
					<#if (objectBean.id)??>
					<a href="javascript:;" class="btn confirm J_ResourceUpdate" data-type="${(objectBean.type)?if_exists}" data-key="${(objectBean.key)?if_exists}">修改资源</a>
					<#else>
					<a href="javascript:;" class="btn confirm btn-lg btn-success J_ResourceAdd">新增资源</a>
					</#if>
				</div>
			
			</form>
	</div>
</div>
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/plugins/jsonlint/json2.js"></script>
<script src="${model.static_domain}/js/plugins/jsonlint/jsonlint.js"></script>
<script src="${model.static_domain}/js/admin/platform/mresource/list.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />
