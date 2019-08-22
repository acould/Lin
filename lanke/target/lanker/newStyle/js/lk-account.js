	
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
				area : [ '490px', '258px' ],
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
				area : [ '506px', '380px' ],
				content : $('#lanke-password'),

			});
		})
		//用户中心修改邮箱
		$("#email").click(function() {
			layer.open({
				btn : [ "确定", "取消" ],
				yes : function(index, layero) {
					if($("#newemail").val()==""){
						layer.tips('请输入新邮箱', '#newemail', {
						 	tips: 3
				        });
						return false;
					}
					else if($("#newemail").val()!=""){
						 if(!ismail($("#newemail").val())){
							layer.tips('邮箱格式不正确', '#newemail', {
						 		tips: 3
				        	});
							return false;
						}
					}
					 //判断邮箱唯一
					/*var EMAIL = $.trim($("#newemail").val());
					$.ajax({
						type: "POST",
						url: '<%=basePath%>register/hasE.do',
				    	data: {EMAIL:EMAIL,tm:new Date().getTime()},
						dataType:'json',
						cache: false,
						success: function(data){
							 if("error" == data.result){
							 	layer.tips('邮箱 '+EMAIL+' 已存在', '#newemail', {
						 			tips: 3
				        		});
								 $("#newemail").val('');
							 }
						}
					}); */
					
					$.ajax({
						type: "POST",
						url: getRealPath()+'/accountSettings/saveEmail.do',
						data : $('#newEmailFrom').serialize(),
						dataType : 'json',
						cache : false,
					    success : function(data) {
						  if ("success" == data.result) {
							layer.msg('修改成功',{time : 800,icon : 1},
							function() {
								location.reload(index);
								});
								} 
						  else {
							  layer.msg('修改失败！',{time : 800,icon : 2},
							function() {
							  location.reload(index);
							  });
							  }
						  }
					});
			},
			type : 1,
			shade : 0.6,
			title : "更换邮箱",
			area : [ '360px','220px' ],
			content : $('#lanke-email'),
		});
	})
	
})

//特权列表页面，下拉框监听回调
function search(basePath,storeId){
		$.ajax({
			type: "POST",
			url: basePath+'accountSettings/changeStore.do',
			data:{STORE_ID:storeId},
			dataType:'json',
			cache: false,
			success: function(data){
				if("true" == data.result){
					$("#lanke-VSTATE").html('');
					$("#Through_Time").html('');
					
					$("#lanke-store").html(data.pd.store_name);
					var STORE_ID = data.pd.STORE_ID;
					var STORE_NAME = data.pd.store_name;
					/**** 计费系统开通情况，加v*****/
					var bstate = data.pd.bstate;
					var Through_Time = data.pd.Through_Time;
					var EXPIRATION_TIME = data.pd.EXPIRATION_TIME;
					//未加V
					if(bstate == 0){
						$("#lanke-addV").html('<a onclick=flow("'+STORE_ID+'","'+STORE_NAME+'","0") class="text-primary">去绑定</a>');
						$(".btn_type1").css("border-color","rgba(26,179,148,0.4)");
					}
					//审核中
					if(bstate == 2){
						$("#lanke-addV").html('<a class="text-default">审核中</a>');
						$(".btn_type1").css("border-color","rgba(194,194,194,0.4)");
					}
					//未通过
					if(bstate == 3){
						$("#lanke-addV").html('<a onclick=flow("'+STORE_ID+'","'+STORE_NAME+'","2") class="text-danger">未通过</a>');
						$(".btn_type1").css("border-color","rgba(237,85,101,0.4)");
					}
					//已开通
					if(bstate == 1){
						$("#lanke-addV").html('<a onclick=toShow("'+STORE_ID+'","'+STORE_NAME+'","1") class="text-success">已绑定</a>');
						$(".btn_type1").css("border-color","rgba(28,132,198,0.5)");
					}
					
					/**** 在线支付开通情况，加￥*****/
					var cid = data.pd.cid;
					var cstate = data.pd.cstate;
					var cstatus = data.pd.cstatus;
					var updatetime = data.pd.updatetime;
					if(updatetime != null && updatetime.indexOf(".") > 0){
						updatetime = updatetime.split(".")[0];
					}
					console.log(cstate +"==sss===="+cstatus);
					if(cstate == "" && cstatus == ""){
						$("#lanke-bank").html('<a onclick=goJLOpen("'+STORE_ID+'","'+cid+'","'+basePath+'") class="text-primary">去开通</a>');
						$(".btn_type2").css("border-color","rgba(26,179,148,0.4)");
					}
					if(cstate == '2' && cstatus == ''){
						$("#lanke-bank").html('<a class="text-default">审核中</a>');
						$(".btn_type2").css("border-color","rgba(194,194,194,0.4)");
					}
					if(cstate == '0' && cstatus == ''){
						$("#lanke-bank").html('<a onclick=goJLOpen("'+STORE_ID+'","'+cid+'","'+basePath+'") class="text-danger">未通过</a>');
						$(".btn_type2").css("border-color","rgba(237,85,101,0.4)");
					}
					if(cstate == '1' && cstatus == '2'){
						$("#lanke-bank").html('<a onclick=goJLOpen("'+STORE_ID+'","'+cid+'","'+basePath+'") class="text-warning">填快递</a>');
						$(".btn_type2").css("border-color","rgba(218,148,36,0.4)");
					}
					if(cstate == '1' && cstatus == '0'){
						$("#lanke-bank").html('<a onclick=goJLOpen("'+STORE_ID+'","'+cid+'","'+basePath+'") class="text-success">待开通</a>');
						$(".btn_type2").css("border-color","rgba(28,132,198,0.5)");
					}
					if(cstate == '1' && cstatus == '1'){
						$("#lanke-bank").html('<a onclick=goJLOpen("'+STORE_ID+'","'+cid+'","'+basePath+'") class="text-success">已开通</a>');
						$(".btn_type2").css("border-color","rgba(28,132,198,0.5)");
						
						/***** 加v和加￥，开通情况 *******/
						
						if(bstate == 1){
							$("#lanke-VSTATE").html("您当前使用的版本是 : 轻营销+￥+V版");
							$("#Through_Time").html('计费系统绑定时间：'+Through_Time+'<span style="padding-left: 30px" id="EXPIRATION_TIME">到期时间为:'+EXPIRATION_TIME+'</span>');
							$("#updatetime").html('在线支付开通时间：'+updatetime)
							$(".lanke-vip-msg").css("top","20%");
						}else{
							$("#lanke-VSTATE").html("您当前使用的版本是 : 轻营销+￥版");	
							$("#Through_Time").html("在线支付开通时间："+updatetime);
							$("#updatetime").html('');
							$(".lanke-vip-msg").css("top","28%");
						}
					}else{
						if(bstate == 1){
							$("#lanke-VSTATE").html("您当前使用的版本是 : 轻营销+V版");	
							$("#Through_Time").html("计费系统绑定时间："+Through_Time+'<span style="padding-left: 30px" id="EXPIRATION_TIME">到期时间为：'+EXPIRATION_TIME+'</span>');
							$("#updatetime").html('');
							$(".lanke-vip-msg").css("top","28%");
						}else{
							$("#lanke-VSTATE").html("您当前使用的版本是 : 轻营销基础版");
							$("#Through_Time").html("到期时间：永久免费");
							$("#updatetime").html('');
							$(".lanke-vip-msg").css("top","28%");
						}
					}
					
					
				}else{
					message(data.message);
					$("#lanke-store").html("");
					$("#lanke-VSTATE").html("暂无门店,请前往门店管理中添加门店");
				}
			}
		});
	}
	

	//门店管理页面，新增方法
	function storeShow_Add(){
		layer.open({
			btn: ['保存','关闭'],
			btn1: function(index, layero){
				//按钮的回调
				var res = window["layui-layer-iframe" + index].store_submit();
				if(res){
					layer.msg("新增门店成功",{icon:1,time:800},function(){
                		layer.closeAll()
						layer.open({	
	        				type:1
	                		,title:"新增门店提示"
	                		,shade:0.8
	                		,shadeClose:false
	                		,content:$(".modal-body")
	                		,success:function(layero,index){
	                			$("#motai").on("click",function(){
									layer.close(index);
	                			})
	                			$("#ud").on("click",function(){
									layer.close(index);
									window.open(getRealPath()+'/accountSettings/toAddv.do');
	                			})
							}
							,area: ['460px','270px']
							,end:function(){
								location.reload();
							}
						})
                	})
				}
			},
			skin: 'demo-class',
	        btnAlign: 'c',
	        type: 2,
	        title: '新增门店',
	        shadeClose: false,
	        shade: 0.8,
	        area: ['620px', '75%'],
	        content:[ getRealPath()+'/storeShow/goAdd.do'],
		});
	}
    //门店管理页面，状态禁用/启用
    function storeShow_Statebt(Id, STATE, A) {
        layer.confirm('确定要' + A + '吗?', {
            btn: ['确定', '取消'],
            title: A,
        }, function (){
            $.ajax({
                type: "POST",
                url: getRealPath()+'/storeShow/statebt.do',
                data: {STORE_ID: Id, STATE: STATE},
                dataType: 'json',
                cache: false,
                success: function (data) {
                    layer.msg(data.message);
                    if (data.result == "true") {
                        setTimeout(function () {
                            location.reload();
                        }, 800)
                    }
                },
                error: function () {
                    layer.msg("系统繁忙，请稍后再试！");
                    return false;
                }
            });
        }, function () {
            return
        });
    }

    //门店管理页面，编辑标签
    function store_EditTips(NAME, ID) {
        layer.open({
            btn: ['保存', '关闭'],
            btn1: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].save();
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '编辑' + NAME,
            shadeClose: false,
            shade: 0.8,
            area: ['580px', '486px'],
            content: [getRealPath()+'/storeShow/goTipsEdit.do?STORE_ID=' + ID],
        });
    }

    //门店管理页面，修改
    function store_Edit(Id) {
        layer.open({
            btn: ['保存', '关闭'],
            btn1: function (index, layero) {
                //按钮的回调
                var res = window["layui-layer-iframe" + index].store_submit();
                
            },
            skin: 'demo-class',
            btnAlign: 'c',
            type: 2,
            title: '编辑门店信息',
            shadeClose: false,
            shade: 0.8,
            area: ['620px', '75%'],
            fixed: false, //不固定
            maxmin: true,
            content: [getRealPath()+'/storeShow/goEdit.do?STORE_ID='+Id]
        });
    }
    
    //绑定计费系统流程图窗口
    function flow(store_id,store_name,state){
    	$("#flow-src1").val(store_id)
    	$("#flow-src2").val(store_name)
    	$("#flow-src3").val(state)
    	layer.open({
    		  type: 1,
    		  title: false,
    		  closeBtn: 0,
    		  area: '735px',
    		  skin: 'layui-layer-nobg', //没有背景色
    		  shadeClose: false,
    		  shade:0.7,
    		  content: $('#flow'),
    		  move :".lk-flow-title"
    		});
    }
	
	function goPayOpen(store_id,id,basePath){
		$("#idd").val(id);
		$("#store_idd").val(store_id);
		$("#YH_FORM").attr("action",basePath+"accountSettings/goPayOpen.do");
		$("#YH_FORM").submit();
	}

    function goJLOpen(store_id,id,basePath) {
        $("#idd").val(id);
        $("#store_idd").val(store_id);
        $("#YH_FORM").attr("action", basePath + "jialian/goEdit.do");
        $("#YH_FORM").submit();
    }

    $('.close-layer').on('click', function(){
    	layer.close(layer.index)
	});
	