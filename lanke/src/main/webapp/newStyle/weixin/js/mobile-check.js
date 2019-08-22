
var msg = {
    store_id:function (obj) {
        if(obj.val() == ""){
            this.erroralert(obj,"请选择分店");
        }else {
            this.success(obj);
        }
    },
    name:function (obj,i) {
        var text = "";
        var name = obj.attr("name");
        if(name == 'team_name'){
            text = "请输入战队名称"
        }else if(name == "header_name"){
            text = "请输入队长姓名"
        }else if(name == "member_name"){
            text = '请输入第'+i+'位队员姓名'
        }else if(name == "name"){
            text = "请输入姓名"
        }
        if(obj.val() == ""){
            this.erroralert(obj,text);
        }else {
            this.success(obj);
        }
    },
    team_msg:function (obj) {
        if(obj.val() == ""){
            this.erroralert(obj,"请输入战队简介");
        }else{
            this.success(obj);
        }
    },
    sfz:function (obj){
        if(obj.val() == ""){
            this.erroralert(obj,"请输入会员卡号");
        }else{
            // var myReg = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
            // if (!myReg.test(obj.val())) {
            //     this.erroralert(obj,"会员卡号格式不正确");
            // }else {
            //     this.success(obj);
            // }
            this.success(obj);
        }
    },
    phone:function (obj,i) {
        var text = "";
        var error_text = "";
        var name = obj.attr("name");
        if(name == "header_phone"){
            text = "请输入队长手机号";
            error_text = "队长手机格式不正确";
        }else if(name == "member_phone"){
            text = '请输入第'+i+'位队员手机号';
            error_text = '第'+i+'位队员手机格式不正确';
        }else if(name == "phone"){
            text = "请输入手机号";
            error_text = "手机号格式不正确";
        }
        if(obj.val() == "") {
            this.erroralert(obj,text);
        }else {
            var phoneReg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
            if(!phoneReg.test(obj.val())){
                this.erroralert(obj,error_text);
            }else {
                this.success(obj);
            }
        }
    },
    code:function (obj) {
        if (obj.val() == ""){
            this.erroralert(obj,"请输入验证码");
        }else {
            this.success(obj);
        }
    },
    success: function (obj) {
        obj.data('group-state', true);
    },
    erroralert: function (obj, text){
        obj.data('group-state', false);
        message(text);
    },
    allright: true,
    checkstate: function (){ //检查所有状态
        msg.allright = true;
        $.each($('*[data-group-state]'), function(item){
            eval($(this).data('isblur'));
            if (!$(this).data('group-state')){
                msg.allright = false;
                return false;
            }
        })
        return msg.allright;
    },
}

var getcode = {
    wait: 60,
    ajax: function (o) {
        var obj = $('#phone');
        var phone = obj.val();
        msg.phone(obj);
        var _this = this;
        if (obj.data('group-state')){
          $.ajax({
              url: o.data('url'),
              type: 'post',
              dataType: "json",
              data: {phone: phone},
              success: function (data) {
                  message(data.result);
                  _this.time(o);
              },
              error: function (){
                  message('网络连接失败');
              }
          });
        }
    },
    time: function (o) {
        if (this.wait == 0) {
            o.removeClass('btn-disabled');
            o.text('获取验证码')
            this.wait = 60;
        } else {
            o.addClass('btn-disabled');
            o.text('重新发送(' + this.wait + ')');
            this.wait--;
            var _this = this;
            setTimeout(function () {
                _this.time(o)
            }, 1000)
        }
    }
}


$(function () {
    $("#getcode").click(function () {
        if(!$(this).hasClass("btn-disabled")){
            getcode.ajax($(this))
        }
    })
})

//提交绑定
function bindSave(url){
    window.scrollTo(0,0);
    if( msg.checkstate()){//检查所有状态
        $("#save").attr("onclick"," ");
        var backurl = $("#backurl").val()
        $.ajax({
            type: "POST",
            url: url,
            data: $("#form").serialize(),
            dataType: 'json',
            beforeSend:beforeSend,
            success: function(data){
                layer.closeAll();
                $("#save").attr("onclick","bindSave('"+url+"');");
                if(data.message == "success"){
                    var html = '<i class="iconfont lk-success-icon">&#xe66a;</i><br>'+data.result;
                    open_oneBtn(html,'我知道了',function(){
                        window.location.href = backurl;
                    })
                }else if(data.message = "false"){
                    message(data.result);
                }
            },
            error: function(){
                layer.closeAll();
                message("系统繁忙，请稍后再试");
                $("#save").attr("onclick","bindSave('"+url+"');");
            }
        });
    }
}

function beforeSend(){
    open_loading("加载中")
}
//取门店数据
var storeList = "",cityArray = [],areaArray = [],wkArray = [];

function getStore(url){
    $.ajax({
        type: "POST",
        url: url,
        dataType: 'json',
        success: function (data){
            storeList = data.cityList;
            for (var i = 0; i < storeList.length; i++){
                cityArray.push(storeList[i].CITY);
                areaArray.push(storeList[i].areaList)
            }
            //第一次拿到所有城市
            var cityDiv = "";
            for (var i = 0; i < cityArray.length; i++){
                cityDiv += '<p onclick="citySelect(this)">'+cityArray[i]+'</p>'
            }
            $("#changeCity-list").html(cityDiv)
            showData("city",cityArray[0]); //初始数据
        },
    });
}

//每次拿门店数据
function showData(type, name){
    var areaList = [];
    var wkList = [];
    if(type == "city"){  // 点击城市拿到所有区域和所有网吧
        var f = 0;
        var index = $.inArray(name,cityArray); //找到城市下标
        for (var i = 0; i < areaArray[index].length; i++){  //循环对应下标的区域列表
            areaList[i] = areaArray[index][i].COUNTY;  //  查出所有区域
            for (var j = 0; j < areaArray[index][i].storeList.length; j++){ //循环对应下标的区域列表的网咖列表
                wkList[f] = areaArray[index][i].storeList[j];  //  查出所有网吧列表
                f++;
            }
        }
        // 赋值区域
        var  areaDiv = '<p onclick="showCity()">切换城市</p>'+'<p onclick=areaSelect(this,"city") class="wkCity-active">全'+name+'</p>'
        for (var s = 0; s < areaList.length; s++) {
            areaDiv += '<p onclick="areaSelect(this)">'+areaList[s]+'</p>'
        }
        $("#changeArea-list").html(areaDiv);
        // 赋值网吧
        changeWk(wkList)
    }else if(type == "area"){ // 点击区域拿到该区域下的所有网吧
        var num = '',sub="";
        for (var i = 0; i < areaArray.length; i++){ // 循环区域数组
            for (var j = 0; j < areaArray[i].length; j++){  // 循环区域列表
                if(areaArray[i][j].COUNTY == name){ //匹配区域名称
                    num = i;  //返回区域数组下标
                    sub = j; //返回区域数组的网吧列表下标
                    break;
                }
            }
        }
        wkList = areaArray[num][sub].storeList; //查到该区域下的所有网吧
        // 赋值网吧
        changeWk(wkList)
    }
}

function changeWk(wkList){
    var wkDiv = "";
    for (var w = 0; w < wkList.length; w++){
        wkDiv += ' <div class="wk-item" onclick="wkSelect(this)" data-id='+wkList[w].STORE_ID+'><p>'+wkList[w].STORE_NAME+'</p><i></i></div>'
    }
    $("#changeWk-list").html(wkDiv);
}


//显示门店list
function showStore(){
    if(cityArray.length > 0 ){
        $("#weic-bindWk").attr("class","show");
    }else {
        message("该网咖暂无门店可绑")
    }
}
//关闭门店list
function closeStore(){
    $("#weic-bindWk").attr("class","hide");
}
//选择区域
function areaSelect(obj,type){
    $(obj).addClass("wkCity-active");
    $(obj).siblings().removeClass("wkCity-active");
    if(type == "city"){
        showData("city",$(obj).text().substr(1))
    }else{
        showData("area",$(obj).text());
    }
}
//选择网吧
function wkSelect(obj){
    $(obj).addClass("wk-item-active");
    $(obj).siblings().removeClass("wk-item-active");
    hideCity();
    var wkName = $(obj).children("p").text();
    $("#STORE_ID").val($(obj).data("id"));
    $("#storeName").val(wkName);
    closeStore();
}
//显示城市
function showCity(){
    $("#changeCity-list").removeClass("changeCity-hide");
    $("#changeCity-list").addClass("changeCity-show");
}
//隐藏城市
function hideCity(){
    $("#changeCity-list").removeClass("changeCity-show");
    $("#changeCity-list").addClass("changeCity-hide");
}
//选择城市
function citySelect(obj){
    $(obj).addClass("wkCity-active");
    $(obj).siblings().removeClass("wkCity-active");
    hideCity();
    showData("city",$(obj).text())
}
