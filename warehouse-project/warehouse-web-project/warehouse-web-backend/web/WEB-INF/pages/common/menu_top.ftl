<div id="navbar" class="navbar navbar-default          ace-save-state">
	<div class="navbar-container ace-save-state" id="navbar-container">
		<div class="navbar-header pull-left">
			<a href="/index.html" class="navbar-brand">
				<small>
					<i class="fa fa-leaf"></i>
					多智啦仓库管理系统
				</small>
			</a>

			<button class="pull-right navbar-toggle navbar-toggle-img collapsed" type="button" data-toggle="collapse" data-target=".navbar-buttons,.navbar-menu">
				<span class="sr-only">Toggle user menu</span>

				<img src="${model.static_acedomain}/assets/images/avatars/user.jpg" alt="Jason's Photo" />
			</button>

			<button class="pull-right navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span>

				<span class="icon-bar"></span>

				<span class="icon-bar"></span>

				<span class="icon-bar"></span>
			</button>
		</div>

		<div class="navbar-buttons navbar-header pull-right  collapse navbar-collapse" role="navigation" id="role_navigation_id" >
			<ul class="nav ace-nav">
				<#-- li消息铃铛 内容 -->
				<#include "/common/menu/menu_top_bell.ftl"> 
				<#-- li user info login -->
				<#include "/common/menu/menu_top_user.ftl">
			</ul>
		</div>
		<#-- nav menunav 菜单项。。 -->
		<#include "/common/menu/menu_top_menunav.ftl">
	</div><!-- /.navbar-container -->
</div>
