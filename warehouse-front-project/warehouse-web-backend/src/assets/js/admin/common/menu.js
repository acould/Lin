/*
 * create  :  2016/12/02
 * author  :  wangwb(w.hoby@qq.com)
 * desc    :  公共菜单 
 */
$(function() {
    var Menu = function() {
        /*
         定义初始化menu函数function
        */
        this.init = function() {
            this.stack = {
                topList: [],
                subList: {}, // 一级id为索引的子菜单对象
                curMenu: {}, // 当前页面的菜单id信息，用以定位
                menuName: {}
            };

            // 添加一个全局变量
            //$.cpc.page = $.cpc.page || {};

            this.event();
        };

        /*
         * 事件绑定
         1、定义变量 ；2、初始化菜单；3、绑定相关菜单事件。。
         */
        this.event = function() {
            var self = this;

            this.$topMenu = $('#navbar-menu-id').find('.menu-list');
            this.$subMenu = $('#sidebar').find('.menu-list');//子菜单定位

            // 无菜单（登陆页）
            if (!this.$topMenu.length) {
                return;
            }

            // 获取当前页面的id
            this.stack.curMenu.subId = this.$topMenu.data('sid').toString();

            // 加载菜单数据
            this.loadMenu();

            // 点击一级菜单
            this.$topMenu.on('click', 'li a', function() {
                self.onClickTopMenu($(this));
            });

            // 点击二级菜单
            this.$subMenu.on('click', 'li', function(e) {
                self.onClickSubMenu($(this), e);
            });

            // 左侧菜单显示/隐藏
            $('#J_switch').click(function() {
                self.onSwitchLeftMenu($(this));
            });
        };

        /*
         * 点击一级菜单
         */
        this.onClickTopMenu = function($elem) {
            var topId = $elem.data('id').toString();

            $elem.parent().addClass('current').siblings().removeClass('current');

            // 除当前一级菜单，其它默认展示第一个子级
            if (topId !== this.stack.curMenu.topId) {
                var subList = this.stack.subList[topId],
                    linkUrl = '';

                // 查找出能渲染的第一子页url
                subList.some(function(item) {
                    linkUrl = item.url;

                    if (item.child.length) {
                        linkUrl = item.child[0].url;
                    }

                    return !!linkUrl;
                });

                // 如果是vue模块hash类型的地址，加上基础路径
                window.location.href = linkUrl.replace(/^#/, '/page/index.html#');
            }
        };

        /*
         * 点击二级菜单
         */
        this.onClickSubMenu = function($elem, e) {
            var $ul = $elem.find('ul'),
                $item = $ul.closest('li'),
                hasChild = !!$ul.length,
                hasSelect = !!this.$subMenu.find('.current').length;

            if (hasChild) {
                $item.toggleClass('is-opened');
                $ul.slideToggle();

                // 若当前无选择，说明是初始化，理应展开第一项
                if (!hasSelect) {
                    $elem = $ul.find('li:first');
                } else {
                    return;
                }
            } else {
                e.stopPropagation();
                this.$subMenu.find('.current').removeClass('current');
            }

            var linkUrl = $elem.addClass('current').find('a').data('url');
            if (linkUrl) {
                // 处理vue类型路由
                if (linkUrl.indexOf('#') === 0) {
                    linkUrl = linkUrl.replace(/^#/, '/page/index.html#');
                }

                window.location.href = linkUrl;
            }
        };

        /*
         * 左侧菜单显示/隐藏
         */
        this.onSwitchLeftMenu = function($elem) {
            $('body').toggleClass('bg');
            $elem.toggleClass('close').html($elem.hasClass('close') ? '&gt;' : '&lt;').parent().show();

            $('#wrap').animate({
                marginLeft: '0'
            });
        };

        /*
         * 加载菜单数据
         */
        this.loadMenu = function() {
            var self = this,
                isLogin = $.cookie('username') == '' ? false : true,
                uid = $('#role_navigation_id').find('.J_UserName').data('uid'),
                menu = sessionStorage.getItem('menu');

            menu = menu && JSON.parse(menu);

            if (isLogin) {
                // 解决两个用户同时登录，数据滞留
                if (menu && menu.uid == uid) {
                    this.dealData(menu.list);
                } else {
                    this.ajax('/platform/menu/load', {}, function(list) {
                        // 缓存
                        sessionStorage.setItem('menu', JSON.stringify({
                            uid: uid,
                            list: list
                        }));
                        self.dealData(list);
                    });
                }
            } else {
                // 解决登录页后退页面残留
                window.location.href = '/login.html?cmd=logout';
            }
        };

        /*
         * 数据分类处理（一二三级全部数据）
         */
        this.dealData = function(originList) {
            var topList = [],
                subList = {},
                curMenu = this.stack.curMenu,
                menuName = this.stack.menuName,
                sorter = function(a, b) {
                    return Number(a.priority) - Number(b.priority);
                };

            // 先分类出顶层一级
            topList = originList.filter(function(item) {
                return item.parentId === '-1';
            }).sort(sorter);

            // 根据一级分出二、三级
            topList.forEach(function(item1) {
                // 先分类出所有二级
                var list2 = [];
                originList.forEach(function(item2) {
                    if (item1.id === item2.parentId) {
                        // 查找当前页面所属的顶级菜单
                        if (curMenu.subId === item2.id) {
                            curMenu.topId = item1.id;
                            menuName.topName = item1.name;
                            menuName.subName = item2.name;

                            // 获取当前菜单url
                           // $.cpc.page.backUrl = item2.url;
                        }

                        list2.push(item2);
                    }
                });

                // 再根据二级分出三级
                list2.forEach(function(item2) {
                    item2.child = [];
                    originList.forEach(function(item3) {
                        if (item2.id === item3.parentId) {
                            // 查找当前页面所属的顶级菜单
                            if (curMenu.subId === item3.id) {
                                curMenu.topId = item1.id;
                                menuName.topName = item1.name;
                                menuName.subName = item2.name;
                                menuName.childName = item3.name;

                                // 获取当前菜单url
                               // $.cpc.page.backUrl = item3.url;
                            }

                            item2.child.push(item3);
                        }
                    });
                    item2.child.sort(sorter);
                });

                // 将二、三级装进以一级Id为索引的对象
                subList[item1.id] = list2.sort(sorter);
            });

            this.stack.topList = topList;
            this.stack.subList = subList;

            // 新增/编辑保存返回列表的url，以适应动态菜单问题，默认取referrer，之后通过定位信息取到当前页面所在的菜单url
            //$.cpc.page.backUrl = document.referrer || $.cpc.page.backUrl;

            // 渲染菜单及导航
            this.renderTopMenu();
            this.renderSubMenu();
            // this.setBreadcrumb(); //设置导航栏
        };

        /*
         * 渲染顶部菜单
         */
        this.renderTopMenu = function() {
            var topList = this.stack.topList,
                curMenu = this.stack.curMenu,
                html = [];

            topList.forEach(function(item, i) {
                html.push('<li class="' + (item.id === curMenu.topId ? 'current' : '') + '"><a href="javascript:;" data-id="' + item.id + '">' + item.name + '</a><span class="triangle"></span></li>');
            });

            this.$topMenu.html(html.join(''));
        };

        /*
         * 渲染左侧菜单
         */
        this.renderSubMenu = function(pid) {
            // 无左侧dom结构不渲染
            if (!this.$subMenu.length) {
                return;
            }

            // 默认取当前页所在的顶级id
            pid = pid || this.stack.curMenu.topId;

            if (!pid) {
                //console.log('Error:', '获取当前菜单定位信息失败，请联系服务端！');
                return;
            }

            var self = this,
                subList = this.stack.subList[pid],
                curMenu = this.stack.curMenu,
                isSpecialPage = window.location.href.indexOf('childFor=') > -1,
                specicalKey = isSpecialPage ? /childFor=(\w+)/.exec(window.location.href)[1] : '',
                html = [];

            // 二级内容
            subList.some(function(item) {
                var childList = item.child,
                    isChildSelect = false,
                    hasFindParent = false,
                    html2 = [];

                if (childList.length) {
                    // 三级内容
                    var html3 = [];

                    childList.forEach(function(item1) {
                        var isSpecialChild = item1.url.indexOf('childFor=') > -1,
                            childKey = isSpecialChild ? /childFor=(\w+)/.exec(item1.url)[1] : '';
                        /*
                         * 当前为正常页： 过滤包含childFor的菜单
                         * 当前为特殊页： 只显示url包含指定key的菜单
                         */
                        if ((!isSpecialPage && !isSpecialChild) || (isSpecialPage && childKey == specicalKey)) {
                            html3.push('<li class=" ' + (item1.id === curMenu.subId ? 'active' : '') + '"><a href="'+item1.url+'"  title="' + item1.name + '"><i class="menu-icon fa fa-caret-right"></i>' + item1.name + '</a><b class="arrow"></b></li>');
                            if (item1.id === curMenu.subId) {
                                isChildSelect = true;
                            }
                        }

                        var isSpecialParent = item1.url.indexOf('parentFor=') > -1,
                            parentkey = isSpecialParent ? /parentFor=(\w+)/.exec(item1.url)[1] : '';

                        // 查出特殊子菜单的父级
                        if (specicalKey && parentkey == specicalKey) {
                            item = item1;
                            delete item.url;
                            hasFindParent = true;
                        }
                    });

                    // if (html3.length) {
                    //     var menu2stra = '<a href="#" class="dropdown-toggle"><i class="menu-icon fa fa-list"></i><span class="menu-text"> '+item.name+' </span>';
                    //     menu2stra = menu2stra+'<b class="arrow fa fa-angle-down"></b></a><b class="arrow"></b>';

                    //     html2.push('<li class="' + (isChildSelect ? 'open' : ' ') + '">' +menu2stra+ '<ul class="submenu">'+html3.join('') + '</ul></li>');
                    // }
                     if (html3.length) {
                        html2.push('<b class="arrow  fa fa-angle-down"></b><ul class="submenu">'+html3.join('') + '</ul>');
                      }
                }

                /*
                 * 二级单项
                 * 特殊页： 只显示特殊子菜单的父级
                 */
                // if ((!isSpecialPage && !hasFindParent) || (isSpecialPage && hasFindParent)) {
                    // if ((!isSpecialPage && !hasFindParent) || (isSpecialPage && hasFindParent)) {
                    // html.push(
                    //     '<li class=" ' + (item.id === curMenu.subId ? ' active' : '') + (isChildSelect ? ' is-opened' : '') + '">',
                    //     '   <a href="' + (item.url || '') + '" title="' + item.name + '"><i class="menu-icon fa fa-calendar"></i><span class="menu-text">' + item.name + '</span></a>' + html2.join(''),
                    //     '<b class="arrow"></b></li>'
                    // );

                    if ((!isSpecialPage && !hasFindParent) || (isSpecialPage && hasFindParent)) {
                        var icon = (!item.icon)?'fa-calendar':item.icon;
                        var arrvar = 'arrow';
                        if(childList.length){
                            arrvar = 'arrow fa fa-angle-down';
                        }
                        html.push(
                            '<li class=" ' + (item.id === curMenu.subId ? ' active' : '') + (isChildSelect ? ' open' : '') + '">',
                            '   <a href="' + (item.url || '') + '" class="dropdown-toggle" title="' + item.name + '"><i class="menu-icon fa '+icon+'"></i><span class="menu-text">' + item.name + '</span><b class="'+arrvar+'"></b>',
                            '</a>'+ html2.join(''),
                            '</li>'
                    );
                }

                // 如果找到当前特殊页，中止循环
                if (isSpecialPage && hasFindParent) {
                    return true;
                }
            });

            this.$subMenu.html(html.join(''));

            this.scrollToCurMenu();
        };

        /*
         * 滚动定位左侧菜单
         */
        this.scrollToCurMenu = function() {
            var $menu = this.$subMenu.parent(),
                menuClientH = $menu.height(),
                menuOffsetTop = $menu.offset().top,
                $elem = this.$subMenu.find('.current'),
                elemOffsetTop = $elem.size() && $elem.offset().top;

            if (elemOffsetTop > menuOffsetTop + menuClientH - 20) {
                $menu.animate({ scrollTop: elemOffsetTop - (menuOffsetTop + menuClientH) + 50 }, 'slow');
            }
        };

        /*
         * 设置面包屑导航
         */
        this.setBreadcrumb = function() {
            var menuName = this.stack.menuName,
                $breadcrumb = $('#breadcrumb');

            if ($breadcrumb.length && menuName.topName) {
                var html = '<em>' + menuName.topName + '</em><span class="part">&gt;</span><em>' + menuName.subName + '</em>';

                // 如果有三级菜单
                if (menuName.childName) {
                    html += '<span class="part">&gt;</span><em>' + menuName.childName + '</em>';
                }

                // 如果有自定义当前页面名称
                var diyName = $breadcrumb.text();
                if (diyName) {
                    html += '<span class="part">&gt;</span><em>' + diyName + '</em>';
                }

                $breadcrumb.html(html);
            }
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
