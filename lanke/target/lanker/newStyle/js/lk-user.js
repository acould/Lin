
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
//修改密码、注册账号
    //找回密码页面校正
    function pageverify(){
    	//检测用户名
    	if($("#USERNAME").val()==""){
			layer.tips('用户名不能为空', '#USERNAME', {
				tips : 3
			});
			$("#USERNAME").focus();
			return false;
		}else {
			$("#USERNAME").val(jQuery.trim($('#USERNAME').val()));			
		}
    	//检测手机号码
    	var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#PHONE").val()==""){
			layer.tips('手机号不能为空', '#PHONE', {
				tips : 3
			});
			$("#PHONE").focus();
			return false;
		}else if($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())){
			layer.tips('手机号格式不正确', '#PHONE', {
				tips : 3
			});
			$("#PHONE").focus();
			return false;
		}
		//检测密码
		if($("#password").val()==""){
			layer.tips('密码不能为空', '#password', {
				tips : 3
			});
			$("#password").focus();
			return false;
		}else if($("#password").val().length<6||$("#password").val().length>20){
			layer.tips('密码长度6-20个字符', '#password', {
				tips : 3
			});
			$("#password").focus();
			return false;
		}
		//第二遍密码
		if($("#chkpwd").val()==""){
			layer.tips('请确认密码', '#chkpwd', {
				tips : 3
			});
			$("#chkpwd").focus();
			return false;
		}else {
			if($("#password").val()!=$("#chkpwd").val()){
				layer.tips('两次密码不相同', '#chkpwd', {
					tips : 3
				});
				$("#chkpwd").focus();
				return false;
			}
		}	
		//检测验证码
		if($("#yzm").val()==""){
			layer.tips('验证码不能为空', '#yzm', {
				tips : 3
			});
			$("#yzm").focus();
			return false;
		}
		return true;
    }
   //修改密码方法
    function retSave(){
    	if(pageverify()){
    		//核对手机号和用户
			var luck = false;
    		var PHONE = $.trim($("#PHONE").val());
    		var USERNAME = $.trim($("#USERNAME").val());
    		$.ajax({
				async:false,
    			type: "POST",
    			url: getRealPath()+'/register/hasPw.do',
    	    	data: {PHONE:PHONE,USERNAME:USERNAME},
    			dataType:'json',
    			cache: false,
    			success: function(data){
    				 console.log(data.result)
    				 if("mismatch" == data.result){
    				 	layer.tips('手机号跟用户名不匹配，请核对!', '#USERNAME', {
    						tips : 3
    					});
    				 	$("#USERNAME").focus();
    				 	luck = true;
    				 }
    			}
    		});
			if(luck){
				return false;
			}
			//校正验证码
			var flag=false;
			var yzm = $.trim($("#yzm").val());
			var PHONE = $.trim($("#PHONE").val());
			$.ajax({
				async:false,
				type: "POST",
				url: getRealPath()+'/register/hasY.do',
		    	data: {yzm:yzm,PHONE:PHONE},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						layer.tips('验证码错误', '#yzm', {
    						tips : 3
    					});
						$("#yzm").focus();
						flag=true;
					 }
				}
			});
			if(flag){
				return false;
			};
			$("#userForm").submit();
    	}
    }
	
	//发送验证码
	function yzmy(){
		if($("#USERNAME").val()==""){
			layer.tips('请输入用户名', '#USERNAME', {
				tips : 3
			});
			$("#USERNAME").focus();
			return false;
		}
		var USERNAME = $.trim($("#USERNAME").val());

		if($("#PHONE").val()==""){
			layer.tips('请输入手机号', '#PHONE', {
				tips : 3
			});
			$("#PHONE").focus();
			return false;
		}
		var PHONE = $.trim($("#PHONE").val());
		$.ajax({
			type: "post",
			url: getRealPath()+'/register/getcodeA.do',
	    	data: {PHONE:PHONE,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("error" == data.result){
						layer.tips('手机号码格式不匹配，请核对', '#PHONE', {
							tips : 3
						});
						$("#PHONE").focus();
				 }else if("error1"==data.result){
						layer.tips('该手机号不是该用户绑定手机', '#PHONE', {
							tips : 3
						});
				 }else if("frequent"==data.result){
						layer.tips('发送验证码太频繁，请稍候再试', '#btn', {
							tips : 3
						});
				 }else if("success"==data.result){
					 var countdown=60; 
					 var obj = $("#btn");
					 settime(obj);
					 function settime(obj) { //发送验证码倒计时
					     if (countdown == 0) { 
					         obj.attr('disabled',false); 
					         //obj.removeattr("disabled"); 
					         obj.val("获取验证码");
					         countdown = 60; 
					         return;
					     } else { 
					         obj.attr('disabled',true);
					         obj.val("发送成功,重新发送(" + countdown + ")");
					         countdown--;
					     } 
					 setTimeout(function() { 
					     settime(obj) }
					     ,1000);
					 }
				 }else if("fail"==data.result){
					layer.tips('发送失败，请重试', '#btn', {
						tips : 3
					});
				 }
			}
		});
	}
	//邮箱正则
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}
	
	
	//注册页面校正
    function registerVerify(){
    	//检测用户名
    	if($("#USERNAME").val()==""){
			layer.tips('用户名不能为空', '#USERNAME', {
				tips : 3
			});
			return false;
		}else {
			$("#USERNAME").val(jQuery.trim($('#USERNAME').val()));			
	 	    var reg = /^(?!\d{4,16}$)(?:[0-9a-zA-Z_]{4,16}|![\u4E00-\u9FA5])$/;
		    if (!reg.test($("#USERNAME").val())){
	        	layer.tips('4-16个字符,不能全为数字,不能有汉字和特殊字符', '#USERNAME', {
			 		tips: 3
	        	});
	            return false;
		    }else{
		    	//检测账号是否存在
				var USERNAME = $.trim($("#USERNAME").val());
				var luck = false;
				$.ajax({
					async:false,
					type: "POST",
					url: getRealPath()+'/register/hasU.do',
			    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						if("error" == data.result){
							layer.tips('账户名 已存在', '#USERNAME', {
					 			tips: 3
			        		});
							 $("#USERNAME").focus();
							 luck = true;
						 }
					}
				});
				if(luck){
					return false;
				}
		    }
		}
		//检测密码
		if($("#password").val()==""){
			layer.tips('密码不能为空', '#password', {
				tips : 3
			});
			return false;
		}else if($("#password").val().length<6||$("#password").val().length>20){
			layer.tips('密码长度6-20个字符', '#password', {
				tips : 3
			});
			return false;
		}
		//第二遍密码
		if($("#chkpwd").val()==""){
			layer.tips('请确认密码', '#chkpwd', {
				tips : 3
			});
			return false;
		}else {
			if($("#password").val()!=$("#chkpwd").val()){
				layer.tips('两次密码不相同', '#chkpwd', {
					tips : 3
				});
				return false;
			}
		}
    	//检测手机号码
    	var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#PHONE").val()==""){
			layer.tips('手机号不能为空', '#PHONE', {
				tips : 3
			});
			return false;
		}else if($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())){
			layer.tips('手机号格式不正确', '#PHONE', {
				tips : 3
			});
			return false;
		}else{
			//检测手机是否存在
			var PHONE = $.trim($("#PHONE").val());
			var flag = false;
			$.ajax({
				async:false,
				type: "POST",
				url: getRealPath()+'/register/hasP.do',
		    	data: {PHONE:PHONE,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("error" == data.result){
					 	layer.tips('手机号'+PHONE+' 已存在', '#PHONE', {
				 			tips: 3
		        		});
						 $("#PHONE").focus();
						 flag = true;
					 }
				}
			});
			if(flag){
				return false;
			}
		}
		//检测邮箱
		if($("#EMAIL").val()==""){
			layer.tips('输入邮箱', '#EMAIL', {
			 	tips: 3
	        });
			return false;
		}else if(!ismail($("#EMAIL").val())){
			layer.tips('邮箱格式不正确', '#EMAIL', {
			 	tips: 3
	        });
			return false;
		}else{
			//检测邮箱是否存在
			var EMAIL = $.trim($("#EMAIL").val());
			var num = false;
			$.ajax({
				async:false,
				type: "POST",
				url: getRealPath()+'/register/hasE.do',
		    	data: {EMAIL:EMAIL,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("error" == data.result){
					 	layer.tips('邮箱 '+EMAIL+' 已存在', '#EMAIL', {
				 			tips: 3
		        		});
						 $("#EMAIL").focus();
						 num = true;
					 }
				}
			});
			if(num){
				return false;
			}
		}
		
		//检测验证码
		if($("#yzm").val()==""){
			layer.tips('验证码不能为空', '#yzm', {
				tips : 3
			});
			return false;
		}
		if(!$("#rememberUser").is(':checked')){
			layer.tips('请勾选', '#rememberUser', {
				tips : 3
			});
			return false;
		}
		return true;
    }
	//注册方法
	function regSave(){
	    if(registerVerify()){
			$.ajax({
				type: "POST",
				url: getRealPath()+'/register/zhuce.do',
		    	data: $('#userForm').serialize(),
				dataType:'json',
				cache: false,
				success: function(data){
					if("false" == data.result){
						error(data.message)
					 }else if ("true" == data.result){
					    var index = parent.layer.getFrameIndex(window.name);
					 	parent.layer.close(index);
					 	parent.layer.open({
						  type: 2,
						  title: false,
						  area: ['668px', '310px'],
						  shade: 0.4,
						  closeBtn: 0,
						  skin:"layui-layer-nobg",
						  shadeClose: false,
						  content: getRealPath()+data.backUrl
						});
					 }
				}
			});
		}
	} 
	
	
	
	
	
	