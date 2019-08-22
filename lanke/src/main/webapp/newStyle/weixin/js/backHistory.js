

$(function(){
    pushHistory(backHistoryUrl);
    var bool=false;
    setTimeout(function(){
        bool=true;
    },100);
    window.addEventListener("popstate", function(e) {
        if(bool){
            window.location.href= backHistoryUrl;
        }
    }, false);
});

function pushHistory(backHistoryUrl) {
    var state = {
        title: "myCenter",
        url: backHistoryUrl
    };
    window.history.pushState(state, state.title, state.url);
}