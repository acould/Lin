/*
 * created :  2018/09/15
 * desc    :  新建/编辑仓库信息
 */
$(function() {
    var module = {
        init: function() {
            this.event();
        },

        event: function() {
            var self = this,
                $wrap = $('#wrap');

            // 保存仓库信息
            $wrap.find('.J_Save').click(function() {
                self.onSave($(this));
            });
        },

      
        onSave: function($elem) {
            var data = {
                    id: $elem.data('id'),
                    name: $.trim($('input[name=name]').val()),
                    code: $.trim($('input[name=code]').val()),
                    warehouseType: $.trim($('select[name=warehouseType]').val()),
                    warehouseClassId: $.trim($('select[name=warehouseClassId]').val()),
                     remark: $.trim($('textarea[name=remark]').val()),
                    orderby: $.trim($('input[name=orderby]').val()),
                    status: $.trim($('input[name=status]').val()),
                    isOutsource: $.trim($('input[name=isOutsource]').val()),
                    outsourceName: $.trim($('input[name=outsourceName]').val()),
                    outsourceUrl: $.trim($('input[name=outsourceUrl]').val()),
                    country: $.trim($('input[name=country]').val()),
                    province: $.trim($('input[name=province]').val()),
                    city: $.trim($('input[name=city]').val()),
                    county: $.trim($('input[name=county]').val()),
                    street: $.trim($('input[name=street]').val()),
                    address: $.trim($('input[name=address]').val()),
                    postCode: $.trim($('input[name=postCode]').val()),
                    telphone: $.trim($('input[name=telphone]').val()),
                    manager: $.trim($('input[name=manager]').val())
                };

            if (!data.id) {
                delete data.id;
            }

            if (data.name === '') {
                alert('仓库名称不能为空~');
                return;
            }

             if (data.warehouseType === '') {
                alert('仓库货品性质不能为空~');
                return;
            }
            if (data.warehouseClassId === '') {
                alert('仓库类别不能为空~');
                return;
            }
           

            this.ajax('/warehouse/info/save', data, function(data) {
                alert('操作成功');
                window.location.href = '/warehouse/info/list.html';//$.cpc.page.backUrl;
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
