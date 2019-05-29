<template>
  <div class="header-wrap">
    <div class="header">
      <div class="row">
        <div class="left-side">
          <router-link :to="{name: 'index'}">
            <img :src="logoSrc" class="logo">
          </router-link>
        </div>
        <div class="right-side">
          <ul class="nav">
            <router-link tag="li" :to="{name: 'index'}">
              <a href="javascript:void(0);">首页</a>
            </router-link>
            <router-link tag="li" :to="{name: 'aboutUs'}">
              <a href="javascript:void(0);">关于我们</a>
            </router-link>
            <router-link tag="li" :to="{name: 'help'}">
              <a href="javascript:void(0);">帮助中心</a>
            </router-link>
            <li>
              <a href="javascript:void(0);" @click="downloadRtn">APP下载</a>
            </li>
          </ul>
          <a href="javascript:void(0)" class="btn-login" @click="rtnLogin">登录/注册</a>
        </div>
      </div>
    </div>
    <select-app-download  :select-app-box-show.sync="selectAppBoxShow"/>
  </div>

</template>
<script>
import logo from '@/assets/images/index/icon_index_logo.png'
export default {
  name: 'webHeader',
  data () {
    return {
      selectAppBoxShow: false,
      qrCodeBoxShow: false,
      logoSrc: logo,
    }
  },
  created () {

  },
  methods: {
    downloadRtn () {
      this.selectAppBoxShow = true
    },
    rtnLogin () {
      this.$router.push({ name: 'schedule' })
    }
  }
}
</script>
<style lang ="less" scoped>
@rowWidth: 1000px;
.header-wrap {
  height: 0.5px;
}
.header {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 99;
  height: @headerHeight;
  width: 100%;
  padding: 21px 0;
  box-sizing: border-box;
  background-color: #fff;
  box-shadow:0px 0px 24px rgba(0,0,0,0.1);
  .row {
    width: @rowWidth;
    margin: 0 auto;
    overflow: hidden;
  }
  .left-side {
    float: left;
    .logo {
      width: 192px;
      height: 58px;
    }
  }
  .right-side {
    float: right;
    .nav {
      overflow: hidden;
      display: inline-block;
      margin-right: 30px;
      li {
        float: left;
        a {
          display: inline-block;
          width: 120px;
          height: 58px;
          line-height: 58px;
          text-align: center;
          color: @color-dark-grey;
          cursor: pointer;
          font-size: @fontsize-large;
          &:hover {
            color: @theme-color;
          }
        }
        &.active,
        &.router-link-active           
         {
          a {
            color: @theme-color;
          }
        }
      }
    }
    .btn-login {
      display: inline-block;
      vertical-align: top;
      width: 130px;
      height: 36px;
      line-height: 36px;
      text-align: center;
      margin-top: 11px;
      border: 1px solid #999;
      border-radius: 18px;
    }
  }
}
</style>
