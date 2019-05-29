<template>
  <div class="sidebar-wrap">
    <router-link :to="{name: 'schedule'}" class="tt" tag="h1">
      <img :src="logoSrc" class="logosrc">
      <span class="txt">车队助手</span>
    </router-link>
    <ul class="sidebar-nav">
      <router-link class="line" tag="li" :to="{name: 'memo'}">
        <span class="iconfont icon-daibanshixiang"></span>
        待办事项
        <span
          class="dot dot-red"
          v-if="backlogRemind > 0 ? true : false "
        >{{backlogRemind}}</span>
      </router-link>
      <router-link class="line" tag="li" :to="{name: 'schedule'}">
        <span class="iconfont icon-paiban"></span>
        统计管理
      </router-link>
      <router-link class="line" tag="li" :to="{name: 'orderManagement'}">
        <span class="iconfont icon-jiedan"></span>
        接单管理
        <span
          class="dot dot-red"
          v-if="isAllArranged > 0 ? true : false "
        >{{isAllArranged+comlaintNum}}</span>
      </router-link>
      <router-link class="line" tag="li" :to="{name: 'carManage'}">
        <span class="iconfont icon-cheliang"></span>
        车辆管理
        <span
          class="dot dot-red"
          v-if="carTimeoutSum > 0 ? true : false "
        >{{carTimeoutSum}}</span>
      </router-link>
      <router-link
        v-if="role_id == 1 || role_id == 0 || role_id == 3"
        class="line"
        tag="li"
        :to="{name: 'finance'}"
      >
        <span class="iconfont icon-caiwu"></span>
        财务管理
      </router-link>
      <router-link class="line" tag="li" :to="{name: 'contacts'}">
        <span class="iconfont icon-tongxunlu"></span>
        客户管理
      </router-link>

      <router-link class="line" tag="li" :to="{name: 'motorcadeInfo'}">
        <span class="iconfont icon-xiaochengxu"></span>
        小程序管理
      </router-link>
      <router-link class="line" tag="li" :to="{name: 'logOrigin'}">
        <span class="iconfont icon-jilu"></span>
        日志记录
      </router-link>
      <router-link class="line" tag="li" :to="{name: 'fleet'}">
        <span class="iconfont icon-jiedan1"></span>
        接单助手
      </router-link>
      <router-link class="line" tag="li" :to="{name: 'set'}">
        <span class="iconfont icon-shezhi"></span>
        设置
      </router-link>
      <router-link
        class="line"
        tag="li"
        :to="{name: 'customSet'}"
      >
        <span class="iconfont icon-zidingyilie"></span>
        自定义设置
      </router-link>
    </ul>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
import logo from '@/assets/images/logo_icon.png'
export default {
  name: 'sidebar',
  data () {
    return {
      logoSrc: logo
    }
  },
  computed: {
    ...mapState({
      isAllArranged: state => state.message.isAllArranged,
      carTimeoutSum: state => state.message.carTimeoutSum,
      comlaintNum: state => state.message.comlaintNum,
      backlogRemind: state => state.message.backlogRemind,
      role_id: state => state.user.user && state.user.user.role_id
    })
  },
  created () {
    this.getCompanyInfo()
    this.getUserInfo()
    this.getMessageCondition()
  },
  methods: {
    ...mapActions([
      "getUserInfo",
      "getMessageCondition"
    ]),
    getCompanyInfo () {
      this.$axios.get(this.$httpUrl.userInfo).then(res => {
        if (res) {
          // this.company = user.saas_company_name
          document.title = res.user.saas_company_name + '管理系统' || '车队助手 - 智能又好用的车队管理系统'
        }
      })
    }
  }
}
</script>
<style lang ="less" scoped>
@sidebar-color: #3a3f51;
@sidebar-border: 1px solid #49515d;
@router-link-active-color: #2e3344;
.sidebar-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: @sidebar-width;
  height: 100%;
  background-color: @sidebar-color;
  color: @bg-white;
  z-index: 999;
  transition: width 0.2s linear;
  &:hover {
    width: 160px;
  }
  .tt {
    font-size: @fontsize-large-x;
    cursor: pointer;
    line-height: 60px;
    height: 60px;
    overflow: hidden;
    font-size: 20px;
    .logosrc {
      margin: 16px 10px 0 10px;
    }
    .txt {
      vertical-align: 10px;
    }
  }
  .line {
    line-height: 50px;
    height: 50px;
    font-size: @fontsize-large;
    border-bottom: @sidebar-border;
    cursor: pointer;
    box-sizing: border-box;
    position: relative;
    overflow: hidden;
    &:last-of-type {
      border-bottom: none;
    }
    &:hover {
      background-color: @router-link-active-color;
    }
    &.router-link-active {
      background-color: @router-link-active-color;
    }
    .dot {
      position: absolute;
      left: 10px;
      top: 6px;
      padding: 1px 3px;
      background-color: #ff5656;
      border-radius: 12px;
      line-height: 1;
      font-size: 12px;
    }
    .iconfont {
      color: #fff;
      margin: 0 20px;
      &.icon-cheliang {
        font-size: 14px;
      }
      &.icon-tongxunlu,
      &.icon-caiwu {
        font-size: 20px;
      }
      &.icon-tongxunlu,
      &.icon-caiwu,
      &.icon-cheliang {
        margin: 0 18px;
      }
    }
  }
}
</style>
