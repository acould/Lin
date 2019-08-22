var moduleUrl = "storeReview/";
var module = "StoreVReview";
var moduleName = "计费审核";

var getListUrl = moduleUrl + "getList";
var goEditUrl = moduleUrl + "toEdit";
var delUrl = moduleUrl + "del";
var delMoreUrl = moduleUrl + "delMore";
var ajaxSave = moduleUrl + "save";


var tableId = "layTable";
var iframeBtn = "laySubmit";

layui.define(['layer','table'],function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laypage = layui.laypage;

    $(top.hangge());//关闭加载状态
    //配置数据表格
    table.render({
        elem:"#"+tableId,
        cols:[[
            {type:'numbers'},
            {type:'checkbox',field:'id'},
            {field:'STATE',title:'审核状态',width:100,
                templet:function (d) {
                    if(d.STATE == '1'){
                        return '<span>已通过</span>';
                    }else if(d.STATE == '2'){
                        return '<span>未审核</span>';
                    }else if(d.STATE == '3'){
                        return '<span>未通过</span>';
                    }
                }
            },
            {field:'Company_Name',title:'企业全称',width:100},
            {field:'STORE_NAME',title:'门店名称',width:100},
            {field:'INTENET_NAME',title:'公众号',width:100},
            {field:'SERVICE_ID',title:'服务商编号',width:100},
            {field:'CHOOSE_PACKAGE',title:'付费套餐',width:100,
                templet:function (d) {
                    if(d.CHOOSE_PACKAGE == '0'){
                        return '<span>试用一个月</span>';
                    }else if(d.CHOOSE_PACKAGE == '1'){
                        return '<span>付费一年</span>';
                    }else if(d.CHOOSE_PACKAGE == '2'){
                        return '<span>试用7天</span>';
                    }
                }
            },
            {field:'ADDTIME',title:'申请时间',width:120},
            {field:'EXPIRATION_TIME',title:'到期时间',width:120},
            {title:'操作', align:'center',
                templet:function (d) {
                    var auditBtn = '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="audit"><i class="layui-icon layui-icon-edit"></i>审核</a>';
                    var detailBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail"><i class="layui-icon layui-icon-edit"></i>查看</a>';
                    var officialBtn = '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="official"><i class="layui-icon layui-icon-edit"></i>转正</a>';
                    var delayBtn = '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delay"><i class="layui-icon layui-icon-edit"></i>延长时间</a>';
                    if(d.STATE == '2'){
                        return auditBtn;
                    }else if(d.STATE == '1'){
                        var btn = '';
                        if(d.CHOOSE_PACKAGE == '0'){
                            btn += officialBtn + delayBtn;
                        }
                        if(d.CHOOSE_PACKAGE == '1'){
                            btn += delayBtn;
                        }
                        btn += detailBtn;
                        return btn;
                    }else if(d.STATE == '3'){
                        return detailBtn;

                    }
                }
            }
        ]],//表头
        skin:'row',//表单风格
        even:true,//开启隔行背景
        page:false,//开启分页
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
                keywords: $("#keywords").val(),
                STATE: $("#STATE").val(),
                DATED:$("#DATED").val(),
                currentPage: curr,
                showCount: limit
            }
        });
    }


    //表格里的编辑和删除按钮触发
    table.on('tool('+tableId+')', function (obj) {
        if(obj.event == 'audit'){
            goAudit(goEditUrl, obj.data);
        }else if(obj.event == 'detail'){
            goDetail(goEditUrl, obj.data);
        }else if(obj.event == 'official'){
            goOfficial(moduleUrl+"ajaxOfficial.do", obj.data);
        }else if(obj.event == 'delay'){
            goDelay(moduleUrl+"ajaxDelay.do", obj.data);
        }


    });

    //表单中的点击事件
    $('.mail-btn').on('click',function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        toExcel: function () {
            var checkStatus = table.checkStatus(tableId),
                checkData = checkStatus.data;//获得选中的数据,对象数组

            if (checkData.length == 0)
                return layer.msg("请选择数据");

            var str = "";
            for (var i = 0; i < checkData.length; i++) {
                str += checkData[i].STORE_ID;
                if(i != checkData.length-1){
                    str += ",";
                }
            }

            //打印
            window.location.href = 'storeReview/excel.do?keywords=' + $("#keywords").val() + '&STATE=' + $("#STATE").val() + '&str=' + str;
        }
    };

    function goAudit(url, obj) {
        window.open(url + "?STORE_ID=" + obj.STORE_ID + "&STATE=0");
    }
    function goDetail(url,obj) {
        window.open(url + "?STORE_ID=" + obj.STORE_ID + "&STATE=1");
    }

    //转正
    function goOfficial(url, obj){
        layer.confirm("确认转正？", {
            btn: ['确定', '取消'],
        }, function () {
            var field = new Object();
            field.store_id = obj.STORE_ID;
            $.post(url,field,function (res) {
                if(res.errcode == 0){
                    message("转正成功");
                    tableReload(1, 10);
                }else{
                    message(res.errmsg);
                }
            })
        }, function () {
            return
        });
    }

    //延长
    function goDelay(url, obj){
        layer.prompt(
            {
                title: '请输入延长月份（整数）'
            },
            function(value, index, elem){
                var field = new Object();
                field.store_id = obj.STORE_ID;
                field.month = value;
                $.post(url,field,function (res) {
                    if(res.errcode == 0){
                        message("延长成功");
                        tableReload(1, 10);
                    }else{
                        message(res.errmsg);
                    }
                })
                layer.closeAll();
            }
        );
    }


    exports(['storeVReview'], {});
});