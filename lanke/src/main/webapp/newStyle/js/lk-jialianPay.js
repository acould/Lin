
var saveUrl = getRealPath() +"/jialian/saveShop";
var getUrl = getRealPath() +"/jialian/getById";
var getAuditInfo = getRealPath() +"/jialian/getAuditInfo";
var delImgUrl = getRealPath() +"/jialian/delImg";
var save_okUrl = getRealPath() +"/jialian/shareShopNo";
var saveType = "";
var status = "";
var user_type = $("#user_type").val();
var module_id = $("#module_id").val();
var store_id = $("#store_id").val();
var store_name = $("#store_name").html();
var account_type = "0";
var login_name = "";
var shop_no = "";
var shop_no_length = $("#shop_no_length").val();
layui.use(["layer","form","laydate"],function () {
    var layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate;
    laydate.render({
        elem: '#cert_start_date'
        ,format: 'yyyy.MM.dd'
    });
    laydate.render({
        elem: '#cert_expire_date'
        ,format: 'yyyy.MM.dd'
    });
    //表单验证
    form.verify({
        must:function (value, item){
            if(value =="" && saveType == "submit"){return '必填项不能为空'}
        }
        ,xwshsfzz: function(value, item){
            if(value =="" && saveType == "submit"){return '请上传身份证正面'}
        }
        ,xwshsfzb: function(value, item){
            if(value =="" && saveType == "submit"){return '请上传身份证背面'}
        }
        ,xwshscsfzzp: function(value, item){
            if(value =="" && saveType == "submit"){return '请上传手持身份证'}
        }
        ,xwshyhkzm: function(value, item){
            if(value =="" && saveType == "submit"){return '请上传银行卡正面'}
        }
        ,lk_identity:function (value, item) {
            if(!/(^\d{15}$)|(^\d{17}(x|X|\d)$)/.test(value) && saveType == "submit"){return '请输入正确的身份证号'}
        }
        ,lk_phone:function (value, item) {
            if(!/^1\d{10}$/.test(value) && saveType == "submit"){return '请输入正确的手机号'}
        }
        ,lk_email:function (value, item) {
            if(!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value) && saveType == "submit"){return '邮箱格式不正确'}
        }
        ,lk_zhongwen:function(value, item){
            if(!/^[\u4e00-\u9fa5]+$/.test(value) && saveType == "submit"){
                return '所填项存在非法格式'
            }
        }
    });

    if(parseInt(shop_no_length) > 0){
        $("#Form_x").show();
        $("#Form").hide();
    }else {
        $("#Form_x").remove();
    }

    //选择是否共用商户号
    form.on('radio(together)', function(data){
        if(data.value == "1"){
            $("#save_ok_box").show();
            $("#save_ok_shop_no").find("input").attr("lay-verify","must");
            $("#Form").hide();
        }else {
            $("#save_ok_box").hide();
            $("#save_ok_shop_no").find("input").attr("lay-verify","");
            $("#Form").show();
        }
    });
    //选择对账类型
    form.on('radio(account_type)', function(data){
        if(data.value == "1"){
            $("#union_bank_box").show();
            $("#union_bank_box").find("input").attr("lay-verify","must");
        }else {
            $("#union_bank_box").hide();
            $("#union_bank_box").find("input").attr("lay-verify","");
        }
    });

    form.on('submit(submit)', function(data){
        var field =data.field;
        field.id = module_id;
        field.store_id = store_id;
        field.save = saveType;
        delete field.file;
        console.log("-----start-----");
        console.log(field);

        loading("提交中");
        $.post(saveUrl, field, function (res){
            layer.closeAll();
            if(res.errcode == 0){
                module_id = res.data.id;
                $("#module_id").val(module_id);
                if(saveType == "onlySave"){
                    success("保存成功");
                }else {
                    saveSuccess();
                }
            }else {
                message(res.errmsg);
            }
        })
    });
    // 提交共用商户号
    form.on('submit(submit_ok)', function(data){
        var field =data.field;
        field.store_id = store_id;
        delete field.file;
        loading("提交中");
        $.post(save_okUrl, field, function (res){
            layer.closeAll();
            if(res.errcode == 0){
                success("共用商户号成功");
                setTimeout(function () {
                    location.reload();
                },500)
            }else {
                message(res.errmsg);
            }
        })
    });

    $(".save").click(function (){
        var othis = $(this);
        saveType = othis.data("type");
        if(saveType == "ok"){
            $("#save_ok").trigger('click');
        }else {
            $("#save").trigger('click');
        }
    });



    getInitial();
    //初始化数据
    function getInitial(){
        loading("加载中");
        //设置默认数据
        form.val("Form", { "account_type":account_type });
        citySelect("","","");
        if(module_id == null || module_id == '' || module_id == undefined || module_id == 'undefined'){  //新增不进行数据初始化
            layer.closeAll();
            return false;
        }
        $.post(getUrl, {'id':module_id,'store_id':store_id}, function (res){
            var data = res.data.pd;
            status = data.status;
            if(res.errcode == 0){  //请求成功

                // 步骤状态
                $(".flow-box").each(function (){
                    $(".flow-box").removeClass("flow-box-active");
                    if(data.status == "2" || data.status == "4"){
                        $(".flow-box").eq(0).addClass("flow-box-active")
                    }else if(data.status == "3"){
                        $(".flow-box").eq(0).addClass("flow-box-active");
                        $(".flow-box").eq(1).addClass("flow-box-active");
                    }else if(data.status == "1"){
                        $(".flow-box").addClass("flow-box-active");
                    }
                });
                login_name = data.mobile;
                shop_no = data.shop_no;
              // 渲染表单数据
                form.val("Form", {
                    "merch_name":data.merch_name
                    ,"shop_contact_name":data.shop_contact_name
                    ,"cert_no":data.cert_no
                    ,"cert_start_date":data.cert_start_date
                    ,"cert_expire_date":data.cert_expire_date
                    ,"mobile":data.mobile
                    ,"email":data.email
                    ,"account_type":data.account_type
                    ,"account_no":data.account_no
                    ,"account_name":data.account_name
                    ,"union_bank_name":data.union_bank_name
                    ,"union_bank_no":data.union_bank_no
                    ,"det_address":data.det_address
                    ,"xwshsfzz":data.xwshsfzz
                    ,"xwshsfzb":data.xwshsfzb
                    ,"xwshscsfzzp":data.xwshscsfzzp
                    ,"xwshyhkzm":data.xwshyhkzm
                })
                 // 对公类型显示联行号
                if(data.account_type == "1"){

                    $("#union_bank_box").show()
                }
                // 显示图片
                var sortList = ['xwshsfzz', 'xwshsfzb', 'xwshscsfzzp', 'xwshyhkzm'];
                var imgList =  [data.xwshsfzz,data.xwshsfzb,data.xwshscsfzzp,data.xwshyhkzm]
                for (var i = 0; i < imgList.length; i++){
                    var j = i +1 ;
                    var path = getRealPath()+"/"+imgList[i];
                    var imgHtml = '<div class="lk-pay-upImg" style="background: url('+path+') no-repeat center center;background-size:120%;" id="div'+j+'">'+
                        '<div class="img-operate">'+
                        '<p style="margin: 36px 0 20px 0;" onclick=delImg("'+j+'","'+sortList[i]+'") class="closeImg">删除</p>'+
                        '<p onclick=seeImg("'+path+'")>查看</p>'+
                        '</div>'+
                        '</div>';
                    if(imgList[i] != undefined){
                        active[sortList[i]] ? active[sortList[i]].call(this,imgHtml,j) : '';
                    }
                }

                if(status == 1){
                    $("#Form").show();
                    $("#Form_x").remove();
                }


                //渲染城市三级联动
                citySelect(data.prov_code,data.city_code,data.area_code);

                // 按钮和验证是否显示
                if((data.status == ""|| data.status == "2" || data.status == "4") && user_type == "user"){
                    $("#btn_box").show()
                    $("#code_box").show()
                }else if(data.status == "1" &&  user_type == "system"){
                    $("#code_box").remove();
                    $("#btn_box").remove();
                    $("#editBtn").show();
                }else{
                    $("#code_box").remove();
                    $("#btn_box").remove();
                    $("#editBtn").remove();
                    $("input").attr("disabled","disabled");
                    $("select").attr("disabled","disabled");
                    $(".closeImg").remove();
                    $(".img-operate p").attr("style","padding:56px");
                }
                // 加载审核日志
                audit();
            }else{  // 请求失败
                message(res.errmsg);
            }
        })
    }

    // 审核日志
    function audit(){
        $.post(getAuditInfo, {"id":module_id}, function (res){
            layer.closeAll();
            if(res.errcode == 0 ){
                var data = res.data.infoList;
                if(data.length > 0){
                    var no_pass_li = "";
                    var pass_li = "";
                    for (var j = 0; j < data.length; j++){
                        var check_time = data[j].check_time;
                        check_time = check_time.substring(0,16);
                        if(data[j].is_checked == "1"){
                            pass_li = '<li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
                                '<div class="layui-timeline-content layui-text">'+
                                '<div class="layui-timeline-title">'+
                                '<h2>开通成功</h2>'+
                                '<span class="layui-badge-rim">'+check_time+'</span>'+
                                '</div>'+
                                '<ul>'+
                                '<li style="margin: 10px 0;">您的门店'+store_name+'在线支付已开通成功，商户号为：<font color="red">'+shop_no+'</font></li>'+
                                '<li style="margin: 10px 0;">如需对账，可到' +
                                '<a href="https://www.jlpay.com/" target="_blank">嘉联支付平台</a>'+
                                '进行对账。登入账号为<font color="red">'+login_name+'</font>，初始密码为<font color="red">123456</font>'+
                                '<a class="layui-btn layui-btn-xs lk-pay-download" href="'+getRealPath()+'/register/account_check.do" target="_blank">更多对账信息</a>'+
                                '</li>'+
                                '<li style="margin: 10px 0;">快去为您的门店设置充值规则吧，菜单充值管理——充值规则</li>'+
                                '</ul>'+
                                '</div>'+
                                '</li>'
                        }else if(data[j].is_checked == "2"){
                            no_pass_li += '<li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>'+
                                '<div class="layui-timeline-content layui-text">'+
                                '<div class="layui-timeline-title">'+
                                '<h2>审核未通过</h2>'+
                                '<span class="layui-badge-rim">'+check_time+'</span>'+
                                '</div>'+
                                '<ul>'+
                                '<li>失败原因为:'+data[j].check_reason+'</li>'+
                                '</ul>'+
                                '</div>'+
                                '</li>'
                        }
                    }
                    var audit_html = '<div class="lanke-audit">'+
                        '<fieldset class="layui-elem-field layui-field-title" style="margin-top:30px">'+
                        '<legend>审核日志</legend>'+
                        '</fieldset>'+
                        '<ul class="layui-timeline" id="pass">'+
                        pass_li+
                        '</ul>'+
                        '<ul class="layui-timeline" id="noPass">'+
                        no_pass_li+
                        '</ul>'+
                        '</div>'
                    $("#audit_box").html(audit_html);
                }
            }else{
                message(res.errmsg);
            }
        })
    }
});

//提交开通资料
function saveSuccess() {
    layer.open({
        type: 1,
        btn:["确定"],
        yes:function(){
            //关闭当前页面
            setTimeout(function(){
                //关闭当前页面
                window.opener = null;
                window.open("","_self");
                window.close();
            },500)
        },
        area: '450px',
        title:false,
        closeBtn: 0, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: '<div style="text-align:center;padding:68px">'+
            '<i class="iconfont" style="color:#1ab394;font-size:60px;">&#xe66a;</i>'+
            '<p style="padding-top:20px;">提交成功，审核结果将会在1-3个工作日内发至您的手机上，请耐心等待~</p>'+
            '</div>'
    });
}




// 上传图片
$(".lk-pay-upload").mouseover(function(){
    if((status == '' || status == '2' || status == '4') && user_type == 'user') {
        var othis = $(this), type = othis.data('type');
        mouseover[type] ? mouseover[type].call(this, othis) : '';
    }
}).mouseout(function(){
    if((status == '' || status == '2' || status == '4') && user_type == 'user') {
        var othis = $(this), type = othis.data('type');
        mouseout[type] ? mouseout[type].call(this, othis) : '';
    }
}).click(function(){
    if((status == '' || status == '2' || status == '4') && user_type == 'user') {
        var othis = $(this), type = othis.data('type');
        $("#file").trigger("click");
        $("#file").off("change");
        $("#file").on("change", function () {
            uploadImg(type);
        })
    }
});


var i = 0;
function uploadImg(type){
    var file = document.getElementById("file").files[0];
    var reader = new FileReader()
    reader.readAsDataURL(file);
    loading("图片上传中");
    reader.onload = function(e){
        var src = e.target.result;
        render(src,type,i);
        i++
    }
}
var MAX_HEIGHT = 2000;
function render(src,type,i){
    var image = new Image();
    image.onload = function() {
        var canvas = document.createElement("canvas");
        console.log(image.height)
        if (image.height > MAX_HEIGHT && image.height >= image.width) {
            image.width *= MAX_HEIGHT / image.height;
            image.height = MAX_HEIGHT;
        }
        if(image.width > MAX_HEIGHT && image.width > image.height){
            image.height *= MAX_HEIGHT / image.width;
            image.width = MAX_HEIGHT;
        }
        var ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        canvas.width = image.width;
        canvas.height = image.height;
        ctx.drawImage(image, 0, 0, image.width, image.height);
        // document.getElementById('img').src = canvas.toDataURL("image/png");
        var blob = canvas.toDataURL("image/jpeg");
        layer.closeAll();
        var divs = '<div class="lk-pay-upImg" style="background: url('+blob+') no-repeat center center;background-size:120%;" id="div'+i+'">'+
            '<div class="img-operate">'+
            '<p style="margin: 36px 0 20px 0;" onclick=delImg("'+i+'","'+type+'")>删除</p>'+
            '<p onclick=seeImg("'+blob+'")>查看</p>'+
            '</div>'+
            '</div>';
        $("#"+type).val(blob);
        active[type] ? active[type].call(this,divs,i) : '';
    }
    image.src = src;
}


//查看图片
function seeImg(src2){
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        area: '600px',
        skin: 'layui-layer-nobg', //没有背景色
        shadeClose: true,
        move: 'img',
        moveOut: true,
        content: '<img src="'+src2+'" width="100%" />'
    });
}

//删除图片
function delImg(ii,type){
    if((status == "" || status == '2' || status == '4') && user_type == 'user'){
        var id = "#div"+ii;
        layer.confirm('确定要删除吗?', {
            btn: ['确定','取消'],
        }, function(index,layero){
            var obj = new Object();
            obj.sort = type;
            obj.id = module_id;
            if(module_id != ""){
                $.post(delImgUrl, obj, function (res){
                    del(id);
                    message(res.errmsg);
                });
            }else{
                del(id);
                message("删除成功");
            }
            layer.close(index)
        }, function(){
            return
        });
    }
}

function del(id) {
    $(id).remove();
    var lk_pay_upImg = $(".lk-pay-upImg").length;
    if (lk_pay_upImg == 0) {
        $(".pay-imgBox").css("height", "auto")
    }
    $("#file").val("");
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

var htmls = '点击上传';
//鼠标移入操作
var mouseover = {
    xwshsfzz:function(othis){
        var pp = '<p class="lk-pay-upTip">请上传当前商户联系人的身份证正面照片</p>'
        othis.html(htmls)
        $("#lk-payUp-upper").prepend(pp)
    },
    xwshsfzb:function(othis){
        var pp = '<p class="lk-pay-upTip">请上传当前商户联系人的身份证背面照片</p>'
        othis.html(htmls)
        $("#lk-payUp-upper").prepend(pp)
    },
    xwshscsfzzp:function(othis){
        var pp = '<p class="lk-pay-upTip">请上传当前商户联系人的手持身份证正面照片，要求露半身不遮挡脸部，且身份证清晰</p>'
        othis.html(htmls)
        $("#lk-payUp-lower").prepend(pp)
    },
    xwshyhkzm:function(othis){
        var pp = '<p class="lk-pay-upTip">请上传当前结算账号的银行卡正面</p>'
        othis.html(htmls)
        $("#lk-payUp-lower").prepend(pp)
    },
}

//鼠标移出操作
var mouseout = {
    xwshsfzz:function(othis){
        othis.html("身份证正面")
        $("#lk-payUp-upper").children("p").remove()
    },
    xwshsfzb:function(othis){
        othis.html("身份证背面")
        $("#lk-payUp-upper").children("p").remove()
    },
    xwshscsfzzp:function(othis){
        othis.html("手持身份证")
        $("#lk-payUp-lower").children("p").remove()
    },
    xwshyhkzm:function(othis){
        othis.html("银行卡正面")
        $("#lk-payUp-lower").children("p").remove()
    },
}
//鼠标点击操作
var active = {
    xwshsfzz:function(divs,i){
        $("#img1").html(divs);
    },
    xwshsfzb:function(divs,i){
        $("#img2").html(divs);
        $(".pay-imgBox").css("height","130px")
    },
    xwshscsfzzp:function(divs,i){
        $("#img3").html(divs);
    },
    xwshyhkzm:function(divs,i){
        $("#img4").html(divs);
        $(".pay-imgBox").css("height","130px")
    },
}









