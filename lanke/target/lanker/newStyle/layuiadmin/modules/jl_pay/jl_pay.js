var module = "jialian";
var name = "嘉联";

var tableId = module + "Table";
var listUrl = module + "/getList";
var detailUrl = module + "/goEdit";
var updateAuditUrl = module + "/updateAuditStatus";
layui.define(['layer','table'],function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    $(top.hangge());//关闭加载状态
    //配置数据表格
    table.render({
        elem:"#"+tableId,
        cols:[[
            {type:'numbers'},
            {type:'checkbox',field:'id'},
            {field:'status',title:'状态',width:80,
                templet:function (d) {
                    if(d.status == 1){
                        return '<span>已开通</span>';
                    }else if(d.status == 2){
                        return '<span>待提交</span>';
                    }else if(d.status == 3){
                        return '<span>审核中</span>';
                    }else if(d.status == 4){
                        return '<span>未通过</span>';
                    }
                }
            },
            {field:'shop_no',title:'商户号',width:120},
            {field:'user_name',title:'用户名',width:140},
            {field:'store_name',title:'门店名称',width:150},
            {field:'merch_name',title:'商户名称',width:120},
            {field:'shop_contact_name',title:'联系人',width:100},
            {field:'rate',title:'费率',width:80},
            {field:'account_type',title:'结算类型',width:80,
                templet:function (d) {
                    if(d.account_type == 0){
                        return '<span>对私</span>';
                    }else if(d.status == 1){
                        return '<span>对公</span>';
                    }
                }
            },
            {field:'create_time',title: '申请时间',"templet":function (d) {
                    var timenum = d.create_time;
                    return '<span>'+gettime(timenum)+'</span>';
                }},
            {title:'操作', align:'center',
                templet:function (d) {
                    var editBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail"><i class="layui-icon layui-icon-edit"></i>详情</a>';
                    var delBtn = '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="audit"><i class="layui-icon layui-icon-delete"></i>查询审核</a>';
                    var updateBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="update"><i class="layui-icon layui-icon-edit"></i>修改信息</a>';
                    if(d.status == 1){
                        return editBtn + updateBtn;
                    }else if(d.status == 3){
                        return editBtn + delBtn;
                    }else{
                        return editBtn;
                    }

                }
            }
        ]],//表头
        skin:'row',//表单风格
        even:true,//开启隔行背景
        page:false,//开启分页
        url:listUrl,
        method:'post',
        where:{
            page:1,
            limit:10
        },//额外的参数
        parseData:function (res) {
            //接口返回({"code":0,"msg":"ok","count":1000,"data":[{},{}]})
            // res.status;//接口状态
            // res.message;//提示信息
            // res.total;//数据长度
            // res.data.item//数据列表
        }
    });

    //表格里的编辑和删除按钮触发
    table.on('tool('+tableId+')', function (obj) {
        if(obj.event == 'detail'){
            goDetail("详情", detailUrl, obj);
        }else if(obj.event == 'audit'){
            goAudit("审核", updateAuditUrl, obj);
        }else if(obj.event == 'update'){
            goUpdate("修改信息", obj);
        }
    });

    $("#toSearch").click(function(){
        table.reload(tableId, {
            where: { //设定异步数据接口的额外参数，任意设
                keywords: $("#keywords").val(),
                status: $("#status").val()
            }
        });
    })

    /**
     * 将时间毫秒数转化字符串
     * @param t
     * @returns {string}
     */
    function gettime(t){
        var _time=new Date(t);
        var   year=_time.getFullYear();//2017
        var   month=_time.getMonth()+1;//7
        tostr(month);
        var   date=_time.getDate();
        tostr(date);
        var   hour=_time.getHours();//10
        tostr(hour);
        var   minute=_time.getMinutes();//56
        tostr(minute);
        var   second=_time.getSeconds();//15
        tostr(second);
        return   year+"年"+month+"月"+date+"日   "+hour+":"+minute+":"+second;//这里自己按自己需要的格式拼接
    }

    /**
     * 将小于10的数字转为0+str
     * @param str
     */
    function tostr(str){
        if(parseInt(str)<parseInt(10)){
            str = '0'+str;
        }
    }


    function goDetail(name, url, obj){
        window.open(url + "?id=" + obj.data.id + "&store_id=" + obj.data.store_id);
    }

    function goAudit(name, url, obj){
        var data = new Object();
        data.id = obj.data.id;
        $.post(url, data, function (res) {
            layer.alert(res.errmsg,function () {
                layer.closeAll();
                table.reload(tableId, {
                    where: { //设定异步数据接口的额外参数，任意设
                        keywords: $("#keywords").val(),
                        status: $("#status").val()
                    }
                });
            });
        })
    }

    function goUpdate(name, obj){
        window.open("jialian/goEdit.do?idd=" + obj.data.id + "&store_idd=" + obj.data.store_id);
    }

    exports(["jl_pay"], {});
});