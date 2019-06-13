/*
 * 新增/编辑账户
 */
$(function() {

    // 保存账户
    $('.J_Save').click(function(event) {
        var id = $(this).data('id'),
            $baseInput = $('.base-input'),
            url = '',
            data = {};

        if (id === '') {
            url = '/sys/user/doAdd';
            data = {
                name: $.trim($baseInput.eq(0).val()),
                passwd: $.trim($baseInput.eq(1).val()),
                realname: $.trim($baseInput.eq(2).val())
            };

            if (data.name === '') {
                alert('用户名不能为空');
                return false;
            } else if (data.passwd === '') {
                alert('密码不能为空');
                return false;
            } else if (data.realname === '') {
                alert('展示名称不能为空');
                return false;
            }

            data.passwd = $.cpc.util.encrypt(data.passwd);

        } else {
            url = '/sys/user/update';
            data = {
                id: id,
                realname: $.trim($baseInput.eq(1).val())
            };

            if (data.realname === '') {
                alert('展示名称不能为空');
                return false;
            }
        }

        // 收集角色Id
        var roleIds = [];
        $('input[name=role]:checked').each(function(i, elem) {
            roleIds.push($(elem).val());
        });

        data.roleIds = roleIds.join();

        if (data.roleIds === '') {
            alert('请至少选择一个角色');
            return false;
        }

        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            data: data,
            async:false, 
            success: function(data) {
                if (data.ret.success) {
                     window.location.href = "/sys/user/list.html";
                    alert('恭喜，操作成功');
                   
                } else {
                    alert(data.ret.resultDes);
                }
            },
            error: function() {
                alert('服务器出错了');
            }
        });
    });

});
