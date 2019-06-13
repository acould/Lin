<li class="light-blue dropdown-modal user-min">
	<a data-toggle="dropdown" href="#" class="dropdown-toggle">
		<img class="nav-user-photo" src="${model.static_acedomain}/assets/images/avatars/user.jpg" alt="Jason's Photo" />
		<span class="user-info J_UserName" data-uid="${sysUser.id!!}">
			<small >欢迎您,</small>
			${sysUser.realname!!}
		</span>

		<i class="ace-icon fa fa-caret-down"></i>
	</a>

	<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
		<li>
			<a href="#">
				<i class="ace-icon fa fa-cog"></i>
				Settings
			</a>
		</li>

		<li>
			<a href="#">
				<i class="ace-icon fa fa-user"></i>
				Profile
			</a>
		</li>

		<li class="divider"></li>

		<li>
			<a href="/login.html?cmd=logout">
				<i class="ace-icon fa fa-power-off"></i>
				退出
			</a>
		</li>
	</ul>
</li>