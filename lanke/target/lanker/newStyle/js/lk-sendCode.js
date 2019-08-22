
//手机号正则
var phoneReg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;

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
	
	function sendCode(){
		var phone = $("#phone").val().trim();
		if(phone == ""){
			message("请填写手机号");
			return;
		}
		if(phone.length != 11 || !phoneReg.test(phone)){
			message("请填写正确的手机号");
			return;
		}
		$.ajax({
			type: "POST",
			url: getRealPath()+'/register/sendCode.do',
			data: {phone:phone},
			dataType: 'json',
			cache: false,
			success: function (data) {
				message(data.message);
				if(data.result == "true"){
					var obj = $("#codeBtn");
					settime(obj);
				}
			},
			error: function () {
				message("系统繁忙，请稍后再试！");
				return;
			}
		});
	}
	
	var countdown=60; //定义全局变量(计时用)
	function settime(obj) {//发送验证码倒计时
		if (countdown == 0) {
			obj.attr('disabled',false); 
			obj.html("发送验证码");
			countdown = 60; 
			return;
		} else {
			obj.attr('disabled',true);
			obj.html("重新发送(" + countdown + ")");
			countdown--;
		} 
		setTimeout(function() { 
			settime(obj) 
		},1000);
	}
	
	
	
	
	
	
	
	
	
	
	