var moduleUrl = "matches/";
var module = "Matches";
var moduleName = "赛事管理";

var getListUrl = moduleUrl + "getList";
var goEditUrl = moduleUrl + "goEdit";
var goEnrollUrl = "enroll/goList";
var delUrl = moduleUrl + "delMatches";

var tableId = "layTable";
var iframeBtn = "laySubmit";
layui.define(['layer','table', 'form', 'laydate'],function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        form = layui.form,
        laypage = layui.laypage;

    //初始化列表
    if(matchedFlag != 'edit'){
        init(null, 1, 10);
    }else{
        var laydate = layui.laydate;
        laydate.render({
            elem: '#enroll_time'
            ,range: '至'
            ,type: 'date'
        });
        laydate.render({
            elem: '#matches_time'
            ,range: '至'
            ,type: 'date'

        });

        //上传图片
        $("#picture-btn").click(function(){
            $("#file").trigger("click");
            $("#file").off("change");
            $("#file").on("change",function(){
                uploadImg()
            })
        })
        loading('加载中...');
        loadStores(null);
        setTimeout(function () {
            initEdit();
        },200)
        $(".matchesEditBtn").click(function(){
            var type = $(this).data('type');
            operate(type);
        })

    }

    //搜索关键词
    $("#toSearch").click(function(){
        var type = $(".active").data('type');
        init(type, 1, 10);
    })



    //切换tab
    $("#filtrate_module li").click(function () {
        $("#filtrate_module li").removeClass("active");
        $(this).addClass("active");
        var type = $(this).data('type');

        init(type, 1 , 10);
    })


    //初始化列表
    function init(type, curr, limit) {
        var obj = new Object();
        obj.keywords = $("#keywords").val();
        obj.currentPage = curr;
        obj.showCount = limit;
        if(type != null && type != ''){
            obj.state = type.split("_")[0];
            if(obj.state == '1'){
                obj.status = type.split("_")[1];
            }
        }

        $.post(getListUrl, obj, function (res) {
            //关闭加载状态
            $(top.hangge());

            if(res.code == 0){
                var count = res.count;
                var varList = res.data;

                // console.log(varList);
                //封装数据
                var htmls = '';
                for (var i = 0; i < varList.length; i++) {
                    var matches = varList[i];

                    var enroll_type = matches.enroll_type==1?"仅单人报名":"组队报名";
                    if(matches.state == 0){
                        matches.status_info = "未发布";
                    }

                    var enroll_html = '';
                    var sel_form = '';
                    var btnClass = '';
                    // if(matches.status != '3'){
                        enroll_html = '已报名'+matches.enroll_num+'人';
                        btnClass = 'layui-col-xs6 layui-col-sm4 layui-col-md4 border';
                        sel_form =  '<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border enrollList" data-id="'+matches.id+'" data-value="'+matches.name+'">'+
                                        '<span class="bghover-warning card-btn" title="报名表"><i class="layui-icon" >&#xe63c;</i></span>'+
                                    '</div>';
                    // }else {
                    //     btnClass = 'layui-col-xs6 layui-col-sm6 layui-col-md6 border';
                    // }
                    htmls += '<div class="match_box" id="'+matches.id+'">'+
                                '<div class="img">'+
                                    '<img src="'+matches.url+'" alt="" width="100%" height="100%">'+
                                '</div>'+
                                '<div class="content">'+
                                    '<p class="title">'+matches.name+'</p>'+
                                    '<p class="store">主办门店：'+matches.stores+'</p>'+
                                    '<p class="store">报名类型：'+enroll_type+'</p>'+
                                    '<p class="store">报名时间：'+matches.enroll_start_time.substring(0,10)+'&nbsp;至&nbsp;'+matches.enroll_end_time.substring(0,10)+'</p>'+
                                    '<p class="store">比赛时间：'+matches.start_time.substring(0,10)+'&nbsp;至&nbsp;'+matches.end_time.substring(0,10)+'</p>'+
                                    '<p class="status">'+
                                        '<font color="#FF6633">'+matches.status_info+'</font>'+
                                        '<span style="margin-left: 20px">'+enroll_html+'</span>'+
                                    '</p>'+
                                '</div>'+
                                '<div class="card-btn-box" style="background: #fff">'+
                                    '<div class="layui-row">'+
                                        '<div class="'+btnClass+' editMatches" data-id="'+matches.id+'">'+
                                            '<span class="bghover-success card-btn" title="编辑"><i class="layui-icon" >&#xe642;</i></span>'+
                                        '</div>'+
                                        sel_form +
                                        '<div class="'+btnClass+' delMatches" data-id="'+matches.id+'">'+
                                            '<span class="bghover-danger card-btn" title="删除"><i class="layui-icon" >&#xe640;</i></span>'+
                                        '</div>'+
                                    '</div>'+
                                '</div>'+
                            '</div>';
                }
                if(htmls == ''){
                    htmls = '<div style="width:100%;text-align: center;margin-top:100px;">暂无数据</div>';
                }
                $("#matchList").html(htmls);

                //去编辑页面
                $(".editMatches").click(function(){
                    var hh = "?id=" + $(this).data("id");
                    window.open(goEditUrl + hh);
                })

                //报名表
                $(".enrollList").click(function(){
                    var hh = "?matches_id=" + $(this).data("id") + "&name=" + $(this).data("value");
                    window.open(goEnrollUrl + hh);
                })

                //删除
                $(".delMatches").click(function(){
                    var obj = new Object();
                    obj.id = $(this).data("id");
                    layer.confirm("确认要删除？", {
                        btn: ['确定', '取消'],
                    }, function () {
                        $.post(delUrl, obj, function (res) {
                            message(res.errmsg);
                            if(res.errcode == 0){
                                var iid = "#" + obj.id;
                                $(iid).remove();
                            }
                    });
                    }, function () {
                        return
                    });
                })


                pageReload(count, limit);
            }else{
                message("查询失败");
            }
        });
    }


    //分页
    function pageReload(count,showCount){
        laypage.render({
            elem: 'layPage'
            ,count: count  //数据总数量
            , limit: showCount //每页展示的数量
            ,limits:[10, 20, 30, 40, 50,60,70,80,90,100]
            ,curr: location.hash.replace('#!fenye=', '') //获取起始页
            ,hash: 'fenye' //自定义hash值
            ,layout:['prev', 'page', 'next','skip','count','limit']
            ,theme: '#41a7f0'
            ,jump: function(obj, first){

                //首次不执行
                if(!first){
                    var type = $(".active").data('type');
                    init(type, obj.curr, obj.limit);
                }
            }
        });
    }

    //去新增页面
    $(".addMatches").click(function(){
        window.open(goEditUrl);
    })


    //初始化编辑页面
    function initEdit(){
        if($("#id").val() == ''){
            form.val("editForm", { "enroll_type":"1" });
            layer.closeAll();
            return false;
        }

        var field = new Object();
        field.id = $("#id").val();

        $.post("matches/getById.do", field, function (res) {
            layer.closeAll();

            if(res.errcode == 0){
                var data = res.data.pd;
                // console.log(data);

                var enroll_time = data.enroll_start_time.substring(0,10) + ' 至 ' + data.enroll_end_time.substring(0,10);
                var matches_time = data.start_time.substring(0,10) + ' 至 ' + data.end_time.substring(0,10);
                var enroll_type = data.enroll_type + "";
                // 渲染表单数据
                form.val("editForm", {
                    "name":data.name
                    ,"enroll_type":enroll_type
                    ,"description":data.description
                    ,"enroll_time":enroll_time
                    ,"matches_time":matches_time
                    ,"team_number":data.team_number
                })
                $("#picture_img").attr("style",'background:url('+data.url+') no-repeat center center;background-size:100%;');
                $("#picture_url").val(data.url);
                $("#cover").val(data.url);

                if(data.enroll_type == 2){
                    $("#team_number_box").show();
                }

                var ue = UE.getEditor("content");
                ue.setContent(data.content);

                var store_ids = data.store_ids.split(",");
                var stores = $(".store");
                for (var i = 0; i < store_ids.length; i++) {
                    for (var j = 0; j < stores.length; j++) {
                        if(store_ids[i] == $(stores[j]).data("id")){
                            $(stores[j]).addClass("active");
                            break;
                        }
                    }
                }
                if(store_ids.length == stores.length){
                    $("#all_store").addClass("active");
                }
                getStores();
                xiugai(data);
            }else{
                message(res.errmsg);
            }
        })
    }

    //单选框监听
    form.on('radio(enroll_type)', function(data){
        if($(data.elem).val() == 2){
            $("#team_number_box").show();
        }else {
            $("#team_number_box").hide();
        }
    });

    //发布赛事
    form.on('submit(save)', function(data){
        data.field.btnType = 'save';
        if($(data.elem).text() == "修改"){
            data.field.btnType = 'issue';
        }
        console.log(data.field);
        saveMatches(data);
    });
    form.on('submit(issue)', function(data){
        data.field.btnType = 'issue';
        if($(data.elem).text() == "取消发布"){
            data.field.btnType = 'save';
        }
        console.log(data.field);
        saveMatches(data);
    });

    //操作赛事
    function operate(type){
        loading('提交中...');
        //分享赛事
        if(type == 'share' || type == 'preview'){

        }
        layer.closeAll();
    }

    //异步保存
    function saveMatches(data){
        var field = data.field;
        var enroll_type = $('input:radio[name="enroll_type"]:checked').val();
        field.enroll_type = enroll_type + "";

        if(field.btnType == "save"){
            field.state = 0;
        }else if(field.btnType == "issue"){
            field.state = 1;
        }else if(field.btnType == "share" || field.btnType == "preview"){
            field.state = '';
        }
        field.btn = $(data.elem).text();

        var store_ids = '';
        var length = $(".active").length;
        $(".active").each(function (e) {
            store_ids += $(this).data("id");
            if(length-1 != e){
                store_ids += ",";
            }
        })
        if(store_ids == ''){
            message('请选择门店');
            return false;
        }
        field.store_ids = store_ids;

        if(field.enroll_type == "2"){
            if($("#team_number").val() < 2){
                $("#team_number").val("");
                error("组队人数不能少于2");
                return false;
            }else if($("#team_number").val() > 100){
                $("#team_number").val("");
                error("组队人数不能大于100");
                return false;
            }

        }

        if($("#cover").val() == ''){
            message('请上传封面图片');
            return false;
        }

        field.enroll_type = enroll_type;

        $.post("matches/saveMatches.do", field, function (res) {
            layer.closeAll();
            if(res.errcode == 0){
                message(field.btn + "成功");
                $("#id").val(res.data.id);

                xiugai(field);
            }else{
                message(res.errmsg);
            }
        })
    }

    function xiugai(field){
        if(field.btnType == "share" || field.btnType == "preview"){
           return false;
        }

    if(field.state == 1){
        $("#save").html("修改");
        $("#save").attr("data-type", "issue");

        $("#issue").html("取消发布");
        $("#issue").attr("data-type", "save");
    }else if(field.state == 0){
        $("#save").html("保存");
        $("#save").attr("data-type", "save");

        $("#issue").html("发布");
        $("#issue").attr("data-type", "issue");
    }

    if(field.btn == '发布' || field.btn == '修改'){
        $("#save").html("修改");
        $("#save").attr("data-type", "issue");

        $("#issue").html("取消发布");
        $("#issue").attr("data-type", "save");
    }else if(field.btn == '取消发布' || field.btn == '保存'){
        $("#save").html("保存");
        $("#save").attr("data-type", "save");

        $("#issue").html("发布");
        $("#issue").attr("data-type", "issue");
    }
}


    //分享预览
    form.on('submit(share)', function(data){
        var field = data.field;
        //先保存
        saveMatches(data);
        setTimeout(function () {
            data.field.btnType = 'matches_share';
            $("#id").val() != '' ? getQrcode(field) : message("请先保存赛事");
        }, 800);
    });
    //赛事预览
    form.on('submit(preview)', function(data){
        var field = data.field;

        //先保存
        saveMatches(data);
        setTimeout(function () {
            data.field.btnType = 'matches_preview';
            $("#id").val() != '' ? getQrcode(field) : message("请先保存赛事");
        }, 800);
    });

    function getQrcode(field){
        field.matches_id = $("#id").val();
        $.post('matches/getQrCode.do', field, function (res) {
            if(res.errcode == 0){
                var url = res.data.url;
                addcode(url);
            }else{
                message(res.errmsg);
            }
        })
    }

    //创建二维码
    function addcode(url){
        $("#qrcode").html('');
        var qrcode = new QRCode(document.getElementById("qrcode"), { // 创建一个二维码
            width : 210,//设置宽高
            height : 210,
            correctLevel: 1 //设置级别
        })
        qrcode.makeCode(url);

        $("#qrcode").show();
        layer.open({
            type: 1,
            shade: false,
            title: false, //不显示标题
            area: ['290px', '270px'], //宽高
            content: $("#qrcode"), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function(){
                $("#qrcode").hide();
            }
        });
    }



    exports(['matches'], {});
});