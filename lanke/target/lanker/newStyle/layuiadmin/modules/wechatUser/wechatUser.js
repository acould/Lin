var moduleUrl = "wechatuser/";
var module = "WechatUser";
var moduleName = "会员列表";

var getListUrl = moduleUrl + "getList";
var goEditUrl = moduleUrl + "toEdit";
var delUrl = moduleUrl + "del";
var delMoreUrl = moduleUrl + "delMore";
var ajaxSave = moduleUrl + "save";
var memberDetailUrl = moduleUrl + "goView";


var tableId = "layTable";
var iframeBtn = "laySubmit";

layui.define(['layer','table'],function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laypage = layui.laypage;


    //配置数据表格
    table.render({
        elem:"#"+tableId,
        cols:[[
            {type:'numbers'},
            {type:'checkbox',field:'WECHAT_ID'},
            {field:'NECK_NAME',title:'昵称'},
            {field:'STORE_NAME',title:'性别',
                templet:function (d) {
                    if(d.SEX == '1'){
                        return '<span>男</span>';
                    }else if(d.SEX == '2'){
                        return '<span>女</span>';
                    }else{
                        return '<span>未知</span>';
                    }
                }
            },
            {field:'CARDED',title:'会员卡号',
                templet:function (d) {
                    if(d.CARDED == '' || d.CARDED == null){
                        return '<span>未绑定</span>';
                    }else{
                        return '<span>'+d.CARDED+'</span>';
                    }
                }
            },
            {field:'STORE_NAME',title:'绑定门店'},
            {field:'CREATE_TIME',title:'关注时间'},
            {field:'live',title:'活跃度'},
            {field:'balance',title:'卡余额'},
            {field:'consume',title:'消费能力'},
            {title:'操作', align:'center',
                templet:function (d) {
                    var detailBtn = '<a class="layui-btn btn-success layui-btn-xs" lay-event="detail"><i class="layui-icon layui-icon-edit"></i>会员详情</a>';
                    if(d.CARDED != '' && d.CARDED != null){
                        return detailBtn;
                    }else{
                        return '';
                    }
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
            $(top.hangge());//关闭加载状态
            //分页
            pageReload(count, res.showCount);
        }

    });

    $("#toSearch").click(function(){
        tableReload(1,10);
    })

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
                    tableReload(obj.curr, obj.limit);
                }
            }
        });
    }

    function tableReload(curr,limit){
        table.reload(tableId, {
            where: { //设定异步数据接口的额外参数，任意设
                STORE_ID: $("#STORE_ID").val(),
                huiyuan: $("#huiyuan").val(),
                SEX: $("#SEX").val(),
                live: $("#live").val(),
                balance: $("#balance").val(),
                consume: $("#consume").val(),
                keywords: $("#keywords").val(),
                currentPage: curr,
                showCount: limit
            }
        });
    }


    //表格里的编辑和删除按钮触发
    table.on('tool('+tableId+')', function (obj) {
        if(obj.event == 'audit'){

        }else if(obj.event == 'detail'){
            goDetail(memberDetailUrl, obj.data);
        }
    });

    //表单中的点击事件
    $('.mail-btn').on('click',function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        toImport: function () {
            layer.confirm("是否要导入公众号粉丝?</br>如若粉丝数量较多，将会在几分钟后导入完成", {
                btn: ['确定', '取消'],
            }, function () {
                $("#toImport").attr("data-type", "");
                $.post('wechatuser/includeOld.do','',function () {
                    layer.msg('导入中...请稍后查看');
                })
            }, function () {
                return
            });
        }
    };

    function goDetail(url,obj) {
        window.open(url + "?STORE_ID=" + obj.STORE_ID + "&USER_ID="+ obj.USER_ID + "&WECHAT_ID="+ obj.WECHAT_ID
            + "&live="+ obj.live + "&consume="+ obj.consume + "&balance="+ obj.balance);
    }


    exports(['wechatUser'], {});
});