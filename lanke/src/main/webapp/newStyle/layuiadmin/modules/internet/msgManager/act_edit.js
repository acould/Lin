
var moduleUrl = "msgManager/";
layui.define(['layer','form', 'element'],function (exports) {
    var layer = layui.layer,
        element = layui.element,
        form = layui.form

    //初始化
    loadStores_v();
    initialize();
    function initialize(){
        loading("加载中");
        var id = $("#send_id").val();
        var type = $("#act_type").val();
        var index = "";
        if(id == ""){
            //设置默认值
            $("#setDefault1").addClass("layui-this");
            $("#setDefault2").addClass("layui-show");

            layer.closeAll();
            return false;
        }else {
            $(".mail-btn").remove();//按钮
            $(".layui-tab-title li").each(function (i){
                if($(this).data("type") == type){
                    index = $(this).index();
                    $($(".layui-tab-title li")[index]).addClass("layui-this");
                    $($(".layui-tab-item")[index]).addClass("layui-show");
                    $($(".layui-tab-title li")[index]).siblings().remove();
                    $($(".layui-tab-item")[index]).siblings().remove();
                }
            })
            var loadActMsgUrl = basePath + 'msgManager/loadActMsg.do';
            var field = new Object();
            field.send_id = id;
            if(type == "card"){
                $.post(loadActMsgUrl, field, function (res){
                    console.log(res);
                    layer.closeAll();
                    if(res.errcode == 0){
                        var data = res.data.pd;
                        var pdCard = data.pdCard;
                        var divs = '<div class="choose_cardBox">'+
                            '<input type="hidden"  value="'+pdCard.CARD_ID+'">'+
                            '<h1 class="title layui-elip '+pdCard.COLOR+'" >'+pdCard.SUB_TITLE+'</h1>'+
                            '<div class="card_content_msg">'+
                            '<p class="time layui-elip">'+pdCard.available_time+'</p>'+
                            '<p class="adrss layui-elip">'+data.store_names+'</p>'+
                            '<p class="xiangqing layui-elip">'+pdCard.DESCRIPTION+'</p>'+
                            '</div>'+
                            '</div>'
                        $("#card_item").html(divs);
                        $("#card_item").prev().remove();
                        data.storeNameList = data.store_names;
                        $('#card_store_list').html(initialize_store(data,'cardStore_item'));
                        $('#cardMember_item').prev().remove();
                        $("#cardMember_item").html('<p>' + data.sel_result + '</p>');
                        $("#card_ad_content").val(data.ad_content);

                    }else {
                        message(res.errmsg);
                    }
                })
            }else if(type == "matches"){
                $.post(loadActMsgUrl, field, function (res){
                    layer.closeAll();
                    if(res.errcode == 0){
                        var data = res.data.pd;
                        var pdMatches = data.pdMatches;
                        var divs = '<div class="match_box" data-id="'+pdMatches.id+'">'+
                            '<div class="img">'+
                            '<img src="'+pdMatches.url+'" alt="" width="100%" height="100%">'+
                            '</div>'+
                            '<div class="content">'+
                            '<p class="title">'+pdMatches.name+'</p>'+
                            '<p class="store the_stores">'+pdMatches.stores+'</p>'+
                            '<p class="store apply_type">'+pdMatches.enroll_type_info+'</p>'+
                            '<p class="store apply_time">'+pdMatches.enroll_time+'</p>'+
                            '<p class="store match_time">'+pdMatches.game_time+'</p>'+
                            '</div>'+
                            '</div>'
                        $("#matches_item").html(divs);
                        $("#matches_item").prev().remove();
                        data.storeNameList = data.store_names;
                        $('#matches_store_list').html(initialize_store(data,'matchesStore_item'));
                        $('#matchesMember_item').prev().remove();
                        $("#matchesMember_item").html('<p>' + data.sel_result + '</p>');
                        $("#matches_ad_content").val(data.ad_content);
                    }else {
                        message(res.errmsg);
                    }
                })

            }else if(type == "msg"){
                $.post(loadActMsgUrl, field, function (res){
                    layer.closeAll();
                    if(res.errcode == 0){
                        var data = res.data.pd;
                        $("#store_box").remove();

                        $("#store_item").remove();
                        data.storeNameList = data.store_names;
                        $('#msg_store_list').html(initialize_store(data,'msg_store_list'));

                        $('#msgMember_item').prev().remove();
                        $("#msgMember_item").html('<p>' + data.sel_result + '</p>');
                        $("#msg_ad_content").val(data.ad_content);
                        form.val("form_msg",{
                            "title":data.title
                            ,"content":data.content
                        });
                    }else {
                        message(res.errmsg);
                    }
                })
            }
        }
    }
    // 初始化获取已发送门店集合
    function initialize_store(data,box_id){
        var sent_storeList = data.storeNameList.split(",");
        var sent_storeName = "";
        for (var i = 0; i < sent_storeList.length; i++) {
            sent_storeName += '<span class="store_name_style layui-btn layui-btn-sm layui-btn-primary">'+sent_storeList[i]+'</span>'
        }
        var htmls = '<div class="layui-form-item">'+
            '<label class="layui-form-label">已发送门店：</label>'+
            '<div class="layui-input-block">'+
            '<div class="lk-title-text" id="'+box_id+'">'+
            sent_storeName +
            '</div>'+
            '</div>'+
            '</div>'
        return htmls
    }

    // 提交群发卡券
    form.on('submit(card_submit)', function(data){
        var field = data.field;
        field.first_data = $("#card_name").html();
        field.sel_json = card_sel_json;
        field.sel_list = card_sel_list;
        field.sel_result = card_sel_result;

        var card_id = $("#card_item").find("input").val();
        field.obj_id = card_id;

        var storeList = $("#cardStore_item").find("span");
        field.store_ids = apply_store_ids(storeList);
        field.store_names = apply_store_names(storeList);
        if(field.obj_id == "" || field.obj_id == undefined){
            message("请选择群发的卡券");
            return false
        }
        goAjax(field)
    })

    // 提交群发赛事
    form.on('submit(matches_submit)', function(data){
        var field =data.field;
        field.first_data = $("#matches_name").html();
        field.sel_json = matches_sel_json;
        field.sel_list = matches_sel_list;
        field.sel_result = matches_sel_result;

        var match_id = $("#matches_item").find(".match_box").data("id");
        field.obj_id = match_id;
        var storeList = $("#matchesStore_item").find("span");
        field.store_ids = apply_store_ids(storeList);
        field.store_names = apply_store_names(storeList);
        if(field.obj_id == "" || field.obj_id == undefined){
            message("请选择群发的赛事")
            return false
        }
        goAjax(field)
    })

    // 提交群发消息
    form.on('submit(msg_submit)', function(data){
        var field =data.field;
        field.first_data = field.title;
        field.sel_json = msg_sel_json;
        field.sel_list = msg_sel_list;
        field.sel_result = msg_sel_result;

        var storeList = $(".active");
        field.store_ids = apply_store_ids(storeList);
        field.store_names = apply_store_names(storeList);
        if(field.store_ids == "" || field.store_ids == undefined){
            message("请选择群发的门店")
            return false
        }
        goAjax(field)
    })

    function goAjax(field) {

        if(field.store_ids == ''){
            message("请选择有效的门店");
            return false;
        }

        var saveUrl = basePath + moduleUrl + "saveActivity.do";
        loading("提交中");

        field.send_token = $("#send_token").val();//验证
        field.sel_json = JSON.stringify(field.sel_json);

        // console.log(field);
        layer.confirm("确认要群发吗？", {
            btn: ['确定', '取消'],
        }, function () {

            $.post(saveUrl, field, function (res){
                layer.closeAll();
                message(res.errmsg);
                if (res.errcode == 0) {
                    setTimeout(function () {
                        window.history.go(-1);//返回上一个页面
                    },500)
                }
            })
        }, function () {
            return
        });


    }
    //触发提交方法
    var active = {
        card: function () {
            $("#card_save").trigger("click");
        },
        matches: function (){
            $("#matches_save").trigger("click");
        },
        msg: function (){
            $("#msg_save").trigger("click");
        }
    }

    //群发按钮绑定事件
    $(".mail-btn").click(function (){
        var type = $(".layui-this").data("type");
        active[type] ? active[type].call(this) : '';
    })

    //通用获取发送门店的ID和name
    function apply_store_ids(storeList){
        var store_ids = '';
        var length = storeList.length;
        storeList.each(function (e){
            store_ids += $(this).data("id");
            if(length-1 != e){
                store_ids += ",";
            }
        })
        return store_ids;
    }
    function apply_store_names(storeList){
        var store_names = '';
        var length = storeList.length;
        storeList.each(function (e){
            store_names += $(this).text();
            if($(this).html().indexOf("<i") > 0){
                //含有i标签的文本中会多出一个字符
                store_names = store_names.substring(0, store_names.length - 1);
            }
            if(length-1 != e){
                store_names += ", ";
            }
        })
        return store_names;
    }

    exports(['act_edit'], {});
});

//选择卡券
function chooseCard(){
    var url =  "card/goSelCard.do";
    layer.open({
        btn: ['确定','关闭'],
        yes:function (index) {
            var res = window["layui-layer-iframe" + index].save();
            if(res.card_id == "" || res.card_id == undefined){
                message("请选择卡券")
                return false
            }
            var divs = '<div class="choose_cardBox">'+
                '<input type="hidden"  value="'+res.card_id+'">'+
                '<h1 class="title layui-elip '+res.titleClass+'" id="card_name">'+res.title+'</h1>'+
                '<div class="card_content_msg">'+
                '<p class="time layui-elip">'+res.time+'</p>'+
                '<p class="adrss layui-elip">'+res.adrss+'</p>'+
                '<p class="xiangqing layui-elip">'+res.xiangqing+'</p>'+
                '</div>'+
                '</div>'

            $("#card_item").html(divs);
            apply_storelist(res,'card')
            layer.close(index);
        },
        type: 2,
        title: '选择群发的卡券',
        shadeClose: false,
        shade: 0.8,
        area: ['704px','80%'],
        fixed: false, //不固定
        maxmin: true,
        content:url,
    });
}

//选择赛事
function chooseMatches() {
    var url = "matches/goSelMatches.do";
    layer.open({
        btn: ['确定', '关闭'],
        yes: function (index) {
            var res = window["layui-layer-iframe" + index].save();
            if(res.match_id == "" || res.match_id == undefined){
                message("请选择赛事")
                return false
            }
            var divs = '<div class="match_box" data-id="'+res.match_id+'">'+
                            '<div class="img">'+
                                '<img src="'+res.match_img+'" alt="" width="100%" height="100%">'+
                            '</div>'+
                            '<div class="content">'+
                                '<p class="title" id="matches_name">'+res.match_title+'</p>'+
                                '<p class="store the_stores">'+res.the_stores+'</p>'+
                                '<p class="store apply_type">'+res.apply_type+'</p>'+
                                '<p class="store apply_time">'+res.apply_time+'</p>'+
                                '<p class="store match_time">'+res.match_time+'</p>'+
                            '</div>'+
                        '</div>'

            $("#matches_item").html(divs);
            apply_storelist(res,'matches')
            layer.close(index);
        },
        type: 2,
        title: '选择群发的赛事',
        shadeClose: false,
        shade: 0.8,
        area: ['630px', '80%'],
        fixed: false, //不固定
        maxmin: true,
        content: url,
    })
}


//卡券和赛事共用渲染门店
function apply_storelist(res,type){
    var stores_v = res.stores_v.split(",");
    var store_ids_v = res.store_ids_v.split(",");
    var stores_nov = res.stores_nov.split(",");
    var s_v = "";
    var s_nov = "";
    var store_box_id = "cardStore_item";
    var storeList_box_id = "card_store_list";
    if(type == "matches"){
        store_box_id = "matchesStore_item";
        storeList_box_id = "matches_store_list";
    }
    for (var i = 0; i < stores_v.length; i++) {
        s_v += '<span class="store_name_style layui-btn layui-btn-sm layui-btn-primary" data-id="'+store_ids_v[i]+'">'+stores_v[i]+'<i class="layui-icon" onclick=delStore(this,"")>&#x1007;</i></span>'
    }
    for (var j = 0; j < stores_nov.length; j++) {
        s_nov += '<span class="store_name_style layui-btn layui-btn-sm layui-btn-disabled">'+stores_nov[j]+'</span>'
    }

    var s_nov_box = ''
    if(stores_nov.length > 1){
        s_nov_box = '<div class="layui-form-item">'+
            '<label class="layui-form-label">不可发送门店：</label>'+
            '<div class="layui-input-block">'+
            '<div class="lk-title-text noChoose_shore"  style="margin-bottom: 0">'+
            s_nov+
            '</div>'+
            '<div class="openUp-action" style="">以上门店未开通计费系统（简称加"V"），无法实现该功能<span onclick=toHome("用户中心","accountSettings/list.do")>去开通</span></div>'+
            '</div>'+
            '</div>';
    }
    var store_list = '<div class="layui-form-item">'+
        '<label class="layui-form-label">可发送门店：</label>'+
        '<div class="layui-input-block">'+
        '<div class="lk-title-text" id="'+store_box_id+'" data-type="one">'+
        s_v +
        '</div>'+
        '</div>'+
        '</div>'+ s_nov_box;
    $('#'+storeList_box_id).html(store_list);
}