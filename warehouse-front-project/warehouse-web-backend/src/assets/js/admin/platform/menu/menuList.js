/*
 * created :  2018/09/15
 * desc    :  菜单管理
 */
$(function() {
    var Menu = function() {
        var url = {
                menuList: '/platform/menu/getListById',
                saveMenu: '/platform/menu/save',
                setDisplay: '/platform/menu/changeDisplayState',
                setOrder: '/platform/menu/updatePriority'
            },
            $wrap = $('.menu');

        this.init = function() {
            // 保存当前各层级parentId
            this.stack = {
                level: 1,
                pid: {
                    1: '-1'
                }
            };

            this.event();

            // 初始加载一级列表
            this.getList();
        };

        /*
         * 事件绑定
         */
        this.event = function() {
            var self = this;

            // 新增
            $wrap.on('click', '.btn-add', function(e) {
                !$(this).hasClass('disabled') && self.showAdd($(this));
            });

            // 编辑
            $wrap.on('click', '.btn-edit', function(e) {
                e.stopPropagation();
                self.showAdd($(this));
            });

            // 加载子菜单
            $wrap.on('click', '.item', function() {
                $(this).addClass('current').siblings().removeClass('current');
                self.showChild($(this));
            });

            // 控制按钮
            $wrap.on('hover', '.item', function() {
                $(this).find('.ctrlPanel').toggleClass('hide');
            });

            // 隐藏/显示
            $wrap.on('click', '.btn-hide', function(e) {
                e.stopPropagation();
                self.setDisplay($(this));
            });

            // 设置顺序
            $wrap.on('click', '.btn-order', function(e) {
                e.stopPropagation();
                self.setOrder($(this));
            });

             // 平台类型 事件
            $('[name=platform]').change(function() {
                self.onPlatform($(this));
            });
        };

        /*
         * 显示新增/编辑窗口
         */
        this.showAdd = function($elem) {
            this.setStack($elem);

            var self = this,
                isEdit = $elem.hasClass('btn-edit'),
                title = '新增菜单',
                name = '',
                data = {
                    parentId: this.stack.pid[this.stack.level],
                    platform:$.trim($('#wrap').find('select[name=platform]').val())
                };

            if (isEdit) {
                var $item = $elem.closest('li');
                title = '编辑菜单';
                name = $item.data('name');
                data.id = $item.data('id').toString();
            }

            $.cpc.dialog.init({
                title: title,
                html: '<div class="row"><label>名称：</label><input type="text" placeholder="建议4-7个中文字符" value="' + name + '" class="base-input"></div>',
                width: '430',
                id: 'J_addDialog',
                delDialog: true,
                submit: function() {
                    var name = $.trim($('#J_addDialog').find('input').val());
                    if (!name) {
                        alert('请输入菜单名称');
                        return false;
                    }

                    data.name = name;
                    self.onSave(data);
                    return true;
                }
            });
        };

        /*
         * 新增/编辑菜单保存
         */
        this.onSave = function(data) {
            var self = this;

            this.ajax(url.saveMenu, data, function() {
                self.getList();
            });
        };

        /*
         * 切换状态（保存当前所在层级和parentId）
         */
        this.setStack = function($elem) {
            var $block = $elem.closest('.block'),
                level = $block.data('level'),
                pid = $block.data('pid');

            this.stack.level = level;
            this.stack.pid[level] = pid;
        };

        /*
         * 隐藏/显示菜单
         */
        this.setDisplay = function($elem) {
            var self = this,
                text = $elem.text(),
                data = {
                    id: $elem.closest('li').data('id').toString(),
                    display: text === '显示' ? 'y' : 'n'
                };

            this.setStack($elem);

            this.ajax(url.setDisplay, data, function() {
                self.getList();
            });
        };

        /*
         * 调整顺序
         */
        this.setOrder = function($elem) {
            var self = this,
                $item = $elem.closest('.item'),
                $will = $elem.children().hasClass('arrow_up') ? $item.prev() : $item.next(),
                data = {
                    id1: $item.data('id').toString(),
                    id2: $will.data('id').toString()
                };

            this.setStack($elem);

            this.ajax(url.setOrder, data, function() {
                self.getList();
            });
        };

        /*
         * 显示子菜单
         */
        this.showChild = function($elem) {
            var level = $elem.closest('.block').data('level') + 1,
                pid = $elem.data('id').toString();

            // 切换level到子级
            this.stack.level = level;
            this.stack.pid[level] = pid;

            // 最后一级无子级
            if (level <= 3) {
                this.getList();
            }
        };

        /*
         * 加载列表数据
         */
        this.getList = function($elem) {
            var self = this,
                level = this.stack.level,
                data = {
                    id: this.stack.pid[level],
                    platform:$.trim($('#wrap').find('select[name=platform]').val())
                };
            console.log(data.platform)
            this.ajax(url.menuList, data, function(list) {
                self.render(list);
                self.resetChild();
            });
        };
        // 平台类型事件
        this.onPlatform =function($elem) {
            //this.init();
            // 保存当前各层级parentId
            this.stack = {
                level: 1,
                pid: {
                    1: '-1'
                }
            };

           this.getList();
        };

        /*
         * 渲染列表
         */
        this.render = function(list) {
            var html = [],
                len = list.length,
                noHideNames = ['平台管理', '菜单管理'];

            list.forEach(function(item, i) {
                html.push(
                    '<li data-pid="' + item.parentId + '" data-id="' + item.id + '" data-name="' + item.name + '" class="item clearfix">',
                    '   <span class="status">' + (item.display === 'y' ? '显示' : '隐藏') + '</span>',
                    '   <span class="name">' + item.name + '</span>',
                    '   <span class="sortPanel">',
                    '       <a class="arrow_con btn-order ' + (i === 0 ? 'hide' : '') + '"><i class="arrow_up"></i></a>',
                    '       <a class="arrow_con btn-order ' + (i === len - 1 ? 'hide' : '') + '"><i class="arrow_down"></i></a>',
                    '   </span>',
                    '   <span class="ctrlPanel hide">',
                    '       <a href="javascript:;" class="btn editor btn-edit">编辑</a>',
                    '       <a href="javascript:;" class="btn confirm btn-hide' + (noHideNames.indexOf(item.name) > -1 ? ' disabled' : '') + '">' + (item.display === 'y' ? '隐藏' : '显示') + '</a>',
                    '   </span>',
                    '</li>'
                );
            });
            $wrap.find('.level' + this.stack.level).find('.list').html(html.join(''));
        };


        /*
         * 更新按钮状态、清空子列表
         */
        this.resetChild = function() {
            var $block = $wrap.find('.level' + this.stack.level),
                level = this.stack.level;

            // data方法保存数据在dom上，但不显示
            $block.data('pid', this.stack.pid[level]);

            if (level > 1) {
                $block.find('.btn-add').removeClass('disabled');
            }

            // 设置子菜单状态
            $block.nextAll().each(function(i, item) {
                $(item).find('.btn-add').addClass('disabled');
                $(item).find('.list').empty();
            });
        };


        /*
         * ajax统一接口封装
         */
        this.ajax = function(url, data, callback) {
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
        };

        /*
         * 初始化
         */
        this.init();
    };

    var menu = new Menu();
});