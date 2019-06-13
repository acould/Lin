	/*
 * created :  2018/09/15
 * desc    :  新建/编辑出库单名称
 */
$(function() {
    var module = {
        init: function() {
            this.event();
        },

        event: function() {
            var self = this,
                $wrap = $('#wrap');

            // 保存出库单名称
            $wrap.find('.J_Save').click(function() {
                self.onSave($(this));
            });
        },

      
        onSave: function($elem) {
            var data = {
                    id: $elem.data('id'),
                    currencyName: $.trim($('input[name=currencyName]').val()),
                    code: $.trim($('input[name=code]').val()),
                    baseCurrencyCode: $.trim($('input[name=baseCurrencyCode]').val()),
                    exchangeRate: $.trim($('input[name=exchangeRate]').val()),
                    orderby: $.trim($('input[name=orderby]').val())
                };

            if (!data.id) {
                delete data.id;
            }

            if (data.name === '') {
                alert('出库单名称不能为空~');
                return;
            }

             if (data.code === '') {
                alert('货币编号不能为空~');
                return;
            }
             if (data.baseCurrencyCode === '') {
                alert('基础货币不能为空~');
                return;
            }

             if (data.exchangeRate === '') {
                alert('汇率不能为空~');
                return;
            }

            this.ajax('/product/storeout/save', data, function(data) {
                alert('操作成功');
                window.location.href = '/product/storeout/list.html';//$.cpc.page.backUrl;
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
