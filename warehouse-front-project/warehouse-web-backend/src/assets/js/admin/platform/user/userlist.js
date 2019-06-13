/*
 * created  :  2018/09/15
 * @desc    :  权限管理列表
 */
$(function() {
    var module = {
        init: function() {
            this.event();
        },

        event: function() {
            var self = this;

            this.$wrap = $('#wrap');

            // 保存权限
            this.$wrap.find('.J_initpasswdWrap').click(function() {
                self.onInipwd($(this));
            });


            // 删除权限
            this.$wrap.find('.J_Delete').click(function() {
                self.onDelete($(this));
            });
           
        },

        // 保存权限
        onInipwd: function($elem) {
            var realname = $(this).closest('tr').find('.realname').text(),
            text = $(this).text(),
            url = '/sys/user/initPasswd',
            data = {
                sysUserId: $elem.data('id')
            };

            if (confirm('是否初始化账号：' + realname + '的密码？')) {
                $.ajax({
                    url: url,
                    type: 'post',
                    dataType: 'json',
                    data: data,
                    success: function(data) {
                        if (data.ret.success) {
                           alert('密码初始化成功：'+data.ret.result);                           
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
        },

       

        // 删除权限
        onDelete: function($elem) {
           var realname = $(this).closest('tr').find('.realname').text(),
            text = $(this).text(),
            data = {
               sysUserId: $(this).data('id')
            };
             // 过滤删除管理员
            if (realname === '管理员') {
                alert('管理员账号不能注销，若有需要请联系服务端！');
                return;
            }

            if (confirm('是否删除展示名称为 “' + realname + '” ?')) {
                this.ajax('/sys/user/delete', data, function() {
                     if (data.ret.success) {
                        alert('恭喜，操作成功！');
                        window.location.reload();
                    } else {
                        alert(data.ret.resultDes);
                    }
                });
            }
        },

        // ajax统一接口封装
        ajax: function(url, data, callback) {
            $.ajax({
                url: url,
                type: 'post',
                data: data
            }).done(function(res) {
                res = res.ret || res;
                if (res.success) {
                    callback(res.result);
                } else {
                    alert(res.resultDes);
                }
            }).fail(function(res) {
                alert('接口错误，请联系服务端！');
            });
        }
    };

    module.init();
});