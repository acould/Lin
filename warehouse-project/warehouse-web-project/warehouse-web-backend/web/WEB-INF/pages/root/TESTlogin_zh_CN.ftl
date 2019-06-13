<#assign webTitle="多智啦仓库管理系统" in model>
<#assign webHead in model>
<link rel="stylesheet" href="${model.static_domain}/css/admin/login.css?v=${model.static_version}">
</#assign>
<@model.webhead />
<div id="wrapper">
	<div class="content">
		<div class="login">
		<div class="logo"></div>
			<div class="box bigfs">
				<div id="loginForm">
					<div class="message">${emsg?if_exists}</div>
					<input type="hidden" name="url" value="${url?if_exists}">
					<div class="row">
						<div class="accounts">
							<input name="username" type="text" placeholder="用户名" value="${name?if_exists}" autocomplete="off">
						</div>
					</div>
					<div class="row">
						<div class="pass">
							<input name="password" type="password" placeholder="密码">
						</div>
					</div>
					<div class="row pr">
						<div class="code">
							<input name="code" type="text" placeholder="验证码" autocomplete="off">
						</div>
						<div class="vcode">
							<img src="/vcode" atl="验证码" title="验证码更新" onclick="this.src='/vcode?rand='+(new Date().getTime().toString(36));"/>
						</div>
					</div>
					<div class="row btn">
						<input type="submit" value="立即登录" class="submit bigfs">
					</div>
				</div>
				<p>Copyright © aiyoumi.com All Rights Reserved</p>
			</div>
		</div>
	</div>
</div>
<#assign webEnd in model >
<script src="${model.static_domain}/js/admin/root/encrypt.min.js?v=${model.static_version}"></script>
<script src="${model.static_domain}/js/admin/root/login.min.js?v=${model.static_version}"></script>
</#assign>
<@model.webend />
