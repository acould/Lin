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

            // 初始化一级分组下拉
            this.getGroupList(1, '-1');

            // 选择分组
            this.$wrap.find('.group').change(function() {
                self.onChangeGroup($(this));
            });

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

            //选择平台类型
            $('[name=platform]').change(function() {
                // 初始化一级分组下拉
                 self.getGroupList(1, '-1');
            });
           
        },

        // 分组多联动下拉
        onChangeGroup: function($elem) {
            var id = $elem.val(),
                level = Number($elem.attr('class').replace(/[^\d]+/, ''));

            if (id) {
                if (level < 3) {
                    this.getGroupList(level + 1, id);
                }
            } else {
                this.resetNextSelect($elem, level);
            }
        },

        // 重置后面联动select
        resetNextSelect: function($elem, level) {
            var levelText = ['一', '二', '三'];

            // 清空后面联动的内容
            $elem.nextAll('.group').each(function(i, elem) {
                $(elem).html('<option value="">选择' + levelText[level] + '级分组</option>');
                level++;
            });
        },

        // 获取分组列表
        getGroupList: function(level, parentId) {
            var self = this,
                levelText = ['一', '二', '三'],
                $select = self.$wrap.find('.level' + level),
                selectId = $select.data('id') || '',
                data = {
                    groupId: parentId,
                    platform:$.trim($('#wrap').find('select[name=platform]').val())
                };
           
            this.ajax('/sys/permission/getGroup', data, function(list) {
                // 渲染下拉列表
                var html = ['<option value="">选择' + levelText[level - 1] + '级分组</option>'];
                list.forEach(function(item) {
                    html.push('<option value="' + item.id + '">' + item.name + '</option>');
                });

                // 编辑时自动赋值并触发change事件
                $select.html(html.join('')).val(selectId).change();

                // 重置后面联动
                self.resetNextSelect($select, level);
            });
        },

        // 保存权限
        onSave: function($elem) {
            var $wrap = this.$wrap,
                data = {
                    id: $elem.data('id'),
                    category: $wrap.find('select[name=category]').val(),
                    firstGroupId: $wrap.find('select[name=firstGroupId]').val(),
                    secondGroupId: $wrap.find('select[name=secondGroupId]').val(),
                    thirdGroupId: $wrap.find('select[name=thirdGroupId]').val(),
                    name: $.trim($wrap.find('input[name=name]').val()),
                    platform: $wrap.find('select[name=platform]').val(),
                    url: $.trim($wrap.find('input[name=url]').val()),
                    verify: $wrap.find('input[name=verify]:checked').val()
                };

            if (!data.id) {
                delete data.id;
            }

            if (!data.category) {
                alert('请选择权限类型');
                return;
            }

            if (!(data.firstGroupId && data.secondGroupId)) {
                alert('一二级分组不能为空，请选择');
                return;
            }

            if (!data.name) {
                alert('请选择权限名');
                return;
            }

            if (!data.platform) {
                alert('请选择平台类型');
                return;
            }

            if (!data.url) {
                alert('url不能为空');
                return;
            }

            this.ajax('/sys/permission/save', data, function(data) {

                window.location.href = $.cpc.page.backUrl;
            });
        },

        // 搜索权限
        onSearch: function($elem) {
            var $form = $elem.closest('form'),
                $searchField = $form.find('.searchField'),
                searchText = $.trim($searchField.val()),

                // 根据输入内容改变传参name
                fieldName = /^[\/#][a-zA-Z\d\/\.\?&=]+$/.test(searchText) ? 'url' : 'name';

            $searchField.attr('name', fieldName).val(searchText);
            $form.submit();
        },

        // 删除权限
        onDelete: function($elem) {
            var name = $elem.closest('tr').find('.name').text(),
                data = {
                    id: $elem.data('id')
                };

            if (confirm('是否删除权限名为 “' + name + '” ?')) {
                this.ajax('/sys/permission/delete', data, function() {
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
                    alert(res.resultDes);
                }
            }).fail(function(res) {
                alert('接口错误，请联系服务端！');
            });
        }
    };

    module.init();
});