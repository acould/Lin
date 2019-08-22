/**

 @Name：lanke 分类管理
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
    layui.use(['layer','form','table','laypage','rate'], function(){
        var $ = layui.$
            , layer = layui.layer
            , table = layui.table;

    })
    exports('category', {})

    //初始化加载
    ajaxSubmit("init", getRealPath() + "/articleLib/loadCategory.do", "");
})


// 点击方法
var re =  /^(0|\+?[1-9][0-9]*)$/;
function category_btn(t,id){
    var num = "";
    var name = "";
    var othis = $(t),type = othis.data("type");
    switch (type)
    {
        case "del":
            del(othis,id);
            break;
        case "edit":
            edit(othis,id);
            break;
        case "add":
            num  = $("#new_num").val();
            name  = $("#new_name").val();
            if(check(num,name)){
                add(num,name)
            }
            break;
        case "confirm":
            var list = []
            othis.parent().siblings().children().each(function (i){
                list[i] = $(this).val();
            })
            num = list[0];
            name = list[1];
            if(check(num,name)){
                confirm(othis,id)
            }
            break;
    }
}


//验证必填项
function check(num,name){
    if(num == ""){
        layer.msg("序号不能为空")
        return false
    }else if(!re.test(num)){
        layer.msg("序号必须为正整数")
        return false
    }
    if(name == ""){
        layer.msg("分类名称不能为空")
        return false
    }
    return true
}


// 添加
function add(num,name){
    var sequence = num;

    var data = {sequence:sequence,name:name};
    ajaxSubmit("add",getRealPath()+"/articleLib/saveCategory.do",data);


}
// 开启编辑
function edit(othis,id){
    var parent_tr = othis.parent().parent();
    parent_tr.find("input").removeClass("input_disabled");
    parent_tr.find("input").removeAttr("disabled");
    var html = '<div  class="category_btn confirm" data-type="confirm" onclick=category_btn(this,"'+id+'")>确定</div>';
    parent_tr.children("td:last-child").html(html);
    parent_tr.find("input[type=number]").focus();
}

//确定
function confirm(othis,id){
    var parent_td = othis.parent();

    var sequence = parent_td.prev().prev().children().val();
    var name = parent_td.prev().children().val();
    var data = {sequence:sequence,name:name,id:id};
    ajaxSubmit("edit",getRealPath()+"/articleLib/saveCategory.do",data,parent_td);


}

// 删除
function del(othis,id) {
    var parent_td = othis.parent().parent();
    layer.confirm('确定要删除？', {
        btn: ['确定','取消']
    }, function(){

        var data = {id:id};
        ajaxSubmit("delete",getRealPath()+"/articleLib/deleteCategory.do",data,parent_td);

    })
}

function callClose(){
    var length = $(".confirm").length;
    return length
}

function ajaxSubmit(flag, url, data2,parent_td){
    $.ajax({
        type	: "POST",
        url		: url,
        data	: data2,
        dataType:'json',
        beforeSend	: beforeSend(''),
        success: function(data){
            layer.closeAll();
            if(data.result == "true"){
                if(flag == "add"){
                    message(data.message);
                    var tr_html = '<tr>'+
                        '<td><input type="number" value='+data2.sequence+' placeholder="请输入序号"  class="layui-input input_disabled"  min="1" step="1"  disabled></td>'+
                        '<td><input type="text" value='+data2.name+'  placeholder="请输入分类名称"  class="layui-input input_disabled" maxlength="4"  disabled></td>'+
                        '<td>'+
                        '<i class="iconfont lk_table_icon_success lk_table_icon" title="编辑"  onclick=category_btn(this,"'+data.id+'") data-type="edit">&#xe637;</i>'+
                        '<i class="iconfont lk_table_icon lk_table_icon_danger" title="删除" style="font-size: 18px"  onclick=category_btn(this,"'+data.id+'") data-type="del">&#xe645;</i>'+
                        '</td>'+
                        '</tr>';
                    $("#addBox").before(tr_html)
                    $("#new_num").val("");
                    $("#new_name").val("");
                    //parent.location.reload();
                }else if(flag == "edit"){
                    message(data.message);

                    parent_td.siblings().children().addClass("input_disabled");
                    parent_td.siblings().children().attr("disabled","disabled");
                    var tr_html =  '<i class="iconfont lk_table_icon_success lk_table_icon" title="编辑"  onclick=category_btn(this,"'+data2.id+'") data-type="edit">&#xe637;</i>'+
                        '<i class="iconfont lk_table_icon lk_table_icon_danger" title="删除" style="font-size: 18px"  onclick=category_btn(this,"'+data2.id+'") data-type="del">&#xe645;</i>'
                    parent_td.html(tr_html);

                    //parent.location.reload();
                }else if(flag == "delete"){
                    message(data.message);
                    parent_td.remove();

                    //parent.location.reload();
                }else if(flag == "init"){
                    var categoryList = data.categoryList;
                    var tr_htmls = "";
                    for (var i = 0; i < categoryList.length; i++) {
                        tr_htmls += '<tr>'+
                            '<td><input type="number" placeholder="请输入序号"  class="layui-input input_disabled"  min="1" step="1"  disabled value="'+categoryList[i].sequence+'"></td>'+
                            '<td><input type="text" placeholder="请输入分类名称"  class="layui-input input_disabled" maxlength="4"  disabled value="'+categoryList[i].name+'"></td>'+
                            '<td>'+
                            '<i class="iconfont lk_table_icon_success lk_table_icon" title="编辑"  onclick=category_btn(this,"'+categoryList[i].id+'") data-type="edit">&#xe637;</i>'+
                            '<i class="iconfont lk_table_icon lk_table_icon_danger" title="删除" style="font-size: 18px"  onclick=category_btn(this,"'+categoryList[i].id+'") data-type="del">&#xe645;</i>'+
                            '</td>'+
                            '</tr>';
                    }
                    $("#addBox").before(tr_htmls);
                }
            }else{
                message(data.message);
            }
        },
        error:function(){
            layer.closeAll();
            layer.msg("系统繁忙，请稍后再试");
        }
    });
}