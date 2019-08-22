
$(".li-tab").click(function () {
    $(this).addClass("layui-this");
    $(this).siblings().removeClass("layui-this");
    if($(this).text() == '群发图文') {
        loadIndex('');
    }else if($(this).text() == '群发消息'){
        loadMessageIndex('');
    }else if($(this).text() == '群发记录'){
        loadRecordIndex('');
    }
});

/*****************************************图文首页 -- 分割线****************************************************/
//加载图文数据列表
function loadIndex(keywords) {
    layui.use('element', function() {
        var $ = layui.jquery
            , element = layui.element;
    })

    $.ajax({
        type	: "POST",
        url		:basePath+'groupSend/loadIndex.do',
        data	:{keywords:keywords},
        dataType:'json',
        beforeSend:loading('加载中...'),
        success: function(data){
            layer.closeAll();
            if(data.errcode == 0){
                var varList = data.varList;
                var html = '';

                for(var i=0;i<varList.length;i++){
                    var baocun = '';
                    if(varList[i].STATE == 1){
                        baocun = '<p class="text-p"><span class="font-12 padding-30">保存于: '+varList[i].CREATE_TIME+'</span><span class="font-12 padding-80">未群发</span></p>';
                    }else if(varList[i].STATE == 2){
                        baocun = '<p class="text-p"><span class="font-12 padding-30">保存于: '+varList[i].SEND_TIME+'</span><span class="font-12 padding-80">已群发</span></p>';
                    }
                    html += '<div class="pin">' +
                                '<div class="box">' +
                                    '<div class="rowBoxCenter">'
                                        + baocun ;
                    for (var j = 0; j < varList[i].mList.length; j++) {
                        var mList = varList[i].mList[j];
                        if(j == 0){
                            html += '<div class="widthImg">'+
                                        '<img src="/uploadFiles/uploadImgs/'+mList.PATH+'" alt="">'+
                                        '<div class="wordTitle"><p>'+mList.TITLE+'</p></div>'+
                                    '</div>';
                        }else{
                            html += '<div class="borBottom">'+
                                        '<table width="100%"><tbody><tr>'+
                                        '<td style="vertical-align: top"><p class="tarTileWid">'+mList.TITLE+'</p></td>'+
                                        '<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img src="/uploadFiles/uploadImgs/'+mList.PATH+'" alt=""></div></td>'+
                                    '</tr></tbody></table></div>';
                        }
                    }

                    html += '<div class="tarWid">'+
                                '<div class="row">'+
                                '<div class="col-md-6 col-xs-6" onclick=editNews("'+varList[i].SENDRECORD_ID+'")>'+
                                '<a class="tarHover"><img src="'+basePath+'newStyle/images/bianji.png" alt="" width="15px"><span  class="tarStyle">编辑</span></a>'+
                            '</div>'+
                            '<div class="col-md-6 col-xs-6 text-right" onclick=delNews("'+varList[i].SENDRECORD_ID+'")>'+
                                '<a class="tarHover"><img src="'+basePath+'newStyle/images/shanchu.png" alt="" width="15px"><span  class="tarStyle">删除</span></a>'+
                            '</div></div></div>';

                    html += '</div></div></div>'
                }
                if(html == ''){
                    html = '暂无数据';
                }
                $("#main").html(html);

            }else {
                message(data.errmsg);
            }
        },
        error:function(){
            layer.closeAll();
            message("系统繁忙，请稍后再试");
        }
    });
}

//搜索(title)
$("#toSearch").click(function () {
    loadIndex($("#keywords").val())
});

//新增图文
function addNews(){
    window.open("groupSend/addNews");
}

//编辑图文
function editNews(id,flag){
    if(flag == "record"){
        window.open("groupSend/editNews.do?id="+id+"&flag=record");
    }
    window.open("groupSend/editNews.do?id="+id);
}

//删除图文
function delNews(id) {
    layer.confirm("确定要删除该图文吗？", {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            type: "POST",
            url:  basePath+"groupSend/delNews.do",
            data: {id:id},
            dataType: 'json',
            beforeSend: loading(''),
            success: function (data) {
                layer.closeAll();
                if(data.errcode == 0){
                    message("删除成功");
                    setTimeout(function () {
                        loadIndex('');
                    },800)
                }else{
                    message(data.errmsg);
                }
            },
            error: function () {
                layer.closeAll();
                message("系统繁忙，请稍后再试");
            }
        });
    });
}


/*****************************************图文编辑 -- 分割线****************************************************/
//图文数组
var item = [];

// initNews(id);
//编辑页面，初始化，渲染
function initNews(id) {
    $(".rowBoxCenter .borBottom").each(function () {
        var borBox = $(this).children(":first");
        borBox.mouseover(function () {
            $(this).parent().css("border","1px solid #a3d8f5")
        })
        borBox.mouseout(function () {
            $(this).parent().css("border","none")
            $(this).parent().css("border-bottom","1px solid #e1e1e1")
        })
    });

    createObj(0);
    $.ajax({
        type	: "POST",
        url		: basePath+"groupSend/loadNews.do",
        data	:{id:id},
        dataType:'json',
        beforeSend	: loading('加载中...'),
        success: function(data) {
            layer.closeAll();
            if(data.errcode == 0){
                var mList = data.mList;
                if(id != '' && mList.length == 0){
                    message("参数错误，查询数据失败");
                    return false;
                }
                //给左边赋值
                if(mList.length >= 1){
                    $("#div0").find(".div-img").attr("src", "/uploadFiles/uploadImgs/"+mList[0].PATH);
                    $("#div0").find(".tarTileWid").html(mList[0].TITLE);

                    //给第一个添加下移按钮
                    var div_down = '<div class="col-md-4 text-center"><p class="div-down" style="line-height: 40px;color:#fff;padding: 0">下移</p></div>';
                    $("#div0").find(".text-right").before(div_down);

                    //给当前图文赋值
                    $("#show_img").attr("src", "/uploadFiles/uploadImgs/" + mList[0].PATH);
                    $("#show_title").val(mList[0].TITLE);
                    $("#show_author").val(mList[0].CREATE_USER);
                    $("#show_digest").val(mList[0].DIGEST);
                    $("#show_source_url").val(mList[0].CONTENT_SOURCE_URL);
                    UE.getEditor("show_content").setContent(mList[0].CONTENT);

                    saveToItem(0);


                }
                var html = '';
                for (var j = 1; j < mList.length; j++) {
                    //判断为最后一篇时，没有下移按钮
                    var div_down = '<div class="col-md-4 text-center"><p class="div-down" style="line-height: 40px;color:#fff;padding: 0">下移</p></div>';
                    if(j == mList.length -1){
                        div_down = '';
                    }
                    html += '<div class="borBottom div-flag" id="div'+j+'">'+
                                '<table width="100%" class="tabHover"><tbody><tr>'+
                                    '<td style="vertical-align: top"><p class="tarTileWid">'+mList[j].TITLE+'</p></td>'+
                                    '<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img class="div-img" src="/uploadFiles/uploadImgs/'+mList[j].PATH+'"></div></td>'+
                                '</tr></tbody></table>'+
                                '<div class="wordTitle back">'+
                                    '<div class="hoverTar">'+
                                    '<div class="row" style="margin: 0;">'+
                                    '<div class="col-md-4 text-left"><p class="div-up" style="line-height: 40px;color:#fff;padding: 0">上移</p></div>'+
                                    div_down +
                                '<div class="col-md-4 text-right"><p class="div-del" style="line-height: 40px;color:#fff;padding: 0">删除</p></div>'+
                            '</div></div></div></div>';

                    //给数组赋值
                    var obj = {};
                    obj.SEQUENCE = mList[j].SEQUENCE;
                    obj.TITLE = mList[j].TITLE;
                    obj.CREATE_USER = mList[j].CREATE_USER;
                    obj.DIGEST = mList[j].DIGEST;
                    obj.CONTENT_SOURCE_URL = mList[j].CONTENT_SOURCE_URL;
                    obj.CONTENT = mList[j].CONTENT;
                    obj.PATH = "/uploadFiles/uploadImgs/"+mList[j].PATH;
                    item[j] = obj;
                }
                $("#div0").after(html);

            }else{
                message(data.errmsg);
            }
        },
        error:function(){
            layer.closeAll();
            message("系统繁忙，请稍后再试！");
            return false;
        }
    });


}

$("#clickImg").click(function () {
    $("#upFlie").click();
})
$("body").on("change","#upFlie",function() {
    var files = this.files
    uploadImg(files);
    // $("#upFlie").val('');
});
//上传图片
function uploadImg(f) {
    for(var i=0;i<f.length;i++){
        var reader = new FileReader();
        reader.readAsDataURL(f[i]);
        var tt = f[i].type;
        var size = f[i].size;
        reader.onload = function(e){
            var srces = e.target.result;
            var rule = /^(?:image\/jpeg|image\/png)$/i;
            if(rule.test(tt)){
                if(size > (2*1024*1024)){
                    layer.msg("图片大小不能超过2M",{time:1500});
                }else{
                    $.ajax({
                        type	: "POST",
                        url		: basePath+"groupSend/uploadImg.do",
                        data	:{upFile:srces},
                        dataType:'json',
                        beforeSend	: loading('上传中...'),
                        success: function(data) {
                            layer.closeAll();
                            if(data.errcode == 0){
                                message("上传成功");
                                $("#show_img").attr("src","/"+data.picture_url);
                                rightToActive("/"+data.picture_url);
                            }else{
                                message(data.errmsg);
                            }
                        },
                        error:function(){
                            layer.closeAll();
                            message("系统繁忙，请稍后再试！");
                            return false;
                        }
                    });
                }
            }else{
                message("请上传jpg或者png格式的图片");
            }
        }
    }
}


//新增图文块
$("#addDiv").click(function () {
    //将当前图文，保存到item
    var active_sequence = parseInt($(".div-active").attr("id").substring(3));
    saveToItem(active_sequence);
    //新增div，最多8篇
    var length = $(".div-flag").length;
    if(length > 7){
        message("最多添加8篇图文");
        return false;
    }

    var last_sequence = parseInt($(".div-flag").last().attr("id").substring(3));
    var id = "#div" + last_sequence;

    var div_down = '';
    var html = '<div class="borBottom div-flag" id="div'+(last_sequence+1)+'">'+
        '<table width="100%" class="tabHover"><tbody><tr>'+
        '<td style="vertical-align: top"><p class="tarTileWid">未命名</p></td>'+
        '<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img class="div-img" src="'+basePath+'newStyle/images/normalImg1.png"></div></td>'+
        '</tr></tbody></table>'+
        '<div class="wordTitle back">'+
        '<div class="hoverTar">'+
        '<div class="row" style="margin: 0;">'+
        '<div class="col-md-4 text-left"><p class="div-up" style="line-height: 40px;color:#fff;padding: 0">上移</p></div>'+
        div_down +
        '<div class="col-md-4 text-right" ><p class="div-del" style="line-height: 40px;color:#fff;padding: 0">删除</p></div>'+
        '</div></div></div></div>';
    $(id).after(html);
    //给上一个div添加下移按钮
    if($(id).find(".text-center").text() != '下移'){
        div_down = '<div class="col-md-4 text-center"><p class="div-down" style="line-height: 40px;color:#fff;padding: 0">下移</p></div>';
        $(id).find(".text-right").before(div_down);
    }

    //定位到新增的div
    var down_sequence = last_sequence + 1;
    var down_div = "#div" + down_sequence;
    $(down_div).addClass("div-active");
    $(down_div).siblings().removeClass("div-active");
    //在item中创建对象，且赋值到右侧图文
    createObj(down_sequence);
    itemToShow(down_sequence);
});

//选中图文块
$(document).on('click', '.div-flag', function(e){
    //将当前的保存到item
    var sequence = parseInt($(".div-active").attr("id").substring(3));
    saveToItem(sequence);

    //当前div设置div-active，其他的remove
    $(this).addClass("div-active");
    $(this).siblings().removeClass("div-active");

    //将当前div数据，显示到右侧的图文中
    var sequence = parseInt($(this).attr("id").substring(3));
    itemToShow(sequence);

    // console.log(item[sequence]);
});

//上移图文块
$(document).on('click', '.div-up', function(e){
    console.log(item);
    //保存当前的图文到item中
    var active_sequence = parseInt($(".div-active").attr("id").substring(3));
    saveToItem(active_sequence);

    //获取点击div的序号
    var sequence = parseInt($(this).parent().parent().parent().parent().parent().attr("id").substring(3));
    var div = "#div" + sequence;

    var up_sequence = sequence - 1;
    var up_div = "#div" + up_sequence;

    //将上一个div设置为div-active
    $(up_div).addClass("div-active");
    $(up_div).siblings().removeClass("div-active");

    //将当前div和上一个div之间替换图片和title
    var imgPath = $(div).find(".div-img").attr("src");
    var title = $(div).find(".tarTileWid").html();
    $(div).find(".div-img").attr("src", $(up_div).find(".div-img").attr("src"));
    $(div).find(".tarTileWid").html($(up_div).find(".tarTileWid").html());
    $(up_div).find(".div-img").attr("src", imgPath);
    $(up_div).find(".tarTileWid").html(title);

    //将item中的顺序替换
    var obj = item[sequence]
    item[sequence] =  item[up_sequence];
    item[up_sequence] = obj;

    //将上一个div显示到右侧的图文中
    itemToShow(up_sequence);

});
//下移图文块
$(document).on('click', '.div-down', function(e){

    //保存当前的图文到item中
    var active_sequence = parseInt($(".div-active").attr("id").substring(3));
    saveToItem(active_sequence);

    //获取点击div的序号
    var sequence = parseInt($(this).parent().parent().parent().parent().parent().attr("id").substring(3));
    var div = "#div" + sequence;

    var down_sequence = sequence + 1;
    var down_div = "#div" + down_sequence;
    //将下一个div设置为div-active
    $(down_div).addClass("div-active");
    $(down_div).siblings().removeClass("div-active");

    //将当前div和下一个div之间替换图片和title
    var imgPath = $(div).find(".div-img").attr("src");
    var title = $(div).find(".tarTileWid").html();
    $(div).find(".div-img").attr("src", $(down_div).find(".div-img").attr("src"));
    $(div).find(".tarTileWid").html($(down_div).find(".tarTileWid").html());
    $(down_div).find(".div-img").attr("src", imgPath);
    $(down_div).find(".tarTileWid").html(title);


    var obj = item[sequence]
    item[sequence] =  item[down_sequence];
    item[down_sequence] = obj;
    //将下一个div显示到右侧的图文中
    itemToShow(down_sequence);

});

//移除图文块
$(document).on('click', '.div-del', function(e){
    //获取点击div的序号
    var sequence = parseInt($(this).parent().parent().parent().parent().parent().attr("id").substring(3));
    var div = "#div" + sequence;
    if($(".div-flag").length == 1){
        message("不能再删除了哦！");
        return false;
    }

    //切换到当前图文
    $(div).addClass("div-active");
    $(div).siblings().removeClass("div-active");

    layer.confirm("确定要删除该图文吗？", {
        btn: ['确定', '取消']
    }, function () {
        //如果是最后一个div，就去除上一个的去除按钮
        if($(div).next().attr("id") == "addDiv"){
            var up_sequence = sequence - 1;
            var up_div = "#div" + up_sequence;
            $(up_div).find(".text-center").remove();
        }

        //移除div（第一个除外）
        if(sequence != 0){
            //定位到上一个div，赋值item到右侧的图文中
            var up_sequence = sequence - 1;
            var up_div = "#div" + up_sequence;
            $(up_div).addClass("div-active");
            $(up_div).siblings().removeClass("div-active");
            itemToShow(up_sequence);

            $(div).remove();
        }else{
            //定位不变，将第二个的值移到第一个
            var id = "#div1";
            $(div).find(".div-img").attr("src", $(id).find(".div-img").attr("src"));
            $(div).find(".tarTileWid").html($(id).find(".tarTileWid").html());
            itemToShow(1);

            $(id).remove();
            //若是仅剩最后一个，则移除下移按钮
            if($(".div-flag").length == 1){
                $("#div0").find(".text-center").remove();
            }
        }

        //移除item
        item.splice(sequence, 1);

        //不是最后一个，更改下面的div序号，更改item中对应的obj（下一个往上提）
        if($(div).next().attr("id") != "addDiv"){
            for (var i = sequence; i < $(".div-flag").length; i++) {
                if(i != 0){
                    var id = "#div" + (i+1);
                    $(id).attr("id", "div" + i);
                }
                item[i].SEQUENCE = i;
            }
        }

        console.log(item);

        layer.closeAll();
    });
});


//将当前图文保存到item
function saveToItem(i){
    if(item[i] != null){

        var content = UE.getEditor("show_content").getContent();
        content = content.replace(/'/g,"&#39;");
        item[i].SEQUENCE = i;
        item[i].TITLE = $("#show_title").val();
        item[i].CREATE_USER = $("#show_author").val();
        item[i].DIGEST = $("#show_digest").val();
        item[i].CONTENT_SOURCE_URL = $("#show_source_url").val();
        item[i].CONTENT = content;
        item[i].PATH = $("#show_img").attr("src");
    }
}
//将item中数据显示到当前图文
function itemToShow(i) {
    if(item[i] != null){
        $("#show_img").attr("src", item[i].PATH);
        if(item[i].PATH == ''){
            $("#show_img").attr("src", basePath+"newStyle/images/normalImg1.png");
        }
        $("#show_title").val(item[i].TITLE);
        $("#show_author").val(item[i].CREATE_USER);
        $("#show_digest").val(item[i].DIGEST);
        $("#show_source_url").val(item[i].CONTENT_SOURCE_URL);
        UE.getEditor("show_content").setContent(item[i].CONTENT);
    }
}
//创建item的下标对象
function createObj(i) {
    var obj = {};
    obj.SEQUENCE = i;
    obj.TITLE = '';
    obj.CREATE_USER = '';
    obj.DIGEST = '';
    obj.CONTENT_SOURCE_URL = '';
    obj.CONTENT =  '';
    obj.PATH = '';
    item[i] = obj;
}
//将右侧更改的数据，同步到左侧div-active的div块中
function rightToActive(picture_url) {
    var active_sequence = parseInt($(".div-active").attr("id").substring(3));
    var id = "#div" + active_sequence;
    //图片
    if(picture_url != ''){
        $(id).find(".div-img").attr("src", picture_url);
    }
    //标题
    $(id).find(".tarTileWid").html($("#show_title").val());
}


//保存图文
function saveNews() {
    var active_sequence = parseInt($(".div-active").attr("id").substring(3));
    saveToItem(active_sequence);
    if(!check("save")){
        return false;
    }
    $.ajax({
        type: "POST",
        url:  basePath+ 'groupSend/saveNews.do',
        data: {item:JSON.stringify(item),id:id},
        dataType: 'json',
        beforeSend: loading('提交中'),
        success: function (data) {
            layer.closeAll();
            if(data.errcode == 0){
                message("保存成功");
                id = data.sendRecordId;
                $("#sendRecordId").val(data.sendRecordId);
            }else{
                message(data.errmsg);
            }
        },
        error: function () {
            layer.closeAll();
            message("系统繁忙，请稍后再试");
        }
    });

}

//预览当前图文
function preview() {
    var active_sequence = parseInt($(".div-active").attr("id").substring(3));
    saveToItem(active_sequence);
    if(!check("preview")){
        return false;
    }
    $.ajax({
        type: "POST",
        url:  basePath+ 'groupSend/preview.do',
        data: {id:id, sequence: active_sequence, item: JSON.stringify(item)},
        dataType: 'json',
        beforeSend: loading('提交中'),
        success: function (data) {
            layer.closeAll();
            if(data.errcode == 0){
                message("保存成功");
                id = data.sendRecordId;
                $("#sendRecordId").val(data.sendRecordId);

                var img_base64 = data.img_base64;
                $('#preview_pic').attr("src", img_base64);
                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    area: '360px',
                    skin: 'layui-layer-nobg', //没有背景色
                    shadeClose: true,
                    content: $('#preview_pic')
                });
            }else{
                message(data.errmsg);
            }
        },
        error: function () {
            layer.closeAll();
            message("系统繁忙，请稍后再试");
        }
    });


}

//保存并群发
function groupSend() {
    var active_sequence = parseInt($(".div-active").attr("id").substring(3));
    saveToItem(active_sequence);
    if(!check("send")){
        return false;
    }
    saveNews();
    setTimeout(function () {
        layer.open({
            btn: ['确定', '关闭'],
            btn1: function(index, layero){
                //按钮的回调
                var res = window["layui-layer-iframe" + index].groupSend();
            },
            btnAlign: 'c',
            type: 2,
            title: '群发设置',
            shadeClose: false,
            shade: 0.8,
            area: ['620px', '65%'],
            content: [basePath + 'groupSend/goGroupSend.do?id=' + id],
        });
    },800)
}
//检查字段是否输入
function check(type) {
    if($("#show_title").val().trim() == ''){
        message('请输入标题');
        return false;
    }
    if($("#show_author").val().trim() == ''){
        message('请输入作者');
        return false;
    }
    if($("#show_img").attr('src').indexOf('newStyle/images/normalImg1.png') > 0){
        message('请选择图片');
        return false;
    }
    if(UE.getEditor("show_content").getContent().trim() == ''){
        message('请输入正文内容');
        return false;
    }
    if(type == 'preview'){
        return true;
    }
    for (var i = 0; i < item.length; i++) {
        var id = "#div" + i;
        if(item[i].TITLE == ''){
            $(id).addClass("div-active");
            $(id).siblings().removeClass("div-active");
            itemToShow(i);
            message('请输入标题');
            return false;
        }
        if(item[i].CREATE_USER.trim() == ''){
            $(id).addClass("div-active");
            $(id).siblings().removeClass("div-active");
            itemToShow(i);
            message('请输入作者');
            return false;
        }
        if(item[i].PATH.indexOf('newStyle/images/normalImg1.png') > 0){
            $(id).addClass("div-active");
            $(id).siblings().removeClass("div-active");
            itemToShow(i);
            message('请选择图片');
            return false;
        }
        if(item[i].CONTENT.trim() == ''){
            $(id).addClass("div-active");
            $(id).siblings().removeClass("div-active");
            itemToShow(i);
            message('请输入正文内容');
            return false;
        }

    }
    return true;
}



/*****************************************模板消息首页 -- 分割线****************************************************/

//加载模板消息数据列表
function loadMessageIndex(keywords) {
    layui.use('element', function () {
        var $ = layui.jquery
            , element = layui.element;
    })
    $.ajax({
        type: "POST",
        url:  basePath + "groupSend/loadMessageIndex.do",
        data: {keywords:keywords},
        dataType: 'json',
        beforeSend: loading('加载中'),
        success: function (data) {
            layer.closeAll();
            if(data.errcode == 0){
                var varList = data.varList;
                var html = '';

                for(var i=0;i<varList.length;i++){
                    var keyword_data = $.parseJSON(varList[i].keyword_data);
                    html += '<div class="card-box">'+
                                '<p class="card-title">服务器维护通知</p>'+
                                '<div class="card-msg">'+
                                    '<p>'+varList[i].first_data+'</p>'+
                                    '<p>维护时间：'+keyword_data.keyword1+'</p>'+
                                    '<p>恢复时间：'+keyword_data.keyword2+'</p><p></p>'+
                                    '<p>维护内容：'+keyword_data.keyword3+'</p>'+
                                    '<p>'+varList[i].remark_data+'</p>'+
                                '</div>'+
                                '<div class="card-btn-box"><div class="layui-row">'+
                                        '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">'+
                                            '<a class="bghover-success card-btn" title="编辑" onclick=editMessage("'+varList[i].id+'")><i class="layui-icon"></i></a>'+
                                        '</div>'+
                                        '<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 border">'+
                                            '<span class="bghover-danger card-btn" title="删除" onclick=delMessage("'+varList[i].id+'")><i class="layui-icon"></i></span>'+
                                        '</div></div>'+
                                '</div></div>';
                }
                if(html == ''){
                    html = '暂无数据';
                }
                $("#message_main").html(html);

            }else{
                message(data.errmsg);
            }
        },
        error: function () {
            message("系统繁忙，请稍后再试");
        }
    });
}

//搜索(title)
$("#message_toSearch").click(function () {
    loadMessageIndex($("#message_keywords").val())
});

//新增模板
function addMessage() {
    layer.open({
        btn: ['仅保存', '保存并群发', '关闭'],
        btn1: function(index, layero){
            //按钮的回调
            var res = window["layui-layer-iframe" + index].save();
        },
        btn2: function(index, layero){
            //按钮的回调
            var res = window["layui-layer-iframe" + index].sendMessage();
            return false;
        },
        btnAlign: 'c',
        type: 2,
        title: '新增模板消息',
        shadeClose: false,
        shade: 0.8,
        area: ['550px', '620px'],
        fixed: false, //不固定
        maxmin: true,
        content: [basePath + 'groupSend/addMessage.do'],
    });
}

//编辑模板
function editMessage(id,flag) {
    if(flag != "record"){
        layer.open({
            btn: ['仅保存', '保存并群发', '关闭'],
            btn1: function(index, layero){
                //按钮的回调
                var res = window["layui-layer-iframe" + index].save();
            },
            btn2: function(index, layero){
                //按钮的回调
                var res = window["layui-layer-iframe" + index].sendMessage();
                return false;
            },
            btnAlign: 'c',
            type: 2,
            title: '编辑模板消息',
            shadeClose: false,
            shade: 0.8,
            area: ['550px', '620px'],
            fixed: false, //不固定
            maxmin: true,
            content: [basePath + 'groupSend/editMessage.do?id=' + id],
        });
    }else{
        layer.open({
            btn: ['关闭'],
            btnAlign: 'c',
            type: 2,
            title: '编辑模板消息',
            shadeClose: false,
            shade: 0.8,
            area: ['550px', '620px'],
            fixed: false, //不固定
            maxmin: true,
            content: [basePath + 'groupSend/editMessage.do?id=' + id],
        });
    }

}

//删除模板
function delMessage(id) {
    layer.confirm("确定要删除该模板吗？", {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            type: "POST",
            url:  basePath+"groupSend/delMessage.do",
            data: {id:id},
            dataType: 'json',
            beforeSend: loading(''),
            success: function (data) {
                layer.closeAll();
                if(data.errcode == 0){
                    message("删除成功");
                    setTimeout(function () {
                        loadMessageIndex('');
                    },800)
                }else{
                    message(data.errmsg);
                }
            },
            error: function () {
                layer.closeAll();
                message("系统繁忙，请稍后再试");
            }
        });
    });
}





/*****************************************群发记录 -- 分割线****************************************************/


//加载记录数据列表
function loadRecordIndex(status) {
    loading('')
    layui.use(['layer','form','table','laypage','rate'], function() {
        var $ = layui.$
            , layer = layui.layer
            , form = layui.form
            , rate = layui.rate
            , laypage = layui.laypage
            , table = layui.table;
        //初始化列表
        table.render({
            id: 'record',
            elem: "#record_list",
            url: basePath + 'groupSend/loadRecordIndex.do',
            request: {
                pageName: 'currentPage',
                limitName: 'showCount'
            },
            loading:true,
            cellMinWidth: 150,
            skin: "line",
            size: "lg",
            cols: [[
                {type: 'numbers', width: 50, title: '序号',align: 'left'}
                , {field: 'type_name', title: '群发类型',align: 'left'}
                , {field: 'create_time', title: '群发时间',align: 'left'}
                , {field: 'make', title: '群发内容',  toolbar: '#barDemo', align: 'left'}
                , {field: 'internet_name', title: '群发公众号',align: 'left'}
            ]]
            ,
            done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                layer.closeAll();
                pageReload(count, res.showCount);
            },
            text: {none: '暂无相关数据'}
        });

        //渲染分页
        function pageReload(count, showCount) {
            laypage.render({
                elem: 'record_page'
                , count: count  //数据总数量
                , limit: showCount
                , limits: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
                , curr: location.hash.replace('#!fenye=', '') //获取起始页
                , hash: 'fenye' //自定义hash值
                , layout: ['prev', 'page', 'next', 'skip', 'count', 'limit']
                , theme: '#41a7f0'
                , jump: function (obj, first) {
                    //首次不执行
                    if (!first) {
                        //console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                        //console.log(obj.limit); //得到每页显示的条数
                        table.reload('record', {
                            where: { //设定异步数据接口的额外参数，任意设
                                status: $("#status").val(),
                                currentPage: obj.curr,
                                showCount: obj.limit
                            }
                        });

                    }
                }
            });
        }



        //表格操作监听
        table.on('tool(record)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            var tr = obj.tr;
            if (layEvent == 'detail') { //详情
                if(data.send_type == 1){//图文
                    editNews(data.foreign_id,"record");
                }else if(data.send_type == 2){//模板消息
                    editMessage(data.foreign_id,"record");
                }
            }

        });

        //搜索
        $("#record_toSearch").click(function () {
            table.reload('record', {
                elem: "#record_list",
                where: { //设定异步数据接口的额外参数，任意设
                    keywords: $("#keywords").val()
                }
            });
        });

    });

}






function ajaxSubmit(url, data) {
    $.ajax({
        type: "POST",
        url:  url,
        data: data,
        dataType: 'json',
        beforeSend: loading(''),
        success: function (data) {
            if(data.errcode == 0){

            }else{
                message(data.errmsg);
            }
        },
        error: function () {
            message("系统繁忙，请稍后再试");
        }
    });
}






