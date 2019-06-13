<#assign webNav="Y" in model>
<#assign webTitle="平台管理-菜单管理" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<style>
.menu {width: 100%; color: #666;}
.menu .block {float:left; margin:8px; width:31.5%; border:1px solid #ddd;}
.menu .head {padding:15px; border-bottom:1px solid #ddd; font-size: 15px; font-weight: bold; text-align: center;}
.menu .btn {line-height: 24px; width: 50px;}
.menu .editor {background-color: #f80;border: 1px solid #e37900;font-size:14px;}
.menu .list {padding:5px; height: 460px; overflow: auto;}
.menu .list li {position:relative; padding:0 4px; height:38px; line-height:38px;}
.menu .list li.current {background-color:#FFEED5;}
.menu .list li .status {position:absolute; left:52%; top:0;}
.menu .list li .name {float:left; width:150px; text-align:center; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;}
.menu .list li .ctrlPanel {position:absolute; right:70px; line-height:38px; height:38px;}
.menu .list li .sortPanel {float:right;}
.menu .list li .sortPanel .arrow_con {width:24px; height:38px; line-height:38px;}
.g-dialog label{font-size: 14px;}
.g-dialog .bd input.base-input {height: auto;}
.g-dialog .bd .btn {line-height: 30px;}
.hide {display: none;}
</style>
<script src="${model.static_domain}/js/lits/jquery.tools.min.js"></script>
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<@model.mainContainerTop />
<#-- 菜单，内容，version底部 -->
<#include "/common/menu_left.ftl">
<#-- 内容导航 -->
<#assign mainPageContentTopNav in model>
<li><a href="/platform/menu/list.html">菜单管理</a></li>
<li class="active">列表信息</li>
</#assign>
<@model.mainPageContentTop />

<!-- div.dataTables_borderWrap -->
	<div id="wrap" class="clearfix pt30 pl30">
		<div id="breadcrumb" class="title">${(pageName)?if_exists}</div>
		<div class="clearfix mt30">
	        <!-- 菜单模块 -->
	        <ul class="menu clearfix">
	            <li data-level="1" data-pid="-1" class="block level1">
	              <div class="head">一级菜单<a class="btn confirm fr btn-add">新增</a></div>
	              <ul class="list"></ul>
	            </li>
	            <li data-level="2" data-pid="" class="block level2">
	              <div class="head">二级菜单<a class="btn confirm fr btn-add disabled">新增</a></div>
	              <ul class="list"></ul>
	            </li>
	            <li data-level="3" data-pid="" class="block level3">
	              <div class="head">三级菜单<a class="btn confirm fr btn-add disabled">新增</a></div>
	              <ul class="list"></ul>
	            </li>
	        </ul>
	        <!-- /菜单模块 -->
 		 </div>
	</div>	
						
<#include "/common/page/mainPageContentEnd.ftl">
<#include "/common/footer.ftl">
<@model.mainContainerBottom />

<#assign webEnd in model>
<script src="${model.static_domain}/js/admin/platform/menu/menuList.min.js?v=${model.static_version}"></script>
</#assign>
<@model.webend />
