<#assign webMenu="index" in model />
<#assign webTitle="多智啦仓库管理系统~" in model/>
<@model.webhead />
<#include "/common/menu_top.ftl">


<@model.mainContainerTop />
<#-- 菜单，内容，version底部 -->
<#include "/common/menu_left.ftl">
<#assign pageContentRows in model>
		<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<div class="alert alert-block alert-success">
						<button type="button" class="close" data-dismiss="alert">
							<i class="ace-icon fa fa-times"></i>
						</button>

						<i class="ace-icon fa fa-check green"></i>

						欢迎使用
						<strong class="green">
							多智啦仓库管理系统
							<small>(v1.0)</small>
						</strong>.
		
					</div>

					<#-- 其他图文，引导。。 -->

					
				

					<!-- PAGE CONTENT ENDS -->
				</div><!-- /.col -->
			</div><!-- /.row -->
</#assign>
<#include "/common/man_content.ftl">
<#include "/common/footer.ftl">

<@model.mainContainerBottom />

<#assign webEnd in model>
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='${model.static_acedomain}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<!-- page specific plugin scripts -->
	
	<!--[if lte IE 8]>
	  <script src="${model.static_acedomain}/assets/js/excanvas.min.js"></script>
	<![endif]-->
	<script src="${model.static_acedomain}/assets/js/jquery-ui.custom.min.js"></script>
	<script src="${model.static_acedomain}/assets/js/jquery.ui.touch-punch.min.js"></script>
	<script src="${model.static_acedomain}/assets/js/jquery.easypiechart.min.js"></script>
	<script src="${model.static_acedomain}/assets/js/jquery.sparkline.index.min.js"></script>
	<script src="${model.static_acedomain}/assets/js/jquery.flot.min.js"></script>
	<script src="${model.static_acedomain}/assets/js/jquery.flot.pie.min.js"></script>
	<script src="${model.static_acedomain}/assets/js/jquery.flot.resize.min.js"></script>
	
</#assign>


<@model.webend />