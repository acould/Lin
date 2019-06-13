<#assign webTitle="仓库管理系统-缓存清除" in model>
<#assign webNav="Y" in model>
<#assign webMenu="sys" in model>
<#assign webHead in model>
<script type="text/javascript" src="${model.static_domain}/js/lits/jquery.tools.min.js"></script>
<style type="text/css">
.page {font-size: 14px;}
.confirm { width: 60px; line-height: 26px;}
</style>
</#assign>
<@model.webhead />
<#include "/common/menu_top.ftl">
<div id="wrapper">
	<div class="content clearfix bigfs">
		<#include "/common/menu_left.ftl">
        <div id="wrap" class="clearfix pt30 pl30">
            <div id="breadcrumb" class="title">${(pageName)?if_exists}</div>
    		<div class="row page clearfix">
    			<br><br>
    			1.) 用户授信功能，清除mongodb异常缓存： <input type="button" data-index="1" value="清除" class="btn confirm J_clear"><br><br>
    			2.) 清除全局强制升级缓存： <input type="button" data-index="2" value="清除" class="btn confirm J_clear"><br><br>
    			3.) 根据type，key清除缓存：
    			type <input type="text" class="base-input type">
    			key <input type="text" class="base-input key">
    			<input type="button" data-index="3" value="清除" class="btn confirm J_clear">
    		</div>
        </div>
	</div>
</div>	
<#assign webEnd in model>
<script>
$('.J_clear').on('click', function() {
    var index = $(this).data('index'),
        url = '',
        data = {};

    switch (index) {
        case 1: 
            url = '/sys/cache/clearmongodb';
            break;
        case 2: 
            url = '/sys/cache/clearforceupgrade';
            break;
        case 3: 
            url = '/sys/cache/clearcache';
            data = {
                type: $('.type').val(),
                key: $('.key').val()
            };
            break;
        default:
    }

    $.ajax({
        url: url,
        type: 'post',
        data: data,
        dataType: 'json',
        success: function(data) {
            var data = data.ret || data;
            if (data.success) {
                alert(data.resultDes);

            } else {
                alert(data.resultDes);
            }
        }
    });
});
</script>
</#assign>
<@model.webend />