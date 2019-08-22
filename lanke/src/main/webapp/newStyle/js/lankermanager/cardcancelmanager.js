var moduleUrl = "cardCancelManager/";
var module = "cardcancelmanager";
var moduleName = "卡券核销管理";
var getListUrl = moduleUrl + "getList";
var getUpdateUrl = moduleUrl + "toUpdate";
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
            ,{field:'INTENET_NAME', title: '网吧名称', width:100}
            ,{field:'USER', title: '操作用户', templet:function (d) {
                    if(d.CONSUME_SOURCE == 'FROM_MOBILE_HELPER'){
                        return '<span>核销员-'+d.STAFF_NECK_NAME+'</span>';
                    }else if(d.CONSUME_SOURCE != 'FROM_MOBILE_HELPER'){
                        return '<span>'+d.NAME+'</span>';
                    }
                }}
            ,{field:'SUB_TITLE', title: '卡券名称', width:100}
            ,{field:'CARD', title: '优惠券码'}
            ,{field:'CARDED', title: '会员卡号'}
            ,{field:'NECK_NAME',title: '会员名称'}
            ,{field:'CARD_TICKET', title: '卡券类型', templet:function (d) {
                    if(d.CARD_TICKET == 1){
                        return '<span>现金券</span>';
                    }else if(d.CARD_TICKET  == 2){
                        return '<span>非现金券</span>';
                    }else{
                        return '';
                    }
                }}
            ,{field:'UPDATE_TIME',title: '核销时间',"templet":function (d) {
                    var timenum = d.UPDATE_TIME;
                    return '<span>'+gettime(timenum)+'</span>';
                    }}
            ,{field:'QRCODE_URL',width:120,title:'状态', templet:function (d) {
                    if(d.STATE=='2' && d.MONEY_STATE == '1'){
                        return '<span>已核销</span>';
                    }else if(d.STATE=='2' && d.MONEY_STATE == '2'){
                        return '<span>充值处理中</span>'
                    }else if(d.STATE=='2' && d.MONEY_STATE == '3'){
                        return '<span>充值失败</span>'
                    }else{
                        return '<span></span>'
                    }
                }}
            ,{field:'CASH_NUMBER',title: '金额' }
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
            /*$(".layui-table-cell").css({
                "height":"auto",
            });*/
        }
    });

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
    //表单中的点击事件
    $('.mail-btn').on('click',function () {
        //打印
        window.location.href = 'cardCancelManager/excel.do?keywords=' + $("#keywords").val() + '&cardtype=' + $("#cardtype").val() +'&state=' + $("#state").val();
    });


    table.on('tool(layTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var url = getUpdateUrl;
        var data = obj.data ;//获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值
        $.post(url, data, function (res) {
            if(res.message){
                layer.msg('更新成功');
                tableReload(1,10);
            }else{
                layer.msg("更新失败");
            }
        });
    });

    $("#toSearch").click(function(){
        tableReload(1,10);//重新加载表格
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
                cardtype: $("#cardtype").val(),
                state: $("#state").val(),
                currentPage: curr,
                showCount: limit
            }
        });
    }

    exports(['cardcancelmanager'], {});//提供外部访问接口
});