/*
 * create  :  2016/12/26
 * author  :  wangwb(w.hoby@qq.com)
 * desc    :  登录
 */
$(function() {
    var Module = function() {

        this.init = function() {
            this.event();
        };

        /*
         * 事件绑定
         */
        this.event = function() {
            var self = this;

            this.$form = $('#loginForm');
            this.$msgbox = this.$form.find('.message');

            // 登录
            this.$form.find('.submit').click(function() {
                self.login();
            });

            // 回车登录
            this.$form.keydown(function(e) {
                if (e.keyCode == 13) {
                    $(this).find('.submit').click();
                }
            });

            // 清除菜单缓存
            sessionStorage.removeItem('menu');

            // 清除登录标志
             $.cookie('username', '', {expires: 0});
        };

        /*
         * 登录
         */
        this.login = function() {
            var self = this,
                url = this.$form.find('input[name=url]').val(),
                username = $.trim(this.$form.find('input[name=username]').val()),
                password = this.$form.find('input[name=password]').val(),
                code = this.$form.find('input[name=code]').val();

            if (!username) {
                this.$msgbox.text('请输入用户名');
                return false;
            }

            if (!password) {
                this.$msgbox.text('请输入密码');
                return false;
            }

            if (!code) {
                this.$msgbox.text('请输入验证码');
                return false;
            }

            var data = {
                username: username,
                password: $.cpc.util.encrypt(password),
                code: code
            };

            this.ajax('/doLogin', data, function(res) {
                if (res.success) {
                    $.cookie('username', res.result.realname);

                    // vue路由hash兼容
                    if (url.indexOf('/page/index.html') === 0) {
                        url = url + window.location.hash;
                    }

                    window.location.href = url || 'index.html';
                } else {
                    self.$msgbox.text(res.resultDes);
                }
            });
        };

        /*
         * ajax统一接口封装
         */
        this.ajax = function(url, data, callback) {
            $.ajax({
                url: url,
                type: 'post',
                data: data
            }).done(function(res) {
                callback(res.ret || res);

            }).fail(function(res) {
                alert('接口错误，请联系服务端！');
            });
        };

        /*
         * 初始化
         */
        this.init();
    };

    var module = new Module();
});
