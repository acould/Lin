/*
 * @create  :  2018/08/28
 * @author  :  xxh 22
 * @desc    :  微信菜单列表
 */
$(function() {
    var module = {
        init: function() {
            this.event();
        },

        //按钮等事件触发绑定
        event: function() {
            var self = this;

            this.$wrap = $('#wrap'); //外层定义的div 的id=wrap

            // 搜索
            $('.btn-search').click(function(e) {
                e.preventDefault();
                self.onSearch($(this));
            });

            // 删除权限
            this.$wrap.find('.J_Delete').click(function() {
                self.onDelete($(this));
            })

        },

        // 获取主菜单函数事件
        getMainMenu: function(callback) {
            var data = {
                appSettingId: $('select[name=appSettingId]').val()
            };
            this.ajax('/wechat/menu/getMainMenu', data, function(res) {
                if (typeof callback === 'function') {
                    callback(res);
                }
            });
        },

        // 菜单类型事件
        onMenuType: function($elem) {
            var value = $elem.val();
            var $parentMenuWrap = $('.parentMenuWrap');
            //如果选择子菜单，则 ajax请求获取主菜单list，并显示主菜单的select；否则隐藏。
            if (value === 'sub') {
                $parentMenuWrap.removeClass('dn'); //隐藏class 标签
                this.getMainMenu(function(mainMenu) {
                    var html = '';
                    for (var i = 0; i < mainMenu.length; i++) {
                        var item = mainMenu[i];
                        html += '<option value=' + (item.id || '') + '>' + (item.menuName || '') + '</option>'
                    }
                    $parentMenuWrap.find('select').html(html);
                });
            } else {
                $parentMenuWrap.addClass('dn');
            }
        },

        // 搜索权限
        onSearch: function($elem) {
            var $form = $elem.closest('form');

            $elem.closest('form').submit();
        },

        // 删除权限
        onDelete: function($elem) {
            var name = $elem.closest('tr').find('.gzhName').text(),
                data = {
                    id: $elem.data('id')
                };

            if (confirm('是否删除菜单名称为 “' + name + '” ?')) {
                this.ajax('/wechat/menu/delete', data, function() {
                    window.location.reload();
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
                    alert(res.resultDes || '删除失败');
                }
            }).fail(function(res) {
                alert('接口错误，请联系服务端！');
            });
        }
    };

    module.init();
});