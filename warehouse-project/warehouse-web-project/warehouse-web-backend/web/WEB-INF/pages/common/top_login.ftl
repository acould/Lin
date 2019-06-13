<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>${webTitle?default("多智啦仓库管理系统")}</title>

		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<link rel="shortcut icon" href="${model.static_domain}/images/admin/ui/favicon.ico" type="image/x-icon">
		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${model.static_acedomain}/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${model.static_acedomain}/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->

		<!-- text fonts -->
		<link rel="stylesheet" href="${model.static_acedomain}/assets/css/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${model.static_acedomain}/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${model.static_acedomain}/assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
		<link rel="stylesheet" href="${model.static_acedomain}/assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="${model.static_acedomain}/assets/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${model.static_acedomain}/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->
		<script src="${model.static_acedomain}/assets/js/ace-extra.min.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="${model.static_acedomain}/assets/js/html5shiv.min.js"></script>
		<script src="${model.static_acedomain}/assets/js/respond.min.js"></script>
		<![endif]-->
		
		
		
		<script src="${model.static_domain}/js/lits/jquery.1.7.2.min.js"></script>
		<script src="${model.static_domain}/js/global.min.js?v=${model.static_version}"></script>
		<script src="${model.static_domain}/js/plugins/cookie/jquery.cookie.js?v=${model.static_version}"></script>
	    ${webHead?if_exists}
	</head>

	<body class="${bodyClass?default("no-skin")}">