
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

//合成图片
var base64Arr = [];
var number = 0;
var str = ""
    ,zip = new JSZip()
    ,img = zip.folder("images")
    ,str_code = ''
    ,zip_code = new JSZip()
    ,img_code = zip_code.folder("images")

var bg = getRealPath()+"/newStyle/images/QR_code.jpg";
function drawAndShareImage(texts,code_url,num){
    var canvas = document.createElement("canvas");
    canvas.width = 700;
    canvas.height = 1075;
    var context = canvas.getContext("2d");

    context.rect(0 , 0 , canvas.width , canvas.height);
    context.fillStyle = "#fff";
    context.fill();
    var myImage = new Image();
    myImage.src = bg;    //背景图片  你自己本地的图片或者在线图片
    myImage.crossOrigin = 'Anonymous';

    myImage.onload = function(){
        context.drawImage(myImage , 0 , 0 , 700 , 1075);
        context.font = "normal bold 142px sans-serif";
        context.textAlign="center";
        context.fillText(texts,350,235);

        var myImage2 = new Image();
        myImage2.src = code_url;   //你自己本地的图片或者在线图片
        myImage2.crossOrigin = 'Anonymous';

        myImage2.onload = function(){
            context.drawImage(myImage2 , 222 , 326 , 252 , 252);
            var base64 = canvas.toDataURL("image/png");  //"image/png" 这里注意一下
            if(num == "more"){
                // base64Arr.push(base64);
                //图片打包
                str = base64.toString();
                img.file(texts+'.jpg', str.substring(22), {base64: true});
                //仅二维码打包
                str_code = code_url.toString();
                img_code.file(texts+'.jpg', str_code.substring(22), {base64: true});
                number += 1
            }
            if(num == "one"){
            //单个下载图片
            var triggerDownload = $("<a>").attr("href", base64).attr("download", texts + ".png").appendTo("body");
            triggerDownload[0].click();
            triggerDownload.remove();
            }
        }
    }
}

//图片下载
function packageImages(status,length){
    // var str = "";
    // var zip = new JSZip();
    // //zip.file("readme.txt", "案件详情资料\n");
    // var img = zip.folder("images");
    // for (var i = 0; i < base64Arr.length; i++) {
    //     str = base64Arr[i].toString();
    //     img.file((i+1)+'.jpg', str.substring(22), {base64: true});
    // }
    layer.msg('图片下载中...', {
        icon: 16
        ,shade: 0.01
    });
    if (number == length){
        if(status == 1){
            zip.generateAsync({type:"blob"}).then(function(content) {
                // see FileSaver.js
                saveAs(content, "二维码.zip");
            });
        }else {
            zip_code.generateAsync({type:"blob"}).then(function(content) {
                // see FileSaver.js
                saveAs(content, "仅二维码.zip");
            });
        }
        layer.closeAll()
    }else {
        setTimeout(function (){
            packageImages(status,length)
        },500)
    }
}

