/*
 * 货品存储区管理列表
 */

$(function() {
    // 删除货币类型
    $('.J_Delete').click(function() {
        var text = $(this).closest('tr').find('td').eq(1).text(),
            id = $(this).data('id');

        if (confirm('是否删除货品存储区名称为：' + text)) {
            $.ajax({
                url: '/product/storage/delete',
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
