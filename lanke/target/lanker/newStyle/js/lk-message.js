

	//黄色感叹号警告提醒
	function remind(msg) {
		layer.msg(msg, {
			time : msg.length*200,
			icon : 0
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
			icon : 2
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
			  ,time:100000
			});
		}
	}
	
	function message(msg){
		layer.msg(msg,{
			time: msg.length*200
		});
	}
	
	function beforeSend(msg){
		loading(msg);
	}
	
	
	function msg_tips(msg,element) {
		layer.tips(msg, element, {
				tips: 3
		});
	}
	