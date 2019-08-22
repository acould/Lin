var moduleUrl = "enroll/";
var module = "Enroll";
var moduleName = "报名管理";

var getListUrl = moduleUrl + "getList";
var goEditUrl = moduleUrl + "goEdit";
var delUrl = moduleUrl + "del";

var tableId = "layTable";
var pageId = "layPage";
var iframeBtn = "laySubmit";

layui.define(['layer','table', 'laypage'],function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laypage = layui.laypage;

    var aaa = 1;
    var bbb = 10;

    //配置数据表格
    table.render({
        elem:"#"+tableId,
        cols:[[
            {type:'checkbox',field:'id'}
            ,{type:'numbers',title:'序号'}
            ,{field:'enroll_type',title:'报名类型',
                templet:function (d) {
                    if(d.team_type == 1){
                        return '<span>自由组队</span>';
                    }else if(d.team_type == 2){
                        return '<span>组队报名</span>';
                    }else{
                        return '<span>单人报名</span>';
                    }
                }
            }
            ,{field:'team_name',title:'队伍名称'}
            ,{field:'name',title:'用户名称'}
            ,{field:'phone',title:'联系电话'}
            ,{field:'create_time',title:'报名时间',
                templet:function (d) {
                    return '<span>'+d.create_time.substring(0, 19)+'</span>';
                }
            }
            /*,{title:'操作', align:'center',
                templet:function (d) {
                    var detailBtn = '<a class="layui-btn btn-success layui-btn-xs" lay-event="detail"><i class="layui-icon layui-icon-edit"></i>会员详情</a>';
                    if(d.CARDED != '' && d.CARDED != null){
                        return detailBtn;
                    }else{
                        return '';
                    }
                }
            }*/
        ]],//表头
        page:false,//开启分页
        loading:true,
        url:getListUrl+"?matches_id="+$("#matches_id").val(),
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
    table.on('tool('+tableId+')', function (obj) {
        if(obj.event == 'audit'){

        }else if(obj.event == 'detail'){

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
                str += checkData[i].id;
                if(i != checkData.length-1){
                    str += ",";
                }
            }

            //打印
            window.location.href = basePath + 'enroll/toExcel.do?keywords=' + $("#keywords").val() + '&str=' + str + '&matches_id=' + $("#matches_id").val();
        }
    };

    exports(['enroll'], {});
});