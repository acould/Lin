<template>
  <el-dialog :visible.sync="boxShow" class="audioBoxdialog">
    <div class="dialog-header" slot="title">
      <p class="title">语音输入</p>
    </div>
    <p class="tip">请打开车队助手APP长按语音录入</p>
    <img :src="qrCodeSrc" class="qrcode">
    <p slot="footer" class="foottext">手机扫一扫 下载App</p>
  </el-dialog>
</template>
<script> 
import qrCode from '@/assets/images/QRcode.png'
export default {
  name: 'AudioBox',
  props: {
    audioBoxShow: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      boxShow: this.audioBoxShow,
      qrCodeSrc: qrCode
    }
  },
  watch: {
    boxShow (newVal) {
      this.$emit('update:audioBoxShow', newVal)
    },
    audioBoxShow(newVal) {
      this.boxShow = newVal
    }
  },
  methods: {

  }
}
</script>
<style lang="less" scoped>
.audioBoxdialog {
  & /deep/ .el-dialog {
    width: 395px;
    height: 283px;
    padding: 0;
    .el-dialog__body {
      padding: 0;
    }
    .el-dialog__footer {
      padding: 0;
    }
  }
  .tip {
    font-size: 14px;
    text-align: center;
    color: @theme-color;
    margin: 25px 0 10px 0;
  }
  .qrcode {
    display: block;
    width: 104px;
    height: 104px;
    margin: 0 auto 30px;
  }
  .foottext {
    text-align: center;
    margin-top: 21px;
  }
}
</style>
