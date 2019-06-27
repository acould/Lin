<template>
    <div class="layout">
        <span v-loading.fullscreen.lock="loading" i-loading-text="请求中..."></span>
        <div class="side-bar">
            <div class="logo">
                <img src="/static/imgs/layout/logo.png" alt="">
            </div>
            <div class="menu">
                <hi-menu :menus="menus" @activated="openTab"></hi-menu>
            </div>
        </div>
        <div class="view-right">
            <div class="header">
                <div class="tabbar-container">
                    <hi-tabbar ref="tabbar"
                               @updateCache="handleCacheUpdate"
                               @inited="handleTabbarInited"></hi-tabbar>
                </div>
                <div class="user">
                    <span class="avatar">
                        <img :src="avatar" alt="">
                    </span>
                    <i-dropdown trigger="click" @command="handleCommand">
                        <div class="dropdown-text">
                            <span>{{username}}</span>
                            <i class="el-icon-arrow-down"></i>
                        </div>
                        <i-dropdown-menu slot="dropdown" class="user-dropdown">
                            <i-dropdown-item command="account">账户管理</i-dropdown-item>
                            <i-dropdown-item command="exit">退出</i-dropdown-item>
                        </i-dropdown-menu>
                    </i-dropdown>
                </div>
                <div class="tool-bar">
                    
                    <!--
                    <a class="btn">
                        <img src="/static/imgs/layout/plus.svg" alt="">
                    </a>
                    <a class="btn" @click.stop="notice">
                        <img src="/static/imgs/layout/mail.svg" alt="">
                    </a>-->
                    <a class="btn">
                        <img src="/static/imgs/layout/qrcode.svg" alt="">
                    </a>
                </div>
            </div>
            <keep-alive :include="cache">
                <router-view class="view-main"></router-view>
            </keep-alive>
        </div>
        <i-drawer v-model="accountStatus" title="账户管理">
            <Account></Account> 
        </i-drawer>
    </div>
</template>
<script>
import HiMenu from './component/menu/menu.vue';
import HiTabbar from './component/tabbar/tabbar.vue';
import Account from './component/account/account.vue';
import {mapState,mapActions,mapMutations} from 'vuex';

import loginService from 'service/login';
import EventBus from 'utils/eventBus';


export default {
    components:{HiMenu,HiTabbar,Account},
    data(){
        return {
            avatar:'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558001528360&di=0639b11b3e770223944296555a5fda29&imgtype=0&src=http%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0407%2F20120407102754475.jpg',
            username:'用户名',
            menus:[/*{
                label:'工作台',
                icon:'icon-dashboard',
                url:''
            },*/{
                label:'房态',
                icon:'icon-house',
                children:[{
                    label:'房态总览',
                    url:'/hpmshouse',
                },{
                    label:'日租房态管理',
                    url:'/house/daily/state',
                },
                // {
                //     label:'房源发布',
                //     url:''
                // }
                ]
            },{
                label:'订单',
                icon:'icon-order',
                url:'',
                children:[
                //     {
                //     label:'预定订单',
                //     url:''
                // },
                {
                    label:'日租订单',
                    url:'/contract/day'
                },{
                    label:'月租合同',
                    url:'/contract/month'
                }]
            },{
                label:'设置',
                icon:'icon-setting',
                children:[/*{
                    label:'权限管理',
                    url:'/system/right'
                },*/{
                    label:'字典项设置',
                    url:'/system/dict'
                },{
                    label:'合同模板设置',
                    url:'/system/template'
                },{
                    label:'业务规则设置',
                    url:'/system/rule'
                }]
            }],
            cache:'',
            accountStatus:false
        }
    },
    methods:{
        ...mapActions(['getDict']),
        ...mapMutations(['setAccount']),
        handleCacheUpdate(cache){
            this.cache = cache;
        },
        handleTabbarInited(){

        },
        openTab({route = {}, label, path}){
            console.log(route);
            this.$refs.tabbar.createTab({
                route,
                label,
                path
            });
        },
        notice(){
            this.$refs.tabbar.createTab({
                label:'消息中心',
                path:'/system/notice'
            })
        },
        handleCommand(command){
            switch(command){
                case 'exit':
                    loginService.logout().then(res => {
                        if (res.code === 0) {
                            this.$router.push('/login');
                        }
                    });
                    break;
                case 'account':
                    this.accountStatus = true;
                    break;    
            }
        },
        getAccount(){
            loginService.getAccount().then(res => {
                if (res.code === 0) {
                    if (!res.data.electronicSeal) {
                        res.data.electronicSeal = []
                    } else {
                        res.data.electronicSeal = [res.data.electronicSeal]
                    }
                    this.username = res.data.userName;
                    this.setAccount(res.data);
                }
            });
        },
    },
    computed:{
        ...mapState(['loading'])
    },
    created(){
        this.getAccount();
        this.getDict();
    },
    mounted(){
        EventBus.$on('show-account', () => {
            this.accountStatus = true;
        });
    }
}
</script>
<style lang="scss">
.view-main{
    position: relative;
    left: 0;
    top: 0;
    width: 100%;
    min-width: 1000px;
    height: calc(100% - 48px);
    padding:8px;
}

.layout{
    -webkit-font-smoothing:antialiased;
    -moz-osx-font-smoothing:grayscale;
    width: 100%;
    height: 100%;

    .side-bar{
        position: absolute;
        left: 0;
        top: 0;
        width: 64px;
        height: 100%;
        background: #001629;

        .logo{
            height: 88px;
            width: 100%;
            text-align: center;
            padding: 24px 0;

            img{
                width: 40px;
                height: 40px;
            }
        }
    }

    .view-right{
        float: right;
        position: relative;
        width: calc(100% - 64px);
        height: 100%;
        overflow: hidden;

        .header{
            position: relative;
            box-sizing: border-box;
            height: 48px;
            box-shadow: 0 1px 0 0 #e9e9e9;

            .tabbar-container{
                width: calc(100% - 350px);
                box-sizing: border-box;
                position: absolute;
                left: 0;
                bottom: 0;
            }

            .tool-bar{
                float:right;
                // width: 310px;
                height: 100%;
                line-height: 48px;

                .btn{
                    display: inline-block;
                    width: 20px;
                    height: 100%;

                    img{
                        width: 20px;
                        height: 20px;
                        vertical-align: middle;
                    }
                }

                .btn + .btn{
                    margin-left:34px;
                }                
            }

            .user{
                float: right;
                display: inline-block;
                height: 100%;
                line-height: 48px;
                margin-right: 24px;

                .dropdown-text{
                    cursor: pointer;
                    
                    span{
                        font-size:14px;
                        color:#333;
                    }
                }

                .avatar{
                    display: inline-block;
                    width: 24px;
                    height: 100%;
                    margin-left: 34px;

                    img{
                        width: 24px;
                        height: 24px;
                        vertical-align: middle;
                    }
                }
            }
        }
    }
}

.user-dropdown{
    .el-dropdown-menu-item{
        font-size:14px;
        color:#111;
    }        
}

.icon-dashboard{
    background: url('/static/imgs/layout/dashboard.png') no-repeat;
}

.icon-house{
    background: url('/static/imgs/layout/house.png') no-repeat;
}

.icon-order{
    background: url('/static/imgs/layout/order.png') no-repeat;
}

.icon-setting{
    background: url('/static/imgs/layout/setting.png')
}

.el-drawer-body{
    background: #f5f5f5;
}
</style>


