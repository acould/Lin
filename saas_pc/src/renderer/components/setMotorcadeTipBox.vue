<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="setMotorcadeTipBox" top='25vh'  :show-close="false">
      <div class="dialog-header" slot="title">
        <p class="title">提示</p>
        <span class="iconfont icon-close iconfont-close" @click="closeBox"></span>
      </div>
      <div class="dialog-bd">
        <p class="txt">
          <span class="icon-xiangqing iconfont iconfont-tip"></span>
          您尚未设置车队首页，请前往首页管理页面
        </p>   
      </div>
      <div slot="footer" class="dialog-foot">
        <a href="javascript:void(0);" class="btn" @click="rtnLink">立即设置</a>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'setMotorcadeTipBox',
  props: {
    setMotorcadeTipBoxShow: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:setMotorcadeTipBoxShow', newVal)

    },
    setMotorcadeTipBoxShow (newVal) {
      this.boxShow = newVal
    },
  },
  data () {
    return {
      boxShow: this.setMotorcadeTipBoxShow
    }
  },
  methods: {
    closeBox () {
      this.boxShow = false
    },
    rtnLink () {
      this.$router.push({ name: 'motorcadeInfo' })
    }
  }
}
</script>
<style lang="less" scope>
.setMotorcadeTipBox {
  & /deep/ .el-dialog {
    width: 414px;
    height: 214px;
    border-radius: 4px;
    .el-dialog__body {
      padding: 0px;
    }
    .el-dialog__footer {
      text-align: center;
      padding: 0;
    }
  }
  .dialog-header {
    position: relative;
    .title {
      font-size: @fontsize-large;
      color: @color-dark-grey;
    }
    .iconfont-close {
      position: absolute;
      right: 0;
      top: 4px;
      padding: 10px;
      color: @color-grey;
      font-size: 18px;
      cursor: pointer;
    }
  }
  .dialog-bd {
    text-align: center;
    .txt {
      margin: 44px 0;
      color: @color-dark-grey;
      font-size: 14px;
    }
    .iconfont-tip {
      color: #FFAA25;
      font-size: 16px;
      margin-right: 4px;
    }
  }
  .dialog-foot {
    .btn {
      width: 160px;
      height: 40px;
      line-height: 40px;
      margin: 0 auto;
    }
  }
}
</style>

