/**

 @Name：lanke 揽客文章库
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

    layui.use(['layer','form','table','laypage','rate'], function() {
        var $ = layui.$
            , layer = layui.layer
            , form = layui.form
            , rate = layui.rate
            , laypage = layui.laypage
            , table = layui.table;

        //渲染新增文章的推荐度
        rate.render({
            elem: '#rate'
            , half: true //开启半星
            , value: Number($('#rate').attr("name"))
            , text: true
            , choose: function (value) {
                $("#popularity").val(value)
            }
        })

        //监听文章类型下拉选择
        form.on('select(category)', function (data) {
            $("#category_id").val(data.value)
        });

        //表格推荐度初始化
        function popularity_rate(id, value) {
            var value = Number(value);
            rate.render({
                elem: '#' + id
                , value: value //初始值
                , half: true //开启半星
                , readonly: true
            })
        }

        //初始化揽客文章库列表
        table.render({
            id: 'article',
            elem: "#article_list",
            url: getRealPath() + '/articleLib/loadArticle.do',
            request: {
                pageName: 'currentPage',
                limitName: 'showCount'
            },
            loading:true,
            cellMinWidth: 150,
            skin: "line",
            size: "lg",
            cols: [[
                {type: 'numbers', width: 50, title: '序号'}
                , {field: 'img_title', title: '文章标题', templet: "#title", width: "35%"}
                , {field: 'name', title: '文章类型'}
                , {field: 'used_number', title: '使用数',sort:true}
                , {field: 'popularity', title: '推荐度', templet: '#popularity'}
                , {field: 'upload_time', title: '上传时间',sort:true}
                , {field: 'make', title: '操作', align: 'center', toolbar: '#barDemo', fixed: "right"}
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
                $(".popularityBox").each(function (index) {
                    var id = "rate" + index
                    $(this).attr("id", id)
                    var value = $(this).html();
                    popularity_rate(id, value)
                })

                pageReload(count, res.showCount);
            },
            text: {none: '暂无相关数据'}
        });

        table.on('sort(article)', function(obj){
            $(".popularityBox").each(function (index) {
                var id = "rate" + index
                $(this).attr("id", id)
                var value = $(this).html();
                popularity_rate(id, value)
            })
        });

        //表格操作监听
        table.on('tool(article)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            var tr = obj.tr;
            if (layEvent == 'edit') { //编辑
                article_edit(data.id, "edit")
            } else if (layEvent == "del") {// 删除
                layer.confirm('确定要删除？', {
                    btn: ['确定', '取消']
                }, function () {
                    $.ajax({
                        type: "POST",
                        url: getRealPath()+"/articleLib/deleteArticle.do",
                        data: {id: data.id},
                        dataType: 'json',
                        beforeSend: beforeSend(),
                        success: function (res) {
                            layer.closeAll();
                            layer.msg(res.message);
                            if(res.result){
                                setTimeout(function () {
                                    location.reload();
                                },800)
                            }
                        },
                        error: function () {
                            layer.closeAll();
                            layer.msg("系统繁忙，请稍后再试！");
                            return false;
                        }
                    });
                });
            } else if (layEvent == "add") {//添加到图文器
                compound_article(data.id)
            } else if (layEvent == "see"){ //查看
                //see_artticle(data.id)
            }
        });

        //渲染分页
        function pageReload(count, showCount) {
            laypage.render({
                elem: 'article_page'
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
                        table.reload('article', {
                            where: { //设定异步数据接口的额外参数，任意设
                                keywords: $("#keywords").val(),
                                currentPage: obj.curr,
                                showCount: obj.limit
                            }
                        });

                    }
                }
            });
        }

        //搜索
        $("#toSearch").click(function () {
            table.reload('article', {
                elem: "#article_list",
                where: { //设定异步数据接口的额外参数，任意设
                    keywords: $("#keywords").val()
                }
            });
        });

        //切换类型
        $(".casethis").click(function () {
            var id = $(this).attr("id");
            table.reload('article', {
                elem: "#article_list",
                where: { //设定异步数据接口的额外参数，任意设
                    keywords: $("#keywords").val(),
                    category_id: id
                }
            });
            $(this).addClass("active");
            $(this).siblings().removeClass("active");
        });


    })
        exports('article', {})
    })
//编辑文章
function article_edit(id,operation){
    window.open(getRealPath()+'/articleLib/goEditArticle.do?id='+id+'&operation='+operation)
}
//前往分类管理
function goCategory(){
    layer.open({
        btn: ['关闭'],
        btn1: function(index, layero){
            //按钮的回调
            var res = window["layui-layer-iframe" + index].callClose();
            if(res > 0 ){
                layer.confirm('还有'+res+'个分类未保存，是否确定不修改？', {
                    btn: ['确定','取消']
                }, function(){
                    layer.closeAll();
                })
            }else {
                layer.closeAll();
            }
        },
        btnAlign: 'c',
        type: 2,
        title: '文章分类管理',
        shadeClose: false,
        shade: 0.8,
        area: ['600px', '70%'],
        content:[ getRealPath()+'/articleLib/goEditCategory.do'],
    });
}

// 添加图文到合成器
function compound_article(id) {
    $.ajax({
        type	: "POST",
        url		:getRealPath()+'/articleLib/addNews.do',
        data	:{article_id:id},
        dataType:'json',
        success: function(data){
            if(data.result == "true"){
                numberChage(data.sequence)
            }else {
                layer.msg(data.message,{icon:2,anim: 6,offset: '60px'})
            }
        },
        error:function(){
            layer.msg("系统繁忙，请稍后再试");
        }
    });
}

//显示加载图文合成器
function  synthesizer_show(){
    $("#synthesizer").show();
    $.ajax({
        type	: "POST",
        url		:getRealPath()+'/articleLib/loadSynthesizer.do',
        success: function(data){
            if(data.result == "true"){
                var list = data.list
                ,html = "" ,make = "" ,className = "";
                $(list).each(function (i){
                    if((i+1) == list.length){
                        make = '<span data-type="up" onclick="synthesizer(this)">上移</span>';
                        className = "other-imageText";
                    }else if( i == 0){
                        make =  '<span data-type="down" onclick="synthesizer(this)">下移</span>'
                        className = "first-imageText";
                    }else {
                        make = '<span data-type="down" onclick="synthesizer(this)">下移</span>'+
                            '<span data-type="up" onclick="synthesizer(this)">上移</span>';
                        className = "other-imageText";
                    }
                   html += '<div class="'+className+' imageText-box" id="imageText'+list[i].sequence+'">'+
                                    '<div class="imageText-title">'+list[i].title+'</div>'+
                                    '<div class="imageText-img" style="background-image:url('+list[i].picture_url+')"></div>'+
                                    '<div class="imageText-makeBox">'+
                                        '<span data-type="del" onclick="synthesizer(this)">删除</span>'+
                                          make +
                                    '</div>'+
                                    '<input type="hidden" value="'+list[i].article_id+'" name="article_id">'+
                                    '<input type="hidden" value="'+list[i].picture_url+'" name="picture_url">'+
                             '</div>'
                })
                $("#synthesizer-body").html(html)
            }
        },
        error:function(){
            layer.msg("系统繁忙，请稍后再试");
        }
    });
}
//隐藏图文合成器
function synthesizer_colse() {
    $("#synthesizer").hide();
}
//图文合成器操作
function synthesizer(t){
    var othis = $(t)
    ,type = othis.data("type")
    ,now_id = "#"+othis.parent().parent().attr("id")
    ,num =  now_id.substr(now_id.length-1,1)
    ,next_id = "#imageText"+(Number(num)+1)
    ,prev_id = "#imageText"+(Number(num)-1);

    switch (type) {
        case "del":
            synthesizer_del(now_id,next_id,num,"delOne")
            break;
        case "down":
            move(num,"down",now_id,next_id);
            break;
        case "up":
            move(num,"up",now_id,prev_id);
            break;
    }
}
//删除
function synthesizer_del(now_id,next_id,num,flag){
   var text = "删除";
   var d = $(now_id).children("input[name=article_id]").val();
   if(flag != "delOne"){
       d = "",num = "",text = "清空";
   }
    layer.confirm('确定要'+text+'？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type	: "POST",
            url		:getRealPath()+'/articleLib/delNews.do',
            data	:{article_id:d,sequence:num,flag:flag},
            dataType:'json',
            success: function(data){
                var n = "";
                layer.closeAll();
                if(data.result == "true"){
                    if(flag == "delOne"){ //单个删除
                        if($(".imageText-box").length != 1){ //当图文数量大于1时，内容交换
                            exchange(now_id,next_id) //删除内容交换
                            $(next_id).remove(); //删除下一个节点
                            $(".imageText-box").each(function (i){ //图文重新排序
                                var id = "imageText"+(i+1)
                                $(this).attr("id",id)
                            })
                        }else { // 当图文数量等于1，直接删除
                            $(now_id).remove();
                        }
                        layer.msg(data.message,{icon:1,time:500});
                        n = Number($("#synthesizer-badge").html())-1;
                    }else { //全部清空
                        $("#synthesizer-body").html("");
                        n = 0;
                    }
                    numberChage(n) //红点数量
                }else {
                    layer.msg(data.message);
                }
            },
            error:function(){
                layer.msg("系统繁忙，请稍后再试");
            }
        });
    })
}

// 移动
function move(num,flag,now_id,exchangee){
    $.ajax({
        type	: "POST",
        url		:getRealPath()+'/articleLib/moveNews.do',
        data	:{sequence:num,flag:flag},
        dataType:'json',
        success: function(data){
            if(data.result == "true"){
                exchange(now_id,exchangee);//移动内容切换
            }
        },
        error:function(){
            layer.msg("系统繁忙，请稍后再试");
        }
    });
}

// 操作后内容切换
function exchange(now_id,exchangee) {
    var now_title = $(now_id).children(".imageText-title").html()
        ,now_article_id = $(now_id).children("input[name=article_id]").val()
        ,now_picture_url = $(now_id).children("input[name=picture_url]").val()
        ,exchangee_title = $(exchangee).children(".imageText-title").html()
        ,exchangee_article_id = $(exchangee).children("input[name=article_id]").val()
        ,exchangee_picture_url = $(exchangee).children("input[name=picture_url]").val();

        $(now_id).children(".imageText-title").html(exchangee_title)
        $(now_id).children(".imageText-img").attr("style",'background-image:url('+exchangee_picture_url+')');
        $(now_id).children("input[name=article_id]").val(exchangee_article_id)
        $(now_id).children("input[name=picture_url]").val(exchangee_picture_url);

        $(exchangee).children(".imageText-title").html(now_title)
        $(exchangee).children(".imageText-img").attr("style",'background-image:url('+now_picture_url+')');
        $(exchangee).children("input[name=article_id]").val(now_article_id)
        $(exchangee).children("input[name=picture_url]").val(now_picture_url);
}
//数量改变
function numberChage(n) {
    $("#synthesizer-badge").html(n);
    $("#synthesizer-badge").addClass("layui-anim-scaleSpring");
    setTimeout(function(){ $("#synthesizer-badge").removeClass("layui-anim-scaleSpring");}, 500);
}




// textarea 自适应高度
var textarea = document.getElementById("title-write");
makeExpandingArea(textarea);

function makeExpandingArea(el) {
    var setStyle = function(el) {
        el.style.height = 'auto';
        el.style.height = el.scrollHeight + 'px';
        // console.log(el.scrollHeight);
    }
    var delayedResize = function(el) {
        window.setTimeout(function() {
                setStyle(el)
            },
            0);
    }
    if (el.addEventListener) {
        el.addEventListener('input', function() {
            setStyle(el)
        }, false);
        setStyle(el)
    }else if (el.attachEvent) {
        el.attachEvent('onpropertychange', function() {
            setStyle(el)
        });
        setStyle(el)
    }
    if (window.VBArray && window.addEventListener) { //IE9
        el.attachEvent("onkeydown", function() {
            var key = window.event.keyCode;
            if (key == 8 || key == 46) delayedResize(el);

        });
        el.attachEvent("oncut", function() {
            delayedResize(el);
        }); //处理粘贴
    }
}
// 自动展开textarea
function autosize(obj) {
    var el = obj;
    setTimeout(function() {
        el.style.cssText = 'height:auto; padding:0';
        // for box-sizing other than "content-box" use:
        // el.style.cssText = '-moz-box-sizing:content-box';
        el.style.cssText = 'height:' + el.scrollHeight + 'px';
    }, 0);
}

//加载动画
function beforeSend(){
    layer.msg('加载中', {
        icon: 16
        ,shade: 0.01
    });
}
function beforeSend2(){
    layer.msg('数据量较大，图文正在合成中...', {
        icon: 16
        ,shade: 0.01
        ,time:100*1000
    });
}


//上传图片
$("#picture-btn").click(function(){
    $("#file").trigger("click");
    $("#file").off("change");
    $("#file").on("change",function(){
        uploadImg()
    })
})
function uploadImg(){
    var file = document.getElementById("file").files[0];
    var reader = new FileReader()
    reader.readAsDataURL(file);
    reader.onload = function(e){
        var src = e.target.result;
        beforeSend();
        render(src);
    }
}
var MAX_HEIGHT = 1000;
function render(src){
    var image = new Image();
    image.onload = function(){
        var canvas = document.createElement("canvas");
        if (image.height > MAX_HEIGHT && image.height >= image.width) {
            image.width *= MAX_HEIGHT / image.height;
            image.height = MAX_HEIGHT;
        }
        if(image.width > MAX_HEIGHT && image.width > image.height) {
            image.height *= MAX_HEIGHT / image.width;
            image.width = MAX_HEIGHT;
        }
        var ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        canvas.width = image.width;
        canvas.height = image.height;
        ctx.drawImage(image, 0, 0, image.width, image.height);
        var blob = canvas.toDataURL("image/jpeg");
        $.ajax({
            type	: "POST",
            url		:getRealPath()+'/articleLib/uploadImg.do',
            data	:{src:blob},
            dataType:'json',
            success: function(data) {
                layer.closeAll();
                if(data.result == "true"){
                    $("#picture_img").attr("style",'background:url('+data.picture_url+') no-repeat center center;background-size:100%;');
                    $("#picture_id").val(data.picture_id);
                    $("#picture_url").val(data.picture_url);
                }else{
                    layer.msg(data.message);
                }
            },
            error:function(){
                layer.closeAll();
                layer.msg("系统繁忙，请稍后再试");
            }
        });
    }
    image.src = src;
}
//发布或保存文章
function new_articleAdd(status) {
     $("#status").val(status);//文章状态
    var ue = UE.getEditor("content_write")
    ,title = $("#title-write").val()
    ,author = $("#author-write").val()
    ,digest = $("#digest_write").val()
    ,category_id = $("#category_id").val() //文章类型
    ,popularity = $("#popularity").val() //文章推荐度
    ,picture_id = $("#picture_id").val() //文章封面图
    ,content = ue.getContent()
    ,element_id = ["#title","#picture_id","#author","#category_id","#popularity","#content"]
    ,element_tip = ["文章标题不能空","封面图片不能为空","文章作者不能为空","文章类型不能为空","文章推荐度不能为空","文章内容不能为空"];
    $("#content").val(content)//文章内容
    $("#title").val(title);//文章标题
    $("#author").val(author);//文章作者w
    $("#digest").val(digest);//文章摘要

    if(status == 1){
        for (var i = 0; i < element_id.length; i++){
            if($(element_id[i]).val() == ""){
                layer.msg(element_tip[i])
                return false;
            }
        }
    }
    new_article($("#article_form").serialize());
}

function new_article(data){
    $.ajax({
        type	: "POST",
        url		:getRealPath()+'/articleLib/saveArticle.do',
        data	:data,
        dataType:'json',
        beforeSend	: beforeSend(),
        success: function(data){
            layer.closeAll();
            layer.msg(data.message);
            if(data.result == "true"){
                $("#id").val(data.id);
            }
        },
        error:function(){
            layer.closeAll();
            layer.msg("系统繁忙，请稍后再试");
        }
    });
}

function dddd() {
    layer.confirm('确定要合成吗？', {
        btn: ['确定','取消']
    }, function(){
        if($("#synthesizer-body").html().trim() != ''){
            //统计使用数
            $.ajax({
                type	: "POST",
                url		:getRealPath()+'/articleLib/totalNumber.do',
                data	:{},
                dataType:'json',
                beforeSend: beforeSend2(),
                success: function(data){
                    layer.closeAll();
                    if(data.result == "true"){
                        window.open(getRealPath() + "/sendRecord/goAdd.do?flag=synthesizer");
                    }else{
                        layer.closeAll();
                    }
                },
                error:function(){
                    layer.closeAll();
                    layer.msg("系统繁忙，请稍后再试");
                }
            });
        }else{
            layer.msg("请先添加图文");
        }
    })
}

function preview333() {
    new_articleAdd(2);
    setTimeout(function () {
        window.open(getRealPath()+'/articleLib/article.do?article_id='+$("#id").val());
    },150)
}
