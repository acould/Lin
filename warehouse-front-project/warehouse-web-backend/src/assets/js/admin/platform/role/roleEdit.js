/*
 * created :  2018/09/15
 * desc    :  新建/编辑角色
 */
$(function() {
    var module = {
        init: function() {
            this.stack = {
                originList: [],
                roleList: []
            };

            this.event();
        },

        event: function() {
            var self = this,
                $wrap = $('#wrap'),
                $role = $wrap.find('.role'),
                $platform = $wrap.find('select[name=platform]');

            this.$role = $role;

            // 编辑页初始化
            this.onChangePlatform($platform);

            // 选择平台类型
            $platform.change(function() {
                self.onChangePlatform($(this));
            });

            // tab切换
            $wrap.find('.tab').on('click', 'li', function() {
                $(this).addClass('cur').siblings().removeClass('cur');
                $wrap.find('.block li').eq($(this).index()).addClass('cur').siblings().removeClass('cur');
            });

            // 子级展开
            $role.on('click', '.J_showSub', function() {
                $(this).prev().click();
            });

            // 勾选二级类目
            $role.on('change', '.level2 input', function() {
                self.syncCheckState($(this));
            });

            // 勾选三级类目
            $role.on('click', '.level3 input', function() {
                self.syncCheckState($(this), 'child');
            });

            // 保存角色
            $wrap.find('.J_Save').click(function() {
                self.onSave($(this));
            });
        },

        onChangePlatform: function($elem) {
            var self = this,
                data = {
                    id: $.trim($('#wrap').find('input[name=roleId]').val()),
                    platform: $elem.val()
                };

            $('.J_role').toggle(data.platform !== '-1');
            //选择平台后，进行树数据初始化。。否则后面return了。
            this.stack = {
                originList: [],
                roleList: []
            };
            // 没选择类型或加载过数据
            if (data.platform === '-1' || this.stack.originList.length) {
                return;
            }

            if (!data.id) {
                delete data.id;
            }
            

            this.ajax('/sys/role/getPermission', data, function(data) {
                if (data.length) {
                    self.stack.originList = data;
                    self.dealRoleData();
                    self.renderRoleList();
                }
            });
        },

        // 数据分类处理（一二三级全部数据）
        dealRoleData: function() {
            var originList = this.stack.originList,
                roleList = [],
                sorter = function(a, b) {
                    return Number(a.priority) - Number(b.priority);
                };

            // 先分类出顶层一级
            roleList = this.stack.roleList = originList.filter(function(item) {
                return item.pid === '-1';
            }).sort(sorter);

            // 根据一级分出二级，放入child属性数组里
            roleList.forEach(function(item1) {
                item1.child = [];
                originList.forEach(function(item2) {
                    if (item1.id === item2.pid && item2.type === 'group') {
                        item1.child.push(item2);
                    }
                });
                item1.child.sort(sorter);

                // 根据二级分出三级
                item1.child.forEach(function(item2) {
                    item2.child = [];
                    originList.forEach(function(item3) {
                        if (item2.id === item3.pid && item3.type === 'permission') {
                            item2.child.push(item3);
                        }
                    });
                });
            });
        },

        // 渲染权限列表
        renderRoleList: function() {
            var roleList = this.stack.roleList,
                tabHtml = [],
                blockHtml = [];

            roleList.forEach(function(item1, i) {
                tabHtml.push('<li class="' + (item1.name === '商户管理' ? 'cur' : '') + '">' + item1.name + '</li>');

                // 二级内容
                var level2 = [];
                item1.child.forEach(function(item2) {

                    // 三级内容
                    var level3 = [],
                        isAllChildChecked = true;

                    item2.child.forEach(function(item3) {
                        level3.push('<label title="' + item3.name + '" class="tal"><input type="checkbox" value="' + item3.id + '" ' + (item3.check ? 'checked' : '') + '> ' + item3.name + '</label>');

                        // 只要有一项没勾选
                        !item3.check && (isAllChildChecked = false);
                    });

                    // 二级单项
                    level2.push(
                        '<div class="row item tree">',
                        '   <div class="row level2">',
                        '       <label class="tal"><input type="checkbox" value="' + item2.id + '" ' + (isAllChildChecked && item2.child.length ? 'checked' : '') + '> ',
                        '       <a href="javascript:;" class="J_showSub">' + item2.name + '</a></label>',
                        '   </div>',
                        '   <div class="row level3">' + level3.join('') + '</div>',
                        '</div>'
                    );
                });

                blockHtml.push('<li class="' + (item1.name === '商户管理' ? 'cur' : '') + '">' + level2.join('') + '</li>');
            });

            this.$role.find('.tab').html(tabHtml.join(''));

            var contentWidth = this.$role.find('.tab').width() + 50;
            this.$role.find('.block').html(blockHtml.join('')).css('width', contentWidth);
        },

        // 同步一二级状态
        syncCheckState: function($elem, flag) {
            var isChecked = $elem.prop('checked');

            if (flag === 'child') {
                var $levelSub = $elem.closest('.level3'),
                    isAll = true;

                // 挨个与当前状态比较
                $levelSub.find('input').each(function(i, elem) {
                    if (isChecked !== $(elem).prop('checked')) {
                        isAll = false;
                    }
                });

                // 只当二级全部选中，一级才会选中
                $elem.closest('.item').find('.level2 input').prop('checked', isAll && isChecked);

            } else {
                $elem.closest('.item').find('.level3 input').each(function(i, elem) {
                    $(elem).prop('checked', isChecked);
                });
            }
        },

        onSave: function($elem) {
            var permissionIds = [],
                data = {
                    id: $elem.data('id'),
                    platform: $('select[name=platform]').val(),
                    name: $.trim($('input[name=name]').val())
                };

            if (!data.id) {
                delete data.id;
            }

            if (data.platform == '-1') {
                alert('请选择平台类型');
                return;
            }

            if (data.name === '') {
                alert('角色名称不能为空~');
                return;
            }

            this.$role.find('.level3 input:checked').each(function() {
                permissionIds.push($(this).val());
            });

            if (!permissionIds.length) {
                alert('至少勾选1个角色权限哟~');
                return false;
            }

            data.permissionIds = permissionIds.join();

            this.ajax('/sys/role/save', data, function(data) {
                alert('恭喜,操作成功');
                window.location.href = $.cpc.page.backUrl;
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
