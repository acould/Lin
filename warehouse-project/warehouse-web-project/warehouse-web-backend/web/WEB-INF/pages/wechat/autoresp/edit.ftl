<#assign webNav="Y" in model>
<#assign webTitle="仓库管理系统-新增消息自动回复配置" in model>
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
<li><a href="/wechat/autoresp/list.html">自动回复消息设置</a></li>
<li class="active"><#if (dataVo.id)??>修改消息设置<#else>新增消息设置</#if></li>
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
				<div class="row mt30 parentMenuWrap ">
					<label> 自动回复消息类型：</label>
					<select name="msgType">
						<option value="">选择自动回复消息类型</option>
						<option value="subscribe" <#if (dataVo.msgType)?? && dataVo.msgType=="subscribe">selected</#if>>被关注</option>
						<option value="keyValue" <#if (dataVo.msgType)?? && dataVo.msgType=="keyValue">selected</#if>>关键词</option>
						<option value="receiveMsg" <#if (dataVo.msgType)?? && dataVo.msgType=="receiveMsg">selected</#if>>收到消息</option>
						<option value="preService" <#if (dataVo.msgType)?? && dataVo.msgType=="preService">selected</#if>>转人工前消息</option>
					</select>
					<b>* 必填</b>
				</div>
				<div class="row mt30 ">
					<label> 是否绑定用户：</label>
					<select name="isBindUser">
						<option value="">选择是否绑定用户</option>
						<option value=0 <#if (dataVo.isBindUser)?? && dataVo.isBindUser==0>selected</#if>>否</option>
						<option value=1 <#if (dataVo.isBindUser)?? && dataVo.isBindUser==1>selected</#if>>是</option>
					</select>
					<b>* 必填</b>
				</div>
				<div class="row mt30 ">
					<label>关键词匹配类型：</label>
					<select name="keyType">
						<option value="">选择关键词匹配类型</option>
						<option value="equal" <#if (dataVo.keyType)?? && dataVo.keyType=="equal">selected</#if>>全匹配</option>
						<option value="like" <#if (dataVo.keyType)?? && dataVo.keyType=="like">selected</#if>>半匹配</option>
					</select>
					<b>* 必填</b>
				</div>
				<div class="row mt30">
					<label>关键词内容：</label>
					<input type="text" class="base-input" name="contentKey" value="${(dataVo.contentKey)?if_exists}">
					<b>* 必填（英文符逗号隔开）</b>
				</div>
				<div class="row mt30">
					<label>消息设置事件类型：</label>
					<select name="eventType">
						<option value="">选择类型</option>
						<option value="link" <#if (dataVo.eventType)?? && dataVo.eventType=="link">selected</#if>>跳转链接</option>
						<option value="miniapp" <#if (dataVo.eventType)?? && dataVo.eventType=="miniapp">selected</#if>>跳转小程序</option>
						<option value="txtmsg" <#if (dataVo.eventType)?? && dataVo.eventType=="txtmsg">selected</#if>>发送文本</option>
						<option value="picmsg" <#if (dataVo.eventType)?? && dataVo.eventType=="picmsg">selected</#if>>发送图片</option>
						<option value="picurlmsg" <#if (dataVo.eventType)?? && dataVo.eventType=="picurlmsg">selected</#if>>发送图文</option>			
					</select>
					<b>* 必填</b>
				</div>
				<div class="row mt30 msgExceed_msgExceedTimes dn">
					<label>消息发送时间阀值(分钟)</label>
					<input type="text" class="base-input" name="msgExceedTimes" value="${(dataVo.msgExceedTimes)?if_exists}">
				</div>
				<div class="row mt30 msgExceed_msgExceedNum dn">
					<label>消息发送次数阀值(次)</label>
					<input type="text" class="base-input" name="msgExceedNum" value="${(dataVo.msgExceedNum)?if_exists}">
				</div>
				<div class="row mt30 msgExceed_msgExceedContent dn">
					<label>消息发送超过阀值内容</label>
					<input type="text" class="base-input" name="msgExceedContent" value="${(dataVo.msgExceedContent)?if_exists}">
				</div>
				<div class="row mt30">
					<label>消息设置事件内容：</label>
					<input type="text" class="base-input" name="eventContent" value="${(dataVo.eventContent)?if_exists}">
				</div>
				
				<div class="action pb30">
					<label>&nbsp;</label>
					<#if (dataVo.id)??>
						<a href="javascript:;" class="btn confirm J_Save" data-id="${(dataVo.id)?if_exists}">更新</a>
					<#else>
						<a href="javascript:;" class="btn btn-lg btn-success J_Save">新增消息设置</a>
					</#if>
				</div>
			</div>
		</div>
		
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/platform/wechat/autoresp.min.js?v=${model.static_version}"></script>
</#assign>

<@model.webend />	
