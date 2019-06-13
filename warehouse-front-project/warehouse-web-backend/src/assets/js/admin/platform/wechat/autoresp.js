/*
 * @create  :  2018/08/28
 * @author  :  xxh 22
 * @desc    :  微信自动回复
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

            // 保存权限
            this.$wrap.find('.J_Save').click(function() {
                self.onSave($(this));
            });



            // 删除权限
            this.$wrap.find('.J_Delete').click(function() {
                self.onDelete($(this));
            });

            // 自动回复消息类型事件
            $('[name=msgType]').change(function() {
                self.onMsgType($(this));
            })
        },

        // 获取主菜单函数事件
        getMainMenu: function(callback) {
            var data = {
                appSettingId: $('select[name=appSettingId]').val()
            };
            this.ajax('/wechat/autoresp/getMainMenu', data, function(res) {
                if (typeof callback === 'function') {
                    callback(res);
                }
            });
        },

        // 菜单类型事件
        onMsgType: function($elem) {
            var value = $elem.val();
            var $msgExceed_msgExceedTimes = $('.msgExceed_msgExceedTimes');
            var $msgExceed_msgExceedNum = $('.msgExceed_msgExceedNum');
            var $msgExceed_msgExceedContent = $('.msgExceed_msgExceedContent');
            //如果选择子菜单，则 ajax请求获取主菜单list，并显示主菜单的select；否则隐藏。
            if (value === 'receiveMsg') {
                $msgExceed_msgExceedTimes.removeClass('dn'); //隐藏class 标签
                $msgExceed_msgExceedNum.removeClass('dn'); //隐藏class 标签
                $msgExceed_msgExceedContent.removeClass('dn'); //隐藏class 标签
               
            } else {
                $msgExceed_msgExceedTimes.addClass('dn');
                $msgExceed_msgExceedNum.addClass('dn');
                $msgExceed_msgExceedContent.addClass('dn');
            }
        },

        // 保存权限
        onSave: function($elem) {
            var $wrap = this.$wrap,
                data = {
                    id: $elem.data('id'),
                    appSettingId: $.trim($wrap.find('select[name=appSettingId]').val()),
                    msgType: $.trim($wrap.find('select[name=msgType]').val()),
                    isBindUser: $.trim($wrap.find('select[name=isBindUser]').val()),
                    keyType: $.trim($wrap.find('select[name=keyType]').val()),
                    contentKey: $.trim($wrap.find('input[name=contentKey]').val()),
                    eventType: $.trim($wrap.find('select[name=eventType]').val()),
                    eventContent: $.trim($wrap.find('input[name=eventContent]').val()),
                    msgExceedTimes: $.trim($wrap.find('input[name=msgExceedTimes]').val()),
                    msgExceedNum: $.trim($wrap.find('input[name=msgExceedNum]').val()),
                    msgExceedContent: $.trim($wrap.find('input[name=msgExceedContent]').val())
                };

            if (!data.id) {
                delete data.id;
            }

            if (!data.appSettingId) {
                alert('请选择公众号名称');
                return;
            }

            if (!data.msgType) {
                alert('请选择自动回复消息类型');
                return;
            }
            if (!data.isBindUser) {
                alert('请选择是否绑定用户');
                return;
            }
            if (!data.keyType) {
                alert('请选择关键词匹配类型');
                return;
            }

            if (!data.contentKey) {
                alert('请填写关键词内容（英文符逗号隔开）');
                return;
            }

            if (!data.eventType) {
                alert('请选择菜单事件类型');
                return;
            }

            if (!data.eventType && !data.eventContent) {
                alert('请填写消息设置事件内容');
                return;
            }

            this.ajax('/wechat/autoresp/save', data, function(data) {

                window.location.href = $.cpc.page.backUrl;
            });
        },

        // 删除权限
        onDelete: function($elem) {
            var name = $elem.closest('tr').find('.gzhName').text(),
                data = {
                    id: $elem.data('id')
                };

            if (confirm('是否删除菜单名称为 “' + name + '” ?')) {
                this.ajax('/wechat/autoresp/delete', data, function() {
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