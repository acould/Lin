
var isChecked = 0
//检查用户绑定数据是否正确
function checkSWUser(type) {
    if(type == null || type == ""){
        return false;
    }
    // var time = new Date().getTime();
    // open_loading("");
    // $.post('/intenetmumber/checkSWUser?type='+type+'&time='+time, '', function (res) {
    //     layer.closeAll();
    //     if(res.errcode != 0){
    //         isChecked = 1;
    //         window.location.href = "/intenetmumber/goBindingError?backurl="+res.data.backurl;
    //     }
    // })
    isChecked = 0;
}
function pushHistory2(url){
    if(url == null || url == ''){
        url = '/intenetmumber/index';
    }
    var state = {
        title: "index",
        url: url
    };
    window.history.pushState(state, state.title, state.url);
}
function popstate2(url) {
    if(url == null || url == ''){
        url = '/intenetmumber/index';
    }

    var bool=false;
    setTimeout(function(){
        bool=true;
    },50);
    window.addEventListener("popstate", function(e) {
        if(bool){
            if(isChecked == 0){
            window.location.href= url;
            }else if(isChecked == 1){
                history.go(-2)
            }
        }
    }, false);
}