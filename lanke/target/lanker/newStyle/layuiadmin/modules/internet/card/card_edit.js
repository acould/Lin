var moduleUrl = "card/";
var module = "Card";
var moduleName = "卡券编辑";

var tableId = "layTable";
var pageId = "layPage";
var iframeBtn = "laySubmit";

var getEditUrl = basePath + moduleUrl + "loadCardEditInfo.do";

layui.define(['layer','form', 'element', 'laydate'],function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        laydate  = layui.laydate,
        element = layui.element;



    //初始化页面
    init();
    getStoreV();


    function init(){
        var FAV_TYPE = $("#FAV_TYPE").val();
        var CARD_ID = $("#CARD_ID").val();
        var FAV_TYPE_INFO = getInfoByFAV_TYPE(FAV_TYPE);

        var field = new Object();
        field.CARD_ID = CARD_ID;
        field.FAV_TYPE = FAV_TYPE;
        $.post(getEditUrl, field, function (res) {
            console.log(res);
            if(res.errcode == 0){

                var pd = res.data.pd;
                var pdCard = res.data.pdCard;
                var pdInternet = res.data.pdInternet;
                var storeList = res.data.storeList;
                var listJg = res.data.listJg;

                if(CARD_ID != null && CARD_ID != '') {
                    FAV_TYPE = pdCard.FAV_TYPE;
                    FAV_TYPE_INFO = getInfoByFAV_TYPE(FAV_TYPE);
                    $("#FAV_TYPE").val(FAV_TYPE);
                }
                //手机模型中的数据
                $("#phoneLogo").attr("src", pdInternet.HEAD_IMG);
                $("#card-logo-title").html(pdInternet.INTENET_NAME);

                //基本信息
                $("#cardLogo").attr("src", pdInternet.HEAD_IMG);
                $("#base_title").html(pdInternet.INTENET_NAME + "<br>");
                $("#LOGO_URL").val(pdInternet.HEAD_IMG);
                $("#BRAND_NAME").val(pdInternet.INTENET_NAME);
                $("#SCENE_NAME").val(FAV_TYPE_INFO);
                $("#show_scene").val(FAV_TYPE_INFO);

                //门店列表
                var storeInputs = '';
                for (var i = 0; i < storeList.length; i++) {
                    storeInputs += '<input type="checkbox" name="STORE_ID" lay-skin="primary"'+
                                'value="'+storeList[i].STORE_ID+'" title="'+storeList[i].STORE_NAME+'" ' +
                            'lay-filter="store" class="storeId">';
                }
                storeInputs += '<input type="checkbox" name="allChecked" id="allChecked" lay-skin="primary" title="全选"' +
                                'lay-filter="tufure">';
                $("#storeList").html(storeInputs);

                //场景列表
                var sceOptions = '';
                for (var i = 0; i < listJg.length; i++) {
                    sceOptions += '<option value="'+listJg[i].DICT_CODE+'">'+listJg[i].DICT_VALUE+'</option>'
                }
                $("#RECEIVE_DETIL").append(sceOptions);
                $("#RUSH_BUY_TIME_UNITS").append(sceOptions);


                //新增的时候赋值默认数据
                if(FAV_TYPE == 'OLD' || FAV_TYPE == 'NEW' || FAV_TYPE == 'BIRTH' || FAV_TYPE == 'APPLY' || FAV_TYPE == 'RUSH'){
                    $("#RECEIVE_NUMBER").val(1);
                    $("#RECEIVE_NUMBER").attr("value", 1);
                    $("#BLANK_NUMBER").val(1);
                    $("#BLANK_NUMBER").attr("value", 1);
                    $("#RECEIVE_DETIL").val("YEAR");
                    $("#RECEIVE_DETIL").children(":last-child").attr("selected", true);

                    $("#receive_div").hide();
                    $("#revceive_blank_div").hide();
                }
                if(CARD_ID == ''){
                    $("#type_range").attr("checked", true);
                    $("#type_term").prop("checked",false);
                    $("#date1box").show();
                    $("#date2box").hide();
                }


                //秒抢券
                if(FAV_TYPE == 'GRAB'){
                    $("#grab_div").show();
                }
                //满时长券
                if(FAV_TYPE == 'TERM'){
                    $("#term_div1").show();
                    $("#term_div2").show();
                    $("#term_div111").val("1");

                    //默认连续满时长
                    $("#cardSumType1").attr("checked", true);

                    laydate.render({
                        elem: '.unusetime',
                        type: 'time',
                        range: true,
                        format: 'HH:mm', //显示格式：小时：分钟
                    });

                }
                //申请会员福利券
                if(FAV_TYPE == 'APPLY'){
                    $("#apply_div").show();
                }
                //抵用券
                if(FAV_TYPE == 'RUSH'){
                    $("#rush_div").show();

                    //有效期类型默认按天
                    $("#type_range").attr("disabled", true);
                    $("#type_range").prop("checked", false);
                    $("#type_range").removeAttr("checked");
                    $("#type_term").prop("checked", true);

                    //赠送方式
                    $("#handsel_type1").prop("checked", true);

                    //现金券
                    $("#CARD_TICKET").val("1");
                    $("#tip").hide();
                    $("#CARD_TICKET").attr("checked", true);
                    $("#CARD_TICKET").attr("readonly", true);

                    $("#date1box").hide();
                    $("#date2box").show();

                    //购买限制
                    $("#rush_time_div").show();
                    $("#rush_buy_div").show();
                }

                //编辑的时候
                if(CARD_ID != null && CARD_ID != ''){
                    $("#SCENE_NAME").val(FAV_TYPE_INFO);
                    $("#show_scene").val(FAV_TYPE_INFO);
                    $("#SCENE_ID").val(pdCard.SCENE_ID);


                    //选择门店
                    $("#STORE_LIST").val(pdCard.store_ids);
                    var store_ids = pdCard.store_ids.split(",");
                    for (var i = 0; i < store_ids.length; i++) {
                        $("input[name='STORE_ID']").each(function () {
                            if(store_ids[i] == $(this).val()){
                                $(this).attr("checked", true);//默认选中的门店
                            }
                        })
                    }


                    //编辑的时候，现金券不能修改，名称不能修改
                    $("#card").append('<p style="margin-top: 10px;color:#666">卡券类型不支持修改</p>');
                    $("#CARD_TICKET").attr("disabled", "");
                    $("#SUB_TITLE").attr("disabled", true);
                    if(pdCard.CARD_TICKET == 1){
                        $("#CARD_TICKET").val("1");
                        if($("#FAV_TYPE").val() != 'RUSH'){
                            $("#tip").show();
                        }

                        $("#CARD_TICKET").attr("checked", true);
                        $("#CASH_NUMBER").val(pdCard.CASH_NUMBER);
                        $("#CASH_NUMBER").attr("disabled", true);

                    }

                    //展示颜色
                    var colorId = "#" + pdCard.COLOR;
                    cardColor($(colorId).attr("name"));


                    //展示已领取的
                    $("#has_lq").show();
                    $("#cardSum").val(pdCard.cardSum);


                    //赋值数据
                    $("#card-shop-title").html(pdCard.SUB_TITLE);
                    $("#SUB_TITLE").val(pdCard.SUB_TITLE);
                    $("#COLOR").val(pdCard.COLOR);
                    $("#QUANTITY").val(pdCard.QUANTITY);
                    $("#DESCRIPTION").val(pdCard.DESCRIPTION);
                    $("#NOTICE").val(pdCard.NOTICE);

                    //领取限制
                    $("#RECEIVE_NUMBER").val(pdCard.RECEIVE_NUMBER);
                    $("#RECEIVE_NUMBER").attr("value", pdCard.RECEIVE_NUMBER);
                    $("#BLANK_NUMBER").val(pdCard.BLANK_NUMBER);
                    $("#BLANK_NUMBER").attr("value", pdCard.BLANK_NUMBER);
                    $("#RECEIVE_DETIL").val(pdCard.RECEIVE_DETIL);
                    $("#RECEIVE_DETIL").children().each(function () {
                        if($(this).val() == pdCard.RECEIVE_DETIL){
                            $(this).attr("selected", true);
                        }
                    })


                    //展示日期类型
                    if(pdCard.TYPE == 'DATE_TYPE_FIX_TERM'){//几天失效
                        $("#type_term").attr("checked", true);
                        $("#type_range").prop("checked",false);
                        $("#type_term").attr("disabled", true);
                        $("#type_range").attr("disabled", true);
                        $("#FIXED_TERM").attr("disabled", true);
                        $("#FIXED_TERM").val(pdCard.FIXED_TERM);
                        $("#date2box").show();
                        $("#date1box").hide();

                    }else if(pdCard.TYPE == 'DATE_TYPE_FIX_TIME_RANGE'){//日期区间
                        $("#type_range").attr("checked", true);
                        $("#type_term").prop("checked",false);
                        $("#type_range").attr("disabled", true);
                        $("#type_term").attr("disabled", true);

                        $("#BEGIN_TIMESTAMP").val(pdCard.BEGIN_TIMESTAMP);
                        $("#END_TIMESTAMP").val(pdCard.END_TIMESTAMP);
                        $("#date1box").show();
                        $("#date2box").hide();
                    }


                    //抵用券时，有充值规则
                    if(FAV_TYPE == 'RUSH'){
                        //不能更改门店
                        $("#storeList :input").attr("disabled", true);

                        //不能更改充值规则
                        $("#rush_div :input").attr("disabled", true);

                        //购买限制
                        $("#RUSH_BUY_NUMBER").val(pdCard.RUSH_BUY_NUMBER);
                        $("#RUSH_BUY_NUMBER").attr("value", pdCard.RUSH_BUY_NUMBER);
                        $("#RUSH_BUY_TIME").val(pdCard.RUSH_BUY_TIME);
                        $("#RUSH_BUY_TIME").attr("value", pdCard.RUSH_BUY_TIME);
                        $("#RUSH_BUY_TIME_UNITS").val(pdCard.RUSH_BUY_TIME_UNITS);
                        $("#RUSH_BUY_TIME_UNITS").children().each(function () {
                            if($(this).val() == pdCard.RUSH_BUY_TIME_UNITS){
                                $(this).attr("selected", true);
                            }
                        })
                        $("#rush_time_div :input").attr("disabled", true);
                        $("#rush_buy_div :input").attr("disabled", true);



                        $("#recharge").val(pdCard.recharge);
                        $("#handsel").val(pdCard.handsel);
                        $("#handsel_sum").val(pdCard.handsel_sum);
                        $("#handsel_type").val(pdCard.handsel_type);
                        $("#handsel_time").val(pdCard.handsel_time);
                        $("#handsel_times").val(pdCard.handsel_times);
                        if(pdCard.handsel_type == '0'){
                            $("#handsel_type1").attr("checked", true);
                            $("#handsel_type2").prop("checked",false);
                        }else if(pdCard.handsel_type == '0'){
                            $("#handsel_type2").attr("checked", true);
                            $("#handsel_type1").prop("checked",false);
                        }
                        var moneyList = pdCard.moneyList;//赠送的金额列表

                        var htmls = "";
                        for (var i = 0; i < moneyList.length; i++) {
                            htmls += '<div class="layui-inline">'+
                                        '<label class="layui-form-label" style="padding: 0 15px;">第'+moneyList[i].card_order+'次送：</label>'+
                                        '<div class="layui-input-block">'+
                                            '<input type="number" name="CASH_NUMBER'+i+'" id="CASH_NUMBER'+i+'" value="'+moneyList[i].CASH_NUMBER+'" min="1" class="layui-input" disabled>'+
                                        '</div>'+
                                    '</div>';
                        }
                        $("#CASH_NUMBER_LIST").html(htmls);

                    }

                    //满时长券
                    if(FAV_TYPE == 'TERM'){
                        $("#card_ad").val(pdCard.card_ad);
                        $("#bannerText").html(pdCard.card_ad);
                        $("#cardSum_time").val(pdCard.cardSum_time);
                        if(pdCard.cardSum_type == 1){
                            $("#cardSumType1").attr("checked", true);

                            //连续上网满时长才有广告展示
                            $("#term_banner").show();
                        }else if(pdCard.cardSum_type == 2){
                            $("#cardSumType2").attr("checked", true);
                        }else if(pdCard.cardSum_type == 3){
                            $("#cardSumType3").attr("checked", true);
                        }

                        //不可用时间段
                        var unusertime = pdCard.unusertime;
                        console.log(unusertime);
                        if(unusertime != null && unusertime.length > 0){
                            var timeList = unusertime.split("、");
                            for (var i = 0; i < timeList.length; i++) {
                                if(i != 0){
                                    $(".leftData").append('<div class="layui-input-block shezhi"><div class="fath">' +
                                        '<input type="text" name="unusetime" class="hidden unusetime" value="'+timeList[i]+'" disabled readonly style="text-align:center">' +
                                        '</div></div>');
                                }else{
                                    $(".jia").hide();
                                    $(".unusetime").val(timeList[i]);
                                    $(".unusetime").attr("disabled", true);
                                }
                            }
                        }
                    }
                }

                form.render();
            }
        })

    }

    //获取加v的门店列表
    var storev_ids = '';
    function getStoreV() {
        var field = new Object();
        field.type = 'pub';
        $.post(basePath +'matches/chooseStore.do',field, function (res) {
            if(res.errcode == 0){
                var list = res.data.list;
                for (var i = 0; i < list.length; i++) {
                    storev_ids += list[i].STORE_ID;
                    if(i != list.length -1){
                        storev_ids += ",";
                    }
                }
            }
        })
    }


    //保存
    form.on('submit(saveCard)', function(data){
        if ($("input:checkbox[name='STORE_ID']:checked").length < 1) {
            message("请勾选适用门店！");
            return false;
        }
        if ($("#COLOR").val() == "") {
            message("请选择卡券颜色！");
            return false;
        }
        if($("#term_div111").val() == '1'){
        for(var i =0;i<$(".shezhi input").length;i++) {
            if (!$(".shezhi input").eq(i).val()) {
                message("时间段不能为空");
                return false;
            }
            var val0 = $(".shezhi input").eq(i).val().split("-");
            var val2 = Number(val0[0].split(":").join(""));
            var val3 = Number(val0[1].split(":").join(""));
            if (val3 <= val2) {
                message("结束时间必须大于启用时间");
                return false;
            }
        }
        }
        if($("#type_range").is(':checked') ){
            //在修改时  启用时间不能比原来大，  结束时间不能比原来的小（总体  修改时间 时不可以缩小原来有效区间）
            if (!CompareDate($("#END_TIMESTAMP").val(), $("#BEGIN_TIMESTAMP").val())) {
                $("#END_TIMESTAMP").focus();
                message("结束时间必须大于启用时间");
                return false;
            }
            var day = new Date();
            var current_time = day.getFullYear() + "-" + (day.getMonth() + 1) + "-" + day.getDate();
            if (!CompareDate($("#END_TIMESTAMP").val(), current_time)) {
                $("#END_TIMESTAMP").focus();
                message("结束时间必须大于当前时间");
                return false;
            }
        }
        //秒抢券
        if($("#FAV_TYPE").val() == "GRAB"){
            if($("#type_range").is(':checked') ) {
                if (!CompareDate($("#END_TIMESTAMP").val(), $("#garbEnd_time").val())) {
                    tipTop("END_TIMESTAMP");
                    layer.tips('卡券结束时间须大于抢券结束时间', '#END_TIMESTAMP', {
                        tips: 3,
                        time: 1500
                    });
                    return false;
                }
            }
        }
        //抵用券
        if($("#FAV_TYPE").val() == "RUSH"){
            //必须为现金券
            if($("#CARD_TICKET").val() == '2'){
                message("抵用券必须选择现金券");
                $("#CARD_TICKET").focus();
                return false;
            }

            var handsel = $("#handsel").val();   //赠送金额
            var handsel_sum=$("#handsel_sum").val(); //赠送次数
            var sum = 0
            for (var i = 0; i < handsel_sum; i++) {
                id = "#CASH_NUMBER"+i;
                sum +=parseInt($(id).val());
            }
            if(sum != handsel){//自定义送的金额总和与计划赠送金额不等
                message("自定义赠送总额与赠送金额不符!");
                $("#handsel").focus();
                return false;
            }
        }

        //TERM

        //保存
        saveCard(data);
    });

    function saveCard(data) {
        var field = data.field;

        var store_ids = "";
        $("input[name='STORE_ID']").each(function () {
            if($(this).is(':checked')){
                store_ids += $(this).val() +",";
            }
        });
        $("#STORE_LIST").val(store_ids.substring(0, store_ids.length - 1));
        field.STORE_LIST = store_ids.substring(0, store_ids.length - 1);
        if(store_ids == ''){
            message("请选择门店");
            return false
        }

        loading('提交中');
        $("#saveCard").attr("lay-filter", "");
        //只有新增的时候
        var time = "";
        if($(".unusetime") != null && $(".unusetime").length > 0){
            $(".unusetime").each(function (e) {
                time += $(this).val();
                if (e != $(".unusetime").length - 1) {
                    time += '、';
                }
            })
        }
        field.time = time;

        console.log(field,"保存");
        $.post(basePath+"card/saveCard.do", field, function (res) {
            if (res.errcode == 0) {
                message(res.errmsg);
                setTimeout(function () {
                    //关闭当前页面
                    window.opener = null;
                    window.open("", "_self");
                    window.close();
                }, 700);
                //刷新父页面
                var card_box_height = ''
                if($("#FAV_TYPE").val() == 'GRAB'){
                    card_box_height = "long";
                }else{
                    card_box_height= "short";
                }
                opener.get_cardMsg("", "", $("#FAV_TYPE").val(), "", "", card_box_height);
            }else{
                layer.alert(res.errmsg);
                $("#saveCard").attr("lay-filter", "saveCard");
            }
        })
    }

    //获取场景值
    function getInfoByFAV_TYPE(fav_type){
        var typeInfo = "";
        switch (fav_type) {
            case "NEW":
                typeInfo = "新手券";
                break;
            case "OLD":
                typeInfo = "老带新";
                break;
            case "MAN":
                typeInfo = "男神券";
                break;
            case "WEM":
                typeInfo = "女神券";
                break;
            case "BIRTH":
                typeInfo = "生日券";
                break;
            case "CURREN":
                typeInfo = "通用券";
                break;
            case "GRAB":
                typeInfo = "限时秒抢券";
                break;
            case "APPLY":
                typeInfo = "申请会员福利券";
                break;
            case "TERM":
                typeInfo = "上网满时长福利券";
                break;
            case "RUSH":
                typeInfo = "抵用券";
                break;
        }
        return typeInfo;
    }

    //修改验证
    function cardColor(num) {
        $(".colla-color").css({"background-color": num, "display": "inline-block"});
        $(".card-phone-content").css({
            "background-color": num,
            "background-image": "url("+basePath+"'newStyle/images/topbar_white3a7b38.png')"
        })
        $(".card-btn").css({"background-color": num, "color": "#fff"})
    }

    //监听折叠颜色面板
    element.on('collapse(color)', function (data) {
        if (data.show == true) {
            $(".layui-colla-content").children().click(function () {
                $(".layui-colla-content").removeClass("layui-show")
                $(".layui-colla-icon").html("&#xe602;")
                cardColor($(this).attr("name"))
                $("#COLOR").val($(this).attr("id"));
            })
        }
    });

    //监听单选框 有效期类型
    form.on('radio(date_type)', function (data) {
        if (data.value == "DATE_TYPE_FIX_TIME_RANGE") {
            $("#date1box").show();
            $("#date2box").hide();
        } else if (data.value == "DATE_TYPE_FIX_TERM") {
            $("#date1box").hide();
            $("#date2box").show();
        }
    });

    //监听满时长券，计算方式
    form.on('radio(cardRadio)', function (data) {
        if (data.value == 1) {
            $("#text1").show();
            $("#text2").hide();
            $("#term_banner").show();
        }else if (data.value != 1) {
            $("#text1").hide();
            $("#text2").show();
            $("#term_banner").hide();
        }
    });

    //checkbox选择门店监听
    form.on('checkbox(store)', function (data) {
        var a = data.elem.checked;//选中
        if(a == true && $("#CARD_TICKET").is(':checked')){
            //选择了现金券
            var isTrue = getCheckIsV();
            if(isTrue){
                $("#CARD_TICKET").val("1");
                if($("#FAV_TYPE").val() != 'RUSH'){
                    $("#tip").show();
                }
            }

        }

        //有一个未选中全选取消选中
        var item = $(".storeId");
        for (var i = 0; i < item.length; i++) {
            if (item[i].checked == false) {
                $("#allChecked").prop("checked", false);
                form.render('checkbox');
                break;
            }
        }
        //如果都勾选了  勾上全选
        var  all=item.length;
        for (var i = 0; i < item.length; i++) {
            if (item[i].checked == true) {
                all--;
            }
        }
        if(all==0){
            $("#allChecked").prop("checked", true);
            form.render('checkbox');
        }

    })

    //checkbox全选监听
    form.on('checkbox(tufure)', function (data){
        var a = data.elem.checked;
        if (a == true) {
            $(".storeId").prop("checked", true);
            form.render('checkbox');
        } else {
            $(".storeId").prop("checked", false);
            form.render('checkbox');
        }


        if(a == true && $("#CARD_TICKET").is(':checked')){
            //选择了现金券
            var isTrue = getCheckIsV();
            if(isTrue){
                $("#CARD_TICKET").val("1");
                if($("#FAV_TYPE").val() != 'RUSH'){
                    $("#tip").show();
                }
            }
        }

    });

    //现金券监听
    form.on('checkbox(CARD_TICKET)', function (data) {
        var elem = $(data.elem);
        var othis = $(data.othis);
        //创建现金券需要确定选择的门店，是否都加V了
        if(data.elem.checked == true){
            if ($("input:checkbox[name='STORE_ID']:checked").length < 1) {
                layer.msg("请勾选适用门店！", {time: 1000});
                checkboxRemove(othis,elem)
                return false;
            }
            var isTrue = getCheckIsV();
            if(isTrue){
                $("#CARD_TICKET").val("1");
            }
            if($("#FAV_TYPE").val() != 'RUSH'){
                $("#tip").show();
            }
        }else{
            $("#CARD_TICKET").val("2");
            $("#tip").hide();
            return false;
        }
    })

    //监听赠送方式
    form.on('radio(handsel_type)', function (data) {
        judeCard();
    });

    //表单验证
    form.verify({
        //库存
        quantity: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(value <= 0 && $("#CARD_ID").val() == ''){//新增时，库存不能为0
                $("#QUANTITY").val(1);
                return '卡券库存需大于0';
            }
            if(value > 100000000){
                $("#QUANTITY").val(100000000);
                return '卡券库存数量最大可设置为100000000';
            }

        },
        //大于0
        moreZero: function (value, item) {
            if (value <= 0) {
                return '请输入大于0的数';
            }
        },
        moreZero2: function (value, item) {
            if($("#type_term").is(':checked') && value <= 0){
                return '请输入大于0的数';
            }
        },
        //勾选现金券
        money: function (value, item) {
            if (value <= 0 && $("#CARD_TICKET").is(':checked')){
                return '请输入大于0的金额';
            }
        },
        //时间不为空
        timeNotNull: function (value, item) {
            if (value == '' && $("#type_range").is(':checked')){
                return '请选择时间';
            }

        },
        grabRequired: function (value, item) {
            if (value == '' && $("#FAV_TYPE").val() == "GRAB"){
                return '请选择秒抢时间';
            }
        },
        termRequired: function (value, item) {
            if (value == '' && $("#FAV_TYPE").val() == "TERM"){
                return '请输入';
            }
        },
        //冲送券的限制
        rushNotNull: function (value, item) {
            if (value == '' && $("#FAV_TYPE").val() == "RUSH"){
                return '请选择';
            }

        },
        rush_moreZero: function (value, item) {
            if (value <= 0 && $("#FAV_TYPE").val() == "RUSH") {
                return '请输入大于0的数';
            }

            if($(item).attr("id") == 'handsel_time' && value > 1000){
                $(item).val(1000);
                return '最大可设为1000';
            }
        }

    });

    //检查选择的门店是否加v
    function getCheckIsV() {
        var isTrue = false;
        $("input:checkbox[name='STORE_ID']:checked").each(function (i) {
            if(storev_ids.indexOf($(this).val()) < 0){
                $(this).prop("checked",false);
                message($(this).next().text() + "：该门店尚未加v，不能使用现金券");
                form.render();
                return;
            }
            if(i == $("input:checkbox[name='STORE_ID']:checked").length-1){
                isTrue = true;
            }
        })
        return isTrue;
    }

    function  checkboxRemove(othis,elem){
        othis.removeClass("layui-form-checked");
        elem.removeAttr("checked");
        $(elem).prop("checked", false);
        $(othis).removeClass("layui-form-checked");
        form.render('checkbox');
    }

    //滚动监听
    var num = $("#card-msg").offset().top - $(window).scrollTop()
    $(window).scroll(function () {
        if ($(window).scrollTop() >= num) {
            $("#card-phone").css({position: "fixed", top: "0"});
            $("#card-msg").css("margin-left", "320px")
        } else {
            $("#card-phone").css({position: ""});
            $("#card-msg").css("margin-left", "25px")
        }
    });



    //渲染时间
    laydate.render({
        elem: '#BEGIN_TIMESTAMP'
        ,trigger: 'click' //采用click弹出
    });
    laydate.render({
        elem: '#END_TIMESTAMP'
        ,trigger: 'click' //采用click弹出
    });
    laydate.render({
        elem: '#garbStart_time',
        type: 'datetime'
    });
    laydate.render({
        elem: '#garbEnd_time',
        type: 'datetime'
    });


    exports(['card_edit'], {});
});

/*金额验证*/
function onumber(obj) {
    obj.value = obj.value.replace(/[^\d.]/g, "");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
    onText();
}
//检测文字
function onText() {
    var val = $(".card-title").val();
    var cash = $("#cash_number").val();
    var num = $(".card-title").val().length;
    var textarea1 = $("#DESCRIPTION").val().length;
    var textarea2 = $("#NOTICE").val().length;
    var inputNum1 = $(".inputNum1").val();
    var inputNum2 = $(".inputNum2").val();
    var fixedNum = $(".fixedNum").val();
    $(".tips-num").html(num);
    $(".tips-textarea1").html(textarea1);
    $(".tips-textarea2").html(textarea2);
    $(".card-shop-title").html(val);

    if (inputNum1 <= 0) {
        $(".inputNum1").val("")
    }
    if (inputNum2 <= 0) {
        $(".inputNum2").val("")
    }
}



function CompareDate(d1, d2) {
    return ((new Date(d1.replace(/-/g, "\/"))) > (new Date(d2.replace(/-/g, "\/"))));
}
function tipTop(id) {
    var a = "#"+id;
    $('html, body').animate({
        scrollTop : $(a).offset().top
    }, 100);
}

//满时长券，限制时长
function checkParam() {
    var cardSum_time = $("#cardSum_time").val();
    if(isNaN(cardSum_time)){
        layer.msg("请输入数字");
        $("#cardSum_time").val("");
    }

    cardSum_time = parseFloat(cardSum_time);
    if(cardSum_time <= 0){
        layer.msg("请输入有效值");
        $("#cardSum_time").val("");
    }else if(cardSum_time.toString().split(".")[1] != null && cardSum_time.toString().split(".")[1].length > 1){
        layer.msg("时长需精确小数点后一位");
        var a = cardSum_time.toString().split(".")[0]
        var b = cardSum_time.toString().split(".")[1]
        $("#cardSum_time").val(a+"."+b.substring(0,1));
    }
}
//满时长券，监听广告文字
function bannerText(o) {
    var othis = $(o),val = othis.val(),le = val.length;
    var remainLenght =  18 - le;
    $("#numtext").html(remainLenght);
    if(val == ""){
        $("#bannerText").html("连续上网满4小时送10元网费券一张")
    }else {
        $("#bannerText").html(val)
    }
}

//抵用券判断
function judeCard() {
    var handsel = $("#handsel").val();   //赠送金额
    var handsel_sum = $("#handsel_sum").val(); //赠送次数

    $("#CASH_NUMBER").val(handsel);

    if (handsel != "" && handsel_sum != "") {
        if(parseInt(handsel)<parseInt(handsel_sum)){
            message("赠送金额需大于赠送次数");
            $("#handsel_sum").val(1);
            $("#handsel").focus();
            return false;
        }
    }

    //判断是否等额
    if(handsel%handsel_sum != 0){
        $("#handsel_type1").prop("checked", false);
        $("#handsel_type1").removeAttr("checked");
        $("#handsel_type2").prop("checked", true);

        layui.define(['layer','form'],function (exports) {
            var form = layui.form;
            form.render("radio")
        });
    }

    var htmls = "";
    var num = 0;
    var hasMoney = 0;
    for (var i = 0; i < handsel_sum; i++) {
        var money = Math.round(handsel/handsel_sum);
        if(i == handsel_sum-1){
            money = handsel - hasMoney;
            if(hasMoney == handsel){
                break;
            }
        }
        hasMoney += money;

        num = i+1;
        htmls += '<div class="layui-inline">'+
                    '<label class="layui-form-label" style="padding-left:15px">第'+num+'次送：</label>'+
                    '<div class="layui-input-block">'+
                        '<input type="number" name="CASH_NUMBER'+i+'" id="CASH_NUMBER'+i+'" value="'+money+'" min="1" class="layui-input">'+
                    '</div>'+
                '</div>';
    }
    $("#CASH_NUMBER_LIST").html(htmls);

    if ($("#handsel_type1").is(':checked')) {
        $("#CASH_NUMBER_LIST :input").attr("readonly", true);
    }

}