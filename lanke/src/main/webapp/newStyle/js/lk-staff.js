$(top.hangge());//关闭加载状态

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
layui.use(['table','laypage','form','layer','laydate'], function(){
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;
    var laypage = layui.laypage;
    var url = getRealPath() +"/internetStaff/loadStaffList.do";

    table.render({
        id: 'staffReload'
        ,elem: "#staff_list"
        ,url:url
        ,cellMinWidth: 95
        ,request:{
            pageName:'currentPage',
            limitName:'showCount'
        }
        ,cols: [[
            {type:'numbers', width:50, title: '序号',field:'id'}
            ,{field:'name', title: '通知接收员'}
            ,{field:'neck_name', title: '微信昵称'}
            ,{field:'store_name',title: '接收门店'}
            ,{field:'info', title: '接收服务类型'}
            ,{field:'make',title: '操作', align:'center',
                templet:function (d) {
                    var btn = '';
                    var editBtn = '<i class="iconfont lk_table_icon lk_table_icon_success" title="编辑" style="font-size: 15px;" lay-event="edit">&#xe637;</i>';
                    var delBtn = '<i class="iconfont lk_table_icon lk_table_icon_danger" title="删除" style="font-size: 17px;" lay-event="del">&#xe645;</i>';
                    btn = editBtn + delBtn;

                    if(d.state == '1'){
                        //已启用
                        var toBanBtn = '<i class="iconfont lk_table_icon lk_table_icon_warning" title="禁用" style="font-size: 17px;" lay-event="ban">&#xe635;</i>';
                        btn += toBanBtn;
                    }else if(d.state == '2'){
                        //已禁用
                        var toStartBtn = '<i class="iconfont lk_table_icon lk_table_icon_warning" title="启用" style="font-size: 17px;" lay-event="start">&#xe65c;</i>';
                        btn += toStartBtn;
                    }

                    return btn
                }
            }
            ]]
        ,done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            // console.log(res);
            //得到当前页码
            //console.log(curr);
            //得到数据总量
            //console.log(count);
            pageReload(count,res.showCount)
        }
        ,text: {none: '暂无相关数据'}
    });

    table.on('tool(staff)', function(obj){
        var data = obj.data;
        var layEvent = obj.event;
        var tr = obj.tr;
        if(layEvent == 'edit'){ //编辑
            layer.open({
                btn: ['确定','取消'],
                btn1: function(index, layero){
                    window["layui-layer-iframe" + index].save();

                },
                type: 2,
                title:'编辑',
                shadeClose: false,
                btnAlign: 'c',
                shade: 0.8,
                area: ['600px', '600px'],
                fixed: false, //不固定
                content:[getRealPath()+'/internetStaff/goEdit.do?id='+data.id],
            })
        }else if(layEvent == 'del') {
            layer.confirm('确定要删除？', {
                btn: ['确定','取消'] //按钮
            }, function(index){
                $.post(getRealPath() +'/internetStaff/delStaff.do', data, function (res) {
                    if(res.result == "true"){
                        message("删除成功");
                        setTimeout(function () {
                            location.reload();
                        },500)
                    }
                })
            }, function(index){
                layer.close(index);
            });
        }else if(layEvent == 'ban') {
            layer.confirm('确定要禁用？', {
                btn: ['确定','取消'] //按钮
            }, function(index){
                $.post(getRealPath() +'/internetStaff/editState.do', data, function (res) {
                    if(res.result == "true"){
                        message("禁用成功");
                        setTimeout(function () {
                            location.reload();
                        },500)
                    }
                })
            }, function(index){
                layer.close(index);
            });
        }else if(layEvent == 'start') {
            layer.confirm('确定要启用？', {
                btn: ['确定','取消'] //按钮
            }, function(index){
                $.post(getRealPath() +'/internetStaff/editState.do', data, function (res) {
                    if(res.result == "true"){
                        message("启用成功");
                        setTimeout(function () {
                            location.reload();
                        },500)
                    }
                })
            }, function(index){
                layer.close(index);
            });
        }
    });

    function pageReload(count, showCount){
        laypage.render({
            elem: 'page'
            ,count: count  //数据总数量
            ,limit:showCount
            ,limits:[10, 20, 30, 40, 50,60,70,80,90,100]
            ,curr: location.hash.replace('#!fenye=', '') //获取起始页
            ,hash: 'fenye' //自定义hash值
            ,layout:['prev', 'page', 'next','skip','count','limit']
            ,theme: '#41a7f0'
            ,jump: function(obj, first){
                //首次不执行
                if(!first){
                    //console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                    //console.log(obj.limit); //得到每页显示的条数

                    table.reload('staffReload', {
                        where: { //设定异步数据接口的额外参数，任意设
                            state: $("#state").val(),
                            keywords: $("#keywords").val(),
                            store_id: $("#store_id").val(),
                        }
                    });

                }
            }
        });
    }

    $("#toSearch").click(function(){
        table.reload('staffReload', {
            where: { //设定异步数据接口的额外参数，任意设
                state: $("#state").val(),
                keywords: $("#keywords").val(),
                store_id: $("#store_id").val(),
            }
        });
    })
});

//新增服务
function add() {
    layer.open({
        btn: ['已设置好，关闭'],
        btn1: function(index, layero){
            layer.close(index);
            location.reload();
        },
        type: 2,
        title:'新增接收服务',
        shadeClose: false,
        btnAlign: 'c',
        shade: 0.8,
        area: ['600px', '500px'],
        fixed: false, //不固定
        content:[getRealPath()+'/internetStaff/goAdd.do'],
    })
}

//创建二维码
function addcode(url){
    var qrcode = new QRCode(document.getElementById("qrcode"), { // 创建一个二维码
        width : 210,//设置宽高
        height : 210,
        correctLevel: 1 //设置级别
    })
    qrcode.makeCode(url);
}

//确定绑定
function save(){
    var flag = 0;
    $.each($("input[type='checkbox']"),function () {
        if( $(this)[0].checked){
            flag = flag + 1;
        }
    });
    if($("#name").val() == ""){
        message("请输入姓名");
        return false;
    }
    if($("#storeName").val() == ""){
        message("请选择服务门店");
        return false;
    }
    if(flag >= 1){
        confirm("是否确定绑定接收服务通知", function (){
            var data = $('#Form').serialize();
            $.post(getRealPath() +'/indexMember/saveStaff.do', data, function (res) {
                console.log(res)
                if(res.result == "true"){
                    $("#bind").hide();
                    $("#success").show();
                    layer.closeAll();
                }else{
                    message(res.message);
                }

            })

            // $.ajax({
            //     type: "POST",
            //     url: getRealPath() +'/internetStaff/saveStaff.do',
            //     data: $('#Form').serialize(),
            //     success: function(data){
            //         if(res.result == "true"){
            //             $("#bind").hide();
            //             $("#success").show();
            //             layer.closeAll();
            //         }
            //     },
            //     error: function(){
            //         layer.closeAll();
            //         message("系统繁忙，请稍后再试");
            //     }
            // });
        })
    }else {
        message("请选择接收服务")
    }
}



