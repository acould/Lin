<template>
  <div class="index-Origin" ref="indexOrigin">
    <webHeader></webHeader>
    <div class="index-row">
      <router-view></router-view>
      <webFooter></webFooter>
    </div>

    <div class="fixed-tool">
      <a href="javascript:void(0)" class="fixed-tool-top" @click="rtnTop">
        <span class="iconfont icon-jiantou2 iconfont-arrow"></span>
        返回顶部
      </a>
      <a href="javascript:void(0)" class="fixed-tool-concat">
        <span class="iconfont icon-kefu iconfont-kefu"></span>
        联系我们
        <el-popover popper-class="concat-mobile" placement="left-start" width="200" trigger="hover" content="电话咨询（400-123123)">
          <el-button slot="reference" class="el-btn"></el-button>
        </el-popover>
      </a>
    </div>
  </div>
</template>
<script>
import webHeader from '@/components/web/webHeader'
import webFooter from '@/components/web/webFooter'
export default {
  name: 'indexOrigin',
  components: {
    webFooter,
    webHeader
  },
  data () {
    return {
     
    }
  },
  methods: {
    rtnTop () {
      this.$refs.indexOrigin.scrollTop = 0
    }
  }
}
</script>
<style lang ="less" scoped>
.index-Origin {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  padding-top: @headerHeight;
  box-sizing: border-box;
  overflow: auto;
  .fixed-tool {
    position: fixed;
    bottom: 330px;
    right: 40px;
    width: 44px;
    z-index: 99;
    a {
      display: block;
      text-align: center;
      height: 130px;
      padding: 0 7px;
      box-sizing: border-box;
      margin-bottom: 10px;
      font-size: 16px;
      .el-btn {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        opacity: 0;
        z-index: 2;
      }
      &.fixed-tool-top {
        background-color: @theme-color;
        color: #fff;
      }
      &.fixed-tool-concat {
        background-color: #fff;
        border: 1px solid #c9d0d8;
        box-sizing: border-box;
        color: #333;
        position: relative;
      }
      .iconfont {
        display: block;
      }
      .iconfont-arrow {
        font-size: 30px;
        display: inline-block;
        transform: rotate(90deg);
      }
      .iconfont-kefu {
        font-size: 30px;
      }
    }
  }
  .index-row {
    position: relative;
    padding-bottom: @footerHeight;
    box-sizing: border-box;
    min-height: 100%;
  }
}
</style>
<style lang="less">
.concat-mobile {
  color: @theme-color;
  font-size: 16px;
}
</style>

