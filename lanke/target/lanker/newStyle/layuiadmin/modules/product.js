/**

 @Name：lanke 产品动态管理
 @Author：廖强
 @Site：http://www.lanke.com/

 */
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

layui.define(function(exports){

    layui.use(['layer','form','table','laypage'], function(){
        var $ = layui.$
            ,layer = layui.layer
            ,form = layui.form
            ,laypage = layui.laypage
            ,table = layui.table;
        table.render({
            id: 'product'
            ,elem: "#product_list"
            ,url:getRealPath()+'/productNews/lists.do'
            ,cols: [[
                {type:'numbers', width:50, title: '序号'}
                ,{field:'version', title: '版本号'}
                ,{field:'updatetime', title: '上线时间'}
                ,{field:'version_id', title: '版本详情',toolbar:"#version_body"}
                ,{field:'make',title: '操作', align:'center', toolbar: '#barDemo'}
            ]]
            ,done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
            }
            ,text: {none: '暂无相关数据'}
        });
        
        //操作监听
        table.on('tool(product)', function(obj){
            var data = obj.data;
            var layEvent = obj.event;
            var tr = obj.tr;
            if(layEvent == 'edit'){ //编辑
                productNews_toEditVersion(data.id)
            }else if(layEvent == "del"){// 删除
                layer.confirm('确定要删除？', {
                    btn: ['确定','取消']
                }, function(){
                    $.ajax({
                        type: "POST",
                        url: getRealPath()+'/productNews/deleteVersion.do' ,
                        data:{id:data.id},
                        dataType:'json',
                        beforeSend:beforeSend(),
                        success : function(data) {
            				layer.msg(data.message, {
            					time : data.message.length * 200
            				}, function() {
            					if ("true" == data.result) {
            						layer.closeAll();
            						location.reload();//刷新父页面	   
            					}
            				});
            			},
            			error : function() {
            				layer.msg("系统繁忙，请稍后再试！");
            			}
                    });
                });
            }else if(layEvent == "det"){//详情
                productNews_selete(data.id,data.version)
            }
        });

    })
    exports('product', {})
});



//产品版本编辑、新增
function productNews_toEditVersion(id){
    layer.open({
        btn: ['保存','关闭'],
        btn1: function(index, layero){
            //按钮的回调
            var res = window["layui-layer-iframe" + index].store_submit();

        },
        skin: 'demo-class',
        btnAlign: 'c',
        type: 2,
        title: '编辑产品动态',
        shadeClose: false,
        shade: 0.8,
        area: ['630px', '75%'],
        content: [getRealPath()+'/productNews/toEditVersion.do?version_id=' + id ]
    });
}

//产品版本详情
function productNews_selete(id,title){
    layer.open({
        btn: ['关闭'],
        btnAlign: 'r',
        type: 2,
        title: title+'版本详情',
        shadeClose: false,
        shade: 0.8,
        area: ['530px', '560px'],
        fixed: false, //不固定
        maxmin: true,
        content: [getRealPath()+'/productNews/selete.do?version_id=' + id ]
    });
}

//产品版本预告删除
function productNews_deleteNews(){
    layer.confirm('确定要删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: getRealPath()+'/productNews/deleteNews',
            dataType:'json',
            beforeSend:beforeSend(),
            success : function(data) {
				layer.msg(data.message, {
					time : data.message.length * 200
				}, function() {
					if ("true" == data.result) {
						layer.closeAll();
						location.reload();//刷新父页面	   
					}
				});
			},
			error : function() {
				layer.msg("系统繁忙，请稍后再试！");
			}
        });

    });
}
//产品版本预告编辑
function productNews_toEditNews(state){
    layer.open({
        btn: ['保存', '关闭'],
        btn1: function (index, layero){
            //按钮的回调
            var res = window["layui-layer-iframe" + index].productNews_submit();
        },
        skin: 'demo-class',
        btnAlign: 'c',
        type: 2,
        title: '编辑产品预告',
        shadeClose: false,
        shade: 0.6,
        area: ['620px', '60%'],
        fixed: false, //不固定
        maxmin: true,
        content: [getRealPath()+'/productNews/toEditNews.do?state='+state]
    });
}

function beforeSend(){
    layer.msg('加载中', {
        icon: 16
        ,shade: 0.01
    });
}