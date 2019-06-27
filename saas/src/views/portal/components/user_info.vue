<template>
    <transition name="drop">
        <div class="vvc-dropdown" v-show="config.trigger">
            <div class="panel-user">
                <div class="panel-user-profile">
                    <img class="avatar" :src="profile.avatar"></img>
                    <p class="info-name">{{profile.name}}</p>
                    <p class="info-phone">{{profile.phone}}</p>
                </div>
                <ul class="panel-user-options">
                    <li class="logout" @click="logout">
                        <img src="/static/logout.svg"></img>
                        退出登录
                    </li>
                    <li class="close-windows" @click="closeAllTabs">
                        <img src="/static/close_windows.svg"></img>
                        关闭所有窗口
                    </li>
                </ul>
            </div>
        </div>
    </transition>
</template>
<script>
    import { Tabbar } from 'i-tofu';
    import CommonServices from 'service/common';

    export default {
        name: 'dropdown-user',
        props: {
            config: {
                require: true,
                validator(){
                    return true;
                }
            }
        },
        data(){
            return {
                profile: {
                    avatar: '/static/avatar_egg.gif',
                    name: '丫丫',
                    phone: '13978894856'
                }
            };
        },
        methods: {
            //退出登录
            logout(){
                CommonServices.logout().then(res => {
                    console.log('登出返回结果为：', res);
                    if (res.code === 0) {
                        location.href = 'http://sys.ishangzu.com';
                    } else {
                        this.$message.error('退出失败！');
                    }
                });
            },
            //关闭所有窗口
            closeAllTabs(){
                this.$tab.destroyAllTabs();
            },
            //获取用户信息
            getUserInfo(){
                const userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
                if (userInfo) {
                    this.profile.name = userInfo.user_name;
                    this.profile.phone = userInfo.user_phone;
                } else {
//                    this.$message.error('获取用户信息失败！');
                }
            }
        },
        created(){
            this.getUserInfo();
        }
    };
</script>
<style lang="scss">
    .vvc-dropdown {
        position: fixed;
        top: 32px;
        right: 6px;
        z-index: 9999;
        background-color: #fff;
        box-shadow: 0 6px 15px 0 rgba(0,0,0,0.30);
        border-radius: 2px;
        text-align: left;

        .panel-user {
            position: relative;
            box-sizing: border-box;
            width: 240px;

            .panel-user-profile {
                position: relative;
                padding: 24px 0 22px 74px;
                border-bottom: 1px solid #C4C4C4;
                .avatar {
                    position: absolute;
                    left: 16px;
                    top: 50%;
                    margin-top:-20px;
                    width: 40px;
                    height: 40px;
                }
                p {
                    /*margin-bottom: 6px;*/
                    line-height: 18px;
                    font-family: HelveticaNeue;
                    font-size: 12px;
                    color: #A1A0A1;

                    &:last-child {
                         margin-bottom: 0;
                    }
                }
            }
            .panel-user-options {
                padding: 8px 0;
                list-style: none;

                li {
                    position: relative;
                    height: 40px;
                    line-height: 40px;
                    padding: 0 54px;
                    font-family: PingFang-SC-Regular;
                    font-size: 12px;
                    color: #1E2C3C;
                    letter-spacing: 0.3px;
                    cursor: pointer;

                    &:hover {
                         background-color: #F5F5F5;
                    }
                    img {
                        position: absolute;
                        top: 11px;
                        left: 18px;
                        height: 18px;
                        border-radius: 0;
                    }
                    &.change-password img {
                         width: 15px;
                    }
                    &.logout img {
                         width: 20px;
                    }
                    &.close-windows img {
                         width: 18px;
                    }
                }
            }
        }
    }
    .drop-enter-active, .drop-leave-active {
        transition: all .3s ease-in-out;
    }
    .drop-enter, .drop-leave-active {
        opacity: 0;
    }
</style>
