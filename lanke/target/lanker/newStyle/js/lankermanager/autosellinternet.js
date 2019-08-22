var moduleUrl = "autosellmanager/";
var module = "autosellinternet";
var moduleName = "自动售货机";
var getInternetsellListUrl = moduleUrl + "getInternetsellList";
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
            ,{field:'intenet_name', title: '公众号',align: 'center',width:300}
            ,{field:'store_name', title: '当前门店',align: 'center',width:300}
            ,{field:'sm_no', title: '自助机编号',align: 'center'}
            ,{field:'make',title: '操作', align:'center', toolbar: '#barDemo',fixed: 'right',width:300}
        ]],//表头
        skin:'row',//表单风格
        even:true,//开启隔行背景
        page:false,//开启分页
        url:getInternetsellListUrl,
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

    function getRealPath(){
        //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
        var curWwwPath=window.document.location.href;
        //获取主机地址之后的目录，如： myproj/view/my.jsp
        var pathName=window.document.location.pathname;
        var pos=curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht=curWwwPath.substring(0,pos);
        //获取带"/"的项目名，如：/myproj
        var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
        //得到了 http://localhost:8083/myproj
        var realPath=localhostPaht+projectName;
        if(projectName == "/lanker"){
            return realPath
        }else{
            return localhostPaht
        }
    }


    table.on('tool(layTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data ;//获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值
        var store_id = data.store_id;
        var store_name = data.store_name;
        // if(layEvent=="edit"){
        //     layer.open({
        //         btn: ['添加','保存'],
        //         btn1: function(index, layero){
        //             //按钮的回调
        //             var res = window["layui-layer-iframe" + index].addtr();
        //
        //         },
        //         btn2: function(index, layero){
        //             //按钮的回调
        //             var res = window["layui-layer-iframe" + index].bc();
        //             // layer.closeAll();
        //             window.location.reload();
        //
        //         },
        //         skin: 'demo-class',
        //         btnAlign: 'c',
        //         type: 2,
        //         title: "自动售货机管理——"+store_name,
        //         shadeClose: false,
        //         shade: 0.8,
        //         area: ['800px', '55%'],
        //         fixed: false, //不固定
        //         maxmin: true,
        //         content: [getRealPath()+'/autosellmanager/goEdit.do?store_id=' + store_id],
        //     });
        // }
        var url = getRealPath()+'/autosellmanager/LoginAutomaten.do'
        if(layEvent=="edit2"){
            console.log(111)
            $.post(url, data, function (res) {
                window.open(res.urll);
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