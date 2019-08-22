$(top.hangge());//关闭加载状态

//手机号正则
var phoneReg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
//身份证号正则
var cardReg = /^([1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;


function chosen_select(){
	//下拉框
    if (!ace.vars['touch']) {
        $('.chosen-select').chosen({allow_single_deselect: true});
        $(window)
            .off('resize.chosen')
            .on('resize.chosen', function () {
                $('.chosen-select').each(function () {
                    var $this = $(this);
                    $this.next().css({'width': $this.parent().width()});
                });
            }).trigger('resize.chosen');
        $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
            if (event_name != 'sidebar_collapsed') return;
            $('.chosen-select').each(function () {
                var $this = $(this);
                $this.next().css({'width': $this.parent().width()});
            });
        });
        $('#chosen-multiple-style .btn').on('click', function (e) {
            var target = $(this).find('input[type=radio]');
            var which = parseInt(target.val());
            if (which == 2) $('#form-field-select-4').addClass('tag-input-style');
            else $('#form-field-select-4').removeClass('tag-input-style');
        });
    }
}
$(function () {
    //复选框全选控制
    var active_class = 'active';
    $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
    
});
	//日期框
	function date_picker(){
		$('.date-picker').datepicker({
	        autoclose: true,
	        todayHighlight: true
	    });
	}
	//城市三级联动
	function citySel(PRON,COUNTY,CITY){
		if(PRON != null && COUNTY != null && CITY != null){
			if(PRON==CITY){
				$("#city").citySelect({prov:PRON, city:COUNTY, dist:COUNTY});
			}
			else{
			$("#city").citySelect({prov:PRON, city:CITY, dist:COUNTY});
			}
		}else{
			$("#city").citySelect({
				nodata:"none",
				required:false
			}); 
		}
	}
	//页面生成 STORE_ID    
	function uuid(){
		var s = [];
	    var hexDigits = "0123456789abcdef";
	    for (var i = 0; i < 36; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
	    s[8] = s[13] = s[18] = s[23] = "-";
	    var uuid = s.join("");
	    return uuid;
	}
	//检索
	function tosearch() {
		top.jzts();
		$("#Form").submit();
	}
	
	function getRealPath(){
	  //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
	  var curWwwPath=window.document.location.href;
	   //获取主机地址之后的目录，如： myproj/view/my.jsp
	  var pathName=window.document.location.pathname;
	  var pos=curWwwPath.indexOf(pathName);
	  //获取主机地址，如： http://localhost:8083
	  var localhostPaht=curWwwPath.substring(0,pos);
	  //获取带"/"的项目名，如：/myproj
	  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	 //得到了 http://localhost:8083/myproj
	  var realPath=localhostPaht+projectName;
		if(projectName == "/lanker"){
			return realPath
		}else{
			return localhostPaht
		}  
	}

	//邮箱正则
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}
	//黄色感叹号警告提醒
	function remind(msg) {
		layer.msg(msg, {
			time : msg.length*200,
			icon : 0,
			offset : '50px',
			anim: 6
		})
	}
	//绿色成功提醒
	function success(msg){
		layer.msg(msg, {
			time : msg.length*200,
			icon : 1
		})
	}
	//红色错误提醒
	function error(msg){
		layer.msg(msg, {
			time : msg.length*200,
			icon : 2,
			anim: 6
		})
	}
	
	//alert方法
	function alert(msg) {
		layer.alert(msg);
	}
	//loading方法
	function loading(msg){
		if(msg == ""){
		    layer.load()
		}else{
			layer.msg(msg, {
			   icon: 16
			  ,shade: 0.01
			});
		}
	}
	
	function message(msg){
		layer.msg(msg,{time:msg.length*200});
	}
	
	
	
	