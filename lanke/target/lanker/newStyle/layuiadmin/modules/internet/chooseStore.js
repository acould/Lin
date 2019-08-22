

layui.define(['layer','form'],function (exports) {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    var type = $("#type").val();
    init(type);

    //加载门店列表
    // $("#chooseStore").click(function(){
    //
    //     layer.open({
    //         type: 2,
    //         title: "选择门店",
    //         content: goChooseStorelUrl,
    //         area: ['500px', '480px'],
    //         btn: ['确定', '取消'],
    //         yes: function (index, layero) {
    //             var iframeWindow = window['layui-layer-iframe' + index],
    //                 btn = layero.find('iframe').contents().find('#'+iframeBtn);
    //
    //             //监听提交
    //             iframeWindow.layui.form.on('submit(' + iframeBtn + ')', function (data) {
    //                 var field = data.field;
    //
    //                 console.log(field);
    //                 if(JSON.stringify(field) == '{}'){
    //                     layer.msg("请至少选中一家门店！")
    //                     return false;
    //                 }
    //
    //                 return false;//阻止表单跳转
    //             });
    //
    //             //确认提交
    //             btn.trigger('click');
    //         }
    //     });
    //
    // })

    function init(type){
        var obj = new Object();
        obj.type = type;

        $.post('matches/chooseStore.do',obj,function (res) {
            if(res.errcode == 0){
                var storeList = res.data.list;
                var htmls = '';
                for (var i = 0; i < storeList.length; i++) {
                    var store = storeList[i];
                    htmls += '<input type="checkbox" name="store_id" lay-skin="primary" value="'+store.STORE_ID+'" title="'+store.STORE_NAME+'">';
                }
                if(htmls == ''){
                    htmls = '<div style="width:100%;text-align: center;margin-top:100px;">暂无数据</div>';
                }else{
                    htmls += '<input type="checkbox" lay-filter="checkAll" lay-skin="primary" title="全选">';
                }

                $("#storeList").html(htmls);
                form.render();
            }else{
                layer.msg(res.message);
            }
        })

        form.on('checkbox(checkAll)', function(data){
            console.log(data.elem); //得到checkbox原始DOM对象
            console.log(data.elem.checked); //是否被选中，true或者false
            console.log(data.value); //复选框value值，也可以通过data.elem.value得到
            console.log(data.othis); //得到美化后的DOM对象



        });

        form.on('checkbox(checkAll)', function (data) {
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="store_id"])');
            child.each(function (index, item) {
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });

        //通过判断机构是否全部选中来确定全选按钮是否选中
        form.on("checkbox(choose)", function (data) {
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="store_id"])');
            var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="store_id"]):checked')
            if (childChecked.length == child.length) {
                $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
            } else {
                $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
            }
            form.render('checkbox');
        })

    }

    exports(['chooseStore'], {});
});