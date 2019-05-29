<template>
  <el-dialog :visible.sync="boxShow" class="qrdialog" :show-close=false>
    <div class="dialog-header" slot="title">
      <p class="title">手机扫一扫 下载App</p>
      <span class="iconfont icon-close iconfont-close" @click="closeBox"></span>
    </div>
    <img :src="qrCodeSrc" class="qrcode" v-show="type == 1">
    <img :src="qrCodeSrc2" class="qrcode" v-show="type == 2">
    <div slot="footer" class="dialog-foot">
      <a href="javascript:void(0)" class="btn-tab" :class="type == 1 ?  'selected' :''"  @click="toggleCodeType(1)">管理员APP</a>
      <a href="javascript:void(0)" class="btn-tab" :class="type == 2 ?  'selected' :''" @click="toggleCodeType(2)">司机端</a>
    </div>
  </el-dialog>
</template>
<script> 
import qrCode from '../../assets/images/QRcode.png'
import qrCode2 from '../../assets/images/QRcode2.png'
export default {
  name: 'qrCodeBox',
  props: {
    qrCodeBoxShow: {
      type: Boolean,
      default: false
    },
    codeType: {
      type: Number,
      default: 1
    }
  },
  data () {
    return {
      boxShow: this.qrCodeBoxShow,
      qrCodeSrc: qrCode,
      qrCodeSrc2: qrCode2,
      type: this.codeType
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:qrCodeBoxShow', newVal)
    },
    qrCodeBoxShow (newVal) {
      this.boxShow = newVal
    },
    codeType (newVal) {
      this.type = newVal
    }
  },
  methods: {
    closeBox () {
      this.boxShow = false
    },
    toggleCodeType (type) {
      this.type = type
    }
  }
}
</script>
<style lang="less" scoped>
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
  .qrcode {
    width: 320px;
    height: 320px;
    margin: 10px 0 0 30px;
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
  .dialog-foot {
    width: 320px;
    margin: 14px auto 0;
    overflow: hidden;
    .btn-tab {
      float: left;
      font-size: 18px;
      color: #333;
      width: 50%;
      box-sizing: border-box;
      text-align: center;
      &:first-of-type {
        border-right: 1px solid #555;
      }
      &.selected {
        color: @theme-color;
      }
    }
  }
}
</style>
