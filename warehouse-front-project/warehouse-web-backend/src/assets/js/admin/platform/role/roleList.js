/*
 * 角色管理列表
 */

$(function() {
    // 删除角色
    $('.del').click(function() {
        var text = $(this).closest('tr').find('td').eq(1).text(),
            id = $(this).data('id');

        if (confirm('是否删除角色为：' + text)) {
            $.ajax({
                url: '/sys/role/delete',
                type: 'post',
                dataType: 'json',
                data: {
                    id: id
                },
                success: function(data) {
                    if (data.ret.success) {
                        alert('恭喜，操作成功!');
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
