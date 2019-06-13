<#assign webTitle="多智啦仓库管理系统" in model>
<#assign bodyClass="login-layout blur-login" in model/>
<@model.webheadLogin />


<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
						<div class="space-16"></div>
						<div class="space-16"></div>
						<div class="space-16"></div>
						<div class="space-16"></div>
						
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>
									<span class="red">多智啦仓库管理系统</span>
									<span class="white" id="id-text2">Application</span>
								</h1>
								<#-- 
								<h4 class="blue" id="id-company-text">&copy; Company Name</h4>
								 -->
							</div>

							<div class="space-6"></div>
							<div class="message">${emsg?if_exists}</div>
							
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												请输入你的账号信息
											</h4>

											<div class="space-6"></div>

											<form id="loginForm">
												<input type="hidden" name="url" value="${url?if_exists}">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名"  name="username" value="${name?if_exists}" autocomplete="off" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input name="password" type="password" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>
													
													<label class="block input-icon input-icon-right">
														<span class="block input-icon input-icon-right">
															<input name="code" type="text" placeholder="验证码" class="form-control" >
															<img src="/vcode" atl="验证码" title="验证码更新" onclick="this.src='/vcode?rand='+(new Date().getTime().toString(36));"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl">记住密码</span>
														</label>
														<button type="button" class="submit width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">立即登录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

											
										</div><!-- /.widget-main -->
										<#-- 
										<div class="toolbar clearfix">
											<div>
												<a href="#" data-target="#forgot-box" class="forgot-password-link">
													<i class="ace-icon fa fa-arrow-left"></i>
													忘记密码？
												</a>
											</div>

											<div>
												<a href="#" data-target="#signup-box" class="user-signup-link">
													我要注册
													<i class="ace-icon fa fa-arrow-right"></i>
												</a>
											</div>
										</div>
										 -->
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->
								
								
								<#-- 忘记密码 -->
								<#-- <#include "/root/forget_password.ftl"> -->
								<#-- 注册账号 -->
								<#-- <#include "/root/regAccount.ftl"> -->
								
							</div><!-- /.position-relative -->

							<#-- 
								<div class="navbar-fixed-top align-right">
									<br />
									&nbsp;
									<a id="btn-login-dark" href="#">Dark</a>
									&nbsp;
									<span class="blue">/</span>
									&nbsp;
									<a id="btn-login-blur" href="#">Blur</a>
									&nbsp;
									<span class="blue">/</span>
									&nbsp;
									<a id="btn-login-light" href="#">Light</a>
									&nbsp; &nbsp; &nbsp;
								</div>
						 -->
							<#-- 版本信息 -->
							<div class="space-6"></div>
							<div class="space-6"></div>
							
							<div class="center">
								
								<h4 class="blue" id="id-company-text">&copy; 杭州智为物联科技有限公司</h4>
							</div>
							
							
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

<#assign webEnd in model >
		<!-- basic scripts -->
	
		<!--[if IE]>
		<script src="${model.static_acedomain}/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${model.static_acedomain}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="${model.static_domain}/js/admin/root/encrypt.min.js?v=${model.static_version}"></script>
		<script src="${model.static_domain}/js/admin/root/login.min.js?v=${model.static_version}"></script>
		<script src="${model.static_domain}/js/admin/login/login.min.js?v=${model.static_version}"></script>
</#assign>
<@model.webend />
