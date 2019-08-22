	
	//用户中心修改新手机号发送验证码
	function updatephone() {
		if($("#newphone").val()==""){
			layer.tips('请输入手机号', '#newphone', {
			 	tips: 3
	        });
			return false;
		}
		var PHONE = $.trim($("#newphone").val());
		$.ajax({
			type: "post",
			url: getRealPath()+'/accountSettings/verification.do',
	    	data: {PHONE:PHONE},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("error1" == data.result){
				 	layer.tips('手机号码格式不匹配，请重试..', '#newphone', {
			 			tips: 3
	        		});
				 }else if("error2"==data.result){
				 	layer.tips('该手机号码已存在，请重试..', '#newphone', {
			 			tips: 3
	        		});
				 }else if("frequent"==data.result){
					 	layer.tips('发送验证码太频繁，请稍候再试!', '#phonebtn', {
				 			tips: 3
		        		});
				}else if("success"==data.result){
					layer.msg('发送成功！', {
						time : 1000,
						icon : 1,
						offset: 'auto',
					})
					 var obj = $("#phonebtn");
					 settime(obj);
				 }else if("fail"==data.result){
				 	layer.tips('发送失败，请重试..', '#phonebtn', {
			 			tips: 3
	        		});
				 }
			}
		});
		return false;
		}
	
	//用户中心修改密码发送验证码
	function updatepassword() {
		var USER_ID = $("#USER_ID").val();
		$.ajax({
			type: "post",
			url: getRealPath()+'/accountSettings/getPhoneCode.do',
			data: {USER_ID:USER_ID},
			dataType:'json',
			cache: false,
			success: function(data){
				if("frequent"==data.result){
				 	layer.tips('发送验证码太频繁，请稍候再试!', '#codebtn', {
			 			tips: 3
	        		});
				 }else if("success"==data.result){
					 layer.msg('发送成功！', {
							time : 1000,
							icon : 1,
							offset: 'auto',
						})
					 var obj = $("#codebtn");
					 settime(obj);
				 }
			   }
			});
			return false;
		}
	
	var countdown=60; //定义全局变量(计时用)
	 function settime(obj) {//发送验证码倒计时
	     if (countdown == 0) { 
	         obj.attr('disabled',false); 
	         obj.html("获取验证码");
	         countdown = 60; 
	         return;
	     } else { 
	         obj.attr('disabled',true);
	         obj.html("重新发送(" + countdown + ")");
	         countdown--;
	     } 
	 setTimeout(function() { 
	     settime(obj) 
	     }
	     ,1000);
	 }
	
	
	layui.use([ "layer", "form", "element"], function(){
		var layer = layui.layer, form = layui.form, $ = layui.jquery;
		//用户中心更换手机
		$("#phone").click(function() {
			layer.open({
				btn : [ "确定", "取消" ],
				yes : function(index, layero) {
					//按钮【按钮一】的回调
					if($("#newphone").val() == ""){
						layer.tips('新手机号不能为空', '#newphone', {
			 				tips: 3
		    			});
						return false;
					}
					if($("#code").val() == ""){
						layer.tips('验证码不能空', '#code', {
			 				tips: 3
						});
						return false;
					}
					var flag=false;
					if($("#code").val()!=null){ /* 验证验证码是否正确 */
						var code = $.trim($("#code").val());
						var PHONE = $.trim($("#newphone").val());
						$.ajax({
							async:false,
							type: "POST",
							url: getRealPath()+'/accountSettings/verificationCheck.do',
					    	data: {CODE:code,PHONE:PHONE},
							dataType:'json',
							cache: false,
							success: function(data){
								if(data.result == "code_invalid"){
									layer.tips('验证码已失效，请重新发送', '#code', {
						 				tips: 3
				        			});
									flag=true;
								}else if(data.result == "error"){
									layer.tips('输入验证码错误', '#code', {
						 				tips: 3
				        			});
									flag=true;
								}else if(data.result == "error1"){
									layer.tips('系统繁忙,请稍后再试', '#code', {
						 				tips: 3
				        			});
									flag=true;
								}
							}
						});
					}
					if(flag){
						return false;
					}
					$.ajax({
						type: "POST",
						url: getRealPath()+'/accountSettings/savePhone.do',
						data: {PHONE:PHONE},
						dataType : 'json',
						success : function(data) {
							if ("success" == data.result) {
								layer.msg('修改成功', {
									time : 800,
									icon : 1
								}, function() {
									location.reload(index);
								});
							}else if ("error" == data.result) {
								layer.msg('手机格式不正确', {
									time : 800,
									icon : 1
								}, function() {
									location.reload(index);
								});
							} else {
								layer.msg('修改失败！', {
									time : 800,
									icon : 2
								}, function() {
									location.reload(index);
								});
							}
						},
					});
				},
				type : 1,
				shade : 0.6,
				title : "更换手机",
				area : [ '500px', '300px' ],
				content : $('#lanke-phone'),
			});
		})
		//用户中心修改密码
		$("#password").click(function(){
			layer.open({
				btn : [ "确定", "取消" ],
				yes : function(index, layero) {
					//按钮【按钮一】的回调
					if($("#oldPassword").val()==""){
						layer.tips('请输入旧密码', '#oldPassword', {
						 	tips: 3
				        });
						return false;
					}
					if($("#newPassword").val()==""){
						layer.tips('请输入新密码', '#newPassword', {
						 	tips: 3
				        });
						return false;
					}
					if($("#codephone").val()==""){
						layer.tips('请输入验证码', '#codephone', {
						 	tips: 3
				        });
						return false;
					}
					var flag=false;
					if($("oldPassword").val()!=""||$("#oldPassword").val()!=null){ // 验证旧密码是否正确 
						var PW = $("#oldPassword").val();
						$.ajax({
							async:false,
							type: "POST",
							url: getRealPath()+'/accountSettings/oldPassword.do',
							data: {oldPassword:PW},
							dataType:'json',
							cache: false,
							success: function(data){
								if("success" != data.result){
									layer.tips('原密码错误', '#oldPassword', {
						 				tips: 3
				        			});
									flag=true;
								 }
							}
						});
					}
					if(flag){
						return false;
					}
					if($("#newPassword").val()!=""){
						if($("#newPassword").val().length<6||$("#newPassword").val().length>20){
							layer.tips('新密码长度6-20个字符', '#newPassword', {
						 		tips: 3
				        	});
							return false;
						}
					}
					if($("codephone").val()!=""||$("#codephone").val()!=null){ /* 验证验证码是否正确 */
						var phone = $("#newPhone").text();
						var code = $("#codephone").val();
						$.ajax({
							async:false,
							type: "POST",
							url: getRealPath()+'/accountSettings/verificationCheck.do',
					    	data: {CODE:code,PHONE:phone},
							dataType:'json',
							cache: false,
							success: function(data){
								if(data.result == "code_invalid"){
									layer.tips('验证码已失效，请重新发送', '#codephone', {
						 				tips: 3
				        			});
									flag=true;
								}else if(data.result == "error"){
									layer.tips('输入验证码错误', '#codephone', {
						 				tips: 3
				        			});
									flag=true;
								}else if(data.result == "error1"){
									layer.tips('系统繁忙,请稍后再试', '#code', {
						 				tips: 3
				        			});
									flag=true;
								}
							}
						});
					}
					if(flag){
						return false;
					}
					var newPW = $("#newPassword").val();
					$.ajax({
						type: "POST",
						url: getRealPath()+'/accountSettings/savePw.do',
						data: {newPassword:newPW},
						dataType : 'json',
						success : function(data) {
							if ("success" == data.result) {
								layer.msg('修改成功', {
									time : 800,
									icon : 1
								},function() {
									location.reload(index);
								});
							} else {
								layer.msg('修改失败！', {
									time : 800,
									icon : 2
								}, function() {
									location.reload(index);
								});
							}
						},
					});
					
				},
				type : 1,
				shade : 0.6,
				title : "更换密码",
				area : [ '506px', '430px' ],
				content : $('#lanke-password'),

			});
		})
		//用户中心修改邮箱
	
})

    $('.close-layer').on('click', function(){
    	layer.close(layer.index)
	});
	