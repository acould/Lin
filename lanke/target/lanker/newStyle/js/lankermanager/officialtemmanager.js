var moduleUrl = "templatemanager/";
var module = "officialtemmanager";
var moduleName = "公众号模板管理";
var getListUrl = moduleUrl + "getList";
var getUpdateUrl = moduleUrl + "update";
var getDeleteUrl = moduleUrl +"delete";
var goEditUrl = moduleUrl + "toEdit";
var delUrl = moduleUrl + "del";
var delMoreUrl = moduleUrl + "delMore";
var ajaxSave = moduleUrl + "save";
var tableId = "layTable";
var iframeBtn = "laySubmit";

layui.define(['layer','table','jquery'],function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laypage = layui.laypage;

    $(top.hangge());//关闭加载状态

    //配置数据表格
    table.render({
        elem:"#"+tableId,
        cols:[[
            {type:'checkbox',field:'id'}
            ,{type:'numbers', width:50, title: '序号'}
            //,{field:'internet_id',title: '公众号ID',hide:true}
            ,{field:'INTENET_NAME', title: '公众号名称'}
            ,{field:'template_id', title: '模板ID'}
            ,{field:'title', title: '标题'}
            ,{field:'primary_industry', title: '一级行业'}
            ,{field:'deputy_industry',title: '二级行业'}
            ,{field:'type', title: '状态'}
            ,{field:'make',title: '操作', align:'center', toolbar: '#barDemo',fixed: 'right'}
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
        }
        ,done: function(res, curr, count) {
            //分页
            pageReload(count, res.showCount);
        }
    });

    table.on('tool(layTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var delUrl = getDeleteUrl;
        var data = obj.data ;//获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'del') {
            layer.confirm('确定删除吗', function (index) {
                $.post(delUrl, data, function (res) {
                    if(res.message){
                        layer.msg('删除成功');
                        tableReload(1,10);
                    }else{
                        layer.msg("删除失败");
                    }
                });
            });
        }
    });

    $("#toSearch").click(function(){
        tableReload(1,10);//重新加载表格
    })

    $("#toUpdate").click(function(){
        var url = getUpdateUrl;
        var update = $("#toUpdate");
        update.hide();
        var jinxing = $("#jinxing");
        jinxing.show();
        $.post(url, "", function (res) {
            if(res.message){
                layer.msg('更新成功');
                update.show();
                jinxing.hide();
                tableReload(1,10);
            }else{
                layer.msg("更新失败");
            }
        });
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
                /*cardtype: $("#cardtype").val(),
                state: $("#state").val(),*/
                currentPage: curr,
                showCount: limit
            }
        });
    }

    exports(['officialtemmanager'], {});//提供外部访问接口
});