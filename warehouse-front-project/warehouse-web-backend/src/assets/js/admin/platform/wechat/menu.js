/*
 * @create  :  2018/08/28
 * @author  :  xxh 22
 * @desc    :  微信公众号设置列表
 */
$(function() {
    var module = {
        init: function() {
            this.event();
            this.initEdit();
        },

        //按钮等事件触发绑定
        event: function() {
            var self = this;

            this.$wrap = $('#wrap'); //外层定义的div 的id=wrap

            // 保存权限
            this.$wrap.find('.J_Save').click(function() {
                self.onSave($(this));
            });

            // 搜索
            $('.btn-search').click(function(e) {
                e.preventDefault();
                self.onSearch($(this));
            });

            // 删除权限
            this.$wrap.find('.J_Delete').click(function() {
                self.onDelete($(this));
            });

            // 菜单类型事件
            $('[name=menuType]').change(function() {
                self.onMenuType($(this));
            });
            // 菜单事件类型 事件
            $('[name=eventType]').change(function() {
                self.onMenuEventType($(this));
            });
             // 是否需要授权 事件
            $('[name=needWxoauth]').change(function() {
                self.onNeedWxoauth($(this));
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
        onMenuType: function($elem, callback) {
            var value = $elem.val();
            var $parentMenuWrap = $('.parentMenuWrap');

            //如果选择子菜单，则 ajax请求获取主菜单list，并显示主菜单的select；否则隐藏。
            if (value === 'sub') {
                // $parentMenuWrap.removeClass('dn'); //隐藏class 标签
                $parentMenuWrap.removeClass('dn');
                this.getMainMenu(function(mainMenu) {
                    var html = '';
                    for (var i = 0; i < mainMenu.length; i++) {
                        var item = mainMenu[i];
                        html += '<option value=' + (item.id || '') + '>' + (item.menuName || '') + '</option>'
                    }
                    $parentMenuWrap.find('select').html(html);

                    if (typeof callback === 'function') {
                        callback($parentMenuWrap.find('select'));
                    }
                });
            } else {
                $parentMenuWrap.addClass('dn');
            }
        },
        // 菜单事件类型事件
        onMenuEventType: function($elem) {
            var $wrap = this.$wrap;
            var value = $elem.val();
            var $needWxoauthWrap = $('.needWxoauthWrap');
             var $needWxoauthScopeWrap = $('.needWxoauthScopeWrap');
            //如果选择链接或者，小程序 ，则显示选择是否必须微信授权选项,否则隐藏。
            if (value === 'link' || value === 'miniapp') {
                $needWxoauthWrap.removeClass('dn'); //隐藏class 标签
                if($wrap.find('select[name=needWxoauth]').val()=='y'){
                    $needWxoauthScopeWrap.removeClass('dn'); //隐藏class 标签
                }
            } else {
                $needWxoauthWrap.addClass('dn');
                $needWxoauthScopeWrap.addClass('dn');
            }
        },
         // 是否需要授权事件
        onNeedWxoauth: function($elem) {
            var value = $elem.val();
             var $needWxoauthScopeWrap = $('.needWxoauthScopeWrap');
            if (value === 'y' ) {
                $needWxoauthScopeWrap.removeClass('dn'); //隐藏class 标签
            } else {
                $needWxoauthScopeWrap.addClass('dn');
            }
        },

        // 保存权限
        onSave: function($elem) {
            var $wrap = this.$wrap,
                data = {
                    id: $elem.data('id'),
                    appSettingId: $.trim($wrap.find('select[name=appSettingId]').val()),
                    menuName: $.trim($wrap.find('input[name=menuName]').val()),
                    menuType: $.trim($wrap.find('select[name=menuType]').val()),
                    parentId: $.trim($wrap.find('select[name=parentId]').val()),
                    eventType: $.trim($wrap.find('select[name=eventType]').val()),
                    needWxoauth: $.trim($wrap.find('select[name=needWxoauth]').val()),
                     oauth2Scope: $.trim($wrap.find('select[name=oauth2Scope]').val()),
                    eventContent: $.trim($wrap.find('input[name=eventContent]').val())
                };

            if (!data.id) {
                delete data.id;
            }

            if (!data.appSettingId) {
                alert('请选择公众号名称');
                return;
            }

            if (!data.menuName) {
                alert('请填写菜单名称');
                return;
            }
            if (!data.menuType) {
                alert('请选择菜单类型');
                return;
            }

            if (!data.eventType) {
                alert('请选择菜单事件类型');
                return;
            }
            // 子菜单 控制
            if (data.menuType == 'sub' && !data.parentId && data.parentId > 0) {
                alert('请选择主菜单名称');
                return;
            }
            if (data.menuType == 'sub' && !data.eventContent) {
                alert('请填写菜单事件内容');
                return;
            }




            this.ajax('/wechat/menu/save', data, function(data) {

                window.location.href = $.cpc.page.backUrl;
            });
        },

        // 搜索权限
        onSearch: function($elem) {

            var $form = $elem.closest('form'),
                $searchField = $form.find('.searchField'),
                searchText = $.trim($searchField.val()),

                // 根据输入内容改变传参name
                fieldName = 'gzhName';

            $searchField.attr('name', fieldName).val(searchText);
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
        },
        //edit 初始化
        initEdit: function() {
            var $wrap = $('#wrap'); //外层定义的div 的id=wrap
            var $menuType = $wrap.find('select[name=menuType]');
            var $eventType = $wrap.find('select[name=eventType]');

            this.onMenuType($menuType, function($select) {
                if ($menuType.val() == 'sub') {
                    $select.val($wrap.find('select[name=parentId]').attr('data-id'));
                }
            });

            this.onMenuEventType($eventType);
        }
    };

    module.init();
});