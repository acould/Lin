import './index.scss';
import tpl from './index.html';
import config from 'config';
import CommonServices from 'service/common';
import IDropdownUser from './components/user_info';
import DepartmentSelector from './components/department_selector';
import {mapState, mapActions} from 'vuex';
export default {
    template: tpl,
    name: 'portal',
    components: {IDropdownUser, DepartmentSelector},
    computed: {...mapState(['common'])},
    data(){
        return {
            config,
            fullscreen: false,
            cache: '',
            title: config.title,
            loading: true,
            defaultTab: { label: '首页', path: `/${config.root}/dashboard` },
            menusMap: {[`/${config.root}/dashboard`]: '首页'},
            menuConfig: {
                useRouter: true,
                menus: [

                ]
            },
            tabConfig: {
                useRouter: true,
                autoRestore: true,
                style: {width: '116px'}
            },
            userDropdownConfig: {trigger: false},
            options: []
        };
    },

    methods: {
        ...mapActions(['getDict', 'getPermissions', 'getDepart', 'getCompany','getAllDepart']),
        /**
         * 触发激活菜单的事件时，打开一个Tab
         */
        openTab({ route = {}, label, path }){
            this.$refs.tabbar.createTab({
                route,
                label,
                path
            });
        },

        handleCacheUpdate(cache) {
            this.cache = cache
        },

        /**
         * 切换下拉菜单
         */
        switchUserDropdown(status){
            if (status === 'open') {
                this.userDropdownConfig.trigger = true;
            } else {
                this.userDropdownConfig.trigger = false;
            }
        },

        /**
         * 获取菜单
         */
        getMenus(){
            CommonServices.getMenus({ res_category: config.category }).then(res => {
                if (res.code === 0) {
                    this.menuConfig.menus = this.menuConfig.menus.concat(this.traverseMenu(res.obj));
                } else if (res.code === -9999) {
                    this.$message.error(res.msg);
                } else {
                    this.$message.error('获取菜单失败！');
                }
            });
        },

        traverseMenu(menus, prefix = '', level = 0){
            return menus.map(menu => {
                let rst = {
                    id: menu.id,
                    label: menu.text
                };

                if (level === 0) {
                    rst.iconURL = '/static/content.png';
                }
                if (menu.children) {
                    let item = menu.attributes.match(/^(\/[a-zA-Z]+\_[a-zA-Z]+|\/.*\/).+$/);
                    let p = item ? item[1] : '';
                    rst.children = this.traverseMenu(
                        menu.children,
                        p,
                        level + 1
                    );
                } else {
                    if (level !== 0) {
                        rst.path = menu.attributes.replace(prefix, config.root); // 部分替换成 ''
                    } else {
                        rst.path = `/${config.root}` + menu.attributes;
                    }
                }

                this.menusMap[rst.path] = rst.label;
                return rst;
            });
        },

        /**
         * 获取部门
         */
        getDeparts(){
            CommonServices.getDeparts().then(res => {
                if (res.code === 0) {
                    this.options = res.obj.cityDepVoList.map(city => {
                        return {
                            value: city.code,
                            label: city.name,
                            children: this.transformDepart(city.sysDepartList)
                        };
                    });
                } else {
                    this.$message.error(res.msg);
                }
            });
        },

        transformDepart(departs){
            if (!departs) {
                return;
            }

            return departs.map(depart => {
                return {
                    value: depart.code || depart.dep_id,
                    label: depart.name || depart.dep_name,
                    children: this.transformDepart(depart.sysDepartList)
                };
            });
        },

        handleChangeDepartment(val){
            this.$refs.tabbar.flush();
            var params = {};
            params['dep_id'] = val[1];
            params['code'] = val[0];
            CommonServices.setBranch(params).then(res => {
                if (res.code === 0) {
                    this.$message({
                        type:'success',
                        message:'城市分支切换成功!'
                    });
                } else {
                    this.$message.error(res.msg);
                }
            });
        },

        handleTabbarInited() {
            if (this.$root.destination) {
                this.openTab({
                    route: this.$root.destination,
                    label: this.menusMap[this.$root.destination.path]
                });
            }
        },

        openMsgDialog(){
            let tab = {};
            tab.path = '/finance/notice';
            tab.label = '消息列表';
            this.openTab(tab);
        }
    },

    beforeRouteUpdate(to, from, next){
        if (to.query && Number(to.query.fullscreen) === 1) {
            this.fullscreen = true;
        } else {
            this.fullscreen = false;
        }
        next();
    },

    created(){
        // 获取菜单并进行转换
        this.getDict();
        this.getPermissions();
        this.getMenus();
        this.getDeparts();
        this.getCompany();
        this.getDepart();
        this.getAllDepart();
    },
    mounted(){

    }
};