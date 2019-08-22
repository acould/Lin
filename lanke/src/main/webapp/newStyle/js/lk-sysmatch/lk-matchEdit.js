
function uploadImg(){
    var file = document.getElementById("file").files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e){
        var src = e.target.result;
        loading("上传中")
        render(src);
    }
}
var MAX_HEIGHT = 1000;
function render(src){
    var image = new Image();
    image.onload = function(){
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
        var blob = canvas.toDataURL("image/jpeg");
        layer.closeAll();
        $("#picture_img").attr("style",'background:url('+blob+') no-repeat center center;background-size:100%;');
        $("#picture_url").val(blob);
        $("#cover").val(blob);

        success("上传成功");
    }
    image.src = src;
}

// textarea 自适应高度
var textarea = document.getElementById("name");
makeExpandingArea(textarea);

function makeExpandingArea(el) {
    var setStyle = function(el) {
        el.style.height = 'auto';
        el.style.height = el.scrollHeight + 'px';
        // console.log(el.scrollHeight);
    }
    var delayedResize = function(el) {
        window.setTimeout(function() {
                setStyle(el)
            },
            0);
    }
    if (el.addEventListener) {
        el.addEventListener('input', function() {
            setStyle(el)
        }, false);
        setStyle(el)
    }else if (el.attachEvent) {
        el.attachEvent('onpropertychange', function() {
            setStyle(el)
        });
        setStyle(el)
    }
    if (window.VBArray && window.addEventListener) { //IE9
        el.attachEvent("onkeydown", function() {
            var key = window.event.keyCode;
            if (key == 8 || key == 46) delayedResize(el);

        });
        el.attachEvent("oncut", function() {
            delayedResize(el);
        }); //处理粘贴
    }
}
// 自动展开textarea
function autosize(obj) {
    var el = obj;
    setTimeout(function(){
        el.style.cssText = 'height:auto; padding:0';
        // for box-sizing other than "content-box" use:
        // el.style.cssText = '-moz-box-sizing:content-box';
        el.style.cssText = 'height:' + el.scrollHeight + 'px';
    }, 0);
}
