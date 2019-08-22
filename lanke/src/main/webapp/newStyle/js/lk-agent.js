// 页面校正
function agent_check() {
	var company_name = $("#company_name").val();
	var s_province = $("#s_province").val();
	var company_address = $("#company_address").val();
	var corporate_name = $("#corporate_name").val();
	var corporate_id = $("#corporate_id").val();
	var credit_number = $("#credit_number").val();
	var contacts_name = $("#contacts_name").val();
	var phone = $("#phone").val();
	var agent_number = $("#agent_number").val();
	if (company_name == "") {
		layer.tips('请填写企业名称', '#company_name', {
			tips : 3
		});
		$("#company_name").focus();
		return false;
	}
	if (s_province == "") {
		layer.tips('请选择企业地址', '#s_province', {
			tips : 3
		});
		return false;
	}
	if (company_address == "") {
		layer.tips('请填写详细地址', '#company_address', {
			tips : 3
		});
		$("#company_address").focus();
		return false;
	}
	if (corporate_name == "") {
		layer.tips('请填写法人姓名', '#corporate_name', {
			tips : 3
		});
		$("#corporate_name").focus();
		return false;
	}
	if (corporate_id == "") {
		layer.tips('请填写法人身份证', '#corporate_id', {
			tips : 3
		});
		$("#corporate_id").focus();
		return false;
	}
	if (corporate_id != "" && !(/^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$/.test(corporate_id))) {
		layer.tips('身份证格式错误', '#corporate_id', {
			tips : 3
		});
		$("#corporate_id").focus();
		return false;
	}
	if (credit_number == "") {
		layer.tips('请填写统一社会信用代码证编号', '#credit_number', {
			tips : 3
		});
		$("#credit_number").focus();
		return false;
	}
	if (credit_number != "" && !(/[1-9A-GY]{1}[1239]{1}[1-5]{1}[0-9]{5}[0-9A-Z]{10}/.test(credit_number))) {
		layer.tips('信用代码证号格式错误', '#credit_number', {
			tips : 3
		});
		$("#credit_number").focus();
		return false;
	}
	if (contacts_name == "") {
		layer.tips('请填写联系人', '#contacts_name', {
			tips : 3
		});
		$("#contacts_name").focus();
		return false;
	}
	if (phone == "") {
		layer.tips('请填写手机号码', '#phone', {
			tips : 3
		});
		$("#phone").focus();
		return false;
	}
	if (phone != "" && !(/^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\d{8}$/.test(phone))) {
		layer.tips('手机格式错误', '#phone', {
			tips : 3
		});
		$("#phone").focus();
		return false;
	}
	if (agent_number == "") {
		layer.tips('请填写代理商编号', '#agent_number', {
			tips : 3
		});
		$("#agent_number").focus();
		return false;
	}
	if (agent_number != "" && !(/^\d{8}$/.test(agent_number))) {
		layer.tips('代理商编号格式错误', '#agent_number', {
			tips : 3
		});
		$("#agent_number").focus();
		return false;
	}
	if ($("#img1").html().trim() == "") {
		layer.tips('请上传营业执照副本', '#img1', {
			tips : 3
		});
		return false;
	}
	if ($("#img2").html().trim()  == "") {
		layer.tips('请上传法人身份证正面', '#img2', {
			tips : 3
		});
		return false;
	}
	if ($("#img3").html().trim()  == "") {
		layer.tips('请上传法人身份证反面', '#img3', {
			tips : 3
		});
		return false;
	}
	return true;
}

// 服务器校正保存方法
function edit(state) {
	if (!agent_check()) {
		return false;
	}
	$.ajax({
		async : false,
		type : "POST",
		url : getRealPath() + '/agent/edit.do',
		data : $('#Form').serialize(),
		dataType : 'json',
		beforeSend : loading("提交中"),
		success : function(data) {
			layer.closeAll();
			layer.msg(data.message, {
				time : data.message.length * 200
			}, function() {
				if ("success" == data.result) {
					setTimeout(function() {
						// 关闭当前页面
						window.opener = null;
						window.open("", "_self");
						window.close();
					}, 700);
					opener.location.reload();// 刷新父页面
				}
				if ("fail" == data.result) {
					$("#agent_number").focus();
				}
			});
		},
		error : function() {
			layer.closeAll();
			layer.msg("系统繁忙，请稍后再试！");
		}
	});
}

var htmls = '点击上传';
// 鼠标移入操作
var mouseover = {
	agent_business:function(othis){
		var pp = '<p class="lk-pay-upTip">请上传当前代理商的营业执照副本（年检有效期内），原件照片或者扫描件</p>'
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
	}
}
// 鼠标移出操作
var mouseout = {
	agent_business:function(othis){
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
	}
}
// 鼠标点击操作
var active = {
	agent_business:function(divs,i){
		$("#img1").html(divs);
	},
	legal_front:function(divs,i){
		$("#img2").html(divs);
		$(".pay-imgBox").css("height","130px") 
	},
	legal_side:function(divs,i){
		$("#img3").html(divs);
		$(".pay-imgBox").css("height","130px")
	}
}

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
		   // document.getElementById('img').src =
			// canvas.toDataURL("image/png");
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
	
	 // 删除图片
	function delImg(ii){
			var id = "#div"+ii;
			layer.confirm('确定要删除吗?', {
				btn: ['确定','取消'],
			}, function(index,layero){
				$(id).remove();
				$(".pay-imgBox").css("height","auto") 
				$("#file").val("");
				layer.close(index)
			 }, function(){
				 return
			 });
	}
	// 查看图片
	function seeImg(src2){
		layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  area: '600px',
			  skin: 'layui-layer-nobg', // 没有背景色
			  shadeClose: true,
			  move: 'img',
			  moveOut: true,
			  content: '<img src="'+src2+'" width="100%" />'
		});
	}
    
