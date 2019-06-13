/*
 * 仓库基本信息管理列表
 */

$(function() {
    // 删除仓库基本信息
    $('.J_Delete').click(function() {
        var text = $(this).closest('tr').find('td').eq(1).text(),
            id = $(this).data('id');

        if (confirm('是否删除仓库名称为：' + text)) {
            $.ajax({
                url: '/warehouse/info/delete',
                type: 'post',
                dataType: 'json',
                data: {
                    id: id
                },
                success: function(data) {
                    if (data.ret.success) {
                        alert('操作成功!');
                        window.location.reload();
                    } else {
                        alert(data.ret.resultDes);
                    }
                },
                error: function() {
                    alert('服务器出错了');
                }
            });
        }
    });
});
