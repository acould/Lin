		
		layui.use('layer',function(){
  			var $ = layui.jquery
  			,layer = layui.layer;
  				  		 
		});
		
		//图片上传
		var imgarr = new Array();	
		var pathList = $("#upImg").find("input[type='hidden']");
			for(var i=0;i<pathList.length;i++){
			   imgarr.push({"pic":pathList[i].value});
			}
	    function uploadImg(){
	    	var d = "file";
			var s = document.getElementById(d).files;
	    	var arrnum = imgarr.length;
	    	var num = arrnum+s.length;
	    	if(num > 6){
	    		layer.msg("最多可以只能上传6张图片")
	    		$("#file").val("");
	    		return false
	    	}
			var seq = $("#upImg").children().length+1;
	    	for(var i=0;i<s.length;i++){
	    		var file = s[i]
	    		 if (!/image\/\w+/.test(file.type)){  
	                 layer.msg("请确保文件为图像类型");  
	                 return false;  
	             }
	    		var reader = new FileReader()
	    		reader.readAsDataURL(file);  
	    		loading("图片上传中")
	   			reader.onload = function(e){
	   			 	var srces = e.target.result;
	   				render(srces,seq);
					seq++;
	   			}
	    	}
	    }

	    //设置压缩图片的最大高度
	    var MAX_HEIGHT = 1000;
		function render(src,i){
		   // 创建一个 Image 对象
		   var image = new Image();
		   
		   // 绑定 load 事件处理器，加载完成后执行
		   image.onload = function(){
		       // 获取 canvas DOM 对象
		       var canvas = document.createElement("canvas");
		       // 如果高度超标
		       if (image.height > MAX_HEIGHT && image.height >= image.width) {
		           // 宽度等比例缩放 *=
		           image.width *= MAX_HEIGHT / image.height;
		           image.height = MAX_HEIGHT;
		       }
		      //考录到用户上传的有可能是横屏图片同样过滤下宽度的图片。
		       if (image.width > MAX_HEIGHT && image.width > image.height) {
		           // 宽度等比例缩放 *=
		           image.height *= MAX_HEIGHT / image.width;
		           image.width = MAX_HEIGHT;
		       }
		       // 获取 canvas的 2d 画布对象,
		       var ctx = canvas.getContext("2d");
		       // canvas清屏，并设置为上面宽高
		       ctx.clearRect(0, 0, canvas.width, canvas.height);
		       // 重置canvas宽高
		       canvas.width = image.width;
		       canvas.height = image.height;
		       // 将图像绘制到canvas上
		       ctx.drawImage(image, 0, 0, image.width, image.height);
		       // !!! 注意，image 没有加入到 dom之中
			   // document.getElementById('img').src = canvas.toDataURL("image/png");
		       var blob = canvas.toDataURL("image/jpeg");
				//将转换结果放在要上传的图片数组里
		       imgarr.push({"pic":blob});
				//提交给后台
		       $.ajax({
			       	type	: "POST",
			       	url		:getRealPath()+'/storeShow/uploadImg',
			       	data	:{baseImg:blob},
			       	async	:false,
			       	dataType:'json',
			       	success: function(data) {
			       		layer.close(layer.index);
			       		if(data.result == "true"){
			       			var str = '<div class="lk-store-upload" id="div'+i+'" style="background: url('+data.url+') no-repeat center center;background-size: 200%;">'+
							   				 '<div class="img-operate" >'+
								   			 	 '<p style="margin: 20px 0 10px 0;" onclick="delImg('+i+')">删除</p>'+
								   			 	 '<p onclick=seeImg("'+data.url+'")>查看</p>'+ 
							   			 	 '</div>'+
							   			 	'<input type="hidden"  value="'+data.path+'">'+
						   			  '</div>';
								$("#upImg").append(str);
			          			$("#file").val("");
			       		}
			       	},
			       	error:function(XMLHttpRequest, textStatus, errorThrown){
			       		layer.close(layer.index);
			       		layer.msg("系统繁忙，请稍后再试！",{time:1500});
			       		return;
			       	 }
			       });
		   };
		   image.src = src;
		   
		}
		
		//删除图片
		function delImg(ii){
			var id = "#div"+ii;
			parent.layer.confirm('确定要删除吗?', {
                btn: ['确定','取消'],
            }, function(index,layero){
            	$(id).remove();
    			$("#file").val("");
    			imgarr.pop()
    			parent.layer.close(index)
             }, function(){
                 return
			 });
		}
		//查看图片
		function seeImg(src2){
			parent.layer.open({
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

		
		
		//页面校正
		function check(){
			var STORE_NAME = $("#STORE_NAME").val();
			var s_province = $("#s_province").val();
			var ADDRESS = $("#ADDRESS").val();
			var TELEPHONE = $("#TELEPHONE").val();
			var imgLength = $(".lk-store-upload").length;
			console.log(imgLength)
			if(STORE_NAME == ""){
				layer.tips('请填写门店名称', '#STORE_NAME', {
    				tips: 3
    			});
    			$("#STORE_NAME").focus();
    			return false;
			}else{
				if(s_province != "" || ADDRESS != "" || TELEPHONE != "" || imgLength >= 1){
					if(s_province == ""){
						layer.tips('请选择门店所在区域', '#s_province', {
    						tips: 3
    					});
    					return false;
					}
					if(ADDRESS == ""){
						layer.tips('请填写门店详情地址', '#ADDRESS', {
    						tips: 3
    					});
    					$("#ADDRESS").focus();
    					return false;
					}
					if(TELEPHONE == ""){
						layer.tips('请填写门店电话', '#TELEPHONE', {
    						tips: 3
    					});
    					return false;
					}else{
						var myReg = /^0\d{2,3}-?\d{7,8}|(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
			 			if(!myReg.test(TELEPHONE)){
			 				layer.tips('电话格式错误,座机请加"区号-"', '#TELEPHONE', {
    							tips: 3
    						});
							$("#TELEPHONE").focus();
							return false;
						}
					}
					if(imgLength < 1){
						layer.tips('至少要有一张图片', '#testBtn', {
    						tips: 3
    					});
    					return false;
					}
				}
			}
			
			var pathList = $("#upImg").find("input[type='hidden']");
			var imgList = '';
			for(var i=0;i<pathList.length;i++){
				imgList += pathList[i].value;
				if(i != pathList.length-1){
					imgList += ",";
				}
			}
			$("#imgList").val(imgList);
			return true;
		}
		//服务器校正保存方法
		function store_submit(){
			if(!check()){
				return false;
			}
			var luck = false;
			$.ajax({
				async:false,
				type	: "POST",
				url		:getRealPath()+'/storeShow/addStore.do',
				data	:$('#Form').serialize(),
				dataType:'json',
				beforeSend: loading("提交中"),
				success: function(data) {
					layer.close(layer.index);
					console.log(data.result)
					if(data.result == "error"){
						layer.tips('此门店存在！', '#STORE_NAME', {
	   						tips: 3
	   					});
	   					$("#STORE_NAME").focus();
					 }
					if(data.result == "success"){ //修改
						layer.msg("修改成功",{icon:1,time:500},function(){
	                		layer.closeAll()
							parent.location.reload();
	                	})					
					}else if (data.result == "true"){ //新增
						luck = true;
					}
					if(data.result == "false"){
						alert(data.message);			
					}
				},
				error:function(){
					layer.close(layer.index);
					layer.msg("系统繁忙，请稍后再试！",{time:1500});
				}
			});
			if(luck){
				return luck;
			}
		}