
$("#all_store").click(function () {
    if($(this).hasClass("allActive")){
        $(this).removeClass("allActive");
        $(this).parent().children("div").removeClass("active");
    }else {
        $(this).parent().children("div").addClass("active");
        $(this).removeClass("active");
        $(this).addClass("allActive");
    }
})


function chooseStore(){
    layer.open({
        type: 1,
        shade: 0.6,
        title: '选择门店',
        content: $('#lk_storeChoose_list'),
        area :['650px', '470px'],
        btn:["确定","取消"],
        yes:function (){
            layer.closeAll();
            getStores("")
        }
    });
}

//选择门店后显示
function getStores(type) {
    var s = $(".store");
    if(type == undefined || type == "undefined"){
        type = "";
    }
    var store_list = "";
    if($("#all_store").hasClass("allActive")){
        store_list = '<span class="store_name_style layui-btn layui-btn-sm layui-btn-primary">全部'+type+'门店<i class="layui-icon" onclick=delStore(this,"all_store")>&#x1007;</i></span>'
    }else{
        for (var i = 0; i < s.length; i++) {
            if($(s[i]).hasClass("active")){
                var storeName = $(s[i]).html();
                store_list += '<span class="store_name_style layui-btn layui-btn-sm layui-btn-primary">'+storeName+'<i class="layui-icon" onclick=delStore(this,"store'+i+'")>&#x1007;</i></span>';
            }
        }
    }

    $("#store_item").html(store_list);
}

// 删除门店
function delStore(obj,id){
    var id = "#"+id;
    var type = $(obj).parent().parent().data("type");
    var len = $(obj).parent().parent().children().length;
    if(type == "one" && len == 1){
        remind("至少含有一个门店")
        return false
    }
    $(obj).parent().remove();
    $(id).trigger("click");
}

// 检测组队人数不能少于2
function num(obj){
    if($(obj).val() < 2){
        $(obj).val("");
        error("组队人数不能少于2");
        return false;
    }
    return true;
}


//加载门店列表
function loadStores(type){
    var obj = new Object();
    obj.type = type;
    $.post('matches/chooseStore.do',obj,function (res) {
        if(res.errcode == 0){
            var storeList = res.data.list;
            apply_store(storeList)
        }else{
            layer.msg(res.message);
        }
    })
}

// 显示可用门店
function apply_store(storeList) {
    var htmls = '';
    for (var i = 0; i < storeList.length; i++) {
        var store = storeList[i];
        htmls += '<div class="store" id="store'+i+'" data-id="'+store.STORE_ID+'">'+store.STORE_NAME+'</div>';
    }
    $("#all_store").after(htmls);

    if(htmls == ''){
        htmls = '<p style="text-align: center;margin-top:40px;">暂无可选门店</p>';
        $("#all_store").parent().html(htmls);
    }

    $(".store").click(function () {
        $(this).toggleClass("active");
        var item = $(".store")
        var n = item.length;
        for (var i = 0; i < n; i++) {
            if($(item[i]).hasClass("active") == false){
                $("#all_store").removeClass("allActive");
            }
        }
        var l = $(".active").length;
        if(n == l){
            $("#all_store").addClass("allActive");
        }
    })
}

//  加v限制，选择门店
function chooseStore_v(color){
    var bg_style = [0.9, '#fff']
    if(color == "black"){
        bg_style = [0.8]
    }
    layer.open({
        type: 1,
        shade: bg_style,
        skin: 'demo-class',
        title: '选择门店',
        content: $('#lk_storeChoose_list_v'),
        area :['650px', '490px'],
        btn:["确定","取消"],
        yes:function (){
            layer.closeAll();
            getStores('加V');
        }
    });
}

// 加载加V门店
 var url = 'matches/chooseStore.do?type=pub&noType=noPub'
function loadStores_v(type) {
    $.post(url,type,function (res){
        var choosableList = res.data.list;//可选门店
        var noChooseList = res.data.noList; //不可选门店，未加V\
        if(res.errcode == 0){
            apply_store(choosableList);
            apply_noChooseList(noChooseList)
        }else {
            message(res.message)
        }
    });
}
// 显示没有加V的门店
function apply_noChooseList(storeList){
    var htmls = ""
    for (var i = 0; i < storeList.length; i++){
        var store = storeList[i];
        htmls += '<div>'+store.STORE_NAME+'</div>';
    }
    if(htmls != ""){
        var dd = '<div class="noChoose_title">不可选门店</div>'+
                    '<div class="noChoose_content layui-clear">'+ htmls+
                    '</div>'+
                    '<div class="noChoose_msg openUp-action">以上门店未开通计费系统（简称加"V"），无法实现该功能'+
                    '<span onclick=toHome("用户中心","accountSettings/list.do")>去开通</span>'+
                '</div>'
        $("#noChoose_box").html(dd);
    }
}


function getRealPath(){
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0,pos);
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var realPath=localhostPaht+projectName;
    if(projectName == "/lanker"){
        return realPath
    }else{
        return localhostPaht
    }
}

//首页菜单按钮
function toHome(name,menuUrl){
    $.ajax({
        type	: "POST",
        url		:getRealPath()+'/home.do',
        data : {
            menuUrl : menuUrl
        },
        dataType : 'json',
        success : function(data) {
            if (data.result == "true") {
                top.mainFrame.tabAddHandler(data.INTENET_ID, name,menuUrl);
            }else{
                layer.alert(data.message);
            }
        },
        error : function() {
            layer.close(layer.index);
            message("系统繁忙，请稍后再试！");
            return;
        }
    });
}

//去群发对象页面
var card_sel_json = new Object();
card_sel_json.send_obj = 'member';
var card_sel_list = '';
var card_sel_result = '全部会员';

var matches_sel_json = new Object();
matches_sel_json.send_obj = 'member';
var matches_sel_list = '';
var matches_sel_result = '全部会员';

var msg_sel_json = new Object();
msg_sel_json.send_obj = 'member';
var msg_sel_list = '';
var msg_sel_result = '全部会员';

function chooseObj(type){
    var storeList ;
    if(type == 'card'){
        storeList = $("#cardStore_item").find("span");
    }else if(type == 'matches'){
        storeList = $("#matchesStore_item").find("span");
    }else if(type == 'msg'){
        storeList = $(".active");
    }


    var store_ids = '';
    var length = storeList.length;
    storeList.each(function (e){
        store_ids += $(this).data("id");
        if(length-1 != e){
            store_ids += ",";
        }
    })
    if(store_ids != '' && store_ids.endsWith(",")){
        store_ids = store_ids.substring(0, store_ids.length - 1);
    }

    var url = "msgManager/goSetMembers.do?store_ids=" + store_ids;

    layer.open({
        type: 2,
        shade: 0.8,
        title: '选择对象',
        content: url,
        area :['80%', '90%'],
        btnAlign: 'c',
        btn:["确定","取消"],
        yes:function (index, layero){
            // layer.closeAll()
            //将查询条件返回
            var iframeWindow = window['layui-layer-iframe' + index],
                btn = layero.find('iframe').contents().find('#chooseSubmit'),
                open_ids = layero.find('iframe').contents().find('#open_ids'),
                send_obj1 = layero.find('iframe').contents().find('#send_obj1'),
                send_obj2 = layero.find('iframe').contents().find('#send_obj2'),
            mem_num = layero.find('iframe').contents().find('#mem_num');

            //监听提交
            iframeWindow.layui.form.on('submit(chooseSubmit)', function (data) {
                var field = data.field;
                if(type == 'card'){
                    card_sel_json = field;
                    card_sel_list = open_ids.val();
                    if(send_obj1.is(':checked')){
                        $("#cardMember_item").html('<p>全部会员</p>');
                        card_sel_result = "全部会员";
                    }
                    if(send_obj2.is(':checked')){
                        $("#cardMember_item").html('<p>'+mem_num.html()+'位会员</p>')
                        card_sel_result = mem_num.html()+'位会员';
                    }
                }else if(type == 'matches'){
                    matches_sel_json = field;
                    matches_sel_list = open_ids.val();
                    if(send_obj1.is(':checked')){
                        $("#matchesMember_item").html('<p>全部会员</p>');
                        matches_sel_result = "全部会员";
                    }
                    if(send_obj2.is(':checked')){
                        $("#matchesMember_item").html('<p>'+mem_num.html()+'位会员</p>')
                        matches_sel_result = mem_num.html()+'位会员';
                    }
                }else if(type == 'msg'){
                    msg_sel_json = field;
                    msg_sel_list = open_ids.val();

                    console.log(send_obj2.is(':checked'));
                    console.log(send_obj2.is(':checked'));
                    if(send_obj1.is(':checked')){
                        $("#msgMember_item").html('<p>全部会员</p>');
                        msg_sel_result = "全部会员";
                    }
                    if(send_obj2.is(':checked')){
                        $("#msgMember_item").html('<p>'+mem_num.html()+'位会员</p>')
                        msg_sel_result = mem_num.html()+'位会员';
                    }
                }

                layer.closeAll();
                return false;//阻止表单跳转
            });

            //确认提交
            btn.trigger('click');
        }
    });
}