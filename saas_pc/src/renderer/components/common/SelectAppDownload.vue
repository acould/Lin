<template>
  <div>
    <el-dialog :visible.sync="boxShow" class="qrdialog" :show-close=false :close-on-click-modal = false>
      <div class="dialog-header" slot="title">
        <p class="title">请选择身份</p>
        <span class="iconfont icon-close iconfont-close" @click="closeBox"></span>
      </div>
      <div class="dialog-bd">
        <div class="btn-wrap">
          <a href="javascript:void(0)" class="btn btn-nobg btn-nobg-theme" @click="popupApp(1)">管理员APP</a>
          <a href="javascript:void(0)" class="btn btn-nobg btn-nobg-theme" @click="popupApp(2)">司机端APP</a>
        </div>
      </div>
    </el-dialog>
    <qrCodeBox :qrCodeBoxShow.sync="qrCodeBoxShow" :code-type="type"></qrCodeBox>
  </div>

</template>
<script>
export default {
  name: 'selectAppBox',
  props: {
    selectAppBoxShow: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:selectAppBoxShow', newVal)
    },
    selectAppBoxShow (newVal) {
      this.boxShow = newVal
    }
  },
  data () {
    return {
      boxShow: this.selectAppBoxShow,
      qrCodeBoxShow: false,
      type: null
    }
  },
  methods: {
    closeBox () {
      this.boxShow = false
    },
    popupApp(type) {
      this.type = type
      this.qrCodeBoxShow =true
      this.closeBox ()
    }
  }
}
</script>
<style lang="less" scope>
.qrdialog {
  & /deep/ .el-dialog {
    width: 380px;
    height: 442px;
    padding: 0;
    .el-dialog__body {
      padding: 0;
    }
    .el-dialog__footer {
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
    .btn-wrap {
      width: 283px;
      margin: 110px auto;
      .btn {
        margin-bottom: 50px;
        border-radius: 23px;
        height: 45px;
        line-height: 45px;
        &:last-child {
          margin-bottom: 0;
        }
      }
    }
  }
}
</style>

