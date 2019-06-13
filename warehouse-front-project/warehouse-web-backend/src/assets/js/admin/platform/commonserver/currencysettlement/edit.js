/*
 * created :  2018/09/15
 * desc    :  新建/编辑货币汇率名称
 */
$(function() {
    var module = {
        init: function() {
            this.event();
        },

        event: function() {
            var self = this,
                $wrap = $('#wrap');

            // 保存货币汇率名称
            $wrap.find('.J_Save').click(function() {
                self.onSave($(this));
            });

             // 货币选择
            $('[name=code]').change(function() {
                self.onCurrencyName($(this));
            })
        },
    //货币选择，赋值选选择项到code
      onCurrencyName: function($elem) {
        var textval = $elem.find("option:selected").text(); // 选中文本
            var $currencyName = $('input[name=currencyName]');
            $currencyName.val(textval);
        },

        onSave: function($elem) {
            var data = {
                    id: $elem.data('id'),
                    currencyName: $.trim($('input[name=currencyName]').val()),
                    code: $.trim($('select[name=code]').val()),
                    baseCurrencyCode: $.trim($('select[name=baseCurrencyCode]').val()),
                    exchangeRate: $.trim($('input[name=exchangeRate]').val()),
                    orderby: $.trim($('input[name=orderby]').val())
                };

            if (!data.id) {
                delete data.id;
            }

            if (data.name === '') {
                alert('货币汇率名称不能为空~');
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

            this.ajax('/commonserver/currencysettlement/save', data, function(data) {
                alert('操作成功');
                window.location.href = '/commonserver/currencysettlement/list.html';//$.cpc.page.backUrl;
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
