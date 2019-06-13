/*
 * 账户管理页
 */
$(function() {

    // 密码初始化/注销
    $('.default,.del').click(function(event) {
        var realname = $(this).closest('tr').find('.realname').text(),
            text = $(this).text(),
            isInit = $(this).hasClass('default'),
            url = isInit ? '/sys/user/initPasswd' : '/sys/user/delete',
            data = {
                sysUserId: $(this).data('id')
            };

        // 过滤删除管理员
        if (!isInit && realname === '管理员') {
            alert('管理员账号不能注销，若有需要请联系服务端！');
            return;
        }

        if (confirm('是否' + text + '账号：' + realname + '？')) {
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                data: data,
                success: function(data) {
                    if (data.ret.success) {
                        if (isInit) {
                            alert('密码初始化成功：1qaz2wsx');
                        } else {
                            alert('恭喜，操作成功！');
                        }

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
