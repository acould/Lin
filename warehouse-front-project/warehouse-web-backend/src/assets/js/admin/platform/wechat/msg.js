/*
 * @create  :  2018/08/28
 * @author  :  xxh 22
 * @desc    :  微信公众号设置列表
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
        },

        // 保存权限
        onSave: function($elem) {
            var $wrap = this.$wrap,
                data = {
                    id: $elem.data('id'),
                    companyId: $.trim($wrap.find('input[name=companyId]').val()),
                    appId: $.trim($wrap.find('input[name=appId]').val()),
                    appSecret: $.trim($wrap.find('input[name=appSecret]').val()),
                    token: $.trim($wrap.find('input[name=token]').val()),
                    aeskey: $.trim($wrap.find('input[name=aeskey]').val()),
                    weixinNo: $.trim($wrap.find('input[name=weixinNo]').val()),
                    originId: $.trim($wrap.find('input[name=originId]').val()),
                    email: $.trim($wrap.find('input[name=email]').val()),
                    gzhType: $wrap.find('select[name=gzhType]').val(),
                    gzhName: $.trim($wrap.find('input[name=gzhName]').val())
                };

            if (!data.id) {
                delete data.id;
            }

            if (!data.appId) {
                alert('请填写appId');
                return;
            }

            if (!data.appSecret) {
                alert('请填写appSecret');
                return;
            }
            if (!data.token) {
                alert('请填写token');
                return;
            }
            if (!data.aeskey) {
                alert('请填写aeskey');
                return;
            }
            if (!data.weixinNo) {
                alert('请填写微信号');
                return;
            }
            if (!data.originId) {
                alert('请填写微信号原始id');
                return;
            }
            if (!data.email) {
                alert('请填写登录邮箱');
                return;
            }


            if (!(data.gzhType)) {
                alert('公众号类型不能为空，请选择');
                return;
            }

            if (!data.gzhName) {
                alert('请填写公众号名称');
                return;
            }


            this.ajax('/wechat/appsetting/save', data, function(data) {

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

            if (confirm('是否删除公众号名称为 “' + name + '” ?')) {
                this.ajax('/wechat/appsetting/delete', data, function() {
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