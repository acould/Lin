
var tableId = "layTable";
var pageId = "layPage";
var getListUrl = basePath + "msgManager/getUserList.do?kk=1";
layui.define(['layer','form', 'laydate','table','laypage','util'],function (exports) {
    var layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate,
        table = layui.table,
         util = layui.util,
        laypage = layui.laypage;

    // console.log("设置对象");
    var d = new Date();
    $("#begin_time").val(d.getFullYear() + '-' + lay.digit(d.getMonth()) + '-' + d.getDate());
    $("#end_time").val(d.getFullYear() + '-' + lay.digit(d.getMonth()+1) + '-' + d.getDate());

    if(store_ids != ''){
        getListUrl += '&store_ids=' + store_ids;
    }
    getListUrl += "&" +$('#selForm').serialize()
    //配置数据表格
    table.render({
        elem:"#"+tableId,
        cols:[[
            {field:'STORE_NAME',title:'门店名称'}
            ,{field:'NAME',title:'姓名' }
            ,{field:'CARDED',title:'卡号'}
            ,{field:'MEMBER_LEVEL',title:'等级'}
            ,{field:'UPTIME',title:'日均活跃时长'}
            ,{field:'OVERAGE_MONEY',title:'日均消费金额'}
            ,{field:'PRINCIPAL',title:'本金'}
            ,{field:'OVERAGE',title:'总余额'}
            ,{field:'DOWN_TIME',title:'最后上机时间'}
        ]],//表头
        page:false,//开启分页
        loading:true,
        cellMinWidth:100,
        url:'',
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
            layer.closeAll();
            //分页
            show = res.showCount;
            $("#mem_num").html(res.data.length);
            //pageReload(count, res.showCount);

            var varlist = res.data;
            var open_ids = '';
            for (var i = 0; i < varlist.length; i++) {
                open_ids += varlist[i].OPEN_ID + ',';
            }
            if(open_ids != ''){
                open_ids = open_ids.substring(0, open_ids.length - 1);
            }
            $("#open_ids").val(open_ids);
        }
    });


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
                    tableReload(obj.curr, obj.limit, true);
                }
            }
        });
    }

    function tableReload(curre,limit, isUrl){
        if ($("#send_obj2").is(":checked")) {
            loading('加载中')
        }
        getListUrl = basePath + "msgManager/getUserList.do?kk=1";
        if(store_ids != ''){
            getListUrl += '&store_ids=' + store_ids;
        }
        getListUrl += "&" +$('#selForm').serialize();

        if(!isUrl){
            getListUrl = '';
        }
        table.reload(tableId, {
            url:getListUrl,
            where: { //设定异步数据接口的额外参数，任意设
                currentPage: curre,
                showCount: limit
            }
        });
    }

    //搜索
    $("#toSearch").click(function () {
        //判断时间
        if(compareDate($("#begin_time").val(), $("#end_time").val())){
            message("查询结束时间需大于开始时间")
            return false;
        }
        //表单验证
        form.on('submit(toSearch)', function(data){
            tableReload(1, 10, true);
        })

    })

    form.verify({
        //大于0的数字
        moreZero: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value != '' && ( isNaN(value) || value < 0)) {
                return '请输入大于0的数字';
            }
        },
        time_required: function (value, item) { //value：表单的值、item：表单的DOM对象
            if ($("#send_obj2").is(":checked") && value == '') {
                return '请选择起始或结束时间';
            }
        },

    })
    form.on('select(record_time)', function(data){
        if(data.value == 5){
            $("#record_time").show();
        }else{
            $("#record_time").hide();
        }
    });

    form.on('radio(send_obj)', function(data){
        console.log();
        if(data.value == "member"){
            if(data.elem.title.indexOf("全部会员") >= 0){
                $("#member_div").hide();
                $("#result_p").hide();
            }
            if(data.elem.title.indexOf("部分会员") >= 0){
                $("#member_div").show()
                $("#result_p").show()
            }
        }else {
            $("#member_div").hide();
        }
    });


    laydate.render({
        elem: '#begin_time'
        ,value:  d.getFullYear() + '-' + lay.digit(d.getMonth()) + '-' + d.getDate()
    });

    laydate.render({
        elem: '#end_time'
        ,value: new Date()
    });

    //执行
    util.fixbar({
        top: true
        ,showHeight: '100'
        ,click: function(type){

        }
    });

    function compareDate(d1, d2) {
        return ((new Date(d1.replace(/-/g, "\/"))) > (new Date(d2.replace(/-/g, "\/"))));
    }

    exports(['set_edit'], {});
});
