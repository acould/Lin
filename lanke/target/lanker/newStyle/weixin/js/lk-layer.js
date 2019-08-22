

//msg提示框
function message(msg) {
	layer.open({
        content: msg
        ,className: 'msg'
        ,shade: false
        ,time: msg.length*0.25 //2秒后自动关闭
    });
}

//单个按钮的信息框
function open_oneBtn(msg,btn,callback){
	layer.open({
		  content: msg,
		  btn: btn,
		  className: 'lk-onebtn',
		  yes: callback
	});
}
//可传class的单按钮弹层
function open_oneBtns(msg,btn,className,callback){
	layer.open({
		  content: msg,
		  btn: btn,
		  className: className,
		  yes: callback,
		  shadeClose:false
	});
}

//loading弹层
function open_loading(msg){
	var className = '';
	if(msg == "" || msg == undefined){
		className = 'load notext'
	}else {
		className = 'load'
	}
	layer.open({
         type: 2
         ,shade: false
         ,className: className
        ,content: msg
    });
}
//询问层
function confirm(msg,callback){
	layer.open({
            content: msg
            ,btn: ['确定', '取消']
            ,className: 'confirm'
            ,yes: callback
     });
}

//通过类型获取图片url
function getTypeImg(FAV_TYPE) {
    var imgUrl = '';
    switch (FAV_TYPE) {
        case 'CURREN':
            imgUrl = basePath+'newStyle/weixin/images/card/img_9.png';
            break;
        case 'NEW':
            imgUrl = basePath+'newStyle/weixin/images/card/img_4.png';
            break;
        case 'OLD':
            imgUrl = basePath+'newStyle/weixin/images/card/img_6.png';
            break;
        case 'MAN':
            imgUrl = basePath+'newStyle/weixin/images/card/img_5.png';
            break;
        case 'WEM':
            imgUrl = basePath+'newStyle/weixin/images/card/img_1.png';
            break;
        case 'BIRTH':
            imgUrl = basePath+'newStyle/weixin/images/card/img_7.png';
            break;
        case 'GRAB':
            imgUrl = basePath+'newStyle/weixin/images/card/img_8.png';
            break;
        case 'APPLY':
            imgUrl = basePath+'newStyle/weixin/images/card/img_3.png';
            break;
        case 'TERM':
            imgUrl = basePath+'newStyle/weixin/images/card/img_2.png';
            break;
    }
    return imgUrl;
}