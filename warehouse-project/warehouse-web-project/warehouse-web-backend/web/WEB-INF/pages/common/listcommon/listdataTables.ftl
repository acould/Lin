<!-- basic scripts -->
<!--[if !IE]> -->
<script src="${model.static_acedomain}/assets/js/jquery-2.1.4.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
	<script src="${model.static_acedomain}/assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->

<#-- ace .js .. -->
<script type="text/javascript">
	if('ontouchstart' in document.documentElement) document.write("<script src='${model.static_acedomain}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<script src="${model.static_acedomain}/assets/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->
<script src="${model.static_acedomain}/assets/js/jquery.dataTables.min.js"></script>
<script src="${model.static_acedomain}/assets/js/jquery.dataTables.bootstrap.min.js"></script>

<script src="${model.static_acedomain}/assets/js/dataTables.buttons.min.js"></script>
<script src="${model.static_acedomain}/assets/js/buttons.flash.min.js"></script>
<script src="${model.static_acedomain}/assets/js/buttons.html5.min.js"></script>
<script src="${model.static_acedomain}/assets/js/buttons.print.min.js"></script>
<script src="${model.static_acedomain}/assets/js/buttons.colVis.min.js"></script>
<script src="${model.static_acedomain}/assets/js/dataTables.select.min.js"></script>

<!-- ace scripts -->
<script src="${model.static_acedomain}/assets/js/ace-elements.min.js"></script>
<script src="${model.static_acedomain}/assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->
<script src="${model.static_domain}/js/acejs/jqgrid/jqgrid_inline.min.js?v=${model.static_version}"></script>