
<div id="navbar" class="navbar navbar-default">
	<script type="text/javascript">
		try {
			ace.settings.check('navbar', 'fixed');
		} catch (e) {
		}
	</script>

	<div class="navbar-container" id="navbar-container">
		<!-- #section:basics/sidebar.mobile.toggle -->
		<button type="button" class="navbar-toggle menu-toggler pull-left"
			id="menu-toggler" data-target="#sidebar">
			<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>

			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>

		<!-- /section:basics/sidebar.mobile.toggle -->
		<div class="navbar-header pull-left">
			<!-- #section:basics/navbar.layout.brand -->
			<a class="navbar-brand"> <%-- <small> <i class="fa fa-leaf"></i> ${pd.SYSNAME} </small> --%>
				<small><img src="<%=basePath%>newStyle/images/logo2.png"
					width="86px"></img><span
					style="position: relative; top: 3px; left: 10px;">${pd.SYSNAME}</span></small>
			</a>
		</div>
		<!-- #section:basics/navbar.dropdown -->
		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
				<!-- 	<li class="grey">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-tasks"></i>
								<span class="badge badge-grey">2</span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-check"></i>
									预留功能,待开发
								</li>
								<li class="dropdown-footer">
									<a href="javascript:">
										预留功能,待开发
										<i class="ace-icon fa fa-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

					<li title="即时聊天" class="purple"  onclick="creatw();">creatw()在 WebRoot\plugins\websocketInstantMsg\websocket.js中
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-bell icon-animated-bell"></i>
								<span class="badge badge-important"></span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-bell-o"></i>
									FH Aadmin 即时通讯
								</li>
							</ul>
						</li>

						<li title="站内信" class="green" onclick="fhsms();" id="fhsmstss">fhsms()在 WebRoot\static\js\myjs\head.js中
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
								<span class="badge badge-success" id="fhsmsCount"></span>
							</a>
						</li> -->

				<!-- #section:basics/navbar.user_menu -->
				<li class="light-blue" id="dropdown" style="margin-left: 15px;">
					<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0)" style="background-color: #333f4a"> 
						<img class="nav-user-photo" src="static/ace/avatars/user.jpg" alt="Jason's Photo" /> 
						<span class="user-info" id="user_info" style="font-size:12px;top:8px;"> </span>
						<i class="ace-icon fa fa-caret-down"></i>
					</a>
					<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<c:if test="${role == 1}">
							<li><a onclick="editUserH();" style="cursor: pointer;"><i
									class="ace-icon fa fa-cog"></i>修改资料</a>
							</li>
							<!-- editUserH()在 WebRoot\static\js\myjs\head.js中 -->
							<!-- <li id="systemset">
										<a onclick="editSys();" style="cursor:pointer;"><i class="ace-icon fa fa-user"></i>系统设置</a>editSys()在 WebRoot\static\js\myjs\head.js中
									</li> -->
							<li class="divider"></li>
						</c:if>
						<li>
							<a href="logout"><i class="ace-icon fa fa-power-off"></i>退出登录</a>
						</li>
					</ul>
				</li>

				<!-- /section:basics/navbar.user_menu -->
			</ul>
		</div>

		<div class="navbar-header pull-right">
			<i class="layui-icon" style="color:#fff;position: relative;left: 18px;top: 1px;">&#xe702;</i>	
			<a style="line-height: 48px; color: #fff; padding: 0 20px; cursor: pointer;"
				href="<%=basePath%>newerGuide/list.do" target="_blank">
				新手引导
			</a>
		</div>
		<c:if test="${msg_role == 'internet'}">
			<div class="navbar-header pull-right">
				<i class="layui-icon" style="color:#fff;position: relative;left: 18px;top: 1px;">&#xe667;</i>
				<a style="line-height: 48px; color: #fff; padding: 0 20px; cursor: pointer;" id="aId"  onclick="siMenu('z${pdMessage.MENU_ID}','lm${pdMessage.PARENT_ID}','${pdMessage.MENU_NAME}','${pdMessage.MENU_URL}')" >
					消息通知
				</a>
			</div>
		</c:if>
		<!-- /section:basics/navbar.dropdown -->
	</div>
	<!-- /.navbar-container -->
</div>
<div id="fhsmsobj">
	<!-- 站内信声音消息提示 -->
</div>

