/*
 * 账户信息
 */
$(function() {
    // 密码修改
    $('#J_Password').bind('click', function() {
        var userId = $(this).attr('userid'),
            html = [];

        html.push(
            '<div class="row"><label>原始密码：</label><input type="text" class="base-input" placeholder="原始密码" id="J_Original"></div>',
            '<div class="row"><label>修改密码：</label><input type="password" class="base-input" placeholder="输入新密码" id="J_NewPassword"></div>',
            '<div class="row"><label>再输一遍：</label><input type="password" class="base-input" placeholder="再输一遍新密码" id="J_ConfirmPassword"></div>'
        );

        $.cpc.dialog.init({
            title: '修改密码',
            html: html.join(''),
            id: 'J_PasswordDialog',
            callback: function() {
                // 保存修改密码
                $('#J_PasswordDialog .J_Save').on('click', function() {
                    var oldPasswd = $.trim($('#J_Original').val()),
                        newPasswd = $.trim($('#J_NewPassword').val()),
                        confirmPwd = $.trim($('#J_ConfirmPassword').val());

                    if (oldPasswd === '') {
                        alert('请输入原始密码!');
                        return false;
                    }

                    if (confirmPwd === '') {
                        alert('请再次输入密码!');
                        return false;
                    }

                    if (newPasswd != confirmPwd) {
                        alert('两遍密码输入错误!');
                        return false;
                    }

                    var data = {
                        sysUserId: userId,
                        oldPasswd: $.cpc.util.encrypt(oldPasswd),
                        newPasswd: $.cpc.util.encrypt(newPasswd) // 加密
                    };

                    $.cpc.ajax.init(data, '/sys/user/updatePasswd');
                });
            }
        });
    });
});
