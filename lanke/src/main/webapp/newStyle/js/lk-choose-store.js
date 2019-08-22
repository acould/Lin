layui.use('form', function () {
    var form = layui.form;
    //全选
    form.on('checkbox(tufure)', function (data) {
        var a = data.elem.checked;
        if (a == true) {
            $(".storeId").prop("checked", true);
            form.render('checkbox');
        }else {
            $(".storeId").prop("checked", false);
            form.render('checkbox');
        }
    });
    //反选
    // form.on('checkbox(f_all)', function (data) {
    //     var item = $(".storeId");
    //     item.each(function () {
    //         if ($(this).prop("checked")) {
    //             $(this).prop("checked", false);
    //         } else {
    //             $(this).prop("checked", true);
    //         }
    //     })
    //     form.render('checkbox');
    // });

    //有一个未选中全选取消选中
    form.on('checkbox(store)', function (data) {
        var item = $(".storeId");
        for (var i = 0; i < item.length; i++) {
            if (item[i].checked == false) {
                $("#allChecked").prop("checked", false);
                form.render('checkbox');
                break;
            }
        }
        //如果都勾选了  勾上全选
        var  all=item.length;
        for (var i = 0; i < item.length; i++) {
            if (item[i].checked == true) {
                all--;
            }
        }
        if(all==0){
            $("#allChecked").prop("checked", true);
            form.render('checkbox');
        }
    });

})
