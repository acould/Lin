
var moduleUrl = "msgManager/";

var getListUrl = moduleUrl + "getList.do";
var delUrl = moduleUrl + "delRecord";
var delMoreUrl = moduleUrl + "delMoreRecord";
var informUrl = moduleUrl + "";
var goNotifyEdit = moduleUrl + 'goNotifyEdit.do?type='

var tableId = "layTable";
var pageId = "layPage";
var iframeBtn = "laySubmit";

layui.define(['layer','table', 'laypage'],function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laypage = layui.laypage;

    $(top.hangge());//关闭加载状态
    var aaa = 1;
    var bbb = 10;

    //初始化通知可发送次数
    initTimes();
    function initTimes(){
        $.post(basePath + 'msgManager/loadInitTimes.do','',function (res) {
            if(res.errcode == 0){
                $("#sm_times").html(res.data.pd.sm_times);
                $("#ef_times").html(res.data.pd.ef_times);
                $("#fr_times").html(res.data.pd.fr_times);
            }
        })
    }

    //配置数据表格
    table.render({
        elem:"#"+tableId,
        cols:[[
            {type:'checkbox',field:'id'}
            ,{type:'numbers',title:'序号'}
            ,{field:'store_names',title:'门店名称'}
            ,{field:'mass_type',title:'群发类型',
                templet:function (d) {
                    return '<span>'+getDes(d.type, d.temp_type, d.act_type)+'</span>';
                }
            }
            ,{field:'title',title:'群发标题'}
            ,{field:'sel_result',title:'群发对象'}
            ,{field:'create_time',title:'群发时间',
                templet:function (d) {
                    return '<span>'+d.create_time.substring(0, 19)+'</span>';
                }
            }
            ,{field:'make_btn',title:'操作',
                templet:function (d) {
                    var btn = '<a class="layui-btn btn-success layui-btn-xs" lay-event="detail"><i class="layui-icon layui-icon-edit"></i>查看</a>'+
                        '<a class="layui-btn btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>'
                    return btn ;
                }
            }
        ]],//表头
        page:false,//开启分页
        loading:true,
        url:getListUrl,
        method:'post',
        request:{
            pageName:'currentPage',
            limitName:'showCount'
        },
        parseData:function (res) {
            //接口返回({"code":0,"msg":"ok","count":1000,"data":[{},{}]})
            // res.status;//接口状态
            // res.message;//提示信息
            // res.total;//数据长度
            // res.data.item//数据列表

        }
        ,done: function(res, curr, count) {
            //分页
            show = res.showCount;
            pageReload(count, res.showCount);
        }

    });


    $("#toSearch").click(function(){
        tableReload(aaa,bbb);
    })

    function pageReload(count,showCount){
        laypage.render({
            elem: pageId
            ,count: count  //数据总数量
            ,limit: showCount //每页展示的数量
            ,limits:[10, 20, 30, 40, 50,60,70,80,90,100]
            ,curr: location.hash.replace('#!fenye=', '') //获取起始页
            ,hash: 'fenye' //自定义hash值
            ,layout:['prev', 'page', 'next','skip','count','limit']
            ,theme: '#41a7f0'
            ,jump: function(obj, first){
                //首次不执行
                if(!first){
                    show = obj.limit;
                    curr = obj.curr;
                    tableReload(obj.curr, obj.limit);
                }
            }
        });
    }

    function tableReload(curre,limit){
        table.reload(tableId, {
            where: { //设定异步数据接口的额外参数，任意设
                keywords: $("#keywords").val(),
                currentPage: curre,
                showCount: limit
            }
        });
    }


    //表格里的编辑和删除按钮触发
    table.on('tool('+tableId+')', function (obj){
        if(obj.event == 'detail'){
            if(obj.data.type == 'temp'){
                var url = goNotifyEdit + obj.data.temp_type + "&send_id=" + obj.data.id;
                openTempDatail(getDes(obj.data.type, obj.data.temp_type, obj.data.act_type), url);
            }else if(obj.data.type == 'act_msg'){
                openActDetail(obj.data.id, obj.data.act_type);
            }

        }else if(obj.event == 'del'){
            layer.confirm('真的删除行么', function(index){
                var field = new Object();
                field.id = obj.data.id;
                $.post(delUrl, field, function (res) {
                    message(res.errmsg);
                    if(res.errcode == 0){
                        setTimeout(function () {
                            obj.del();
                            layer.close(index);
                        },800)
                    }
                })
            });
        }
    });

    //表单中的点击事件
    $('.mail-btn').on('click',function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        all_del : function (){
            // 批量删除
                var checkStatus = table.checkStatus(tableId);
                var ids = '';
                var num = checkStatus.data.length;
                if( num<= 0){
                    message("请选择数据");
                    return false;
                }else {
                    layer.confirm("确定删除这"+num+"项", {
                        btn: ['确定', '取消']
                    }, function () {
                        for (var i = 0; i < num; i++) {
                            ids += checkStatus.data[i].id;
                            if(i != num-1){
                                ids += ",";
                            }
                        }
                        loading();
                        var field = new Object();
                        field.ids = ids;
                        $.post(delMoreUrl, field, function (res) {
                            layer.closeAll();
                            message(res.errmsg);
                            if(res.errcode == 0){
                                setTimeout(function () {
                                    location.reload();
                                },800)
                            }
                        })
                    });
                }

        }
        ,inform:function () {
            // 群发通知
            var f = function(index){
                var type =  $(".msgLayer_box_avtive").data("type");
                if(type == "" || type == undefined){
                    message("请选择群发类型");
                }else {

                    if(type == 'server_maintenance' && parseInt($("#sm_times").html()) <= 0){
                        message("每天仅可发送3次");
                        return false;
                    }else if(type == 'equipment_failure' && parseInt($("#ef_times").html()) <= 0){
                        message("每天仅可发送3次");
                        return false;
                    }else if(type == 'failure_recovery' && parseInt($("#fr_times").html()) <= 0){
                        message("每天仅可发送3次");
                        return false;
                    }

                    layer.closeAll();
                    var title = getDes("temp", type, '');
                    var c = "";
                    var url = goNotifyEdit + type;
                    var c = function(index, layero){
                        var res = window["layui-layer-iframe" + index].save();
                    }
                    openLayer(2, title, ['810px', '640px'], url, c);
                }
            }
            openLayer(1,"选择通知推送类型",['790px', '490px'], $("#inform_html"),f);
        }
        ,activity:function () {
            // 群发活动
            window.location.href = moduleUrl+"goActivityEdit.do";

            /*var f = function(index){
                var type =  $(".msgLayer_box_avtive").data("type");
                if(type == "" || type == undefined){
                    message("请选择群发类型");
                }else {
                    if(type == "public_show"){   //公众号显示
                        layer.closeAll();
                        window.location.href = moduleUrl+"goActivityEdit"
                    }else if(type == "imageText_matches"){  // 图文-群发赛事
                        layer.closeAll();
                        var matches_url = "matches/goSelMatches.do";
                        var m = function(index){
                            var res = window["layui-layer-iframe" + index].save();
                            if(res.match_id == "" || res.match_id == undefined){
                                message("请选择赛事")
                                return false
                            }else {
                                layer.closeAll();
                                window.open(moduleUrl+"goNewsEdit.do")
                            }
                        }
                        openLayer(2,"选择赛事",['630px', '80%'],matches_url,m)
                    }else if(type == "imageText_card"){      //图文-群发卡券
                        layer.closeAll();
                        var card_url = "card/goSelCard.do";
                        var c = function(index){
                            var res = window["layui-layer-iframe" + index].save();
                            if(res.card_id == "" || res.card_id == undefined){
                                message("请选择卡券")
                                return false
                            }else {
                                layer.closeAll();
                                window.open(moduleUrl+"goNewsEdit.do")
                            }
                        }
                        openLayer(2,"选择卡券",['704px','80%'],card_url,c)
                    }
                }
            }
            openLayer(1,"选择活动推送类型",['690px', '490px'],$("#activity_html"),f)*/
        }
    };

    // 打开弹层
    function openLayer(type,title,area,content,f){
        layer.open({
            btn: ['确定','关闭'],
            yes:f,
            type: type,
            title: title,
            shadeClose: false,
            shade: 0.8,
            area: area,
            fixed: false, //不固定
            maxmin: true,
            content:content,
        });
    }

    //查看模板消息
    function openTempDatail(title, url){
        layer.open({
            btn: ['关闭'],
            type: 2,
            title: title,
            shadeClose: false,
            shade: 0.8,
            area: ['810px', '640px'],
            fixed: false, //不固定
            maxmin: true,
            content:url,
        });
    }

    function openActDetail(send_id, act_type){
        window.location.href = moduleUrl + "goActivityEdit.do?send_id=" + send_id + "&act_type=" + act_type;
    }


    // 点击选中样式
    $(".msgLayer_box").click(function () {
        $(".msgLayer_box").removeClass("msgLayer_box_avtive");
        $(this).addClass("msgLayer_box_avtive");
    })

    $("#next_step").click(function (){
        $("#outside").hide();
        $("#inside").show();
        $("#outside").find(".msgLayer_box_avtive").removeClass("msgLayer_box_avtive");
    });

    $("#goback").click(function (){
        $("#inside").hide();
        $("#outside").show();
        $("#inside").find(".msgLayer_box_avtive").removeClass("msgLayer_box_avtive");
    })


    //判断群发的类型
    function getDes(type, temp_type, act_type){
        var typeInfo = "";
        switch (type) {
            case "temp":
                switch (temp_type) {
                    case "server_maintenance":
                        typeInfo = "服务器维护通知";
                        break;
                    case "equipment_failure":
                        typeInfo = "设备故障通知";
                        break;
                    case "failure_recovery":
                        typeInfo = "故障恢复通知";
                        break;
                }
                break;
            case "wx_news":
                typeInfo = "活动图文";
                break;
            case "act_msg":
                switch (act_type) {
                    case "card":
                        typeInfo = "卡券活动";
                        break;
                    case "matches":
                        typeInfo = "赛事活动";
                        break;
                    case "msg":
                        typeInfo = "活动消息";
                        break;
                }
        }
        return typeInfo;
    }

    exports(['mass_news'], {});
});