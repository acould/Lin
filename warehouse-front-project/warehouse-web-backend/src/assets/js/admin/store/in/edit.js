/*
 * created :  2018/09/15
 * desc    :  新建/编辑入库单名称
 */
$(function() {
    var module = {
        init: function() {
            this.event();
        },

        event: function() {
            var self = this,
                $wrap = $('#wrap');

            // 保存入库单名称
            $wrap.find('.J_Save').click(function() {
                self.onSave($(this));
            });
        },

      
        onSave: function($elem) {
            var data = {
                    id: $elem.data('id'),
                    name: $.trim($('input[name=name]').val()),
                    url: $.trim($('input[name=url]').val()),
                    orderby: $.trim($('input[name=orderby]').val())
                };

            if (!data.id) {
                delete data.id;
            }

            if (data.name === '') {
                alert('入库单名称不能为空~');
                return;
            }

            

            this.ajax('/store/in/save', data, function(data) {
                alert('操作成功');
                window.location.href = '/store/in/list.html';//$.cpc.page.backUrl;
            });
        },

        /*
         * ajax统一接口封装
         */
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
