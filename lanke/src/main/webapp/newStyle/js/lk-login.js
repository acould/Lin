
		var flag = 1;
		//加密cookie，读取cookie
		window.onload = function(){
			if($.cookie('loginname') && $.cookie('token')){
				$("#loginname").val($.base64.decode($.cookie('loginname')));
				var token = $.base64.decode($.cookie('token'));
				$("#hiddenPPd").val(token);
				if(token.length > 20){
					$("#password").val(token.substring(0,20));
				}else{
					$("#password").val(token);
				}
				$("#saveid").attr("checked","checked");
				//$("#code").focus();
				$("#code").attr("autofocus","autofocus")
				flag = 2;
			}else{
				$("#saveid").attr("checked",false);
				$("#code").removeAttr("autofocus")
				$("#loginname").attr("autofocus","autofocus")
			}
		}
		function checkPsd(){
			flag = 1;
		}
		//判断当我们的浏览器不等于IE的6.7.8.9时，我们才去初始化我们的WOW.js
		if (!(/msie [6|7|8|9]/i.test(navigator.userAgent))) {
			new WOW().init();
		};
		// 导航栏点击效果
		$(document).ready(function() {
			$(".click-a").click(function() {
				$('html, body').animate({
					scrollTop : $("#intro").offset().top
				}, 750);
			});
			$(".nav_p > a").click(function() {
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			})
			$(".fixed-e").click(function() {
        		$('html, body').animate({
          	 	scrollTop : $(".la-nav-bg").offset().top
        		}, 750);
        	});
    	
    		// 图片禁止拖住 右键
    		var imgs=$("img");
    		imgs.on("contextmenu",function(){return false;});
    		imgs.on("dragstart",function(){return false;});
		});

		//服务器校验
		function severCheck() {
			
			if (check()) {
				var loginname = $("#loginname").val();
				var password = $("#password").val();
				if(flag == 2 ){
					password = $("#hiddenPPd").val();
				}
				var code = "qq313596790fh" + loginname + ",fh," + password
						+ "QQ978336446fh" + ",fh," + $("#code").val();
				$.ajax({
					type : "POST",
					url : 'login_login',
					data : {
						KEYDATA : code,
						flag : flag,
						tm : new Date().getTime()
					},
					dataType : 'json',
					cache : false,
					success : function(data) {
						if ("success" == data.result) {
							if($('#saveid').is(':checked')) {
								saveCookie(data.ppd);
							 }else{
								 clearCookie();
							 }
							window.location.href = "main/index";
						} else if ("usererror" == data.result) {
							layer.tips('用户名或密码有误', '#loginname', {
								tips : 3
							});
							$("#loginname").focus();
						} else if ("userstop" == data.result) {
							layer.alert('您的账户已被停用,请联系客服,客服电话:4000965099',
									 {
										icon : 0
									});
							$("#loginname").focus();
						}else if ("subscribe" == data.result) {
							layer.alert('您当前绑定公众号类型为订阅号,无法正常使用功能,请换绑为服务号,如有疑问请联系客服,客服电话:4000965099',
									 {
										icon : 0
									});
							$("#loginname").focus();
						} else if ("codeerror" == data.result) {
							layer.tips('验证码有误', '#code', {
								tips : 3
							});
							$("#code").focus();
						} else if ("authorize" == data.result || "authorize1" == data.result) {
							$('#myModal').modal('show');
							$("#internet_id").val(data.internet_id);
						} else {
							layer.tips('缺少参数', '#loginname', {
								tips : 3
							});
							$("#loginname").focus();
						}
					}
				});
			}
		}
		//切换验证码
		$(document).ready(function() {
			changeCode();
			//bind() 方法为被选元素添加一个或多个事件处理程序，并规定事件发生时运行的函数。
			$("#codeImg").bind("click", changeCode);     
			$("#codeImgA").bind("click", changeCode);
		}); 
		
		//回车键登入
		$(document).keydown(function(event) {
			if (event.keyCode == 13) {
				event.preventDefault(); 
				//trigger() 方法触发被选元素的指定事件类型。
				$("#to-recover").trigger("click");
			}
		});
		//获取当前系统的时间戳
		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}
		//改变验证码图片的路径
		function changeCode() {
			$("#codeImgA").attr("src", "code.do?t=" + genTimestamp());
		}

		//客户端校验
		function check() {
			if ($("#loginname").val() == "") {
				layer.tips('用户名不得为空', '#loginname', {
					tips : 3
				});
				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}

			if ($("#password").val() == "") {
				layer.tips('密码不得为空', '#password', {
					tips : 3
				});
				$("#password").focus();
				return false;
			}
			if ($("#code").val() == "") {
				layer.tips('验证码不得为空', '#code', {
					tips : 3
				});
				$("#code").focus();
				return false;
			}
			layer.tips('正在登录 , 请稍后 ...', '#loginbox', {
				tips : 3
			});
			return true;
		}
		//存一个7天的cookie
		function saveCookie(ppd) {
			if ($('#saveid').is(':checked')) {
				$.cookie('loginname', $.base64.encode($("#loginname").val()), {
					expires : 7
				});
				$.cookie('token', $.base64.encode(ppd), {
					expires : 7
				});
			}
		}
		//清除cookie
		function clearCookie() {
			$.cookie('loginname', '', {
				expires : -1
			});
			$.cookie('token', '', {
				expires : -1
			});
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
		getRealPath()
		//注册窗口
		function goRegister(){
			layer.open({
			  type: 2,
			  title: false,
			  area: ['420px', '570px'],
			  shade: 0.4,
			  closeBtn: 1,
			  skin:"layui-layer-nobg",
			  move:".layui-layer-content",
			  moveOut: true,
			  shadeClose: true,
			  content: getRealPath()+'/register/gotoZhuCe.do'
			});
		}
		
		function goAuthoriz(){
			var newTab=window.open(getRealPath()+'/register/goAccredit.do'); 
			$.ajax({
				type: "POST",
				url: getRealPath()+'/register/goAuthoriz.do',
				data: {internet_id:$("#internet_id").val()},
				dataType:'json',
				success: function(data){
					if(data.result == "true"){
					 	newTab.location.href = data.url1;  
					}else{
						newTab.location.href = getRealPath()+'/register/goAccreditError.do'
					}
				},
				error:function(){
				   newTab.location.href = getRealPath()+'/register/goAccreditError.do'
				}
			});
		}
		
		