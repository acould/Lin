	var htmls = '点击上传';
	//鼠标移入操作
	var mouseover = {
		agent_business:function(othis){
			var pp = '<p class="lk-pay-upTip">请上传当前代理商的营业执照副本（年检有效期内），原件照片或者扫描件</p>'
			othis.html(htmls)
			$("#lk-payUp-upper").prepend(pp)
		},
		business:function(othis){
			var pp = '<p class="lk-pay-upTip">请上传当前门店的营业执照副本（年检有效期内），原件照片或者扫描件</p>'
			othis.html(htmls)
			$("#lk-payUp-upper").prepend(pp)
		},
		legal_front:function(othis){
			var pp = '<p class="lk-pay-upTip">需与营业执照上的法人保持一致</p>'
			othis.html(htmls)
			$("#lk-payUp-upper").prepend(pp)
		},
		legal_side:function(othis){
			var pp = '<p class="lk-pay-upTip">需与已上传的营业执照上的法人身份证正面保持一致</p>'
			othis.html(htmls)
			$("#lk-payUp-upper").prepend(pp)
		},
		open_bank:function(othis){
			var pp = '<p class="lk-pay-upTip">请提供开户许可证、对公账户存折、对公存款账户信息，三者选其一</p>'
			othis.html(htmls)
			$("#lk-payUp-lower").prepend(pp)
		},
		internet_culture:function(othis){
			var pp = '<p class="lk-pay-upTip">需与营业执照上的企业名称保持一致</p>'
			othis.html(htmls)
			$("#lk-payUp-lower").prepend(pp)
		}
	}
	//鼠标移出操作
	var mouseout = {
		agent_business:function(othis){
			othis.html("营业执照副本")
			$("#lk-payUp-upper").children("p").remove()
		},
		business:function(othis){
			othis.html("营业执照副本")
			$("#lk-payUp-upper").children("p").remove()
		},
		legal_front:function(othis){
			othis.html("法人身份证正面")
			$("#lk-payUp-upper").children("p").remove()
		},
		legal_side:function(othis){
			othis.html("法人身份证反面")
			$("#lk-payUp-upper").children("p").remove()
		},
		open_bank:function(othis){
			othis.html("对公账户信息证明")
			$("#lk-payUp-lower").children("p").remove()
		},
		internet_culture:function(othis){
			othis.html("网络文化经营许可证")
			$("#lk-payUp-lower").children("p").remove()
		}
	}
	//鼠标点击操作
	var active = {
		agent_business:function(divs,i){
			$("#img1").html(divs);
		},
		business:function(divs,i){
			$("#img1").html(divs);
		},
		legal_front:function(divs,i){
			$("#img2").html(divs);
			$(".pay-imgBox").css("height","130px") 
		},
		legal_side:function(divs,i){
			$("#img3").html(divs);
			$(".pay-imgBox").css("height","130px")
		},
		open_bank:function(divs,i){
			$("#img4").html(divs);
		},
		internet_culture:function(divs,i){
			$("#img5").html(divs);
			$(".pay-imgBox").css("height","130px")
		}
	}
	var state = $("#sstate").val();
	var user_type = $("#ssuser_type").val();
	if((state == null || state == '' || state == '0') && user_type == 'user'){
		$(".lk-pay-upload").mouseover(function(){
			var othis = $(this), type = othis.data('type');
			mouseover[type] ? mouseover[type].call(this, othis) : '';
		}).mouseout(function(){
			var othis = $(this), type = othis.data('type');
			mouseout[type] ? mouseout[type].call(this, othis) : '';
		}).click(function(){
			var othis = $(this), type = othis.data('type');
			$("#file").trigger("click");
			$("#file").off("change");
			$("#file").on("change",function(){
				uploadImg(type)
			})
		})
	}
	
	
	var i = 0;
	function uploadImg(type){
		var file = document.getElementById("file").files[0];
	    var reader = new FileReader()
	    reader.readAsDataURL(file);  
	    layer.load();
	   	reader.onload = function(e){
	   		var src = e.target.result;
	    	render(src,type,i);
	    	i++
	   	}
	}
	    var MAX_HEIGHT = 1000;
		function render(src,type,i){
		   var image = new Image();
		   image.onload = function() {
		       var canvas = document.createElement("canvas");
		       console.log(image.height)
		       if (image.height > MAX_HEIGHT && image.height >= image.width) {
		           image.width *= MAX_HEIGHT / image.height;
		           image.height = MAX_HEIGHT;
		       }
		       if(image.width > MAX_HEIGHT && image.width > image.height) {
		           image.height *= MAX_HEIGHT / image.width;
		           image.width = MAX_HEIGHT;
		       }
		       var ctx = canvas.getContext("2d");
		       ctx.clearRect(0, 0, canvas.width, canvas.height);
		       canvas.width = image.width;
		       canvas.height = image.height;
		       ctx.drawImage(image, 0, 0, image.width, image.height);
			   // document.getElementById('img').src = canvas.toDataURL("image/png");
		       var blob = canvas.toDataURL("image/jpeg");
		       layer.closeAll()
		       var divs = '<div class="lk-pay-upImg" style="background: url('+blob+') no-repeat center center;background-size:120%;" id="div'+i+'">'+
		            	        	'<div class="img-operate" >'+
								   			'<p style="margin: 36px 0 20px 0;" onclick="delImg('+i+')">删除</p>'+
								   			'<p onclick=seeImg("'+blob+'")>查看</p>'+ 
							   		'</div>'+
							   		'<input type="hidden" name="'+type+'" value="'+blob+'">'+
		            	  '</div>';
		       active[type] ? active[type].call(this,divs,i) : '';
		   }
		   image.src = src;
	    }
	    
	    //删除图片
		function delImg(ii){
			if((state == null || state == '' || state == '0') && user_type == 'user'){
				var id = "#div"+ii;
				layer.confirm('确定要删除吗?', {
					btn: ['确定','取消'],
				}, function(index,layero){
					$(id).remove();
					var lk_pay_upImg = $(".lk-pay-upImg").length;
					if(lk_pay_upImg == 0 ){
						$(".pay-imgBox").css("height","auto")
					}
					$("#file").val("");
					layer.close(index)
				 }, function(){
					 return
				 });
			}
		}
		//查看图片
		function seeImg(src2){
			layer.open({
				  type: 1,
				  title: false,
				  closeBtn: 0,
				  area: '600px',
				  skin: 'layui-layer-nobg', //没有背景色
				  shadeClose: true,
				  move: 'img',
				  moveOut: true,
				  content: '<img src="'+src2+'" width="100%" />'
			});
		}
		
		//检查参数是否为空
		function check(){
			if($("#enterprise_name").val().trim() == ""){
				message("请填写企业名称");
				return false;
			}
			if($("#corporate_name").val().trim() == ""){
				message("请填写法人姓名");
				return false;
			}
			if($("#corporate_id").val().trim() == ""){
				message("请填写法人身份证");
				return false;
			}
			if($("#phone").val().trim().length > 0){
				if($("#phone").val().trim().length != 11 || !phoneReg.test($("#phone").val().trim())){
					message("请填写正确的手机号");
					return false;
				}
				if($("#code").val().trim() == ""){
					message("请填写验证码");
					return false;
				}
			}
			if($("#img1").html().trim() == ""){
				message("请上传营业执照副本");
				return false;
			}
			if($("#img2").html().trim() == ""){
				message("请上传法人身份证正面");
				return false;
			}
			if($("#img3").html().trim() == ""){
				message("请上传法人身份证反面");
				return false;
			}
			if($("#img4").html().trim() == ""){
				message("请上传开户许可证");
				return false;
			}
			if($("#img5").html().trim() == ""){
				message("请上传网络文化经营许可证");
				return false;
			}
			return true;
		}
		//保存资料
		function save(basePath,type){
			$("#data_type").val(type);
			if(type == "0" ){
				if(!check()){
					return;
				}
				layer.confirm('确定要提交吗？', {
				   btn: ['确定','取消'] //按钮
				}, function(){
					save_save(basePath);
				}, function(){
					return;
				});
			}else{
				save_save(basePath);
			}
			
			
		}
		//提交开通资料
		function save_save(basePath){
			$.ajax({
				type	: "POST",
				url		:basePath+'accountSettings/savePay.do',
				data	:$('#Form').serialize(),
				dataType:'json',
				beforeSend: beforeSend(''),
				success: function(data) {
					layer.closeAll();
					
					if(data.result == "true"){
						//返回结果正确时
						opener.location.reload();//刷新父页面
						$("#id").val(data.id);
						$("#phone").val(data.phone);
						layer.open({
						  type: 1,
						  btn:["确定"],
						  yes:function(){
						  	//关闭当前页面
							setTimeout(function(){ 
								//关闭当前页面
								window.opener = null;
								window.open("","_self");
								window.close();
							},500)
						  },
						  area: '450px',
						  title:false,
						  closeBtn: 0, //不显示关闭按钮
						  shadeClose: false, //开启遮罩关闭
						  content: '<div style="text-align:center;padding:68px">'+
						  				'<i class="iconfont" style="color:#1ab394;font-size:60px;">&#xe66a;</i>'+
						  				'<p style="padding-top:20px;">提交成功，审核结果将会在1-3个工作日内发至您的手机上，请耐心等待~</p>'+
						  			'</div>'
						});
					}else if(data.result == "false" ){
						message(data.message);
						if(data.phone != ''){
							$("#phone").val(data.phone);
						}
					}
				},
				error:function(){
					layer.close(layer.index);
					message("系统繁忙，请稍后再试！");
					return;
				}
			});
		}
		//审核
		function review(basePath,type){
			if(type == '0'){
				layer.open({
					btn: ['确定','取消'],
					yes: function(index,layero){
						submitReview(basePath,type);
					},
					type : 1,
					title : '请填写不通过的原因',
					skin : "demo-class",
					shadeClose : false,
					shade : 0.8,
					fixed :true,
					area : [ '445px', '300px' ],
					content : $('.FAIL')
				});
			}else if(type == '1'){
				layer.confirm('确定要通过该资料审核吗？', {
				   btn: ['确定','取消'] //按钮
				}, function(){
					submitReview(basePath,type);
				}, function(){
					return;
				});
			}
		}
		//提交审核情况
		function submitReview(basePath,type){
			var form = "";
			if(type == "0"){
				if($("#opinion").val().trim() == ""){
					message("请填写原因");
					return;
				}
				form = $('#REVIEW_FORM').serialize();
			}else if(type == "1"){
				form = {'review_state':'1','opinion':'审核通过'};
				form["intenetdatum_id"] = $("#id").val();
			}
						
			$.ajax({
				type	: "POST",
				url		: basePath+'payOpenReview/review.do',
				data	: form,
				dataType:'json',
				beforeSend: beforeSend(''),
				success: function(data){
					layer.closeAll();
					message(data.message);
					if(data.result == "true"){
						//返回结果正确时
						opener.location.reload();//刷新父页面
						setTimeout(function () { 
							window.opener = null;
							window.open("","_self");
							window.close();
						},1000)
						
					}
				},
				error:function(){
					layer.closeAll();
					message("系统繁忙，请稍后再试！");
					return;
				}
			});
			
		}
		
		//提交快递
		function submitExpress(basePath){
			if($("#express_company").val().trim() == ""){
				message("请填写快递公司");
				return;
			}
			if($("#express_number").val().trim() == ""){
				message("请填写快递单号");
				return;
			}
			layer.confirm('确定要提交吗', {
				   btn: ['确定','取消'] //按钮
				}, function(){
					
					$.ajax({
						type	: "POST",
						url		: basePath+'accountSettings/saveExpress.do',
						data	: $('#EXPRESS_FORM').serialize(),
						dataType:'json',
						beforeSend: beforeSend(''),
						success: function(data) {
							layer.close(layer.index);
							message(data.message);
							if(data.result == "true"){
								$("#express_div").remove();
								//返回结果正确时
								opener.location.reload();//刷新父页面
								setTimeout(function () { 
									//关闭当前页面
									window.opener = null;
									window.open("","_self");
									window.close();
								},1000)
								
							}
						},
						error:function(){
							layer.close(layer.index);
							message("系统繁忙，请稍后再试！");
							return;
						}
					});
			}, function(){
				return;
			});
		}
		
		//提交开通
		function submitOpen(id,basePath){
			$("#intenetdatum_id").val(id);
			$("#business_number").val("");
			layer.open({
				btn: ['确定','取消'],
				yes: function(index,layero){
					$.ajax({
						type	: "POST",
						url		: basePath+'payOpenReview/open.do',
						data	: $('#OPEN_FORM').serialize(),
						dataType:'json',
						beforeSend: beforeSend(''),
						success: function(data) {
							layer.close(layer.index);
							message(data.message);
							if(data.result == "true"){
								//返回结果正确时
								$('.FAIL').hide();
								setTimeout(function () { 
									location.reload();//刷新父页面
								},700)
							}
						},
						error:function(){
							layer.close(layer.index);
							message("系统繁忙，请稍后再试！");
							return;
						}
					});
				},
				btn2: function(index,layero){
					$('.FAIL').hide();
				},
				type : 1,
				title : '在线支付开通',
				skin : "demo-class",
				shadeClose : false,
				shade : 0.8,
				fixed :true,
				area : [ '500px', '320px' ],
				content : $('.FAIL')
			});
		}
		
		function toExcel(){
			var search = 'payOpenReview/list.do';
			var excel = 'payOpenReview/exportExcel.do';
			$("#form").attr("action",excel);
			$("#form").submit();
			$("#form").attr("action",search);
		}
		
/***************************************充值规则********************************************************/		
		function go_rechargeEdit(basePath){
			$("#allChecked").html("");
			$("#notAllChecked").html("");
			
			$.ajax({
				type: "POST",
				url: basePath+'rechargeRule/getStoreList.do',
				data : {},
				dataType : 'json',
				async:false,
				success : function(res) {
					console.log(res);

					if(res.errcode == 0){
						var pdMenu = res.data.pdMenu;
						var list = res.data.list;
						var noList = res.data.noList;
						var addedStoreList = res.data.addedRulesStores;


						//可选门店
						var addFlag = false;
						var openInputs = '';
						for (var j = 0; j < list.length; j++) {
							var openStore = list[j];
							for (var k = 0; k < addedStoreList.length; k++) {
								if(openStore.STORE_ID == addedStoreList[k].store_id){
									addFlag = true;
									break;//如果存在已创建的门店，则移除
								}
							}
							if(addFlag){
								addFlag = false;
								continue;
							}
							if(openInputs.indexOf(openStore.STORE_ID) >= 0){
								continue;
							}

							openInputs += '<input type="checkbox" title="'+openStore.STORE_NAME+'" lay-skin="primary" checked lay-filter="store" name="open_store_id" value="'+openStore.STORE_ID+'">';
						}
						if(openInputs == ""){
							$("#openBox").remove();
						}else{
							openInputs += '<input type="checkbox"  title="全选" lay-skin="primary" checked lay-filter="tufure" id="allChecked">';
							$("#openStoreList").html(openInputs);
						}

						//已创建规则的门店
						var addedInputs = '';
						for (var i = 0; i < addedStoreList.length; i++) {
							addedInputs += '<input type="checkbox" title="' + addedStoreList[i].store_name + '" lay-skin="primary" disabled>';
						}
						if(addedInputs == ""){
							$("#addedBox").remove();
						}else{
							addedInputs += '<p class="openUp-action">以上门店已创建充值规则，无需重新新建</p>';
							$("#addedStoreList").html(addedInputs);
						}

						//不可选门店
						var notOpenInputs = '';
						for (var i = 0; i < noList.length; i++) {
							if(notOpenInputs.indexOf(noList[i].STORE_ID) >= 0){
								continue;
							}
							notOpenInputs += '<input type="checkbox" title="' + noList[i].STORE_NAME + '" lay-skin="primary" disabled value="'+noList[i].STORE_ID+'">';
						}
						if(notOpenInputs == ""){
							$("#notOpenBox").remove();
						}else{
							notOpenInputs += '<p class="openUp-action">以上门店因未开通计费系统或者未开通在线支付，无法实现<br>充值功能<span  onclick=siMenu("z'+pdMenu.MENU_ID+'","lm'+pdMenu.PARENT_ID+'","'+pdMenu.MENU_NAME+'","'+pdMenu.MENU_URL+'")>去开通</span></p>';
							$("#notOpenStoreList").html(notOpenInputs);
						}

						layui.form.render();
						layer.open({
							btn : [ "确定", "取消" ],
							yes : function(index, layero) {
								var length = $("input:checkbox[name='open_store_id']:checked").length;

								if(length == 0){
									message("请选择群发的门店");
									return false;
								}else{
									var nameList = '';
									$("input:checkbox[name='open_store_id']:checked").each(function (i) {
										nameList += $(this).attr("title") + " ";
									})
									$("#nameList").val(nameList);

									$("#store_form").attr("action",basePath+"rechargeRule/goAddRule.do");
									$("#store_form").submit();
									layer.close(layer.index);
								}
							},
							type : 1,
							shade : 0.6,
							title : "选择门店",
							area: ['530px', '436px'],
							content : $('#store_form'),
						})

					}else{
						message(data.errmsg);
					}

				},
				error:function(){
					message("系统繁忙，请稍后再试！");
				}
			});
	  }
		
		
		
		
layui.use(['form','layer','element'], function(){
	var form = layui.form,
		layer = layui.layer,
		element = layui.element;
	
	element.on('collapse(color)', function (data) {
            if (data.show == true) {
                $(".layui-colla-content").children().click(function () {
                    $(".layui-colla-content").removeClass("layui-show");
                    $(".layui-colla-icon").html("&#xe602;");
                    var color = $(this).attr("name");
                    $(".colla-color").addClass(color);
            		$(".colla-color").css("display","inline-block");
                    $("#new_lable_color").val(color);
                })
            }
        });
        
          
		  	  //全选监听
  	form.on('checkbox(tufure)', function(data){
        if(data.elem.checked == true){
            $(data.othis).prevAll(".layui-form-checkbox").addClass("layui-form-checked")
            $(data.othis).prevAll("input").prop("checked","checked");
        }else {
            $(data.othis).prevAll(".layui-form-checkbox").removeClass("layui-form-checked")
            $(data.othis).prevAll("input").removeAttr("checked");
        }
    });
	//复选框监听
	form.on('checkbox(store)', function(data){
		var stores = document.getElementsByName("store_id");
		for(var i=0;i<stores.length;i++){
			if(stores[i].checked == false){
				$("#allChecked").next().removeClass("layui-form-checked")
            	$("#allChecked").removeAttr("checked");
				return ;
			}
			if(i == stores.length-1){
				$("#allChecked").next().addClass("layui-form-checked")
            	$("#allChecked").prop("checked","checked");
				return ;
			}
		}
    });
})

		
		//添加模块
		function add_module(){
			var module_length = $(".module-box").children().length;
			var n = module_length;
			var i = 0;
			if(n > 0){
				var last_id = $(".module-box").children("div:last-child").attr("id");
				i = parseInt(last_id.substring(6)) + 1;
			}
			var module = '<div class="lk-payRule-module" id="module'+i+'" onclick="module_avtive('+i+')">'+
								'<h1>￥<span>0</span></h1>'+
								'<p>奖励<span>0</span>元</p>'+
								'<span class="module-active-icon"></span>'+
								'<div class="lable-append"></div>'+
								'<input type="hidden" name="moduleName'+i+'" id="moduleName'+i+'" value="0,0,kong,kong">'+
							'</div>';
			var phone_m = '<div class="layui-col-md4" id="phone_m'+i+'">'+
								'<div class="setMeal-det">'+
									'<h3>￥<span>0</span></h3>'+
									'<p>奖励<span>0</span>元</p>'+
									'<span class="active-icon"></span>'+
									'<div class="lable-append"></div>'+
								'</div>'+
							'</div> ';
			var module_id = "#module"+i;
			var phone_m_id = "#phone_m"+i;
			if(n < 9){
				$(".module-box").append(module);
				$(module_id).addClass("module-active");
				$(module_id).siblings().removeClass("module-active");
				$(".pay-nobody-box").hide();
				
				focus(module_id);
				
				$("#agreementList").append(phone_m);
				$(phone_m_id).addClass("setMeal-active");
				$(phone_m_id).siblings().removeClass("setMeal-active");
			}
			if((n+1) == 9 ){
				$(".lk-payRule-add").hide();
				message("模块已满");
			}
		}
		
		//选中效果
		function module_avtive(n){
			var module_id = "#module"+n;
			$(module_id).addClass("module-active");
			$(module_id).siblings().removeClass("module-active");
			
			focus(module_id);
			
			var phone_m_id = "#phone_m"+n;
			$(phone_m_id).addClass("setMeal-active");
			$(phone_m_id).siblings().removeClass("setMeal-active");
		}
		function focus(module_id){
			var reward_money = $(module_id).children("p").children("span").html();
			var recharege =$(module_id).children("h1").children("span").html();
			$("#recharge_money").val(recharege);
			$("#reward_money").val(reward_money);
			$("#recharge_money").focus();
			
			
		}
		
		//删除模块 
		function del_module(){
			var h = $(".module-active").html();
			if(h == undefined){
				message("请选择要删除的模块");
				return false;
			}
			layer.confirm('确定要删除', {
				btn: ['确定','取消'] 
			}, function(){
				$(".module-active").remove();
				$(".setMeal-active").remove();
				$(".lk-payRule-add").show();
				layer.closeAll();
				
				if($(".module-box").html() == "" || $(".module-box").html() == undefined){
					$(".pay-nobody-box").show();
				}
			}, function(){
				layer.closeAll()
			});
		}
		
		//为模块添加标签
		function add_lable(color,i){
			if($(".module-active").html() == undefined){
				message("请选择要添加的模块");
				return false;
			}
			var now_lable_id = "#now_lable"+i;
			var lable_name = $(now_lable_id).html();
			var spans = '<span class="lk-pay-lable '+color+'">'+lable_name+'</span>';
			$(".module-active").children(".lable-append").html(spans);
			$(".setMeal-active").children().children(".lable-append").html(spans);
			
			//将标签放入input
			var id = "#moduleName" + $(".module-active").attr("id").substring(6);
			var money = $(id).val().split(",")[0] + "," +$(id).val().split(",")[1];
			$(id).val(money+","+lable_name+","+color);
			
			
		}
		//模块删除标签 
		function del_lable(){
			var h = $(".module-active").children(".lable-append").html();
			if(h == "" ){
				message("该模块没有标签");
				return false;
			}
			if( h == undefined){
				message("请选择模块");
				return false;
			}
			$(".module-active").children(".lable-append").html("");
			$(".setMeal-active").children().children(".lable-append").html("");
			message("已删除");
			
			//将标签从input中移除
			var id = "#moduleName" + $(".module-active").attr("id").substring(6);
			var money = $(id).val().split(",")[0] + "," +$(id).val().split(",")[1];
			$(id).val(money+",kong,kong");
		}
		
		
		//填写充值金额
		var reg = /^[1-9]\d*$/;
		var regs = /^[0-9]\d*$/;
		$("#recharge_money").keyup(function(){
			if(panduan() == true){
				var recharege = $(this).val().trim();
				if(reg.test(recharege)){
					$(".module-active").children("h1").children("span").html(recharege);
					$(".setMeal-active").children().children("h3").children("span").html(recharege);
					
					//将金额放入input
					var id = "#moduleName" + $(".module-active").attr("id").substring(6);
					var label = recharege + "," +$(id).val().split(",")[1] + "," + $(id).val().split(",")[2] + "," + $(id).val().split(",")[3];
					$(id).val(label);
			
				}else {
					error("请输入大于0的整数金额");
				}
			}
		})
		//填写奖励金额
		$("#reward_money").keyup(function(){
			if(panduan() == true){
				var reward_money = $(this).val().trim();
				if(regs.test(reward_money)){
					$(".module-active").children("p").children("span").html(reward_money);
					$(".setMeal-active").children().children("p").children("span").html(reward_money);
					
					//将金额放入input
					var id = "#moduleName" + $(".module-active").attr("id").substring(6);
					var label = $(id).val().split(",")[0] + "," + reward_money + "," + $(id).val().split(",")[2] + "," + $(id).val().split(",")[3];
					$(id).val(label);
				}else {
					error("奖励金额不正确");
				}
			}
		})
		function panduan(){
			var h = $(".module-active").html()
			if(h == "" || h == undefined){
				message("请选择模块");
				$(this).val("");
				return false;
			}
			return true;
		}
		
		
		//新建新标签
		function new_lable(basePath){
			layer.open({
				btn : [ "确定", "取消" ],
				yes : function(index, layero) {
					var new_lable_name = $("#new_lable_name").val();
					var new_lable_color = $("#new_lable_color").val();
					
					if(new_lable_name.trim() == ""){
						msg_tips("标签名称不能为空","#new_lable_name")
						return false;
					}else if(new_lable_name == "kong"){
						msg_tips("请输入规范的标签名称","#new_lable_name")
						return false;
					}
					if(new_lable_color == "" || new_lable_color == undefined){
						msg_tips("请选择标签颜色",".layui-colla-title")
						return false;
					}
					$.ajax({
						type: "POST",
						url: basePath+'rechargeRule/saveLabel.do',
						data : {label_name:new_lable_name,label_color:new_lable_color},
						dataType : 'json',
						async:false,
					    success : function(data) {
							message(data.message);
							if(data.result == "true"){
								var i = $(".lk-payLable-item").children().length;
								var htmls = '<span class="'+new_lable_color+'_s" id="now_lable'+i+'" onclick=add_lable("'+new_lable_color+'","'+i+'")>'+new_lable_name+'</span>';
								var del_htmls = '<span id="del_lable'+i+'" onclick=avtive_lable("'+i+'")>'+new_lable_name+'</span>';
								
								$(".lk-payLable-item").append(htmls);
								$("#lk-delLable-item").append(del_htmls);
								$(".colla-color").removeClass(new_lable_color);
								$("#new_lable_name").val("");
								setTimeout(function(){layer.closeAll();},800)
								
							}
					    	
					    },
					    error:function(){
							message("系统繁忙，请稍后再试！");
						}
					});
				},
				type : 1,
				shade : 0.6,
				title : "添加新标签",
				area: ['460px', '380px'],
				content : $('#new-lable'),
			})
		}
		//删除创建的标签 
		function del_nowLable(basePath){
						
			if($("#lk-delLable-item").html().trim() != ""){
				layer.open({
					btn : [ "确定", "取消" ],
					yes : function(index, layero) {
						
						if($(".active_del_lable").length == 0){
							message("请选择标签");
							return false;
						}
						var list = [];
						var nameList = '';
						$(".active_del_lable").each(function(i){
							var otihs = $(".active_del_lable")[i];
							
							list.push($(otihs).attr("id").substring(9));
							nameList += $(otihs).html() + ","
						})
						nameList.substring(0,nameList.length-1);
						$.ajax({
							type: "POST",
							url: basePath+'rechargeRule/delLabel.do',
							data : {nameList:nameList},
							dataType : 'json',
							async:false,
							success : function(data) {
								message(data.message);
								if(data.result == "true"){
									//删除标签
									$.each(list,function(i){
										var remove_id = "#now_lable"+list[i];
										$(remove_id).remove();
										
										var del_lable_id = "#del_lable"+list[i];
										$(del_lable_id).remove();
									})
									setTimeout(function(){layer.closeAll();},800)
									
								}
							},
							error:function(){
								message("系统繁忙，请稍后再试！");
							}
						});
					},
					type : 1,
					shade : 0.6,
					title : "删除标签",
					area: ['360px', '280px'],
					content : $('#lk-delLable-item'),
				})
			}else{
				message("没有可删除标签");
			}
		}
		//选中要删除的标签
		function avtive_lable(i){
			var active_id = "#del_lable"+i;
			$(active_id).toggleClass("active_del_lable");
		}
		
		//保存规则
		function saveRule(basePath){
			
			var module_length = $(".module-box").children().length;
			if(module_length > 0){
				var last_id = $(".module-box").children("div:last-child").attr("id");
				$("#max_module").val(last_id.substring(6));
			}else{
				message("请先添加模块");
				return false;
			}
			
			if(!checkModule()){
				return false;
			}else{
				layer.confirm('确定要保存发布吗?', {
					btn: ['确定','取消'],
				}, function(index,layero){
					$.ajax({
						type: "POST",
						url: basePath+'rechargeRule/saveRule.do',
						data : $('#rule_form').serialize(),
						dataType : 'json',
						async:false,
						success : function(data) {
							message(data.message);
							if(data.result == "false"){
								if(data.flag == "money"){
									module_avtive(data.sequence);
									return false;
								}
							}else{
								setTimeout(function(){opener.location.reload();},800);
							}
						},
						error:function(){
							message("系统繁忙，请稍后再试！");
						}
					});
				 }, function(){
					 return
				 });
			}
		}
		//检查模块数据是否正常
		function checkModule(){
			var flag = true;
			$(".lk-payRule-module").each(function (i) {
				var recharge = $(this).children("h1").children("span").html();
				var reward_money = $(this).children("p").children("span").html();
				
				var i = $(this).attr("id").substring(6);
				if(recharge <= 0){
					message("请输入正确的充值金额");
					module_avtive(i);
					flag = false;
				}
				
				if(reward_money < 0){
					message("请输入正确的奖励金额");
					module_avtive(i);
					flag = false;
				}
				
			});
			return flag;
		}

		//编辑门店的充值规则
		function editDetail(basePath,store_id){
			$("#store_id").val(store_id);
			$("#edit_form").attr("action", basePath+"rechargeRule/editDetail.do");
			$("#edit_form").submit();
			
		}
		//查看门店的充值规则详情信息
		function showDetail(basePath,store_id){
			var url = basePath+'rechargeRule/showDetail.do?store_id='+store_id;
			
			$.ajax({
				type: "POST",
				url: url,
				data : {store_id:store_id},
				dataType : 'json',
				async:false,
				success : function(data) {
					if(data.result == "true"){
						var ruleList = data.ruleList;
						var rules = '';
						for(var i=0;i<ruleList.length;i++){
							var label_name = '';
							if(ruleList[i].label_name != null){
								label_name = '<span class="lk-pay-lable '+ruleList[i].label_color+'">'+ruleList[i].label_name+'</span>';
							}
							rules += '<div class="layui-col-md4" id="phone_m'+i+'">'+
										'<div class="setMeal-det">'+
											'<h3>￥<span>'+ruleList[i].recharge_money+'</span></h3>'+
											'<p>奖励<span>'+ruleList[i].reward_money+'</span>元</p>'+
											'<span class="active-icon"></span>'+
											'<div class="lable-append">'+label_name+'</div>'+
										'</div>'+
									'</div>';
						}
						var display = '';
						if(rules != ''){
							display = 'style="display:none;"';
						}
						var htmls = '<div class="pay-left-phone" style="margin-right:0;border: none;" id="show_phone">'+
							'<div class="pay-left-content">'+
								'<div class="lk-payHeader-box">'+
									'<div class="lk-pay-header">'+
										'<div id="lk-pay-userbox">'+
											'<div class="lk-pay-bor">'+
												'<div class="lk-pay-bg">'+
													'<h1 id="CARDED"><i class="iconfont">&#xe620;</i>4304221997898900XX</h1>'+
													'<p>当前绑定门店：'+data.nameList+'</p>'+
												'</div>'+
											'</div>'+
										'</div>'+
									'</div>'+
									'<div class="bindIocn"><i class="iconfont">&#xe628;</i></div>'+
								'</div>'+
								'<div class="lk-pay-setMeal lk-pay-bor">'+
									'<h1>充值金额</h1>'+
									'<div class="layui-row layui-col-space8" id="agreementList">'+
									rules+
									'</div>'+
									'<div class=""></div>'+
								'</div>'+
								'<div class="pay-btnBox">'+
									'<div class="lk-pay-bor" >'+
										'<p class="weic-pay-agreement">'+
											'<span id="agreement-icon" class=""><i class="iconfont">&#xe6c1;</i></span>'+
											'已阅读并同意<a herf="">《用户充值协议》</a>'+
										'</p>'+
										'<div class="weic-dent-btn weic-btn-gradientBlue" onclick="goPay_details()">立即充值</div>'+
									'</div>'+
								'</div>'+
								'<div class="pay-nobody-box" '+display+'>'+
									'<div class="pay-nobody-img"></div>'+
									'<p>老板太懒了，没设置充值套餐</p>'+
								'</div>'+
							'</div>'+
						'</div>'
						
						layer.open({
							type: 1,
							title: false, //不显示标题
							area: ['320px', '628px'],
							skin: 'layui-layer-nobg',
							content: htmls, 
							shadeClose: true,
							closeBtn: 0,
							move:"#show_phone",
							shade:0.6
						});
					}else{
						message(data.message);
						setTimeout(function(){location.reload();},800);
					}
				},
				error:function(){
					message("系统繁忙，请稍后再试！");
				}
			});
			
			
		}
		
		//删除门店的充值规则
		function delRules(basePath,store_id){
			layer.confirm('确定要该门店的所有规则吗?', {
				btn: ['确定','取消'],
			}, function(index,layero){
				$.ajax({
					type: "POST",
					url: basePath+'rechargeRule/delRules.do',
					data : {store_id:store_id},
					dataType : 'json',
					async:false,
					success : function(data) {
						message(data.message);
						if(data.result == "true"){
							setTimeout(function(){location.reload();},800);
						}
					},
					error:function(){
						message("系统繁忙，请稍后再试！");
					}
				});
			 }, function(){
				 return
			 });
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		